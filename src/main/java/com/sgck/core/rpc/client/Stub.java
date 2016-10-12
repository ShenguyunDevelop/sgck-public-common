package com.sgck.core.rpc.client;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import com.sgck.core.amf.Amf3Input;
import com.sgck.core.amf.Amf3Output;
import com.sgck.core.rpc.server.InvokeError;
import com.sgck.core.rpc.server.InvokeFeedback;
import com.sgck.core.rpc.server.InvokeResult;

import flex.messaging.io.amf.ASObject;

/// <summary>
/// 远程调用�?
/// </summary>
public class Stub {
	public enum InvokeType {
		SOCKET, HTTP
	}

	// ----- const and static properties
	static private byte[] STR_VERSION = { 'a', 'm', 'f', '0', '0', '1' };
	static private int HEAD_SIZE = 32;
	static private byte[] crypt = { 'A', 'M', 'F', '_', 'R', 'E', 'M', 'O',
			'T', 'I', 'N', 'G', '_', 'H', 'E', 'A', 'D' };
	static int crypt_len = 12;
	static int m_nPackageCounter = 1;
	static final byte PACKET_CMD_BREAK = 0; // 0 断开连接
	static final byte PACKET_CMD_RPC = 1; // 1 远程调用请求�?
	static final byte PACKET_CMD_RET = 2; // 2 远程调用返回�?
	static final byte PACKET_CMD_EVENT = 3; // 3 事件通知
	static final byte PACKET_CMD_MESSAGE = 4; // 4 实时消息
	static final byte TAG_HAS_CLIENT_INFO = 0X01; // 包含 clientInfo
	static MessageDigest digester;

	private Socket _remoteSock;
	private RemoteMethodArray _callList = new RemoteMethodArray();
	private ArrayList<RemoteCallBack> _cbList = new ArrayList<RemoteCallBack>();
	private ASObject _clientInfo;
	private OutputStream netWriter;
	private InputStream netReader;
	private String cur_host;
	private int cur_port;
	// 如果发现连接异常，则自动重连
	private boolean AutoReConnect = true;
	// 调用�?��远程连接后，自动断开
	// 如果使用长连接时，请�?AutoCloseConnect 设置�?true
	private boolean AutoCloseConnect = true;
	// 远程调用请求的发送方�?
	private InvokeType invokeType = InvokeType.SOCKET;
	// 用于HTTP方式的远程调�?
	private URL invokeURL;
	private HttpURLConnection connection;
	private String _postUrl;

	// / <summary>
	// / 构�?函数
	// / </summary>
	public Stub() {
	}

	public Stub(String host, int port) {
		cur_host = host;
		cur_port = port;
	}

	public Stub(String url) {
		_postUrl = url;
		SetInvokeType(Stub.InvokeType.HTTP);
	}

	public Stub(String host, int port, ASObject clientInfo) {
		cur_host = host;
		cur_port = port;
		_clientInfo = clientInfo;
	}

	public Stub(String url, ASObject clientInfo) {
		_postUrl = url;
		_clientInfo = clientInfo;
		SetInvokeType(Stub.InvokeType.HTTP);
	}

	public Stub(ASObject clientInfo) {
		_clientInfo = clientInfo;
	}

	public boolean isAutoReConnect() {
		return AutoReConnect;
	}

	public void setAutoReConnect(boolean autoReConnect) {
		AutoReConnect = autoReConnect;
	}

	public boolean isAutoCloseConnect() {
		return AutoCloseConnect;
	}

	public void setAutoCloseConnect(boolean autoCloseConnect) {
		AutoCloseConnect = autoCloseConnect;
	}

	private void SetInvokeType(InvokeType it) {
		invokeType = it;
	}

	public void Close() {
		CloseConnect();
		_callList = null;
	}

	public void Connect(String host, int port) throws IOException {
		if (IsConnected()) {
			return;
		}

		// 为自动重连准备的host/port
		cur_host = host;
		cur_port = port;

		_remoteSock = new Socket(host, port);
		_remoteSock.setTcpNoDelay(true);
		_remoteSock.setSoTimeout(3000);

		netWriter = _remoteSock.getOutputStream();
		netReader = _remoteSock.getInputStream();
	}

	public void Connect(String postUrl) throws IOException {
		if (IsConnected()) {
			return;
		}

		_postUrl = postUrl;

		invokeURL = new URL(_postUrl);

		_restHTTPConnect();
	}

	private void _restHTTPConnect() throws IOException {
		if (invokeURL == null) {
			return;
		}

		if (connection != null) {
			connection.disconnect();
		}

		connection = (HttpURLConnection) invokeURL.openConnection();

		// 设置是否向connection输出，因为这个是post请求，参数要放在
		// http正文内，因此�?��设为true
		connection.setDoOutput(true);
		// Read from the connection. Default is true.
		connection.setDoInput(true);

		// Set the post method. Default is GET
		connection.setRequestMethod("POST");

		connection.setRequestProperty("ContentType",
				"application/x-www-form-urlencoded");

		connection.setReadTimeout(60000);

		connection.connect();
	}

	public void ReConnect() throws IOException {
		if (IsConnected()) {
			CloseConnect();
		}

		if (invokeType == InvokeType.SOCKET) {
			Connect(cur_host, cur_port);
		} else {
			Connect(_postUrl);
		}
	}

	public void CloseConnect() {
		try {
			if (invokeType == InvokeType.SOCKET) {
				_remoteSock.close();
			} else {
				connection.disconnect();
				invokeURL = null;
			}
		} catch (Exception e) {
		}
	}

	public boolean IsConnected() {
		if (invokeType == InvokeType.SOCKET) {
			if (null == _remoteSock) {
				return false;
			}
			return _remoteSock.isConnected() && !_remoteSock.isClosed();
		} else {
			return invokeURL != null && connection != null;
		}
	}

	public void SetClientInfo(ASObject clientInfo) {
		_clientInfo = clientInfo;
	}

	// / <summary>
	// / 添加远程调用到调用列�?
	// / </summary>
	// / <param name="className">远程类名</param>
	// / <param name="funName">远程方法�?/param>
	// / <param name="cb">回调函数</param>
	// / <param name="funParams">远程方法的参数列�?/param>
	public void AddCall(String className, String funName, RemoteCallBack cb,
			Object[] funParams) {
		try {
			RemoteMethodObject method = new RemoteMethodObject(funName,
					className);
			method.SetParams(funParams);
			_callList.Add((Object) method.GetRemoteMethodObject());
			_cbList.add(cb);
		} catch (Exception e) {

		}
	}

	// / <summary>
	// / 把FLEX设置好的DS远程调用列表直接设置进该对象
	// / </summary>
	// / <param name="callList">远程调用列表</param>
	public void SetCallList(Collection<Object> callList) {
		_callList = new RemoteMethodArray(callList);
	}

	// / <summary>
	// / 清除远程调用列表
	// / </summary>
	public void ClearCalls() {
		_callList = new RemoteMethodArray();
		_cbList.clear();
	}

	public Object[] Commit() throws Exception {
		ArrayList<ASObject> ret;
		if (invokeType == InvokeType.SOCKET) {
			ret = CommitByTCP();
		} else {
			ret = CommitByHTTP();
		}

		int errCode;
		for (int i = 0; i < _cbList.size(); i++) {
			if (_cbList.get(i) != null) {
				if ((errCode = ((Number) ret.get(i).get("code")).intValue()) == 0) {
					_cbList.get(i).onOK(ret.get(i).get("ret"));
				} else {
					_cbList.get(i).onError(errCode,
							(String) ret.get(i).get("what"));
				}
			}
		}

		return ret.toArray();
	}

	/**
	 * 兼容sg8k的远程调用格式
	 * @param className
	 * @param funName
	 * @param funParams
	 * @return
	 * @throws Exception
	 */
	public Object CallDirectLegacy(String className, String funName, Object[] funParams) throws Exception
	{
		ClearCalls();
		AddCall(className, funName, null, funParams);
		Object[] retArray;
		if (invokeType == InvokeType.SOCKET)
		{
			retArray = CommitByTCP().toArray();
		}
		else
		{
            //统计发出去的次数和大小
//				if(null == HessianProxy.trafficStatisticsMap.get(funName))
//				{
//					TrafficStatistic traff = new TrafficStatistic();
//					HessianProxy.trafficStatisticsMap.put(funName, traff);
//				}
//				traffic  = HessianProxy.trafficStatisticsMap.get(funName);
//			UpperStatisticConst.count++;
			
			retArray = CommitByHTTP().toArray();
		}
				
		if (retArray.length > 0)
		{
			ASObject ret = (ASObject) retArray[0];
			Object code = ret.get("code");
			if (null == code)
			{
				throw new Exception("the data format from server is error : [code]!");
			}

			Object retObject;
			int i_code = (Integer) (code);
			if (i_code == 0)
			{
				retObject = ret.get("ret");
//					if (null == retObject)
//					{
//						throw new Exception("the data format from server is error : [ret]!");
//					}
			}
			else
			{
				retObject = ret.get("what");
				if (null == retObject)
				{
					throw new Exception("the data format from server is error : [what]!");
				}
				throw new Exception("error code : " + code + "], what : " + retObject);
			}

			return retObject;
		}
		else
		{
			throw new Exception("the result not Commit success!");
		}
	}
	
	// / <summary>
	// / 远程调用DS的一个接口，返回远程调用结果
	// / </summary>
	// / <param name="className">远程类名</param>
	// / <param name="funName">远程方法�?/param>
	// / <param name="funParams">远程方法的参数列�?/param>
	// / <returns></returns>
	public Object CallDirect(String className, String funName,
			Object[] funParams) throws Exception {
		ClearCalls();
		AddCall(className, funName, null, funParams);
		Object[] retArray;
		if (invokeType == InvokeType.SOCKET) {
			retArray = CommitByTCP().toArray();
		} else {
			// 统计发出去的次数和大�?
			// if(null == HessianProxy.trafficStatisticsMap.get(funName))
			// {
			// TrafficStatistic traff = new TrafficStatistic();
			// HessianProxy.trafficStatisticsMap.put(funName, traff);
			// }
			// traffic = HessianProxy.trafficStatisticsMap.get(funName);
			// UpperStatisticConst.count++;

			retArray = CommitByHTTP().toArray();
		}

		if (retArray.length > 0) {
			Object obj = retArray[0];
			InvokeFeedback ret = (InvokeFeedback) obj;
			int code = ret.getCode();

			Object retObject;
			if (code == 0) {
				InvokeResult ir = (InvokeResult) ret;
				retObject = ir.getRet();
			} else {
				InvokeError retError = (InvokeError) ret;
				retObject = retError.getWhat();
				if (null == retObject) {
					throw new Exception(
							"the data format from server is error : [what]!");
				}
				throw new Exception("error code : " + code + "], what : "
						+ retObject);
			}

			return retObject;
		} else {
			throw new Exception("the result not Commit success!");
		}
	}

	public Object CallDirectEx(String className, String funName,
			Object[] funParams) throws Exception {
		ClearCalls();
		AddCall(className, funName, null, funParams);
		Object[] retArray;
		if (invokeType == InvokeType.SOCKET) {
			retArray = CommitByTCP().toArray();
		} else {
			retArray = CommitByHTTP().toArray();
		}

		if (retArray.length > 0) {
			return (InvokeFeedback) retArray[0];
		} else {
			throw new Exception("the result not Commit success!");
		}
	}

	public int CreatePacketCounter() {
		return m_nPackageCounter++;
	}

	private ArrayList CommitByHTTP() throws Exception {
		try {
			if (AutoReConnect && !IsConnected()) {
				ReConnect();
			}

			ByteArrayOutputStream mmStream = new ByteArrayOutputStream();
			Amf3Output amfWriter = new Amf3Output();
			amfWriter.setOutputStream(mmStream);
			if (null == _clientInfo) {
				_clientInfo = new ASObject();
			}
			byte tags[] = new byte[1];
			tags[0] = 0;
			if (null != _clientInfo) {
				amfWriter.writeObject(_clientInfo);
				tags[0] |= 1;
			}

			amfWriter.writeObject(_callList.GetArray());
			amfWriter.flush();

			// 准备要发送的数据�?
			int counter = CreatePacketCounter();
			byte[] header = GetRpcHeader((int) mmStream.size() + HEAD_SIZE,
					counter, PACKET_CMD_RPC, tags);

			// 发�?到网�?
			netWriter = connection.getOutputStream();
			netWriter.write(header, 0, HEAD_SIZE);
			netWriter.write(mmStream.toByteArray(), 0, (int) mmStream.size());
			netWriter.flush();
			// sended = mmStream.size() + HEAD_SIZE;
			// UpperStatisticConst.sendLength += mmStream.size() + HEAD_SIZE;

			netReader = connection.getInputStream();
			// readed = netReader.available();
			// UpperStatisticConst.receiveLength += netReader.available();

			byte[] buf = GetRpcRecData(netReader, tags);

			ByteArrayInputStream objStm = new ByteArrayInputStream(buf);

			// AMF读取�?
			Amf3Input tmpAMFReader = new Amf3Input();
			tmpAMFReader.setInputStream(objStm);

			// 读取ClientInfo
			if ((tags[0] & 1) > 0) {
				_clientInfo = (ASObject) tmpAMFReader.readObject();
			}

			return (ArrayList) tmpAMFReader.readObject();
		} catch (Exception e) {
			throw new IOException(e.toString());
		} finally {
			if (AutoCloseConnect) {
				CloseConnect();
			} else {
				_restHTTPConnect();
			}
		}

	}

	// / <summary>
	// / 提交远程调用协议
	// / </summary>
	// / <returns>远程调用结果</returns>
	private ArrayList CommitByTCP() throws Exception {
		try {
			byte[] tags = new byte[1];
			tags[0] = (byte) 0xff;
			byte[] buf = _CommitByTCPNoDecode(tags);
			int len = buf.length;

			//
			// Decode
			//
			ByteArrayInputStream objStm = new ByteArrayInputStream(buf);

			// AMF读取�?
			Amf3Input tmpAMFReader = new Amf3Input();
			tmpAMFReader.setInputStream(objStm);

			// 读取ClientInfo
			if ((tags[0] & TAG_HAS_CLIENT_INFO) > 0) {
				_clientInfo = (ASObject) tmpAMFReader.readObject();
			}

			// 读取数据
			ArrayList tmpReData = (ArrayList) tmpAMFReader.readObject();

			return (ArrayList) tmpAMFReader.readObject();
		} catch (Exception e) {
			throw new Exception("CommitByTCP error: " + e.getMessage());
		}
	}

	private byte[] CommitByTCPNoDecode() throws Exception {
		byte[] tags = new byte[1];
		byte[] buf = _CommitByTCPNoDecode(tags);
		return buf;
	}

	protected byte[] _CommitByTCPNoDecode(byte[] tags) throws Exception {
		try {
			if (AutoReConnect && !IsConnected()) {
				ReConnect();
			}

			// 发�?数据... 将数据写入流 stream
			RpcSend(_clientInfo, _callList.GetArray());
			// 接收服务器返回的数据
			while (true) {
				byte[] b = GetRpcRecData(this.netReader, tags);
				if (null != b) {
					if (AutoCloseConnect) {
						this.CloseConnect();
					}
					return b;
				}
			}
		} catch (Exception ex) {
			if (AutoCloseConnect) {
				this.CloseConnect();
			}
			throw new Exception(ex.getMessage());
		}
	}

	// ----------------------------------------------------------
	// / <summary>
	// / 向DS服务器发送请求，并返回结�?HTTP)
	// / </summary>
	// / <param name="callerList">请求参数列表</param>
	// / <param name="clientObject">客户端对�?/param>
	// / <returns>通讯结果</returns>
	// public Object[] Send(Array callerList, ASObject clientObject)
	// {
	// try
	// {
	// //return Send2(callerList, clientObject);
	// //HTTP请求对象
	// var req = (HttpWebRequest)WebRequest.Create(_postUrl);

	// //构�?Http头（基本参数�?
	// req.Method = HtConfig.HttpMethod;
	// req.ContentType = HtConfig.HttpContentType;
	// req.SendChunked = HtConfig.HttpSendChunked;

	// // 发�?数据
	// var stream = req.GetRequestStream();
	// RpcSend(clientObject, callerList);
	// stream.Close();

	// //获得返回的Http�?
	// var rep = (HttpWebResponse)req.GetResponse();
	// var receiveStream = rep.GetResponseStream();

	// _recObjArr = GetRpcRecData(receiveStream);
	// return _recObjArr;
	// }
	// catch (Exception ex)
	// {
	// throw new Exception(ex.Message, ex);
	// }
	// }

	public static MessageDigest getMD5() throws Exception {
		try {
			if (null == digester) {
				digester = MessageDigest.getInstance("MD5");
			}
			return (MessageDigest) digester.clone();
		} catch (Exception e) {
			throw new Exception("no md5 supported!");
		}
	}

	static public byte[] CreateMD5(byte[] src, int offset, int len)
			throws Exception {
		try {
			len -= 16;
			MessageDigest md5 = Stub.getMD5();
			md5.reset();

			byte buf[] = new byte[(int) (len + crypt_len)];
			ByteBuffer writer = ByteBuffer.wrap(buf);
			writer.put(crypt, 0, crypt_len);
			writer.put(src, 0, len);
			md5.update(buf);
			// stm.Write(src, offset, len);
			// return md5.ComputeHash(stm.GetBuffer(), 0, crypt_len + len);
			return md5.digest();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	public static String CreateMD5String(byte[] buf) throws Exception {
		MessageDigest md5 = Stub.getMD5();
		md5.reset();
		md5.update(buf);
		byte s[] = md5.digest();

		String result = "";

		for (int i = 0; i < s.length; i++) {

			result += Integer.toHexString((0x000000ff & s[i]) | 0xffffff00)
					.substring(6);

		}
		return result;
	}

	static public byte[] GetRpcHeader(int len, int counter, byte cmd,
			byte[] tags) throws Exception {
		try {
			/*
			 * 4 + 4 + 2 + 6 + 16 = 32 uint32 len; // 数据包长度，含长�? uint32 count;
			 * // 包号，当有多个包同时返回�?用来识别请求与返回包的关系，同时起到加密混淆的作�? byte cmd; // 包的指令类型:
			 * //#define PACKET_CMD_BREAK 0 // 断开连接 //#define PACKET_CMD_RPC 1
			 * // 远程调用请求�? //#define PACKET_CMD_RET 2 // 远程调用返回�? //#define
			 * PACKET_CMD_EVENT 3 // 事件通知 //#define PACKET_CMD_MESSAGE 4 // 实时消息
			 * char tag; // 附加标识 char strVersion[6]; // 版本�? unsigned char
			 * md5[16]; // md5 包头校验
			 */
			ByteArrayOutputStream stm = new ByteArrayOutputStream();
			DataOutputStream writer = new DataOutputStream(stm);

			writer.writeInt(len);
			writer.writeInt(counter);
			writer.writeByte(cmd);
			writer.writeByte(tags[0]);
			writer.write(STR_VERSION, 0, 6);

			byte[] fromData = stm.toByteArray(); // stm.GetBuffer();
			byte[] targetData = CreateMD5(fromData, 0, Stub.HEAD_SIZE);

			writer.write(targetData, 0, 16);
			writer.write(targetData, 0, 16); /* 协议又增加了16字节的时�? */

			return stm.toByteArray(); // stm.GetBuffer();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	// read header, check by md5
	// if ok, return the header memoryStream
	// else return null;
	public byte[] ReadHeader(InputStream receiveStream) throws Exception {
		byte[] head = new byte[HEAD_SIZE];
		if (readFromNet(receiveStream, head, HEAD_SIZE) == HEAD_SIZE) {
			// MemoryStream stm = new MemoryStream(head, false);
			byte[] targetData = CreateMD5(head, 0, HEAD_SIZE);
			for (int i = 0; i < 16; i++) {
				if (targetData[i] != head[i + 16]) {
					throw new Exception("数据格式不正�? iLen < 0"); // MD5校验失败
				}
			}
			return head;
		} else {
			return null;
		}
	}

	// / <summary>
	// / 设置并发送AMF数据
	// / </summary>
	// / <param name="headerData">协议�?/param>
	// / <param name="clientObject">扩展数据�?/param>
	// / <param name="callerList">调用请求列表</param>
	// / <param name="stream">HTTP的数据流</param>
	private void RpcSend(ASObject clientObject, Object[] callerList)
			throws Exception {
		try {
			// 准备要写入的clientInfo, Amf对象
			ByteArrayOutputStream mmStream = new ByteArrayOutputStream();
			Amf3Output amfWriter = new Amf3Output();
			amfWriter.setOutputStream(mmStream);
			if (null == clientObject) {
				clientObject = new ASObject();
			}
			byte tags[] = new byte[1];
			tags[0] = 0;
			if (null != clientObject) {
				amfWriter.writeObject(clientObject);
				tags[0] |= TAG_HAS_CLIENT_INFO;
			}
			amfWriter.writeObject(callerList);
			amfWriter.flush();

			// 准备要发送的数据�?
			int counter = this.CreatePacketCounter();
			byte[] header = GetRpcHeader((int) mmStream.size() + HEAD_SIZE,
					counter, PACKET_CMD_RPC, tags);

			// 发�?到网�?
			netWriter.write(header, 0, HEAD_SIZE);
			netWriter.write(mmStream.toByteArray(), 0, (int) mmStream.size());
			netWriter.flush();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	private int readFromNet(InputStream s, byte[] bytes, int numBytesToRead)
			throws Exception {
		int numBytesRead = 0;
		try {
			while (numBytesToRead > 0) {
				// Read may return anything from 0 to numBytesToRead.
				int n = s.read(bytes, numBytesRead, numBytesToRead);
				// The end of the file is reached.
				if (n == 0) {
					break;
				}
				// continue;
				numBytesRead += n;
				numBytesToRead -= n;
			}

			return numBytesRead;
		} catch (Exception ex) {
			// 当数据接收不完整�?自动断开连接,以防止数据错�?
			CloseConnect();
			throw new Exception("数据接收超时，自动断�?���? 已接收字节数 = " + numBytesRead
					+ " 剩余字节�?= " + numBytesToRead, ex);
		}
	}

	// / <summary>
	// / 验证返回的数据的有效性，并取出数�?
	// / </summary>
	// / <param name="receiveStream">HTTP的返回数据流</param>
	// / <returns>返回的数�?/returns>
	public byte[] GetRpcRecData(InputStream receiveStream, byte[] tags)
			throws Exception {
		try {
			byte[] head = ReadHeader(receiveStream);
			if (null == head) {
				throw new Exception("数据格式不正�? 数据加密校验不�?过；");
			}

			// MemoryStream stm = new MemoryStream(head, false);
			// BinaryReader reader = new BinaryReader(stm);
			ByteArrayInputStream stm = new ByteArrayInputStream(head);
			DataInputStream reader = new DataInputStream(stm);

			int len = reader.readInt();
			int counter = reader.readInt();
			byte cmd = reader.readByte();
			tags[0] = reader.readByte();
			byte[] strVersion = new byte[6];
			reader.read(strVersion, 0, 6);
			int iLen = (int) len - 32;
			if (iLen < 0) {
				throw new Exception("数据格式不正�? iLen < 0");
			}

			byte[] buf = new byte[iLen];
			if (iLen == 0) {
				return buf; // 没有数据�?
			}

			// receiveStream.ReadTimeout = 5000;
			int recLen = readFromNet(receiveStream, buf, iLen);
			if (recLen != iLen) {
				throw new Exception("数据接收超时，调用失�? recLen = " + recLen
						+ "timeout = ?");
			}

			switch (cmd) {
			case PACKET_CMD_RET: {// 远程调用返回
				return buf;
			}
			case PACKET_CMD_EVENT: {// 发�?事件
				return null;
			}
			default:
				throw new Exception("服务器返回的协议不正确，无效的CMD，强制断");
			}
		} catch (Exception ex) {
			throw new Exception(
					"服务器返回的协议不正确，强制断开连接[ " + ex.getMessage() + " ]", ex);
		}
	}

	// ----------------------------------------------------------

	public static void main(String[] args) {

		String aa = "123456";
		try {
			String result = (Stub.CreateMD5String(aa.getBytes()));

			System.out.println(result);

		} catch (Exception e) {
		}

	}
}
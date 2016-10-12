package com.sgck.common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

import com.sgck.common.log.DSLogger;

/*import vo.EventModel;
import com.sgck.manager.InitConfig;*/

import flex.messaging.io.amf.ASObject;

public class FileOperateUtils {
	private static int incfilecount = 0;// 鍐欏叆鏂囦欢鐨勮鏁板櫒
	private static int localfilecount = 0;// 鏈骇鏈嶅姟鍣ㄦ枃浠惰鏁板櫒

	/**
	 * 鍒涘缓鏂囦欢澶�
	 * 
	 * @param serveridDiro
	 */
	public static void createDirectory(String path) {
		File file = new File(path);
		if (!file.exists() || !file.isDirectory()) {
			file.mkdirs();
		}
	}

	public static void createFile(String filePath) throws IOException {
		File file = new File(filePath);
		if (file == null || !file.isFile()) {
			file.createNewFile();
		}
	}

	/**
	 * 鍒犻櫎鐩綍
	 * 
	 * @param dir
	 * @return
	 */
	public static boolean deleteDirectory(String dir) { // 濡傛灉dir涓嶄互鏂囦欢鍒嗛殧绗︾粨灏撅紝鑷姩娣诲姞鏂囦欢鍒嗛殧绗�
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir); // 濡傛灉dir瀵瑰簲鐨勬枃浠朵笉瀛樺湪锛屾垨鑰呬笉鏄竴涓洰褰曪紝鍒欓�鍑�
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		boolean flag = true;
		File[] files = dirFile.listFiles(); // 鍒犻櫎鏂囦欢澶逛笅鐨勬墍鏈夋枃浠�鍖呮嫭瀛愮洰褰�
		for (int i = 0; i < files.length; i++) {
			if (files[i].isFile()) { // 閫掑綊鍒犻櫎瀛愭枃浠�
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			} else { // 閫掑綊鍒犻櫎瀛愮洰褰�
				flag = deleteDirectory(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}
		if (!flag) {
			return false;
		}

		if (dirFile.delete()) { // 鍒犻櫎褰撳墠鐩綍鏈韩
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 鍒犻櫎鏂囦欢
	 * 
	 * @param fileName
	 * @return
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 绉诲姩鏂囦欢
	 * 
	 * @param sourceFileParam
	 * @param targetFileParam
	 */
	public static void moveFile(String sourceFileParam, String targetFileParam) {
		Properties prop = System.getProperties();
		String os = prop.getProperty("os.name");
		if (os.startsWith("win") || os.startsWith("Win")) {
			File sourceFile = new File(sourceFileParam);
			File targetFile = new File(targetFileParam);
			sourceFile.renameTo(targetFile);
		} else {
			String mvcmd = "mv " + sourceFileParam + " " + targetFileParam;
			Runtime r = Runtime.getRuntime();
			try {
				r.exec(mvcmd);
			} catch (IOException e) {
				DSLogger.error("mv data failed:" + e.getMessage());
			}
		}
	}

	/**
	 * 鎸夌洰褰曞悕杩涜鎺掑簭
	 *
	 * @param path
	 *            鐩綍璺緞
	 * @return 鎺掑簭鍚庣殑鐩綍鍚�
	 * @throws Exception
	 */
	public static SortedSet<String> getSortDirList(String path) {
		SortedSet<String> sortSet = new TreeSet<String>();
		try {
			File file = new File(path);
			if (!file.exists()) {
				return null;
			}

			File[] files = file.listFiles();
			if (null == files || files.length == 0) {
				return null;
			}

			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					File file1 = new File(files[i].getPath());
					File[] files1 = file1.listFiles();
					if (null == files1 || files1.length == 0) {
						if (files.length == 1)// 1.濡傛灉璇ユ枃浠跺す涓嬫病鏈夊瓙鏂囦欢澶规垨瀛愭枃浠跺す锛屽苟涓斿彧鏈変竴涓繖1涓枃浠跺す锛屽垯鐩存帴杩斿洖绌猴紱
						{
							return null;
						} else if (files.length > 1)// 2.濡傛灉璇ユ枃浠跺す涓嬫病鏈夊瓙鏂囦欢澶规垨瀛愭枃浠跺す锛屼絾鏄繕鏈夊涓枃浠跺す锛岃鏄庤鏂囦欢澶逛笅鐨勫唴瀹瑰凡缁忓彂閫佸畬姣曪紝鍒欏垹闄よ鏂囦欢澶癸紱
						{
							file1.delete();
						}
					} else// 濡傛灉鏂囦欢澶逛笅瀛樺湪瀛愭枃浠跺す鎴栨枃浠讹紝鍒欏皢璇ユ枃浠跺す鍚嶅啓鍏ortSet涓�
					{
						String fileName = files[i].getPath();
						sortSet.add(fileName);
					}
				}
			}
		} catch (Exception e) {
			DSLogger.error("getSortDirList error:" + e.getMessage());
			return null;
		}

		return sortSet;

	}

	public static Set<String> getDirList(String path) {
		Set<String> sortSet = new HashSet<String>();
		try {
			File file = new File(path);
			if (!file.exists()) {
				return null;
			}

			File[] files = file.listFiles();
			if (null == files || files.length == 0) {
				return null;
			}

			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					File file1 = new File(files[i].getPath());
					File[] files1 = file1.listFiles();
					if (null == files1 || files1.length == 0) {
						if (files.length == 1)// 1.濡傛灉璇ユ枃浠跺す涓嬫病鏈夊瓙鏂囦欢澶规垨瀛愭枃浠跺す锛屽苟涓斿彧鏈変竴涓繖1涓枃浠跺す锛屽垯鐩存帴杩斿洖绌猴紱
						{
							return null;
						} else if (files.length > 1)// 2.濡傛灉璇ユ枃浠跺す涓嬫病鏈夊瓙鏂囦欢澶规垨瀛愭枃浠跺す锛屼絾鏄繕鏈夊涓枃浠跺す锛岃鏄庤鏂囦欢澶逛笅鐨勫唴瀹瑰凡缁忓彂閫佸畬姣曪紝鍒欏垹闄よ鏂囦欢澶癸紱
						{
							file1.delete();
						}
					} else// 濡傛灉鏂囦欢澶逛笅瀛樺湪瀛愭枃浠跺す鎴栨枃浠讹紝鍒欏皢璇ユ枃浠跺す鍚嶅啓鍏ortSet涓�
					{
						String fileName = files[i].getPath();
						sortSet.add(fileName);
					}
				}
			}
		} catch (Exception e) {
			DSLogger.error("getSortDirList error:" + e.getMessage());
			return null;
		}

		return sortSet;

	}

	public static SortedSet<Integer> getSortLastDirList(String path) {
		SortedSet<Integer> sortSet = new TreeSet<Integer>();
		try {
			File file = new File(path);
			if (!file.exists()) {
				return null;
			}

			File[] files = file.listFiles();
			if (null == files || files.length == 0) {
				return null;
			}

			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					File file1 = new File(files[i].getPath());
					File[] files1 = file1.listFiles();
					if (null == files1 || files1.length == 0) {
						if (files.length == 1)// 1.濡傛灉璇ユ枃浠跺す涓嬫病鏈夊瓙鏂囦欢澶规垨瀛愭枃浠跺す锛屽苟涓斿彧鏈変竴涓繖1涓枃浠跺す锛屽垯鐩存帴杩斿洖绌猴紱
						{
							return null;
						} else if (files.length > 1)// 2.濡傛灉璇ユ枃浠跺す涓嬫病鏈夊瓙鏂囦欢澶规垨瀛愭枃浠跺す锛屼絾鏄繕鏈夊涓枃浠跺す锛岃鏄庤鏂囦欢澶逛笅鐨勫唴瀹瑰凡缁忓彂閫佸畬姣曪紝鍒欏垹闄よ鏂囦欢澶癸紱
						{
							file1.delete();
						}
					} else// 濡傛灉鏂囦欢澶逛笅瀛樺湪瀛愭枃浠跺す鎴栨枃浠讹紝鍒欏皢璇ユ枃浠跺す鍚嶅啓鍏ortSet涓�
					{
						String fileName = files[i].getPath();
						int lastindex = (fileName.lastIndexOf("/") == -1 ? fileName.lastIndexOf("\\")
								: fileName.lastIndexOf("/"));
						String sdir = fileName.substring(lastindex + 1);
						sortSet.add(Integer.valueOf(sdir));
					}
				}
			}
		} catch (Exception e) {
			DSLogger.error("getSortDirList error:" + e.getMessage());
			return null;
		}

		return sortSet;

	}

	public static SortedSet<Integer> getSortDirListNoDelete(String path) {
		SortedSet<Integer> sortSet = new TreeSet<Integer>();
		try {
			File file = new File(path);
			if (!file.exists()) {
				return null;
			}

			File[] files = file.listFiles();
			if (null == files || files.length == 0) {
				return null;
			}

			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					String fileName = files[i].getPath();
					int lastindex = (fileName.lastIndexOf("/") == -1 ? fileName.lastIndexOf("\\")
							: fileName.lastIndexOf("/"));
					String sdir = fileName.substring(lastindex + 1);
					sortSet.add(Integer.valueOf(sdir));
				}
			}
		} catch (Exception e) {
			DSLogger.error("getSortDirList error:" + e.getMessage());
			return null;
		}

		return sortSet;

	}

	/**
	 * 鎸夋枃浠跺悕杩涜鎺掑簭
	 *
	 * @param path
	 *            鏂囦欢璺緞
	 * @return 鎺掑簭鍚庣殑鏂囦欢
	 * @throws Exception
	 */
	public static SortedSet<String> getSortFileList(String path) {
		SortedSet<String> sortSet = new TreeSet<String>();
		try {
			File file = new File(path);
			if (!file.exists()) {
				return null;
			}

			File[] files = file.listFiles();
			if (null == files) {
				return null;
			}

			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					String fileName = files[i].getPath();
					sortSet.add(fileName);
				}
			}
		} catch (Exception e) {
			DSLogger.error("getSortFileList error:" + e.getMessage());
			return null;
		}

		return sortSet;

	}

	public static String getIpFromUrl(String url) {
		String ip = url.substring(7);
		return ip.substring(0, ip.indexOf("/"));
	}

	public static boolean listEquals(List<String> oldUploadMachineList, List<String> _uploadMachineList) {
		if (oldUploadMachineList == null || _uploadMachineList == null) {
			return false;
		}
		if (oldUploadMachineList.size() != _uploadMachineList.size()) {
			return false;
		}

		for (int i = 0; i < oldUploadMachineList.size(); i++) {
			String a = oldUploadMachineList.get(i);
			if (a == null || !a.equals(_uploadMachineList.get(i))) {
				return false;
			}

		}
		return true;
	}

	/**
	 * 鑾峰彇涓嬩竴涓枃浠剁殑璁℃暟
	 *
	 * @return incfilecount
	 */
	public synchronized static int getNextFileCount() {
		return incfilecount++;
	}

	public synchronized static int getNextLocalFileCount() {
		return localfilecount++;
	}

	/**
	 * 解压到指定目录
	 * 
	 * @param zipPath
	 * @param descDir
	 * @author isea533
	 */
	public static void unZipFiles(String zipPath, String descDir) throws IOException {
		unZipFiles(new File(zipPath), descDir);
	}

	/**
	 * 解压文件到指定目录
	 * 
	 * @param zipFile
	 * @param descDir
	 * @author isea533
	 */
	@SuppressWarnings("rawtypes")
	public static void unZipFiles(File zipFile, String descDir) throws IOException {
		File pathFile = new File(descDir);
		if (!pathFile.exists()) {
			pathFile.mkdirs();
		}
		ZipFile zip = new ZipFile(zipFile);
		for (Enumeration entries = zip.entries(); entries.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) entries.nextElement();
			String zipEntryName = entry.getName();
			InputStream in = zip.getInputStream(entry);
			String outPath = (descDir + zipEntryName).replaceAll("\\*", "/");
			;
			// 判断路径是否存在,不存在则创建文件路径
			File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));
			if (!file.exists()) {
				file.mkdirs();
			}
			// 判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压
			if (new File(outPath).isDirectory()) {
				continue;
			}
			// 输出文件路径信息
			System.out.println(outPath);

			OutputStream out = new FileOutputStream(outPath);
			byte[] buf1 = new byte[1024];
			int len;
			while ((len = in.read(buf1)) > 0) {
				out.write(buf1, 0, len);
			}
			in.close();
			out.close();
		}
	}

	/**
	 * csv文件读取<BR/>
	 * 读取绝对路径为argPath的csv文件数据，并以List返回。
	 * 
	 * @param argPath
	 *            csv文件绝对路径
	 * @return csv文件数据（List<String[]>）
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static List<String[]> readCsvFile(String argPath, String encoding) {

		List<String[]> list = new ArrayList<String[]>();
		File file = new File(argPath);

		FileInputStream input = null;
		InputStreamReader reader = null;
		BufferedReader bReader = null;
		try {
			input = new FileInputStream(file);
			if (encoding == null) {
				reader = new InputStreamReader(input);
			} else {
				reader = new InputStreamReader(input, encoding);
			}
			bReader = new BufferedReader(reader);
			String str = bReader.readLine();
			String str1 = "";
			Pattern pCells = Pattern.compile("(\"[^\"]*(\"{2})*[^\"]*\")*[^,]*,");
			while ((str = bReader.readLine()) != null) {
				if (!str.endsWith(",")) {
					str = str + ",";
				}
				Matcher mCells = pCells.matcher(str);
				List<String> listTemp = new ArrayList<String>();
				// 读取每个单元格
				while (mCells.find()) {

					str1 = mCells.group();
					str1 = str1.replaceAll("(?sm)\"?([^\"]*(\"{2})*[^\"]*)\"?.*,", "$1");
					str1 = str1.replaceAll("(?sm)(\"(\"))", "$2");
					listTemp.add(str1);

				}
				list.add((String[]) listTemp.toArray(new String[listTemp.size()]));
			}
		} catch (FileNotFoundException e) {
			DSLogger.error(",e");
		} catch (IOException e) {
			DSLogger.error(",e");
		} finally {
			if (null != bReader) {
				try {
					bReader.close();
				} catch (IOException e) {
					DSLogger.error(",e");
				}
			}
		}

		return list;
	}

	/**
	 * 将请求的数据对象写文件
	 *
	 * @param filename
	 *            文件路径
	 * @param rpcObj
	 *            数据请求包
	 * @return
	 */
	public static void writeFile(String filename, ASObject rpcObj) {
		OutputStream outs = null;
		ObjectOutputStream oos = null;
		try {
			 outs = new FileOutputStream(new File(filename));
			 oos = new ObjectOutputStream(outs);
			 oos.writeObject(rpcObj);
		} catch (FileNotFoundException ex) {
			DSLogger.error("ds::hanldeClientReq...File" + filename + " not found :" + ex.getMessage());
		} catch (IOException ex) {
			DSLogger.error("ds::hanldeClientReq...File" + filename + " IOException: " + ex.getMessage());
		}finally {
			if(null!=oos){
				try {
					oos.close();
				} catch (IOException e) {
					DSLogger.error("ds::hanldeClientReq...File" + filename + " oos close ex :" + e.getMessage());
				}
			}
			if(null!=outs){
				try {
					outs.close();
				} catch (IOException e) {
					DSLogger.error("ds::hanldeClientReq...File" + filename + " outs close ex :" + e.getMessage());
				}
			}
		}
	}

	/**
	 * 读文件中的对象
	 *
	 * @param file
	 *            文件
	 * @return 文件对象
	 * @throws Exception
	 */
	public static Object readObjectFromFile(File file) throws Exception { // 以普通流方式
		FileInputStream is = null;
		ObjectInputStream ois = null;
		try {
			is = new FileInputStream(file);
			ois = new ObjectInputStream(is);
			Object result = ois.readObject();
			return result;
		} catch (Exception e) {
			DSLogger.error("Open file error,filename:" + file.toString() + "," + e.getMessage());
		} finally {
			if (ois != null)
				ois.close();
			if (is != null)
				is.close();
		}
		return null;
	}

	/**
	 * 由数据生成csv文件
	 * 
	 * @param exportData
	 *            数据列表
	 * @param rowMapper
	 * @param outPutPath
	 * @param filename
	 * @return csv文件
	 */
	public static File writeCsvFile(List exportData, LinkedHashMap rowMapper, String outPutPath, String filename) {

		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			csvFile = new File(outPutPath + filename + ".csv");
			// csvFile.getParentFile().mkdir();
			File parent = csvFile.getParentFile();
			if (parent != null && !parent.exists()) {
				parent.mkdirs();
			}
			csvFile.createNewFile();

			// GB2312使正确读取分隔符","
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "GB2312"),
					1024);
			// 写入文件头部
			for (Iterator propertyIterator = rowMapper.entrySet().iterator(); propertyIterator.hasNext();) {
				java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
				csvFileOutputStream.write("\"" + propertyEntry.getValue().toString() + "\"");
				if (propertyIterator.hasNext()) {
					csvFileOutputStream.write(",");
				}
			}
			csvFileOutputStream.newLine();

			// 写入文件内容
			for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
				// Object row = (Object) iterator.next();
				LinkedHashMap row = (LinkedHashMap) iterator.next();
				// System.out.println(row);

				for (Iterator propertyIterator = row.entrySet().iterator(); propertyIterator.hasNext();) {
					java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator.next();
					// System.out.println( BeanUtils.getProperty(row,
					// propertyEntry.getKey().toString()));
					csvFileOutputStream.write("\"" + propertyEntry.getValue().toString() + "\"");
					if (propertyIterator.hasNext()) {
						csvFileOutputStream.write(",");
					}
				}
				if (iterator.hasNext()) {
					csvFileOutputStream.newLine();
				}
			}
			csvFileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}

	public static List getFileListByFileType(String path, String type) {
		try {
			List csvFileList = new ArrayList();
			File file = new File(path);
			if (!file.exists()) {
				return null;
			}
			File[] files = file.listFiles();
			if (null == files || files.length == 0) {
				return null;
			}
			String fileName = null;
			String extention = null;
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) // 是文件
				{
					fileName = files[i].getName();
					extention = fileName.substring(fileName.lastIndexOf(".") + 1);
					if (extention != null && extention.equals(type)) {
						csvFileList.add(files[i].getName());
					}
				}
			}
			return csvFileList;
		} catch (Exception e) {
			Logger.getRootLogger().error("getFileListByFileType exception:", e);
			return null;
		}
	}
}

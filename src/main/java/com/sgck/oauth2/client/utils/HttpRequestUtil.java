package com.sgck.oauth2.client.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

import com.sgck.oauth2.client.config.OAuthConfig;

public class HttpRequestUtil {

	/*
	 * public static JSONObject getHttpResponse(String url, Map<String, String>
	 * params, Map<String, String> header, String method) {
	 * 
	 * try {
	 * 
	 * HttpHeaders headers = new HttpHeaders(); if (null != header) { for
	 * (Map.Entry<String, String> ent2 : header.entrySet()) {
	 * headers.set(ent2.getKey(), ent2.getValue()); } }
	 * headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	 * MultiValueMap<String, String> plist = new LinkedMultiValueMap<String,
	 * String>(); if (null != params) { for (Map.Entry<String, String> ent :
	 * params.entrySet()) { plist.add(ent.getKey(), ent.getValue()); } }
	 * 
	 * ResponseEntity<String> response = getRestTemplate().exchange(getUrl(url),
	 * (method.equalsIgnoreCase("post") ? HttpMethod.POST : HttpMethod.GET), new
	 * HttpEntity<MultiValueMap<String, String>>(plist, headers), String.class);
	 * 
	 * if (null != response && response.getStatusCode() == HttpStatus.OK) {
	 * return JSONObject.fromObject(response.getBody()); }
	 * 
	 * } catch (Exception e) { } finally { }
	 * 
	 * return null; }
	 */

	/*
	 * private static String getUrl(String path) { if (path.startsWith("http:"))
	 * { return path; } if (!path.startsWith("/")) { path = "/" + path; } try {
	 * return OAuthConstant.OAUTH_HTTP +
	 * OAuthConfig.getInstance().getOauthDomain() + ":" +
	 * OAuthConfig.getInstance().getOauthSrverPort() + ((null !=
	 * OAuthConfig.getInstance().getServiceName()) ? ("/" +
	 * OAuthConfig.getInstance().getServiceName()) : "") + path; } catch
	 * (Exception e) { return null; } }
	 */

	public static String getUrlWithoutServlet(HttpServletRequest req) throws Exception {

		String url = req.getRequestURL().toString();
		String serPath = req.getServletPath();
		String urltop = url;
		if (null != serPath && !serPath.equals("") && !serPath.equals("/"))
			urltop = url.split(serPath)[0];
		if (!urltop.endsWith("/"))
			urltop += "/";

		String sname = OAuthConfig.getInstance().getSeftServiceName();
		if (null != sname) {
			if (!urltop.endsWith(sname + "/")) {
				urltop += (sname + "/");
			}
		}

		return urltop;

	}

	public static String getRootUrlWithoutServlet(HttpServletRequest req) throws Exception {

		String url = req.getRequestURL().toString();
		String serPath = req.getServletPath();
		String urltop = url;
		if (null != serPath && !serPath.equals("") && !serPath.equals("/"))
			urltop = url.split(serPath)[0];

		String contextPath = req.getContextPath();
		if (urltop.contains(contextPath)) {
			urltop = urltop.replace(contextPath, "");
		}

		if (!urltop.endsWith("/"))
			urltop += "/";

		return urltop;

	}

	/*
	 * public static RestTemplate getRestTemplate() { if (null == httpClient) {
	 * httpClient = new RestTemplate(); httpClient.setRequestFactory(new
	 * SimpleClientHttpRequestFactory() {
	 * 
	 * @Override protected void prepareConnection(HttpURLConnection connection,
	 * String httpMethod) throws IOException {
	 * super.prepareConnection(connection, httpMethod);
	 * connection.setInstanceFollowRedirects(false); } });
	 * httpClient.setErrorHandler(new ResponseErrorHandler() { public boolean
	 * hasError(ClientHttpResponse response) throws IOException { return false;
	 * }
	 * 
	 * public void handleError(ClientHttpResponse response) throws IOException {
	 * } }); }
	 * 
	 * return httpClient; }
	 */

	public static boolean isPath(String path, String tars) throws Exception {

		if (tars.indexOf("*") >= 0) {
			tars = tars.replaceAll("\\*", ".*");
			return path.matches(tars);
		} else {
			return path.equals(tars);
		}
	}

	public static String doPost(String url, String param, String clientId, String scr) throws Exception {

		URL localURL = new URL(url);

		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		httpURLConnection.setRequestProperty("accept", "*/*");
		httpURLConnection.setRequestProperty("connection", "Keep-Alive");
		if (null != clientId) {
			scr = ((null == scr) ? "" : scr);
			httpURLConnection.setRequestProperty("Authorization",
					"Basic " + new String(Base64.encodeBase64((clientId + ":" + scr).getBytes())));
		}

		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);

		PrintWriter pw = null;
		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		try {

			pw = new PrintWriter(httpURLConnection.getOutputStream());
			pw.print(param);
			pw.flush();

			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
		} finally {
			if (reader != null)
				reader.close();
			if (inputStreamReader != null)
				inputStreamReader.close();
			if (inputStream != null)
				inputStream.close();
			if (pw != null)
				pw.close();
		}

		return resultBuffer.toString();
	}

	public static String doGet(String url) throws Exception {

		URL localURL = new URL(url);

		URLConnection connection = localURL.openConnection();
		HttpURLConnection httpURLConnection = (HttpURLConnection) connection;

		httpURLConnection.setRequestProperty("Accept-Charset", "utf-8");
		httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		InputStream inputStream = null;
		InputStreamReader inputStreamReader = null;
		BufferedReader reader = null;
		StringBuffer resultBuffer = new StringBuffer();
		String tempLine = null;

		try {

			httpURLConnection.connect();

			if (httpURLConnection.getResponseCode() >= 300)
				throw new Exception(
						"HTTP Request is not success, Response code is " + httpURLConnection.getResponseCode());

			inputStream = httpURLConnection.getInputStream();
			inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			reader = new BufferedReader(inputStreamReader);

			while ((tempLine = reader.readLine()) != null) {
				resultBuffer.append(tempLine);
			}
		} finally {
			if (reader != null)
				reader.close();
			if (inputStreamReader != null)
				inputStreamReader.close();
			if (inputStream != null)
				inputStream.close();
		}

		return resultBuffer.toString();
	}

}

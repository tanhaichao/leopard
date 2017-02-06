package io.leopard.httpnb;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map.Entry;

public class HttpUpload {
	private static final String BR = "\r\n";

	// POST http://olla.laopao.com/share/upload/image.do HTTP/1.1
	// Host: olla.laopao.com
	// Connection: keep-alive
	// Content-Length: 13481
	// Cache-Control: max-age=0
	// Accept:
	// text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8
	// Origin: null
	// User-Agent: Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36
	// (KHTML, like Gecko) Chrome/35.0.1916.153 Safari/537.36
	// Content-Type: multipart/form-data;
	// boundary=----WebKitFormBoundaryEvaq8bJriOMUn4F6
	// Accept-Encoding: gzip,deflate,sdch
	// Accept-Language: zh-CN,zh;q=0.8,en;q=0.6
	//
	// ------WebKitFormBoundaryEvaq8bJriOMUn4F6
	// Content-Disposition: form-data; name="file"; filename="test-3-1.jpg"
	// Content-Type: image/jpeg
	//
	//
	//
	//
	// ------WebKitFormBoundaryEvaq8bJriOMUn4F6
	// Content-Disposition: form-data; name="file"; filename="test-3-2.jpg"
	// Content-Type: image/jpeg
	//
	// ------WebKitFormBoundaryEvaq8bJriOMUn4F6--

	// public static void upload2(HttpURLConnection conn, Entry<String, String> fileEntry, List<Param> paramList) throws IOException {
	// List<Entry<String, String>> fileList = new ArrayList<Entry<String, String>>();
	// fileList.add(fileEntry);
	//
	// Param[] params = new Param[paramList.size()];
	// paramList.toArray(params);
	// upload(conn, fileList, params);
	// }

	// public static void upload(HttpURLConnection conn, List<Entry<String, String>> fileList, Param... params) throws IOException {
	// upload(conn, fileList, params);
	// }

	public static void upload(HttpURLConnection conn, List<Entry<String, String>> fileList, List<Param> paramList) throws IOException {
		long time = System.currentTimeMillis();
		String boundary = "Droid4jFormBoundary" + time;
		boundary = "WebKitFormBoundary7Ihe6KrrkF27C7DX";

		// conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

		OutputStream output = conn.getOutputStream();

		DataOutputStream ds = new DataOutputStream(output);
		ds.writeBytes(BR);

		for (Param param : paramList) {
			if (param.getValue() != null) {
				uploadParam(ds, boundary, param);
			}
		}

		for (Entry<String, String> file : fileList) {
			uploadFile(ds, boundary, file);
		}

		ds.writeBytes(BR + "--" + boundary + "--" + BR);

		ds.flush();
		ds.close();
	}

	protected static void uploadParam(DataOutputStream ds, String boundary, Param param) throws IOException {
		String name = param.getName();
		String value = param.getValue().toString();

		StringBuilder sb = new StringBuilder();

		// System.err.println("value:" + value);
		/* 设置DataOutputStream */
		sb.append("--" + boundary + BR);
		sb.append("Content-Disposition: form-data; name=\"" + name + "\"" + BR + BR);
		sb.append(value + BR);

		// ds.writeBytes(sb.toString());

		byte[] data = sb.toString().getBytes();
		ds.write(data, 0, data.length);
	}

	protected static void uploadFile(DataOutputStream ds, String boundary, Entry<String, String> file) throws IOException {
		String name = file.getKey();
		String path = file.getValue();

		{
			StringBuilder sb = new StringBuilder();
			sb.append("--" + boundary + BR);
			sb.append("Content-Disposition: form-data;" + "name=\"" + name + "\";filename=\"" + path + "\"" + BR);
			sb.append("Content-Type: application/octet-stream" + BR + BR);
			// sb.append("Content-Type:application/octet-stream\r\n\r\n");
			// ds.writeBytes(sb.toString());
			byte[] data = sb.toString().getBytes();
			ds.write(data, 0, data.length);
		}

		FileInputStream fStream = new FileInputStream(path);
		int bufferSize = 1024;
		byte[] buffer = new byte[bufferSize];
		int length = -1;
		while ((length = fStream.read(buffer)) != -1) {
			ds.write(buffer, 0, length);
		}
		ds.writeBytes(BR);
		fStream.close();
	}
}

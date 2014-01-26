package com.helloweenvsfei.gzip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class GZipOutputStream extends ServletOutputStream {

	private HttpServletResponse response;

	// JDK 附帶的壓縮資料的類
	private GZIPOutputStream gzipOutputStream;

	// 將壓縮後的資料存放到 ByteArrayOutputStream 對像中
	private ByteArrayOutputStream byteArrayOutputStream;

	public GZipOutputStream(HttpServletResponse response) throws IOException {
		this.response = response;
		byteArrayOutputStream = new ByteArrayOutputStream();
		gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
	}

	public void write(int b) throws IOException {
		gzipOutputStream.write(b);
	}

	public void close() throws IOException {

		// 壓縮完畢 一定要呼叫該方法
		gzipOutputStream.finish();

		// 將壓縮後的資料輸出到客戶端
		byte[] content = byteArrayOutputStream.toByteArray();

		// 設定壓縮方式為 GZIP, 客戶端瀏覽器會自動將資料解壓
		response.addHeader("Content-Encoding", "gzip");
		response.addHeader("Content-Length", Integer.toString(content.length));

		// 輸出
		ServletOutputStream out = response.getOutputStream();
		out.write(content);
		out.close();
	}

	public void flush() throws IOException {
		gzipOutputStream.flush();
	}

	public void write(byte[] b, int off, int len) throws IOException {
		gzipOutputStream.write(b, off, len);
	}

	public void write(byte[] b) throws IOException {
		gzipOutputStream.write(b);
	}
}

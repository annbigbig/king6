package com.helloweenvsfei.gzip;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class GZipResponseWrapper extends HttpServletResponseWrapper {

	// 預設的 response
	private HttpServletResponse response;

	// 自定義的 outputStream, 執行close()的時候對資料壓縮，並輸出
	private GZipOutputStream gzipOutputStream;

	// 自定義 printWriter，將內容輸出到 GZipOutputStream 中
	private PrintWriter writer;

	public GZipResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
		this.response = response;
	}

	public ServletOutputStream getOutputStream() throws IOException {
		if (gzipOutputStream == null)
			gzipOutputStream = new GZipOutputStream(response);
		return gzipOutputStream;
	}

	public PrintWriter getWriter() throws IOException {
		if (writer == null)
			writer = new PrintWriter(new OutputStreamWriter(
					new GZipOutputStream(response), "UTF-8"));
		return writer;
	}

	// 壓縮後資料長度會發生變化 因此將該方法內容置空
	public void setContentLength(int contentLength) {
	}

	public void flushBuffer() throws IOException {
		gzipOutputStream.flush();
	}

	public void finishResponse() throws IOException {
		if (gzipOutputStream != null)
			gzipOutputStream.close();
		if (writer != null)
			writer.close();
	}
}

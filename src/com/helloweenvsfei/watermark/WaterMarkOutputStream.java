package com.helloweenvsfei.watermark;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

public class WaterMarkOutputStream extends ServletOutputStream {

	// 緩衝圖片資料
	private ByteArrayOutputStream byteArrayOutputStream;

	public WaterMarkOutputStream() throws IOException {
		byteArrayOutputStream = new ByteArrayOutputStream();
	}

	public void write(int b) throws IOException {
		byteArrayOutputStream.write(b);
	}

	public void close() throws IOException {
		byteArrayOutputStream.close();
	}

	public void flush() throws IOException {
		byteArrayOutputStream.flush();
	}

	public void write(byte[] b, int off, int len) throws IOException {
		byteArrayOutputStream.write(b, off, len);
	}

	public void write(byte[] b) throws IOException {
		byteArrayOutputStream.write(b);
	}

	public ByteArrayOutputStream getByteArrayOutputStream() {
		return byteArrayOutputStream;
	}

}

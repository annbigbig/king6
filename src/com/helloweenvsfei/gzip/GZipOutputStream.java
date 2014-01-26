package com.helloweenvsfei.gzip;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class GZipOutputStream extends ServletOutputStream {

	private HttpServletResponse response;

	// JDK ���a�����Y��ƪ���
	private GZIPOutputStream gzipOutputStream;

	// �N���Y�᪺��Ʀs��� ByteArrayOutputStream �ﹳ��
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

		// ���Y���� �@�w�n�I�s�Ӥ�k
		gzipOutputStream.finish();

		// �N���Y�᪺��ƿ�X��Ȥ��
		byte[] content = byteArrayOutputStream.toByteArray();

		// �]�w���Y�覡�� GZIP, �Ȥ���s�����|�۰ʱN��Ƹ���
		response.addHeader("Content-Encoding", "gzip");
		response.addHeader("Content-Length", Integer.toString(content.length));

		// ��X
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

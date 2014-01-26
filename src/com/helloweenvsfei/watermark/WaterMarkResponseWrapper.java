package com.helloweenvsfei.watermark;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.helloweenvsfei.util.ImageUtil;

public class WaterMarkResponseWrapper extends HttpServletResponseWrapper {

	// ���L�Ϥ���m
	private String waterMarkFile;

	// ��response
	private HttpServletResponse response;

	// �۩w�qservletOutputStream�A�Ω�w�ļv�����
	private WaterMarkOutputStream waterMarkOutputStream;

	public WaterMarkResponseWrapper(HttpServletResponse response,
			String waterMarkFile) throws IOException {
		super(response);
		this.response = response;
		this.waterMarkFile = waterMarkFile;
		this.waterMarkOutputStream = new WaterMarkOutputStream();
	}

	// �л\getOutputStream()�A�Ǧ^�۩w�q��waterMarkOutputStream
	public ServletOutputStream getOutputStream() throws IOException {
		return waterMarkOutputStream;
	}

	public void flushBuffer() throws IOException {
		waterMarkOutputStream.flush();
	}

	// �N�v����ƥ����L�A�ÿ�X��Ȥ���s����
	public void finishResponse() throws IOException {

		// ��Ϥ����
		byte[] imageData = waterMarkOutputStream.getByteArrayOutputStream()
				.toByteArray();

		// �����L�᪺�Ϥ����
		byte[] image = ImageUtil.waterMark(imageData, waterMarkFile);

		// �N�v����X���s����
		response.setContentLength(image.length);
		response.getOutputStream().write(image);

		waterMarkOutputStream.close();
	}
}

package com.helloweenvsfei.watermark;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.helloweenvsfei.util.ImageUtil;

public class WaterMarkResponseWrapper extends HttpServletResponseWrapper {

	// 水印圖片位置
	private String waterMarkFile;

	// 原response
	private HttpServletResponse response;

	// 自定義servletOutputStream，用於緩衝影像資料
	private WaterMarkOutputStream waterMarkOutputStream;

	public WaterMarkResponseWrapper(HttpServletResponse response,
			String waterMarkFile) throws IOException {
		super(response);
		this.response = response;
		this.waterMarkFile = waterMarkFile;
		this.waterMarkOutputStream = new WaterMarkOutputStream();
	}

	// 覆蓋getOutputStream()，傳回自定義的waterMarkOutputStream
	public ServletOutputStream getOutputStream() throws IOException {
		return waterMarkOutputStream;
	}

	public void flushBuffer() throws IOException {
		waterMarkOutputStream.flush();
	}

	// 將影像資料打水印，並輸出到客戶端瀏覽器
	public void finishResponse() throws IOException {

		// 原圖片資料
		byte[] imageData = waterMarkOutputStream.getByteArrayOutputStream()
				.toByteArray();

		// 打水印後的圖片資料
		byte[] image = ImageUtil.waterMark(imageData, waterMarkFile);

		// 將影像輸出到瀏覽器
		response.setContentLength(image.length);
		response.getOutputStream().write(image);

		waterMarkOutputStream.close();
	}
}

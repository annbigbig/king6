package com.helloweenvsfei.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class ImageUtil {

	/**
	 * 
	 * @param imageData
	 *            JPG 影像檔案
	 * @param waterMarkFile
	 *            水印圖片
	 * @return 加水印後的影像資料
	 * @throws IOException
	 */
	public static byte[] waterMark(byte[] imageData, String waterMarkFile)
			throws IOException {

		// 水印圖片的右邊距 下邊距
		int paddingRight = 10;
		int paddingBottom = 10;

		// 原始影像
		Image image = new ImageIcon(imageData).getImage();
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);

		// 水印圖片
		Image waterMark = ImageIO.read(new File(waterMarkFile));
		int waterMarkWidth = waterMark.getWidth(null);
		int waterMarkHeight = waterMark.getHeight(null);

		// 如果圖片尺寸過小，則不打水印，直接傳回
		if (imageWidth < waterMarkWidth + 2 * paddingRight
				|| imageHeight < waterMarkHeight + 2 * paddingBottom) {
			return imageData;
		}
		BufferedImage bufferedImage = new BufferedImage(imageWidth,
				imageHeight, BufferedImage.TYPE_INT_RGB);

		Graphics g = bufferedImage.createGraphics();

		// 繪製原始影像
		g.drawImage(image, 0, 0, imageWidth, imageHeight, null);
		// 繪製水印圖片
		g.drawImage(waterMark, imageWidth - waterMarkWidth - paddingRight,
				imageHeight - waterMarkHeight - paddingBottom, waterMarkWidth,
				waterMarkHeight, null);
		g.dispose();

		// 轉成JPEG格式
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(bufferedImage);
		byte[] data = out.toByteArray();
		out.close();
		return data;
	}
}

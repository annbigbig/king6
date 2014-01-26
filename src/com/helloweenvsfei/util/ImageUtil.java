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
	 *            JPG �v���ɮ�
	 * @param waterMarkFile
	 *            ���L�Ϥ�
	 * @return �[���L�᪺�v�����
	 * @throws IOException
	 */
	public static byte[] waterMark(byte[] imageData, String waterMarkFile)
			throws IOException {

		// ���L�Ϥ����k��Z �U��Z
		int paddingRight = 10;
		int paddingBottom = 10;

		// ��l�v��
		Image image = new ImageIcon(imageData).getImage();
		int imageWidth = image.getWidth(null);
		int imageHeight = image.getHeight(null);

		// ���L�Ϥ�
		Image waterMark = ImageIO.read(new File(waterMarkFile));
		int waterMarkWidth = waterMark.getWidth(null);
		int waterMarkHeight = waterMark.getHeight(null);

		// �p�G�Ϥ��ؤo�L�p�A�h�������L�A�����Ǧ^
		if (imageWidth < waterMarkWidth + 2 * paddingRight
				|| imageHeight < waterMarkHeight + 2 * paddingBottom) {
			return imageData;
		}
		BufferedImage bufferedImage = new BufferedImage(imageWidth,
				imageHeight, BufferedImage.TYPE_INT_RGB);

		Graphics g = bufferedImage.createGraphics();

		// ø�s��l�v��
		g.drawImage(image, 0, 0, imageWidth, imageHeight, null);
		// ø�s���L�Ϥ�
		g.drawImage(waterMark, imageWidth - waterMarkWidth - paddingRight,
				imageHeight - waterMarkHeight - paddingBottom, waterMarkWidth,
				waterMarkHeight, null);
		g.dispose();

		// �নJPEG�榡
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(bufferedImage);
		byte[] data = out.toByteArray();
		out.close();
		return data;
	}
}

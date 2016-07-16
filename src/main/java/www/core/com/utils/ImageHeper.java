package www.core.com.utils;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImageHeper {
	/*
	 * ���ݳߴ�ͼƬ���вü�
	 */
	public static void cutCenterImage(String src, String dest, int w, int h) throws IOException {
		Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = (ImageReader) iterator.next();
		InputStream in = new FileInputStream(src);
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		int imageIndex = 0;
		Rectangle rect = new Rectangle((reader.getWidth(imageIndex) - w) / 2, (reader.getHeight(imageIndex) - h) / 2, w,
				h);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, "jpg", new File(dest));

	}

	/*
	 * ͼƬ�ü�����֮һ
	 */
	public static void cutHalfImage(String src, String dest) throws IOException {
		Iterator iterator = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = (ImageReader) iterator.next();
		InputStream in = new FileInputStream(src);
		ImageInputStream iis = ImageIO.createImageInputStream(in);
		reader.setInput(iis, true);
		ImageReadParam param = reader.getDefaultReadParam();
		int imageIndex = 0;
		int width = reader.getWidth(imageIndex) / 2;
		int height = reader.getHeight(imageIndex) / 2;
		Rectangle rect = new Rectangle(width / 2, height / 2, width, height);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, "jpg", new File(dest));
	}
	/*
	 * ͼƬ�ü�ͨ�ýӿ�
	 */

	public static void cutImage(String src, String dest, int x, int y, int w, int h) throws IOException {

		Image img;
		ImageFilter cropFilter;
		// ��ȡԴͼ��
		BufferedImage bi = ImageIO.read(new File(src));
		int srcWidth = bi.getWidth(); // Դͼ���
		int srcHeight = bi.getHeight(); // Դͼ�߶�
		Image image = bi.getScaledInstance(srcWidth, srcHeight,Image.SCALE_DEFAULT);
		cropFilter = new CropImageFilter(x, y, w, h);
		img = Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(image.getSource(), cropFilter));
		BufferedImage tag = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(img, 0, 0, null); // ������С���ͼ
		g.dispose();
		// ���Ϊ�ļ�
		ImageIO.write(tag, "JPEG", new File(dest));
	}

	/*
	 * ͼƬ����
	 */
	public static void zoomImage(String src, String dest, int w, int h) throws Exception {
		double wr = 0, hr = 0;
		File srcFile = new File(src);
		File destFile = new File(dest);
		Image img;
		ImageFilter cropFilter;
		// ��ȡԴͼ��
		BufferedImage bi = ImageIO.read(srcFile);
		int srcWidth = bi.getWidth(); // Դͼ���
		int srcHeight = bi.getHeight(); // Դͼ�߶�

	}

	public static void main(String[] args) throws IOException {
		ImageHeper.cutImage("/Users/1/fastcore/static/Jcrop-v0.9.12/demos/demo_files/sago.jpg",
				"/Users/kaja.yin/Desktop/sago.jpg", 206, 104, 324, 243);
	}
}

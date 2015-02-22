package org.landcycle.media;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class BufferedImageUtil {
	public static BufferedImage loadImage(final byte[] imageInByte) throws IOException
		{
		InputStream in = new ByteArrayInputStream(imageInByte);
		BufferedImage image = ImageIO.read(in);
		return image;
		}
}

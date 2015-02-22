package org.landcycle.media;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface IResize {
	public BufferedImage resizePerc(byte[] stream, int percTargetDim) throws IOException;
	public BufferedImage resize (byte[] stream, int targetDim, ResizeDimensionEnum resizeDimension) throws IOException;
	public BufferedImage shrinkToSize (byte[] stream, int maxWidth, int maxHeight) throws IOException;
	public Byte[] convertImage (byte[] stream, ImageOutputFormatEnum imageOutputFormatEnum) throws IOException;
	public Byte[] convertImage (BufferedImage bufferedImage, ImageOutputFormatEnum imageOutputFormatEnum) throws IOException;
}

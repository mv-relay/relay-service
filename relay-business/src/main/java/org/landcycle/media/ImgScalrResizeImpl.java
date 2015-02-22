package org.landcycle.media;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
public class ImgScalrResizeImpl implements IResize 
	{
	protected Scalr.Method defaultScalrMethod;
	public ImgScalrResizeImpl()
		{
		defaultScalrMethod =Scalr.Method.AUTOMATIC;
		}
	public ImgScalrResizeImpl(ScaleMethodEnum scaleMethodEnum) throws IOException
		{
		if(scaleMethodEnum.equals(ScaleMethodEnum.BALANCED))		defaultScalrMethod =Scalr.Method.BALANCED;
		if(scaleMethodEnum.equals(ScaleMethodEnum.ULTRA_QUALITY))	defaultScalrMethod =Scalr.Method.ULTRA_QUALITY;
		if(scaleMethodEnum.equals(ScaleMethodEnum.QUALITY))			defaultScalrMethod =Scalr.Method.QUALITY;
		if(scaleMethodEnum.equals(ScaleMethodEnum.SPEED))			defaultScalrMethod =Scalr.Method.SPEED;
		}
	@Override
	public Byte[] convertImage(BufferedImage bufferedImage,ImageOutputFormatEnum imageOutputFormatEnum)throws IOException  {
		
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage,imageOutputFormatEnum.getFormat(), os); 
		InputStream fis = new ByteArrayInputStream(os.toByteArray());
		return toObjects(os.toByteArray());
	}
	@Override
	public Byte[] convertImage(byte[] stream,ImageOutputFormatEnum imageOutputFormatEnum) throws IOException 
	{
		BufferedImage bufferedImage = BufferedImageUtil.loadImage(stream);
		
		return convertImage(bufferedImage, imageOutputFormatEnum);
	}
	@Override
	public BufferedImage resize(byte[] stream, int targetDim,
			ResizeDimensionEnum resizeDimension)  throws IOException
		{
		Scalr.Mode scalar = null;
		if(resizeDimension.equals(ResizeDimensionEnum.FIT_EXACT)) scalar = Scalr.Mode.FIT_EXACT;
		if(resizeDimension.equals(ResizeDimensionEnum.FIT_TO_HEIGHT)) scalar = Scalr.Mode.FIT_TO_HEIGHT;
		if(resizeDimension.equals(ResizeDimensionEnum.FIT_TO_WIDTH)) scalar = Scalr.Mode.FIT_TO_WIDTH;
		
		BufferedImage bufferedImage = BufferedImageUtil.loadImage(stream);		
		BufferedImage thumb = Scalr.resize(bufferedImage,defaultScalrMethod,scalar,targetDim,Scalr.OP_ANTIALIAS);
		return thumb;
	}
	@Override
	public BufferedImage resizePerc(byte[] stream, int percTargetDim) throws IOException {
		BufferedImage bufferedImage = BufferedImageUtil.loadImage(stream);
		int w = bufferedImage.getWidth();
		int targetSize = (int)Math.floor(w*(new Float(percTargetDim)/100));
		
		BufferedImage thumb = Scalr.resize(bufferedImage,defaultScalrMethod,Scalr.Mode.FIT_TO_WIDTH,targetSize,Scalr.OP_ANTIALIAS);
		return thumb;
	}
	@Override
	public BufferedImage shrinkToSize(byte[] stream, int maxWidth, int maxHeight) throws IOException {
		BufferedImage bufferedImage = BufferedImageUtil.loadImage(stream);
		int w = bufferedImage.getWidth();
		int h = bufferedImage.getHeight();
		int targetWidth = w;
		int targetHeight = h;
		if(w>maxWidth)  targetWidth=maxWidth;
		if(h>maxHeight) targetHeight=maxHeight;
		
		BufferedImage thumb = Scalr.resize(bufferedImage,defaultScalrMethod,Scalr.Mode.FIT_EXACT,targetWidth,targetHeight,Scalr.OP_ANTIALIAS);
		return thumb;
	}
	
	private Byte[] toObjects(byte[] bytesPrim) {

	    Byte[] bytes = new Byte[bytesPrim.length];
	    int i = 0;
	    for (byte b : bytesPrim) bytes[i++] = b; //Autoboxing
	    return bytes;

	}

}

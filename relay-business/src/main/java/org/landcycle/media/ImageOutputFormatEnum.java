package org.landcycle.media;

public enum ImageOutputFormatEnum 
	{
	PNG("png"),JPG("jpg");
	
	private String format;
	ImageOutputFormatEnum(String format)
		{this.format=format;}
	
	public String getFormat() {
		return format;
	}
	}

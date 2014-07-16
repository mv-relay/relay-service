package org.landcycle.util;



public class ConfigurationLoaderImpl implements ConfigurationLoader {
	private String baseImg = null;
	private String baseImgUrl = null;

	/* (non-Javadoc)
	 * @see org.landcycle.util.ConfigurationLoader#getBaseImg()
	 */
	public ConfigurationLoaderImpl(String baseImg){
		this.baseImg = baseImg;
	}
	public ConfigurationLoaderImpl(String baseImg,String baseImgUrl){
		this.baseImg = baseImg;
		this.baseImgUrl = baseImgUrl;
	}
	@Override
	public String getBaseImg() {
		return baseImg;
	}

	public void setBaseImg(String baseImg) {
		this.baseImg = baseImg;
	}
	public String getBaseImgUrl() {
		return baseImgUrl;
	}
	public void setBaseImgUrl(String baseImgUrl) {
		this.baseImgUrl = baseImgUrl;
	}
	@Override
	public String getUrlImg() {
		// TODO Auto-generated method stub
		return this.baseImgUrl;
	}
	
}

package org.landcycle.controllers;

import org.springframework.web.multipart.MultipartFile;

public class FileUpload {
	MultipartFile file;
//	@NotNull
//	private String fileName;
	private String userId = "";
	private String type = "";
	private String channel = "";

	/** The path. */
	private String path = "";

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

//	public String getFileName() {
//		return fileName;
//	}
//
//	public void setFileName(String fileName) {
//		this.fileName = fileName;
//	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

}

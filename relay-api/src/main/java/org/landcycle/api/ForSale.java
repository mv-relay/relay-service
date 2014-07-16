package org.landcycle.api;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class ForSale implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String stream;
	private byte[] streams;
	private String id;
	private String name;
	private String mailAcq;
	private String imageType;
	private String img;
	private String description;
	private String[] tags;
	private Position position;

	public byte[] getStreams() {
		return streams;
	}

	public void setStreams(byte[] streams) {
		this.streams = streams;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMailAcq() {
		return mailAcq;
	}

	public void setMailAcq(String mailAcq) {
		this.mailAcq = mailAcq;
	}

	public String getImageType() {
		return imageType;
	}

	public void setImageType(String imageType) {
		this.imageType = imageType;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}
}

package org.landcycle.api;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

public class TaggableItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String stream = null;
	// @JsonIgnore
	// private byte[] streams = null;
	private String id;
	private String idBeacon;
	private String name;
	private String mailAcq;
	private String imageType;
	private String img;
	private String description;
	private String[] tags;
	private Position position;
	private LikeItem like;
	private MediaItem[] medias;

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

	public String getIdBeacon() {
		return idBeacon;
	}

	public void setIdBeacon(String idBeacon) {
		this.idBeacon = idBeacon;
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

	public LikeItem getLike() {
		return like;
	}

	public void setLike(LikeItem like) {
		this.like = like;
	}

	public MediaItem[] getMedias() {
		return medias;
	}

	public void setMedias(MediaItem[] medias) {
		this.medias = medias;
	}

	// public byte[] getStreams() {
	// return streams;
	// }
	//
	// public void setStreams(byte[] streams) {
	// this.streams = streams;
	// }

}

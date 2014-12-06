package org.landcycle.api;

import org.codehaus.jackson.annotate.JsonIgnore;

public class MediaItem {
	private String id;
	private String user;
	private String stream;
	@JsonIgnore
	private String type;
	@JsonIgnore
	private byte[] streams;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getStream() {
		return stream;
	}

	public void setStream(String stream) {
		this.stream = stream;
	}

	public byte[] getStreams() {
		return streams;
	}

	public void setStreams(byte[] streams) {
		this.streams = streams;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

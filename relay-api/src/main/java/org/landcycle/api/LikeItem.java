package org.landcycle.api;

public class LikeItem {
	private String id;
	private String resources;
	private String type;
	private String user;

	private int count;
	private boolean myLike = false;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResources() {
		return resources;
	}

	public void setResources(String resources) {
		this.resources = resources;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public boolean getMyLike() {
		return myLike;
	}

	public void setMyLike(boolean myLike) {
		this.myLike = myLike;
	}

}

package org.landcycle.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User user;
	private List<Taggable> taggables;
	private Taggable taggable;
	
	public List<Taggable> getForSales() {
		return taggables;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setTaggables(List<Taggable> taggables) {
		this.taggables = taggables;
	}

	public Taggable getTaggable() {
		return taggable;
	}

	public void setTaggable(Taggable taggable) {
		this.taggable = taggable;
	}

	public void addTag(Taggable tag) {
		if (this.taggables == null)
			this.taggables = new ArrayList<Taggable>();
		this.taggables.add(tag);
	}
}

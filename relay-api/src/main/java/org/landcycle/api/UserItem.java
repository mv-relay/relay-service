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
	private List<TaggableItem> taggables;

	private TaggableItem taggable;

	public List<TaggableItem> getForSales() {
		return taggables;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setTaggables(List<TaggableItem> taggables) {
		this.taggables = taggables;
	}

	public TaggableItem getTaggable() {
		return taggable;
	}

	public void setTaggable(TaggableItem taggable) {
		this.taggable = taggable;
	}

	public void addTag(TaggableItem tag) {
		if (this.taggables == null)
			this.taggables = new ArrayList<TaggableItem>();
		this.taggables.add(tag);
	}
}

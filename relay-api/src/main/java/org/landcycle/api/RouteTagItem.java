package org.landcycle.api;

import java.io.Serializable;

public class RouteTagItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idTaggableExt;
	private TaggableBaseItem taggable;

	public String getIdTaggableExt() {
		return idTaggableExt;
	}

	public void setIdTaggableExt(String idTaggableExt) {
		this.idTaggableExt = idTaggableExt;
	}

	public TaggableBaseItem getTaggable() {
		return taggable;
	}

	public void setTaggable(TaggableBaseItem taggable) {
		this.taggable = taggable;
	}

}

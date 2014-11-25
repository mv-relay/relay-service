package org.landcycle.service;

import java.util.List;

import org.landcycle.api.CommentItem;
import org.landcycle.api.LikeItem;
import org.landcycle.api.TaggableItem;
import org.landcycle.api.UserItem;

public interface LandCycleBusiness {
	UserItem saveOrUpdateTaggable(UserItem upload) throws Exception;

	LikeItem saveLike(LikeItem like) throws Exception;

	UserItem upload(UserItem upload) throws Exception;

	public List<TaggableItem> find(TaggableItem taggable) throws Exception;

	TaggableItem findOne(UserItem user) throws Exception;

	CommentItem saveComment(CommentItem comment) throws Exception;

}

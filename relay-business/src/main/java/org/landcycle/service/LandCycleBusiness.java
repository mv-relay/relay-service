package org.landcycle.service;

import java.util.List;

import org.landcycle.api.CommentItem;
import org.landcycle.api.LikeItem;
import org.landcycle.api.MediaItem;
import org.landcycle.api.RouteItem;
import org.landcycle.api.TaggableItem;
import org.landcycle.api.UserItem;

public interface LandCycleBusiness {
	UserItem saveOrUpdateTaggable(UserItem upload) throws Exception;

	MediaItem saveOrUpdateMedia(MediaItem upload) throws Exception;

	LikeItem saveLike(LikeItem like) throws Exception;

	MediaItem uploadMedia(MediaItem upload) throws Exception;

	public List<TaggableItem> find(TaggableItem taggable) throws Exception;

	TaggableItem findOne(UserItem user) throws Exception;

	CommentItem saveComment(CommentItem comment) throws Exception;

	List<TaggableItem> findByUser(String user) throws Exception;

	RouteItem saveRoute(RouteItem comment) throws Exception;

	void deleteLike(LikeItem like) throws Exception;

	List<LikeItem> getLikes(LikeItem like) throws Exception;

	List<TaggableItem> getTaggableLikes(LikeItem like) throws Exception;

}

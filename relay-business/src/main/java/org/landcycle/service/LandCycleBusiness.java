package org.landcycle.service;

import java.util.List;

import org.landcycle.api.LikeItem;
import org.landcycle.api.UserItem;

public interface LandCycleBusiness {
	UserItem saveOrUpdateTaggable(UserItem upload) throws Exception;
	LikeItem saveLike(LikeItem like) throws Exception;
	UserItem upload(UserItem upload) throws Exception;
	
	public List<UserItem> find(UserItem user) throws Exception;

}

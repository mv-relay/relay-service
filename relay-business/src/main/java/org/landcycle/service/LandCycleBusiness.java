package org.landcycle.service;

import java.util.List;

import org.landcycle.api.UserItem;

public interface LandCycleBusiness {
	UserItem saveOrUpdateSale(UserItem upload) throws Exception;
	UserItem upload(UserItem upload) throws Exception;
	
	public List<UserItem> find(UserItem user) throws Exception;

}

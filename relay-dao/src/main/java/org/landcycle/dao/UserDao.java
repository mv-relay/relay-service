package org.landcycle.dao;

import java.util.List;

public interface UserDao {

	public abstract List<UserEntity> findAll();

	UserEntity findOne(String email);

	public abstract UserEntity save(UserEntity user);

	public abstract void delete(String entity);

}
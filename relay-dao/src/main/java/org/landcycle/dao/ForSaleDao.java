package org.landcycle.dao;

import java.util.List;

public interface ForSaleDao {

	public abstract List<ForSaleEntity> findAll();

	public abstract List<ForSaleEntity> findByQuery(ForSaleEntity user);

	public abstract ForSaleEntity save(ForSaleEntity user);

	public abstract void delete(String entity);

}
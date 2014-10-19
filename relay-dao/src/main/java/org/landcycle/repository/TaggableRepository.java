package org.landcycle.repository;

import org.springframework.data.repository.Repository;

public interface TaggableRepository extends Repository<TaggableEntity, String> {
	
	public TaggableEntity save(TaggableEntity entity);
	public TaggableEntity findOne(String id);

}

package org.landcycle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface TaggableRepository extends Repository<TaggableEntity, String> {
	
	public TaggableEntity save(TaggableEntity entity);
	public TaggableEntity findOne(String id);
	public List<TaggableEntity> findByUser(String user);
	@Query(nativeQuery = true, value = "select f.*, ( 6371 * acos( cos( radians(:latitudine) ) * cos( radians( lat ) ) * cos( radians( lng ) - radians(:longitudine) ) + sin( radians(:latitudine) ) * sin( radians( lat ) ) ) ) AS distance from Taggable AS f HAVING distance < 50 ORDER BY distance LIMIT 0 , 50")
	public List<TaggableEntity> findByQuery(@Param("longitudine") Double longitudine,
			@Param("latitudine") Double latitudine);

}

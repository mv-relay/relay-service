package org.landcycle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends Repository<LikeEntity, String> {
	public LikeEntity save(LikeEntity likes);
	@Query(nativeQuery = true, value = "select f.* from Likes AS f where user = :user")
	public List<LikeEntity> findByUser(@Param("user") String user);
	public void delete(LikeEntity likes);
}

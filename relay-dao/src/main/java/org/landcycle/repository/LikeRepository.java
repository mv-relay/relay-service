package org.landcycle.repository;

import org.springframework.data.repository.Repository;

public interface LikeRepository extends Repository<LikeEntity, String> {
	public LikeEntity save(LikeEntity likes);
}

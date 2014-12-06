package org.landcycle.repository;

import org.springframework.data.repository.Repository;

public interface MediaRepository extends Repository<MediaEntity, String> {
	public MediaEntity save(MediaEntity comment);
}

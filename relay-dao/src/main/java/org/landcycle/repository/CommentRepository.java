package org.landcycle.repository;

import org.springframework.data.repository.Repository;

public interface CommentRepository extends Repository<CommentEntity, String> {
	public CommentEntity save(CommentEntity comment);
}

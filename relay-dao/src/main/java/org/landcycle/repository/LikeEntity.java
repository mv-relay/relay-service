package org.landcycle.repository;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "Likes")
public class LikeEntity {
	@EmbeddedId
	private LikesKey likesKey;
	@Column(name = "createdat", updatable = false)
	private Date createdAt;

	public LikesKey getLikesKey() {
		return likesKey;
	}

	public void setLikesKey(LikesKey likesKey) {
		this.likesKey = likesKey;
	}

	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}

	public Date getCreatedAt() {
		return createdAt;
	}
}

package org.landcycle.repository;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "Likes")
public class LikeEntity {
	public LikeEntity() {
	}

	public LikeEntity(String id, String user) {
		super();
		this.id = id;
		this.user = user;
	}

	@Id
	@Column(name = "idTaggable", length = 100)
	private String id;
	@Column(name = "user", length = 100)
	private String user;
	@Column(name = "createdat",updatable = false)
	private Date createdAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
	@PrePersist
	void createdAt() {
		this.createdAt  = new Date();
	}


	public Date getCreatedAt() {
		return createdAt;
	}
}

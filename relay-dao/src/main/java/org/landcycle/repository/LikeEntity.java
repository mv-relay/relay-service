package org.landcycle.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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
	@Column(name = "id", length = 100)
	private String id;
	@Column(name = "user", length = 100)
	private String user;

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
}

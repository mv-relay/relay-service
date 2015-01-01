package org.landcycle.repository;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "Comments")
public class CommentEntity {
	public CommentEntity() {
	}

	public CommentEntity(String id, String user, String comment) {
		super();
		this.id = id;
		this.user = user;
		this.comment = comment;
	}

	@Id
	@Column(name = "idTaggable", length = 100)
	private String id;
	@Column(name = "user", length = 100)
	private String user;
	@Column(name = "comment", length = 100)
	private String comment;
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

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@PrePersist
	void createdAt() {
		this.createdAt  = new Date();
	}


	public Date getCreatedAt() {
		return createdAt;
	}
	
}

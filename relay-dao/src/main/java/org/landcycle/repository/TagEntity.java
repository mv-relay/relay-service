package org.landcycle.repository;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Tags")
public class TagEntity {
	public TagEntity() {
	}

	public TagEntity(String id, String tag) {
		super();
		this.id = id;
		this.tag = tag;
	}


	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "uuid", unique = true)
	private String uuid;
	
	@Column(name = "id", length = 100)
	private String id;
	@Column(name = "tag", length = 100)
	private String tag;
	@Column(name = "createdat", updatable = false)
	private Date createdAt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}

	public Date getCreatedAt() {
		return createdAt;
	}
}

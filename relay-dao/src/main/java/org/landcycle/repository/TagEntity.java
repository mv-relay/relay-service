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

	public TagEntity(String idTaggable, String tag) {
		super();
		this.idTaggable = idTaggable;
		this.tag = tag;
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "uuid", unique = true)
	private String uuid;

	@Column(name = "idTaggable", length = 100)
	private String idTaggable;

	@Column(name = "tag", length = 100)
	private String tag;
	@Column(name = "createdat", updatable = false)
	private Date createdAt;

	public String getIdTaggable() {
		return idTaggable;
	}

	public void setIdTaggable(String idTaggable) {
		this.idTaggable = idTaggable;
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

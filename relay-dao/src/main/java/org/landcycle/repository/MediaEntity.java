package org.landcycle.repository;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Media")
public class MediaEntity {
	public MediaEntity() {
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "uuid", unique = true)
	private String uuid;

	@Column(name = "idTaggable", length = 100)
	private String idTaggable;
	@Column(name = "media", length = 100)
	private String media;
	@Column(name = "path", length = 100)
	private String path;
	@Column(name = "name", length = 100)
	private String name;
	@Column(name = "type", length = 100)
	private String type;
	@Column(name = "createdat", updatable = false)
	private Date createdAt;
	@Column(name = "updatedat")
	private Date updatedAt;

	public String getIdTaggable() {
		return idTaggable;
	}

	public void setIdTaggable(String idTaggable) {
		this.idTaggable = idTaggable;
	}

	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	@PreUpdate
	void updatedAt() {
		this.updatedAt = new Date();
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}

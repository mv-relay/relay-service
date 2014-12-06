package org.landcycle.repository;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

@Entity
@Table(name = "Taggable")
public class TaggableEntity {
	public TaggableEntity() {
	}

	public TaggableEntity(String id, String name, String img, String description, String user, String mailacq, String optional, String citta, Double lat,
			Double lng, int category) {
		super();
		this.id = id;
		this.name = name;
		this.img = img;
		this.description = description;
		// this.tags = tags;
		this.user = user;
		this.mailacq = mailacq;
		this.optional = optional;
		this.citta = citta;
		this.lat = lat;
		this.lng = lng;
		this.category = category;
	}

	@Id
	@Column(name = "id", length = 100)
	private String id;
	@Column(name = "name", length = 100)
	private String name;
	@Column(name = "img", length = 100)
	private String img;
	@Column(name = "description", length = 1000)
	private String description;
	@Column(name = "user", length = 100)
	private String user;
	@Column(name = "mailacq", length = 100)
	private String mailacq;
	@Column(name = "optional", length = 100)
	private String optional;
	@Column(name = "city", length = 150)
	private String citta;
	private Double lat;
	private Double lng;
	private int category;
	private String type;
	@Column(name = "createdat", updatable = false)
	private Date createdAt;

	@Column(name = "updatedat")
	private Date updatedAt;

	@JoinColumn(name = "id", referencedColumnName = "id", updatable = false)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<LikeEntity> likes;

	@JoinColumn(name = "id", referencedColumnName = "id", updatable = false)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<CommentEntity> comments;

	@JoinColumn(name = "id", referencedColumnName = "id", updatable = false)
	@OneToMany(cascade = CascadeType.ALL)
	private List<TagEntity> tags;

	@JoinColumn(name = "id", referencedColumnName = "id", updatable = false)
	@OneToMany(cascade = CascadeType.ALL)
	private List<MediaEntity> media;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<TagEntity> getTags() {
		return tags;
	}

	public void setTags(List<TagEntity> tags) {
		this.tags = tags;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getMailacq() {
		return mailacq;
	}

	public void setMailacq(String mailacq) {
		this.mailacq = mailacq;
	}

	public String getOptional() {
		return optional;
	}

	public void setOptional(String optional) {
		this.optional = optional;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public Set<LikeEntity> getLikes() {
		return likes;
	}

	public void setLikes(Set<LikeEntity> likes) {
		this.likes = likes;
	}

	public void addForSale(LikeEntity likes) {
		if (this.likes == null)
			this.likes = new HashSet<LikeEntity>();
		this.likes.add(likes);
	}

	@PrePersist
	void createdAt() {
		this.createdAt = this.updatedAt = new Date();
	}

	@PreUpdate
	void updatedAt() {
		this.updatedAt = new Date();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Set<CommentEntity> getComments() {
		return comments;
	}

	public void setComments(Set<CommentEntity> comments) {
		this.comments = comments;
	}

	public List<MediaEntity> getMedia() {
		return media;
	}

	public void setMedia(List<MediaEntity> media) {
		this.media = media;
	}

}
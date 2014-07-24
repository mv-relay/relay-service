package org.landcycle.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "Forsale")
public class ForSaleEntity implements Serializable {
	@Id
	@Column(name = "id", length = 100)
	private String id;
	@Column(name = "name", length = 100)
	private String name;
	@Column(name = "img", length = 100)
	private String img;
	@Column(name = "description", length = 1000)
	private String description;
	@Column(name = "tags", length = 100)
	private String tags;
//	@Column(name = "mailvend", length = 100)
//	private String mailvend;
	@Column(name = "mailacq", length = 100)
	private String mailacq;
	@Column(name = "optional", length = 100)
	private String optional;
	@Column(name = "city", length = 150)
	private String citta;
	@Column(name = "lat", columnDefinition = "Decimal(10,8)")
	private Double lat;
	// @Column(name = "lng	", columnDefinition = "Decimal(10,8)")
	private Double lng;
	private int category;

	 @ManyToOne
	 private UserEntity user;

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

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

//	public String getMailvend() {
//		return mailvend;
//	}
//
//	public void setMailvend(String mailvend) {
//		this.mailvend = mailvend;
//	}

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

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

}

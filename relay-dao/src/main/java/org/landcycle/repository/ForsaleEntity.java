package org.landcycle.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Forsale")
public class ForsaleEntity {
	public ForsaleEntity() {
	}

	public ForsaleEntity(String id, String name, String img, String description, String tags, String mailvend,
			String mailacq, String optional, String citta, Double lat, Double lng, int category) {
		super();
		this.id = id;
		this.name = name;
		this.img = img;
		this.description = description;
		this.tags = tags;
		this.mailvend = mailvend;
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
	@Column(name = "tags", length = 100)
	private String tags;
	@Column(name = "mailvend", length = 100)
	private String mailvend;
	@Column(name = "mailacq", length = 100)
	private String mailacq;
	@Column(name = "optional", length = 100)
	private String optional;
	@Column(name = "city", length = 150)
	private String citta;
	private Double lat;
	private Double lng;
	private int category;

	// @ManyToOne
	// private User user;

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

	public String getMailvend() {
		return mailvend;
	}

	public void setMailvend(String mailvend) {
		this.mailvend = mailvend;
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

	// public User getUser() {
	// return user;
	// }
	//
	// public void setUser(User user) {
	// this.user = user;
	// }
	//
	//

}

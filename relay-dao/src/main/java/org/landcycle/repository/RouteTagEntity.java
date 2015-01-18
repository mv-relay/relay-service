package org.landcycle.repository;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "RouteTag")
public class RouteTagEntity {

	@Id
	@Column(name = "idTaggable", length = 100)
	private String id;

	@Column(name = "idTaggableExt", length = 100)
	private String idTaggableExt;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdTaggableExt() {
		return idTaggableExt;
	}

	public void setIdTaggableExt(String idTaggableExt) {
		this.idTaggableExt = idTaggableExt;
	}

}
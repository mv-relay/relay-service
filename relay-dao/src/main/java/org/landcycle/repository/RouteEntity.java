package org.landcycle.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "Route")
public class RouteEntity {
	public RouteEntity() {
	}

	@Id
	@Column(name = "idTaggable", length = 100)
	private String id;

	@Column(name = "descRoute", length = 500)
	private String descRoute;

	@Column(name = "createdat", updatable = false)
	private Date createdAt;

	@JoinColumn(name = "idTaggable", referencedColumnName = "idTaggable", updatable = false)
	@OneToMany(cascade = CascadeType.ALL)
	private List<RouteTagEntity> routeTag;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<RouteTagEntity> getRouteTag() {
		if(routeTag == null)
			routeTag = new ArrayList<RouteTagEntity>();
		return routeTag;
	}

	public void setRouteTag(List<RouteTagEntity> routeTag) {
		this.routeTag = routeTag;
	}

	public String getDescRoute() {
		return descRoute;
	}

	public void setDescRoute(String descRoute) {
		this.descRoute = descRoute;
	}

	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	// public List<RouteTagEntity> getRoutTag() {
	// return routTag;
	// }
	//
	// public void setRoutTag(List<RouteTagEntity> routTag) {
	// this.routTag = routTag;
	// }

}

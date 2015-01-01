package org.landcycle.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Route")
public class RouteEntity {
	public RouteEntity() {
	}

	

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "idRoute", unique = true)
	private String idRoute;

	@Column(name = "descRoute", length = 500)
	private String descRoute;

	@Column(name = "createdat", updatable = false)
	private Date createdAt;

	@JoinColumn(name = "idRoute", referencedColumnName = "idRoute", updatable = false)
	@OneToMany(cascade = CascadeType.ALL)
	private List<RouteTagEntity> routeTag;

	public String getIdRoute() {
		return idRoute;
	}

	public void setIdRoute(String idRoute) {
		this.idRoute = idRoute;
	}

	public List<RouteTagEntity> getRouteTag() {
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

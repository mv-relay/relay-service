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
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	@Column(name = "uuid", unique = true)
	private String uuid;

	
//	@Column(name = "idRoute", length = 100)
//	private String idRoute;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	

//	public String getIdRoute() {
//		return idRoute;
//	}
//
//	public void setIdRoute(String idRoute) {
//		this.idRoute = idRoute;
//	}

}

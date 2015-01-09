package org.landcycle.api;

import java.io.Serializable;

public class RouteItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String descRoute;
	private String id;
	private RouteTagItem [] routes;
	public String getDescRoute() {
		return descRoute;
	}
	public void setDescRoute(String descRoute) {
		this.descRoute = descRoute;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public RouteTagItem[] getRoutes() {
		return routes;
	}
	public void setRoutes(RouteTagItem[] routes) {
		this.routes = routes;
	}
}

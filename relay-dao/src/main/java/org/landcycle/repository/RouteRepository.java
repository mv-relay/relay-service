package org.landcycle.repository;

import org.springframework.data.repository.Repository;

public interface RouteRepository extends Repository<RouteEntity, String> {
	public RouteEntity save(RouteEntity comment);
}

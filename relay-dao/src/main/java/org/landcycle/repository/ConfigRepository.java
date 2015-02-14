package org.landcycle.repository;

import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

public interface ConfigRepository extends Repository<ConfigEntity, String> {
	public ConfigEntity findByIdApp(@Param("idApp") String idApp);
}

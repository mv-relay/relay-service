package org.landcycle.repository;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;

@Entity
@Table(name = "APPConfig")
public class ConfigEntity {
	public ConfigEntity() {
	}

	@Id
	@Column(name = "idApp", length = 100)
	private String idApp;
	@Column(name = "jsonConfig", length = 4000)
	private String jsonConfig;

	@Column(name = "createdat", updatable = false)
	private Date createdAt;

	public String getIdApp() {
		return idApp;
	}

	public void setIdApp(String idApp) {
		this.idApp = idApp;
	}

	public String getJsonConfig() {
		return jsonConfig;
	}

	public void setJsonConfig(String jsonConfig) {
		this.jsonConfig = jsonConfig;
	}

	@PrePersist
	void createdAt() {
		this.createdAt = new Date();
	}

	public Date getCreatedAt() {
		return createdAt;
	}

}

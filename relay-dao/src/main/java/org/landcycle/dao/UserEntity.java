package org.landcycle.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "User" )
public class UserEntity implements Serializable {

	public UserEntity() {
	}

	public UserEntity(String mail, String nome, String cognome, String avatar) {
		this.mail = mail;
		this.nome = nome;
		this.cognome = cognome;
		this.avatar = avatar;
	}

	@Id
	@Column(name = "mail", length = 80)
	private String mail;
	@Column(name = "firstName", length = 80)
	private String nome;
	@Column(name = "lastName", length = 80)
	private String cognome;
	@Column(name = "avatar", length = 80)
	private String avatar;

	
	@OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="mailvend")
	private List<ForSaleEntity> forSaleEntity;

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public List<ForSaleEntity> getForSaleEntity() {
		return forSaleEntity;
	}

	public void setForSaleEntity(List<ForSaleEntity> forSaleEntity) {
		this.forSaleEntity = forSaleEntity;
	}

}

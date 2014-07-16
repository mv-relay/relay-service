package org.landcycle.dao;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "user")
public class UserEntity implements Serializable {
	
	public UserEntity(){}
	public UserEntity(String mail,String nome,String cognome,String avatar){
		this.mail = mail;
		this.nome = nome;
		this.cognome = cognome;
		this.avatar = avatar;
	}
	@Id
	@Column(name = "mail", length = 80)
	private String mail;
	@Column(name = "nome", length = 80)
	private String nome;
	@Column(name = "cognome", length = 80)
	private String cognome;
	@Column(name = "avatar", length = 80)
	private String avatar;

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

}

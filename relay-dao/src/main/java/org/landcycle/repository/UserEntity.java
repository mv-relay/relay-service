package org.landcycle.repository;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "User")
public class UserEntity {

	public UserEntity() {
	}

	public UserEntity(String mail, String nome, String cognome, String avatar) {
		this.mail = mail;
		this.nome = nome;
		this.cognome = cognome;
		this.avatar = avatar;
	}

	@Id
	@Column(name = "mail", length = 100, unique = true, nullable = false)
	private String mail;
	@Column(name = "firstName", length = 80)
	private String nome;
	@Column(name = "lastName", length = 80)
	private String cognome;
	@Column(name = "avatar", length = 80)
	private String avatar;

	// @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	// @JoinColumn(name="mailvend")
	@JoinColumn(name = "user", referencedColumnName = "mail", updatable = false)
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch (FetchMode.SELECT)
	private Set<TaggableEntity> taggable;

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

	

	public Set<TaggableEntity> getTaggable() {
		return taggable;
	}

	public void setTaggable(Set<TaggableEntity> taggable) {
		this.taggable = taggable;
	}

	public void addForSale(TaggableEntity taggable) {
		if (this.taggable == null)
			this.taggable = new HashSet<TaggableEntity>();
		this.taggable.add(taggable);
	}

}

package org.landcycle.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// private String mail;
	// private String firstName;
	// private String secondName;
	// private String avatar;

	private User user;
	private List<ForSale> forSales;
	private ForSale forSale;

	// public String getMail() {
	// return mail;
	// }
	//
	// public void setMail(String mail) {
	// this.mail = mail;
	// }
	//
	// public String getFirstName() {
	// return firstName;
	// }
	//
	// public void setFirstName(String firstName) {
	// this.firstName = firstName;
	// }
	//
	// public String getSecondName() {
	// return secondName;
	// }
	//
	// public void setSecondName(String secondName) {
	// this.secondName = secondName;
	// }
	//
	// public String getAvatar() {
	// return avatar;
	// }
	//
	// public void setAvatar(String avatar) {
	// this.avatar = avatar;
	// }

	public List<ForSale> getForSales() {
		return forSales;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setForSales(List<ForSale> forSales) {
		this.forSales = forSales;
	}

	public ForSale getForSale() {
		return forSale;
	}

	public void setForSale(ForSale forSale) {
		this.forSale = forSale;
	}

	public void addSale(ForSale forSale) {
		if (this.forSales == null)
			this.forSales = new ArrayList<ForSale>();
		this.forSales.add(forSale);
	}
}

package org.landcycle.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserItem implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private User user;
	private List<ForSale> forSales;
	private ForSale forSale;

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

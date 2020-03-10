package com.personal.stock.dbservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created a table quotes which will
 * have values id(primary key),username and quote of the stock
 * @author ezaksch
 *
 */
@Entity
@Table(name="quotes",catalog="stock")
public class Quote {

	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	@Column(name="id")
	private int id;
	@Column(name="userName")
	private String userName;
	@Column(name="quote")
	private String quote;
	
	public Quote(){
		
	}
	
	public Quote(String userName, String quote) {
		super();
		this.userName = userName;
		this.quote = quote;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}
	@Override
	public String toString() {
		return "Quote [id=" + id + ", userName=" + userName + ", quote="
				+ quote + "]";
	}
	
	
	
}

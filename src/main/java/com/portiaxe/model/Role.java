package com.portiaxe.model;



public class Role {
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	public Role role(String role){
		this.role = role;
		return  this;
	}

	private int id;
	private String role;
}

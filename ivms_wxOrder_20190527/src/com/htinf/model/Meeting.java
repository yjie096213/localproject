package com.htinf.model;

import java.util.List;

public class Meeting {
	
	private int id;
	
	private int pid;
	
	private int accompany_id;
	
	private String created_at;
	
	private String updated_at;
	
	private int app_id;
	
	private Prisoner prisoner;
	
	private Accompany accompany;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public int getAccompany_id() {
		return accompany_id;
	}

	public void setAccompany_id(int accompanyId) {
		accompany_id = accompanyId;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String createdAt) {
		created_at = createdAt;
	}

	public String getUpdated_at() {
		return updated_at;
	}

	public void setUpdated_at(String updatedAt) {
		updated_at = updatedAt;
	}

	public int getApp_id() {
		return app_id;
	}

	public void setApp_id(int appId) {
		app_id = appId;
	}

	public Prisoner getPrisoner() {
		return prisoner;
	}

	public void setPrisoner(Prisoner prisoner) {
		this.prisoner = prisoner;
	}

	public Accompany getAccompany() {
		return accompany;
	}

	public void setAccompany(Accompany accompany) {
		this.accompany = accompany;
	}

}

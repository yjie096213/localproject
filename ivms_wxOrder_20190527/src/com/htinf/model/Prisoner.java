package com.htinf.model;

public class Prisoner {
	
	private long id;
	
	private String name;
	
	private String code;
	
	private String deleted_at;
	
	private String created_at;
	
	private String updated_at;
	
	private String prison_num;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDeleted_at() {
		return deleted_at;
	}

	public void setDeleted_at(String deletedAt) {
		deleted_at = deletedAt;
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

	public String getPrison_num() {
		return prison_num;
	}

	public void setPrison_num(String prisonNum) {
		prison_num = prisonNum;
	}
	
}

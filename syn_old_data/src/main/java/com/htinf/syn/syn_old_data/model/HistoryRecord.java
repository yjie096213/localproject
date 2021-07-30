/**
 * ProjectName: ivms_wujiaochang_20210727
 * ClassName: HistoryRecord 
 * @Description: TODO
 * @author yj
 * @date 2021-7-28
 */
package com.htinf.syn.syn_old_data.model;

import lombok.Data;

import java.util.Date;

@Data
public class HistoryRecord {

	/**
	 *
	 */
	private static final long serialVersionUID = -243213772384735150L;


	private long id;

	private String prisonerNo;

	private String prisonerName;

	private String prisonerArea;

	private String prisonerLevel;

	private String visitType;

	private String windowNo;

	private Integer isApply;

	private String isApplyName;

	private String approveOperator;

	private String approveTime;

	private String register;

	private String registTime;

	private String startTime;

	private String endTime;

	private String visitState;

	private String duration;

	private String visitorNames;

	private String visitorIdNos;

	private String listener;

	private String listenTime;

	private String listenEvaluation;

	private String listenContext;

	private String recordPath;

	private String recordName;

	private String recordWebPath;

	private String originalId;

	private Integer status;

	private Date searchStartTime;

	private Date searchEndTime;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getPrisonerNo() {
		return prisonerNo;
	}

	public void setPrisonerNo(String prisonerNo) {
		this.prisonerNo = prisonerNo;
	}

	public String getPrisonerName() {
		return prisonerName;
	}

	public void setPrisonerName(String prisonerName) {
		this.prisonerName = prisonerName;
	}

	public String getPrisonerArea() {
		return prisonerArea;
	}

	public void setPrisonerArea(String prisonerArea) {
		this.prisonerArea = prisonerArea;
	}

	public String getPrisonerLevel() {
		return prisonerLevel;
	}

	public void setPrisonerLevel(String prisonerLevel) {
		this.prisonerLevel = prisonerLevel;
	}

	public String getVisitType() {
		return visitType;
	}

	public void setVisitType(String visitType) {
		this.visitType = visitType;
	}

	public String getWindowNo() {
		return windowNo;
	}

	public void setWindowNo(String windowNo) {
		this.windowNo = windowNo;
	}

	public Integer getIsApply() {
		return isApply;
	}

	public void setIsApply(Integer isApply) {
		this.isApply = isApply;
	}

	public String getIsApplyName() {
		return isApplyName;
	}

	public void setIsApplyName(String isApplyName) {
		this.isApplyName = isApplyName;
	}

	public String getApproveOperator() {
		return approveOperator;
	}

	public void setApproveOperator(String approveOperator) {
		this.approveOperator = approveOperator;
	}

	public String getApproveTime() {
		return approveTime;
	}

	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}

	public String getRegister() {
		return register;
	}

	public void setRegister(String register) {
		this.register = register;
	}

	public String getRegistTime() {
		return registTime;
	}

	public void setRegistTime(String registTime) {
		this.registTime = registTime;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getVisitState() {
		return visitState;
	}

	public void setVisitState(String visitState) {
		this.visitState = visitState;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getVisitorNames() {
		return visitorNames;
	}

	public void setVisitorNames(String visitorNames) {
		this.visitorNames = visitorNames;
	}

	public String getVisitorIdNos() {
		return visitorIdNos;
	}

	public void setVisitorIdNos(String visitorIdNos) {
		this.visitorIdNos = visitorIdNos;
	}

	public String getListener() {
		return listener;
	}

	public void setListener(String listener) {
		this.listener = listener;
	}

	public String getListenTime() {
		return listenTime;
	}

	public void setListenTime(String listenTime) {
		this.listenTime = listenTime;
	}

	public String getListenEvaluation() {
		return listenEvaluation;
	}

	public void setListenEvaluation(String listenEvaluation) {
		this.listenEvaluation = listenEvaluation;
	}

	public String getListenContext() {
		return listenContext;
	}

	public void setListenContext(String listenContext) {
		this.listenContext = listenContext;
	}

	public String getRecordPath() {
		return recordPath;
	}

	public void setRecordPath(String recordPath) {
		this.recordPath = recordPath;
	}

	public String getRecordName() {
		return recordName;
	}

	public void setRecordName(String recordName) {
		this.recordName = recordName;
	}

	public String getRecordWebPath() {
		return recordWebPath;
	}

	public void setRecordWebPath(String recordWebPath) {
		this.recordWebPath = recordWebPath;
	}

	public String getOriginalId() {
		return originalId;
	}

	public void setOriginalId(String originalId) {
		this.originalId = originalId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getSearchStartTime() {
		return searchStartTime;
	}

	public void setSearchStartTime(Date searchStartTime) {
		this.searchStartTime = searchStartTime;
	}

	public Date getSearchEndTime() {
		return searchEndTime;
	}

	public void setSearchEndTime(Date searchEndTime) {
		this.searchEndTime = searchEndTime;
	}

	@Override
	public String toString() {
		return "HistoryRecord{" +
				"prisonerNo='" + prisonerNo + '\'' +
				", prisonerName='" + prisonerName + '\'' +
				", prisonerArea='" + prisonerArea + '\'' +
				", prisonerLevel='" + prisonerLevel + '\'' +
				", visitType='" + visitType + '\'' +
				", windowNo='" + windowNo + '\'' +
				", isApply=" + isApply +
				", isApplyName='" + isApplyName + '\'' +
				", approveOperator='" + approveOperator + '\'' +
				", approveTime='" + approveTime + '\'' +
				", register='" + register + '\'' +
				", registTime='" + registTime + '\'' +
				", startTime='" + startTime + '\'' +
				", endTime='" + endTime + '\'' +
				", visitState='" + visitState + '\'' +
				", duration='" + duration + '\'' +
				", visitorNames='" + visitorNames + '\'' +
				", visitorIdNos='" + visitorIdNos + '\'' +
				", listener='" + listener + '\'' +
				", listenTime='" + listenTime + '\'' +
				", listenEvaluation='" + listenEvaluation + '\'' +
				", listenContext='" + listenContext + '\'' +
				", recordPath='" + recordPath + '\'' +
				", recordName='" + recordName + '\'' +
				", recordWebPath='" + recordWebPath + '\'' +
				", originalId='" + originalId + '\'' +
				'}';
	}
}

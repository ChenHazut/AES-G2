package logic;

import java.io.Serializable;

public class OvertimeDetails implements Serializable {
	private String ExamID;
	private int ExecutionID;
	private int requestedTime;
	private String reason;
	private Boolean isApproved;

	public OvertimeDetails(String examID, int executionID, int requestedTime, String reason, Boolean isApproved) {
		super();
		ExamID = examID;
		ExecutionID = executionID;
		this.requestedTime = requestedTime;
		this.reason = reason;
		this.isApproved = isApproved;
	}

	public String getExamID() {
		return ExamID;
	}

	public void setExamID(String examID) {
		ExamID = examID;
	}

	public int getExecutionID() {
		return ExecutionID;
	}

	public void setExecutionID(int executionID) {
		ExecutionID = executionID;
	}

	public int getRequestedTime() {
		return requestedTime;
	}

	public void setRequestedTime(int requestedTime) {
		this.requestedTime = requestedTime;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Boolean getIsApproved() {
		return isApproved;
	}

	public void setIsApproved(Boolean isApproved) {
		this.isApproved = isApproved;
	}

}

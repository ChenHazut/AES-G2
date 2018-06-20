package logic;

import java.io.Serializable;

public class ExecutionDetails implements Serializable {
	private String ExamID;
	private int ExecutionID;

	public ExecutionDetails(String examID, int executionID) {
		super();
		ExamID = examID;
		ExecutionID = executionID;
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

	@Override
	public String toString() {
		return "ExecutionDetails [ExamID=" + ExamID + ", ExecutionID=" + ExecutionID + "]";
	}

}

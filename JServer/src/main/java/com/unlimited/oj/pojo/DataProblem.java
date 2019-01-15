package com.unlimited.oj.pojo;

public class DataProblem {
	/** 题目标题 */
	public Long id;
	public java.lang.String title = "";
	public java.lang.String description;
	public java.lang.String input;
	public java.lang.String output;
	public java.lang.String sampleInput;
	public java.lang.String sampleOutput;
	public java.lang.String hint;
	public java.lang.String source;
	public int timeLimit = 0;
	public int caseTimeLimit = 0;
	public int memoryLimit = 0;
	public int accepted = 0;
	public int submit = 0;
	public java.lang.String author;
	public int type = 0;
	/** 与该题目关联的外网题目的ID(仅用于type为2时) */
	public java.lang.String problemIdLinked;
	public java.lang.String onlineJudgeLinked;
	public java.lang.String onlineJudgeId;
	public String tag;
	public String tagAuthor;
	public int doneStatus;
	public String md5Identity;
	public boolean hasAnswer;
	public boolean answerVisible;
	public boolean publishFlag;
	public boolean privateFlag;

	public Long getId() {
		return id;
	}

	public java.lang.String getTitle() {
		return title;
	}

	public java.lang.String getDescription() {
		return description;
	}

	public java.lang.String getInput() {
		return input;
	}

	public java.lang.String getOutput() {
		return output;
	}

	public java.lang.String getSampleInput() {
		return sampleInput;
	}

	public java.lang.String getSampleOutput() {
		return sampleOutput;
	}

	public java.lang.String getHint() {
		return hint;
	}

	public java.lang.String getSource() {
		return source;
	}

	public int getTimeLimit() {
		return timeLimit;
	}

	public int getCaseTimeLimit() {
		return caseTimeLimit;
	}

	public int getMemoryLimit() {
		return memoryLimit;
	}

	public int getAccepted() {
		return accepted;
	}

	public int getSubmit() {
		return submit;
	}

	public java.lang.String getAuthor() {
		return author;
	}

	public int getType() {
		return type;
	}

	public java.lang.String getProblemIdLinked() {
		return problemIdLinked;
	}

	public java.lang.String getOnlineJudgeLinked() {
		return onlineJudgeLinked;
	}

	public java.lang.String getOnlineJudgeId() {
		return onlineJudgeId;
	}

	public String getTag() {
		return tag;
	}

	public String getTagAuthor() {
		return tagAuthor;
	}

	public int getDoneStatus() {
		return doneStatus;
	}

	public String getMd5Identity() {
		return md5Identity;
	}

	public boolean getHasAnswer() {
		return hasAnswer;
	}

	public boolean isPublishFlag() {
		return publishFlag;
	}

	public boolean isPrivateFlag() {
		return privateFlag;
	}

	public boolean isAnswerVisible() {
		return answerVisible;
	}
	
}

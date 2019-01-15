package com.unlimited.appserver.webapp;

public interface OJAPI {
	String OJ_ADDRESS="http://127.0.0.1:8080/uoj";
	String GET_PROBLEM_TREE=OJ_ADDRESS+"/getOffspringByParentId.html";
	String GET_PROBLEM_BY_ID=OJ_ADDRESS+"/getProblemByProblemId.html";
	String GET_RECORDS_BY_PROBLEM_ID=OJ_ADDRESS+"/getListOfUserByProblemId.html";
	String GET_ACCEPT_RECORDS=OJ_ADDRESS+"/getAllAcceptedOfUser.html";
	String GET_RECORD_BY_ID=OJ_ADDRESS+"/getBySolutionId.html";
	String GET_PROBLEM_TREE_BY_KEYSTRING=OJ_ADDRESS+"/getIdByKeystring.html";
	String GET_ALL_EXAM_OF_USER=OJ_ADDRESS+"/getAllOfUser.html";
	String GET_EXAM_BY_EXAM_ID=OJ_ADDRESS+"/getDetailOfExamPaper.html";
	String GET_EXAM_SOLUTION=OJ_ADDRESS+"/getListOfExamPaper.html";
	String GET_USER_SIGNIN=OJ_ADDRESS+"/signIn.html";
}

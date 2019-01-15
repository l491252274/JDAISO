<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="cssMainMenu.vm" permissions="rolesAdapter">
    <ul class="mainmenu">
        <c:if test="${empty pageContext.request.remoteUser}"><li><a href="<c:url value="/login.jsp"/>" class="current"><fmt:message key="login.title"/></a></li></c:if>
        <%
            Object obj = session.getAttribute("oj_sign");
            pageContext.setAttribute("_sign", obj);
        %>
        <c:if test="${false && _sign==null &&
                      (currentUser.administrator || appConfig['SCAUCPCMenu']!=null && appConfig['SCAUCPCMenu']==1)}">
        <menu:displayMenu name="SCAUCPCMenu"/>
        </c:if>

        <c:if test="${_sign==null &&
                      (currentUser.administrator || appConfig['MainMenu']!=null && appConfig['MainMenu']==1)}">
        <menu:displayMenu name="MainMenu"/>
        </c:if>

        <c:if test="${_sign==null &&
                      (currentUser.administrator || appConfig['UserMenu']!=null && appConfig['UserMenu']==1)}">
        <menu:displayMenu name="UserMenu"/>
        </c:if>
        
        <c:if test="${false && _sign==null &&
                      (currentUser.administrator || appConfig['ExerciseMenu']!=null && appConfig['ExerciseMenu']==1)}">
            <menu:displayMenu name="ExerciseMenu"/>
        </c:if>
        <c:if test="${_sign==null &&
                      (currentUser.administrator || appConfig['ExperimentMenu']!=null && appConfig['ExperimentMenu']==1)}">
            <menu:displayMenu name="ExperimentMenu"/>
        </c:if>
        <c:if test="${false && _sign==null &&
                      (currentUser.administrator || appConfig['GenericBoardMenu']!=null && appConfig['GenericBoardMenu']==1)}">
            <menu:displayMenu name="GenericBoardMenu"/>
        </c:if>
        <c:if test="${_sign==null &&
                      (currentUser.administrator || appConfig['ReportMenu']!=null && appConfig['ReportMenu']==1)}">
            <menu:displayMenu name="ReportMenu"/>
        </c:if>
        <c:if test="${_sign==null &&
                      (currentUser.administrator || appConfig['ExamMenu']!=null && appConfig['ExamMenu']==1)}">
            <menu:displayMenu name="ExamMenu"/>
        </c:if>
        <c:if test="${_sign==null &&
                      (currentUser.administrator || appConfig['ContestMenu']!=null && appConfig['ContestMenu']==1)}">
            <menu:displayMenu name="ContestMenu"/>
        </c:if>
        <c:if test="${_sign==null &&
                      (currentUser.administrator || appConfig['ContestMenu']!=null && appConfig['ContestMenu']==1)}">
            <menu:displayMenu name="VirtualContestMenu"/>
        </c:if>
        <c:if test="${false && _sign==null &&
                      (currentUser.administrator || appConfig['CourseDesignMenu']!=null && appConfig['CourseDesignMenu']==1)}">
            <menu:displayMenu name="CourseDesignMenu"/>
        </c:if>
        <c:if test="${false && _sign==null &&
                      (currentUser.administrator || appConfig['ChallengeMenu']!=null && appConfig['ChallengeMenu']==1)}">
            <menu:displayMenu name="ChallengeMenu"/>
        </c:if>
        <c:if test="${false && _sign==null &&
                      (currentUser.administrator || appConfig['GameMenu']!=null && appConfig['GameMenu']==1)}">
            <menu:displayMenu name="GameMenu"/>
        </c:if>
        <c:if test="${_sign==null &&
                      (currentUser.administrator || appConfig['AdvanceExperimentMenu']!=null && appConfig['AdvanceExperimentMenu']==1)}">
            <menu:displayMenu name="AdvanceExperimentMenu"/>
        </c:if>
        <c:if test="${_sign==null &&
                      (currentUser.administrator || appConfig['HeroBoardMenu']!=null && appConfig['HeroBoardMenu']==1)}">
            <menu:displayMenu name="HeroBoardMenu"/>
        </c:if>
        <c:if test="${_sign==null &&
                      (currentUser.administrator || appConfig['RegisterMenu']!=null && appConfig['RegisterMenu']==1)}">
            <menu:displayMenu name="RegisterMenu"/>
        </c:if>
        <c:if test="${_sign==null &&
                      (currentUser.administrator || appConfig['LibraryMenu']!=null && appConfig['LibraryMenu']==1)}">
            <menu:displayMenu name="LibraryMenu"/>
        </c:if>
        <c:if test="${_sign==null &&
                      (currentUser.administrator || appConfig['NewsMenu']!=null && appConfig['NewsMenu']==1)}">
            <menu:displayMenu name="NewsMenu"/>
        </c:if>
        <c:if test="${_sign==null &&
                      (currentUser.administrator || appConfig['AdminMenu']!=null && appConfig['AdminMenu']==1)}">
            <menu:displayMenu name="AdminMenu"/>
        </c:if>

        <c:if test="${false && _sign!=null &&
                      (currentUser.administrator || appConfig['ContestMenuSign']!=null && appConfig['ContestMenuSign']==1)}">
            <menu:displayMenu name="ContestMenuSign"/>
        </c:if>
        <c:if test="${false && _sign!=null &&
                      (currentUser.administrator || appConfig['AdminMenu']!=null && appConfig['AdminMenu']==1)}">
            <menu:displayMenu name="AdminMenu"/>
        </c:if>

        <menu:displayMenu name="Logout"/>
    </ul>
</menu:useMenuDisplayer>

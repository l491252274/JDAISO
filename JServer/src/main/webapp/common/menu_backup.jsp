<%@ include file="/common/taglibs.jsp"%>

<menu:useMenuDisplayer name="Velocity" config="cssMainMenu.vm" permissions="rolesAdapter">
<ul class="mainmenu">
    <c:if test="${empty pageContext.request.remoteUser}"><li><a href="<c:url value="/login.jsp"/>" class="current"><fmt:message key="login.title"/></a></li></c:if>
    <menu:displayMenu name="MainMenu"/>
    <menu:displayMenu name="UserMenu"/>
    <menu:displayMenu name="ExerciseMenu"/>
    <menu:displayMenu name="ExperimentMenu"/>
    <menu:displayMenu name="ExamMenu"/>
    <menu:displayMenu name="ContestMenu"/>
    <menu:displayMenu name="CourseDesignMenu"/>
    <menu:displayMenu name="ChallengeMenu"/>
    <menu:displayMenu name="HeroBoardMenu"/>
    <menu:displayMenu name="AdminMenu"/>
    <menu:displayMenu name="Logout"/>
</ul>
</menu:useMenuDisplayer>
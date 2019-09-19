<%@page import="kr.co.itcen.mysite.dao.UserDao"%>
<%@page import="kr.co.itcen.web.WebUtils"%>
<%@page import="kr.co.itcen.mysite.vo.UserVo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
 	UserVo userVo = (UserVo)request.getAttribute("userVo");
%>

<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath() %>/assets/css/user.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="content">
			<div id="user">

				<form id="update-form" name="updateForm" method="post" action="<%=request.getContextPath() %>/user">
				<input type = 'hidden' name = 'a' value ='update' />
				
					<label class="block-label" for="name">이름</label>
					<input id="name" name="name" type="text" value="${userVo.name }">
					<label class="block-label" for="email">이메일</label>
					<h4>${userVo.email }</h4>
					
					<label class="block-label">패스워드</label>
					<input name="password" type="password" value="">
					
					<fieldset>
						<legend>성별</legend>
						<c:choose>
							<c:when test="${requestScope.result == 'fail'}">
								<p>
									로그인이 실패 했습니다.
								</p>
							</c:when>
						</c:choose>
						<c:choose>
							<c:when test="${userVo.gender == 'female'}">
								<label>여</label> <input type="radio" name="gender" value="female" checked="checked">
								<label>남</label> <input type="radio" name="gender" value="male">
							</c:when>
							<c:otherwise>
								<label>여</label> <input type="radio" name="gender" value="female" >
								<label>남</label> <input type="radio" name="gender" value="male" checked="checked">
							</c:otherwise>
						</c:choose>
					</fieldset>
					
					<input type="submit" value="수정하기">
					
				</form>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>
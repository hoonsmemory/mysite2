<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="<%=request.getContextPath()%>/assets/css/board.css" rel="stylesheet" type="text/css">
</head>
<body>
	<div id="container">
		<c:import url="/WEB-INF/views/includes/header.jsp"/>
		<div id="content">
			<div id="board">
				<form id="search_form" action="<%=request.getContextPath()%>/board?a=search" method="post">
					<input type="text" id="kwd" name="text" value="제목으로만 찾기 가능">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>
					<c:forEach items ='${list}' var = 'vo' varStatus = 'status'>			
					<tr>
						<td>${status.count }</td>
						<c:choose>
							<c:when test="${vo.state eq 'y'}">
							<c:if test="${vo.o_no > 1 }">
							<td style ='padding-left:${50*vo.depth}px'><img src='${pageContext.servletContext.contextPath }/assets/images/reply.png'><a href="${pageContext.servletContext.contextPath }/board?a=viewform&titleNo=${vo.no }&boardUserNo=${vo.user_no}">[새글] ${vo.title }</a></td>
							</c:if>
							<c:if test="${vo.o_no eq 1 }">
							<td><a href="${pageContext.servletContext.contextPath }/board?a=viewform&titleNo=${vo.no }&boardUserNo=${vo.user_no}">[새글] ${vo.title }</a></td>	
							</c:if>
							<td >${vo.user_name }</td>
							<td>${vo.hit }</td>
							<td>${vo.reg_date }</td>
							<c:if test="${vo.user_no eq userNo}">
							<td><a href="${pageContext.servletContext.contextPath }/board?a=delete&no=${vo.no }" class="del">삭제</a></td>
							</c:if>
							</c:when>
							<c:when test="${vo.state eq 'u'}">
							<c:if test="${vo.o_no > 1 }">
							<td style ='padding-left:${50*vo.depth}px'><img src='${pageContext.servletContext.contextPath }/assets/images/reply.png'><a href="${pageContext.servletContext.contextPath }/board?a=viewform&titleNo=${vo.no }&boardUserNo=${vo.user_no}">[새글] ${vo.title }</a></td>
							</c:if>
							<c:if test="${vo.o_no eq 1 }">
							<td><a href="${pageContext.servletContext.contextPath }/board?a=viewform&titleNo=${vo.no }&boardUserNo=${vo.user_no}">[새글] ${vo.title }</a></td>	
							</c:if>							
							<td >${vo.user_name }</td>
							<td>${vo.hit }</td>
							<td>${vo.reg_date }</td>
							<c:if test="${vo.user_no eq userNo}">
							<td><a href="${pageContext.servletContext.contextPath }/board?a=delete&no=${vo.no }" class="del">삭제</a></td>
							</c:if>							
							</c:when>
							<c:when test="${vo.state eq 'n'}">
							<td>[삭제글]</td>
							<td >${vo.user_name }</td>
							<td>${vo.hit }</td>
							<td>${vo.reg_date }</td>
							<td></td>
							</c:when>
						</c:choose>
					</tr>
					</c:forEach>
				
				</table>
				
				<!-- pager 추가 -->
				<div class="pager">

					<ul>
						<c:if test="${cCount < 5 }">
						<li><a href="${pageContext.servletContext.contextPath }/board?a=default&cCount=${cCount = cCount }">◀</a></li>
						</c:if>
						<c:if test="${cCount > 5 }">
						<li><a href="${pageContext.servletContext.contextPath }/board?a=default&cCount=${startnum - 1 }">◀</a></li>
						</c:if>
						<c:forEach var="i" begin="${startnum }" end="${lastnum }">
						<c:choose>
					    <c:when test="${page >= i}">
					    	<c:if test="${cCount eq i }">
					    		<li class="selected"><a href="${pageContext.servletContext.contextPath }/board?a=default&cCount=${i }">${i }</a></li>
					    	</c:if>
					        <c:if test="${cCount ne i }">
					       		<li><a href="${pageContext.servletContext.contextPath }/board?a=default&cCount=${i }">${i }</a></li>
					        </c:if>
					    </c:when>
					    <c:otherwise>
					    	<li>${i }</li>
					    </c:otherwise>
						</c:choose>
						</c:forEach>
						<li><a href="${pageContext.servletContext.contextPath }/board?a=default&cCount=${lastnum + 1 }">▶</a></li>
					</ul>
				</div>					
				<!-- pager 추가 -->
				
				<c:if test="${user eq 'logO'}">
				<div class="bottom">
					<a href="${pageContext.servletContext.contextPath }/board?a=writeform" id="new-book">글쓰기</a>
				</div>
				</c:if>				
			</div>
		</div>
		<c:import url="/WEB-INF/views/includes/navigation.jsp">
			<c:param name="menu" value="board"/>
		</c:import>
		<c:import url="/WEB-INF/views/includes/footer.jsp"/>
	</div>
</body>
</html>
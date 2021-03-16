<%-- <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<meta charset="UTF-8">
<span class="blks">
 <span class="special">
	<span class="our">글목록</span> 
	<span class="lnk">
	<c:forEach var="Board" items="${titleList}">
		<a href='board/contents.do?blno=${Board.blno}&cno=${Board.cno}'>
		${Board.title}</a>,
		${Board.id},
		${Board.regDate},
		${Board.viewCount}
		<br/>
	</c:forEach>
	</span> <a href="http://all-free-download.com/free-website-templates/"><img
			src="images/rd.gif" alt="" width="56" height="12" class="rdmr2" /></a>
	</span>
</span> --%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지게시판 </title>
</head>
<body>
	공지 
</body>
</html>

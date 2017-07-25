<%@page import="sesoc.global.test.vo.Board"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	body {
		font-family : sans-serif;
		font-size : 0.9em;
	}
	input[type=submit] {
		border : none;
	}
	div#wrapper {
		width:900px;
		margin : 0 auto;
		text-align : center;
	}
	table {
		width : 900px;
	}
	.title {
		width : 450px;
	}
	th {
		background : #efefef;
	}
	.write {
		text-align : right;
	}
	.home {
		text-align : left;
	}
		a {
		display : inline-block;
	}
	a, a:link, a:visited, a:active {
		text-decoration : none;
	}
	a:hover {
		font-weight:bolder;
		color : #F00;
	}
	.btn {
		text-align : center;
		border-radius : 0.5em;
		width : 50px;
		margin-top : 10px;
		padding : 5px;
		background : #eee;
		
	}
	input[type=submit]:hover{
		font-weight:bold;
	}
	#search {
		text-align:right;
	}
	#star,#clip{
		width:15px;
		height:15px;
	}
</style>
</head>
<body>
<div id="wrapper">

<h2>[ 게시판 글 목록 ]</h2>
<div class="home">
	<a href="${pageContext.request.contextPath}/"><img src="images/home.png" /></a>

	<!-- 특정 글 검색 -->
	<form id="search" action ="boardList" method="GET" >
	<input type = "hidden" name = "action" value = "boardList" />
	<select name="searchtype">
		<option value="title" ${map.searchtype == 'title' ? 'selected' : '' }>제목</option>
		<option value="custid" ${map.searchtype == 'custid' ? 'selected' : '' }>작성자</option>
		<option value="content" ${map.searchtype == 'content' ? 'selected' : '' }>내용</option>
	</select>
	<input type="text" name="searchword" value = "${map.searchword}" />
	<input class="btn" type="submit" value="검색" />
	</form>
</div>
<!-- 게시글 목록 시작 -->
<table border='1'>
	<tr>
		<th>번호</th>
		<th>첨부파일</th>
		<th class="title">글제목</th>
		<th>글쓴날</th>
		<th>글쓴이</th>
		<th>조회수</th>
	</tr>
	
	<c:forEach var="notice" items="${noticeList}" varStatus="stat">
	<tr>
		<td>${stat.count + navi.currentPage*navi.countPerPage-navi.countPerPage}</td>
		<td>
		<c:if test="${notice.originalfile != null}">
			<img src="images/clip.png" id="clip" />
		</c:if>
		</td>
		<td class="title">
			<a href="noticeDetail?noticenum=${notice.noticenum}&currentPage=${currentPage}&searchtype=${searchtype}&searchword=${searchword}">[  공지사항  ]      ${notice.title}</a>
		</td>
		<td>${notice.inputdate}</td>
		<td>${notice.custid}</td>
		<td>${notice.hits}</td>
	</tr>	
	</c:forEach>
	
	<c:forEach var="board" items="${boardList}" varStatus="stat">
	<tr>
		<td>${stat.count + fn:length(noticeList) + navi.currentPage*navi.countPerPage-navi.countPerPage}</td>
		<td>
		<c:if test="${board.originalfile != null}">
			<img src="images/clip.png" id="clip" />
		</c:if>
		</td>
		<td class="title">
			<c:forEach var="b" items="${rankList}" varStatus="s">
				<c:if test="${b.boardnum == board.boardnum}">
					<img src="images/star.jpg" id="star" />
				</c:if>
			</c:forEach>
			<a href="boardDetail?boardnum=${board.boardnum}&currentPage=${currentPage}&searchtype=${searchtype}&searchword=${searchword}">${board.title}</a>
		</td>
		<td>${board.inputdate}</td>
		<td>${board.custid}</td>
		<td>${board.hits}</td>
	</tr>	
	</c:forEach>
</table>
<%-- <c:if test="${not empty custid}"> --%>
	<div class="write"><a class="btn" href="boardWriteForm">글쓰기</a></div>
<%-- </c:if> --%>

<!-- Paging 출력 부분 -->
<!-- 고칠부분 -->
<div id="navigator">
<a href="boardList?currentPage=${navi.currentPage - navi.pagePerGroup }&searchtype=${map.searchtype}&searchword=${map.searchword}">◁◁</a>
<a href="boardList?currentPage=${navi.currentPage-1}&searchtype=${map.searchtype}&searchword=${map.searchword}">◀</a>
<c:forEach var="page" begin="${navi.startPageGroup}" end="${navi.endPageGroup}">
	<c:if test = "${navi.currentPage == page}">
		<span style = "color:blure; font-weight:bold; font-size:1.1em;">${page}</span>
	</c:if>
	<c:if test = "${navi.currentPage ne page}">
		<a href="boardList?currentPage=${page}&searchtype=${map.searchtype}&searchword=${map.searchword}">${page}</a>
	</c:if>
</c:forEach>
<a href="boardList?currentPage=${navi.currentPage+1}&searchtype=${map.searchtype}&searchword=${map.searchword}">▶</a>
<a href="boardList?currentPage=${navi.currentPage + navi.pagePerGroup }&searchtype=${map.searchtype}&searchword=${map.searchword}">▷▷</a>

</div>
<hr/>

<%-- <div>
<c:forEach var="page" begin="1" end="${totalPage}">
	<c:if test = "${currPage == page}">
	<span style = "color:blure; font-weight:bold; font-size:1.1em;">${page}</span>
	</c:if>
	<c:if test = "${currPage ne page}">
	<a href="board?action=boardList&currPage=${page}">${page}</a>
	</c:if>
</c:forEach>
</div> --%>
<!-- 위 코드와 동일 -->
<%-- <div>
	<% for(int i=1; i<=5 ; i++) { %>
		<a href=""><%=i %></a>
	<%} %>
</div> --%>
<div>
	<h2>${custid}</h2>
</div>
</div>
</body>
</html>






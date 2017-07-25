<%@page import="sesoc.global.test.vo.Board"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>notice List</title>
<style>
	body {
		font-family : sans-serif;
		font-size : 0.9em;
	}
	input[type=submit] {
		border : none;
	}
	div#wrapper {
		width:600px;
		margin : 0 auto;
		text-align : center;
	}
	table {
		width : 600px;
	}
	.title {
		width : 300px;
	}
	th {
		background : #efefef;
	}
	.write {
		text-align : right;
	}
	.notice {
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
		width : 100px;
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
<h2> [ 공지사항 글 목록  ]</h2>
	<div class = "notice">
			<a href="${pageContext.request.contextPath }/"><img src="images/home.png"></a>
			<form id="search" action = "noticeList" method="GET">
			<select name="searchtype">
				<option value="title" ${map.searchtype=='title'?'selected' : '' }>제목</option>
				<option value="content" ${map.searchtype=='content'?'selected' : '' }>내용</option>
			</select>
			<input type="text" name="searchword" value="${map.searchword }"/>
			<input class="btn" type="submit" value = "검색" />
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
	
	<c:forEach var="notice" items="${noticeList }" varStatus="stat">
	<tr>
		<td>${stat.count + navi.currentPage*navi.countPerPage-navi.countPerPage }</td>
		<td>
			<c:if test="${notice.originalfile != null }">
				<img src="images/clip.png" id="clip"/>
			</c:if>
		</td>
		<td class="title">
			<%-- <c:forEach var="b" items="${rankList }" varStatus="s">
				<c:if test="${b.num == notice.noticenum }">
					<img src="images/star.jpg" id="star" />
				</c:if>
			</c:forEach> --%>
			<a href="noticeDetail?noticenum=${notice.noticenum }&currentPage=${currentPage}&searchtype=${searchtype}&searchword=${searchword}">${notice.title}</a>
		</td>
		<td>${notice.inputdate }</td>
		<td>${notice.custid }</td>
		<td>${notice.hits }</td>
	</tr>
	</c:forEach>
</table>
	<c:if test="${custid == 'admin' }">
		<div class="write"><a class="btn" href="noticeWriteForm">공지사항 작성</a></div>
	</c:if>

<div id="navigator">
	<a href="noticeList?currentPage=${navi.currentPage-navi.pagePerGroup }&searchtype=${map.searchtype}&searchword=${map.searchword}">◁◁</a>
	<a href="noticeList?currentPage=${navi.currentPage-1 }&searchtype=${map.searchtype}&searchword=${map.searchword}">◀</a>
	<c:forEach var="page" begin="${navi.startPageGroup }" end="${navi.endPageGroup }">	
		<c:if test="${navi.currentPage == page }">
			<span style = "color:blure; font-weight:bold; font-size:1.1em;">${page }</span>
		</c:if>
		<c:if test="${navi.currentPage ne page }">
			<a href="noticeList?currentPage=${page }&searchtype=${map.searchtype}&searchword=${map.searchword}">${page}</a>
		</c:if>
	</c:forEach>
	<a href="noticeList?currentPage=${navi.currentPage+1 }&searchtype=${map.searchtype}&searchword=${map.searchword}">▶</a>
	<a href="noticeList?currentPage=${navi.currentPage+navi.pagePerGroup }&searchtype=${map.searchtype}&searchword=${map.searchword}">▷▷</a>
</div>
<div>
	<h2>${custid}</h2>
</div>
	
	
	
	
	
	
	
	
</div>
</body>
</html>
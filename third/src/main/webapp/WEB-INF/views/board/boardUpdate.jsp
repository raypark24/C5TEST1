<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	div#wrapper{
		width : 800px;
		margin : 0 auto;
		
	}
	div#wrapper>h2 {
		text-align : center;
	}
	th {
		width: 100px;
	}
	td {
		width:600px;
	}
	div#wrapper table {
		margin:0 auto;
	}
</style>
</head>
<body>
<div id="wrapper">
<h2>수정</h2>
<form action="boardUpdate" method="POST" enctype="multipart/form-data">
<input type = "hidden" name = "boardnum" value = "${board.boardnum}" />
	<table border="1">
	<tr>
		<th>제목</th>
		<td><input type="text" name="title" value = "${board.title}"/></td>
	</tr>
	<tr>
		<th>글쓴이</th>
		<td>${custid}</td>
	</tr>
	<tr>
		<th>첨부파일</th>
		<td><input type="file" name="upload" />${board.originalfile}</td>
	</tr>
	<tr>
		<th>글내용</th>
		<td><textarea name="content" cols="60" rows="10">${board.content}</textarea></td>
	</tr>
	<tr>
		<td colspan="2">
			<input type="submit" value="수정완료" />
			<a href="boardDetail?boardnum=${board.boardnum}&currentPage=${currentPage}&searchtype=${searchtype}&searchword=${searchword}">수정 취소</a>
		</td>
	</tr>
	</table>
</form>
</div>
</body>
</html>
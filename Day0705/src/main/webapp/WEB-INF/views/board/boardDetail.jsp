<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
	div#wrapper table {
		margin:0 auto;
		width : 800px;
	}
	th {
		width: 100px;
	}
	pre {
		width : 600px;
		height : 200px;
		overflow: auto;
	}
	table.reply {
		width : 800px;
	}
	input[name='text'] {
		width : 600px;
	}
	span {
		display: inline-block;

		margin : 5px;
	}
	td.replycontent {
		width : auto;
	}
	td.replytext {
		width : 600px;
		text-align : left;
	}
	td.replysub {
		width : 80px;
		text-align : right;
	}
	td.replybtn{
		width : 100px;
		text-align : right;
	}
</style>
<script>
function replydelete(replynum) {
	var answer = confirm("댓글을 삭제하시겠습니까?");
	if(answer) {
		location.href="replyDelete?replynum="+replynum+"&boardnum="+"${board.boardnum}";
	}
	return;
}
function replymodify(replynum, text) {
	document.getElementById("reply_txt").value = text;
	document.getElementById("reply_submit").value = "댓글 수정";
	
	document.getElementById("reply_submit").onclick = function() {
		if(document.getElementById("reply_submit").value == "댓글 수정") {
			var updatetext = document.getElementById("reply_txt").value;
			location.href="replyUpdate?replynum="+replynum+"&boardnum="+"${board.boardnum}&text="+updatetext;
		}
	}

	return false;
}
function replywrite() {
	var writetext = document.getElementById("reply_txt").value;
	document.getElementById("replyWrite").submit();
}



</script>
</head>
<body>
<div id="wrapper">
<h2>[ 게시판 글보기 ]</h2>

<table border="1">
	<tr>
		<th>제목</th>
		<td>${board.title}</td>
	</tr>
	<tr>
		<th>글쓴날</th>
		<td>${board.inputdate}</td>
	</tr>	
	<tr>
		<th>글쓴이</th>
		<td>${board.custid}</td>
	</tr>
	<tr>
		<th>첨부파일</th>
		<td>
		<c:if test="${mime == 'image'}">
			<img src="download?boardnum=${board.boardnum}"/>
		</c:if>
		<c:if test="${board.originalfile != null}">
			<a href="download?boardnum=${board.boardnum}">${board.originalfile}</a>
		</c:if>
		</td>
	</tr>
	<tr>
		<th>글내용</th>
		<td>
			<pre>${board.content }</pre>
		</td>
	</tr>
	<tr>
		<td colspan="2">
			<c:if test="${custid == board.custid }">
			<input type="button" onclick="location.href='boardUpdateForm?boardnum=${board.boardnum}&currentPage=${currentPage}&searchtype=${searchtype}&searchword=${searchword}'" value="글수정" />
			<input type="button" onclick="location.href='boardDelete?boardnum=${board.boardnum}'" value="글삭제" />
			</c:if>
			<a href="boardList?currentPage=${currentPage}&searchtype=${searchtype}&searchword=${searchword}">목록으로</a>
		</td>
	</tr>
</table>
<br />
<!-- 댓글 입력 / 로그인한 사람만 댓글을 달 수 있다. -->
<c:if test="${not empty custid }">
	<form id="replyWrite" action="board" method="POST">
	<input type="hidden" name="action" value="replyWrite" />
	<input type="hidden" name="boardnum" value="${board.boardnum }" />
	<table id="replyinput" class="reply">
		<tr>
			<td>
			<input id="reply_txt" type="text" name="text" />
			<input id="reply_submit" type="button" value="댓글 입력" onclick="replywrite()" />
			</td>
		</tr>
	</table>
	</form>
</c:if>

<!-- 댓글 출력 -->
<div id="replydisplay">
<table class="reply">
	<c:forEach var="reply" items="${replyList}">
	<tr>
		<td class="replytext">
			${reply.text}
		</td>
		<td class="sub replyid">
			<span>${reply.custid} </span>
		</td>
		<td class="sub replydate">
			<span>${reply.inputdate}</span>
		</td>
		<c:if test="${custid == reply.custid }">
		<td class="replybtn">
			<input type="button" value="삭제" onclick="replydelete('${reply.replynum}')"/>
			<input type="button" value="수정" onclick="replymodify('${reply.replynum}', '${reply.text}')"/>
		</td>
		</c:if>
	</tr>
	</c:forEach>
</table>
</div><!-- end #replydisplay -->
</div> <!-- end #wrapper -->
</body>
</html>
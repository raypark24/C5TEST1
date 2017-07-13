<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
	span#msg {
		color : #ff0000;
	}
</style>
<script>


function saveCheck(){
	var saveValue = document.getElementById("savedid");
	if(saveValue.checked == true){
		saveValue.value = "checked";
	}
}
</script>
</head>
<body>
<div id="wrapper">
	<h2>[ 로그인 ]</h2>
	<form action="login" method="POST" onsubmit="saveCheck();">
	<input type="hidden" name="actionType" value="Login">
	<table border="1">
		<tr>
			<th>아이디</th>
			<td colspan="2">
				<input type="text" id="custid" name="custid" value="${cookie.savedid.value }" placeholder="3~10자리 아이디" />
				id저장<input type="checkbox" name="savedid" id="savedid" checked="checked" value="" >
			</td>	
		</tr>
		<tr>
			<th>비밀번호</th>
			<td>
				<input type="password" id="password" name="password" placeholder="3~10자리 비밀번호" />
			</td>	
		</tr>

		<tr>
			<td colspan="2">
				<input type="submit" id="send_btn" value="로그인" />
				<input type="reset" value="지우기" />
				<a href="index" >돌아가기</a>	
			</td>	
		</tr>				
		</table>
	</form>
</div>
</body>
</html>

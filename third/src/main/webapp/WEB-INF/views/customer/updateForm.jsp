<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String custid = (String)session.getAttribute("custid");
	String name = (String)session.getAttribute("name");
	String preEmail = (String)session.getAttribute("preEmail");
	String preIdno = (String)session.getAttribute("preIdno");
	String preAddress = (String)session.getAttribute("preAddress");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
	span#msg{
		color : #FF0000;
	}
</style>
<script>
//유효성 검사
	window.onload = function(){
		
		var custid = document.getElementById("custid");	// var은 객체들. ( 뽑을때는 .value)
		var password = document.getElementById("password");
		var password2 = document.getElementById("password2");
		var name2 = document.getElementById("name");
		var idno = document.getElementById("idno");
		
		password.focus();
		document.getElementById("send_btn").onclick = check;

	};
	
	function check(){
		if(custid.value.length < 3 || custid.value.length > 10){
			alert("아이디는 3~10자리");
			return false;
		}
		if(custid.value.charAt(0)<'a' || custid.value.charAt(0)>'z'){
			alert("아이디는 영문자 소문자여야 합니다.");
			return false;
		}
		if(password.value != (password2.value)){
			alert("패스워드와 패스워드 확인이 같지 않습니다.");
			return false;
		}
		if(name2.value == null){
			alert("이름을 입력하세요");
			return false;
		} 
		if(idno == null){
			alert("식별번호를 입력하세요.");
			return false;
		}
		if(isNaN(idno.value)){
			alert("식별번호는 숫자입니다.")
			return false;
		}
		return true;
	};

</script>

</head>
<body>
<div id="wrapper">
	<h2>[ 회원 정보 수정 ]</h2>
	<form action="update" method="POST" >
	<!-- id/중복확인/pw/name/email/division/idno/address -->
	<table border="1">
		<tr>
			<th>아이디</th>
			<td><input type="text" name="custid" id="custid" value=<%= custid %> readonly></td>
		</tr>
		<tr>
			<th>비밀번호</th>
			<td><input type="password" name="password" id="password" placeholder="3~10자리 비밀번호"></td>
		</tr>
		<tr>
			<th>비밀번호 확인</th>	
			<td><input type="password" name="password2" id="password2" placeholder="비밀번호 확인"></td>
		</tr>
		<tr>
			<th>이름</th>
			<td><input type="text" name="name" id="name" value=<%=name %>></td>
		</tr>
		<tr>
			<th>이메일</th>
			<td><input type="email" name="email" id="email" value=<%=preEmail %>></td>
		</tr>
		<tr>
			<th>회원 구분</th>
			<td><input type="radio" name="division" id="division" value="personal" checked/>개인회원 &nbsp;
			<input type="radio" name="division" id="division" value="company"/>기업회원 </td>
		</tr>
		<tr>
			<th>식별번호</th>
			<td><input type="text" name="idno" id="idno" value=<%=preIdno %>>
			<span id="msg">개인회원 : 주민번호 / 기업회원 : 사업자 등록번호</span></td>
		</tr>
		<tr>
			<th>주소</th>
			<td><input type="text" name="address" id="address" size = "50px" value=<%=preAddress %>></td>
		</tr>
		<tr>
			<td colspan="2">
			<input type="submit" id="send_btn" value="수정" />
			<input type="reset"value="지우기" />
			<a href = "index">메인 화면</a>
			</td>
		</tr>
	</table>
	</form>
</div>
</body>
</html>
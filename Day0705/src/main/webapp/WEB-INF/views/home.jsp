<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>Home</title>
</head>
<body>
<h1>
	Hello world!  
</h1>
<P>  The time on the server is ${serverTime}. </P>

<div>
	<h2>[ Form data 전송 ]</h2>
	<form action="input" method="post">
		<label>이름 : <input type="text" name="name"/></label>
		<label>나이 : <input type="text" name="age"/></label>
		<label>전화번호 : <input type="text" name="phone"/></label>
		<!-- <label>생일 : <input type="text" name="birth"/></label> -->
		<label><input type="submit" /></label>
	</form>
	
	<h2>수집된 데이터 </h2>
		<label>이름 : <input type="text" value="${friend.name }"/></label>
		<label>나이 : <input type="text" value="${friend.age }"/></label>
		<label>전화번호 : <input type="text" value="${friend.phone }"/></label>

	<h2>modelAttribute로 수집된 데이터</h2>
		<label>이름 : <input type="text" value="${friend.name }"/></label>
		<label>나이 : <input type="text" value="${friend.age }"/></label>
		<label>전화번호 : <input type="text" value="${friend.phone }"/></label>
		<%-- <label>생일 : <input type="text" value="${birth }"/></label> --%>
</div>

</body>
</html>

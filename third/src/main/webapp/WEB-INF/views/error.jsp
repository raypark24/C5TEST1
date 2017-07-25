<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta http-equiv="refresh" content="5,index"/> 
	<title>Insert title here</title>
<style>
	#wrapper{
		width : 700px;
		height : auto;
		text-align : center;
		margin : 0 auto;
	}
</style>
</head>
<body>
<div id="wrapper">
	<h2>[ 오 류 발 생 ]</h2>
	<img src="resources/clip.png">
	<p>5초 뒤에 첫 화면으로 이동합니다. 잠시 ㅎㅎ.</p>
	<p>오류 메시지 : ${errormsg}</p>
</div>
</body>
</html>
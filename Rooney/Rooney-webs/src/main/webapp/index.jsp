<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/jsp/Tablib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/jsp/Meta.jsp"%>
<link rel="stylesheet" type="text/css" href="commons/css/common.css">
<link rel="stylesheet" type="text/css" href="commons/css/login/login.css">
<title>hadoop集群监控系统</title>
</head>
<body>
	<div class="container">
		<form class="form-signin" action="Login.action?method=login" method="post">
			<h2 class="form-signin-heading">请先登录</h2>
			<label for="inputEmail" class="control-label">用户名</label> <input
				type="text" id="inputEmail" class="form-control"
				placeholder="User name" required autofocus> <label
				for="inputPassword" class="control-label">密码</label> <input
				type="password" id="inputPassword" class="form-control"
				placeholder="Password" required>
			<div class="checkbox">
				<label> <input type="checkbox" value="remember-me">
					Remember me
				</label>
			</div>
			<button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
			<button class="btn btn-lg btn-default btn-block" type="reset">重置</button>
		</form>
	</div>
</body>
<%@include file="/commons/jsp/Javascript.jsp"%>
</html>
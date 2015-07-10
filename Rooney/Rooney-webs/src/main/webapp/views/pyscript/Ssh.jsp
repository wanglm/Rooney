<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/jsp/Tablib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/jsp/Meta.jsp"%>
<link rel="stylesheet" type="text/css" href="commons/css/common.css">
<title>ssh</title>
</head>
<body>
	<%@include file="/views/main/Main_Navbar.jsp"%>
	<div class="container-fluid main-row-top">
		<div class="row row-offcanvas row-offcanvas-right">
			<div class="panel panel-default">
				<div class="panel-heading">
				</div>
				<div class="panel-body">
					<div class="table-responsive">
					<table class="table table-striped table-hover table-bordered">
							<thead>
								<tr>
									<th>序号</th>
									<th>name</th>
								</tr>
							</thead>
							<tbody>
							<c:forEach items="${list}" var="scs">
								<c:forEach items="${scs}" var="name" varStatus="vs">
									<tr>
										<td>${vs.index+1}</td>
										<td>${name}</td>
								</c:forEach>
							</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<%@include file="/commons/jsp/Javascript.jsp"%>
</html>
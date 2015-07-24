<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/jsp/Tablib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/jsp/Meta.jsp"%>
<link rel="stylesheet" type="text/css" href="commons/css/common.css">
<title>数据日志展示</title>
</head>
<body>
<button type="button" id="save">save</button>

</body>
<%@include file="/commons/jsp/Javascript.jsp"%>
<script type="text/javascript">
$('#save').on('click',function(){
	$.post('Log.action',{method:'convert',id:'1'},function(json){
		if(json.success){
			console.log(json.msg);
		}
	});
});
</script>
</html>
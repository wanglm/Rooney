<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/commons/jsp/Tablib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<%@include file="/commons/jsp/Meta.jsp"%>
<link rel="stylesheet" type="text/css" href="commons/css/common.css">
<link rel="stylesheet" type="text/css"
	href="commons/css/google-nexus-menu/css/normalize.css" />
<link rel="stylesheet" type="text/css"
	href="commons/css/google-nexus-menu/css/demo.css" />
<link rel="stylesheet" type="text/css"
	href="commons/css/google-nexus-menu/css/component.css" />
<title>系统主页</title>
</head>
<body>
	<div class="container">
		<ul id="gn-menu" class="gn-menu-main">
			<li class="gn-trigger"><a class="gn-icon gn-icon-menu"><span>Menu</span></a>
				<nav class="gn-menu-wrapper">
					<div class="gn-scroller">
						<ul class="gn-menu">
							<li class="gn-search-item"><input placeholder="Search"
								type="search" class="gn-search"> <a
								class="gn-icon gn-icon-search"><span>Search</span></a></li>
							<li><a class="gn-icon gn-icon-download">Downloads</a>
								<ul class="gn-submenu">
									<li><a class="gn-icon gn-icon-illustrator">Vector
											Illustrations</a></li>
									<li><a class="gn-icon gn-icon-photoshop">Photoshop
											files</a></li>
								</ul></li>
							<li><a class="gn-icon gn-icon-cog">Settings</a></li>
							<li><a class="gn-icon gn-icon-help">Help</a></li>
							<li><a class="gn-icon gn-icon-archive">Archives</a>
								<ul class="gn-submenu">
									<li><a class="gn-icon gn-icon-article">Articles</a></li>
									<li><a class="gn-icon gn-icon-pictures">Images</a></li>
									<li><a class="gn-icon gn-icon-videos">Videos</a></li>
								</ul></li>
						</ul>
					</div>
					<!-- /gn-scroller -->
				</nav></li>
			<li>Codrops</li>
			<li>Previous Demo</li>
			<li></li>
		</ul>
	</div>
</body>
<%@include file="/commons/jsp/Javascript.jsp"%>
<script>
	new gnMenu(document.getElementById('gn-menu'));
</script>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<div class="panel-group main-collapse-left" id="accordion"
	role="tablist" aria-multiselectable="true">
	<div class="panel panel-default">
		<div class="panel-heading" role="tab" id="hdfs">
			<h4 class="panel-title"> <a class="btn" data-toggle="collapse"
				data-parent="#accordion" href="#collapseOne" aria-expanded="true"
				aria-controls="collapseOne">Hadoop<span class="badge">14</span></a>
			</h4>
		</div>
		<div id="collapseOne" class="panel-collapse collapse in"
			role="tabpanel" aria-labelledby="hdfs">
			<ul class="list-group">
				<li class="list-group-item"><a href="Menu.action?method=list">hdfs</a><span
					class="badge">14</span></li>
				<li class="list-group-item"><a href="Menu.action?method=list">yarn</a><span
					class="badge">14</span></li>
				<li class="list-group-item"><a href="Menu.action?method=list">mapreduce</a><span
					class="badge">14</span></li>
				<li class="list-group-item"><a href="Menu.action?method=list">hive</a><span
					class="badge">14</span></li>
				<li class="list-group-item"><a href="Menu.action?method=list">hbase</a><span
					class="badge">14</span></li>
			</ul>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading" role="tab" id="quartz">
			<h4 class="panel-title">
				<a class="btn collapsed" data-toggle="collapse"
					data-parent="#accordion" href="#collapseTwo"
					aria-expanded="false" aria-controls="collapseTwo"> Java定时任务<span
					class="badge">14</span></a>
			</h4>
		</div>
		<div id="collapseTwo" class="panel-collapse collapse"
			role="tabpanel" aria-labelledby="quartz">
			<ul class="list-group">
				<li class="list-group-item"><a href="Menu.action?method=list">MR任务</a><span
					class="badge">14</span></li>
				<li class="list-group-item"><a href="PyScript.action?method=show">hive任务</a><span
					class="badge">14</span></li>
			</ul>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading" role="tab" id="log">
			<h4 class="panel-title">
				<a class="btn collapsed" data-toggle="collapse"
					data-parent="#accordion" href="#collapseThree" aria-expanded="false"
					aria-controls="collapseThree"> 日志<span class="badge">14</span></a>
			</h4>
		</div>
		<div id="collapseThree" class="panel-collapse collapse" role="tabpanel"
			aria-labelledby="log">
			<ul class="list-group">
				<li class="list-group-item"><a href="Menu.action?method=list">MR日志</a><span
					class="badge">14</span></li>
				<li class="list-group-item"><a href="Menu.action?method=list">hive日志</a><span
					class="badge">14</span></li>
				<li class="list-group-item"><a href="Menu.action?method=list">用户日志</a><span
					class="badge">14</span></li>
				<li class="list-group-item"><a href="Menu.action?method=list">运行日志</a><span
					class="badge">14</span></li>
			</ul>
		</div>
	</div>
	<div class="panel panel-default">
		<div class="panel-heading" role="tab" id="user">
			<h4 class="panel-title">
				<a class="btn collapsed" data-toggle="collapse"
					data-parent="#accordion" href="#collapseFourth" aria-expanded="false"
					aria-controls="collapseFourth"> 系统<span class="badge">14</span></a>
			</h4>
		</div>
		<div id="collapseFourth" class="panel-collapse collapse" role="tabpanel"
			aria-labelledby="user">
			<ul class="list-group">
				<li class="list-group-item"><a href="Menu.action?method=list">菜单管理</a><span
					class="badge">14</span></li>
				<li class="list-group-item"><span
					class="glyphicon glyphicon-leaf"></span><span class="badge">14</span></li>
				<li class="list-group-item"><span
					class="glyphicon glyphicon-plane"></span><span class="badge">14</span></li>
				<li class="list-group-item"><span
					class="glyphicon glyphicon-phone-alt"></span><span class="badge">14</span></li>
				<li class="list-group-item"><span
					class="glyphicon glyphicon-tree-conifer"></span><span class="badge">14</span></li>
			</ul>
		</div>
	</div>
</div>
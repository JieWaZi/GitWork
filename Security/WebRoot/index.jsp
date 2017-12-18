<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix='sec' uri='http://www.springframework.org/security/tags' %>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>SECSC</title>

<link href="<c:url value="/img/default/favicon.ico"></c:url>" rel="shortcut icon">
<link rel="stylesheet"
	href="<c:url value="/css/bootstrap.min.css"></c:url>" />
<link rel="stylesheet"
	href="<c:url value="/css/bootstrap-theme.min.css" ></c:url>" />
<link rel="stylesheet"
	href="<c:url value="/css/customizedCSS/dashboard.css"></c:url>" />
<link rel="stylesheet"
	href="<c:url value="/css/customizedCSS/customizedTheme.css"></c:url>" />
<script type="text/javascript"
	src="<c:url value="/js/jquery-3.1.1.min.js"></c:url>"></script>
<script type="text/javascript"
	src="<c:url value="/js/jquery.ba-hashchange.js"></c:url>"></script>
<script type="text/javascript"
	src="<c:url value="/js/bootstrap.min.js"></c:url>"></script>
<script type="text/javascript"
	src="<c:url value="/js/echarts.js"></c:url>"></script>
<script type="text/javascript"
	src="<c:url value="/js/macarons.js"></c:url>"></script>
<script type="text/javascript"
	src="<c:url value="/js/Math.uuid.js"></c:url>"></script>
<script type="text/javascript"
	src="<c:url value="/js/utils.js"></c:url>"></script>
<script type="text/javascript"
	src="<c:url value="/js/index.js"></c:url>"></script>
</head>

<body style="padding-top: 10em;background-color:#333;" data-spy="scroll"
	data-target="#sideBar" data-offset="140">
	<div id="modalDiaglog" class="modal fade bs-example-modal-lg"
		tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-lg" role="document">
			<div class="modal-content">
				<button type="button" style="margin-top: 1em;margin-right: 1em;"
					class="btn btn-danger pull-right" data-dismiss="modal">X</button>
				<div class="modal-header"
					style="font-size: x-large;font-weight: bold;">加载中...</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-sm-12"
							style="text-align: center;font-size: xx-large;">加载中...</div>
					</div>
				</div>
				<div class="modal-footer" hidden="hidden"></div>
			</div>
		</div>
	</div>


	<nav class="nav navbar-default navbar-fixed-top navbar-inverse"
		style="height: 15%;background-image: linear-gradient(to bottom,#3c3c3c 0,#333 100%);border-bottom: rgba(255, 255, 255, 0.15);">
		<div class="container">
			<div class="navbar-header" style="margin-left: -2em;">
				<a href="#" type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar"> <span class="sr-only">Toggle
						navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a> <a href="" class="navbar-brand"> <img alt="brand"
					src="<c:url value="/img/default/BrandLOGO2.png"></c:url>"
					style="height: 420%;" />
				</a>
				<p class="navbar-text" style="margin-top: 2em;color: white;">

					<span style="font-size: 2.5em;"><ruby>
							上海市节能监察中心
							<rt>Shanghai Energy Conservation Supervision Center</rt>
				</ruby>
				</span>
				</p>
			</div>
			<div id="navbar" class="navbar-collapse collapse ">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<div id="loginPanelBtn" class="dropdown-toggle"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<img src="<c:url value="/img/default/UserLOGO.png" ></c:url>"
								class="img-circle" style="width: 5em;" />
							<p
								style="text-align: center;font-size: 0.8em;font-weight: bold; color: #EB9316;">
								未登录</p>
						</div>
						<ul class="dropdown-menu" role="loginDpMenu"
							aria-labelledby="dropdownMenuDivider"
							style="background-color: #3C3C3C;">
							<li class="dropdown-header">用户登录</li>
							<li>

								<form id="loginForm" class="navbar-form">
									<div id="loginStatus">
										<div class="alert alert-warning alert-dismissible"
											role="alert">
											<a type="button" class="close" data-dismiss="alert"
												aria-label="Close"><span aria-hidden="true">&times;</span></a>
											状态：未登录
										</div>
									</div>

									<div class="form-group">
										<label for="inputUsername" class="sr-only">在此输入用户名</label> <input
											type="text" id="username" class="form-control"
											placeholder="Username" required autofocus> <label
											for="inputPassword" class="sr-only">在此输入口令</label> <input
											type="password" id="password" class="form-control"
											placeholder="Password" required>
									</div>
									<input type="submit" class="btn btn-default" value="登录"></input>
									<input type="reset" class="btn btn-default" value="重置"></input>
									<a href="#" class="navbar-link">忘记密码</a>

								</form>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>


	<div id="outline" class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h1 class="page-header">
			<span style="color:white;">总览</span> <a tabindex="0"
				class="bs-docs-popover" role="button" data-toggle="popover"
				data-trigger="focus" data-content="Optional tips box"> <span
				class="glyphicon glyphicon-question-sign" aria-hidden="true"></span>
			</a>
		</h1>
		<div id="outlineContent" role="content-panel" class="row" style="color:white;"></div>
	</div>
	
	<sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')">
	<div id="ele-heat" class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h1 class="page-header">
			<span style="color:white;">电力、热力生产和供应业</span> <a tabindex="0"
				class="bs-docs-popover" role="button" data-toggle="popover"
				data-trigger="focus" data-content="Optional tips box"> <span
				class="glyphicon glyphicon-question-sign" aria-hidden="true"></span>
			</a>
		</h1>
		<div id="ele-heatContent" role="content-panel" class="row" style="color:white;"></div>
	</div>
	</sec:authorize>

	<div class="container-fluid">
		<div class="row">
			<div id="sideBar" class="col-sm-3 col-md-2 sidebar"
				style="margin-top:4.6em;background-color: #333;border-right-color: rgba(255, 255, 255, 0.15);">
				<ul class="nav nav-sidebar" role="tablist" style="margin-top: 1em;">
					<li class="active"><a role="sideNav" href="#outline" >总览</a><span class="sr-only"></span></li>
				    <sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')"><li><a role="sideNav" href="#ele-heat">电力、热力生产和供应业</a></li></sec:authorize>
				</ul>
			</div>
		<div>
	</div>

	</div>
</body>

</html>
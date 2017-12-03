<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
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
<script type="text/javascript"
	src="<c:url value="/js/business.js"></c:url>"></script>
</head>

<body style="padding-top: 10em;background-color:#333;" data-spy="scroll"
	data-target="#sideBar1" data-offset="140">
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
							style="text-align: center;font-size: xx-large;"></div>
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
				</a> <a href="index" class="navbar-brand"> <img alt="brand"
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
	
	
	<!-- 上传模板 -->
	<div id="upload" class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h1 class="page-header">
			<span style="color:white;"> 年报上传</span> 
			<a tabindex="0" class="bs-docs-popover" role="button" data-toggle="popover"
				data-trigger="focus" data-content="Optional tips box"> 
				<span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span>
			</a>
		</h1>
		<div id="uploadContent" role="content-panel" class="row" style="color:white;">
			<div class="col-sm-12">
				<a href="#" role="customeLauncher" class="btn btn-primary pull-right" data-toggle="modal"
					data-target="#modalDiaglog">新建 ＋ </a>
				<h3 style="margin-top:0.2em;">上传历史记录：</h3>
				<table id="uploadHistory" class="table" class="table">
					<thead>
						<tr>
							<td>上传日期</td>
							<td>年份</td>
							<td>文件名</td>
						</tr>
						<tr>
					</thead>
					<tbody>
						<tr class="info">
							<td colspan="999" style="text-align:center;font-weight:bold;color:#333;">加载中...</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>



	<!-- 数据预处理模板 -->
	<div id="dataPre" class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h1 class="page-header">
			<span style="color:white;"> 数据预处理</span> 
			<a tabindex="0" class="bs-docs-popover" role="button" data-toggle="popover"
				data-trigger="focus" data-content="Optional tips box"> 
				<span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span>
			</a>
		</h1>
		<div id="dataPreContent" role="content-panel" class="row" style="color:white;">
			<div class="col-sm-12">
				<a href="#" role="customeLauncher" class="btn btn-primary pull-right" data-toggle="modal" data-target="#modalDiaglog">新建 ＋ </a>
				<h3 style="margin-top:0.2em;">预处理记录：</h3>
				<table id="dataPreHistory" class="table table-hover" class="table">
					<thead>
						<tr>
							<td>任务执行日期</td>
							<td>数据源</td>
							<td>处理方法</td>
							<td>所属用户</td>
							<td>状态</td>
						</tr>
					</thead>
					<tbody style="color:#333">
						<tr class="info">
							<td colspan="999" style="text-align:center;font-weight:bold;color:#333;">加载中...</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	
	
	<!-- 数据分析模板 -->
	<div id="dataAna" class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h1 class="page-header">
			<span style="color:white;"> 数据分析</span> 
			<a tabindex="0" class="bs-docs-popover" role="button" data-toggle="popover"
				data-trigger="focus" data-content="Optional tips box"> 
				<span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span>
			</a>
		</h1>
		<div id="dataAnaContent" role="content-panel" class="row" style="color:white;">
		<div class="col-sm-12">
			<a href="#" role="customeLauncher" class="btn btn-primary pull-right" data-toggle="modal" data-target="#modalDiaglog">新建 ＋ </a>
			<h3 style="margin-top:0.2em;">历史数据分析记录：</h3>
			<table id="dataAnaHistory" class="table table-hover" class="table">
				<thead>
					<tr>
						<td>分析类型</td>
						<td>分析算法</td>
						<td>创建时间</td>
						<td>所属行业</td>
						<td>创建者</td>
					</tr>
				</thead>
				<tbody>
					<tr class="info">
						<td colspan="999" style="text-align:center;font-weight:bold;color:#333;">加载中...</td>
					</tr>
				</tbody>
			</table>
		</div>
		</div>
	</div>



	<!-- 数据分析报告模板 -->
	<div id="dataRep" class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<h1 class="page-header">
			<span style="color:white;"> 数据分析报告</span> 
			<a tabindex="0" class="bs-docs-popover" role="button" data-toggle="popover"
				data-trigger="focus" data-content="Optional tips box"> 
				<span class="glyphicon glyphicon-question-sign" aria-hidden="true"></span>
			</a>
		</h1>
		<div id="dataRepContent" role="content-panel" class="row" style="color:white;">
		<div class="col-sm-12">
			<a href="#" role="customeLauncher" class="btn btn-primary pull-right" data-toggle="modal" data-target="#modalDiaglog">新建 ＋ </a>
			<h3 style="margin-top:0.2em;">已经生成的报告：</h3>
			<table id="dataRepHistory" class="table table-hover" class="table">
				<thead>
					<tr>
						<td>报告标题</td>
						<td>创建时间</td>
						<td>所属行业</td>
						<td>创建者</td>
					</tr>
				</thead>
				<tbody>
					<tr class="info">
						<td colspan="999" style="text-align:center;font-weight:bold;color:#333;">加载中...</td>
					</tr>
				</tbody>
			</table>
		</div>
		</div>
	</div>




	<!-- 左侧导航啦 -->
	<div class="container-fluid">
		<div class="row">
			<div id="sideBar1" class="col-sm-3 col-md-2 sidebar"
				style="margin-top:4.6em;background-color: #333;border-right-color: rgba(255, 255, 255, 0.15);">
				<ul class="nav nav-sidebar" role="tablist" style="margin-top: 1em;">
					<li class="active"><a role="sideNav" href="#upload" >年报上传</a><span class="sr-only"></span></li>
					<li><a role="sideNav" href="#dataPre">数据预处理</a></li>
					<li><a role="sideNav" href="#dataAna">数据分析</a></li>
					<li><a role="sideNav" href="#dataRep">数据数据分析报告</a></li>
				</ul>
			</div>
		<div>
	</div>

	</div>
</body>

</html>
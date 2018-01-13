<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
	src="<c:url value="/js/manage.js"></c:url>"></script>
<script type="text/javascript"
	src="<c:url value="/js/bootstrap-paginator.js"></c:url>"></script>
<script type="text/javascript">
	$(function(){
		var element = $('#myPage');
		var user
        var options = {
            bootstrapMajorVersion:3,//如果是bootstrap3版本需要加此标识，并且设置包含分页内容的DOM元素为UL,如果是bootstrap2版本，则DOM包含元素是DIV
            currentPage: ${page.currPage},    //设置当前页，默认起始页为第一页
            numberOfPages: "5",         //设置控件显示的页码数,跟后台计算出来的总页数没多大关系
            totalPages:${page.totalPage},      //总页数
            /*  useBootstrapTooltip:'true',    //是否显示tip提示框 */
            onPageClicked: function (event, originalEvent, type, page) {
                   $.ajax({
                         url:'pageAccount?count='+page, //点击分页提交当前页码
                          success:function(data){
                               if(data!=null){
                            	$("#accountHistory").children("tbody").empty();
                    			var currPage=data.currPage
                    			var totalPage=data.totalPage
                    			var datalength=data.data.length
                    			var length=0
                    			if(datalength <= 0) {
                    				displayTableEle.children("tbody").html("<tr class='info'><td colspan='999' style='text-align:center;font-weight:bold;color:#333'>没有上传记录</td></tr>");
                    			}
                    			else if(datalength<=5){
                    				length=data.data.length
                    			}else if(totalPage == currPage){
                    				length=data.data.length%5
                    			}else{
                    				length=5
                    			}

                    			
                    			$.each(data.data, function(i, item) {
                    				$("#accountHistory").children("tbody")
                    					.append("<tr>" +
                    						"<td>" + (data.data)[i+(currPage-1)*5].username+"</td>" +
                    						"<td>" + (data.data)[i+(currPage-1)*5].password + "</td>" +
                    						"<td>" + (data.data)[i+(currPage-1)*5].accountNonLocked + "</td>" +
                    						"<td>"+
                    						"<button type='button' class=' btn btn-success btn-xs' aria-label='Left Align'>"+
                    							"编辑&nbsp;&nbsp<span class='glyphicon glyphicon-cog' aria-hidden='true'></span>"+
                    						"</button>"+
                    						"<button style='margin-left:20px' name='nameDelete' type='button' class='btn btn-info btn-xs' aria-label='Left Align'>"+
                								"解锁&nbsp;&nbsp<span class='glyphicon glyphicon-trash' aria-hidden='true'></span>"+
                							"</button>"+
                    						"<button style='margin-left:20px' type='button' class='btn btn-danger btn-xs' aria-label='Left Align'>"+
                    							"删除&nbsp;&nbsp<span class='glyphicon glyphicon-trash' aria-hidden='true'></span>"+
                    						"</button>"+
                    						"</td></tr>");
                    				if(i>=length-1) return false
                    			});
                               }
                           }
                       })
                    },
            itemTexts: function (type, page, current) {
                switch (type) {
                    case "first":
                        return "首页";
                    case "prev":
                        return "上一页";
                    case "next":
                        return "下一页";
                    case "last":
                        return "末页";
                    case "page":
                        return page;
                }
            }
        }
        element.bootstrapPaginator(options);	
			
		
		
	})
	
</script>
</head>

<body style="padding-top: 10em;background-color:#FFFFF;" data-spy="scroll"
	data-target="#sideBar2" data-offset="140">
	<div id="modalDiaglog" class="modal fade bs-example-modal-md"
		tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
		<div class="modal-dialog modal-md" role="document">
			<div class="modal-content">
				<button type="button" style="margin-top: 1em;margin-right: 1em;"
					class="btn btn-danger pull-right" data-dismiss="modal">X</button>
				<div class="modal-header"
					style="font-size: x-large;font-weight: bold;">加载中...</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-sm-12"
							style="text-align: center;font-size: xx-large;">
						</div>
					</div>
				</div>
				<div class="modal-footer" hidden="hidden"></div>
			</div>
		</div>
	</div>


	<nav class="nav navbar-default navbar-fixed-top navbar-inverse"
		style="height: 9%;background-image: linear-gradient(to bottom,#f3e6e6 0,#568cb9 80%);border-bottom: rgba(255, 255, 255, 0.15);">
		<div class="container">
			<div class="navbar-header" style="margin-left: -10em;">
				<a href="#" type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar"> <span class="sr-only">Toggle
						navigation</span> <span class="icon-bar"></span> <span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</a> 
				<p class="navbar-text" style="margin-top: 1em;color:black">
					<span style="font-size: 2.5em;">后台管理</span>
				</p>
			</div>
			<div id="navbar" class="navbar-collapse collapse ">
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						<div id="loginPanelBtn" class="dropdown-toggle"
							data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
							<img src="<c:url value="/img/default/UserLOGO.png" ></c:url>"
								class="img-circle" style="width: 3em;" />
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
	

		 <div class="row">
		 <div class="col-md-1 col-sm-offset-2   main"><span style="font-size: 124%;line-height: 2;" class="label label-default">输入需要查询的ID号:</span></div>
		  <div class="col-md-3 col-sm-offset-1 main">
		    <div class="input-group">
		      <input type="text" name="username" class="form-control">
		      <span class="input-group-btn">
		        <button id="searchUser" class="btn btn-primary" type="button">查询</button>
		      </span>
		    </div><!-- /input-group -->
		  </div><!-- /.col-lg-6 -->
		  <div class="col-md-1  main" >
				<a href="#" role="newaccount" class="btn btn-primary pull-right" data-toggle="modal"
					data-target="#modalDiaglog">新建账号＋ </a>
			</div>
		</div><!-- /.row -->
		
					
		
		<div id="accountContent" role="content-panel" class="row" >
			<div class="col-sm-offset-2 col-md-9  main">
				<h3 style="margin-top:0.2em;">账户信息：</h3>
				<table id="accountHistory" class="table table-hover" >
					<thead>
						<tr>
							<td>用户名</td>
							<td>密码</td>
							<td>是否正常（未被锁）</td>
							<td>操作</td>
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
		
	<div class="col-md-12" style="text-align:center">	<ul id="myPage"></ul> </div>
	</div>




	<!-- 左侧导航啦 -->
	<div class="container-fluid">
		<div class="row">
			<div id="sideBar1" class="col-sm-3 col-md-2 sidebar"
				style="margin-top:1.6em;background-color:#e9ecf3;border-right-color: rgba(255, 255, 255, 0.15);">
				<ul class="nav nav-sidebar" role="tablist" style="margin-top: 1em;">
					<li ><a role="sideNav" href="algorithmManage" style="width: 93%;margin-left: 4%;">算法管理</a><span class="sr-only"></span></li>
					<li class="active"><a role="sideNav" href="accountManage" style="width: 93%;margin-left: 4%; margin-top:25px">账号管理</a></li>
				</ul>
			</div>
		<div>
	</div>

	</div>
	
	
</body>

</html>
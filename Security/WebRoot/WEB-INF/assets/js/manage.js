$(function(){
	
	$('body').scrollspy({
		target: '#sideBar2'
	});
	
	$("body").bind("DOMNodeInserted", function() {
		$('[data-spy="scroll"]').each(function() {
			var $spy = $(this).scrollspy('refresh');
		});
	});
	$("body").bind("DOMNodeRemoved", function() {
		$('[data-spy="scroll"]').each(function() {
			var $spy = $(this).scrollspy('refresh');
		});
	});
	
	//控制导航点击后样式
	$("#sideBar2 li").click(function(){
		$(".active").removeClass("active")
		$(this).addClass("active")
	})
	
	
	
	//检查登陆状态
	ajaxLoginStatus();
	
	function ajaxLoginStatus() {
		$.ajax({
			url: 'login/status',
			type: 'GET',
			async: true,
			dataType: 'json',
			success: function(data) {
				loginRequestOK(data);
			},
			error: function(data) {
				ajaxError(data);
			}
		});
	}
	
	//进行登陆检测
	function loginRequestOK(loginInfo) {
		var statusDiv = $("#loginStatus");
		statusDiv.siblings("input[type='submit']").removeAttr("disabled");
		statusDiv.siblings("input[type='submit']").val("登录");
		statusDiv.empty();
		if(loginInfo.status == "true") {
			loginSuccessed(statusDiv, loginInfo);
		}
	}
	

	//登陆成功更新下拉
	function loginSuccessed(statusDiv,loginInfo) {
		$("#loginPanelBtn").children("p").text("已登录");
		$("#loginPanelBtn").children("p").css("color", "lightgreen");
		$("ul[role='loginDpMenu']").replaceWith(loginDropdownMenuTemplet(loginInfo));
		
		// 将更新后的菜单添加相关监听
		$("a[role='switchPanelBtn']").click(function() {
			if($(this).parent("li").hasClass("active")) {
				//a->li->ul->li.dropdown
				$("#loginPanelBtn").dropdown("toggle");
				return false;
			} else {
				$(this).parent("li").siblings().removeClass("active");
				$(this).parent("li").addClass("active");
				return true;
			}
		});
	}



	
	//登陆信息提示模板
	function loginInfoTemplet(info, type) {
		var templet =
			"<div class='alert alert-" + type + " alert-dismissible' role='alert'>" +
			"<a class='btn close' data-dismiss='alert' aria-label='Close' style='margin-top:-0.2em;'><span aria-hidden='true '>&times;</span></a>" +
			info +
			"</div>";
		return templet;
	}

	
	//登陆以后更新下拉菜单内容
	function loginDropdownMenuTemplet(loginInfo) {
		
		var templet =
			'<ul class="dropdown-menu" role="loginDpMenu" style="background-color:#eee;" aria-labelledby="dropdownMenuDivider">' +
			'	<li class="dropdown-header">用户信息</li>' +
			'	<li>' +
			'		<div style="margin-left:1.3em;margin-top:1em;font-weight:bold;color:#000">' +
			'			<p>用户名:	' + loginInfo.user + '</p>' +
			'			<p>身份:	' + loginInfo.authorities + '</p>' +
			'		</div>' +
			'	</li>' +
			'	<li role="separator" class="divider"></li>' +
			'	<li ><a href="index" role="switchPanelBtn" style="color:#337ab7;font-weight:bold;">返回首页</a></li>' +
			'	<li role="separator" class="divider"></li>' +
			'	<li><a href="logout" cus-role="logoutBtn" style="color:#337ab7;font-weight:bold;">注销登录</a></li>' +
			'</ul>';
		return templet;
	}
	
	
	//查询历史信息
		ajaxQueryUploadJars(function(data) {
			uploaddata=data
			displayUploadRecords(data, $("#arithmeticHistory"));
		});
		
		ajaxQueryAccounts(function(data) {
			uploaddata=data
			displayAccountRecords(data, $("#accountHistory"));
		});
		
		
		
		
		
		//注册a标签监听，在选中后更改相关信息
		regListBtn($("#uploadForm1").find("a[role='listBtn1']").parents(".input-group"),1, function() {});
		regListBtn($("#uploadForm1").find("a[role='listBtn2']").parents(".input-group"),2, function() {});
		regListBtn($("#uploadForm1").find("a[role='listBtn3']").parents(".input-group"),3, function() {});
		regListBtn($("#uploadForm1").find("a[role='listBtn4']").parents(".input-group"),4, function() {});
		regListBtn($("#uploadForm1").find("a[role='listBtn5']").parents(".input-group"),5, function() {});
		//对选中文件进行监听，修改相关提示
		$("#uploadForm1").children("div").children("input[type='file']").on("change", function() {
			var path = $(this).val();
			if(checkFileExt(path, ['jar'])) {
				$("#arithmeticContent").find("[role='uploadTip']").removeClass("alert-success alert-danger").addClass("alert-info");
				$("#arithmeticContent").find("[role='uploadTip']").html("提示：待上传");
				$("#arithmeticContent").find("input[type='submit']").removeAttr("disabled");
			} else {
				$("#arithmeticContent").find("[role='uploadTip']").removeClass("alert-info alert-success").addClass("alert-danger");
				$("#arithmeticContent").find("[role='uploadTip']").html("<span style='color:red;font-weight:bold;'>提示：文件类型错误</span>");
				$("#arithmeticContent").find("input[type='submit']").attr("disabled", "disabled");
				}
			});
			// TODO 点击提交是修改提示状态，进行提交
		$("#uploadForm1").submit(function() {
			$("#arithmeticContent").find("[role='uploadTip']").removeClass("alert-success alert-danger").addClass("alert-info");
			$("#arithmeticContent").find("[role='uploadTip']").html("提示：上传中...");
			$("#arithmeticContent").find("[role='uploadTip']").append(getLoadingAnimation("20px", "20px"));
			$("#arithmeticContent").find("[role='uploadTip']").append("<br /><br /><br /><br /><br /><br />");
				ajaxUpload($(this),function(data) {
					uploaddata=data
					$("#arithmeticContent").find("[role='uploadTip']").removeClass("alert-info alert-danger").addClass("alert-success");
					$("#arithmeticContent").find("[role='uploadTip']").html("<span style='color:green;font-weight:bold;'>提示：上传完成</span>");
					ajaxQueryUploadJars(function(data) {
						displayUploadRecords(data, $("#arithmeticHistory"));
					});
				});
				return false;
			});
			



		function displayUploadRecords(data, displayTableEle) {
			displayTableEle.children("tbody").empty();
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
				displayTableEle.children("tbody")
					.append("<tr>" +
						"<td>" + (data.data)[i+(currPage-1)*5].analysisType+"</td>" +
						"<td>" + (data.data)[i+(currPage-1)*5].arithmetic + "</td>" +
						"<td>" + (data.data)[i+(currPage-1)*5].jarName + "</td>" +
						"<td>"+
/*						"<button type='button' class=' btn btn-success btn-xs' aria-label='Left Align'>"+
							"更新&nbsp;&nbsp<span class='glyphicon glyphicon-cog' aria-hidden='true'></span>"+
						"</button>"+*/
						"<button style='margin-left:20px' name='jardelete' type='button' class='btn btn-danger btn-xs' aria-label='Left Align'>"+
							"删除&nbsp;&nbsp<span class='glyphicon glyphicon-trash' aria-hidden='true'></span>"+
						"</button>"+
						"</td></tr>");
				if(i>=length-1) return false
			});
			
			//删除
			$("button[name='jardelete']").click(function(){
				var arithmetic=$(this).parent().prev().prev().html()
				 if (confirm("你确定将其删除吗？")) {  
					 $.ajax({
							url: 'upload/deletejars',
							type: 'POST',
							async: true,
							data:{"arithmetic":arithmetic},
							dataType: 'json',
							success: function(returndata) {
								alert("该算法已删除")
								window.location.reload()
							},
							error: function(returndata) {
							}
						}) 
			        }  
			        else {   
			        }  
			})
		}
		
		
		//显示用户信息
		function displayAccountRecords(data, displayTableEle) {
			displayTableEle.children("tbody").empty();
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
				displayTableEle.children("tbody")
					.append("<tr>" +
						"<td>" + (data.data)[i+(currPage-1)*5].username+"</td>" +
						"<td>" + (data.data)[i+(currPage-1)*5].password + "</td>" +
						"<td>" + (data.data)[i+(currPage-1)*5].accountNonLocked + "</td>" +
						"<td>"+
						"<button type='button' name='nameModify' class=' btn btn-success btn-xs' aria-label='Left Align'>"+
							"编辑&nbsp;&nbsp<span class='glyphicon glyphicon-cog' aria-hidden='true'></span>"+
						"</button>"+
						"<button style='margin-left:20px' name='unlock' type='button' class='btn btn-info btn-xs' aria-label='Left Align'>"+
							"解锁&nbsp;&nbsp<span class='glyphicon glyphicon-trash' aria-hidden='true'></span>"+
						"</button>"+
						"<button style='margin-left:20px' name='nameDelete' type='button' class='btn btn-danger btn-xs' aria-label='Left Align'>"+
							"删除&nbsp;&nbsp<span class='glyphicon glyphicon-trash' aria-hidden='true'></span>"+
						"</button>"+
						"</td></tr>");
				if(i>=length-1) return false
			});
			
			//更新用户
			$("button[name='nameModify']").click(function(){
				var name=$(this).parent().prev().prev().prev().html()
				var modal = $('#modalDiaglog');
				var title = modal.find('.modal-header');
				var body = modal.find('.modal-body');
				var footer = modal.find('.modal-footer');
				title.text("修改用户密码");
				body.children().remove()
				footer.children().remove()
				var template=
				'<div class="row">'+
				'	<div class="col-sm-2" style="margin-top:20px"></div>'+
				'	<div class="col-sm-8" style="margin-top:20px">'+
				'		<div class="input-group">'+
				'  			<span class="input-group-addon" id="basic-addon1">用户名</span>'+
				'  			<input type="text" readonly name="name" class="form-control" value="'+name+'"/>'+
				'		</div>'+
				'	</div>'+
				'</div>'+
				'<div class="row">'+
				'	<div class="col-sm-2" style="margin-top:20px"></div>'+
				'	<div class="col-sm-8" style="margin-top:40px">'+
				'		<div class="input-group">'+
				'  			<span class="input-group-addon" id="basic-addon1">新密码：</span>'+
				'  			<input type="text" name="password" class="form-control" />'+
				'		</div>'+
				'	</div>'+
				'</div>'+
				'<div class="row">'+
				'	<div class="col-sm-2" style="margin-top:20px"></div>'+
				'	<div class="col-sm-8" style="margin-top:40px">'+
				'		<div class="input-group">'+
				'  			<span class="input-group-addon" id="basic-addon1">确认密码：</span>'+
				'  			<input type="text"  name="repassword" class="form-control" />'+
				'		</div>'+
				'	</div>'+
				'	<div class="col-sm-1" style="margin-top:20px">'+
				'			<h3><span style="margin-left:-27px"  class="label label-danger" id="wrong"></span></h3></div>'+
				'</div>'+
				'<div class="row">'+
				'	<div class="col-sm-2 col-sm-offset-5" style="margin-top:20px">'+
				'			<h3><span style="margin-left:-27px"  class="label label-success" id="success"></span></h3>'+
				'	</div>'+
				'</div>'+
				'<div class="row">'+
				'	<div class="col-sm-2 col-sm-offset-5" style="margin-top:20px">'+
				'		<input type="button" id="modiryname" class="btn btn-primary" value="确定修改"/>'+
				'	</div>'+
				'</div>'
				body.append(template)
				modal.modal('show')
				
				$("#modiryname").click(function(){
					var name=$("input[name='name']").val()
					var password=$("input[name='password']").val()
					var repassword=$("input[name='repassword']").val()
					if(password==repassword){
						$.ajax({
							url: 'login/modify',
							type: 'PUT',
							async: true,
							data:{
								"username":name,
								"password":password
							},
							dataType: 'json',
							success: function(returndata) {
								$("#success").html("修改成功")
							},
							error: function(returndata) {
							}
						})
					}else{
						$("#wrong").html("密码不正确")
					}

				})
				
			})
			
			
			//解锁
			$("button[name='unlock']").click(function(){
				var name=$(this).parent().prev().prev().prev().html()
				var flag=$(this).parent().prev().html()
				if(flag){
					alert("该账户已处于未锁状态，无需解锁！！")
				}else{
					 if (confirm("你确定将其解锁吗？")) {  
						 $.ajax({
								url: 'login/unlock',
								type: 'PUT',
								async: true,
								data:{"username":name},
								dataType: 'json',
								success: function(returndata) {
									alert("该账号已解锁")
									window.location.reload()
								},
								error: function(returndata) {
								}
							}) 
				        }  
				        else {   
				        }  
				}
		
			})
			
			//删除
			$("button[name='nameDelete']").click(function(){
				var name=$(this).parent().prev().prev().prev().html()
				 if (confirm("你确定将其删除吗？")) {  
					 $.ajax({
							url: 'login/delete',
							type: 'POST',
							async: true,
							data:{"username":name},
							dataType: 'json',
							success: function(returndata) {
								alert("该账号已删除")
								window.location.reload()
							},
							error: function(returndata) {
							}
						}) 
			        }  
			        else {   
			        }  
			})
			
		}
		
		
		
		
		
		
		
		
		
		//检查文件的拓展名
		function checkFileExt(filename, typeArr) {
			var flag = false; //状态
			//取出上传文件的扩展名
			var index = filename.lastIndexOf(".");
			var ext = filename.substr(index + 1);
			//循环比较
			for(var i = 0; i < typeArr.length; i++) {
				if(ext == typeArr[i]) {
					flag = true; //一旦找到合适的，立即退出循环
					break;
				}
			}
			return flag;
		}
		
		
		function ajaxUpload(formEle,recallFunc) {
			var formData = new FormData(formEle[0]);
			$.ajax({
				url: 'upload/uploadJar',
				type: 'PUT',
				data: formData,
				async: true,
				cache: false,
				contentType: false,
				processData: false,
				success: function(returndata) {
					recallFunc(returndata);
				},
				error: function(returndata) {
				}
			});
		}
		
		
		function ajaxQueryUploadJars(recallFunc) {
			$.ajax({
				url: 'page?count=1',
				type: 'GET',
				async: true,
				dataType: 'json',
				success: function(returndata) {
					recallFunc(returndata);
				},
				error: function(returndata) {
					ajaxError(returndata);
				}
			});
		}
		
		function ajaxQueryAccounts(recallFunc) {
			$.ajax({
				url: 'pageAccount?count=1',
				type: 'GET',
				async: true,
				dataType: 'json',
				success: function(returndata) {
					recallFunc(returndata);
				},
				error: function(returndata) {
					ajaxError(returndata);
				}
			});
		}
		
		
		
		//等待动画
		function getLoadingAnimation(height, width, text) {
			var height = height || "50px";
			var width = width || "50px";
			var text = text || "";
			var verticalOffset = verticalOffset || "230px";
			var temple =
				'<div style="height:' + height + ';width:' + width + ';margin-top:' + verticalOffset + ';" >' +
				'<div class="object" id="object_one" style:"margin-top:5em"></div>' +
				'<div class="object" id="object_two" style:"margin-top:5em"></div>' +
				'<div class="object" id="object_three" style:"margin-top:5em"></div>' +
				'<div class="object" id="object_four" style:"margin-top:5em"></div>' +
				'<div class="object" id="object_five" style:"margin-top:5em"></div>' +
				'<div class="object" id="object_six" style:"margin-top:5em"></div>' +
				'<div class="object" id="object_seven" style:"margin-top:5em"></div>' +
				'<div class="object" id="object_eight" style:"margin-top:5em"></div>' +
				'<div class="object" id="object_big" style:"margin-top:5em"></div>' +
				'<div>' + text + '</div>' +
				'</div>';
			return temple;
		}
		
		
		
		//查询按钮
		
		$("#searchUser").click(function(){
			
			var username=$("input[name='username']").val()
			
			$.ajax({
				url: 'pageAccountById?count=1&username='+username,
				type: 'GET',
				async: true,
				dataType: 'json',
                success:function(data){
                    if(data!=null){
                 	$("#accountHistory").children("tbody").empty();
         			var currPage=data.currPage
         			var totalPage=data.totalPage
         			var datalength=data.data.length
         			var length=0
         			if(datalength <= 0) {
         				$("#accountHistory").children("tbody").html("<tr class='info'><td colspan='999' style='text-align:center;font-weight:bold;color:#333'>没有相关记录</td></tr>");
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
         						"<button type='button' name='nameModify' class=' btn btn-success btn-xs' aria-label='Left Align'>"+
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
		})
		
		
		
		//新建账户
		
		$("a[role='newaccount']").click(function(){
			var modal = $('#modalDiaglog');
			var title = modal.find('.modal-header');
			var body = modal.find('.modal-body');
			var footer = modal.find('.modal-footer');
			title.text("新建账户");
			body.children().remove()
			footer.children().remove()
			var template=
				'<div class="row">'+
				'	<div class="col-sm-2" style="margin-top:20px"></div>'+
				'	<div class="col-sm-8" style="margin-top:20px">'+
				'		<div class="input-group">'+
				'  			<span class="input-group-addon" id="basic-addon1">用户名</span>'+
				'  			<input type="text"  name="name" class="form-control"/>'+
				'		</div>'+
				'	</div>'+
				'</div>'+
				'<div class="row">'+
				'	<div class="col-sm-2" style="margin-top:20px"></div>'+
				'	<div class="col-sm-8" style="margin-top:40px">'+
				'		<div class="input-group">'+
				'  			<span class="input-group-addon" id="basic-addon1">新密码：</span>'+
				'  			<input type="text" name="password" class="form-control" />'+
				'		</div>'+
				'	</div>'+
				'</div>'+
				'<div class="row">'+
				'	<div class="col-sm-2" style="margin-top:20px"></div>'+
				'	<div class="col-sm-8" style="margin-top:40px">'+
				'		<div class="input-group">'+
				'  			<span class="input-group-addon" id="basic-addon1">确认密码：</span>'+
				'  			<input type="text"  name="repassword" class="form-control" />'+
				'		</div>'+
				'	</div>'+
				'	<div class="col-sm-1" style="margin-top:20px">'+
				'			<h3><span style="margin-left:-27px"  class="label label-danger" id="wrong"></span></h3></div>'+
				'</div>'+
				'<div class="row">'+
				'	<div class="col-sm-8 col-sm-offset-2" style="margin-top:40px">'+
				'		<div class="input-group">' +
				'					<div class="input-group-btn">' +
				'						<button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' +
				'							选择权限<span class="caret"></span>' +
				'						</button>' +
				'						<ul class="dropdown-menu">' +
				'							<li><a href="javascript:void(0)" role="listBtn1" list-value="超级管理员">超级管理员</a></li>' +
				'							<li><a href="javascript:void(0)" role="listBtn2" list-value="政府人员">政府人员</a></li>' +
				'						</ul>' + 
				'					</div>' +
				'					<input type="text" role="listBtnDisplay" value="超级管理员" name="authoritie" class="form-control" value="未选择" readonly="readonly"/>' +
				'		</div>' +
				'	</div>'+
				'</div>'+
				'<div class="row">'+
				'	<div class="col-sm-2 col-sm-offset-5" style="margin-top:20px">'+
				'			<h3><span style="margin-left:-27px"  class="label label-success" id="success"></span></h3>'+
				'	</div>'+
				'</div>'+
				'<div class="row">'+
				'	<div class="col-sm-2 col-sm-offset-5" style="margin-top:20px">'+
				'		<input type="button" id="createname" class="btn btn-primary" value="确定新增"/>'+
				'	</div>'+
				'</div>'
				body.append(template)
				
						//注册a标签监听，在选中后更改相关信息
				regListBtn(modal.find("a[role='listBtn1']").parents(".input-group"),1, function() {});
				regListBtn(modal.find("a[role='listBtn2']").parents(".input-group"),2, function() {});
				
				$("#createname").click(function(){
					var name=$("input[name='name']").val()
					var password=$("input[name='password']").val()
					var repassword=$("input[name='repassword']").val()
					var authoritie=$("input[name='authoritie']").val()
					if(password==repassword){
						$.ajax({
							url: 'login/create',
							type: 'POST',
							async: true,
							data:{
								"username":name,
								"password":password,
								"authoritie":authoritie
							},
							dataType: 'json',
							success: function(returndata) {
								$("#success").html("创建成功")
							},
							error: function(returndata) {
							}
						})
					}else{
						$("#wrong").html("密码不正确")
					}

				})
		})
		
		$("#modalDiaglog").on('hide.bs.modal', function () {
			window.location.reload()
		})
		
})
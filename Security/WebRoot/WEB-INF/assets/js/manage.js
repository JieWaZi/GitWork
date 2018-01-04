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
						"<button type='button' class=' btn btn-success btn-xs' aria-label='Left Align'>"+
							"更新&nbsp;&nbsp<span class='glyphicon glyphicon-cog' aria-hidden='true'></span>"+
						"</button>"+
						"<button style='margin-left:20px' type='button' class='btn btn-danger btn-xs' aria-label='Left Align'>"+
							"删除&nbsp;&nbsp<span class='glyphicon glyphicon-trash' aria-hidden='true'></span>"+
						"</button>"+
						"</td></tr>");
				if(i>=length-1) return false
			});
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
			
	
})
$(function(){

	
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
		} else if(loginInfo == "failure") {
			var status = "<p>登录失败，请检查密码</p>";
			statusDiv.html(loginInfoTemplet(status, "danger"));
		} else if(loginInfo == "user not found") {
			var status = "<p>登录失败，用户名不存在</p>";
			statusDiv.html(loginInfoTemplet(status, "danger"));
		} else if(loginInfo == "user is locked") {
			var status = "<p>登录失败，用户名已被锁，请联系管理员</p>";
			statusDiv.html(loginInfoTemplet(status, "danger"));
		}else {
			var status = "<p>用户未登录</p>";
			statusDiv.html(loginInfoTemplet(status, "warning"));
		}
	}
	
	
	//错误信息
	function ajaxError(data) {
		switch(data.status) {
			case 200:
				alert("身份认证信息已过期，请重新登录！")
				location.reload();
				break;
			case 403:
				alert('身份认证失效！');
				doAjaxLogOut();
				break;
			case 400:
				alert("code:400");
				break;
			case 0:
				alert('请不要过快点击或刷新');
				break;
			default:
				alert('Error:' + data.status);
				break;
		}
	}
	
	//控制登陆时的动画
	$("#loginForm").submit(function() {
		$(this).find("input[type='submit']").attr("disabled", "disabled");
		$(this).find("input[type='submit']").val("登录中...");
		ajaxLogin();
		return false;
	});
	
	
	//ajax登陆
	function ajaxLogin(recallFunc) {
		var authToken = {};
		authToken.username = $("#username").val();
		authToken.password = $("#password").val();
		$.ajax({
			url: 'login',
			type: 'post',
			async: true,
			dataType: 'text',
			data: {
				username: authToken.username,
				password: authToken.password,
			},

			success: function(data) {
				loginInited = true;
				if(data == "success") {
					$("#loginForm").find("input[type='submit']").val("已登录，跳转中...");
					location.reload();
				} else {
					loginRequestOK(data);
				}
			},
			error: function(data) {
				var statusDiv = $("#loginStatus");
				statusDiv.siblings("input[type='submit']").removeAttr("disabled");
				statusDiv.siblings("input[type='submit']").val("登录");
				if(data.status == 200 || data.status == 403) {
					$("#loginStatus").html(loginInfoTemplet("<p>用户已在他处登陆，请检查密码是否泄漏！</p>", "danger"));
				} else {
					ajaxError(data);
				}

			}
		});
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

//		//reg event for logout 
//		$("a[cus-role='logoutBtn']").click(function() {
//			doAjaxLogOut();
//			return false;
//		})
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
			'<ul class="dropdown-menu" role="loginDpMenu" style="background-color:#3C3C3C;" aria-labelledby="dropdownMenuDivider">' +
			'	<li class="dropdown-header">用户信息</li>' +
			'	<li>' +
			'		<div style="margin-left:1.3em;margin-top:1em;font-weight:bold;color:#FFF">' +
			'			<p>用户名:	' + loginInfo.user + '</p>' +
			'			<p>身份:	' + loginInfo.authorities + '</p>' +
			'		</div>' +
			'	</li>' +
			'	<li role="separator" class="divider"></li>' +
			//		'	<li class="dropdown-header">面板选择</li>' +
			'	<li ><a href="index" role="switchPanelBtn" style="color:#FFF;">报告面板</a></li>' +
			'	<li ><a href="business" role="switchPanelBtn" style="color:#FFF;">业务面板</a></li>' +
			'	<li ><a href="algorithmManage" role="switchPanelBtn" style="color:#FFF;">管理面板</a></li>' +
			'	<li role="separator" class="divider"></li>' +
			'	<li><a href="logout" cus-role="logoutBtn" style="color:#FFF;">注销登录</a></li>' +
			'</ul>';
		return templet;
	}
	
	
//	//登出请求
//	function doAjaxLogOut() {
//		$.ajax({
//			url: 'logout',
//			type: 'POST',
//			async: true,
//			dataType: 'json',
//			data: {
//				logout: true
//			},
//			success: function() {
//				location.reload();
//			},
//			error: function() {
//				location.reload();
//			}
//		});
//	}
	
	
	
	//导航栏滚动监听
		$('body').scrollspy({
			target: '#sideBar'
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
		
	
		//控制登陆图标移进移出
		$("#loginPanelBtn").parent().mouseenter(
				function() {
					$(this).addClass("open");
				}
			);

			$("#loginPanelBtn").parent().mouseleave(
				function() {
					$(this).removeClass("open");
				}
			);
			
		//控制导航点击后样式
		$("#sideBar li").click(function(){
			$(".active").removeClass("active")
			$(this).addClass("active")
		})
		
		
///////////////////////////////////////////////////////////////
/*************************************************************/
/*************************************************************/
/**********        Ajax Contents module        ***************/
/*************************************************************/
/*************************************************************/
///////////////////////////////////////////////////////////////
		
ajaxContents()

//获取报告
function ajaxGetReports(industrySort,recallFunc) {
	$.ajax({
		url: 'report/industrySort',
		type: 'GET',
		async: true,
		dataType: 'json',
		data: 'industrySort='+industrySort,
		success: function(reports) {
			recallFunc(reports);
		},
		error: function(reports) {
			recallFunc(reports);
		}
	});
}

function ajaxContents() {
		$("#sideBar").children("ul").children("li").each(
			function(i, item) {
				var href = $(item).children("a").attr("href");
				var name = $(item).children("a").text();
				$(href+"Content").append('<div class="alert alert-info alert-dismissible" role="alert">加载中，请稍后... <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>');
				ajaxGetReports(name, function(reports) {
					$(href+"Content").children().remove()
					if(reports.length <= 0) {
						$(href+"Content").append('<div class="alert alert-info alert-dismissible" role="alert">本栏暂未添加内容 <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button></div>');
					} else {
						$.each(reports, function(i, report) {
							var diagramThumbnailUUID = report.diagramUuid;
							var title =report.title
							if(href == '#outline') {
								$(href+"Content").append("<div class='col-sm-12 col-md-6' id='" + diagramThumbnailUUID + "' style='height:300px;'></div>");
								var diagramEle = $("#" + diagramThumbnailUUID);
								ajaxRequestOfOutline(diagramThumbnailUUID, function(data) {
									var axisLine = {
											lineStyle: {
												color: 'lightgray',
												width: 2, //这里是为了突出显示加上的
											}
										};
									var option={}
									if(data.chart=="cluster"){
										var json=clusterOption(data)
										json.xAxis[0]['axisLine'] = axisLine;
										json.yAxis[0]['axisLine'] = axisLine;
										json.legend['orient']=='horizontal'
										option = json
									}else if(data.chart=="bar"){
										option=data.option
										// 使用if确保option中具有完整的相关对象
										if(option.xAxis != null) {
											option.xAxis[0]['axisLine'] = axisLine;
										}
										if(option.yAxis != null) {
											option.yAxis[0]['axisLine'] = axisLine;
										}
										if(option.legend != null) {
											option.title['textStyle'] = {
												'color': 'white'
											};
										}
									}
									option.grid['top']='24%'
									option.legend['textStyle']={color: '#FFFFFF'}

										
									console.log(option);
									addDiagram(diagramEle, false, option);
								});
							} else {
								$(href).append(fillContentPanelTemplet(report, "Tumbnail"));
								var reportEle = $("#" + report.uuid);
								var diagramEle = reportEle.find("div[role='diagramContainer']");
								diagramEle.attr("id", diagramThumbnailUUID);
								ajaxRequestOptionData(diagramThumbnailUUID, function(data) {
									var json=clusterOption(data)
									var thumbnailOption = {};
									var axisLine = {
										lineStyle: {
											color: 'white',
											width: 2, //这里是为了突出显示加上的
										}
									};
									thumbnailOption['series'] = json.series;
									thumbnailOption['xAxis'] = json.xAxis;
									thumbnailOption['yAxis'] = json.yAxis;

									thumbnailOption.xAxis[0]['axisLine'] = axisLine;
									thumbnailOption.yAxis[0]['axisLine'] = axisLine;
									addDiagram(diagramEle, false, thumbnailOption);
								})
//								ajaxRequestOption(diagramThumbnailUUID, function(option) {
//									var thumbnailOption = {};
//									var axisLine = {
//										lineStyle: {
//											color: 'white',
//											width: 2, //这里是为了突出显示加上的
//										}
//									};
//									thumbnailOption['series'] = option.series;
//									thumbnailOption['xAxis'] = option.xAxis;
//									thumbnailOption['yAxis'] = option.yAxis;
//
//									thumbnailOption.xAxis[0]['axisLine'] = axisLine;
//									thumbnailOption.yAxis[0]['axisLine'] = axisLine;
//									addDiagram(diagramEle, false, thumbnailOption);
//								});

							}

						});
					}
				});
			}
		);

	regModalEvents();
	$('[data-toggle="popover"]').popover();
}



function fillContentPanelTemplet(report, style) {
	var defaultUUID = Math.uuidFast().replace("-", "");
	var defaultReport = {
		uuid: defaultUUID,
		diagramUuid: "testDemo",
		title: "默认样式",
		texts: "描述信息",
	}
	$.extend(defaultReport, report);
	defaultReport.texts = removeBr(defaultReport.texts).substring(0, 40);
	var templet;
	switch(style) {
		case "Tumbnail":
			templet = getThumbnailTemplet(defaultReport);
			break;
		case "TogglePanel":
			templet = getTogglePanelTemplet(defaultReport);
			break;
		case "Panel":
			templet = getPanelTemplet(defaultReport);
			break;
		default:
			templet = getThumbnailTemplet(defaultReport);
			break;
	}

	return templet;
}

function getThumbnailTemplet(report) {
	var thumbnailTemplet =
		"<div id=" + report.uuid + " class='col-xs-12 col-sm-6 col-md-4' style='height:420px' >" +
		"	<a href='#" + report.uuid + "' role='reportLauncher'  data-toggle='modal' data-target='#modalDiaglog'>" +
		"		<div class='thumbnail' style='background-color: #3C3C3C;border-color:rgba(255, 255, 255, 0.15);'>" +
		"			<div class='caption' style='color:white;'>" +
		"				<h3>" + report.title + "</h3>" +
		"				<div role='diagramContainer' style='width: 100%;height: 200%;'></div>" +
		"				<p>" + report.texts + "...</p>" +
		"			</div>" +
		"		</div>" +
		"	</a>" +
		"</div>";
	return thumbnailTemplet;
}

function getTogglePanelTemplet(report) {
	var templet =
		"<div id=" + report.uuid + "  class='col-xs-12 col-sm-6 col-md-4'>" +
		"	<div class='panel panel-default'>" +
		"		<div id='" + report.uuid + "heading' role='tab' class='panel-heading'>" +
		"			<h4 class='panel-title'>" +
		"				<a role='botton' data-toggle='collapse' data-parent='#accordion' href='#" + report.uuid + "' aria-expanded='true' aria-controls='" + report.uuid + "'>" +
		report.title +
		"				</a>" +
		"			</h4>" +
		"		</div>" +
		"		<div id='" + report.uuid + "' class='panel-collapse collapse in' role='tabpanel' aria-labelledby='id----heading'>" +
		"			<div class='panel-body'>" +
		"				<a href='#" + report.detailsLink + "'  role='reportLauncher' data-toggle='modal' data-target='#modalDiaglog'>" +
		"					<div  role='diagramContainer' style='width: 100%;height: 200%;'></div>" +
		"				<p>" + report.texts + "...</p>" +
		"			</div>" +
		"		</div>" +
		"	</div>" +
		"</div>";
	return templet;
}

function getPanelTemplet(report) {
	var templet =
		"<div id=" + report.uuid + " class='col-xs-12 col-sm-6 col-md-4'>" +
		"	<div class='panel panel-default'>" +
		"		<div class='panel-heading'>" +
		"			<h3>" + report.title + "</h3>" +
		"		</div>" +
		"		<a href='#" + report.uuid + "' role='reportLauncher' data-toggle='modal' data-target='#modalDiaglog'>" +
		"			<div class='panel-body'>" +
		"				<div  role='diagramContainer' style='width: 100%;height: 200%;'>" +
		"				</div>" +
		"				<p>" + report.texts + "...</p>" +
		"			</div>" +
		"		</a>" +
		"	</div>" +
		"</div>";
	return templet;

}


///////////////////////////////////////////////////////////////
/*************************************************************/
/*************************************************************/
/**********       Ajax Module - Diagrams       ***************/
/*************************************************************/
/*************************************************************/
///////////////////////////////////////////////////////////////

function ajaxRequestOfOutline(i, recallFunc) {
	$.ajax({
		url:'diagrams/overview/'+ i + '/',
		type: 'GET',
		async: true,
		dataType: 'json',
		data: {},
		success: function(returnOption) {
			recallFunc(returnOption);
		},
		error: function(returndata) {
			console.log(returndata.status);
			
		}
	});
}

function ajaxRequestOption(diagram_uuid, recallFunc) {
	$.ajax({
		url:'diagrams/'+ diagram_uuid + '/',
		type: 'GET',
		async: true,
		dataType: 'json',
		data: {},
		success: function(returnOption) {
			recallFunc(returnOption);
		},
		error: function(returndata) {
			console.log(returndata.status);
			//ajaxError(returndata);
		}
	});
}

function ajaxRequestOptionData(diagram_uuid, recallFunc) {
	$.ajax({
		url:'diagrams/cluster/' + diagram_uuid + '/',
		type: 'GET',
		async: true,
		dataType: 'json',
		data: {},
		success: function(returnOption) {
			recallFunc(returnOption);
		},
		error: function(returndata) {
			console.log(returndata.status);
			//ajaxError(returndata);
		}
	});
}
	

///////////////////////////////////////////////////////////////
/*************************************************************/
/*************************************************************/
/****************    Modal function part     *****************/
/*************************************************************/
/*************************************************************/
///////////////////////////////////////////////////////////////


//报告面板监听
function regModalEvents() {
	$('#modalDiaglog').on('show.bs.modal', function(event) {
		var button = $(event.relatedTarget) // Button that triggered the modal
		var modal = $(this);
		loadModal(button, modal);
	});
	$('#modalDiaglog').on('hidden.bs.modal', function(event) {
		resetModal();
	});

}

//加载图表
function loadModal(button, modal) {
	var title = modal.find('.modal-header');
	var body = modal.find('.modal-body');
	var footer = modal.find('.modal-footer');
	var role = button.attr("role");
	var report_uuid = button.attr("href").substr(1);
	var diagram_uuids = [];
	diagram_uuids[0] = button.find("div[role='diagramContainer']").attr("id");
	switch(role) {
	case "reportLauncher":
		 ajaxRequestReportDetails(report_uuid,function(report){
				title.text(report.title);
				modal.find("[role='reportText'] p").html(report.texts);
		 })
		body.html(getReportDetailsTemplet(modal.height(), modal.width()));
		ajaxRequestOptionData(diagram_uuids[0], function(data) {
			var json=clusterOption(data)
			addDiagram(modal.find("[role='diagramContainer']"), false, json);
		})		 
		 
//		ajaxRequestOption(diagram_uuids[0], function(option) {
//			option["tooltip"]= {
//			        showDelay : 20,
//			        formatter : function (params) {return '公司名称：'+params.value[3]+'</br>所属类别：'+params.value[2]+'</br>公司名称：'+params.value[0]+'</br>公司名称：'+params.value[1]+'</br>'},
//			        axisPointer:{
//			            show: false,
//			            type : 'cross',
//			            lineStyle: {
//			                type : 'dashed',
//			                width : 10
//			            }
//			        },
//			        zlevel: 1
//			    }
//			addDiagram(modal.find("[role='diagramContainer']"), false, option);
//		});
		break;
	case "customeLauncher":
		break;
	default:
		break;
	}

}

function addDiagram(ele, showTitle, option) {
	var domEle = ele[0];
	var mychart = echarts.init(domEle, 'macarons');
	mychart.setOption(option);
	$(window).resize(function() {
		$(mychart).each(function(index, chart) {
			chart.resize();
		});
	});

}

function resetModal() {
	var modal = $("#modalDiaglog");
	var title = modal.find('.modal-header');
	var body = modal.find('.modal-body');
	var footer = modal.find('.modal-footer');
	title.html("加载中...");
	body.html('<div class="row">' +
			'<div style="margin: 0;font-size: xx-large;" class="col-sm-12">加载中...</div>' +
	'</div>');
}

function getReportDetailsTemplet(width, height) {
	var width = width;
	var height = screen.height * 0.4;
	var templet =
		'<div class="row">' +
		'	<div class="col-md-12" role="diagramContainer" style="height:' + height + 'px;width:' + width + 'px;">' +
		'	</div>' +
		'	<div class="col-md-12" role="reportText">' +
		'		<p></p>' +
		'	</div>' +
		'</div   >';
	return templet;
}

function ajaxRequestReportDetails(uuid, recallFunc) {
	$.ajax({
		url: 'report/' + uuid + '/',
		type: 'GET',
		async: true,
		dataType: 'json',
		data: {},
		success: function(report) {
			recallFunc(report);
		},
		error: function(data) {
			console.log(data);
		}
	});
}
function return2Br(str) {
	return str.replace(/\r?\n/g,"<br />");
}

function removeBr(str){
	return str.replace(/<br \/>/g,"");
}




//聚类图形显示模板
function clusterOption(data){
	
    var title=data.title
	var list=data.data
	var serie=[]
	$.each(list, function(i, item) {
		var tag=""
		if(item.clustertagalias!=null||item.clustertagalias!=""){
			tag=item.clustertagalias
		}else{
			tag=item.clustertag
		}
		template={
	        		large: true,
	        		name: tag,
	        		type: 'scatter',
	        		symbolSize: 5,
	        		data: [[item.x2D,item.y2D,item.clustertag,item.clustertagalias,item.companyName]]
	   			},
	    serie.push(template)
	})

	var option = {
	        backgroundColor: 'transparent',
		    title : {
		        text: title,
		        textStyle:{color:'#fff'}
		    },
		    grid: {
		        left: '3%',
		        right: '7%',
		        bottom: '3%',
		        containLabel: true
		    },
		    tooltip : {
		        showDelay : 20,
		        formatter : function (params) {return '公司名称：'+params.value[4]+'</br>所属类别：'
		        			+params.value[2]+'('+params.value[3]+')</br>单位增加值能耗：'+params.value[0]+'</br>能源消费占总成本比：'
		        			+params.value[1]+'</br>'},
		        axisPointer:{
		            show: false,
		            type : 'cross',
		            lineStyle: {
		                type : 'dashed',
		                width : 10
		            }
		        },
		        zlevel: 1
		    },
		    toolbox: {
		        feature: {
		            dataZoom: { 
		            	show: true,
		                title: {
		                    dataZoom: '区域缩放',
		                    dataZoomReset: '区域缩放后退'
		                }},
		            dataView: {
		                    show: true,
		                    title: '数据视图',
		                    readOnly: true,
		                    lang: [
		                        '数据视图',
		                        '关闭',
		                        '刷新'
		                    ]
		                },
		            restore: {
		                    show: true,
		                    title: '还原'
		                },
		            saveAsImage: {
		                    show: true,
		                    title: '保存为图片',
		                    type: 'png',
		                    lang: [
		                        '点击保存'
		                    ]
		                }
		        },
	        show: true,
	        right: 40
		    },
		    grid:{
		    	bottom:12,
		    	containLabel:true,
		    	left:3,
		    	right:4
		    },
		    legend: {
		        data: data.legend,
		        orient: 'horizontal',
		        x: 'center',
		        y: 'top',
		        top:'35px'
		    },
		    xAxis : [
		        {
		            type : 'value',
		            scale:true,
		            axisLabel : {
		                formatter: '{value} '
		            },
		            splitLine: {
		                show: false
		            }
		        }
		    ],
		    yAxis : [
		        {
		            type : 'value',
		            scale:true,
		            axisLabel : {
		                formatter: '{value} '
		            },
		            splitLine: {
		                show: false
		            }
		        }
		    ],
		    series : serie
		};
	
	return option
}
});

$(function(){
	
	$('body').scrollspy({
		target: '#sideBar1'
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
	$("#sideBar1 li").click(function(){
		$(".active").removeClass("active")
		$(this).addClass("active")
	})
	var uploaddata;
	var preprocessdata;
	
	
	//模态框消失进行刷新数据
	$("#modalDiaglog").on('hide.bs.modal', function () {
		ajaxQueryUploadRecord(function(data) {
			displayUploadRecords(data, $("#uploadHistory"));
		});
		
		ajaxQueryPreproccessRecords(function(data) {
			displayPreProcessRecords(data, $("#dataPreHistory"));
		});
		
		ajaxRequestDataAnalysisHistory(function(reports) {
			displayDataAnalysisHistory(reports, $("#dataAnaHistory"));
		});
		
		ajaxRequestReportsHistory(function(reports) {
			displayReportHistory(reports, $("#dataRepHistory"));
		});
	})

	
///////////////////////////////////////////////////////////////
/*************************************************************/
/*************************************************************/
/**********    Ajax Contents module - upload   ***************/
/*************************************************************/
/*************************************************************/
///////////////////////////////////////////////////////////////
	
	
	
	
	
	//查询历史信息
	ajaxQueryUploadRecord(function(data) {
		uploaddata=data
		displayUploadRecords(data, $("#uploadHistory"));
	});
	
	
	$("#uploadContent").find("a[role='customeLauncher']").click(function() {
		var modal = $('#modalDiaglog');
		var title = modal.find('.modal-header');
		var body = modal.find('.modal-body');
		var footer = modal.find('.modal-footer');
		title.text("新建上传任务");
		body.children().remove()
		footer.children().remove()
		body.append(getUploadAnualReportTemplet());
		
		//注册a标签监听，在选中后更改相关信息
		regListBtn(modal.find("a[role='listBtn1']").parents(".input-group"),1, function() {});
		regListBtn(modal.find("a[role='listBtn2']").parents(".input-group"),2, function() {});
		
		//对选中文件进行监听，修改相关提示
		$("#uploadForm").children("div").children("input[type='file']").on("change", function() {
			var path = $(this).val();
			if(checkFileExt(path, ['xlsx', 'xls'])) {
				body.find("[role='uploadTip']").removeClass("alert-success alert-danger").addClass("alert-info");
				body.find("[role='uploadTip']").html("提示：待上传");
				body.find("input[type='submit']").removeAttr("disabled");
			} else {
				body.find("[role='uploadTip']").removeClass("alert-info alert-success").addClass("alert-danger");
				body.find("[role='uploadTip']").html("<span style='color:red;font-weight:bold;'>提示：文件类型错误</span>");
				body.find("input[type='submit']").attr("disabled", "disabled");
			}
		});
		// TODO 点击提交是修改提示状态，进行提交
		$("#uploadForm").submit(function() {
			body.find("[role='uploadTip']").removeClass("alert-success alert-danger").addClass("alert-info");
			body.find("[role='uploadTip']").html("提示：上传中...");
			body.find("[role='uploadTip']").append(getLoadingAnimation("20px", "20px"));
			body.find("[role='uploadTip']").append("<br /><br /><br /><br /><br /><br />");
			ajaxUpload($(this),function(data) {
				uploaddata=data
				body.find("[role='uploadTip']").removeClass("alert-info alert-danger").addClass("alert-success");
				body.find("[role='uploadTip']").html("<span style='color:green;font-weight:bold;'>提示：上传完成</span>");
			});
			return false;
		});
		

	});


	function displayUploadRecords(data, displayTableEle) {
		displayTableEle.children("tbody").empty();
		if(data.length <= 0) {
			displayTableEle.children("tbody").html("<tr class='info'><td colspan='999' style='text-align:center;font-weight:bold;color:#333'>没有上传记录</td></tr>");
		}

		$.each(data, function(i, item) {
			var uploadDate=item.uploadDateTime;
			var fileName = item.originalName;
			displayTableEle.children("tbody")
				.append("<tr>" +
					"<td>" + formatDatetimeOfJsonObject(uploadDate)+"</td>" +
					"<td>" + item.uploadTarget + "</td>" +
					"<td>" + fileName + "</td></tr>");
		});
	}


	//上传模板
	function getUploadAnualReportTemplet() {
		var baseTemplet =
			'<div class="btn-group btn-group-justified" role="group">' +
			'		<form id="uploadForm" enctype="multipart/form-data">' +
			'			<div class="col-lg-6 col-sm-12">' +
			'				<div class="input-group">' +
			'					<div class="input-group-btn">' +
			'						<button type="button"  class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">' +
			'							选择上传目的<span class="caret"></span>' +
			'						</button>' +
			'						<ul class="dropdown-menu">' +
			'							<li><a href="javascript:void(0)" role="listBtn1" list-value="企业基本信息">企业基本信息</a></li>' +
			'							<li><a href="javascript:void(0)" role="listBtn2" list-value="企业能源消费结构">企业能源消费结构</a></li>' +
			'						</ul>' + 
			'					</div>' +
			'					<input type="text" role="listBtnDisplay" value="企业基本信息" name="uploadTarget" class="form-control" value="未选择" readonly="readonly"/>' +
			'				</div>' +
			'			</div>' +
			'			<div class="col-lg-6 col-sm-12">' +
			'				<input type="file" accept=".xlsx,.xls" class="btn btn-default" name="file" /> ' + // TODO-ID:1 target should be dynamicly loaded.
			'				<input type="submit" style="margin-top:7px" class="btn btn-default" value="开始上传" disabled="disabled"/>' +
			'			</div>' +
			'			<div class="col-lg-12 col-sm-12" style="margin-top:0.5em;">' +
			'				<div  role="uploadTip" class="alert alert-info">' +
			'					提示：未选择文件' +
			'				</div>' +
			'			</div>' +
			'		</form>' +
			'</div>';
			return baseTemplet;
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
			url: 'upload/singleUpload',
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
	
	
	function ajaxQueryUploadRecord(recallFunc) {
		$.ajax({
			url: 'upload/record/',
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
	
	function ajaxQueryUploadRecords(uploadTarget,recallFunc) {
		$.ajax({
			url: 'upload/record/',
			type: 'GET',
			async: true,
			dataType: 'json',
			data:'uploadTarget='+uploadTarget,
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
	
	
	
	
	
	
	
	
	
	
	
	
///////////////////////////////////////////////////////////////
/*************************************************************/
/*************************************************************/
/********** Ajax Contents module - preproccess ***************/
/*************************************************************/
/*************************************************************/
///////////////////////////////////////////////////////////////	
	
	
	//查询预处理记录
	ajaxQueryPreproccessRecords(function(data) {
		displayPreProcessRecords(data, $("#dataPreHistory"));
	});
	

	$("#dataPreContent").find("a[role='customeLauncher']").click(function() {
		var modal = $('#modalDiaglog');
		var title = modal.find('.modal-header');
		var body = modal.find('.modal-body');
		var footer = modal.find('.modal-footer');
		title.text("新建预处理任务");
		body.children().remove()
		footer.children().remove()
		body.append(getPreproccessTemplet());
		regListBtn(body.find("[role='listBtn1']").parents(".input-group"),1, function(target) {
			updateDatasourceList(body, target);
		});
		regListBtn(body.find("[role='listBtn2']").parents(".input-group"),2, function(target) {
			updateDatasourceList(body, target);
		});

		body.find("div[stepRole='2']").children('div')
			.append(getRadioInfoBtnTemplet("averageDataPreProccess", "DataPreProccessMethods", "均值法"));
		body.find("div[stepRole='2']").children('div')
		.append(getRadioInfoBtnTemplet("zeroDataPreProccess", "DataPreProccessMethods", "置零法"));
		regInfoBtnGroup(body, function() {
			updatePreProccessPara(body);
		});

		updatePreProccessPara(body);

	});



	//数据预处理模板
	function getPreproccessTemplet() {
		var templet =
			'<div class="row">' +
			'<div stepRole="1" class="col-sm-12">' +
			'	<h3>第一步：选择数据源</h3>' +
			'	<div class="col-sm-10 col-md-8 col-lg-6">' +
			'		<div class="input-group">' +
			'			<div class="input-group-btn">' +
			'				<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">选择数据源类别  <span class="caret"></span></button>' +
			'				<ul class="dropdown-menu">' +
			'					<li><a href="#" role="listBtn1" list-value="企业基本信息">企业基本信息</a></li>' +
			'					<li><a href="#" role="listBtn2" list-value="企业能源消费结构">企业能源消费结构</a></li>' +
			'				</ul>' +
			'			</div>' +
			'			<input type="text" class="form-control" role="listBtnDisplay" value="未选择" readonly="readonly"/>' +
			'		</div>' +
			'	</div>' +
			'	<div class="col-sm-12" style="margin-top:1.5em;"></div>' +
			'</div>' +
			'<div stepRole="2" class="col-sm-12">' +
			'	<h3>第二步：选择预处理方法</h3>' +
			'	<div></div>' +
			'</div>' +
			'<div stepRole="3" class="col-sm-12">' +
			'	<h3>第三步：确认信息并开始预处理</h3>' +
			'	<div></div>' +
			'</div>' +
			'</div>';
		return templet;
	}
	
	
	function updateDatasourceList(contentEle, target) {
		
		//重写查询上传记录方法
		ajaxQueryUploadRecords(target,function(data) {
			var targetDiv = contentEle.find("div[stepRole='1']").children("div:last");
			targetDiv.empty();
			$.each(data, function(i, item) {
				var uploadDate=item.uploadDateTime;
				var fileName = item.originalName;
				targetDiv.append(getRadioInfoBtnTemplet(item.uuid, "datasourceList", item.originalName + "/" + formatDatetimeOfJsonObject(uploadDate)));
			});
			regInfoBtnGroup(contentEle, function() {
				var uploadTarget=$("input[role='listBtnDisplay']").val()
				updatePreProccessPara(contentEle,uploadTarget);
			});
	
		});
	}
	
	function updatePreProccessPara(contentEle,uploadTarget) {
		var datasource = {};
		var method = {};
		method['name'] = $(contentEle).find("input[name='DataPreProccessMethods']:checked").val() || null;
		method['aliasValue'] = $(contentEle).find("input[name='DataPreProccessMethods']:checked").attr("valueAlias") || "未选择";
		datasource["uuid"] = $(contentEle).find("input[name='datasourceList']:checked").val() || "unselect";
		datasource["name"] = $("input[related-box='" + datasource["uuid"] + "']").val() || "未选择";
		var templet =
			'<div class="col-sm-12">' +
			'<p>已选择的数据源：' + datasource.name + '</p>' +
			'<p>已选择的处理方法：' + method.aliasValue + '</p>' +
			'<p>当前预处理任务状态：<div role="PreproccessTip">待提交</div></p>' +
			'<p><input type="button" id="submitPreProccessBtn" class="btn btn-primary" value="确定并开始数据预处理"></p>' +
			'</div>';
		contentEle.find("div[steprole='3']").children("div").html(templet);
		$("#submitPreProccessBtn").click(function() {
			if(method.name != null &&
				method.aliasValue != "未选择" &&
				datasource.uuid != "unselect" &&
				datasource.name != "未选择") {
				contentEle.children("div:last").children("div").find("div[role='PreproccessTip']")
					.html(getLoadingAnimation("50px", "50px", "处理中..."));
				$(this).attr("disabled", "disabled");
				$(this).val("执行中...");
				ajaxSubmitPreProccessJob(uploadTarget,contentEle, datasource, method);
			} else {
				contentEle.children("div:last").children("div").find("div[role='PreproccessTip']").text("数据源或方法未选择！");
			}
		});
	}
	
	function ajaxSubmitPreProccessJob(uploadTarget,contentEle, datasource, method) {
		$.ajax({
			url: 'preprocess/newJob/',
			type: 'POST',
			async: true,
			dataType: 'json',
			data: {
				"datasource": datasource.uuid,
				"method": method.name,
				"uploadTarget":uploadTarget
			},
			success: function(returndata) {
				if(returndata.status == 0) {
					contentEle.children("div:last").children("div").find("div[role='PreproccessTip']").text("任务已完成！");
					console.log(contentEle);
					$("#submitPreProccessBtn").removeAttr("disabled");
					$("#submitPreProccessBtn").val("再次执行");
				} else {
					contentEle.children("div:last").children("div").find("div[role='PreproccessTip']")
						.html('<span style="color:red;font-weight:bold;">任务已中止，' + returndata.info + '</span>');
					$("#submitPreProccessBtn").removeAttr("disabled");
					$("#submitPreProccessBtn").val("重试");
				}
			},
			error: function(returndata) {
				if(returndata.status < 500 && returndata.status >= 400 || returndata.status == 200) {
					ajaxError(returndata);
				}
				contentEle.children("div:last").children("div").find("div[role='PreproccessTip']")
					.html('<span style="color:red;font-weight:bold;">任务异常中止！代码：' + returndata.status + '</span>');
				$("#submitPreProccessBtn").removeAttr("disabled");
				$("#submitPreProccessBtn").val("重试");
			}
		});
	}
	
	function ajaxQueryPreproccessStatus(uuid) {
		$.ajax({
			url: 'preprocess/' + uuid + '/status',
			type: 'POST',
			async: true,
			dataType: 'json',
			data: {},
			success: function(returndata) {
	
			},
			error: function(returndata) {
				ajaxError(returndata);
			}
		});
	}
	
	//查询预处理记录
	function ajaxQueryPreproccessRecords(recallFunc) {
		$.ajax({
			url: 'preprocess/records',
			type: 'GET',
			async: true,
			dataType: 'json',
			data: {},
			success: function(returndata) {
				recallFunc(returndata);
			},
			error: function(returndata) {
				ajaxError(returndata);
			}
		});
	}
	
	function displayPreProcessRecords(data, displayTableEle) {
		displayTableEle.children("tbody").empty();
		if(data.length <= 0) {
			displayTableEle.children("tbody").html("<tr class='info'><td colspan='999' style='text-align:center;font-weight:bold;color:#333;'>还没有创建任何报告</td></tr>");
		}
	
		$.each(data, function(i, item) {
			// translation status
			var rowClass = '';
			var statusTranslation = '';
			switch(item.preProccessStatus) {
				case "Step.6: finished":
					rowClass = "success";
					statusTranslation = "已完成";
					break;
				case "Step.1: inited":
					rowClass = "warning";
					statusTranslation = "已经初始化";
					break;
				default:
					rowClass = "danger";
					statusTranslation = item.preProccessStatus;
					break;
			}
			var dataTemplet =
				'<tr class="' + rowClass + '">' +
				'	<td>' + formatDatetimeOfJsonObject(item.preProccessDateTime)+ '</td>' +
				'	<td>' + item.datasource + '</td>' +
				'	<td>' + preProcessMethodTranslator(item.preProccessMethod) + '</td>' +
				'	<td>' + item.userName + '</td>' +
				'	<td>' + statusTranslation + '</td>' +
				'</tr>';
			displayTableEle.children("tbody")
				.append(dataTemplet);
	
		});
	}
	
	// preprocess method translator
	function preProcessMethodTranslator(method) {
		switch(method) {
			case "averageDataPreProccess":
				method = "均值法";
				break;
			case "zeroDataPreProccess":
				method = "置零法";
				break;
			default:
				method = "未注册的方法";
				break;
		}
		return method;
	}	
	
	
	
	
	//监听单选按钮函数
	function regListBtn(listBtnEle, i,recallFunc) {
		var target = listBtnEle.find("a[ role='listBtn"+i+"']");
		target.click(function() {
			//listBtnEle.children("input").val(target.text());
			listBtnEle.children("input").attr("value",target.attr("list-value"));
			listBtnEle.children("div").removeClass("open");
			recallFunc(target.attr("list-value")); //do some operation after select a btn
			return false;
		});
	}
	
	//选择框模板
	function getRadioInfoBtnTemplet(value, groupName, text) {
		var templet =
			'<div class="col-sm-9 col-md-7 col-lg-6">' +
			'	<div class="input-group">' +
			'		<span class="input-group-addon">' +
			' 	       <input name="' + groupName + '" type="radio" value="' + value + '" valueAlias="' + text + '"/>' +
			'	   	</span>' +
			'		<input type="button" role="infoBtn" related-box="' + value + '" value="' + text + '" class="form-control" readonly="readonly" />' +
			'	</div>' +
			'</div>';
		return templet;
	}
	
	
	

	
///////////////////////////////////////////////////////////////
/*************************************************************/
/*************************************************************/
/**********   Ajax Contents - Data Analysis Job     **********/
/*************************************************************/
/*************************************************************/
///////////////////////////////////////////////////////////////

	
		ajaxRequestDataAnalysisHistory(function(reports) {
			displayDataAnalysisHistory(reports, $("#dataAnaHistory"));
		});

		$("#dataAnaContent").find("a[role='customeLauncher']").click(function() {
			var modal = $('#modalDiaglog');
			var title = modal.find('.modal-header');
			var body = modal.find('.modal-body');
			var footer = modal.find('.modal-footer');
			title.text("新建数据分析任务");
			title.append("<input type='hidden' name='Resultuuid'/>")
			body.children().remove()
			footer.children().remove()
			body.append(getNewDataAnalysisTemplet());
			updateDataAnalysis(body);
			regListBtn(modal.find("a[role='listBtn1']").parents(".input-group"),1, function(target) {
				updateDataAnalysis(body)
			});
			regListBtn(modal.find("a[role='listBtn2']").parents(".input-group"),2,function() {
				updateDataAnalysis(body)
			});
			regListBtn(modal.find("a[role='listBtn3']").parents(".input-group"),3,function() {
				updateDataAnalysis(body)
			});
			regListBtn(modal.find("a[role='listBtn4']").parents(".input-group"),4,function() {
				updateDataAnalysis(body)
			});
			regListBtn(modal.find("a[role='listBtn5']").parents(".input-group"),5,function() {
				updateArithmetic(modal,body,function(){})
				updateDataAnalysis(body)
				$(".input-grouptem").remove()
			});
			regListBtn(modal.find("a[role='listBtn6']").parents(".input-group"),6,function() {
				updateArithmetic(modal,body,function(){})
				updateDataAnalysis(body)
				$(".input-grouptem").remove()
			});
			regListBtn(modal.find("a[role='listBtn7']").parents(".input-group"),7,function() {
				updateArithmetic(modal,body,function(){})
				updateDataAnalysis(body)
				$(".input-grouptem").remove()
			});
			regListBtn(modal.find("a[role='listBtn8']").parents(".input-group"),8,function() {
				updateArithmetic(modal,body,function(){})
				updateDataAnalysis(body)
			});
			regListBtn(modal.find("a[role='listBtn9']").parents(".input-group"),9,function() {
				updateArithmetic(modal,body,function(){})
				updateDataAnalysis(body)
			});
			regListBtn(modal.find("a[role='listBtn10']").parents(".input-group"),10,function() {
				updateArithmetic(modal,body,function(){})
				updateDataAnalysis(body)
			});
			



			$("input[name='year']").blur(function(){
				updateDataAnalysis(body)
			})

		});


	function getNewDataAnalysisTemplet() {
		var templet =
			'<div class="row">' +
			'<div stepRole="1" class="col-sm-12">' +
			'	<h3>选择数据源及年份</h3>' +
			'	<div class="col-sm-6 col-md-6 col-lg-6">' + 
			'		<div class="input-group">' +
			'			<div class="input-group-btn">' +
			'				<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">选择数据源  <span class="caret"></span></button>' +
			'				<ul class="dropdown-menu">' +
			'					<li class="dropdown-submenu"><a href="#" >企业基本信息</a>'+
			'						<ul class="dropdown-menu">'+
			'							<li><a href="#" role="listBtn1" list-value="企业基本信息/所有类别">所有类别</a></li>'+
			'							<li><a href="#" role="listBtn2" list-value="企业基本信息/电力、热力生产和供应业">电力、热力生产和供应业</a></li>'+
			'						</ul>'+
			'					</li>' +
			'					<li><a href="#" role="listBtn8" list-value="企业能源消费结构">企业能源消费结构</a></li>' +
			'				</ul>' +
			'			</div>' +
			'			<input type="text" name="datasource" class="form-control" role="listBtnDisplay"   value="未选择" readonly="readonly"/>' +
			'		</div>' +
			'	    <div class="col-sm-12" style="margin-top:1.3em;"></div>' +
			'		<div class="input-group">' +
			'			<div class="input-group-btn">' +
			'				<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">选择数据处理类型  <span class="caret"></span></button>' +
			'				<ul class="dropdown-menu">' +
			'					<li><a href="#" role="listBtn3" list-value="通过均值法处理的数据">通过均值法处理的数据</a></li>' +
			'					<li><a href="#" role="listBtn4" list-value="通过置零法处理的数据">通过置零法处理的数据</a></li>' +
			'				</ul>' +
			'			</div>' +
			'			<input type="text" name="method" class="form-control" role="listBtnDisplay"   value="未选择" readonly="readonly"/>' +
			'		</div>' +
			'	    <div class="col-sm-12" style="margin-top:1.3em;"></div>' +
			'		<div class="input-group">' +
			'			<div class="input-group-btn">' +
			'			<input type="text"  class="form-control" style="width:136px" role="listBtnDisplay" value="输入年份：" readonly="readonly"/>' +
			'           <input type="text" name="year" class="form-control" style="width:264px" role="listBtnDisplay" />'+
			'			</div>' +
			'		</div>' +
			'	</div>' +
			'</div>' +
			'<div stepRole="3" class="col-sm-12">' +
			'	<h3>选择分析类型</h3>' +
			'	<div class="col-sm-10 col-md-8 col-lg-6">' +
			'		<div class="input-group">' +
			'			<div class="input-group-btn">' +
			'				<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">选择数据分析类型 <span class="caret"></span></button>' +
			'				<ul class="dropdown-menu">' +
			'					<li><a href="#" role="listBtn5" list-value="聚类">聚类</a></li>' +
			'					<li><a href="#" role="listBtn6" list-value="关联规则">关联规则</a></li>' +
			'					<li><a href="#" role="listBtn7" list-value="回归">回归</a></li>' +
			'					<li><a href="#" role="listBtn8" list-value="分类">分类</a></li>' +
			'					<li><a href="#" role="listBtn9" list-value="离群点检测">离群点检测</a></li>' +
			'					<li><a href="#" role="listBtn10" list-value="统计">统计</a></li>' +
			'				</ul>' +
			'			</div>' +
			'			<input type="text" name="analysistype" class="form-control"  role="listBtnDisplay" value="未选择" readonly="readonly"/>' +
			'		</div>' +
			'	</div>' +
			'</div>' +
			'<div stepRole="4" class="col-sm-12">' +
			'	<h3>选择分析算法</h3>' +
			'	<div class="col-sm-10 col-md-8 col-lg-6">' +
			'		<div class="input-group">' +
			'			<div class="input-group-btn" id="arithmeticgroup">' +
			'				<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">选择数据分析算法<span class="caret"></span></button>' +
			'				<ul class="dropdown-menu">' +
			'				</ul>' +
			'			</div>' +
			'			<input type="text" name="arithmetic" class="form-control" role="listBtnDisplay3" value="未选择" readonly="readonly"/>' +
			'		</div>' +
			'	</div>' +
			'</div>' +
			'<div stepRole="4" class="col-sm-12">' +
			'	<h3>设置参数</h3>' +
			'	<div class="col-sm-10 col-md-8 col-lg-6">' +
			'		<div class="input-group" id="params">' +
			'		</div>' +
			'	</div>' +
			'</div>' +
			'<div stepRole="5" class="col-sm-12">' +
			'	<div class="col-sm-12" style="margin-top:1.5em;"></div>' +
			'</div>' +
			'</div>';

		return templet;
	}


	
	function updateArithmetic(modal,body,recallFunc){
		var analysistype=$("input[name='analysistype']").val()
		$.ajax({
			url: 'analysisCategory/getArithmetic',
			type: 'GET',
			async: true,
			dataType: 'json',
			data:{"analysistype":analysistype},
			success: function(result) {
				template=""
				$.each(result.data,function(i,item){
					template=template+
					'<li><a href="#" role="listBtn10'+item.id+'" list-value="'+item.arithmetic+'">'+item.arithmetic+'</a></li>'
				})
				$("#arithmeticgroup").find("ul").children().remove()
				$("#arithmeticgroup").find("ul").append(template)
				$.each(result.data,function(i,item){
					regListBtn(modal.find("a[role='listBtn10"+item.id+"']").parents(".input-group"),"10"+item.id,function() {
						updateDataAnalysis(body)
						if(item.arithmetic=="DBSCAN"){
							$("#params").append(
							'		<div class="input-grouptem">' +
							'			<div class="input-group-btn">' +
							'			<input type="text"  class="form-control" style="width:100px" role="listBtnDisplay" value="领域半径值：" readonly="readonly"/>' +
							'           <input type="text" name="param1" class="form-control" style="width:90px" role="listBtnDisplay" value="10" />'+
							'			<input type="text"  class="form-control" style="width:120px" role="listBtnDisplay" value="领域密度阀值：" readonly="readonly"/>' +
							'           <input type="text" name="param2" class="form-control" style="width:90px" role="listBtnDisplay" value="2" />'+
							'			</div>' +
							'		</div>' 
					)
						}
					});
				})
	
			},
			error: function(data) {
				recallFunc(data);
			}
		});
	}
	
	function updateDataAnalysis(contentEle) {
		var datasource = {};
		var method = {};
		datasource['name'] = $(contentEle).find("input[name='datasource']").val() ||"未选择";
		datasource['method'] = $(contentEle).find("input[name='method']").val() ||"未选择";
		datasource['year'] = $(contentEle).find("input[name='year']").val() || "未选择";
		method["analysistype"] = $(contentEle).find("input[name='analysistype']").val() || "未选择";
		method["arithmetic"] = $(contentEle).find("input[name='arithmetic']").val() || "未选择";

		var templet =
			'<div class="col-sm-12">' +
			'<p>已选择的数据源：' + datasource.name + '</p>' +
			'<p>已选择的数据处理方法：' + datasource.method + '</p>' +
			'<p>已输入的年份：' + datasource.year + '</p>' +
			'<p>已选择的分析类型：' + method.analysistype + '</p>' +
			'<p>已选择的方法：' + method.arithmetic + '</p>' +
			'<p>当前任务状态：<div role="PreproccessTip">待提交</div></p>' +
			'<p><input type="button" id="submitDataAnalysis" class="btn btn-primary" value="确定并开始数据分析">'+
			'<input style="margin-left:30px;display:none" type="button" id="showAnalysisResult" class="btn btn-success" value="显示分析结果">'+
			'</p>' +
			'</div>';
		contentEle.find("div[stepRole='5']").children("div").html(templet);
		$("#submitDataAnalysis").click(function() {
			var params=$(contentEle).find("input[name='param1']").val()+","+$(contentEle).find("input[name='param2']").val();
			if(datasource.name != "未选择" &&
					datasource.method != "未选择" &&
					datasource.year != "未选择" &&
					method.analysistype != "未选择" &&
					method.arithmetic != "未选择") {
				contentEle.children("div:last").children("div").find("div[role='PreproccessTip']")
				.html(getLoadingAnimation("50px", "50px", "处理中..."));
				$(this).attr("disabled", "disabled");
				$(this).val("执行中...");
				ajaxSubmitDataAnalysisJob(contentEle, datasource.name,datasource.method, datasource.year, method.analysistype,method.arithmetic,params);
			} else {
				contentEle.children("div:last").children("div").find("div[role='PreproccessTip']").text("数据源或方法未选择！");
			}
		});
		
		$("#showAnalysisResult").click(function() {
			var resultUuid= $("input[name='Resultuuid']").val()
			loadModal(resultUuid,$('#modalDiaglog'))
		});
	}


//	ajax进行数据分析  运行spark
	function ajaxSubmitDataAnalysisJob(contentEle, datasource,datamethod, year,method,arithmetic,param) {
		
		$.ajax({
			url: 'dataAnlysisSys/newJob/Submit/'+methodToUrl(method),
			type: 'POST',
			async: true,
			dataType: 'json',
			data: {
				"datasource": datasource,
				"datamethod": datamethod,
				"year":year,
				"method": method,
				"arithmetic":arithmetic,
				"param":param
			},
			success: function(returndata) {
				if(returndata.status == 0) {
					contentEle.children("div:last").children("div").find("div[role='PreproccessTip']").text("任务已完成！");
					$("#showAnalysisResult").attr("style","margin-left:30px")
					$("input[name='Resultuuid']").val(returndata.uuid)
					$("#submitDataAnalysis").removeAttr("disabled");
					$("#submitDataAnalysis").val("再次执行");
				} else {
					contentEle.children("div:last").children("div").find("div[role='PreproccessTip']")
					.html('<span style="color:red;font-weight:bold;">任务已中止，' + returndata.info + '</span>');
					$("#submitDataAnalysis").removeAttr("disabled");
					$("#submitDataAnalysis").val("重试");
				}
			},
			error: function(returndata) {
				if(returndata.status < 500 && returndata.status >= 400 || returndata.status == 200) {
					ajaxError(returndata);
				}
				contentEle.children("div:last").children("div").find("div[role='PreproccessTip']")
				.html('<span style="color:red;font-weight:bold;">任务异常中止！代码：' + returndata.status + '</span>');
				$("#submitDataAnalysis").removeAttr("disabled");
				$("#submitDataAnalysis").val("重试");
			}
		});
	}

   function methodToUrl(method){
	   switch(method){
	   case "聚类": 
		   return "clustering";
	   	   break;
	   case "统计":
		   return "statistics";
	   	   break;
	   
	   }
	   
   }


	function ajaxRequestDataAnalysisHistory(recallFunc) {
		$.ajax({
			url: 'dataAnlysisSys/records',
			type: 'GET',
			async: true,
			dataType: 'json',
			success: function(data) {
				recallFunc(data);
			},
			error: function(data) {
				recallFunc(data);
			}
		});
	}

	//显示数据分析历史数据
	function displayDataAnalysisHistory(reports, displayTableEle) {
		displayTableEle.children("tbody").empty();
		if(reports.length <= 0) {
			displayTableEle.children("tbody").html("<tr class='info'><td colspan='999' style='text-align:center;font-weight:bold;color:#333;'>还没有创建任何报告</td></tr>");
		} else {
			$.each(reports, function(i, item) {
				var dataTemplet =
					'<tr class="success"  style="color:#333;">' +
					'	<td> '+item.analysisMethod +' </td>' +
					'	<td>' + item.arithmetic + '</td>' +
					'	<td>' +formatDatetimeOfJsonObject(item.jobDateTime) + '</td>' +
					'   <td>' +item.industrySort  +' </td>'+
					'	<td>' +item.username + '</td>' +
					'</tr>';
				displayTableEle.children("tbody")
				.append(dataTemplet);
			});

		}
	}
	
	//显示分析结果图，方便用户查看和进行数据设置
	
	function loadModal(uuid, modal) {
		var title = modal.find('.modal-header');
		var body = modal.find('.modal-body');
		var footer = modal.find('.modal-footer');
		var method = $("input[name='analysistype']").val()
		var uuid = $("input[name='Resultuuid']").val()
		var inputtemplate = '<div class="input-group" id="crowedgroup">'
		var num=0
		switch(method) {
		case "聚类":
			title.html("聚类结果")
			body.html(getReportDetailsTemplet(modal.height(), modal.width()));
			ajaxClusterResultOptionData1(uuid,function(data) {
				$.each(data.legend,function(index,value){
					inputtemplate=inputtemplate+
					'<span class="input-group-addon">簇组'+value+'</span>'+
					'<input class="col-md-4 form-control"  type="text" name="crowed'+index+'"/>'
				})
				inputtemplate=inputtemplate+'</div><br /><input class="col-md-4 btn btn-primary" type="button" name="crowedsubmit" value="提交" />'
				var json=clusterOption(data)
				addDiagram(modal.find("[role='diagramContainer']"), false, json);
				modal.find("[role='install']").children("h3").html("设置簇别名")
				modal.find("[role='install']").append(inputtemplate)
				$("input[name='crowedsubmit']").click(function(){
					ajaxupdateClusterName(uuid,function(data){
						body.html(getReportDetailsTemplet(modal.height(), modal.width()));
						var json=clusterOption(data)
						addDiagram(modal.find("[role='diagramContainer']"), false, json);
					})
				})
			})
			break;
		default:
			break;
		}

	}
	
	function ajaxupdateClusterName(uuid,recallFunc){
		var json={}
		$("#crowedgroup input").each(function(index){
			var crowed=$(this).prev().text()
			json[crowed]= $("input[name='crowed"+index+"']").val()
		})

		$.ajax({
			url: 'dataAnlysisSys/clustering/modify/'+uuid,
			type: 'POST',
			async: true,
			dataType: 'json',
			data: {"json":JSON.stringify(json)},
			success: function(report) {
				recallFunc(report);
			},
			error: function(data) {
				console.log(data);
			}
		})
	}
	
	function ajaxClusterResultOptionData1(uuid,recallFunc){
		$.ajax({
			url: 'diagrams/result/cluster/'+ uuid +'/',
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
	
	function getReportDetailsTemplet(width, height) {
		var width = width;
		var height = screen.height * 0.4;
		var templet =
			'<div class="row">' +
			'	<div class="col-md-12" role="diagramContainer" style="height:' + height + 'px;width:' + width + 'px;">' +
			'	</div>' +
			'	<div class="col-md-12" role="install">' +
			'		<h3></h3>' +
			'	</div>' +
			'</div   >';
		return templet;
	}
	
	function clusterOption(data){
		
	    var title=data.title
		var list=data.data
		var serie=[]
		$.each(list, function(i, item) {
			var tag=""
			if(item.clustertagalias!=""){
				tag=item.clustertagalias
			}else{
				tag=item.clustertag
			}
			template={
		        		large: true,
		        		name: tag,
		        		type: 'scatter',
		        		symbolSize: 5,
		        		data: [[item.x2D,item.y2D,item.clustertag,item.clustertag,item.companyName]]
		   			},
		    serie.push(template)
		})

		var option = {
		        backgroundColor: 'transparent',
			    title : {
			        text: title,
			    },
			    grid: {
			        left: '3%',
			        right: '7%',
			        bottom: '3%',
			        containLabel: true
			    },
			    tooltip : {
			        showDelay : 20,
			        formatter : function (params) {return '公司名称：'+params.value[3]+'</br>所属类别：'
			        			+params.value[2]+'</br>单位增加值能耗：'+params.value[0]+'</br>能源消费占总成本比：'
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
			    
			    legend: {
			        data: data.legend,
			        orient: 'vertical',
			        x: 'right',
			        y: 60
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
///////////////////////////////////////////////////////////////
/*************************************************************/
/*************************************************************/
/**************   Ajax Contents - Reports Gen    *************/
/*************************************************************/
/*************************************************************/
///////////////////////////////////////////////////////////////


		ajaxRequestReportsHistory(function(reports) {
			displayReportHistory(reports, $("#dataRepHistory"));
		});

		$("#dataRepContent").find("a[role='customeLauncher']").click(function() {
			var modal = $('#modalDiaglog');
			var title = modal.find('.modal-header');
			var body = modal.find('.modal-body');
			var footer = modal.find('.modal-footer');
			title.text("新建分析报告");
			body.children().remove()
			footer.children().remove()
			body.append(getNewReportTemplet());
			$("#newReportMenu").append(getRadioInfoBtnTemplet("elec-heat", "menuItems", "电力、热力生产和供应业"))
			$("#newReportMenu").append(getRadioInfoBtnTemplet("pandect", "menuItems", "总览"))
			$("#newReportMenu").append(getRadioInfoBtnTemplet("other", "menuItems", "其他"))
			regInfoBtnGroup(body, function() {});
			$("#newReportDataSource").append(getListBtnTemplet());
			
			var data = [{
				"name": "聚类",
				"value": "聚类"
			},{
				"name": "能源消费结构统计",
				"value": "能源消费结构统计"
			},{
				"name": "其他",
				"value": "其他"
			}];
			fillListBtnList($("#newReportDataSource").children(".input-group"), data);
			$("#newReportDataSource").children("div:last").after("<br /><div class='col-sm-12'></div>");
			regListBtn($("#newReportDataSource").children(".input-group"),1, function(target) {
				ajaxRequestDataSource("聚类", function(ds) {
					var container = $("#newReportDataSource").children("div:last");
					container.empty();
					$.each(ds, function(i, item) {
						container.append(getRadioInfoBtnTemplet(item.uuid, "reportDS",formatDatetimeOfJsonObject(item.jobDateTime)))
						if(i == 0) {
							container.find("input[type='radio']").attr("checked", "checked");
						}
					});
					regInfoBtnGroup($("#newReportDataSource"), function() {});
				});
			});
			regListBtn($("#newReportDataSource").children(".input-group"),2, function(target) {
				ajaxRequestDataSource("能源消费结构统计", function(ds) {
					var container = $("#newReportDataSource").children("div:last");
					container.empty();
					$.each(ds, function(i, item) {
						container.append(getRadioInfoBtnTemplet(item.uuid, "reportDS",formatDatetimeOfJsonObject(item.jobDateTime)))
						if(i == 0) {
							container.find("input[type='radio']").attr("checked", "checked");
						}
					});
					regInfoBtnGroup($("#newReportDataSource"), function() {});
				});
			});
			
			regListBtn($("#newReportDataSource").children(".input-group"),3, function(target) {
				ajaxRequestDataSource("other", function(ds) {
					var container = $("#newReportDataSource").children("div:last");
					container.empty();
					$.each(ds, function(i, item) {
						container.append(getRadioInfoBtnTemplet(item.uuid, "reportDS",item.jobDateTime.substring(0,10)+" "+item.jobDateTime.substring(11)))
						if(i == 0) {
							container.find("input[type='radio']").attr("checked", "checked");
						}
					});
					regInfoBtnGroup($("#newReportDataSource"), function() {});
				});
			});

//			display footer 
			var footerTemplet =
				'<div class="text-info pull-left" style="font-size:x-large;font-weight:bold;">状态：<span>尚未提交</span></div>' +
				'<input type="submit" form="newReport" class="btn btn-primary" />' +
				'<input type="reset" form="newReport" value="全部重填" class="btn btn-danger">';
			footer.removeAttr("hidden");
			footer.html(footerTemplet);

			$("#newReport").submit(function() {
				var title = $("#newReportTitle").val();
				var industrySort= $("#newReportMenu").find("input[type='radio']:checked").val();
				console.log($("#newReportTextArea").val());
				var text = return2Br($("#newReportTextArea").val());
				var ds_method = $("#newReportDataSource").find("input[role='listBtnDisplay']").val();
				var ds_uuid = $("#newReportDataSource").find("input[name='reportDS']:checked").val();
				var report = {};
				report["title"] = title;
				report["text"] = text;
				report["industrySort"] = industrySort;
				report["ds_method"] = ds_method;
				report["ds_uuid"] = ds_uuid;
				footer.find("span").text("提交中...");
				ajaxSubmitNewReport(report, function(data) {
					if(data.status == "200") {
						footer.find("span").text("创建成功！");
						footer.children("div").removeClass("text-info text-danger").addClass("text-success");
					} else {
						footer.find("span").text("创建失败！");
						footer.children("div").removeClass("text-info text-success").addClass("text-danger");
					}
				});
				return false;
			});
		});


	function ajaxRequestReportsHistory(recallFunc) {
		$.ajax({
			url: 'report/allReports',
			type: 'GET',
			async: true,
			dataType: 'json',
			data: {},
			success: function(data) {
				recallFunc(data);
			},
			error: function(data) {
				recallFunc(data);
			}
		});
	}

	function displayReportHistory(reports, displayTableEle) {
		displayTableEle.children("tbody").empty();
		if(reports.length <= 0) {
			displayTableEle.children("tbody").html("<tr class='info'><td colspan='999' style='text-align:center;font-weight:bold;'>还没有创建任何报告</td></tr>");
		} else {
			$.each(reports, function(i, item) {
				var dataTemplet =
					'<tr class="success"  style="color:#333;">' +
					'	<td>' + item.title + '</td>' +
					'	<td>' +formatDatetimeOfJsonObject(item.generateDateTime )+ '</td>' +
					'	<td>' + item.industrySort + '</td>' +
					'	<td>' + item.username + '</td>' +
					'</tr>';
				displayTableEle.children("tbody")
				.append(dataTemplet);
			});

		}
	}

	function getNewReportTemplet() {
		templet =
			'<div class="row">' +
			'	<form id="newReport" class="from-group" action="#" >' +
			'		<div class="col-sm-12">' +
			'			<lable for="newReportTitle">报告标题：</lable>' +
			'			<input id="newReportTitle" class="form-control" type="text" pattern="[^x00-xff 0-9]{0,16}|[A-z 0-9]{0,32}|[a-zA-Z\u4e00-\u9fa5 0-9]{0,20}" placeholder="报告标题，汉字(不超过16个字符)或英文(不超过32个字符)，包含空格" required="required"/>' +
			'		</div>' +
			'		<div id="newReportMenu" class="col-sm-12">' +
			'			<br />' +
			'			<p>选择所属行业大类：</p>' +
			'		</div>' +
			'		<div id="newReportDataSource" class="col-sm-12">' +
			'			<br />' +
			'			<p>选择数据源：</p>' +
			'		</div>' +
			'		<div  class="col-sm-12">' +
			'			<br />' +
			'			<lable for="newReportTitle">报告说明文字：</lable>' +
			'			<textarea id="newReportTextArea" form="newReport" class="form-control" rows="5" cols="30" maxlength="1000" wrap="hard" placeholder="中文或英文，最多1000个字符"></textarea>' +
			'		</div>' +
			'	</div>';
		return templet;
	}

	function ajaxRequestDataSource(method, recallFunc) {
		$.ajax({
			url:'dataAnlysisSys/record',
			type: 'GET',
			async: true,
			dataType: 'json',
			data:'method='+method,
			success: function(data) {
				recallFunc(data);
			},
			error: function(data) {
				ajaxError(data);
			}
		});
	}

	function ajaxSubmitNewReport(reportData, recallFunc) {
		console.log(reportData);
		$.ajax({
			url: 'report/new/',
			type: 'PUT',
			async: true,
			dataType: 'json',
			data: {
				"title": reportData.title,
				"texts": reportData.text,
				"datasourceUuid": reportData.ds_uuid,
				"industrySort": reportData.industrySort,
				"method": reportData.ds_method,
			},
			success: function(data) {
				recallFunc(data);
			},
			error: function(data) {
				recallFunc(data);
			}
		});
	}	
	
	
	function getListBtnTemplet() {
		var templet =
			'		<div class="input-group">' +
			'			<div class="input-group-btn">' +
			'				<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">选择数据源类别  <span class="caret"></span></button>' +
			'				<ul class="dropdown-menu">' +
			'					<li><a href="#" role="listBtn" list-value="default">default</a></li>' +
			'				</ul>' +
			'			</div>' +
			'			<input type="text" class="form-control" role="listBtnDisplay" value="未选择" readonly="readonly"/>' +
			'		</div>';
			return templet;
	}
	
	
	function regInfoBtnGroup(contentEle, recallFunc) {
		var contentEle = contentEle || $("body");
		contentEle.find("input[role='infoBtn']").click(function() {
			var targetInput = $(this).siblings("span").children("input");
			targetInput.prop("checked", true);
			recallFunc();
		});
		contentEle.find("input[name='radioBox']").click(function() {
			recallFunc();
		});

	}
	
	function fillListBtnList(listBtnEle,data){
		listBtnEle.find("ul").empty();
		$.each(data, function(i,item) {	
			i=i+1
			listBtnEle.find("ul").append('<li><a href="#" role="listBtn'+i+'" list-value="'+item.value+'">'+item.name+'</a></li>');
		});
	}
	
	
	function return2Br(str) {
		return str.replace(/\r?\n/g,"<br />");
	}

	function removeBr(str){
		return str.replace(/<br \/>/g,"");
	}
	
	//日期格式化
	function formatDatetimeOfJsonObject(datetimeData) {
		var uploadDate = datetimeData.date;
		var uploadTime = datetimeData.time;
		var datetime = uploadDate.year + "-" + uploadDate.month + "-" + uploadDate.day;
		datetime = datetime + "  " + uploadTime.hour + ":" + uploadTime.minute + ":" + uploadTime.second;
		return datetime;
	}
})
/*
 * id:0
 * author：qiuyiqiang1995@foxmail.com
 * function: utils
 * date:2017.06.01
 */

// CSRF Defence header producer
function getHeaderToken() {
	var header = $("meta[name='_csrf_header']").attr("content");
	var token = $("meta[name='_csrf']").attr("content");
	var headerToken = {};
	headerToken[header] = token;
	return headerToken;
}

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

function doAjaxLogOut() {
	var csrfHeader = getHeaderToken();
	$.ajax({
		url: rootPath + '/auth/logout/',
		type: 'POST',
		async: true,
		headers: csrfHeader,
		dataType: 'json',
		contentType: 'application/x-www-form-urlencoded;charset:utf-8;',
		data: {
			logout: true
		},
		success: function() {
			location.reload();
		},
		error: function() {
			location.reload();
		}
	});
}



function ajaxUpload(formEle, recallFunc) {
	var formData = new FormData(formEle[0]);
	var headerToken = getHeaderToken();
	$.ajax({
		url: rootPath + '/upload/singleUpload/',
		type: 'PUT',
		headers: headerToken,
		data: formData,
		async: true,
		cache: false,
		contentType: false,
		processData: false,
		success: function(returndata) {
			recallFunc(returndata);
		},
		error: function(returndata) {
			switch(returndata.status) {
				case 403:
					alert('身份认证失效！');
					doAjaxLogOut();
					break;
				case 400:
					alert('上传失败，表单信息错误');
					break;
				case 500:
					alert('上传失败，服务器出错！');
					break;
				default:
					alert("上传失败！STATUS CODE=" + returndata.status);
					location.reload();
					break;
			}
		}
	});
}

function ajaxQueryUploadRecord(target, recallFunc) {
	var headerToken = getHeaderToken();
	$.ajax({
		url: rootPath + '/upload/records/',
		headers: headerToken,
		type: 'GET',
		async: true,
		dataType: 'json',
		contentType: 'application/json;charset:utf-8;',
		data: {
			"uploadTarget": target
		},
		success: function(returndata) {
			recallFunc(returndata);
		},
		error: function(returndata) {
			ajaxError(returndata);
		}
	});
}

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

/**
 * 
 * @param {Object} height
 * @param {Object} width
 * @param {Object} text
 */
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

function formatDatetimeOfJsonObject(datetimeData) {
	var uploadDate = datetimeData.date;
	var uploadTime = datetimeData.time;
	var datetime = uploadDate.year + "-" + uploadDate.month + "-" + uploadDate.day;
	datetime = datetime + "  " + uploadTime.hour + ":" + uploadTime.minute + ":" + uploadTime.second;
	return datetime;
}

/**
 * 
 * @param {Object} value
 * @param {Object} groupName
 * @param {Object} text
 */
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

function getCheckboxInfoBtnTemplet(value, groupName, text) {
	var templet =
		'<div class="col-sm-3">' +
		'	<div class="input-group">' +
		'		<span class="input-group-addon">' +
		' 	       <input name="' + groupName + '" type="checkbox" value="' + value + '">' +
		'	   	</span>' +
		'		<input type="button" role="infoBtn" related-box="' + value + '"  value="' + text + '" class="form-control" readonly="readonly"/>' +
		'	</div>' +
		'</div>';
	return templet;
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



function regListBtn(listBtnEle, i,recallFunc) {
	var target = listBtnEle.find("a[ role='listBtn"+i+"']");
	target.click(function() {
		listBtnEle.children("input").val(target.text());
		listBtnEle.children("input").attr("value",target.attr("list-value"));
		listBtnEle.children("div").removeClass("open");
		recallFunc(target.attr("list-value")); //do some operation after select a btn
		return false;
	});
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

//id:3
function doOffsetFix() {
	var target = $(location.hash);
	var offVal = 125;
	if(target.length == 1) {
		var top = target.offset().top - offVal;
		if(top > 0) {
			$('html,body').animate({
				scrollTop: top
			}, 100);
		}
	}
}
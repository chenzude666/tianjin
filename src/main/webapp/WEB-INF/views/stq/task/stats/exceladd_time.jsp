<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="huake" uri="/huake"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>

<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
	<link href="${ctx}/static/bootstarp-fileinput/css/fileinput.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/static/bootstarp-fileinput/js/fileinput.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstarp-fileinput/js/zh.js" type="text/javascript"></script>
	<title>新增任务</title>
	<style type="text/css"> 
		.error{ 
		color:Red; 
		} 
		
		#left{float: left;}
		#right{float: right;}
	</style> 
	<link rel="stylesheet" type="text/css" media="screen" href="${ctx}/static/datetimepicker/bootstrap-datetimepicker.min.css">
</head>
<body>
	<div class="page-header">
			<h4><huake:getWordsTaskTypeTag taskType="${taskType}"></huake:getWordsTaskTypeTag>新增任务</h4>
			<!--a class="label" href="${excelmubanDownUrl}${mubanname}">Excel模板下载</a-->
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}
		</div>
	</c:if>
	<form id="inputForm" method="post" Class="form-horizontal" action="${ctx}/manage/stq/stats/task/savaexcel/${taskType}" enctype="multipart/form-data" onsubmit="return checkSubmit()">
		<div class="control-group">
			<div class="control-group">
				<div class="controls">
					<a class="muban" href="${excelmubanDownUrl}${mubanname}">Excel 上传模板点击下载</a>、<a class="muban" href="${excelmubanDownUrl}${cibaoReadMe}">导出原文说明文档点击下载</a>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label" for="secondName"></label>
				<div class="controls">
					<a href="#" class="img-rounded"> <img
						src="${excelmubanDownUrl}/images/${imagename}"
						alt="通用的占位符缩略图">
					</a>
				</div>
			</div>			
			<div class="control-group">
				<label class="control-label" for="secondName">检测项名称：</label>
				<div class="controls">
					<input type="text" name="task_name" id="task_name" class="input-large " >
				</div>
			</div>
			
			
			<div class="control-group">
				<label for=fileInput class="control-label">导入Excel文件:</label>
				<div class="controls">
					<input id="fileInput" class="file" name="fileInput" type="file" multiple >
				</div>
			</div>
			<c:if test="${taskType!='weibo_toComment' }">
				<div class="control-group">
						<label class="control-label" for="datetimepickerStart">起始时间（含）：</label>
						<div class="controls">
							<div id="datetimepickerStart" class="input-append date">
								<input type="text" name="search_EQ_dateFrom" value="${param.search_EQ_dateFrom == null ? dateFrom : param.search_EQ_dateFrom }" id="dateFrom" onblur="testTime()"></input> 
								<span class="add-on"> 
									<i data-time-icon="icon-time" data-date-icon="icon-calendar" onblur="testTime()"></i>
								</span>
							</div>
							<span id="error_dateFrom" class="error" hidden="hidden">起始查询时间必须填写</span>
						</div>
					</div>
					<div class="control-group">
						<label class="control-label" for="datetimepickerEnd">结束时间（含）：</label>
						<div class="controls">
							<div id="datetimepickerEnd" class="input-append date">
								<input type="text" name="search_EQ_dateTo" value="${param.search_EQ_dateTo == null ? dateTo : param.search_EQ_dateTo}" id="dateTo" onsubmit="return testTime()" onblur="testTime()" ></input>
								<span class="add-on"> 
									<i data-time-icon="icon-time" data-date-icon="icon-calendar" onblur="testTime()"></i>
								</span>
							</div>
							<span id="error_dateTo" class="error" hidden="hidden">结束查询时间必须填写</span>
							<span id="error_dateTo_vs_dateFrom" class="error" hidden="hidden">结束时间不能小于开始时间</span>
						</div>
					</div>
			</c:if>
			<div class="control-group ">
				<label class="control-label" for="status">状态：</label>
				<div class="controls">
					<select name="status">
						<option value="1" selected="selected">有效</option>
						<option value="0">无效</option>
					</select>
				</div>
			</div>
			</div>
		
			
		<div class="form-actions">
			<button type="submit" class="btn btn-primary" id="submit">保存</button>
			<a href="<%=request.getContextPath()%>/manage/stq/stats/task/index/${taskType}" class="btn btn-primary">返回</a>
			<a href="<%=request.getContextPath()%>/manage/stq/stats/task/add/${taskType}" class="btn btn-info">切换至单条导入任务</a>
		</div>
	</form>
	<script type="text/javascript" src="${ctx}/static/datetimepicker/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript">
		function selectAll(){  
		    if ($("#functions").prop("checked")) {
		        $("input[id='functions']").prop("checked", false);  
		    } else {  
		    	$("input[id='functions']").prop("checked", true);  
		    }  
		}	
		
		function selectAll2(){  
		    if ($("#functions2").prop("checked")) {
		        $("input[id='functions2']").prop("checked", false);  
		    } else {  
		    	$("input[id='functions2']").prop("checked", true);  
		    }  
		}
		
	    $('#fileInput').fileinput({
	        language: 'zh',
	        allowedFileExtensions : ['xlsx'],
	        maxFileCount: 1,
            showCaption: false,    //不显示标题
            showUpload: false,      //不显示上传按钮
	    });
		$(function() {
			$("#inputForm").validate({
				rules : {
					task_name:{
						required : true
					},
					fileInput : {
						required : true
					},
					status : {
						required : true
					},
					seeks : {
						required : true
					}
				},
				messages : {
					task_name : {
						required : "检测项名称必须填写"
					},
					fileInput : {
						required : "必须上传Excel文件"
					},
					status : {
						required : "必须填写"
					},
					seeks : {
						required : "必须填写"
					}
				}
			});
		})
		
		function testTime(){
			var flag = true;
			var dateFrom = document.getElementById("dateFrom").value;
			var dateTo = document.getElementById("dateTo").value;
			if(dateFrom != ""&& dateTo != ""){
				dateFrom = new Date(dateFrom).getTime();
				dateTo = new Date(dateTo).getTime();
				dateNow = new Date().getTime();
				if(dateFrom>dateTo){
					alert("注意结束时间小于了起始时间，请修改")
					$("#dateTo").val("");
					flag = false;
				}
				if(dateTo>dateNow){
					alert("注意结束时间不能大于当前时间，请修改")
					$("#dateTo").val("");
					flag = false;
				}
			}
			return flag;
		}
		
		$("#storeId").change(function(){
			var storeName = $("#storeId").val();
			$("#storeName").empty();
			if(storeName!=""){
				$("#storeName").text("（"+$("#storeId").val()+"）");
			}else{
				$("#storeName").text("");
			}
		})
	    $("#condition").click(function(){
	    	if($("#condition").text() == "开启筛选条件"){
	    		$("#condition").text("关闭筛选条件");
	    		$("#conditionX").show();
	    	}else{
	    		$("#condition").text("开启筛选条件");
	    		$("#conditionX").hide();
	    		$("input[type='checkbox']").attr("checked", false);
	    	}
	    });
		$('#datetimepickerStart').datetimepicker({
			format : 'yyyy-MM-dd',
			language : 'en',
			pickDate : true,
			pickTime : true,
			hourStep : 1,
			minuteStep : 15,
			secondStep : 30,
			inputMask : true
		});
		$('#datetimepickerEnd').datetimepicker({
			format : 'yyyy-MM-dd',
			language : 'en',
			pickDate : true,
			pickTime : true,
			hourStep : 1,
			minuteStep : 15,
			secondStep : 30,
			inputMask : true
		});
		
		var checkSubmitFlg = false; 

		function checkSubmit(){ 
			var taskName = document.getElementById("task_name").value;
			var fileInput = document.getElementById("fileInput").value;
			if(taskName!="" && fileInput!=""){
				$("#submit").attr("disabled","true")
			}
		}
		
		function selectAll(id){  
		    if ($("#functions_"+id).prop("checked")) {
		        $("input[id='functions_"+id+"']").prop("checked", false);  
		    } else {  
		    	$("input[id='functions_"+id+"']").prop("checked", true);  
		    }  
		}	
		
	</script>
</body>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
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
	</style> 
</head>
<body>
	<div class="page-header">
			<h4>新增任务</h4>
			<!--a class="label" href="${excelmubanDownUrl}${mubanname}">Excel模板下载</a-->
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}
		</div>
	</c:if>
	<form id="inputForm" method="post" Class="form-horizontal" action="${ctx}/manage/stq/task/save" enctype="multipart/form-data">
		<div class="control-group">
			
			<div class="controls">
				<a class="muban" href="${excelmubanDownUrl}${mubanname}">如有需要点击此处下载Excel模板</a>
				
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="secondName">任务分类：</label>
			<div class="controls">
				<input type="text" name="taskTypeName" class="input-large " value="${taskTypeName}" readonly="readonly"/>
				<input type="hidden" name="taskType" value="${taskType}" > 
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" for="secondName">检测项名称：</label>
			<div class="controls">
				<input type="text" name="task_name" class="input-large " >
			</div>
		</div>
		
		<div class="control-group">
			<label for=fileInput class="control-label">导入Excel文件:</label>
			<div class="controls">
				<input id="fileInput" class="file" name="fileInput" type="file" multiple >
			</div>
		</div>
				
		<div class="control-group ">
			<label class="control-label" for="status">状态：</label>
			<div class="controls">
				<select name="status">
					<option value="1" selected="selected">有效</option>
					<option value="0">无效</option>
				</select>
			</div>
		</div>
		<div class="page-header">
			<span class="label">选择数据指标</span>
		</div>			
			
		<div class="control-group">
			<label class="control-label" onclick="selectAll();">（常规计算）:</label>
			<div class="controls">
				<c:forEach items="${dataIndexMap}" var="item" varStatus="i">
					<c:if test="${item.value == '情感分析'}">
						<label class="checkbox inline">
							<input type="checkbox" class="box" name="dataIndex" value="${item.key}" id="functions"/>
							<span>${item.value}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</label>
					</c:if>
				</c:forEach>					
			</div>
		</div>
		
		<div class="form-actions">
			<button type="submit" class="btn btn-primary" id="submit">保存</button>
			<a href="<%=request.getContextPath()%>/manage/stq/task/index?taskType=${taskType}" class="btn btn-primary">返回</a>
		</div>
	</form>
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
					taskType : {
						required : true
					},
					fileInput : {
						required : true
					},
					status : {
						required : true
					},
					dataIndex : {
						required : true
					}
				},
				messages : {
					taskType : {
						required : "必须填写"
					},
					fileInput : {
						required : "必须上传Excel文件"
					},
					status : {
						required : "必须填写"
					},
					dataIndex : {
						required : "必须填写"
					}
				}
			});
		})
	</script>
</body>
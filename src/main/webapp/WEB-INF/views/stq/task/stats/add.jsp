<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="huake" uri="/huake"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<title>统计模块</title>
    <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/static/datetimepicker/bootstrap-datetimepicker.min.css">
</head>
<body>
	<div>
		<div class="page-header">
			<h4>
				<huake:getWordsTaskTypeTag taskType="${taskType}"></huake:getWordsTaskTypeTag>新增任务
			</h4>
		</div>
		<div class="container-fluid">
			<form id="inputForm" method="post" Class="form-horizontal" onsubmit="return testTime()" action="${ctx}/manage/stq/stats/task/saveone/${taskType}">
				<div class="control-group">
					<div class="controls">
						<!--a class="muban" href="${excelmubanDownUrl}${cibaoReadMe}">原文导出说明文档点击下载</a-->
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="secondName">检测项名称：</label>
					<div class="controls">
						<input type="text" name="task_name" class="input-large " >
					</div>
				</div>
				
				<div class="control-group">
				 <label class="control-label" for="secondName">
						<c:choose>
							<c:when test="${taskType=='weibo_kol'||taskType=='weibo_involvement'
							||taskType=='weibo_depth'||taskType=='weibo_topic_frequency_volume'
							||taskType=='weibo_kol_crawled'||taskType=='weibo_addAccounts'}">
									微博账号URL：
							</c:when>
							<c:when test="${taskType=='weibo_toComment'}">
									微博文章URL：
							</c:when>
							<c:when test="${taskType=='weibo_fans_follow_number'}">
									微博账号或者账号URL：
							</c:when>
							
							<c:when test="${taskType=='weixin_kol'}">
									微信文章URL：
							</c:when>
							<c:when test="${taskType=='weixin_kol_crawled'}">
									微信文章URL或者微信号：
							</c:when>
							<c:otherwise>
									URL：
							</c:otherwise>
						</c:choose>
					</label>
					<div class="controls">
					<input type="text" name="key_word" class="input-large " >
					<a class="intro" title="${tishi }">
						<c:choose>
								<c:when test="${fn:length(tishi)>20}">
									<c:out value="${fn:substring(tishi,0,28) }(注意! url请使用http,非https)" />
								</c:when>
								<c:otherwise>
									<c:out value="${tishi}(注意! url请使用http,非https)" />
								</c:otherwise>
						</c:choose>
					</a>
					</div>
				</div>
				<c:if test="${!(taskType == 'weibo_toComment'||taskType == 'weibo_addAccounts')}">
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
								<input type="text" name="search_EQ_dateTo" value="${param.search_EQ_dateTo == null ? dateTo : param.search_EQ_dateTo}" id="dateTo" onblur="testTime()"></input>
								<span class="add-on"> 
									<i data-time-icon="icon-time" data-date-icon="icon-calendar" onblur="testTime()"></i>
								</span>
							</div>
							<span id="error_dateTo" class="error" hidden="hidden">结束查询时间必须填写</span>
							<span id="error_dateTo_vs_dateFrom" class="error" hidden="hidden">结束时间不能小于开始时间</span>
						</div>
					</div>
				</c:if>
				
				<c:if test="${taskType == 'weibo_addAccounts'}">
					<div class="control-group ">
						<label class="control-label" for="toLog">是否日常监控：</label>
						<div class="controls">
							<select name="toLog">
								<option value="1" >是</option>
								<option value="0" selected="selected">否</option>
							</select>
							<span class="help-inline">是否为需要监测账号</span>
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
		
				<div class="form-actions">
						<button type="submit" class="btn btn-primary" id="submit">保存</button>
						<a href="<%=request.getContextPath()%>/manage/stq/stats/task/index/${taskType}" class="btn btn-primary">返回</a>
						<a href="<%=request.getContextPath()%>/manage/stq/stats/task/exceladd/${taskType}" class="btn btn-info">切换至Excel导入任务</a>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/datetimepicker/bootstrap-datetimepicker.min.js"></script>
	<script type="text/javascript">

		function selectAll(){  
		    if ($("#functions").prop("checked")) {
		        $("input[id='functions']").prop("checked", false);  
		    } else {  
		    	$("input[id='functions']").prop("checked", true);  
		    }  
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

		$(function() {
			$("#inputForm").validate({
				rules:{
					task_name:{
						required:true
					},
					key_word:{
						required:true
					},
					search_EQ_dateFrom:{
						required:true
					},
					search_EQ_dateTo:{
						required:true
					},
					seeks : {
						required : true
					}
				},messages:{
					task_name:{
						required:"检测项名称必须填写"
					},
					key_word:{
						required:"检测项关键词必须填写"
					},
					search_EQ_dateFrom:{
						required:"起始时间必须填写"
					},
					search_EQ_dateTo:{
						required:"结束时间必须填写"
					},seeks : {
						required : "必须填写"
					}
				}
			});
		});
		
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
		
		function selectAll(id){  
		    if ($("#functions_"+id).prop("checked")) {
		        $("input[id='functions_"+id+"']").prop("checked", false);  
		    } else {  
		    	$("input[id='functions_"+id+"']").prop("checked", true);  
		    }  
		}	
		
	</script>
</body> 	
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
	<link href="${ctx}/static/bootstarp-fileinput/css/fileinput.css" type="text/css" rel="stylesheet" />
	<script src="${ctx}/static/bootstarp-fileinput/js/fileinput.js" type="text/javascript"></script>
	<script src="${ctx}/static/bootstarp-fileinput/js/zh.js" type="text/javascript"></script>
	 <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/static/datetimepicker/bootstrap-datetimepicker.min.css">
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
	<form id="inputForm" method="post" Class="form-horizontal" onsubmit="return testTime()" action="${ctx}/manage/stq/task/saveone">
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
					<label class="control-label" for="secondName">
						关键词：
					</label>
					<div class="controls">
					<input type="text" name="key_word" class="input-large " >
					<a class="intro" title="${tishi }">
						<c:choose>
								<c:when test="${fn:length(tishi)>20}">
									<c:out value="${fn:substring(tishi,0,28) }" />
								</c:when>
								<c:otherwise>
									<c:out value="${tishi}" />
								</c:otherwise>
						</c:choose>
					</a>
					</div>
				</div>
		
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
					<c:if test="${item.value == '声量' || item.value == '实际声量'|| item.value == '系数'|| item.value == '曝光量'|| item.value == '原发提及占比'
					|| item.value == '独立账号占比'|| item.value == '互动量'|| item.value == '互动量（旧）'|| item.value == '评论贡献比'|| item.value == '点赞贡献比'
					|| item.value == '二级及以上转发占比'|| item.value == '三级及以上转发占比'|| item.value == '转发深度'}">
						<label class="checkbox inline">
							<input type="checkbox" class="box" name="dataIndex" value="${item.key}" id="functions"/>
							<span>${item.value}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</label>
						<c:if test="${(i.index+1)%7 == 0}">
						<br>
						</c:if>
					</c:if>
				</c:forEach>					
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" onclick="selectAll2();" id="label_sentiment">（情感计算）:</label>
			<div class="controls">
				<c:forEach items="${dataIndexMap}" var="item" varStatus="i">
					<c:if test="${item.value == '正面情绪占比' || item.value == '中立情绪占比'|| item.value == '负面情绪占比'|| item.value == '口碑值'}">
						<label class="checkbox inline">
							<input type="checkbox" class="box" name="dataIndex" value="${item.key}" id="functions2"/>
							<span>${item.value}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</label>
					</c:if>
				</c:forEach>					
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label" onclick="selectAll3();" id="label_sentiment">（分类计算）:</label>
			<div class="controls">
				<c:forEach items="${dataIndexMap}" var="item" varStatus="i">
					<c:if test="${item.value == '品牌分类占比'|| item.value == '明星分类占比'|| item.value == '视频分类占比'|| item.value == '粉丝分类占比'|| item.value == '链接分类占比'|| item.value == '其他分类占比'}">
						<label class="checkbox inline">
							<input type="checkbox" class="box" name="dataIndex" value="${item.key}" id="functions3"/>
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
		
		function selectAll3(){  
		    if ($("#functions3").prop("checked")) {
		        $("input[id='functions3']").prop("checked", false);  
		    } else {  
		    	$("input[id='functions3']").prop("checked", true);  
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
	    
	    
		$(function() {
			$("#inputForm").validate({
				rules : {
					taskType : {
						required : true
					},
					key_word : {
						required : true
					},
					search_EQ_dateTo : {
						required : true
					},
					search_EQ_dateFrom : {
						required : true
					},
					dataIndex : {
						required : true
					},
					task_name : {
						required : true
					},
				},
				messages : {
					taskType : {
						required : "必须填写"
					},
					key_word : {
						required : "必须填写"
					},
					search_EQ_dateTo : {
						required : "必须填写"
					},
					search_EQ_dateFrom : {
						required : "必须填写"
					},
					dataIndex : {
						required : "必须填写"
					},
					task_name : {
						required : "必须填写"
					}
				}
			});
		})
	</script>
</body>
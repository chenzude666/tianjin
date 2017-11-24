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
	<title>Mongo 数据指标</title>
    <!-- 引入 G2 文件 -->
    <script src="${ctx}/static/g2/index.js"></script>
    <!-- 引入 Echarts 文件 -->
    <!-- 
    <script src="${ctx}/static/echarts/echarts.min.js"></script>
     -->
    <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/static/datetimepicker/bootstrap-datetimepicker.min.css">
</head>
<body>
	<div>
		<div class="page-header">
			<h4>
				修改任务
			</h4>
		</div>
		<div class="container-fluid">
			<form id="inputForm" method="post" Class="form-horizontal" onsubmit="return testTime()" action="${ctx}/manage/stq/words/task/saveone">
				<div class="control-group">
					<div class="controls">
						<a class="muban" href="${excelmubanDownUrl}${cibaoReadMe}">计算指标说明文档点击下载</a>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="secondName">检测项名称：</label>
					<div class="controls">
						<input type="text" name="task_name" class="input-large " >
					</div>
				</div>
				
				<div class="control-group">
					<label class="control-label" for="secondName">检测项关键词：</label>
					<div class="controls">
					<input type="text" name="key_word" class="input-large " >
					<a class="intro" title="如：三少爷的剑+电影+-测试1+-测试2+测试3+-测试4+中文加+-中文减||三少爷的剑+林更新||三少爷的剑+何润东||三少爷的剑+江一燕||三少爷的剑+蒋梦婕">
						<c:choose>
								<c:when test="${fn:length(tishi)>20}">
									<c:out value="${fn:substring(tishi,0,20) }..." />
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
		
		<div class="control-group">
			<label class="control-label" id="label_sentiment" onclick="selectAll();">监测项任务分类(全选):</label>
			<div class="controls">
				<c:forEach items="${taskType}" var="item" varStatus="i">
						<label class="checkbox inline">
						<c:choose>
							<c:when test="${item.key=='weibo' }">
								<input type="checkbox" class="box" name="task_type" checked="checked" value="${item.key}" id="functions"/>
								<span>${item.value}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</c:when>
							<c:otherwise>
								<input type="checkbox" class="box" name="task_type" value="${item.key}" id="functions"/>
								<span>${item.value}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</c:otherwise>
						</c:choose>
						</label>
						<c:if test="${(i.index+1)%4 == 0}">
					<br>
					</c:if>
				</c:forEach>					
			</div>
		</div>
				<div class="control-group">
					<label class="control-label"></label>
					<div class="controls">
						<button class="btn btn-primary" type="submit">
							<i class="fa fa-check"></i>&nbsp;&nbsp;<span class="bold">确定</span>
						</button>
						<a href="<%=request.getContextPath()%>/manage/stq/words/task/index" class="btn btn-primary">返回</a>
						<a href="<%=request.getContextPath()%>/manage/stq/words/task/exceladd" class="btn btn-info">切换至Excel导入任务</a>
					</div>
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
					task_type : {
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
					},task_type : {
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
	</script>
</body> 	
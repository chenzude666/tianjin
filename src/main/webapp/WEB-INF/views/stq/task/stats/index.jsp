<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="huake" uri="/huake"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
	<head>
		<title>${taskType}主任务管理</title>
	</head>
<body>
	<div>
		<div class="page-header">
			<h4><huake:getWordsTaskTypeTag taskType="${taskType}"></huake:getWordsTaskTypeTag>主任务管理</h4>
		</div>
		<div>
			<c:if test="${not empty message}"> 
				<div id="message" class="alert alert-success">
					<button data-dismiss="alert" class="close">×</button>${message}
				</div>
			</c:if>
			<c:if test="<%=request.getParameter("message")!=null %>">
				<div id="message" class="alert alert-success">
					<button data-dismiss="alert" class="close">×</button><%=request.getParameter("message") %>
				</div>
			</c:if>
			<form id="queryForm" class="well form-inline" method="get" action="${ctx}/manage/stq/stats/task/index/${taskType}">
				<label>任务状态：</label> 
				<select name="search_EQ_taskStatus">
					<option value="">---------请选择---------</option>
					<option value="0" ${param.search_EQ_taskStatus == '0' ? 'selected' : '' }>任务未开始</option>
					<option value="1" ${param.search_EQ_taskStatus == '1' ? 'selected' : '' }>任务进行中</option>
					<option value="2" ${param.search_EQ_taskStatus == '2' ? 'selected' : '' }>任务已完成</option>
					<option value="3" ${param.search_EQ_taskStatus == '3' ? 'selected' : '' }>任务已失败</option>
				</select> 
				<label>任务ID：</label>
				<input type="text" class="well form-inline" name="search_EQ_id" id="search_EQ_id" onblur="testNumber()">
				<label>任务类型：</label> 
				<!--  select name="search_EQ_category">
					<option value='common' ${param.search_EQ_category == 'common' ? 'selected' : '' }>普通任务</option>
					<option value='ivst' ${param.search_EQ_category == 'ivst' ? 'selected' : '' }>ivst</option>
				</select-->
				<input type="submit" class="btn btn-default" value="查 找" />
				<tags:sort />
			</form>
		</div>
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th title="编号" width="120px">编号</th>
					<th title="任务名称">任务名称</th>
					<th title="监测项任务分类">监测项任务分类</th>
					<th title="任务状态">任务状态</th>
					<th title="任务创建时间">任务创建时间</th>
					<th title="任务详情">任务详情</th>
					<th title="任务进度">任务进度</th>
					<th title="任务下载">任务下载</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${textTasks.content}" var="item" varStatus="s">
					<tr ${item.taskStatus == 0 ? 'class="error"' : item.taskStatus == 1 ? 'class="info"' : item.taskStatus == 2 ? 'class="success"' :""}>
						<td >
							<div class="btn-group">
								<a class="btn" href="#">#${item.id}</a> 
								<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
									<span class="caret"></span>
								</a>
								
									<ul class="dropdown-menu">
											<li><a href="<%=request.getContextPath()%>/manage/stq/stats/task/cancel/${taskType }?taskId=${item.id}">任务删除</a></li>
											<li><a href="<%=request.getContextPath()%>/manage/stq/stats/task/pause/${taskType }?taskId=${item.id}&<%=request.getQueryString()%>">任务暂停</a></li>
											<li><a href="<%=request.getContextPath()%>/manage/stq/stats/task/restart/${taskType }?taskId=${item.id}&<%=request.getQueryString()%>">任务重启</a></li>
									</ul>
							</div>
						</td>
						<td>${item.taskName}</td>
						<td><huake:getWordsTaskTypeTag taskType="${item.taskType}"></huake:getWordsTaskTypeTag></td>
						<td>
							${
								item.taskStatus == "0" ? "任务未开始" : 
								item.taskStatus == "1" ? "任务进行中" : 
								item.taskStatus == "2" ? "任务已完成" : 
								item.taskStatus == "3" ? "任务已失败" :
								"未知"
							}
						</td>
						<td><fmt:formatDate value="${item.crDate}" pattern="yyyy/MM/dd  HH:mm:ss" /></td>
						<td><a href="<%=request.getContextPath()%>/manage/stq/stats/task/details/${item.taskType}?taskId=${item.id}">任务详情</a></td>
						<td id="td_${item.id}">
							<c:choose>
								<c:when test="${item.taskStatus == '2' }">
									<button class="btn btn-default" value="${item.id}" id="ID_${item.id }">100.00%</button>
								</c:when>
								<c:otherwise>
									<button class="btn btn-default" value="${item.id}" id="ID_${item.id }" onclick="showSchedule(this)">点击显示进度</button>
								</c:otherwise>
							</c:choose>
							
						</td>
						<td>
							<c:choose>
								<c:when test="${item.taskStatus == '2'}">
									<a href="${item.excelPath}">点击下载</a>
								</c:when>
								<c:otherwise>
									等待
								</c:otherwise>
							</c:choose>		
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<tags:pagination page="${textTasks}" paginationSize="5" />
		<div class="form-actions">
			<a href="<%=request.getContextPath()%>/manage/stq/stats/task/add/${taskType}" class="btn btn-primary">单条新增任务</a>
			<a href="<%=request.getContextPath()%>/manage/stq/stats/task/exceladd/${taskType}" class="btn btn-primary">批量新增任务</a>
			<a href="<%=request.getContextPath()%>/manage/stq/stats/task/changeTask" class="btn btn-primary" style="display:none">修改任务</a>
		</div>
	</div>
	<script type="text/javascript">
		var id = "";
		function showSchedule(but){
			id=but.value
			if(id!=""){
				  $.ajax({
				  type: 'get',//提交方式 post 或者get
				  dataType: 'text',
				  url: '<%=request.getContextPath()%>/manage/stq/stats/task/showSchedule',
				  data: "taskId="+id,//提交的参数
				  success:function(msg){
				  //alert(msg);//弹出窗口，这里的msg 参数 就是访问aaaa.action 后 后台给的参数 
				  	$("#td_"+id).empty();
				  	$("#td_"+id).append("<button class='btn btn-default' value='"+id+"' id='ID_"+id+"' onclick='showSchedule(this)' >"+msg+"</button>");
				  	if(msg == "100.00%"){
				  		window.location.href = window.location.href;
				  	}
				  },
				  error:function(){
				    	id="";
				        alert("提交失败的处理函数");
				        }
				});
			}
		}
		
		$(document).ready(function(){
		});
		
		$(function(){
			$('.intro').tooltip();
		});
		
		function testNumber(){
			var num = document.getElementById("search_EQ_id").value;
			if (num != "")
			if (!(/(^[1-9]\d*$)/.test(num)))
			{
				alert("输入的不是正整数");
				 $("#search_EQ_id").val("");
				return false;
			}
		}
	</script>
</body>
</html>
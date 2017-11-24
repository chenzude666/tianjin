<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib prefix="huake" uri="/huake"%>
<%@ page session="false"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
	<head>
		<title>微博导出详情</title>
	</head>
<body>
	<div>
		<div class="page-header">
			<h4>【${taskName}】子任务详情</h4>
		</div>
		<div>
			<c:if test="${not empty message}"> 
				<div id="message" class="alert alert-success">
					<button data-dismiss="alert" class="close">×</button>${message}
				</div>
			</c:if>
			<form id="queryForm" class="well form-inline" method="get" action="${ctx}/manage/stq/words/task/details?taskId=${taskId}">
				<input type="hidden" name="taskId" value="${taskId}" > 
				<label>子任务状态：</label> 
				<select name="search_EQ_taskStatus">
					<option value="">---------请选择---------</option>
					<option value="0" ${param.search_EQ_taskStatus == '0' ? 'selected' : '' }>子任务未开始</option>
					<option value="1" ${param.search_EQ_taskStatus == '1' ? 'selected' : '' }>子任务进行中</option>
					<option value="2" ${param.search_EQ_taskStatus == '2' ? 'selected' : '' }>子任务已完成</option>
					<option value="3" ${param.search_EQ_taskStatus == '3' ? 'selected' : '' }>子任务已失败</option>
				</select> 
				<input type="submit" class="btn btn-default" value="查 找" />
				<tags:sort />
			</form>
		</div>
		<table class="table table-striped table-bordered table-condensed">
			<thead>
				<tr>
					<th title="编号" width="120px">编号</th>
					<th title="监测项目名称">监测项目名称</th>
					<th title="监测项目关键词">监测项目关键词</th>
					<th title="起始时间（含）">起始时间（含）</th>
					<th title="结束时间（含）">结束时间（含）</th>
					<th title="任务分类">任务分类</th>
					<th title="创建时间">创建时间</th>
					<th title="子任务状态">子任务状态</th>
					<th title="图表展示">图表展示</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${wordsTaskSenconds.content}" var="item" varStatus="s">
					<tr ${item.taskStatus == 0 ? 'class="error"' : item.taskStatus == 1 ? 'class="info"' : item.taskStatus == 2 ? 'class="success"' :""}>
						<td>
							<div class="btn-group">
								<a class="btn" href="#">#${s.index+1}</a> 
								<a class="btn dropdown-toggle" data-toggle="dropdown" href="#">
									<span class="caret"></span>
								</a>
								<ul class="dropdown-menu">
									<li class="divider"></li>
									<li><a href="#">sample</a></li>
								</ul>
							</div>
						</td>
						<td>${item.taskName}</td>
						<td><a href="#"  class="intro" title="${item.keyWord}">
							<c:choose>
								<c:when test="${fn:length(item.keyWord)>8}">
									<c:out value="${fn:substring(item.keyWord,0,8) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${item.keyWord}" />
								</c:otherwise>
							</c:choose>
							</a>
						</td>
						<td>${item.startDate}</td>
						<td>${item.endDate}</td>
						<td><huake:getWordsTaskTypeTag taskType="${item.taskType}"></huake:getWordsTaskTypeTag></td>
						<td><fmt:formatDate value="${item.crDate}" pattern="yyyy/MM/dd  HH:mm:ss" /></td>
						<td>
							${
								item.taskStatus == "0" ? "任务未开始" : 
								item.taskStatus == "1" ? "任务进行中" : 
								item.taskStatus == "2" ? "任务已完成" : 
								item.taskStatus == "3" ? "任务已失败" : 
								"未知"
							}
						</td>
						<td>
							<a href="<%=request.getContextPath()%>/manage/stq/words/task/charts?sencondId=${item.id}" >点击</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<tags:pagination page="${wordsTaskSenconds}" paginationSize="5" />
		<div class="form-actions">
			<a href="<%=request.getContextPath()%>/manage/stq/words/task/index" class="btn btn-primary">返回</a>
		</div>
	</div>
	<script type="text/javascript">
		$(function(){
			$('.intro').tooltip();
		});
	</script>
</body>
</html>
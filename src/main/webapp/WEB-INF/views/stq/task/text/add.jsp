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
	<title>导出模块</title>
    <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/static/datetimepicker/bootstrap-datetimepicker.min.css">
</head>
<body>
	<div>
		<div class="page-header">
			<h4>${category == 'common' ? '新增任务（关键词）' : category == 'weiboAccount' ? '新增任务（微博账号URL）' : category == 'weixinAccount' ? '新增任务（微信号或者微信账号URL）' : '新增任务'}
			</h4>
		</div>
		<div class="container-fluid">
			<form id="inputForm" method="post" Class="form-horizontal"
				onsubmit="return testTime()"
				action="${ctx}/manage/stq/text/task/saveone/${category}">
				<div class="control-group">
					<div class="controls">
						<a class="muban" href="${excelmubanDownUrl}${cibaoReadMe}">原文导出说明文档点击下载</a>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="secondName">检测项名称：</label>
					<div class="controls">
						<input type="text" name="task_name" class="input-large ">
					</div>
				</div>

				<div class="control-group">
					<c:if test="${category=='common' }">
						<label class="control-label" for="secondName">检测项关键词：</label>
					</c:if>
					<c:if test="${category=='weiboAccount' }">
						<label class="control-label" for="secondName">微博账号URL：</label>
					</c:if>
					<c:if test="${category=='weixinAccount' }">
						<label class="control-label" for="secondName">微信号或者微信账号URL：</label>
					</c:if>
					<div class="controls">
						<input type="text" name="key_word" class="input-large"> <a
							class="intro" title="${tishi}"> <c:choose>
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
							<input type="text" name="search_EQ_dateFrom"
								value="${param.search_EQ_dateFrom == null ? dateFrom : param.search_EQ_dateFrom }"
								id="dateFrom" onblur="testTime()"></input> <span class="add-on">
								<i data-time-icon="icon-time" data-date-icon="icon-calendar"
								onblur="testTime()"></i>
							</span>
						</div>
						<span id="error_dateFrom" class="error" hidden="hidden">起始查询时间必须填写</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="datetimepickerEnd">结束时间（含）：</label>
					<div class="controls">
						<div id="datetimepickerEnd" class="input-append date">
							<input type="text" name="search_EQ_dateTo"
								value="${param.search_EQ_dateTo == null ? dateTo : param.search_EQ_dateTo}"
								id="dateTo" onblur="testTime()"></input> <span class="add-on">
								<i data-time-icon="icon-time" data-date-icon="icon-calendar"
								onblur="testTime()"></i>
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
				<c:if test="${category!='weixinAccount' }">
					<div class="control-group ">
						<label class="control-label" for="sentiment">情感分析：</label>
						<div class="controls">
							<select name="sentiment">
								<option value="1">是</option>
								<option value="0" selected="selected">否</option>
							</select>
						</div>
					</div>
				</c:if>
				<div class="control-group ">
					<label class="control-label" for="export_statu">全文导出：</label>
					<div class="controls">
						<select name="export_statu">
							<!-- option value="1" >是</option-->
							<option value="0" selected="selected">否</option>
						</select><a>注:默认导出三千条</a>
					</div>
				</div>
				<div class="page-header">
					<span class="label">导出指标</span>
				</div>

				<c:if test="${category=='common'}">
					<c:forEach items="${taskType}" var="item" varStatus="i">
						<div class="control-group">
							<label class="control-label" onclick="selectAll(${i.index});">
								（点击全选）<huake:getWordsTaskTypeTag taskType="${item.key}"></huake:getWordsTaskTypeTag>:
							</label>
							<div class="controls">
								<c:forEach items="${item.value}" var="ite" varStatus="j">
									<label class="checkbox inline"> <input type="checkbox"
										class="box" name="seeks" value="${ite.key}"
										id="functions_${i.index}" /> <span>${ite.value}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<c:if test="${(j.index+1)%7 == 0}">
										</c:if>
									</label>
								</c:forEach>
								<c:if test="${item.key=='weibo' }">
									<select name="${item.key }_radio">
										<option value="repost_count">转发量(降序)</option>
										<option value="comment_count">评论量(降序)</option>
										<option value="up_count">点赞量(降序)</option>
										<option value="published_at">时间(降序)</option>
										<!--option value="fans_count">关注数</option-->
										<!--option value="follower_count">粉丝数</option-->
										<!--option value="weibo_count">微博数</option-->
									</select>
								</c:if>
								<c:if test="${item.key=='weixin' }">
									<select name="${item.key }_radio">
										<option value="stat_read_count" selected="selected">阅读数(降序)</option>
										<option value="stat_like_count">点赞量(降序)</option>
										<option value="last_modified_at">时间(降序)</option>
									</select>
								</c:if>
								<c:if test="${item.key=='xinwen' }">
									<select name="${item.key }_radio">
										<option value="comment_num" selected="selected">评论数(降序)</option>
										<option value="replyCount">回复数(降序)</option>
										<option value="votecount">投票数(降序)</option>
										<option value="post_time">时间(降序)</option>
									</select>
								</c:if>
								<c:if test="${item.key=='toutiao' }">
									<select name="${item.key }_radio">
										<option value="follower_count" selected="selected">关注量(降序)</option>
										<option value="fans_count">粉丝量(降序)</option>
										<option value="read_count">阅读量(降序)</option>
										<option value="comment_count">评论量(降序)</option>
										<option value="up_count">点赞量(降序)</option>
										<option value="down_count">踩量(降序)</option>
										<option value="published_at">时间(降序)</option>
									</select>
								</c:if>
								<c:if test="${item.key=='zhihuquestions' }">
									<select name="${item.key }_radio">
										<option value="follows_num" selected="selected">关注人数(降序)</option>
										<option value="views_num">查看人数(降序)</option>
										<option value="answers_num">回答人数(降序)</option>
										<option value="published_at">时间(降序)</option>
									</select>
								</c:if>
								<c:if test="${item.key=='tianya' }">
									<select name="${item.key }_radio">
										<option value="clicks_num" selected="selected">帖子点击数(降序)</option>
										<option value="replays_num">帖子回复数(降序)</option>
										<option value="published_at">时间(降序)</option>
									</select>
								</c:if>
								<c:if test="${item.key=='zixun' }">
									<select name="${item.key }_radio">
										<option value="supports_num" selected="selected">点赞量(降序)</option>
										<option value="collections_num">收藏量(降序)</option>
										<option value="comments_num">评论量(降序)</option>
										<option value="views_num">浏览量(降序)</option>
										<option value="published_at">时间(降序)</option>
									</select>
								</c:if>
								<c:if test="${item.key=='tieba' }">
									<select name="${item.key }_radio">
										<option value="reply_num" selected="selected">回复数(降序)</option>
										<option value="date">时间(降序)</option>
									</select>
								</c:if>
							</div>
						</div>
					</c:forEach>
				</c:if>
				<c:if test="${category=='weiboAccount'}">
					<div class="control-group">
						<label class="control-label" onclick="selectAll(${i.index});">
							（点击全选）微博: </label>
						<div class="controls">
							<label class="checkbox inline"> <input type="checkbox"
								class="box" name="seeks" value="weiboAccount#weiboer_id"
								id="functions_${i.index}" checked="checked" readonly="readonly" />
								<span>weiboer_id</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</label> 
							<select name="weiboAccount_radio">
								<option value="repost_count">转发量(降序)</option>
								<option value="comment_count">评论量(降序)</option>
								<option value="up_count">点赞量(降序)</option>
								<option value="published_at">时间(降序)</option>
								<!--option value="fans_count">关注数</option-->
								<!--option value="follower_count">粉丝数</option-->
								<!--option value="weibo_count">微博数</option-->
							</select>
						</div>
					</div>
				</c:if>
				<c:if test="${category=='weixinAccount'}">
					<div class="control-group">
						<label class="control-label" onclick="selectAll(${i.index});">
							（点击全选）微信: </label>
						<div class="controls">
							<label class="checkbox inline"> <input type="checkbox"
								class="box" name="seeks" value="weixinAccount#biz"
								id="functions_${i.index}" checked="checked" readonly="readonly" />
								<span>biz</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							</label> 
							<select name="weixinAccount_radio">
									<option value="stat_read_count" selected="selected">阅读数(降序)</option>
									<option value="stat_like_count">点赞量(降序)</option>
									<option value="last_modified_at">时间(降序)</option>
							</select>
						</div>
					</div>
				</c:if>
				<div class="form-actions">
					<button type="submit" class="btn btn-primary" id="submit">保存</button>
					<a
						href="<%=request.getContextPath()%>/manage/stq/text/task/index/${category}"
						class="btn btn-primary">返回</a> <a
						href="<%=request.getContextPath()%>/manage/stq/text/task/exceladd/${category}"
						class="btn btn-info">切换至Excel导入任务</a>
				</div>
			</form>
		</div>
	</div>
	<script type="text/javascript"
		src="${ctx}/static/datetimepicker/bootstrap-datetimepicker.min.js"></script>
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
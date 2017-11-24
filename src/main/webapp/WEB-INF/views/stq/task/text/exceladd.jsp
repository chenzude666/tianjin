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
	</style> 
</head>
<body>
	<div class="page-header">
			<h4>
				${category == 'common' ? '新增任务（关键词）' : category == 'weiboAccount' ? '新增任务（微博账号URL）' : category == 'weixinAccount' ? '新增任务（微信号或者微信账号URL）' : '新增任务'}
			</h4>
			<!--a class="label" href="${excelmubanDownUrl}${mubanname}">Excel模板下载</a-->
	</div>
	<c:if test="${not empty message}">
		<div id="message" class="alert alert-success">
			<button data-dismiss="alert" class="close">×</button>${message}
		</div>
	</c:if>
	<form id="inputForm" method="post" Class="form-horizontal" action="${ctx}/manage/stq/text/task/savaexcel/${category}" enctype="multipart/form-data" onsubmit="return checkSubmit()">
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
				
		<div class="control-group ">
			<label class="control-label" for="status">状态：</label>
			<div class="controls">
				<select name="status">
					<option value="1" selected="selected">有效</option>
					<option value="0">无效</option>
				</select>
			</div>
		</div>
		
		<div class="control-group ">
			<label class="control-label" for="export_statu">全部导出：</label>
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
						<label class="checkbox inline">
							<input type="checkbox" class="box" name="seeks" value="${ite.key}" id="functions_${i.index}"/>
							<span>${ite.value}</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
					（点击全选）微博:
				</label>
				<div class="controls">
						<label class="checkbox inline">
							<input type="checkbox" class="box" name="seeks" value="weiboAccount#weiboer_id" id="functions_${i.index}" checked="checked" readonly="readonly"/>
							<span >微博用户ID</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
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
					</label> <select name="weixinAccount_radio">
						<option value="stat_read_count" selected="selected">阅读数(降序)</option>
						<option value="stat_like_count">点赞量(降序)</option>
						<option value="last_modified_at">时间(降序)</option>
					</select>
				</div>
			</div>
		</c:if>
		<div class="form-actions">
			<button type="submit" class="btn btn-primary" id="submit">保存</button>
			<a href="<%=request.getContextPath()%>/manage/stq/text/task/index/${category}" class="btn btn-primary">返回</a>
			<a href="<%=request.getContextPath()%>/manage/stq/text/task/add/${category}" class="btn btn-info">切换至单条导入任务</a>
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
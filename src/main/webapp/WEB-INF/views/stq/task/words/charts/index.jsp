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
	<title>图表分析</title>
    <!-- 引入 G2 文件 -->
    <script src="${ctx}/static/g2/index.js"></script>
    <link rel="stylesheet" type="text/css" media="screen" href="${ctx}/static/datetimepicker/bootstrap-datetimepicker.min.css">
</head>
<body>
	<div>
		<div class="page-header">
			<h4>
				图表分析
			</h4>
		</div>
		<div class="container-fluid">
			<form id="inputForm"  Class="form-horizontal" >
				<!--div class="control-group">
					<label class="control-label" for="datetimepickerStart">起始时间：</label>
					<div class="controls">
						<div id="datetimepickerStart" class="input-append date">
							<input type="text" name="search_EQ_dateFrom" value="${startTime}" id="dateFrom" readonly="readonly"></input> 
							<!--  span class="add-on"> 
								<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
							</span>
						
						</div>
						<span id="error_dateFrom" class="error" hidden="hidden">起始查询时间必须填写</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="datetimepickerEnd">结束时间：</label>
					<div class="controls">
						<div id="datetimepickerEnd" class="input-append date">
							<input type="text" name="search_EQ_dateTo" value="${endTime}" id="dateTo" readonly="readonly"></input>
							<!--  span class="add-on"> 
								<i data-time-icon="icon-time" data-date-icon="icon-calendar"></i>
							</span>
						
						</div>
						<span id="error_dateTo" class="error" hidden="hidden">结束查询时间必须填写</span>
						<span id="error_dateTo_vs_dateFrom" class="error" hidden="hidden">结束时间不能小于开始时间</span>
					</div>
				</div>
				<div class="control-group">
					<label class="control-label" for="datetimepickerStart">关键字：</label>
					<div class="controls">
						<div id="datetimepickerStart" class="input-append date">
							<input type="text" name="key_word" value="${keyword}" id="keyword" readonly="readonly"></input> 
						</div>
						<span id="error_dateFrom" class="error" hidden="hidden">起始查询时间必须填写</span>
					</div>
				</div-->
				<!--  <div class="control-group">
					<label class="control-label"></label> 
					<div class="controls">
						<a href="#" class="btn btn-success" id="yesterday">昨日</a> 
						<a href="#" class="btn btn-success" id="sevenDayAgo">近7日</a> 
						<a href="#" class="btn btn-success" id="thirtyDayAgo">近30日</a>
						<button class="btn btn-primary" type="submit">
							<i class="fa fa-check"></i>&nbsp;&nbsp;<span class="bold">确定</span>
						</button>
					</div>
				</div>-->
				<h4 align="center" >${startTime } 至 ${endTime} 关键字:<a class="intro" title="${keyword}">
						<c:choose>
								<c:when test="${fn:length(keyword)>20}">
									<c:out value="${fn:substring(keyword,0,20) }..." />
								</c:when>
								<c:otherwise>
									<c:out value="${keyword}" />
								</c:otherwise>
						</c:choose>
					</a> 图表</h4>
			</form>
			<div class="row-fluid">
					<div class="row-fluid">
						<div class="tabbable span12">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#a1" data-toggle="tab">微博指标趋势</a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="a1">
									<div id="chart_weiboData"></div>
								</div>
							</div>
						</div>
					</div>
			</div>
				<div class="row-fluid">
					<div class="row-fluid">
						<div class="tabbable span12">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#a2" data-toggle="tab">微信指标趋势</a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="a2">
									<div id="chart_weixinData"></div>
								</div>
							</div>
						</div>
					</div>
				</div>
			
			<div class="row-fluid">
					<div class="row-fluid">
						<div class="tabbable span12">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#a3" data-toggle="tab">贴吧指标趋势</a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="a3">
									<div id="chart_tiebaData"></div>
								</div>
							</div>
						</div>
					</div>
			</div>
			<div class="row-fluid">
					<div class="row-fluid">
						<div class="tabbable span12">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#a4" data-toggle="tab">知乎指标趋势</a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="a4">
									<div id="chart_zhihuquestionsData"></div>
								</div>
							</div>
						</div>
					</div>
			</div>
			<div class="row-fluid">
					<div class="row-fluid">
						<div class="tabbable span12">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#a5" data-toggle="tab">天涯指标趋势</a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="a5">
									<div id="chart_tianyaData"></div>
								</div>
							</div>
						</div>
					</div>
			</div>
			<div class="row-fluid">
					<div class="row-fluid">
						<div class="tabbable span12">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#a6" data-toggle="tab">头条指标趋势</a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="a6">
									<div id="chart_toutiaoData"></div>
								</div>
							</div>
						</div>
					</div>
			</div>
			<div class="row-fluid">
					<div class="row-fluid">
						<div class="tabbable span12">
							<ul class="nav nav-tabs">
								<li class="active"><a href="#a7" data-toggle="tab">资讯指标趋势</a></li>
							</ul>
							<div class="tab-content">
								<div class="tab-pane active" id="a7">
									<div id="chart_zixunData"></div>
								</div>
							</div>
						</div>
					</div>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${ctx}/static/datetimepicker/bootstrap-datetimepicker.min.js"></script>
	<%@ include file="chart.jsp"%>
	<script type="text/javascript">
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
					search_EQ_serverId:{
						required:true
					},
					search_EQ_dateFrom:{
						required:true
					},
					search_EQ_dateTo:{
						required:true
					}
				},messages:{
					search_EQ_serverId:{
						required:"查询区服必须填写"
					},
					search_EQ_dateFrom:{
						required:"起始时间必须填写"
					},
					search_EQ_dateTo:{
						required:"结束时间必须填写"
					}
				}
			});
			
			$("#yesterday").click(function() {
				$.ajax({
					url : '<%=request.getContextPath()%>/manage/game/summary/getDate',
					type: 'GET',
					contentType: "application/json;charset=UTF-8",		
					dataType: 'text',
					success: function(data){
						var parsedJson = $.parseJSON(data);
						$("#dateFrom").val(parsedJson.yesterday);
						$("#dateTo").val(parsedJson.nowDate);
					},error:function(xhr){
						window.location.href = window.location.href;
					}//回调看看是否有出错
				});
			});
			$("#sevenDayAgo").click(function(){
				$.ajax({                                               
					url: '<%=request.getContextPath()%>/manage/game/summary/getDate',
					type: 'GET',
					contentType: "application/json;charset=UTF-8",		
					dataType: 'text',
					success: function(data){
						var parsedJson = $.parseJSON(data);
						$("#dateFrom").val(parsedJson.sevenDayAgo);
						$("#dateTo").val(parsedJson.nowDate);
					},error:function(xhr){
						window.location.href = window.location.href;
					}//回调看看是否有出错
				});
			});
			$("#thirtyDayAgo").click(function(){
				$.ajax({                                               
					url: '<%=request.getContextPath()%>/manage/game/summary/getDate',
					type: 'GET',
					contentType: "application/json;charset=UTF-8",		
					dataType: 'text',
					success: function(data){
						var parsedJson = $.parseJSON(data);
						$("#dateFrom").val(parsedJson.thirtyDayAgo);
						$("#dateTo").val(parsedJson.nowDate);
					},error:function(xhr){
						window.location.href = window.location.href;
					}//回调看看是否有出错
				});
			});		
		});
	</script>
</body> 	
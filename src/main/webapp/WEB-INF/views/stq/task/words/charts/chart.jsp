<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="huake" uri="/huake"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
	
<script type="text/javascript">
	// G2 对数据源格式的要求，仅仅是 JSON 数组，数组的每个元素是一个标准 JSON 对象。
	// Step 1: 创建 Chart 对象
	
	var weiboData = ${weiboData}
	var weiboFrame = [ 'weibo_volume', 'weibo_actualVolume','weibo_exposure', 'weibo_interactive', 'weibo_forwardDepth','weibo_account' ];
	var weiboMap = {weibo_volume : '声量',weibo_actualVolume : '实际声量',weibo_exposure : '曝光量',weibo_interactive : '互动量',weibo_forwardDepth : '转发深度',weibo_account : '提及账号数'};
	
	var weixinData = ${weixinData}
	var weixinFrame = [ 'weixin_volume', 'weixin_actualVolume','weixin_readCount', 'weixin_upCount', 'weixin_readArticleCount','weixin_account' ];
	var weixinMap = {weixin_volume : '总文章量',weixin_actualVolume : '实际文章量',weixin_readCount : '总阅读数',weixin_upCount : '总点赞量',weixin_readArticleCount : '有阅读的文章数量',weixin_account : '帐号提及量'};
	
	var tiebaData = ${tiebaData}
	var tiebaFrame = [ 'tieba_recoveryCount', 'tieba_actualVolume','tieba_account'];
	var tiebaMap = {tieba_recoveryCount : '回复数',tieba_actualVolume : '实际声量',tieba_account : '帐号提及量'};
	
	var zhihuquestionsData = ${zhihuquestionsData}
	var zhihuquestionsFrame = [ 'zhihuquestions_questionsCount', 'zhihuquestions_followCount','zhihuquestions_seeCount', 'zhihuquestions_answerCount', 'zhihuquestions_actualVolume','zhihuquestions_account' ];
	var zhihuquestionsMap = {zhihuquestions_questionsCount : '问题数',zhihuquestions_followCount : '关注人数',zhihuquestions_seeCount : '查看人数',zhihuquestions_answerCount : '回答人数',zhihuquestions_actualVolume : '实际声量',zhihuquestions_account : '帐号提及量'};
	
	var tianyaData = ${tianyaData}
	var tianyaFrame = [ 'tianya_clicksCount', 'tianya_answerCount','tianya_actualVolume', 'tianya_account'];
	var tianyaMap = {tianya_clicksCount : '帖子点击数',tianya_answerCount : '贴子回复数',tianya_actualVolume : '实际声量',tianya_account : '帐号提及量'};
	
	var toutiaoData = ${toutiaoData}
	var toutiaoFrame = [ 'toutiao_followCount', 'toutiao_fansCount','toutiao_readCount', 'toutiao_commentCount', 'toutiao_upCount','toutiao_stepCount', 'toutiao_actualVolume','toutiao_account' ];
	var toutiaoMap = {toutiao_followCount : '关注量',toutiao_fansCount : '粉丝量',toutiao_readCount : '阅读量',toutiao_commentCount : '评论量',toutiao_upCount : '点赞量',toutiao_stepCount : '踩量',toutiao_actualVolume : '实际声量',toutiao_account : '提及账号数'};
	
	var zixunData = ${zixunData}
	var zixunFrame = [ 'zixun_upCount', 'zixun_collectionCount','zixun_commentCount', 'zixun_seeCount', 'zixun_actualVolume','zixun_account' ];
	var zixunMap = {zixun_upCount : '点赞量',zixun_collectionCount : '收藏量',zixun_commentCount : '评论量',zixun_seeCount : '浏览量',zixun_actualVolume : '实际声量',zixun_account : '帐号提及量'};
	
	
	var eachdata2 = ["weiboData","weixinData","tiebaData","zhihuquestionsData","tianyaData","toutiaoData","zixunData"]; 
	var eachdata = ${eachdata}
	$.each( eachdata, function(index, value){ 
		var i = value;
		
		//console.log( "Item #" + i + ": " + value ); 
		var data;
		var f;
		var m;
		if(value=='weiboData'){data=weiboData;f=weiboFrame;m=weiboMap}
		else if(value=='weixinData'){data=weixinData;f=weixinFrame;m=weixinMap}
		else if(value==='tiebaData'){data=tiebaData;f=tiebaFrame;m=tiebaMap}
		else if(value=='zhihuquestionsData'){data=zhihuquestionsData;f=zhihuquestionsFrame;m=zhihuquestionsMap}
		else if(value=='tianyaData'){data=tianyaData;f=tianyaFrame;m=tianyaMap}
		else if(value=='toutiaoData'){data=toutiaoData;f=toutiaoFrame;m=toutiaoMap}
		else if(value=='zixunData'){data=zixunData;f=zixunFrame;m=zixunMap}
		
		var Frame = G2.Frame;
		var frame = new Frame(data);
		frame = Frame.combinColumns(frame, f, 'value', 'type', 'startDateString');
		
		var chart = new G2.Chart({
			id : 'chart_'+i,
			forceFit : true,
			height : 600,
			plotCfg : {
				margin : [ 20, 20, 100, 80 ]
			}
		});
		var map = m;
		chart.source(frame, {
			date : {
				type : 'time',
				mask : 'yyyy.mm.dd',
				tickCount : 12
			},
			value : {
				alias : '计算指标'
			},
			type : {
				formatter : function(val) {
					return map[val];
				}
			}
		});
		chart.legend({
			position : 'top'
		});
		chart.axis('startDateString', {
			tickLine : null,
			title : null
		});
		chart.axis('value', {
			labels : null,
			line : null,
			tickLine : null,
			grid : null
		});
		chart.line().position('startDateString*value').color('type',[ '#1f77b4', '#ff7f0e', '#2ca02c', '#1f77b3', '#1f7f1e','#2ca04c' ]).shape('spline').size(2);
		chart.render();
	});  

</script>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


<div class="navbar navbar-fixed-top">
	<div class="navbar-inner">
		<div class="container" style="margin-left: 40px;width: auto;">
			<a class="btn btn-navbar" data-toggle="collapse"
				data-target=".nav-collapse"> <span class="icon-bar"></span> <span
				class="icon-bar"></span> <span class="icon-bar"></span>
			</a>
			<div class="nav-collapse">
				<ul class="nav navbar-nav">
					<!--  
					<shiro:hasAnyRoles name="admin,summary">
						<li class="dropdown"><a href="${ctx}/manage/game/summary/index" class="dropdown-toggle" >首页 <b class="caret"></b></a>
						</li>
					</shiro:hasAnyRoles>
					-->
					<shiro:hasAnyRoles name="admin,systemUser">
						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">后台设置 <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<shiro:hasAnyRoles name="admin,systemUser">
									<li><a href="${ctx}/manage/user/index">账号管理</a></li>
								</shiro:hasAnyRoles>
								<shiro:hasAnyRoles name="admin,systemLogger">
									<li><a href="${ctx}/manage/logger/index">操作记录</a></li>
								</shiro:hasAnyRoles>
								<shiro:hasAnyRoles name="admin">
									<li><a href="${ctx}/manage/functions/index">权限管理</a></li>
								</shiro:hasAnyRoles>
							</ul>
						</li>
					</shiro:hasAnyRoles>
					
					<shiro:hasAnyRoles name="admin,stqCibao">
						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">词包模块 <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<shiro:hasAnyRoles name="admin,stqCibao">
									<li><a href="${ctx}/manage/stq/words/task/index">词包任务</a></li>
								</shiro:hasAnyRoles>
							</ul>
						</li>
					</shiro:hasAnyRoles>
					
					 
					 <shiro:hasAnyRoles name="admin,stqExport">
						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">导出模块 <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<shiro:hasAnyRoles name="admin,stqExport">
									<li class="dropdown-submenu">
										<a tabindex="-1" href="${ctx}/manage/stq/text/task/index/common">导出任务列表（点击）</a>
										<ul class="dropdown-menu">
											<li class="dropdown-submenu">
													<li><a style="padding:2 0 0 10px" href="${ctx}/manage/stq/text/task/exceladd/common">新增任务(关键字)</a></li>
													<li><a style="padding:2 0 0 10px" href="${ctx}/manage/stq/text/task/exceladd/weiboAccount">新增任务(微博账号URL)</a></li>
													<li><a style="padding:2 0 0 10px" href="${ctx}/manage/stq/text/task/exceladd/weixinAccount">新增任务(微信账号URL)</a></li>
											<li>
										</ul>
									</li>
								</shiro:hasAnyRoles>
							</ul>
						</li>
					</shiro:hasAnyRoles>

					<shiro:hasAnyRoles name="admin,stqStats">
						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown">统计模块<b class="caret"></b></a>
							<ul class="dropdown-menu">
								<shiro:hasAnyRoles name="admin,stqStats">
									<li class="dropdown-submenu">
										<a tabindex="-1" href="#">微博（调用爬虫）</a>
										<ul class="dropdown-menu">
											<li class="dropdown-submenu">
													<li><a style="padding:2 0 0 10px" href="${ctx}/manage/stq/stats/task/index/weibo_kol">KOL影响力</a></li>
													<li><a style="padding:2 0 0 10px" href="${ctx}/manage/stq/stats/task/index/weibo_involvement">用户卷入</a></li>
													<li><a style="padding:2 0 0 10px" href="${ctx}/manage/stq/stats/task/index/weibo_depth">传播深度</a></li>
													<li><a style="padding:2 0 0 10px" href="${ctx}/manage/stq/stats/task/index/weibo_topic_frequency_volume">话题(频次,声量 )</a></li>
													<li><a style="padding:2 0 0 10px" href="${ctx}/manage/stq/stats/task/index/weibo_kol_crawled">导出微博账号转评赞</a></li>													
													<li><a style="padding:2 0 0 10px" href="${ctx}/manage/stq/stats/task/index/weibo_toComment">导出单条微博转评赞</a></li>
													<li><a style="padding:2 0 0 10px" href="${ctx}/manage/stq/stats/task/index/weibo_addAccounts">新增微博帐号（日常监测）</a></li>
													<li><a style="padding:2 0 0 10px" href="${ctx}/manage/stq/stats/task/index/weibo_fans_follow_number">导出日常监测的微博粉丝量、关注量、微博量</a></li>
													<li><a style="padding:2 0 0 10px" href="${ctx}/manage/stq/task/index?taskType=1">微博统计(数据，口碑，分类)</a></li>
											<li>
										</ul>
									</li>
									<li class="dropdown-submenu">
										<a tabindex="-1" href="#">微信（调用爬虫）</a>
										<ul class="dropdown-menu">
											<li class="dropdown-submenu">
													<li><a style="padding:2 0 0 10px" href="${ctx}/manage/stq/stats/task/index/weixin_kol">导出微信KOL影响力</a></li>
													<li><a style="padding:2 0 0 10px" href="${ctx}/manage/stq/stats/task/index/weixin_kol_crawled">导出微信KOL统计信息</a></li>
													<li><a style="padding:2 0 0 10px" href="${ctx}/manage/stq/task/index?taskType=2">微信统计(数据，口碑，分类)</a></li>
											<li>
										</ul>
									</li>
								</shiro:hasAnyRoles>
							</ul>
						</li>
					</shiro:hasAnyRoles>
					
				</ul>
				<shiro:user>
					<ul class="nav navbar-nav navbar-right">
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown"><i class="icon-user icon-white"></i>&nbsp;<shiro:principal
									property="name" /><b class="caret"></b></a>
							<ul class="dropdown-menu nav-list">
								<li><a href="${ctx}/profile">编辑个人资料</a></li>
								<li><a href="${ctx}/logout">安全退出</a></li>
							</ul></li>
					</ul>
				</shiro:user>
			</div>
			<!--/.nav-collapse -->

		</div>
	</div>
</div>


<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<meta charset="utf-8">
		<title>聚集之美 ${userdata.user} 的主页</title>
		<#if show!=true>
		<meta name="keywords" content="我是${userdata.user} 这些都是我的技术问答 ">
		<meta name="description" content="我是 ${userdata.user} 欢迎来到聚集之美 ">
		</#if>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<link rel="stylesheet" type="text/css" href="/web/css/style.css">
		<link rel="stylesheet" type="text/css" href="/web/css/reveal.css" />
		<script type="text/javascript" src="/js/jquery.min.js"></script>
		<script type="text/javascript" src="/web/js/jquery.reveal.js"></script>
		<script type="text/javascript" src="/js/publicMethod.js"></script>
	</head>

	<body>
		<div class="my">
			<div class="content relative">
				<nav>
					<ul class="clearfix">
						<li class="fr"><a id="userImg" href="/user/my/post">我的</a></li>
						<li class="fr"><a id="UnreadMessageCount" href="/user/message">消息</a></li>
					</ul>
				</nav>
			</div>
		</div>
		<div id="menuTop" style="color: black;width: 100%;text-align: center;">
			<ul style="position: relative;left:-100px;margin: 16px 0px 16px 0px;">
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><img width="48" height="48" alt="java图标" src="/web/images/logo.jpg"><br><div style="text-align: center;width: 65px;position: relative;left:-8px;">聚集之美</div></li>
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><img width="48" height="48" alt="java图标" src="/web/images/cloud_48dp.png"><br><div style="text-align: center;width: 48px;">圈子</div></li>
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><img width="48" height="48" alt="java图标" src="/web/images/logo.png"><br><div style="text-align: center;width: 48px;">标签</div></li>
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><div style="width: 48px;height: 48px;background-color: #c52062;border-radius:50%;"></div><br><div style="text-align: center;width: 48px;">图书</div></li>
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><div style="width: 48px;height: 48px;background-color: #eceff1;border-radius:50%;color: #039be5;text-align: center;line-height: 48px;">&bull;&bull;&bull;</div><br><div style="text-align: center;width: 48px;">帖子</div></li>
			</ul>
		</div>
		<div class="center">
			<div class="content clearfix">
				<div class="my-left">
					<ul>
			<#include "myleftmenu.ftl"> 
					</ul>
				</div>
				<div class="my-right">
					<div class="contact f16 clearfix">
						<i class="fl">IT</i>
						<p class="fl"><span class="author">LSS</span>
							<br />823297503@qq.com</p>
					</div>
				
				<div class="title">技术问答 
						<a href="#" class="createCamp" data-reveal-id="answerTopic"></a>
					</div>
					<dl class="techAsk">
						<dt>我回答的</dt>
						<#if topics??>
							<#if (topics?size > 0)>
								<#list topics as topic>
									<dd>
										回复：
										<#if replys??>
										<#if (replys?size >0)>
										<#list replys as reply>
											<#if reply_index == topic_index>
												${reply.content}
											</#if>
										</#list>
										</#if>
										</#if>
										帖子:<a href="">${topic.title!''}</a>
									</dd>
								</#list>
							</#if>
						</#if>
					</dl>
				
				</div>
			</div>
		</div>
		<form id="idForm">
		<input type="hidden" name="id" id="id" value=""/>
		</form>

		
	</body>
	
</html>
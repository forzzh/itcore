
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<meta charset="utf-8">
		<title>我的</title>
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
							<li class="fl"><a href="/group/list">大小营地</a></li>
						<li class="fl"><a href="/label/list">长短标签</a></li>
						<li class="fl"><a href="/book/list">图书推荐</a></li>
						<li class="fr"><a href="/user/my/post">我的</a></li>
						<li class="fr"><a id="UnreadMessageCount" href="/user/message">消息</a></li>
						<li class="fr"><a href="/post/all">技术问答</a></li>
					</ul>
				</nav>
				<img width="1282" src="images/my.jpg">
			</div>
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
						<dt>我提问的</dt>
						<#if questions??>
							<#list questions as question>
								<dd><a href="">${question.title}</a>
									<#if question.answerState?? && question.answerState == 'haveanser'>
										<i class="answered"></i>
									</#if>
								</dd>
							</#list>
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
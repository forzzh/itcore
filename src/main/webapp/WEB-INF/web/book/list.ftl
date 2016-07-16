
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title></title>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<link rel="stylesheet" type="text/css" href="/web/css/style.css">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<script type="text/javascript" src="/js/jquery1.9.0.min.js"></script>
		<script type="text/javascript" src="/js/erweimajs/jquery.qrcode.js"></script>
		<script type="text/javascript" src="/js/erweimajs/qrcode.js"></script>
		<script type="text/javascript" src="/js/publicMethod.js"></script>
		<script>
		var staticurl = '${staticurl!""}'
		</script>
		<script type="text/javascript" src="/web/js/booklist.js"></script>
	</head>

	<body style="    overflow-x: hidden;">
		<div class="book">
	<div class="content relative" style="margin:0 auto;">
				<nav>
					<ul class="clearfix">
				<ul class="clearfix" style="    list-style: none;"  >
						<li class="fr"  style=" margin-left:0px;line-height: normal;margin-top:0px;"><a id="userImg" href="/user/my/post">我的</a></li>
						<li class="fr"  style=" line-height: normal;margin-top:0px;"><a id="UnreadMessageCount" href="/user/message">消息</a></li>
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
				<div class="left">
					<div class="part1">
						<div class="title">图书推荐</div>
						<ul class="clearfix" id="randomLabels">
							
						</ul>
					</div>
					<ul class="partContent clearfix">
					</ul>
				</div>	
		<div class="right" style="width:386px">
					<!-- <div class="topic2">
						<p class="visHidden">添加标签</p>
						<div class="relative">
							<i class="abs"></i>
							<input type="text" name="" id="" value="" placeholder="搜索..."/>
						</div>
					</div> -->
					<h3>热门标签</h3>
					<ul class="boxList clearfix" style="    margin-left: -32px;">
						<#if (labels?size>0)>   
							<#list labels as label>
								<li   style="    list-style: none;"><a href="/post/all/${label.name}">${label.name}</a></li>
							</#list>
						</#if>
					</ul>
					<dl class="newSoftware">
						<dt>最新软件</dt>
						<#if (adTopics?size>0)>   
							<#list adTopics as ad>
								<dd class="clearfix">
									<#if ad.image??>
										<img style="margin-left:-34px;" src="${staticUrl}${ad.image}" alt="" width="49" class="fl"/>
									<#else>
										<img style="margin-left:-34px;" src="/web/images/ai.jpg" alt="" width="49" class="fl"/>
									</#if>
									<p style="margin-left: 20px;margin-top: -90px;" class="fl"><a href="/post/${ad.id}"><span>${ad.title!""}</span></a> <br />
									${ad.simpleContent!""}</p>
								</dd>
							</#list>
						</#if>
						<!-- <dd class="clearfix">
							
							<p class="fl"><span>Photoshop</span> <br />
							更新最新2016CC抢先版！</p>
						</dd> -->
						
					</dl>
					<#if (requestTopics?size>0)>  
					<dl class="QAlist">
						<dt>技术问答</dt>
						
							<#list requestTopics as question>
								<dd class="clearfix">
									<i class="fl">前</i>
									<p class="fl"><a href="/post/${question.id}"><span class="question">${question.title}</span></a>
									<#if question.answerState?? && question.answerState == 'HaveAnser'>
										<span class="answer">小伙伴你来晚了.这个问题已解答了...</span></p>
									<#else>
										<span class="answer">跪求小伙伴们解答...</span></p>
									</#if>
								</dd>
							</#list>
							
						
					</dl>
					</#if>
				</div>
			</div>
		</div>
	</body>

</html>
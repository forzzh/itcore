
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<meta charset="utf-8">
		<title>话题搜索结果页</title>
	<meta name="keywords" content="聚集之美">
		<meta name="description" content="聚集之美是一个原创+交流的论坛,聚集大量it精英。欢迎您的加入 让代码美起来">
		<link rel="stylesheet" type="text/css" href="/web/css/style.css">
		<link rel="stylesheet" type="text/css" href="/web/css/reveal.css" />
		<script type="text/javascript" src="/web/js/jquery.min.js"></script>
		<script type="text/javascript" src="/js/publicMethod.js"></script>
	</head>

	<body>
		<div class="detail">
			<div class="content relative">
				<nav>
					<ul class="clearfix">
						<li class="fr"><a href="/user/my/post">我的</a></li>
						<li class="fr"><a id="userImg" id="UnreadMessageCount" href="/user/message">消息</a></li>
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
			<div class="content clearfix campDetailPage">
				<div class="left">
					<div class="part2 relative">
						<ul id="list">
							<#if page??>
								<#if page.list ??>
									<#list page.list as tc>
									<li>
										<div class="f16 clearfix">
											<#if !tc.topic.user.image??>
											
											<#if  tc.topic.user.sex=='boy'>
												<a href="/user/other/post/${tc.topic.user.id}"><i class="f21 fl" style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											<#if  tc.topic.user.sex!='boy'>
												<a href="/user/other/post/${tc.topic.user.id}"><i class="f21 fl" style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											</#if>
											<#if tc.topic.user.image??>
												<a href="/user/other/post/${tc.topic.user.id}"><i class="f21 fl" style="background: url(${static}${tc.topic.user.image}) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											<p class="fl"><span class="author c44"><a href="/user/other/post/${tc.topic.user.id}">${tc.topic.user.user!''}</a></span>
												<span class="classify c44">
												&nbsp;&nbsp;&nbsp;来自&nbsp;&nbsp;&nbsp;
													<#if tc.topic.group ??>
													<a href="/user/other/post/${tc.topic.group.id}">	${tc.topic.group.name!''}</a>
													</#if>
												</span><br />${tc.topic.showData} · ${tc.topic.hits}人阅读</p>
										</div>
										<div class="articleName"><a href="//post/${tc.topic.id}">${tc.topic.title!''}</a></div>
										<div class="articleCon"><a href="//post/${tc.topic.id}">${tc.topic.simpleContent}</a></div>
									</li>
									</#list>
								</#if>
							</#if>
							<#if page??>
								<#if (page.list?? && page.list?size == 10)>
									<li class="last" style="list-style:none;"><a style="background-color:#c9c9c9;" href="javascript:void(0);" onclick="getMore()">更多</a></li>
								</#if>
							</#if>
						</ul>
					</div>
				</div>
				<div class="right">
					<figure class="tabBlock">
					
					</figure>
					<figure class="tabBlock">
						<ul class="tabBlock-tabs">
							<li class="tabBlock-tab is-active">
								<h3>热门标签</h3></li>
		
						</ul>
						<div class="tabBlock-content">
							<div class="tabBlock-pane" style="display: block;">
							<#if labels??>
								<ul class="boxList clearfix" style="padding-left:0px;">
								<#list labels as label>
									<li>
										<a href="/post/all/${label.name}">${label.name!''}</a>
									</li>
								</#list>
								</ul>
							</#if>
							</div>
							<div class="tabBlock-pane" style="display: none;">

							</div>
						</div>
					</figure>
					<!--	<h3>热门标签</h3>-->
						<#if (questions??)>
						<#if questions?size!=0 >
							<dl class="QAlist">
								<dt>技术问答</dt>
							<#list questions as question>
									<dd class="clearfix" style="margin-left:0px;">
										<#if !question.user.image??>
											<#if  question.user.sex=='boy'>
												<a href="/user/other/post/${question.user.id}"><i class="f21 fl" style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											<#if  question.user.sex!='boy'>
												<a href="/question/other/post/${topic.user.id}"><i class="f21 fl" style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											</#if>
											<#if question.user.image??>
												<a href="/user/other/post/${question.user.id}"><i class="f21 fl" style="background: url(${static}${question.user.image}) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
										<p class="fl" style="margin-top:0px;"><span class="question">
											<a href="/post/${question.id}" class="question" style="white-space: nowrap;overflow: hidden;text-overflow:ellipsis;">${question.title!''}</a></span>
										<span class="answer">
											<#if question.answerState?? && question.answerState == 'HaveAnser'>
												小伙伴你来晚了.这个问题已解答了...
											<#else>
												 跪求小伙伴们解答...
											</#if>
										</span></p>
									</dd>
								</#list>
							</dl>
						</#if>
					</#if>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="/web/js/tipso.js"></script>
		<script type="text/javascript" src="/web/js/tab.js"></script>
		<script type="text/javascript">
			var page = 2;
			var search = '${search!''}';
			
			function getMore(){
				$('.last').css('display','none');
					$.ajax({
						url:'/topic/result/page',
						type:'get',
						data:{page:page,search:search},
						dataType:'json',
						success:function(data){			
							if(data.list.length > 0){
								page++;
								for(var i=0 ; i<data.list.length;i++){
									var obj = data.list[i];
									var str = '<div class="f16 clearfix">';
									
									var image = obj.topic.user.image;
									if(image==null||image ==""){
										if(obj.user.sex =='boy'){
											image = ' style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;"'
										}else{
											image = ' style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;"'
										}
									}else{
										image = ' style="background: url(${static}'+obj.topic.user.image+') no-repeat 0 center;background-size: 100%;"'
									}
								
									str +=	'<a href="/user/other/post/'+ obj.topic.user.id +'"><i '+image+' class="f21 fl"></i></a>';
									
										str +=	'<p class="fl"><span class="author c44">'+ obj.topic.user.user+'</span>&nbsp;&nbsp;&nbsp;来自&nbsp;&nbsp;&nbsp;';
										str +=	'<span class="classify c44">';
										
									if(obj.topic.group != null){
										str += 	obj.topic.group.name;
									}
										
										str +=	'</span><br />'+obj.topic.showData+' · '+obj.topic.hits+'人阅读</p></div>';
										str += '<div class="articleName">'+obj.topic.title+'</div>';
										
									
									str += '<div class="articleCon">'+obj.content+'</div>';
									var li = $('<li></li>');
									li.html(str);
									$('#list').append(li);
								}
								
								if(data.list.length == 10){
									var li = $('<li style="list-style:none;"></li>');
									li.addClass('last');
									li.html('<a style="background-color:#c9c9c9;" href="javascript:void(0);" onclick="getMore()" >更多</a>');
									$('#list').append(li);
								}
							}
						},
						error:function(){
							alert('查询出错');
						}
					})
			}
		</script>
	</body>

</html>
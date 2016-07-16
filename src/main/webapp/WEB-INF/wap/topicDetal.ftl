
<!DOCTYPE html>
<html>

	<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
<meta name="apple-mobile-web-app-capable" content="yes">
<meta name="apple-mobile-web-app-status-bar-style" content="black">
<meta name="format-detection" content="telephone=no">

		<link rel="stylesheet" type="text/css" href="/web/css/style.css">
		<script type="text/javascript" src="/web/js/jquery.min.js"></script>
		<script type="text/javascript" src="/js/publicMethod.js"></script>
		<title>${msg.data.title}</title> 
		<script type="text/javascript">
			var replyID = null;
			var userId  = null;
			var currentPage = 1;
			var count = 11;//设置查询个数
			$(document).ready(function(){
				//帖子ID
				replyID = $("#replyID").val();
				userId =  $("#userId").val();
				getReplyList(replyID,currentPage,count);
			});
			function getReplyList(replyID,pageNum,count){
				if (!$.publicMethod.hasValue(replyID) || !$.publicMethod.hasValue(pageNum)) {
					alert("参数错误：replyID"+replyID +" pageNum" +pageNum);
					return false;
				};
				$(".last").hide();
				$.ajax({
				   type: "GET",
				   url: "/reply/queryReplyByReplyID",
				   data:{
				   		replyID:replyID,
				   		pageNum:pageNum,
				   		count:count,
				   		replyEnum:'TopicReply'
				   },
				   success: function(msg){
				       if ($.publicMethod.hasValue(msg) && msg.status == true) {
				       		if($.publicMethod.hasValue(msg.list)){
				       			//页码加1
				       			currentPage += 1;
				       			for (var i = 0; i < msg.list.length; i++) {
				       				var reply = msg.list[i];
				       				var replyJQ = [];
				       				if(i<count-1){
				       					replyJQ.push("<li><div class='f16 clearfix relative'>");
				       					var image = reply.user.image;
				       					if(image==null||image ==""){
											if(reply.user.sex =='boy'){
												image = ' style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;"'
											}else{
												image = ' style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;"'
											}
										}else{
											image = ' style="background: url(${static}'+ reply.user.image+') no-repeat 0 center;background-size: 100%;"'
										}
				       					replyJQ.push('<i class="logo" '+image+'></i>'); 
				       					replyJQ.push("<p class='fl'>");
				       					replyJQ.push("<a href=/user/other/post/"+reply.user.id+"'><span class='author c44'>"+reply.user.user+"</span></a>");
				       					
				       					if($.publicMethod.hasValue(reply.group)){
				       						replyJQ.push("&nbsp;&nbsp;&nbsp;来自&nbsp;&nbsp;&nbsp;<span class='classify c44'><a href=\"/group/detail/"+reply.group.id+"\">"+reply.group.name+"</a></span>");
				       					};
				       					replyJQ.push("<br/>");
				       					replyJQ.push(reply.showData+"</p>");
				       					//内容
				       					replyJQ.push("<p class='fl'>");
				       					replyJQ.push(reply.content);
				       					replyJQ.push("</p>");
				       					replyJQ.push("</div></li>");
				       				}else{
				       					//显示更多
				       					replyJQ.push('<li class="last" style="color: #666;border: 1px solid #eee;padding: 15px 40px;width: 50px;"><a style="background-color:#c9c9c9;" href=\'javascript:getReplyList(\"'+replyID+'\",\"'+currentPage+'\",\"'+count+'\");\'>更多</a></li>');
				       				}
				       				$(".answerList").append(replyJQ.join(""));
				       			};
				       			
				       		}else{
				       			//第一次获取没有数据时，隐藏
				       			if(currentPage == 1){
				       				$(".answerList").hide();
				       			}
				       		}	
				       }else{
				           alert("查询失败：" + msg.msg);
				       }
				   }
				});
			}
		</script>
	</head>

	<body>
		<input type="hidden" value="${id}" id="replyID"/>
		<input type="hidden" value="${userId!''}" id="userId"/>
		<div class="detail">
			<div class="content relative">
				<nav>
					<ul class="clearfix">
						<li class="fr" style="line-height: normal;margin-top:0px;"><a id="userImg" href="/user/my/post">我的</a></li>
						<li class="fr" style="line-height: normal;margin-top:0px;"><a id="UnreadMessageCount" href="/user/message">消息</a></li>
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
				<div class="quesDetail">
					<div class="part1 relative">
						<div class="title">${msg.data.title}  
						<#if msg.data.answerState??>
						
							<#if msg.data.answerState=='NotAnser'>
								<i style=" background: url(/web/images/notAnswer.jpg);"></i>
							</#if>
							<#if msg.data.answerState=='HaveAnser'>
								<i style=" background: url(/web/images/answered.jpg);"></i>
							</#if>
						</#if>
						
						</div>
						<ul class="clearfix">
							<li style="list-style:none;"><a href="/post/all/${msg.data.label.name}"><span class="c22">${msg.data.label.name}</span></a></li>
							<#if extLabels??>
							<#list  extLabels as label> 
								<li style="list-style:none;"><a href="/post/all/${label}">${label}</a></li>
							</#list>
							</#if>
						</ul>
						<div class="share abs">
							<div>
							</div>
						</div>
					</div>
					<p class="articleCon">
						${topicContent.markdown}
					</p>
					<div class="part2">
						 <ul class="personInfo">
							<li>
								<div class="f16 clearfix relative">
<#if !msg.data.user.image??>
											<#if  msg.data.user.sex=='boy'>
												<a href="/user/other/post/${msg.data.user.id}"><i class="f21 fl" style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											<#if  msg.data.user.sex!='boy'>
												<a href="/user/other/post/${msg.data.user.id}"><i class="f21 fl" style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											</#if>
											<#if msg.data.user.image??>
												<a href="/user/other/post/${msg.data.user.id}"><i class="f21 fl" style="background: url(${static!''}${msg.data.user.image}) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>									
									<p class="fl"><a href="/user/other/post/${msg.data.user.id}"><span class="author c44">${msg.data.user.user}</span></a>
									<#if msg.data.group??>
										&nbsp;&nbsp;&nbsp;来自&nbsp;&nbsp;&nbsp;
										<a href="/group/detail/${msg.data.group.id}"><span class="classify c44">
										${msg.data.group.name}</span></a>
									</#if><br />
									${msg.data.showData!""} · ${msg.data.hits!0}人阅读</p>
								</div>

							</li>
						</ul>
						<figure class="tabBlock">
							<div class="tabBlock-content">
								<div class="tabBlock-pane" style="display: block;">
									<ul class="answerList">							
									</ul>
								</div>
							</div>
						</figure>
					</div>
				</div>
			</div>
		</div>
		<!-- <script src="/web/js/tab.js"></script> -->
	</body>

</html>
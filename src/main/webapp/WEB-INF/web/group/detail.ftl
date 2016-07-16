

<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title>大小营地</title>
		<meta name="keywords" content="">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<meta name="description" content="">
		<link rel="stylesheet" type="text/css" href="/web/css/style.css">
		<link rel="stylesheet" type="text/css" href="/web/css/reveal.css" />
		<script type="text/javascript" src="/web/js/jquery.min.js"></script>
			<script type="text/javascript" src="/js/publicMethod.js"></script>
		<style>
			.li-more-little{
				margin-right:20px;
			}
			.out-link{
				color:red;
				font-family:微软雅黑;
			}
		</style>
	</head>

	<body>
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
			<input type="hidden" value="${userId!''}" id="userId"/>
			<input type="hidden" value="${group.id}" id="groupId"/>
			<input type="hidden" value="${isAdded!''}" id="isAdded"/>
			<input type="hidden" value="${isGroupOwn!''}" id="isOwnner"/>
			<div class="content clearfix campDetailPage">
				<div class="left">
					<div class="part2 relative">
						<div class="title clearfix">
							<div class="fl">
								<i class="recommend"></i>
								<p class="f21">${group.name!""}</p>
								<p class="f16">${group.description!""}</p>
								<p class="f16">规模  ${group.foucsPersonNum}   |   帖子  ${group.topicsNum} </p>
							</div>
								<div class="blueBtn" id="quitgroup"><a href="javascript:quitGroup()" class="f16">退出营地</a></div>
								<div class="blueBtn" id="addgroup"><a href="javascript:addGroup()" class="f16">加入营地</a></div>
						</div>
						<ul >
							<li class="last" style="list-style:none;"><a style="background-color:#c9c9c9;" href="javascript:loadTopic()">更多</a></li>
						</ul>
					</div>
				</div>
				<div class="right">
					<figure class="tabBlock" style="margin: 0px;">
						<ul class="tabBlock-tabs">
							<li class="tabBlock-tab is-active">
								<h3>营地成员</h3></li>
							<li class="tabBlock-tab">
								<h3 onclick="alert('如果你是营地创建人请发邮件到question@corer.me 我们可以给你们提供删除圈子和踢出营地的操作')">设置圈子</h3></li>
						</ul>
						<div class="tabBlock-content">
							<div class="tabBlock-pane" style="display: block;">
								<ul class="friendList clearfix" style="padding-left: 0px;">
								</ul>
							</div>
							<div class="tabBlock-pane" style="display: none;">

							</div>
						</div>
					</figure>
					<figure class="tabBlock" style="margin: 0px;">
						<ul class="tabBlock-tabs">
							<li class="tabBlock-tab is-active">
								<h3>热门标签</h3></li>
							<li class="tabBlock-tab">
						</ul>
						<div class="tabBlock-content">
							<div class="tabBlock-pane" style="display: block;">
								<ul class="boxList clearfix" style="padding-left: 0px;">
								<#list labels as label> 
									<li><a href="/post/all/${label}">${label}</a></li>
								</#list>
								</ul>
							</div>
							<div class="tabBlock-pane" style="display: none;">

							</div>
						</div>
					</figure>
					<!--	<h3>热门标签</h3>-->
					<#if (questions?size>0)>
						<dl class="QAlist">
							<dt>技术问答</dt>
							
							<#list questions as question>
							<dd class="clearfix" style="margin-left:0px;">
								<i class="logo">前</i>
								<p class="fl" style="margin-top:0px;"><a href="/post/${question.id}"><span class="question">${question.title!''}</span>	
								<span class="answer">
								<#if question.answerState?? && question.answerState == 'HaveAnser'>
									 小伙伴你来晚了.这个问题已解答了...
								<#else>
									 跪求小伙伴们解答...
								</#if>
								</span>
								
								</p>
							</dd>
							</#list>
						</dl>
					</#if>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="/web/js/tipso.js"></script>
		<script type="text/javascript" src="/web/js/tab.js"></script>
		<script type="text/javascript" src="/js/publicMethod.js"></script>
		<script type="text/javascript">
			var userId = null;
			var groupId = null;
			var isOwnner = null;
			var start = 0;
			var count = 11;
			var currentPage = 1;
			$(function(){
				userId = $("#userId").val();
				groupId = $("#groupId").val();
				isAdded = $("#isAdded").val();
				isOwnner = $("#isOwnner").val();
				if(isAdded == "add"){
					$("#quitgroup").show();
					$("#addgroup").hide();
				}else{
					$("#addgroup").show();
					$("#quitgroup").hide();
				}
				getUserList(1);
				loadTopic();
			})
			function getUserList(pageNum,count){
				if (!$.publicMethod.hasValue(pageNum)) {
					alert("参数错误 pageNum" +pageNum);
					return false;
				};
				if (!$.publicMethod.hasValue(count)) {
					count = 11;
				};
				$(".friendList").empty();
				$.ajax({
				   type: "GET",
				   url: "/group/queryGroupUser",
				   data:{
				   		pageNum:pageNum,
				   		count:count,
				   		groupId:groupId
				   },
				   success: function(msg){
				       if ($.publicMethod.hasValue(msg) && msg.status == true) {
				       		if($.publicMethod.hasValue(msg.list)){
				       			var replyJQ = [];
				       			for (var i = 0; i < msg.list.length; i++) {
				       				var user = msg.list[i];
				       				
				       				var image = user.image;
							if(image==null||image ==""){
								if(user.sex =='boy'){
									image = ' style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;"'
								}else{
									image = ' style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;"'
								}
							}else{
								image = ' style="background: url(${static}'+user.image+') no-repeat 0 center;background-size: 100%;"'
							}
				       				if(i<count-1){
				       					replyJQ.push('<li style="list-style:none;width:148px;"><div class="f16 clearfix">');
				       					replyJQ.push('<a  href="/user/other/post/'+user.id+'"><i class="logo" '+image+'></i></a>');
				       					replyJQ.push('<p class="fl" style="margin:0px;"><a href="/user/other/post/'+user.id+'"><span class="author c44">'+user.user+'</span><span class="classify c99" style="width:64px;overflow:hidden;text-overflow:ellipsis;">'+user.chinaName+'</span></a>');

				       					if(isOwnner == "owner" && user.id != userId){
				       						replyJQ.push("<a class='out-link' href=\"javascript:outGroup(\'"+user.id+"\')\">踢出</a>")
				       					}else if(isOwnner == "owner" && user.id == userId){
				       						replyJQ.push("<a href=\"javascript:void(0);\">创建者</a>")
				       					}
				       					replyJQ.push('</div></li>');
				       				}else {
				       					replyJQ.push("<div class='li-more-little' style='clear:both;'</div>");
				       					if(currentPage !=1){
				       						replyJQ.push("<li class='li-more-little'><span class='c22'><a href=\"javascript:SPage(\'"+currentPage+"\');\">上一页</a></span></li>")
				       					}
				       					replyJQ.push("<li class='li-more-little'><span class='c22'><a href=\"javascript:Dpage(\'"+currentPage+"\');\">下一页</a></span></li>")
				       				}
				       			};
				       			if( msg.list.length < count && pageNum!=1){
				       				replyJQ.push("<div class='li-more-little' style='clear:both;'</div>");
			       					replyJQ.push("<li class='li-more-little'><span class='c22'><a href=\"javascript:SPage(\'"+currentPage+"\');\">上一页</a></span></li>")
				       			}
				       			$(".friendList").show();
				       			$(".friendList").append(replyJQ.join(""));
				       		}else{
				       			$(".friendList").hide();
				       		}	
				       }else{
				           alert("请求错误：" + msg.msg);
				       }
				   }
				});
			}
			function SPage(){
				currentPage --;
				getUserList(currentPage);
			}
			function Dpage(){
				currentPage ++;
				getUserList(currentPage);
			}
			function outGroup(outId){
				$.ajax({
					   type: "GET",
					   url: "/group/outGroup",
					   data:{
					   		groupId:groupId,
					   		outUserId:outId
					   },
					   success:function(msg){
						   if(msg.status){
							   alert("操作成功！");
							   currentPage = 1;
							   getUserList(1);
						   }else{
							   alert("操作失败:"+msg.msg);
						   }
						}
					});
			}
			function loadTopic() {
				$.get("/group/getTopicsByGroupId", {"groupId":"${group.id}", "start":start, "count":11}, function(result) {
					if (result.status) {

						if (result.data.length != 11) {
							$(".last").hide();
						} else {
							result.data.length -= 1;
						}
				
						for (var i = 0; i < result.data.length; i++) {
							
							var image = result.data[i].user.image;
							if(image==null||image ==""){
								if(result.data[i].user.sex =='boy'){
									image = ' style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;"'
								}else{
									image = ' style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;"'
								}
							}else{
								image = ' style="background: url(${static}'+result.data[i].user.image+') no-repeat 0 center;background-size: 100%;"'
							}
							
							
							var data = '<li>'
								+'<div class="f16 clearfix">'
									+'<a href="/user/other/post/'+result.data[i].user.id+'"><i class="logo" '+image+'></i><a>'
									+'<p class="fl"><a href="/user/other/post/'+result.data[i].user.id+'"><span class="author c44">'+result.data[i].user.user+'</span></a>&nbsp;&nbsp;&nbsp;来自&nbsp;&nbsp;&nbsp; <span class="classify c44">${group.name!""}</span><br />2天前 · '+result.data[i].hits+'人阅读</p>'
						+'</div><a href="/post/'+result.data[i].id+'"><div class="articleName">'+result.data[i].title+'</div></a>';
						
					
							$(".last").before(
								data +'<a href="/post/'+result.data[i].id+'"><div class="articleCon">'+result.data[i].simpleContent+'</div></a>'
							+'</li>');
						}
						
		
						start += 11;
					
					} else if (result.status){
						alert(result.msg);
					} 
				})
			}

			function addGroup() {
				if(!$.publicMethod.hasValue(userId)){
					alert("请先登录！");
					$.publicMethod.redirectToLogin();
					return false;
				}
				$.get("/group/addGroup", {"groupid":groupId, "userid":userId}, 
				function(result) {
					if(result.status){
						alert(result.msg);
					}else{
						alert("操作失败："+result.msg);
					}
				})
			}
			function quitGroup() {
				if(!$.publicMethod.hasValue(userId) || !$.publicMethod.hasValue(groupId)){
					alert("请先登录！");
					$.publicMethod.redirectToLogin();
					return false;
				}
				$.get("/group/quitGroup", {"groupId":groupId, "userId":userId}, 
				function(result) {
					if(result.status){
						alert(result.msg);
						$("#addgroup").show();
						$("#quitgroup").hide();
					}else{
						alert("操作失败："+result.msg);
					}
				})
			}

		</script>

	</body>

</html>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<meta charset="utf-8">
		<title>聚集之美 ${userdata.user} 的主页</title>
		<link rel="stylesheet" type="text/css" href="/web/css/style.css">
		<link rel="stylesheet" type="text/css" href="/web/css/reveal.css" />
		<link rel="stylesheet" type="text/css" href="/web/css/tab.css" />
		<script type="text/javascript" src="/js/jquery.min.js"></script>
		<script type="text/javascript" src="/js/publicMethod.js"></script>

	</head>
<style>
	#alldan {
    font-size: 18px;
    text-align: center;
    margin: 55px 0 50px;
    border-bottom: 0px solid #e5e5e5;
}
	.msg-list{
		width: 100%;
	    padding-left: 0px !important;
	    padding-top: 20px !important;
	}
</style>
	<body>
		<div class="my">
			<div class="content relative">
				<nav>
					<ul class="clearfix">
						<li class="fr"><a id="userImg" href="/user/my/post">我的</a></li>
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
					<div class="title">我的消息
				</div>
				<div>
					<a href="javascript:void(0);" onclick="setAllRead()">全部标记为已读</a>&nbsp;&nbsp;
					<a href="javascript:void(0);" onclick="delteAllMsg()">删除所有消息</a>
				</div>
				<div id="tab">
					<h3 id="two1" class="up" onclick="setContentTab(&#39;two&#39;,1,2)">未读消息</h3>
					<div class="part2 relative block" id="con_two_1">
						<ul id="list" class="unpartContent">
						</ul>
					</div>
					
					<h3 id="two2" onclick="setContentTab(&#39;two&#39;,2,2)">已读消息</h3>
					<div class="part2 relative" id="con_two_2">
						<ul id="list" class="partContent">
						</ul>
					</div>
				</div>	
		<footer></footer>
	</body>

<script type="text/javascript">
	var userId = ${userId};
	var currentPage1 = 1; //未读起始页
	var currentPage2 = 1; //已读起始页
	
	var count = 11;      //每页个数+1
	var status = "noread";//
	var flag = false;//默认加载标志
	
	$(document).ready(function(){;	
		//加载默认消息列表
		getMessageList(currentPage1,count);
	});
	function getMessageList(pageNum,count){
		if (!$.publicMethod.hasValue(pageNum)) {
			alert("参数错误 pageNum" +pageNum);
			return false;
		};
		if(!$.publicMethod.hasValue(count)){
			count = 11;
		}
		
		if(status == 'noread'){
		 	$(".unpartContent .li-more").hide();
		}
		
		if(status == 'read'){
			$(".partContent .li-more").hide();
		}

		$.ajax({
		   type: "GET",
		   url: "/msg/queryUserMsgList",
		   data:{
		   		pageNum:pageNum,
		   		count:count,
		   		userId:userId,
		   		status: status
		   },
		   success: function(msg){
		       if ($.publicMethod.hasValue(msg) && msg.status == true) {
		       		var parents = $(".unpartContent");
		       
		       		if($.publicMethod.hasValue(msg.list)){
		       			if(status == 'read'){
		       				parents = $(".partContent");
		       				currentPage2 += 1;
		       			}else{
		       				currentPage1 += 1;
		       			}
		       			
		       			
		       			var replyJQ = [];
		       			for (var i = 0; i < msg.list.length; i++) {
		       				var message = msg.list[i];
		       				if(i<count-1){
		       					replyJQ.push('<li class="msg-list">');
		       					
		       					if(status == 'noread'){
		       						replyJQ.push('<span class=""><img src="/web/images/20160611223928.jpg" style="width:20px;height:20px;"/>消息：'+message.content+'</a></span></br>');
		       					}else{
		       						replyJQ.push('<span class="">消息：'+message.content+'</a></span></br>');
		       					}
		       					
		       					if(message.type=="addGroup"){
		       						replyJQ.push('<p><a href="javascript:handleMsg('+message.id+', 2);">同意</a>·<a href="javascript:handleMsg('+message.id+',3);">拒绝</a></p>')
		       					}
		       					replyJQ.push('<p>'+$.publicMethod.getLocalTime(message.createDate) +'·' +(message.status=="noread"?"未阅读":"已阅读")+'</p>');
		       					replyJQ.push('</li>');
		       				}else{
		       					if(status == 'noread'){
		       						replyJQ.push("<li class='li-more'><span class='c22'><a  href=\"javascript:getMessageList(\'"+currentPage1+"\',\'"+count+"\');\">更多</a></span></li>")
		       					}else{
		       						replyJQ.push("<li class='li-more'><span class='c22'><a  href=\"javascript:getMessageList(\'"+currentPage2+"\',\'"+count+"\');\">更多</a></span></li>")
		       					}
		       				}
		       			};
		       			parents.show();
		       			parents.append(replyJQ.join(""));
		       		}
		       }else{
		           alert("请求错误：" + msg.msg);
		       }
		   }
		});
	}
	
	function handleMsg(msgID, status) {
		if (status == 2) {
			status = "agree";
		} else {
			status = "noagree";
		}
		jQuery.get('/msg/agree', {msgID: msgID, status: status}, function(data, textStatus, xhr) {
			// if (data.status)
			alert(data.msg);
			if (data.status) {
				window.location.reload();
			}
		});
		
	}
	
	//全部已读
	function setAllRead(){
		if(confirm("您确定把所有消息标为已读？")){
			$.ajax({
				url:'/msg/setAllRead',
				type:'get',
				dataType:'json',
				success:function(data){
					if(data.status){
						window.location.reload();
					}else{
						alert('操作出错');
					}
				},
				error:function(){
					alert('error');
				}
			});
		}
	}
	
	function delteAllMsg(){
		if(confirm("您确定删除所有消息？该操作无法将修改！")){
			$.ajax({
				url:'/msg/delteAllMsg',
				type:'get',
				dataType:'json',
				success:function(data){
					if(data.status){
						window.location.reload();
					}else{
						alert('操作出错');
					}
				},
				error:function(){
					alert('error');
				}
			});
		}
	}
	
	function setContentTab(name, curr, n) {
		if(n == 1){
			status = 'noread';
		}
		
		if(n == 2){
			status = 'read';
		}
		
		//第一次点击已读消息时
		if(!flag && status == 'read'){
			getMessageList(currentPage2,count);
			flag = true;
		}
	
	    for (i = 1; i <= n; i++) {
	        var menu = document.getElementById(name + i);
	        var cont = document.getElementById("con_" + name + "_" + i);
	        menu.className = i == curr ? "up" : "";
	        if (i == curr) {
	            cont.style.display = "block";
	        } else {
	            cont.style.display = "none";
	        }
	    }
	}
</script>
</html>
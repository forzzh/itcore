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
	</head>

	<body>
		<div class="camp">
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
				<div class="left">
					<div class="part1">
						<div class="title">所有营地</div>
					</div>
					<div class="part2 relative">
						<ul class="friendList allCamp clearfix">
						   <div id="content">
							
							</div>
							<div id="more">
							</div>
						</ul>
					</div>
				</div>	
				<div class="right">
					<div class="topic2">
						<p>搜索营地</p>
						<div class="relative">
							<i class="abs"></i>
							<input type="text" name="" id="input" value="" placeholder="搜索..."/>
						</div>
					</div>
					<h3>热门标签</h3>
					<ul class="boxList clearfix">
						<#if (labels?size>0)>   
							<#list labels as label>
								<li><a href="/post/all/${label.name}">${label.name}</a></li>
							</#list>
						</#if>
					</ul>
					<dl class="newSoftware">
						<dt>最新软件</dt>
						<#if adTopics??>   
							<#list adTopics as ad>
								<dd class="clearfix" style="margin-left:0px;">
									<#if ad.image??>
										<img src="${ad.image}" alt="" width="49" class="fl"/>
									<#else>
										<img src="/web/images/ai.jpg" alt="" width="49" class="fl"/>
									</#if>
									<p class="fl" style="width: 267px;white-space: nowrap;text-overflow:ellipsis;overflow: hidden;"><a href="/post/detail/${ad.id}"><span>${ad.title}</span></a><br />
									${ad.extLabels!''}</p>
								</dd>
							</#list>
						</#if>
					</dl>
					<dl class="QAlist">
						<dt>技术问答</dt>
						<#if (requestTopics?size>0)>  
							<#list requestTopics as question>
								<dd class="clearfix" style="margin-left:0px;">
									<i class="fl">前</i>
									<p class="fl" style="margin-top:0px;"><a href="/post/${question.id}"><span class="question">${question.title}</span></a>
									<#if question.answerState?? && question.answerState == 'HaveAnser'>
										<span class="answer">小伙伴你来晚了.这个问题已解答了...</span></p>
									<#else>
										<span class="answer">跪求小伙伴们解答...</span></p>
									</#if>
								</dd>
							</#list>
							
						</#if>
						<!-- <dd class="clearfix">
							<i class="fl">前</i>
							<p class="fl"><span class="question">“ 问什么Java这么这么这么难学习
    呢？是我智商有问题吗？”</span><span class="answer">― 没错就是因为你笨笨笨笨笨...</span></p>
						</dd> -->
					</dl>
				</div>
			</div>
		</div>
		<script type="text/javascript" src="js/tipso.js"></script>
		<script type="text/javascript" src="js/pop.js"></script>
		<script type="text/javascript">
		//console.log(123)
		$(function(){
           getGrouplist(1);		
		});
		
		
		var count=22;
		function getGrouplist(obj){
		next = obj+1;
		$.get("/group/getListGroup",{"pageNow":obj,"count":count},function(data){
		data=data.list;
		var length="";
		var str1="";
		if(data.length>=23){
		length=data.length-1;
		str1='<li class="last" style="list-style:none;"><a style="background-color:#c9c9c9;" href="javaScript:void(0)" onclick="getGrouplist('+next+')"> 更多</a></li>';
		}else{
		length=data.length;
		}
		var str=$("#content").html();
		for(var i=0;i<length;i++){
		    str+='<li>';
			str+='<div class="f16 clearfix">';
			str+='<i class="logo" style=\'    background-size: 100%;background-image:url("${static}'+data[i].image+'")\'>'+'</i>';
			var url ='/group/detail/'+data[i].id;
			str+='<p class="fl"><a href="'+url+ '"><span class="author">'+data[i].name+'</span></a><span class="classify c99" >规模  '+data[i].extLabels+'&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;帖子  '+data[i].topicsNum+'  </span>';
			str+='</div>';
			str+='</li>';
		}
		$("#content").html(str);
		$("#content").append("<li style='display:none;'></li>");
		$("#more").html(str1);
		},"json");
		
		}
		
	$("#input").blur(function(){
	   var value=$("#input").val();
       $.get("/group/searchByName",{"name":value},function(data){
       if(data.status){
        var data = data.list;
        var str ="";
        for(var i=0;i<data.length;i++){
		       str+='<li>';
			str+='<div class="f16 clearfix">';
			str+='<i class="logo" style=\'    background-size: 100%;background-image:url("${static}'+data[i].image+'")\'>'+'</i>';
			var url ='/group/detail/'+data[i].id;
			str+='<p class="fl"><a href="'+url+'"><span class="author">'+data[i].name+'</span></a><span class="classify c99" >规模  '+data[i].extLabels+'&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;帖子  '+data[i].topicsNum+'  </span>';
			str+='</div>';
			str+='</li>';
		}
		$("#content").html(str);
		$("#more").html("");
       }
     },"json");
       	});
       	
       	
       	document.getElementById("input").onkeypress = function(e){
    		checkChar(e);
		}
 
		function checkChar(e){
		    e = window.event || e;
		    var code = e.keyCode || e.which;
		    var reg = new RegExp("[~'！!·@#$%^&*()-+_=:]");
		    if(reg.test(String.fromCharCode(code))){
		        if(window.event){
		            e.returnValue = false;
		        }else{
		            e.preventDefault();
		        }
		    }
		}
		</script>
	</body>

</html>
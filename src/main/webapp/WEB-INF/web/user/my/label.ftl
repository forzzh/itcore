
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<meta charset="utf-8">
		<title>聚集之美 ${userdata.user} 的主页</title>
		<#if show!=true>
		<meta name="keywords" content="我是${userdata.user} 这些都是我的标签 ">
		<meta name="description" content="我是 ${userdata.user} 欢迎来到聚集之美 ">
		</#if>
		<link rel="stylesheet" type="text/css" href="/web/css/style.css">
		<link rel="stylesheet" type="text/css" href="/web/css/reveal.css" />
		<link rel="stylesheet" href="/Jcrop-v0.9.12/demos/demo_files/main.css" type="text/css" />
		<link rel="stylesheet" href="/Jcrop-v0.9.12/demos/demo_files/demos.css" type="text/css" />
		<link rel="stylesheet" href="/Jcrop-v0.9.12/css/jquery.Jcrop.css" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/uploadify/uploadify.css">
		<script type="text/javascript" src="/js/jquery.min.js"></script>
		<script type="text/javascript" src="/js/tipso.js"></script>
		<script type="text/javascript" src="/web/js/jquery.reveal.js"></script>
		<script type="text/javascript" src="/js/publicMethod.js"></script>
		<script type="text/javascript" src="/js/jquery.form.js"></script>
		<script type="text/javascript" src="/uploadify/jquery.uploadify.min.js"></script>
		<script type="text/javascript" src="/Jcrop-v0.9.12/js/jquery.Jcrop.js"></script>
		<style type="text/css">
			.reveal-modal-bg1 .reveal-modal-bg {
			    position: fixed;
			    height: 100%;
			    width: 100%;
			    background: #000;
			    background: rgba(0, 0, 0, .8);
			    z-index: 100;
			    top: 0;
			    left: 0;
			}
			#file_upload{
		   		margin-top: 80px;
		   	}
		</style>
	</head>

	<body>
	
	<script>
	
	</script>
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
					
						<#include "../common/userInfo.ftl"> 
				
					<div class="title" style="<#if show??><#if show!=true>margin-top:100px;</#if></#if>"><#if show==true>我</#if><#if show!=true><#if userdata.sex=='boy'>他</#if><#if userdata.sex!='boy'>她</#if></#if>的标签
					</div>
					<dl class="labelPart myLabel">
						<dt><#if show==true>我</#if><#if show!=true><#if userdata.sex=='boy'>他</#if><#if userdata.sex!='boy'>她</#if></#if>关注的</dt>
						<dd class="partContent">
							<ul  class="clearfix myfocus">
							<div id="focus"></div>
							<div id="more2"></div>
							</ul>
						</dd>
					</dl>
				<dl class="labelPart myLabel">
						<dt><#if show==true>我</#if><#if show!=true><#if userdata.sex=='boy'>他</#if><#if userdata.sex!='boy'>她</#if></#if>创建的</dt>
						<dd class="partContent">
							<ul id="" class="clearfix myfocus">
							<div id="create"></div>
							<div id="more1"></div>
							</ul>
						</dd>
					</dl>
					
					
				</div>
			</div>
		</div>
		<form id="idForm">
		<input type="hidden" name="id" id="id" value="${id}"/>
		</form>

		<div id="createCamp" class="reveal-modal add">
			<div class="model-title">创建圈子<a class="close-reveal-modal">&#215;</a></div>
			<form action="">
				<div class="inputDiv">
					<label for="">名称</label><input type="text" name="" id="" value="" /></div>
				<div class="inputDiv"> <label for="">描述</label><input type="text" name="" id="" value="" />
				</div>
				<div class="buttonGroup">
					<button class="close">关闭</button><button type="submit">保存</button>
				</div>
			</form>
		</div>
		<div id="publishTopic" class="reveal-modal publish">
			<div class="model-title">发帖子<a class="close-reveal-modal">&#215;</a></div>
			<form action="">
				<div class="inputDiv">
					<label for="">标题</label><input type="text" name="" id="" value="" />
				</div>
				<div class="inputDiv">
					<label for="">图片</label><a href="" class="file">上传<input type="file" name="" id="" value="" /></a>
				</div>
				<div class="inputDiv">
					<label for="">正文</label><textarea name="" id="" cols="30" rows="10"></textarea>
				</div>
				<div class="inputDiv">
					<label for="">标签</label>
					<a href="#" class="createCamp"></a>
				<!--	<a href="#" class="createCamp" data-reveal-id="publishTopic"></a>-->
				</div>
				<div class="buttonGroup">
					<button class="close">关闭</button><button type="submit">保存</button>
				</div>
			</form>
		</div>
		<div id="askTopic" class="reveal-modal ask">
			<div class="model-title">发布问题<a class="close-reveal-modal">&#215;</a></div>
			<form action="">
				<div class="inputDiv">
					<label for="">问题</label><input type="text" name="" id="" value="" />
				</div>
				<div class="inputDiv">
					<label for="">描述</label><textarea name="" id="" cols="30" rows="10"></textarea>
				</div>
				<div class="inputDiv">
					<label for="">标签</label>
					<a href="#" class="createCamp" data-reveal-id="publishTopic"></a>
				</div>
				<div class="buttonGroup">
					<button class="close">关闭</button><button type="submit">发布问题</button>
				</div>
			</form>
		</div>
		<div id="answerTopic" class="reveal-modal answer">
			<div class="model-title">回答问题<a class="close-reveal-modal">&#215;</a></div>
			<form action="">
				<div class="inputDiv">
					<label for="">问题</label>
					<select name="">
						<option value="">如何设计好用的触控手势?1</option>
						<option value="">如何设计好用的触控手势?2</option>
					</select>
				</div>
				<div class="inputDiv">
					<label for="">答案</label><textarea name="" id="" cols="30" rows="10"></textarea>
				</div>
				<div class="buttonGroup">
					<button class="close">关闭</button><button type="submit">回答问题</button>
				</div>
			</form>
		</div>
		

<script>




$(function(){
createLabel(1);
focuslabelPage(1);
});

var id =$("#id").val();
var count=15;
function createLabel(obj){
next = obj+1;
$.get("/group/createLabel",{"id":id,"pagenum":obj,"count":count},function(data){
if(data.status){
var str1 =$("#create").html();
var length="";
if(data.list.length>=16){
length=data.list.length-1;
}else{
length=data.list.length;
}
for(var i=0;i<length;i++){
//console.log(data.list[i])
//console.log(1231231);;
str1+='<li ><a href="/post/all/'+ data.list[i].name +'">'+data.list[i].name+'</a></li>';
}
var str2="";
if(data.list.length >= 16){
    str2='<li style="list-style:none;"><a style="background-color:#c9c9c9;" href="javascript:void(0)" onclick="createLabel('+next+')">更多</a></li>';
}
$("#create").html(str1);
$("#more1").html(str2);
}
},"json");

};

function focuslabelPage(obj){
next=obj+1;
$.get("/group/focuse",{"id":id,"pagenum":obj,"count":count},function(data){
if(data.status){
var str3 =$("#focus").html();
var length1="";
if(data.list.length>=16){
length1=data.list.length-1;
}else{
length1=data.list.length;
}
for(var i=0;i<length1;i++){
//console.log(data.list[i])
str3+='<li   ><a href="/post/all/'+ data.list[i].label.name +'">'+data.list[i].label.name+'</a></li>';
}
var str4="";
if(data.list.length >=16){
   str4='<li style="list-style:none;"><a style="background-color:#c9c9c9;" href="javascript:void(0)" onclick="focuslabelPage('+next+')">更多</a></li>';
}
$("#focus").html(str3);
$("#more2").html(str4);
}
},"json");
};



</script>
	</body>
	
</html>
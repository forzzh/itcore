
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<meta charset="utf-8">
		<title>�ۼ�֮�� ${userdata.user} ����ҳ</title>
		<#if show!=true>
		<meta name="keywords" content="����${userdata.user} ��Щ�����ҵı�ǩ ">
		<meta name="description" content="���� ${userdata.user} ��ӭ�����ۼ�֮�� ">
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
						<li class="fr"><a id="userImg" href="/user/my/post">�ҵ�</a></li>
						<li class="fr"><a id="UnreadMessageCount" href="/user/message">��Ϣ</a></li>
					</ul>
				</nav>
			</div>
		</div>
		<div id="menuTop" style="color: black;width: 100%;text-align: center;">
			<ul style="position: relative;left:-100px;margin: 16px 0px 16px 0px;">
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><img width="48" height="48" alt="javaͼ��" src="/web/images/logo.jpg"><br><div style="text-align: center;width: 65px;position: relative;left:-8px;">�ۼ�֮��</div></li>
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><img width="48" height="48" alt="javaͼ��" src="/web/images/cloud_48dp.png"><br><div style="text-align: center;width: 48px;">Ȧ��</div></li>
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><img width="48" height="48" alt="javaͼ��" src="/web/images/logo.png"><br><div style="text-align: center;width: 48px;">��ǩ</div></li>
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><div style="width: 48px;height: 48px;background-color: #c52062;border-radius:50%;"></div><br><div style="text-align: center;width: 48px;">ͼ��</div></li>
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><div style="width: 48px;height: 48px;background-color: #eceff1;border-radius:50%;color: #039be5;text-align: center;line-height: 48px;">&bull;&bull;&bull;</div><br><div style="text-align: center;width: 48px;">����</div></li>
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
				
					<div class="title" style="<#if show??><#if show!=true>margin-top:100px;</#if></#if>"><#if show==true>��</#if><#if show!=true><#if userdata.sex=='boy'>��</#if><#if userdata.sex!='boy'>��</#if></#if>�ı�ǩ
					</div>
					<dl class="labelPart myLabel">
						<dt><#if show==true>��</#if><#if show!=true><#if userdata.sex=='boy'>��</#if><#if userdata.sex!='boy'>��</#if></#if>��ע��</dt>
						<dd class="partContent">
							<ul  class="clearfix myfocus">
							<div id="focus"></div>
							<div id="more2"></div>
							</ul>
						</dd>
					</dl>
				<dl class="labelPart myLabel">
						<dt><#if show==true>��</#if><#if show!=true><#if userdata.sex=='boy'>��</#if><#if userdata.sex!='boy'>��</#if></#if>������</dt>
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
			<div class="model-title">����Ȧ��<a class="close-reveal-modal">&#215;</a></div>
			<form action="">
				<div class="inputDiv">
					<label for="">����</label><input type="text" name="" id="" value="" /></div>
				<div class="inputDiv"> <label for="">����</label><input type="text" name="" id="" value="" />
				</div>
				<div class="buttonGroup">
					<button class="close">�ر�</button><button type="submit">����</button>
				</div>
			</form>
		</div>
		<div id="publishTopic" class="reveal-modal publish">
			<div class="model-title">������<a class="close-reveal-modal">&#215;</a></div>
			<form action="">
				<div class="inputDiv">
					<label for="">����</label><input type="text" name="" id="" value="" />
				</div>
				<div class="inputDiv">
					<label for="">ͼƬ</label><a href="" class="file">�ϴ�<input type="file" name="" id="" value="" /></a>
				</div>
				<div class="inputDiv">
					<label for="">����</label><textarea name="" id="" cols="30" rows="10"></textarea>
				</div>
				<div class="inputDiv">
					<label for="">��ǩ</label>
					<a href="#" class="createCamp"></a>
				<!--	<a href="#" class="createCamp" data-reveal-id="publishTopic"></a>-->
				</div>
				<div class="buttonGroup">
					<button class="close">�ر�</button><button type="submit">����</button>
				</div>
			</form>
		</div>
		<div id="askTopic" class="reveal-modal ask">
			<div class="model-title">��������<a class="close-reveal-modal">&#215;</a></div>
			<form action="">
				<div class="inputDiv">
					<label for="">����</label><input type="text" name="" id="" value="" />
				</div>
				<div class="inputDiv">
					<label for="">����</label><textarea name="" id="" cols="30" rows="10"></textarea>
				</div>
				<div class="inputDiv">
					<label for="">��ǩ</label>
					<a href="#" class="createCamp" data-reveal-id="publishTopic"></a>
				</div>
				<div class="buttonGroup">
					<button class="close">�ر�</button><button type="submit">��������</button>
				</div>
			</form>
		</div>
		<div id="answerTopic" class="reveal-modal answer">
			<div class="model-title">�ش�����<a class="close-reveal-modal">&#215;</a></div>
			<form action="">
				<div class="inputDiv">
					<label for="">����</label>
					<select name="">
						<option value="">�����ƺ��õĴ�������?1</option>
						<option value="">�����ƺ��õĴ�������?2</option>
					</select>
				</div>
				<div class="inputDiv">
					<label for="">��</label><textarea name="" id="" cols="30" rows="10"></textarea>
				</div>
				<div class="buttonGroup">
					<button class="close">�ر�</button><button type="submit">�ش�����</button>
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
    str2='<li style="list-style:none;"><a style="background-color:#c9c9c9;" href="javascript:void(0)" onclick="createLabel('+next+')">����</a></li>';
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
   str4='<li style="list-style:none;"><a style="background-color:#c9c9c9;" href="javascript:void(0)" onclick="focuslabelPage('+next+')">����</a></li>';
}
$("#focus").html(str3);
$("#more2").html(str4);
}
},"json");
};



</script>
	</body>
	
</html>
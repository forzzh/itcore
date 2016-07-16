
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<meta charset="utf-8">
		<title>聚集之美 ${userdata.user} 的主页</title>
		<#if show!=true>
		<meta name="keywords" content="我是${userdata.user} 这些都是我的圈子 ">
		<meta name="description" content="我是 ${userdata.user} 欢迎来到聚集之美 ">
		</#if>
		<link rel="stylesheet" type="text/css" href="/web/css/style.css">
		<link rel="stylesheet" type="text/css" href="/web/css/reveal.css" />
		<link rel="stylesheet" href="/Jcrop-v0.9.12/demos/demo_files/main.css" type="text/css" />
		<link rel="stylesheet" href="/Jcrop-v0.9.12/demos/demo_files/demos.css" type="text/css" />
		<link rel="stylesheet" href="/Jcrop-v0.9.12/css/jquery.Jcrop.css" type="text/css" />
		<script type="text/javascript" src="/js/jquery.min.js"></script>
		<script type="text/javascript" src="/js/publicMethod.js"></script>
		<script type="text/javascript" src="/web/js/jquery.reveal.js"></script>
		<script type="text/javascript" src="/uploadify/jquery.uploadify.min.js"></script>
		<link rel="stylesheet" type="text/css" href="/uploadify/uploadify.css">  
		<script type="text/javascript" src="/js/jquery.form.js"></script>
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
	<style>
	.uploadify-queue{
	display:none;}
	</style>
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
						<#include "../common/userInfo.ftl"> 
			<div class="title" style="<#if show??><#if show!=true>margin-top:100px;</#if></#if>"><#if show==true>我</#if><#if show!=true><#if userdata.sex=='boy'>他</#if><#if userdata.sex!='boy'>她</#if></#if>的营地
						<a href="/group/addGroupPage" class="createCamp"></a>
					</div>
					<dl class="labelPart myLabel">
						<dt><#if show==true>我</#if><#if show!=true><#if userdata.sex=='boy'>他</#if><#if userdata.sex!='boy'>她</#if></#if>创建的</dt>
						<dd class="partContent">
							<ul id="create" class="boxList clearfix">
								
							</ul>
						</dd>
					</dl>
					<dl class="labelPart myLabel">
						<dt><#if show==true>我</#if><#if show!=true><#if userdata.sex=='boy'>他</#if><#if userdata.sex!='boy'>她</#if></#if>关注的</dt>
						<dd class="partContent">
							<ul  class="boxList clearfix">
							<div id="focus"></div>
							<div id="moreF"></div>
								
							</ul>
						</dd>
					</dl>
				</div>
			</div>
		</div>
		
		  
			<div id="publishTopic" class="reveal-modal publish" style="height:740px;">
			<div class="model-title">创建圈子<a class="close-reveal-modal">&#215;</a></div>
			
			<form id="addGroup">
				<div class="inputDiv">
					<label for="">圈子名称</label><input type="text" name="name" id="name" value="" />
				</div>
				<div>
				  <input type="hidden" name="user.id" id="id" value="${groupid}"/>
				</div>
				<input  value=""  type="hidden"  name="image" id="image1">
				<div class="inputDiv">
					<label for="">图片</label>上传<input type="file" name="file_upload1" id="file_upload1" value="" />
				</div>
				<div class="inputDiv">
					<label for="">圈子描述</label><textarea style="height: 100px;" name="description" id="description" cols="30" rows="10"></textarea>
				</div>
				<div class="inputDiv">
					<label for="">标签</label><input type="text" name="label.name" id="label" value="" />
					<select name="joinType" id="joinType"class="group-select">
						<option value="">----申请加入是否需要验证----</option>
						<option value="approve">------------需要审核------------</option>
						<option value="noapprove">------------不需要审核-----------</option>
					</select>
				</div>			
				<div class="buttonGroup">
					<button class="close" >关闭</button><button class="btn-submit" type="submit" onclick="return addGroup()">保存</button>
				</div>
			</form>
		</div>
<script>
	 $(document).ready(function() {  
         $("#file_upload1").uploadify({  
       	 	'formData'      : {'fileType' : "project"},
                'buttonText' : '请选择',  
                'height' : 20,  
                'swf' : '/uploadify//uploadify.swf',  
                'uploader' : '/base/upload',  
                'width' : 100,  
                'auto':true,  
                'fileObjName'   : 'file',  
                'onUploadSuccess' : function(file, data, response) {  
                   data = eval("("+data+")")
                   if(data.status==true){
                   		alert("图片上传成功");
                   		$("#image1").val(data.data);
                   }else{
                   		alert(data.msg);
                   }
                   
                }  
            });  
     }); 
	$(function(){
		focusgroupPage(1);
		createGroup();
	});

	var userid =$("#id").val();
	var row=15;
	function focusgroupPage(obj){
		next=obj+1;
		$.get("/groupPerson/getGroupByuserId",{"userid":userid,"start":obj,"row":row},function(data){
			if(data.status){
				var str3 =$("#focus").html();
				var length="";
				if(data.list.length >= 16){
				   length=data.list.length-1;
				}else{
				 	length=data.list.length;
				}
				for(var i=0;i<length;i++){
					str3+='<li><a href="/group/detail/'+data.list[i].id+'">'+data.list[i].name+'</a></li>';
				}
				var str4="";
				if(data.list.length >= 16){
				   str4='<li style="list-style:none;"><a style="background-color:#c9c9c9;" href="javascript:void(0)" onclick="focusgroupPage('+next+')">更多</a></li>';
				}
				$("#focus").html(str3);
					$("#moreF").html(str4);
				}
		},"json");
	};


	function createGroup(){
		$.get("/groupPerson/getCreateGroupByuserId",{"userid":userid},function(data){
			if(data.status){
				var str1 ="";
				for(var i=0;i<data.data.length;i++){
					str1+='<li><a href="/group/detail/'+data.data[i].id+'">'+data.data[i].name+'</a></li>';
				}
				$("#create").html(str1);
			}
		},"json");
	}

	function addGroup(){
		if($("#name").val()==""){
			alert("请填写圈子名称！");
			return false;
		}
		if($("#image1").val()==""){
			alert("请上传图片！");
			return false;
		}
		if($("#description").val()==""){
			alert("请填写圈子详细！");
			return false;
		}
		if($("#joinType").val()==""){
			alert("请选择审核权限！");
			return false;
		}
		$.ajax({
			url:"/group/addGroupByUser",
			type:"POST",
			data:$("#addGroup").serialize(),
			beforeSend:function(){
				$(".btn-submit").attr("disabled",true);
			},
			success:function(data){
				if(data.status){
					alert(data.msg);
				}else{
					alert("数据错误:"+data.msg);
				}
				window.location.reload();
			},
			complete:function(){
				$(".btn-submit").attr("disabled",false);
			}
		});
}

</script>		
	</body>
	
</html>
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<meta charset="utf-8">
		<title>�ۼ�֮�� ${userdata.user} ����ҳ</title>
		<#if show!=true>
		<meta name="keywords" content="����${userdata.user} ��Щ�����ҵ��ҳ����� ">
		<meta name="description" content="���� ${userdata.user} ��ӭ�����ۼ�֮�� ">
		</#if>
		<link rel="stylesheet" type="text/css" href="/web/css/style.css">
		<link rel="stylesheet" type="text/css" href="/web/css/reveal.css" />
		<link rel="stylesheet" href="/Jcrop-v0.9.12/demos/demo_files/main.css" type="text/css" />
		<link rel="stylesheet" href="/Jcrop-v0.9.12/demos/demo_files/demos.css" type="text/css" />
		<link rel="stylesheet" href="/Jcrop-v0.9.12/css/jquery.Jcrop.css" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/uploadify/uploadify.css">
		<script type="text/javascript" src="/js/jquery.min.js"></script>
		<script type="text/javascript" src="/js/publicMethod.js"></script>
		<script type="text/javascript" src="/web/js/jquery.reveal.js"></script>
		<script type="text/javascript" src="/js/jquery.form.js"></script>
		<script type="text/javascript" src="/uploadify/jquery.uploadify.min.js"></script>
		<script type="text/javascript" src="/Jcrop-v0.9.12/js/jquery.Jcrop.js"></script>

	</head>
<style>
	.last{
	    font-size: 18px;
	    margin: 55px 0 50px;
	    border-bottom: 0px solid #e5e5e5;
	    margin-top:30px;
	}
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

	<script>
	var userid = ${userId}
	
	</script>
	
	<body>
		<div class="my">
			<div class="content relative">
				<nav>
					<ul class="clearfix">
						<li class="fr"><a id="userImg" href="/user/my/post">�ҵ�</a></li>
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
			 
					<div class="title" style="<#if show??><#if show!=true>margin-top:100px;</#if></#if>">��������
					<#if show??><#if show==true><a href="/topic/addTopic" class="createCamp"  ></a></#if></#if>
					</div>
					<div class="my-title"><#if show==true>��</#if><#if show!=true><#if userdata.sex=='boy'>��</#if><#if userdata.sex!='boy'>��</#if></#if>д�Ļ���</div>
					<div class="part2 relative">
						<#if topics??>
							<ul id="list">
								<#list topics as topic>
								<#if topic_index==10 >
									<#break>
								</#if>
									<li>
										<div class="f16 clearfix">
											<#if !topic.user.image??>
											
											<#if  topic.user.sex=='boy'>
												<a href="/user/other/post/${topic.user.id}"><i class="f21 fl" style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											<#if  topic.user.sex!='boy'>
												<a href="/user/other/post/${topic.user.id}"><i class="f21 fl" style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											</#if>
											<#if topic.user.image??>
												<a href="/user/other/post/${topic.user.id}"><i class="f21 fl" style="background: url(${static}${topic.user.image}) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											
											<p class="fl"><a href="/user/other/post/${topic.user.id}"><span class="author c44">${topic.user.user}</span></a>
													<#if topic.group ??>
											&nbsp;&nbsp;&nbsp;����&nbsp;&nbsp;&nbsp;
												<span class="classify c44">
														${topic.group.name!''}
												</span>
													</#if>
												<br />${topic.showData} �� ${topic.hits}���Ķ�</p>
										</div>
										<div class="articleName">���⣺<a href="/post/${topic.id}">${topic.title}</a></div>
										<div class="articleCon"></div>
									</li>
								</#list>
								<#if (topics?size==11)>
									<li class="last"  style="list-style:none;text-align: center;margin-top:40px;line-height: 60px;"><a style="background-color:#c9c9c9;" href="javascript:void(0);" onclick="getMore()">����</a></li>
								</#if>
							</ul>
						</#if>
					</div>
				
	<div id="publishTopic" class="reveal-modal publish">
			<div class="model-title">������
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
		<footer></footer>
	</body>

<script type="text/javascript">
	var page = 2;//Ĭ������ʾ2ҳ
	
	function getMore(){
		$('.last').css('display','none');
		
		$.ajax({
				url:'/user/my/post/page?userid=${userId}',
				type : 'get',
				data:{page:page},
				dataType:'json',
				success:function(data){
					if(data.length > 0){
						page++;
						for(var i=0 ; i < data.length ;i++){
							if(i == 10){
								break;
							}
						
							var obj = data[i];
							
							var groupname = obj.group == null ? '' : obj.group.name
							
							var image = obj.user.image;
							if(image==null||image ==""){
								if(obj.user.sex =='boy'){
									image = ' style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;"'
								}else{
									image = ' style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;"'
								}
							}else{
								image = ' style="background: url(${static}'+obj.user.image+') no-repeat 0 center;background-size: 100%;"'
							}
							var html = '<div class="f16 clearfix"><a href="/user/other/post/'+ obj.user.id +'"><i '+image+' class="f21 fl"></i></a><p class="fl"><a href="/user/other/post/'+obj.user.id+'"><span  class="author c44">'+ obj.user.user+'</span></a>';
							if (groupname!="") {
								html+='&nbsp;&nbsp;&nbsp;����&nbsp;&nbsp;&nbsp;<span class="classify c44">'+ groupname  +'</span>';
							}
							html+='<br />'+obj.showData +' �� '+ obj.hits+'���Ķ�</p></div><div class="articleName">���⣺<a href="/post/'+obj.id+'">'+obj.title+'</a></div><div class="articleCon"></div>'
							var li = $('<li></li>');
							li.html(html)
							$('#list').append(li);
						}
						
						if(data.length == 11){
							var li = $('<li style="list-style:none;text-align: center;margin-top:40px;line-height: 60px;"></li>');
							li.addClass('last');
							li.html('<a style="background-color:#c9c9c9;" href="javascript:void(0);" onclick="getMore()" >����</a>');
							$('#list').append(li);
						}
					}
				},
				error:function(){
					alert('��ѯ����')
				}
			})
	}

</script>
</html>
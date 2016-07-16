
<!DOCTYPE html>
<html>

	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<meta charset="utf-8">
		<title>聚集之美 ${userdata.user} 的主页</title>
		<#if show!=true>
		<meta name="keywords" content="我是${userdata.user} 这些都是我的朋友 ">
		<meta name="description" content="我是 ${userdata.user} 欢迎来到聚集之美 ">
		</#if>
		<link rel="stylesheet" type="text/css" href="/web/css/style.css">
		<link rel="stylesheet" type="text/css" href="/web/css/reveal.css" />
		<link rel="stylesheet" href="/Jcrop-v0.9.12/demos/demo_files/main.css" type="text/css" />
		<link rel="stylesheet" href="/Jcrop-v0.9.12/demos/demo_files/demos.css" type="text/css" />
		<link rel="stylesheet" href="/Jcrop-v0.9.12/css/jquery.Jcrop.css" type="text/css" />
		<link rel="stylesheet" type="text/css" href="/uploadify/uploadify.css">
<!--		<link rel="stylesheet" type="text/css" href="/web/css/tipso.min.css"/>-->
		<script type="text/javascript" src="/web/js/jquery.min.js"></script>
		<script type="text/javascript" src="/web/js/jquery.reveal.js"></script>
		<script type="text/javascript" src="/web/js/tipso.js"></script>
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
		<input type="hidden" id="userid" value="${userid}"/>
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
			
					<div class="title" style="<#if show??><#if show!=true>margin-top:100px;</#if></#if>"><#if show==true>我</#if><#if show!=true><#if userdata.sex=='boy'>他</#if><#if userdata.sex=='女'>她</#if></#if>的朋友</div>
					<div class="part2 relative">
						<ul class="friendList clearfix" >
						<div id="content">
							
						</div>
						<div id="more">
						<div>
						</ul>
					</div>
				</div>
			</div>
		</div>
		
		<script src="js/pop.js" type="text/javascript" charset="utf-8"></script>
		<script>
		    var userid=$("#userid").val();
		    var start=0;
		    var row=9;
		    $(function(){
		         followList(1);
		    });
			
		    function followList(obj){
		       start=row*(obj-1);
		       var next=obj+1;
		       $.get("/userRelationship/getFollowerList",{"positiveUserId":userid,"start":start,"row":(row+1)},function(data){
		             if(data.status){
		                  var length="";
		                  var str2='';
		                  if(data.list.length >=10){
		                     length=data.list.length-1;
		                     str2='<li><div class="f16 clearfix"><a style="background-color:#c9c9c9;" href="javascript:void(0)" onclick="followList('+next+')">更多</a></div></li>';
		                  }else{
		                     length=data.list.length;
		                  }
		                  
		               
		                		
		                		
		                  var str=$("#content").html(); 
		                  for(var i=0;i<length;i++){
		                  	  var image = data.list[i].image;
		                  	  //console.log(image)
							if(image==null||image ==""){
								if(data.list[i].sex =='boy'){
									image = ' style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;"'
								}else{
									image = ' style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;"'
								}
							}else{
								image = ' style="background: url(${static}'+ data.list[i].image+') no-repeat 0 center;background-size: 100%;"'
							}
		                  str+='<li><div class="f16 clearfix">';
						  str+='<a href="/user/other/post/'+ data.list[i].id+'"><i class="logo"    '+image+' ></i></a>';
						  str+='<p class="fl"><a href="/user/other/post/'+ data.list[i].id +'"><span class="author c44">'+data.list[i].user+'</span><span class="classify c99">'+data.list[i].chinaName+'</span></a>';
						  str+='</div></li>'
		                  }
		                  $("#content").html(str); 
		                  $("#more").html(str2);
		             }else{
		              alert(data.msg);
		             }
		          },"json");
		    }
		
		</script>
	</body>

</html>
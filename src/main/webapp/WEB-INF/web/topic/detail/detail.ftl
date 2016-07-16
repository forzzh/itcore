<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
	<meta charset="utf-8">
	<title>${msg.data.title}  </title>
	<meta name="keywords" content="">
	<meta name="description" content="">
	<link rel="stylesheet" type="text/css" href="/web/css/style.css">
	<script type="text/javascript" src="/js/jquery1.9.0.min.js"></script>
	<script type="text/javascript" src="/js/erweimajs/jquery.qrcode.js"></script>
	<script type="text/javascript" src="/js/erweimajs/qrcode.js"></script>
	<script type="text/javascript" src="/js/publicMethod.js"></script>
	
		<meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE">
		<link rel="stylesheet" type="text/css" href="/markdown/public/stylesheets/vendor/font-awesome.css">
		<link rel="stylesheet" type="text/css" href="/markdown/public/stylesheets/vendor/sunburst.css">
				<link rel="stylesheet" type="text/css" href="/markdown/public/stylesheets/vendor/markdown.css">
		<link rel="stylesheet" type="text/css" href="/markdown/public/stylesheets/vendor/font.css">
		<link rel="stylesheet" type="text/css" href="/markdown/public/stylesheets/emoji/nature.css">
<style type="text/css">
    @import "/markdown/public/stylesheets/vendor/font.css";
    @import "/markdown/public/stylesheets/vendor/markdown.css";
    @import "/markdown/public/stylesheets/emoji/nature.css";
    @import "/markdown/public/stylesheets/emoji/object.css";
    @import "/markdown/public/stylesheets/emoji/people.css";
    @import "/markdown/public/stylesheets/emoji/place.css";
    @import "/markdown/public/stylesheets/emoji/Sysmbols.css";
    @import "/markdown/public/stylesheets/emoji/twemoji.css";
    @import "/markdown/public/stylesheets/vendor/font-awesome.css";
    @import "/markdown/public/stylesheets/vendor/sunburst.css";
</style>
<style>		
	select {
	    font-size: 15px;
	    color: #fff;
	    width: 405px;
	    height: 58px;
	    border: 1px solid #b9b9b9;
	    border-radius: 2px;
	    background: #999;
	}
	.title i {
	    display: inline-block;
	    vertical-align: sub;
	    margin-top:15px;
	    margin-right:10px;
	    width: 48px;
	    height: 48px;
	    background: url(/web/images/answered.jpg);
	}
	.buttom-line{
		text-align:center;
		font-family:"微软雅黑";
	}
	.group-select{
		width:200px;
		height:30px;
	}
	.extBtn {
		width: 116px;
  		height: 45px;
  		text-align: center;
  		color: #448aca;
 		border: 2px solid #448aca;
 		border-radius: 20px;
 		padding: 5px;
 		cursor: pointer;
	}
</style>
		<script type="text/javascript">
		$(function(){
		jQuery('#qrcodeTable').qrcode({
			render	: "table",
			text	: window.location.href+"/wap",
			width	:100,
			height	:100
		});	
		})
		
		var answerid = '${answerid!''}';
		
		var replyID = null;
		var userId  = null;
		var currentPage = 1;
		var count = 11;//设置查询个数
		var flag = true;
		$(document).ready(function(){
			//帖子ID
			replyID = $("#replyID").val();
			userId  = $("#userId").val();
			getReplyList(replyID,currentPage,count);
			getGroupByUserId(userId);

			var loginUser = '${userId!""}';
			var topicUser = '${msg.data.user.id!""}';
			if (loginUser != topicUser) {
				$(".extBtn").hide();
			}
		});
		//提交
		function submit() {
			var data = {};
			data.content = $("#replyContent").val();
			if($.publicMethod.getStringLength(data.content) == 0 || $.publicMethod.getStringLength(data.content)>300){
				alert("内容为空或超过字数限制！");
				return false;
			}
			data.topicid = replyID;
			data.userID  ='${userId!""}';
			data.groupid = $("#groupID").val();
			data.categroyEnum;//帖子分类
			data.categroyEnum = "topic";
			
			if (!flag) {
				return;
			}
			flag = false;
			
			//提交
			$.ajax({
			   type: "GET",
			   url: "/topic/reply",
			   data:data,
			   success:function(msg){
			   		if(msg.status == true){
			   			alert("回复成功！~");
			   			//刷新回复列表
			   			$(".answerList").show().empty();
			   			//刷新表单
			   			$("#replyContent").val("");
			   			$("#groupID").val("");
			   			currentPage = 1;
			   			getReplyList(replyID,1,count);
			   		}else{
			   			if(msg.msg=="用户未登录"){
			   				alert("用户未登录");
			   				$.publicMethod.redirectToLogin();	
			   			}else{
			   				alert("回复失败！~");
			   			}
			   		}
			   		flag = true;
				}
			});
		};
		//根据用户id获得group列表
		function getGroupByUserId(userID){
			if($.publicMethod.hasValue(userID)){
				$.ajax({
				   type: "GET",
				   url: "/manager/groupPerson/getGroupByUserId",
				   data:{ 
				   		userId:userID
				   },
				   success:function(msg){
				   		if($.publicMethod.hasValue(msg.data)){
				   			//group插入到下拉列表
				   			//<option value ="volvo">Volvo</option>
				   			for (var i = 0; i < msg.data.length; i++) {
				   				$(".group-select").append("<option value='"+msg.data[i].group.id+"''>"+msg.data[i].group.name+"</option>");
				   			};
				   		}else{
				   			$(".group-select").hide();
				   		}
					}
				});
			}else{
				$(".group-select").hide();
			}
		}
		
		
		function setAnswer(replyid){
			if(confirm("您确定把该回复设为最佳答案吗？操作后无法修改！")){
				$.ajax({
					url:'/topic/addAnser',
					type:'post',
					data:{
						topicId : '${id}',
						replyId : replyid
					},
					dataType:'json',
					success:function(msg){
						if(msg.status){
							window.location.reload();
						}else{
							alert(msg.msg);
						}
					},
					error:function(){
						alert('操作失败！');
					}
				});
			}
		}
		
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
			   		replyEnum:'TopicReply',
			   		answerid:answerid
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
			       					replyJQ.push("<li>");
			       					<#if userId??>
			       						<#if userId == msg.data.user.id>
			       							<#if msg.data.categroyName == 'question'>
				       							<#if answer??>
				       							<#else>
				       							  replyJQ.push("<img width='40' height='40' src='/web/images/five-star-none.jpg' style='position:relative;top:90px;left:-50px;' onclick='setAnswer("+ reply.id +")'>");
				       							</#if>
			       							</#if>
			       						</#if>
			       					</#if>
			       					replyJQ.push("<div class='f16 clearfix relative'>");
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
			       					replyJQ.push('<a href="/user/other/post/'+ reply.user.id +'"><i class="logo" '+image+'></i></a>'); 
			       					replyJQ.push("<p class='fl'>");
			       					replyJQ.push("<a href='/user/other/post/"+reply.user.id+"'><span class='author c44'>"+reply.user.user+"</span></a>");
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
        //添加好友
        function addFriend(topicUserId,userId){
        	if(!$.publicMethod.hasValue(userId)){
        		//没有用户id，重定向
        		alert("用户未登录");
        		$.publicMethod.redirectToLogin();	
        	}else{
        		$.ajax({
     			   type: "POST",
     			   url: "/userRelationship/addFriend",
     			   data:{
     				  passiveUserId:topicUserId,
     				  positiveUserId:userId
     			   },
     			   success:function(msg){
     				   if(msg.status){
     					   alert("添加好友成功！")
     				   }else{
     					   if(msg.msg=="用户未登录"){
     						  alert("用户未登录");
     						  $.publicMethod.redirectToLogin();	
     					   }else{
     						   alert(msg.msg);
     					   }
     				   }
     			   }
     			});
        	}
        }

        function deleteTopic() {
        	if (window.confirm("确定要删除吗？")) {
	        	$.get("/topic/deleteTopic", {topicId:"${msg.data.id}"}, function(result){
	        		alert(result.msg);
					if (result.status) {
						window.location.href = "/post/all";
					}
	        	})
        	}
        }

        function updateTopic() {
			window.location.href = "/topic/modifyTopic?topicId=${msg.data.id}";
        }
	</script>
</head>

	<body>
		<input type="hidden" value="${id}" id="replyID"/>
		<input type="hidden" value="${userId!""}" id="userId"/>
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
						<span class="extBtn" onclick="deleteTopic()">删除</span>
						<span class="extBtn" onclick="updateTopic()">修改</span>
						<ul class="clearfix" style="padding-left:0px;">
							<li style="list-style: none;"><a href="/post/all/${msg.data.label.name}"><span class="c22">${msg.data.label.name}</span></a></li>
							<#if extLabels??>
							<#list  extLabels as label> 
								<li style="list-style: none;"><a href="/post/all/${label}">${label}</a></li>
							</#list>
							</#if>
						</ul>
						<div class="f16 clearfix relative">
								<#if !msg.data.user.image??>
											<#if  msg.data.user.sex=='boy'>
												<a href="/user/other/post/${msg.data.user.id}"><i class="f21 fl" style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;    border-radius: 50%;" ></i>
												</a>
											</#if>
											<#if  msg.data.user.sex!='boy'>
												<a href="/user/other/post/${msg.data.user.id}"><i class="f21 fl" style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;    border-radius: 50%;" ></i>
												</a>
											</#if>
											</#if>
											<#if msg.data.user.image??>
												<a href="/user/other/post/${msg.data.user.id}"><i class="f21 fl" style="background: url(${static!''}${msg.data.user.image}) no-repeat 0 center;background-size: 100%;    border-radius: 50%;" ></i>
												</a>
											</#if>		
									<p class="fl"><a href="/user/other/post/${msg.data.user.id}"><span class="author c44">${msg.data.user.user}</span></a>
									<#if msg.data.group??>
										&nbsp;&nbsp;&nbsp;来自&nbsp;&nbsp;&nbsp;
										<a href="/group/detail/${msg.data.group.id}"><span class="classify c44">
										${msg.data.group.name}</span></a>
									</#if><br />
									${msg.data.showData!""} · ${msg.data.hits!0}人阅读</p>
									<div class="blueBtn" onclick="addFriend('${msg.data.user.id}','${userId!''}')"><a href="#" class="f16">加为好友</a></div>
								</div>
	<!-- JiaThis Button BEGIN -->
<div class="jiathis_style_32x32">
<a class="jiathis_button_weixin"></a>
<a class="jiathis_button_cqq"></a>
<a class="jiathis_button_tsina"></a>
<a class="jiathis_button_renren"></a>

<a class="jiathis_button_linkedin"></a>

<a class="jiathis_button_kaixin001"></a>
<a class="jiathis_button_baidu"></a>
<a class="jiathis_button_hi"></a>
<a class="jiathis_button_txinhua"></a>
<a class="jiathis_button_tieba"></a>
<a class="jiathis_counter_style"></a>
</div>
<script type="text/javascript" >
var jiathis_config={
	summary:"",
	shortUrl:false,
	hideMore:true
}
</script>
<script type="text/javascript" src="http://v3.jiathis.com/code/jia.js" charset="utf-8"></script>
<!-- JiaThis Button END -->
						<#if !topicContent.type??>
							${topicContent.content}
						</#if>
						
						<#if topicContent.type??>
								<div style="margin-top:40px;" class="md-editor">
								<div class="md-full-preview" style="    display: block;">
								<div class="md-full-preview-body" style="margin-right: 25px; margin-left: -25px;"> 
								<div class="md-full-preview-inner" style="margin-right: -25px; ">
					${topicContent.markdown}
							
								</div>
							</div>
						</div>
					</div>
						</#if>
						<#if msg.data.answerState??>
						
							<#if msg.data.answerState=='NotAnser'>
								<i style=" background: url(/web/images/notAnswer.jpg);"></i>
							</#if>
							<#if msg.data.answerState=='HaveAnser'>
								<i style=" background: url(/web/images/answered.jpg);"></i>
							</#if>
						</#if>
						
						</div>
						
					
						
						<div class="share abs">							
								<div id="qrcodeTable" width="100px"></div>
								
							<div>
							</div>
						</div>
						
						
					</div>
					<style>
					#qrcodeTable{
						width:100px;
					}
					.articleCon img{
					max-width:505px;
					}
					</style>
			
					
					<div class="part2" style="background:white;margin-top:210px;">
						<ul style="padding-left: 0px;">					
							<li style="border-bottom:0px;">
								
								<div class="articleName" style="margin-bottom:10px;">欢迎拍砖</div>	
								
									<textarea id="replyContent"name="" rows="" cols="" placeholder="写下你想说的..."></textarea>
									<div class="f16 clearfix relative">
										<select id="groupID"class="group-select" style="display:none">
											<option value="">------请选择分组--------</option>
										</select>
										<div class="blueBtn" onclick="submit()" style="float:right;margin:30px 0;"><a href="javascript:void;" class="f16">提交</a></div>
									</div>
								
							</li>						
						</ul>
						<ul class="answerList" style="margin-top:40px;">
							<#if answer??>
								<li>
									<img width='40' height='40' src='/web/images/five-star-yellow.jpg' style='position:relative;top:90px;left:-50px;'>
									<div class='f16 clearfix relative'>
									<#if answer.user.image??>
										<a href="/user/other/post/${answer.user.id}"><i class="logo" style="background: url(${static}${answer.user.image}) no-repeat 0 center;background-size: 100%;"></i></a>
									<#else>
										<#if answer.user.sex == 'boy'>
											<a href="/user/other/post/${answer.user.id}"><i class="logo" style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;"></i></a>
										<#else>
											<a href="/user/other/post/${answer.user.id}"><i class="logo" style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;"></i></a>
										</#if>
									</#if>
			       					<p class='fl'>
			       					<a href='/user/other/post/${answer.user.id}'><span class='author c44'>${answer.user.user}</span></a>
			       					<#if answer.group??>
			       						&nbsp;&nbsp;&nbsp;来自&nbsp;&nbsp;<span class='classify c44'><a href="/group/detail/${answer.group.id}">${answer.group.name}</a></span>
			       					</#if>
			       					<br/>
			       					${answer.showData}</p>
			       					<p class='fl'>${answer.content};</p>
			       					</div>
			       				</li>
							</#if>
						</ul>
					</div>
				</div>				
			</div>
		</div>
	</body>

</html>
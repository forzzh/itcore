<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=GBK">
	<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
	<meta charset="utf-8">
	<title></title>
	<meta name="keywords" content="">
	<meta name="description" content="">
	<link rel="stylesheet" type="text/css" href="/web/css/style.css">
	<script type="text/javascript" src="/js/jquery.min.js"></script>
	<script type="text/javascript" src="/js/erweimajs/jquery.qrcode.js"></script>
	<script type="text/javascript" src="/js/erweimajs/qrcode.js"></script>
	<script type="text/javascript" src="/js/publicMethod.js"></script>
	
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
<script type="text/javascript" charset="utf-8" src="/markdown/public/javascripts/vendor/underscore/underscore-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/markdown/public/javascripts/vendor/raphael/raphael.min.js"></script>
<!-- <script type="text/javascript" charset="utf-8" src="/markdown/public/javascripts/vendor/jquery/jquery-2.1.3.js"></script> -->
<script type="text/javascript" charset="utf-8" src="/markdown/public/javascripts/vendor/sequence-diagram/sequence-diagram-min.js"></script>
<script type="text/javascript" charset="utf-8" src="/markdown/public/javascripts/vendor/flowchart/flowchart.js"></script>
<script type="text/javascript" charset="utf-8" src="/markdown/public/javascripts/vendor/flowchart/jquery.flowchart.min.js"></script>
<script type="text/javascript" charset="utf-8" src="/markdown/public/javascripts/vendor/highlight/highlight.js"></script>
<script type="text/javascript" charset="utf-8" src="/markdown/public/javascripts/vendor/markdown/he.js"></script>
<script type="text/javascript" charset="utf-8" src="/markdown/public/javascripts/vendor/markdown/marked.js"></script>
<script type="text/javascript" charset="utf-8" src="/markdown/public/javascripts/vendor/markdown/to-markdown.js"></script>
<script type="text/javascript" charset="utf-8" src="/markdown/public/javascripts/vendor/markdown/jsHtmlToText.js"></script>
<script type="text/javascript" charset="utf-8" src="/markdown/public/javascripts/vendor/markdown/tab.js"></script>
<script type="text/javascript" charset="utf-8" src="/markdown/public/javascripts/vendor/markdown/config.js"></script>
<script type="text/javascript" charset="utf-8" src="/markdown/public/javascripts/vendor/markdown/emoji.js"></script>
<script type="text/javascript" charset="utf-8" src="/markdown/public/javascripts/vendor/markdown/bootstrap-markdown.js"></script>
<script type="text/javascript" charset="utf-8" src="/markdown/public/javascripts/vendor/markdown/locale/bootstrap-markdown.zh.js"></script>
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
	    width: 60px;
	    height: 24px;
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
	.part1 ul li{
		float:none;
	}
</style>
		<script type="text/javascript">
		$(document).ready(function(){
			var topicId = $("#topicId").val();
		
		});
		//提交
		//发布帖子
		function submitForm(){	
			var json = {};
			if(document.getElementById("content").value==""){
				alert("写点东西吧?");
				return false;
			}
			json['markdown'] = marked(document.getElementById("content").value)
			json['content'] = $("#content").val();
			if($("#title").val()==""){
				alert("标题不能为空");
				return false;
			}
			
			
			json['title']=$("#title").val();
			json['id']=$("#topicId").val();
			json['label.name'] =$("#mainname").val();
			json['extLabels'] = $("#extLabels").val();
			json['categroyName'] = $("#categroyName").val();
			console.log(json['content']);	 
				//提交
			$.ajax({
			     type: 'POST',
			     url: "/topic/update",
			     data: json ,
			     dataType: "json",
			     success: function(data){
			    	if(data.status){
			    		alert("修改成功");
			    		//自动关闭
			    		$("#topicform")[0].reset();
			    		$("#answerTopic").trigger('reveal:close');
			    		window.location.href ='/user/my/post';
			    	}else{
			    		if(data.msg == "创建者为空"){
			    			alert("用户未登录！~");
			    			window.location.href = "/user/login?redirect=" + window.location.href;
			    		}else{
			    			alert("提交错误："+data.msg);
			    		}
			    		
			    	}
			    } 
			});
		}
	
        
	</script>
</head>

	<body>
		<input type="hidden" value="${userId!""}" id="userId"/>
		<input type="hidden" value="${(topic.id)!''}" id="topicId" name="id"/>
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
						<div id="answerTopic" style="height:1245px;" class="reveal-modal answer">
			<div class="model-title" >修改帖子</div>
			<form id="topicform">
				<div class="inputDiv">
					<label for="">标题</label><input style="width: 830px;" type="text" name="title" id="title" value="${(topic.title)!''}" />
				</div>
				<style>
				#file_upload-button{
				padding-bottom: 0px;     margin-left: 100px;}
				#file_upload{   margin-left: 100px;
    margin-top: -20px;}
    
#SWFUpload_0 {
    left: 150px;
    margin-left: 80px;
}
				 
				</style>
				
				<style>
				.ke-container-default{
				margin-left: 128px;}
				
				.reveal-modal form .inputDiv{
				margin: 0 0; 
				}
				
				.model-title {
     padding-bottom:0px; 
    border-bottom: 0px solid #eee;
}
	.btnsubimt{
	    color: #fff;
	    background: #6ebd3f;
	    border: 1px solid #6ebd3f;
	}
				</style>
				<div class="inputDiv"  style="padding-bottom:0px;border-bottom: 0px solid #eee;">
					<label for="">内容</label><textarea class="tab-content" id="content" name="content" cols="100" rows="8" style="width:830px;height:400px;">${content!''}</textarea>
				</div>
				
				<div class="buttonGroup">
					<button type="button" class="btnsubimt" onclick="submitForm()">发布</button>
				</div>			
			</form>	
		</div>	
					</div>
				</div>
				<script>
						var edit =  $("#content").markdown({
        language: 'zh',
     fullscreen: {
        enable: true
    },
        localStorage: 'md',
       imgurl: '/c/imgUpload',
        base64url: '/c/imgUpload',
        flowChart : true
    });
				</script>
					<style>
					#qrcodeTable{
						width:100px;
					}
					.articleCon img{
					max-width:505px;
					}
					</style>
						
			</div>
		</div>
	</body>

</html>
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
	<script type="text/javascript" src="/js/jquery1.9.0.min.js"></script>
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
<script type="text/javascript" src="/js/publicMethod.js"></script>
<style>		
	select {
	    font-size: 15px;
	    color: black;
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
		var flag = true;
		$(document).ready(function(){
		});
		//提交
		//发布帖子
		function submitForm(){	
			var json = {};
			if(document.getElementById("content").value==""){
				alert("写点东西吧?");
				return false;
			}
			json['content'] = document.getElementById("content").value;
			json['markdown'] = marked(document.getElementById("content").value)
			
			if($("#title").val()==""){
				alert("标题不能为空");
				return false;
			}
			json['title']=$("#title").val();
			if($("#mainname").val()==""){
				alert("主标签不能为空");
				return false;
			}
			json['label.name'] =$("#mainname").val();
			json['extLabels'] = $("#extLabels").val();
			json['categroyName'] = $("#categroyName").val();
			json['group.id'] = $("#groupId").val();
			console.log(json['content']);	 
				//提交
				if (!flag) {
					return;
				}
				flag = false;
			$.ajax({
			     type: 'POST',
			     url: "/topic/add",
			     data: json ,
			     dataType: "json",
			     success: function(data){
			    	if(data.status){
			    		alert("添加成功");
			    		//自动关闭
			    		$("#topicform")[0].reset();
			    		$("#answerTopic").trigger('reveal:close');
			    		window.location.href ='/user/my/post';
			    	}else{
			    		if(data.msg == "创建者为空"){
			    			alert("用户未登录！~");
			    			window.location.href = "/user/login?redirect=" + window.location.href;
			    		}else{
			    			flag = true;
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
		<div class="center">
			<div class="content clearfix">
				<div class="quesDetail">
					<div class="part1 relative">
						<div id="answerTopic" style="height:1245px;" class="reveal-modal answer">
			<div class="model-title" >发表帖子</div>
			<form id="topicform">
				<div class="inputDiv">
					<label for="">帖子类型</label>
					<select name="categroyName" id="categroyName">
						<#if categroyEnums??>
							<#list categroyEnums as categroyEnum>
								<option value="${categroyEnum}">${categroyEnum.show()}</option>
							</#list>
						</#if>
					</select>
				</div>
				<div class="inputDiv" style="padding-bottom:0px;border-bottom: 0px solid #eee;" >
					<label for="">主标签</label><input style="width: 830px;" type="text" name="label.name" id="mainname" value="" />
					<#if newIndexLabels??>
						<#if (newIndexLabels?size > 0)>
							<ul class="clearfix" style="padding-left:0px;margin-bottom:0px;" id="randomLabels">
								<#list newIndexLabels as label>
									<li  style="list-style: none;float: left;border-right: 3px solid #666;"><a href="javascript:void(0)" onclick="$('#mainname').val($(this).text());">${label.name!''}</a></li>
								</#list>
								<li  style="list-style: none;float: left;"><a   href="javascript:void(0);" onclick="getRandomLabelList(10)"><span class="c22">更换一批标签</span></a></li>
							</ul>
						</#if>
					</#if>
				</div>
				<div class="inputDiv">
					<label for="">标题</label><input style="width: 830px;" type="text" name="title" id="title" value="" />
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
			<#if userGroup?? && (userGroup?size > 0)>
				
				<div class="inputDiv">
					<label for="">圈子营地</label>
					<select name="group.id" id="groupId">
						<option value="">请选择</option>
						<#list userGroup as group>
							<option value="${group.id}">${group.name}</option>
						</#list>
						
					</select>
				</div>
				
			</#if>
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
					<label for="">内容</label><textarea class="tab-content" id="content" name="content" cols="100" rows="8" style="width:830px;height:400px;"></textarea>
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
    
    
document.getElementById("mainname").onkeypress = function(e){
    checkChar(e);
}
 
function checkChar(e){
    e = window.event || e;
    var code = e.keyCode || e.which;
    var reg = new RegExp("[<>~'!@#$%^&*()-+_=:]");
    if(reg.test(String.fromCharCode(code))){
        if(window.event){
            e.returnValue = false;
        }else{
            e.preventDefault();
        }
    }
}
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
<script type="text/javascript">
	
		//获取随机标签
	function getRandomLabelList(num){
		if(!$.publicMethod.hasValue(num)){
			num = 10;
		}
		$.ajax({
		   type: "GET",
		   url: "/label/randomLabelList",
		   data:{
			   num:num
		   },
		   dataType : 'json',
		   success: function(msg){
		       if (msg.status) {
			       if($.publicMethod.hasValue(msg.list)){
				       var labelListJQ = [];

				       for(var i=0 ;i<msg.list.length;i++){
				    		labelListJQ.push('<li style="list-style: none;float:left;border-right: 3px solid #666;"><a href="javascript:void(0)" onclick="$(\'#mainname\').val($(this).text());">'+ msg.list[i].name +'</a></li>');
				       }
				       labelListJQ.push('<li   style="list-style: none;"><a href="javascript:getRandomLabelList(\'\',\'\');"><span class="c22">换一批标签</span></a></li>');
				       $("#randomLabels").empty().append(labelListJQ.join(""));
			       }
		       }else{
		           alert("获取列表出错!"+ msg.msg);
		       }
		   }
		});
	}
</script>		
		
	</body>

</html>
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
	<script type="text/javascript" src="/uploadify/jquery.uploadify.min.js"></script>
	<link rel="stylesheet" type="text/css" href="/uploadify/uploadify.css"> 
	
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
	.uploadify-queue{
	display:none;}
	#file_upload{
		   		margin-top: 80px;
		   	}
</style>
		<script type="text/javascript">
        
	</script>
</head>

	<body>
		
		
		<div class="detail">
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
				<div class="quesDetail">
					<div class="part1 relative">
						<div id="answerTopic" style="height:1245px;" class="reveal-modal answer">
			<form id="addGroup">
				<input  value=""  type="hidden"  name="image" id="image1">
				<input type="hidden" name="user.id" value="${userId!""}" id="userId"/>
				<div class="inputDiv">
					<label for="">圈子名称</label><input style="width: 830px;" type="text" name="name" id="name" value="" />
				</div>
				<div class="inputDiv">
					<label for="">图片上传</label><input type="file" name="file_upload1" id="file_upload" value="" />
				</div>
				<div class="inputDiv">
					<label for="">圈子描述</label><textarea style="height: 100px;" name="description" id="description" cols="30" rows="10"></textarea>
				</div>
				<div class="inputDiv">
					<label for="">标签</label><input style="width: 830px;" type="text" name="label.name" id="label" value="" />
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
				<div class="inputDiv">
					<select name="joinType" style="margin:10px;" id="joinType" class="group-select">
						<option value="">----申请加入是否需要验证----</option>
						<option value="approve">------------需要审核------------</option>
						<option value="noapprove">------------不需要审核-----------</option>
					</select>
				</div>		
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
				
				<div class="buttonGroup">
					<button type="button" class="btnsubimt" onclick="addGroup()">提交 </button>
				</div>			
			</form>	
		</div>	
					</div>
				</div>
				<script>
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

$(document).ready(function() {  
         $("#file_upload").uploadify({  
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
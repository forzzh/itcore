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
		font-family:"΢���ź�";
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
				<div class="quesDetail">
					<div class="part1 relative">
						<div id="answerTopic" style="height:1245px;" class="reveal-modal answer">
			<form id="addGroup">
				<input  value=""  type="hidden"  name="image" id="image1">
				<input type="hidden" name="user.id" value="${userId!""}" id="userId"/>
				<div class="inputDiv">
					<label for="">Ȧ������</label><input style="width: 830px;" type="text" name="name" id="name" value="" />
				</div>
				<div class="inputDiv">
					<label for="">ͼƬ�ϴ�</label><input type="file" name="file_upload1" id="file_upload" value="" />
				</div>
				<div class="inputDiv">
					<label for="">Ȧ������</label><textarea style="height: 100px;" name="description" id="description" cols="30" rows="10"></textarea>
				</div>
				<div class="inputDiv">
					<label for="">��ǩ</label><input style="width: 830px;" type="text" name="label.name" id="label" value="" />
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
						<option value="">----��������Ƿ���Ҫ��֤----</option>
						<option value="approve">------------��Ҫ���------------</option>
						<option value="noapprove">------------����Ҫ���-----------</option>
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
					<button type="button" class="btnsubimt" onclick="addGroup()">�ύ </button>
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
                'buttonText' : '��ѡ��',  
                'height' : 20,  
                'swf' : '/uploadify//uploadify.swf',  
                'uploader' : '/base/upload',  
                'width' : 100,  
                'auto':true,  
                'fileObjName'   : 'file',  
                'onUploadSuccess' : function(file, data, response) {  
                   data = eval("("+data+")")
                   if(data.status==true){
                   		alert("ͼƬ�ϴ��ɹ�");
                   		$("#image1").val(data.data);
                   }else{
                   		alert(data.msg);
                   }
                   
                }  
            });  
     }); 

function addGroup(){
		if($("#name").val()==""){
			alert("����дȦ�����ƣ�");
			return false;
		}
		if($("#image1").val()==""){
			alert("���ϴ�ͼƬ��");
			return false;
		}
		if($("#description").val()==""){
			alert("����дȦ����ϸ��");
			return false;
		}
		if($("#joinType").val()==""){
			alert("��ѡ�����Ȩ�ޣ�");
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
					alert("���ݴ���:"+data.msg);
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
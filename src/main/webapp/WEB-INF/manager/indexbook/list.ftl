

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1" />
<title>聚集之美管理系统</title>
	<link rel="shortcut icon"href="/images/icon.png"> 
<link rel="stylesheet" href="/manager/css/bootstrap.css" />
<link rel="stylesheet" href="/manager/css/css.css" />
<script type="text/javascript" src="/js/jquery1.9.0.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/sdmenu.js"></script>
<script type="text/javascript" src="/js/laydate/laydate.js"></script>
<link rel="stylesheet" type="text/css" href="/uploadify/uploadify.css">  
<script type="text/javascript" src="/uploadify/jquery.min.js"></script>  
<script type="text/javascript" src="/js/jquery.form.js"></script>
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<script type="text/javascript" src="/uploadify/jquery.uploadify.min.js"></script>
<script charset="utf-8" src="/kindeditor/kindeditor-min.js"></script>
<script charset="utf-8" src="/kindeditor/lang/zh_CN.js"></script>    
</head>

<body>

<#include "../header.ftl"> 
            
<div id="middle">
     <div class="left">

     
     
     
     <script type="text/javascript">
     if($.browser.msie && $.browser.version<11){
 		alert("ie11以下浏览器不支持，请使用其它浏览器");
 		window.close();
 	}
var myMenu;
window.onload = function() {
	myMenu = new SDMenu("my_menu");
	myMenu.init();
};


</script>

<#include "../menu.ftl"> 

     <div class="Switch"></div>
     <script type="text/javascript">
	$(document).ready(function(e) {
    $(".Switch").click(function(){
	$(".left").toggle();
	 
		});
});
</script>

     <div  class="right"  id="mainFrame">
     
     <div class="right_cont">
      <table class="table table-bordered">
      <#list entity as list>  
 		<tr>
         	<td  width="10%" align="right" bgcolor="#f1f1f1">图书名称:</td>
         	<td  width="28%">${list.book.name} </td>
         	<td  width="10%" align="right" bgcolor="#f1f1f1">次序:</td>
         	<td  width="28%">${list.sort} </td>
         </tr>
	  </#list>
        
         
 </table>
 
    </div>
    </div>
<!-- 底部 -->
<div id="footer">版权所有：晶科客流 &copy; 2015&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://www.mycodes.net/" target="_blank">源码之家</a></div>
    
    
	<script type="text/javascript">
	var editor1 = null;
	function inputBlurUserId(){
	if($("#userId").val()==""){
	return 
	}
		$.ajax({

		     type: 'POST',
		
		     url: "/manager/groupPerson/getGroupByUserId" ,
			data:{"userId":$("#userId").val()},
		
		    success: function(data){
		    	
		    	if(data.status){
		    		for(var i = 0;i<data.data.length;i++){
		    			var entity = data.data[i];
		    			$("#group").append("<option value="+entity.group.id+">"+entity.group.name+"</option>")
		    		}
		    	}
		    } ,
		
		    dataType: "json"
		
		});
	}
	
	function submitForm(){
	editor1.sync();
		
		console.log($("#form1").serializeArray())
		$.ajax({

		     type: 'POST',
		
		     url: "/manager/topic/addTopic?"+ $("#form1").serialize() ,
		
		
		    success: function(data){
		    	if(data.status){
		    		alert("添加成功")
		    	}else{
		    		alert(data.msg)
		    	}
		    } ,
		
		    dataType: "json"
		
		});
	}
	//alert(455)
	function look(id){
	console.log($("#"+id).val())
		window.open ($("#"+id).val(),'newwindow','height=600,width=800,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') 
	}
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
                    	alert("图片上传成功")
                    	$("#image").val(data.data);
                    }else{
                    	alert(data.msg);
                    }
                    
                 }  
             });  
     }); 
     
     	KindEditor.ready(function(K) {
			editor1 = K.create('textarea[id="content"]', {
				uploadJson : '/base/uploadKinditor',
				fileManagerJson : '../jsp/file_manager_json.jsp',
				allowFileManager : true,
				afterCreate : function() {
					var self = this;
					K.ctrl(document, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
					K.ctrl(self.edit.doc, 13, function() {
						self.sync();
						document.forms['example'].submit();
					});
				}
			});
		});
	</script>
 <script>
!function(){
laydate.skin('molv');
laydate({elem: '#Calendar'});
}();
 
</script>

 
</body>
</html>



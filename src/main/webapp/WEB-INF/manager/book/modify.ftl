

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
<title>�ۼ�֮������ϵͳ</title>
	<link rel="shortcut icon"href="/images/icon.png"> 
<link rel="stylesheet" href="/manager/css/bootstrap.css" />
<link rel="stylesheet" href="/manager/css/css.css" />
<script type="text/javascript" src="/js/jquery1.9.0.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/sdmenu.js"></script>
<script type="text/javascript" src="/js/laydate/laydate.js"></script>
<link rel="stylesheet" type="text/css" href="/uploadify/uploadify.css">  
<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
<script type="text/javascript" src="/uploadify/jquery.min.js"></script>  
<script type="text/javascript" src="/js/jquery.form.js"></script>
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
 		alert("ie11�����������֧�֣���ʹ�����������");
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
     <table>
     <form id="form1">
       <tr>
         	<td  width="10%" align="right" bgcolor="#f1f1f1">����:</td>
            <td  width="28%"><input type="text" name="name" value="${book.name}" id="input18"  class="span1-1" /> </td>
            
            <td  width="10%" align="right" bgcolor="#f1f1f1">����:</td>
            <td  width="28%"><input type="text" name="label.id" id="input18" value="${book.label.id}" class="span1-1" /> </td>
            
            <td  width="10%" align="right" bgcolor="#f1f1f1">�۸�:</td>
            <td  width="28%"><input type="text" name="price" id="input18" value="${book.price}" class="span1-1" /> </td>
         </tr>
         <tr>
            <td  width="10%" align="right" bgcolor="#f1f1f1">����:</td>
            <td  width="28%"><input type="text" name="author" id="input18" value="${book.author}" class="span1-1" /> </td>
            
            <td  width="10%" align="right" bgcolor="#f1f1f1">����:</td>
            <td  width="28%"><input type="text" name="description" id="input18" value="${book.description}" class="span1-1" /> </td>
             <td  width="28%"><input type="hidden" name="id" id="input18" value="${book.id}" class="span1-1" /> </td>
         </tr>
         <tr>
            <td  width="10%" align="right" bgcolor="#f1f1f1">����:</td>
            <td  colspan="5">
	 	    <textarea id="content" name="content" cols="100" value="dffdf" rows="8" style="width:700px;height:700px;visibility:hidden;">${book.content} </textarea>
	        </td>
         </tr>
    
    
	  
		 
	</tr>
	
	 <tr>
	   <td width="10%" align="right" bgcolor="#f1f1f1">ͼƬ�ϴ���</td>
	 	 <td  colspan="5"> <a href="javascript:look('image')">Ԥ��</a> <input id="file_upload" name="file_upload" type="file" multiple="true"> 
		 
		
		 
		 <input type="hidden" id="image" value ="" name="image">
		
	</tr>
	<tr>
		<td  colspan="6">
			<input onclick="submitForm()" type="button" value="�ύ" class="btn btn-info" style="width:80px;"/>
		</td>
	</tr>
	</form>
 </table>
		
    </div>
    </div>
<!-- �ײ� -->
<div id="footer">��Ȩ���У����ƿ��� &copy; 2015&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://www.mycodes.net/" target="_blank">Դ��֮��</a></div>
    
    
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
                    	alert("ͼƬ�ϴ��ɹ�")
                    	$("#image").val(data.data);
                    }else{
                    	alert(data.msg);
                    }
                    
                 }  
             });  
     }); 
     function look(id){
	console.log($("#"+id).val())
		window.open ($("#"+id).val(),'newwindow','height=600,width=800,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') 
	}
	function submitForm(){
	editor1.sync();
		$.ajax({

		     type: 'POST',
		
		     url: "/manager/book/modifyBook?"+ $("#form1").serialize() ,
		
		    success: function(data){
		    	if(data.status){
		    		alert("�޸ĳɹ�")
		    	}else{
		    		alert(data.msg)
		    	}
		    } ,
		
		    dataType: "json"
		
		});
	}
	

     
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



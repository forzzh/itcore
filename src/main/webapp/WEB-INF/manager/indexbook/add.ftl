

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1" />
<title>�ۼ�֮������ϵͳ</title>
	<link rel="shortcut icon"href="/images/icon.png"> 
<link rel="stylesheet" href="/manager/css/bootstrap.css" />
<link rel="stylesheet" href="/manager/css/css.css" />
<script type="text/javascript" src="/js/jquery1.9.0.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/sdmenu.js"></script>
<script type="text/javascript" src="/js/laydate/laydate.js"></script>
 

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

     <div class="right"  id="mainFrame">
     
     <div class="right_cont">
<form id="myform">
 <table class="table table-bordered">
         <tr>
         	<td  width="10%" align="right" bgcolor="#f1f1f1">ͼ��id:</td>
         	<td  width="28%"><input type="text" name="bookId" id="lname" id="input18" class="span1-1" /> </td>
         	<td  width="10%" align="right" bgcolor="#f1f1f1">����:</td>
         	<td  width="28%"><input type="text" name="range"  id="input18"  class="span1-1" /> </td>
         </tr>
 </table>
 <div style="margin-left:75%;">
		  <input type="button" value="�ύ" onclick="setBookRange()"/>
		  <input type="reset" value="����"/>
</div>
</form>
    </div>
    </div>
<!-- �ײ� -->
<div id="footer">��Ȩ���У����ƿ��� &copy; 2015&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://www.mycodes.net/" target="_blank">Դ��֮��</a></div>
    
 <script>
!function(){
laydate.skin('molv');
laydate({elem: '#Calendar'});
}();
</script>

<script type="text/javascript">
function setBookRange(){
var params = $("#myform").serialize();

 $.ajax({url : "/manager/indexBook/setBookRange",
         type : "get",
         contentType: "application/json",
         dataType : "json",
         data : params,
         success : function(result) {
              alert(result.msg);                          
     }});
}
</script>
</body>
</html>



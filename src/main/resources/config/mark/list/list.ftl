

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>聚集之美管理系统</title>
	<link rel="shortcut icon"href="/images/icon.png"> 
<link rel="stylesheet" href="/manager/css/bootstrap.css" />
<link rel="stylesheet" href="/manager/css/css.css" />
<script type="text/javascript" src="/js/jquery1.9.0.min.js"></script>
<script type="text/javascript" src="/js/bootstrap.min.js"></script>
<script type="text/javascript" src="/js/sdmenu.js"></script>
<script type="text/javascript" src="/js/laydate/laydate.js"></script>

</head>

<body>
 <script type="text/javascript">
  	function jump(page,query){
  		if(page ==""){
  			return;
  		}
  		var url ='${action}';
	  	window.location.href=url+"?"+query+"&start="+page;
  	}
  	function myjump(query){
  		
  		jump(document.getElementById("jumppage").value,query)
  	}
  </script>
<#include "../header.ftl"> 
            
<div id="middle">
     <div class="left">

     
     
     
     <script type="text/javascript">
     	function deleteEntity (url,id,manager){
     			$.ajax({

		     type: 'POST',
		
		     url: url+"?delete="+manager+"&id="+id ,
		
		
		    success: function(data){
		    	if(data.status){
		    		alert("删除成功")
		    		window.location.reload();
		    	}else{
		    		alert(data.msg)
		    	}
		    } ,
		
		    dataType: "json"
		
		});
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
     
<form name="form1" id="form1" action="${action}" method="get">
  {2}

<table class="table table-bordered table-hover table-striped">
    <tbody>
    	<tr  align="center">
    		{0}
    	</tr>
    	<#list entity as entity>  
    		<tr  align="center">
    			 {1}
    		</tr>
    	</#list>
    	   </tbody>
    </table>
 当前 ${current } 页，  共${count }  <a href="javascript:jump(${back },'${auto.query }')">上一页 </a>  <a href="javascript:jump(${next },'${auto.query }')">下一页 </a>
  <br>
  跳转<input id="jumppage">  <a href="javascript:myjump('${auto.query }')">跳转 </a>
      </form>
    </div>
    </div>
<!-- 底部 -->
<div id="footer">版权所有：晶科客流 &copy; 2015&nbsp;&nbsp;&nbsp;&nbsp;<a href="http://www.mycodes.net/" target="_blank">源码之家</a></div>
    
    

 <script>
!function(){
laydate.skin('molv');
laydate({elem: '#Calendar'});
}();
 
</script>

 
</body>
</html>



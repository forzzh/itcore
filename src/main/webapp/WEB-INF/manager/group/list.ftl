

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

</head>

<body>
 <script type="text/javascript">
 if($.browser.msie && $.browser.version<11){
		alert("ie11以下浏览器不支持，请使用其它浏览器");
		window.close();
	}
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

function auditGroup(groupId, status) {
$.get("/manager/group/auditGroup", {"groupId":groupId, "status":status}, function(result){
	alert(result.msg);
	if(result.status) {
		location.reload();
	}
})
}
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
  <td   align="right" nowrap="nowrap" bgcolor="#f1f1f1"><td   align="right" nowrap="nowrap" bgcolor="#f1f1f1"><td   align="right" nowrap="nowrap" bgcolor="#f1f1f1"><td   align="right" nowrap="nowrap" bgcolor="#f1f1f1"><td   align="right" nowrap="nowrap" bgcolor="#f1f1f1"><td   align="right" nowrap="nowrap" bgcolor="#f1f1f1"><td   align="right" nowrap="nowrap" bgcolor="#f1f1f1"><table class="table table-bordered" > <tr> <td>圈子名称<td><input class="span1-1" name='name'/></td><td>图标地址<td><input class="span1-1" name='image'/></td><td>详情描述<td><input class="span1-1" name='description'/></td><td>关注人数<td><input class="span1-1" name='foucsPersonNum'/></td><td>标签<td><input class="span1-1" name='label.name'/></td><td>热度<td><input class="span1-1" name='score'/></td>状态:<select name='status'><option value=''>未选择</option><option value='delete'>删除</option><option value='nodelete'>正常</option><option value='agree'>2</option><option value='noagree'>3</option><option value='read'>3</option><option value='noread'>4</option><option value='ok'>同意</option><option value='no'>不同意</option><option value='pending'>审核中</option></select>开放状态:<select name='joinType'><option value=''>未选择</option><option value='approve'>需要审核</option><option value='noapprove'>不需要审核</option></select><td>扩展标签<td><input class="span1-1" name='extLabels'/></td></tr></table><table class="margin-bottom-20 table  no-border"><tbody><tr><td class="text-center"><input type="submit" value="确定" class="btn btn-info" style="width:80px;"/><input type="button" onclick="window.location.href='${action}'" value="清空" class="btn btn-info" style="width:80px;"/></td> </tr></tbody></table>

<table class="table table-bordered table-hover table-striped">
    <tbody>
    	<tr  align="center">
    		<td> 主键</td><td>圈子名称</td><td>图标地址</td><td>详情描述</td><td>关注人数</td><td>标签</td><td>热度</td><td>状态</td><td>开放状态</td><td>扩展标签</td><td> 创建时间</td><td> 操作</td>
    	</tr>
    	<#list entity as entity>  
    		<tr  align="center">
    			 <td><a href='/manager/group/modify?id=${entity.id}'>${entity.id}</a></td><td><#if entity.name??>${entity.name}</#if></td><td><#if entity.image??>${entity.image}</#if></td><td><#if entity.description??>${entity.description}</#if></td><td><#if entity.foucsPersonNum??>${entity.foucsPersonNum}</#if></td><td><#if entity.label??><#if entity.label.name??><a href='/manager/label/modify?id=${entity.label.id}'>${entity.label.name}</a></#if></#if></td><td><#if entity.score??>${entity.score}</#if></td><td><#if entity.status?? ><#if "pending"==entity.status >审核中</#if></#if><#if entity.status?? ><#if "no"==entity.status >不同意</#if></#if><#if entity.status?? ><#if "ok"==entity.status >同意</#if></#if><#if entity.status?? ><#if "noread"==entity.status >4</#if></#if><#if entity.status?? ><#if "read"==entity.status >3</#if></#if><#if entity.status?? ><#if "noagree"==entity.status >3</#if></#if><#if entity.status?? ><#if "agree"==entity.status >2</#if></#if><#if entity.status?? ><#if "nodelete"==entity.status >正常</#if></#if><#if entity.status?? ><#if "delete"==entity.status >删除</#if></#if></td><td><#if entity.joinType?? ><#if "noapprove"==entity.joinType >不需要审核</#if></#if><#if entity.joinType?? ><#if "approve"==entity.joinType >需要审核</#if></#if></td><td><#if entity.extLabels??>${entity.extLabels}</#if></td><td><#if entity.createDate??>${entity.createDate}</#if></td><td><a href="javascript:auditGroup(${entity.id}, 1)">同意</a><a href="javascript:auditGroup(${entity.id}, 2)">拒绝</a></td>
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



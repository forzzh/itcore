

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
 <script type="text/javascript">
 if($.browser.msie && $.browser.version<11){
		alert("ie11�����������֧�֣���ʹ�����������");
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
		    		alert("ɾ���ɹ�")
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
  <td   align="right" nowrap="nowrap" bgcolor="#f1f1f1"><td   align="right" nowrap="nowrap" bgcolor="#f1f1f1"><td   align="right" nowrap="nowrap" bgcolor="#f1f1f1"><td   align="right" nowrap="nowrap" bgcolor="#f1f1f1"><td   align="right" nowrap="nowrap" bgcolor="#f1f1f1"><td   align="right" nowrap="nowrap" bgcolor="#f1f1f1"><td   align="right" nowrap="nowrap" bgcolor="#f1f1f1"><table class="table table-bordered" > <tr> <td>Ȧ������<td><input class="span1-1" name='name'/></td><td>ͼ���ַ<td><input class="span1-1" name='image'/></td><td>��������<td><input class="span1-1" name='description'/></td><td>��ע����<td><input class="span1-1" name='foucsPersonNum'/></td><td>��ǩ<td><input class="span1-1" name='label.name'/></td><td>�ȶ�<td><input class="span1-1" name='score'/></td>״̬:<select name='status'><option value=''>δѡ��</option><option value='delete'>ɾ��</option><option value='nodelete'>����</option><option value='agree'>2</option><option value='noagree'>3</option><option value='read'>3</option><option value='noread'>4</option><option value='ok'>ͬ��</option><option value='no'>��ͬ��</option><option value='pending'>�����</option></select>����״̬:<select name='joinType'><option value=''>δѡ��</option><option value='approve'>��Ҫ���</option><option value='noapprove'>����Ҫ���</option></select><td>��չ��ǩ<td><input class="span1-1" name='extLabels'/></td></tr></table><table class="margin-bottom-20 table  no-border"><tbody><tr><td class="text-center"><input type="submit" value="ȷ��" class="btn btn-info" style="width:80px;"/><input type="button" onclick="window.location.href='${action}'" value="���" class="btn btn-info" style="width:80px;"/></td> </tr></tbody></table>

<table class="table table-bordered table-hover table-striped">
    <tbody>
    	<tr  align="center">
    		<td> ����</td><td>Ȧ������</td><td>ͼ���ַ</td><td>��������</td><td>��ע����</td><td>��ǩ</td><td>�ȶ�</td><td>״̬</td><td>����״̬</td><td>��չ��ǩ</td><td> ����ʱ��</td><td> ����</td>
    	</tr>
    	<#list entity as entity>  
    		<tr  align="center">
    			 <td><a href='/manager/group/modify?id=${entity.id}'>${entity.id}</a></td><td><#if entity.name??>${entity.name}</#if></td><td><#if entity.image??>${entity.image}</#if></td><td><#if entity.description??>${entity.description}</#if></td><td><#if entity.foucsPersonNum??>${entity.foucsPersonNum}</#if></td><td><#if entity.label??><#if entity.label.name??><a href='/manager/label/modify?id=${entity.label.id}'>${entity.label.name}</a></#if></#if></td><td><#if entity.score??>${entity.score}</#if></td><td><#if entity.status?? ><#if "pending"==entity.status >�����</#if></#if><#if entity.status?? ><#if "no"==entity.status >��ͬ��</#if></#if><#if entity.status?? ><#if "ok"==entity.status >ͬ��</#if></#if><#if entity.status?? ><#if "noread"==entity.status >4</#if></#if><#if entity.status?? ><#if "read"==entity.status >3</#if></#if><#if entity.status?? ><#if "noagree"==entity.status >3</#if></#if><#if entity.status?? ><#if "agree"==entity.status >2</#if></#if><#if entity.status?? ><#if "nodelete"==entity.status >����</#if></#if><#if entity.status?? ><#if "delete"==entity.status >ɾ��</#if></#if></td><td><#if entity.joinType?? ><#if "noapprove"==entity.joinType >����Ҫ���</#if></#if><#if entity.joinType?? ><#if "approve"==entity.joinType >��Ҫ���</#if></#if></td><td><#if entity.extLabels??>${entity.extLabels}</#if></td><td><#if entity.createDate??>${entity.createDate}</#if></td><td><a href="javascript:auditGroup(${entity.id}, 1)">ͬ��</a><a href="javascript:auditGroup(${entity.id}, 2)">�ܾ�</a></td>
    		</tr>
    	</#list>
    	   </tbody>
    </table>
 ��ǰ ${current } ҳ��  ��${count }  <a href="javascript:jump(${back },'${auto.query }')">��һҳ </a>  <a href="javascript:jump(${next },'${auto.query }')">��һҳ </a>
  <br>
  ��ת<input id="jumppage">  <a href="javascript:myjump('${auto.query }')">��ת </a>
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

 
</body>
</html>



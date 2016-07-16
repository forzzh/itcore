



	<!DOCTYPE html>
	<html>
		<head>
			<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
			<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
			<meta charset="utf-8">
			<title></title>
			<meta name="keywords" content="">
			<meta name="description" content="">
			<link rel="stylesheet" type="text/css" href="/web/css/style.css">
			<script type="text/javascript" src="/js/jquery1.9.0.min.js"></script>
			<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
					<link rel="stylesheet" type="text/css" href="/web/css/self.css">
		<script src="/kindeditor/kindeditor.js"></script>
		<script type="text/javascript" src="/web/js/iebug.js"></script>
		<script type="text/javascript" src="/js/jquery.md5.js"></script>
		</head>
<script>
if($.browser.msie && $.browser.version<11){
	alert("ie11以下浏览器不支持，请使用其它浏览器");
	window.close();
}
			var dialog = null;
			KindEditor.ready(function(k) {
			
		 function show(title,content,back){
			
					dialog =k.dialog({
						width : 200,
						title : title,
						autoOpen:false,
						body : '<div style="margin:10px;"><strong>'+content+'</strong></div>',
						closeBtn : {
							name : '关闭',
							click : function(e) {
								if(back||"" !=""){
									back();
								}
								dialog.remove();
							}
						},
						yesBtn : {
							name : '确定',
							click : function(e) {
								//alert(this.value);
								if(back||"" !=""){
									back();
								}
									dialog.remove();
							}
						}
					
			
					});
			}
		 	function checkPassword(password) {
				if (password == null || password.length<6||password.length>15) {
					return false;
				}
				var pattern = new RegExp("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");
				return	pattern.exec(password);
			}
			var redirectlogin  = function(){
				window.location.href="/user/login";
			}
			$("#check").click(function(){
				//console.log(dialog)
				
				if($("#newPassword").val()==""){
					show("提示","密码不能为空")
					return false;
				}
				if(!checkPassword($("#newPassword").val())){
					alert("密码不能小于6并且不能大于15位，并且包含数字和字母");
					return;
				}
				$.ajax({

				     type: 'POST',
				
				     url: "/user/hadleForget" ,
				
				    data: {UUID:$("#UUID").val(),newPassword:$.md5($("#newPassword").val())} ,
				
				    success: function (data){
				    	//console.log(data)
				    	
				    	if(data.status){
				    	
				    		show("提示",data.msg,redirectlogin)
				    	}else{
				    	show("提示",data.msg)
				    	}
				    	
				    } ,
				
				    dataType: "json"
				
				});
							
			})
			<#if  msg?? >
				show("提示","${msg.msg}")
			</#if>
			});
		</script>
		<body>
			<div class="login">
				<div class="content relative">
					<nav>
						<ul class="clearfix">
							<li class="fl"><a href="/group/list">大小营地</a></li>
						<li class="fl"><a href="/label/list">长短标签</a></li>
						<li class="fl"><a href="/book/list">图书推荐</a></li>
						<li class="fr"><a href="/user/my/post">我的</a></li>
						<li class="fr"><a href="/user/message">消息</a></li>
						<li class="fr"><a href="/post/all">技术问答</a></li>

						</ul>
					</nav>
					<img width="1282" src="/web/images/login.jpg">
					<form action="/user/hadleForget" method="post" name="form1" id="form1" >
						  <input type="password" id="newPassword"  name="newPassword"  placeholder =  "新密码"/></p>
						   <input type="hidden" id="UUID" name="UUID" value ="${UUID}"/>
						<input type="button" name=""  id="check" value="修改密码" />
					<a href="/user/login">转到登录页面？</a>
					</form>
					
					
					
				</div>
			</div>	
		</body>

	</html>
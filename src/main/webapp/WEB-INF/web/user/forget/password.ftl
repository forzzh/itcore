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
					<link rel="stylesheet" type="text/css" href="/web/css/self.css">
			<script type="text/javascript" src="/js/jquery1.9.0.min.js"></script>
			<link rel="stylesheet" href="/kindeditor/themes/default/default.css" />
		<script src="/kindeditor/kindeditor.js"></script>
		<script type="text/javascript" src="/web/js/iebug.js"></script>
		</head>
<script>
if($.browser.msie && $.browser.version<11){
	alert("ie11以下浏览器不支持，请使用其它浏览器");
	window.close();
}
			var dialog = null;
			KindEditor.ready(function(k) {
			
		 function show(title,content){
			
					dialog =k.dialog({
						width : 200,
						title : title,
						autoOpen:false,
						body : '<div style="margin:10px;"><strong>'+content+'</strong></div>',
						closeBtn : {
							name : '关闭',
							click : function(e) {
								dialog.remove();
							}
						},
						yesBtn : {
							name : '确定',
							click : function(e) {
								//alert(this.value);
									dialog.remove();
							}
						}
					
			
					});
			}
			$("#check").click(function(){
				//console.log(dialog)
				if($("#user").val()==""){
					show("提示","昵称不能为空")
					return false;
				}
				 var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
				if(!filter.test($("#email").val())){
					show("提示","邮箱不符合规则")
					return false;
				}
				document.form1.submit()
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
							<li class="fl"><a href="">大小营地</a></li>
							<li class="fl"><a href="">长短标签</a></li>
							<li class="fl"><a href="">图书推荐</a></li>
							<li class="fr"><a href="">注册</a></li>
							<li class="fr"><a href="">登录</a></li>
							<li class="fr"><a href="">技术问答</a></li>
						</ul>
					</nav>
					<img width="1282" src="/web/images/login.jpg">
					<form action="/user//password/forgetAction" method="post" name="form1" id="form1" >
						<input type="text" id="email" name="email" id="" placeholder="找回密码邮箱"/>
						<input type="button" name=""  id="check" value="发送邮件" />
						<a href="/user/login">转到登录页面？</a>
					</form>
				</div>
			</div>	
		</body>

	</html>
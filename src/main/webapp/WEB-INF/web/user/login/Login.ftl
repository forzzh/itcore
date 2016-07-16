<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<title>聚集之美登录</title>
			<meta name="keywords" content="聚集之美登录">
		<meta name="description" content="欢迎来到聚集之美登录，开启你的技术生涯新生活">
		<link rel="stylesheet" type="text/css" href="/web/css/style.css">
		<link rel="stylesheet" type="text/css" href="/web/css/self.css">
		<script type="text/javascript" src="/js/jquery1.9.0.min.js"></script>
		<script type="text/javascript" src="/web/js/iebug.js"></script>
		<script type="text/javascript" src="/js/publicMethod.js"></script>
		<script type="text/javascript" src="/js/jquery.md5.js"></script>
	</head>
	
	<body>
		<div class="login">
			<div class="content relative">
				<nav>
					<ul class="clearfix">
				<li class="fl"><a href="/group/list">大小营地</a></li>
						<li class="fl"><a href="/label/list">长短标签</a></li>
						<li class="fl"><a href="/book/list">图书推荐</a></li>
						<li class="fr"><a href="/user/my/post">我的</a></li>
						<li class="fr"><a id="UnreadMessageCount" href="/user/message">消息</a></li>
						<li class="fr"><a href="/post/all">技术问答</a></li>

					</ul>
				</nav>
				<img width="1282" src="/web/images/login.jpg">
				<form name="loginForm" action="">
					<input type="text" name="email" value="<#if  msg?? >${email}</#if>" id="email" placeholder="邮箱" onkeypress="if(event.keyCode==13) {btn.click();return false;}"/>
					<input type="password" name="password" id="password" placeholder="密码" onkeypress="if(event.keyCode==13) {btn.click();return false;}"/>
					<input name="isMemPassword" type="checkbox" style="display: inline-block;" />记住密码
					<a href="/user/password/forget" style="width: 70px;display: inline-block;position: relative;left: 250px;color: white;">找回密码</a>
					<input type="button" id="btn" name="btn" onclick="login()" name="" id="" value="登录" />
					<a href="/user/reg" class="tc" style="color: white;">没有账号？注册</a>
				</form>
			</div>
		</div>	
	</body>
	<script>
	
	$(function(){
		document.loginForm.isMemPassword.checked=GetCookie("checked");
		document.loginForm.email.value=GetCookie("email");
		document.loginForm.password.value=GetCookie("password");
		if(GetCookie("checked")&&GetCookie("password")){
			login();
		};
		<#if msg?? >
			setTimeout(function(){
			alert("${msg}");
			},1000)
		</#if>
	})
	
	//获得Cookie解码后的值
	function GetCookieVal(offset)
	{
		var endstr = document.cookie.indexOf (";", offset);
		if (endstr == -1)
			endstr = document.cookie.length;
		return unescape(document.cookie.substring(offset, endstr));
	}
	function SetCookie(name, value) {
		var today = new Date();
		var expires = new Date();
		expires.setTime(today.getTime() + 1000*60*60*24*7);
		document.cookie = name + "=" + escape(value) + "; expires=" + expires.toGMTString() + ";path=/";
	}
	//删除Cookie
	function DelCookie(name)
	{
		var exp = new Date();
		exp.setTime (exp.getTime() - 1);
		var cval = GetCookie (name);
		document.cookie = name + "=" + cval + "; expires="+ exp.toGMTString();
	}
	//获得Cookie的原始值
	function GetCookie(name)
	{
		var arg = name + "=";
		var alen = arg.length;
		var clen = document.cookie.length;
		var i = 0;
		while (i < clen)
		{
			var j = i + alen;
			if (document.cookie.substring(i, j) == arg)
				return GetCookieVal (j);
			i = document.cookie.indexOf(" ", i) + 1;
			if (i == 0) break;
		}
		return null;
	}
	
	var redirect="${redirect!""}";
				function login(){
					if(!GetCookie("password")&&!checkPassword(document.loginForm.password.value)){
						DelCookie("password");
						alert("密码不能小于6并且不能大于15位，并且包含数字和字母");
						return;
					}
					if(document.loginForm.isMemPassword.checked){
						var md5Pwd=GetCookie("password");
						DelCookie("checked");
						DelCookie("email");
						DelCookie("password");
						SetCookie("checked",document.loginForm.isMemPassword.checked);
						SetCookie("email",document.loginForm.email.value);
						SetCookie("password",md5Pwd||$.md5(document.loginForm.password.value));
					}else{
						DelCookie("checked");
// 						DelCookie("email");
						DelCookie("password");
					}
					$.ajax({

				     type: 'POST',
				
				     url: "/user/loginAction" ,
				
				    data: {email:$("#email").val(),password:GetCookie("password")||$.md5($("#password").val())} ,
				
				    success: function (data){
				    	//console.log(data)
				    	
				    	if(data.status){
				    		if(redirect==""){
				    			window.location.href="/index.html"
				    		}else{
				    			window.location.href=redirect;
				    		}
				    		
				    	}else	if(!data.status&&data.msg=="用户未激活"){
				    		DelCookie("password");
				    		document.loginForm.password.value=GetCookie("password");
				    		 alert("用户未激活，已经发送邮件到邮箱，请检查");
				    	}else if(!data.status&&data.msg=="用户名密码错误"){
				    		DelCookie("password");
				    		document.loginForm.password.value=GetCookie("password");
				    		 alert("用户名或密码错误");
				    		// window.location.href="/user/password/forget"
				    	}else if(!data.status&&data.msg=="没有该用户"){
				    		DelCookie("password");
				    		document.loginForm.password.value=GetCookie("password");
				    		 alert("没有该用户,请发送邮件去question@corer.me");
				    	}else{
				    		DelCookie("password");
				    		document.loginForm.password.value=GetCookie("password");
				    		alert(data.msg);
				    		
				    	}
				    	
				    } ,
				
				    dataType: "json"
				
				});
				}
				function checkPassword(password) {
					if (password == null || password.length<6||password.length>15) {
						return false;
					}
					var pattern = new RegExp("^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$");
					return	pattern.exec(password);
				}
			</script>
	

</html>
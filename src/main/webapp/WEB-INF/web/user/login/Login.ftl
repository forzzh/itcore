<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<title>�ۼ�֮����¼</title>
			<meta name="keywords" content="�ۼ�֮����¼">
		<meta name="description" content="��ӭ�����ۼ�֮����¼��������ļ�������������">
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
				<li class="fl"><a href="/group/list">��СӪ��</a></li>
						<li class="fl"><a href="/label/list">���̱�ǩ</a></li>
						<li class="fl"><a href="/book/list">ͼ���Ƽ�</a></li>
						<li class="fr"><a href="/user/my/post">�ҵ�</a></li>
						<li class="fr"><a id="UnreadMessageCount" href="/user/message">��Ϣ</a></li>
						<li class="fr"><a href="/post/all">�����ʴ�</a></li>

					</ul>
				</nav>
				<img width="1282" src="/web/images/login.jpg">
				<form name="loginForm" action="">
					<input type="text" name="email" value="<#if  msg?? >${email}</#if>" id="email" placeholder="����" onkeypress="if(event.keyCode==13) {btn.click();return false;}"/>
					<input type="password" name="password" id="password" placeholder="����" onkeypress="if(event.keyCode==13) {btn.click();return false;}"/>
					<input name="isMemPassword" type="checkbox" style="display: inline-block;" />��ס����
					<a href="/user/password/forget" style="width: 70px;display: inline-block;position: relative;left: 250px;color: white;">�һ�����</a>
					<input type="button" id="btn" name="btn" onclick="login()" name="" id="" value="��¼" />
					<a href="/user/reg" class="tc" style="color: white;">û���˺ţ�ע��</a>
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
	
	//���Cookie������ֵ
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
	//ɾ��Cookie
	function DelCookie(name)
	{
		var exp = new Date();
		exp.setTime (exp.getTime() - 1);
		var cval = GetCookie (name);
		document.cookie = name + "=" + cval + "; expires="+ exp.toGMTString();
	}
	//���Cookie��ԭʼֵ
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
						alert("���벻��С��6���Ҳ��ܴ���15λ�����Ұ������ֺ���ĸ");
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
				    		
				    	}else	if(!data.status&&data.msg=="�û�δ����"){
				    		DelCookie("password");
				    		document.loginForm.password.value=GetCookie("password");
				    		 alert("�û�δ����Ѿ������ʼ������䣬����");
				    	}else if(!data.status&&data.msg=="�û����������"){
				    		DelCookie("password");
				    		document.loginForm.password.value=GetCookie("password");
				    		 alert("�û������������");
				    		// window.location.href="/user/password/forget"
				    	}else if(!data.status&&data.msg=="û�и��û�"){
				    		DelCookie("password");
				    		document.loginForm.password.value=GetCookie("password");
				    		 alert("û�и��û�,�뷢���ʼ�ȥquestion@corer.me");
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
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
				<title>聚集之美注册</title>
		<meta name="keywords" content="聚集之美注册">
		<meta name="description" content="欢迎来到聚集之美注册，开启你的技术生涯新生活">
				<link rel="stylesheet" type="text/css" href="/web/css/self.css">
		<link rel="stylesheet" type="text/css" href="/web/css/style.css">
			<script type="text/javascript" src="/js/jquery1.9.0.min.js"></script>
			<script type="text/javascript" src="/web/js/iebug.js"></script>
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
						<li class="fr"><a href="/user/message">消息</a></li>
						<li class="fr"><a href="/post/all">技术问答</a></li>

					</ul>
				</nav>
				<img width="1282" src="/web/images/login.jpg">
				<form action="">
					<input type="text" name="user"  id="user" placeholder="昵称"/>
					<input type="text" name="email" id="email" placeholder="邮箱"/>
					<input type="password" name="password" id="password" placeholder="密码"/>
					<select id="sex" >
						<option value="boy">男</option>
							<option value="gril">女</option>
					</select>					
					<input style="margin-top:10px;" type="button" onclick="login()" name="" id="" value="注册" />
					<a href="/user/login" style="color: white;">有账号? 登陆</a>
				</form>
			</div>
			<script>
				function login(){
					if(!checkPassword($("#password").val())){
						alert("密码不能小于6并且不能大于15位，并且包含数字和字母");
						return;
					}
					$.ajax({

				     type: 'POST',
				
				     url: "/user/regUser" ,
				
				    data: {user:$("#user").val(),password:$("#password").val(),email:$("#email").val(),sex:$("#sex").val()} ,
				
				    success: function (data){
				    	//console.log(data)
				    	
				    	if(data.status){
				    	
				    		alert("激活邮件已发送,请检查邮箱。如果未收到邮件，请登陆激活!");
				    		window.location.href="/user/login"
				    	}else{
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
		</div>	
	</body>

</html>
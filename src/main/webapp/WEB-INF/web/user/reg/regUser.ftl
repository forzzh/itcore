<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
				<title>�ۼ�֮��ע��</title>
		<meta name="keywords" content="�ۼ�֮��ע��">
		<meta name="description" content="��ӭ�����ۼ�֮��ע�ᣬ������ļ�������������">
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
		<li class="fl"><a href="/group/list">��СӪ��</a></li>
						<li class="fl"><a href="/label/list">���̱�ǩ</a></li>
						<li class="fl"><a href="/book/list">ͼ���Ƽ�</a></li>
						<li class="fr"><a href="/user/my/post">�ҵ�</a></li>
						<li class="fr"><a href="/user/message">��Ϣ</a></li>
						<li class="fr"><a href="/post/all">�����ʴ�</a></li>

					</ul>
				</nav>
				<img width="1282" src="/web/images/login.jpg">
				<form action="">
					<input type="text" name="user"  id="user" placeholder="�ǳ�"/>
					<input type="text" name="email" id="email" placeholder="����"/>
					<input type="password" name="password" id="password" placeholder="����"/>
					<select id="sex" >
						<option value="boy">��</option>
							<option value="gril">Ů</option>
					</select>					
					<input style="margin-top:10px;" type="button" onclick="login()" name="" id="" value="ע��" />
					<a href="/user/login" style="color: white;">���˺�? ��½</a>
				</form>
			</div>
			<script>
				function login(){
					if(!checkPassword($("#password").val())){
						alert("���벻��С��6���Ҳ��ܴ���15λ�����Ұ������ֺ���ĸ");
						return;
					}
					$.ajax({

				     type: 'POST',
				
				     url: "/user/regUser" ,
				
				    data: {user:$("#user").val(),password:$("#password").val(),email:$("#email").val(),sex:$("#sex").val()} ,
				
				    success: function (data){
				    	//console.log(data)
				    	
				    	if(data.status){
				    	
				    		alert("�����ʼ��ѷ���,�������䡣���δ�յ��ʼ������½����!");
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
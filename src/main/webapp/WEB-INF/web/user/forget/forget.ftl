



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
	alert("ie11�����������֧�֣���ʹ�����������");
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
							name : '�ر�',
							click : function(e) {
								if(back||"" !=""){
									back();
								}
								dialog.remove();
							}
						},
						yesBtn : {
							name : 'ȷ��',
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
					show("��ʾ","���벻��Ϊ��")
					return false;
				}
				if(!checkPassword($("#newPassword").val())){
					alert("���벻��С��6���Ҳ��ܴ���15λ�����Ұ������ֺ���ĸ");
					return;
				}
				$.ajax({

				     type: 'POST',
				
				     url: "/user/hadleForget" ,
				
				    data: {UUID:$("#UUID").val(),newPassword:$.md5($("#newPassword").val())} ,
				
				    success: function (data){
				    	//console.log(data)
				    	
				    	if(data.status){
				    	
				    		show("��ʾ",data.msg,redirectlogin)
				    	}else{
				    	show("��ʾ",data.msg)
				    	}
				    	
				    } ,
				
				    dataType: "json"
				
				});
							
			})
			<#if  msg?? >
				show("��ʾ","${msg.msg}")
			</#if>
			});
		</script>
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
					<form action="/user/hadleForget" method="post" name="form1" id="form1" >
						  <input type="password" id="newPassword"  name="newPassword"  placeholder =  "������"/></p>
						   <input type="hidden" id="UUID" name="UUID" value ="${UUID}"/>
						<input type="button" name=""  id="check" value="�޸�����" />
					<a href="/user/login">ת����¼ҳ�棿</a>
					</form>
					
					
					
				</div>
			</div>	
		</body>

	</html>
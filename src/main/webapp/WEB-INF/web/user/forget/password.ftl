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
	alert("ie11�����������֧�֣���ʹ�����������");
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
							name : '�ر�',
							click : function(e) {
								dialog.remove();
							}
						},
						yesBtn : {
							name : 'ȷ��',
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
					show("��ʾ","�ǳƲ���Ϊ��")
					return false;
				}
				 var filter  = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
				if(!filter.test($("#email").val())){
					show("��ʾ","���䲻���Ϲ���")
					return false;
				}
				document.form1.submit()
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
							<li class="fl"><a href="">��СӪ��</a></li>
							<li class="fl"><a href="">���̱�ǩ</a></li>
							<li class="fl"><a href="">ͼ���Ƽ�</a></li>
							<li class="fr"><a href="">ע��</a></li>
							<li class="fr"><a href="">��¼</a></li>
							<li class="fr"><a href="">�����ʴ�</a></li>
						</ul>
					</nav>
					<img width="1282" src="/web/images/login.jpg">
					<form action="/user//password/forgetAction" method="post" name="form1" id="form1" >
						<input type="text" id="email" name="email" id="" placeholder="�һ���������"/>
						<input type="button" name=""  id="check" value="�����ʼ�" />
						<a href="/user/login">ת����¼ҳ�棿</a>
					</form>
				</div>
			</div>	
		</body>

	</html>
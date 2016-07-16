
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
		<script type="text/javascript" src="/web/js/jquery.min.js"></script>
		<script type="text/javascript" src="/js/publicMethod.js"></script>
		<script type="text/javascript">
			var replyID = null;
			var userId  = null;
			var currentPage = 1;
			var count = 11;//���ò�ѯ����
			$(document).ready(function(){
				//����ID
				replyID = $("#replyID").val();
				userId =  $("#userId").val();
				getReplyList(replyID,currentPage,count);
			});
			function getReplyList(replyID,pageNum,count){
				if (!$.publicMethod.hasValue(replyID) || !$.publicMethod.hasValue(pageNum)) {
					alert("��������replyID"+replyID +" pageNum" +pageNum);
					return false;
				};
				$(".last").hide();
				$.ajax({
				   type: "GET",
				   url: "/reply/queryReplyByReplyID",
				   data:{
				   		replyID:replyID,
				   		pageNum:pageNum,
				   		count:count,
				   		replyEnum:'BookReply'
				   },
				   success: function(msg){
				       if ($.publicMethod.hasValue(msg) && msg.status == true) {
				       		if($.publicMethod.hasValue(msg.list)){
				       			//ҳ���1
				       			currentPage += 1;
				       			for (var i = 0; i < msg.list.length; i++) {
				       				var reply = msg.list[i];
				       				var replyJQ = [];
				       				if(i<count-1){
				       					replyJQ.push("<li><div class='f16 clearfix relative'>");
				       					var image = reply.user.image;
				       					if(image==null||image ==""){
											if(reply.user.sex =='boy'){
												image = ' style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;"'
											}else{
												image = ' style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;"'
											}
										}else{
											image = ' style="background: url(${static}'+ reply.user.image+') no-repeat 0 center;background-size: 100%;"'
										}
				       					replyJQ.push('<i class="logo" '+image+'></i>'); 
				       					replyJQ.push("<p class='fl'>");
				       					replyJQ.push("<a href='/user/other/post/"+reply.user.id+"'><span class='author c44'>"+reply.user.user+"</span></a>");
				       					if($.publicMethod.hasValue(reply.group)){
				       						replyJQ.push("&nbsp;&nbsp;&nbsp;����&nbsp;&nbsp;&nbsp;<span class='classify c44'><a href=\"/group/detail/"+reply.group.id+"\">"+reply.group.name+"</a></span>");
				       					};
				       					replyJQ.push("<br/>");
				       					replyJQ.push(reply.showData+"</p>");
				       					//����
				       					replyJQ.push("<p class='fl'>");
				       					replyJQ.push(reply.content);
				       					replyJQ.push("</p>");
				       					replyJQ.push("</div></li>");
				       				}else{
				       					//��ʾ����
				       					replyJQ.push('<li class="last" style="color: #666;border: 1px solid #eee;padding: 15px 40px;width: 50px;"><a style="background-color:#c9c9c9;" href=\'javascript:getReplyList(\"'+replyID+'\",\"'+currentPage+'\",\"'+count+'\");\'>����</a></li>');
				       				}
				       				$(".answerList").append(replyJQ.join(""));
				       			};
				       			
				       		}else{
				       			//��һ�λ�ȡû������ʱ������
				       			if(currentPage == 1){
				       				$(".answerList").hide();
				       			}
				       		}	
				       }else{
				           alert("��ѯʧ�ܣ�" + msg.msg);
				       }
				   }
				});
			}
		</script>
	</head>

	<body>
		<input type="hidden" value="${id}" id="replyID"/>
		<input type="hidden" value="${userId!''}" id="userId"/>
		<div class="detail">
			<div class="content relative">
				<nav>
					<ul class="clearfix">
						<li class="fr"><a id="userImg" href="/user/my/post">�ҵ�</a></li>
						<li class="fr"><a id="UnreadMessageCount" href="/user/message">��Ϣ</a></li>
					</ul>
				</nav>
			</div>
		</div>
		<div id="menuTop" style="color: black;width: 100%;text-align: center;">
			<ul style="position: relative;left:-100px;margin: 16px 0px 16px 0px;">
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><img width="48" height="48" alt="javaͼ��" src="/web/images/logo.jpg"><br><div style="text-align: center;width: 65px;position: relative;left:-8px;">�ۼ�֮��</div></li>
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><img width="48" height="48" alt="javaͼ��" src="/web/images/cloud_48dp.png"><br><div style="text-align: center;width: 48px;">Ȧ��</div></li>
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><img width="48" height="48" alt="javaͼ��" src="/web/images/logo.png"><br><div style="text-align: center;width: 48px;">��ǩ</div></li>
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><div style="width: 48px;height: 48px;background-color: #c52062;border-radius:50%;"></div><br><div style="text-align: center;width: 48px;">ͼ��</div></li>
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><div style="width: 48px;height: 48px;background-color: #eceff1;border-radius:50%;color: #039be5;text-align: center;line-height: 48px;">&bull;&bull;&bull;</div><br><div style="text-align: center;width: 48px;">����</div></li>
			</ul>
		</div>
		<div class="center">
			<div class="content clearfix">
				<div class="quesDetail">
					<div class="part1 relative">
						<div class="title">${book.name}?</div>
						<ul class="clearfix">
							<#if book.label??>
								<li style="border-right:0px;">��ǩ��</li>
								<li><a href="/post/all/${book.label.name}"><span class="c22">${book.label.name}</span></a></li>
							</#if>
							<li style="border-right:0px;">���ߣ�${book.author}</li>
						</ul>
						<div class="share abs">
							<div>
							</div>
						</div>
					</div>
					<p class="articleCon">
						��飺<br/>
						${book.content}
					</p>
					<div class="part2">
						<!-- <ul class="personInfo">
							<li>
								<div class="f16 clearfix relative">
									<i class="logo">ǰ</i>
									<p class="fl"><span class="author c44">Kaja</span>&nbsp;&nbsp;&nbsp;����&nbsp;&nbsp;&nbsp;<span class="classify c44">ǰ�����Ĳ�Ҫ��Ҫ��</span><br />2��ǰ �� 432���Ķ�</p>
									<div class="blueBtn"><a href="" class="f16">��Ϊ����</a></div>
								</div>

							</li>
						</ul> -->

						<figure class="tabBlock">
							<!-- <ul class="tabBlock-tabs">
								<li class="tabBlock-tab is-active">����</li>
								<li class="tabBlock-tab">׫д</li>
							</ul> -->
							<div class="tabBlock-content">
								<div class="tabBlock-pane" style="display: block;">
									<ul class="answerList">							
									</ul>
								</div>
							</div>
						</figure>
					</div>
				</div>
			</div>
		</div>
		<!-- <script src="/web/js/tab.js"></script> -->
	</body>

</html>
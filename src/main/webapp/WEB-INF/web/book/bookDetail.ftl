<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<meta charset="utf-8">
		<title></title>
		<meta name="keywords" content="">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<meta name="description" content="">
		<link rel="stylesheet" type="text/css" href="/web/css/style.css">
		<script type="text/javascript" src="/js/jquery1.9.0.min.js"></script>
		<script type="text/javascript" src="/js/erweimajs/jquery.qrcode.js"></script>
		<script type="text/javascript" src="/js/erweimajs/qrcode.js"></script>
		<script type="text/javascript" src="/js/publicMethod.js"></script>

	<style>
		.articleCon img{
			max-width:505px;
		}
		select {
		    font-size: 15px;
		    color: #fff;
		    width: 405px;
		    height: 58px;
		    border: 1px solid #b9b9b9;
		    border-radius: 2px;
		    background: #999;
		}
		.title i {
		    display: inline-block;
		    vertical-align: sub;
		    width: 60px;
		    height: 24px;
		    background: url(/web/images/answered.jpg);
		}
		.buttom-line{
			text-align:center;
			font-family:"΢���ź�";
		}
		.group-select{
			width:200px;
			height:30px;
		}
		.title{
			text-align:center;
		}
		.title image{
			margin:0px auto;
		}
		.p-center-title{
			height:26px;
		}
	</style>
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
			getGroupByUserId(userId);
			
			jQuery('#qrcodeTable').qrcode({
				render	: "table",
				text	: window.location.href+"/wap",
				width	:100,
				height	:100
			});	
		});
		//�ύ
		function submit() {
			var data = {};
			data.content = $("#replyContent").val();
			if($.publicMethod.getStringLength(data.content) == 0 || $.publicMethod.getStringLength(data.content)>300){
				alert("����Ϊ�ջ򳬹��������ƣ�");
				return false;
			}
			data.bookid = replyID;
			data.userID  = userId;
			data.groupid = $("#groupID").val();
			data.categroyEnum = "";
			$.ajax({
			   type: "GET",
			   url: "/book/reply",
			   data:data,
			   success:function(msg){
			   		if(msg.status == true){
			   			alert("�ظ��ɹ���~");
			   			//ˢ�»ظ��б�
			   			$(".answerList").show().empty();
			   			//ˢ�±�
			   			$("#replyContent").val("");
			   			$("#groupID").val("");
			   			currentPage = 1;
			   			getReplyList(replyID,1,count);
			   		}else{
			   			if(msg.msg=="�û�δ��¼"){
			   				alert("�û�δ��¼");
			   				window.location.href="/user/login?redirect="+window.location.href;
			   			}else{
			   				alert("�ظ�ʧ�ܣ�~");
			   			}
			   		}
				}
			});
		};
		//�����û�id���group�б�
		function getGroupByUserId(userID){
			if($.publicMethod.hasValue(userID)){
				$.ajax({
				   type: "GET",
				   url: "/manager/groupPerson/getGroupByUserId",
				   data:{ 
				   		userId:userID
				   },
				   success:function(msg){
				   		if($.publicMethod.hasValue(msg.data)){
				   			//group���뵽�����б�
				   			//<option value ="volvo">Volvo</option>
				   			for (var i = 0; i < msg.data.length; i++) {
				   				$(".group-select").append("<option value='"+msg.data[i].group.id+"''>"+msg.data[i].group.name+"</option>");
				   			};
				   		}else{
				   			$(".group-select").hide();
				   		}
					}
				});
			}else{
				$(".group-select").hide();
			}
		}
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
			       					/* replyJQ.push("<div class='buttom-line c44 f16'>");
			       					replyJQ.push("<a href='javascript:getReplyList(\""+replyID+"\",\""+currentPage+"\",\""+count+"\");' >��ȡ����ظ�</a>")
			       					replyJQ.push("</div>"); */
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
	<div class="content relative"  style="margin-left: -10px;
    margin-top: -8px;">
				<nav>
					<ul class="clearfix">
						<li class="fr"  style=" margin-left:0px;line-height: normal;margin-top:0px;"><a id="userImg" href="/user/my/post">�ҵ�</a></li>
						<li class="fr"  style=" line-height: normal;margin-top:0px;"><a id="UnreadMessageCount" href="/user/message">��Ϣ</a></li>
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
						<div class="title">${book.name}</title>
						</div>
						<ul class="clearfix">
							<#if book.label??>
								<li style="border-right:0px;">��ǩ��</li>
								<li><a href="/post/all/${book.label.name}"><span class="c22">${book.label.name}</span></a></li>
							</#if>
							<li style="border-right:0px;">���ߣ�${book.author}</li>
						</ul>
						<div class="bshare-custom icon-medium">
							<a title="��������΢��" class="bshare-sinaminiblog"></a>
							<a title="��������Qing" class="bshare-sinaqing"></a>
							<a title="��������Vivi" class="bshare-sinavivi"></a>
							<a title="����ƽ̨" class="bshare-more bshare-more-icon more-style-addthis"></a>
							<span class="BSHARE_COUNT bshare-share-count">0</span>
						</div>
						<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/button.js#style=-1&amp;uuid=&amp;pophcol=2&amp;lang=zh"></script>
						<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC0.js"></script>
						<div class="share abs">							
							<div id="qrcodeTable">
							</div>
							<div>
							</div>
						</div>
					</div>
					<p class="articleCon">
						��飺<br/>
						${book.content}
					</p>
					<div class="part2">
						<ul>					
							<li style="border-bottom:0px;">
								<div class="articleName">׫д����</div>	
									<textarea id="replyContent"name="" rows="" cols="" placeholder="д������˵��..."></textarea>
									<div class="f16 clearfix relative">
										<select id="groupID"class="group-select">
											<option value="">------��ѡ�����--------</option>
										</select>
										<div class="blueBtn" onclick="submit()" style="float:right;margin:30px 0;"><a href="javascript:void(0);" class="f16">�ύ</a>
										</div>
									</div>
							</li>						
						</ul>
						<ul class="answerList" style="margin-top:40px;">
							
						</ul>
					</div>
				</div>				
			</div>
		</div>
	</body>

</html>
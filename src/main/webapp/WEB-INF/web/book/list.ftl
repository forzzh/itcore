
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title></title>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<link rel="stylesheet" type="text/css" href="/web/css/style.css">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<script type="text/javascript" src="/js/jquery1.9.0.min.js"></script>
		<script type="text/javascript" src="/js/erweimajs/jquery.qrcode.js"></script>
		<script type="text/javascript" src="/js/erweimajs/qrcode.js"></script>
		<script type="text/javascript" src="/js/publicMethod.js"></script>
		<script>
		var staticurl = '${staticurl!""}'
		</script>
		<script type="text/javascript" src="/web/js/booklist.js"></script>
	</head>

	<body style="    overflow-x: hidden;">
		<div class="book">
	<div class="content relative" style="margin:0 auto;">
				<nav>
					<ul class="clearfix">
				<ul class="clearfix" style="    list-style: none;"  >
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
				<div class="left">
					<div class="part1">
						<div class="title">ͼ���Ƽ�</div>
						<ul class="clearfix" id="randomLabels">
							
						</ul>
					</div>
					<ul class="partContent clearfix">
					</ul>
				</div>	
		<div class="right" style="width:386px">
					<!-- <div class="topic2">
						<p class="visHidden">��ӱ�ǩ</p>
						<div class="relative">
							<i class="abs"></i>
							<input type="text" name="" id="" value="" placeholder="����..."/>
						</div>
					</div> -->
					<h3>���ű�ǩ</h3>
					<ul class="boxList clearfix" style="    margin-left: -32px;">
						<#if (labels?size>0)>   
							<#list labels as label>
								<li   style="    list-style: none;"><a href="/post/all/${label.name}">${label.name}</a></li>
							</#list>
						</#if>
					</ul>
					<dl class="newSoftware">
						<dt>�������</dt>
						<#if (adTopics?size>0)>   
							<#list adTopics as ad>
								<dd class="clearfix">
									<#if ad.image??>
										<img style="margin-left:-34px;" src="${staticUrl}${ad.image}" alt="" width="49" class="fl"/>
									<#else>
										<img style="margin-left:-34px;" src="/web/images/ai.jpg" alt="" width="49" class="fl"/>
									</#if>
									<p style="margin-left: 20px;margin-top: -90px;" class="fl"><a href="/post/${ad.id}"><span>${ad.title!""}</span></a> <br />
									${ad.simpleContent!""}</p>
								</dd>
							</#list>
						</#if>
						<!-- <dd class="clearfix">
							
							<p class="fl"><span>Photoshop</span> <br />
							��������2016CC���Ȱ棡</p>
						</dd> -->
						
					</dl>
					<#if (requestTopics?size>0)>  
					<dl class="QAlist">
						<dt>�����ʴ�</dt>
						
							<#list requestTopics as question>
								<dd class="clearfix">
									<i class="fl">ǰ</i>
									<p class="fl"><a href="/post/${question.id}"><span class="question">${question.title}</span></a>
									<#if question.answerState?? && question.answerState == 'HaveAnser'>
										<span class="answer">С�����������.��������ѽ����...</span></p>
									<#else>
										<span class="answer">����С����ǽ��...</span></p>
									</#if>
								</dd>
							</#list>
							
						
					</dl>
					</#if>
				</div>
			</div>
		</div>
	</body>

</html>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<meta charset="GBK">
		<title>�ۼ�֮��</title>
		<meta name="keywords" content="�ۼ�֮��">
		<meta name="description" content="�ۼ�֮����һ��ԭ��+��������̳,�ۼ�����it��Ӣ����ӭ���ļ��� �ô���������">
		<meta name="baidu-site-verification" content="uX0jlmGbS0" />
		<link rel="stylesheet" type="text/css" href="/web/css/reveal.css">
		<link rel="stylesheet" type="text/css" href="/web/css/self.css">
		<link rel="stylesheet" type="text/css" href="/web//css/style.css">
		<script type="text/javascript" src="/js/jquery1.9.0.min.js"></script>
		<script type="text/javascript" src="/web/js/jquery.reveal.js"></script>
		<script type="text/javascript" src="/js/publicMethod.js"></script>
		<script type="text/javascript" src="/js/ajax.js"></script>
		<script type="text/javascript" src="js/t_sina.js"></script>
	</head>
	<style>
	.part1 ul li:nth-child(7) {
/*  border-right: 3px solid #666; */
}
	.imageTitle{
	    max-width: 790px;
	    max-height: 200px;
	}
	#noteCookie li:hover{
		background-color: #f7f7f7;
	}
	#userCookie li:hover{
		background-color: #f7f7f7;
	}
	</style>
	<style type="text/css">

#t_sina{width:360px;padding:10px;background:#fff;margin:0px auto 0;font-size:12px;border:1px solid #ccc;}
#t_sina h2{padding:10px 0 10px 10px;border-bottom:1px solid #ebebeb;color:#333;font-size:14px;}
#content2{width:360px;height:345px;overflow:hidden;}
#content2 ul{width:360px;}
#content2 li{width:360px;padding:10px 0;overflow:hidden;border-bottom:1px dotted #9f9f9f;}
#content2 .pic{float:left;width:50px;height:50px;}
#content2 .pic a{display:block;width:50px;height:50px;}
#content2 p{float:right;width:285px;margin-right:10px;display:inline;line-height:16px;color:#666;}
#content2 p a{color:#6eafd5;font-family:arial;text-decoration:none;}
#content2 p a:hover{text-decoration:underline;}
#content2 p span{color:#999;}
#t_sina dt{font-size:14px;height:40px;line-height:40px;}
#t_sina dd{padding-bottom:5px;color:#666;}
#t_sina .text{width:290px;height:20px;line-height:20px;font-size:12px;font-family:arial;color:#666;position:relative;left:20px;}
#t_sina textarea{width:290px;height:80px;line-height:20px;overflow:auto;font-size:12px;font-family:arial;color:#666;position:relative;left:20px;}
#t_sina .btn{position:relative;left:55px;width:120px;height:30px;}
</style>
	<body  onclick="flag=false;$('#poput').css('display','none');">
		<div class="banner" style="text-align: center;">
			<div class="content relative" style="margin:0;text-align: center;">
				<nav>
					<ul class="clearfix">
						<li class="fr"  style=" margin-left:0px;line-height: normal;margin-top:0px;"><a id="userImg" href="/user/my/post">�ҵ�</a></li>
						<li class="fr"  style=" line-height: normal;margin-top:0px;"><a id="UnreadMessageCount" href="/user/message">��Ϣ </a></li>
					</ul>
				</nav>
				<div style="position: relative; top:100px;margin-left: auto;margin-right: auto;">
					<img width="50" height="50" alt="logo" src="/web/images/logo.jpg" style="display: inline-block;position: relative;top:7px;">
					<span style="font-size: 50px;font-weight: bolder;display: inline-block;">�ۼ�֮��</span>
				</div>
				<div class="abs" style="height: 37px;background-color: white;position: relative;margin-left: auto;margin-right: auto;">
					<img alt="search" src="/web/images/search.jpg" style="left:10px;float: left;padding-top: 10px;padding-left: 10px;">
					<input onclick="if(window.event.stopPropagation)window.event.stopPropagation();else if (window.event)window.event.cancelBubble = true;"  oninput="newsearch(this)" onfocus="flag=false;$('#poput').css('display','');" onblur="setTimeout(function(){if(!flag)$('#poput').css('display','none');}, 500);" id="search" placeholder="����" type="text" style="color:black; padding:5px 0px;background-color: white;padding-left: 20px;background-repeat: no-repeat;" value="" autocomplete="off">
				</div>
				<div onclick="flag=true;if(window.event.stopPropagation)window.event.stopPropagation();else if (window.event)window.event.cancelBubble = true;" id="poput" style="display:none; position: absolute;background-color: white;color: black;top: 270px;z-index: 999;border: 0px solid black;box-shadow:3px 3px 10px black;height: auto;">
					<ul id="notetitle" style="display:none">
						<li style="list-style: none;font-size: 12px;font-weight: bolder;font-family: Roboto,sans-serif;">����</li>
					</ul>
					<ul id="noteCookie" style="display: '';">
						
					</ul>
					<ul id="usertitle"  style="display:none">
						<li style="list-style: none;font-size: 12px;font-weight: bolder;font-family: Roboto,sans-serif;">�û�</li>
					</ul>
					<ul id="userCookie" style="display: '';">
						
					</ul>
					<ul id="more">
						
					</ul>
				</div>
			</div>
		</div>
		<div id="menuTop" style="color: black;width: 100%;text-align: center;">
			<ul style="position: relative;left:-70px;">
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><img width="48" height="48" alt="javaͼ��" src="/web/images/logo.jpg"><br><div style="text-align: center;width: 65px;position: relative;left:-8px;">�ۼ�֮��</div></li>
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><img width="48" height="48" alt="javaͼ��" src="/web/images/cloud_48dp.png"><br><div style="text-align: center;width: 48px;">Ȧ��</div></li>
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><img width="48" height="48" alt="javaͼ��" src="/web/images/logo.png"><br><div style="text-align: center;width: 48px;">��ǩ</div></li>
<!-- 				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><img width="48" height="48" alt="javaͼ��" src="/web/images/maps_48dp.png"><br><div style="text-align: center;width: 65px;position: relative;left:-8px;">���ӵ�ͼ</div></li> -->
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><div style="width: 48px;height: 48px;background-color: #c52062;border-radius:50%;"></div><br><div style="text-align: center;width: 48px;">ͼ��</div></li>
				<li style="margin-left: 100px;list-style: none;display: inline-block;cursor: pointer;"><div style="width: 48px;height: 48px;background-color: #eceff1;border-radius:50%;color: #039be5;text-align: center;line-height: 48px;">&bull;&bull;&bull;</div><br><div style="text-align: center;width: 48px;">����</div></li>
			</ul>
		</div>
		<div class="center">
			<div class="content clearfix">
				<div class="left">
					<div class="part1">
						<div class="title">��СӪ���ҵ���</div>
						<#if indexLabels??>
							<#if (indexLabels?size > 0)>
								<ul class="clearfix">
									<#list indexLabels as indexLabel>
										
										<li  style="    list-style: none;"><a href="/post/all/${indeLabel.label.name!''}">${indeLabel.label.name!''}</a></li>
									</#list>
									<li  style="list-style: none;" ><a href="/label/list" target="_blank" ><span class="c22">����</span></a></li>
								</ul>
							</#if>
						</#if>
						<#if newIndexLabels??>
							<#if (newIndexLabels?size > 0)>
								<ul class="clearfix">
									<#list newIndexLabels as label>
										<li  style="    list-style: none;"><a style="    color: #000;" href="/post/all/${label.name!''}">${label.name!''}</a></li>
									</#list>
									<li  style="    list-style: none;"><a href="/label/list" target="_blank"><span class="c22">����</span></a></li>
								</ul>
							</#if>
						</#if>
					</div>
					<div class="part2 relative">
						<div class="title clearfix" id="group">
							<div class="fl">
								<i class="recommend"></i>
								<input type="hidden" id="gid"/>
								<p class="f21" id="gname"></p>
								<p class="f16" id="gdesc"></p>
							</div>
							<div class="fr" id="addgroup"></div>
						</div>
						
						<#if focusTopics?? >
						<#if (focusTopics?size > 0) >
							<ul>
								<#list focusTopics as topic>
									<li style="list-style:none;">
										<div class="f16 clearfix">
											<#if !topic.user.image??>
											<#if  topic.user.sex=='boy'>
												<a href="/user/other/post/${topic.user.id}" target="_blank" ><i class="f21 fl" style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											<#if  topic.user.sex!='boy'>
												<a href="/user/other/post/${topic.user.id}" target="_blank" ><i class="f21 fl" style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											</#if>
											<#if topic.user.image??>
												<a href="/user/other/post/${topic.user.id}" target="_blank" ><i class="f21 fl" style="background: url(${staticUrl!''}${topic.user.image}) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											<p class="fl"><a href="/user/other/post/${topic.user.id}" target="_blank" ><span class="author c44">${topic.user.user}</span></a>&nbsp;&nbsp;&nbsp;	<#if topic.group ??>����&nbsp;&nbsp;&nbsp;</#if>
											<span class="classify c44">
												<#if topic.group ??>
													<a href="/group/detail/${topic.group.id}" target="_blank" >${topic.group.name!''}</a>
												</#if>
											</span>
											<br />${topic.showData} �� ${topic.hits}���Ķ�</p>
										</div>
										<div class="articleName"  style="padding-bottom:20px;font-weight: bolder;"><a href="/post/${topic.id}" target="_blank" >${topic.title}</a></div>
										<div class="articleCon">${topic.simpleContent}</div>
									</li>
								</#list>
							<li class="last" style="list-style:none;"><a style="background-color:#c9c9c9;" href="/post/all" target="_blank" >����</a></li>
							</ul>
						</#if>
						</#if>
						
						<#if indexTopics?? >
						<#if (indexTopics?size > 0) >
							<ul>
								<#list indexTopics as indexTopic>
									<li style="list-style:none;">
										<div class="f16 clearfix">
											<#if !indexTopic.topic.user.image??>
											<#if  indexTopic.topic.user.sex=='boy'>
												<a href="/user/other/post/${indexTopic.topic.user.id}" target="_blank" ><i class="f21 fl" style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											<#if  indexTopic.topic.user.sex!='boy'>
												<a href="/user/other/post/${indexTopic.topic.user.id}" target="_blank" ><i class="f21 fl" style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											</#if>
											<#if indexTopic.topic.user.image??>
												<a href="/user/other/post/${indexTopic.topic.user.id}" target="_blank" ><i class="f21 fl" style="background: url(${staticUrl!''}${indexTopic.topic.user.image}) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											<p class="fl"><a href="/user/other/post/${indexTopic.topic.user.id}" target="_blank" ><span class="author c44">${indexTopic.topic.user.user}</span></a>&nbsp;&nbsp;&nbsp;	<#if indexTopic.topic.group ??>����&nbsp;&nbsp;&nbsp;</#if>
											<span class="classify c44">
												<#if indexTopic.topic.group ??>
													<a href="/group/detail/${indexTopic.topic.group.id}" target="_blank" >${indexTopic.topic.group.name!''}</a>
												</#if>
											</span>
											<br />${indexTopic.topic.showData} �� ${indexTopic.topic.hits}���Ķ�</p>
										</div>
										<div class="articleName"  style="padding-bottom:20px;font-weight: bolder;"><a href="/post/${indexTopic.topic.id}" target="_blank" >${indexTopic.topic.title}</a></div>
										${indexTopic.topic.simpleContent!""}
										<#if indexTopic.topic.image??>
											<img class="imageTitle" src="${staticUrl!''}${indexTopic.topic.image}" alt=""  max-width="791"/>
										</#if>
										<div class="articleCon">${indexTopic.description}</div>
									</li>
								</#list>
							<li class="last" style="list-style:none;"><a style="background-color:#c9c9c9;" href="/post/all" target="_blank" >����</a></li>
							</ul>
						</#if>
						</#if>
						
						<#if newIndexTopics??>
						<#if (newIndexTopics?size > 0)>
							<ul>
								<#list newIndexTopics as topic>
									<li>
										<div class="f16 clearfix">
										<#if !topic.user.image??>
											<#if  topic.user.sex=='boy'>
												<a href="/user/other/post/${topic.user.id}" target="_blank" ><i class="f21 fl" style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;" ></i></a>
											
											</#if>
											<#if  topic.user.sex!='boy'>
												<a href="/user/other/post/${topic.user.id}" target="_blank" ><i class="f21 fl" style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											</#if>
											<#if topic.user.image??>
												<a href="/user/other/post/${topic.user.id}" target="_blank" ><i class="f21 fl" style="background: url(${staticUrl!''}${topic.user.image}) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											<p class="fl"><a href="/user/other/post/${topic.user.id}" target="_blank" ><span class="author c44" >${topic.user.user}</span></a>&nbsp;&nbsp;&nbsp;<#if topic.group ??>����</#if>&nbsp;&nbsp;&nbsp;
											<span class="classify c44">
												<#if topic.group ??>
													<a href="/group/detail/${topic.group.id}" target="_blank" >${topic.group.name!''}</a>
												</#if>
											</span>
											<br />${topic.showData} �� ${topic.hits}���Ķ�</p>
										</div>
										<div class="articleName"><a href="/post/${topic.id}" target="_blank" >${topic.title}</a></div>
										${topic.simpleContent!""}
										<#if topic.image??>
											
											<img class="imageTitle" src="${staticUrl}${topic.image}" alt=""  max-width="791"/>
										</#if>
									</li>
								</#list>
								
								
										<li class="last" style="list-style:none;"><a style="background-color:#c9c9c9;" href="/post/all" target="_blank" >����</a></li>
							</ul>
						</#if>
						</#if>
					</div>
					<#if indexBooks?? > 
						<#if (indexBooks?size > 0)>
							<div class="part1">
								<div class="title">ͼ���Ƽ�</div>
							</div>
						</#if>
					<#if (indexBooks?size > 0)>
						<div class="part3">						
							<ul class="clearfix">
								<#list indexBooks as indexBook>
									<li><a href="/book/detail/${indexBook.id}" target="_blank" ><img style="    max-width: 240px;height: 310px;" src="${staticUrl}${indexBook.image}" alt="" width="240"/></a></li>
								</#list>				
							</ul>
							<a href="/book/list" target="_blank" >ȫ��ͼ���Ƽ�</a>
						</div>
					</#if>
					</#if>
				</div>	
				<div class="right" style="width:386px">
					<dl class="topic1">
						<dt>���죬�㳶�����</dt>
						<dd style="    margin-left: 0px;">��������Լ���Ӫ�ط���ְ�����顢���뾫�񡢻�
���ֺ����������...</dd>
						<dd><a href="/topic/addTopic" style="TEXT-DECORATION:none;color:#FFF;" target="_blank" >ȥ�ҳ�</a></dd>
	<div id="container" style="overflow:hidden;">
					    <div id="t_sina">
						    <h2>�������˵</h2>
						    
						    <div id="content2" style="height: 528px;overflow-y:visible;">
						        <ul style="padding-left: 0px;" id="newList">
						        <!--
						            <li>
						                <div class="pic"><a href="http://www.17sucai.com/preview/11/2015-03-18/%E5%BE%AE%E5%8D%9A%E5%8F%91%E5%B8%83/index.html###"></a></div>
						                <p><a href="http://www.17sucai.com/preview/11/2015-03-18/%E5%BE%AE%E5%8D%9A%E5%8F%91%E5%B8%83/index.html#">ҹ������</a>������,����һ��������ĵ����,̽�յƼ���ġ�������������ʧ�ˣ�һ�����ڽ�������Ҳ��������<br><span>3����ǰ</span></p>
						            </li>
						            <li>
						                <div class="pic"><a href="http://www.17sucai.com/preview/11/2015-03-18/%E5%BE%AE%E5%8D%9A%E5%8F%91%E5%B8%83/index.html###"></a></div>
						                <p><a href="http://www.17sucai.com/preview/11/2015-03-18/%E5%BE%AE%E5%8D%9A%E5%8F%91%E5%B8%83/index.html#">xinshangzeyu_2xr</a>�����������أ�ȥ���أ�������ԭ��������������������Ϊ��������ʮ�ꡣ����<br><span>3����ǰ</span></p>
						            </li>
						            <li>
						                <div class="pic"><a href="http://www.17sucai.com/preview/11/2015-03-18/%E5%BE%AE%E5%8D%9A%E5%8F%91%E5%B8%83/index.html###"></a></div>
						                <p><a href="http://www.17sucai.com/preview/11/2015-03-18/%E5%BE%AE%E5%8D%9A%E5%8F%91%E5%B8%83/index.html#">���ֵ���</a>����������������ô��ֵ���ư�ɾ�Ȼ��������ô������״�������Ե绰�����ˣ�ȫ���澯�����鲻�����ϣ��ðɣ�����ôһֱû�绰�ɣ��氲����������<br><span>3����ǰ</span></p>
						            </li>
						            <li>
						                <div class="pic"><a href="http://www.17sucai.com/preview/11/2015-03-18/%E5%BE%AE%E5%8D%9A%E5%8F%91%E5%B8%83/index.html###"></a></div>
						                <p><a href="http://www.17sucai.com/preview/11/2015-03-18/%E5%BE%AE%E5%8D%9A%E5%8F%91%E5%B8%83/index.html#">б��ϸ��ʱ˼������</a>��ԭ��λ�쵼��Ϊ��������ʱ����Ȼð����������ĸ���־䣬Ϊ�˸�����Щ����ۡ���ʼ����Ϊ��ͬѧ���̺ܸߣ�ϣ�������԰�ԭ��λ����~��Ӧ��Ҳ����ã�ֻ��ϣ������ʱ�����������������Ӧ���õ��ˣ�Ī��Ū������˫��~3����ǰ<br><span>3����ǰ</span></p>
						            </li>
						            <li>
						                <div class="pic"><a href="http://www.17sucai.com/preview/11/2015-03-18/%E5%BE%AE%E5%8D%9A%E5%8F%91%E5%B8%83/index.html###"></a></div>
						                <p><a href="http://www.17sucai.com/preview/11/2015-03-18/%E5%BE%AE%E5%8D%9A%E5%8F%91%E5%B8%83/index.html#">ճ�ò�����</a>����ѧ�𻹲��������졣���ҵĺ����˵�<br><span>3����ǰ</span></p>
						            </li>
						            -->
						        </ul>
						    </div>
						    
						    <dl>
						    	<dt style="margin: 0px;">-������̬-</dt>
						   <!--     <dd style="margin-left: 0px;font-size: 12px;">������<input id="btn_name" class="text" type="text"></dd>-->
						        <dd style="margin-left: 0px;">���ݣ�<textarea id="btn_msg"></textarea></dd>
						        <dd style="margin-left: 0px;font-size: 12px;color: #666;"><input class="btn" id="btn_submit" type="button" value="�ύ"></dd>
						    	<dd style="display: none;" id="userId">${userId!""}</dd>
						    </dl>
						
						</div>
						
						<ul id="tmp_container" style="height:0px; overflow:hidden;"></ul>
					</div>
					</dl>
		<#if labels??>
							<ul class="boxList clearfix">
							<#list labels as label>
								<li style="list-style: none;">
									<a href="/post/all/${label.name}" target="_blank" >${label.name!''}</a>
								</li>
							</#list>
							</ul>
					</#if>
					<#if topics??>
						<#if topics?size!=0 >
						<dl class="newSoftware">
							<dt>�������</dt>
							<#list topics as topic>
								<dd class="clearfix"  style="margin-left:0px;">
									<#if topic.image??>
										<img src="${staticUrl}${topic.image!''}" alt="" width="49" class="fl"/>
									<#else>
										<img src="/web/images/ai.jpg" alt="" width="49" class="fl"/>
									</#if>
									<p class="fl" style="width: 267px;white-space: nowrap;text-overflow:ellipsis;overflow: hidden;"><a href="/post/${topic.id}" target="_blank" ><span>${topic.title!''}</span></a><br />
									${topic.extLabels!''}</p>
								</dd>
							</#list>
						</dl>
						</#if>
					</#if>
					<#if (questions??)>
						<#if questions?size!=0 >
							<dl class="QAlist">
								<dt>�����ʴ�</dt>
								<#list questions as question>
									<dd class="clearfix" style="margin-left:0px;">
										<i class="fl">ǰ</i>
										<p class="fl" style="margin-top: 0px;">
											<span class="question">
												<a href="/post/${question.id}" class="question" target="_blank" >${question.title}</a>
											</span>
											<span class="answer">
												<#if question.answerState?? && question.answerState == 'HaveAnser'>
													С�����������.��������ѽ����...
												<#else>
													 ����С����ǽ��...
												</#if>
											</span>
										</p>
									</dd>
								</#list>
							</dl>
						</#if>
					</#if>
					
				</div>
			</div>
		</div>	
		<div class="bottom"  style="display:none;" >
			<div class="content relative">
				<img width="1282" src="/web/images/bottom.jpg" style="margin-bottom:30px;">
				<form action="" method="post">
					<div><label for="name">����</label><input type="text" id="email" onkeypress="if(event.keyCode==13) {btn.click();return false;}"></div>
					<div><label for="name">����</label><input type="password" id="password" onkeypress="if(event.keyCode==13) {btn.click();return false;}"></div>				
					<input style="margin-left:73px;" id="btn" name="btn" type="button" onclick="login()" value="��½"/><br/><a  style="margin-left:210px;"  href="">û���˺ţ�ע��</a>
				</form>
			</div>
		</div>
	</body>
	
	<script>
	
	var flag=false;
	function addGroup(){
	var groupid = $("#gid").val();
	$.get("/group/addGroup",{groupid:groupid},function(data){
	
	    if(data.status){
	       alert(data.msg);
	    }else{
	     alert(data.msg);
	     if(data.msg=="�û�δ��¼"){
	       window.location.href=  "/user/login";
	       return;
	     }
	    }
	     window.location.reload();
	},"json");
	
	}

	function look(id){
	////console.log($("#"+id).val())
		window.open ($("#"+id).val(),'newwindow','height=600,width=800,top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no') 
	}
	
	 $(document).ready(function() {  
       
             
             //����group
             $.ajax({
             	url:'/group/getRecommendGroup',
             	type:'get',
             	dataType:'json',
             	success : function(msg){
             		if(msg.status && msg.data){
             			var obj = msg.data;
             			$('#gid').val(obj.id);
             			$('#gname').text(obj.name);
             			if(obj.description|"" !=""){
             			$('#gdesc').text(obj.description);
             			}
             			$("#addgroup").html('<a href="javaScript:void(0)" onclick="addGroup()" class="f16">����Ӫ��</a>');
             		}else{
             			$('#group').css('display','none');
             		}
             	}
             })
     }); 
     
     function checkUser(){
     
     $.ajax({
				     type: 'POST',
				     url: "/user/statusUser",
				     dataType: "html",
				     success: function(data){
				    	if(data == "false"){
				    		window.location.href="/user/login"
				    	}
				    } 
				});
     }

		//��������
		function submitForm(){	
			// alert(marked($("textarea[id='content']").val()))
			var json = {};
			if(document.getElementById("content").value==""){
				alert("д�㶫����?");
				return false;
			}
				
			json['content'] = document.getElementById("content").value;
			if($("#title").val()==""){
				alert("���ⲻ��Ϊ��");
				return false;
			}
			json['title']=$("#title").val();
			if($("#mainname").val()==""){
				alert("����ǩ����Ϊ��");
				return false;
			}
			json['label.name'] =$("#mainname").val();
			json['extLabels'] = $("#extLabels").val();
			json['categroyName'] = $("#categroyName").val();
			console.log(json['content']);	 
			
				//�ύ
				$.ajax({
				     type: 'POST',
				     url: "/topic/add",
				     data: json ,
				     dataType: "json",
				     success: function(data){
				    	if(data.status){
				    		alert("��ӳɹ�");
				    		//�Զ��ر�
				    		$("#topicform")[0].reset();
				    		$("#answerTopic").trigger('reveal:close');
				    		window.location.reload();//ˢ��
				    	}else{
				    		alert(data.msg)
				    	}
				    } 
				});
		}
	
	//����
	function search(){
		var v = $.trim($('#search').val());
		if(v == ''){
			alert('�������ݲ���Ϊ��');
			return;
		}
		
		
		
		window.location.href='/topic/result?search='+ v;
	}
	
	//��½
	function login(){
					
					$.ajax({

				     type: 'POST',
				
				     url: "/user/loginAction" ,
				
				    data: {email:$("#email").val(),password:$("#password").val()} ,
				
				    success: function (data){
				    	////console.log(data)
				    	
				    	if(data.status){
				    		window.location.href="/index.html"
				    	}else	if(!data.status&&data.msg=="�û�δ����"){
				    		 alert("�û�δ����Ѿ������ʼ������䣬����");
				    	}else if(!data.status&&data.msg=="�û����������"){
				    		 alert("�û������������");
				    		// window.location.href="/user/password/forget"
				    	}else if(!data.status&&data.msg=="û�и��û�"){
				    		 alert("û�и��û�,�뷢���ʼ�ȥquestion@corer.me");
				    	}else{
				    		alert(data.msg);
				    	}
				    	
				    } ,
				
				    dataType: "json"
				
				});
			}
			
		document.getElementById("search").onkeypress = function(e){
    		checkChar(e);
		}
	
	//�²�ѯ
	function newsearch(input){
		var v = $(input).val();
		if(v == ''){
			return;
		}
		
		$.ajax({
			url:'/search',
			type:'post',
			data:{
				search : v
			},
			dataType:'json',
			success:function(msg){
				var note = $('#noteCookie');
				var user = $('#userCookie');
				note.empty();
				user.empty();
			
				if(msg.status){
					var data = msg.data;
					
					var topics = data.topics;
					var users = data.users;
					
					if(topics && topics.length > 0){
						$("#notetitle").css('display','block');
						
						for(var t in topics){
							if(t >= 5){
								break;
							}
							var li = $('<li style="list-style: none;font: 13px Roboto,sans-serif;line-height: 30px;cursor: pointer;"></li>');
							li.html(topics[t].title);
							note.append(li);
							
							li.click(function(){
								window.location.href = "/post/"+topics[t].id;
							});
						}
					}else{
						$("#notetitle").css('display','none');
					}
					
					if(users && users.length > 0){
						$("#usertitle").css('display','block');
						
						for(var u in users){
							if(u >= 5){
								break;
							}
							var li = $('<li style="list-style: none;font: 13px Roboto,sans-serif;line-height: 30px;cursor: pointer;"></li>');
							li.click(function(){
								window.location.href = "/user/other/post/"+users[u].id;
							});
							
							li.text(users[u].user);
							user.append(li);
						}
					}else{
						$("#usertitle").css('display','none');
					}
					
					var li = $('<li style="list-style: none;color: blue;font-family: Roboto,sans-serif;cursor: pointer;"></li>');
					li.html('�鿴��<span style="font-size: 14px;font-weight: bolder;">'+ v +'</span>����������������');
					li.click(function(){
						window.location.href = "/topic/result?search="+v;
					})
					$("#more").empty();
					$("#more").append(li);
					
				}
			},
			error:function(){
				alert('����');
			}
		});
	}
 
function checkChar(e){
    e = window.event || e;
    var code = e.keyCode || e.which;
  //  var reg = /[\\\/:\*\?"\<\>\|]/;// ��ֹ \/:*?"<>|
    var reg = new RegExp("[~'!@#$%^&*()-+_=:]");
    if(reg.test(String.fromCharCode(code))){
        if(window.event){
            e.returnValue = false;
        }else{
            e.preventDefault();
        }
    }
}
	</script>

</html>
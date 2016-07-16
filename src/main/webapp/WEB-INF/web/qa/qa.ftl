
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<meta charset="utf-8">
		<title>��������</title>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<link rel="stylesheet" type="text/css" href="/web/css/style.css">
		<script type="text/javascript" src="/js/jquery.min.js"></script>
		<script type="text/javascript" src="/js/publicMethod.js"></script>
	</head>


<style>
.part2 .articleName2{    color: #000;
    font-size: 19px;
    margin-bottom: 40px;}
    
 .articleName1{    color: #000;
    font-size: 24px;
    margin-bottom: 40px;}
</style>
	<body>
		<div class="QA">
			<div class="content relative">
				<nav>
					<ul class="clearfix" >
						<li   style="line-height: normal;margin-top:0px;list-style: none;" class="fr"><a id="userImg" href="/user/my/post">�ҵ�</a></li>
						<li   style="line-height: normal;margin-top:0px;list-style: none;" class="fr"><a id="UnreadMessageCount" href="/user/message">��Ϣ</a></li>
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
						<div class="title">�����Ҵ�</div>
						<ul class="clearfix" id="randomLabels">
							
						</ul>
					</div>
					<div class="part2">
						<ul id="topicContent">
						</ul>
					</div>
				</div>	
				<div class="right">
					<div class="topic2" style="display:none;">
						<p>��Ҫ����</p>
						<div class="relative">
							<i class="abs"></i>
							<input type="text" name="" id="" value="" placeholder="����..."/>
						</div>
					</div>
					<#if labels??>
						<#if (labels?size > 0)>
							<h3>���ű�ǩ</h3>
						</#if>
							<ul class="boxList clearfix">
								<#list labels as label>
									<li   style="    list-style: none;"><a href="/post/all/${label.name}">${label.name!''}</a></li>
								</#list>
							</ul>
					</#if>
					<#if topics??>
						<#if topics?size!=0 >
						<dl class="newSoftware">
							<dt>�������</dt>
							<#list topics as topic>
								<dd class="clearfix" style="margin-left:0px;">
									<#if topic.image??>
										<img src="${static}${topic.image!''}" alt=""  width="49" class="fl"/>
									<#else>
										<img src="/web/images/ai.jpg" alt="" width="49" class="fl"/>
									</#if>
									<p class="fl" style="width: 267px;white-space: nowrap;text-overflow:ellipsis;overflow: hidden;">
									<a href="/post/${topic.id}"><span>${topic.title!''}</span></a><br />
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
										<#if !question.user.image??>
											<#if  question.user.sex=='boy'>
												<a href="/user/other/post/${question.user.id}"><i class="f21 fl" style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											<#if  question.user.sex!='boy'>
												<a href="/question/other/post/${topic.user.id}"><i class="f21 fl" style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
											</#if>
											<#if question.user.image??>
												<a href="/user/other/post/${question.user.id}"><i class="f21 fl" style="background: url(${static}${question.user.image}) no-repeat 0 center;background-size: 100%;" ></i>
												</a>
											</#if>
										<p class="fl" style="margin-top:0px;"><a href="/post/${question.id}"><span class="question">${question.title!''}<br></span></a>
										<span class="answer">
											<#if question.answerState?? && question.answerState == 'HaveAnser'>
												С�����������.��������ѽ����...
											<#else>
												 ����С����ǽ��...
											</#if>
										</span></p>
									</dd>
								</#list>
							</dl>
						</#if>
					</#if>
				</div>
			</div>
		</div>
	</body>
<script type="text/javascript">
	var currentPage = 1; //
	var count = 10;      //
	var randomLabelCount = 7;
	var staticUrl = '${static}';
	var labelid = '${labelid!''}';
	var lablename = '${labelname!''}';
	
	$(function(){
		if(labelid != '' && lablename != ''){
			getTopicByLabel(labelid,lablename);
		}else{
			//Ĭ�ϼ���
			getRandomLabelList(randomLabelCount);
		
			//��������
			getTopicList(currentPage,count);
		}
	});

	
	//���ݱ�ǩ��ȡtopic
	function getTopicByLabel(labelId,name){
		var label = {};
		if($.publicMethod.hasValue(labelId)){
			label.id  = labelId;
			label.name = name;
		}
		
		//��ȡ�����ǩ
		getRandomLabelList(randomLabelCount,label);
	
		//ˢ��topic
		currentPage = 1;
		clearTopicList();
		getTopicList(currentPage,count,labelId);
	}
	
		
	//��ȡ����
	function getTopicList(pageNum,count,labelId){
			if(!labelId){
				labelId = '';
			}
			
			$.ajax({
				url:'/post/page',
				type:'get',
				data:{pageNum:currentPage,count:count,labelId:labelId},
				dataType:'json',
				success:function(msg){
					if(!msg.status){
						alert('��ȡ����');
						return;
					}
					
					//����
					var data = msg.data;
					$('.last').css('display','none');
					
					if(data.length > 0){
						currentPage++;
						for(var i=0 ; i < data.length;i++){
							var obj = data[i];
							var str = '<div class="f16 clearfix">';
							
								var image = obj.user.image;
								if(image==null||image ==""){
									if(obj.user.sex =='boy'){
										image = ' style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;"'
									}else{
										image = ' style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;"'
									}
								}else{
									image = ' style="background: url(${static}'+obj.user.image+') no-repeat 0 center;background-size: 100%;"'
								}
							
								str +=	'<a href="/user/other/post/'+ obj.user.id +'"><i '+image+' class="f21 fl"></i></a>';
								str +=	'<p class="fl"><span class="author c44"><a href="/user/other/post/'+ obj.user.id +'" target="_blank" class="author c44">'+ obj.user.user+'</a></span>&nbsp;&nbsp;&nbsp;����&nbsp;&nbsp;&nbsp;';
								
								str +=	'<span class="classify c44">';
								
								if(obj.group != null){
									str += '<a href="/group/detail/'+ obj.group.id +'" target="_blank" class="classify c44">'+obj.group.name + '</a>';
								}
								
								str +=	'</span><br />'+obj.showData+' �� '+obj.hits+'���Ķ�</p></div>';
								str += '<div><a href="/post/'+ obj.id +'" target="_blank" class="articleName2"><span style="font-size:24px;">'+obj.title+'</span></div>';
								
							if(obj.image != null){
								str += '<img class="imageTitle"  style="display:none" src="' +staticUrl + obj.image+'" alt="" width="791"/>';
							}
							
							var simple = obj.simpleContent == null ? '' : obj.simpleContent;
							str += '<div class="articleCon" >'+ simple + '</div>';
							var li = $('<li></li>');
							li.html(str);
							$('#topicContent').append(li);
						}
						
						if(data.length == count){
							//�������
							var moe = $('<li style="list-style:none;"></li>');
							moe.html('<li style="list-style:none;" class="last"><a style="background-color:#c9c9c9;" href="javascript:void(0)" onclick="getTopicList(\''+currentPage+'\',\''+count+'\',\''+labelId+'\')">����</a></li>');
							$('#topicContent').append(moe);
						}
					}
				},
				error:function(){
					alert('��ѯ����');
				}
			});
	}
	
	//���topic
	function clearTopicList(){
		if($("#topicContent li").length>0){
			$("#topicContent").empty();
		}
	}
	
	//��ȡ�����ǩ
	function getRandomLabelList(num,selectLabel){
		if(!$.publicMethod.hasValue(num)){
			num = 10;
		}
		$.ajax({
		   type: "GET",
		   url: "/label/randomLabelList",
		   data:{
			   num:num
		   },
		   dataType : 'json',
		   success: function(msg){
		       if (msg.status) {
			       if($.publicMethod.hasValue(msg.list)){
				       var labelListJQ = [];
				       if($.publicMethod.hasValue(selectLabel)){
				    	   labelListJQ.push('<li   style="    list-style: none;"><a href="javascript:getTopicByLabel(\''+selectLabel.id+'\',\''+selectLabel.name+'\');"><span class="c22">'+selectLabel.name+'</span></a></li>');
				       }
				       for(var i=0 ;i<msg.list.length;i++){
				    	   if($.publicMethod.hasValue(selectLabel) && selectLabel.id == msg.list[i].id){
				    		   continue;
				    	   }else{
				    		   labelListJQ.push('<li   style="    list-style: none;"><a href="javascript:getTopicByLabel(\''+msg.list[i].id+'\',\''+msg.list[i].name+'\');">'+msg.list[i].name+'</a></li>');
				    	   }
				       }
				       labelListJQ.push('<li   style="    list-style: none;"><a href="javascript:getTopicByLabel(\'\',\'\');"><span class="c22">��һ����ǩ</span></a></li>');
				       $("#randomLabels").empty().append(labelListJQ.join(""));
			       }
		       }else{
		           alert("��ȡ�б����!"+ msg.msg);
		       }
		   }
		});
	}
</script>
</html>
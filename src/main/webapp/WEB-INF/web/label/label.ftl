
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta charset="utf-8">
		<title></title>
		<meta name="keywords" content="">
		<meta name="description" content="">
		<meta http-equiv="X-UA-Compatible" content="IE=EDGE,chrome=1">
		<link rel="stylesheet" type="text/css" href="/web/css/style.css">
		<link rel="stylesheet" type="text/css" href="/web/css/reveal.css" />
		<script type="text/javascript" src="/js/jquery.min.js"></script>
		<script type="text/javascript" src="/web/js/tipso.js"></script>
		<script type="text/javascript" src="/web/js/jquery.reveal.js"></script>
		<script type="text/javascript" src="/js/publicMethod.js"></script>
	</head>
<style>


</style>
	<body  style="    overflow-x: hidden;">
		<div class="label">
	<div class="content relative" style="margin:0 auto;">
				<nav>
					<ul class="clearfix" style="    list-style: none;" >
						<li class="fr"  style="line-height: normal;margin-top:0px;"><a id="userImg" href="/user/my/post">�ҵ�</a></li>
						<li class="fr"  style="line-height: normal;margin-top:0px;"><a id="UnreadMessageCount" href="/user/message">��Ϣ</a></li>
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
					<dl class="labelPart" style="border-bottom-color: white;">
						<dt>Ӫ�ر�ǩ</dt>
						<dd class="partContent"  style="    margin-left: 0px;">
							<ul class="clearfix" >
								<div id="ulcontent"></div>
								<div id="more"></div>
							</ul>
						</dd>						
					</dl>
				
				</div>	
				<div class="right" style="    width: 386px;">
					<div class="topic2">
						<p>������ǩ</p>
						<div class="relative">
							<i class="abs"></i>
							<input type="text" name="" id="input" value="" placeholder="����..."/>
						</div>
					</div>
					<h3 id="result"></h3>
					<ul class="labelList clearfix" id="search">
						
					</ul>
					<dl class="newSoftware">
						<dt>�������</dt>
						<#if (adTopics?size>0)>   
							<#list adTopics as ad>
								<dd class="clearfix" style="margin-left:0px;">
									<#if ad.image??>
										<img src="${staticUrl}${ad.image}" alt="" width="49" class="fl"/>
									<#else>
										<img src="/web/images/ai.jpg" alt="" width="49" class="fl"/>
									</#if>
									<p class="fl" style="width: 267px;white-space: nowrap;text-overflow:ellipsis;overflow: hidden;"><a href="/post/${ad.id}"><span>${ad.title!""}</span></a><br />
									${ad.extLabels!''}</p>
								</dd>
							</#list>
						</#if>
						<!-- <dd class="clearfix">
							
							<p class="fl"><span>Photoshop</span> <br />
							��������2016CC���Ȱ棡</p>
						</dd> -->
						
					</dl>
				</div>
			</div>
		</div>
<script>
$(function(){
page(1);
});
function pop(obj,foucsPerson){
		var attrstatus = $(obj).attr("attrstatus")
		//console.log(attrstatus)
		var content = "";
		var domethod = ""
		if(attrstatus=="δ��ע"){
			content = "δ��ע"
		}else{
			content = "�ѹ�ע"
			
		}
	
		//console.log($(obj).attr("id"))
		domethod = "doTag("+$(obj).attr("attrid")+")"
			//console.log(content)
		$(obj).tipso({
			useTitle: false,
			content: '��ע' + foucsPerson,
			tooltipHover:true,
			test: content,
			url:domethod
		});
}

function  doTag(labelid){
	 $.get("/focus/dofocus",{"label":labelid},function(data){
	 
	 	if(data.msg =="�û�δ��¼"){
	 		alert("�û�δ��¼")
	 			window.location.href="/user/login?redirect="+window.location.href
	 	}else{
	 		alert(data.msg)
	 		window.location.reload()
	 	}
	 },"json");

}

function page(obj){
var next = obj+1;
var count=201;
$.get("/label/labelList",{"pageNum":obj,"count":count},function(data){
if(data.status){
var result=data.data;
var str = $("#ulcontent").html();;
for(var i=0;i<result.length-1;i++){
str+='<li  style="    list-style: none;"  id="list'+result[i].id+'" onmouseover="pop(this,'+result[i].foucsPerson+')" attrid="'+result[i].id+'" attrstatus="'+result[i].showData+'"><a href="/post/all/'+result[i].name+'">'+result[i].name+'</a></li>';
}
var str1="";
if(result.length >= 201){
  str1+='<li><a href="javascript:void(0)" onclick="page('+next+')"><span class="c22">�������</span></a></li>';
}

$("#ulcontent").html(str);
$("#more").html(str1);

}else{
alert(data.msg);
}

},"json");
}


//����
$("#input").blur(function(){
  $("#search").html("");
  var value=$("#input").val();
  value = $.trim(value);
  if (value == "") {
  	return;
  }
  $.get("/label/searchByName",{"name":value},function(data){
     if(data.status){
     if(data.data.length>0){
     
        var results = data.data;
        var str ="";
        for(var i=0;i< results.length;i++){
        //console.log(results[i].name)
          str+='<li  style="    list-style: none;"  id="list'+results[i].id+'" onmouseover="pop(this,'+results[i].foucsPerson+')" attrid="'+results[i].id+'" attrstatus="'+results[i].showData+'"><a href="/post/all/'+results[i].name+'">'+results[i].name+'</a></li>';
        }
        
        $("#result").html("<span>�������</span>");
        $("#search").html(str);
        }else{
         $("#result").html("<span>�������</span>");
         $("#search").html("û�����ƵĽ��");
        }
        
     }else{
        alert(data.msg);
     }
  },"json");

});


document.getElementById("input").onkeypress = function(e){
    checkChar(e);
}
 
function checkChar(e){
    e = window.event || e;
    var code = e.keyCode || e.which;
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
		
	</body>
</html>
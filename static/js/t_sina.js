var g_iLastID=0;

var publicSignId;

window.onload=function ()
{
	var oBtnSubmit=document.getElementById('btn_submit');
	
	oBtnSubmit.onclick=postMessage;
	
	getNewMessage();
	setInterval(getNewMessage, 5000);
};

function postMessage() {
	var oTxtMsg=document.getElementById('btn_msg');
	var content = oTxtMsg.value;
	if(content.length==0)
	{
		alert('输入完整信息');
		return;
	}
	
	var userId = $("#userId").html();
	if (userId == "") {
		window.location.href = "/user/login";
		return;
	}
	if(content.length>200)
	{
		alert('输入内容需要小于200字');
		return;
	}
	
	$.get("/publicSign/add", {"content":content, "createUser.id":userId}, function(result) {
		if (result.status) {
			oTxtMsg.value = "";
			getNewMessage();
		}
		alert(result.msg);
	})
	
}

function getNewMessage() {
	$.get("/publicSign/getNewPublicSign", function(result) {
		if (result.status) {
			var id = result.list[0].id;
			if (publicSignId == id) {
				return;
			} else {
				publicSignId = id;
				$("#newList").empty();
			}
			$.each(result.list, function(i, data) {
				
				var image ="http://7xtex6.com2.z0.glb.clouddn.com/"+ data.createUser.image;
				if (data.createUser.image == null) {
					if (data.createUser.sex == "bog") {
						image = "/web/images/boy.jpg";
					} else {
						image = "/web/images/gril.jpg";
					}
				}
				//<img width="40" height="40" alt="用户头像" src="'+image+'" style="border-radius:50%;position: relative;left: 40px;top:-25px;">
				$("#newList").append('<li>'
						+'<div class="pic" style="    border-radius: 50%;background: url('+image+') no-repeat 0 center;background-size: 100%;" ><a href="/user/other/post/'+data.createUser.id+'"></a></div>'
						+'<p><a href="/user/other/post/'+data.createUser.id+'">'+data.createUser.user+'</a>：<span style="font-size: 18px;">'+data.content+'<br></span><span style="font-size: 15px;">'+data.showData+'</span></p>'
						+'</li>');
			})
		}
	})
}


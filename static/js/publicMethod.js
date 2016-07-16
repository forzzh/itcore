(function(){
    var bp = document.createElement('script');
    var curProtocol = window.location.protocol.split(':')[0];
    if (curProtocol === 'https') {
        bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';        
    }
    else {
        bp.src = 'http://push.zhanzhang.baidu.com/push.js';
    }
    var s = document.getElementsByTagName("script")[0];
    s.parentNode.insertBefore(bp, s);
})();

document.write('<link rel="shortcut icon" href="/web/images/logo.png" type="image/x-icon" />')
//通用Js方法
$.publicMethod = {
	hasValue:function (anyType, isIncludeString) {
        var Is = false;
        if (anyType != undefined && anyType != null) {
            switch (Object.prototype.toString.apply(anyType)) {
                case "[object String]":
                    anyType = $.publicMethod.trim(anyType.toLocaleLowerCase());
                    anyType = anyType.toString().replace(/^\s*|\s$/g, '');
                    Is = (anyType == "undefined" || anyType == "null" || anyType.length < 1);
                    break;
                case "[object Number]":
                    break;
                case "[object Boolean]":
                    break;
                case "[object Object]":
                    Is = $.isEmptyObject(anyType);
                    break;
                case "[object Array]":
                    Is = (anyType.length < 1);
                    break;
            };
        } else {
            Is = true;
        };
        return !Is;
    },
    trim:function(value){
        return value.toString().replace(/^\s*|\s$/g, '');
    },
    getStringLength:function(str) {
        if (!$.publicMethod.hasValue(str)) return 0;
        var bytesCount = 0;
        for (var i = 0; i < str.length; i++) {
            var c = str.charAt(i);
            if (/^[\u0000-\u00ff]$/.test(c))
            {
                bytesCount += 1;
            }
            else {
                bytesCount += 2;
            }
    	};
    	return bytesCount;
	},
	getAbsWebSitePath:function(){
	    var href = window.document.location.href;
	    var pathname =window.document.location.pathname;
	    var pos = href.indexOf(pathname);
	    var localhost = href.substring(0,pos);
	    return localhost;
	},
	redirectToLogin:function(referURL){
		if($.publicMethod.hasValue(referURL)){
			window.location.href = $.publicMethod.getAbsWebSitePath()+"/user/login?redirect=" + referURL;
		}else{
			window.location.href = $.publicMethod.getAbsWebSitePath()+"/user/login?redirect=" + window.location.href;
		}
	},
	getLocalTime:function(nS) {     
		 return new Date(parseInt(nS)).toLocaleString();
	},
	unreadMessageCount:function(){
		// 自动查询用户未读取信息数
		var $message = $("#UnreadMessageCount");
		var $userImg = $("#userImg");
		// 有消息栏则获取消息个数
		if($.publicMethod.hasValue($message)){
			$.ajax({
				url:"/msg/unreadMessageCount",
				type:"POST",
				dataType:'json',
				success:function(message){
					if(message.status && $.publicMethod.hasValue(message.data)){
						//有数据则显示个数
						$message.html("消息<span style='color:red'>("+message.data+")</span>");
						//"我的"右侧显示头像
						var image = message.user.image;
						if (image == null) {
							if (message.user.sex == "bog") {
								image = "/web/images/boy.jpg";
							} else {
								image = "/web/images/gril.jpg";
							}
						}
						$userImg.html('我的<img width="40" height="40" alt="用户头像" src="'+image+'" style="border-radius:50%;position: relative;left: 40px;top:-25px;">');
					}
				}
			});
		}
	}
};

//========================页面初始化========================
$(function(){
	if($.browser.msie && $.browser.version<11){
		alert("ie11以下浏览器不支持，请使用其它浏览器");
		window.close();
	}
	if($("#menuTop").length!=0){
		$("#menuTop li:eq(0)").bind("click",function(){
			window.location.href="/";
		});
		$("#menuTop li:eq(1)").bind("click",function(){
			window.location.href="/group/list";
		});
		$("#menuTop li:eq(2)").bind("click",function(){
			window.location.href="/label/list";
		});
		$("#menuTop li:eq(3)").bind("click",function(){
			window.location.href="/book/list";
		});
		$("#menuTop li:eq(4)").bind("click",function(){
			window.location.href="/post/all";
		});
	}
	//页面加载完成后启动，获得消息数
	$.publicMethod.unreadMessageCount();
})
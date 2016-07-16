var currentPage = 1; //书籍起始页
var count = 10;      //每页个数+1
var randomLabelCount = 7; //随机标签数
$(document).ready(function(){
	jQuery('#qrcodeTable').qrcode({
		render	: "table",
		text	: window.location.href+"/wap"
	});	
	//加载默认书籍列表
	getBookList(currentPage,count);
	//随机加载标签页
	getRandomLabelList(randomLabelCount);
});
function getBookByLabel(labelId,name){
	//更换一批标签，保留选中的标签
	var label = {};
	if($.publicMethod.hasValue(labelId)){
		label.id  = labelId;
		label.name = name;
	}
	getRandomLabelList(randomLabelCount,label);
	//根据标签查询  将初始页设为1
	currentPage = 1;
	clearBookList();
	getBookList(currentPage,count,labelId);
}
function clearBookList(){
	if($(".partContent li").length>0){
		$(".partContent").empty();
	}
}
function getBookList(pageNum,count,labelId){
	if (!$.publicMethod.hasValue(pageNum)) {
		alert("参数错误 pageNum" +pageNum);
		return false;
	};
	if (!$.publicMethod.hasValue(labelId)) {
		labelId = "";
	};
	$(".li-more").hide();
	$.ajax({
	   type: "GET",
	   url: "/book/queryBookList",
	   data:{
	   		pageNum:pageNum,
	   		count:count,
	   		labelId:labelId
	   },
	   success: function(msg){
	       if ($.publicMethod.hasValue(msg) && msg.status == true) {
	       		if($.publicMethod.hasValue(msg.list)){
	       			currentPage += 1;
	       			var replyJQ = [];
	       			for (var i = 0; i < msg.list.length; i++) {
	       				var book = msg.list[i];
	       				if(i<count-1){
	       					replyJQ.push('<li  style="    list-style: none;"><a href="/book/detail/'+book.id+'">');
	       					replyJQ.push('<img src="'+staticurl+book.image+'" alt="" width="240"/></a>');
	       					replyJQ.push('<p class="p-center" alt=\"'+book.name+'\">'+book.name+'</p><p class="p-center">'+book.author+'</p></li>');
	       				}else{
	       					replyJQ.push("<div class='li-more' style='clear:both;'</div>");
	       					replyJQ.push("<li  style='    list-style: none;' class='li-more'><span class='c22'><a href=\"javascript:getBookList(\'"+currentPage+"\',\'"+count+"\',\'"+labelId+"\');\">更多</a></span></li>")
	       				}
	       			};
	       			$(".partContent").show();
	       			$(".partContent").append(replyJQ.join(""));
	       			
	       		}else{
	       		}	
	       }else{
	           alert("请求错误：" + msg.msg);
	       }
	   }
	});
}
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
	   success: function(msg){
	       if (msg.status) {
		       if($.publicMethod.hasValue(msg.list)){
			       var labelListJQ = [];
			       if($.publicMethod.hasValue(selectLabel)){
			    	   labelListJQ.push('<li  style="    list-style: none;"><a href="javascript:getBookByLabel(\''+selectLabel.id+'\',\''+selectLabel.name+'\');"><span class="c22">'+selectLabel.name+'</span></a></li>');
			       }
			       for(var i=0 ;i<msg.list.length;i++){
			    	   if($.publicMethod.hasValue(selectLabel) && selectLabel.id == msg.list[i].id){
			    		   continue;
			    	   }else{
			    		   labelListJQ.push('<li  style="    list-style: none;"><a href="javascript:getBookByLabel(\''+msg.list[i].id+'\',\''+msg.list[i].name+'\');">'+msg.list[i].name+'</a></li>');
			    	   }
			       }
			       labelListJQ.push('<li  style="    list-style: none;"><a href="javascript:getBookByLabel(\'\',\'\');"><span class="c22">更换一批标签</span></a></li>');
			       $("#randomLabels").empty().append(labelListJQ.join(""));
		       }
	       }else{
	           alert("请求错误：" + msg.msg);
	       }
	   }
	});
}
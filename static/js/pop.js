$(function() {
	$('.myfocus li').each(function() {
		$(this).tipso({
			useTitle: false,
			content: '关注' + '   450',
			tooltipHover:true,
			test: '取消关注',
			//取消关注链接代码
			url: function(){
				$.ajax({
					type:"get",
					url:"",
					async:true
				});
			}
		});
	});
	$('.myCreate li').each(function() {
		$(this).tipso({
			useTitle: false,
			content: '关注' + '   450',
			tooltipHover:true,
			test: '删除标签',
			//删除标签链接代码
			url: function(){
				$.ajax({
					type:"get",
					url:"",
					async:true
				});
			}
		});
	});
	$('.myBookSelf li').each(function() {
		$(this).tipso({
			useTitle: false,
			content: '<a style="color:#448aca" href="javascript:;" onclick="'+buyBook()+'">购买图书</a>',
			tooltipHover:true,
			test: '移除书架',
			//移除书架链接代码
			url: function(){
				$.ajax({
					type:"get",
					url:"",
					async:true
				});
			}
		});
	});
	//购买图书链接代码
	function buyBook(){
		$.ajax({
			type:"get",
			url:"",
			async:true
		});
	}
});
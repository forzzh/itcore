$(function() {
	$('.myfocus li').each(function() {
		$(this).tipso({
			content: '关注' + '   450',
			test: '取消关注',
			//取消关注链接代码
			url: 'cancelFocus()'
		});
	});
	$('.myCreate li').each(function() {
		$(this).tipso({
			content: '关注' + '   450',
			test: '删除标签',
			//删除标签链接代码
			url: 'deleteLabel()'
		});
	});
	$('.myBookSelf li').each(function() {
		$(this).tipso({
			content: '<a style="color:#448aca" href="javascript:;" onclick="'+buyBook()+'">购买图书</a>',
			test: '移除书架',
			offsetY: 100,
			offsetX: 25,
			//移除书架链接代码
			url: 'removeShelf()'
		});
	});
	$('.boxList li').each(function(){
		$(this).tipso({
			width:360,
			titleContent:'<div><img src="images/QR.png" alt="" width="81" height="81"/><p><span class="campName">上海.Net聚集地</span><span class="campDetail">.net开发狗进这里，坐标上海 的开发狗更要进来～～</span></p></div>',
			content: '营地规模'+'ddd 人',
			test: '删除营地',
			//移除书架链接代码
			url: 'deleteCamp()'
		});
	});
});
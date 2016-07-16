
<#if show==true>
	<ul>
		<li><a href="/user/my/post">我的话题</a></li>
		<li><a href="/group/my">我的营地</a></li>
		<li><a href="/label/my">我的标签</a></li>
		<li><a href="/book/my">我的书架</a></li>
		<li><a href="/user/friend/">我的朋友</a></li>
		<li><a id="UnreadMessageCount" href="/user/message">我的消息</a></li>
	</ul>
</#if>
					
<#if show==false>
<ul>
	<li><a href="/user/other/post/${userid}"><#if show==true>我</#if><#if show!=true><#if userdata.sex=='boy'>他</#if><#if userdata.sex!='boy'>她</#if></#if>的话题</a></li>
	<li><a href="/group/other/${userid}"><#if show==true>我</#if><#if show!=true><#if userdata.sex=='boy'>他</#if><#if userdata.sex!='boy'>她</#if></#if>的营地</a></li>
	<li><a href="/label/other/${userid}"><#if show==true>我</#if><#if show!=true><#if userdata.sex=='boy'>他</#if><#if userdata.sex!='boy'>她</#if></#if>的标签</a></li>
	<li><a href="/book/other/${userid}"><#if show==true>我</#if><#if show!=true><#if userdata.sex=='boy'>他</#if><#if userdata.sex!='boy'>她</#if></#if>的书架</a></li>
	<li><a href="/user/other/friend/${userid}"><#if show==true>我</#if><#if show!=true><#if userdata.sex=='boy'>他</#if><#if userdata.sex!='boy'>她</#if></#if>的朋友</a></li>
	</ul>
</#if>
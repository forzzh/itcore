
<#if show==true>
	<ul>
		<li><a href="/user/my/post">�ҵĻ���</a></li>
		<li><a href="/group/my">�ҵ�Ӫ��</a></li>
		<li><a href="/label/my">�ҵı�ǩ</a></li>
		<li><a href="/book/my">�ҵ����</a></li>
		<li><a href="/user/friend/">�ҵ�����</a></li>
		<li><a id="UnreadMessageCount" href="/user/message">�ҵ���Ϣ</a></li>
	</ul>
</#if>
					
<#if show==false>
<ul>
	<li><a href="/user/other/post/${userid}"><#if show==true>��</#if><#if show!=true><#if userdata.sex=='boy'>��</#if><#if userdata.sex!='boy'>��</#if></#if>�Ļ���</a></li>
	<li><a href="/group/other/${userid}"><#if show==true>��</#if><#if show!=true><#if userdata.sex=='boy'>��</#if><#if userdata.sex!='boy'>��</#if></#if>��Ӫ��</a></li>
	<li><a href="/label/other/${userid}"><#if show==true>��</#if><#if show!=true><#if userdata.sex=='boy'>��</#if><#if userdata.sex!='boy'>��</#if></#if>�ı�ǩ</a></li>
	<li><a href="/book/other/${userid}"><#if show==true>��</#if><#if show!=true><#if userdata.sex=='boy'>��</#if><#if userdata.sex!='boy'>��</#if></#if>�����</a></li>
	<li><a href="/user/other/friend/${userid}"><#if show==true>��</#if><#if show!=true><#if userdata.sex=='boy'>��</#if><#if userdata.sex!='boy'>��</#if></#if>������</a></li>
	</ul>
</#if>
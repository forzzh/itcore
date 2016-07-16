<%@ page language="java" import="java.util.*" pageEncoding="GBK"
	isELIgnored="false"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<%response.sendRedirect("/index.html"); %>
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'list.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

</head>
  <link rel="stylesheet" type="text/css" href="/uploadify/uploadify.css">  
     <script type="text/javascript" src="/uploadify/jquery.min.js"></script>  
          <script type="text/javascript" src="/uploadify/jquery.uploadify.min.js"></script>  
<body>


<h1>Uploadify Demo</h1>
	<form>
		<div id="queue"></div>
		<input id="file_upload" name="file_upload" type="file" multiple="true">
	</form>

	<script type="text/javascript">
	 $(document).ready(function() {  
         $("#file_upload").uploadify({  
        	 	'formData'      : {'fileType' : "project"},
                 'buttonText' : '«Î—°‘Ò',  
                 'height' : 30,  
                 'swf' : '/uploadify//uploadify.swf',  
                 'uploader' : '/base/upload',  
                 'width' : 120,  
                 'auto':true,  
                 'fileObjName'   : 'file',  
                 'onUploadSuccess' : function(file, data, response) {  
                    console.log(data)
                 }  
             });  
     }); 
	</script>
	
</body>
</html>
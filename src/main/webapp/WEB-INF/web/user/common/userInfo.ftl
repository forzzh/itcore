	<i class="fl" 
	<#if userdata.image??>
		<#if userdata.image !="">
		style="background:url(${userdata.image}) no-repeat 0 center;background-size: 100%;"
		</#if>
	 </#if>
	 <#if !userdata.image??>
	 	<#if  userdata.sex=='boy'>
			style="background: url(/web/images/boy.jpg) no-repeat 0 center;background-size: 100%;" 
		</#if>
		<#if  userdata.sex!='boy'>
			style="background: url(/web/images/gril.jpg) no-repeat 0 center;background-size: 100%;"
		</#if>
	 </#if>	
	   >
	</i> 
	   
	<#if show==false>
		<span id="isFocus">
		</span>
	</#if>						
	<p class="fl">
		<span class="author">
			${userdata.user}
		</span>
		<br />${userdata.email}
	</p>
	<div class="blueBtn" id="foucesdiv" style="display:none;"><a href="javascript:void(0)" onclick="addfocus()" class="f16" id="focusBtn">��ע</a></div>
	<input type="hidden" value="${userdata.id}" id="userid"/>
	<div   id="file_upload" style="<#if show??><#if show!=true> display:none;</#if> </#if>" > 
	</div>
	<#if show??>
		<#if show==true>
			<font style="color:red">ͼƬ�Ŀ��ܳ���700���Ҹ߶Ȳ��ܸ���600</font>
		</#if>
	</#if>
	<div id="answerTopic" class="reveal-modal-bg1" style="display: none; cursor: pointer;top:0px;">	
		<div  style="height: 590px; width: 700px; opacity: 1; visibility: visible;top:0px;" class="reveal-modal answer">
			<div class="model-title">
				�޸�ͷ��
				<a class="close-reveal-modal" onclick="hideimage()">��</a>
			</div>
			
  		<img src="/Jcrop-v0.9.12/demos/demo_files/sago.jpg" id="target" alt="[Jcrop Example]" />  
  		<form id="coords" class="coords" onsubmit="return false;" style="display:none;" action="http://example.com/post.php">

		    <label>X1 <input type="text" size="4" id="x1" name="x"></label>
		    <label>Y1 <input type="text" size="4" id="y1" name="y"></label>
		    <label>X2 <input type="text" size="4" id="x2" name="x2"></label>
		    <label>Y2 <input type="text" size="4" id="y2" name="y2"></label>
		    <label>W <input type="text" size="4" id="w" name="w"></label>
		    <label>H <input type="text" size="4" id="h" name="h"></label>
		    
			<input type="hidden" id="image" name="image" value="">
  		</form>
		<div class="buttonGroup" style="margin-top:15px">
			<button type="button" onclick="save()" style="    color: #fff;background: #6ebd3f; border: 1px solid #6ebd3f;">����</button> 
		</div>
	</div>
		</div>
				<script type="text/javascript">
				
				//�ж��Ƿ��Ѿ���ע
				$(function(){
				$.get("/userRelationship/isFocus",{passiveUserId:$("#userid").val()},function(data){
				      if(data.msg=="�û�δ��¼"){
	                      $("#isFocus").remove();
	                  }
				      if(data.msg == "û�иù�ϵ����"){
				      	$("#foucesdiv").show()
				      } else if (data.msg == "��ѯ��ϵ�ɹ�") {
				      		$("#focusBtn").html("ȡ����ע");
				      		$("#foucesdiv").show()
				      } else {
				      	$("#foucesdiv").hide()
				        //$("#isFocus").html('<div class="blueBtn"><a href="javascript:void(0)" onclick="addfocus()" class="f16">��ע</a></div>');
				      }
				   },"json");
				
				});
				
				function addfocus(){
				
				   $.get("/userRelationship/addFriend",{passiveUserId:$("#userid").val()},function(data){
				   
				      alert(data.msg);
				   },"json");
				}
				
				
				//$("#answerTopic").hide();
function showimage(){
	$(".jcrop-holder").show()
}
function hideimage(){
window.location.reload()
}
 

  // Simple event handler, called from onChange and onSelect
  // event handlers, as per the Jcrop invocation above
  function showCoords(c)
  {
    $('#x1').val(c.x);
    $('#y1').val(c.y);
/*     $('#x2').val(c.x2);
    $('#y2').val(c.y2); */
    $('#w').val(c.w);
    $('#h').val(c.h);
  };
       var jcrop_api;
  function clearCoords()
  {
    $('#coords input').val('');
  };
<#if show??><#if show==true>
	 $(document).ready(function() {  
         $("#file_upload").uploadify({  
        	 	'formData'      : {'fileType' : "project"},
                 'buttonText' : '��ѡ��ͼƬ',  
                 'height' : 20,  
                 'swf' : '/uploadify//uploadify.swf',  
                 'uploader' : '/base/uploadPhoto',  
                 'width' : 100,  
                 'auto':true,  
                 'fileObjName'   : 'file',  
                 'onUploadSuccess' : function(file, data, response) {  
                    data = eval("("+data+")")
                    if(data.status==true){
                    	alert("ͼƬ�ϴ��ɹ�")
                    	$("#image").val(data.data);
                    	$("#answerTopic").show()
                    	$(document).scrollTop(0)
                    	document.getElementById("target").style.display="block"
                    	$("#target").css("visibility","visible");
                    	$("#target").attr("src",data.data)
               

    $('#target').Jcrop({
      onChange:   showCoords,
      onSelect:   showCoords,
      onRelease:  clearCoords,
      height:400,
      width:400,
	  aspectRatio: 1,
		    minSize: [ 80, 80 ],
        maxSize: [ 350, 350 ]
    },function(){
      jcrop_api = this;
    });
//    $('#coords').on('change','input',function(e){
 //     var x1 = $('#x1').val(),
       //   x2 = $('#x2').val(),
    //      y1 = $('#y1').val(),
   //       y2 = $('#y2').val();
   //   jcrop_api.setSelect([x1,y1,x2,y2]);
  //  });
                    }else{
                    	alert(data.msg);
                    }
                    
                 }  
             });  
     }); 
     
     </#if>  </#if>
	function save(){
		$.ajax({

	     type: 'POST',
	
	     url: "/user/modifyImage" ,
	
	    data: {x:parseInt($("#x1").val()),y:parseInt($("#y1").val() ),width:parseInt($("#w").val()),heigth:parseInt($("#h").val()) ,image:$("#image").val()} ,
	
	    success: function(data){
	    	//console.log(data)
	    	if(data.status){
	    		window.location.reload()
			}else{
				if(data.msg =="δ��¼"){
					window.location.href="/user/login?redirect="+window.location.href
				}
			}
	    } ,
	
	    dataType: "json"
	
	});
	}
</script>

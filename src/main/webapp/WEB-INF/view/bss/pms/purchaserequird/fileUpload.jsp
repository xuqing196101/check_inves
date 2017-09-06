<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %> 
    <script type="text/javascript" src="${pageContext.request.contextPath}/public/upload/ajaxfileupload.js"></script>
    <script type="text/javascript">
 	  function uploadFile(){
 		 $.ajaxFileUpload ({
             url: '${pageContext.request.contextPath}/purchaser/upload.do', 
             secureuri: false, 
             fileElementId: 'fileName', 
             dataType: "json", 
             success: function (data){ 
               if (data != null){ 
            	   if (data.success){
            		   loadData(data.obj);
            	   } else {
            		   layer.msg(data.obj);
            	   }
               }
             },error: function (data, status, e){
          	   layer.msg("上传失败");
               }
        });
 	  }
 	  
 	  function loadData(data){
 		  if (data != null){
 			  for (var i =0;i<data.length;i++){
 				  
 			  }
 		  }
 	  }
 	  
 	</script>
 
  </head>  
  
  <body>  
    <div class=" mt30"  id="add_div" >
    	<form action="${pageContext.request.contextPath}/purchaser/upload.do" class="fl" method="post" enctype="multipart/form-data">
    		<input type="file" name="file" class="fl pl20" >
    		 <input type="submit" value="导入" class="fl" />
    	</form>
    
    <!--   <form>
      	<div>
      	  <input id="fileName" type="file" name="file" />
      	</div>
      	<div>
      	  <button type="button" class="" onclick="uploadFile();">确定</button>
      	  <button type="button" >取消</button>
      	</div>
      </form> -->
    </div>
  </body>
</html>
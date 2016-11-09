<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  </head>
  <script type="text/javascript">
    $(function(){
        $("#save").click(function(){
            $.ajax({  
               type: "POST",  
               url: "${pageContext.request.contextPath}/role/save.html",  
               data: $("#form1").serializeArray(),  
               dataType: 'json',  
               success:function(result){
                    if(!result.success){
                        layer.msg(result.msg,{offset: ['150px', '180px']});
                    }else{
                        parent.window.setTimeout(function(){
                            parent.window.location.href = "${pageContext.request.contextPath}/role/list.html";
                        }, 1000);
                        layer.msg(result.msg,{offset: ['150px', '180px']});
                    }
                },
                error: function(result){
                    layer.msg("添加失败",{offset: ['150px', '180px']});
                }
            });
            
        });
        $("#backups").click(function(){
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); 
        });
    });
  </script>
 <body>
   
   <div class="container">
	   <form action="" id="form1" method="post">
		   <div>
		   	
			   <ul class="list-unstyled mt10">
			     <li class="col-md-6 p0">
			     	<span class="fl mt5 red">*</span>
				   <span class="fl mt5">名称：</span>
				   <div class="input-append">
			        <input class="w140" name="name" maxlength="30" type="text">
			        <span class="add-on">i</span>
			       </div>
				 </li>
				 <li class="col-md-6 p0 ">
				   <span class="fl red">*</span>
				   <span class="fl">状态：</span>
			        <select name="status" class="w180 mb10">
			        	<option value="0">可用</option>
			        	<option value="1">禁用</option>
			        </select>
				 </li>
			     <li class="col-md-12 p0 ml6">
				   <span class="fl mt5">描述：</span>
				   <div class="fn mt5 ">
			        <textarea class="text_area1 mt6" name="description" maxlength="200" title="" placeholder=""></textarea>
			       </div>
				 </li> 
			   </ul>
		  </div> 
	   
		  <div  class="col-md-12">
		    <div class="fl padding-10">
			    <button class="btn btn-windows save" id="save" type="button">保存</button>
			    <button class="btn btn-windows back" id="backups" type="button">返回</button>
			</div>
		  </div>
	  </form>
  </div>
 </body>
</html>

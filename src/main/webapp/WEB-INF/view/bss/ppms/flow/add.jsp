<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<script type="text/javascript">
    $(function(){
        $("#save").click(function(){
        	var typeId = $("#typeId").val();
            $.ajax({  
               type: "POST",  
               url: "${pageContext.request.contextPath}/flow/save.html",  
               data: $("#form1").serializeArray(),  
               dataType: 'json',  
               success:function(result){
                    if(!result.success){
                        layer.msg(result.msg,{offset: ['20px']});
                    }else{
                        parent.window.setTimeout(function(){
                            parent.window.location.href = "${pageContext.request.contextPath}/flow/list.html?purchaseTypeId="+typeId;
                        }, 1000);
                        layer.msg(result.msg,{offset: ['20px']});
                    }
                },
                error: function(result){
                    layer.msg("添加失败",{offset: ['20px']});
                }
            });
            
        });
        $("#backups").click(function(){
            var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
            parent.layer.close(index); 
        });
    });
  </script>
</head>
<body>

    <div >
        <form action="${pageContext.request.contextPath}/flow/save.html" method="post" id="form1">
            <div>
            	<input type="hidden" name="purchaseTypeId" id="typeId" value="${typeId }">
				<ul class="list-unstyled mt10">
				    <li class="col-md-12 ml30">
				     	<span class="fl mt5 red">*</span>
					   	<span class="fl mt5">流程环节名称：</span>
					   	<div class="input-append">
					        <input class="w140" name="name" maxlength="30" type="text">
					        <span class="add-on">i</span>
				       	</div>
					</li>
					<li class="col-md-12 ml15">
				     	<span class="fl mt5 red">*</span>
					   	<span class="fl mt5">流程环节步骤：</span>
					   	<div class="input-append">
					        <input class="w140" name="step" maxlength="10" type="text">
					        <span class="add-on">i</span>
				       	</div>
					</li>
					<li class="tc col-md-12 mt20">
					 <input type="button" class="btn" id="save" value="确定"/>
					 <input type="button" class="btn" id="backups" value="取消"/>
					</li>
			 	</ul>
            </div>
        </form>
    </div>
</body>
</html>

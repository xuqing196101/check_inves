<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<script type="text/javascript">
    $(function(){
        $("#update").click(function(){
        	var typeId = $("#typeId").val();
        	var currpage = $("#currpage").val();
            $.ajax({  
               type: "POST",  
               url: "${pageContext.request.contextPath}/flow/update.html",  
               data: $("#form1").serializeArray(),  
               dataType: 'json',  
               success:function(result){
                    if(!result.success){
                        layer.msg(result.msg,{offset: ['20px']});
                    }else{
                        parent.window.setTimeout(function(){
                            parent.window.location.href = "${pageContext.request.contextPath}/flow/list.html?purchaseTypeId="+typeId+"&page="+currpage;
                        }, 1000);
                        layer.msg(result.msg,{offset: ['20px']});
                    }
                },
                error: function(result){
                    layer.msg("更新失败",{offset: ['20px']});
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
        <form action="${pageContext.request.contextPath}/flow/update.html" method="post" id="form1">
            <div>
            	<input type="hidden" name="purchaseTypeId" id="typeId" value="${fd.purchaseTypeId }">
            	<input type="hidden" name="id"  value="${fd.id }">
            	<input type="hidden" name="currpage" id="currpage"  value="${currpage }">
				<ul class="list-unstyled mt10">
				    <li class="col-md-12 ml20">
				     	<span class="fl mt5 red">*</span>
					   	<span class="fl mt5">流程环节名称：</span>
					   	<div class="input-append">
					        <input class="w140" name="name" value="${fd.name }" maxlength="30" type="text">
					        <span class="add-on">i</span>
				       	</div>
					</li>
					<li class="col-md-12 ml5">
				     	<span class="fl mt5 red">*</span>
					   	<span class="fl mt5">流程环节步骤：</span>
					   	<div class="input-append">
					        <input class="w140" name="step" value="${fd.step }" maxlength="10" type="text">
					        <span class="add-on">i</span>
				       	</div>
					</li>
					<li class="col-md-12 ml10">
					   	<span class="fl mt5">流程跳转路径：</span>
					   	<div class="input-append">
					        <input class="w140" name="url" value="${fd.url }" maxlength="200" type="text">
					        <span class="add-on">i</span>
				       	</div>
					</li>
					<li class="tc col-md-12 mt20">
					 <input type="button" class="btn" id="update" value="更新"/>
					 <input type="button" class="btn" id="backups" value="取消"/>
					</li>
			 	</ul>
            </div>
        </form>
    </div>
</body>
</html>

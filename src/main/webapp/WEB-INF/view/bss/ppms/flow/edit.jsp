<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
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

    <div class="layui-layer-wrap" >
        <form action="${pageContext.request.contextPath}/flow/update.html" method="post" id="form1">
            <div class="drop_window">
            	<input type="hidden" name="purchaseTypeId" id="typeId" value="${fd.purchaseTypeId }">
            	<input type="hidden" name="id"  value="${fd.id }">
            	<input type="hidden" name="currpage" id="currpage"  value="${currpage }">
				<ul class="list-unstyled">
                    <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                        <label class="col-md-12 pl20 col-xs-12 col-xs-12"><a class="star_red">*</a>流程环节编码</label>
                        <span class="col-md-12 col-xs-12">
                            <input class="title col-md-12" name="code" disabled="disabled" value="${fd.code }" maxlength="30" type="text">
                        </span>
					</li>
					<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                        <label class="col-md-12 pl20 col-xs-12 col-xs-12"><a class="star_red">*</a>流程环节步骤</label>
                        <span class="col-md-12 col-xs-12">
                            <input  class="title col-md-12" name="step" value="${fd.step }" maxlength="10" type="text">
                        </span>
                    </li>
                    <li class="mt10 col-md-12 p0 col-xs-12">
                        <label class="col-md-12 pl20 col-xs-12"><a class="star_red">*</a>流程环节名称</label>
                        <span class="col-md-12 col-xs-12">
                        <input class="col-xs-12 h80 mt6" name="name" value="${fd.name }" maxlength="30" type="text">
                        </span>
                    </li>
                    <li class="mt10 col-md-12 p0 col-xs-12">
                        <label class="col-md-12 pl20 col-xs-12">流程跳转路径</label>
                        <span class="col-md-12 col-xs-12">
                        <input class="col-xs-12 h80 mt6" name="url" value="${fd.url }" maxlength="300" type="text">
                        </span>
                    </li>
                    <div class="clear"></div>
                    </ul>
                </div> 
                <div class="tc mt10 col-md-12 col-xs-12">
					 <input type="button" class="btn" id="update" value="更新"/>
					 <input type="button" class="btn" id="backups" value="取消"/>
			     </div>
        </form>
    </div>
</body>
</html>

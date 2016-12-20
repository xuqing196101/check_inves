<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
  <link href="${pageContext.request.contextPath}/public/backend/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">

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
                        layer.msg(result.msg,{offset: ['150px']});
                    }else{
                        parent.window.setTimeout(function(){
                            parent.window.location.href = "${pageContext.request.contextPath}/role/list.html";
                        }, 1000);
                        layer.msg(result.msg,{offset: ['150px']});
                    }
                },
                error: function(result){
                    layer.msg("添加失败",{offset: ['150px']});
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
    	<form action="" id="form1" method="post">
          <div class="drop_window">
              <ul class="list-unstyled">
				<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
                   <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>名称</label>
                  <span class="col-md-12 col-sm-12 col-xs-12 p0">
                   <input style="padding-right: 20px;" name="name" maxlength="30" type="text">
                  </span>
                </li>
                <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                   <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>唯一编码</label>
                  <span class="col-md-12 col-sm-12 col-xs-12 p0">
                  <input style="padding-right: 20px;" name="code" maxlength="30" type="text">
                  </span>
                </li>
                
                <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                   <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>状态</label>
                  <span class="col-md-12 col-sm-12 col-xs-12 select_common p0">
                   <select name="status" class="">
                        <option value="0">可用</option>
                        <option value="1">禁用</option>
                    </select>
                  </span>
                </li>
                <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                   <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>角色所属后台</label>
                  <span class="col-md-12 col-sm-12 col-xs-12 select_common p0">
                   <select name="kind" class="">
                   			<option value="">请选择</option>
                   		<c:forEach items="${dds}" var="dd" varStatus="vs">
                   			<option value="${dd.id}">
	                   			<c:if test="${'PURCHASE_BACK' eq dd.code}">采购后台</c:if>
	                   			<c:if test="${'EXPERT_BACK' eq dd.code}">专家后台</c:if>
	                   			<c:if test="${'SUPPLIER_BACK' eq dd.code}">供应商后台</c:if>
	                   			<c:if test="${'IMPORT_SUPPLIER_BACK' eq dd.code}">进口供应商后台</c:if>
	                   			<c:if test="${'IMPORT_AGENT_BACK' eq dd.code}">进口代理商后台</c:if>
                   			</option>
                   		</c:forEach>
                    </select>
                  </span>
                </li>
                <li class="col-md-12 col-sm-12 col-xs-12">
                  <label class="col-md-12 pl20 col-xs-12 padding-left-5">描述</label>
                   <span class="col-md-12 col-sm-12 col-xs-12 p0">
                    <textarea class="col-md-12 col-sm-12 col-xs-12 h80" name="description" maxlength="200" title="" placeholder=""></textarea>
                   </span>
                </li>
                <div class="clear"></div>
             </ul>
             <div class="col-md-12 col-sm-12 col-xs-12 tc mt20">
                 <button class="btn btn-windows save" id="save" type="button">保存</button>
                <button class="btn btn-windows back" id="backups" type="button">返回</button>
              </div>
           </div>
        </form>  
    </div>         
 </body>
</html>

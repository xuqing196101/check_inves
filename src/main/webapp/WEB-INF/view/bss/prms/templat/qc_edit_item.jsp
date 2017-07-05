<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
  <script type="text/javascript">
	  //关闭弹窗
	  function cancel(){
	      layer.closeAll();
	  }
	  
	  //保存更新
	  function updateItem(){
		  $.ajax({   
	            type: "POST",  
	            url: "${pageContext.request.contextPath}/auditTemplat/updateItem.html",        
	            data : $('#form2').serializeArray(),
	            dataType:'json',
	            success:function(result){
	                if(!result.success){
	                    layer.msg(result.msg,{offset: ['150px']});
	                }else{
	                    var templatKind = $("#templatKind").val();
	                    var templatId = $("#templatId").val();
	                    parent.window.setTimeout(function(){
                            parent.window.location.href = '${pageContext.request.contextPath}/auditTemplat/editTemplat.html?templetKind='+templatKind+'&templetId='+templatId;
                        }, 1000);
	                    layer.msg(result.msg,{offset: ['150px']});
	                }
	            },
	            error: function(result){
	                layer.msg("修改失败",{offset: ['150px']});
	            }
	       });    
	  }
	  //关闭弹窗
      function cancel(){
    	  var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
          parent.layer.close(index);
      }
  </script>
  </head>
<body>
    <div class="layui-layer-wrap" >
      <form id="form2" method="post" >
        <div class="drop_window">
              <input type="hidden" id="templatKind" value="${templat.kind}">
              <input type="hidden" name="templatId" id="templatId" value="${item.templatId}">
              <input type="hidden" name="kind" id="itemKind" value="${item.kind}"> 
              <input type="hidden" name="id" value="${item.id}"> 
              <ul class="list-unstyled">
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
                    <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>评审名称</label>
                    <span class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
                       <input name="name" id="itemName" value="${item.name}" maxlength="30" type="text">
                    </span>
                  </li>
                  <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
                    <label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>序号</label>
                    <div class="col-md-12 col-sm-12 col-xs-12 p0 input-append input_group">
                       <input  name="position" id="itemPosition" value="${item.position}" maxlength="10" type="text">
                    </div>
                 </li>
                 <li class="col-md-12 col-sm-12 col-xs-12 mb20">
                   <label class="col-md-12 pl20 col-xs-12 padding-left-5"><div class="star_red">*</div>评审内容</label>
                   <span class="col-md-12 col-sm-12 col-xs-12 p0">
                    <textarea class="w100p h80" id="itemContent" name="content" maxlength="200" title="" placeholder="">${item.content}</textarea>
                   </span>
                 </li>
              </ul>
              <div class="mt40 tc mb50">
                <input class="btn btn-windows save"  onclick="updateItem();" value="更新" type="button"> 
                <input class="btn btn-windows back"  onclick="cancel();" value="取消" type="button"> 
              </div>
            </div>
         </form>
      </div>
</body>
</html>
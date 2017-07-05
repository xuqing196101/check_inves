<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>My JSP 'view.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page"> 
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
  </head>
  <script type="text/javascript">
	  //关闭弹窗
	  function cancel(){
	      layer.closeAll();
	  }
	  
	  //保存更新
	  function updateItem(){
	    var pattern = /^[1-9]\d*$/;
          var _val = $("#itemPosition").val();
          if(!pattern.test(_val)){
              layer.msg('请输入正确的序号');
          }else{
              $.ajax({
                  type: "POST",
                  url: "${pageContext.request.contextPath}/adFirstAudit/updateItem.html",
                  data : $('#form2').serializeArray(),
                  dataType:'json',
                  success:function(result){
                      if(!result.success){
                          layer.msg(result.msg,{offset: ['150px']});
                      }else{
                          var packageId = $("#packageId").val();
                          var projectId = $("#projectId").val();
                          parent.window.setTimeout(function(){
                              var flag = $("#isConfirm").val();
                              if (flag == 1) {
                                  parent.window.location.href = '${pageContext.request.contextPath}/adIntelligentScore/editPackageScore.html?packageId='+packageId+'&projectId='+projectId;
                              } else {
                                  parent.window.location.href = '${pageContext.request.contextPath}/adFirstAudit/editPackageFirstAudit.html?packageId='+packageId+'&projectId='+projectId;
                              }
                          }, 1000);
                          layer.msg(result.msg,{offset: ['150px']});
                      }
                  },
                  error: function(result){
                      layer.msg("修改失败",{offset: ['150px']});
                  }
              });
          }
	  }
	  //关闭弹窗
      function cancel(){
        var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
          parent.layer.close(index);
      }
      function inputChange(){
          var pattern = /^[1-9]\d*$/;
          var _val = $("#itemPosition").val();
          if(!pattern.test(_val)){
              layer.msg('请输入正整数序号');
          }
      }
  </script>
<body>
    <div class="layui-layer-wrap" >
      <form id="form2" method="post" >
        <div class="drop_window">
              <input type="hidden" id="packageId" id="packageId" value="${item.packageId}">
              <input type="hidden" name="projectId" id="projectId" value="${item.projectId}">
              <input type="hidden" name="kind" id="itemKind" value="${item.kind}"> 
              <input type="hidden" name="id" value="${item.id}"> 
              <input type="hidden" id="isConfirm" name="isConfirm" value="${isConfirm}">
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
                       <input  name="position" id="itemPosition" value="${item.position}" maxlength="10" type="text" onchange="inputChange()">
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
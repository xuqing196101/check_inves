<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script type="text/javascript">
  function start(id){
    var name = $("input[name='name']").val();
    var documentNumber = $("#documentNumber").val();
    name = $.trim(name);
    documentNumber = $.trim(documentNumber);
    $.ajax({
          url: "${pageContext.request.contextPath}/advancedProject/verifyUpload.html",
          data: "id=" + id,
          type: "post",
          dataType: "json",
          success: function(result) {
            if(result == "1"){
              layer.alert("请上传彩色扫描件", {
              shade: 0.01
             });
            }else{
              if(name == ""){
                layer.tips("名称不允许为空", "#name");
                $("#name").focus();
              } else if (documentNumber == ""){
                layer.tips("名称不允许为空", "#documentNumber");
                $("#documentNumber").focus();
              } else {
                $("#form1").submit();
              }
            }
          },
        });    
    
  }
  
  function generateMixed() {
    var chars = ['0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];
       var res = "";
       for(var i = 0; i < 6 ; i ++) {
           var id = Math.ceil(Math.random()*35);
           res += chars[id];
       }
       return res;
  }
  
  function cancel(){
    var index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
  }
</script>  
</head>

<body>
<div class="container">
  <form id="form1" action="${pageContext.request.contextPath}/advancedProject/transmit.html"  method="post" name="form1" class="simple" target="_parent">
    <div id="openDiv" class="layui-layer-wrap" >
      <div class="drop_window">
        <ul class="list-unstyled">
           <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6 pl15">
               <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">采购任务名称:</span>
               <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 p0 input_group input-append">
                   <input  type="text" id="name" name="name" value="${proName}">
               </div>
           </li>
           <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
             <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5">采购任务编号:</span>
             <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 p0 input_group input-append">
                  <input id="documentNumber"  type="text" name="documentNumber" value="${projectNumber}">
	              <input type="hidden" name="id" value="${projectId}"/>
	              <input type="hidden" name="proName" value="${proName}"/>
	              <input type="hidden" name="projectNumber" value="${projectNumber}"/>
	              <input type="hidden" name="department" value="${department}"/>
	              <input type="hidden" name="purchaseType" value="${purchaseType}"/>
	              <input type="hidden" name="planType" value="${planType}"/>
	              <input type="hidden" name="ids" value="${ids}"/>
	              <input type="hidden" name="organization" value="${organization}"/>
	          </div>
	       </li>
	       <li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
	              <span class="col-md-12 col-sm-12 col-xs-12 col-lg-12 padding-left-5"><div class="star_red">*</div>预研通知书上传:</span>
	              <div class="col-md-12 col-sm-12 col-xs-12 col-lg-12 p0">
	                 <u:upload id="upload_id" businessId="${projectId}" multiple="true"  auto="true" typeId="${advancedAdvice}" sysKey="2"/>
	                 <u:show showId="upload_id" businessId="${projectId}" sysKey="2" typeId="${advancedAdvice}"/>
                  </div>
          </li>
           <div class="clear"></div>
        </ul>
      </div>
    </div>
    <div class="tc mt10 col-md-12 col-xs-12 com-sm-12">
      <button class="btn btn-windows save" type="button" onclick="start('${projectId}');">确认</button>
      <input class="btn btn-windows cancel" value="取消" type="button" onclick="cancel();">
    </div>
  </form>
</div>
</body>
</html>

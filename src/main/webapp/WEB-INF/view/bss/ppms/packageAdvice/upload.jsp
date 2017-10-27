<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script type="text/javascript">
  //取消
  var index;
  function cancel(){
    index=parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
  }
  
  function saveAudit(){
		var packageId = "${pachageIds}";
 		var advice = $("#advice").val();
 		var type = "${type}";
 		if(advice){
 			$.ajax({
	 			url:"${pageContext.request.contextPath}/packageAdvice/saveAudit.do",
	 			tpye:"post",
	 			dataType:"text",
	 			async: false,
	 			data:{
	 				"projectId" : "${projectId}",
	 				"packageIds" : packageId,
	 				"advice" : advice,
	 				"auditCode" : "${auditCode}",
	 				"type" : "${type}",
	 				"flowDefineId" : "${currHuanjieId}"
	 			},
	 			success:function(data){
	 				if(data == "ok"){
	 					/* layer.msg("成功");
	 					index=parent.layer.getFrameIndex(window.name);
    				parent.layer.close(index); */
    				if(type == 1){
    					parent.auditSuspend();
    					index=parent.layer.getFrameIndex(window.name);
        				parent.layer.close(index);
    				} else {
    					var divs=$('#openDiv_packages', window.parent.document);
    					var checkboxSize=$(divs).find("input[type='checkbox']").length;
    		   	  		if(checkboxSize==0){
    		   	  		   parent.layer.closeAll();
    		   	  		};
    					index=parent.layer.getFrameIndex(window.name);
    					parent.layer.close(index);
    				}
    				
	 				} else if (data == "erroFile"){
	 					layer.msg("请上传附件");
	 				} else if (data == "erroAdvice"){
	 					layer.msg("请填写原因");
	 				} else {
	 					layer.msg("失败");
	 				}
	 			}
	  	});
 		} else {
 			layer.msg("请填写原因！");
 		}
	}
</script>  
</head>

<body>
  <div id="openAudit" class="layui-layer-wrap">
    	<div class="drop_window">
    		<ul class="list-unstyled">
    			<li class="col-sm-6 col-md-6 col-lg-6 col-xs-6">
    				<c:if test="${type == 2}">
	          	<label class="col-md-12 pl20 col-xs-12">上传转竞谈附件：</label>
	            <span class="col-md-12 col-xs-12">
	              <u:upload id="upload_${projectId}" groups="show_${projectId},upload_${projectId}" multiple="true" auto="true" businessId="${auditCode}" typeId="${auditJZXTP}" sysKey="2"/>
	              <u:show showId="show_${projectId}" groups="show_${projectId},upload_${projectId}" businessId="${auditCode}" sysKey="2" typeId="${auditJZXTP}"/>
	            </span>
            </c:if>
            <c:if test="${type == 1}">
            	<label class="col-md-12 pl20 col-xs-12">上传中止附件：</label>
	            <span class="col-md-12 col-xs-12">
	              <u:upload id="upload_${projectId}" groups="show_${projectId},upload_${projectId}" multiple="true" auto="true" businessId="${auditCode}" typeId="${auditZZFJ}" sysKey="2"/>
	              <u:show showId="show_${projectId}" groups="show_${projectId},upload_${projectId}" businessId="${auditCode}" sysKey="2" typeId="${auditZZFJ}"/>
	            </span>
            </c:if>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
          </li>
    			<li class="col-md-12 col-sm-6 col-xs-12">
   	      	<label class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>
   	      	<c:if test="${type == 2}">转竞谈</c:if><c:if test="${type == 1}">中止</c:if>原因：</label>
   	      	<span class="col-md-12 col-sm-12 col-xs-12 p0">
            	<textarea id="advice" name="advice" class="w100p h80 p0" rows="3" cols="1"></textarea>
            </span>
           </li>
    		</ul>
    		<div class="tc mt10 col-md-12 col-sm-12 col-xs-12">
        	<input class="btn" onclick="saveAudit()" value="确定" type="button"> 
					<input class="btn" onclick="cancel();" value="取消" type="button"> 
        </div>
    	</div>
    </div>
</body>
</html>

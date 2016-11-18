<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="/tld/upload" prefix="f"%>
<%@ include file="../../../common.jsp"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
function start(){
   $.ajax({
        type: 'post',
        url: "${pageContext.request.contextPath}/purchaseManage/updatePurchaseDepAjxa.do?",
        data : $("#formID").serialize(),
        success: function(data) {
            console.dir(data);
            var index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
             var index1=parent.parent.layer.getFrameIndex(window.name);
            parent.parent.parent.layer.closeAll();
             parent.layer.closeAll();
              layer.closeAll();
            
        }
    });  
     
}
function submit(){
	$.ajax({
        type: 'post',
        url: "${pageContext.request.contextPath}/purchaseManage/updatePurchaseDepAjxa.do?",
        data : $("#formID").serialize(),
        success: function(data) {
            console.dir(data);
            var index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            
            
        }
    });
}
function cancel(){
     var index=parent.layer.getFrameIndex(window.name);
     parent.layer.close(index);
     
}
</script>  
</head>

<body>
    <form id="formID" action="${pageContext.request.contextPath}/purchaseManage/updatePurchaseDepAjxa.html" 
        method="post" name="form1" class="simple" target="_parent">
        <input type="hidden" name="id" value="${purchaseDep.id}"/>
        <input type="hidden" name="quaStatus" value="${purchaseDep.quaStatus}"/>
         <div class="drop_window" id="delTask">
              <ul class="ul_list">
				<li class="col-md-11 margin-0 padding-0 "><span
					class="col-md-12 padding-left-5">理由:</span>
					<div class="">
						<textarea class="col-md-12" style="height:50px" title="不超过800个字" name="quaStashReason"></textarea>
					</div></li>
				<li class="col-md-3 margin-0 padd">
                    <span class="col-md-12 padding-left-5">上传文件:</span>
                   <f:upload id="upload_id" businessId="${purchaseDep.id}" typeId="${PURCHASE_QUA_STATUS_STASH_ID}" sysKey="2"/>
                   <f:show showId="upload_id" businessId="${purchaseDep.id}" sysKey="2" typeId="${PURCHASE_QUA_STATUS_STASH_ID}"/>
                </li>
               
                <div class="clear"></div>
              </ul>
         </div>
        
        <div class="tc mt10 col-md-12">
     <a class="btn btn-windows save" onclick="start();">确认</a>
         <input class="btn btn-windows reset" value="取消" type="button" onclick="cancel();">
         </div>
    </form>
</body>
</html>

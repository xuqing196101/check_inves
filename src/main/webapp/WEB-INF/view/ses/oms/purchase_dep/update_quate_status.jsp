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
	var quaStatus = $("#quaStatus").val();
	var id = $("#id").val();
	var str ="";
	var title ="";
	if(quaStatus==0){
		str = "暂停";
		title ="您确认要暂停采购机构资质吗";
	}else if(quaStatus==1){
		str = "正常";
		title ="您确认要启用采购机构资质吗";
	}else if(quaStatus==2){
		str = "终止";
		title ="您确认要终止采购机构资质吗";
	}
	layer.confirm(title,{
                offset: ['50px','90px'],
                shade:0.01,
                btn:['是','否'],
                },function(){
                    $.ajax({
				        type: 'post',
				        url: "${pageContext.request.contextPath}/purchaseManage/updateOrgnizationAjxa.do?",
				        data : $("#formID").serialize(),
				        dataType: 'json',  
				        success: function(data) {
				            console.dir(data);
				            console.dir(id);
				            console.dir(window.parent.document.getElementById(id).innerHTML=str);
				           // console.dir(window.parent.document.getElementById("78769032CB224CDDA03B19C28EAC9EF6").innerHTML);
				            var index=parent.layer.getFrameIndex(window.name);
				            parent.layer.close(index);
				        }
			   		 });  
                },function(){
                    var index=parent.layer.getFrameIndex(window.name);
                     parent.layer.close(index);
                }
                    
            ); 
   
     
}
function submit(){
	$.ajax({
        type: 'post',
        url: "${pageContext.request.contextPath}/purchaseManage/updatePurchaseDepAjxa.do?",
        data : $("#formID").serialize(),
        success: function(data) {
            console.dir(data);
            console.dir($("#6A25760E7F1A456D88E3B544630BCAA5").text());
            var index=parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
           // parent.parent.$("#6A25760E7F1A456D88E3B544630BCAA5").text(22);
            
            
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
    <form id="formID" action="${pageContext.request.contextPath}/purchaseManage/updateOrgnizationAjxa.html" 
        method="post" name="form1" class="simple" target="_parent">
        <input type="hidden" name="id" id="id" value="${purchaseDep.id}"/>
        <input type="hidden" name="quaStatus" value="${quaStatus}" id="quaStatus"/>
         <div class="drop_window" id="delTask">
              <ul class="ul_list">
				<c:choose>
					<c:when test="${quaStatus==0}">
						<li class="col-md-11 margin-0 padding-0 "><span
							class="col-md-12 padding-left-5">理由:</span>
							<div class="">
								<textarea class="col-md-12" style="height:50px" title="不超过800个字"
									name="quaStashReason"></textarea>
							</div>
						</li>
						<li class="col-md-3 margin-0 padd"><span
							class="col-md-12 padding-left-5">上传文件:</span> <f:upload
								id="upload_id" businessId="${purchaseDep.id}"
								typeId="${PURCHASE_QUA_STATUS_STASH_ID}" sysKey="2" auto="true" />
							<f:show showId="upload_id2" businessId="${purchaseDep.id}"
								sysKey="2" typeId="${PURCHASE_QUA_STATUS_STASH_ID}" /></li>
						<div class="clear"></div>
					</c:when>
					<c:when test="${quaStatus==1}">
						<li class="col-md-11 margin-0 padding-0 "><span
							class="col-md-12 padding-left-5">理由:</span>
							<div class="">
								<textarea class="col-md-12" style="height:50px" title="不超过800个字"
									name="quaNormalReason"></textarea>
							</div>
						</li>
						<li class="col-md-3 margin-0 padd"><span
							class="col-md-12 padding-left-5">上传文件:</span> <f:upload
								id="upload_id" businessId="${purchaseDep.id}"
								typeId="${PURCHASE_QUA_STATUS_NORMAL_ID}" sysKey="2" auto="true" />
							<f:show showId="upload_id2" businessId="${purchaseDep.id}"
								sysKey="2" typeId="${PURCHASE_QUA_STATUS_NORMAL_ID}" /></li>
						<div class="clear"></div>
					</c:when>
					<c:when test="${quaStatus==2}">
						<li class="col-md-11 margin-0 padding-0 "><span
							class="col-md-12 padding-left-5">理由:</span>
							<div class="">
								<textarea class="col-md-12" style="height:50px" title="不超过800个字"
									name="quaTerminalReason"></textarea>
							</div>
						</li>
						<li class="col-md-3 margin-0 padd"><span
							class="col-md-12 padding-left-5">上传文件:</span> <f:upload
								id="upload_id" businessId="${purchaseDep.id}"
								typeId="${PURCHASE_QUA_STATUS_TERMINAL_ID}" sysKey="2" auto="true" />
							<f:show showId="upload_id2" businessId="${purchaseDep.id}"
								sysKey="2" typeId="${PURCHASE_QUA_STATUS_TERMINAL_ID}" /></li>
						<div class="clear"></div>
					</c:when>
					<c:otherwise>
						出错啦
					</c:otherwise>
				</c:choose>
              </ul>
         </div>
        
        <div class="tc mt10 col-md-12">
     <a class="btn btn-windows save" onclick="start();">确认</a>
         <input class="btn btn-windows reset" value="取消" type="button" onclick="cancel();">
         </div>
    </form>
</body>
</html>

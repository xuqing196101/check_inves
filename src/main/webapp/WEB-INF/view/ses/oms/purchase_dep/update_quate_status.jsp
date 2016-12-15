<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="/WEB-INF/view/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
  
  /** 开始 **/
  function start(){
	var quaStatus = $("#quaStatus").val();
	var str ="";
	var title ="";
	if(quaStatus == 1){
		str = "暂停";
		title ="您确认要暂停采购机构资质吗";
	}else if(quaStatus == 0){
		str = "正常";
		title ="您确认要启用采购机构资质吗";
	}else if(quaStatus == 2){
		str = "终止";
		title ="您确认要终止采购机构资质吗（终止操作不可以恢复）";
	}
	   layer.confirm(title,{
                btn:['确认','取消'],
                },function(){
                    $.ajax({
				        type: 'post',
				        url: "${pageContext.request.contextPath}/purchaseManage/updatePurchaseStatus.do",
				        data : $("#formID").serialize(),
				        success: function(data) {
				        	if(data == 'ok'){
				        		alert(222);
				        		parent.updateStatus(quaStatus,str);
				        		cancel();
				        	}
				        }
			   		 });  
        }); 
	}
	/** 取消 **/
	function cancel(){
      var index=parent.layer.getFrameIndex(window.name);
      parent.layer.close(index);
	}
</script>  
</head>

<body>
      <form id="formID"> 
        <input type="hidden" name="id"  value="${id}"/>
        <input type="hidden" name="quaStatus" value="${purchaseStarus}" id="quaStatus"/>
        <input type="hidden" name="purchaseId" value="${purchaseDepId}"/>
         <div class="drop_window" id="delTask">
           <ul class="ul_list">
				<li class="col-md-11 margin-0 padding-0 "><span
					class="col-md-12 padding-left-5">理由:</span>
					<div class="">
						<textarea class="col-md-12"  title="不超过800个字" name="quaStashReason"></textarea>
					</div>
				</li>
				<li class="col-md-11 margin-0 padd"><span class="col-md-12 padding-left-5">批文扫描文件:</span> 
					<div class="">
					   <u:upload id="qua" auto="true" multiple="true" exts="png,jpeg,jpg,bmp" businessId="${id}" typeId="${purchaseTypeId}" sysKey="2"/>
					   <u:show showId="qua" businessId="${id}" typeId="${purchaseTypeId}" sysKey="2"/>
					</div>
				</li>
              </ul>
         </div>
        
        <div class="tc mt10 col-md-12">
          <button class="btn btn-windows save" type="button" onclick="start();">确认</button>
          <button class="btn btn-windows reset"  type="button" onclick="cancel();">取消</button>
        </div>
      </form>  
</body>
</html>

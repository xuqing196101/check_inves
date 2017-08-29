<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp" %>
<script type="text/javascript">
  
  /** 开始 **/
  function start(){
	var quaStatus = $("#quaStatus").val();
	var str ="";
	var title ="";
	var con = $("#quaStashReasonId").val();
	if (con == null || con == "") {
		layer.msg("原因说明不能为空");
	} else {
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
					        	  layer.msg("修改成功");
					        		parent.updateStatus(quaStatus,str);
					        		cancel();
					        	} else {
					        	   layer.msg("失败");
					        	}
					        }
				   		 });  
	        }); 
	}
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
           <ul>
				<li class="col-md-12 col-sm-12 col-xs-12 pl15">
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>理由:</span>
					<div class="col-md-12 col-sm-12 col-xs-12 p0">
						<textarea class="w100p h80" maxlength="600"  title="不超过600个字" id="quaStashReasonId" name="quaStashReason"></textarea>
					</div>
				</li>
				<li class="col-md-12 col-sm-12 col-xs-12 mt10">
				<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 ">批文扫描文件:</span> 
					<div class="col-md-12 col-sm-12 col-xs-12 p0">
					   <u:upload id="qua" auto="true" multiple="true" exts="png,jpeg,jpg,bmp" businessId="${id}" typeId="${purchaseTypeId}" sysKey="2"/>
					   <u:show showId="qua" businessId="${id}" typeId="${purchaseTypeId}" sysKey="2"/>
					</div>
				</li>
              </ul>
         </div>
        
        <div class="tc mt10 col-md-12 col-sm-12 col-xs-12 mt20">
          <button class="btn btn-windows save" type="button" onclick="start();">确认</button>
          <button class="btn btn-windows cancel"  type="button" onclick="cancel();">取消</button>
        </div>
      </form>  
</body>
</html>

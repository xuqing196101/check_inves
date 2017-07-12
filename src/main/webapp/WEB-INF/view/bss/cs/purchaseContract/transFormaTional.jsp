<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
  	<jsp:include page="/WEB-INF/view/common.jsp"/>
  	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
 	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <title>生成正式合同</title>
    
<script type="text/javascript">
function save(){
	var text = $("#post_attach_show_disFileId").find("a");
	var flag = true;
	if(text.length<=0){
		flag = false;
		layer.alert("请先上传附件",{offset: ['222px', '390px'], shade:0.01});
	}/*else{
		var houzhui = text.split(".");
		alert(houzhui[1]);
		if(houzhui[1].toLowerCase()=='bmp' || houzhui[1].toLowerCase()=='png' || houzhui[1].toLowerCase()=='gif' || houzhui[1].toLowerCase()=='jpg' || houzhui[1].toLowerCase()=='jpeg'){
			var fga = $("#formalGitAt").val();
			var fra = $("#formalReviewedAt").val();
			$("#fga").val(fga);
			$("#fra").val(fra);
			$("#contractType").submit();
		}else{
			layer.alert("上传的附件类型不正确",{offset: ['222px', '390px'], shade:0.01});
		}
	}*/
	if(flag){
		var fga = $("#formalGitAt").val();
		var fra = $("#formalReviewedAt").val();
		$("#fga").val(fga);
		$("#fra").val(fra);
		$("#contractType").submit();
	}
}

function cancel(){
	var id = "${id}";
	var supckid = "${supckid}";
	window.location.href="${pageContext.request.contextPath}/purchaseContract/createerrContractPage.html?ids="+id+"&supckid="+supckid;
}
	
</script>    
 </head>
  
  <body>
  
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">采购合同管理</a></li><li><a href="javascript:void(0);">正式合同生成</a></li></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <div class="container container_box pt20">
    <form id="contractType" action="${pageContext.request.contextPath}/purchaseContract/updateDraftById.html" method="post">
    <input type="hidden" value="${id}" id="ids" name="id"/>
    <input type="hidden" name="fga" id="fga" value=""/>
    <input type="hidden" name="fra" id="fra" value=""/>
    <input type="hidden" value="2" name="status"/>
     <div class="">
	   <ul class="ul_list mb20">
     	<li class="col-md-3 col-sm-6 col-xs-12 pl15">
	   <span class="col-md-12 col-sm-12 col-xs-12 p0"><div class="red star_red">*</div>合同批准文号：</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input class="contract_name" id="apN" name="approvalNumber" value="${purCon.approvalNumber}" type="text">
        <div class="cue">${ERR_approvalNumber}</div>
       </div>
	 </li>

     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 p0"><div class="red star_red">*</div>正式合同上报时间：</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input type="text" name="formalGitAt" id="formalGitAt"  readonly="readonly" value="<fmt:formatDate value="${purCon.formalGitAt}" pattern="yyyy-MM-dd HH:mm:ss" />" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate contract_name"/>
        <div class="cue">${ERR_formalGitAt}</div>
       </div>
	 </li> 
     <li class="col-md-3 col-sm-6 col-xs-12">
	   <span class="col-md-12 col-sm-12 col-xs-12 p0"><div class="red star_red">*</div>正式合同批复时间：</span>
	   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
        <input type="text" name="formalReviewedAt" readonly="readonly" value="<fmt:formatDate value="${purCon.formalReviewedAt}" pattern="yyyy-MM-dd HH:mm:ss" />" id="formalReviewedAt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate contract_name"/>
        <div class="cue">${ERR_formalReviewedAt}</div>
       </div>
	 </li> 
	 <li class="col-md-3 col-sm-6 col-xs-12">
	    <span class="col-md-12 col-sm-12 col-xs-12 p0"><div class="red star_red">*</div>附件上传：</span>
	    <div class="select_common input_group col-md-12 col-sm-12 col-xs-12 p0">
	        <u:upload id="post_attach_up" businessId="${attachuuid}" sysKey="${attachsysKey}" typeId="${attachtypeId}" auto="true" />
			<u:show showId="post_attach_show" businessId="${attachuuid}" sysKey="${attachsysKey}" typeId="${attachtypeId}"/>
		</div>
	 </li>
  	 </ul> 
	 <div  class="col-md-12 tc">
	   <input type="button" class="btn" onclick="save()" value="生成"/>
	   <input type="button" class="btn" onclick="cancel()" value="取消"/>
	</div>
  </div>
</form>
  </div>
</body>
</html>

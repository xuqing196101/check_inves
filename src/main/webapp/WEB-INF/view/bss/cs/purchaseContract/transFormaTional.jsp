<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/tld/upload" prefix="up" %>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
    <title>生成正式合同</title>
    
<script type="text/javascript">
function save(){
	var text = $("#post_attach_show_disFileId").find("a").text();
	var houzhui = text.split(".");
	if(houzhui[1].toLowerCase()=='bmp' || houzhui[1].toLowerCase()=='png' || houzhui[1].toLowerCase()=='gif' && houzhui[1].toLowerCase()=='jpg' && houzhui[1].toLowerCase()=='jpeg'){
		$("#contractType").submit();
	}else{
		layer.alert("上传的附件类型不正确",{offset: ['222px', '390px'], shade:0.01});
	}
}
	
</script>    
 </head>
  
  <body>
  
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">采购合同管理</a></li><li><a href="#">正式合同生成</a></li></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
   <div class="container container_box pt20">
    <form id="contractType" action="${pageContext.request.contextPath}/purchaseContract/updateDraftById.html" method="post">
    <input type="hidden" value="${id}" id="ids" name="id"/>
    <input type="hidden" value="2" name="status"/>
     <div class="">
	   <ul class="ul_list mb20">
     	<li class="col-md-3 p0">
	   <span class="col-md-12 p0"><div class="red star_red">*</div>合同批准文号：</span>
	   <div class="input-append">
        <input class="span2 contract_name" id="apN" name="approvalNumber" value="" type="text">
        <div class="cue">${ERR_approvalNumber}</div>
       </div>
	 </li>

     <li class="col-md-3 p0">
	   <span class="col-md-12 p0"><div class="red star_red">*</div>正式合同上报时间：</span>
	   <div class="input-append">
        <input type="text" name="formalGitAt" id="formalGitAt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate span2 contract_name"/>
        <div class="cue">${ERR_formalGitAt}</div>
       </div>
	 </li> 
     <li class="col-md-3 p0">
	   <span class="col-md-12 p0"><div class="red star_red">*</div>正式合同批复时间：</span>
	   <div class="input-append">
        <input type="text" name="formalReviewedAt" id="formalReviewedAt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate span2 contract_name"/>
        <div class="cue">${ERR_formalReviewedAt}</div>
       </div>
	 </li> 
	 <li class="col-md-3 p0 margin-0 ">
	    <span class="col-md-12"><div class="red star_red">*</div>附件上传：</span>
	    <div class="col-md-12 select_common">
	        <up:upload id="post_attach_up" businessId="${attachuuid}" sysKey="${attachsysKey}" typeId="${attachtypeId}" auto="true" />
			<up:show showId="post_attach_show" businessId="${attachuuid}" sysKey="${attachsysKey}" typeId="${attachtypeId}"/>
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
    
<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
</script>
     
</body>
</html>

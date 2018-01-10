<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
     <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		function goBack(){
			window.location.href = "${pageContext.request.contextPath}/contractAdvice/list.html";
		}
		
		function pass(id,status) {
			if (status == 4) {
				var reason = $("#reason").val();
				if (reason == null || reason == ''){
				  layer.msg("请填写审核意见");
				  return;
				}
			}
			var contractId = "${advice.purchaseContract.id}";
			var text = $("#show_" + contractId + "_disFileId").find("a");
			if (text.length<=0) {
				layer.msg("请上传附件");
				return;
			}
			$.ajax({
		 			url:"${pageContext.request.contextPath}/contractAdvice/pass.html",
		 			type:"post",
		 			dataType:"text",
		 			async: false,
		 			data:{
		 				"id" : id,
		 				"status" : status,
		 				"reason" : reason
		 			},
		 			success:function(data){
		 				if(data == "ok"){
		 					window.location.href = "${pageContext.request.contextPath}/contractAdvice/list.html";
		 				} else {
		 					layer.msg("失败");
		 				}
		 			}
		  	});
		}
	</script>
  </head>
  
  <body>
  	<div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">保障作业系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购合同管理</a>
          </li>
          <li class="active">
            <a href="javascript:jumppage('${pageContext.request.contextPath}/contractAdvice/list.html')">合同审核</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container container_box">
    	<div>
          <h2 class="count_flow"><i>1</i>合同基本信息</h2>
          <div class="ul_list mb0 p20">
            <table class="table table-bordered m0">
              <tbody>
                <tr>
                  <td width="10%" class="info">合同名称：</td>
                  <td width="25%">${advice.purchaseContract.name}</td>
                  <td width="10%" class="info">合同编号：</td>
                  <td width="25%">${advice.purchaseContract.code}</td>
                </tr>
                <tr>
                  <td class="info">项目名称：</td>
                  <td>${advice.project.name}</td>
                  <td class="info">合同金额：</td>
                  <td>${advice.purchaseContract.budget}</td>
                </tr>
                <tr>
                  <td class="info">提交人：</td>
                  <td>${advice.proposer}</td>
                  <td class="info">草案附件：</td>
                  <td class="tc"><u:show showId="show_${advice.id}" delete="false" businessId="${advice.purchaseContract.id}" sysKey="2" typeId="${uploadId}"/></td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="clear"></div>
        <div id="clear">
          <h2 class="count_flow"><i>2</i>合同审核信息</h2>
          <div class="ul_list mb0 p20">
          	<li class="col-md-3 col-sm-6 col-xs-12">
						 	<span class="zzzx col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>审核附件：</span>
					     	<div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
					        <u:upload id="upload_${advice.purchaseContract.id}" multiple="true" auto="true" businessId="${advice.id}" typeId="${auditId}" sysKey="2"/>
					      	<u:show showId="show_${advice.purchaseContract.id}" businessId="${advice.id}" sysKey="2" typeId="${auditId}"/>
					      </div>
						 </li>
						 <li class="col-md-12 col-sm-12 col-xs-12">
						   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">审核意见：</span>
						   <div class="col-md-12 col-sm-12 col-xs-12 p0">
					        <textarea class="col-md-12 col-sm-12 col-xs-12" name="reason" id="reason" style="height:130px" title="不超过800个字">${advice.reason}</textarea>
					       </div>
						 </li> 
          </div>
          <div class="clear"></div>
        </div>
        <div class="mt10 tc">
        	<c:if test="${status == 1 || status == 2}">
	        	<button class="btn" onclick="pass('${advice.id}','3')" type="button">审核通过</button>
	        	<button class="btn" onclick="pass('${advice.id}','4')" type="button">审核不通过</button>
        	</c:if>
          <button class="btn btn-windows back" onclick="goBack()" type="button">返回</button>
        </div>
    </div>
  </body>
</html>

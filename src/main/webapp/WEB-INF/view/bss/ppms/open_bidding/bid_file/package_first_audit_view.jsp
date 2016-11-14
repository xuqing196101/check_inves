<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    <title>包关联初审项</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
    <script type="text/javascript">
    	function confirmOk(obj, id){
      		layer.confirm('您已经确认了吗?', {title:'提示',offset: ['100px'],shade:0.01}, function(index){
	 			layer.close(index);
	 			$.ajax({
	 				url:"<%=basePath%>open_bidding/confirmOk.html?projectId="+id,
	 				dataType: 'json',
	 	       		success:function(result){
	                   $("#queren").after("<a href='javascript:volid(0);' >05、已确认</a>");
	                    $("#queren").remove();
	                },
	                error: function(result){
	                    layer.msg("确认失败",{offset: '222px'});
	                }
	 	       	});
	 		});
      }
	</script>
  </head>
  <body>
     <div class="col-md-12 p0">
	   <ul class="flow_step">
	     <li >
		   <a  href="${pageContext.request.contextPath}/open_bidding/firstAduitView.html?projectId=${projectId}" >01、符合性</a>
		   <i></i>
		 </li>
		 <li class="active">
		   <a  href="${pageContext.request.contextPath}/open_bidding/packageFirstAuditView.html?projectId=${projectId}" >02、符合性关联</a>
		   <i></i>							  
		 </li>
	     <li>
		   <a  href="${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${projectId}">03、评标细则</a>
		   <i></i>
		 </li>
		 <li>
		   <a  href="${pageContext.request.contextPath}/open_bidding/bidFileView.html?id=${projectId}" >04、招标文件</a>
		   <i></i>
		 </li>
		 <li>
		   <a  onclick="confirmOk(this,'${projectId}');" id="queren">05、确认</a>
		 </li>
	   </ul>
	 </div>
	 <div class="tab-content clear step_cont">
		 <!--第二个 -->
		 <div class=class="col-md-12 tab-pane active"  id="tab-1">
		 	<!-- <h1 class="f16 count_flow"><i>02</i>关联初审项</h1> -->
		 	   <div class="container clear margin-top-30" id="package">
				   <c:forEach items="${packageList }" var="pack" varStatus="p">
			           <h5>01、项目分包信息</h5>
				   		<form action="${pageContext.request.contextPath}/packageFirstAudit/relate.html" method="post" id="form1">
				   		<input type="hidden" name="packageIds" id="packageIds">
				   		<input type="hidden" id="packageId" name="packageId" value="${pack.id }"/>
				   		<input type="hidden" name="projectId" value="${projectId}">
				   		<span>包名:<span>${pack.name }</span>
				   		</span>
				   		<table class="table table-bordered table-condensed mt5">
				        	<thead>
				        		<tr class="info">
				          			<th class="w50">序号</th>
				         			<th>需求部门</th>
							        <th>物资名称</th>
							        <th>规格型号</th>
							        <th>质量技术标准</th>
							        <th>计量单位</th>
							        <th>采购数量</th>
							        <th>单价（元）</th>
							        <th>预算金额（万元）</th>
							        <th>交货期限</th>
							        <th>采购方式建议</th>
							        <th>供应商名称</th>
							        <th>是否申请办理免税</th>
								    <th>物资用途（进口）</th>
								    <th>使用单位（进口）</th>
				        		</tr>
				        	</thead>
				          <c:forEach items="${pack.projectDetails}" var="obj">
				            <tr class="tc">
					            <td class="w50">${obj.serialNumber }</td>
					            <td>${obj.department}</td>
					            <td>${obj.goodsName}</td>
					            <td>${obj.stand}</td>
					            <td>${obj.qualitStand}</td>
					            <td>${obj.item}</td>
					            <td>${obj.purchaseCount}</td>
					            <td>${obj.price}</td>
					            <td>${obj.budget}</td>
					            <td>${obj.deliverDate}</td>
					            <td>${obj.purchaseType}</td>
					            <td>${obj.supplier}</td>
					            <td>${obj.isFreeTax}</td>
						        <td>${obj.goodsUse}</td>
						        <td>${obj.useUnit}</td>
				            </tr>
				         </c:forEach> 
			     	 	</table>
					       <table class="table table-bordered table-condensed mt5">
				 	            <h5>02、项目初审项信息</h5>
							    <thead>
							      <tr>
							        <th>初审项名称</th>
							        <th>要求类型</th>
							        <th>创建人</th>
							      </tr>
							     </thead>
							      <c:forEach items="${list }" var="l" varStatus="vs">
								      <thead>
								       <tr>
								         <c:forEach items="${idList }" var="id" varStatus="p">
									 	      <c:if test="${id.firstAuditId==l.id && id.packageId==pack.id }">
													<td align="center">${l.name } </td>
											        <td align="center">${l.kind }</td>
											        <td align="center">${l.creater }</td>
											  </c:if>
									 	 </c:forEach>
								      </tr>
								      </thead>
						      	  </c:forEach>
				   		  </table>
		   		      </form>
				   </c:forEach>
			   </div> 
			<div class="container clear margin-top-30" id="package">
		 	</div>	
		 </div>
     </div>
  </body>
</html>

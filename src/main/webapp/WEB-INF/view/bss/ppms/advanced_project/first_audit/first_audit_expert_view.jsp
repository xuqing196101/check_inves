<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="${pageContext.request.contextPath}/">
    <title>初审明细</title>
	<script type="text/javascript">
		//查看理由
	    function reason(firstAuditId,supplierId,expertId){
		   var projectId="${extension.projectId }";
		   var packageId="${extension.packageId }";
		   //查找该数据的理由
		   var reason;
		   $.ajax({
		    	url:'${pageContext.request.contextPath}/reviewFirstAudit/getReason.do',
		    	data:{'projectId':projectId,'packageId':packageId,'firstAuditId':firstAuditId,'supplierId':supplierId,'expertId':expertId},
		    	type:'post',
		    	success:function(obj){
		    		reason=obj.rejectReason;
		    		if(reason!=null){
		    		layer.open({
		    			   type: 1,
		    			   area: ['420px', '240px'], //宽高
		    			   shade: false,
		    			   shift: 3,
		    			   title: '理由', //不显示标题
		    			   offset: '30px',
		    			   content: reason, //捕获的元素
		    			 });
		    		}else{
		    			 layer.msg('没有理由!');
		    		}
		    	},
		    	error:function(obj){}
		    	
		    });
	    }
	    //返回
	  	function goBack(){
	  		var projectId = $("#projectId").val();
	  		var flowDefineId = $("#flowDefineId").val();
	  		var packageId = $("#packageId").val();
	  		window.location.href="${pageContext.request.contextPath}/adPackageExpert/firstAuditView.html?projectId="+projectId+"&packageId="+packageId+"&flowDefineId="+flowDefineId;
	  	}
  </script>
  </head>
  
  <body>
	<!-- <div class="container" > -->
	    <h2 class="list_title">${expert.relName}初审详情</h2>
	    <input type="hidden" id="projectId" value="${projectId}">
	   	<input type="hidden" id="flowDefineId" value="${flowDefineId}">
	   	<input type="hidden" id="packageId" value="${packageId}">
   	   	<div class="over_scroll">
		   <table class="table table-bordered table-condensed table-hover col-md-12 col-sm-12 col-xs-12 p0 space_nowrap" id="table2">
		   		<thead>
		   		  <th class="w50 info">序号</th>
		   		  <th class="info">初审项</th>
		   		  <c:forEach items="${extension.supplierList}" var="supplier" varStatus="vs">
		   		  	<c:if test="${fn:contains(supplier.packages,extension.packageId)}">
			   		    <th class="info">
			   		      ${supplier.suppliers.supplierName }
			   		    </th>
		   		    </c:if>
		   		  </c:forEach>
		   		</thead>
 	            <c:forEach items="${extension.firstAuditList}" var="first" varStatus="vs">
			      	<tr>
			      	  <td class="tc w30">${vs.count} </td>
			      	  <td class="">${first.name }</td>
			      	  <c:forEach items="${extension.supplierList }" var="supplier" varStatus="v">
			      	  	<c:if test="${fn:contains(supplier.packages,extension.packageId)}">
	   		                <td class="tc">
	   		                	<c:forEach items="${reviewFirstAuditList }" var="r" >
		   		                  <c:if test="${r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq expert.id && r.isPass==0 }">合格</c:if>
		   		                  <c:if test="${r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq expert.id && r.isPass==1 }">
		   		                  	不合格
		   		                  	<a href="javascript:void(0);" onclick="reason('${first.id}','${supplier.suppliers.id }','${r.expertId}');">查看理由</a>
		   		                  </c:if>
		   		                </c:forEach>	
		   		            </td>
   		                </c:if>
   		               </c:forEach>
			      </tr>
 	           </c:forEach>
		   </table>
	   </div>
	   <div class="col-md-12 pl20 mt10 tc">
		    <button class="btn btn-windows back" onclick="goBack();" type="button">返回</button>
	   	</div>
  </body>
</html>

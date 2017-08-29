<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
     <%@ include file="/WEB-INF/view/common.jsp" %>
    <title>专家评审详情</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  <script type="text/javascript">
  	//查看理由
   function reason(firstAuditId,supplierId,expertId){
	   var projectId="${extension.projectId}";
	   var packageId="${extension.packageId}";
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
	    			   skin: 'layui-layer-rim', //加上边框
	    			   area: ['420px', '240px'], //宽高
	    			   shade: false,
	    			   shift: 3,
	    			   offset: '100px',
	    			   title: '理由', //不显示标题
	    			   content: reason, //捕获的元素
	    			 });
	    		}else{
	    			 layer.msg('没有理由！',{offset: '100px'});
	    		}
	    	},
	    	error:function(obj){}
	    	
	    });
	   
   }
  	
 
   function printResult(projectId,packageId,auditType){
   	   window.location.href="${pageContext.request.contextPath}/adPackageExpert/openAllPrintWord.html?projectId="+projectId+"&packageId="+packageId+"&auditType="+auditType;

   }
   
  </script>
  <body>
    <div class="container">
		 
		<div class="container clear" id="package">
			<div class="mt5 mb5 fr">
			    <button class="btn" onclick="printResult('${project.id}','${pack.id}','${auditType}');" type="button">打印检查数据</button>
		   	</div>
		    <div class="headline-v2">
		   		<h2>检查数据</h2>
		   	</div>
		   	<div class="mt10 tc">
		   		<h2>${project.name}--${pack.name}</h2>
		   	</div>
		   	<c:forEach items="${extensions}" var="extension">
				<form action="" method="post" class="mt50">
				   <h4>评审人员：${extension.expert.relName}</h4>
			   	   <div class="content table_box over_scroll">
					   <table class="table table-bordered table-condensed table-hover m_resize_table_width" id="table2">
					   		<thead>
					   		  <th class="info space_nowrap" >资格性和符合性检查项</th>
					   		  <c:set var="suppliers" value="0" />
					   		  <c:forEach items="${extension.supplierList}" var="supplier" varStatus="vs">
					   		  	<c:if test="${fn:contains(supplier.packages,extension.packageId)}">
						   		    <c:set var="suppliers" value="${suppliers+1}" />
						   		    <th class="info" width="120">
						   		      ${supplier.suppliers.supplierName }
						   		    </th>
					   		    </c:if>
					   		  </c:forEach>
					   		</thead>
					   		<c:forEach items="${extension.dds}" var="d">
					   			<tr><td class="info" colspan="${suppliers+1}"><b>${d.name}</b></td></tr>
					   			<c:forEach items="${extension.firstAuditList}" var="first" varStatus="vs">
							      	<c:if test="${first.kind == d.id}">
								      	<tr>
								      	  <td class="w260"><a href="javascript:void(0);" title="${first.content}">${first.name}</a></td>
								      	  <c:forEach items="${extension.supplierList}" var="supplier" varStatus="v">
								      	  	<c:if test="${fn:contains(supplier.packages,extension.packageId)}">
						   		                <td class="tc space_nowrap">
						   		                    <c:forEach items="${reviewFirstAuditList}" var="r" >
						   		                      <c:if test="${extension.isSubmit == 0 && r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq extension.expert.id}">暂无</c:if>
						   		                      <c:if test="${extension.isSubmit == 1 && r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq extension.expert.id && r.isPass==0 }">合格</c:if>
						   		                      <c:if test="${extension.isSubmit == 1 && r.supplierId eq supplier.suppliers.id && r.firstAuditId eq first.id && r.expertId eq extension.expert.id && r.isPass==1 }">
						   		                    	  <div class="red">不合格</div>
						   		                    	 <a id="notPassReason_${v.index}_${vs.index}" name="notPassReason" href="javascript:void(0);" onclick="reason('${first.id}','${supplier.suppliers.id }','${extension.expert.id}');">查看理由</a>
						   		                      </c:if>
						   		                    </c:forEach>
						   		                </td>
					   		                </c:if>
					   		              </c:forEach>
								      	</tr>
							      	</c:if>
			 	            	</c:forEach>
					   		</c:forEach>
					   </table>
				   </div>
					   <h4>专家签名：</h4>
				</form>
		   	</c:forEach>
		</div> 
	</div>
	
	<script type="text/javascript">
		function resize_table_width() {
	        $('.m_resize_table_width').each(function () {
	            var table_width = 0;
	            var parent_width = $(this).parent().width();
	            $(this).find('thead th').each(function () {
	            	if(typeof($(this).attr('width')) != 'undefined') {
	            		table_width +=  parseInt($(this).attr('width'));
		            }
	            });
	            if (table_width > parent_width) {
		            $(this).css({
		                width: table_width,
		                maxWidth: table_width
		            });
	            }
	        });
	    }
	    $(function () {
	        resize_table_width();
	    });
	   	</script>
  </body>
</html>

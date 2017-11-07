<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    
    <title>专家咨询委员会</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <script type="text/javascript">
      function submit1(){
		  layer.confirm('您确定要提交吗?', {title:'提示',offset: '100px',shade:0.01}, function(index){      
	      	  $.ajax({
					url:'${pageContext.request.contextPath}/packageExpert/saveCheck.do',
					data:$("#saveCheck").serialize(),
					type:"post",
					success:function(data){
						if (data == 'ok') {
							layer.msg("提交成功",{offset: '400px'});
							window.opener.callback();
	      	  				window.close();//当前页面关闭
						} else if (data == 'unCheck') {
							layer.msg("还有未进行确定结果的供应商",{offset: '400px'});
						} else if (data == 'unSubmit') {
							layer.msg("还有专家未提交评审",{offset: '400px'});
						} else {
							layer.msg("提交失败，请稍后重试！",{offset: '400px'});
						}
					}
			  });
		  });
      }
      
      function closeWin(){
      	  window.opener.callback();
      	  window.close();//当前页面关闭
      }
      

	  function printResult(projectId,packageId,flag){
	     window.location.href="${pageContext.request.contextPath}/packageExpert/expertConsultWord.html?projectId="+projectId+"&packageId="+packageId+"&flag="+flag;
	  }
	  
  </script>
  <body>
  	<div class="container">
    	<div class="mt5 mb5 fr">
		    <!-- <button class="btn" onclick="printdiv('div_print');" type="button">打印</button> -->
	   	    <button class="btn" onclick="printResult('${project.id}','${pack.id}','${flag}');" type="button">打印</button>
	   	    
	   	</div>
	   	<div class="headline-v2">
	   		<h2>专家咨询委员会</h2>
	   	</div>
	   	<div id="div_print">
	   	<div class="mt10 tc">
	   		<h2>${project.name}--${pack.name}</h2>
	   	</div>
	   	<div class="over_auto col-md-12 col-xs-12 col-sm-12 p0 m0">
	  	<table id="tabId" class="table table-bordered table-condensed table-hover p0 space_nowrap">
 		  <thead>
		      <tr>
		        <th class="info" rowspan="2">评审内容/供应商</th>
		        <c:set var="suppliers" value="0" />
		        <c:forEach items="${saleTenderList}" var="supplier" varStatus="vs">
		        	<c:set var="suppliers" value="${suppliers+1}" />
		        	<th class="info" colspan="2">${supplier.suppliers.supplierName}</th>
		        </c:forEach>
		      </tr>
		      <tr>
		      	<c:forEach items="${saleTenderList}" var="supplier" varStatus="vs">
		        	<th class="info" >合格</th>
		        	<th class="info" >不合格</th>
		        </c:forEach>
		      </tr>
		      
	      </thead>
	      <tbody id="content">
	      <c:forEach items="${dds}" var="d">
		  	  <tr><td class="info" colspan="${(suppliers+1)*2}"><b>${d.name}</b></td></tr>
		  	  <c:forEach items="${firstAudits}" var="first" varStatus="vs">
		  	  	  <c:if test="${first.kind == d.id}">
		  	  	  	 <tr>
		  	  	  	 	<td class="w260"><a href="javascript:void(0);" title="${first.content}">${first.name}</a></td>
		  	  	  	 	<c:forEach items="${saleTenderList}" var="supplier">
		  	  	  	 		<td class="tc">
		  	  	  	 			<c:set value="0" var="isPassNum"/>
		  	  	  	 			<c:forEach items="${reviewFirstAudits}" var="rfa">
		  	  	  	 				<c:if test="${rfa.firstAuditId eq first.id && rfa.supplierId eq supplier.suppliers.id && rfa.isPass==0}">
		  	  	  	 					<c:set value="${isPassNum+1}" var="isPassNum"/>
		  	  	  	 				</c:if>
		  	  	  	 			</c:forEach>
		  	  	  	 			${isPassNum}人
		  	  	  	 		</td>
		  	  	  	 		<td class="tc">
		  	  	  	 			<c:set value="0" var="noPassNum"/>
		  	  	  	 			<c:forEach items="${reviewFirstAudits}" var="rfa">
		  	  	  	 				<c:if test="${rfa.firstAuditId eq first.id && rfa.supplierId eq supplier.suppliers.id && rfa.isPass==1}">
		  	  	  	 					<c:set value="${noPassNum+1}" var="noPassNum"/>
		  	  	  	 				</c:if>
		  	  	  	 			</c:forEach>
		  	  	  	 			${noPassNum}人
		  	  	  	 		</td>
		  	  	  	 	</c:forEach>
		  	  	  	 </tr>
		  	  	  </c:if>
		  	  </c:forEach>
		  </c:forEach>
		  <tr>
	  	  	  <th class="info tc">评审结果</th>
	  	  	  <c:if test="${flag != '1' }">
		  	  	  <form id="saveCheck" action="" method="post">
		  	  		  <input type="hidden" name="supplierNum" value="${saleTenderList.size()}">
		  	  		  <input type="hidden" name="projectId" value="${project.id}">
		  	  		  <input type="hidden" name="packageId" value="${pack.id}">
		  	  		  <c:forEach items="${saleTenderList}" var="st" varStatus="i">
			        	<td class="info tc" colspan="2">
			        		<input type="radio" name="checkName${i.index}" value="${st.id},100" ><span class="green">合格</span>
			        		<input type="radio" name="checkName${i.index}" value="${st.id},0" ><span class="red">不合格</span>
			        	</td>
		        	  </c:forEach>
	        	  </form>
        	  </c:if>
        	  <c:if test="${flag == '1' }">
        	  	  <c:forEach items="${saleTenderList}" var="st" varStatus="i">
		        	<td class="info tc" colspan="2">
		        		<c:if test="${st.economicScore == 100 && st.technologyScore == 100 }">
		        			<span class="green">合格</span>
		        		</c:if>
		        		<c:if test="${st.economicScore == 0 && st.technologyScore == 0 }">
		        			<span class="red">不合格</span>
		        		</c:if>
		        	</td>
	        	  </c:forEach>
        	  </c:if>
	  	  </tr>
	     </tbody>
  		</table>
  		</div>
  		</div>
  	  </div>
  	<div class="col-md-12 col-sm-12 col-xs-12 clear tc mt10">
  		<c:if test="${flag != '1' }">
			<input type="button" onclick="submit1();"  value="提交" class="btn btn-windows git">
  		</c:if>
		<input class="btn btn-windows back" value="关闭" type="button" onClick="closeWin();"><br/>
	</div>
  </body>
</html>

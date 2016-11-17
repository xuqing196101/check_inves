<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>项目评分</title>
<script type="text/javascript">
	
	function audit(obj,scoreModelId,supplierId){
		var expertValue = $(obj).val();
		alert(scoreModelId);
		alert(supplierId);
		$.ajax({
			url:'',
			data:{"expertValue":expertValue,"scoreModelId":scoreModelId,"supplierId":supplierId},
			type:"post",
			success:function(data){
				
			}
			
		});
	}
	//提交
	function submit1(){
		$("#form1").submit();
		
	}
  </script>
  </head>
  
  <body>
  
						 <div class="tab-content clear step_cont">
						 <div class=class="col-md-12 tab-pane active"  id="tab-1">
						 	   <div class="container clear margin-top-30" id="package">
						 	 
						 	       <table >
						 	         <thead>
						 	            <th><h2>项目名称：</h2></th><th><h2>${project.name }（${pack.name }）&nbsp;&nbsp;&nbsp;&nbsp;</h2></th>
						 	            
						 	            <th><h2>编号：</h2></th><th><h2>${project.projectNumber }</h2></th>
						 	            <tr>
						 	              <th></th>
						 	            </tr>
						 	         </thead>
						 	       </table>
									   <form action="${pageContext.request.contextPath}/expert/saveGrade.html" id="form1" method="post" >
									   <!--项目id  -->
								   	   <input type="hidden" name="projectId" id="projectId" value="${projectId }">
									   <!--包id  -->
								   	   <input type="hidden" name="packageId" id="packageId" value="${packageId }">
								   	   <div style="overflow:scroll;">
										   <table class="table table-bordered table-condensed mt5" id="table2" style="white-space: nowrap;overflow: hidden;word-spacing: keep-all;" >
										   		<thead>
										   		  <tr>
										   		  <th></th>
										   		  <th></th>
										   		  <th></th>
										   		  <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
										   		    <th colspan="6">
										   		      ${supplier.supplierName }
										   		    </th>
										   		  </c:forEach>
										   		   </tr>
										   		   <tr>
										   		     <th>序号</th>
										   		     <th>评审项目</th>
										   		     <th>计分模型</th>
										   		      <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
										   		        <th>标准分值</th>
										   		        <th>投标人填写</th>
										   		        <th>投标文件页码</th>
										   		        <th>系统初始得分</th>
										   		        <th>评委填写</th>
										   		        <th>评审得分</th>
										   		      </c:forEach>
										   		   </tr>
										   		</thead>
										 	          <c:forEach items="${list }" var="l" varStatus="vs">
										 	            <tr align="center">
										 	           <td>${vs.count }</td>
										 	           <td>${l.markTermName }</td>
										 	           <td>
										 	           <c:if test="${l.typeName == 0}">模型1:是否判断</c:if>
										 	           <c:if test="${l.typeName == 1}">模型2:按项加减分</c:if>
										 	           <c:if test="${l.typeName == 2}">模型3:评审数额最高递减</c:if>
										 	           <c:if test="${l.typeName == 3}">模型4:评审数额最低递增</c:if>
										 	           <c:if test="${l.typeName == 4}">模型5:评审数额高计算</c:if>
										 	           <c:if test="${l.typeName == 5}">模型6:评审数额低计算</c:if>
										 	           <c:if test="${l.typeName == 6}">模型7:评审数额低区间递增</c:if>
										 	           <c:if test="${l.typeName == 7}">模型8:评审数额高区间递减</c:if>
										 	           </td>
										 	           <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
										 	                 <td>${l.standardScore }</td>
											 	             <td>${l.supplierValue }</td>
											 	             <td>${l.supplierValue }</td>
											 	             <td>${l.initScore }</td>
											 	             <c:choose>
											 	              <c:when test="${l.typeName == '0' }">
											 	               <td>
											 	                 <select name="expertValue">
											 	                   <option value="-1">请选择</option>
											 	                   <option value="1">是</option>
											 	                   <option value="0">否</option>
											 	                 </select>
											 	               </td>
											 	              </c:when>
											 	              <c:otherwise>
											 	                 <td><input type="text" onblur="audit(this,'${l.scoreModelId}','${supplier.id }')" name="expertValue" style="width: 50px;"/> </td>
											 	              </c:otherwise>
											 	             </c:choose>
											 	            
											 	             <td><input type="text" name="expertScore" readonly="readonly"  style="width: 50px;"/></td>
										 	           </c:forEach>
												       </tr> 
												    </c:forEach>
										   </table>
													      	<input type="button" onclick="submit1();"  value="提交" class="btn btn-windows git">
													      	<input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'"><br/><br/><br/>
									  
									   </div>
									   </form>
							   </div> 
						</div>
                      </div>
  </body>
  
</html>

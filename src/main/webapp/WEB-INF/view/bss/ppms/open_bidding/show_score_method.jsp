<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
	<!--<![endif]-->

	<head>
	<%@ include file="../../../common.jsp"%>
	<script type="text/javascript">
		function save(obj){
		    var y;  
        	oRect = obj.getBoundingClientRect();  
        	y=oRect.top-150;  
        	x=oRect.left;
			form1.submit();
		}
	</script>
	</head>
	<body>
		<!-- 修改订列表开始-->
		<div class="">
			<div>
				<div class="headline-v2">
					<h2>查看评标方法</h2>
				</div>
				<table class="table table-bordered">
					<tbody>
						    <c:if test="${bidMethod.typeName ==1 || bidMethod.typeName ==3}">
						     <tr>
							    <td class="bggrey">评分方法：</td>
								<td>
									<c:forEach items="${ddList}" var="list" varStatus="vs">
											<c:if test="${vs.index == bidMethod.typeName}">${list.name}</c:if>
									</c:forEach>
								</td>
							 </tr>
						    </c:if>
						    
						    <c:if test="${bidMethod.typeName == 0}">
						      <tr>
							    <td class="bggrey">评分方法：</td>
								<td>
									<c:forEach items="${ddList}" var="list" varStatus="vs">
											<c:if test="${vs.index == bidMethod.typeName}">${list.name}</c:if>
									</c:forEach>
								</td>
								<td class="bggrey ">下浮比例：</td>
								<td>${bidMethod.floatingRatio}%</td>
							  </tr>
								<tr>
								<td colspan="3" class="bggrey ">供应商报价不得超过有效供应商报价平均值百分比：</td>
								<td>${bidMethod.valid }%</td></tr>
						    </c:if>
						    
						    <c:if test="${bidMethod.typeName == 2}">
						        <tr>
						    	<td class="bggrey">评分方法：</td>
								<td>
									<c:forEach items="${ddList}" var="list" varStatus="vs">
											<c:if test="${vs.index == bidMethod.typeName}">${list.name}</c:if>
									</c:forEach>
								</td>
								</tr>
								<tr>
								<td class="bggrey ">供应商报价不得超过有效供应商报价平均值百分比：</td>
								<td>${bidMethod.valid}%</td>
								</tr>
								<tr>
								<td class="bggrey ">商务技术评分不得低于上午技术评分百分比：</td>
								<td>${bidMethod.business}%</td></tr>
						    </c:if>
						
					</tbody>
				</table>
				<div class="col-md-12 tc mt20">
					<button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
				</div>
			</div>
		</div>
	</body>

</html>
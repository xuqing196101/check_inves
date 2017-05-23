<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<script type="text/javascript">
</script>
</head>

<body>
		<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0);"> 首页</a></li>
				<li><a href="javascript:void(0);">保障作业系统</a></li>
				<li><a href="javascript:void(0);">采购计划管理</a></li>
				<li class="active"><a href="javascript:void(0);">采购计划查看明细</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
 
	<div class="container">
	 <div class="col-md-12 pl20 mt10">
	 <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'">
	 </div>
		<div class="content table_box over_auto">
        <table class="table table-bordered table-condensed table-hover table-striped table_wrap">
					<thead>
						<tr>
							<th class="info w50">序号</th>
							<th class="info">需求部门</th>
							<th class="info">物资类别<br>及名称</th>
							<th class="info">规格型号</th>
							<th class="info">质量技术标准（技术参数）</th>
							<th class="info">计量单位</th>
							<th class="info">采购数量</th>
							<th class="info">单位（元）</th>
							<th class="info">预算金额（万元）</th>
							<th class="info">交货期限</th>
							<th class="info">采购方式建议</th>
							<th class="info">供应商名称</th>
							<th class="info">是否申请办理免税</th>
							<th class="info">物资用途（仅进口）</th>
							<th class="info">使用单位（仅进口）</th>
							<th class="info">备注</th>
						</tr>
					</thead>

				 <c:forEach items="${list}" var="obj">
						<tr>
							<td class="tc w50">${obj.seq } 
							</td>
							<td class="tl pl20"> ${obj.department }  </td>
							<td class="tl pl20">${obj.goodsName }</td>
							<td class="tl pl20"> ${obj.stand }</td>
							<td class="tl pl20"> ${obj.qualitStand }</td>
							<td class="tl pl20"> ${obj.item }</td>
							<td class="tl pl20">${obj.purchaseCount }</td>
							<td class="tr pr20">${obj.price }</td>
							<td class="tr pr20">${obj.budget }</td>
							<td class="tl pl20">${obj.deliverDate }</td>
							<td class="tl pl20">
							  <c:forEach items="${kind}" var="kind" >
			                    <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
			                  </c:forEach>
							</td>
							<td class="tl pl20">${obj.supplier }</td>
							<td class="tl pl20">${obj.isFreeTax }</td>
							<td class="tl pl20">${obj.goodsUse }</td>
							<td class="tl pl20">${obj.useUnit }</td>
							<td class="tl pl20">${obj.memo }
						
							</td>
						</tr>

					</c:forEach>  
					</table>
		</div>
	</div>

</body>
</html>

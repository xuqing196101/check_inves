<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
	<%@ include file="/WEB-INF/view/common.jsp"%>
	<title>项目管理</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="${pageContext.request.contextPath}/js/ses/sms/multiple_quotes/flow_common.js"></script>
 	<script src="${pageContext.request.contextPath}/js/ses/sms/multiple_quotes/open_bid.js"></script> 
</head>
	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0);"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0);">我的项目</a>
					</li>
					<li>
						<a href="javascript:void(0);">标书管理</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<%@ include file="/WEB-INF/view/ses/sms/multiple_quotes/flow_common.jsp" %>
		<!-- 开标一览表-->
		<div class="container" >
		  <div class="headline-v2">
                  <h2>开标一览表</h2>
           </div>  
			<div class="content table_box">
            <h2 class="count_flow"><i>1</i>基本信息</h2>
			  <form  action="" id="myForm" method="post" class="mb0">
			     <table class="table table-bordered mt10">
			      <tbody>
			      <tr>
                      <td class="info">投标人全称:</td>
                      <td class="p0">${supplier.supplierName }</td>
                      <td class="bggrey">项目名称:</td>
                      <td class="p0">${project.name}</td>
                    </tr>
                    <tr>
                      <td class="info">项目编号:</td>
                      <td class="p0">${project.projectNumber}</td>
                      <td class="info">金额单位:</td>
                      <td class="p0">元</td>
                    </tr>
				 </tbody>
				</table>
				<h2 class="count_flow"><i>2</i>项目明细</h2>
				<c:set var="index" value="-1"/>
				<c:forEach items="${saleTenderList}" var="list" varStatus="st">
         <c:choose>
       <c:when test="${st.index>0}">
       <div class="count_flow shrink hand" onclick="ycDiv(this,'${st.index}')">
        <span class="f16 b">包名：</span>
        <span class="f14 blue" id="packageName">${list.packageNames}</span>
        </div>
        <div class="p0${st.index} hide">
       </c:when>
       <c:otherwise>
       <div class="count_flow spread hand" onclick="ycDiv(this,'${st.index}')">
        <span class="f16 b">包名：</span>
        <span class="f14 blue" id="packageName">${list.packageNames}</span>
        <tr colspan="3"><span class="f14 red">提示：</span></tr>
        </div>
        <div class="p0${st.index}">
       </c:otherwise>
        </c:choose>
        <table class="table table-bordered left_table">
        </thead>
         <tr>
            <th class="info">序号</th>
            <th class="info">货物名称</th>
            <th class="info">品牌</th>
            <th class="info">规格型号</th>
            <th class="info">计量单位</th>
            <th class="info">数量</th>
            <th class="info">单位（含税）</th>
            <th class="info">金额（含税）</th>
            <th class="info">交货时间</th>
            <th class="info">备注</th>
          </tr>
        </thead>
          <c:forEach items="${list.projectDetail}" var="detail" varStatus="ls">
        <c:set var="index" value="${index+1 }"/>
          <tbody>
            <tr>
            <td class="p0">${index+1 }</td>
            <td class="p0"><input  class="m0" readonly="readonly" id="goodsName" value="${detail.goodsName}" type="text" class="m0" /></td>
            <td class="p0"><input  class="m0" id="brand" value="${detail.brand}" type="text" /></td>
            <td class="p0"><input type="text"  id="stand" readonly="readonly" value="${detail.stand}" /></td>
            <td class="p0"><input  id="item" class="m0" readonly="readonly" value="${detail.item}" type="text" /></td>
            <td class="p0"><input  class="m0" id="purchaseCount" value="${detail.purchaseCount}" type="text" readonly="readonly"/>
                      <input name="openBidInfoList[${index}].projectsDetailId"  type="hidden" value="${detail.id}" /></td>
            <td class="p0"><input name="openBidInfoList[${index}].unitPrice" class="m0" id="unitPrice" value="${detail.price}" type="text" /></td>
            <td class="p0"><input  class="m0" id="budget" value="" type="text" readonly="readonly"/></td>
            <td class="p0"><input name="openBidInfoList[${index}].deliveryTheGoods" class="m0" id="deliveryTheGoods" value="${detail.deliverDate}" type="text" /></td>
            <td class="p0"><input name="openBidInfoList[${index}].remark" id="remark" value="${detail.memo}" type="text" class="m0" /></td>
            </tr>
          </tbody>
                  <%-- <tbody>
                     
                    <tr>
                      <td class="bggrey">货物名称:</td>
                      <td class="p0"><input  class="m0" readonly="readonly" id="goodsName" value="${detail.goodsName}" type="text" class="m0" /></td>
                      <td class="bggrey">品牌:</td>
                      <td class="p0"><input  class="m0" id="brand" value="${detail.brand}" type="text" /></td>
                    </tr>
                    <tr>
                      <td class="bggrey">规格型号:</td>
                      <td class="p0"><input type="text"  id="stand" readonly="readonly" value="${detail.stand}" />
                      </td>
                      <td class="bggrey">计量单位:</td>
                      <td class="p0"><input  id="item" class="m0" readonly="readonly" value="${detail.item}" type="text" /></td>
                    </tr>
                    <tr>
                      <td class="bggrey">数量:</td>
                      <td class="p0">
                        <input  class="m0" id="purchaseCount" value="${detail.purchaseCount}" type="text" readonly="readonly"/>
                      </td>
                      <input name="openBidInfoList[${index}].projectsDetailId"  type="hidden" value="${detail.id}" />
                      <td class="bggrey"><span class="red star_red">*</span>单价(含税):</td>
                      <td class="p0"><input name="openBidInfoList[${index}].unitPrice" class="m0" id="unitPrice" value="${detail.price}" type="text" /></td>
                    </tr>
                    <tr>
                      <td class="bggrey">金额(含税):</td>
                      <td class="p0">
                        <input  class="m0" id="budget" value="" type="text" readonly="readonly"/>
                      </td>
                      <td class="bggrey"><span class="red star_red">*</span>交货时间:</td>
                      <td class="p0">
                        <input name="openBidInfoList[${index}].deliveryTheGoods" class="m0" id="deliveryTheGoods" value="${detail.deliverDate}" type="text" /></td>
                    </tr>
                    <tr>
                      <td class="bggrey">备注:</td>
                      <td colspan="3" class="p0"><input name="openBidInfoList[${index}].remark" id="remark" value="${detail.memo}" type="text" class="m0" /></td>
                    </tr>
                  </tbody> --%>
                    </c:forEach>
                </table>
				    </div>
                </c:forEach>
				<!-- <span class="ml10">投标人全称：政法大学（盖章）</span><span class="ml100">法定代表人（或授权代表）：宋彪伟（签字）</span><span class="ml200">2016年  12月  13日</span> -->
			      <div class="col-md-12 clear tc mt10">
							  <button class="btn btn-windows save mb20" type="submit" onclick="isTemporary()">暂存</button>
							  <button class="btn btn-windows apply mb20" type="submit" onclick="">确定</button>
							  <button class="btn btn-windows back mb20" type="button" onclick="history.go(-1)">返回</button>
             </div>
			</form>
			</div>
		</div>
	</body>

</html>
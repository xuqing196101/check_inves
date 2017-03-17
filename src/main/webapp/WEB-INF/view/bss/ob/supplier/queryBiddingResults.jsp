<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<%@ include file ="/WEB-INF/view/common/webupload.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>供应商报价页面</title>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">竞价结果查询</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    
    <!-- 修改订列表开始-->
   <div class="container container_box">
   <div>
    <h2 class="count_flow">竞价基本信息</h2>
		<table class="table table-bordered mt10">
			    <tbody>
				  <tr>
				    <td class="tc">竞价标题</td>
				    <td class="tc">${obProject.name }</td>
				    <td class="tc">交货截止时间</td>
				    <td class="tc">
				    	<fmt:formatDate value="${obProject.deliveryDeadline }" pattern="yyyy-MM-dd HH:ss:mm"/>
				    </td>
				  </tr>
				  <tr>
				    <td class="tc">交货地点</td>
				    <td class="tc">${obProject.deliveryAddress }</td>
				    <td class="tc">成交供应商数</td>
				    <td class="tc">${obProject.tradedSupplierCount }</td>
				  </tr>
				  <tr>
				    <td class="tc">运杂费</td>
				    <td class="tc">${obProject.transportFees }</td>
				    <td class="tc">需求单位</td>
				    <td class="tc">${obProject.demandUnit }</td>
				  </tr>
				  <tr>
				    <td class="tc">联系人</td>
				    <td class="tc">${obProject.contactName }</td>
				    <td class="tc">联系电话</td>
				    <td class="tc">${obProject.contactTel }</td>
				  </tr>
				  <tr>
				    <td class="tc">采购机构</td>
				    <td class="tc">${obProject.orgId }</td>
				    <td class="tc">采购联系人</td>
				    <td class="tc">${obProject.orgContactName }</td>
				  </tr>
				  <tr>
				    <td class="tc">联系电话</td>
				    <td class="tc">${obProject.orgContactTel }</td>
				    <td class="tc">竞价开始时间</td>
				    <td class="tc">
				    	<fmt:formatDate value="${obProject.startTime }" pattern="yyyy-MM-dd HH:ss:mm"/>
				    </td>
				  </tr>
				  <tr>
				    <td class="tc">竞价结束时间</td>
				    <td class="tc">
				    	<fmt:formatDate value="${obProject.endTime }" pattern="yyyy-MM-dd HH:ss:mm"/>
				    </td>
				  </tr>
				  <tr>
				    <td class="tc">竞价内容</td>
				    <td class="tc" colspan="3" style="height:130px">
				    	${obProject.content }
				    </td>
				  </tr>
				  <tr>
				    <td class="tc">竞价文件</td>
				    <td class="tc">${obProject.attachmentId }.pdf</td>
				    <td class="tc">
				    	<button class="btn" onclick="download('${file.id}','2','','')"> 查看 </button>
				    </td>
				  </tr>
				 </tbody>
			 </table>
  </div> 
  <div class="clear" ></div>
  <c:forEach items="${selectInfoByPID }" var="supplierProduct" varStatus="vs">
  <div>
    <h2 class="count_flow mt3 b">
    	<span>供应商名称：北京新华电子科技有限公司</span>
    	<span style="margin-left: 22px;">排名：第一名</span>
    	<span style="margin-left: 22px;">成交比例：50%</span>
    </h2>
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info">序号</th>
		  <th class="info">产品名称</th>
		  <th class="info">数量</th>
		  <th class="info">自报单价（元）</th>
		  <th class="info">成交总价（元）</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc"></td>
		  <td class="tc" colspan="3">合计</td>
		  <td class="tc">12000</td>
		</tr>
		<c:forEach items="${supplierProduct.productList }" var="bidproduct" varStatus="pi">
		<tr>
		  <td class="tc">${pi.index + 1 }</td>
		  <td class="tc">${bidproduct.productName }</td>
		  <td class="tc">${bidproduct.productNum }</td>
		  <td class="tc">${bidproduct.myOfferMoney }</td>
		  <td class="tc">1000</td>
		</tr>
		</c:forEach>
	</table>
  </div>
  </div>
  </c:forEach>
  <div>
    <h2 class="count_flow">供应商名称：北京新华电子科技有限公司排名：第一名成交比例：50%</h2>
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info">序号</th>
		  <th class="info">产品名称</th>
		  <th class="info">数量</th>
		  <th class="info">自报单价（元）</th>
		  <th class="info">成交总价（元）</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc">1</td>
		  <td class="tc">台式计算机</td>
		  <td class="tc">20</td>
		  <td class="tc">100</td>
		  <td class="tc">1000</td>
		</tr>
		<tr>
		  <td class="tc">2</td>
		  <td class="tc">便携式式计算机</td>
		  <td class="tc">20</td>
		  <td>200</td>
		  <td class="tc">4000</td>
		</tr>
		<tr>
		  <td class="tc">3</td>
		  <td class="tc">服务器</td>
		  <td class="tc">10</td>
		  <td>300</td>
		  <td class="tc">3000</td>
		</tr>
		<tr>
		  <td class="tc"></td>
		  <td class="tc" colspan="3">合计</td>
		  <td class="tc">12000</td>
		</tr>
	</table>
  </div>
  </div>
  <div>
    <h2 class="count_flow">供应商名称：北京新华电子科技有限公司排名：第一名成交比例：50%</h2>
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info">序号</th>
		  <th class="info">产品名称</th>
		  <th class="info">数量</th>
		  <th class="info">自报单价（元）</th>
		  <th class="info">成交总价（元）</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc">1</td>
		  <td class="tc">台式计算机</td>
		  <td class="tc">20</td>
		  <td class="tc">100</td>
		  <td class="tc">1000</td>
		</tr>
		<tr>
		  <td class="tc">2</td>
		  <td class="tc">便携式式计算机</td>
		  <td class="tc">20</td>
		  <td>200</td>
		  <td class="tc">4000</td>
		</tr>
		<tr>
		  <td class="tc">3</td>
		  <td class="tc">服务器</td>
		  <td class="tc">10</td>
		  <td>300</td>
		  <td class="tc">3000</td>
		</tr>
		<tr>
		  <td class="tc"></td>
		  <td class="tc" colspan="3">合计</td>
		  <td class="tc">12000</td>
		</tr>
	</table>
  </div>
  </div>
 </div>
</body>
</html>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="table table-bordered mt10">
    <tbody>
	  <tr>
	    <td class="info"><b>竞价项目编号</b></td>
	    <td>${ obProject.projectNumber }</td>
	    <td class="info"><b>竞价项目名称</b></td>
	    <td>${ obProject.name }</td>
	  </tr>
	  <tr>
	    <td class="info"><b>交货地点</b></td>
	    <td>${ obProject.deliveryAddress }</td>
	     <td class="info"><b>交货时间</b></td>
	    <td><fmt:formatDate value="${ obProject.deliveryDeadline }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	  </tr>
	  <tr>
	    <td class="info"><b>成交供应商数</b></td>
	    <td>
		    ${ obProject.tradedSupplierCount }
		</td>
		<td colspan="2"></td>
	  </tr>
	  <tr>
	    <td class="info"><b>运杂费</b></td>
	    <td>${ transportFees }</td>
	    <c:if test="">
		    <td colspan="2"></td>
	    </c:if>
	    <c:if test="${ empty obProject.transportFeesPrice }">
		    <td colspan="2"></td>
	    </c:if>
	    <c:if test="${ !empty obProject.transportFeesPrice }">
		    <td class="info"><b>运杂费用（元）</b></td>
		    <td>${obProject.transportFeesPrice}</td>
	    </c:if>
	  </tr>
	  <tr>
	    <td class="info"><b>需求单位</b></td>
	    <td>${ demandUnit }</td>
	    <td class="info"><b>采购机构</b></td>
	    <td>${ orgName }</td>
	  </tr>
	  <tr>
	    <td class="info"><b>需求联系人</b></td>
	    <td>${ obProject.contactName }</td>
	    <td class="info"><b>采购联系人：</b></td>
	    <td>${ obProject.orgContactName }</td>
	  </tr>
	  <tr>
	    <td class="info"><b>需求联系电话</b></td>
	    <td>${ obProject.contactTel }</td>
	    <td class="info"><b>采购联系电话</b></td>
	    <td>${ obProject.orgContactTel }</td>
	  </tr>
	  <tr>
	    <td class="info"><b>竞价开始时间</b></td>
	    <td><fmt:formatDate value="${ obProject.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	    <td class="info"><b>竞价结束时间</b></td>
	    <td><fmt:formatDate value="${ obProject.endTime }" pattern="yyyy-MM-dd HH:mm:ss"/></td>
	  </tr>
	  <tr>
	    <td class="info"><b>竞价内容</b></td>
	    <td colspan="3">${ obProject.content }</td>
	  </tr>
	  <tr>
	    <td class="info"><b>竞价文件</b></td>
	    <td colspan="3">
	    	<c:forEach items="${ uploadFiles }" var="file">
	    		<span title="点击下载"><u:show showId="project" groups="b,c,d"  delete="false" businessId="${fileid}" sysKey="${sysKey}" typeId="${typeId }" /></span>
	    	</c:forEach>
	     </td>
	  </tr>
	</tbody>
</table>
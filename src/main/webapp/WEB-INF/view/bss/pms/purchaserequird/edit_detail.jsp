
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>

<tr name="detailRow">
	<td class="tc">${index+vs.index+1}</td>
	<td class="tc  p0"><input type="hidden" id="id${index}"
		name="list[${index }].id" value="${id }"> <input type="text"
		id="seq${index}" name="list[${index }].seq" value=""
		onblur="getSeq(this,${index})" class="m0 border0 w50 tc"> <input
		type="hidden" id="parentId${index}" name="list[${index }].parentId"
		value=""></td>
	<td class=" p0">
		<%-- 	<input type="text" name="list[${index }].department"   value="${obj.department}"> --%>
		<%-- 	<input type="hidden" name="list[${index }].department" value="${orgId }" > --%>
		<%--<input type="text" name="list[${index }].department"
		readonly="readonly" value="${orgName}" class="m0 border0 w80">--%>
			<div class="department">${orgName}</div>
	</td>
	<td>
	<div class="goodsname">
	    <input type="hidden" name="ss" value="${obj.id }">
			<input type="text"  class="m0 border0"
				name="list[${index }].goodsName" onkeyup="listName(this)"
				 value="" />
		 </div>
		 </td>
	<td><input type="text" class="stand" name="list[${index }].stand"
		value="${objs.stand}"></td>
	<td><input type="text" class="qualitstand"
		name="list[${index }].qualitStand" value="" class=""></td>
	<td><input type="text"  class="item" name="list[${index }].item"
		value=""></td>
	<td><input type="hidden" value="${id }"> <input
		type="text" class="purchasecount" onblur='sum2(this)'
		name="list[${index }].purchaseCount" onkeyup="checkNum(this,1)"
		value=""> <input type="hidden" value=""></td>
	<td><input type='hidden' value='${id }'> <input
		type="text" class="price" onblur='sum1(this)'
		name="list[${index }].price" onkeyup="checkNum(this,2)" value="">
		<input type="hidden" value=""></td>

	<td><input type="hidden" value='${id }'> <input
		type="text" readonly="readonly" class="budget"
		name="list[${index }].budget" value=""> <input type="hidden"
		value=""></td>


	<td><input type="text" class="deliverdate"
		name="list[${index }].deliverDate" value=" "></td>
	<td><select name="list[${index }].purchaseType"
		class="purchasetype" onchange="changeType(this)">
			<option value="">请选择</option>
			<c:forEach items="${list2 }" var="objd">
				<option value="${objd.id }">${objd.name }</option>

			</c:forEach>
	</select></td>
	<td><input type="text" name="list[${index }].supplier" onblur="checkSupplierName('${index }')"
		class="m0 w260 border0"> <%-- <select name="list[${index }].supplier" class="purchasename" onchange="changeType(this)" id="pType[0]">
												<option value="">请选择</option>
												<c:forEach items="${suppliers }" var="sup">
													<option value="${sup.supplierName }">${sup.supplierName}</option>
												</c:forEach>
											</select> --%></td>
	<td name='userNone'><input type="text" name="list[${index }].isFreeTax"
		class="freetax"></td>
	<td name='userNone'><input type="text"
		name="list[${index }].goodsUse" class="goodsuse"></td>
	<td name='userNone'><input type="text"
		name="list[${index }].useUnit" class="useunit"></td>
	<td><input type="text" name="list[${index }].memo" value=""
		class="memo"></td>
	<td>
		<div class="extrafile">
			<u:upload id="pUp${index}" multiple="true" buttonName="上传文件"
				businessId="${id}" sysKey="2" typeId="${attId}" auto="true" />
			<u:show showId="pShow${index}" businessId="${id}" sysKey="2"
				typeId="${attId}" />
		</div>
	</td>
	<td class="tc w100"><button type="button" class="btn" onclick="deleteRow(this)">删除</button></td>
</tr>
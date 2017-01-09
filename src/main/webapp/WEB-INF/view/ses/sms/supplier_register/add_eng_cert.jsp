<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<tr>
<td class="tc">
<input type="checkbox" value="${id}" />
<input type="hidden" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].id" value="${id}">
</td>
<td class="tc"><input type="text" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certType"/></td>
<td class="tc"><input type="text" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certCode"/></td>
<td class="tc"><input type="text" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certMaxLevel"/></td>
<td class="tc"><input type="text" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].techName"/></td>
<td class="tc"><input type="text" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].techPt"/></td>
<td class="tc"><input type="text" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].techJop"/></td>
<td class="tc"><input type="text" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].depName"/></td>
<td class="tc"><input type="text" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].depPt"/></td>
<td class="tc"><input type="text" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].depJop"/></td>
<td class="tc"><input type="text" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].licenceAuthorith"/></td>
<td class="tc"><input type="text" readonly="readonly" onClick="WdatePicker()" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].expStartDate"/></td>
<td class="tc"><input type="text" readonly="readonly" onClick="WdatePicker()" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].expEndDate"/></td>
<td class="tc">
  	<select name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certStatus">
		<option value="1">有效</option>
		<option value="0">无效</option>
	</select>
</td>
<td class="tc">
 <u:upload id="eng_up_${certEngNumber}" multiple="true" businessId="${id}" typeId="1" sysKey="1"  auto="true" />
 <u:show showId="eng_show_${certEngNumber}" businessId="${id}" typeId="1" sysKey="1" />
</td>
</tr>
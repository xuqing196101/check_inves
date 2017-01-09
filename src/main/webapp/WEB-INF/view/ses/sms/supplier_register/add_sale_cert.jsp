<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<tr>
<td class="tc">
<input type="checkbox" value="${id}" />
<input type="hidden" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].id" value="${id}">
</td>
<td class="tc"><input type="text" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].name"/></td>
<td class="tc"><input type="text" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].levelCert"/></td>
<td class="tc"><input type="text" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].licenceAuthorith"/></td>
<td class="tc">
<input type="text" readonly="readonly" onClick="WdatePicker()" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].expStartDate"/>
   </td>
<td class="tc">
	<input type="text" readonly="readonly" onClick="WdatePicker()" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].expEndDate"/>
</td>
<td class="tc">
   	<select name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].mot">
         <option value="1">是</option>
         <option value="0">无</option>
  	</select>
</td>
<td class="tc">
 	<u:upload id="sale_up_${certSaleNumber}" multiple="true" businessId="${id}" typeId="1" sysKey="1"  auto="true" />
 	<u:show showId="sale_show_${certSaleNumber}" businessId="${id}" typeId="1" sysKey="1" />
</td>
</tr>
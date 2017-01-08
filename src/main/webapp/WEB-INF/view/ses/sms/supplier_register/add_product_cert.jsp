<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<tr>
<td class="tc">
<input type="checkbox" value="${id}"/>
<input type="hidden" name="supplierMatPro.listSupplierCertPros[${certProNumber}].id" value="${id}">
</td>
<td class="tc"><input type="text" name="supplierMatPro.listSupplierCertPros[${certProNumber}].name"/> </td>
<td class="tc"><input type="text" name="supplierMatPro.listSupplierCertPros[${certProNumber}].levelCert"/> </td>
<td class="tc"><input type="text" name="supplierMatPro.listSupplierCertPros[${certProNumber}].licenceAuthorith"/></td>
<td class="tc">
<input type="text" readonly="readonly" onClick="WdatePicker()" name="supplierMatPro.listSupplierCertPros[${certProNumber}].expStartDate"/>
   </td>
<td class="tc">
	<input type="text" name="supplierMatPro.listSupplierCertPros[${certProNumber}].expEndDate" onClick="WdatePicker()" readonly="readonly"  />
   </td>
<td class="tc">
   <select name="supplierMatPro.listSupplierCertPros[${certProNumber}].mot">
         <option value="1" >是</option>
         <option value="0" >无</option>
       </select>
</td>
<td class="tc">
 <u:upload id="pro_up_${certProNumber}" multiple="true" groups="pro_up_${certProNumber}" businessId="${id}" typeId="1" sysKey="1"  auto="true" />
 <u:show showId="pro_show_${certProNumber}" businessId="${id}" groups="pro_show_${certProNumber}" typeId="1" sysKey="1" />
</td>
</tr>
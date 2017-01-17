<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<tr>
<td class="tc">
<input type="checkbox" value="${id}" class="border0"/>
<input type="hidden" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].id" value="${id}" class="border0">
</td>
<td class="tc"><input type="text" onblur="tempSave()" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].name" class="border0"/></td>
<td class="tc"><input type="text" onblur="tempSave()" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].levelCert" class="border0"/></td>
<td class="tc"><input type="text" onblur="tempSave()" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].licenceAuthorith" class="border0"/></td>
<td class="tc">
<input type="text" readonly="readonly" onblur="tempSave()" onClick="WdatePicker()" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].expStartDate" class="border0"/>
   </td>
<td class="tc">
	<input type="text" readonly="readonly" onblur="tempSave()" onClick="WdatePicker()" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].expEndDate" class="border0"/>
</td>
<td class="tc">
   	<select name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].mot" class="w100p border0" onchange="tempSave()">
         <option value="1">是</option>
         <option value="0">否</option>
  	</select>
</td>
<td class="tc w200">
  <div class="w200">
 	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="sale_up_${certSaleNumber}" multiple="true" businessId="${id}" typeId="${supplierDictionaryData.supplierBusinessCert}" sysKey="${sysKey}"  auto="true" />
 	<u:show showId="sale_show_${certSaleNumber}" businessId="${id}" typeId="${supplierDictionaryData.supplierBusinessCert}" sysKey="${sysKey}" />
  </div>
</td>
</tr>
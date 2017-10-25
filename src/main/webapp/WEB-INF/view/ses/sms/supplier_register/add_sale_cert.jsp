<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<tr>
<td class="tc">
<input type="checkbox" value="${id}" class="border0" isAdd="true"/>
<input type="hidden" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].id" value="${id}" class="border0"/>
</td>
<td class="tc"><input type="text" maxlength="30" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].name" class="border0"/></td>
<td class="tc"><input type="text" maxlength="150" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].code" class="border0"/></td>
<td class="tc"><input type="text" maxlength="10" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].levelCert" class="border0"/></td>
<td class="tc"><input type="text" maxlength="30" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].licenceAuthorith" class="border0"/></td>
<td class="tc">
	<input type="text" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].expStartDate" class="border0"/>
</td>
<td class="tc">
	<input type="text" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].expEndDate" class="border0"/>
</td>
<td class="tc"><input type="text" maxlength="15" name="supplierMatSell.listSupplierCertSells[${certSaleNumber}].mot" class="border0"/></td>
<td class="tc w200">
  <div class="w200">
 	<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="sale_up_${certSaleNumber}" multiple="true" businessId="${id}" typeId="${typeId}" sysKey="${sysKey}"  auto="true" />
 	<u:show showId="sale_show_${certSaleNumber}" businessId="${id}" typeId="${typeId}" sysKey="${sysKey}" />
  </div>
</td>
</tr>
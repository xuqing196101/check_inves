<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<tr>
<td class="tc">
<input type="checkbox" class="border0" value="${id}" />
<input type="hidden" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].id" value="${id}">
</td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certType"/></td>
<td class="tc"><input type="text" onblur="tempSave()" class="border0" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certCode"/></td>
<td class="tc"><input type="text" onblur="tempSave()" class="border0" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certMaxLevel"/></td>
<td class="tc"><input type="text" onblur="tempSave()" class="border0" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].licenceAuthorith"/></td>
<td class="tc"><input type="text" onblur="tempSave()" class="border0" readonly="readonly" onClick="WdatePicker()" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].expStartDate"/></td>
<td class="tc"><input type="text" onblur="tempSave()" class="border0" readonly="readonly" onClick="WdatePicker()" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].expEndDate"/></td>
<td class="tc">
	<input type="text" onblur="tempSave()" class="border0" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certStatus"/>
</td>
<td class="tc w200">
<div class="w200">
 <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="eng_up_${certEngNumber}" multiple="true" businessId="${id}" typeId="${typeId}" sysKey="${sysKey}"  auto="true" />
 <u:show showId="eng_show_${certEngNumber}" businessId="${id}" typeId="${typeId}" sysKey="${sysKey}" />
</div>
</td>
</tr>
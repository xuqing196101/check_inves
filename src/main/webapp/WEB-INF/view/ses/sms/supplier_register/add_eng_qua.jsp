<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<tr>
<td class="tc">
<input type="checkbox" class="border0" value="${id}" isAdd="true"/>
<input type="hidden" name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].id" value="${id}"/>
</td>
<td class="tc"><input type="text" class="border0" maxlength="30" name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].name"/></td>
<td class="tc"><input type="text" class="border0" maxlength="15" name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].code"/></td>
<td class="tc"><input type="text" class="border0" maxlength="30" name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].levelCert"/></td>
<td class="tc"><input type="text" class="border0" maxlength="60" name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].licenceAuthorith"/></td>
<td class="tc"><input type="text" class="border0" name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].expStartDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" readonly="readonly"/></td>
<td class="tc"><input type="text" class="border0" name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].expEndDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})" readonly="readonly"/></td>
<td class="tc"><input type="text" class="border0" maxlength="15" name="supplierMatEng.listSupplierEngQuas[${engQuaNumber}].mot"/></td>
<td class="tc w200">
	<div class="w200">
		<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="eng_qua_up_${engQuaNumber}" multiple="true" businessId="${id}" typeId="${typeId}" sysKey="${sysKey}"  auto="true" />
		<u:show showId="eng_qua_show_${engQuaNumber}" businessId="${id}" typeId="${typeId}" sysKey="${sysKey}" />
	</div>
</td>
</tr>
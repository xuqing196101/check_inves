<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<tr>
<td class="tc">
<input type="checkbox" class="border0" value="${id}" isAdd="true"/>
<input type="hidden" name="supplierMatSe.listSupplierCertSes[${certSeNumber}].id" value="${id}"/>
</td>
<td class="tc"><input type="text" class="border0" maxlength="30" onkeyup="checkInputLength(this,30)" name="supplierMatSe.listSupplierCertSes[${certSeNumber}].name"/></td>
<td class="tc"><input type="text" class="border0" maxlength="15" onkeyup="checkInputLength(this,15)" name="supplierMatSe.listSupplierCertSes[${certSeNumber}].code"/></td>
<td class="tc"><input type="text" class="border0" maxlength="30" onkeyup="checkInputLength(this,30)" name="supplierMatSe.listSupplierCertSes[${certSeNumber}].levelCert"/></td>
<td class="tc"><input type="text" class="border0" maxlength="60" onkeyup="checkInputLength(this,60)" name="supplierMatSe.listSupplierCertSes[${certSeNumber}].licenceAuthorith"/></td>
<td class="tc"><input type="text" class="border0" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" name="supplierMatSe.listSupplierCertSes[${certSeNumber}].expStartDate"/></td>
<td class="tc"><input type="text" class="border0" readonly="readonly" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})" name="supplierMatSe.listSupplierCertSes[${certSeNumber}].expEndDate"/></td>
<td class="tc"><input type="text" class="border0" maxlength="15" onkeyup="checkInputLength(this,15)" name="supplierMatSe.listSupplierCertSes[${certSeNumber}].mot"/></td>
<td class="tc w200">
	<div class="w200">
		<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="se_up_${certSeNumber}" multiple="true" businessId="${id}" typeId="${typeId}" sysKey="${sysKey}"  auto="true" />
		<u:show showId="se_show_${certSeNumber}" businessId="${id}" typeId="${typeId}" sysKey="${sysKey}" />
	</div>
</td>
</tr>
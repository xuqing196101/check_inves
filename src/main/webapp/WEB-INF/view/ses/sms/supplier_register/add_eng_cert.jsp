<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<tr>
<td class="tc">
<input type="checkbox" class="border0" value="${id}" isAdd="true"/>
<input type="hidden" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].id" value="${id}"/>
</td>
<td class="tc"><input type="text" class="border0" maxlength="60" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certType"/></td>
<td class="tc"><input type="text" maxlength="150" class="border0" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certCode"/></td>
<td class="tc"><input type="text" maxlength="10" class="border0" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certMaxLevel"/></td>
<td class="tc"><input type="text" maxlength="30" class="border0" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].licenceAuthorith"/></td>
<td class="tc"><input type="text" class="border0" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d',readOnly:true})" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].expStartDate"/></td>
<td class="tc"><input type="text" class="border0" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',readOnly:true})" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].expEndDate"/></td>
<td class="tc">
	<input type="text" maxlength="15" class="border0" name="supplierMatEng.listSupplierCertEngs[${certEngNumber}].certStatus"/>
</td>
<%-- <td class="tc w200">
<div class="w200">
 <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="eng_up_${certEngNumber}" multiple="true" businessId="${id}" typeId="${typeId}" sysKey="${sysKey}"  auto="true" />
 <u:show showId="eng_show_${certEngNumber}" businessId="${id}" typeId="${typeId}" sysKey="${sysKey}" />
</div>
</td> --%>
</tr>
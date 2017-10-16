<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<tr>
  <td class="tc">
  	<input type="checkbox" class="border0" value="${id}" isAdd="true"/>
  	<input type="hidden" class="border0" name="supplierMatPro.listSupplierCertPros[${certProNumber}].id" value="${id}"/>
  </td>
  <td class="tc"><input type="text" maxlength="30" name="supplierMatPro.listSupplierCertPros[${certProNumber}].name" class="border0"/> </td>
  <td class="tc"><input type="text" maxlength="150" name="supplierMatPro.listSupplierCertPros[${certProNumber}].code" class="border0"/> </td>
  <td class="tc"><input type="text" maxlength="10" name="supplierMatPro.listSupplierCertPros[${certProNumber}].levelCert" class="border0"/> </td>
  <td class="tc"><input type="text" maxlength="30" name="supplierMatPro.listSupplierCertPros[${certProNumber}].licenceAuthorith" class="border0"/></td>
  <td class="tc">
    <input type="text" name="supplierMatPro.listSupplierCertPros[${certProNumber}].expStartDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'%y-%M-%d'})" class="border0" readonly="readonly"/>
  </td>
  <td class="tc">
    <input type="text" name="supplierMatPro.listSupplierCertPros[${certProNumber}].expEndDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})" class="border0" readonly="readonly"/>
  </td>
  <td class="tc"><input type="text" maxlength="15" onblur="tempSave()" name="supplierMatPro.listSupplierCertPros[${certProNumber}].mot" class="border0"/> </td>
  <td class="tc w200">
  <div class="w200">
    <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="pro_up_${certProNumber}" multiple="true" businessId="${id}" typeId="${typeId}" sysKey="${sysKey}"  auto="true" />
 		<u:show showId="pro_show_${certProNumber}" businessId="${id}" typeId="${typeId}" sysKey="${sysKey}" />
  </div>
  </td>
</tr>
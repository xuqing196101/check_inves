<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<tr>
  <td class="tc">
  	<input type="checkbox" class="border0" value="${id}"/>
  	<input type="hidden" class="border0" name="supplierMatPro.listSupplierCertPros[${certProNumber}].id" value="${id}">
  </td>
  <td class="tc"><input type="text" onblur="tempSave()" name="supplierMatPro.listSupplierCertPros[${certProNumber}].name" class="border0"/> </td>
  <td class="tc"><input type="text" onblur="tempSave()" name="supplierMatPro.listSupplierCertPros[${certProNumber}].levelCert" class="border0"/> </td>
  <td class="tc"><input type="text" onblur="tempSave()" name="supplierMatPro.listSupplierCertPros[${certProNumber}].licenceAuthorith" class="border0"/></td>
  <td class="tc">
    <input type="text" readonly="readonly" onblur="tempSave()" onClick="WdatePicker()" name="supplierMatPro.listSupplierCertPros[${certProNumber}].expStartDate" class="border0"/>
  </td>
  <td class="tc">
    <input type="text" onblur="tempSave()" name="supplierMatPro.listSupplierCertPros[${certProNumber}].expEndDate" onClick="WdatePicker()" readonly="readonly" class="border0"/>
  </td>
  <td class="tc">
    <select name="supplierMatPro.listSupplierCertPros[${certProNumber}].mot" class="w100p border0" onchange="tempSave()">
	  <option value="1" >是</option>
	  <option value="0" >否</option>
	</select>
  </td>
  <td class="tc w200">
  <div class="w200">
    <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="pro_up_${certProNumber}" multiple="true" businessId="${id}" typeId="${typeId}" sysKey="${sysKey}"  auto="true" />
 	<u:show showId="pro_show_${certProNumber}" businessId="${id}" typeId="${typeId}" sysKey="${sysKey}" />
  </div>
  </td>
</tr>
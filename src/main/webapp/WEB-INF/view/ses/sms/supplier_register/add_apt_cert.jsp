<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<tr>
<td class="tc">
<input type="checkbox" class="border0" value="${id}" />
<input type="hidden" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].id" value="${id}">
</td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certType"/></td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certCode"/></td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteSequence"/></td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].professType"/></td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteLevel"/></td>
<td class="tc">
   <select name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].isMajorFund" class="w100p border0" onchange="tempSave()">
         <option value="1">是</option>
         <option value="0">否</option>
       </select>
</td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteContent"/></td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteCode"/></td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" readonly="readonly" onClick="WdatePicker()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteDate"/></td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteWay"/></td>
<td class="tc">
   <select name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteStatus" class="w100p border0" onchange="tempSave()">
         <option value="1">有效</option>
         <option value="0">无效</option>
       </select>
</td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" readonly="readonly" onClick="WdatePicker()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteChangeAt"/></td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteChangeReason"/></td>
<td class="tc w200">
<div class="w200">
 <u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="apt_up_${certAptNumber}" multiple="true" businessId="${id}" typeId="${supplierDictionaryData.supplierBusinessCert}" sysKey="${sysKey}"  auto="true" />
 <u:show showId="apt_show_${certAptNumber}" businessId="${id}" typeId="${supplierDictionaryData.supplierBusinessCert}" sysKey="${sysKey}" />
</div>
</td>
</tr>
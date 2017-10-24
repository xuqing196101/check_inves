<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<tr>
<td class="tc">
<input type="checkbox" class="border0" value="${id}" isAdd="true"/>
<input type="hidden" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].id" value="${id}"/>
</td>
<td class="tc"><div class="w200"><input type="text" class="border0" maxlength="30" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certName"/></div></td>
<td class="tc"><div class="w150"><input type="text" class="border0" maxlength="150" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certCode"/></div></td>
<td class="tc">
	<select name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certType" id="certType_${certAptNumber }" title="cnjewfn" class="w100p border0 certTypeSelect"  style="width:200px;border: none;">
    <option value="">请选择</option>
		<c:forEach items="${typeList}" var="type">
			<option value="${type.id}">${type.name}</option>
		</c:forEach>
	</select>
	<input type="hidden" value="">
</td>
<td class="tc"><input type="text" class="border0" maxlength="80" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteSequence"/></td>
<td class="tc"><input type="text" class="border0" maxlength="30" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].professType"/></td>
<td class="tc">
	<select name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteLevel" id="certGrade_${certAptNumber }" title="cnjewfnGrade" class="w100p border0" style="width:200px;border: none;"></select>
	<input type="hidden" id="certLevel_${certAptNumber}" value="${aptitute.aptituteLevel}">
</td>
<td class="tc">
	<select name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].isMajorFund" class="w100p border0">
		<option value="1">是</option>
		<option value="0">否</option>
	</select>
	<script type="text/javascript">
		var certAptNumber = '${certAptNumber}';
		
		$("#certType_"+certAptNumber).combobox({
			panelHeight : 240,
			onSelect : function(record) {
				getAptLevelSelect(record);
			},
			onChange : function() {
				$("#certLevel_"+certAptNumber).val("");
				getAptLevel($(this), true);
			}
		});
		
		$("#certGrade_"+certAptNumber).combobox({});
		
	</script>
</td>

<td class="tc">
	<div class="w200 fl">
    <u:upload  singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="eng_up_${certAptNumber}" multiple="true" businessId="${id}" typeId="${typeId}" sysKey="${sysKey}" auto="true" />
    <u:show showId="eng_show_${certAptNumber}" businessId="${id}" typeId="${typeId}" sysKey="${sysKey}" />
	</div>
</td>
</tr>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<tr>
<td class="tc">
<input type="checkbox" class="border0" value="${id}" />
<input type="hidden" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].id" value="${id}">
</td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certName"/></td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certCode"/></td>
<td class="tc">
	<select name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].certType" id="certType_select${certAptNumber }" title="cnjewfnAdd1" class="w100p border0" onchange="getAptLevel(this)">
		<c:forEach items="${typeList}" var="type">
			<option value="${type.id}">${type.name}</option>
		</c:forEach>
	</select>
	<input type="hidden" value="">
</td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteSequence"/></td>
<td class="tc"><input type="text" class="border0" onblur="tempSave()" name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].professType"/></td>
<td class="tc">
	<select name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].aptituteLevel" id="certGrade_addSelect${certAptNumber }" title="cnjewfnAdd2" class="w100p border0" onchange="tempSave()"></select>
</td>
<td class="tc">
	<select name="supplierMatEng.listSupplierAptitutes[${certAptNumber}].isMajorFund" class="w100p border0" onchange="tempSave()">
		<option value="1">是</option>
		<option value="0">否</option>
	</select>
	<script type="text/javascript">
		$("select[title='cnjewfnAdd1']").each(function() {
			var $obj = $(this);
			$obj.combobox({
				width : "200px",
				onSelect : function(record) {
					getAptLevelSelect(record);
				},
				onChange : function() {
					var changeStatus = $obj.attr("id");
					var changeStatusNum;
					for(var i = 0;i < changeStatus.length;i++) {
						if(changeStatus.charAt(i) > 0 && changeStatus.charAt(i) < 10) {
							changeStatusNum = changeStatus.substr(i,changeStatus.length);
							//changeStatusJudge = changeStatus.substr(0,i);
							break;
						}
					}
					getAptLevel($obj,"addBtn");
				}
			});
		});
		$("select[title='cnjewfnAdd2']").each(function() {
			var $obj = $(this);
			$obj.combobox({
				width : "200px",
				onSelect : function(record) {
					getAptLevelSelect(record);
				},
				onChange : function() {
					var changeStatus = $obj.attr("id");
					var changeStatusNum;
					for(var i = 0;i < changeStatus.length;i++) {
						if(changeStatus.charAt(i) > 0 && changeStatus.charAt(i) < 10) {
							changeStatusNum = changeStatus.substr(i,changeStatus.length);
							//changeStatusJudge = changeStatus.substr(0,i);
							break;
						}
					}
					getAptLevel($obj,"addBtn");
				},
				valueField: 'value',
				textField: 'label',
				data : [{label: '特级',value: '1',selected:true},
				        {label: '一级',value: '2'},
				        {label: '二级',value: '3'},
				        {label: '三级',value: '4'},
				        {label: '不分级',value: '5'},]
			});
		});
	</script>
</td>
</tr>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<c:choose>
	<c:when test="${!empty listAptitutes}">
		<c:set var="undoCount" value="0" />
		<c:forEach
			items="${listAptitutes}"
			var="aptitute" varStatus="vs">
			<tr <c:if test="${fn:contains(engPageField,aptitute.id)}"> onmouseover="errorMsg(this,'${aptitute.id}','mat_eng_page')"</c:if>>
				<td class="tc"
					<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
					<input type="checkbox" class="border0"
					value="${aptitute.id}" /> <input type="hidden"
					name="supplierMatEng.listSupplierAptitutes[${vs.index+ind}].id"
					value="${aptitute.id}"></td>
				<td class="tc"
					<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
					 <div class="w200">
					 	<input <c:if test="${!fn:contains(engPageField,aptitute.id)&&supplierSt==2}">readonly='readonly' </c:if>
					type="text" required="required" class="border0" maxlength="30"
					name="supplierMatEng.listSupplierAptitutes[${vs.index+ind}].certName"
					value="${aptitute.certName}" />
					  </div>
				</td>
				<td class="tc"
					<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
					  <div class="w150"><input <c:if test="${!fn:contains(engPageField,aptitute.id)&&supplierSt==2}">readonly='readonly' </c:if>
					type="text" required="required" class="border0" maxlength="150"
					name="supplierMatEng.listSupplierAptitutes[${vs.index+ind}].certCode"
					value="${aptitute.certCode}" />
					</div>
				</td>
				<td class="tc" <c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
					<select title="cnjewfn" id="certType_${vs.index+ind}" class="w100p border0 certTypeSelect" name="supplierMatEng.listSupplierAptitutes[${vs.index+ind}].certType" style="width:200px;border: none;">
				    <c:set var="tempForShowOption" value="go" scope="page"/>
				    <option value="-1" selected="selected">请选择</option>
				    <c:forEach items="${quaList}" var="qua">
							<option value="${qua.id}" <c:if test="${aptitute.certType eq qua.id}">selected</c:if>>${qua.name}</option>
							<c:if test="${aptitute.certType eq qua.id}">
								<c:set var="tempForShowOption" value="notgo"/>
							</c:if>
						</c:forEach>
						<c:if test="${tempForShowOption eq 'go' }">
							<option value="${aptitute.certType}" selected="selected">${aptitute.certType}</option>
						</c:if>
					</select>
				</td>
				<td class="tc"
					<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>><input
					type="text" required="required" class="border0" maxlength="80" <c:if test="${!fn:contains(engPageField,aptitute.id)&&supplierSt==2}">readonly='readonly' </c:if>
					name="supplierMatEng.listSupplierAptitutes[${vs.index+ind}].aptituteSequence"
					value="${aptitute.aptituteSequence}" />
				</td>
				<td class="tc"
					<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>><input
					type="text" required="required" class="border0" maxlength="100" <c:if test="${!fn:contains(engPageField,aptitute.id)&&supplierSt==2}">readonly='readonly' </c:if>
					name="supplierMatEng.listSupplierAptitutes[${vs.index+ind}].professType"
					value="${aptitute.professType}" />
				</td>
				<td class="tc" <c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
					<!-- 
					<select name="supplierMatEng.listSupplierAptitutes[${vs.index+ind}].aptituteLevel" class="w100p border0"></select>
					 -->
					<select id="certGrade_${vs.index+ind}" title="cnjewfnGrade" name="supplierMatEng.listSupplierAptitutes[${vs.index+ind}].aptituteLevel" class="w100p border0" style="width:200px;border: none;">
                       <%-- <c:if test="${tempForShowOption eq 'go' }">
							<option selected="selected">${aptitute.aptituteLevel}</option>
						</c:if> --%>
						<%-- <option selected="selected">${aptitute.aptituteLevel}</option> --%>
						<%-- <c:set var="tempForShowOption" value="notgo"/> --%>
					</select>
					<input type="hidden" id="certLevel_${vs.index+ind}" value="${aptitute.aptituteLevel}">
					<script type="text/javascript">
						function initCombo(){
							var currSupplierSt = '${supplierSt}';
							var index = "${vs.index+ind}";
							
							var obj_type = $("#certType_"+index);
							var options_type = {
								panelHeight : 240,
								onSelect : function(record) {
									getAptLevelSelect(record);
								},
								onChange : function() {
									$("#certLevel_"+index).val("");
									getAptLevel(obj_type, true);
								},
							};
							if(currSupplierSt == '2'){
								options_type.disabled = true;
								//$(this).parent("td").css("border") == '1px solid rgb(255, 0, 0)'
								if(obj_type.parent("td").attr("style") == 'border: 1px solid red;'){
									options_type.disabled = false;
								}
							}
							obj_type.combobox(options_type);
							
							var obj_grade = $("#certGrade_"+index);
							var options_grade = {
								onChange : function() {
                            //console.log($obj.combobox("getText"));
                            //tempSave();
                          },
							};
							if(currSupplierSt == '2'){
								options_grade.disabled = true;
								//$(this).parent("td").css("border") == '1px solid rgb(255, 0, 0)'
								if(obj_grade.parent("td").attr("style") == 'border: 1px solid red;'){
									options_grade.disabled = false;
								}
							}
							obj_grade.combobox(options_grade);
						}
						
						initCombo();// 初始化combo下拉
						init_web_upload_in('#file_${vs.index+ind}');// 初始化附件上传
						showInit('eng_show_${vs.index+ind}');// 初始化附件显示
						getAptLevel($("select[id='certType_${vs.index+ind}']"));// 初始化资质等级
					</script>
				</td>
				<td class="tc"
					<c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
					<select
					name="supplierMatEng.listSupplierAptitutes[${vs.index+ind}].isMajorFund"
					class="w100p border0" <c:if test="${!fn:contains(engPageField,aptitute.id)&&supplierSt==2}">onchange="this.selectedIndex=this.defaultIndex;"</c:if> >
						<option value="1"
							<c:if test="${aptitute.isMajorFund==1}"> selected="selected"</c:if>>是</option>
						<option value="0"
							<c:if test="${aptitute.isMajorFund==0}"> selected="selected"</c:if>>否</option>
					</select>
				</td>
				
				<td class="tc" <c:if test="${fn:contains(engPageField,aptitute.id)}">style="border: 1px solid red;" </c:if>>
					<div class="w200 fl" id="file_${vs.index+ind}">
						<%-- <%@ include file="/WEB-INF/view/common/webupload.jsp"%> --%>
						<c:if test="${(fn:contains(engPageField,aptitute.id)&&supplierSt==2) || supplierSt==-1 || empty(supplierSt)}">
							<u:upload singleFileSize="${properties['file.picture.upload.singleFileSize']}" exts="${properties['file.picture.type']}" id="eng_up_${vs.index+ind}" multiple="true" businessId="${aptitute.id}" typeId="${supplierDictionaryData.supplierEngCert}" sysKey="${sysKey}" auto="true" />
						</c:if>
						<c:if test="${!fn:contains(engPageField,aptitute.id)&&supplierSt==2 }">
							<u:show showId="eng_show_${vs.index+ind}" delete="false" businessId="${aptitute.id}" typeId="${supplierDictionaryData.supplierEngCert}" sysKey="${sysKey}" />
						</c:if>
						<c:if test="${supplierSt==-1 || empty(supplierSt) || fn:contains(engPageField,aptitute.id)}">
							<u:show showId="eng_show_${vs.index+ind}" businessId="${aptitute.id}" typeId="${supplierDictionaryData.supplierEngCert}" sysKey="${sysKey}" />
						</c:if>
					</div>
				</td>
			</tr>
			<c:set var="undoCount" value="${undoCount+1}" />
		</c:forEach>
		<input type="hidden" id="undoCount" value="${undoCount}" />
	</c:when>
</c:choose>
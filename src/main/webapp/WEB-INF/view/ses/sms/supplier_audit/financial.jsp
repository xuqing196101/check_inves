<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<title>财务信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/merge_aptitude.js"></script>
		<style type="text/css">
			td {
				cursor: pointer;
			}
		</style>
		<script type="text/javascript">
			//默认不显示叉
			$(function() {
                // 导航栏选中
                $("#reverse_of_two").attr("class","active");
                $("#reverse_of_two").removeAttr("onclick");
				$("td").each(function() {
					$(this).find("p").hide();
				});

			});

			function reason(id, auditFieldName, year) {
				/* var offset = "";
	    if (window.event) {
		    e = event || window.event;
		    var x = "";
		    var y = "";
		    x = e.clientX + 20 + "px";
		    y = e.clientY + 20 + "px";
		    offset = [y, x];
	    } else {
	      offset = "200px";
	    } */
	     var supplierStatus= $("input[name='supplierStatus']").val();
       var sign = $("input[name='sign']").val();
        //只有审核的状态能审核
       if(supplierStatus == -2 || supplierStatus == 0 || supplierStatus == 4 || (sign == 3 && supplierStatus == 5)){
				var supplierId = $("#supplierId").val();
				if(auditFieldName == "财务信息") {
					var auditContent = year + "年财务信息"; //审批的字段内容
				} else {
					var auditContent = year + "年财务附件";
				}

				var index = layer.prompt({
						title: '请填写不通过的理由：',
						formType: 2,
						offset: '100px',
						maxlength: '100'
					},
					function(text) {
						var text = trim(text);
				 	  if(text != null && text !=""){
							$.ajax({
								url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.do",
								type: "post",
							  data: {"auditType":"basic_page","auditFieldName":auditFieldName,"auditContent":auditContent,"suggest":text,"supplierId":supplierId,"auditField":id},
								dataType: "json",
								success:function(result){
                  if(result.status == "503"){
                     layer.msg('该条信息已审核过！', {             
                       shift: 6, //动画类型
                       offset:'100px'
                    });
                  }
                }
							});
								if(auditFieldName == "财务信息") {
									$("#" + id + "_hidden").hide();
									$("#" + id + "_show").show();
								} 
								if(auditFieldName == "财务附件"){
									$("#" + id + "_hidden").hide();
									$("#" + id + "_show").show();
								}
							layer.close(index);
							}else{
		      			layer.msg('不能为空！', {offset:'100px'});
		      		}
					});
        }
			}

			/* function reason1(year, ele,auditField){
			  var offset = "";
			  if (window.event) {
			    e = event || window.event;
			    var x = "";
			    var y = "";
			    x = e.clientX + 20 + "px";
			    y = e.clientY + 20 + "px";
			    offset = [y, x];
			  } else {
			      offset = "200px";
			  }
			  var supplierId=$("#supplierId").val();
			  var value = $(ele).parents("li").find("span").text().replace("：","");//审批的字段名字
			  var auditFieldName=year+"年";//审批的字段名字
			  var fail = false;
			  var index = layer.prompt({
			      title: '请填写不通过的理由：', 
			      formType: 2, 
			      offset : offset,
			    }, 
			      function(text){
					      $.ajax({
					          url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
					          type:"post",
					          data:"auditType=finance_page"+"&auditFieldName="+auditFieldName+"&auditContent=附件"+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+auditField,
					          dataType:"json",
						          success:function(result){
						          result = eval("(" + result + ")");
						          if(result.msg == "fail"){
						           layer.msg('该条信息已审核过！', {
					                shift: 6, //动画类型
					                offset:'300px'
					            });
					            }
					          }
					        });
						        $(ele).parent("li").find("div").eq(1).show(); //隐藏勾
						        layer.close(index);
			        });
			    } */

			//下一步
			function nextStep() {
				var action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//上一步
			function lastStep() {
				var action = "${pageContext.request.contextPath}/supplierAudit/essential.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//文件下載
			/*   function downloadFile(fileName) {
			    $("input[name='fileName']").val(fileName);
			    $("#download_form_id").submit();
			  } */

			function download(id, key) {
				var form = $("<form>");
				form.attr('style', 'display:none');
				form.attr('method', 'post');
				form.attr('action', globalPath + '/file/download.html?id=' + id + '&key=' + key);
				$('body').append(form);
				form.submit();
			}
			
			// 提示修改之前的信息
			function showContent(field, id) {
				var supplierId = $("#supplierId").val();
				var showId = field + "_" +id;
				$.ajax({
					url: "${pageContext.request.contextPath}/supplierAudit/showModify.do",
					data: {"supplierId":supplierId, "beforeField":field, "modifyType":"finance_page", "relationId":id},
					async: false,
					success: function(result) {
						layer.tips("修改前:" + result, "#" + showId, 
						{
							tips: 3
						});
					}
				});
			}
			
			//删除左右两端的空格
			function trim(str){ 
				return str.replace(/(^\s*)|(\s*$)/g, "");
			}
			
			//暂存
        function zhancun(){
          var supplierId = $("#supplierId").val();
          $.ajax({
            url: "${pageContext.request.contextPath}/supplierAudit/temporaryAudit.do",
            dataType: "json",
            data:{supplierId : supplierId},
            success : function (result) {
                layer.msg(result, {offset : [ '100px' ]});
            },error : function(){
              layer.msg("暂存失败", {offset : [ '100px' ]});
            }
          });
        }
		</script>

		<script type="text/javascript">
			  /* function jump(str) {
				var action;
				if(str == "essential") {
					action = "${pageContext.request.contextPath}/supplierAudit/essential.html";
				}
				if(str == "financial") {
					action = "${pageContext.request.contextPath}/supplierAudit/financial.html";
				}
				if(str == "shareholder") {
					action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
				}
				/*if(str == "materialProduction") {
					action = "${pageContext.request.contextPath}/supplierAudit/materialProduction.html";
				}
				if(str == "materialSales") {
					action = "${pageContext.request.contextPath}/supplierAudit/materialSales.html";
				}
				if(str == "engineering") {
					action = "${pageContext.request.contextPath}/supplierAudit/engineering.html";
				}
				if(str == "serviceInformation") {
					action = "${pageContext.request.contextPath}/supplierAudit/serviceInformation.html";
				}* /
				if(str=="items"){
			        action = "${pageContext.request.contextPath}/supplierAudit/items.html";
			    }
			    if(str == "aptitude") {
					action = "${pageContext.request.contextPath}/supplierAudit/aptitude.html";
				}
				if(str == "contract") {
					action = "${pageContext.request.contextPath}/supplierAudit/contract.html";
				}
				if(str == "applicationForm") {
					action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
				}
				if(str == "reasonsList") {
					action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
				}
				if(str == "supplierType") {
					action = "${pageContext.request.contextPath}/supplierAudit/supplierType.html";
				}
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}  */
		</script>

	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a>支撑环境</a>
					</li>
					<li>
						<a>供应商管理</a>
					</li>
					<c:if test="${sign == 1}">
						<li>
							<a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=1')">供应商审核</a>
						</li>
					</c:if>
					<c:if test="${sign == 2}">
						<li>
							<a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=2')">供应商复核</a>
						</li>
					</c:if>
					<c:if test="${sign == 3}">
						<li>
							<a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=3')">供应商实地考察</a>
						</li>
					</c:if>
				</ul>
			</div>
		</div>
		<div class="container container_box">
			<div class="content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<%@include file="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp"%>

					<form id="form_id" action="" method="post">
						<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
						<input id="status" name="supplierStatus" value="${supplierStatus}" type="hidden">
						<input type="hidden" name="sign" value="${sign}">
					</form>

					<c:forEach items="${financial}" var="f" varStatus="vs">
						<h2 class="count_flow"><i>${vs.index + 1}</i>${f.year }年财务（金额单位：万元）</h2>
						<ul class="ul_list count_flow">
							<table class="table table-bordered  table-condensed table-hover">
								<thead>
									<tr>
										<!-- <th class="info">序号</th> -->
										<th class="info w50">年份</th>
										<th class="info" width="23%">会计事务所名称</th>
										<th class="info" width="13%">事务所联系电话</th>
										<th class="info" width="23%">审计人姓名（2人）</th>
										<!-- <th class="info">指标</th> -->
										<th class="info">资产总额</th>
										<th class="info">负债总额</th>
										<th class="info">净资产总额</th>
										<th class="info">营业收入</th>
										<th class="info w50">操作</th>
									</tr>
								</thead>
								<tr>
									<%-- <td class="tc">${vs.index + 1}</td> --%>
									<td class="tc w50" id="${f.id }">${f.year } </td>
									<td class="tl" id="name_${f.id }" <c:if test="${fn:contains(field,f.id.concat('_name'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('name','${f.id}');"</c:if>>${f.name }</td>
									<td class="tc" id="telephone_${f.id }" <c:if test="${fn:contains(field,f.id.concat('_telephone'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('telephone','${f.id}');"</c:if>>${f.telephone }</td>
									<td class="tl" id="auditors_${f.id }" <c:if test="${fn:contains(field,f.id.concat('_auditors'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('auditors','${f.id}');"</c:if>>${f.auditors }</td>
									<%-- <td class="tc">${f.quota }</td> --%>
									<td class="tc" id="totalAssets_${f.id }" <c:if test="${fn:contains(field,f.id.concat('_totalAssets'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('totalAssets','${f.id}');"</c:if>>${f.totalAssets }</td>
									<td class="tc" id="totalLiabilities_${f.id }" <c:if test="${fn:contains(field,f.id.concat('_totalLiabilities'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('totalLiabilities','${f.id}');"</c:if>>${f.totalLiabilities }</td>
									<td class="tc" id="totalNetAssets_${f.id }" <c:if test="${fn:contains(field,f.id.concat('_totalNetAssets'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('totalNetAssets','${f.id}');"</c:if>>${f.totalNetAssets}</td>
									<td class="tc" id="taking_${f.id }" <c:if test="${fn:contains(field,f.id.concat('_taking'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('taking','${f.id}');"</c:if>>${f.taking}</td>
									<td class="tc w50">
										<a onclick="reason('${f.id}_info','财务信息','${f.year}');" id="${f.id}_info_hidden" class="editItem"><c:if test="${fn:contains(passedField,f.id.concat('_info'))}"><img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png' class="hidden"></c:if> <c:if test="${!fn:contains(passedField,f.id.concat('_info'))}"><img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'></c:if></a>
										<p id="${f.id}_info_show"><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
										
										<c:if test="${fn:contains(passedField,f.id.concat('_info'))}">
											<img src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
										</c:if>
									</td>
								</tr>
							</table>
							
							<table class="table table-bordered  table-condensed table-hover">
							<thead>
								<tr>
									<th class="w50 info">年份</th>
									<th class="info">审计报告的审计意见</th>
									<th class="info">资产负债表</th>
									<th class="info">财务利润表</th>
									<th class="info">现金流量表</th>
									<th class="info">所有者权益变动表</th>
									<th class="info w50">操作</th>
								</tr>
							</thead>
							<tbody id="finance_attach_list_tbody_id">
								<tr class="tc">
									<td class="tc w50" id="${f.id }_file">${f.year}</td>
									<td class="tc" <c:if test="${fn:contains(fileModifyField,f.id.concat(supplierDictionaryData.supplierAuditOpinion))}">style="border: 1px solid #FF8C00;"</c:if>>
										<u:show showId="fina_${vs.index}_audit" delete="false" groups="fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${f.id}" typeId="${supplierDictionaryData.supplierAuditOpinion}" sysKey="${sysKey}" />
									</td>
									<td class="tc" <c:if test="${fn:contains(fileModifyField,f.id.concat(supplierDictionaryData.supplierLiabilities))}">style="border: 1px solid #FF8C00;"</c:if>>
										<u:show showId="fina_${vs.index}_lia" delete="false" groups="fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${f.id}" typeId="${supplierDictionaryData.supplierLiabilities}" sysKey="${sysKey}" />
									</td>
									<td class="tc" <c:if test="${fn:contains(fileModifyField,f.id.concat(supplierDictionaryData.supplierProfit))}">style="border: 1px solid #FF8C00;"</c:if>>
										<u:show showId="fina_${vs.index}_pro" delete="false" groups="fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${f.id}" typeId="${supplierDictionaryData.supplierProfit}" sysKey="${sysKey}" />
									</td>
									<td class="tc" <c:if test="${fn:contains(fileModifyField,f.id.concat(supplierDictionaryData.supplierCashFlow))}">style="border: 1px solid #FF8C00;"</c:if>>
										<u:show showId="fina_${vs.index}_cash" delete="false" groups="fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${f.id}" typeId="${supplierDictionaryData.supplierCashFlow}" sysKey="${sysKey}" />
						 		  </td>
									<td class="tc" <c:if test="${fn:contains(fileModifyField,f.id.concat(supplierDictionaryData.supplierOwnerChange))}">style="border: 1px solid #FF8C00;"</c:if>>
										<u:show showId="fina_${vs.index}_change" delete="false" groups="fina_0_pro,fina_1_pro,fina_2_pro,fina_0_audit,fina_1_audit,fina_2_audit,fina_0_lia,fina_1_lia,fina_2_lia,fina_0_cash,fina_1_cash,fina_2_cash,fina_0_change,fina_1_change,fina_2_change" businessId="${f.id}" typeId="${supplierDictionaryData.supplierOwnerChange}" sysKey="${sysKey}" />
								  </td>
								  <td class="tc w50">
											<a onclick="reason('${f.id}_file','财务附件','${f.year}');" id="${f.id}_file_hidden" class="editItem"><c:if test="${fn:contains(passedField,f.id.concat('_file'))}"><img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png' class="hidden"></c:if> <c:if test="${!fn:contains(passedField,f.id.concat('_file'))}"><img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'></c:if></a>
											<p id="${f.id}_file_show"><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
											
											<c:if test="${fn:contains(passedField,f.id.concat('_file'))}">
												<img src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
											</c:if>
								  </td>
								</tr>
							</tbody>
						</table>
							
							
							<%-- <table class="table table-bordered  table-condensed table-hover">
								<thead>
									<tr>
										<!-- <th class="info">序号</th> -->
										<th class="info w50">年份</th>
										<th class="info">财务利润表</th>
										<th class="info">审计报告的审计意见</th>
										<th class="info">资产负债表</th>
										<th class="info">现金流量表</th>
										<th class="info">所有者权益变动表</th>
										<th class="info w50">操作</th>
									</tr>
								</thead>
								<tbody id="finance_attach_list_tbody_id">
									<tr>
										<td class="tc">${vs.index + 1}</td>
										<td class="tc w50">${f.year}</td>
										<td class="tc">
											<a class="mt3 color7171C6" href="javascript:download('${f.auditOpinionId}', '${sysKey}')">${f.auditOpinion}</a>
										</td>
										<td class="tc">
											<a class="mt3 color7171C6" href="javascript:download('${f.liabilitiesListId}', '${sysKey}')">${f.liabilitiesList}</a>
										</td>
										<td class="tc">
											<a class="mt3 color7171C6" href="javascript:download('${f.profitListId}', '${sysKey}')">${f.profitList}</a>
										</td>
										<td class="tc">
											<a class="mt3 color7171C6" href="javascript:download('${f.cashFlowStatementId}', '${sysKey}')">${f.cashFlowStatement}</a>
										</td>
										<td class="tc">
											<a class="mt3 color7171C6" href="javascript:download('${f.changeListId}', '${sysKey}')">${f.changeList}</a>
										</td>
										<td class="tc w50">
											<a onclick="reason('${f.id}','财务附件');" id="${f.id}_hidden2" class="btn">审核</a>
											<p id="${f.id}_fileShow" class="b red">×</p>
										</td>
									</tr>
								</tbody>
							</table> --%>
						</ul>
					</c:forEach>
				</div>
				<div class="col-md-12 add_regist tc">
					<a class="btn" type="button" onclick="lastStep();">上一步</a>
					<c:if test="${supplierStatus == -2 or supplierStatus == 0 or supplierStatus ==4 or (sign ==3 and supplierStatus ==5)}">
            <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a>
          </c:if>
					<a class="btn" type="button" onclick="nextStep();">下一步</a>
				</div>
			</div>
		</div>

		<form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
			<input type="hidden" name="fileName" />
		</form>
	</body>

</html>
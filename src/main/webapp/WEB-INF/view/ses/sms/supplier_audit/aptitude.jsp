<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<title>资质文件</title>
		<script type="text/javascript">
			//默认不显示叉
			$(function() {
				$("td").each(function() {
					$(this).find("p").hide();
				});
			});
			
			//下一步
			function nextStep() {
				var action = "${pageContext.request.contextPath}/supplierAudit/contract.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//上一步
			function lastStep() {
				/*$("#form_id").attr("action", lastUrl);*/
				var action = "${pageContext.request.contextPath}/supplierAudit/supplierType.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}
			
			/* function reason(auditFieldName, auditContent, dex) {
				var supplierId = $("#supplierId").val();
				var index = layer.prompt({
						title: '请填写不通过的理由：',
						formType: 2,
						offset: '100px'
					},
					function(text) {
						$.ajax({
							url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
							type: "post",
							data: {
								"auditType": "aptitude_page",
								"auditFieldName": auditFieldName,
								"auditContent": auditContent + "附件信息",
								"suggest": text,
								"supplierId": supplierId,
								"auditField": auditContent
							},
							dataType: "json",
							success: function(result) {
								result = eval("(" + result + ")");
								if(result.msg == "fail") {
									layer.msg('该条信息已审核过！', {
										shift: 6, //动画类型
										offset: '100px'
									});
								}
							}
						});

						$("#" + dex + "_hidden").hide();
						$("#" + dex + "_show").show();
						layer.close(index);
					});
			}
			 */
			
			function reason(auditField, auditFieldName, auditContent) {
				var supplierId = $("#supplierId").val();
				var index = layer.prompt({
					title: '请填写不通过的理由：',
					formType: 2,
					offset: '100px',
					maxlength: '100'
				}, function(text) {
					var text = trim(text);
				  if(text != null && text !=""){
						$.ajax({
							url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
							type: "post",
							data: "&auditFieldName=" + auditFieldName + "&suggest=" + text + "&supplierId=" + supplierId + "&auditType=aptitude_page" + "&auditContent=" + auditContent + "&auditField=" + auditField,
							dataType: "json",
							success: function(result) {
								result = eval("(" + result + ")");
								if(result.msg == "fail") {
									layer.msg('该条信息已审核过！', {
										shift: 6, //动画类型
										offset: '100px'
									});
								}
							}
						});
							$("#"+ auditField +"").show(); //显示叉
							layer.close(index);
						}else{
		      		layer.msg('不能为空！', {offset:'100px'});
		      	};
				});
			}
			
			
			function reasonProject(auditField, auditFieldName, auditContent) {
				var supplierId = $("#supplierId").val();
				var index = layer.prompt({
					title: '请填写不通过的理由：',
					formType: 2,
					offset: '100px',
					maxlength: '100',
				}, function(text) {
					var text = trim(text);
				  if(text != null && text !=""){
						$.ajax({
							url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
							type: "post",
							data: "&auditFieldName=" + auditFieldName + "&suggest=" + text + "&supplierId=" + supplierId + "&auditType=aptitude_page" + "&auditContent=" + auditContent + "&auditField=" + auditField,
							dataType: "json",
							success: function(result) {
								result = eval("(" + result + ")");
								if(result.msg == "fail") {
									layer.msg('该条信息已审核过！', {
										shift: 6, //动画类型
										offset: '100px',
									});
								};
							}
						});
							$("#" + auditField + "_show").show();
								$("#" + auditField + "_hidden").hide();
							layer.close(index);
						}else{
		      		layer.msg('不能为空！', {offset:'100px'});
		      	};
				});
			}
			
			//删除左右两端的空格
			function trim(str){ 
				return str.replace(/(^\s*)|(\s*$)/g, "");
			}
			
			//暂存
        function zhancun(){
         var supplierId = $("#id").val();
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
			function jump(str) {
				var action;
				if(str == "essential") {
					action = "${pageContext.request.contextPath}/supplierAudit/essential.html";
				}
				if(str == "financial") {
					action = "${pageContext.request.contextPath}/supplierAudit/financial.html";
				}
				/*if(str == "shareholder") {
					action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
				}
				if(str == "materialProduction") {
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
				}*/
				if(str == "items") {
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
			}
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
			<div class="content ">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="flow_step">
						<li onclick="jump('essential')">
							<a aria-expanded="false" href="#tab-1">基本信息</a>
							<i></i>
						</li>
						<li onclick="jump('financial')">
							<a aria-expanded="true" href="#tab-2">财务信息</a>
							<i></i>
						</li>
						<li onclick="jump('shareholder')">
							<a aria-expanded="false" href="#tab-3">股东信息</a>
							<i></i>
						</li>
						<%--<c:if test="${fn:contains(supplierTypeNames, '生产')}">
							<li onclick="jump('materialProduction')">
								<a aria-expanded="false" href="#tab-4">生产信息</a>
								<i></i>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '销售')}">
							<li onclick="jump('materialSales')">
								<a aria-expanded="false" href="#tab-4">销售信息</a>
								<i></i>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '工程')}">
							<li onclick="jump('engineering')">
								<a aria-expanded="false" href="#tab-4">工程信息</a>
								<i></i>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeNames, '服务')}">
							<li onclick="jump('serviceInformation')">
								<a aria-expanded="false" href="#tab-4" data-toggle="tab">服务信息</a>
								<i></i>
							</li>
						</c:if>
						--%>
						<li onclick = "jump('supplierType')">
           	  <a aria-expanded="false">供应商类型</a>
            	<i></i>
	          </li>
						<!-- <li onclick="jump('items')">
							<a aria-expanded="false" href="#tab-4">产品类别</a>
							<i></i>
						</li> -->
						<li onclick="jump('aptitude')" class="active">
							<a aria-expanded="false" href="#tab-4">资质文件维护</a>
							<i></i>
						</li>
						<li onclick="jump('contract')">
							<a aria-expanded="false" href="#tab-4">销售合同</a>
							<i></i>
						</li>
						<li onclick="jump('applicationForm')">
							<a aria-expanded="false" href="#tab-4">承诺书和申请表</a>
							<i></i>
						</li>
						<li onclick="jump('reasonsList')">
							<a aria-expanded="false" href="#tab-4">审核汇总</a>
						</li>
					</ul>
					<ul class="count_flow ul_list count_flow">
						<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab">
							<c:set value="0" var="liCountPro" />
							<c:set value="0" var="liCountSell" />
							<c:set value="0" var="liCountEng" />
							<c:set value="0" var="liCountSer" />
							<c:if test="${fn:contains(supplierTypeNames, '生产') and fn:length(cateList) > 0}">
							<c:set value="${liCountPro+1}" var="liCountPro"/>
								<li id="li_id_1" class="active">
									<a aria-expanded="true" href="#tab-1" data-toggle="tab">物资-生产型资质信息</a>
								</li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '销售') and fn:length(saleQua) > 0}">
								<li id="li_id_2" class='<c:if test="${liCountPro == 0}">active  <c:set value="${liCountSell+1}" var="liCountSell"/></c:if>'>
									<a aria-expanded="false" href="#tab-2" data-toggle="tab">物资-销售型资质信息</a>
								</li>						
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '工程')}">
								<li id="li_id_3" class='<c:if test="${liCountSell == 0 && liCountPro == 0}">active <c:set value="${liCountEng+1}" var="liCountEng"/></c:if>'>
									<a aria-expanded="false" href="#tab-3" data-toggle="tab">工程资质信息</a>
								</li>
							</c:if>
							<c:if test="${fn:contains(supplierTypeNames, '服务') and fn:length(serviceQua) > 0}">
								<li id="li_id_4" class='<c:if test="${liCountEng == 0 && liCountPro == 0 && liCountEng == 0}">active <c:set value="${liCountSer+1}" var="liCountSer"/></c:if>'>
									<a aria-expanded="false" href="#tab-4" data-toggle="tab">服务资质信息</a>
								</li>
							</c:if>
						</ul>

						<div class="tab-content padding-top-20" id="tab_content_div_id">

							<!-- 物资生产型 -->
							<c:if test="${fn:contains(supplierTypeNames, '生产')}">
								<c:set value="0" var="prolength" />
								<div class="tab-pane fade active in" id="tab-1">
									<table class="table table-bordered">
										<!-- <thead>
											<tr>
												<th class="tc info">名称</th>
												<th class="tc info">环保管理体系认证证书</th>
												<th class="tc info">国家行业准入证书</th>
												<th class="tc info">质量管理体系认证证书</th>
												<th class="tc info">审核操作</th>
											</tr>
										</thead> -->
										<c:forEach items="${cateList }" var="obj" varStatus="vs">
											<tr >
												<td class="tc info w120">${obj.categoryName } </td>
												<c:forEach items="${obj.list }" var="quaPro">
													<td>
														<c:set value="${prolength+1}" var="prolength"></c:set>
														<span <c:if test="${fn:contains(fileModifyField,quaPro.flag)}">style="border: 1px solid #FF8C00;"</c:if> class="hand" onclick="reason('${quaPro.flag}','${obj.categoryName }','生产-${quaPro.name}');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${quaPro.name}：</span>
													  <u:show showId="pShow${prolength}" groups="${saleShow}" delete="false" businessId="${quaPro.flag}" sysKey="${sysKey }" typeId="${typeId}" />
														<p id="${quaPro.flag}" ><img style="padding-left: 20px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
														
														<c:if test="${fn:contains(passedField,quaPro.flag)}">
															<img style="padding-left: 20px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
														</c:if>
													</td>
												</c:forEach>
												<%-- <td class="tc w100">
													<a onclick="reason('物资生产品目信息','${obj.categoryName }','${vs.index + 1}');" id="${vs.index + 1}_hidden" class="btn">审核</a>
													<p id="${vs.index + 1}_show"><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
												</td> --%>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>

							<!-- 物资销售型 -->
							<c:if test="${fn:contains(supplierTypeNames, '销售')}">
								<c:set value="0" var="length"> </c:set>
								<div class="tab-pane <c:if test="${liCountSell == 1}">active in</c:if> fade height-300" id="tab-2">
									<table class="table table-bordered">
										<!-- <thead>
											<tr>
												<th class="tc info">名称</th>
												<th class="tc info">环保管理体系认证证书</th>
												<th class="tc info">国家行业准入证书</th>
												<th class="tc info">质量管理体系认证证书</th>
												<th class="tc info">审核操作</th>
											</tr>
										</thead> -->
										<c:forEach items="${saleQua }" var="sale">
											<tr>
												<td class="tc info">${sale.categoryName } </td>
												<c:forEach items="${sale.list }" var="saua" varStatus="vs">
													<td>
														<c:set value="${length+1}" var="length"></c:set>
														<span <c:if test="${fn:contains(fileModifyField,saua.flag)}">style="border: 1px solid #FF8C00;"</c:if> class="hand" onclick="reason('${saua.flag}','${sale.categoryName }','销售-${saua.name}');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${saua.name}：</span>
														<u:show showId="saleShow${length}" groups="${saleShow}" delete="false" businessId="${saua.flag}" sysKey="${sysKey }" typeId="${typeId}" />							
														<p id="${saua.flag}" ><img style="padding-left: 20px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
													
														<c:if test="${fn:contains(passedField,saua.flag)}">
															<img style="padding-left: 20px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
														</c:if>
													</td>
												</c:forEach>
												<%-- <td class="tc w100">
													<a onclick="reason('物资销售品目信息','${sale.categoryName }','${vs.index + 1}');" id="${vs.index + 1}_hidden" class="btn">审核</a>
													<p id="${vs.index + 1}_show"><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
												</td> --%>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>

							<!-- 工程 -->
							<c:if test="${fn:contains(supplierTypeNames, '工程')}">
								<div class="tab-pane <c:if test="${liCountEng == 1}">active in</c:if> fade" id="tab-3">
									<c:set value="0" var="plength"> </c:set>
									<table class="table table-bordered">
										<thead>
											<tr>
									    	<th class="info tc w50">序号</th>
									      <!-- <th class="info tc">类别</th>
									      <th class="info tc">大类</th>
									      <th class="info tc">中类</th> -->
									      <th class="info tc" width="20%">产品类别</th>
									      <th class="info tc" width="25%">资质类型</th>
									      <th class="info tc" width="15%">证书编号</th>
									      <th class="info tc" width="10%">专业类别</th>
									      <th class="info tc" width="10%">资质等级</th>
									     	<th class="info tc">证书图片</th>
									     	<th class="info tc">操作</th>
										  </tr>
										</thead>
								    <c:forEach items="${allTreeList}" var="cate" varStatus="vs">
								      <tr>
								      	<td>${vs.index + 1}</td>
								      	<%-- <td>${cate.rootNode}</td>
								      	<td>${cate.firstNode}</td>
								      	<td>${cate.secondNode}</td> --%>
								      	<td>
									      	<c:choose>
		                      	<c:when test="${cate.fourthNode!=null}">
		                            ${cate.fourthNode}
		                        </c:when>
	                        	<c:otherwise>
	                          	<c:choose>
	                            	<c:when test="${cate.thirdNode!=null}">
	                              	${cate.thirdNode}
	                              </c:when>
	                              <c:otherwise>
	                              	<c:choose>
	                                	<c:when test="${cate.secondNode!=null}">
	                                  	${cate.secondNode}
	                                  </c:when>
	                                  <c:otherwise>
	                                  	${cate.firstNode}
	                                  </c:otherwise>
	                                </c:choose>
	                            	</c:otherwise>
	                            </c:choose>
	                        	</c:otherwise>
	                    		</c:choose>
								      	</td>
								      	<td>
								      		<c:forEach items="${cate.typeList}" var="type">
										      	<c:if test="${cate.qualificationType eq type.id}">${type.name}</c:if>
										     	</c:forEach>
										    </td>
								      	<td>${cate.certCode}</td>
								      	<td>${cate.proName}</td>
								      	<td>${cate.level.name}</td>
								      	<td>
								      		<div class="w110 fl">
								      			<u:show showId="eng_show_${vs.index}" businessId="${cate.fileId}" typeId="${engTypeId}" sysKey="${sysKey}" delete="false"/>
								      		</div>
								      	</td>
								      	<td class="tc w50">
													<a id="${cate.itemsId}_hidden" onclick="reasonProject('${cate.itemsId}','${cate.firstNode }','工程-${cate.firstNode}');"><c:if test="${!fn:contains(passedField,cate.itemsId)}"><img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'></c:if>  <c:if test="${fn:contains(passedField,cate.itemsId)}"><img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png' class="hidden"></c:if></a>
													<p id="${cate.itemsId}_show"><img style="padding-left: 20px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
													<c:if test="${fn:contains(passedField,cate.itemsId)}">
														<img style="padding-left: 20px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
													</c:if>
											 	</td>
								      </tr>
								    </c:forEach>
								  </table>
									
									
									<%-- <table class="table table-bordered">
										<c:forEach items="${projectQua }" var="project">
											<tr>
												<td class="tc info">${project.categoryName }
													<c:forEach items="${project.list }" var="pr" varStatus="vs">
														<td>
															<c:set value="${plength+1}" var="plength"></c:set>
															<span class="hand" onclick="reason('${pr.flag}','${project.categoryName }','工程-${pr.name}');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${pr.name}：</span>
															<a>
																<u:show showId="projectShow${plength}" delete="false" groups="${saleShow}" businessId="${pr.flag}" sysKey="${sysKey }" typeId="${typeId}" />
															</a>
															
															<p id="${pr.flag}" ><img style="padding-left: 20px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
														</td>
													</c:forEach>
											</tr>
										</c:forEach>
									</table> --%>
								</div>
							</c:if>

							<!-- 服务 -->
							<c:if test="${fn:contains(supplierTypeNames, '服务')}">
								<div class="tab-pane <c:if test="${liCountSer == 1}">active in</c:if> fade height-200" id="tab-4">
									<table class="table table-bordered">
										<c:set value="0" var="slength"> </c:set>
										<!-- <thead>
											<tr>
												<th class="tc info">名称</th>
												<th class="tc info">环保管理体系认证证书</th>
												<th class="tc info">国家行业准入证书</th>
												<th class="tc info">质量管理体系认证证书</th>
												<th class="tc info">审核操作</th>
											</tr>
										</thead> -->
										<c:forEach items="${serviceQua }" var="server">
											<tr>
												<td class="tc info">${server.categoryName }
												</td>
													<c:forEach items="${server.list }" var="ser" varStatus="vs">
														<td>
															<c:set value="${slength+1}" var="slength"></c:set>
															<span <c:if test="${fn:contains(fileModifyField,ser.flag)}">style="border: 1px solid #FF8C00;"</c:if> class="hand" onclick="reason('${ser.flag}','${server.categoryName }','服务-${ser.name}');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'">${ser.name}：</span>
															<u:show showId="serverShow${plength}" delete="false" groups="${saleShow}" businessId="${ser.flag}" sysKey="${sysKey }" typeId="${typeId}" />
															<p id="${ser.flag}"><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
															
															<c:if test="${fn:contains(passedField,ser.flag)}">
																<img style="padding-left: 20px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
															</c:if>
														</td>
													</c:forEach>
												
												<%-- <td class="tc w100">
													<a onclick="reason('服务品目信息','${ser.categoryName }','${vs.index + 1}');" id="${vs.index + 1}_hidden" class="btn">审核</a>
													<p id="${saua.id}_销售" ><img style="padding-left: 20px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
												</td> --%>
											</tr>
										</c:forEach>
									</table>
								</div>
							</c:if>
						</div>
						
						</ul>
						<div class="col-md-12 col-sm-12 col-xs-12 add_regist tc">
							<a class="btn" type="button" onclick="lastStep();">上一步</a>
							<a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a>
							<a class="btn" type="button" onclick="nextStep();">下一步</a>
					</div>
				</div>
			</div>
		</div>
		<form id="form_id" action="" method="post">
			<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
			<input name="supplierStatus" value="${supplierStatus}" type="hidden">
			<input type="hidden" name="sign" value="${sign}">
		</form>
	</body>

</html>
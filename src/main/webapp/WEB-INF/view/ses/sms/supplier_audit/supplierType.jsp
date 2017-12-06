<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
		<title>供应商类型</title>
		<style type="text/css">
			td {
				cursor: pointer;
			}
			
			input {
				cursor: pointer;
			}
			
			.inline {
				display: inline;
			}
			.abolish_img {
				margin-bottom: 3px;
				margin-left: 10px;
			}
			.abolish_img_file {
				position: absolute;
		    right: 20px;
		    color: #ef0000;
		    font-weight: bold;
		    font-size: 18px;
		    cursor: pointer;
		    top: 5px;
			}
			.icon_edit,.icon_sc{
      	padding: 5px;
      }
		</style>
		<script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/essential.js"></script>
		<script type="text/javascript">
			$(function() {
				
				//选中信息头
				var typeIds = "${supplierTypeCode}";
				var ids = typeIds.split(",");
				//回显
				var checklist1 = document.getElementsByName("chkItem_1");
				for(var i = 0; i < checklist1.length; i++) {
					var vals = checklist1[i].value;
					for(var j = 0; j < ids.length; j++) {
						if(ids[j] == vals) {
							checklist1[i].checked = true;
						}
					}
				}
				var checklist2 = document.getElementsByName("chkItem_2");
				for(var i = 0; i < checklist2.length; i++) {
					var vals = checklist2[i].value;
					for(var j = 0; j < ids.length; j++) {
						if(ids[j] == vals) {
							checklist2[i].checked = true;
						}
					}
				}
			});
			
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
			<div class="content">
				<div class="col-md-12 col-sm-12 col-xs-12 tab-v2 job-content">
					<%-- <%@include file="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp"%> --%>
          <jsp:include page="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp">
          	<jsp:param value="four" name="currentStep"/>
           	<jsp:param value="${supplierId }" name="supplierId"/>
           	<jsp:param value="${supplierStatus }" name="supplierStatus"/>
           	<jsp:param value="${sign }" name="sign"/>
          </jsp:include>
					<!-- 供应商类型信息头 -->
					<ul class="ul_list count_flow">
						<li>
							<div class="tc">
			       		<c:forEach items="${scxsList }" var="obj">
			       			<div class="margin-left-30 inline"
			       				<c:if test="${fn:contains(unableTypeField,obj.id)}">style="border:1px solid #FF0000"</c:if>>
			       				<span class="hand"
			       					<c:if test="${fn:contains(auditTypeField,obj.id) && !fn:contains(unableTypeField,obj.id)}">style="border:1px solid #FF0000"</c:if>
			       					<c:if test="${fn:contains(supplierTypeCode,obj.code)}">onclick="auditType(this,'supplierType_page','${obj.id}','${obj.name}');"</c:if>>
			       					<input type="checkbox" disabled="disabled" name="chkItem_1" value="${obj.code}"/> ${obj.name }
			       				</span>
						      	<%-- <a class="b f18 ml10 red" id="${obj.id}_show" style="visibility:hidden"><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a> --%>
						     		<c:if test="${fn:contains(unableTypeField,obj.id)}">
						     			<img src='${pageContext.request.contextPath}/public/backend/images/sc.png' class="abolish_img"/>
						     		</c:if>
			       			</div>
					      </c:forEach>
					      <c:forEach items="${gcfwList }" var="obj">
					      	<div class="margin-left-30 inline"
					      		<c:if test="${fn:contains(unableTypeField,obj.id)}">style="border:1px solid #FF0000"</c:if>>
								    <span class="hand"
								    	<c:if test="${fn:contains(auditTypeField,obj.id) && !fn:contains(unableTypeField,obj.id)}">style="border:1px solid #FF0000"</c:if>
								    	<c:if test="${fn:contains(supplierTypeCode,obj.code)}">onclick="auditType(this,'supplierType_page','${obj.id}','${obj.name}');"</c:if>>
								    	<input type="checkbox" disabled="disabled" name="chkItem_2" value="${obj.code }"/>${obj.name }
								    </span>
						      	<%-- <a class="b f18 ml10 red" id="${obj.id}_show" style="visibility:hidden"><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a> --%>
						      	<c:if test="${fn:contains(unableTypeField,obj.id)}">
						     			<img src='${pageContext.request.contextPath}/public/backend/images/sc.png' class="abolish_img"/>
						     		</c:if>
					     		</div>
					      </c:forEach>
						  </div>
						</li>
					</ul>

					<ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab count_flow clear">
						<c:set value="0" var="liCountPro" />
						<c:set value="0" var="liCountSell" />
						<c:set value="0" var="liCountEng" />
						<c:set value="0" var="liCountSer" />
						<c:if test="${fn:contains(supplierTypeCode, 'PRODUCT')}">
							<li class="active">
								<a aria-expanded="true" href="#tab-1" data-toggle="tab">物资-生产型专业信息</a>
								<c:set value="${liCountPro+1}" var="liCountPro"/>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeCode, 'SALES')}">
							<li class='<c:if test="${liCountPro == 0}">active  <c:set value="${liCountSell+1}" var="liCountSell"/></c:if>'>
								<a aria-expanded="false" href="#tab-2" data-toggle="tab">物资-销售型专业信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeCode, 'PROJECT')}">
							<li class='<c:if test="${liCountSell == 0 && liCountPro == 0}">active <c:set value="${liCountEng+1}" var="liCountEng"/></c:if>'>
								<a aria-expanded="false" href="#tab-3" data-toggle="tab" onmouseup="init_web_upload_in('#tab-3')">工程专业信息</a>
							</li>
						</c:if>
						<c:if test="${fn:contains(supplierTypeCode, 'SERVICE')}">
							<li class='<c:if test="${liCountSell == 0 && liCountPro == 0 && liCountEng == 0}">active <c:set value="${liCountSer+1}" var="liCountSer"/></c:if>'>
								<a aria-expanded="false" href="#tab-4" data-toggle="tab">服务专业信息</a>
							</li>
						</c:if>
					</ul>
					<div class="count_flow">
						<div class="tab-content padding-top-20" id="tab_content_div_id">
							<c:if test="${fn:contains(supplierTypeCode, 'PRODUCT')}">
								<div class="tab-pane fade active in height-300" id="tab-1">
								<%-- <h2 class="count_flow"><i>1</i>组织结构和人员</h2>
									<ul class="ul_list count_flow">
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">组织机构：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="orgName" type="text" value="${supplierMatPros.orgName }" onclick="auditText(this,'mat_pro_page','orgName')" <c:if test="${fn:contains(field,'orgName')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','orgName');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">人员总数：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalPerson" type="text" value="${supplierMatPros.totalPerson }" onclick="auditText(this,'mat_pro_page','totalPerson')" <c:if test="${fn:contains(field,'totalPerson')}"> style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','totalPerson');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">管理人员：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalMange" type="text" value="${supplierMatPros.totalMange }" onclick="auditText(this,'mat_pro_page','totalMange')" <c:if test="${fn:contains(field,'totalMange')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','totalMange');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">技术人员：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalTech" type="text" value="${supplierMatPros.totalTech }" onclick="auditText(this,'mat_pro_page','totalTech')" <c:if test="${fn:contains(field,'totalTech')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','totalTech');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">工人(职员)：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalWorker" type="text" value="${supplierMatPros.totalWorker }" onclick="auditText(this,'mat_pro_page','totalWorker')" <c:if test="${fn:contains(field,'totalWorker')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','totalWorker');"</c:if>/>
											</div>
										</li>
									</ul> --%>
									
									<h2 class="count_flow"><i>1</i>产品研发能力</h2>
									<ul class="ul_list">
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">技术人员数量比例(%)：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="scaleTech" type="text" value="${supplierMatPros.scaleTech }" 
													<c:if test="${fn:contains(fieldProOne,'scaleTech') && !fn:contains(auditProField,'scaleTech')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','scaleTech');"</c:if> 
													<c:if test="${fn:contains(auditProField,'scaleTech')}">style="border: 1px solid red;"</c:if>
													onclick="auditText(this,'mat_pro_page','scaleTech')"/>
												<c:if test="${fn:contains(unableProField,'scaleTech')}">
													<a class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
												</c:if>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">高级技术人员数量比例(%)：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="scaleHeightTech" type="text" value="${supplierMatPros.scaleHeightTech }" 
													<c:if test="${fn:contains(fieldProOne,'scaleHeightTech') && !fn:contains(auditProField,'scaleHeightTech')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','scaleHeightTech');"</c:if> 
													<c:if test="${fn:contains(auditProField,'scaleHeightTech')}">style="border: 1px solid red;"</c:if>
													onclick="auditText(this,'mat_pro_page','scaleHeightTech')"/>
												<c:if test="${fn:contains(unableProField,'scaleHeightTech')}">
													<a class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
												</c:if>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" onclick="reason1(this)">研发部门名称：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="researchName" type="text" value="${supplierMatPros.researchName }" onclick="auditText(this,'mat_pro_page','researchName')" <c:if test="${fn:contains(fieldProOne,'researchName') && !fn:contains(auditProField,'researchName')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','researchName');"</c:if> <c:if test="${fn:contains(auditProField,'researchName')}">style="border: 1px solid red;"</c:if>/>
												<c:if test="${fn:contains(unableProField,'researchName')}">
													<a class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
												</c:if>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" onclick="reason1(this)">研发部门人数：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalResearch" type="text" value="${supplierMatPros.totalResearch }" onclick="auditText(this,'mat_pro_page','totalResearch')" <c:if test="${fn:contains(fieldProOne,'totalResearch') && !fn:contains(auditProField,'totalResearch')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','totalResearch');"</c:if> <c:if test="${fn:contains(auditProField,'totalResearch')}">style="border: 1px solid red;"</c:if>/>
												<c:if test="${fn:contains(unableProField,'totalResearch')}">
													<a class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
												</c:if>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">研发部门负责人：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="researchLead" type="text" value="${supplierMatPros.researchLead }" onclick="auditText(this,'mat_pro_page','researchLead')" <c:if test="${fn:contains(fieldProOne,'researchLead') && !fn:contains(auditProField,'researchLead')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','researchLead');"</c:if> <c:if test="${fn:contains(auditProField,'researchLead')}">style="border: 1px solid red;"</c:if>/>
												<c:if test="${fn:contains(unableProField,'researchLead')}">
													<a class='abolish'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
												</c:if>
											</div>
										</li>
										<li class="col-md-12 col-sm-12 col-xs-12 ">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">承担国家军队科研项目：</span>
											<div class="col-md-12 col-sm-12 col-xs-12 p0">
												<textarea class="col-md-12 col-xs-12 col-sm-12 h80 hand" id="countryPro" onclick="auditText(this,'mat_pro_page','countryPro')" <c:if test="${fn:contains(fieldProOne,'countryPro') && !fn:contains(auditProField,'countryPro')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','countryPro');"</c:if> <c:if test="${fn:contains(auditProField,'countryPro')}">style="border: 1px solid red;"</c:if>>${supplierMatPros.countryPro }</textarea>
												<c:if test="${fn:contains(unableProField,'countryPro')}">
													<a class='abolish' style='margin-top: 6px;'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
												</c:if>
											</div>
										</li>
										<li class="col-md-12 col-sm-12 col-xs-12 ">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">获得国家军队科技奖项：</span>
											<div class="col-md-12 col-sm-12 col-xs-12 p0">
												<textarea class="col-md-12 col-xs-12 col-sm-12 h80 hand" id="countryReward" onclick="auditText(this,'mat_pro_page','countryReward')" <c:if test="${fn:contains(fieldProOne,'countryReward') && !fn:contains(auditProField,'countryReward')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','countryReward');"</c:if> <c:if test="${fn:contains(auditProField,'countryReward')}">style="border: 1px solid red;"</c:if>>${supplierMatPros.countryReward }</textarea>
												<c:if test="${fn:contains(unableProField,'countryReward')}">
													<a class='abolish' style='margin-top: 6px;'><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
												</c:if>
											</div>
										</li>
									</ul>
									
									<%-- <h2 class="count_flow"><i>3</i>生产能力</h2>
									<ul class="ul_list count_flow">
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">生产线名称数量：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalBeltline" type="text" value="${supplierMatPros.totalBeltline }" onclick="auditText(this,'mat_pro_page','totalBeltline')" <c:if test="${fn:contains(field,'totalBeltline')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','totalBeltline');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">生产设备名称数量：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalDevice" type="text" value="${supplierMatPros.totalDevice }" onclick="auditText(this,'mat_pro_page','totalDevice')" <c:if test="${fn:contains(field,'totalDevice')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','totalDevice');"</c:if>/>
											</div>
										</li>
									</ul> --%>
									
									<%-- <h2 class="count_flow"><i>4</i>质量检测能力</h2>
									<ul class="ul_list count_flow">
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">质量检测部门：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="qcName" type="text" value="${supplierMatPros.qcName }" onclick="auditText(this,'mat_pro_page','qcName')" <c:if test="${fn:contains(field,'qcName')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','qcName');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">质量检测人数：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalQc" type="text" value="${supplierMatPros.totalQc }" onclick="auditText(this,'mat_pro_page','totalQc')" <c:if test="${fn:contains(field,'totalQc')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','totalQc');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">质检部门负责人：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="qcLead" type="text" value="${supplierMatPros.qcLead }" onclick="auditText(this,'mat_pro_page','qcLead')" <c:if test="${fn:contains(field,'qcLead')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','qcLead');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">质量检测设备名称：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="qcDevice" type="text" onclick="auditText(this,'mat_pro_page','qcDevice')" value="${supplierMatPros.qcDevice }" <c:if test="${fn:contains(field,'qcDevice')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_pro_page','qcDevice');"</c:if>>
											</div>
										</li>
									</ul> --%>
									
									<h2 class="count_flow"><i>2</i>资质证书信息</h2>
									<div class="ul_list">
										<table class="table table-bordered table-condensed table-hover m_table_fixed_border">
											<thead>
												<tr>
													<th class="info w50">序号</th>
													<th class="info" width="15%">资质证书名称</th>
													<th class="info" width="12%">证书编号</th>
													<th class="info">资质等级</th>
													<th class="info" width="12%">发证机关或机构</th>
													<th class="info">有效期(起止时间)</th>
													<th class="info">有效期(起止时间)</th>
													<th class="info">证书状态</th>
													<th class="info">证书图片</th>
													<th class="info w50">操作 </th>
												</tr>
											</thead>
											<c:forEach items="${materialProduction}" var="m" varStatus="vs">
												<tr >
													<td class="tc">${vs.index + 1}</td>
													<td class="tl" id="name_${m.id}" <c:if test="${fn:contains(fieldProTwo,m.id.concat('_name'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_pro_page','name','${m.id }');"</c:if>>${m.name }</td>
													<td class="tl" id="code_${m.id}"<c:if test="${fn:contains(fieldProTwo,m.id.concat('_code'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_pro_page','code','${m.id }');"</c:if>>${m.code} </td>
													<td class="tc" id="levelCert_${m.id}" <c:if test="${fn:contains(fieldProTwo,m.id.concat('_levelCert'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_pro_page','levelCert','${m.id }');"</c:if>>${m.levelCert}</td>
													<td class="tl" id="licenceAuthorith_${m.id}" <c:if test="${fn:contains(fieldProTwo,m.id.concat('_licenceAuthorith'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_pro_page','licenceAuthorith','${m.id }');"</c:if>>${m.licenceAuthorith }</td>
													<td class="tc" id="expStartDate_${m.id}" <c:if test="${fn:contains(fieldProTwo,m.id.concat('_expStartDate'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_pro_page','expStartDate','${m.id }');"</c:if>>
														<fmt:formatDate value="${m.expStartDate }" pattern='yyyy-MM-dd' />
													</td>
													<td class="tc" id="expEndDate_${m.id}" <c:if test="${fn:contains(fieldProTwo,m.id.concat('_expEndDate'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_pro_page','expEndDate','${m.id }');"</c:if>>
														<fmt:formatDate value="${m.expEndDate }" pattern='yyyy-MM-dd' />
													</td>
													<td class="tc" id="mot_${m.id}" <c:if test="${fn:contains(fieldProTwo,m.id.concat('_mot'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_pro_page','mot','${m.id }');"</c:if>>${m.mot}</td>
													<td class="tc" <c:if test="${fn:contains(fileModifyField,m.id.concat(supplierDictionaryData.supplierProCert))}">style="border: 1px solid #FF8C00;"</c:if>>
														<u:show showId="pro_show${vs.index+1}" delete="false" businessId="${m.id}" typeId="${supplierDictionaryData.supplierProCert}" sysKey="${sysKey}" />
													</td>
													<td class="tc w50" >
														<%-- <c:if test="${!fn:contains(unableProField,m.id)}">
															<p onclick="reasonProduction('${m.id}','${m.name}');" id="${m.id}_hidden" class="editItem">
																<c:if test="${!fn:contains(auditProField,m.id)}">
	                                <img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
	                              </c:if>
	                              <c:if test="${fn:contains(auditProField,m.id)}">
	                                <img src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
	                              </c:if>
															</p>
														</c:if>
														<c:if test="${fn:contains(unableProField,m.id)}">
                              <img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                            </c:if> --%>
                            <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon.png" />
                            <c:set var="iconCls" value="icon_edit" />
                            <c:if test="${!fn:contains(unableProField,m.id) && fn:contains(auditProField,m.id)}">
                            	<c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon_2.png" />
                            </c:if>
                            <c:if test="${fn:contains(unableProField,m.id)}">
                              <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/sc.png" />
                              <c:set var="iconCls" value="icon_sc" />
                            </c:if>
                            <img src="${iconUrl}" class="${iconCls}"
                            onclick="auditList(this,'mat_pro_page','${m.id}','物资生产-资质证书','${m.name}');" />
													</td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</div>
							</c:if>

							<c:if test="${fn:contains(supplierTypeCode, 'SALES')}">
								<div class="tab-pane <c:if test="${liCountSell == 1}">active in</c:if> fade in height-200" id="tab-2">
									<%-- <h2 class="count_flow"><i>1</i>供应商组织结构和人员</h2>
									<ul class="ul_list">
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">组织机构：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="orgName_sale" type="text" value="${supplierMatSells.orgName }" onclick="reasonSale1(this)" <c:if test="${fn:contains(field,'orgName')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_sell_page','orgName');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">人员总数：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalPerson_sale" type="text" value="${supplierMatSells.totalPerson }" onclick="reasonSale1(this)" <c:if test="${fn:contains(field,'totalPerson')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_sell_page','totalPerson');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">管理人员：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalMange_sale" type="text" value="${supplierMatSells.totalMange }" onclick="reasonSale1(this)" <c:if test="${fn:contains(field,'totalMange')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_sell_page','totalMange');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">技术人员：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalTech_sale" type="text" value="${supplierMatSells.totalTech }" onclick="reasonSale1(this)" <c:if test="${fn:contains(field,'totalTech')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_sell_page','totalTech');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">工人(职员)：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalWorker_sale" type="text" value="${supplierMatSells.totalWorker }" onclick="reasonSale1(this)" <c:if test="${fn:contains(field,'totalWorker')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_sell_page','totalWorker');"</c:if>/>
											</div>
										</li>
									</ul> --%>
								
									<!-- <h2 class="count_flow"><i>2</i>供应商物资销售资质证书</h2> -->
									<div class="ul_list">
										<table class="table table-bordered table-condensed table-hover m_table_fixed_border">
											<thead>
												<tr>
													<th class="info w50">序号</th>
													<th class="info" width="15%">资质证书名称</th>
													<th class="info" width="12%">证书编号</th>
													<th class="info">资质等级</th>
													<th class="info" width="12%">发证机关或机构</th>
													<th class="info">有效期（起始时间）</th>
													<th class="info">有效期（结束时间）</th>
													<th class="info">证书状态</th>
												  <th class="info">证书图片</th>
													<th class="info w50">操作</th>
												</tr>
											</thead>
											<c:forEach items="${supplierCertSell}" var="s" varStatus="vs">
												<tr>
													<td class="tc">${vs.index + 1}</td>
													<td class="tl" id="name_${s.id }" <c:if test="${fn:contains(fieldSell,s.id.concat('_name'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_sell_page','name','${s.id }');"</c:if>>${s.name }</td>
													<td class="tl" id="code_${s.id }" <c:if test="${fn:contains(fieldSell,s.id.concat('_code'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_sell_page','code','${s.id }');"</c:if>>${s.code}</td>
													<td class="tc" id="levelCert_${s.id }" <c:if test="${fn:contains(fieldSell,s.id.concat('_levelCert'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_sell_page','levelCert','${s.id }');"</c:if>>${s.levelCert}</td>
													<td class="tl" id="licenceAuthorith_${s.id }" <c:if test="${fn:contains(fieldSell,s.id.concat('_licenceAuthorith'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_sell_page','licenceAuthorith','${s.id }');"</c:if>>${s.licenceAuthorith }</td>
													<td class="tc" id="expStartDate_${s.id }" <c:if test="${fn:contains(fieldSell,s.id.concat('_expStartDate'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_sell_page','expStartDate','${s.id }');"</c:if>>
														<fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd' />
													</td>
													<td class="tc" id="expEndDate_${s.id }" <c:if test="${fn:contains(fieldSell,s.id.concat('_expEndDate'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_sell_page','expEndDate','${s.id }');"</c:if>>
														<fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd' />
													</td>
													<td class="tc" id="mot_${s.id }" <c:if test="${fn:contains(fieldSell,s.id.concat('_mot'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_sell_page','mot','${s.id }');"</c:if>>${s.mot}</td>
													<td class="tc" <c:if test="${fn:contains(fileModifyField,s.id.concat(supplierDictionaryData.supplierSellCert))}">style="border: 1px solid #FF8C00;"</c:if>>
													  <u:show showId="sale_show_${vs.index+1}" delete="false" businessId="${s.id}" typeId="${supplierDictionaryData.supplierSellCert}" sysKey="${sysKey}" />
													</td>
													<td class="tc w50">
														<%-- <c:if test="${!fn:contains(unableSellField,s.id)}">
															<p onclick="reasonSale('${s.id}','${s.name }');" id="${s.id}_hidden" class="editItem">
																<c:if test="${!fn:contains(auditSellField,s.id)}">
	                                <img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
	                              </c:if>
	                              <c:if test="${fn:contains(auditSellField,s.id)}">
	                                <img src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
	                              </c:if>
															</p>
														</c:if>
														<c:if test="${fn:contains(unableSellField,s.id)}">
                              <img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                            </c:if> --%>
                            <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon.png" />
                            <c:set var="iconCls" value="icon_edit" />
                            <c:if test="${!fn:contains(unableSellField,s.id) && fn:contains(auditSellField,s.id)}">
                            	<c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon_2.png" />
                            </c:if>
                            <c:if test="${fn:contains(unableSellField,s.id)}">
                              <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/sc.png" />
                              <c:set var="iconCls" value="icon_sc" />
                            </c:if>
                            <img src="${iconUrl}" class="${iconCls}"
                            onclick="auditList(this,'mat_sell_page','${s.id}','物资销售-资质证书','${s.name}');" />
													</td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</div>
							</c:if>

							<c:if test="${fn:contains(supplierTypeCode, 'PROJECT')}">
								<div class="tab-pane <c:if test="${liCountEng == 1}">active in</c:if> fade height-200" id="tab-3">
									<%-- <h2 class="count_flow"><i>1</i>组织结构和人员信息</h2>
									<ul class="ul_list count_flow">
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">组织机构：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="orgName_engineering" type="text" value="${supplierMatEngs.orgName }" onclick="auditText(this,'mat_eng_page','orgName_engineering')" <c:if test="${fn:contains(field,'orgName')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_eng_page','orgName');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">技术负责人数量：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalTech_engineering" type="text" value="${supplierMatEngs.totalTech }" onclick="auditText(this,'mat_eng_page','totalTech_engineering')" <c:if test="${fn:contains(field,'totalTech')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_eng_page','totalTech');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">中级及以上职称人员数量：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalGlNormal_engineering" type="text" value="${supplierMatEngs.totalGlNormal }" onclick="auditText(this,'mat_eng_page','totalGlNormal_engineering')" <c:if test="${fn:contains(field,'totalGlNormal')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_eng_page','totalGlNormal');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">现场管理人员数量：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalMange_engineering" type="text" value="${supplierMatEngs.totalMange }" onclick="auditText(this,'mat_eng_page','totalMange_engineering')" <c:if test="${fn:contains(field,'totalMange')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_eng_page','totalMange');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">技术和工人数量：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalTechWorker_engineering" type="text" value="${supplierMatEngs.totalTechWorker }" onclick="auditText(this,'mat_eng_page','totalTechWorker_engineering')" <c:if test="${fn:contains(field,'totalTechWorker')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_eng_page','totalTechWorker');"</c:if>/>
											</div>
										</li>
									</ul> --%>
									
									<h2 class="count_flow"><i>1</i>保密工程业绩</h2>
									<ul class="ul_list">
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5" style="width: 230px;">是否有国家或军队保密工程业绩：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
												<c:if test="${supplierMatEngs.isHavingConAchi eq '0'}">
												  <input id="isHavingConAchi" type="text" value="无" onclick="auditText(this,'mat_eng_page','isHavingConAchi','true')" <c:if test="${fn:contains(fieldSecrecy,'isHavingConAchi') && !fn:contains(auditEngField,'isHavingConAchi')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_eng_page','isHavingConAchi');"</c:if> <c:if test="${fn:contains(auditEngField,'isHavingConAchi')}">style="border: 1px solid red;"</c:if>/>
											  </c:if>
												<c:if test="${supplierMatEngs.isHavingConAchi eq '1'}">
												  <input id="isHavingConAchi" type="text" value="有" onclick="auditText(this,'mat_eng_page','isHavingConAchi')" <c:if test="${fn:contains(fieldSecrecy,'isHavingConAchi') && !fn:contains(auditEngField,'isHavingConAchi')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_eng_page','isHavingConAchi');"</c:if> <c:if test="${fn:contains(auditEngField,'isHavingConAchi')}">style="border: 1px solid red;"</c:if>/>
												</c:if>
												<c:if test="${fn:contains(unableEngField,'isHavingConAchi')}">
													<a class="abolish">
														<img src='${pageContext.request.contextPath}/public/backend/images/sc.png'/>
													</a>
												</c:if>
											</div>
										</li>
										
										<c:if test="${supplierMatEngs.isHavingConAchi eq '1'}">
											<li class="col-md-3 col-sm-6 col-xs-12 pl10">
												<span <c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierConAch) && !fn:contains(auditEngField,'supplierConAch')}">style="border: 1px solid #FF8C00;"</c:if> class="col-md-12 col-sm-12 col-xs-12 padding-left-5 hand" 
												onclick="auditFile(this,'mat_eng_page','supplierConAch');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'"  <c:if test="${fn:contains(auditEngField,'supplierConAch')}">style="border: 1px solid red;"</c:if>>承包合同主要页及保密协议：</span>
												<c:if test="${isStatusToAudit}">
												  <u:upload singleFileSize="300" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierConAch}" id="conAch_up" multiple="true" auto="true" maxcount="5"/>
												</c:if>
												<c:choose>
												  <c:when test="${isStatusToAudit}">
												    <u:show showId="conAch_show"  businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierConAch}"/>
												  </c:when>
												  <c:otherwise>
												    <u:show showId="conAch_show"  delete="false" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierConAch}"/>
												  </c:otherwise>
												</c:choose>
												<c:if test="${fn:contains(unableEngField,'supplierConAch')}">
													<img src='${pageContext.request.contextPath}/public/backend/images/sc.png' class="abolish_img_file"/>
												</c:if>
												<%-- <p><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p> --%>
											</li>
										
											<li class="col-md-12 col-xs-12 col-sm-12 mb25">
												<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">国家或军队保密工程业绩：</span>
												<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
													<textarea class="col-md-12 col-xs-12 col-sm-12 h80 hand" id="confidentialAchievement" onclick="auditText(this,'mat_eng_page','confidentialAchievement')" <c:if test="${fn:contains(fieldSecrecy,'confidentialAchievement') && !fn:contains(auditEngField,'confidentialAchievement')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_eng_page','confidentialAchievement');"</c:if> <c:if test="${fn:contains(auditEngField,'confidentialAchievement')}">style="border: 1px solid red;"</c:if>>${supplierMatEngs.confidentialAchievement}</textarea>
												</div>
											</li>
										</c:if>
									</ul>
									
									<h2 class="count_flow"><i>2</i>承揽业务范围：省级行政区对应合同主要页 （体现甲乙双方盖章及工程名称、地点的相关页）</h2>
									<ul class="ul_list">
										<c:forEach items="${areas}" var="area" varStatus="st">
											<li class="col-md-3 col-sm-6 col-xs-12 pl15 h70">
												<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 hand" 
													onclick="auditFile(this,'mat_eng_page','${area.name}');" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" 
													<c:if test="${fn:contains(auditEngField,area.name)}">style="border: 1px solid red;"</c:if>
													<c:if test="${fn:contains(fileModifyField,area.id) && !fn:contains(auditEngField,area.name)}">style="border: 1px solid #FF8C00;"</c:if>
													>${area.name}：</span>
													<c:if test="${isStatusToAudit}">
													  <u:upload singleFileSize="300" maxcount="5"  id="area_show_${st.index+1}" multiple="true" businessId="${supplierId}_${area.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProContract}" auto="true" />
													</c:if>
													<c:choose>
													  <c:when test="${isStatusToAudit}">
													    <u:show showId="area_show_${st.index+1}"  businessId="${supplierId}_${area.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProContract}" />
													  </c:when>
													  <c:otherwise>
													    <u:show showId="area_show_${st.index+1}"  delete="false" businessId="${supplierId}_${area.id}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierProContract}" />
													  </c:otherwise>
													</c:choose>
													<c:if test="${fn:contains(unableEngField,area.name)}">
														<img src='${pageContext.request.contextPath}/public/backend/images/sc.png' class="abolish_img_file"/>
													</c:if>
													<%-- <p><img style="padding-left: 125px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p> --%>
											</li>
										</c:forEach>
									</ul>
									
									<h2 class="count_flow"><i>3</i>资质证书信息</h2>
									<div class="ul_list">
										<table class="table table-bordered table-condensed table-hover m_table_fixed_border">
											<thead>
												<tr>
													<th class="info w50">序号</th>
													<th class="info" width="15%">资质证书名称</th>
													<th class="info" width="12%">证书编号</th>
													<th class="info">资质等级</th>
													<th class="info" width="12%">发证机关或机构</th>
													<th class="info">有效期（起始时间）</th>
													<th class="info">有效期（结束时间）</th>
													<th class="info">证书状态</th>
													<th class="info">证书图片</th>
													<th class="info w50">操作</th>
												</tr>
											</thead>
											<c:forEach items="${supplierEngQuas}" var="s" varStatus="vs">
												<tr>
													<td class="tc">${vs.index + 1}</td>
													<td class="tl" id="name_${s.id}" <c:if test="${fn:contains(fieldEngQuas,s.id.concat('_name'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','name','${s.id }');"</c:if>>${s.name }</td>
													<td class="tl" id="code_${s.id}" <c:if test="${fn:contains(fieldEngQuas,s.id.concat('_code'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','code','${s.id }');"</c:if>>${s.code}</td>
													<td class="tc" id="levelCert_${s.id}" <c:if test="${fn:contains(fieldEngQuas,s.id.concat('_levelCert'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','levelCert','${s.id }');"</c:if>>${s.levelCert}</td>
													<td class="tl" id="licenceAuthorith_${s.id}" <c:if test="${fn:contains(fieldEngQuas,s.id.concat('_licenceAuthorith'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','licenceAuthorith','${s.id }');"</c:if>>${s.licenceAuthorith }</td>
													<td class="tc" id="expStartDate_${s.id}" <c:if test="${fn:contains(fieldEngQuas,s.id.concat('_expStartDate'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','expStartDate','${s.id }');"</c:if>>
														<fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd' />
													</td>
													<td class="tc" id="expEndDate_${s.id}" <c:if test="${fn:contains(fieldEngQuas,s.id.concat('_expEndDate'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','expEndDate','${s.id }');"</c:if>>
														<fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd' />
													</td>
													<td class="tc" id="mot_${s.id}" <c:if test="${fn:contains(fieldEngQuas,s.id.concat('_mot'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','mot','${s.id }');"</c:if>>${s.mot}</td>
													<td class="tc" <c:if test="${fn:contains(fileModifyField,s.id.concat(supplierDictionaryData.supplierEngQua))}">style="border: 1px solid #FF8C00;"</c:if>>
														<u:show showId="eng_qua_show${vs.index+1}" businessId="${s.id}" delete="false" typeId="${supplierDictionaryData.supplierEngQua}" sysKey="${sysKey}" />
													</td>
													<td class="tc w50">
														<%-- <c:if test="${!fn:contains(unableEngField,s.id)}">
															<p onclick="reasonEngineering('${s.id}','工程-资质证书','${s.name}');" id="${s.id}_hidden3" class="editItem toinline">
																<c:if test="${!fn:contains(auditEngField,s.id)}">
	                                <img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
	                              </c:if>
	                              <c:if test="${fn:contains(auditEngField,s.id)}">
	                                <img src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
	                              </c:if>
															</p>
														</c:if>
														<c:if test="${fn:contains(unableEngField,s.id)}">
                              <img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                            </c:if> --%>
                            <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon.png" />
                            <c:set var="iconCls" value="icon_edit" />
                            <c:if test="${!fn:contains(unableEngField,s.id) && fn:contains(auditEngField,s.id)}">
                            	<c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon_2.png" />
                            </c:if>
                            <c:if test="${fn:contains(unableEngField,s.id)}">
                              <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/sc.png" />
                              <c:set var="iconCls" value="icon_sc" />
                            </c:if>
                            <img src="${iconUrl}" class="${iconCls}"
                            onclick="auditList(this,'mat_eng_page','${s.id}','工程-资质证书','${s.name}');" />
													</td>
												</tr>
											</c:forEach>
										</table>
									</div>
									
									<h2 class="count_flow"><i>4</i>取得注册资质的人员信息</h2>
									<div class="ul_list">
										<table class="table table-bordered table-condensed table-hover m_table_fixed_border">
											<thead>
												<tr>
													<th class="info w50">序号</th>
													<th class="info">注册资格名称</th>
													<th class="info">注册人姓名</th>
													<th class="info w50">操作</th>
												</tr>
											</thead>
											<c:forEach items="${listRegPerson}" var="regPrson" varStatus="vs">
												<tr>
													<td class="tc">${vs.index + 1}</td>
													<td class="tc" id="regType_${regPrson.id }" <c:if test="${fn:contains(fieldRegPersons,regPrson.id.concat('_regType'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','regType','${regPrson.id }');"</c:if>>${regPrson.regType}</td>
													<td class="tc" id="regNumber_${regPrson.id }" <c:if test="${fn:contains(fieldRegPersons,regPrson.id.concat('_regNumber'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','regNumber','${regPrson.id }');"</c:if>>${regPrson.regNumber}</td>
													<td class="tc w50">
														<%-- <c:if test="${!fn:contains(unableEngField,regPrson.id)}">
															<p onclick="reasonEngineering('${regPrson.id}','工程-注册人员登记','${regPrson.regType}');" id="${regPrson.id}_hidden2" class="toinline">
																<c:if test="${!fn:contains(auditEngField,regPrson.id)}">
	                                <img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
	                              </c:if>
	                              <c:if test="${fn:contains(auditEngField,regPrson.id)}">
	                                <img src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
	                              </c:if>
															</p>
														</c:if>
														<c:if test="${fn:contains(unableEngField,regPrson.id)}">
                              <img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                            </c:if> --%>
                            <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon.png" />
                            <c:set var="iconCls" value="icon_edit" />
                            <c:if test="${!fn:contains(unableEngField,regPrson.id) && fn:contains(auditEngField,regPrson.id)}">
                            	<c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon_2.png" />
                            </c:if>
                            <c:if test="${fn:contains(unableEngField,regPrson.id)}">
                              <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/sc.png" />
                              <c:set var="iconCls" value="icon_sc" />
                            </c:if>
                            <img src="${iconUrl}" class="${iconCls}"
                            onclick="auditList(this,'mat_eng_page','${regPrson.id}','工程-注册人员登记','${regPrson.regType}');" />
													</td>
												</tr>
											</c:forEach>
										</table>
									</div>
									
									<h2 class="count_flow"><i>5</i>供应商资质（认证）证书信息</h2>
									<div class="ul_list">
										<table class="table table-bordered table-condensed table-hover m_table_fixed_border">
											<thead>
												<tr>
													<th class="info" width="20%">证书名称</th>
													<th class="info" width="12%">证书编号</th>
													<th class="info">资质等级</th>
													<!-- <th class="info">技术负责人姓名</th>
													<th class="info">技术负责人职称</th>
													<th class="info">技术负责人职务</th>
													<th class="info">单位负责人姓名</th>
													<th class="info">单位负责人职称</th>
													<th class="info">单位负责人职务</th> -->
													<th class="info" width="12%">发证机关或机构</th>
													<th class="info">发证日期</th>
													<th class="info">有效截止日期</th>
													<th class="info">证书状态</th>
													<!-- <th class="info">证书图片</th> -->
													<th class="info w50">操作</th>
												</tr>
											</thead>
											<c:forEach items="${supplierCertEngs}" var="s" varStatus="vs">
												<tr>
													<td class="tl" id="certType_${s.id }" <c:if test="${fn:contains(fieldCertEngs,s.id.concat('_certType'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','certType','${s.id }');"</c:if>>${s.certType }</td>
													<td class="tl" id="certCode_${s.id }" <c:if test="${fn:contains(fieldCertEngs,s.id.concat('_certCode'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','certCode','${s.id }');"</c:if>>${s.certCode }</td>
													<td class="tc" id="certMaxLevel_${s.id }" <c:if test="${fn:contains(fieldCertEngs,s.id.concat('_certMaxLevel'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','certMaxLevel','${s.id }');"</c:if>>${s.certMaxLevel }</td>
													<%-- <td class="tc">${s.techName }</td>
													<td class="tc">${s.techPt }</td>
													<td class="tc">${s.techJop }</td>
													<td class="tc">${s.depName }</td>
													<td class="tc">${s.depPt }</td>
													<td class="tc">${s.depJop }</td> --%>
													<td class="tl" id="licenceAuthorith_${s.id }" <c:if test="${fn:contains(fieldCertEngs,s.id.concat('_licenceAuthorith'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','licenceAuthorith','${s.id }');"</c:if>>${s.licenceAuthorith }</td>
													<td class="tc " id="expStartDate_${s.id }" <c:if test="${fn:contains(fieldCertEngs,s.id.concat('_expStartDate'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','expStartDate','${s.id }');"</c:if>>
														<fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd' />
													</td>
													<td class="tc" id="expEndDate_${s.id }" <c:if test="${fn:contains(fieldCertEngs,s.id.concat('_expEndDate'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','expEndDate','${s.id }');"</c:if>>
														<%-- <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd' /> --%>
														<fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd' />
													</td>
													<td class="tc" id="certStatus_${s.id }" <c:if test="${fn:contains(fieldCertEngs,s.id.concat('_certStatus'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','certStatus','${s.id }');"</c:if>>${s.certStatus}</td>
													<%-- <td class="tc" >
														<u:show showId="eng_show${vs.index+1}" delete="false" businessId="${s.id}" typeId="${supplierDictionaryData.supplierEngCert}" sysKey="${sysKey}" />
													</td> --%>
													<td class="tc w50">
														<%-- <c:if test="${!fn:contains(unableEngField,s.id)}">
															<p onclick="reasonEngineering('${s.id}','工程-资质（认证）证书信息','${s.certCode}');" id="${s.id}_hidden" class="editItem toinline">
																<c:if test="${!fn:contains(auditEngField,s.id)}">
	                                <img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
	                              </c:if>
	                              <c:if test="${fn:contains(auditEngField,s.id)}">
	                                <img src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
	                              </c:if>
															</p>
														</c:if>
														<c:if test="${fn:contains(unableEngField,s.id)}">
                              <img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                            </c:if> --%>
                            <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon.png" />
                            <c:set var="iconCls" value="icon_edit" />
                            <c:if test="${!fn:contains(unableEngField,s.id) && fn:contains(auditEngField,s.id)}">
                            	<c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon_2.png" />
                            </c:if>
                            <c:if test="${fn:contains(unableEngField,s.id)}">
                              <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/sc.png" />
                              <c:set var="iconCls" value="icon_sc" />
                            </c:if>
                            <img src="${iconUrl}" class="${iconCls}"
                            onclick="auditList(this,'mat_eng_page','${s.id}','工程-资质（认证）证书信息','${s.certCode}');" />
													</td>
												</tr>
											</c:forEach>
										</table>
									</div>

									<h2 class="count_flow"><i>6</i>供应商资质证书详细信息</h2>
									<div class="ul_list">
										<table class="table table-bordered table-condensed table-hover m_table_fixed_border">
											<thead>
												<tr>
													<th class="info" width="15%">证书名称</th>
													<th class="info" width="12%">证书编号</th>
													<th class="info">资质类型</th>
													<th class="info">资质序列</th>
													<th class="info" width="12%">专业类别</th>
													<th class="info">资质等级</th>
													<th class="info">是否主项资质</th>
													<!-- <th class="info">批准资质资格内容</th>
													<th class="info">首次批准资质资格文号</th>
													<th class="info">首次批准资质资格日期</th>
													<th class="info">资质资格取得方式</th>
													<th class="info">资质资格状态</th>
													<th class="info">资质资格状态变更时间</th>
													<th class="info">资质资格状态变更原因</th> -->
													<!-- <th class="info">附件</th> -->
						
													<th class="info w100">证书图片</th>
													<th class="info w50">操作</th>
												</tr>
											</thead>
											<c:forEach items="${supplierAptitutes}" var="s" varStatus="vs">
												<tr>
													<td class="tl" id="certName_${s.id }" <c:if test="${fn:contains(fieldAptitutes,s.id.concat('_certName'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','certName','${s.id }');"</c:if>>${s.certName }</td>
													<td class="tl" id="certCode_${s.id }" <c:if test="${fn:contains(fieldAptitutes,s.id.concat('_certCode'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','certCode','${s.id }');"</c:if>>${s.certCode }</td>
													<td class="tl" id="certType_${s.id }" <c:if test="${fn:contains(fieldAptitutes,s.id.concat('_certType'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','certType','${s.id }');"</c:if>>
														<c:forEach items="${typeList}" var="type">
 															<c:if test="${s.certType eq type.id}">${type.name}</c:if>
														</c:forEach>
													</td>
													<td class="tc" id="aptituteSequence_${s.id }" <c:if test="${fn:contains(fieldAptitutes,s.id.concat('_aptituteSequence'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','aptituteSequence','${s.id }');"</c:if>>${s.aptituteSequence }</td>
													<td class="tl" id="professType_${s.id }" <c:if test="${fn:contains(fieldAptitutes,s.id.concat('_professType'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','professType','${s.id }');"</c:if>>${s.professType }</td>
													<td class="tc" id="aptituteLevel_${s.id }" <c:if test="${fn:contains(fieldAptitutes,s.id.concat('_aptituteLevel'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','aptituteLevel','${s.id }');"</c:if>>${s.aptituteLevel }</td>
													<td class="tc" id="isMajorFund_${s.id }" <c:if test="${fn:contains(fieldAptitutes,s.id.concat('_isMajorFund'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_eng_page','isMajorFund','${s.id }');"</c:if>>
														<c:if test="${s.isMajorFund==0 }">否</c:if>
														<c:if test="${s.isMajorFund==1 }">是</c:if>
													</td>
													<%-- <td class="tc">${s.aptituteContent }</td>
													<td class="tc">${s.aptituteCode }</td>
													<td class="tc">
														<fmt:formatDate value="${s.aptituteDate }" pattern='yyyy-MM-dd' />
													</td>
													<td class="tc">${s.aptituteWay }</td>
													<td class="tc">
														<c:if test="${s.aptituteStatus==0 }">无效</c:if>
														<c:if test="${s.aptituteStatus==1 }">有效</c:if>
													</td>
													<td class="tc">
														<fmt:formatDate value="${s.aptituteChangeAt }" pattern='yyyy-MM-dd' />
													</td>
													<td class="tc">${s.aptituteChangeReason }</td> --%>
													<td class="tc" <c:if test="${fn:contains(fileModifyField,s.id.concat(supplierDictionaryData.supplierEngCert))}">style="border: 1px solid #FF8C00;"</c:if>>
														 <u:show showId="apt_show${vs.index+1}" delete="false" businessId="${s.id}" typeId="${supplierDictionaryData.supplierEngCert}" sysKey="${sysKey}" />
													</td>
														<td class="tc w50">
															<%-- <c:if test="${!fn:contains(unableEngField,s.id)}">
																<p onclick="reasonEngineering('${s.id}','工程-资质证书详细信息','${s.certCode}');" id="${s.id}_hidden1" class="editItem toinline">
																	<c:if test="${!fn:contains(auditEngField,s.id)}">
		                                <img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
		                              </c:if>
		                              <c:if test="${fn:contains(auditEngField,s.id)}">
		                                <img src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
		                              </c:if>
																</p>
															</c:if>
															<c:if test="${fn:contains(unableEngField,s.id)}">
	                              <img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
	                            </c:if> --%>
	                            <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon.png" />
	                            <c:set var="iconCls" value="icon_edit" />
	                            <c:if test="${!fn:contains(unableEngField,s.id) && fn:contains(auditEngField,s.id)}">
	                            	<c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon_2.png" />
	                            </c:if>
	                            <c:if test="${fn:contains(unableEngField,s.id)}">
	                              <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/sc.png" />
	                              <c:set var="iconCls" value="icon_sc" />
	                            </c:if>
	                            <img src="${iconUrl}" class="${iconCls}"
	                            onclick="auditList(this,'mat_eng_page','${s.id}','工程-资质证书详细信息','${s.certCode}');" />
														</td>
												</tr>
											</c:forEach>
										</table>
									</div>
								</div>
							</c:if>
							
							<c:if test="${fn:contains(supplierTypeCode, 'SERVICE')}">
								<div class="tab-pane <c:if test="${liCountSer == 1}">active in</c:if> fade height-200" id="tab-4">
									<%-- <h2 class="count_flow"><i>1</i>组织结构和人员信息</h2>
									<ul class="ul_list">
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">组织机构：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="orgName_service" class="span5" type="text" value="${supplierMatSes.orgName }" onclick="reasonService1(this)" <c:if test="${fn:contains(field,'orgName')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_serve_page','orgName');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">人员总数：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalPerson_service" class="span5" type="text" value="${supplierMatSes.totalPerson }" onclick="reasonService1(this)" <c:if test="${fn:contains(field,'totalPerson')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_serve_page','totalPerson');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">管理人员：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalMange_service" class="span5" type="text" value="${supplierMatSes.totalMange }" onclick="reasonService1(this)" <c:if test="${fn:contains(field,'totalMange')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_serve_page','totalMange');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">技术人员：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalTech_service" class="span5" type="text" value="${supplierMatSes.totalTech }" onclick="reasonService1(this)" <c:if test="${fn:contains(field,'totalTech')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_serve_page','totalTech');"</c:if>/>
											</div>
										</li>
										<li class="col-md-3 col-sm-6 col-xs-12 pl15">
											<span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">工人(职员)：</span>
											<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
												<input id="totalWorker_service" class="span5" type="text" value="${supplierMatSes.totalWorker }" onclick="reasonService1(this)" <c:if test="${fn:contains(field,'totalWorker')}">style="border: 1px solid #FF8C00;" onMouseOver="showModify(this,'mat_serve_page','totalWorker');"</c:if>/>
											</div>
										</li>
									</ul> --%>
								
									<div class="ul_list count_flow">
										<table class="table table-bordered table-condensed table-hover m_table_fixed_border">
											<thead>
												<tr>
													<th class="info w50">序号</th>
													<th class="info" width="15%">资质证书名称</th>
													<th class="info" width="12%">证书编号</th>
													<th class="info">资质等级</th>
													<th class="info" width="12%">发证机关或机构</th>
													<th class="info">有效期（起始时间）</th>
													<th class="info">有效期（结束时间）</th>
													<th class="info">证书状态</th>
													<th class="info">证书图片</th>
													<th class="info w50">操作</th>
												</tr>
											</thead>
											<c:forEach items="${supplierCertSes}" var="s" varStatus="vs">
												<tr>
													<td class="tc">${vs.index + 1}</td>
													<td class="tl" id="name_${s.id}" <c:if test="${fn:contains(fieldServe,s.id.concat('_name'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_serve_page','name','${s.id }');"</c:if>>${s.name }</td>
													<td class="tl" id="code_${s.id}" <c:if test="${fn:contains(fieldServe,s.id.concat('_code'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_serve_page','code','${s.id }');"</c:if>>${s.code}</td>
													<td class="tc" id="levelCert_${s.id}" <c:if test="${fn:contains(fieldServe,s.id.concat('_levelCert'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_serve_page','levelCert','${s.id }');"</c:if>>${s.levelCert}</td>
													<td class="tl" id="licenceAuthorith_${s.id}" <c:if test="${fn:contains(fieldServe,s.id.concat('_licenceAuthorith'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_serve_page','licenceAuthorith','${s.id }');"</c:if>>${s.licenceAuthorith }</td>
													<td class="tc" id="expStartDate_${s.id}" <c:if test="${fn:contains(fieldServe,s.id.concat('_expStartDate'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_serve_page','expStartDate','${s.id }');"</c:if>>
														<fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd' />
													</td>
													<td class="tc" id="expEndDate_${s.id}" <c:if test="${fn:contains(fieldServe,s.id.concat('_expEndDate'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_serve_page','expEndDate','${s.id }');"</c:if>>
														<fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd' />
													</td>
													<td class="tc" id="mot_${s.id}" <c:if test="${fn:contains(fieldServe,s.id.concat('_mot'))}">style="border: 1px solid #FF8C00;" onMouseOver="showModifyList(this,'mat_serve_page','mot','${s.id }');"</c:if>>${s.mot}</td>
													<td class="tc" <c:if test="${fn:contains(fileModifyField,s.id.concat(supplierDictionaryData.supplierServeCert))}">style="border: 1px solid #FF8C00;"</c:if>>
														<u:show showId="ser_show${vs.index+1}" businessId="${s.id}" delete="false" typeId="${supplierDictionaryData.supplierServeCert}" sysKey="${sysKey}" />
													</td>
													<td class="tc w50">
														<%-- <c:if test="${!fn:contains(unableServeField,s.id)}">
															<p onclick="reasonService('${s.id}','服务-资质证书','${s.name}');" id="${s.id}_hidden" class="editItem">
																<c:if test="${!fn:contains(auditServeField,s.id)}">
	                                <img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
	                              </c:if>
	                              <c:if test="${fn:contains(auditServeField,s.id)}">
	                                <img src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
	                              </c:if>
															</p>
														</c:if>
														<c:if test="${fn:contains(unableServeField,s.id)}">
                              <img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                            </c:if> --%>
                            <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon.png" />
                            <c:set var="iconCls" value="icon_edit" />
                            <c:if test="${!fn:contains(unableServeField,s.id) && fn:contains(auditServeField,s.id)}">
                            	<c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/light_icon_2.png" />
                            </c:if>
                            <c:if test="${fn:contains(unableServeField,s.id)}">
                              <c:set var="iconUrl" value="${pageContext.request.contextPath}/public/backend/images/sc.png" />
                              <c:set var="iconCls" value="icon_sc" />
                            </c:if>
                            <img src="${iconUrl}" class="${iconCls}"
                            onclick="auditList(this,'mat_serve_page','${s.id}','服务-资质证书','${s.name}');" />
													</td>
												</tr>
											</c:forEach>
										</table>
									</div>					
								</div>
							</c:if>

							<div class="col-md-12 col-sm-12 col-xs-12 add_regist tc mt20">
								<a class="btn" type="button" onclick="toStep('three');">上一步</a>
								<c:if test="${isStatusToAudit}">
			            <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="tempAudit();">暂存</a>
			          </c:if>
								<a class="btn" type="button" onclick="toStep('five');">下一步</a>
							</div>
						</div>
					</div>
				</div>
				<form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
					<input type="hidden" name="fileName" />
				</form>
				<%-- <form id="form_id" action="" method="post">
					<input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
					<input id="status" name="supplierStatus" value="${supplierStatus}" type="hidden">
					<input type="hidden" name="sign" value="${sign}">
				</form> --%>
	</body>
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/js/regex.js"></script>

</html>
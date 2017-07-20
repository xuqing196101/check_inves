<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <%@ include file="/WEB-INF/view/common/webupload.jsp" %>
    <title>审核汇总</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/merge_aptitude.js"></script>
    <script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/audit_attach_upload.js"></script>
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
            <li>
                <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=1')">供应商审核</a>
            </li>
        </ul>
    </div>
</div>
<div class="container container_box">
    <div class="content">
        <div class="col-md-12 tab-v2 job-content">
            <%@include file="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp" %>
            <!-- 审核公示扫描件上传 -->
            <div>
                <h2 class="count_flow"><i>1</i>供应商审批表</h2>
                <ul class="ul_list">
                    <c:if test="${ supplierStatus == -3 || supplierStatus == 3 }">
                        <li class="col-md-6 col-sm-6 col-xs-6">
                            <div>
                                <span class="fl">供应商审批表：</span>
                                <u:show showId="pic_checkword" businessId="${ supplier.auditOpinionAttach }"
                                        sysKey="${ sysKey }" typeId="${typeId }" delete="false"/>
                            </div>
                        </li>
                    </c:if>
                    <c:if test="${ supplierStatus != -3 && supplierStatus != 3 }">
                        <li class="col-md-6 col-sm-6 col-xs-6">
                            <div>
                                <span class="fl">上传批准审核表：</span>
                                <% String uuidcheckword = UUID.randomUUID().toString().toUpperCase().replace("-", ""); %>
                                <input id="auditOpinionFile" type="hidden" value="<%=uuidcheckword%>"/>
                                <u:upload id="pic_checkword" businessId="<%=uuidcheckword %>" sysKey="${ sysKey }" typeId="${ typeId }" buttonName="上传彩色扫描件" auto="true"
                                          exts="png,jpeg,jpg,bmp,git"/>
                                <u:show showId="pic_checkword" businessId="<%=uuidcheckword %>" sysKey="${ sysKey }" typeId="${typeId }"/>
                            </div>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>

        <div class="col-md-12 col-sm-12 col-xs-12 add_regist tc mt20">
            <input name="downloadAttachFile" id="downloadAttachFile" value="" type="hidden">
            <form id="form_id" action="${pageContext.request.contextPath}/supplierAudit/uploadApproveFile.html" method="post">
                <input name="supplierId" value="${supplierId}" type="hidden">
                <input name="supplierStatus" value="${supplierStatus}" type="hidden">
                <input type="hidden" name="sign" value="${sign}">
            </form>
            <form id="form_shen" action="${pageContext.request.contextPath}/supplierAudit/updateStatus.html">
                <input name="supplierId" id="supplierId" value="${supplierId}" type="hidden">
                <input name="status" id="status" type="hidden" value="${supplierStatus}">
                <input name="opinion" type="hidden">
                <input name="id" type="hidden">
                <input name="auditOpinionAttach" id="auditOpinionAttach" type="hidden"/>
                <div class="margin-bottom-0  categories">
                    <div class="col-md-12 add_regist tc">
                        <div class="col-md-12 add_regist tc">
                            <a class="btn" type="button" onclick="lastStep();">上一步</a>
                            <c:if test="${supplierStatus == -2}">
                                <%--<input class="btn btn-windows apply" type="button" id="auditPass" value="审核通过 " />
                                <input class="btn btn-windows cancel" type="button" id="auditNoPass" value="审核不通过" />--%>
                                <input class="btn btn-windows git" type="button" id="auditOver" value="审核结束 " />
                            </c:if>
                        </div>
                    </div>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>

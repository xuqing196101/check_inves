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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertAudit/merge_jump.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertAudit/audit_attach_upload.js"></script>
<script type="text/javascript">
$(function(){
	$("#reverse_of_five_i").css("display","inline-block");
})
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
                <a href="javascript:void(0)">支撑系统</a>
            </li>
            <li>
                <a href="javascript:void(0)">专家管理</a>
            </li>
            <li>
                <a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=1')">专家初审</a>
            </li>
        </ul>
        <div class="clear"></div>
    </div>
</div>
<div class="container container_box">
    <div class="content">
        <div class="col-md-12 tab-v2 job-content">
            <%@include file="/WEB-INF/view/ses/ems/expertAudit/common_jump.jsp" %>
            <!-- 审核公示扫描件上传 -->
            <div>
                <h2 class="count_flow"><i>1</i>专家初审表</h2>
                <ul class="ul_list">
                    <%-- <c:if test="${ status == -3 || status == 5}">
                        <li class="col-md-6 col-sm-6 col-xs-6">
                            <div>
                                <span class="fl">专家审批表：</span>
                                <u:show showId="pic_checkword" businessId="${expertId}1"
                                        sysKey="${ sysKey }" typeId="${typeId }" delete="false"/>
                            </div>
                        </li>
                    </c:if>
                    <c:if test="${ status != -3 && status != 5">
                        <li class="col-md-6 col-sm-6 col-xs-6">
                            <div>
                                <span class="fl">上传批准审核表：</span>
                                <% String uuidcheckword = UUID.randomUUID().toString().toUpperCase().replace("-", ""); %>
                                <input name="check_word_pic" id="auditOpinionFile" type="hidden" value="${expertId}1" />
                                <u:upload id="pic_checkword" businessId="${expertId}1" sysKey="${ sysKey }" typeId="${ typeId }" buttonName="上传彩色扫描件" auto="true" exts="png,jpeg,jpg,bmp,git" />
                                <u:show showId="pic_checkword" businessId="${expertId}1" sysKey="${ sysKey }" typeId="${typeId }" />
                            </div>
                        </li>
                    </c:if> --%>
                    <c:if test="${ status == 15 || status == 16}">
                        <li class="col-md-6 col-sm-6 col-xs-6">
                            <div>
                                <span class="fl">上传批准初审表：</span>
                                <u:upload id="pic_checkword" businessId="${expertId}2" sysKey="${ sysKey }" typeId="${ typeId }" buttonName="上传彩色扫描件" auto="true" multiple="true"/>
                                <u:show showId="pic_checkword" businessId="${expertId}2" sysKey="${ sysKey }" typeId="${typeId }" />
                            </div>
                        </li>
                    </c:if>
                    <c:if test="${ status != 15 && status != 16}">
                        <li class="col-md-6 col-sm-6 col-xs-6">
                            <div>
                                <span class="fl">上传批准初审表：</span>
                                <u:upload id="pic_checkword" businessId="${expertId}2" sysKey="${ sysKey }" typeId="${ typeId }" buttonName="上传彩色扫描件" auto="true" multiple="true"/>
                                <u:show showId="pic_checkword" businessId="${expertId}2" sysKey="${ sysKey }" typeId="${typeId }" delete = "false"/>
                            </div>
                        </li>
                    </c:if>
                </ul>
            </div>
        </div>

        <div class="col-md-12 col-sm-12 col-xs-12 add_regist tc mt20">
            <input name="downloadAttachFile" id="downloadAttachFile" value="" type="hidden">
            <form id="form_id" action="${pageContext.request.contextPath}/supplierAudit/uploadApproveFile.html" method="post">
                <input name="expertId" value="${expertId}" type="hidden">
                <input name="sign" value="${sign}" type="hidden">
                <input name="status" id="expertStatus" value="${status}" type="hidden">
                <input name="isReviewRevision" value="${isReviewRevision}" type="hidden">
                <input name="isCheck" value="${isCheck}" type="hidden">
            </form>
            <form id="form_shenhe" action="${pageContext.request.contextPath}/expertAudit/updateStatus.html">
                <input name="id" value="${expertId}" type="hidden">
                <input name="status" type="hidden" id="status" value="${status}"/>
                <input name="auditOpinionAttach" id="auditOpinionAttach" type="hidden" />
                <div class="margin-bottom-0  categories">
                    <div class="col-md-12 add_regist tc">
                        <div class="col-md-12 add_regist tc">
                            <a class="btn" type="button" onclick="lastStep();">上一步</a>
                            <c:if test="${status == -2}">
                                <%--<input class="btn btn-windows apply" type="button" id="auditPass" value="复审合格 " />
                                <input class="btn btn-windows cancel" type="button" id="auditNoPass" value="复审不合格" />--%>
                                <input class="btn btn-windows end" type="button" id="auditOver" value="复审结束" />
                            </c:if>
                            <c:if test="${status == 15 || status == 16}">
                                <input class="btn btn-windows end" type="button" value = "初审结束" onclick = "chuAuditEnd()"/>
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

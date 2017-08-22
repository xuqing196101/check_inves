<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../../../../common.jsp"%>
    <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <%@ include file="/WEB-INF/view/ses/sms/supplier_query/supplierInfo/common.jsp"%>
    <script type="text/javascript" src="${ pageContext.request.contextPath }/js/ses/ems/expertQuery/common.js"></script>
    <title>供应商查看</title>
</head>
<body>
<div class="wrapper">
    <div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li>
                    <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
                </li>
                <c:choose>
                    <c:when test="${person == 1 }">
                        <li>
                            <a href="javascript:void(0);">个人中心</a>
                        </li>
                        <li>
                            <a href="javascript:void(0);">个人信息</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li>
                            <a href="javascript:void(0);">支撑环境</a>
                        </li>
                        <li>
                            <a href="javascript:void(0);">供应商管理</a>
                        </li>
                        <li>
                            <a href="javascript:void(0);"
                               onclick="jumppage('${pageContext.request.contextPath}/supplierQuery/findSupplierByPriovince.html?sign=1')">供应商列表</a>
                        </li>
                        <li>
                            <a href="javascript:void(0);">供应商查看</a>
                        </li>
                    </c:otherwise>
                </c:choose>

            </ul>
        </div>
    </div>
    <div class="container container_box">
        <div class=" content height-350">
            <div class="col-md-12 tab-v2 job-content">
                <ul class="nav nav-tabs bgwhite">
                    <li class="">
                        <a aria-expanded="false" href="#tab-1" data-toggle="tab" class="f18"
                           onclick="tijiao('essential');">基本信息</a>
                    </li>
                    <li class="">
                        <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18"
                           onclick="tijiao('financial');">财务信息</a>
                    </li>
                    <li class="">
                        <a aria-expanded="false" href="#tab-3" data-toggle="tab" class="f18"
                           onclick="tijiao('shareholder');">股东信息</a>
                    </li>
                    <li class="">
                        <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18"
                           onclick="tijiao('supplierType');">供应商类型</a>
                    </li>
                    <li class="">
                        <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('item');">产品类别</a>
                    </li>
                    <li class="">
                        <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('zizhi');">资质文件</a>
                    </li>
                    <li class="">
                        <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18"
                           onclick="tijiao('contract');">销售合同</a>
                    </li>
                    <li class="active">
                        <a aria-expanded="true" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('table');">承诺书/申请表</a>
                    </li>
                    <li class="">
                        <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tijiao('audit');">审核信息</a>
                    </li>
                </ul>

                <!--基本信息-->
                <div class="padding-top-10">
                    <div class="ul_list">
                        <div class="headline-v2">
                            <h2>查看供应商申请表、承诺书 </h2>
                        </div>
                        <table class="table table-bordered">
                            <tbody>
                            <tr>
                                <td class="bggrey" width="15%">供应商承诺书：</td>
                                <td>
                                    <u:show showId="application_show" delete="false"
                                            groups="promise_show,application_show" businessId="${currSupplier.id}"
                                            sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierPledge}"/>
                                </td>
                                <td class="bggrey" width="15%">供应商申请表：</td>
                                <td>
                                    <u:show showId="promise_show" groups="promise_show,application_show" delete="false"
                                            businessId="${currSupplier.id}" sysKey="${sysKey}"
                                            typeId="${supplierDictionaryData.supplierRegList}"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <form id="form_back" action="" method="post">
            <input name="judge" value="${judge}" type="hidden">
            <c:if test="${sign!=1 and sign!=2 }">
                <input name="address" id="address" value="${suppliers.address}" type="hidden">
            </c:if>
            <input name="sign" value="${sign}" type="hidden">
        </form>
        <form id="form_id" action="" method="post">
            <input name="supplierId" id="id" value="${supplierId }" type="hidden">
            <input name="judge" value="${judge}" type="hidden">
            <input name="sign" value="${sign}" type="hidden">
            <input name="person" value="${person}" type="hidden">
        </form>
        <div class="col-md-12 tc">
            <c:choose>
                <c:when test="${person == 1 }">
                    <button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
                </c:when>
                <c:otherwise>
                    <button class="btn btn-windows back" onclick="fanhui()">返回</button>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>
</body>
</html>

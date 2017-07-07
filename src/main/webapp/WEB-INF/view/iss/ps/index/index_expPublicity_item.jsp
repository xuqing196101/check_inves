<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
    <head>
        <jsp:include page="/index_head.jsp"></jsp:include>
    <script type="text/javascript">
        $(function(){
            var expertId = $("#expertId").val();
            var mat = $("#mat").val();
            var eng = $("#eng").val();
            var ser = $("#ser").val();
            var goodsProject = $("#goodsProject").val();
            var goodsEngInfo = $("#goodsEngInfo").val();
            var engInfo = $("#engInfo").val();

            var matCodeId = $("#matCodeId").val();
            var engCodeId = $("#engCodeId").val();
            var serCodeId = $("#serCodeId").val();
            var goodsProjectId = $("#goodsProjectId").val();
            var goodsEngInfoId = $("#goodsEngInfoId").val();
            if(mat == "mat_page"){
                // 物资品目信息
                loading = layer.load(1);
                var path = "${pageContext.request.contextPath}/expertAudit/getCategories.html?flags=publicity&expertId=" + expertId + "&typeId=" + matCodeId;
                $("#tbody_category").load(path);
            }else if(eng == "eng_page"){
                // 工程品目信息
                loading = layer.load(1);
                var path = "${pageContext.request.contextPath}/expertAudit/getCategories.html?flags=publicity&expertId=" + expertId + "&typeId=" + engCodeId;
                $("#tbody_category").load(path);
            }else if(ser == "ser_page"){
                // 服务
                loading = layer.load(1);
                var path = "${pageContext.request.contextPath}/expertAudit/getCategories.html?flags=publicity&expertId=" + expertId + "&typeId=" + serCodeId;
                $("#tbody_category").load(path);
            }else if(goodsProject == "goodsProject_page"){
                // 工程产品类别信息
                loading = layer.load(1);
                var path = "${pageContext.request.contextPath}/expertAudit/getCategories.html?flags=publicity&expertId=" + expertId + "&typeId=" + goodsProjectId;
                $("#tbody_category").load(path);
            }else if(goodsEngInfo == "goodsEngInfo_page"){
                // 工程专业属性信息
                loading = layer.load(1);
                var path = "${pageContext.request.contextPath}/expertAudit/getCategories.html?flags=publicity&expertId=" + expertId + "&typeId=" + goodsEngInfoId;
                $("#tbody_category").load(path);
            }
        });

        function showDivTree(code) {
            // 加载已选品目列表
            loading = layer.load(1);
            var expertId = $("#expertId").val();
            var path = "${pageContext.request.contextPath}/expertAudit/getCategories.html?flags=publicity&expertId=" + expertId + "&typeId=" + code;
            $("#tbody_category").load(path);
        };
    </script>
</head>

<body >
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li><a href="${pageContext.request.contextPath}/"> 首页</a></li><li><a href="javascript:void(0);">类别</a></li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>
    <input type="hidden" name="id" id="id" value="${expertId}" />
    <input value="物资" type="hidden" name="tab-1">
    <input value="工程" type="hidden" name="tab-2">
    <input value="服务" type="hidden" name="tab-3">
    <div class="container job-content ">
        <div class="report_list_box">

            <div class="col-md-12 tab-v2 job-content">
                <div class="padding-top-10">
                    <ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab hand">
                        <c:set value="0" var="liCount" />
                        <c:forEach items="${allCategoryList}" var="cate" varStatus="vs">
                            <c:if test="${cate.code eq 'GOODS'}">
                                <c:set value="${liCount+1}" var="liCount" />
                                <li id="li_id_${vs.index + 1}" class="active" onclick="showDivTree('${matCodeId }');">
                                    <a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">物资品目信息</a>
                                    <input type="hidden" id="mat" value="mat_page">
                                    <input id="matCodeId" type="hidden" value="${matCodeId }">
                                </li>
                            </c:if>
                            <c:if test="${cate.code eq 'PROJECT'}">
                                <li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree('${engCodeId }');">
                                    <a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">工程品目信息</a>
                                    <input type="hidden" id="eng" value="eng_page">
                                    <input id="engCodeId" type="hidden" value="${engCodeId }">
                                </li>
                                <c:set value="${liCount+1}" var="liCount" />
                            </c:if>
                            <c:if test="${cate.code eq 'PROJECT'}">
                                <li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree('${engInfoId }');">
                                    <a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">工程专业信息</a>
                                    <input type="hidden" id="engInfo" value="engInfo_page">
                                    <input id="engInfoId" type="hidden" value="${engInfoId }">
                                </li>
                                <c:set value="${liCount+1}" var="liCount" />
                            </c:if>
                            <c:if test="${cate.code eq 'SERVICE'}">
                                <li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree('${serCodeId }');">
                                    <a id="li_${vs.index + 1}" aria-expanded="false" data-toggle="tab" class="f18">服务品目信息</a>
                                    <input type="hidden" id="ser" value="ser_page">
                                    <input id="serCodeId" type="hidden" value="${serCodeId }">
                                </li>
                                <c:set value="${liCount+1}" var="liCount" />
                            </c:if>

                            <!-- 经济 -->
                            <c:if test="${cate.code eq 'GOODS_PROJECT'}">
                                <li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree('${goodsProjectId }');">
                                    <a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">工程产品类别信息</a>
                                    <input type="hidden" id="goodsProject" value="goodsProject_page">
                                    <input id=goodsProjectId type="hidden" value="${goodsProjectId }">
                                </li>
                                <c:set value="${liCount+1}" var="liCount" />
                            </c:if>
                            <c:if test="${cate.code eq 'GOODS_PROJECT'}">
                                <li id="li_id_${vs.index + 1}" class='<c:if test="${liCount == 0}">active</c:if>' onclick="showDivTree('${engInfoId }');">
                                    <a id="li_${vs.index + 1}" aria-expanded="true" data-toggle="tab" class="f18">工程专业属性信息</a>
                                    <input type="hidden" id="goodsEngInfo" value="goodsEngInfo_page">
                                    <input id="goodsEngInfoId" type="hidden" value="${engInfoId }">
                                </li>
                                <c:set value="${liCount+1}" var="liCount" />
                            </c:if>

                        </c:forEach>
                    </ul>
                    <div class="mt20" id="tbody_category"></div>
                    <div id="pagediv" align="right" class="mb50"></div>
                </div>
            </div>
        </div>
    </div>
    <input value="${expertId}" id="expertId" type="hidden">
    <form id="form_id" action="" method="post">
       <input name="expertId" value="${expertId}" type="hidden">
       <input name="sign" value="${sign}" type="hidden">
    </form>
    <!--底部代码开始-->
    <jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>
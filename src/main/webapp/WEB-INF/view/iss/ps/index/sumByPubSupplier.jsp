<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
    <head>
        <jsp:include page="/index_head.jsp"></jsp:include>
        <script type="text/javascript">
            var id = "${id}";
            var twoid = "${twoid}";
            var title = "${title}";
            $(function () {
                laypage({
                    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
                    pages: "${list.pages}", //总页数
                    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
                    skip: true, //是否开启跳页
                    total: "${list.total}",
                    startRow: "${list.startRow}",
                    endRow: "${list.endRow}",
                    groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
                    curr: function () { //通过url获取当前页，也可以同上（pages）方式获取
                        var page = location.search.match(/page=(\d+)/);
                        return page ? page[1] : 1;
                    }(),
                    jump: function (e, first) { //触发分页后的回调
                        if (!first) { //一定要加此判断，否则初始时会无限刷新
                            var supplierName = $("#supplierName").val();
                            var status = $("#status").val();
                            window.location.href = "${pageContext.request.contextPath}/index/selectsumByDirectory.html?act=0&page=" + e.curr + "&supplierName=" + supplierName + "&status=" + status;
                        }
                    }
                });

                // 页面加载完毕设置选中状态
                $("#status option[value='${status}']").prop("selected", true);
            });

            function query() {
                var supplierName = $("#supplierName").val();
                var status = $("#status").val();
                window.location.href = "${pageContext.request.contextPath}/index/selectsumByDirectory.html?act=0&supplierName=" + supplierName + "&status=" + status;
            }

            function reset() {
                $("#status").val("");
                $("#supplierName").val("");
                $("#code").val("");
            }
        </script>
    </head>

<body>
<!--面包屑导航开始-->
<div class="margin-top-10 breadcrumbs">
    <div class="container">
        <ul class="breadcrumb mb0">
            <li><a href="${pageContext.request.contextPath}/"> 首页</a></li>
            <li><a href="javascript:void(0);">入库名单</a></li>
        </ul>
        <div class="clear"></div>
    </div>
</div>
<div class="container job-content ">
    <div class="search_box col-md-12 col-sm-12 col-xs-12 form-inline">
        <div class="form-group">
            <label>状态：</label>
            <select name="status" id="status" class="form-control">
                <option value="">全部</option>
                <option value="1">审核通过</option>
                <option value="4">待复核</option>
                <option value="5">复核通过</option>
                <option value="6">复核未通过</option>
                <!-- <option value="5">待考察</option> -->
                <option value="7">考察合格</option>
                <option value="8">考察不合格</option>
                <%--<c:choose>
                   <c:when test="${!empty status && fn:contains('1,4,6',status)}">
                       <option value="1,4,6" selected="selected">审核通过</option>
                   </c:when>
                    <c:otherwise>
                   <option value="1,4,6">审核通过</option>
               </c:otherwise>
                   </c:choose>
                  <c:choose>
                   <c:when test="${!empty status && fn:contains('5,7',status)}">
                       <option value="5,7" selected="selected">复核通过</option>
                   </c:when>
                    <c:otherwise>
                   <option value="5,7">复核通过</option>
               </c:otherwise>
                   </c:choose>
                  <c:choose>
                   <c:when test="${'8' eq status}">
                       <option value="8" selected="selected" >考察合格</option>
                   </c:when>
                    <c:otherwise>
                   <option value="8" >考察合格</option>
               </c:otherwise>
                   </c:choose>--%>
            </select>
        </div>
        <div class="form-group">
            <label>供应商名称：</label>
            <input name="supplierName" type="text" id="supplierName" value="${supplierName }" class="form-control"/>
        </div>
        <div class="form-group">
            <label> 编号：</label>
            <input name="code" type="text" id="code" value="${code }" class="form-control"/>
        </div>
        <button type="button" onclick="query()" class="btn btn-u-light-grey">查询</button>
        <button type="button" onclick="reset()" class="btn btn-u-light-grey">重置</button>
    </div>
    <div class="report_list_box">
        <div class="col-md-12 col-sm-12 col-xs-12 report_list_title">
            <div class="col-md-6 col-xs-6 col-sm-5 tc f16">供应商名称</div>
            <div class="col-md-3 col-xs-3 col-sm-4 tc f16">编号</div>
            <div class="col-md-3 col-xs-3 col-sm-3 tc f16">状态</div>
        </div>
        <c:choose>
            <c:when test="${!empty list.list}">
                <ul class="categories li_square col-md-12 col-sm-12 col-xs-12 p0 list_new">
                    <c:forEach items="${list.list}" var="item" varStatus="status">
                        <li>
                            <div class="col-md-6 col-xs-6 col-sm-5">
                                <span class="f18 mr5 fl">·</span>${item.supplierName }
                            </div>
                            <div class="col-md-3 col-xs-3 col-sm-4 tc">
                            </div>
                            <span class=" col-md-3 col-sm-5 col-xs-3 tc">
                                <%--<c:choose>
                                    <c:when test="${item.status == 1 or item.status == 4 or item.status == 6}">
                                        审核通过
                                    </c:when>
                                    <c:when test="${item.status == 5 or item.status == 7}">
                                        复核通过
                                    </c:when>
                                    <c:when test="${item.status == 8}">
                                        考察合格
                                    </c:when>
                                    <c:otherwise>
                                        无状态
                                    </c:otherwise>
                                </c:choose>--%>
                                <c:if test="${item.status==1 }">审核通过</c:if>
                                <c:if test="${item.status==4 }">待复核</c:if>
                                <c:if test="${item.status==5 }">复核通过</c:if>
                                <c:if test="${item.status==6 }">复核未通过</c:if>
                                <c:if test="${item.status==7 }">考察合格</c:if>
                                <c:if test="${item.status==8 }">考察不合格</c:if>
                            </span>
                        </li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                <li class="tc">暂无数据</li>
            </c:otherwise>
        </c:choose>
        <div id="pagediv" align="right"></div>
    </div>
</div>
<!--底部代码开始-->
<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>

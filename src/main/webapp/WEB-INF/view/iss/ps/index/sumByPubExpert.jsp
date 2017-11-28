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
                            var relName = $("#relName").val();
                            var status = $("#status").val();
                            window.location.href = "${pageContext.request.contextPath}/index/selectsumByDirectory.html?page=" + e.curr + "&atc=1&relName=" + relName + "&status=" + status;
                        }
                    }
                });

                $("#status").val('${status}');
            });

            function query() {
                var relName = $("#relName").val();
                var status = $("#status").val();
                var batchDetailsNumber = $("#batchDetailsNumber").val();
                //title = decodeURI(title);
                //alert(title);
                window.location.href = "${pageContext.request.contextPath}/index/selectsumByDirectory.html?act=1&relName=" + relName + "&status=" + status + "&batchDetailsNumber=" + batchDetailsNumber;
            }

            //重置
            function reset() {
                $("#status").val("");
                $("#relName").val("");
                $("#batchDetailsNumber").val("");
            }
        </script>
    </head>

<body>
<!--面包屑导航开始-->
<div class="margin-top-10 breadcrumbs">
    <div class="container">
        <ul class="breadcrumb margin-left-0">
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
                <option value="6">复审通过(待复查)</option>
                <option value="7">复查合格</option>
                <option value="8">复查不合格</option>
            </select>
        </div>
        <div class="form-group">
            <label> 专家名称：</label>
            <input name="relName" type="text" id="relName" value="${ relName }" class="form-control"/>
        </div>
        <div class="form-group">
            <label>编号：</label>
            <input type="text" id="batchDetailsNumber" name="batchDetailsNumber" value="${batchDetailsNumber}" class="form-control"/>
        </div>
        <button type="button" onclick="query()" class="btn btn-u-light-grey">查询</button>
        <button type="button" onclick="reset()" class="btn btn-u-light-grey">重置</button>
    </div>
    <div class="report_list_box">
        <div class="col-md-12 col-sm-12 col-xs-12 report_list_title">
            <div class="col-xs-3 tc f16">专家姓名</div>
            <div class="col-xs-6 tc f16">编号</div>
            <div class="col-xs-3 tc f16">状态</div>
        </div>
        <c:choose>
            <c:when test="${!empty list.list}">
                <ul class="categories li_square col-md-12 col-sm-12 col-xs-12 p0 list_new">
                    <c:forEach items="${list.list}" var="item" varStatus="status">
                        <li>
                            <div class="col-xs-3">
                                <span class="f18 mr5 fl">·</span>${item.relName }
                            </div>
                            <div class="col-xs-6 tc">${item.batchDetailsNumber}</div>
                            <span class="col-xs-3 tc">
					   <c:choose>
                           <c:when test="${item.status eq '4' or item.status == '6' or item.status == '8'}">
                               复审通过
                           </c:when>
                           <c:when test="${item.status eq '7'}">
                               复查通过
                           </c:when>
                           <c:otherwise>
                           </c:otherwise>
                       </c:choose>
					   </span>
                        </li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:otherwise>
                暂无数据
            </c:otherwise>
        </c:choose>
        <div id="pagediv" align="right"></div>
    </div>
</div>
<!--底部代码开始-->
<jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>

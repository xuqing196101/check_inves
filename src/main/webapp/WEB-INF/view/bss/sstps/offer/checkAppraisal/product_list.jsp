<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../../../../common.jsp" %>
    <title>产品审价</title>

    <script type="text/javascript">

        $(function () {
            $("#name").val('${name }');
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
                        var id = "${id}";
                        location.href = '${pageContext.request.contextPath}/offer/selectProductCheck.html?id=+"id"&page=' + e.curr;
                    }
                }
            });
        });

        /** 全选全不选 */
        function selectAll() {
            var checklist = document.getElementsByName("chkItem");
            var checkAll = document.getElementById("checkAll");
            if (checkAll.checked) {
                for (var i = 0; i < checklist.length; i++) {
                    checklist[i].checked = true;
                }
            } else {
                for (var j = 0; j < checklist.length; j++) {
                    checklist[j].checked = false;
                }
            }
        }

        /** 单选 */
        function check() {
            var count = 0;
            var checklist = document.getElementsByName("chkItem");
            var checkAll = document.getElementById("checkAll");
            for (var i = 0; i < checklist.length; i++) {
                if (checklist[i].checked == false) {
                    checkAll.checked = false;
                    break;
                }
                for (var j = 0; j < checklist.length; j++) {
                    if (checklist[j].checked == true) {
                        checkAll.checked = true;
                        count++;
                    }
                }
            }
        }

        function offer() {
            var id = [];
            $('input[name="chkItem"]:checked').each(function () {
                id.push($(this).val());
            });

            if (id.length == 1) {
                window.location.href = "${pageContext.request.contextPath}/offer/userSelectProductInfoCheck.do?productId=" + id;
            } else if (id.length > 1) {
                layer.alert("只能选择一个", {offset: ['222px', '390px'], shade: 0.01});
            } else {
                layer.alert("请选择需要复审的产品", {offset: ['222px', '390px'], shade: 0.01});
            }
        }

    </script>
    `
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
                <a href="javascript:void(0);"> 保障作业</a>
            </li>
            <li>
                <a href="javascript:void(0);"> 单一来源审价</a>
            </li>
            <li>
                <a href="javascript:jumppage('${pageContext.request.contextPath}/offer/checkList.html')">审价人员复审</a>
            </li>
            <li><a href="javascript:void(0)">产品复审</a></li>
        </ul>
        <div class="clear"></div>
    </div>
</div>

<div class="container">
    <div class="headline-v2">
        <h2>查询条件</h2>
    </div>
    <!-- 查询 -->
    <div class="search_detail">
        <form id="form1" action="${pageContext.request.contextPath}/offer/selectProductCheck.html?contractId=${id}"
              method="post" class="mb0">
            <ul class="demand_list">
                <li><label class="fl">产品名称：</label>
                    <span>
							<input type="text" name="name" id="name" class="mb0"/>
						</span>
                </li>
                <button class="btn" type="submit">查询</button>
                <button type="reset" class="btn">重置</button>
            </ul>

            <div class="clear"></div>
        </form>
    </div>
    <!-- 表格开始-->
    <div class="col-md-12 pl20 mt10">
        <button class="btn" type="button" onclick="offer()">产品复审</button>
    </div>
    <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
            <thead>
            <tr>
                <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()"/></th>
                <th class="info w50">序号</th>
                <th class="info" width="25%">产品名称</th>
                <th class="info" width="20%">品牌商标</th>
                <th class="info" width="20%">规格型号</th>
                <th class="info" width="8%">采购数量</th>
                <th class="info" width="8%">计量单位</th>
                <th class="info">审核状态</th>
            </tr>
            </thead>
            <c:forEach items="${list.list}" var="product" varStatus="vs">
                <c:if test="${product.offer==1 }">
                    <tr>
                        <td class="tc w30" id="tds"><input onclick="check()" type="checkbox" name="chkItem"
                                                           value="${product.id }"/></td>
                        <td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
                        <td class="tc">${product.name }</td>
                        <td class="tl">${product.contractRequired.brand }</td>
                        <td class="tl">${product.contractRequired.stand }</td>
                        <td class="tc">${product.contractRequired.purchaseCount }</td>
                        <td class="tc">${product.contractRequired.item }</td>
                        <td class="tc">
                            <c:if test="${product.auditOffer == 0 }">
                                未审核
                            </c:if>
                            <c:if test="${product.auditOffer == 1 }">
                                已审价
                            </c:if>
                            <c:if test="${product.auditOffer == 2 }">
                                已复审
                            </c:if>
                        </td>
                    </tr>
                </c:if>
            </c:forEach>
        </table>
        <div id="pagediv" align="right"></div>
    </div>

</body>
</html>

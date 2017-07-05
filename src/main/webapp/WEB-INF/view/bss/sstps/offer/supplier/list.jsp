<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../../../../common.jsp" %>
    <title>供应商报价</title>

    <script type="text/javascript">

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
                        location.href = '${pageContext.request.contextPath }/offer/list.html?page=' + e.curr;
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

        function add() {
            var id = [];
            $('input[name="chkItem"]:checked').each(function () {
                id.push($(this).val());
            });
            if (id.length == 1) {
                window.location.href = "${pageContext.request.contextPath}/offer/selectProduct.html?contractId=" + id;
            } else if (id.length > 1) {
                layer.alert("只能选择一个", {offset: ['222px', '390px'], shade: 0.01});
            } else {
                layer.alert("请选择需要报价的合同", {offset: ['222px', '390px'], shade: 0.01});
            }
        }

        function resetQuery() {
            $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
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
                <a href="javascript:void(0);"> 保障作业</a>
            </li>
            <li>
                <a href="javascript:void(0);"> 单一来源审价</a>
            </li>
            <li><a href="javascript:jumppage('${pageContext.request.contextPath}/offer/list.html')">供应商报价</a></li>
        </ul>
        <div class="clear"></div>
    </div>
</div>

<div class="container">
    <div class="headline-v2">
        <h2>报价合同列表</h2>
    </div>

    <div class="search_detail">
        <form id="form1" action="${pageContext.request.contextPath }/offer/search.html" method="post" class="mb0">
            <ul class="demand_list">
                <li class="fl">
                    <label class="fl">合同名称：</label><span><input type="text" name="name" value="${name }"
                                                                class=""/></span>
                </li>
                <li class="fl">
                    <label class="fl">合同编号：</label><span><input type="text" name="code" value="${code }"
                                                                class=""/></span>
                </li>
                <li class="fl">
                    <label class="fl">供应商名称：</label><span><input type="text" name="supplierName"
                                                                 value="${supplierName }" class=""/></span>
                </li>
                <button type="submit" class="btn fl">查询</button>
                <button type="button" class="btn fl" onclick="resetQuery()">重置</button>
            </ul>
            <div class="clear"></div>
        </form>
    </div>

    <div class="col-md-12 pl20 mt10">
        <button class="btn" type="button" onclick="add()">报价</button>
    </div>

    <div class="content table_box">
        <table class="table table-bordered table-striped table-hover">
            <thead>
            <tr>
                <th class="info"><input id="checkAll" type="checkbox" onclick="selectAll()"/></th>
                <th class="info">序号</th>
                <th class="info" width="30%">合同名称</th>
                <th class="info" width="18%">合同编号</th>
                <th class="info" width="12%">合同金额(万元)</th>
                <th class="info" width="20%">供应商名称</th>
                <th class="info">签订状态</th>
            </tr>
            </thead>
            <c:forEach items="${list.list}" var="contract" varStatus="vs">
                <tr class="pointer">
                    <td class="tc w30"><input onclick="check()" type="checkbox" name="chkItem" value="${contract.id }"/>
                    </td>
                    <td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
                    <td class="tl">${contract.name }</td>
                    <td class="tl">${contract.code }</td>
                    <td class="tr">${contract.money }</td>
                    <td class="tl">${contract.supplierName }</td>
                    <td class="tc">
                        <c:if test="${contract.appraisal=='1' }">
                            审价中
                        </c:if>
                        <c:if test="${contract.appraisal=='2' }">
                            审价完成
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <div id="pagediv" align="right"></div>
</div>
</body>
</html>

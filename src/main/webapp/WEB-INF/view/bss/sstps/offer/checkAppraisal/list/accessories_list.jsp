<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
    <%@ include file="../../../../../common.jsp" %>

    <title>原、辅材料工艺定额消耗明细表</title>

    <script type="text/javascript">

        function onStep() {
            var productId = $("#proId").val();
            window.location.href = "${pageContext.request.contextPath}/offer/userSelectProductInfoCheck.do?productId=" + productId;
        }
        $(document).ready(function () {
            var workWeightTotal0 = document.getElementsByName("workWeightTotal0");
            var workMoney0 = document.getElementsByName("workMoney0");
            var workWeightTotal1 = document.getElementsByName("workWeightTotal1");
            var workMoney1 = document.getElementsByName("workMoney1");
            var workWeightTotalinput0 = document.getElementsByName("workWeightTotalinput0");
            var workMoneyinput0 = document.getElementsByName("workMoneyinput0");
            var workWeightTotalinput1 = document.getElementsByName("workWeightTotalinput1");
            var workMoneyinput1 = document.getElementsByName("workMoneyinput1");
            var checkMoneyinput0 = document.getElementsByName("checkMoneyinput0");
            var checkMoneyinput1 = document.getElementsByName("checkMoneyinput1");
            var workWeightTotal = 0;
            var workMoney = 0;
            var workWeightTotalInput = 0;
            var workMoneyInput = 0;
            var checkMoney0 = 0;
            var checkMoney1 = 0;
            /* for(var i=0;i<checkMoneyinput0.length;i++){
             if($(checkMoneyinput0[i].firstChild).val()!=""){
             checkMoney0+=parseFloat($(checkMoneyinput0[i].firstChild).val());
             }
             }
             $("#checkMoneyinput0").text(checkMoney0.toFixed(2));
             for(var i=0;i<checkMoneyinput1.length;i++){
             if($(checkMoneyinput1[i].firstChild).val()!=""){
             checkMoney1+=parseFloat($(checkMoneyinput1[i].firstChild).val());
             }
             }
             $("#checkMoneyinput1").text(checkMoney1.toFixed(2)); */
            for (var i = 0; i < workWeightTotalinput0.length; i++) {
                if ($(workWeightTotalinput0[i]).text() != "") {
                    workWeightTotalInput += parseFloat($(workWeightTotalinput0[i]).text());
                }
                if ($(workMoneyinput0[i]).text() != "") {
                    workMoneyInput += parseFloat($(workMoneyinput0[i]).text());
                }
            }
            $("#workWeightTotalinput0").text(workWeightTotalInput.toFixed(2));
            $("#workMoneyinput0").text(workMoneyInput.toFixed(2));
            workWeightTotalInput = 0;
            workMoneyInput = 0;
            for (var i = 0; i < workWeightTotalinput1.length; i++) {
                if ($(workWeightTotalinput1[i]).text() != "") {
                    workWeightTotalInput += parseFloat($(workWeightTotalinput1[i]).text());
                }
                if ($(workMoneyinput1[i]).text() != "") {
                    workMoneyInput += parseFloat($(workMoneyinput1[i]).text());
                }
            }
            $("#workWeightTotalinput1").text(workWeightTotalInput.toFixed(2));
            $("#workMoneyinput1").text(workMoneyInput.toFixed(2));

            for (var i = 0; i < workWeightTotal0.length; i++) {
                if ($(workWeightTotal0[i]).text() != "") {
                    workWeightTotal += parseFloat($(workWeightTotal0[i]).text());
                }
                if ($(workMoney0[i]).text() != "") {
                    workMoney += parseFloat($(workMoney0[i]).text());
                }
            }
            $("#workWeightTotal0").text(workWeightTotal.toFixed(2));
            $("#workMoney0").text(workMoney.toFixed(2));
            workWeightTotal = 0;
            workMoney = 0;
            for (var i = 0; i < workWeightTotal1.length; i++) {
                if ($(workWeightTotal1[i]).text() != "") {
                    workWeightTotal += parseFloat($(workWeightTotal1[i]).text());
                }
                if ($(workMoney1[i]).text() != "") {
                    workMoney += parseFloat($(workMoney1[i]).text());
                }
            }
            $("#workWeightTotal1").text(workWeightTotal.toFixed(2));
            $("#workMoney1").text(workMoney.toFixed(2));
            $('#total').text((parseFloat($("#workMoney1").text()) + parseFloat($("#workMoney0").text())).toFixed(2));
            $('#total2').text((parseFloat($("#workMoneyinput1").text()) + parseFloat($("#workMoneyinput0").text())).toFixed(2));
            $('#total3').text((parseFloat($("#total").text()) - parseFloat($("#total2").text())).toFixed(2));
            /*  $('#total4').text((checkMoney0+checkMoney1).toFixed(2)) */
            $("#Moneyinput1").text(parseFloat($("#workMoney1").text()) - parseFloat($("#workMoneyinput1").text()));
            $("#Moneyinput0").text(parseFloat($("#workMoney0").text()) - parseFloat($("#workMoneyinput0").text()));
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
                <a href="javascript:void(0);"> 保障作业</a>
            </li>
            <li>
                <a href="javascript:void(0);"> 单一来源审价</a>
            </li>
            <li>
                <a href="javascript:jumppage('${pageContext.request.contextPath}/offer/checkList.html')">审价人员复审</a>
            </li>
            <li><a href="javascript:void(0)">原、辅材料工艺定额消耗明细复审</a></li>
        </ul>
        <div class="clear"></div>
    </div>
</div>

<div class="container">
    <div class="headline-v2">
        <h2>原、辅材料工艺定额消耗明细表</h2>
    </div>

</div>
<input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>

<div class="container margin-top-5">
    <form action="${pageContext.request.contextPath}/accessoriesCon/userUpdateCheck.html?productId=${proId }"
          method="post" enctype="multipart/form-data">
        <div class="container padding-left-25 padding-right-25">
            <table class="table table-bordered table-condensed">
                <tr>
                    <th rowspan="2" class="info">序号</th>
                    <th rowspan="2" class="info">材料性质</th>
                    <th rowspan="2" class="info">材料名称</th>
                    <th rowspan="2" class="info">规格型号</th>
                    <th rowspan="2" class="info">图纸位置号<br/>（代号）</th>
                    <th colspan="5" class="info">所属加工生产装配工艺消耗定额（数量、质量、含税金额）</th>
                    <th colspan="5" class="info">消耗定额审核核准数（含税金额）</th>
                    <th rowspan="2" class="info">核减金额</th>
                    <!-- <th rowspan="2" class="info">复核减金额</th> -->
                    <th rowspan="2" class="info">供货单位</th>
                    <th rowspan="2" class="info">备 注</th>
                </tr>
                <tr>
                    <th class="info">数量</th>
                    <th class="info">单件重</th>
                    <th class="info">重量小计</th>
                    <th class="info">单价</th>
                    <th class="info">金额</th>
                    <th class="info">数量</th>
                    <th class="info">单件重</th>
                    <th class="info">重量小计</th>
                    <th class="info">单价</th>
                    <th class="info">金额</th>
                </tr>
                <tr id="tr0">
                    <td class="tc"><input type="checkbox" id="check0"/></td>
                    <td class="tc">一</td>
                    <td class="tl">主要材料</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="tc"></td>
                    <td class="tc" id="workWeightTotal0"></td>
                    <td class="tr"></td>
                    <td class="tr" id="workMoney0"></td>
                    <td class="tr"></td>
                    <td class="tc"></td>
                    <td class="tc" id="workWeightTotalinput0"></td>
                    <td class="tc"></td>
                    <td class="tr" id="workMoneyinput0"></td>
                    <td class="tr" id="Moneyinput0"></td>
                    <!-- <td id="checkMoneyinput0"></td> -->
                    <td></td>
                    <td></td>
                </tr>
                <c:forEach items="${list0}" var="acc" varStatus="vs">
                    <tr>
                        <td class="tc">${vs.index+1 }<input type="hidden" name="listAcc[${vs.index+1}].id"
                                                            value="${acc.id }"/></td>
                        <td class="tc">
                            <c:if test="${acc.productNature=='0' }">
                                主要材料
                            </c:if>
                            <c:if test="${acc.productNature=='1' }">
                                辅助材料
                            </c:if>
                        </td>
                        <td>${acc.stuffName }</td>
                        <td>${acc.norm }</td>
                        <td>${acc.paperCode }</td>
                        <td class="tc">${acc.workAmout }</td>
                        <td class="tc">${acc.workWeight }</td>
                        <td class="tc" name="workWeightTotal0">${acc.workWeightTotal }</td>
                        <td class="tc">${acc.workPrice }</td>
                        <td class="tr" name="workMoney0">${acc.workMoney }</td>
                        <td class="tc">${acc.consumeAmout }</td>
                        <td class="tc">${acc.consumeWeight }</td>
                        <td class="tc" name="workWeightTotalinput0">${acc.consumeWeightTotal }</td>
                        <td class="tc">${acc.consumePrice }</td>
                        <td class="tr" name="workMoneyinput0">${acc.consumeMoney }</td>
                        <td class="tr">${acc.subtractMoney }</td>
                            <%-- <td class="tc" name="checkMoneyinput0"><input type="text"  class='m0 p0  border0 w80 tr' value='${acc.checkMoney }' name="listAcc[${vs.index+1}].checkMoney"></td> --%>
                        <td>${acc.supplyUnit }</td>
                        <td>${acc.remark }</td>
                    </tr>
                </c:forEach>
                <tr id="tr1">
                    <td class="tc"><input type="checkbox" id="check1"/></td>
                    <td class="tc">二</td>
                    <td class="tl">辅助材料</td>
                    <td></td>
                    <td></td>
                    <td></td>
                    <td class="tc"></td>
                    <td class="tc" id="workWeightTotal1"></td>
                    <td class="tr"></td>
                    <td class="tr" id="workMoney1"></td>
                    <td class="tr"></td>
                    <td class="tc"></td>
                    <td class="tc" id="workWeightTotalinput1"></td>
                    <td class="tc"></td>
                    <td class="tr" id="workMoneyinput1"></td>
                    <td class="tr" id="Moneyinput1"></td>
                    <!-- <td id="checkMoneyinput1"></td> -->
                    <td></td>
                    <td></td>
                </tr>
                <c:forEach items="${list1}" var="acc" varStatus="vs">
                    <tr>
                        <td class="tc">${vs.index+1 }<input type="hidden"
                                                            name="listAcc[${fn:length(list0)+vs.index+1}].id"
                                                            value="${acc.id }"/></td>
                        <td class="tc">
                            <c:if test="${acc.productNature=='0' }">
                                主要材料
                            </c:if>
                            <c:if test="${acc.productNature=='1' }">
                                辅助材料
                            </c:if>
                        </td>
                        <td>${acc.stuffName }</td>
                        <td>${acc.norm }</td>
                        <td>${acc.paperCode }</td>
                        <td class="tc">${acc.workAmout }</td>
                        <td class="tc">${acc.workWeight }</td>
                        <td class="tc" name="workWeightTotal1">${acc.workWeightTotal }</td>
                        <td class="tc">${acc.workPrice }</td>
                        <td class="tr" name="workMoney1">${acc.workMoney }</td>
                        <td class="tc">${acc.consumeAmout }</td>
                        <td class="tc">${acc.consumeWeight }</td>
                        <td class="tc" name="workWeightTotalinput1">${acc.consumeWeightTotal }</td>
                        <td class="tc">${acc.consumePrice }</td>
                        <td class="tr" name="workMoneyinput1">${acc.consumeMoney }</td>
                        <td class="tr">${acc.subtractMoney }</td>
                            <%-- <td class="tc" name="checkMoneyinput1"><input type="text"  class='m0 p0  border0 w80 tr' value='${acc.checkMoney }' name="listAcc[${fn:length(list0)+vs.index+1}].checkMoney"></td> --%>
                        <td>${acc.supplyUnit }</td>
                        <td>${acc.remark }</td>
                    </tr>
                </c:forEach>
                <tr id="totalRow">
                    <td colspan="6" class="tc">总计：</td>
                    <td colspan="3"></td>
                    <td class="tr" id="total"></td>
                    <td colspan="4"></td>
                    <td class="tr" id="total2"></td>
                    <td class="tr" id="total3"></td>
                    <!-- <td id="total4"></td> -->
                    <td colspan="2"></td>
                </tr>
            </table>
        </div>

        <div class="col-md-12 tc">
            <button class="btn" type="button" onclick="onStep()">上一步</button>
            <button class="btn " type="submit">下一步</button>
        </div>
    </form>

</div>

</body>
</html>

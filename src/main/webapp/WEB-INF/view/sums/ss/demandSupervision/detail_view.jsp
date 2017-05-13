<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script src="${pageContext.request.contextPath}/public/webuploadFT/layui/layui.js"></script>
    <script type="text/javascript">
      /* $(function() {
              $(".progress-bar").each(function() {
                var progress = $(this).prev().val();
                progress = progress + "%";
                $(this).width(progress);
              });
            }); */

      function view(id) {
        window.location.href = "${pageContext.request.contextPath}/planSupervision/overview.html?id=" + id;
      }

      function bigImg(obj, x) {
        $(obj).append("<span>" + x + "%" + "</span>");
      }

      function normalImg(obj, x) {
        $(obj).children("span").remove();
      }

      $(function() {
        layui.use('flow', function() {
          var flow = layui.flow;
          flow.load({
            elem: '#tbody_id' //指定列表容器
              ,
            done: function(page, next) { //到达临界点（默认滚动触发），触发下一页
              var lis = [];
              //以jQuery的Ajax请求为例，请求下一页数据
              $.ajax({
                url: "${pageContext.request.contextPath}/supervision/paixu.do?id=${planId}&fileId=${demand.fileId}&page=" + page,
                type: "get",
                dataType: "json",
                success: function(res) {
                  layui.each(res.data, function(index, item) {
                    var department = "";
                    var progress = "";
                    if(item.price == null) {
                      department = "<div class='department'>" + item.department + "</div>";
                    } else if(item.price != 0) {
                      progress = "<div class='progress-new'><input type='hidden' value='" + item.progressBar + "'/><div id='progress' class='progress-bar' style='background:#2c9fa6;width:" + item.progressBar + "%" + "'" +
                        "onmouseover='bigImg(this,\"" + item.progressBar + "\")' onmouseout='normalImg(this,\"" + item.progressBar + "\")'></div></div>";
                    }
                    var code = "";
                    if(item.oneAdvice == "DYLY") {
                      code = item.supplier;
                    }
                    if(item.purchaseCount == 0) {
                      item.purchaseCount = "";
                    }
                    if(item.price == 0) {
                      item.price = "";
                    }
                    var html = "<tr class='pointer'><td class='tc w50'>" + item.seq + "</td><td>" +
                      department + "</td><td class='tl pl20'>" + item.goodsName + "</td><td class='tl pl20'>" + item.stand + "</td><td class='tl pl20'>" +
                      item.qualitStand + "</td><td class='tl pl20'>" + item.item + "</td><td class='tl pl20'>" + item.purchaseCount + "</td><td class='tr pr20'>" + item.price + "</td><td class='tr pr20'>" +
                      item.budget + "</td><td class='tl pl20'>" + item.deliverDate + "</td><td class='tl pl20'>" + item.purchaseType + "</td><td class='tl pl20'>" +
                      code + "</td><td class='tl pl20'>" + item.status + "</td><td class='tc' onclick='view(\"" + item.id + "\")'>" + progress + "</td></tr>";
                    lis.push(html);
                  });
                  next(lis.join(''), page < res.pages);
                },
              });
            }
          });
        });
      });
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)">首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">业务监管系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购业务监督</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">采购需求监督</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container container_box">
      <div>
        <h2 class="count_flow"><i>1</i>基本信息</h2>
        <ul class="ul_list">
          <c:if test="${'0' eq type}">
            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <td width="15%" class="info">需求名称：</td>
                  <td width="35%">${demand.planName}</td>
                  <td width="15%" class="info">需求编号：</td>
                  <td width="35%">${demand.planNo}</td>
                </tr>
                <tr>
                  <td width="15%" class="info">需求状态：</td>
                  <td width="25%">
                    <c:if test="${demand.status eq '1'}">未提交</c:if>
                    <c:if test="${demand.status eq '4'}">受理退回</c:if>
                    <c:if test="${demand.status eq '2' || demand.status eq '3' || demand.status eq '5'}">已提交</c:if>
                  </td>
                  <td width="15%" class="info">创建人：</td>
                  <td width="25%">${demand.userId}</td>
                </tr>
                <tr>
                  <td width="15%" class="info">创建日期：</td>
                  <td width="25%" colspan="3">
                    <fmt:formatDate value='${demand.createdAt}' pattern='yyyy-MM-dd HH:mm:ss' />
                  </td>
                </tr>
              </tbody>
            </table>
          </c:if>
          <c:if test="${'1' eq type}">
            <table class="table table-bordered mt10">
              <tbody>
                <tr>
                  <td width="25%" class="info">计划名称：</td>
                  <td width="25%">${collectPlan.fileName}</td>
                  <td width="25%" class="info">计划文号：</td>
                  <td width="25%">${collectPlan.taskId}</td>
                </tr>
                <tr>
                  <td width="25%" class="info">计划状态：</td>
                  <td width="25%">
                    <c:if test="${collectPlan.status == 1}">审核轮次设置</c:if>
                    <c:if test="${collectPlan.status == 2}">已下达</c:if>
                    <c:if test="${collectPlan.status == 3}">第一轮审核</c:if>
                    <c:if test="${collectPlan.status == 4}">第二轮审核人员设置</c:if>
                    <c:if test="${collectPlan.status == 5}">第二轮审核</c:if>
                    <c:if test="${collectPlan.status == 6}">第三轮审核人员设置</c:if>
                    <c:if test="${collectPlan.status == 7}">第三轮审核</c:if>
                    <c:if test="${collectPlan.status == 12}">未下达</c:if>
                  </td>
                  <td width="25%" class="info">创建人：</td>
                  <td width="25%">${collectPlan.userId}</td>
                </tr>
                <tr>
                  <td width="25%" class="info">创建日期：</td>
                  <td width="25%" colspan="3">
                    <fmt:formatDate value='${collectPlan.createdAt}' pattern='yyyy-MM-dd HH:mm:ss' />
                  </td>
                </tr>
              </tbody>
            </table>
          </c:if>
        </ul>
      </div>
      <div class="padding-top-10 clear" id="clear">
        <h2 class="count_flow">
            <i>2</i>
              <c:choose>
            <c:when test="${'0' eq type}">需求明细</c:when>
            <c:otherwise>采购明细</c:otherwise>
          </c:choose>
             </h2>
        <ul class="ul_list">
          <div class="col-md-12 col-sm-12 col-xs-12 p0 over_scroll" id="content">
            <table id="table" class="table table-bordered table-condensed lockout">
              <thead>
                <tr class="info">
                  <th class="w50">序号</th>
                  <th class="info department">需求部门</th>
                  <th class="info " width="10%">物资类别及名称</th>
                  <th class="info ">规格型号</th>
                  <th class="info " width="15%">质量技术标准<br/>(技术参数)</th>
                  <th class="info item">计量<br/>单位</th>
                  <th class="info ">采购<br/>数量</th>
                  <th class="info w80">单价<br/>（元）</th>
                  <th class="info w120">预算<br/>金额<br/>（万元）</th>
                  <th class="info " width="10%">交货期限</th>
                  <th class="info " width="8%">采购方式</th>
                  <th class="info " width="10%">供应商名称</th>
                  <th class="info " width="8%">状态</th>
                  <th class="info" width="8%">进度</th>
                </tr>
              </thead>
              <tbody id="tbody_id">

              </tbody>
            </table>
        </ul>
        </div>
      </div>
      <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
    </div>
  </body>

</html>
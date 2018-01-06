<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="../../../common.jsp"%>
    <script type="text/javascript">
      $(function() {
        laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${list.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${list.total}",
          startRow: "${list.startRow}",
          endRow: "${list.endRow}",
          groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
          curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
            var page = location.search.match(/page=(\d+)/);
            return page ? page[1] : 1;
          }(),
          jump: function(e, first) { //触发分页后的回调
            if(!first) { //一定要加此判断，否则初始时会无限刷新
              location.href = '${pageContext.request.contextPath}/statistical/view.html?page=' + e.curr;
            }
          }
        });

        $("#purchaseDepName").val("${purchaseDepName}");
      });

      function showCharts() {
        var myChart = echarts.init(document.getElementById("chart"));
        $.getJSON("${pageContext.request.contextPath}/statistical/echarts.do", function(json) {
          myChart.setOption(json);
          myChart.hideLoading();
        });

      }

      function on() {
        showCharts();
      }

      function resetQuery() {
        $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
      }
    </script>

  </head>

  <body>

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
            <a href="javascript:jumppage('${pageContext.request.contextPath}/statistical/view.html')">审价结果统计</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container mt20">
      <div class="search_detail">
      <form id="form1" action="${pageContext.request.contextPath}/statistical/view.html" method="post" enctype="multipart/form-data" class="mb0">
      <div class="m_row_5">
      <div class="row">
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购机构：</div>
            <div class="col-xs-8 f0 lh0">
              <select id="purchaseDepName" name="purchaseDepName" class="w100p h32 f14">
                  <option value="">--请选择--</option>
                  <c:forEach items="${purchaseOrgList}" var="p">
                    <option value="${p.id}">${p.name}</option>
                  </c:forEach>
              </select>
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">合同名称：</div>
            <div class="col-xs-8 f0 lh0">
              <input type="text" value="${name }" name="name" class="w100p h32 f14 mb0">
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">合同编号：</div>
            <div class="col-xs-8 f0 lh0">
              <input type="text" value="${code }" name="code" class="w100p h32 f14 mb0">
            </div>
          </div>
        </div>
        
        <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
          <div class="row">
            <div class="col-xs-12 f0">
              <button type="submit" class="btn mb0 h32">查询</button>
              <button type="button" class="btn mb0 mr0 h32" onclick="resetQuery()">重置</button>
            </div>
          </div>
        </div>
      </div>
      </div>
      </form>
      </div>
    </div>

    <div class="content table_box container">
      <div class="col-md-12 tab-v2 job-content">
        <div class="padding-top-10">
          <ul class="nav nav-tabs bgwhite">
            <li class="active">
              <a aria-expanded="true" href="#tab-1" data-toggle="tab" class="total_list f18">统计列表</a>
            </li>
            <li class="" onclick="on()">
              <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="anlysis f18">统计分析图</a>
            </li>
          </ul>
          <div class="tab-content padding-top-20">
            <div class="tab-pane fade active in height-450" id="tab-1">
              <div class=" margin-bottom-0 mt10">
                <table class="table table-bordered table-striped table-hover">
                  <thead>
                    <tr>
                      <th class="info w50">序号</th>
                      <th class="info" width="23%">采购机构</th>
                      <th class="info" width="25%">合同名称</th>
                      <th class="info" width="15%">合同编号</th>
                      <th class="info"  width="10%">合同金额(万元)</th>
                      <th class="info"  width="10%">审价金额(万元)</th>
                      <th class="info">审减百分比(%)</th>
                    </tr>
                  </thead>
                  <tbody>
                    <c:forEach items="${list.list}" var="statis" varStatus="vs">
                      <tr>
                        <td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
                        <td>${statis.purchaseDepName }</td>
                        <td>${statis.name }</td>
                        <td>${statis.code }</td>
                        <td class="tr">${statis.money }</td>
                        <td class="tr">${statis.auditMoney }</td>
                        <td class="tc">${(statis.money-statis.auditMoney)*100/statis.money}</td>
                      </tr>
                    </c:forEach>
                  </tbody>
                </table>
              </div>
              <div id="pagediv" align="right"></div>
            </div>

            <div class="tab-pane fade height-450" id="tab-2">
              <div id="dcDataUseStatisticContainer" class="margin-bottom-0">
                <div id="chart" class="icharts" style="width:800px; height:460px; margin:20px auto;">
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

  </body>

</html>
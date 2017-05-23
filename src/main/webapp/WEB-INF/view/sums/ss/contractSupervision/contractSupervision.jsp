<%@ page language="java" contentType="text/html; charset=utf-8"
  pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >

<head>
  <jsp:include page="/WEB-INF/view/common.jsp" />
  <script src="${pageContext.request.contextPath}/js/sums/jquery.circliful.min.js"></script>
  <script type="text/javascript">
    $(function() {
      $('.circle_box').circliful();
    });

    function contractDateil(id) {
      location.href = "${pageContext.request.contextPath }/contractSupervision/contractDateil.html?id=" + id;
    }

    function openProject(contractId) {
      location.href = "${pageContext.request.contextPath }/contractSupervision/projectDateil.html?contractId=" + contractId;
    }

    function openPlan(contractId) {
      location.href = "${pageContext.request.contextPath }/contractSupervision/planList.html?contractId=" + contractId;
    }

    function openDemand(contractId) {
      location.href = "${pageContext.request.contextPath }/contractSupervision/viewDemand.html?contractId=" + contractId;
    }
  </script>
</head>

<body>
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
      <ul class="breadcrumb margin-left-0">
        <li>
          <a href="javascript:void(0);"> 首页</a>
        </li>
        <li>
          <a href="javascript:void(0);">业务监管系统</a>
        </li>
        <li>
          <a href="javascript:void(0);">采购业务监督</a>
        </li>
        <li>
          <a href="javascript:void(0);">采购合同监督</a>
        </li>
      </ul>
      <div class="clear"></div>
    </div>
  </div>
  <div class="container">
    <div class="headline-v2">
      <h2>合同监督</h2>
    </div>
    <div class="content table_box">
      <table class="table table-bordered mt5">
        <tbody>
          <tr>
            <td class="w300 tc">采购需求</td>
            <td class="w300 tc">采购计划</td>
            <td class="w300 tc">采购项目</td>
            <td class="w300 tc">采购合同</td>
          </tr>
          <tr>
            <td class="tc" width="25%" onclick="openDemand('${contract.id}')">
              <div data-dimension="150" data-text="100%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="100" data-fgcolor="#24a34a" data-bgcolor="#eee" class="circle_box"></div>
            </td>
            <td class="tc" width="25%" onclick="openPlan('${contract.id}')">
              <div data-dimension="150" data-text="100%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="100" data-fgcolor="#24a34a" data-bgcolor="#eee" class="circle_box"></div>
            </td>
            <td class="tc" width="25%" onclick="openProject('${contract.id}')">
              <div data-dimension="150" data-text="100%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="100" data-fgcolor="#24a34a" data-bgcolor="#eee" class="circle_box"></div>
            </td>
            <td class="tc" width="25%" onclick="contractDateil('${contract.id}')">
              <c:if test="${contractStatus eq 100}">
                <div data-dimension="150" data-text="100%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="100" data-fgcolor="#24a34a" data-bgcolor="#eee" class="circle_box"></div>
              </c:if>
              <c:if test="${contractStatus eq 50}">
                <div data-dimension="150" data-text="50%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="50" data-fgcolor="#68d6fa" data-bgcolor="#eee" class="circle_box"></div>
              </c:if>
              <c:if test="${contractStatus eq 20}">
                <div data-dimension="150" data-text="20%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="20" data-fgcolor="#ff8641" data-bgcolor="#eee" class="circle_box"></div>
              </c:if>
            </td>
          </tr>

        </tbody>
      </table>
      <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
    </div>
  </div>

</body>

</html>
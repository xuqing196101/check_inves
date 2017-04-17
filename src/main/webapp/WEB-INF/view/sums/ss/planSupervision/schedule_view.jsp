<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script src="${pageContext.request.contextPath}/js/sums/jquery.circliful.min.js"></script>
    <script type="text/javascript">
      $(function() {
        $('.circle_box').circliful();
      });
      
      function view(id,type){
        window.location.href = "${pageContext.request.contextPath}/planSupervision/viewTask.html?id=" + id + "&type=" + type;
      }

      function viewProject(id, type) {
        var project = "${project}";
        if(project){
          window.location.href = "${pageContext.request.contextPath}/planSupervision/viewTask.html?id=" + id + "&type=" + type;
        }
      }
      function viewContract(id, type) {
        var contractRequireds = "${contractRequireds}";
        if(contractRequireds){
          window.location.href = "${pageContext.request.contextPath}/planSupervision/viewTask.html?id=" + id + "&type=" + type;
        }
      }
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
            <a href="javascript:void(0)">采购计划监督</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container">
      <div class="headline-v2">
        <h2>进度列表</h2>
      </div>
      <div class="content table_box">
        <table class="table table-bordered">
          <tbody>
            <tr>
              <td class="w350 tc">采购需求</td>
              <td class="w350 tc">采购计划</td>
              <td class="w350 tc">采购项目</td>
              <td class="w350 tc">采购合同</td>
            </tr>
            <tr>
              <td class="tc" width="25%" onclick="view('${collectPlan.id}','1')">
                <div data-dimension="150" data-text="100%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="100" data-fgcolor="#24a34a" data-bgcolor="#eee" class="circle_box"></div>
              </td>
              <td class="tc" width="25%" onclick="view('${collectPlan.id}','2')">
                <c:if test="${planStatus eq 20}">
                  <div data-dimension="150" data-text="20%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="20" data-fgcolor="#ff8641" data-bgcolor="#eee" class="circle_box"></div>
                </c:if>
                <c:if test="${planStatus eq 50}">
                  <div data-dimension="150" data-text="50%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="50" data-fgcolor="#68d6fa" data-bgcolor="#eee" class="circle_box"></div>
                </c:if>
                <c:if test="${planStatus eq 80}">
                  <div data-dimension="150" data-text="80%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="80" data-fgcolor="#038dbc" data-bgcolor="#eee" class="circle_box"></div>
                </c:if>
                <c:if test="${planStatus eq 100}">
                  <div data-dimension="150" data-text="100%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="100" data-fgcolor="#24a34a" data-bgcolor="#eee" class="circle_box"></div>
                </c:if>
              </td>
              <td class="tc" width="25%" onclick="viewProject('${collectPlan.id}','3')">
                <c:if test="${project ne null}">
                  <c:if test="${projectStatus gt 99}">
                  <div data-dimension="150" data-text="100%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="100" data-fgcolor="#24a34a" data-bgcolor="#eee" class="circle_box"></div>
                  </c:if>
                  <c:if test="${projectStatus gt 79 && projectStatus lt 99}">
                  <div data-dimension="150" data-text="${projectStatus}%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="${projectStatus}" data-fgcolor="#038dbc" data-bgcolor="#eee" class="circle_box"></div>
                  </c:if>
                  <c:if test="${projectStatus gt 49 && projectStatus lt 80}">
                  <div data-dimension="150" data-text="${projectStatus}%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="${projectStatus}" data-fgcolor="#68d6fa" data-bgcolor="#eee" class="circle_box"></div>
                  </c:if>
                  <c:if test="${projectStatus gt 0 && projectStatus lt 50}">
                  <div data-dimension="150" data-text="${projectStatus}%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="${projectStatus}" data-fgcolor="#ff8641" data-bgcolor="#eee" class="circle_box"></div>
                  </c:if>
                </c:if>
                <c:if test="${project eq null}">
                  <div data-dimension="150" data-text="0%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="0" data-fgcolor="#ffffff" data-bgcolor="#eeeeee" class="circle_box"></div>
                </c:if>
              </td>
              <td class="tc" width="25%" onclick="viewContract('${collectPlan.id}','4')">
                <c:if test="${contractRequireds eq null}">
                  <div data-dimension="150" data-text="0%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="0" data-fgcolor="#ffffff" data-bgcolor="#eeeeee" class="circle_box"></div>
                </c:if>
                <c:if test="${contractRequireds ne null}">
                  <c:if test="${contractStatus gt 99}">
                    <div data-dimension="150" data-text="${contractStatus}%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="${contractStatus}" data-fgcolor="#24a34a" data-bgcolor="#eee" class="circle_box"></div>
                  </c:if>
                  <c:if test="${contractStatus gt 79 && contractStatus lt 99}">
                  <div data-dimension="150" data-text="${contractStatus}%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="${contractStatus}" data-fgcolor="#038dbc" data-bgcolor="#eee" class="circle_box"></div>
                  </c:if>
                  <c:if test="${contractStatus gt 49 && contractStatus lt 80}">
                    <div data-dimension="150" data-text="${contractStatus}%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="${contractStatus}" data-fgcolor="#68d6fa" data-bgcolor="#eee" class="circle_box"></div>
                  </c:if>
                  <c:if test="${contractStatus gt 0 && contractStatus lt 50}">
                    <div data-dimension="150" data-text="${contractStatus}%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="${contractStatus}" data-fgcolor="#ff8641" data-bgcolor="#eee" class="circle_box"></div>
                  </c:if>
                </c:if>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
    <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
      <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
    </div>
  </body>

</html>
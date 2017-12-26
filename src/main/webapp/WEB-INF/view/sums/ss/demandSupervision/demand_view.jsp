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

      function view(id, type) {
        window.location.href = "${pageContext.request.contextPath}/supervision/demandDetail.html?requiredId=" + id + "&type=" + type;
      }

      function planDetail(id, type) {
        var details = "${planStatus}";
        if(details){
          window.location.href = "${pageContext.request.contextPath}/supervision/planDetail.html?requiredId=" + id + "&type=" + type;
        }
        
      }

      function viewProject(id) {
        var project = "${projectStatus}";
        if(project){
          window.location.href = "${pageContext.request.contextPath}/supervision/viewProject.html?requiredId=" + id;
        }
      }

      function viewContract(id) {
        var contractRequireds = "${contractStatus}";
        if(contractRequireds){
          window.location.href = "${pageContext.request.contextPath}/supervision/viewContract.html?requiredId=" + id;
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
              <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">业务监管系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购业务监督</a>
          </li>
          <li class="active">
              <a href="javascript:jumppage('${pageContext.request.contextPath}/supervision/demandSupervisionList.html');">采购需求监督</a>
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
              <td class="tc" width="25%" onclick="view('${requiredId}','0')">
                <div data-dimension="150" data-text="100%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="100" data-fgcolor="#24a34a" data-bgcolor="#eee" class="circle_box"></div>
              </td>
              <td class="tc" width="25%" onclick="planDetail('${requiredId}','1')">
                <c:if test="${planStatus eq null}">
                  <div data-dimension="150" data-text="0%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="0" data-fgcolor="#ffffff" data-bgcolor="#eeeeee" class="circle_box"></div>
                </c:if>
                <c:if test="${planStatus ne null}">
                  <c:if test="${planStatus eq 20}">
                  <div data-dimension="150" data-text="${planStatus}%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="${planStatus}" data-fgcolor="#ff8641" data-bgcolor="#eee" class="circle_box"></div>
	                </c:if>
	                <c:if test="${planStatus eq 50}">
	                  <div data-dimension="150" data-text="${planStatus}%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="${planStatus}" data-fgcolor="#68d6fa" data-bgcolor="#eee" class="circle_box"></div>
	                </c:if>
	                <c:if test="${planStatus eq 80}">
	                  <div data-dimension="150" data-text="${planStatus}%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="${planStatus}" data-fgcolor="#038dbc" data-bgcolor="#eee" class="circle_box"></div>
	                </c:if>
	                <c:if test="${planStatus eq 100}">
	                  <div data-dimension="150" data-text="${planStatus}%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="${planStatus}" data-fgcolor="#24a34a" data-bgcolor="#eee" class="circle_box"></div>
	                </c:if>
                </c:if>
              </td>
              <td class="tc" width="25%" onclick="viewProject('${requiredId}')">
                <c:if test="${projectStatus ne null}">
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
                <c:if test="${projectStatus eq null}">
                  <div data-dimension="150" data-text="0%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="0" data-fgcolor="#ffffff" data-bgcolor="#eeeeee" class="circle_box"></div>
                </c:if>
              </td>
              <td class="tc" width="25%" onclick="viewContract('${requiredId}')">
                <c:if test="${contractStatus eq null}">
                  <div data-dimension="150" data-text="0%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="0" data-fgcolor="#ffffff" data-bgcolor="#eeeeee" class="circle_box"></div>
                </c:if>
                <c:if test="${contractStatus ne null}">
                  <c:if test="${contractStatus eq 100}">
		                <div data-dimension="150" data-text="100%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="100" data-fgcolor="#24a34a" data-bgcolor="#eee" class="circle_box"></div>
		              </c:if>
		              <c:if test="${contractStatus gt 20 && contractStatus ge 99}">
		                <div data-dimension="150" data-text="${contractStatus}%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="${contractStatus}" data-fgcolor="#68d6fa" data-bgcolor="#eee" class="circle_box"></div>
		              </c:if>
		              <c:if test="${contractStatus gt 0 && contractStatus ge 20}">
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
      <a class="btn btn-windows back" href="${pageContext.request.contextPath}/supervision/demandSupervisionList.html" >返回</a>
    </div>
  </body>

</html>
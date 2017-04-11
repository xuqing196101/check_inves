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
      function view(id){
        window.location.href = "${pageContext.request.contextPath}/projectSupervision/viewPack.html?projectId="+id;
      }
      function contractList(id){
    	  window.location.href = "${pageContext.request.contextPath}/projectSupervision/contractList.html?projectId="+id;
      }
      function planList(id){
    	  window.location.href = "${pageContext.request.contextPath}/projectSupervision/planList.html?projectId="+id;
      }
      function demandList(id){
    	  window.location.href = "${pageContext.request.contextPath}/projectSupervision/demandList.html?projectId="+id;
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
            <a href="javascript:void(0)">采购项目监督</a>
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
          <td class="tc" width="25%"  onclick="demandList('${project.id}')">
            <div data-dimension="150" data-text="100%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="100" data-fgcolor="#24a34a" data-bgcolor="#eee" class="circle_box"></div>
          </td>
          <td class="tc" width="25%"  onclick="planList('${project.id}')">
            <div data-dimension="150" data-text="100%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="100" data-fgcolor="#24a34a" data-bgcolor="#eee" class="circle_box"></div>
          </td>
          <td class="tc" width="25%"   onclick="view('${project.id}')">
            <div data-dimension="150" data-text="100%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="100" data-fgcolor="#24a34a" data-bgcolor="#eee" class="circle_box"></div>
          </td>
          <td class="tc" width="25%" onclick="contractList('${project.id}')">
            <c:if test="${contractRequireds eq null}">
                  <div data-dimension="150" data-text="0%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="0" data-fgcolor="#ffffff" data-bgcolor="#eeeeee" class="circle_box"></div>
                </c:if>
                <c:if test="${contractRequireds ne null}">
                  <div data-dimension="150" data-text="100%" data-info="New Clients" data-width="15" data-fontsize="30" data-percent="100" data-fgcolor="#24a34a" data-bgcolor="#eee" class="circle_box"></div>
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

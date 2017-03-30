<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script src="${pageContext.request.contextPath}/public/easyui/jquery.easyui.min.js"></script>
    <link href="${pageContext.request.contextPath}/public/easyui/themes/icon.css" media="screen" rel="stylesheet" type="text/css">
    <link href="${pageContext.request.contextPath}/public/easyui/themes/default/easyui.css" media="screen" rel="stylesheet" type="text/css">
    <script type="text/javascript">
      function onclickDetail(id){
	      location.href="${pageContext.request.contextPath }/contractSupervision/contractDateil.html?id="+id;
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
        <h2>采购合同列表</h2>
      </div>
      <div class="col-md-12 pl20 mt10">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
      <div class="content table_box over_auto table_wrap">
        <table class="table table-striped table-bordered table-hover">
          <thead>
            <tr>

              <th class="tnone"></th>
              <th class="info w50">序号</th>
              <th class="info">合同编号</th>
              <th class="info">合同名称</th>
              <th class="info">合同金额(万元)</th>
              <th class="info">项目名称</th>
              <th class="info">计划文件号</th>
              <th class="info">预算(万元)</th>
              <th class="info">年度</th>
              <th class="info">项级预算科目</th>
              <th class="info">甲方单位</th>
              <th class="info">供应商</th>
              <th class="info">状态</th>
              <th class="info">查看</th>
            </tr>
          </thead>
          <c:forEach items="${listContract}" var="draftCon" varStatus="vs">
            <tr>
              <td class="tnone">${draftCon.status}</td>
              <td class="pl20 pointer">${(vs.index+1)}</td>
              <c:set value="${draftCon.code}" var="code"></c:set>
              <c:set value="${fn:length(code)}" var="length"></c:set>
              <c:if test="${length>7}">
                <td class="pointer pl20" title="${code}" onclick="openFile('${draftCon.id}');">${fn:substring(code,0,7)}...</td>
              </c:if>
              <c:if test="${length<=7}">
                <td class="pointer pl20" title="${code}" onclick="openFile('${draftCon.id}');">${code}</td>
              </c:if>
              <c:set value="${draftCon.name}" var="name"></c:set>
              <c:set value="${fn:length(name)}" var="length"></c:set>
              <c:if test="${length>9}">
                <td class="pointer pl20" title="${name}" onclick="openFile('${draftCon.id}');">
                  <a>${fn:substring(name,0,9)}...</a>
                </td>
              </c:if>
              <c:if test="${length<=9}">
                <td class="pointer pl20" title="${name}" onclick="openFile('${draftCon.id}');">
                  <a>${name}</a>
                </td>
              </c:if>
              <td class="tr pr20 pointer">${draftCon.money}</td>
              <td class="tl pl20 pointer">${draftCon.projectName}</td>
              <td class="tl pl20 pointer">${draftCon.documentNumber}</td>
              <td class="tr pr20 pointer">${draftCon.budget}</td>
              <td class="tc pointer">${draftCon.year}</td>
              <td class="tl pl20 pointer">${draftCon.budgetSubjectItem}</td>
              <td class="tl pl20 pointer">${draftCon.showDemandSector}</td>
              <td class="tl pl20 pointer">${draftCon.showSupplierDepName}</td>
              <c:if test="${draftCon.status==1}">
                <td class="tc pointer">草案</td>
              </c:if>
              <c:if test="${draftCon.status==2}">
                <td class="tc pointer">正式</td>
              </c:if>
              <td class="tl pl20 pointer">
                <a onclick="onclickDetail('${draftCon.id}');">进入</a>
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
    </div>
  </body>

</html>
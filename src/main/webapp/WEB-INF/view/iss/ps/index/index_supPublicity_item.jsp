<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<head>
<jsp:include page="/index_head.jsp"></jsp:include>
    <script type="text/javascript" src="${ pageContext.request.contextPath }/js/iss/ps/index/index_supPublicity_item.js"></script>
    <script type="text/javascript">
        $(function() {
            init();
        });
    </script>
</head>

<body>
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="${pageContext.request.contextPath}/"> 首页</a></li><li><a href="javascript:void(0);">类别</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="tab-v2 container job-content ">
      <div class="report_list_box">
          <ul id="page_ul_id" class="nav nav-tabs bgdd supplier_tab hand">
          </ul>
          <div class="padding-top-20 tab-pane fade display-none"  id="tab_1">
              <table class="table table-bordered table-condensed table-hover m_table_fixed_border" id="content_1">
                  <thead>
                  <tr>
                      <td class="tc info">序号</td>
                      <td class="tc info">类别</td>
                      <td class="tc info">大类</td>
                      <td class="tc info">中类</td>
                      <td class="tc info">小类</td>
                      <td class="tc info">品种名称</td>
                  </tr>
                  </thead>
                  <tbody>
                  </tbody>
              </table>
          </div>
          <div class="padding-top-20 tab-pane fade display-none"  id="tab_2">
              <table class="table table-bordered table-condensed table-hover m_table_fixed_border" id="content_2">
                  <thead>
                  <tr>
                      <td class="tc info">序号</td>
                      <td class="tc info">类别</td>
                      <td class="tc info">大类</td>
                      <td class="tc info">中类</td>
                      <td class="tc info">小类</td>
                      <td class="tc info">品种名称</td>
                  </tr>
                  </thead>
                  <tbody>
                  </tbody>
              </table>
          </div>
          <div class="padding-top-20 tab-pane fade display-none"  id="tab_3">
              <table class="table table-bordered table-condensed table-hover m_table_fixed_border" id="content_3">
                  <thead>
                  <tr>
                      <td class="tc info">序号</td>
                      <td class="tc info">类别</td>
                      <td class="tc info">大类</td>
                      <td class="tc info">中类</td>
                      <td class="tc info">小类</td>
                      <td class="tc info">品种名称</td>
                  </tr>
                  </thead>
                  <tbody>
                  </tbody>
              </table>
          </div>
          <div class="padding-top-20 tab-pane fade display-none"  id="tab_4">
              <table class="table table-bordered table-condensed table-hover m_table_fixed_border" id="content_4">
                  <thead>
                  <tr>
                      <td class="tc info">序号</td>
                      <td class="tc info">类别</td>
                      <td class="tc info">大类</td>
                      <td class="tc info">中类</td>
                      <td class="tc info">小类</td>
                      <td class="tc info">品种名称</td>
                  </tr>
                  </thead>
                  <tbody>
                  </tbody>
              </table>
          </div>
          <div id="pagediv" align="right"></div>
      </div>
  </div>
  <form id="form_id" action="" method="post">
      <input id="supplierId" name="supplierId" type="hidden" value="${supplierId}"  />
      <input name="supplierStatus" value="${supplierStatus}" type="hidden">
      <input type="hidden" id="supplierTypes" value="${supplierTypes}">
      <input type="hidden" name="supplierType" >
      <input type="hidden" name="flag" value="list">
  </form>
  <!--底部代码开始-->
  <jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>

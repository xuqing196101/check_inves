<%@ page language="java" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
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
            <a href="javascript:void(0)">支撑环境</a>
          </li>
          <li>
          	<a href="javascript:void(0)">供应商管理</a>
          </li>
          <li>
          	<a href="javascript:void(0)">供应商复核</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container container_box">
      <div class=" content height-350">
        <div class="col-md-12 tab-v2 job-content">
          <ul class="nav nav-tabs bgwhite">

          </ul>

          <h2 class="count_flow"><i>1</i>审核信息</h2>
          <ul class="ul_list hand">
            <table class="table table-bordered table-condensed table-hover">
              <thead>
                <tr>
                  <th class="info w50">序号</th>
                  <th class="info">项目</th>
                  <th class="info">扫描件</th>
                  <th class="info">原件与扫描件是否一致</th>
                  <th class="info">理由</th>
                </tr>
              </thead>
              <tr>
                <td>1</td>
                <td>基本账户开户许可证</td>
              </tr>
              <tr>
                <td>2</td>
                <td>营业执照（副本）</td>
              </tr>
              <tr>
                <td>2</td>
                <td>法人代表人身份证</td>
              </tr>
              <tr>
                <td>3</td>
                <td>生产或经营地址的房产证明或租赁协议</td>
              </tr>
              <tr>
                <td>4</td>
                <td>近三个月完税凭证</td>
              </tr>
            </table>
          </ul>
        </div>
      </div>
    </div>
  </body>

</html>
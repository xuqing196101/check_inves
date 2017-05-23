<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>

<html class=" js cssanimations csstransitions" lang="en">

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <link href="${pageContext.request.contextPath }/public/select2/css/select2.css" rel="stylesheet">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bss/ppms/main.js"></script>
  </head>

  <body onload="initLoad()">

    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs " id="bread_crumbs">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0);">首页</a>
          </li>
          <li>
            <a href="">保障作业</a>
          </li>
          <li>
            <a href="">采购项目管理</a>
          </li>
          <li>
            <a href="">采购项目实施</a>
          </li>
        </ul>
      </div>
    </div>
    <!--=== End Breadcrumbs ===-->

    <!--=== Content Part ===-->
    <div class="container content">
      <div class="row">
        <!-- Begin Content -->
          <div class="col-md-2 col-sm-3 col-xs-12" id="show_tree_div">
            <ul class="btn_list" id="menu">
              <c:forEach items="${fds}" var="fd">
                <!-- 已执行 -->
                <c:if test="${fd.status == 1}">
                  <li onclick="jumpLoad('${fd.url}','${project.id }','${fd.id}')" <c:if test="${fd.step == 1}">class="active "</c:if>>
                <a class="executed son-menu">${fd.name }</a>
                </li>
                </c:if>
                <!-- 执行中 -->
                <c:if test="${fd.status == 2}">
                  <li onclick="jumpLoad('${fd.url}','${project.id }','${fd.id}')" <c:if test="${fd.step == 1}">class="active "</c:if>>
                <a class="son-menu">${fd.name }</a>
                </li>
                </c:if>
                <!-- 环节结束，不可在操作 -->
                <c:if test="${fd.status == 3}">
                  <li onclick="jumpLoad('${fd.url}','${project.id }','${fd.id}')" <c:if test="${fd.step == 1}">class="active"</c:if>>
                <a class="executed son-menu ">${fd.name }</a>
                </li>
                </c:if>
                <!-- 未执行 -->
                <c:if test="${fd.status == 0}">
                  <li onclick="jumpLoad('${fd.url}','${project.id }','${fd.id}')" <c:if test="${fd.step == 1}">class="active"</c:if>>
                <a class="son-menu">${fd.name }</a>
                </li>
                </c:if>
              </c:forEach>
            </ul>
          </div>
          <!-- 右侧内容开始-->
          <input type="hidden" id="initurl" value="${url}">
          <!-- <div class="tag-box tag-box-v4 col-md-9 "  id="open_bidding_main">
                
            </div> -->
          <script type="text/javascript" language="javascript">
            function getContentSize() {
              var he = document.documentElement.clientHeight;
              var btn = $("#iframe_btns").outerHeight(true);
              var bread = $("#bread_crumbs").outerHeight(true);
              ch = (he - btn - bread) + "px";
              document.getElementById("open_bidding_iframe").style.height = ch;
            }
            window.onload = getContentSize;
            window.onresize = getContentSize;
          </script>
          <!-- 右侧内容开始-->
          <div class="tag-box tag-box-v4 col-md-10 col-sm-9 col-xs-12 pt10">
            <input type="hidden" id="isOperate">
            <form id="updateLinkId" action="" method="post" class="w100p fl mb10 border1 padding-10 bg11">
              <input type="hidden" id="projectId" name="projectId" value="${project.id}">
              <input type="hidden" id="type" name="type" value="${type}">
              <div class="fr" id="updateOperateId">
                <span class="fl h30 lh30">经办人：</span>
                <div class="w120 fl">
                  <input type="hidden" id="principalId">
                  <select id="principal" name="principal" onchange="submitCurrOperator();"></select>
                </div>
              </div>
              <div class="fr mr10" id="nextHaunjie">
                <span class="fl h30 lh30">下一环节：</span>
                <div class="fl">
                  <input type="hidden" id="huanjieId" name="huanjieId"><span id="huanjie" class="h30 lh30"></span>
                </div>
              </div>
              <div class="fl mr10">
                <span class="fl h30 lh30">变更经办人：</span>
                <div class="w120 fl">
                  <input type="hidden" id="currPrincipalId">
                  <input type="hidden" id="currHuanjieId">
                  <select id="currPrincipal" name="currPrincipal" onchange="updateCurrOperator()"></select>
                </div>
                <div class="fl ml5">
                  <input id="submitdiv" type="button" class="btn btn-windows git" onclick="submitcurr();" value="环节结束" />
                </div>
              </div>
            </form>
            <iframe frameborder="0" name="open_bidding_main" id="open_bidding_iframe" scrolling="auto" marginheight="0" width="100%" onLoad="iFrameHeight('open_bidding_iframe')" src="${pageContext.request.contextPath}/${url}"></iframe>
          </div>
          <div id="onmouse" onmouseover="bigImg(this)" onmouseout="normalImg(this)">
            <div class="mt5 mb5 tc">
              <%-- <button class="btn btn-windows delete" onclick="abandoned('${project.id}');" type="button">废标</button> --%>
              <button class="btn btn-windows back" onclick="back();" type="button">返回列表</button>
            </div>
          </div>
	     </div>
    </div>
    <!--/container-->
    <a id="as" class="dnone" target="open_bidding_main" class="son-menu"></a>
  </body>

</html>
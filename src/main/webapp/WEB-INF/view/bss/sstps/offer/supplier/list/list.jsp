<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>装备（产品）技术资料概述</title>

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
            <a href="javascript:void(0);"> 保障作业</a>
          </li>
          <li>
            <a href="javascript:void(0);"> 单一来源审价</a>
          </li>
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/offer/list.html')">供应商报价</a>
          </li>
          <li>
            <a href="javascript:void(0)">产品报价</a>
          </li>
          <li>
            <a href="javascript:void(0)">审价人员审价</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <form action="${pageContext.request.contextPath}/offerProduct/view.html" method="post" enctype="multipart/form-data">

      <div class="container">
        <div class="headline-v2">
          <h2>装备（产品）技术资料概述</h2>
        </div>

        <input type="hidden" id="proId" name="proId" class="w230 mb0" value="${contractProduct.id }" readonly>
        <input type="hidden" id="id" name="id" class="w230 mb0" value="${productInfo.id }" readonly>

        <div class="container padding-left-25 padding-right-25 mt5">
          <table class="table table-bordered">
            <tobody>
              <tr>
                <td width="10%" class="bggrey tr">产品名称：</td>
                <td width="25%">
                  <input type="text" id="name" name="name" class="col-md-8" value="${contractProduct.name }" readonly>
                </td>
              </tr>
              <tr>
                <td width="10%" class="bggrey tr">设计单位：</td>
                <td width="25%">
                  <input id="designDepartment" name="designDepartment" type="text" class="col-md-8" value="${productInfo.designDepartment }" readonly>
                </td>
              </tr>
              <tr>
                <td width="10%" class="bggrey tr">产品概述：</td>
                <td width="25%">
                  <textarea class="col-md-8 h100" id="productOverview" name="productOverview" title="不超过4000个字" placeholder="不超过4000个字" readonly>${productInfo.productOverview } </textarea>
                </td>
              </tr>
              <tr>
                <td width="10%" class="bggrey tr"> 产品生产过程概述：</td>
                <td width="25%">
                  <textarea class="col-md-8 h100" id="productProcess" name="productProcess" title="不超过4000个字" placeholder="不超过4000个字" readonly>${productInfo.productProcess }</textarea>
                </td>
              </tr>
              <tr>
                <td width="10%" class="bggrey tr">产品技术状况概述：</td>
                <td width="25%">
                  <textarea class="col-md-8 h100" id="productSkill" name="productSkill" title="不超过4000个字" placeholder="不超过4000个字" readonly>${productInfo.productSkill }</textarea>
                </td>
              </tr>
              <tr>
                <td width="10%" class="bggrey tr">结论：</td>
                <td width="25%">
                  <textarea class="col-md-8 h100" id="conclusion" name="conclusion" title="不超过4000个字" placeholder="不超过4000个字" readonly>${productInfo.conclusion }</textarea>
                </td>
              </tr>
            </tobody>
          </table>
        </div>

        <div class="col-md-12">
          <div class="mt40 tc mb50">
            <button class="btn" type="submit">下一步</button>
            <button class="btn btn-windows cancel" type="button" onclick="location.href='javascript:history.go(-1);'">取消</button>
          </div>
        </div>

      </div>
    </form>

  </body>

</html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>

    <title>修改</title>

    <script type="text/javascript">
      function down() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/specialCost/select.html?proId=" + proId;
      }
    </script>
  </head>

  <body>

    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">修改专项费用明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container container_box">
      <form action="${pageContext.request.contextPath}/specialCost/update.html" method="post">

        <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
        <input type="hidden" id="id" name="id" class="w230 mb0" value="${sc.id }" readonly>

        <div>
          <h2 class="f16 count_flow mt40">专项费用信息</h2>
          <ul class="ul_list mb20">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>项目名称：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input type="text" id="projectName" name="projectName" value="${sc.projectName }">
                <div class="cue">${ERR_projectName}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>项目明细：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input id="productDetal" name="productDetal" value="${sc.productDetal }" type="text">
                <div class="cue">${ERR_productDetal}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>名称：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input id="name" name="name" type="text" value="${sc.name }">
                <div class="cue">${ERR_name}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">规格型号：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input id="norm" name="norm" type="text" value="${sc.norm }">
                <div class="cue">${ERR_norm}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">计量单位：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input id="measuringUnit" name="measuringUnit" type="text" value="${sc.measuringUnit }">
                <div class="cue">${ERR_measuringUnit}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">数量(消耗使用)：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input id="amount" name="amount" type="text" value="${sc.amount }">
                <div class="cue">${ERR_paperCode}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">单价：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input id="price" name="price" type="text" value="${sc.price }">
                <div class="cue">${ERR_paperCode}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">金额：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input id="money" name="money" type="text" value="${sc.money }">
                <div class="cue">${ERR_money}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">分摊数量：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input id="proportionAmout" name="proportionAmout" type="text" value="${sc.proportionAmout }">
                <div class="cue">${ERR_proportionAmout}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">单位产品分摊额：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input id="proportionPrice" name="proportionPrice" type="text" value="${sc.proportionPrice }">
                <div class="cue">${ERR_proportionPrice}</div>
              </div>
            </li>
            <li class="col-md-12 col-sm-12 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">备注：</span>
              <div class="col-md-12 col-sm-12 col-xs-12 p0">
                <textarea class="col-md-12 col-sm-12 col-xs-12 h80" id="remark" name="remark" title="不超过200个字" placeholder="不超过200个字">${sc.remark }</textarea>
              </div>
            </li>
          </ul>
        </div>

        <div class="col-md-12">
          <div class="mt40 tc mb50">
            <button class="btn btn-windows edit" type="submit">修改</button>
            <button class="btn btn-windows cancel" type="button" onclick="down()">取消</button>
          </div>
        </div>

      </form>
    </div>

  </body>

</html>
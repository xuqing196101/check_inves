<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>修改</title>
    <script type="text/javascript">
      function down() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/outsourcingCon/select.html?proId=" + proId;
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
            <a href="javascript:void(0)">修改外协加工件消耗定额明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container container_box">
      <form action="${pageContext.request.contextPath}/outsourcingCon/update.html" method="post">

        <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
        <input type="hidden" id="id" name="id" class="w230 mb0" value="${out.id }" readonly>

        <div>
          <h2 class="f16 count_flow mt40"><i>01</i>材料信息</h2>
          <ul class="ul_list mb20">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>外协加工工件名称：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
                <input type="text" id="outsourcingName" name="outsourcingName" value="${out.outsourcingName }">
                <div class="cue">${ERR_outsourcingName}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>规格型号：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
                <input id="norm" name="norm" type="text" value="${out.norm }">
                <div class="cue">${ERR_norm}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>图纸位置号(代号)：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
                <input id="paperCode" name="paperCode" type="text" value="${out.paperCode }">
                <div class="cue">${ERR_paperCode}</div>
              </div>
            </li>
          </ul>
        </div>

        <div class="padding-top-10 clear">
          <h2 class="f16 count_flow mt40"><i>02</i>所属加工生产装配工艺消耗定额（数量、质量、含税金额）</h2>
          <ul class="ul_list mb20">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">数量：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
                <input type="text" id="workAmout" name="workAmout" value="${out.workAmout }">
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">单件重：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
                <input type="text" id="workWeight" name="workWeight" value="${out.workWeight }">
              </div>
            </li>

            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">重量小计：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
                <input type="text" id="workWeightTotal" name="workWeightTotal" value="${out.workWeightTotal }">
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">单价(元)：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
                <input type="text" id="workPrice" name="workPrice" value="${out.workPrice }">
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">金额：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
                <input type="text" id="workMoney" name="workMoney" value="${out.workMoney }">
              </div>
            </li>
          </ul>
        </div>

        <div class="padding-top-10 clear">
          <h2 class="f16 count_flow mt40"><i>03</i>消耗定额审核核准数（含税金额）</h2>
          <ul class="ul_list mb20">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">数量：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
                <input type="text" id="consumeAmout" name="consumeAmout" value="${out.consumeAmout }">
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">单价(元)：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
                <input type="text" id="consumePrice" name="consumePrice" value="${out.consumePrice }">
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">金额：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
                <input type="text" id="consumeMoney" name="consumeMoney" value="${out.consumeMoney }">
              </div>
            </li>
          </ul>
        </div>

        <div class="padding-top-10 clear">
          <h2 class="f16 count_flow mt40"><i>04</i>其他</h2>
          <ul class="ul_list mb20">
            <li class="col-md-4 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>供货单位：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 p0 input_group">
                <input type="text" id="supplyUnit" name="supplyUnit" value="${out.supplyUnit }">
                <div class="cue">${ERR_supplyUnit}</div>
              </div>
            </li>
            <li class="col-md-12 col-sm-12 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">备注：</span>
              <div class="col-md-12 col-sm-12 col-xs-12 p0">
                <textarea class="col-md-12 col-sm-12 col-xs-12 h80" id="remark" name="remark" title="不超过200个字" placeholder="不超过200个字">${out.remark }</textarea>
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
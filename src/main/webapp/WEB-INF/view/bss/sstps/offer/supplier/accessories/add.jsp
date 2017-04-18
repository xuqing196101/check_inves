<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML
<html>

  <head>
     <%@ include file="../../../../../common.jsp"%>

    <title>添加</title>

    <script type="text/javascript">
      function down() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath }/accessoriesCon/select.html?proId=" + proId;
      }
      function workWeightTotals(){
    	  var workAmout=$("#workAmout").val();
    	  var workWeight=$("#workWeight").val();
    	  $("#workWeightTotal").val((workAmout*workWeight).toFixed(2));
      }
      function workMoneys(){
    	  var workPrice=$("#workPrice").val();
    	  var workWeightTotal=$("#workWeightTotal").val();
    	  $("#workMoney").val((workPrice*workWeightTotal).toFixed(2));
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
            <a href="javascript:void(0)">供应商报价</a>
          </li>
          <li>
            <a href="javascript:void(0)">产品报价</a>
          </li>
          <li>
            <a href="javascript:void(0)">装备（产品）技术资料概述</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container container_box">
      <form action="${pageContext.request.contextPath }/accessoriesCon/save.html" method="post">

        <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>

        <div>
          <h2 class="f16 count_flow mt40"><i>01</i>材料信息</h2>
          <ul class="ul_list mb20">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>材料性质：</span>
              <div class="select_common input_group col-sm-12 col-xs-12 p0">
                <select class="col-md-12" id="productNature" name="productNature">
                  <option value="0">主要材料</option>
                  <option value="1">辅助材料</option>
                </select>
                <div class="cue">${ERR_productNature}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>材料名称：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                <input type="text" id="stuffName" name="stuffName" value="${acc.stuffName }">
                <span class="add-on">i</span>
                <div class="cue">${ERR_stuffName}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>规格型号：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                <input id="norm" name="norm" type="text" value="${acc.norm }" class="">
                <span class="add-on">i</span>
                <div class="cue">${ERR_norm}</div>
              </div>

            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>图纸位置号(代号)：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                <input id="paperCode" name="paperCode" type="text" value="${acc.paperCode }" class="">
                <span class="add-on">i</span>
                <div class="cue">${ERR_paperCode}</div>
              </div>
            </li>
          </ul>
        </div>

        <div class="padding-top-10 clear">
          <h2 class="f16 count_flow mt40"><i>02</i>所属加工生产装配工艺消耗定额（数量、质量、含税金额）</h2>
          <ul class="ul_list mb20">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>数量：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                <input type="text" id="workAmout" name="workAmout" value="${acc.workAmout }" class="">
                <span class="add-on">i</span>
                <div class="cue">${ERR_workAmout}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>单件重：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                <input type="text" id="workWeight" name="workWeight" value="${acc.workWeight }" class="" onblur="workWeightTotals();">
                <span class="add-on">i</span>
                <div class="cue">${ERR_workWeight}</div>
              </div>
            </li>

            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>重量小计：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                <input type="text" id="workWeightTotal" name="workWeightTotal" value="${acc.workWeightTotal }" class="">
                <span class="add-on">i</span>
                <div class="cue">${ERR_workWeightTotal}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>单价(元)：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                <input type="text" id="workPrice" name="workPrice" value="${acc.workPrice }" class="" onblur="workMoneys();">
                <span class="add-on">i</span>
                <div class="cue">${ERR_workPrice}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>金额(元)：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                <input type="text" id="workMoney" name="workMoney" value="${acc.workMoney }" class="">
                <span class="add-on">i</span>
                <div class="cue">${ERR_workMoney}</div>
              </div>
            </li>
          </ul>
        </div>

        <%-- <div class="padding-top-10 clear">
          <h2 class="f16 count_flow mt40"><i>03</i>消耗定额审核核准数（含税金额）</h2>
          <ul class="ul_list mb20">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">数量：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                <input type="text" id="consumeAmout" name="consumeAmout" value="${acc.consumeAmout }" class="">
                <span class="add-on">i</span>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">单件重：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 col-md-12input-append input_group col-sm-12 col-xs-12 p0">
                <input type="text" id="consumeWeight" name="consumeWeight" value="${acc.consumeWeight }" class="">
                <span class="add-on">i</span>
              </div>
            </li>

            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">重量小计：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                <input type="text" id="consumeWeightTotal" name="consumeWeightTotal" value="${acc.consumeWeightTotal }" class="">
                <span class="add-on">i</span>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">单价(元)：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                <input type="text" id="consumePrice" name="consumePrice" value="${acc.consumePrice }" class="">
                <span class="add-on">i</span>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">金额：</span>
              <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                <input type="text" id="consumeMoney" name="consumeMoney" value="${acc.consumeMoney }" class="">
                <span class="add-on">i</span>
              </div>
            </li>
          </ul>
        </div> --%>

        <div class="padding-top-10 clear">
          <h2 class="f16 count_flow mt40"><i>03</i>其他</h2>
          <ul class="ul_list mb20">
            <li class="col-md-4 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>供货单位：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input type="text" id="supplyUnit" name="supplyUnit" value="${acc.supplyUnit }" class="">
                <span class="add-on">i</span>
                <div class="cue">${ERR_supplyUnit}</div>
              </div>
            </li>
            <li class="col-md-12 col-sm-12 col-xs-12">
              <span class="fl col-md-12 col-sm-12 col-xs-12 padding-left-5">备注：</span>
              <div class="col-md-12 col-sm-12 col-xs-12 p0 mt5 ">
                <textarea class="col-md-12 h80 col-xs-12 col-sm-12" id="remark" name="remark" title="不超过200个字" placeholder="不超过200个字">${acc.remark } </textarea>
              </div>
            </li>
          </ul>
        </div>

        <div class="col-md-12">
          <div class="mt40 tc mb50">
            <button class="btn btn-windows save" type="submit">确定</button>
            <button class="btn btn-windows cancel" type="button" onclick="down()">取消</button>
          </div>
        </div>

      </form>
    </div>

  </body>

</html>
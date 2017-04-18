<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>添加</title>

    <script type="text/javascript">
      function down() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/outproductCon/select.html?proId=" + proId;
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
            <a href="javascript:void(0)">添加外购成品件消耗定额明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container container_box">
      <form action="${pageContext.request.contextPath}/outproductCon/save.html" method="post">

        <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>

        <div>
          <h2 class="f16 count_flow mt40"><i>01</i>成品信息</h2>
          <ul class="ul_list mb20">
            <li class="col-md-3 col-xs-12 col-sm-6 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>成品件名称：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input type="text" class="w220" id="finishedName" name="finishedName" value="${out.finishedName }">
                <span class="add-on">i</span>
                <div class="cue">${ERR_finishedName}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>规格型号：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input id="norm" name="norm" type="text" value="${out.norm }" class="w220">
               <span class="add-on">i</span>
                <div class="cue">${ERR_norm}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>图纸位置号(代号)：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input id="paperCode" name="paperCode" type="text" value="${out.paperCode }" class="w220">
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
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input type="text" id="workAmout" name="workAmout" value="${out.workAmout }" class="w220">
              <span class="add-on">i</span>
              <div class="cue">${ERR_workAmout}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>单件重：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input type="text" id="workWeight" name="workWeight" value="${out.workWeight }" class="w220" onblur="workWeightTotals();">
              <span class="add-on">i</span>
              <div class="cue">${ERR_workWeight}</div>
              </div>
            </li>

            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>重量小计：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input type="text" id="workWeightTotal" name="workWeightTotal" value="${out.workWeightTotal }" class="w220">
              <span class="add-on">i</span>
              <div class="cue">${ERR_workWeightTotal}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>单价(元)：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input type="text" id="workPrice" name="workPrice" value="${out.workPrice }" class="w220" onblur="workMoneys();">
              <span class="add-on">i</span>
              <div class="cue">${ERR_workPrice}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>金额：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input type="text" id="workMoney" name="workMoney" value="${out.workMoney }" class="w220">
              <span class="add-on">i</span>
              <div class="cue">${ERR_workMoney}</div>
              </div>
            </li>
          </ul>
        </div>

       <%--  <div class="padding-top-10 clear">
          <h2 class="f16 count_flow mt40"><i>03</i>消耗定额审核核准数（含税金额）</h2>
          <ul class="ul_list mb20">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">数量：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input type="text" id="consumeAmout" name="consumeAmout" value="${out.consumeAmout }" class="w220">
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">单价(元)：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input type="text" id="consumePrice" name="consumePrice" value="${out.consumePrice }" class="w220">
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">金额：</span>
              <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                <input type="text" id="consumeMoney" name="consumeMoney" value="${out.consumeMoney }" class="w220">
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
                <input type="text" id="supplyUnit" name="supplyUnit" value="${out.supplyUnit }" class="w220">
              <span class="add-on">i</span>
              <div class="cue">${ERR_supplyUnit}</div>
              </div>
            </li>
            <li class="col-md-12 col-sm-12 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">备注：</span>
              <div class="col-md-12 col-sm-12 col-xs-12 p0">
                <textarea class="col-md-12 col-sm-12 col-xs-12 h80 " id="remark" name="remark" title="不超过200个字" placeholder="不超过200个字">${out.remark } </textarea>
              </div>
            </li>
          </ul>
        </div>

        <div class="col-md-12 col-sm-12 col-xs-12 tc my20 mb50">
          <button class="btn btn-windows save" type="submit">确定</button>
          <button class="btn btn-windows cancel" type="button" onclick="down()">取消</button>
        </div>

      </form>
    </div>

  </body>

</html>
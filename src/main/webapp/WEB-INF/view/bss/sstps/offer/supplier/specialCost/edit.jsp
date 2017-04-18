<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>修改</title>
    <script src="${pageContext.request.contextPath}/public/easyui/jquery.easyui.min.js"></script>
	<link href="${pageContext.request.contextPath}/public/easyui/themes/icon.css" media="screen" rel="stylesheet" type="text/css">
	<link href="${pageContext.request.contextPath}/public/easyui/themes/default/easyui.css" media="screen" rel="stylesheet" type="text/css">
    <script type="text/javascript">
      function down() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/specialCost/select.html?proId=" + proId;
      }
      function moneys(){
    	  var amount=$("#amount").val();
    	  var price=$("#price").val();
    	  $("#money").val((amount*price).toFixed(2));
      }
      function proportionAmouts(){
    	  var money=$("#money").val();
    	  var proportionAmout=$("#proportionAmout").val();
    	  $("#proportionPrice").val((money/proportionAmout).toFixed(2));
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
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input class="easyui-combobox" name="projectName" id="projectName" data-options="valueField:'projectName',textField:'projectName',panelHeight:'auto',panelMaxHeight:200,panelMinHeight:100"  style="width: 100%;height: 29px" value="${sc.projectName }"/>
                <div class="cue">${ERR_projectName}</div>
              </div>
            </li>
            <script type="text/javascript">
				    $('#projectName').combobox({  
				        prompt:'',  
				        required:false,  
				        url: "${pageContext.request.contextPath }/specialCost/findAllProjectName.do?proId="+$("#proId").val(),  
				        editable:true,  
				        hasDownArrow:true,  
				        filter: function(L, row){  
				            var opts = $(this).combobox('options');  
				            return row[opts.textField].indexOf(L) == 0;  
				        }
				    });  
				 </script>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>项目明细：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="productDetal" name="productDetal" value="${sc.productDetal }" type="text" class="w220">
                <span class="add-on">i</span>
                <div class="cue">${ERR_productDetal}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>名称：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="name" name="name" type="text" value="${sc.name }" class="w220">
                <span class="add-on">i</span>
                <div class="cue">${ERR_name}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>规格型号：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="norm" name="norm" type="text" value="${sc.norm }" class="w220">
                <span class="add-on">i</span>
                <div class="cue">${ERR_norm}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>计量单位：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="measuringUnit" name="measuringUnit" type="text" value="${sc.measuringUnit }" class="w220">
                <span class="add-on">i</span>
                <div class="cue">${ERR_measuringUnit}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>数量(消耗使用)：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="amount" name="amount" type="text" value="${sc.amount }" class="w220">
                <span class="add-on">i</span>
                <div class="cue">${ERR_amout}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>单价：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="price" name="price" type="text" value="${sc.price }" class="w220"  onblur="moneys();">
                <span class="add-on">i</span>
                <div class="cue">${ERR_price}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>金额：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="money" name="money" type="text" value="${sc.money }" class="w220">
                <span class="add-on">i</span>
                <div class="cue">${ERR_money}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>分摊数量：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="proportionAmout" name="proportionAmout" type="text" value="${sc.proportionAmout }" class="w220" onblur="proportionAmouts();">
                <span class="add-on">i</span>
                <div class="cue">${ERR_proportionAmout}</div>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
              <span class="col-md-12 col-sm-12 col-xs-12padding-left-5"><div class="star_red">*</div>单位产品分摊额：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="proportionPrice" name="proportionPrice" type="text" value="${sc.proportionPrice }" class="w220">
                <span class="add-on">i</span>
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
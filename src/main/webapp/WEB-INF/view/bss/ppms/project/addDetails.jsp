<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
  //勾选明细
  function check(ele){
    var flag = $(ele).prop("checked");
    var purchaseType = $("input[name='chkItem']:checked").parents("tr").find("td").eq(10).children().val();
    purchaseType = $.trim(purchaseType);
    var goodUse = $("input[name='chkItem']:checked").parents("tr").find("td").eq(13).text();
    goodUse = $.trim(goodUse);
    if (!goodUse) {
      goodUse = null;
    }
    var id = $(ele).val();
    $.ajax({
      url:"${pageContext.request.contextPath}/project/checkDeail.html",
      data:"id="+id,
      type:"post",
      dataType:"json",
      success:function(result){
        for (var i = 0; i < result.length; i++) {
          $("input[name='chkItem']").each(function() {
            var v1 = result[i].id;
            var v3 = result[i].purchaseType;
          if(v3 == purchaseType){
            var v2 = $(this).val();
            if (v1 == v2) {
              $(this).prop("checked", flag);
            }
          }else{
            layer.alert("采购方式不相同",{offset: ['222px', '390px'], shade:0.01});
          }
          });
        } 
        $("input[name='chkItem']:checked").each(function() {
          var currGoodUse = $(this).parents("tr").find("td").eq(13).text();
          currGoodUse = $.trim(currGoodUse);
          if (!currGoodUse) {
            currGoodUse = null;
          }
          if (currGoodUse != goodUse) {
            $(this).prop("checked", false);
            layer.alert("进口方式不相同",{offset: ['222px', '390px'], shade:0.01});
          }
        });
      },
      error: function(){
        layer.msg("失败",{offset: ['222px', '390px']});
      }
    });
  }
    
    
  //返回
  function cancel(){
    window.location.href = "${pageContext.request.contextPath}/project/add.html";
  }
  
  function save(){
	    var checkIds =[]; 
	    $('input[name="chkItem"]:checked').each(function(){ 
	    	checkIds.push($(this).val()); 
	    });
	    if(checkIds.length>0){
	    	
	    	var checked;
	    	var unCheckedBoxs = [];
	          $('input[name="chkItem"]:not(:checked)').each(function() {
	            unCheckedBoxs.push($(this).val());
	       });
	       if(unCheckedBoxs<1){
	    	   checked=0;
	       }else{
	    	   checked=1;
	       }
	      $("#uncheckId").val(checked);
	    	var purchaseTypes = $("#purchaseTypes").val();
	    	$("#purchaseType").val(purchaseTypes);
	    	$("#detail_id").val(checkIds);
	      $("#save_form_id").submit();
	     }
	   }
</script>
</head>
  
<body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
      <ul class="breadcrumb margin-left-0">
        <li><a href="javascript:void(0)"> 首页</a></li>
        <li><a href="javascript:void(0)">保障作业</a></li>
        <li><a href="javascript:void(0)">项目管理</a></li>
        <li class="active"><a href="javascript:void(0)">选择明细</a></li>
      </ul>
      <div class="clear"></div>
    </div>
  </div>
  
  <!-- 录入采购计划开始-->
  <div class="container">
  
    <!-- 项目戳开始 -->
    <div class="col-md-12 pl20 mt10">
      <button class="btn btn-windows save" type="button" onclick="save()">确定</button>
      <button class="btn btn-windows back" type="button" onclick="javascript:history.go(-1);">返回</button>
    </div>
    <div class="content table_box">
      <table id="table" class="table table-bordered table-condensed table-hover">
        <thead>
          <tr>
            <th class="info w50">序号</th>
            <th class="info">需求部门</th>
            <th class="info">物资名称</th>
            <th class="info">规格型号</th>
            <th class="info">质量技术标准</th>
            <th class="info">计量单位</th>
            <th class="info">采购数量</th>
            <th class="info">单价（元）</th>
            <th class="info">预算金额（万元）</th>
            <th class="info">交货期限</th>
            <th class="info">采购方式</th>
            <th class="info">供应商名称</th>
            <th class="info">是否申请办理免税</th>
            <th class="info">物资用途（进口）</th>
            <th class="info">使用单位（进口）</th>
            <th class="info">备注</th>
            <th class="info w30">操作</th>
          </tr>
        </thead>
        <tbody>
          <c:forEach items="${lists}" var="obj" varStatus="vs">
            <c:if test="${obj.projectStatus eq '0'}">
                    <tr style="cursor: pointer;">
                      <td class="tc w50"> ${obj.seq}
                        <input type="hidden" name="list[${vs.index }].seq" value="${obj.seq }">
                        <input type="hidden" name="list[${vs.index }].id" value="${obj.id }">
                      </td>
                      <td class="tc">
                       <c:if test="${orgnization.id == obj.department}"> 
			                     ${orgnization.name}
			                 </c:if>
                        <input type="hidden" name="list[${vs.index }].department" value="${obj.department }">
                      </td>
                      <td class="tc">${obj.goodsName}
                        <input type="hidden" name="list[${vs.index }].goodsName" value="${obj.goodsName }">
                      </td>
                      <td class="tc">${obj.stand}
                        <input type="hidden" name="list[${vs.index }].stand" value="${obj.stand }">
                      </td>
                      <td class="tc">${obj.qualitStand}
                        <input type="hidden" name="list[${vs.index }].qualitStand" value="${obj.qualitStand }">
                      </td>
                      <td class="tc">${obj.item}
                        <input type="hidden" name="list[${vs.index }].item" value="${obj.item }">
                      </td>
                      <td class="tc">${obj.purchaseCount}
                        <input type="hidden" name="list[${vs.index }].purchaseCount" value="${obj.purchaseCount }">
                      </td>
                      <td class="tc">${obj.price}
                        <input type="hidden" name="list[${vs.index }].price" value="${obj.price }">
                      </td>
                      <td class="tc">${obj.budget}
                        <input type="hidden" name="list[${vs.index }].budget" value="${obj.budget }">
                      </td>
                      <td class="tc">${obj.deliverDate}
                        <input type="hidden" name="list[${vs.index }].deliverDate" value="${obj.deliverDate }">
                      </td>
                      <td class="tc">
                         <input type="hidden" id="purchaseTypes" value="${obj.purchaseType }">
                        <c:forEach items="${kind}" var="kind">
                          <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                        </c:forEach>
                        <input type="hidden" name="list[${vs.index }].purchaseType" value="${obj.purchaseType }">
                      </td>
                      <td class="tc">${obj.supplier}
                        <input type="hidden" name="list[${vs.index }].supplier" value="${obj.supplier }">
                      </td>
                      <td class="tc">${obj.isFreeTax}
                        <input type="hidden" name="list[${vs.index }].isFreeTax" value="${obj.isFreeTax }">
                      </td>
                      <td class="tc">${obj.goodsUse}
                        <input type="hidden" name="list[${vs.index }].goodsUse" value="${obj.goodsUse }">
                      </td>
                      <td class="tc">${obj.useUnit}
                        <input type="hidden" name="list[${vs.index }].useUnit" value="${obj.useUnit }">
                      </td>
                      <td class="tc">${obj.memo}
                        <input type="hidden" name="list[${vs.index }].memo" value="${obj.memo }">
                        <input type="hidden" name="list[${vs.index }].parentId" value="${obj.parentId }">
                        <input type="hidden" name="list[${vs.index }].detailStatus" value="${obj.detailStatus}">
                        <input type="hidden" name="list[${vs.index }].planType" value="${obj.planType}">
                      </td>
                      <td class="tc w30">
                        <input type="checkbox" value="${obj.id }" name="chkItem" onclick="check(this)" alt="">
                      </td>
                    </tr>
                    </c:if>
                  </c:forEach>
        </tbody>
      </table>
    </div>
  </div>
  
  <form id="save_form_id" action="${pageContext.request.contextPath}/project/save.html" method="post">
  
    <c:forEach items="${lists}" var="obj" varStatus="vs">
                    <input type="hidden" name="list[${vs.index }].memo" value="${obj.memo }">
                        <input type="hidden" name="list[${vs.index }].parentId" value="${obj.parentId }">
                        <input type="hidden" name="list[${vs.index }].detailStatus" value="${obj.detailStatus}">
                        <input type="hidden" name="list[${vs.index }].planType" value="${obj.planType}">
    </c:forEach>
    <input id="detail_id" name="checkIds" type="hidden" />
     <input name="name" type="hidden" value="${name}" />
    <input name="purchaseType" id="purchaseType" type="hidden"/>
    <input  name="projectNumber" value="${projectNumber}" type="hidden" />
    <input name="projectId" type="hidden" value="${projectId }" />
    <input name="id" type="hidden" value="${id}" />
    <input id="uncheckId" name="uncheckId" type="hidden" />
    <input id="uncheckId" name="orgId" type="hidden" value="${orgId }"/>
    
  </form> 
  
  
</body>
</html>

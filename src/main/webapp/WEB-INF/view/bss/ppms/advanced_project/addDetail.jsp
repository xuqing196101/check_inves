<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/public/backend/js/lock_table_head_two.js" ></script>
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
      url:"${pageContext.request.contextPath}/advancedProject/checkDetail.html",
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
    
  //保存
  function save(){
    var id =[]; 
    $('input[name="chkItem"]:checked').each(function(){ 
      id.push($(this).val()); 
    });
    if(id.length>0){
      $("#detail_id").val(id);
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
        <li><a href="javascript:void(0)">预研项目管理</a></li>
        <li class="active"><a href="javascript:void(0)">选择明细</a></li>
      </ul>
      <div class="clear"></div>
    </div>
  </div>
  
  <!-- 录入采购计划开始-->
  <div class="container">
    <!-- 项目戳开始 -->
    <div class="col-md-12 pl20 mt10">
      <button class="btn btn-windows save" type="button" onclick="save();">确定</button>
      <button class="btn btn-windows back" type="button" onclick="javascript:history.go(-1);">返回</button>
    </div>
    <div class="content table_box" id="content">
      <table id="table" class="table table-bordered table-condensed" style="border-color: rgb(221, 221, 221); color: rgb(51, 51, 51); width: 1600px; font-size: medium; max-width: 10000px; margin: 0px;">
        <thead>
          <tr class="space_nowrap">
            <th class="info w50">序号</th>
            <th class="info w80">需求部门</th>
            <th class="info w80">物资名称</th>
            <th class="info w80">规格型号</th>
            <th class="info w80">质量技术<br>标准</th>
            <th class="info w80">计量<br>单位</th>
            <th class="info w80">采购<br>数量</th>
            <th class="info w80">单价<br>（元）</th>
            <th class="info w80">预算金额<br>（万元）</th>
            <th class="info w80">交货期限</th>
            <th class="info w100">采购方式<br>建议</th>
            <th class="info w100">供应商名称</th>
            <th class="info w80">是否申请<br>办理免税</th>
            <th class="info w80">物资用途<br>（进口）</th>
            <th class="info w80">使用单位<br>（进口）</th>
            <th class="info w160">备注</th>
          </tr>
        </thead>
        <tbody id="tbody_id">
          <c:forEach items="${list}" var="obj" varStatus="vs">
              <c:if test="${obj.advancedStatus eq '0'}">
            <tr style="cursor: pointer;">
              <td class="tc w50">${obj.seq}</td>
              <td class=""><div class="w80">${obj.department}</div></td>
              <td class=""><div class="w80">${obj.goodsName}</div></td>
              <td class=""><div class="w80">${obj.stand}</div></td>
              <td class="tc"><div class="w80">${obj.qualitStand}</div></td>
              <td class="tc"><div class="w80">${obj.item}</div></td>
              <td class="tc"><div class="w80">${obj.purchaseCount}</div></td>
              <td class="tc"><div class="w80">${obj.price}</div></td>
              <td class="tc"><div class="w80">${obj.budget}</div></td>
              <td class="tc"><div class="w80">${obj.deliverDate}</div></td>
              <td class="tc">
               <div class="w100">
                <c:forEach items="${kind}" var="kind" >
                  <c:if test="${kind.id == obj.purchaseType}">
                    <input type="hidden" name="ttype" value="${kind.id }">
                    ${kind.name}
                  </c:if>
                </c:forEach>
                </div>
              </td>
              <td class="tc"><div class="w100">${obj.supplier}</div></td>
              <td class="tc"><div class="w80">${obj.isFreeTax}</div></td>
              <td class="tc"><div class="w80">${obj.goodsUse}</div></td>
              <td class="tc"><div class="w80">${obj.useUnit}</div></td>
              <td class="tc"><div class="w160">${obj.memo}</div></td>
              <td class="tc w30">
              <input type="checkbox" value="${obj.id }" name="chkItem" onclick="check(this);"  alt="">
              </td>
            </tr>
               </c:if>
          </c:forEach>  
        </tbody>
      </table>
    </div>
  </div>
  <form id="save_form_id" action="${pageContext.request.contextPath}/advancedProject/saveDetail.html" method="post">
    <input id="detail_id" name="id" type="hidden" />
    <input name="name" type="hidden" value="${project.name}" />
    <input name="projectId" type="hidden" value="${projectId}" />
    <input  name="projectNumber" value="${project.projectNumber}" type="hidden" />
  </form> 
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
    <%-- <script src="${pageContext.request.contextPath}/public/backend/js/lock_table_head_two.js" ></script> --%>
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
    <div class="col-md-12 col-sm-12 col-xs-12 p0 over_auto" id="content">
      <table id="table" class="table table-bordered table-condensed lockout">
        <thead>
          <tr class="space_nowrap">
            <th class="info seq">序号</th>
            <th class="info department">需求部门</th>
            <th class="info goodsname">物资名称</th>
            <th class="info stand">规格型号</th>
            <th class="info qualitstand">质量技术<br>标准</th>
            <th class="info item">计量<br>单位</th>
            <th class="info purchasecount">采购<br>数量</th>
            <th class="info price">单价<br>（元）</th>
            <th class="info budget">预算金额<br>（万元）</th>
            <th class="info deliverdate">交货期限</th>
            <th class="info purchasetype">采购方式<br>建议</th>
            <th class="info purchasename">供应商名称</th>
            <th class="info freetax">是否申请<br>办理免税</th>
            <th class="info goodsuse">物资用途<br>（进口）</th>
            <th class="info useunit">使用单位<br>（进口）</th>
            <th class="info memo">备注</th>
          </tr>
        </thead>
        <tbody id="tbody_id">
          <c:forEach items="${list}" var="obj" varStatus="vs">
              <c:if test="${obj.advancedStatus eq '0'}">
            <tr style="cursor: pointer;">
              <td><div class="seq">${obj.seq}</div></td>
              <td><div class="department">${obj.department}</div></td>
              <td class=""><div class="goodsname">${obj.goodsName}</div></td>
              <td><div class="stand">${obj.stand}</div></td>
              <td><div class="qualitstand">${obj.qualitStand}</div></td>
              <td><div class="item">${obj.item}</div></td>
              <td><div class="purchasecount">${obj.purchaseCount}</div></td>
              <td><div class="price">${obj.price}</div></td>
              <td><div class="budget">${obj.budget}</div></td>
              <td><div class="deliverdate">${obj.deliverDate}</div></td>
              <td>
               <div class="purchasetype">
                <c:forEach items="${kind}" var="kind" >
                  <c:if test="${kind.id == obj.purchaseType}">
                    <input type="hidden" name="ttype" value="${kind.id }">
                    ${kind.name}
                  </c:if>
                </c:forEach>
                </div>
              </td>
              <td><div class="purchasename">${obj.supplier}</div></td>
              <td><div class="freetax">${obj.isFreeTax}</div></td>
              <td><div class="goodsuse">${obj.goodsUse}</div></td>
              <td><div class="useunit">${obj.useUnit}</div></td>
              <td><div class="memo">${obj.memo}</div></td>
              <td>
              <div class="choose">
                 <input type="checkbox" value="${obj.id }" name="chkItem" onclick="check(this);"  alt="">
              </div>
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

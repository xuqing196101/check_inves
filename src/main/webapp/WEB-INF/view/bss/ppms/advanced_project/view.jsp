<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  <script type="text/javascript">
  /** 全选全不选 */
  function selectAll(){
    var checklist = document.getElementsByName ("chkItem");
    var checkAll = document.getElementById("checkAll");
    if(checkAll.checked){
      for(var i=0;i<checklist.length;i++){
        checklist[i].checked = true;
      } 
    }else{
      for(var j=0;j<checklist.length;j++){
        checklist[j].checked = false;
      }
    }
  }
    
  /** 单选 */
  function check(){
    var count=0;
    var checklist = document.getElementsByName ("chkItem");
    var checkAll = document.getElementById("checkAll");
    for(var i=0;i<checklist.length;i++){
      if(checklist[i].checked == false){
        checkAll.checked = false;
        break;
      }
      for(var j=0;j<checklist.length;j++){
        if(checklist[j].checked == true){
          checkAll.checked = true;
          count++;
        }
      }
    }
  }
    
  //返回
  function cancel(){
     /* var index=parent.layer.getFrameIndex(window.name);
     parent.layer.close(index); */
    window.location.href="${pageContext.request.contextPath}/project/list.html";
     
  }
    
</script>
</head>
  
<body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
      <ul class="breadcrumb margin-left-0">
        <li><a href="javascript:void(0)"> 首页</a></li>
        <li><a href="javascript:void(0)">保障作业系统</a></li>
        <li><a href="javascript:void(0)">采购项目管理</a></li>
        <li class="active"><a href="javascript:void(0)">查看项目</a></li>
      </ul>
      <div class="clear"></div>
    </div>
  </div>
  <!-- 录入采购计划开始-->
  <div class="container">
    <!-- 项目戳开始 -->
    <div class="headline-v2 fl">
      <h2>查看项目明细</h2>
    </div> 
    <div class="col-md-12 pl20 mt10">
      <button class="btn btn-windows back" onclick="location.href='javascript:history.go(-1);'">返回</button>
    </div>
    <div class="content table_box">
      <c:if test="${lists != null }">
        <table class="table table-bordered table-condensed table-hover table-striped">
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
              <th class="info">采购方式建议</th>
              <th class="info">供应商名称</th>
              <th class="info">是否申请办理免税</th>
              <th class="info">物资用途（进口）</th>
              <th class="info">使用单位（进口）</th>
              <th class="info">备注</th>
            </tr>
          </thead>
          <c:forEach items="${lists}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w50">${obj.serialNumber}</td>
              <td class="tc">${obj.department}</td>
              <td class="tc">${obj.goodsName}</td>
              <td class="tc">${obj.stand}</td>
              <td class="tc">${obj.qualitStand}</td>
              <td class="tc">${obj.item}</td>
              <td class="tc">${obj.purchaseCount}</td>
              <td class="tc">${obj.price}</td>
              <td class="tc">${obj.budget}</td>
              <td class="tc">${obj.deliverDate}</td>
              <td class="tc">
                <c:forEach items="${kind}" var="kind" >
                  <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                </c:forEach>
              </td>
              <td class="tc">${obj.supplier}</td>
              <td class="tc">${obj.isFreeTax}</td>
              <td class="tc">${obj.goodsUse}</td>
              <td class="tc">${obj.useUnit}</td>
              <td class="tc">${obj.memo}</td>
            </tr>
          </c:forEach>  
        </table> 
      </c:if>
      <c:if test="${packageList != null }">
        <c:forEach items="${packageList }" var="pack" varStatus="p">
          <div class="col-md-6 col-sm-6 col-xs-12 p0">
            <span class="f16 b">包名:</span>
            <span class="f14 blue">${pack.name }</span>
          </div>
          <input type="hidden" value="${pack.id }"/>
          <table class="table table-bordered table-condensed table-hover table-striped">
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
          <th class="info">采购方式建议</th>
          <th class="info">供应商名称</th>
          <c:if test="${pack.isImport==1 }">
                  <th class="info">是否申请办理免税</th>
                  <th class="info">物资用途（进口）</th>
                  <th class="info">使用单位（进口）</th>
                </c:if>
          <th class="info">备注</th>
              </tr>
            </thead>
            <c:forEach items="${pack.advancedDetails}" var="obj">
              <tr style="cursor: pointer;">
                <td class="tc w50">${obj.serialNumber}</td>
                <td class="tc">${obj.department}</td>
                <td class="tc">${obj.goodsName}</td>
                <td class="tc">${obj.stand}</td>
                <td class="tc">${obj.qualitStand}</td>
                <td class="tc">${obj.item}</td>
                <td class="tc">${obj.purchaseCount}</td>
                <td class="tc">${obj.price}</td>
                <td class="tc">${obj.budget}</td>
                <td class="tc">${obj.deliverDate}</td>
                <td class="tc">
                  <c:forEach items="${kind}" var="kind" >
                    <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
                  </c:forEach>
                </td>
                <td class="tc">${obj.supplier}</td>
                <c:if test="${pack.isImport==1 }">
                  <td class="tc">${obj.isFreeTax}</td>
                  <td class="tc">${obj.goodsUse}</td>
                  <td class="tc">${obj.useUnit}</td>
                </c:if>
                <td class="tc">${obj.memo}</td>
              </tr>
            </c:forEach> 
          </table>
        </c:forEach>
      </c:if>
    </div>
  </div>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <base href="${pageContext.request.contextPath}/">

    <title>确定中标供应商</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->

  </head>
  <script type="text/javascript">
  /** 全选全不选 */
  function selectAll(){
       var checklist = document.getElementsByName ("chkItem");
       var checkAll = document.getElementById("checkAll");
         if(checkAll.checked){
             for(var i=0;i<checklist.length;i++)
             {
                checklist[i].checked = true;
             } 
           }else{
            for(var j=0;j<checklist.length;j++)
            {
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

      
      //确认中标供应商
     function confirm(){
    	 var id=[]; 
         $('input[name="chkItem"]:checked').each(function(){ 
             id.push($(this).val());
         }); 
         if(id.length==1){
             window.location.href="${pageContext.request.contextPath}/winningSupplier/packageSupplier.html?packageId="+id+"&&flowDefineId=${flowDefineId}&&projectId=${projectId}";
         }else if(id.length>1){
             layer.alert("只能选择一个",{offset: ['100px', '300px'], shade:0.01});
         }else{
             layer.alert("请选择包",{offset: ['100px', '300px'], shade:0.01});
         }
     }
  </script>

  <body>
    <div class="col-md-12 p0">
      <ul class="flow_step">
        <li class="active">
          <a href="${pageContext.request.contextPath}/winningSupplier/selectSupplier.html?projectId=${projectId}">01、确认中标供应商</a>
          <i></i>
        </li>
        <li>
          <a href="${pageContext.request.contextPath}/winningSupplier/template.do?projectId=${projectId}">02、中标通知书</a>
          <i></i>
        </li>
        <li>
          <a href="${pageContext.request.contextPath}/winningSupplier/notTemplate.do?projectId=${projectId}">03、未中标通知书</a>
        </li>
      </ul>
    </div>
      <div class="headline-v2">
        <h2>专家抽取包列表</h2>
      </div>
      <c:if test="${execute != 'SCCUESS' }">
        <div class="col-md-12 pl20 mt10">
          <button class="btn btn-windows add" onclick="confirm();" type="button">选择</button>
        </div>
      </c:if>
      <div class="content table_box">
        <table class="table table-bordered table-condensed table-hover table-striped">
          <thead>
            <tr>
              <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th class="w50 info">序号</th>
              <th class="info">包名</th>
              <th class="info">中标供应商信息</th>
            </tr>
          </thead>
          <c:forEach items="${packList }" var="pack" varStatus="vs">
            <tr>
              <td class="tc"><input onclick="check();" type="checkbox" name="chkItem" value="${pack.id}" /></td>
              <td class="tc w30">${vs.count }</td>
              <td class="tc">${pack.name }</td>
              <td class="tc">
                <c:forEach items="${pack.listCheckPasses}" var="list">
<%--                   <c:choose> --%>
<%--                     <c:when test="${fn:length(list) != 0}"> --%>
                      ${list.supplier.supplierName}
<%--                     </c:when> --%>
<%--                     <c:otherwise> --%>
<!--                                                                    未选择 -->
<%--                     </c:otherwise> --%>
<%--                   </c:choose> --%>
                </c:forEach>
              </td>
            </tr>
          </c:forEach>
        </table>
      </div>
  </body>

</html>
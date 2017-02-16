<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <base href="${pageContext.request.contextPath}/">

    <title>录入标的</title>

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
    function selectAll() {
      var checklist = document.getElementsByName("chkItem");
      var checkAll = document.getElementById("checkAll");
      if(checkAll.checked) {
        for(var i = 0; i < checklist.length; i++) {
          checklist[i].checked = true;
        }
      } else {
        for(var j = 0; j < checklist.length; j++) {
          checklist[j].checked = false;
        }
      }
    }

    /** 单选 */
    function check() {
      var count = 0;
      var checklist = document.getElementsByName("chkItem");
      var checkAll = document.getElementById("checkAll");
      for(var i = 0; i < checklist.length; i++) {
        if(checklist[i].checked == false) {
          checkAll.checked = false;
          break;
        }
        for(var j = 0; j < checklist.length; j++) {
          if(checklist[j].checked == true) {
            checkAll.checked = true;
            count++;
          }
        }
      }
    }
    /**展示信息**/
    function view(id) {
      window.location.href = "${pageContext.request.contextPath}/winningSupplier/packageSupplier.html?packageId=" + id + "&&flowDefineId=${flowDefineId}&&projectId=${projectId}&&view=1";
    }
    
    //新增行
    function add(btns,detailId) {
   $(btns).parent().parent().parent().append('<tr><td><input type="hidden" value="'+detailId+'"/><input  type="text"></td><td><input type="text"></td><td><input type="text"></td><td><input type="text"></td><td><input type="text"></td><td><input type="text"></td><td><button class="btn btn-windows add " onclick="add(this,\''+detailId+'\');" type="button">新增</button></td></tr>');
    }

  </script>

  <body>
    <h2 class="list_title mb0 clear">标的录入</h2>
        <div class="content table_box pl0">
           <table class="table table-bordered table-condensed table-hover table-striped">
                  <tr class="tc ">
<!-- 		                <th class="w30"> -->
<!-- 		                  <input type="checkbox" id="checkAll"  onclick="selectAll()" /> -->
<!-- 		                </th> -->
<!--                     <th class="w30">序号</th> -->
                    <th class="150">物资名称</th>
                    <th>规格型号</th>
                    <th>质量技术标准</th>
                    <th>计量单位</th>
                    <th>采购数量</th>
                    <th>单价（元）</th>
                    <th>操作</th>
                   </tr>
                  <c:forEach items="${detailList }" var="detail" varStatus="p">
                    <tr  class="tc ">
<%--                       <td class=""> <input type="checkbox" value="${pack.id }" name="chkItem" onclick="check()"></td> --%>
<%--                       <td>${detail.serialNumber}</td> --%>
                      <td title="${detail.goodsName}">
                       <input type="hidden" name="detailId" value="${detail.id }">
                       <input type="text" name="goodsName" value="${detail.goodsName }">
                      </td>
                      <td class="w150" title=" ${detail.stand }">
                        <input type="text" name="stand" value=" ${detail.stand }">
                      </td>
                      <td title="${detail.qualitStand }">
                       <input type="text" name="qualitStand" value="${detail.qualitStand }">
                       </td>
                      <td><input type="text" name="qualitStand" value="${detail.item }"></td>
                        <td id="purchaseCount"><input type="text" name="item" value="${detail.item}"></td>
                        <td><input type="text" name="price" value="${detail.price }"></td>
                          <td><button class="btn btn-windows add " onclick="add(this,'${detail.id }');" type="button">新增</button></td>
                    </tr>
                </c:forEach>
              </table>
        </div>
  </body>

</html>
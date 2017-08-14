<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
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

    
    /** 执行完成*/
    function finish() {
      layer.confirm('确定之后不可修改，是否确定？', {
        btn: ['确定', '取消'],
        offset: ['100px', '300px'],
        shade: 0.01
      }, function(index) {
        $.ajax({
          type: "POST",
          url: "${pageContext.request.contextPath}/winningSupplier/executeFinish.html?flowDefineId=${flowDefineId}&&projectId=${projectId}",
          dataType: "json",
          success: function(data) {
            if(data == "SCCUESS") {
              window.location.href = '${pageContext.request.contextPath}/winningSupplier/selectSupplier.html?projectId=${projectId}&&flowDefineId=${flowDefineId}&&isFinish=1';
            } else {
              layer.alert("请选择中标供应商", {
                offset: ['100', '300px'],
                shade: 0.01
              });
            }
          }
        });

      }, function(index) {
        layer.close(index);
      });

    }
    
        /** 确认*/
    function qued() {
      var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
      var status = $("input[name='chkItem']:checked").parents("tr").find("td").eq(4).find("input").val();
      status = $.trim(status);
      if(status == "0"){
         if(id.length == 1){
	       layer.confirm('确定之后不可修改，是否确定？', {
	        btn: ['确定', '取消'],
	        shade: 0.01
	        }, function(index) {
	          $.ajax({
	            type: "POST",
	            url: "${pageContext.request.contextPath}/winningSupplier/comparisons.html?id="+id + "&projectId=${projectId}&flowDefineId=${flowDefineId}",
	            dataType: "json",
	            success: function(data) {
	              if(data == "SCCUESS") {
	                window.location.href = "${pageContext.request.contextPath}/winningSupplier/packageSuppliers.html?projectId=${projectId}&flowDefineId=${flowDefineId}&id=" + id;
	                //window.location.href = '${pageContext.request.contextPath}/winningSupplier/selectSupplier.html?projectId=${projectId}&&flowDefineId=${flowDefineId}&&isFinish=1';
	              } else {
	                layer.alert("请选择供应商", {
	                  offset: ['100', '300px'],
	                  shade: 0.01,
	                });
	              }
	            }
	            });
	    
	          }, function(index) {
	            layer.close(index);
	          }); 
	      }else if(id.length > 1){
	         layer.alert("只能选择一个!", {
                    shade: 0.01
                  });
	      }else{
	        layer.alert("请选择!", {
	                  shade: 0.01
	                });
	      }
      }else{
        layer.msg("已中标!");
      }
      
      

    }

  </script>

  <body>
    <h2 class="list_title mb0 clear">包列表</h2>
          <div class="col-md-12 col-xs-12 col-sm-12 mt10 p0">
            <c:if test="${ error ne null || error eq 'ERROR' }">
		          <div class="col-md-12 col-xs-12 col-sm-12 mt10 p0">
		            <button class="btn " onclick="finish();" type="button">执行完成</button>
		            <button class="btn " onclick="qued();" type="button">确定</button>
		          </div>
		        </c:if>
          </div>
        <div class="content table_box pl0">
          <table class="table table-bordered table-condensed table-hover table-striped">
            <thead>
              <tr>
                <th class="w30">
                  <input type="checkbox" id="checkAll" onclick="selectAll()" />
                </th>
                <th class="info">包名</th>
                <th class="info">供应商</th>
                <th class="info">最终报价</th>
                <th class="info">是否中标</th>
              </tr>
            </thead>
            <c:forEach items="${packLi}" var="pack" varStatus="vs">
              <tr>
                <td class="tc w30">
                  <input type="checkbox" value="${pack.id }" name="chkItem" onclick="check()">
                </td>
                <td class="tc">${pack.name }
                </td>
                <c:if test="${fn:length(pack.listCheckPasses) != 0}">
                  <c:forEach items="${pack.listCheckPasses}" var="list">
                    <td class="tc">
                      ${list.supplier.supplierName}
                    </td>
                    <td class="tc">
                      ${list.totalPrice}
                    </td>
                    <td class="tc">
                      <input type="hidden" id="isWonBid" value="${list.isWonBid}"/>
                      <c:if test="${'0' eq list.isWonBid}">否</c:if>
                      <c:if test="${'1' eq list.isWonBid}">是</c:if>
                    </td>
                  </c:forEach>
                </c:if>
              </tr>
            </c:forEach>
          </table>
        </div>
        
  </body>

</html>
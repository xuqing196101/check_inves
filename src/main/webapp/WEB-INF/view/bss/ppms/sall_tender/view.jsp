<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
  <!--<![endif]-->

  <head>
    <meta http-equiv="content-type" content="text/html; charset=UTF-8">
    <title></title>

    <!-- Meta -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <script type="text/javascript">
      function download() {
        window.location.href = "${pageContext.request.contextPath}/saleTender/download.do?projectId=${projectId}";
      }

      function register() {
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        var packId = $("#packId").val();

        var projectId = $("#projectId").val();

        var statusBid = $("input[name='chkItem']:checked").parents("tr").find("td").eq(5).text();
        if(id.length == 1) {
          if($.trim(statusBid) == "已缴纳") {
            layer.alert("该标书费已缴纳", {
              offset: ['180px', '200px'],
              shade: 0.01,
            });
          } else {
            layer.alert("未缴纳", {
              offset: ['180px', '200px'],
              shade: 0.01,
            });
            // window.location.href = "${pageContext.request.contextPath }/saleTender/register.html?id="+id+"&packId="+packId;
          }

        } else if(id.length > 1) {
          layer.alert("请选择一个登记的信息", {
            offset: ['180px', '200px'],
            shade: 0.01
          });
        } else {
          layer.alert("请选择登记的信息", {
            offset: ['180px', '200px'],
            shade: 0.01
          });
        }
      }

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

      function add(packId) {
    	  var kindName = $("#kindName").val();
    	  if(kindName=="公开招标"){
    		  var projectId = $("#projectId").val();
    	        window.location.href = "${pageContext.request.contextPath}/saleTender/showAllSupplier.html?projectId=" + projectId + "&packId=" + packId;
    	  }else{
    		  var id = [];
    	        $('input[name="chkItem"]:checked').each(function() {
    	          id.push($(this).val());
    	        });
    	        var packId = $("#packId").val();

    	        var projectId = $("#projectId").val();

    	        var statusBid = $("input[name='chkItem']:checked").parents("tr").find("td").eq(5).text();
    	        if(id.length == 1) {
    	          if($.trim(statusBid) == "已缴纳") {
    	            layer.alert("该标书费已缴纳", {
    	              offset: ['180px', '200px'],
    	              shade: 0.01,
    	            });
    	          } else {
    	            layer.alert("未缴纳", {
    	              offset: ['180px', '200px'],
    	              shade: 0.01,
    	            });
    	            // window.location.href = "${pageContext.request.contextPath }/saleTender/register.html?id="+id+"&packId="+packId;
    	          }

    	        } else if(id.length > 1) {
    	          layer.alert("请选择一个登记的信息", {
    	            offset: ['180px', '200px'],
    	            shade: 0.01
    	          });
    	        } else {
    	          layer.alert("请选择登记的信息", {
    	            offset: ['180px', '200px'],
    	            shade: 0.01
    	          });
    	        }
    	  }
        
      }

      function ycDiv(obj, index) {
        if($(obj).hasClass("jbxx") && !$(obj).hasClass("zhxx")) {
          $(obj).removeClass("jbxx");
          $(obj).addClass("zhxx");
        } else {
          if($(obj).hasClass("zhxx") && !$(obj).hasClass("jbxx")) {
            $(obj).removeClass("zhxx");
            $(obj).addClass("jbxx");
          }
        }

        var divObj = new Array();
        divObj = $(".p0" + index);
        for(var i = 0; i < divObj.length; i++) {
          if($(divObj[i]).hasClass("p0" + index) && $(divObj[i]).hasClass("hide")) {
            $(divObj[i]).removeClass("hide");
          } else {
            if($(divObj[i]).hasClass("p0" + index)) {
              $(divObj[i]).addClass("hide");
            }
          }
        };
      }
    </script>
  </head>

  <body>
    <!-- 表格开始-->
    <input type="hidden" id="projectId" value="${project.id }" />

    <h2 class="list_title">项目名称：${project.name } 项目编号：${project.projectNumber }
          </h2>

     <h2>
     <c:forEach items="${kind}" var="kind" >
        <c:if test="${kind.id == project.purchaseType}"><input type="hidden" id="kindName" value="${kind.name}"/></c:if>
     </c:forEach>
      
    <div class="clear">
      <c:forEach items="${packageList }" var="pack" varStatus="p">

        <c:set value="${p.index}" var="index"></c:set>

        <div>
            <h2><span onclick="ycDiv(this,'${index}')" class="f14 count_flow jbxx hand">包名:<span class="f14 blue">${pack.name }</span></span>
              <button class="btn btn-windows add" onclick="add('${pack.id }')" type="button">登记</button>
              <button class="btn btn-windows withdraw" onclick="download()" type="button">下载标书</button>
            </h2>
            <input type="hidden" id="packId" value="${pack.id }" />
	          
        </div>

        <div class="p0${index}">
          <table class="table table-bordered table-condensed table-hover table-striped">
            <thead>
              <tr>
                <th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
                <th class="info w50">供应商名称</th>
                <th class="info">联系人</th>
                <th class="info">联系电话</th>
                <th class="info">发售日期</th>
                <th class="info">标书费状态</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach items="${pack.saleTenderList}" var="obj" varStatus="vs">
                <tr>
                  <td class="tc opinter"><input onclick="check()" type="checkbox" name="chkItem" value="${obj.id}" /></td>
                  <td class="tc opinter w200" title="${obj.suppliers.supplierName}">
                    ${obj.suppliers.supplierName}
                  </td>
                  <td class="tc opinter w100">${obj.suppliers.contactName}</td>

                  <td class="tc opinter w110">${obj.suppliers.contactTelephone}</td>

                  <td class="tc opinter w150">
                    <fmt:formatDate value='${obj.createdAt}' pattern='yyyy-MM-dd  HH:mm:ss' />
                  </td>
                  <td class="tc opinter w60">
                    <c:if test="${obj.statusBid==1}">
                      未缴纳
                    </c:if>
                    <c:if test="${obj.statusBid==2}">
                      已缴纳
                    </c:if>
                  </td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </c:forEach>
    </div>
  </body>

</html>
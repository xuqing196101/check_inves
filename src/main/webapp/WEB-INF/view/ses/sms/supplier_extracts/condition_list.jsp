<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <title>任务管理</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">

    <script type="text/javascript">
      /*分页  */
      $(function() {
        laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${list.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${list.total}",
          startRow: "${list.startRow}",
          endRow: "${list.endRow}",
          groups: "${list.pages}" >= 5 ? 5 : "${list.pages}", //连续显示分页数
          curr: function() { //通过url获取当前页，也可以同上（pages）方式获取

            return "${list.pageNum}";
          }(),
          jump: function(e, first) { //触发分页后的回调
            if(!first) { //一定要加此判断，否则初始时会无限刷新
              //                      location.href = '${pageContext.request.contextPath}/SupplierExtracts/Extraction.html?id=${projectId}&page='+e.curr;
            }
          }
        });


        //获取包id
        var projectId = "${projectId}";
        if(projectId != null && projectId != '') {
          $("#projectName").attr("readonly", true);
          $("#projectNumber").attr("readonly", true);
          $("#packageName").attr("readonly", true);
        } else {
          $("#projectName").attr("readonly", false);
          $("#projectNumber").attr("readonly", false);
          $("#packageName").attr("readonly", false);
        }

      });
      
      function ycDiv(obj, index){
    	  if ($(obj).hasClass("jbxx") && !$(obj).hasClass("zhxx")) {
    	    $(obj).removeClass("jbxx");
    	    $(obj).addClass("zhxx");
    	  } else {
    	    if ($(obj).hasClass("zhxx") && !$(obj).hasClass("jbxx")) {
    	      $(obj).removeClass("zhxx");
    	      $(obj).addClass("jbxx");
    	    }
    	  }
    	  
    	  var divObj = new Array();
    	  divObj = $(".p0" + index);
    	  for (var i =0; i < divObj.length; i++) {
    	      if ($(divObj[i]).hasClass("p0"+index) && $(divObj[i]).hasClass("hide")) {
    	        $(divObj[i]).removeClass("hide");
    	      } else {
    	        if ($(divObj[i]).hasClass("p0"+index)) {
    	          $(divObj[i]).addClass("hide");
    	        };
    	      };
    	  };
    	}



      function add() {
        var packageId= $("#packageId").find("option:selected").val();
        $.ajax({
          cache: true,
          type: "POST",
          dataType: "json",
          url: '${pageContext.request.contextPath}/SupplierExtracts/validateAddExtraction.do',
          data: $('#form').serialize(), // 你的formid
          async: false,
          success: function(data) {
            $("#projectNameError").text("");
            $("#projectNumberError").text("");
            $("#packageNameError").text("");
            $("#dSupervise").text("");
            $("#extractionSitesError").text("");
            var map = data;
            $("#projectNameError").text(map.projectNameError);
            $("#projectNumberError").text(map.projectNumberError);
            $("#packageNameError").text(map.packageNameError);
            $("#dSupervise").text(map.supervise);
            $("#extractionSitesError").text(map.extractionSitesError);
            if(map.status != null && map.status != 0) {
              layer.alert("请全部抽取完之后在添加条件", {
                shade: 0.01
              });
            }
            if(map.sccuess == "SCCUESS") {
              var projectId = map.projectId;
              window.location.href = '${pageContext.request.contextPath}/SupplierExtracts/addExtractions.html?projectId=' + projectId + '&&typeclassId=${typeclassId}&&packageId='+packageId;
            }
          }
        });

      }
      /**抽取页面*/
      function opens(){
    	
    	  window.location.href = '${pageContext.request.contextPath}/SupplierExtracts/addExtractions.html?projectId=' + pachageId + '&&typeclassId=${typeclassId}&&packageId='+packageId;
}

      //选择监督人员
      function supervise() {
        //  iframe层
        var iframeWin;
        layer.open({
          type: 2,
          title: "选择监督人员",
          shadeClose: true,
          shade: 0.01,
          offset: '20px',
          move: false,
          area: ['90%', '50%'],
          content: '${pageContext.request.contextPath}/SupplierExtracts/showSupervise.do',
          success: function(layero, index) {
            iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
          },
          btn: ['保存', '关闭'],
          yes: function() {
            iframeWin.add();

          },
          btn2: function() {
            layer.closeAll();
          }
        });
      }
    </script>
  </head>

  <body>
    <!--面包屑导航开始-->
    <c:if test="${typeclassId!=null && typeclassId !='' }">
      <div class="margin-top-10 breadcrumbs ">
        <div class="container">
          <ul class="breadcrumb margin-left-0">
            <li>
              <a href="#"> 首页</a>
            </li>
            <li>
              <a href="#">支撑环境系统</a>
            </li>
            <li>
              <a href="#">供应商管理</a>
            </li>
            <li>
              <a href="#">供应商抽取</a>
            </li>
            <li class="active">
              <a href="#">供应商抽取列表</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
      </div>
    </c:if>

    <!-- 项目戳开始 -->
    <div class="container container_box">
      <form id="form">
            <!-- 监督人员 -->
            <input type="hidden" name="sids" id="sids" value="${userId}" />
            <!-- 打开类型 -->
          <input type="hidden" value="${typeclassId}" name="typeclassId"/>
            <!-- 项目id  -->
          <input type="hidden" id="projectId" value="${projectId}" name="id">
           <!-- 包id  -->
          <input type="hidden" id="packageIds" value="${packageId}" name="packageId">
        <div>
          <h2 class="count_flow"><i>1</i>必填项</h2>
          <div class="ul_list">
            <ul class="ul_list border0">
             <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>项目名称:</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input id="projectName" name="name" value="${projectName}" type="text">
                  <span class="add-on">i</span>
                  <div class="cue" id="projectNameError"></div>
                </div>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>项目编号:</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input id="projectNumber" name="projectNumber" value="${projectNumber}" type="text">
                  <span class="add-on">i</span>
                  <div class="cue" id="projectNumberError"></div>
                </div>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>采购方式:</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <select name="purchaseType" class="col-md-12 col-sm-12 col-xs-6 p0">
                    <c:forEach items="${findByMap}" var="map">
                      <option value="${map.id}">${map.name}</option>
                    </c:forEach>
                  </select>
                </div>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12 ">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="star_red">*</div>监督人员:</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input readonly id="supervises" title="${userName}" value="${userName}" onclick="supervise();" type="text">
                  <span class="add-on">i</span>
                  <div class="cue" id="dSupervise"></div>
                </div>
              </li>
               <li class="col-md-12 col-sm-12 col-xs-12 ">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><div class="star_red">*</div>抽取地区:</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <input id="extractionSites" name="extractionSites" value="${extractionSites}" type="text">
                  <span class="add-on">i</span>
                  <div class="cue" id="extractionSitesError"></div>
                </div>
              </li>
            </ul>
          </div>
        </div>
          <div>
          <h2 class="count_flow "><i>2</i>
                    <div class="ww50 fl">抽取信息</div>
          </h2>
           <div align="right" class=" pl20 mb10 " >
           <select class="w200" id="packageId" >
            <c:forEach items="${listResultSupplier}" var="list">
                <option value="${list.id }" >${list.name }</option>
            </c:forEach>
          </select>
            <button class="btn" 
                onclick="add();" type="button">抽取</button>
            <button class="btn"
                onclick="record();" type="button">引用其他包</button>
        </div>
          <div class="ul_list">
        <div class="clear">
            <input id="priceStr" name="priceStr" type="hidden" />
            <input id="projectId" name="projectId" value="${projectId }" type="hidden" />
            <c:forEach items="${listResultSupplier }" var="list" varStatus="vs">
              <c:set value="${vs.index}" var="index"></c:set>
              <div>
                <h2 onclick="ycDiv(this,'${index}')" class="count_flow jbxx hand">包名:<span class="f14 blue">${listResultSupplier[index].name }</span></h2>
              </div>
              <div class="p0${index}">
	              <table  class="table table-bordered table-condensed mt5">
	                <thead>
	                  <tr>
	                    <th class="info w50">序号</th>
	                    <th class="info">供应商名称</th>
	                    <th class="info">类型，级别</th>
	                    <th class="info">联系人名称</th>
	                    <th class="info">联系人电话</th>
	                    <th class="info">联系人手机</th>
	                  </tr>
	                </thead>
	                <tbody id="tbody">
	                  <c:forEach items="${list.listSupplier}" var="listyes" varStatus="vs">
	                    <tr class='cursor '>
	                      <td class='tc' >${vs.index+1}</td>
	                      <td class='tc' >${listyes.supplierName}</td>
	                      <td class='tc' >${listyes.supplierName}</td>
	                      <td class='tc' >${listyes.contactName}</td>
	                      <td class='tc' >${listyes.contactTelephone}</td>
	                      <td class='tc' >${listyes.contactMobile}</td>
	                    </tr>
	                  </c:forEach>
	                </tbody>
	              </table>
              </div>
            </c:forEach>
        </div>
        </div>
        </div>
      </form>
    </div>

  </body>

</html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp" %>
    <script type="text/javascript">
		  //默认不显示叉
		   $(function() {
		    $("td").each(function() {
		    $(this).parent("tr").find("td").find("a").hide();
		    });
		  });

			function reason(id){
			  var offset = "";
			  if (window.event) {
			    e = event || window.event;
			    var x = "";
			    var y = "";
			    x = e.clientX + 20 + "px";
			    y = e.clientY + 20 + "px";
			    offset = [y, x];
			  } else {
			      offset = "200px";
			  }
			  var supplierId=$("#supplierId").val();
			  var auditFieldName = $("#"+id+"_name").val(); //审批的字段名字
			  var auditContent= auditFieldName+"的产品信息"; //审批的字段内容
			  var index =  layer.prompt({
				  title: '请填写不通过的理由：', 
				  formType: 2, 
				  offset: offset,
			  }, function(text){
			    $.ajax({
			      url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
			      type:"post",
			      data:"auditType=products_page"+"&auditFieldName="+auditFieldName+"&auditContent="+auditContent+"&suggest="+text+"&supplierId="+supplierId+"&auditField="+id,
			      dataType:"json",
			      success:function(result){
			      result = eval("(" + result + ")");
			      if(result.msg == "fail"){
			        layer.msg('该条信息已审核过！', {
			          shift: 6, //动画类型
			          offset:'300px'
			          });
			        }
			      }
			    });
			    	$("#"+id+"_hidden").hide();
				    $("#"+id+"_show").show();
				    layer.close(index);
			    });
			}
			
			//下一步
			function nextStep(){
			  var action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
			  $("#form_id").attr("action",action);
			  $("#form_id").submit();
			}
			
			//上一步
			function lastStep(lastUrl){
			  $("#form_id").attr("action",lastUrl);
			  $("#form_id").submit();
			}
			
			//文件下載
			  function downloadFile(fileName) {
			    $("input[name='fileName']").val(fileName);
			    $("#download_form_id").submit();
			  }
  </script>
  
		<script type="text/javascript">
			function jump(str){
			  var action;
			  if(str=="essential"){
			     action ="${pageContext.request.contextPath}/supplierAudit/essential.html";
			  }
			  if(str=="financial"){
			    action = "${pageContext.request.contextPath}/supplierAudit/financial.html";
			  }
			  if(str=="shareholder"){
			    action = "${pageContext.request.contextPath}/supplierAudit/shareholder.html";
			  }
			  if(str=="materialProduction"){
			    action = "${pageContext.request.contextPath}/supplierAudit/materialProduction.html";
			  }
			  if(str=="materialSales"){
			    action = "${pageContext.request.contextPath}/supplierAudit/materialSales.html";
			  }
			  if(str=="engineering"){
			    action = "${pageContext.request.contextPath}/supplierAudit/engineering.html";
			  }
			  if(str=="serviceInformation"){
			    action = "${pageContext.request.contextPath}/supplierAudit/serviceInformation.html";
			  }
			  if(str=="items"){
			    action = "${pageContext.request.contextPath}/supplierAudit/items.html";
			  }
			  if(str=="product"){
			    action = "${pageContext.request.contextPath}/supplierAudit/product.html";
			  }
			  if(str=="applicationForm"){
			    action = "${pageContext.request.contextPath}/supplierAudit/applicationForm.html";
			  }
			  if(str=="reasonsList"){
			    action = "${pageContext.request.contextPath}/supplierAudit/reasonsList.html";
			  }
			  $("#form_id").attr("action",action);
			  $("#form_id").submit();
			}
		</script>
  </head>
  
  <body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
	  <div class="container">
	    <ul class="breadcrumb margin-left-0">
	      <li>
	        <a href="javascript:void(0);"> 首页</a>
	      </li>
	      <li>
	         <a href="javascript:void(0);">供应商管理</a>
	      </li>
	      <li>
	         <a href="javascript:void(0);">供应商审核</a>
	      </li>
	    </ul>
	  </div>
  </div> 
  <div class="container container_box">
    <div class="content height-350">
      <div class="col-md-12 tab-v2 job-content">
	      <ul class="nav nav-tabs bgdd">
	          <li onclick = "jump('essential')">
	            <a aria-expanded="false" href="#tab-1">基本信息</a>
	            <i></i>
	          </li>
	          <li onclick = "jump('financial')">
	            <a aria-expanded="true" href="#tab-2">财务信息</a>
	            <i></i>                            
	          </li>
	          <li onclick = "jump('shareholder')" >
	            <a aria-expanded="false" href="#tab-3">股东信息</a>
	            <i></i>
	          </li>
	          <c:if test="${fn:contains(supplierTypeNames, '生产')}">
	            <li onclick = "jump('materialProduction')">
	              <a aria-expanded="false" href="#tab-4">生产信息</a>
	              <i></i>
	            </li>
	          </c:if>
	          <c:if test="${fn:contains(supplierTypeNames, '销售')}">
	            <li onclick = "jump('materialSales')" >
	              <a aria-expanded="false" href="#tab-4" >销售信息</a>
	              <i></i>
	            </li>
	          </c:if>
	          <c:if test="${fn:contains(supplierTypeNames, '工程')}">
	            <li onclick = "jump('engineering')">
	              <a aria-expanded="false" href="#tab-4">工程信息</a>
	              <i></i>
	            </li>
	          </c:if>
	          <c:if test="${fn:contains(supplierTypeNames, '服务')}">
	            <li onclick = "jump('serviceInformation')" >
	              <a aria-expanded="false" href="#tab-4" >服务信息</a>
	              <i></i>
	            </li>
	          </c:if>
	          <li onclick = "jump('items')">
	            <a aria-expanded="false" href="#tab-4" >品目信息</a>
	            <i></i>
	          </li>
	          <li onclick = "jump('product')" class="active" >
	            <a aria-expanded="false" href="#tab-4" data-toggle="tab">产品信息</a>
	             <i></i>
	          </li>
	          <li onclick = "jump('applicationForm')">
	            <a aria-expanded="false" href="#tab-4" >申请表</a>
	            <i></i>
	          </li>
	          <li onclick = "jump('reasonsList')">
	            <a aria-expanded="false" href="#tab-4" >审核汇总</a>
	          </li>
	        </ul>

        <form id="form_id" action="" method="post">
            <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
        </form>
            
        <%-- <c:forEach items="${listItem}" var="item" varStatus="vs">
          <h2 class="count_flow"><i>${vs.index + 1}</i>${item.categoryName}产品信息表</h2>
          <ul class="ul_list">
            <table class="table table-bordered table-condensed table-hover">
            <thead>
              <tr>
                <th class="info w50">序号</th>
                <th class="info">所属类别</th>
                <th class="info">产品名称</th>
                <th class="info">品牌</th>
                <th class="info">规格型号</th>
                <th class="info">尺寸</th>
                <th class="info">生产产地</th>
                <th class="info">保质期</th>
                <th class="info">生产商</th>
                <th class="info">参考价格</th>
                <th class="info">产品图片</th>
                <th class="info">商品二维码</th>
                <th class="info"></th>
              </tr>
            </thead>
            <tbody >
              <c:forEach items="${item.listSupplierProducts}" var="products" varStatus="vs">
                <tr>
                  <td class="tc w50">${vs.index + 1}</td>
                  <td class="tc" onclick="reason('${products.id}');" id="${products.id}">${item.categoryName}</td>
                  <td class="tc" onclick="reason('${products.id}');" id="${products.id}_name">${products.name}</td>
                  <td class="tc" onclick="reason('${products.id}');">${products.brand}</td>
                  <td class="tc" onclick="reason('${products.id}');">${products.models}</td>
                  <td class="tc" onclick="reason('${products.id}');">${products.proSize}</td>
                  <td class="tc" onclick="reason('${products.id}');">${products.orgin}</td>
                  <td class="tc" onclick="reason('${products.id}');"><fmt:formatDate value="${products.expirationDate }" pattern="yyyy-MM-dd"/></td>
                  <td class="tc" onclick="reason('${products.id}');">${products.producer}</td>
                  <td class="tc" onclick="reason('${products.id}');">${products.referencePrice}</td>
                  <td class="tc">
                    <c:if test="${products.productPic != null}">
                      <a class="green" onclick="downloadFile('${products.productPic}')">下载附件</a>
                    </c:if>
                    <c:if test="${products.productPic == null}"><a class="red">无附件下载</a></c:if>
                  </td>
                  <td class="tc" >
                    <c:if test="${products.qrCode != null}">
                      <a class="green" onclick="downloadFile('${products.qrCode}')">下载附件</a>
                    </c:if>
                    <c:if test="${products.qrCode == null}"><a class="red">无附件下载</a></c:if>
                  </td>
                  <td class="tc">
                    <a  id="${products.id }_show" class="b f18 fl ml10 hand red">×</a>
                  </td>
                </tr>
              </c:forEach>
	                </tbody>
	              </table>
	              </ul>
	            </c:forEach> --%>
	            
	            <!--这是所有品目  -->
              <c:forEach items="${currSupplier.listSupplierItems}" var="category" varStatus="vs">
	              <h2 class="count_flow"><i>${vs.index + 1}</i>${category.categoryName}产品信息表</h2>
	              <input type="hidden" id="${category.id}_name" value="${category.categoryName}">
	              <ul class="ul_list">
	                <table class="table table-bordered table-condensed table-hover">
	                  <thead>
	                    <tr>
	                      <!--这是所有的品目参数  -->
	                      <th class="info w50">序号</th>
		                    <c:forEach items="${currSupplier.categoryParam}" var="item" varStatus="vs"> 
		                      <c:if test="${category.categoryId==item.cateId }">
		                        <th class="info">${item.paramName}</th>
		                      </c:if>
		                    </c:forEach>
		                  <th class="info w50">操作</th> 
	                    </tr>
	                  </thead>
	                  <tr >
	                  	<!--这是所有的品目参数值  -->
	                  	<td class="tc w50">${vs.index + 1}</td> 
	                      <c:forEach items="${currSupplier.categoryParam}" var="cate" varStatus="vs">
	                        <c:forEach items="${currSupplier.paramVleu}" var="obj"  > 
	                          <c:if test="${category.categoryId==cate.cateId and obj.categoryParamId==cate.id }"> 
	                            <td  align="center" >${obj.paramValue}</td>
	                          </c:if>
	                        </c:forEach> 
	                      </c:forEach>
	                    <td class="tc w50" >
	                      <p onclick="reason('${category.id}');" class="btn" id="${category.id}_hidden">审核</p>
                       	  <a  id="${category.id}_show" class="b red">×</a>
                     	</td>
	                  </tr>  
	                </table>
                </ul>
              </c:forEach>
            </div>
        <div class="col-sm-12 col-md-12 col-xs-12 add_regist tc">
          <!-- <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a> -->
          <a class="btn"  type="button" onclick="lastStep('${lastUrl}');">上一步</a>
          <a class="btn"  type="button" onclick="nextStep();">下一步</a>
        </div>
      </div>
    </div>
	  <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
	    <input type="hidden" name="fileName" />
	  </form>
  </body>
</html>

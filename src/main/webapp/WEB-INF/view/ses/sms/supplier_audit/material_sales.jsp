<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
		<title>物资-销售型专业信息</title>
		
    <script type="text/javascript">
	   //默认不显示叉
			$(function() {
			  $(":input").each(function() {
			    var onMouseMove = "this.style.background='#E8E8E8'";
			    var onmouseout = "this.style.background='#FFFFFF'";
			     $(this).attr("onMouseMove",onMouseMove);
			     $(this).attr("onmouseout",onmouseout);
			  });
			  
			  $("td").each(function() {
			  $(this).parent("tr").find("td").eq(6).find("a").hide();
			  });
			});
		  
			function reason(id){
			  var supplierId=$("#supplierId").val();
			  var auditContent= "销售-资质证书信息";
			  var index = layer.prompt({
				  title: '请填写不通过的理由：', 
				  formType: 2, 
				  offset: '100px'
			  }, function(text){
			    $.ajax({
			      url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
			      type: "post",
			      data: {"auditType":"mat_sell_page","auditFieldName":"销售-资质证书","auditContent":auditContent,"suggest":text,"supplierId":supplierId,"auditField":id},
			      dataType: "json",
			      success:function(result){
			      result = eval("(" + result + ")");
			      if(result.msg == "fail"){
			        layer.msg('该条信息已审核过！', {
			          shift: 6, //动画类型
			          offset:'100px'
			          });
			        }
			      }
			    });
			       $("#"+id+"_hidden").hide();
			       $("#"+id+"_show").show();
			       layer.close(index);
			    });
			}
		
		function reason1(obj){
	    var supplierId=$("#supplierId").val();
		  var auditField = obj.id;;
		  var auditContent;
		  var auditFieldName ;
		  var html = "<a class='abolish'><img src='/zhbj/public/backend/images/sc.png'></a>";
	    $("#"+obj.id+"").each(function() {
	      auditFieldName = $(this).parents("li").find("span").text().replace("：","").trim();
         auditContent = $(this).parents("li").find("input").val();
   		});
		  var index = layer.prompt({
			    title: '请填写不通过的理由：', 
			    formType: 2, 
			    offset: '100px'
		    }, function(text){
		    $.ajax({
	        url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
	        type:"post",
	        data: {"auditType":"mat_sell_page","auditFieldName":auditFieldName,"auditContent":auditContent,"suggest":text,"supplierId":supplierId,"auditField":auditField},
	        dataType:"json",
	        success:function(result){
	        result = eval("(" + result + ")");
	        if(result.msg == "fail"){
	          layer.msg('该条信息已审核过！', {
	            shift: 6, //动画类型
	            offset: '100px'
	            });
	         }
	        }
        });
        $(obj).after(html);
	       layer.close(index);
		    });
		  }
		
		//文件下載
		/*   function downloadFile(fileName) {
		    $("input[name='fileName']").val(fileName);
		    $("#download_form_id").submit();
		  } */
		  
		    function download(id,key){
		    var form = $("<form>");   
	        form.attr('style', 'display:none');   
	        form.attr('method', 'post');
	        form.attr('action', globalPath + '/file/download.html?id='+ id +'&key='+key);
	        $('body').append(form); 
	        form.submit();
		    }
		  
		  //只读
		  $(function() {
		    $(":input").each(function() {
		      $(this).attr("readonly", "readonly");
		    });
		  });
		
	      //下一步	
		  function nextStep(url){
		    $("#form_id").attr("action",url);
		    $("#form_id").submit();
		  }
		
		  //上一步
		  function lastStep(lastUrl){
		    $("#form_id").attr("action",lastUrl);
		    $("#form_id").submit();
		  }
			
				// 提示之前的信息
				function isCompare(field) {
					var supplierId=$("#supplierId").val();
					$.ajax({
						url: "${pageContext.request.contextPath}/supplierAudit/showModify.do",
						data: {"supplierId":supplierId, "beforeField":field, "modifyType":"sale"},
						async: false,
						success: function(result) {
							layer.tips("修改前:" + result, "#" + field, { 
								tips: 1
							});
						}
					});
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
			  if(str == "aptitude") {
					action = "${pageContext.request.contextPath}/supplierAudit/aptitude.html";
				}
			  if(str == "contract") {
					action = "${pageContext.request.contextPath}/supplierAudit/contract.html";
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
	            <a aria-expanded="false" href="#tab-1">详细信息</a>
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
	            <li onclick = "jump('materialSales')"  class="active">
	              <a aria-expanded="false" href="#tab-4" data-toggle="tab">销售信息</a>
	              <i></i>
	            </li>
	          </c:if>
	          <c:if test="${fn:contains(supplierTypeNames, '工程')}">
	            <li onclick = "jump('engineering')">
	              <a aria-expanded="false" href="#tab-4" >工程信息</a>
	              <i></i>
	            </li>
	          </c:if>
	          <c:if test="${fn:contains(supplierTypeNames, '服务')}">
	            <li onclick = "jump('serviceInformation')">
	              <a aria-expanded="false" href="#tab-4" >服务信息</a>
	              <i></i>
	            </li>
	          </c:if>
	          <li onclick = "jump('items')">
              <a aria-expanded="false" href="#tab-4" >品目信息</a>
              <i></i>
	          </li>
	          <li onclick="jump('aptitude')">
							<a aria-expanded="false" href="#tab-4">资质文件</a>
							<i></i>
						</li>
						<li onclick="jump('contract')">
							<a aria-expanded="false" href="#tab-4">品目合同</a>
						</li>
	          <li onclick = "jump('contract')">
	            <a aria-expanded="false" href="#tab-4" >产品信息</a>
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

          <form id="form_id" action="" method="post" >
              <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
          </form>
          
          <h2 class="count_flow"><i>1</i>供应商物资销售资质证书</h2>
          <ul class="ul_list">
	          <table class="table table-bordered table-condensed table-hover">
	            <thead>
	              <tr>
	                <th class="info w50">序号</th>
	                <th class="info">资质证书名称</th>
	                <th class="info">资质等级</th>
	                <th class="info">发证机关</th>
	                <th class="info">有效期(起止时间)</th>
	                <th class="info">是否年检</th>
	                <!-- <th class="info">附件</th> -->
	                <th class="info w50">操作</th>
	              </tr>
	            </thead>
	            <c:forEach items="${supplierCertSell}" var="s" varStatus="vs">
	              <tr>
	                <td class="tc">${vs.index + 1}</td>
	                <td class="tl pl20" id="${s.id}" >${s.name }</td>
	                <td class="tc"  >${s.levelCert}</td>
	                <td class="tc"  >${s.licenceAuthorith }</td>
	                <td class="tc"  >
	                  <fmt:formatDate value="${s.expStartDate }" pattern='yyyy-MM-dd'/>  至  
	                  <fmt:formatDate value="${s.expEndDate }" pattern='yyyy-MM-dd'/>
	                </td>
	                <td class="tc" >
	                 <c:if test="${s.mot==0 }">否</c:if>
	                 <c:if test="${s.mot==1 }">是</c:if>
	                </td>
	                <%-- <td class="tc" >
	                  <a class="mt3 color7171C6" href="javascript:download('${s.attachId}', '${sysKey}')">${s.attach}</a>
	                </td> --%>
	                <td class="tc w50">
	                  <p onclick="reason('${s.id}');"  id="${s.id}_hidden" class="btn">审核</p>
	                  <a id="${s.id }_show"><img src='/zhbj/public/backend/images/sc.png'></a>
	                </td>
	              </tr>
	            </c:forEach>
	          </table>
          </ul>
                
          <h2 class="count_flow"><i>2</i>供应商组织结构和人员</h2>
          <ul class="ul_list">
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">组织机构：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="orgName" type="text" value="${supplierMatSells.orgName }" onclick="reason1(this)" <c:if test="${fn:contains(field,'orgName')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('orgName');"</c:if>/>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">人员总数：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="totalPerson" type="text" value="${supplierMatSells.totalPerson }" onclick="reason1(this)" <c:if test="${fn:contains(field,'totalPerson')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalPerson');"</c:if>/>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">管理人员：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="totalMange" type="text"  value="${supplierMatSells.totalMange }" onclick="reason1(this)" <c:if test="${fn:contains(field,'totalMange')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalMange');"</c:if>/>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">技术人员：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="totalTech" type="text"  value="${supplierMatSells.totalTech }" onclick="reason1(this)" <c:if test="${fn:contains(field,'totalTech')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalTech');"</c:if>/>
              </div>
            </li>
            <li class="col-md-3 col-sm-6 col-xs-12 pl15">
              <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">工人(职员)：</span>
              <div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
                <input id="totalWorker" type="text" value="${supplierMatSells.totalWorker }" onclick="reason1(this)" <c:if test="${fn:contains(field,'totalWorker')}">style="border: 1px solid #FF8C00;" onMouseOver="isCompare('totalWorker');"</c:if>/>
              </div>
            </li>
          </ul>
        </div>
        <div class="col-sm-12 col-md-12 col-xs-12 add_regist tc">
          <!-- <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a> -->
          <a class="btn"  type="button" onclick="lastStep('${lastUrl}');">上一步</a>
          <a class="btn"  type="button" onclick="nextStep('${url}');">下一步</a>
        </div>
      </div>
    </div>   
    <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
      <input type="hidden" name="fileName" />
	  </form>
  </body>
</html>

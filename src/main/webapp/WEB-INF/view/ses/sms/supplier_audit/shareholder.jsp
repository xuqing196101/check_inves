<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
     <%@ include file="/WEB-INF/view/common.jsp" %>
		<title>股东信息</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<style type="text/css">
		td {
		  cursor:pointer;
		}  
		</style>
		<script type="text/javascript">
		  //默认不显示叉
		  $(function() {
		  $("td").each(function() {
		  $(this).find("a").eq(0).hide();
		  });
		});
		
		function reason(id, str){
		  /* var offset = "";
		  if (window.event) {
		    e = event || window.event;
		    var x = "";
		    var y = "";
		    x = e.clientX + 20 + "px";
		    y = e.clientY + 20 + "px";
		    offset = [y, x];
		  } else {
		      offset = "200px";
		  } */
		  var supplierId=$("#supplierId").val();
		  var auditContent=str + "股东信息"; //审批的字段内容
		  var index = layer.prompt({
		    title: '请填写不通过的理由：', 
		    formType: 2, 
		    offset: '100px',
		    }, 
		    function(text){
		    $.ajax({
		      url:"${pageContext.request.contextPath}/supplierAudit/auditReasons.html",
		      type:"post",
		      data: {"auditType":"basic_page","auditFieldName":"股东信息","auditContent":auditContent,"suggest":text,"supplierId":supplierId,"auditField":id},
		      dataType:"json",
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
		
		//下一步
	    function nextStep(url){
		  /*$("#form_id").attr("action",url);*/
		  var action = "${pageContext.request.contextPath}/supplierAudit/supplierType.html";
		  $("#form_id").attr("action",action);
		  $("#form_id").submit();
		}
		   
		//上一步
		function lastStep(){
		  var action = "${pageContext.request.contextPath}/supplierAudit/financial.html";
		  $("#form_id").attr("action",action);
		  $("#form_id").submit();
		}
		
		// 提示修改之前的信息
			function showContent(field, id) {
				var supplierId = $("#supplierId").val();
				var showId = field + "_" +id;
				$.ajax({
					url: "${pageContext.request.contextPath}/supplierAudit/showModify.do",
					data: {"supplierId":supplierId, "beforeField":field, "modifyType":"shareholder_page", "relationId":id},
					async: false,
					success: function(result) {
						layer.tips("修改前:" + result, "#" + showId, 
						{
							tips: 3
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
			  /*if(str=="materialProduction"){
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
			  }*/
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
			  if(str == "supplierType") {
					action = "${pageContext.request.contextPath}/supplierAudit/supplierType.html";
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
	          <a href="#"> 首页</a>
	        </li>
	        <li>
						<a href="#">支撑环境</a>
					</li>
	        <li>
	          <a href="#">供应商管理</a>
	        </li><li>
	          <a href="#">供应商审核</a>
	        </li>
	      </ul>
	    </div>
	  </div> 
    <div class="container container_box">
      <div class="content height-350">
        <div class="col-md-12 tab-v2 job-content">
          <%-- <ul class="nav nav-tabs bgdd">
        <li class=""><a>详细信息</a></li>
        <li class=""><a>财务信息</a></li>
        <li class="active"><a>股东信息</a></li>
        <c:if test="${fn:contains(supplierTypeNames, '生产')}">
        <li class=""><a >物资-生产专业信息</a></li>
        </c:if>
        <c:if test="${fn:contains(supplierTypeNames, '销售')}">
        <li class=""><a >物资-销售专业信息</a></li>
        </c:if>
        <c:if test="${fn:contains(supplierTypeNames, '工程')}">
        <li class=""><a >工程-专业信息</a></li>
        </c:if>
        <c:if test="${fn:contains(supplierTypeNames, '服务')}">
        <li class=""><a >服务-专业信息</a></li>
        </c:if>
        <li class=""><a >品目信息</a></li>
        <li class=""><a >产品信息</a></li>
        <li class=""><a >申请表</a></li>
        <li class=""><a >审核汇总</a></li>
        </ul> --%>
                    
        <ul class="flow_step">
          <li onclick = "jump('essential')">
            <a aria-expanded="false" >详细信息</a>
            <i></i>
          </li>
          <li onclick = "jump('financial')">
            <a aria-expanded="true">财务信息</a>
            <i></i>                            
          </li>
          <li onclick = "jump('shareholder')"  class="active" >
            <a aria-expanded="false">股东信息</a>
            <i></i>
          </li>
          <%--<c:if test="${fn:contains(supplierTypeNames, '生产')}">
            <li onclick = "jump('materialProduction')">
              <a aria-expanded="false" href="#tab-4" >生产信息</a>
              <i></i>
            </li>
          </c:if>
          <c:if test="${fn:contains(supplierTypeNames, '销售')}">
            <li onclick = "jump('materialSales')">
              <a aria-expanded="false" href="#tab-4" >销售信息</a>
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
          --%><li onclick = "jump('supplierType')">
           	  <a aria-expanded="false">供应商类型</a>
            	<i></i>
	          </li>
          <li onclick = "jump('items')">
            <a aria-expanded="false" >产品类别</a>
            <i></i>
	        </li>
	        <li onclick="jump('aptitude')">
						<a aria-expanded="false" >资质文件维护</a>
						<i></i>
					</li>
          <li onclick="jump('contract')">
					  <a aria-expanded="false" >销售合同</a>
					  <i></i>
					</li>
          <li onclick = "jump('applicationForm')">
            <a aria-expanded="false" >承诺书和申请表</a>
            <i></i>
          </li>
          <li onclick = "jump('reasonsList')">
            <a aria-expanded="false" >审核汇总</a>
          </li>
        </ul>

        <form id="form_id" action="" method="post" >
            <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
            <input name="supplierStatus" value="${supplierStatus}" type="hidden">
        </form>
        <ul class="ul_list count_flow">
        	<h5>出资人（股东）信息 （说明：出资人（股东）多于10人的，出资金额前十位的信息，但出资比例应高于50%）</h5>
          <table class="table table-bordered table-condensed table-hover">
            <thead>
		          <tr>
		            <th class="info w50">序号</th>
		            <th class="info">出资人性质</th>
		            <th class="info">出资人名称或姓名</th>
		            <th class="info">统一社会信用代码或身份证号码</th>
		            <th class="info">出资金额或股份(万元/份)</th>
		            <th class="info">比例(%)</th>
		            <th class="info w50">操作</th>
		          </tr>
            </thead>
	            <c:forEach items="${shareholder}" var="s" varStatus="vs">
	              <tr>
		              <td class="tc">${vs.index + 1}</td>
		              <td class="tc" id="nature_${s.id }" <c:if test="${fn:contains(field,s.id.concat('_nature'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('nature','${s.id}');"</c:if>>
		              	<c:if test="${s.nature eq '1'}">法人</c:if>
		              	<c:if test="${s.nature eq '2'}">自然人</c:if>
		              </td>
		              <td class="tl pl20" id="name_${s.id }" <c:if test="${fn:contains(field,s.id.concat('_name'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('name','${s.id}');"</c:if> >${s.name}</td>
		              <td class="tc" id="identity_${s.id }" <c:if test="${fn:contains(field,s.id.concat('_identity'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('identity','${s.id}');"</c:if>>${s.identity}</td>
		              <td class="tc" id="shares_${s.id }" <c:if test="${fn:contains(field,s.id.concat('_shares'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('shares','${s.id}');"</c:if>>${s.shares}</td>
		              <td class="tc" id="proportion_${s.id }" <c:if test="${fn:contains(field,s.id.concat('_proportion'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('proportion','${s.id}');"</c:if>>${s.proportion}</td>
		              <td class="tc w50" >
		                <a id="${s.id}_show"><img src='/zhbj/public/backend/images/sc.png'></a>
		                <p onclick="reason('${s.id}','${s.name}');" id="${s.id}_hidden" class="editItem"><img src='/zhbj/public/backend/images/light_icon.png'></a>
		              </td>
	              </tr>
	            </c:forEach>
            </table>
          </ul>
	      <div class="col-sm-12 col-xs-12 col-md-12 add_regist tc">
		    <!-- <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a> -->
		    <a class="btn"  type="button" onclick="lastStep();">上一步</a>
		    <%--<a class="btn"  type="button" onclick="nextStep('${url}');">下一步</a>--%>
		    <a class="btn"  type="button" onclick="nextStep();">下一步</a>
	      </div>
        </div>
      </div>
    </div>
  </body>
</html>

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
		<script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/merge_aptitude.js"></script>
		<style type="text/css">
		td {
		  cursor:pointer;
		}
		</style>
		<script type="text/javascript">
		  //默认不显示叉
		  $(function() {
        // 导航栏选中
        $("#reverse_of_three").attr("class","active");
        $("#reverse_of_three").removeAttr("onclick");
        $("td").each(function() {
        $(this).find("a").eq(0).hide();
		  });
		});
		
		// 获取旧的审核记录
    function getOldAudit(auditData){
    	var result = null;
    	$.ajax({
        url: "${pageContext.request.contextPath}/supplierAudit/ajaxOldAudit.do",
        type: "post",
        dataType: "json",
        data: auditData,
        async: false,
        success: function(data){
          result = data;
        }
      });
      return result;
    }
    
    // 撤销审核记录
    function cancelAudit(auditData){
    	var bool = false;
    	$.ajax({
        url: "${pageContext.request.contextPath}/supplierAudit/cancelAudit.do",
        type: "post",
        dataType: "json",
        data: auditData,
        async: false,
        success: function(result){
          if(result && result.status == 500){
          	bool = true;
          	layer.msg('撤销成功！');
          }
        }
      });
      return bool;
    }

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
		  var supplierStatus = $("input[name='supplierStatus']").val();
      var sign = $("input[name='sign']").val();
      //只有审核的状态能审核
      if(isAudit){
			  var supplierId=$("#supplierId").val();
			  var auditContent=str + "股东信息"; //审批的字段内容
			  var auditData = {
        		"supplierId": supplierId,
            "auditType": "basic_page",
            "auditField": id,
            "auditFieldName": "股东信息",
            "auditContent": auditContent
        };
        // 判断：新审核/可再次审核/不可再次审核
        // 获取旧的审核记录
        var result = getOldAudit(auditData);
        if(result && result.status == 0){
        	layer.msg('该条信息已审核过并退回过！');
     			return;
        }
        var defaultVal = "";
        var options = {
					title: '请填写不通过的理由：',
					value: defaultVal,
					formType: 2, 
					offset: '100px',
					maxlength: '100'
				};
        if(result && result.status == 1 && result.data){
        	defaultVal = result.data.suggest;
        	options.value = defaultVal;
        	options.btn = ['确定','撤销','取消'];
        	options.btn2 = function(index){
        		var bool = cancelAudit(auditData);
        		if(bool){
        			var icon = "<img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>";
               $("#" + id + "_hidden").html("").append(icon);
        		}
        	};
        	options.btn3 = function(index){layer.close(index);};
        }
				layer.prompt(options, function(value, index, elem){
		 			var text = trim(value);
				  if(text != null && text !=""){
				  	auditData.suggest = text;
				    $.ajax({
				      url: "${pageContext.request.contextPath}/supplierAudit/auditReasons.do",
				      type: "post",
				      //data: {"auditType":"basic_page","auditFieldName":"股东信息","auditContent":auditContent,"suggest":text,"supplierId":supplierId,"auditField":id},
				      data: auditData,
				      dataType: "json",
				      success: function(result){
                 if(result.status == "503"){
                   layer.msg('该条信息已审核并退回过！', {
                     shift: 6, //动画类型
                     offset:'100px'
                   });
                 }
                 if(result.status == "500"){
                   layer.msg('审核成功！', {
                     shift: 6, //动画类型
                     offset:'100px'
                   });
                   var icon = "<img src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>";
		              $("#" + id + "_hidden").html("").append(icon);
					        /* $("#"+id+"_hidden").hide();
						      $("#"+id+"_show").show(); */
                 }
            		}
			      });
						layer.close(index);
			    }else{
	      		layer.msg('不能为空！', {offset:'100px'});
	      	}
		    });
		  }
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

	  //删除左右两端的空格
		function trim(str){
			return str.replace(/(^\s*)|(\s*$)/g, "");
		}
			
		//暂存
    function zhancun(){
      var supplierId = $("#supplierId").val();
      $.ajax({
        url: "${pageContext.request.contextPath}/supplierAudit/temporaryAudit.do",
        dataType: "json",
        data: {supplierId : supplierId},
        success: function (result) {
          layer.msg(result, {offset : [ '100px' ]});
        },error: function(){
          layer.msg("暂存失败", {offset : [ '100px' ]});
        }
      });
    }
    </script>

		<script type="text/javascript">
			 /* function jump(str){
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
			  }* /
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
			}  */
		</script>

  </head>

  <body>
	  <!--面包屑导航开始-->
	  <div class="margin-top-10 breadcrumbs ">
          <div class="container">
              <ul class="breadcrumb margin-left-0">
                  <li>
                      <a> 首页</a>
                  </li>
                  <li>
                      <a  href="javascript:void(0)">支撑环境</a>
                  </li>
                  <li>
                      <a  href="javascript:void(0)">供应商管理</a>
                  </li>
                  <c:if test="${sign == 1}">
                      <li>
                          <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=1')">供应商审核</a>
                      </li>
                  </c:if>
                  <c:if test="${sign == 2}">
                      <li>
                          <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=2')">供应商复核</a>
                      </li>
                  </c:if>
                  <c:if test="${sign == 3}">
                      <li>
                          <a href="javascript:jumppage('${pageContext.request.contextPath}/supplierAudit/supplierAll.html?sign=3')">供应商实地考察</a>
                      </li>
                  </c:if>
              </ul>
          </div>
      </div>
    <div class="container container_box">
      <div class="content height-350">
        <div class="col-md-12 tab-v2 job-content">
          <%-- <%@include file="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp"%> --%>
          <jsp:include page="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp">
          	<jsp:param value="${supplierStatus }" name="supplierStatus"/>
          </jsp:include>
        <form id="form_id" action="" method="post" >
            <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
            <input id="status" name="supplierStatus" value="${supplierStatus}" type="hidden">
            <input type="hidden" name="sign" value="${sign}">
        </form>
        <ul class="ul_list count_flow">
        	<h5>出资人（股东）信息 （说明：出资人（股东）多于10人的，可以列出出资金额前十位的信息，但所列的出资比例应高于50%）</h5>
          <table class="table table-bordered table-condensed table-hover m_table_fixed_border">
            <thead>
		          <tr>
		            <th class="info w50">序号</th>
		            <th class="info" width="10%">出资人性质</th>
		            <th class="info">出资人名称或姓名</th>
		            <th class="info">证件类型</th>
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
		              <td class="tl" id="name_${s.id }" <c:if test="${fn:contains(field,s.id.concat('_name'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('name','${s.id}');"</c:if> >${s.name}</td>
		              <td class="tl" id="nature_${s.id }" >
                    <c:if test="${s.nature==1}">统一社会信用代码</c:if>
                    <c:if test="${s.nature==2}">居民二代身份证</c:if>
                  </td>
		              <td class="tc" id="identity_${s.id }" <c:if test="${fn:contains(field,s.id.concat('_identity'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('identity','${s.id}');"</c:if>>${s.identity}</td>
		              <td class="tc" id="shares_${s.id }" <c:if test="${fn:contains(field,s.id.concat('_shares'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('shares','${s.id}');"</c:if>>${s.shares}</td>
		              <td class="tc" id="proportion_${s.id }" <c:if test="${fn:contains(field,s.id.concat('_proportion'))}">style="border: 1px solid #FF8C00;" onMouseOver="showContent('proportion','${s.id}');"</c:if>>${s.proportion}</td>
		              <td class="tc w50">
	                	<%-- <a id="${s.id}_show"><img src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a> --%>
		                <c:if test="${!fn:contains(unableField,s.id)}">
		                	<p onclick="reason('${s.id}','${s.name}');" id="${s.id}_hidden" class="editItem">
		                		<%-- <c:if test="${!fn:contains(passedField,s.id)}"><img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'></c:if>
		                		<c:if test="${!fn:contains(passedField,s.id)}"><img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png' class="hidden"></c:if> --%>
		                		<c:if test="${!fn:contains(auditField,s.id)}">
	                        <img src='${pageContext.request.contextPath}/public/backend/images/light_icon.png'/>
	                      </c:if>
	                      <c:if test="${fn:contains(auditField,s.id)}">
	                        <img src='${pageContext.request.contextPath}/public/backend/images/light_icon_2.png'/>
	                      </c:if>
		                	</p>
		              	</c:if>
		              	<%-- <c:if test="${fn:contains(auditField,s.id)}">
		              		<img src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
		              	</c:if> --%>
		              	<c:if test="${fn:contains(unableField,s.id)}">
                      <img src='${pageContext.request.contextPath}/public/backend/images/sc.png' onclick="javascript:layer.msg('该条信息已审核并退回过！');"/>
                    </c:if>
		              </td>
	              </tr>
	            </c:forEach>
            </table>
          </ul>
        <div class="col-sm-12 col-xs-12 col-md-12 add_regist tc">
          
          <a class="btn"  type="button" onclick="lastStep();">上一步</a>
          <c:if test="${isStatusToAudit}">
            <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zhancun();">暂存</a>
          </c:if>
          <%--<a class="btn"  type="button" onclick="nextStep('${url}');">下一步</a>--%>
          <a class="btn"  type="button" onclick="nextStep();">下一步</a>
	      </div>
        </div>
      </div>
    </div>
  </body>
</html>

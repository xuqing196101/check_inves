<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
     <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		$(function() {
			var status = "${status}";
			if(status == 1 || status == 2){
				var packStatus = 0;
				$("input[name='packStatus']").each(function (){
					if($(this).val() == 5){
						packStatus++;
					}
				});
				if($("input[name='packId']").length == packStatus){
					ajaxComet();
					window.location.href = "${pageContext.request.contextPath}/packageAdvice/list.html";
				}
			}
		});
	
		function pass(code){
			var packId = [];
		 	$("input[name='packId']:checked").each(function() {
	      packId.push($(this).val());
	    });
	    if(packId.length > 0){
	    	$.ajax({
		 			url:"${pageContext.request.contextPath}/packageAdvice/pass.html",
		 			type:"post",
		 			dataType:"text",
		 			async: false,
		 			data:{
		 				"projectId" : "${advice.project.id}",
		 				"code" : code,
		 				"packId" : packId.join(",")
		 			},
		 			success:function(data){
		 				if(data == "ok"){
		 					layer.msg("审核通过");
		 					if($("input[name='packId']").length == packId.length){
		 						ajaxComet();
		 					} else {
		 						window.location.href = "${pageContext.request.contextPath}/packageAdvice/audit.html?code=" + code + "&status=2";
		 					}
		 				} else {
		 					layer.msg("失败");
		 				}
		 			}
		  	});
	    } else {
	    	layer.msg("请选择要评审的包");
	    }
		}
		
		function ajaxComet(){
			$.ajax({
	 			url:"${pageContext.request.contextPath}/packageAdvice/comet.do",
	 			type:"post",
	 			dataType:"text",
	 			async: false,
	 			data:{
	 				"projectId" : "${advice.project.id}",
	 				"proposer" : "${advice.proposer}",
	 				"code" : "${advice.code}"
	 			},
	 			success:function(data){
	 				if(data=="ok"){
	 					window.location.href = "${pageContext.request.contextPath}/packageAdvice/list.html";
	 				}
	 			}
	 			
			});
		}
		function noPass(code){
			var packId = [];
		 	$("input[name='packId']:checked").each(function() {
	      packId.push($(this).val());
	    });
	    if(packId.length > 0){
	    	var removedReason = layer.prompt({
			    title : '请填写不通过理由：', 
			    formType : 2, 
			    maxlength: 300,
				},function(text){
					$.ajax({
			 			url:"${pageContext.request.contextPath}/packageAdvice/noPass.do",
			 			type:"post",
			 			dataType:"text",
			 			async: false,
			 			data:{
			 				"projectId" : "${advice.project.id}",
			 				"code" : code,
			 				"removedReason" : text,
			 				"packId" : packId.join(",")
			 			},
			 			success:function(data){
			 				if(data == "ok"){
			 					layer.msg("成功");
				 				if($("input[name='packId']").length == packId.length){
				 					ajaxComet();
			 					} else {
			 						window.location.href = "${pageContext.request.contextPath}/packageAdvice/audit.html?code=" + code + "&status=2";
			 					}
			 				} else {
			 					layer.msg("失败");
			 				}
			 			}
			  	});
				});
	    } else {
	    	layer.msg("请选择要评审的包");
	    }
		}
		
		function goBack(){
			window.location.href = "${pageContext.request.contextPath}/packageAdvice/list.html";
		}
	</script>
  </head>
  
  <body>
  	<div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">保障作业系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购项目管理</a>
          </li>
          <li class="active">
            <a href="javascript:jumppage('${pageContext.request.contextPath}/packageAdvice/list.html')">转竞谈审核</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">转竞谈审核</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container container_box">
    	<div>
          <h2 class="count_flow"><i>1</i>项目基本信息</h2>
          <div class="ul_list mb0 p20">
            <table class="table table-bordered m0">
              <tbody>
                <tr>
                  <td width="10%" class="info">项目名称：</td>
                  <td width="25%">${advice.project.name}</td>
                  <td width="10%" class="info">项目编号：</td>
                  <td width="25%">${advice.project.projectNumber}</td>
                </tr>
                <tr>
                  <td class="info">采购机构：</td>
                  <td>${advice.project.purchaseDepName}</td>
                  <td class="info">项目状态：</td>
                  <td>${advice.project.status}</td>
                </tr>
                <tr>
                  <td class="info">项目负责人：</td>
                  <td>${advice.project.principal}</td>
                  <td class="info">负责人电话：</td>
                  <td>${advice.project.ipone}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="clear"></div>
        <div id="clear">
          <h2 class="count_flow"><i>2</i>转竞谈谈判信息</h2>
          <div class="ul_list mb0 p20">
            <table class="table table-bordered m0">
              <tbody>
                <tr>
                  <td class="info tc w160">项目预算（万元）：</td>
                  <td class="tc">${advice.budget}</td>
                  <td class="info tc w160">附件：</td>
                  <td class="tc"><u:show showId="show1_${advice.project.id}" delete="false" businessId="${advice.code}" sysKey="2" typeId="${auditJZXTP}"/></td>
                  <%-- <td width="16%" class="info tc">状态：</td>
                  <td width="16%" class="tc">
                    <c:if test="${advice.status == 1}">待审核</c:if>
										<c:if test="${advice.status == 2}">审核中</c:if>
										<c:if test="${advice.status == 3}">审核通过</c:if>
										<c:if test="${advice.status == 4}">审核不通过</c:if>
                  </td> --%>
                </tr>
                <tr>
                  <td class="info tc">转竞谈谈判原因：</td>
                  <td colspan="3" class="no_break">${advice.advice}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="clear"></div>
        </div>
        <div id="clear">
          <h2 class="count_flow"><i>3</i>转竞谈包信息</h2>
          <div class="ul_list mb0 p20">
          	<c:forEach items="${advice.packageList}" var="obj">
          		<input type="hidden" value="${obj.status}" name="packStatus"/>
          		<input type="checkbox" value="${obj.id}" <c:if test="${obj.status == 5}">disabled="disabled"</c:if> name="packId"/>${obj.name}
          	</c:forEach>
          </div>
          <div class="clear"></div>
        </div>
        <div class="mt10 tc">
        	<c:if test="${status == 1 || status == 2}">
	        	<button class="btn" onclick="pass('${advice.code}')" type="button">审核通过</button>
	        	<button class="btn" onclick="noPass('${advice.code}')" type="button">审核不通过</button>
        	</c:if>
          <button class="btn btn-windows back" onclick="goBack()" type="button">返回</button>
        </div>
    </div>
  </body>
</html>

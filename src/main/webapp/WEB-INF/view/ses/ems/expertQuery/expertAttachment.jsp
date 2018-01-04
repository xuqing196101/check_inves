<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
   <%@ include file="/WEB-INF/view/common.jsp" %>
   <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
   <%@ include file="/WEB-INF/view/ses/ems/expertQuery/common.jsp"%>
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertQuery/merge_jump.js"></script>
    <script type="text/javascript">
     function pass(id,typeId) {
    	if($("#passButton"+id).attr("class")=="btn"){
    		statusIsNull(id,typeId);
    		 return;
    	}
    	if($(".bgred").size()==0){
			$("#fchg").attr("disabled",false);
        	$("#zlbq").attr("disabled",false);
		};
    	var expertId=$("#expertId").val();
    	var finalInspectNumber=$("#finalInspectNumber").val();
		 $.ajax({
             url: "${pageContext.request.contextPath}/finalInspect/updateExpertFinalInspect.do",
             data: {
                 "expertId": expertId,
                 "fileId":id,
                 "finalInspectNumber":finalInspectNumber,
                 "status":"1",
                 "fileType":typeId
             },
             type: "POST",
            // async: false,
             //dataType: "json",
            success: function(result) {
            	$("#s_"+id).val(1);
            	$("#passButton"+id).attr("class","btn");
        		$("#notPassButton"+id).attr("class","btn bgdd black_link");
			}
         });
	 }
     function notPass(id,typeId) {
    	if($("#notPassButton"+id).attr("class")=="btn bgred"){
    		if($(".bgred").size()==0){
    			$("#fchg").attr("disabled",false);
            	$("#zlbq").attr("disabled",false);
    		};
    		statusIsNull(id,typeId);
    		return;
    	}
    	var expertId=$("#expertId").val();
    	var finalInspectNumber=$("#finalInspectNumber").val();
		 $.ajax({
             url: "${pageContext.request.contextPath}/finalInspect/updateExpertFinalInspect.do",
             data: {
                 "expertId": expertId,
                 "fileId":id,
                 "finalInspectNumber":finalInspectNumber,
                 "status":"2",
                 "fileType":typeId
             },
             type: "POST",
            // async: false,
             //dataType: "json",
            success: function(result) {
            	$("#s_"+id).val(2);
            	$("#notPassButton"+id).attr("class","btn bgred");
            	$("#passButton"+id).attr("class","btn bgdd black_link");
            	$("#fchg").attr("disabled","disabled");
            	$("#zlbq").attr("disabled","disabled");
			}
         });
	 }
     function updReason(id,typeId) {
    	 var expertId=$("#expertId").val();
     	 var finalInspectNumber=$("#finalInspectNumber").val();
    	 var reason=$("#reason"+id).val();
    	 $.ajax({
             url: "${pageContext.request.contextPath}/finalInspect/updateExpertFinalInspect.do",
             data: {
                 "expertId": expertId,
                 "fileId":id,
                 "finalInspectNumber":finalInspectNumber,
                 "reason":reason,
                 "fileType":typeId
             },
             type: "POST",
            // async: false,
             //dataType: "json",
            success: function(result) {
            	
			}
         });
	}
     function statusIsNull(id,typeId) {
    	 var expertId=$("#expertId").val();
     	 var finalInspectNumber=$("#finalInspectNumber").val();
    	 $.ajax({
             url: "${pageContext.request.contextPath}/finalInspect/updateExpertFinalInspectStatusIsNull.do",
             data: {
                 "expertId": expertId,
                 "fileId":id,
                 "finalInspectNumber":finalInspectNumber,
                 "fileType":typeId
             },
             type: "POST",
            // async: false,
             //dataType: "json",
            success: function(result) {
            	$("#s_"+id).val("");
            	$("#notPassButton"+id).attr("class","btn bgdd black_link");
            	$("#passButton"+id).attr("class","btn bgdd black_link");
			}
         });
	}
     function updStatus() {
    	 var status = $(":radio:checked").val();
    	 var expertId=$("#expertId").val();
     	 var finalInspectNumber=$("#finalInspectNumber").val();
    	 $.ajax({
             url: "${pageContext.request.contextPath}/finalInspect/auditOpinion.do",
             data: {
                 "expertId": expertId,
                 "flagTime":"2",
                 "flagAudit":status,
                 "count":finalInspectNumber
             },
             type: "POST",
            // async: false,
             //dataType: "json",
            success: function() {
            	if(status==7){
            		$("#check_opinion").html("复查合格。");
            	}else if(status==8){
            		$("#check_opinion").html("复查不合格。");
            	}else if(status==17){
            		$("#check_opinion").html("资料不全。");
            	}
			}
    	 });
	}
     function updExpertReason() {
    	 var status = $(":radio:checked").val();
    	 var expertId=$("#expertId").val();
     	 var finalInspectNumber=$("#finalInspectNumber").val();
     	 var reason = document.getElementById('opinion').value;
     	 if(status!=null){
     		if(status==7){
     			reason="复查合格。"+reason;
        	}else if(status==8){
        		reason="复查不合格。"+reason;
        	}else if(status==17){
        		reason="资料不全。"+reason;
        	}
     	 }
    	 $.ajax({
             url: "${pageContext.request.contextPath}/finalInspect/auditOpinion.do",
             data: {
                 "expertId": expertId,
                 "flagTime":"2",
                 "opinion":reason,
                 "count":finalInspectNumber
             },
             type: "POST",
            // async: false,
             //dataType: "json",
            success: function() {
            	
			}
    	 });
	}
     function over() {
    	var s=true;
		$(".red").each(function(){
			if($(this).next().val()==null || $(this).next().val()==""){
				s=false;
			}
		});
		if(!s){
			layer.alert("红色字体为必要复核项，必须选择是否一致！", {offset: '100px'});
			return;
		}
		$(".bgred").each(function(){
			if($(this).parent("td").next().children("input").val()==null || $(this).parent("td").next().children("input").val()==""){
				s=false;
			}
		});
		if(!s){
			layer.alert("不一致项必须填写理由！", {offset: '100px'});
			return;
		}
		var status = $(":radio:checked").val();
		if(status==null){
			layer.alert("请选择复查意见！", {offset: '100px'});
			return;
		}if(status==8 || status==17){
			var reason = document.getElementById('opinion').value;
			if(reason==null || reason ==""){
				s=false;
			}
		}
		if(!s){
			layer.alert("必须填写复查理由！", {offset: '100px'});
			return;
		}
		 var expertId=$("#expertId").val();
		 var finalInspectNumber=$("#finalInspectNumber").val();
		 $.ajax({
             url: "${pageContext.request.contextPath}/finalInspect/over.do",
             data: {
                 "expertId": expertId,
                 "count":finalInspectNumber
             },
             type: "POST",
            // async: false,
             //dataType: "json",
            success: function(e) {
            	 $("#over").val("over");
            	 $("#form_id").attr("action", "${pageContext.request.contextPath}/finalInspect/expertAttachment.html");
            	 $("#form_id").submit();
			}
    	 });
	}
     function dwonExpertFcTable() {
    	 $("#downloadForm").attr("action", "${pageContext.request.contextPath}/finalInspect/downloadExpertFinalInspect.html");
         $("#downloadForm").submit();
	}
    </script>

  </head>

  <body>
  <!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0)">支撑系统</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家管理</a>
					</li>
					<li>
						<c:if test="${sign == 1}">
							<a  href="javascript:jumppage('${pageContext.request.contextPath}/expert/findAllExpert.html')">全部专家查询</a>
						</c:if>
						<c:if test="${sign == 2}">
							<a  href="javascript:jumppage('${pageContext.request.contextPath}/expertQuery/list.html')">入库专家查询</a>
						</c:if>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container container_box pb70">
			<div class=" content">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="nav nav-tabs bgwhite">
						<li class="">
							<a aria-expanded="true" href="#tab-1" data-toggle="tab" class="f18" onclick="jump('basicInfo');">基本信息</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('expertType');">专家类别</a>
						</li>
						<li class="">
							<a aria-expanded="fale" href="#tab-3" data-toggle="tab" class="f18" onclick="jump('product');">参评类别</a>
						</li>
						<li class="">
							<a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('expertFile');">承诺书和申请表</a>
						</li>
						<li class="">
              <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('auditInfo');">采购机构初审意见</a>
            </li>
						<li class="">
              <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="jump('review');">专家复审意见</a>
            </li>
            <c:if test="${expert.finalInspectCount>0}">
	    		<c:forEach var="i" begin="1" end="${expert.finalInspectCount}" step="1">
						<li <c:if test="${notCount eq i}">class="active"</c:if>>
			              <a aria-expanded="false" href="#tab-2" data-toggle="tab" class="f18" onclick="tojump('expertAttachment',${i});">采购机构复查意见</a>
			            </li>
            	</c:forEach>
            </c:if>
					</ul>
					<div class="padding-top-10">
						 <h2 class="count_flow"><i>1</i>专家复查</h2>
						 <table class="table table-bordered table-hover mb0">
						    <tr>
						      <td class="info tc w50">序号</td>
						      <td class="info tc w100">项目</td>
						      <td class="info tc">扫描件</td>
						      <td class="info tc">原件与扫描件是否一致</td>
						      <td class="info tc">理由</td>
						    </tr>
						    <c:forEach items="${list}" var="list" varStatus="vs">
						      <tr>
							      <td class="tc">${vs.index + 1}</td>
								    <td class="tc">
								    <span
								     <c:if test="${list.typeId==3 ||list.typeId==2 ||list.typeId==12 ||list.typeId==4 ||list.typeId==6 ||list.typeId==8 ||list.typeId==7 ||list.typeId==9 }">class="red"</c:if>
								    >${list.name}</span>
								    <input id="s_${list.id}" calss="status" type="hidden" value="${list.status}"/>
								    </td>
								    <td class="tc pl20">
								    	<u:show showId="${vs.index + 1}"  delete="false" businessId="${list.businessId}" sysKey="${expertKey}" typeId="${list.typeId}" />
								    </td>
								    <td class="tc pl20" >
								    	<button type="button" id="passButton${list.id}" 
								    	<c:if test="${list.status==1}">class="btn"</c:if>
								    	<c:if test="${list.status!=1}">class="btn bgdd black_link"</c:if>
								    	disabled="disabled"
								    	onclick="pass('${list.id}','${list.typeId}')">一致</button> | 
								    	<button type="button" id="notPassButton${list.id}" 
								    	<c:if test="${list.status==2}">class="btn bgred"</c:if>
								    	<c:if test="${list.status!=2}">class="btn bgdd black_link"</c:if>  
								    	disabled="disabled"
								    	onclick="notPass('${list.id}','${list.typeId}')">不一致</button>
								    </td>
								    <td class="tl pl20">
										<input type="text" id="reason${list.id}" class="w100p mb0" value="${list.reason}" onblur="updReason('${list.id}','${list.typeId}')" disabled="disabled">
									</td>
						      </tr>
						    </c:forEach>
						  </table> 
					</div>
					<div class="clear"></div>
					<div class="padding-top-10">
						<h2 class="count_flow"><i>2</i>复查意见</h2>
						 <ul class="ul_list">
		                   <li>
		                   <div class="select_check">
		                      <input type="radio" id="fchg" <c:if test="${auditOpinion.flagAudit eq '7'}">checked</c:if> name="status" value="7" onclick = "updStatus()" disabled="disabled">复查合格
		                      <input type="radio"  id="fcbhg" <c:if test="${auditOpinion.flagAudit eq '8'}">checked</c:if> name="status" value="8" onclick = "updStatus()" disabled="disabled">复查不合格
		                      <input type="radio"  id="zlbq" <c:if test="${auditOpinion.flagAudit eq '17'}">checked</c:if> name="status" value="17" onclick = "updStatus()" disabled="disabled">资料不全
		                    </div>
		                  </li>
		                  <li>
		                   <div id="check_opinion">
		                   		<c:if test="${auditOpinion.flagAudit eq '7'}">复查合格。</c:if>
		                   		<c:if test="${auditOpinion.flagAudit eq '8'}">复查不合格。</c:if>
		                   		<c:if test="${auditOpinion.flagAudit eq '17'}">资料不全。</c:if>
		                   </div>
		                 </li>
		                  <li class="mt10">
		                     <textarea id="opinion" class="col-md-12 col-xs-12 col-sm-12 h80" onblur="updExpertReason()" disabled="disabled">${auditOpinion.opinion }</textarea>
		                  </li>
		                </ul>
					</div>
					<div class="clear"></div>
					<div class="padding-top-10">
						<h2 class="count_flow"><i>3</i>专家复查表</h2>
						<ul class="ul_list">
		                   <li>
		                   	    <span class="fl">专家复查表：</span>
		                		<u:show showId="upfcTableFlie" businessId="${auditOpinion.id}" sysKey="${expertKey}" delete="false" typeId="${typeId}" />
		                   </li>
		                </ul>
					</div>
				</div>
			</div>
		</div>
		<div class="add_regist tc m_btn_ab">
			<c:if test="${sign == 1}">
				<a class="btn btn-windows reset" href="${pageContext.request.contextPath}/expert/findAllExpert.html">返回列表</a>
			</c:if>
			<c:if test="${sign == 2}">
				<a class="btn btn-windows reset" href="${pageContext.request.contextPath}/expertQuery/list.html">返回列表</a>
			</c:if>
	    </div>
 	<input id="status" value=" ${expert.status}" type="hidden">
	<form id="form_id" action="" method="post">
			<input name="expertId" value="${expert.id}" type="hidden">
			<input id="sign" name="sign" value="${sign}" type="hidden">
			<input name="status" value="${status}" type="hidden">
			<input id="finalInspectNumber" name="finalInspectNumber" value="" type="hidden">
	</form>
</body>

</html>
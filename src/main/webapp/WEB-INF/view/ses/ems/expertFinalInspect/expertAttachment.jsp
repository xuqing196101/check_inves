<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>

  <head>
   <%@ include file="/WEB-INF/view/common.jsp" %>
   <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
   <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertFinalInspect/merge_jump.js"></script>
    <script type="text/javascript">
     function pass(id,typeId) {
    	if($("#passButton"+id).attr("class")=="btn"){
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
     	    	if($(".bgred").size()==0){
     				$("#fchg").attr("disabled",false);
     	        	$("#zlbq").attr("disabled",false);
     			};
			}
         });
		
	 }
     function notPass(id,typeId) {
    	if($("#notPassButton"+id).attr("class")=="btn bgred"){
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
            	if($(".bgred").size()==0){
        			$("#fchg").attr("disabled",false);
                	$("#zlbq").attr("disabled",false);
        		};
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
     var indexRecheck;
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
		var status = $(":radio:checked").val();
		if(status=="17"){
			indexRecheck = layer.open({
		  	    shift: 1, //0-6的动画形式，-1不开启
		  	    moveType: 1, //拖拽风格，0是默认，1是传统拖动
		  	    title: ['资料不全确认','border-bottom:1px solid #e5e5e5'],
		  	    shade:0.01, //遮罩透明度
			  		type : 1,
			  		area : [ '30%', '250px'  ], //宽高
			  		content : $('#openDiv'),
			});
		}else{
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
	            	 $("#form_id").attr("action", "${pageContext.request.contextPath}/finalInspect/expertAttachment.html");
	            	 $("#form_id").submit();
				}
	    	 });
		}
		
	}
     function confirm(){
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
            	 $("#form_id").attr("action", "${pageContext.request.contextPath}/finalInspect/expertAttachment.html");
            	 $("#form_id").submit();
			}
    	 });
     }
     function cancel(){
    	 layer.close(indexRecheck);
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
					<c:if test="${sign == 1}">
						<li>
							<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=1')">专家初审</a>
						</li>
					</c:if>
					<c:if test="${sign == 2}">
						<li>
							<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAgainAudit/findBatchDetailsList.html?batchId=${batchId}')">专家复审</a>
						</li>
					</c:if>
					<c:if test="${sign == 3}">
						<li>
							<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=3')">专家复查</a>
						</li>
					</c:if>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container container_box pb70">
			<div class=" content">
				<div class="col-md-12 tab-v2 job-content">
					<%@include file="/WEB-INF/view/ses/ems/expertFinalInspect/common_jump.jsp" %>
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
								    	<c:if test="${(over!=null&& over!='')||expert.status=='7' || expert.status=='8' || (expert.status=='17'&&notCount<=expert.finalInspectCount)}">disabled="disabled"</c:if>
								    	onclick="pass('${list.id}','${list.typeId}')">一致</button> | 
								    	<button type="button" id="notPassButton${list.id}" 
								    	<c:if test="${list.status==2}">class="btn bgred"</c:if>
								    	<c:if test="${list.status!=2}">class="btn bgdd black_link"</c:if>  
								    	<c:if test="${(over!=null&& over!='')||expert.status=='7' || expert.status=='8' || (expert.status=='17'&&notCount<=expert.finalInspectCount)}">disabled="disabled"</c:if>
								    	onclick="notPass('${list.id}','${list.typeId}')">不一致</button>
								    </td>
								    <td class="tl pl20">
										<input type="text" id="reason${list.id}" class="w100p mb0" value="${list.reason}" onblur="updReason('${list.id}','${list.typeId}')" <c:if test="${(over!=null&& over!='')||expert.status=='7' || expert.status=='8' || (expert.status=='17'&&notCount<=expert.finalInspectCount)}">disabled="disabled"</c:if>>
									</td>
						      </tr>
						    </c:forEach>
						  </table> 
					</div>
					<div class="clear"></div>
					<c:if test="${over==null||over==''}">
						<div class="padding-top-10">
							<h2 class="count_flow"><i>2</i>复查意见</h2>
							 <ul class="ul_list">
			                   <li>
			                   <div class="select_check">
			                      <input type="radio" id="fchg" <c:if test="${auditOpinion.flagAudit eq '7'}">checked</c:if> name="status" value="7" onclick = "updStatus()" <c:if test="${(over!=null&& over!='')||qualified eq 'false'||expert.status=='7' || expert.status=='8' || (expert.status=='17'&&notCount<=expert.finalInspectCount)}">disabled="disabled"</c:if>>复查合格
			                      <input type="radio"  id="fcbhg" <c:if test="${auditOpinion.flagAudit eq '8'}">checked</c:if> name="status" value="8" onclick = "updStatus()" <c:if test="${(over!=null&& over!='')||expert.status=='7' || expert.status=='8' || (expert.status=='17'&&notCount<=expert.finalInspectCount)}">disabled="disabled"</c:if>>复查不合格
			                      <input type="radio"  id="zlbq" <c:if test="${auditOpinion.flagAudit eq '17'}">checked</c:if> name="status" value="17" onclick = "updStatus()" <c:if test="${(over!=null&& over!='')||qualified eq 'false'||expert.status=='7' || expert.status=='8' || (expert.status=='17'&&notCount<=expert.finalInspectCount)}">disabled="disabled"</c:if>>资料不全
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
			                     <textarea id="opinion" class="col-md-12 col-xs-12 col-sm-12 h80" onblur="updExpertReason()" <c:if test="${(over!=null&& over!='')||expert.status=='7' || expert.status=='8' || (expert.status=='17'&&notCount<=expert.finalInspectCount)}">disabled="disabled"</c:if>>${auditOpinion.opinion }</textarea>
			                  </li>
			                </ul>
						</div>
					</c:if>
					<c:if test="${over!=null&&over!=''}">
						<div class="padding-top-10">
							<h2 class="count_flow"><i>2</i>复查意见</h2>
							 <ul class="ul_list">
			                  <li>
			                   <div id="check_opinion">
			                   		<c:if test="${auditOpinion.flagAudit eq '7'}">复查合格。</c:if>
			                   		<c:if test="${auditOpinion.flagAudit eq '8'}">复查不合格。</c:if>
			                   		<c:if test="${auditOpinion.flagAudit eq '17'}">资料不全。</c:if>
			                   		${auditOpinion.opinion }
			                   </div>
			                 </li>
			                </ul>
						</div>
					</c:if>
					<div class="clear"></div>
					<div class="padding-top-10">
						<h2 class="count_flow"><i>3</i>下载专家复查表</h2>
						<ul class="ul_list">
		                   <li>
		                		<button class="btn" onclick="dwonExpertFcTable()">下载专家复查表</button>
		                   </li>
		                </ul>
					</div>
					<div class="clear"></div>
					<div id="upfcTable" class="padding-top-10" <c:if test="${expert.status!='7' && expert.status!='8' && expert.status!='17' }">hidden</c:if>><!-- &&( auditOpinion.flagAudit ==null || auditOpinion.flagAudit =='') -->
						<h2 class="count_flow"><i>4</i>上传专家复查表</h2>
						<ul class="ul_list">
		                   <li>
		                		<u:upload id="upfcTableFile" businessId="${auditOpinion.id}" sysKey="${expertKey}" typeId="${typeId}" buttonName="上传专家复查表" auto="true" multiple="true"/>
        						<u:show showId="upfcTableFlie" businessId="${auditOpinion.id}" sysKey="${expertKey}"  typeId="${typeId}" />
		                   </li>
		                </ul>
					</div>
				</div>
			</div>
		</div>
		<div class="add_regist tc m_btn_ab">
			<c:if test="${notCount<expert.finalInspectCount||notCount==1}"><a class="btn mb0 mr0" type="button" onclick="jump('expertFile')">上一步</a></c:if>
			<c:if test="${notCount>=expert.finalInspectCount&&notCount!=1}"><a class="btn mb0 mr0" type="button" onclick="tojump('expertAttachment',${notCount-1})">上一步</a></c:if>
	        <c:if test="${notCount<=expert.finalInspectCount && over==null}">
	        	<a class="btn" type="button" onclick="tojump('expertAttachment',${notCount+1})">下一步</a>
	        </c:if>
	        <c:if test="${notCount>expert.finalInspectCount}">
		        <a class="btn" type="button" onclick="" <c:if test="${expert.status=='7' || expert.status=='8' || (expert.status=='17'&&notCount<=expert.finalInspectCount)}">disabled="disabled"</c:if>>暂存</a>
		        <a class="btn" type="button" onclick="over()" <c:if test="${expert.status=='7' || expert.status=='8' || (expert.status=='17'&&notCount<=expert.finalInspectCount)}">disabled="disabled"</c:if>>复查结束</a>
	        </c:if>
	    </div>
 	<input id="status" value=" ${expert.status}" type="hidden">
	<form id="form_id" action="" method="post">
	    <input id="expertId" name="expertId" value="${expert.id}" type="hidden">
	    <input name="sign" value="${sign}" type="hidden">
	    <input name="batchId" value="${batchId}" type="hidden">
	    <input name="isReviewRevision" value="${isReviewRevision}" type="hidden">
	    <input name="isCheck" value="${isCheck}" type="hidden">
	    <input id="finalInspectNumber" name="finalInspectNumber" value="${expert.finalInspectCount+1}" type="hidden">
	    <input id="over" name="over" value="${over}" type="hidden">
	    <input id="notCount" name="notCount" value="${notCount}" type="hidden">
	</form> 
	<form id="downloadForm" action="" method="post">
          <input name="expertId" value="${expert.id}" type="hidden" />
          <input name=count value="${notCount}" type="hidden">
    </form>	
     <div id="openDiv" class="dnone layui-layer-wrap p20">
    	<div class="tl ">
    		已记录资料不全次数:${expert.finalInspectCount}</br></br>
    		
    		当被记录资料不全达3次时,系统将直接判定该专家为"复查不合格",是否确认记录该专家本次复查"资料不全"?
    	</div>
      <div class="tc mt50">
        <input class="btn" type="button" onclick="confirm();" value="确认"> 
        <input class="btn" type="button" onclick="cancel();" value="取消"> 
      </div>
      <div class="clear"></div>
    </div>
</body>

</html>
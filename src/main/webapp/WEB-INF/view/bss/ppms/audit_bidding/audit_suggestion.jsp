<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<script type="text/javascript">
  	function ycDiv(obj, index) {
    	  if ($(obj).hasClass("shrink") && !$(obj).hasClass("spread")) {
              $(obj).removeClass("shrink");
              $(obj).addClass("spread");
            } else {
              if ($(obj).hasClass("spread") && !$(obj).hasClass("shrink")) {
                $(obj).removeClass("spread");
                $(obj).addClass("shrink");
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
</script>
</head>

<body>
    <c:if test="${type != '1'}">
  	<div class="col-md-12 p0">
	   <ul class="flow_step">
		     <li>
			   <a  href="${pageContext.request.contextPath}/firstAudit/toAdd.html?projectId=${project.id}&flowDefineId=${flowDefineId}" >01、资格性和符合性审查</a>
			   <i></i>
			 </li>
		     <li>
			   <a  href="${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${project.id}&flowDefineId=${flowDefineId}">02、经济和技术评审细则</a>
			   <i></i>
			 </li>
			 <li>
			   <a  href="${pageContext.request.contextPath}/open_bidding/bidFile.html?id=${project.id}&flowDefineId=${flowDefineId}" >
			     03、采购文件
		         <%-- <c:if test="${project.dictionary.code eq 'GKZB' }">
			     03、采购文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'XJCG' }">
			     03、询价文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'YQZB' }">
			     03、采购文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'JZXTP' }">
			     03、竞谈文件
			     </c:if>
			     <c:if test="${project.dictionary.code eq 'DYLY' }">
			     03、单一来源文件
			     </c:if> --%>
			   </a>
			   <i></i>
			 </li>
			 <li class="active">
			   <a  href="${pageContext.request.contextPath}/Auditbidding/viewAudit.html?projectId=${project.id}&flowDefineId=${flowDefineId}">04、审核意见</a>
			 </li>
		</ul>
	 </div>
	 </c:if>
     <h2 class="list_title">
             审核结果：
	 <c:if test="${project.confirmFile == 0}">采购文件未提交</c:if>
	 <c:if test="${project.confirmFile == 1}">暂无审核结果</c:if>
	 <c:if test="${project.confirmFile == 2}">审核退回</c:if>
	 <c:if test="${project.confirmFile == 3}">审核通过</c:if>
	 <c:if test="${project.confirmFile == 4}">修改报备</c:if>
	 </h2>
	<h2 onclick="ycDiv(this,'1')" class="count_flow spread hand">采购管理部门意见</h2>
	<div class="p01 p0_40 clearfix">${reasons.pcReason}
		<div class="clear mt10">
			<span class="fl"><b>采购管理部门审核意见附件:</b></span>
				<c:if test="${project.confirmFile == 3}">
		    <u:show  showId="e" delete="flase" businessId="${project.id}" sysKey="${sysKey}" typeId="${pcTypeId}"/>
		    </c:if>
		</div>
	</div>
	<h2 onclick="ycDiv(this,'2')" class="count_flow spread hand">事业部门意见</h2>
	<div class="p02 p0_40 clearfix">${reasons.causeReason}
		<div class="clear mt10">
			<span class="fl"><b>事业部门审核意见附件:</b></span>
				<c:if test="${project.confirmFile == 3}">
		    <u:show delete="flase"  showId="y" businessId="${project.id}" sysKey="${sysKey}" typeId="${causeTypeId}"/>
		    </c:if>
		</div>
	</div>
	<h2 onclick="ycDiv(this,'3')" class="count_flow spread hand">财务部门意见</h2>
	<div class="p03 p0_40 clearfix">${reasons.financeReason}
		<div class="clear mt10">
			<span class="fl"><b>财务部门审核意见附件:</b></span>
				<c:if test="${project.confirmFile == 3}">
		    <u:show  showId="o" delete="flase" businessId="${project.id}" sysKey="${sysKey}" typeId="${financeTypeId}"/>
		    </c:if>
		</div>
	</div>
	<h2 onclick="ycDiv(this,'4')" class="count_flow spread hand">最终意见</h2>
	<c:if test="${project.confirmFile == 3}">
	<div class="p04 p0_40 clearfix">${reasons.finalReason}</div>
	</c:if>
</body>
</html>

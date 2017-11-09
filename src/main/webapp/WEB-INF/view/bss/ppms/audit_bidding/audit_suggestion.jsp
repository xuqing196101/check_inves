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
  	<h2 class="list_title">
             审核结果：
	 <c:if test="${project.confirmFile == 0}">采购文件未提交</c:if>
	 <c:if test="${project.confirmFile == 1}">暂无审核结果</c:if>
	 <c:if test="${project.confirmFile == 2}">审核退回</c:if>
	 <c:if test="${project.confirmFile == 3}">审核通过</c:if>
	 <c:if test="${project.confirmFile == 4}">修改报备</c:if>
	 </h2>
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
		   <a  href="${pageContext.request.contextPath}/open_bidding/projectApproval.html?projectId=${project.id}&flowDefineId=${flowDefineId}">03、报批说明、审批单</a>
		   <i></i>
		 </li>
		 <li>
						   <a  href="${pageContext.request.contextPath}/open_bidding/projectView.html?projectId=${project.id}&flowDefineId=${flowDefineId}">04、评审项预览</a>
						   <i></i>
						 </li>
			 <li>
			   <a  href="${pageContext.request.contextPath}/open_bidding/bidFile.html?id=${project.id}&flowDefineId=${flowDefineId}" >
			     05、采购文件
			   </a>
			   <i></i>
			 </li>
			 <li class="active">
			   <a  href="${pageContext.request.contextPath}/Auditbidding/viewAudit.html?projectId=${project.id}&flowDefineId=${flowDefineId}">06、审核意见</a>
			 </li>
		</ul>
	 </div>
	 </c:if>
	 
	 <div class="clear"></div>
	 <div class="bggrey p20_15 mt20">
	   <div class="fw f14" id="cgspan">采购管理部门意见</div>
     <div class="mt10">
       <textarea class="w100p h80 resizen" disabled="disabled">${MapPa['pcId'].content}</textarea>
       <div class="m_uploadFiles mt10">
         <span class="m_inline f14 lh16">采购管理部门审核意见附件：</span>
         <div class="m_inline m_uploadFiles f0">
           <u:show showId="e" delete="flase" businessId="${MapPa['pcId'].id}" sysKey="${sysKey}" typeId="${pcTypeId}"/>
         </div>
       </div>
     </div>
     
     <div class="mt20 fw f14" id="cgspan">事业部门意见</div>
     <div class="mt10">
       <textarea class="w100p h80 resizen" disabled="disabled">${MapPa['causeId'].content}</textarea>
       <div class="m_uploadFiles mt10">
         <span class="m_inline f14 lh16">事业部门审核意见附件：</span>
         <div class="m_inline m_uploadFiles f0">
           <u:show delete="flase" showId="y" businessId="${MapPa['causeId'].id}" sysKey="${sysKey}" typeId="${causeTypeId}"/>
         </div>
       </div>
     </div>

     <div class="mt20 fw f14" id="cgspan">财务部门意见</div>
     <div class="mt10">
       <textarea class="w100p h80 resizen" disabled="disabled">${MapPa['financeId'].content}</textarea>
       <div class="m_uploadFiles mt10">
         <span class="m_inline f14 lh16">财务部门审核意见附件：</span>
         <div class="m_inline m_uploadFiles f0">
           <u:show showId="o" delete="flase" businessId="${MapPa['financeId'].id}" sysKey="${sysKey}" typeId="${financeTypeId}"/>
         </div>
       </div>
     </div>

     <div class="mt20 fw f14" id="cgspan">最终意见</div>
     <div class="mt10">
       <textarea class="w100p h80 resizen" disabled="disabled">${MapPa['finalId'].content}</textarea>
       <div class="m_uploadFiles mt10">
         <span class="m_inline f14 lh16">最终意见附件：</span>
         <div class="m_inline m_uploadFiles f0">
           <u:show showId="kk" delete="flase" businessId="${MapPa['finalId'].id}" sysKey="${sysKey}" typeId="${finalTypeId}"/>
         </div>
       </div>
     </div>
     
     <div class="clear"></div>
	 </div>
	</div>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
  <head>
		<%@ include file="/WEB-INF/view/common.jsp" %>    
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
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
   
   function priceReport(packageId,projectId) {
     window.location.href = "${pageContext.request.contextPath}/packageExpert/purchaseEmbodiment.html?packageId=" + packageId + "&projectId=" + projectId;
   }
   
   $(function() {
     var index=0;
     var divObj = $(".p0" + index);
     $(divObj).removeClass("hide");
     $("#packageIds").removeClass("shrink");        
     $("#packageIds").addClass("spread");
   });
  </script>
  </head>
  
  <body>
  	<h2 class="list_title">评审报告</h2>
   	<input type="hidden" id="projectId" value="${projectId}">
   	<c:forEach items="${list}" var="pack" varStatus="vs">
				<div class="over_hideen">
					<h2 onclick="ycDiv(this,'${vs.index}')" 
						<c:if test="${pack.projectStatus eq 'YZZ' || pack.projectStatus eq 'ZJZXTP' || pack.projectStatus eq 'ZJTSHZ' || pack.projectStatus eq 'ZJTSHBTG'}">
							class="count_flow hand fl spread"</c:if>class="count_flow shrink hand fl clear" id="packageIds">包名:<span class="f15 blue">${pack.name}</span>
						<c:if test="${pack.projectStatus eq 'YZZ'}"><span class="star_red">[该包已终止]</span></c:if>
						<c:if test="${pack.projectStatus eq 'ZJZXTP'}"><span class="star_red">[该包已转竞谈]</span></c:if>
						<c:if test="${pack.projectStatus eq 'ZJTSHZ'}"><span class="star_red">[该包转竞谈审核中]</span></c:if>
						<c:if test="${pack.projectStatus eq 'ZJTSHBTG'}"><span class="star_red">[该包转竞谈审核不通过]</span></c:if>
          </h2>
          <c:if test="${pack.projectStatus ne 'YZZ' || pack.projectStatus ne 'ZJZXTP' || pack.projectStatus ne 'ZJTSHZ' || pack.projectStatus ne 'ZJTSHBTG'}">
          	<div class="p0${vs.index} hide">
          		<div class="row mt10">
		            <div class="col-sm-2 col-xs-4 mb10"><button class="btn btn-windows input m0 w100p" type="button" onclick="priceReport('${pack.id}','${projectId}')">评审报告</button></div>
		          </div>
          	</div>
          </c:if>
        </div>
			</c:forEach>
  </body>
</html>

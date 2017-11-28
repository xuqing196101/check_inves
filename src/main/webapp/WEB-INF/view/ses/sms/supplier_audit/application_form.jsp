<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
	<%@ include file="/WEB-INF/view/common.jsp" %>
	<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
    <title>申请表</title>
    <style type="text/css">
      .abolish_img_file{
  			position: absolute;
		    right: 375px;
		    top: 5px;
		    color: #ef0000;
		    font-weight: bold;
		    font-size: 18px;
		    cursor: pointer;
      }
    </style>
    <script src="${pageContext.request.contextPath}/js/ses/sms/supplier_audit/essential.js"></script>
		<script type="text/javascript">
      $(function() {
          $("li").find("span").each(function() {
              var onmousemove = "this.style.background='#E8E8E8'";
              var onmouseout = "this.style.background='#FFFFFF'";
              $(this).attr("onmousemove",onmousemove);
              $(this).attr("onmouseout",onmouseout);
          });
      });

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
                    <a>支撑环境</a>
                </li>
                <li>
                    <a>供应商管理</a>
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
        <div class="content ">
          <div class="col-md-12 tab-v2 job-content">
			  		<%-- <%@include file="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp"%> --%>
	          <jsp:include page="/WEB-INF/view/ses/sms/supplier_audit/common_jump.jsp">
	          	<jsp:param value="six" name="currentStep"/>
	           	<jsp:param value="${supplierId }" name="supplierId"/>
	           	<jsp:param value="${supplierStatus }" name="supplierStatus"/>
	           	<jsp:param value="${sign }" name="sign"/>
	          </jsp:include>
            <%-- <form id="form_id" action="" method="post" >
                <input id="supplierId" name="supplierId" value="${supplierId}" type="hidden">
                <input id="status" name="supplierStatus" value="${supplierStatus}" type="hidden">
                <input type="hidden" name="sign" value="${sign}">
            </form> --%>

            <ul class="count_flow ul_list hand">
              <%-- <li class="col-md-3 margin-0 padding-0 ">
                <span class="" onclick="reason1(this,'supplierLevel');" >军队供应商分级方法：</span>
                <up:show showId="lvel_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierLevel}"/>
                <p class="b f18 ml10 red">×</p>
              </li> --%>
             	<li class="col-md-6 mt10 mb25" >
              	<span class="col-md-5 padding-left-5" onclick="auditFile(this,'download_page','supplierPledge');"
              	<c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierPledge) && !fn:contains(auditField,'supplierPledge')}">style="border: 1px solid #FF8C00;"</c:if>
              	<c:if test="${fn:contains(auditField,'supplierPledge')}">style="border: 1px solid #FF0000;"</c:if>>供应商承诺书：</span>
              	<c:if test="${fn:contains(unableField,'supplierPledge')}">
              		<img style="" src="/zhbj/public/backend/images/sc.png" class="abolish_img_file"/>
              	</c:if>
                <u:show showId="pledge_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierPledge}"/>
                <%-- <p class='abolish'><img style="padding-right: 120px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
               	<c:if test="${fn:contains(auditField,'supplierPledge')}">
                 	<a class='abolish'>
                  		<img style="padding-right: 120px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
                 	</a>
								</c:if> --%>
             	</li>
	            <li class="col-md-6 p0 mt10 mb25">
                <span class="col-md-5 padding-left-5" onclick="auditFile(this,'download_page','supplierRegList');"
                <c:if test="${fn:contains(fileModifyField,supplierDictionaryData.supplierRegList) && !fn:contains(auditField,'supplierRegList')}">style="border: 1px solid #FF8C00;"</c:if>
                <c:if test="${fn:contains(auditField,'supplierRegList')}">style="border: 1px solid #FF0000;"</c:if>>供应商申请表：</span>
                <c:if test="${fn:contains(unableField,'supplierRegList')}">
              		<img style="" src="/zhbj/public/backend/images/sc.png" class="abolish_img_file"/>
              	</c:if>
                <u:show showId="regList_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierRegList}"/>
                <%-- <p class='abolish'><img style="padding-right: 120px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></p>
                <c:if test="${fn:contains(auditField,'supplierRegList')}">
                  <a class='abolish'>
					 					<img style="padding-right: 120px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
									</a>
								</c:if> --%>
	            </li>
	            
	            <%-- <li class="col-md-3 margin-0 padding-0 ">
                <span class="" onclick="reason1(this,'supplierInspectList');" >军队供应商实地考察记录表：</span>
                <up:show showId="inspectList_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierInspectList}"/>
                <p class="b f18 ml10 red">×</p>
	            </li>
	            <li class="col-md-3 margin-0 padding-0 ">
                <span class="" onclick="reason1(this,'supplierReviewList');" >军队供应商考察廉政意见函：</span>
                <up:show showId="reviewList_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierReviewList}"/>
                <p class="b f18 ml10 red">×</p>
	            </li>
	            <li class="col-md-3 margin-0 padding-0 ">
                <span class="" onclick="reason1(this,'supplierChangeList');" >军队供应商注册变更申请表：</span>
                <up:show showId="changeList_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierChangeList}"/>
                <p class="b f18 ml10 red">×</p>
	            </li>
	            <li class="col-md-3 margin-0 padding-0 ">
                <span class="" onclick="reason1(this,'supplierExitList');" >军队供应商退库申请表：</span>
                <up:show showId="exitList_show" delete="false" groups="lvel_show,pledge_show,regList_show,inspectList_show,reviewList_show,changeList_show,exitList_show" businessId="${supplierId}" sysKey="${sysKey}" typeId="${supplierDictionaryData.supplierExitList}"/>
                <p class="b f18 ml10 red">×</p>
	             </li> --%>
             </ul>
           </div>
	         <div class="col-md-12 add_regist tc">
	           <a class="btn"  type="button" onclick="toStep('five');">上一步</a>
	           <c:if test="${isStatusToAudit}">
	             <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="tempAudit();">暂存</a>
	           </c:if>
	           <a class="btn"  type="button" onclick="toStep('seven');">下一步</a>
	         </div>
         </div>
       </div>
     <form target="_blank" id="download_form_id" action="${pageContext.request.contextPath}/supplierAudit/download.html" method="post">
       <input type="hidden" name="fileName" />
     </form>
  </body>
</html>

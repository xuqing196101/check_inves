<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
	<%@ include file="/WEB-INF/view/common.jsp"%>
	<script type="text/javascript" src= "${pageContext.request.contextPath}/js/dss/rids/analyze/list.js"></script>
	<script type="text/javascript">
	  // 采购资源权限标识
		var typeName = '${typeName}';
	</script>
</head>
<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
				<li><a href="javascript:void(0)">决策支持</a></li>
				<li><a href="javascript:void(0)">采购资源综合展示</a></li>
				<li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/resAnalyze/list.html')">采购资源展示</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<c:if  test="${typeName == 4}">
	<!-- 内容 -->
	<div class="container content job-content">
        <div class="col-md-12 col-sm-12 col-xs-12">
           <div class="m-chart-index clearfix">
               <div class="mci-left pull-left">
                   <div class="mcil-head m-bg-blue-600">基础信息</div>
                   <ul class="mcil-list clearfix">
                       <li>
                           <a href="javascript:void(0)" title="" id="analyzeSupplier">
                               <span class="mcill-icon icon-1"></span>
                               <span class="mcill-tit">供应商</span>
                           </a>
                       </li>
                       <li>
                           <a href="javascript:void(0)" title="" id="analyzeExpert">
                               <span class="mcill-icon icon-2"></span>
                               <span class="mcill-tit">专家</span>
                           </a>
                       </li>
                       <li>
                           <a href="javascript:void(0)" title="" id="analyzePurOrg">
                               <span class="mcill-icon icon-3"></span>
                               <span class="mcill-tit">采购机构</span>
                           </a>
                       </li>
                       <li>
                           <a href="javascript:void(0)" title="" id="analyzePurMember">
                               <span class="mcill-icon icon-4"></span>
                               <span class="mcill-tit">采购人员</span>
                           </a>
                       </li>
                   </ul>
               </div>
               <div class="mci-right pull-right">
                   <div class="mcil-head m-bg-green-600">业务信息</div>
                   <ul class="mcil-list clearfix">
                       <li>
                           <a href="javascript:void(0)" title="" id="analyzePurReq">
                               <span class="mcill-icon icon-1"></span>
                               <span class="mcill-tit">采购需求</span>
                           </a>
                       </li>
                       <li>
                           <a href="javascript:void(0)" title="" id="analyzePurPlan">
                               <span class="mcill-icon icon-2"></span>
                               <span class="mcill-tit">采购计划</span>
                           </a>
                       </li>
                       <li>
                           <a href="javascript:void(0)" title="" id="analyzePurProject">
                               <span class="mcill-icon icon-3"></span>
                               <span class="mcill-tit">采购项目</span>
                           </a>
                       </li>
                       <li>
                           <a href="javascript:void(0)" title="" id="analyzePurContract">
                               <span class="mcill-icon icon-4"></span>
                               <span class="mcill-tit">采购合同</span>
                           </a>
                       </li>
                       <li>
                           <a href="javascript:void(0)" title="" id="analyzePurNotice" id="analyzePurNotice">
                               <span class="mcill-icon icon-5"></span>
                               <span class="mcill-tit">采购公告</span>
                           </a>
                       </li>
                   </ul>
               </div>
           </div>
       </div>
    </div>
</c:if>
</body>
</html>
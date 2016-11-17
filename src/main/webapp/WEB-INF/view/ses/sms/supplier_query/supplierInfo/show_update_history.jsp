<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../../common.jsp"%>

<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<script type="text/javascript">
function tijiao(str){
  var action;
  if(str=="essential"){
     action ="${pageContext.request.contextPath}/supplierQuery/essential.html";
  }
  if(str=="financial"){
    action = "${pageContext.request.contextPath}/supplierQuery/financial.html";
  }
  if(str=="shareholder"){
    action = "${pageContext.request.contextPath}/supplierQuery/shareholder.html";
  }
  if(str=="materialProduction"){
    action = "${pageContext.request.contextPath}/supplierQuery/materialProduction.html";
  }
  if(str=="materialSales"){
    action = "${pageContext.request.contextPath}/supplierQuery/materialSales.html";
  }
  if(str=="engineering"){
    action = "${pageContext.request.contextPath}/supplierQuery/engineering.html";
  }
  if(str=="service"){
    action = "${pageContext.request.contextPath}/supplierQuery/serviceInformation.html";
  }
  if(str=="chengxin"){
    action = "${pageContext.request.contextPath}/supplierQuery/list.html";
  }
  if(str=="item"){
     action = "${pageContext.request.contextPath}/supplierQuery/item.html";
  }
  if(str=="product"){
     action = "${pageContext.request.contextPath}/supplierQuery/product.html";
  }
   if(str=="updateHistory"){
     action = "${pageContext.request.contextPath}/supplierQuery/showUpdateHistory.html";
  }
  $("#form_id").attr("action",action);
  $("#form_id").submit();
}
function yincang(){
	$("div").removeClass("dnone");
}
</script>
</head>
  
<body>
 <div class="margin-top-10 breadcrumbs ">
        <div class="container">
            <ul class="breadcrumb margin-left-0">
                <li><a href="#"> 首页</a>
                </li>
                <li><a href="#">支撑系统</a>
                </li>
                <li><a href="#">供应商查看</a>
                </li>
            </ul>
            <div class="clear"></div>
        </div>
    </div>
    <!-- 项目戳开始 -->
    <div class="container clear margin-top-30">
        <!-- <div class="container">
   <div class="col-md-12">
    <button class="btn btn-windows back" onclick="fanhui()">返回</button> 
    </div>
    </div> -->
        <!--详情开始-->
        <div class="container content pt0">
            <div class="tab-v2">
                <ul class="nav nav-tabs bgwhite">
            <li class=""><a aria-expanded="true"  class="f18" href="#tab-1" data-toggle="tab" onclick="tijiao('essential');">基本信息</a></li>
            <li class=""><a aria-expanded="false" class="f18"  href="#tab-2" data-toggle="tab" onclick="tijiao('financial');">财务信息</a></li>
            <li class=""><a aria-expanded="fale" class="f18"  href="#tab-3" data-toggle="tab" onclick="tijiao('shareholder');">股东信息</a></li>
            <c:if test="${fn:contains(suppliers.supplierType, '生产型')}">
            <li class=""><a aria-expanded="fale" class="f18"  href="#tab-2" data-toggle="tab" onclick="tijiao('materialProduction');">物资-生产型专业信息</a></li>
            </c:if>
             <c:if test="${fn:contains(suppliers.supplierType, '销售型')}">
            <li class=""><a aria-expanded="fale"  class="f18" href="#tab-3" data-toggle="tab" onclick="tijiao('materialSales');">物资-销售型专业信息</a></li>
            </c:if>
            <c:if test="${fn:contains(suppliers.supplierType, '工程')}">
            <li class=""><a aria-expanded="false" class="f18"  href="#tab-3" data-toggle="tab" onclick="tijiao('engineering');">工程-专业信息</a></li>
            </c:if>
             <c:if test="${fn:contains(suppliers.supplierType, '服务')}">
            <li class=""><a aria-expanded="false" class="f18"  href="#tab-3" data-toggle="tab" onclick="tijiao('service');">服务-专业信息</a></li>
            </c:if>
            <li class=""><a aria-expanded="false"  class="f18" href="#tab-2" data-toggle="tab" onclick="tijiao('item');">品目信息</a></li>
            <li class=""><a aria-expanded="false"  class="f18" href="#tab-3" data-toggle="tab" onclick="tijiao('product');" >产品信息</a></li>
            <li class=""><a aria-expanded="false"  class="f18" href="#tab-2" data-toggle="tab" onclick="tijiao('chengxin');">诚信记录</a></li>
            <li class="active"><a aria-expanded="false"  class="f18" href="#tab-2" data-toggle="tab" onclick="tijiao('updateHistory');">历史修改记录</a></li>
          </ul>
          <div class="padding-top-20"></div>
          <form id="form_id" action="" method="post">
            <input name="supplierId" id="id" value="${suppliers.id }" type="hidden">
          </form> 
          <c:if test="${not empty list }">
           <c:forEach items="${list }" var="record" varStatus="vs">
             <c:if test="${vs.index<5 }">
              <div class=" margin-bottom-0">
                <div class="tml_container padding-top-10">
				  <div class="dingwei">
					  <div class="tml_spine">
						<span class="tml_spine_bg"></span>
						<span id="timeline_start_point" class="start_point"></span>
					  </div>
					  <div class="tml_poster" id="post_area" >
					       <div class="poster" id="poster_1">
		                     <div class=" margin-bottom-0">
		                       <h2 class="history_icon">修改记录</h2>
						        <div class="padding-left-40">
						          <c:set value="${fn:substringBefore(record, '^-^')}" var="records"></c:set>
						 		  <span> ${fn:replace(records,"null", " ")}  </span>
							    </div>
		                     </div>
					       </div>
						   <div class="period_header"><span>${fn:substringAfter(record, "^-^")}  </span></div>
						   <span class="ui_left_arrow"><span class="ui_arrow"></span></span>
						   <div class="clear"></div>
					  </div>
                  </div>
                </div>
			  </div>
			 </c:if>
			 <c:if test="${vs.index==5}">
			  	<span class="hand" onclick="yincang()"><b>点击更多...</b></span>
			 </c:if>
			  <c:if test="${vs.index>4}">
				  <div  class="dnone margin-bottom-0" >
	                <div  class="tml_container padding-top-10">
					  <div class="dingwei">
						  <div class="tml_spine">
							<span class="tml_spine_bg"></span>
							<span id="timeline_start_point" class="start_point"></span>
						  </div>
						  <div class="tml_poster" id="post_area" >
							  <div class="poster" id="poster_1">
			                     <div class=" margin-bottom-0">
			                       <h2 class="history_icon">修改记录</h2>
							        <div class="padding-left-40">
							 		  <c:set value="${fn:substringBefore(record, '^-^')}" var="records"></c:set>
							 		  <span> ${fn:replace(records,"null", " ")}  </span>
								    </div>
			                     </div>
							  </div>
							  <div class="period_header"><span>${fn:substringAfter(record, "^-^")}  </span></div>
							  <span class="ui_left_arrow"><span class="ui_arrow"></span></span>
							  <div class="clear"></div>
					      </div>
	                  </div>
	                </div>
				  </div>
			  </c:if>
         </c:forEach>
         </c:if> 
         <c:if test="${empty list }">
         	<div class=" margin-bottom-0">
                <div class="tml_container padding-top-10">
				  <div class="dingwei">
					  <div class="tml_spine">
						<span class="tml_spine_bg"></span>
						<span id="timeline_start_point" class="start_point"></span>
					  </div>
					  <div class="tml_poster" id="post_area" >
					       <div class="poster" id="poster_1">
		                     <div class=" margin-bottom-0">
		                       <h2 class="history_icon">修改记录</h2>
						        <div class="padding-left-40">
						 		  <span>暂无修改记录 </span>
							    </div>
		                     </div>
					       </div>
						   <div class="period_header"><span>  </span></div>
						   <span class="ui_left_arrow"><span class="ui_arrow"></span></span>
						   <div class="clear"></div>
					  </div>
                  </div>
                </div>
			  </div>
         </c:if> 
</div>
</div>
</body>
</html>

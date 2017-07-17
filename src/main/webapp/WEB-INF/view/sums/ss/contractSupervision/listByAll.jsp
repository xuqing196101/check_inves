<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>
  <head>
  	<jsp:include page="/WEB-INF/view/common.jsp"/>
    <title>合同列表</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script src="${pageContext.request.contextPath}/public/easyui/jquery.easyui.min.js"></script>
<link href="${pageContext.request.contextPath}/public/easyui/themes/icon.css" media="screen" rel="stylesheet" type="text/css">
<link href="${pageContext.request.contextPath}/public/easyui/themes/default/easyui.css" media="screen" rel="stylesheet" type="text/css">
  <script type="text/javascript">
  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${list.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total: "${list.total}",
		    startRow: "${list.startRow}",
		    endRow: "${list.endRow}",
		    groups: "${list.pages}">=3?3:"${list.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		    	var page = location.search.match(/page=(\d+)/);
		    	if(page==null){
		    		page = {};
		    		var data = "${list.pageNum}";
		    		page[0]=data;
		    		page[1]=data;
		    	}
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	$("#page").val(e.curr);
					$("#form1").submit();
		        }
		    }
		});
	    $(document).keyup(function(event) {
			if (event.keyCode == 13) {
				$("#form1").submit();
			}
		});
  });
	
  	function resetForm(){
  		$("#projectName").val("");
  		$("#code").val("");
  		$("#demandSector").val("");
  		$("#documentNumber").val("");
  		$("#supplierDepName").val("");
  		$("#purchaseDepName").val("");
  		$("#year").val("");
  		$("#budgetSubjectItem").val("");
  		$("#status").val("");
  	}
  	
  	function onclickDetail(id){
        jumppage("${pageContext.request.contextPath }/contractSupervision/contSupervision.html?id="+id);
  	}
	function openFile(id){
		location.href="${pageContext.request.contextPath }/contractSupervision/filePage.html?id="+id;
	}
  </script>
  </head>
  
  <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
               <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
               <li><a href="javascript:void(0);">业务监管系统</a></li>
               <li><a href="javascript:void(0);">采购业务监督</a></li>
               <li><a href="javascript:jumppage('${pageContext.request.contextPath}/contractSupervision/contractSupervisionByAll.html');">采购合同监督</a></li>
           </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="container">
   <div class="headline-v2">
        <h2>合同列表
	    </h2>
   </div> 
<!-- 项目戳开始 -->
    <form id="form1" action="${pageContext.request.contextPath}/contractSupervision/contractSupervisionByAll.html" method="post">
    <input type="hidden" value="" name="page" id="page"/>
    <input type="hidden" value="${ purCon.purchaseDepShortName }" name="purchaseDepShortName" />
     <div class="search_detail">
    	<ul class="demand_list">
          <li class="fl"><label class="fl">采购项目：</label><span><input type="text" value="${purCon.projectName }" id="projectName" name="projectName" class="mb0 mt5 w200"/></span></li>
	      <li class="fl"><label class="fl">合同编号：</label><span><input type="text" value="${purCon.code }" id="code" name="code" class="mb0 mt5 w200"/></span></li>
	      <li class="fl"><label class="fl">需求部门：</label><span><input type="text" value="${purCon.demandSector }" id="demandSector" name="demandSector" class="mb0 mt5 w200"/></span></li>
	      <li class="fl"><label class="fl">计划文件号：</label><span><input type="text" value="${purCon.documentNumber }" id="documentNumber" name="documentNumber" class="mb0 mt5 w200"/></span></li>
	      <li class="fl"><label class="fl">供应商：</label><span><input type="text" value="${purCon.supplierDepName }" id="supplierDepName" name="supplierDepName" class="mb0 mt5 w200"/></span></li>
	      <li class="fl"><label class="fl">采购机构：</label><span><input type="text" value="${purCon.purchaseDepName }" id="purchaseDepName" name="purchaseDepName" class="mb0 mt5 w200"/></span></li>
	      <li class="fl"><label class="fl">年度：</label><span><input type="text" value="${purCon.year_string }" id="year" name="year_string" class="mb0 mt5 w200"/></span></li>
	      <li class="fl"><label class="fl">项级预算科目：</label><span><input type="text" value="${purCon.budgetSubjectItem }" id="budgetSubjectItem" name="budgetSubjectItem" class="mb0 mt5 w200"/></span></li>
	     
    	  <div class="fl col-md-12 tc mt10">
    	    <input type="submit" class="btn" value="查询"/>
    	    <input type="button" onclick="resetForm()" class="btn" value="重置"/>
    	  </div>
    	</ul>
    	  <div class="clear"></div>
    	  </div>
      </form>
        
   <div class="content table_box over_auto table_wrap">
   	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th class="tnone"></th>
			    <th class="info w50">序号</th>
			    <th class="info" width="10%">合同编号</th>
				<th class="info" width="10%">合同名称</th>
				<th class="info" width="10%">合同金额(万元)</th>
				<th class="info" width="10%">项目名称</th>
				<th class="info" width="10%">计划文件号</th>
				<th class="info">预算(万元)</th>
				<th class="info w50">年度</th>
				<th class="info">项级预算科目</th>
				<th class="info" width="10%">甲方单位</th>
				<th class="info" width="10%">供应商</th>
				<!-- <th class="info">状态</th> -->
			</tr>
		</thead>
		<c:forEach items="${draftConList}" var="draftCon" varStatus="vs">
			<tr>
				<td class="tnone">${draftCon.status}</td>
				<td class="pointer tc" >${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
				<c:set value="${draftCon.code}" var="code"></c:set>
				<c:set value="${fn:length(code)}" var="length"></c:set>
				<c:if test="${length>7}">
					<td  class="pointer" title="${code}" onclick="openFile('${draftCon.id}');">${fn:substring(code,0,10)}...</td>
				</c:if>
				<c:if test="${length<=7}">
					<td  class="pointer" title="${code}" onclick="openFile('${draftCon.id}');">${code}</td>
				</c:if>
				<c:set value="${draftCon.name}" var="name"></c:set>
				<c:set value="${fn:length(name)}" var="length"></c:set>
				<c:if test="${length>9}" >
					<td  class="pointer" title="${name}" onclick="onclickDetail('${draftCon.id}');"><a onclick="onclickDetail('${draftCon.id}');">${fn:substring(name,0,9)}...</a></td>
				</c:if>
				<c:if test="${length<=9}" >
					<td  class="pointer" title="${name}" onclick="onclickDetail('${draftCon.id}');"><a onclick="onclickDetail('${draftCon.id}');">${name}</a></td>
				</c:if>
				<td class="tr pointer" >${draftCon.money}</td>
				<td class="tl pointer" >${draftCon.projectName}</td>
				<td class="tl pointer" >${draftCon.documentNumber}</td>
				<td class="tr pointer" >${draftCon.budget}</td>
				<td class="tc pointer" >${draftCon.year}</td>
				<td class="tl pointer" >${draftCon.budgetSubjectItem}</td>
				<td class="tl pointer" >${draftCon.showDemandSector}</td>
				<td class="tl pointer" >${draftCon.showSupplierDepName}</td>
				<%--<c:if test="${draftCon.status==0}">
					<td class="tc pointer" >暂存</td>
				</c:if>
				 <c:if test="${draftCon.status==1}">
					<td class="tc pointer" >草案</td>
				</c:if>
				<c:if test="${draftCon.status==2}">
					<td class="tc pointer" >正式</td>
				</c:if> --%>
			</tr>
		</c:forEach>
	</table>
    </div>
   <div id="pagediv" align="right"></div>
   </div>
   
</body>
</html>

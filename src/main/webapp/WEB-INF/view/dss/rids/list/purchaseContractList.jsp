<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>
  <head>
  	<jsp:include page="/WEB-INF/view/common.jsp"/>
    <title>合同草案列表</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  <script type="text/javascript">
  $(function(){
			laypage({
					cont : $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
					pages : "${list.pages}", //总页数
					skin : '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
					skip : true, //是否开启跳页
					total : "${list.total}",
					startRow : "${list.startRow}",
					endRow : "${list.endRow}",
					groups : "${list.pages}" >= 3 ? 3 : "${list.pages}", //连续显示分页数
					curr : function() { //通过url获取当前页，也可以同上（pages）方式获取
						var page = location.search.match(/page=(\d+)/);
						if (page == null) {
							page = {};
							var data = "${list.pageNum}";
							page[0] = data;
							page[1] = data;
						}
						return page ? page[1] : 1;
					}(),
					jump : function(e, first) { //触发分页后的回调
						if (!first) { //一定要加此判断，否则初始时会无限刷新
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

			function resetForm() {
				$("#projectName").val("");
				$("#code").val("");
				$("#demandSector").val("");
				$("#documentNumber").val("");
				$("#supplierDepName").val("");
				$("#year").val("");
				$("#budgetSubjectItem").val("");
			}

			function showDraftContract(id, status) {
				window.location.href = "${pageContext.request.contextPath}/purchaseContract/showDraftContract.html?ids="
						+ id + "&status=" + status;
			}

			function back() {
				window.location.href = "${pageContext.request.contextPath}/resAnalyze/analyzePurchaseContract.html";
			}
		</script>
  </head>
  
  <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
			   <li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
			   <li><a href="javascript:void(0);">采购合同管理</a></li>
			   <li><a href="javascript:jumppage('${pageContext.request.contextPath}/purchaseContract/selectDraftContract.html');">合同管理</a></li>
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
    <form id="form1" action="${pageContext.request.contextPath}/purchaseContract/readOnlyList.html" method="post">
    	<input type="hidden" name="page" value="" id="page"/>
    	<input type="hidden" name="purchaseDepName" value="${ purCon.purchaseDepName }" id="purchaseDepName"/>
    	<input type="hidden" name="status" value="${ purCon.status }" id="status"/>
     	<div class="search_detail">
    	<ul class="demand_list">
        <li class="fl"><label class="fl">采购项目：</label><span><input type="text" value="${purCon.projectName }" id="projectName" name="projectName" class="mb0 mt5 w200"/></span></li>
	      <li class="fl"><label class="fl">合同编号：</label><span><input type="text" value="${purCon.code }" id="code" name="code" class="mb0 mt5 w200"/></span></li>
	      <li class="fl"><label class="fl">计划文件号：</label><span><input type="text" value="${purCon.documentNumber }" id="documentNumber" name="documentNumber" class="mb0 mt5 w200"/></span></li>
	      <li class="fl"><label class="fl">供应商：</label><span><input type="text" value="${purCon.supplierDepName }" id="supplierDepName" name="supplierDepName" class="mb0 mt5 w200"/></span></li>
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
  
   <div class="col-md-12 pl20 mt10">
     <button class="btn btn-windows back" onclick="back()" type="button">返回</button>
   </div>
   <div class="fr mt5 b">
    	项目总金额(万元)：${contractSum}
   </div>
   <div class="content table_box over_auto table_wrap">
  	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
				<th class="tnone"></th>
			    <th class="info w50">序号</th>
			    <th class="info">合同编号</th>
				<th class="info">合同名称</th>
				<th class="info">合同金额(万元)</th>
				<th class="info">项目名称</th>
				<th class="info">计划文件号</th>
				<th class="info">预算(万元)</th>
				<th class="info">年度</th>
				<th class="info">项级预算科目</th>
				<th class="info">甲方单位</th>
				<th class="info">供应商</th>
				<th class="info">状态</th>
			</tr>
		</thead>
		<c:forEach items="${draftConList}" var="draftCon" varStatus="vs">
			<tr>
				<td class="tc pointer"><input onclick="check()" type="checkbox" name="chkItem" value="${draftCon.id}" /></td>
				<td class="tnone">${draftCon.status}</td>
				<td class="pointer tc" onclick="showDraftContract('${draftCon.id}','${draftCon.status}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
				<c:set value="${draftCon.code}" var="code"></c:set>
				<c:set value="${fn:length(code)}" var="length"></c:set>
				<c:if test="${length>7}">
					<td onclick="showDraftContract('${draftCon.id}','${draftCon.status}')" class="pointer" title="${code}">${fn:substring(code,0,7)}...</td>
				</c:if>
				<c:if test="${length<=7}">
					<td onclick="showDraftContract('${draftCon.id}','${draftCon.status}')" class="pointer" title="${code}">${code}</td>
				</c:if>
				<c:set value="${draftCon.name}" var="name"></c:set>
				<c:set value="${fn:length(name)}" var="length"></c:set>
				<c:if test="${length>9}">
					<td onclick="showDraftContract('${draftCon.id}','${draftCon.status}')" class="pointer" title="${name}">${fn:substring(name,0,9)}...</td>
				</c:if>
				<c:if test="${length<=9}">
					<td onclick="showDraftContract('${draftCon.id}','${draftCon.status}')" class="pointer" title="${name}">${name}</td>
				</c:if>
				<td class="tr pointer" onclick="showDraftContract('${draftCon.id}','${draftCon.status}')">${draftCon.money}</td>
				<td class="tl pointer" onclick="showDraftContract('${draftCon.id}','${draftCon.status}')">${draftCon.projectName}</td>
				<td class="tl pointer" onclick="showDraftContract('${draftCon.id}','${draftCon.status}')">${draftCon.documentNumber}</td>
				<td class="tr pointer" onclick="showDraftContract('${draftCon.id}','${draftCon.status}')">${draftCon.budget}</td>
				<td class="tc pointer" onclick="showDraftContract('${draftCon.id}','${draftCon.status}')">${draftCon.year}</td>
				<td class="tl pointer" onclick="showDraftContract('${draftCon.id}','${draftCon.status}')">${draftCon.budgetSubjectItem}</td>
				<td class="tl pointer" onclick="showDraftContract('${draftCon.id}','${draftCon.status}')">${draftCon.showDemandSector}</td>
				<td class="tl pointer" onclick="showDraftContract('${draftCon.id}','${draftCon.status}')">${draftCon.showSupplierDepName}</td>
				<%--<c:if test="${draftCon.status==0}">
					<td class="tc pointer" onclick="showDraftContract('${draftCon.id}','${draftCon.status}')">暂存</td>
				</c:if>
				--%><c:if test="${draftCon.status==1}">
					<td class="tc pointer" onclick="showDraftContract('${draftCon.id}','${draftCon.status}')">草案</td>
				</c:if>
				<c:if test="${draftCon.status==2}">
					<td class="tc pointer" onclick="showDraftContract('${draftCon.id}','${draftCon.status}')">正式</td>
				</c:if>
				<c:if test="${draftCon.status==0}">
					<td class="tc pointer" onclick="showDraftContract('${draftCon.id}','${draftCon.status}')">暂存</td>
				</c:if>
			</tr>
		</c:forEach>
	</table>
    </div>
   <div id="pagediv" align="right"></div>
   </div>
   <form action="${pageContext.request.contextPath}/purchaseContract/toRoughContract.html" id="form2">
    <input type="hidden" name="id" id="ids" value=""/>
    <input type="hidden" name="status" value="1"/>
    <ul class="list-unstyled mt10 dnone" id="numberWin">
  		    <li class="col-md-6 col-sm-12 col-xs-12 pl15">
			   <span class="col-md-12 col-sm-12 col-xs-12"><div class="red star_red">*</div>草稿合同上报时间：</span>
			   <div class="input-append input_group col-sm-12 col-xs-12 p0 col-md-12">
			     <input type="text" name="draftGitAt" id="draftGitAt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate mb0 w220"/>
			     <div id='gitTime' class="cue"></div>
			   </div>
			</li>
			<li class="col-md-6 col-sm-12 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12"><div class="red star_red">*</div>草稿合同批复时间：</span>
			   <div class="input-append input_group col-sm-12 col-xs-12 p0 col-md-12">
			     <input type="text" name="draftReviewedAt" id="draftReviewedAt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate mb0 w220"/>
			     <div id='reviewTime' class="cue"></div>
			   </div>
			</li>
			<li class="tc col-md-12 col-sm-12 col-xs-12 mt20">
			 <input type="button" class="btn" onclick="save()" value="生成"/>
			 <input type="button" class="btn" onclick="cancel()" value="取消"/>
			</li>
	</ul>
	</form>
</body>
</html>

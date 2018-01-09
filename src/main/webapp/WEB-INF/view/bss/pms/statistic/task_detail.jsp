<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
  /** 分页  */
  $(function() {
    laypage({
      cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
      pages: "${info.pages}", //总页数
      skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
      skip: true, //是否开启跳页
      total: "${info.total}",
      startRow: "${info.startRow}",
      endRow: "${info.endRow}",
      groups: "${info.pages}" >= 5 ? 5 : "${info.pages}", //连续显示分页数
      curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
        var page = location.search.match(/page=(\d+)/);
        if(page == null) {
          page = {};
          page[0] = "${info.pageNum}";
          page[1] = "${info.pageNum}";
        }
        return page ? page[1] : 1;
      }(),
      jump: function(e, first) { //触发分页后的回调
        if(!first) { //一定要加此判断，否则初始时会无限刷新
          $("#page").val(e.curr);
          $("#add_form").submit();
        }
      }
    });
  });
  function restValue(){
	  $(':input','#add_form') 
	  .not(':button, :submit, :reset, :hidden') 
	  .val('') 
  }
  
  function exports(){
  	var department = $("#department").val();
  	var goodsName = $("input[name='goodsName']").val();
  	var taskNumber = $("input[name='taskNumber']").val();
  	var purchaseType = $("#purchaseTypes").val();
  	var organization = $("#organization").val();
  	var beginDate = $("input[name='beginDate']").val();
  	var endDate = $("input[name='endDate']").val();
  	var projectNumber = $("input[name='projectNumber']").val();
  	var proBeginDate = $("input[name='proBeginDate']").val();
  	var proEndDate = $("input[name='proEndDate']").val();
  	var code = $("input[name='code']").val();
  	var materialsType = $("#materialsType").val();
  	var showName = $("#showName").val();
  	var showValue = $("#showValue").val();
  	window.location.href = "${pageContext.request.contextPath}/statistic/taskExcel.html?department=" + department + 
  	"&goodsName=" +goodsName + "&taskNumber=" + taskNumber + "&purchaseType=" + purchaseType + "&organization=" + organization
  	+ "&beginDate=" + beginDate + "&endDate=" + endDate + "&projectNumber=" + projectNumber + "&proBeginDate=" + proBeginDate
  	+ "&proEndDate=" + proEndDate + "&code=" + code + "&materialsType=" + materialsType + "&showName=" + showName + "&showValue=" + showValue;
  }
  
  /* function exports(){
  	var department = $("#department").val();
  	var goodsName = $("input[name='goodsName']").val();
  	var taskNumber = $("input[name='taskNumber']").val();
  	var purchaseType = $("#purchaseTypes").val();
  	var organization = $("#organization").val();
  	var beginDate = $("input[name='beginDate']").val();
  	var endDate = $("input[name='endDate']").val();
  	var projectNumber = $("input[name='projectNumber']").val();
  	var proBeginDate = $("input[name='proBeginDate']").val();
  	var proEndDate = $("input[name='proEndDate']").val();
  	var code = $("input[name='code']").val();
  	var materialsType = $("#materialsType").val();
  	layer.open({
        type: 2, //page层
        area: ['800px', '500px'],
        title: '请选择导入的内容',
        shade: 0.01, //遮罩透明度
        moveType: 1, //拖拽风格，0是默认，1是传统拖动
        shift: 1, //0-6的动画形式，-1不开启
        shadeClose: true,
        content: '${pageContext.request.contextPath}/statistic/nextStep.html?department=' + department + 
		  	'&goodsName=' +goodsName + '&taskNumber=' + taskNumber + '&purchaseType=' + purchaseType + '&organization=' + organization
		  	+ '&beginDate=' + beginDate + '&endDate=' + endDate + '&projectNumber=' + projectNumber + '&proBeginDate=' + proBeginDate
		  	+ '&proEndDate=' + proEndDate + '&code=' + code + '&materialsType=' + materialsType,
		     });
  } */
  
  var zTreeObj,
	setting={
        data:{
            simpleData:{
                enable:true,
                idKey:"id",
                pId:"pId",
            }
        },
        check: {
							enable: true,
							chkStyle: "checkbox",
							chkboxType: { "Y": "ps", "N": "ps" },
						},
				callback: {
					onCheck: onCheck
				}
						
    };
	zTreeNodes = [
			{"id":1, "pId":0, "name":"采购需求", "value":""},
	    {"id":11, "pId":1, "name":"需求名称", "value":"demandName","checked":false},
	    {"id":12, "pId":1, "name":"需求文号", "value":"demandNumber","checked":false},
	    {"id":13, "pId":1, "name":"编报人", "value":"demandMan","checked":false},
	    {"id":2, "pId":0, "name":"采购计划", "value":""},
	    {"id":11, "pId":2, "name":"计划名称", "value":"planName","checked":false},
	    {"id":12, "pId":2, "name":"计划文号", "value":"planNo","checked":false},
	    /* {"id":22, "pId":2, "name":"采购管理部门", "value":"demandNumber","checked":false},
	    {"id":23, "pId":2, "name":"下达人", "value":"demandNumber","checked":false},
	    {"id":24, "pId":2, "name":"下达时间", "value":"demandNumber","checked":false}, */
	    {"id":3, "pId":0, "name":"采购项目", "value":""},
	    {"id":11, "pId":3, "name":"项目名称", "value":"projectName","checked":false},
	    {"id":12, "pId":3, "name":"项目编号", "value":"projectNumber","checked":false},
	    {"id":13, "pId":3, "name":"负责人", "value":"userId","checked":false},
	    {"id":14, "pId":3, "name":"包名", "value":"packName","checked":false},
	    {"id":15, "pId":3, "name":"包号", "value":"packNumber","checked":false},
	    {"id":16, "pId":3, "name":"开标时间", "value":"bidDate","checked":false},
	    {"id":17, "pId":3, "name":"开标地点", "value":"bidAddress","checked":false},
	    {"id":18, "pId":3, "name":"评审专家", "value":"expertName","checked":false},
	    {"id":19, "pId":3, "name":"中标供应商", "value":"supplier","checked":false},
	    {"id":4, "pId":0, "name":"采购合同", "value":""},
	    {"id":11, "pId":4, "name":"合同名称", "value":"contractName","checked":false},
	    {"id":12, "pId":4, "name":"合同编号", "value":"contractCode","checked":false},
	    
	];
	
	function onCheck(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj("tree"),
					nodes = zTree.getCheckedNodes(true),
					v = "";
				var rid = "";
				var values = "";
				for(var i = 0, l = nodes.length; i < l; i++) {
					if(nodes[i].pId != null){
						v += nodes[i].name + ",";
						rid += nodes[i].id + ",";
						values += nodes[i].value + ",";
					}
					
				}
				if(v.length > 0) v = v.substring(0, v.length - 1);
				if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
				var cityObj = $("#showName");
				cityObj.attr("value", v);
				cityObj.attr("title", v);
				$("#showValue").val(values);
			}
  
  function show(){
  	var cityObj = $("#showName");
		var cityOffset = $("#showName").offset();
  	$("#packageContent").css({
					left: cityOffset.left + "px",
					top: cityOffset.top + cityObj.outerHeight() + "px"
		}).slideDown("fast");
  	zTreeObj = $.fn.zTree.init($("#tree"), setting, zTreeNodes);
  	//全部展开
  	/* var treeObj = $.fn.zTree.getZTreeObj("tree");
		treeObj.expandAll(true); */
  	$("body").bind("mousedown", onBodyDownPackageType);
  }
  
  function onBodyDownPackageType(event) {
				if(!(event.target.id == "menuBtn" || $(event.target).parents("#packageContent").length > 0)) {
					hidePackageType();
				}
			}

			function hidePackageType() {
				$("#packageContent").fadeOut("fast");
				$("body").unbind("mousedown", onBodyDownPackageType);

			}
  
  
  </script>
</head>

<body>
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')">首页</a></li>
				<li><a href="javascript:void(0);">保障作业系统</a></li>
				<li><a href="javascript:void(0);">采购计划管理</a></li>
				<li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/statistic/taskList.html');">任务查询统计</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<!-- 录入采购计划开始-->
	<div class="container">
		<div class="headline-v2 fl">
			<h2>按明细查询</h2>
		</div>
		<div id="packageContent" class="packageContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
			<ul id="tree" class="ztree" style="margin-top:0;"></ul>
		</div>
		<h2 class="search_detail">
		<form id="add_form" action="${pageContext.request.contextPath }/statistic/taskDetailList.html" method="post">
		<input type="hidden" name="page" id="page">
    <div class="m_row_5">
    <div class="row">
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">需求部门：</div>
          <div class="col-xs-8 f0 lh0">
            <select name="department" id="department" class="w100p h32 f14">
              <option value="">请选择</option>
              <c:forEach items="${allXq}" var="org" >
              <option value="${org.shortName}" <c:if test="${org.shortName eq detail.department}">selected="selected"</c:if>>${org.shortName}</option>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">任务类型：</div>
          <div class="col-xs-8 f0 lh0">
            <select name="materialsType" id="materialsType" class="w100p h32 f14">
              <option value="">请选择</option>
              <c:forEach items="${planTypes}" var="type" >
              <option value="${type.id}" <c:if test="${type.id eq materialsType}">selected="selected"</c:if>>${type.name}</option>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">产品名称：</div>
          <div class="col-xs-8 f0 lh0">
            <input type="text" name="goodsName" value="${detail.goodsName }" class="w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">任务文号：</div>
          <div class="col-xs-8 f0 lh0">
            <input type="text" name="taskNumber" value="${detail.taskNumber }" class="w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购方式：</div>
          <div class="col-xs-8 f0 lh0">
            <select name="purchaseType" id="purchaseTypes" class="w100p h32 f14">
              <option value="">请选择</option>
              <c:forEach items="${dataType}" var="d" >
              <option value="${d.id}" <c:if test="${d.id eq detail.purchaseType}">selected="selected"</c:if>>${d.name}</option>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购机构：</div>
          <div class="col-xs-8 f0 lh0">
            <select name="organization" id="organization" class="w100p h32 f14">
              <option value="">请选择</option>
              <c:forEach items="${allOrg}" var="org" >
              <option value="${org.id}" <c:if test="${org.id eq detail.organization}">selected="selected"</c:if>>${org.shortName}</option>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">下达开始时间：</div>
          <div class="col-xs-8 f0 lh0">
            <input type="text" name="beginDate" id="draftReviewedAt" value="${detail.beginDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">下达结束时间：</div>
          <div class="col-xs-8 f0 lh0">
            <input type="text" name="endDate" id="draftReviewedAt" value="${detail.endDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})" class="Wdate w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">项目编号：</div>
          <div class="col-xs-8 f0 lh0">
            <input type="text" name="projectNumber" value="${projectNumber}" class="w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">开标开始时间：</div>
          <div class="col-xs-8 f0 lh0">
            <input type="text" name="proBeginDate" value="${proBeginDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">开标结束时间：</div>
          <div class="col-xs-8 f0 lh0">
            <input type="text" name="proEndDate" value="${proEndDate}" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" class="Wdate w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">合同编号：</div>
          <div class="col-xs-8 f0 lh0">
            <input type="text" name="code" value="${code}" class="w100p h32 f14 mb0">
          </div>
        </div>
      </div>
      
      <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3 mb10">
        <div class="row">
          <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">导出选项：</div>
          <div class="col-xs-8 f0 lh0">
            <input class="w100p h32 f14 mb0" readonly id="showName" value="" placeholder="请选择" onclick="show();" type="text">
						<input readonly id="showValue" name="showValue" type="hidden">
          </div>
        </div>
      </div>
    </div>
    </div>
    
    <div class="tc">
      <input class="btn mb0" type="submit" name="" value="查询" />
      <input type="button" class="btn mb0" onclick="restValue();" value="重置" />
      <button class="btn mb0 mr0" type="button" onclick="exports();">导出excel</button>
    </div>
		</form>
		</h2>
		<div class="pl20">
			<input class="btn-u" type="button" name="" value="按明细查询" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/taskDetailList.html'" />
			<input class="btn-u" type="button" name="" value="按任务查询" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/taskList.html'" />
			<input class="btn-u" type="button" name="" value="按需求部门统计" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/charDept.html'" />
			<input class="btn-u" type="button" name="" value="按采购方式统计" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/charType.html'" />
			<input class="btn-u" type="button" name="" value="按月统计" onclick="javascript:location.href='${pageContext.request.contextPath}/statistic/charMonth.html'" />
		</div>
		<div class="content table_box pt10">
			<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr>
						<th class="w50">序号</th>
						<th class="w80">任务名称</th>
						<th class="w80">任务文号</th>
						<th class="w80">需求部门</th>
						<th class="w80">产品编号</th>
						<th>产品名称</th>
						<th class="w80">单位</th>
						<th class="w80">采购数量</th>
						<th class="w80">单价(元)</th>
						<th class="w80">金额(万元)</th>
						<th class="w100">采购方式</th>
						<th class="w120">采购机构</th>
						<th class="w100">下达时间</th>
					</tr>
				</thead>
				<c:forEach items="${info.list}" var="obj" varStatus="vs">
				
					<tr  <c:if test="${obj.isParent=='true'}">class="red" </c:if> >
						<td class="tc">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
						<td class="tc" title="${obj.taskName }">
					    <c:if test="${fn:length (obj.taskName) > 15}">${fn:substring(obj.taskName,0,14)}...</c:if>
              <c:if test="${fn:length(obj.taskName) <=15}">${obj.taskName}</c:if>
						</td>
						<td class="tc" title="${obj.taskNumber }">
              <c:if test="${fn:length (obj.taskNumber) > 15}">${fn:substring(obj.taskNumber,0,14)}...</c:if>
              <c:if test="${fn:length(obj.taskNumber) <=15}">${obj.taskNumber}</c:if>
						</td>
						<td class="tc">${obj.department }</td>
						<td class="tc">${obj.seq }</td>
						<td title="${obj.goodsName }">
					    <c:if test="${fn:length (obj.goodsName) > 18}">${fn:substring(obj.goodsName,0,17)}...</c:if>
              <c:if test="${fn:length(obj.goodsName) <=18}">${obj.goodsName}</c:if>
						</td>
						<td class="tc">${obj.item}</td>
						<td class="tc">${obj.purchaseCount }</td>
						<td class="tr">${obj.price }</td>
						<td class="tr">${obj.budget }</td>
						<td class="tc">
						   <c:if test="${obj.purchaseType eq '空值' }"></c:if>
						   <c:if test="${obj.purchaseType ne '空值' }">${obj.purchaseType }</c:if>
						</td>
						<td class="tc">${obj.organization }</td>
						<td class="tc"><fmt:formatDate value="${obj.taskGiveTime}" pattern="yyyy-MM-dd"/></td>
					</tr>
				</c:forEach>


			</table>

			<div id="pagediv" align="right"></div>
		</div>
	</div>


</body>
</html>

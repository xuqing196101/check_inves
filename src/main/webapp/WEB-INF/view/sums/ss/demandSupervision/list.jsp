<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp"%>
<!DOCTYPE HTML>
<html>

<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<title>需求监管列表</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript">

	/*分页  */
	$(function(){
		  laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${list.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${list.total}",
			    startRow: "${list.startRow}",
			    endRow: "${list.endRow}",
			    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			       
			        return "${list.pageNum}";
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			        	$("#page").val(e.curr);
                		$("#form1").submit();
			        }
			    }
			});
	  });


  	/** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		   if(checkAll.checked){
			   for(var i=0;i<checklist.length;i++)
			   {
			      checklist[i].checked = true;
			   } 
			 }else{
			  for(var j=0;j<checklist.length;j++)
			  {
			     checklist[j].checked = false;
			  }
		 	}
		}
	
	/** 单选 */
	function check(){
		 var count=0;
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("checkAll");
		 for(var i=0;i<checklist.length;i++){
			   if(checklist[i].checked == false){
				   checkAll.checked = false;
				   break;
			   }
			   for(var j=0;j<checklist.length;j++){
					 if(checklist[j].checked == true){
						   checkAll.checked = true;
						   count++;
					   }
				 }
		   }
	}

	//重置
	function resetResult() {
		$("#name").val("");
		$("#archiveCode").val("");
		$("#contractCode").val("");
		//$("#planCode").val("");
		var status = document.getElementById("status").options;
		status[0].selected = true;
	}

	//保存
	function save() {
		var type = $("#type").val();
		var info = document.getElementsByName("info");
		var str = "";
		for (var i = 0; i < info.length; i++) {
			if (info[i].checked == true) {
				str = info[i].value;
			}
		}
		$
				.ajax({
					type : "POST",
					dataType : "json",
					url : "${pageContext.request.contextPath }/purchaseArchive/leadArchive.do?id="
							+ str + "&type=" + type,
					success : function(data) {
						if (data == 1) {
							layer.msg("归档成功", {
								offset : [ '40%', '45%' ]
							});
							window.setTimeout(function() {
								window.location.reload();
							}, 1000);
						} else {
							var obj = new Function("return" + data)();
							$("#errorType").html(obj.type);
						}
					}
				});
	}

	//取消
	function cancel() {
		layer.closeAll();
	}

	//采购档案录入
	function add() {
		window
				.setTimeout(
						function() {
							window.location.href = "${pageContext.request.contextPath }/purchaseArchive/add.html";
						}, 500);
	}

	
</script>
</head>

<body>
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0);">首页</a></li>
				<li><a href="javascript:void(0);">业务监管</a></li>
				<li><a href="javascript:void(0);">采购业务监督</a></li>
				<li><a href="javascript:void(0);">采购需求监督</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="container">
		<div class="headline-v2">
			<h2>需求计划列表</h2>
		</div>

		<form id="form1" name="form1"
			action="${pageContext.request.contextPath }/supervision/demandSupervisionList.html"
			method="post">

			<div class="search_detail">
				<ul class="demand_list">
					<li><label class="fl">需求名称：</label><span><input
							type="text" id="name" name="name" class="" /></span></li>
					<li><label class="fl">产品名称：</label><span><input
							type="text" id="GOODS_NAME" name="GOODS_NAME" class="" /></span></li>
					<li><label class="fl">产品目录：</label><span><input
							type="text" id="contractCode" name="contractCode" class="" /></span></li>
					<li><label class="fl">预算金额：</label><span><input
							type="text" id="contractCode" name="contractCode" class="" /></span></li>
					<li style="width: 255px"><label class="fl">采购方式：</label> <span>
							<select id="status" name="status">
								<option value="">请选择</option>
								<option value="1">暂存</option>
								<option value="2">审核通过</option>
								<option value="3">审核不通过</option>
								<option value="4">已归档</option>
								<option value="5">已提交</option>
						</select>
					</span></li>
					<li><label class="fl">预算金额：</label><span><input
							type="text" id="contractCode" name="contractCode" class="" /></span></li>
					<li><label class="fl">填报时间：</label><span><input
							type="text" id="contractCode" name="contractCode" class="w80" /><span
							class="fl">到</span><input type="text" id="contractCode"
							name="contractCode" class="w80" /></span></li>
					<li class="fr w220 tc">
						<button class="btn fr" type="submit">查询</button>
						<button class="btn fr" type="button" onclick="resetResult()">重置</button>
					</li>
				</ul>
			</div>
            <input type="hidden" name="page" id="page">
			<div class="clear"></div>
		</form>

		<div class="content table_box">
			<table class="table table-bordered table-condensed table-hover">
				<thead>
					<tr class="info">
						<th class="w50"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
						<th class="w50">序号</th>
						<th>需求计划名称</th>
						<th>需求计划文号</th>
						<th>需求单位</th>
						<th>预算总金额（万元）</th>
						<th>任务性质</th>
						<th>填报人</th>
						<th>状态</th>
						<th>查看</th>

					</tr>
				</thead>
				<tbody>
					<c:forEach items="${list.list}" var="items" varStatus="status">
						<tr class="tc">
							<td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${items.id}" /></td>
							<td class="tc w50"  >${(status.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
							<td>${items.planName}</td>
							<td>${items.referenceNo}</td>
							<td>${items.department}</td>
							<td>${items.budget}</td>
							<td>${items.goodsType}</td>
							<td>${items.userId}</td>
							<td>${items.status}</td>
							<td>查看</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div id="pagediv" align="right"></div>
	</div>

	<ul class="list-unstyled list-flow dnone mt10" id="guidang">
		<li class="col-md-12 ml15"><span class="span3 fl mt5"><div
					class="red star_red">*</div>档案类型：</span> <select id="type" name="type"
			class="w178 fl">
				<option value="">请选择</option>
				<option value="1">临时档案</option>
				<option value="2">永久档案</option>
		</select>
			<div class="clear red" id="errorType"></div></li>
		<div class="col-md-12 mt10 tc">
			<button class="btn btn-windows save" type="button" onclick="save()">保存</button>
			<button class="btn btn-windows cancel" type="button"
				onclick="cancel()">取消</button>
		</div>
	</ul>
</body>

</html>
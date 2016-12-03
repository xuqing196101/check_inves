<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
</head>
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
		    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	$("#page").val(e.curr);
		            location.href = '${pageContext.request.contextPath}/intelligentScore/scoreModelList.html?page='+e.curr+'&packageId=${scoreModel.packageId}';
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
  	function view(id){
  		window.location.href="${pageContext.request.contextPath}/purchaseManage/showStationMessage.do?id="+id[0]+"&&type='view'";
  	}
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			window.location.href="${pageContext.request.contextPath}/purchaseManage/editPurchaseDep.do?id="+id[0]+"&&type='edit'";
		}else if(id.length>1){
			layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
		}else{
			layer.alert("请选择需要修改的用户",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function del(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="${pageContext.request.contextPath}/StationMessage/deleteSoftSMIsDelete.do?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的用户",{offset: ['222px', '390px'], shade:0.01});
		}
    }
    function add(){
    	window.location.href="${pageContext.request.contextPath}/purchaseManage/addPurchaseDep.do";
    }
    function show(id){
    	window.location.href="${pageContext.request.contextPath}/purchaseManage/showPurchaseDep.do?id="+id+"&&type=view";
    }
    function addPurchase(){
    	window.location.href="${pageContext.request.contextPath}/purchase/add.html?origin=1";
    }
    function resetQuery(){
		$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	}
	function submit() {
		$("#form1").submit();
	}
	function purchaseStash(quaStatus){
		var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
		}else if(id.length>1){
			layer.alert("只能选择一条记录",{offset: ['222px', '390px'], shade:0.01});
			return;
		}else{
			layer.alert("请选择一条记录",{offset: ['222px', '390px'], shade:0.01});
			return ;
		}
		var title = "确定暂停采购机构资质吗?";
		var status = $("#"+id[0]).text().trim();
		if(status=="终止"){
			layer.alert("已终止不可暂停",{offset: ['222px', '390px'], shade:0.01});
			return ;
		}
		if(status=="暂停"){
			layer.alert("已是暂停状态",{offset: ['222px', '390px'], shade:0.01});
			return ;
		}
		 layer.open({
			type : 2, //page层
			area : [ '500px', '300px' ],
			title : title,
			shade : 0.01, //遮罩透明度
			moveType : 1, //拖拽风格，0是默认，1是传统拖动
			shift : 1, //0-6的动画形式，-1不开启
			offset : [ '220px', '630px' ],
			shadeClose : true,
			content : '${pageContext.request.contextPath}/purchaseManage/updateQuateStatus.html?id='+id[0]+'&quaStatus=0'
		 });
		
	}
	function purchaseNormal(quaStatus){
		var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
		}else if(id.length>1){
			layer.alert("只能选择一条记录",{offset: ['222px', '390px'], shade:0.01});
			return;
		}else{
			layer.alert("请选择一条记录",{offset: ['222px', '390px'], shade:0.01});
			return ;
		}
		var title = "确定要启用采购机构资质吗";
		var status = $("#"+id[0]).text().trim();
		if(status=="终止"){
			layer.alert("已终止不可启用",{offset: ['222px', '390px'], shade:0.01});
			return ;
		}
		if(status=="正常"){
			layer.alert("已是正常状态",{offset: ['222px', '390px'], shade:0.01});
			return ;
		}
		layer.open({
			type : 2, //page层
			area : [ '500px', '300px' ],
			title : title,
			shade : 0.01, //遮罩透明度
			moveType : 1, //拖拽风格，0是默认，1是传统拖动
			shift : 1, //0-6的动画形式，-1不开启
			offset : [ '220px', '630px' ],
			shadeClose : true,
			content : '${pageContext.request.contextPath}/purchaseManage/updateQuateStatus.html?id='+id[0]+'&quaStatus=1'
		});
	}
	function purchaseTerminal(quaStatus){
		if(quaStatus!=null && quaStatus!='' && quaStatus==0){
			layer.alert("已经暂定，不可终止，请恢复后在终止操作",{offset: ['222px', '390px'], shade:0.01});
			return;
		}
		var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length==1){
			
		}else if(id.length>1){
			layer.alert("只能选择一条记录",{offset: ['222px', '390px'], shade:0.01});
			return;
		}else{
			layer.alert("请选择一条记录",{offset: ['222px', '390px'], shade:0.01});
			return ;
		}
		var title = "确定终止采购机构资质吗";
		var status = $("#"+id[0]).text().trim();
		if(status=="终止"){
			layer.alert("已是终止状态",{offset: ['222px', '390px'], shade:0.01});
			return ;
		}
		if(status=="暂停"){
			layer.alert("暂停状态不可终止",{offset: ['222px', '390px'], shade:0.01});
			return ;
		}
		layer.open({
			type : 2, //page层
			area : [ '500px', '300px' ],
			title : title,
			shade : 0.01, //遮罩透明度
			moveType : 1, //拖拽风格，0是默认，1是传统拖动
			shift : 1, //0-6的动画形式，-1不开启
			offset : [ '220px', '630px' ],
			shadeClose : true,
			content : '${pageContext.request.contextPath}/purchaseManage/updateQuateStatus.html?id='+id[0]+'&quaStatus=2'
		 });
	}
  </script>
<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="#"> 首页</a>
					</li>
					<li><a href="#">支撑系统</a>
					</li>
					<li><a href="#">后台管理</a>
					</li>
					<li class="active"><a href="#">采购机构管理</a>
					</li>
				</ul>
			</div>
		</div>
		
		<div class="container">
			 <div class="headline-v2">
				<h2>采购机构列表</h2>
		      </div>
		      <h2 class="search_detail">
			       <form action="${pageContext.request.contextPath}/purchaseManage/purchaseUnitList.html" method="post" id="form1" enctype="multipart/form-data" class="mb0">
			       <input type="hidden" name="page" id="page">
			        <ul class="demand_list">
			          <li>
			            <label class="fl">名称：</label><span><input type="text" name="name" value="${purchaseDep.name }"></span>
			          </li>
			         <button type="button" onclick="submit()" class="btn">查询</button>
					<button type="button" onclick="resetQuery();" class="btn">重置</button>
			        </ul>
			        <div class="clear"></div>
			       </form>
			       
			       <input type="hidden" name="flag" value="0">
			  </h2>
			<!-- 表格开始-->
			 <div class="col-md-12 pl20 mt10">
					<button class="btn btn-windows edit" type="button"
						onclick="edit();">修改</button>
					<button class="btn btn-windows delete" type="button"
						onclick="dell();">删除</button>
					<button class="btn btn-windows add" type="button" onclick="addPurchase();">人员管理</button>
					<button class="btn btn-windows edit" type="button" onclick="purchaseStash()">资质暂停</button>
					<button class="btn btn-windows edit" type="button" onclick="purchaseNormal()">资质启用</button>
					<button class="btn btn-windows edit" type="button" onclick="purchaseTerminal()">资质终止</button>
					
			</div>

			<div class="content table_box">
                <table class="table table-bordered table-condensed table-hover table-striped">
							<thead>
								<tr>
									<th class="info w30"><input id="checkAll" type="checkbox"
										onclick="selectAll()" />
									</th>
									<th class="info w50">序号</th>
									<th class="info">采购机构名称</th>
									<th class="info">邮编</th>
									<th class="info">单位地址</th>
									<th class="info">采购业务范围</th>
									<th class="info">采购资质编号</th>
									<th class="info">采购业务等级</th>
									<th class="info">采购资质范围</th>
									<th class="info">资质状态</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${purchaseDepList}" var="p" varStatus="vs">
									<tr class="cursor">
										<!-- 选择框 -->
										<td class="tc"><input
											type="checkbox" name="chkItem" value="${p.id}" />
										</td>
										<!-- 序号 -->
										<td class="tc" onclick="show('${p.id}');">${vs.index+1}</td>
										<!-- 标题 -->
										<td class="tc" >
										<a href="javascript:void(0)" onclick="show('${p.id}');">${p.name}</a>
										</td>
										<!-- 内容 -->
										<td class="tc" onclick="show('${p.id}');">${p.postCode}</td>
										<!-- 创建人-->
										<td class="tc" onclick="show('${p.id}');">${p.address}</td>
										<!-- 是否发布 -->
										<td class="tc" onclick="show('${p.id}');">${p.businessRange}</td>
										<!-- 是否发布 -->
										<td class="tc" onclick="show('${p.id}');">${p.quaCode}</td>
										<!-- 是否发布 -->
										<td class="tc" onclick="show('${p.id}');">${p.quaLevel}</td>
										<!-- 是否发布 -->
										<td class="tc" onclick="show('${p.id}');">${p.quaRange}</td>
										<td class="tc" onclick="show('${p.id}');" id="${p.id }">
											<c:choose>
												<c:when test="${p.quaStatus==0}">
													暂停
												</c:when>
												<c:when test="${p.quaStatus==1}">
													正常
												</c:when>
												<c:when test="${p.quaStatus==2}">
													终止
												</c:when>
												<c:otherwise>
													正常
												</c:otherwise>
											</c:choose>
										
										</td>
									</tr>
								</c:forEach>
							</tbody>
					</table>
					<!-- <div id="page" align="right"></div> -->
					<div id="pagediv" align="right"></div>
					<%-- <p class="pagestyle">${pagesql}</p> --%>

				</div>
			</div>
</body>
</html>

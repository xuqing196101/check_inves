<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
</head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript">
   $(function() {
        laypage({
          cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
          pages: "${info.pages}", //总页数
          skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
          skip: true, //是否开启跳页
          total: "${info.total}",
          startRow: "${info.startRow}",
          endRow: "${info.endRow}",
          groups: "${info.pages}" >= 3 ? 3 : "${info.pages}", //连续显示分页数
          curr: function() { //通过url获取当前页，也可以同上（pages）方式获取
            return "${info.pageNum}";
          }(),
          jump: function(e, first) { //触发分页后的回调
            if(!first) { //一定要加此判断，否则初始时会无限刷新
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
  	function view(id){
  		window.location.href="${pageContext.request.contextPath}/purchaseManage/showStationMessage.do?id="+id[0]+"&&type='view'";
  	}
  	
  	/** 编辑 **/
    function edit(){
    	var auth='${authType}';
  	    if(auth !='4'){
  	    layer.msg("只有资源服务中心可以操作");
  	    return;
  	    }
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length == 1){
			window.location.href="${pageContext.request.contextPath}/purchaseManage/editPurchaseDep.do?id="+id[0]+"&&type='edit'";
		}else{
			layer.msg("请选择一个采购机构进行编辑");
		}
    }
    
    /** 编辑 **/
    function del(){
    	var auth='${authType}';
  	    if(auth !='4'){
  	    layer.msg("只有资源服务中心可以操作");
  	    return;
  	    }
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {
				btn:['确认','取消']
			},function(){
				delPurchaseDept(ids.toString());
			});
		}else{
			layer.msg("请选择需要删除的采购机构");
		}
    }
    
    /** ajax删除 */
    function delPurchaseDept(ids){
    	$.ajax({
		    type: 'post',
		    url: "${pageContext.request.contextPath}/purchaseManage/delPurchaseDep.do",
		    data : {id:ids},
		    success: function(msg) {
		    	if (msg == "ok"){
		    		layer.msg("删除成功");
		    		window.location.href= "${pageContext.request.contextPath}/purchaseManage/purchaseUnitList.html";
		    	} else {
		    		layer.msg(msg);
		    	}
		       
		    }
		});
    }
    
    /** 新增 **/
    function add(){
    	var auth='${authType}';
  	    if(auth !='4'){
  	    layer.msg("只有资源服务中心可以操作");
  	    return;
  	    }
    	window.location.href="${pageContext.request.contextPath}/purchaseManage/addPurchaseDep.html";
    }
    
    /** 详情 **/
    function show(id){
    	var auth='${authType}';
  	    if(auth !='4'){
  	    layer.msg("只有资源服务中心可以操作");
  	    return;
  	    }
    	window.location.href="${pageContext.request.contextPath}/purchaseManage/showPurchaseDep.do?id="+id+"&&type=view";
    }
    
    /** 采购机构人员管理 **/
    function addPurchase(){
    	var auth='${authType}';
  	    if(auth !='4'){
  	    layer.msg("只有资源服务中心可以操作");
  	    return;
  	    }
  	    var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			if(ids.length == 1){
				window.location.href="${pageContext.request.contextPath}/purchase/list.html?id="+ids[0];
			}else{
				layer.msg("只能选择一个采购机构");
			}
		}else{
			layer.msg("请选择一个采购机构");
		}
  	    
    }
    
    /**  重置查询条件  **/
    function resetQuery(){
    	var auth='${authType}';
  	    if(auth !='4'){
  	    layer.msg("只有资源服务中心可以操作");
  	    return;
  	    }
		$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	}
    /** 查询 **/
	function submit() {
		var auth='${authType}';
  	    if(auth !='4'){
  	    layer.msg("只有资源服务中心可以操作");
  	    return;
  	    }
		$("#form1").submit();
	}
    
    /** 更改状态 **/
	function purchaseStash(opera,title,status){
		var auth='${authType}';
  	    if(auth !='4'){
  	    layer.msg("只有资源服务中心可以操作");
  	    return;
  	    }
		var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		if(id.length != 1){
			layer.msg("请选择一个采购机构进行" + opera);
			return ;
		}
		
		var qStatus = null;
		$('input[name="chkItem"]:checked').each(function(){
			qStatus = $(this).parents('tr').find('input[name=qStatus]').val();
		});
		
		var title = title;
		if (qStatus == status){
			layer.msg("该采购机构的资质状态已经是" + opera +"，无法" + opera);
			return ;
		}
		layer.open({
			type : 2, 
			area : [ '600px', '330px' ],
			title : title,
			offset : [ '30px', '400px' ],
			shadeClose : true,
			content : '${pageContext.request.contextPath}/purchaseManage/updateQuateStatus.html?id='+id[0]+'&quaStatus=' + status
		 });
		
	}
    
    /** 更新状态 **/
    function updateStatus(status,text){
    	$('input[name="chkItem"]:checked').each(function(){
			qStatus = $(this).parents('tr').find('input[name=qStatus]').val(status);
			qStatus = $(this).parents('tr').find('td').eq(9).text(text);
		});
    }
  </script>
<body>
		
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a></li>
					<li><a href="javascript:void(0);">支撑系统</a> </li>
					<li><a href="javascript:void(0)">机构管理</a></li>
					<li class="active"><a href="javascript:jumppage('${pageContext.request.contextPath}/purchaseManage/purchaseUnitList.html')">采购机构管理</a></li>
				</ul>
			</div>
		</div>
		
		<div class="container">
			 <div class="headline-v2">
				<h2>采购机构列表</h2>
		      </div>
		      <h2 class="search_detail">
			       <form action="${pageContext.request.contextPath}/purchaseManage/purchaseUnitList.html" method="post" id="form1" enctype="multipart/form-data" class="mb0">
			       <input type="hidden" name="page" id="page"/>
			        <ul class="demand_list">
			          <li>
			            <label class="fl">名称：</label><span><input type="text" name="name" value="${purchaseDep.name }"></span>
			          </li>
			         <button type="button" onclick="submit()" class="btn fl mt1">查询</button>
					 <button type="button" onclick="resetQuery();" class="btn  fl mt1">重置</button>
			        </ul>
			        <div class="clear"></div>
			       </form>
			       
			       <input type="hidden" name="flag" value="0">
			  </h2>
			<!-- 表格开始-->
			 <div class="col-md-12 pl20 mt10">
			 		<button class="btn btn-windows add"   type="button" onclick="add();">新增</button>
					<button class="btn btn-windows edit"   type="button" onclick="edit();">修改</button>
					<button class="btn btn-windows delete" type="button" onclick="del();">删除</button>
					<button class="btn btn-windows add"    type="button" onclick="addPurchase();">人员管理</button>
					<button class="btn btn-windows edit"   type="button" onclick="purchaseStash('暂停','暂停资质','1')">资质暂停</button>
					<button class="btn btn-windows edit"   type="button" onclick="purchaseStash('启用','资质启用','0')">资质启用</button>
					<button class="btn btn-windows edit"   type="button" onclick="purchaseStash('终止','资质终止','2')">资质终止</button>
			</div>
			<div class="content table_box">
                <table class="table table-bordered table-condensed table-hover table-striped break-all">
							<thead>
								<tr>
									<th class="info w30"><input id="checkAll" type="checkbox"
										onclick="selectAll()" />
									</th>
									<th class="info w50">序号</th>
									<th class="info w150">采购机构名称</th>
									<th class="info w80">邮编</th>
									<th class="info w120">单位地址</th>
									<th class="info w120">采购业务范围</th>
									<th class="info w120">采购资质编号</th>
									<th class="info w120">采购业务等级</th>
									<th class="info w120">采购资质范围</th>
									<th class="info w120">资质状态</th>
								</tr>
							</thead>
							<c:if test="${authType == 4}">
							<tbody>
								<c:forEach items="${info.list}" var="p" varStatus="vs">
									<tr class="cursor">
									  <input type="hidden" name="qStatus" value="${p.quaStatus}"/>
										<td class="tc"><input
											type="checkbox" name="chkItem" value="${p.id}" />
										</td>
										<td class="tc w50">${(vs.index+1)+(info.pageNum-1)*(info.pageSize)}</td>
										<td class="tl pl20" >
										<a href="javascript:void(0)" onclick="show('${p.id}');">${p.name}</a>
										</td>
										<td class="tc" onclick="show('${p.id}');">${p.postCode}</td>
										<td class="tl pl20" onclick="show('${p.id}');">${p.areaName}${p.address}</td>
										<td width="250px" onclick="show('${p.id}');">${p.businessRange}</td>
										<td class="tl pl20" onclick="show('${p.id}');">${p.quaCode}</td>
										<td class="tc" onclick="show('${p.id}');">
										  <c:if test="${p.quaLevel == '1'}">
										        一级
										  </c:if>
										  <c:if test="${p.quaLevel == '2'}">
										        二级
										  </c:if>
										  <c:if test="${p.quaLevel == '3'}">
										        三级
										  </c:if>
										  <c:if test="${p.quaLevel == '4'}">
										        四级
										  </c:if>
										  <c:if test="${p.quaLevel == '5'}">
										        五级
										  </c:if>
										  <c:if test="${p.quaLevel == '6'}">
										        六级
										  </c:if>
										</td>
										<td class="tc" onclick="show('${p.id}');">
										  <c:if test="${p.quaRange == '1'}">
										          综合
										  </c:if>
										  <c:if test="${p.quaRange == '2'}">
										          物资
										  </c:if>
										  <c:if test="${p.quaRange == '3'}">
										          工程
										  </c:if>
										  <c:if test="${p.quaRange == '4'}">
										          服务
										  </c:if>
										</td>
										<td class="tc" onclick="show('${p.id}');" id="${p.id }">
											<c:choose>
												<c:when test="${p.quaStatus == 0}">
													正常
												</c:when>
												<c:when test="${p.quaStatus == 1}">
													暂停
												</c:when>
												<c:when test="${p.quaStatus == 2}">
													终止
												</c:when>
											</c:choose>
										
										</td>
									</tr>
								</c:forEach>
							</tbody>
					</c:if>
					</table>
				</div>
				<c:if test="${authType == 4}">
				<div id="pagediv" align="right"></div>
				</c:if>
			</div>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML PUBLIC>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp" %> 
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
		        //var page = location.search.match(/page=(\d+)/);
		        //return page ? page[1] : 1;
		        return "${list.pageNum}";
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	$("#page").val(e.curr);
		            //location.href = '${pageContext.request.contextPath}/purchase/list.html?page='+e.curr;
		        	$("#form1").submit();
		        }
		    }
		});
  });
    	
   /** 全选全不选 */
	function selectAll(){
		 var checklist = document.getElementsByName ("chkItem");
		 var checkAll = document.getElementById("allId");
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
	
	function submit() {
		var auth='${authType}';
  	    if(auth !='4'){
  	    layer.msg("只有资源服务中心可以操作");
  	    return;
  	    }
		$("#form1").submit();
	}
	function chongzhi() {
		var auth='${authType}';
  	    if(auth !='4'){
  	    layer.msg("只有资源服务中心可以操作");
  	    return;
  	    }
		$("#relName").val('');
		$("#purchaseDepName").val('');
	}
	function add(){
		var auth='${authType}';
  	    if(auth !='4'){
  	    layer.msg("只有资源服务中心可以操作");
  	    return;
  	    }
  	    var purchaseDepId = $("#purchaseDepId").val();
  	    if(purchaseDepId != null && purchaseDepId != ''){
  	    	window.location.href="${pageContext.request.contextPath}/purchase/add.html?origin=1&cggl=cggl&orgId="+purchaseDepId;
  	    }else{
    		window.location.href="${pageContext.request.contextPath}/purchase/add.do";
  	    }
    }
    function edit(){
    	var auth='${authType}';
  	    if(auth !='4'){
  	    layer.msg("只有资源服务中心可以操作");
  	    return;
  	    }
    	var id = []; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		
		if(id.length !=1){
			layer.msg("请选择一条记录进行编辑");
			return;
		}
  	    var purchaseDepId = $("#purchaseDepId").val();
  	    if(purchaseDepId != null && purchaseDepId != ''){
  	    	window.location.href = "${pageContext.request.contextPath}/purchase/edit.html?id=" + id[0] + "&origin=1&cggl=cggl&orgId="+purchaseDepId;
  	    }else{
			window.location.href = "${pageContext.request.contextPath}/purchase/edit.html?id=" + id[0]; 
  	    }
    }
    function delPurchaser(){
    	var auth='${authType}';
  	    if(auth !='4'){
  	    layer.msg("只有资源服务中心可以操作");
  	    return;
  	    }
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length > 0){
			layer.confirm('您确定要删除吗?', {
				btn:['确认','取消']
			},function(){
				ajaxDelUser(ids.toString());
			});
		}else{
			layer.alert("请选择需要删除的用户");
		}
    }
    
    function ajaxDelUser(idstr){
    	$.ajax({
		    type: 'post',
		    url: "${pageContext.request.contextPath}/purchase/delajax.do",
		    data : {ids:idstr},
		    success: function(data) {
		    	if (data.success){
		    		layer.msg("删除成功");
		    		var purchaseDepId = $("#purchaseDepId").val();
		      	    if(purchaseDepId != null && purchaseDepId != ''){
		      	    	window.location.href= "${pageContext.request.contextPath}/purchase/list.html?purchaseDepId="+purchaseDepId;
		      	    }else{
		    			window.location.href= "${pageContext.request.contextPath}/purchase/list.html";
		      	    }
		    	} else {
		    		layer.msg("删除失败");
		    	}
		       
		    }
		});
    }
    
	function resetQuery(){
		var auth='${authType}';
  	    if(auth !='4'){
  	    layer.msg("只有资源服务中心可以操作");
  	    return;
  	    }
		$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	}
	
	function back(){
		window.location.href= "${pageContext.request.contextPath}/purchaseManage/purchaseUnitList.html";
	}
	
	function show(id){
		var auth='${authType}';
  	    if(auth !='4'){
  	    layer.msg("只有资源服务中心可以操作");
  	    return;
  	    }
  	    var purchaseDepId = $("#purchaseDepId").val();
  	    if(purchaseDepId != null && purchaseDepId != ''){
  	    	window.location.href = "${pageContext.request.contextPath}/purchase/view.html?id=" + id + "&origin=1&cggl=cggl&orgId="+purchaseDepId;
  	    }else{
			window.location.href = "${pageContext.request.contextPath}/purchase/view.html?id=" + id; 
  	    }
	}
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
					<li><a href="javascript:void(0);">支撑系统</a>
					</li>
					<c:if test="${purchaseInfo.purchaseDepId == null || purchaseInfo.purchaseDepId == ''}">
					<li><a href="javascript:void(0);">人员管理</a>
					</li>
					<li class="active"><a href="javascript:void(0);" onclick="jumppage('${pageContext.request.contextPath}/purchase/list.html')">采购人管理</a>
					</li>
					</c:if>
					<c:if test="${purchaseInfo.purchaseDepId != null and purchaseInfo.purchaseDepId != ''}">
					<li><a href="javascript:void(0)">机构管理</a></li>
					<li><a href="javascript:void(0)">采购机构人员管理</a></li>
					</c:if>
				</ul>
			</div>
		</div>
		<div class="container">
			<div class="headline-v2">
				<h2>采购机构人员列表</h2>
			</div>
				<h2 class="search_detail">
				<form action="${pageContext.request.contextPath}/purchase/list.html" method="post" id="form1" class="mb0">
				<input type="hidden" name="page" id="page"/> 
				<input type="hidden" name="purchaseDepId" id="purchaseDepId" value="${purchaseInfo.purchaseDepId }"/>
        <div class="m_row_5">
        <div class="row">
          <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">姓名：</div>
              <div class="col-xs-8 f0 lh0">
                <input type="text" name="relName" id="relName" value="${purchaseInfo.relName }" class="w100p h32 f14 mb0">
              </div>
            </div>
          </div>
          
          <c:if test="${purchaseInfo.purchaseDepId == null || purchaseInfo.purchaseDepId == ''}">
          <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
            <div class="row">
              <div class="col-xs-4 f14 h32 lh32 tr text-nowrapEl">采购机构名称：</div>
              <div class="col-xs-8 f0 lh0">
                <input type="text" name="purchaseDepName" id="purchaseDepName" value="${purchaseInfo.purchaseDepName }" class="w100p h32 f14 mb0">
              </div>
            </div>
          </div>
          </c:if>
          
          <div class="col-xs-12 col-sm-4 col-md-4 col-lg-3">
            <div class="row">
              <div class="col-xs-12 f0">
                <button type="button" onclick="submit();" class="btn mb0 h32">查询</button>
                <button type="button" onclick="resetQuery();" class="btn mb0 mr0 h32">重置</button>
              </div>
            </div>
          </div>
        </div>
        </div>
				</form>
				</h2>
			<!-- 表格开始-->
				<div class="col-md-12 pl20 mt10">
					<button class="btn btn-windows add" type="button"
						onclick="add();">新增</button>
					<button class="btn btn-windows edit" type="button"
						onclick="edit();">修改</button>
					<button class="btn btn-windows delete" type="button"
						onclick="delPurchaser();">删除</button>
						<c:if test="${purchaseInfo.purchaseDepId != null and purchaseInfo.purchaseDepId != ''}">
						<button type="button" class="btn btn-windows back" onclick="back();">返回</button>
						</c:if>
				</div>
			<div class="content table_box">
                 <table class="table table-bordered table-condensed table-hover table-striped">
					<thead>
						<tr>
							<th class="info w30"><input type="checkbox"
								onclick="selectAll();" id="allId" alt=""></th>
							<th class="info w50">序号</th>
							<th class="info">姓名</th>
							<th class="info">所属采购机构</th>
							<th class="info">人员类别</th>
							<th class="info">性别</th>
							<th class="info">职务</th>
							<th class="info">职称</th>
							<th class="info">采购资质等级</th>
							<th class="info">学历</th>
							<th class="info">办公号码</th>
							<th class="info">资质证书编号</th>
						</tr>
					</thead>
					<c:if test="${authType == 4}">
					<tbody>
						<c:forEach items="${purchaseList}" var="p" varStatus="vs">
							<tr class="cursor">
								<td class="tc"><input type="checkbox" name="chkItem" value="${p.id}" /></td>
								<td class="tc" onclick="show('${p.id}');">${vs.index+1}</td>
								<td class="tc" onclick="show('${p.id}');">${p.relName}</td>
								<td class="tl pl20" onclick="show('${p.id}');">${p.purchaseDepName}</td>
								<td class="tc" onclick="show('${p.id}');">
									<c:choose>
										<c:when test="${p.purcahserType== 0 }">
											军人
										</c:when>
										<c:when test="${p.purcahserType== 1 }">
											文职
										</c:when>
										<c:when test="${p.purcahserType== 2 }">
											职工
										</c:when>
										<c:when test="${p.purcahserType== 3 }">
											战士
										</c:when>
									</c:choose>
								</td>
								<c:forEach items="${genders}" var="g" >
									<c:if test="${g.id eq p.gender}">
									  <td class="tc" onclick="show('${p.id}');">${g.name}</td>
									</c:if>
					        	</c:forEach>
								<td class="tl pl20" onclick="show('${p.id}');">${p.duties}</td>
								<td class="tc" onclick="show('${p.id}');">${p.professional}</td>
								<td class="tc" onclick="show('${p.id}');">
									<c:choose>
										<c:when test="${p.quaLevel==0}">
											初
										</c:when>
										<c:when test="${p.quaLevel==1}">
											中
										</c:when>
										<c:when test="${p.quaLevel==2}">
											高
										</c:when>
									</c:choose>
								</td>
								<td class="tc" onclick="show('${p.id}');">${p.topStudy}</td>
								<td class="tc" onclick="show('${p.id}');">${p.telephone}</td>
								<td class="tl pl20" onclick="show('${p.id}');">${p.quaCode}</td>
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

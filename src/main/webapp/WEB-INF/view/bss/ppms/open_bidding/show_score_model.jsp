<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
		<%@ include file="/WEB-INF/view/common.jsp"%>
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
		$("#form1").submit();
	}
	function chongzhi() {
		$("#relName").val('');
		$("#purchaseDepName").val('');
	}
	function add(){
    	window.location.href="${pageContext.request.contextPath}/purchase/add.do";
    }
    function edit(){
    	var id=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			id.push($(this).val());
		}); 
		
		if(id.length==1){
			$("#purchaseid").val(id[0]);
			$("#hideform").submit();
			//window.location.href="${pageContext.request.contextPath}/purchaseManage/editPurchaseDep.do?id="+id+"&&type='edit'";
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
		var idstr="";
		if(ids.length>0){
			for(var i=0;i<ids.length;i++){
		    	idstr += ids[i];
		    	idstr += ",";
		    }
		    idstr = idstr.substr(0,idstr.length-1);
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				$.ajax({
				    type: 'post',
				    url: "${pageContext.request.contextPath}/purchase/delajax.do",
				    data : {ids:idstr},
				    //data: {'pid':pid,$("#formID").serialize()},
				    success: function(data) {
				        truealert(data.message,data.success == false ? 5:1);
				    }
				});
			});
		}else{
			layer.alert("请选择要删除的用户",{offset: ['222px', '390px'], shade:0.01});
		}
    }
	function truealert(text,iconindex){
		layer.open({
		    content: text,
		    icon: iconindex,
		    shade: [0.3, '#000'],
		    yes: function(index){
		        //do something
		         //parent.location.reload();
		    	 layer.closeAll();
		    	 //parent.layer.close(index); //执行关闭
		    	 parent.location.href="${pageContext.request.contextPath}/purchase/list.do";
		    }
		});
	}
	function resetQuery(){
		$("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("").removeAttr("checked").removeAttr("selected");
	}
	function pageOnLoad(){
		var markTermTypeName = $("#show_mark_term_type_name").val();
		var typeName = $("#show_type_name").val();
		if(markTermTypeName!=null && markTermTypeName!="" && markTermTypeName!=undefined){
			$("#m_type_name").val(markTermTypeName);
		}
		if(typeName!=null && typeName!="" && typeName!=undefined){
			$("#type_name").val(typeName);
		}
	}
	function prevStep(){
		//window.history.back(-1); 
		window.location.href = "${pageContext.request.contextPath}/intelligentScore/packageList.html?projectId=${projectId}" ;
	}
	//查看模型详细信息
	function showScoreModel(id){
		window.location.href = "${pageContext.request.contextPath}/intelligentScore/showScoreModel.html?id="+id ;
	}
</script>
</head>

<body onload="pageOnLoad();">
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0);"> 首页</a></li>
				<li><a href="javascript:void(0);">保障作业</a></li>
				<li><a href="javascript:void(0);">采购项目管理</a></li>
				<li><a href="javascript:void(0);">立项管理</a></li>
				<li class="active"><a href="javascript:void(0);">评分细则</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<!-- 我的订单页面开始-->
	<div class="container">
		<div class="headline-v2">
			<h2>打分项关联模型列表</h2>
		</div>
		<h2 class="search_detail">
			<form action="${pageContext.request.contextPath}/intelligentScore/scoreModelList.html" method="post" class="mb0" id="form1">
				<input type="hidden" name="page" id="page">
				<input type="hidden" name="packageId" id="packageId" value="${scoreModel.packageId}">
				<input type="hidden" id="show_mark_term_type_name" value="${scoreModel.markTerm.typeName}">
				<input type="hidden" id="show_type_name" value="${scoreModel.typeName}">
				<input type="hidden" name="proid" value="${projectId}">
				<ul class="demand_list">
					<li><label class="fl">打分项名称：</label><span><input name="markTerm.name" value="${scoreModel.markTerm.name }"
							type="text" id="topic" class="" />
					</span></li>

					<li><label class="fl">类型：</label> <span class="fl">
							<select name="markTerm.typeName" id="m_type_name">
								<option value="">请选择</option>
								<option value="0">商务</option>
								<option value="1">技术</option>
								
						</select> </span></li>
					<li><label class="fl">模型：</label> <span class="fl">
							<select class="w178" name="typeName" id="type_name">
								<option value="">请选择</option>
								<option value="0">模型1:是否判断</option>
								<option value="1">模型2:按项加减分</option>
								<option value="2">模型3:评审数额最高递减</option>
								<option value="3">模型4:评审数额最低递增</option>
								<option value="4">模型5:评审数额高计算</option>
								<option value="5">模型6:评审数额低计算</option>
								<option value="6">模型7:评审数额低区间递增</option>
								<option value="7">模型8:评审数额高区间递减</option>
						</select> </span></li>

				</ul>

				<div class="col-md-12 clear tc mt10">
					<button type="button" onclick="submit()" class="btn">查询</button>
					<button type="button" onclick="resetQuery();" class="btn">重置</button>
					<button type="button" onclick="prevStep();" class="btn">返回</button>
				</div>
				<div class="clear"></div>
			</form>
		</h2>
		<!-- 表格开始-->
		<div class="content table_box">
			<table
				class="table table-bordered table-condensed table-hover table-striped">
				<thead>
						<tr>
							<!-- <th class="info w30"><input type="checkbox"
								onclick="selectAll();" id="allId" alt=""></th> -->
							<th class="info w50">序号</th>
							<th class="info">打分项名称</th>
							<th class="info">模型</th>
							<th class="info">类型</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${scoreModelList}" var="p" varStatus="vs">
							<tr class="cursor">
								<!-- 选择框 -->
								<%-- <td onclick="" class="tc"><input
									type="checkbox" name="chkItem" value="${p.id}" /></td> --%>
								<!-- 序号 -->
								<td class="tc">${vs.index+1}</td>
								<!-- 标题 -->
								<td class="tc">${p.markTerm.name}</td>
								<!-- 内容 -->
								<td class="tc">
									<a href="javascript:void(0)" onclick="showScoreModel('${p.id}');">
									<c:choose>
										<c:when test="${p.typeName==0}">
											模型1:是否判断
										</c:when>
										<c:when test="${p.typeName==1}">
											模型2:按项加减分
										</c:when>
										<c:when test="${p.typeName==2}">
											模型3:评审数额最高递减
										</c:when>
										<c:when test="${p.typeName==3}">
											模型4:评审数额最低递增
										</c:when>
										<c:when test="${p.typeName==4}">
											模型5:评审数额高计算
										</c:when>
										<c:when test="${p.typeName==5}">
											模型6:评审数额低计算
										</c:when>
										<c:when test="${p.typeName==6}">
											模型7:评审数额低区间递增
										</c:when>
										<c:when test="${p.typeName==7}">
											模型8:评审数额高区间递减
										</c:when>
										<c:otherwise>
											
										</c:otherwise>
									</c:choose>
									</a>
								</td>
								<td class="tc">
									<c:choose>
										<c:when test="${p.markTerm.typeName==0}">
											商务
										</c:when>
										<c:when test="${p.markTerm.typeName==1}">
											技术
										</c:when>
										<c:otherwise>
											
										</c:otherwise>
									</c:choose>
								</td>
							</tr>
						</c:forEach>
					</tbody>
			</table>

		</div>
		<div id="pagediv" align="right"></div>
	</div>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
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
		             location.href = '${pageContext.request.contextPath}/purchase/list.html?page='+e.curr;
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
</script>
</head>

<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li><a href="#"> 首页</a>
					</li>
					<li><a href="#">支撑系统</a>
					</li>
					<li><a href="#">机构管理</a>
					</li>
					<li class="active"><a href="#">采购人管理</a>
					</li>
				</ul>
			</div>
		</div>
		<div class="container">
			<div class="headline-v2">
				<h2>采购机构人员列表</h2>
			</div>
		<div>
			<form id="hideform" action="${pageContext.request.contextPath}/purchase/edit.html" method="post">
				<input type="hidden" id="purchaseid" name="id">
			</form>
		</div>
		<!-- hide  form -->
				<h2 class="search_detail">
					<form action="${pageContext.request.contextPath}/purchase/list.html" method="post" id="form1" enctype="multipart/form-data" class="mb0">
						<input type="hidden" name="page" id="page"/> 
						<ul class="demand_list">
							<li><label class="fl">姓名：</label><span><input type="text" name="relName" id="relName"
							value="${purchaseInfo.relName }"/>
							</span></li>
							<li><label class="fl">采购机构名称：</label><span><input type="text" name="purchaseDepName" id="purchaseDepName"
							value="${purchaseInfo.purchaseDepName }"/>
							</span>
							</li>
							<button type="button" onclick="submit();" class="btn">查询</button>
                            <button type="button" onclick="chongzhi();" class="btn">重置</button>
						</ul>
						<div class="clear"></div>
					</form>
				</h2>
			<!-- 表格开始-->
				<div class="col-md-12 pl20 mt10">
					<button class="btn btn-windows add" type="button"
						onclick="add();">新增</button>
					<button class="btn btn-windows edit" type="button"
						onclick="edit();">修改</button>
					<button class="btn btn-windows delete" type="button"
						onclick="del();">删除</button>
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
							<th class="info">类型</th>
							<th class="info">性别</th>
							<th class="info">年龄</th>
							<th class="info">职务</th>
							<th class="info">职称</th>
							<th class="info">采购资格等级</th>
							<th class="info">学历</th>
							<th class="info">电话</th>
							<!-- <th class="info">资质证书类型</th> -->
							<th class="info">证书编号</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach items="${purchaseList}" var="p" varStatus="vs">
							<tr class="cursor">
								<!-- 选择框 -->
								<td onclick="null" class="tc"><input
									type="checkbox" name="chkItem" value="${p.id}" /></td>
								<!-- 序号 -->
								<td class="tc" onclick="show('${p.id}');">${vs.index+1}</td>
								<!-- 标题 -->
								<td class="tc" onclick="show('${p.id}');">${p.relName}</td>
								<!-- 内容 -->
								<td class="tc" onclick="show('${p.id}');">${p.purchaseDepName}</td>
								<!-- 创建人-->
								<td class="tc" onclick="show('${p.id}');">
									<c:choose>
										<c:when test="${p.purcahserType==0}">
											军人
										</c:when>
										<c:when test="${p.purcahserType==1}">
											文职
										</c:when>
										<c:when test="${p.purcahserType==2}">
											职工
										</c:when>
										<c:when test="${p.purcahserType==3}">
											战士
										</c:when>
										<c:otherwise>
											
										</c:otherwise>
									</c:choose>
								</td>
								<!-- 是否发布 -->
								<td class="tc" onclick="show('${p.id}');"> 
									<c:choose>
										<c:when test="${p.gender=='M'}">
											男
										</c:when>
										<c:when test="${p.gender=='F'}">
											女
										</c:when>
										<c:otherwise>
											男
										</c:otherwise>
									</c:choose>
								</td>
								<!-- 是否发布 -->
								<td class="tc" onclick="show('${p.id}');">${p.age}</td>
								<!-- 是否发布 -->
								<td class="tc" onclick="show('${p.id}');">${p.duties}</td>
								<!-- 是否发布 -->
								<td class="tc" onclick="show('${p.id}');">${p.professional}</td>
								<!-- 是否发布 -->
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
										<c:when test="${p.quaLevel==3}">
											
										</c:when>
										<c:otherwise>
											
										</c:otherwise>
									</c:choose>
								</td>
								<!-- 创建人-->
								<td class="tc" onclick="show('${p.id}');">${p.topStudy}</td>
								<!-- 是否发布 -->
								<td class="tc" onclick="show('${p.id}');">${p.telephone}</td>
								<!-- 是否发布 -->
								<%-- <td class="tc" onclick="show('${p.id}');">${p.quaCode}</td> --%>
								<!-- 是否发布 -->
								<td class="tc" onclick="show('${p.id}');">${p.quaCode}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
		</div>
		<div id="pagediv" align="right"></div>
	</div>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
  <head>
  	<jsp:include page="/WEB-INF/view/common.jsp"/>
    <title>正式合同列表</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
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
    
  	function delDraft(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="${pageContext.request.contextPath}/purchaseContract/deleteDraft.html?ids="+ids;
			});
		}else{
			layer.alert("请选择要删除的信息",{offset: ['222px', '390px'], shade:0.01});
		}
    }
  	
  	function query(){
  		var projectName = $("#projectName").val();
  		var projectCode = $("#projectCode").val();
  		var purchaseDep = $("#purchaseDep").val();
  		window.location.href="${pageContext.request.contextPath}/purchaseContract/selectAllPuCon.html?projectName="+projectName+"&projectCode="+projectCode+"&purchaseDep="+purchaseDep;
  	}
  	
  	function resetForm(){
  		$("#projectName").val("");
  		$("#code").val("");
  		$("#demandSector").val("");
  		$("#documentNumber").val("");
  		$("#supplierDepName").val("");
  		$("#purchaseDepName").val("");
  		$("#year").val("");
  		$("#budgetSubjectItem").val("");
  	}
  	
  	function updateDraft(){
  		var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			if(ids.length>1){
				layer.alert("只可选择一条修改",{offset: ['222px', '390px'], shade:0.01});
			}else{
				window.location.href="${pageContext.request.contextPath}/purchaseContract/createDraftContract.html?ids="+ids;
			}
		}else{
			layer.alert("请选择要修改的草稿",{offset: ['222px', '390px'], shade:0.01});
		}
  	}
  	
  	function showDraftContract(id){
  		window.location.href="${pageContext.request.contextPath}/purchaseContract/showFormalContract.html?ids="+id;
  	}
  	
  	var ind;
  	function createContract(){
  		var ids =[];
  		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			if(ids.length>1){
				layer.alert("只可选择一条草稿生成",{offset: ['222px', '390px'], shade:0.01});
			}else{
				ind = layer.open({
					shift: 1, //0-6的动画形式，-1不开启
				    moveType: 1, //拖拽风格，0是默认，1是传统拖动
				    title: ['请输入合同批准文号','border-bottom:1px solid #e5e5e5'],
				    shade:0.01, //遮罩透明度
					type : 1,
					skin : 'layui-layer-rim', //加上边框
					area : [ '370px', '200px' ], //宽高
					content : $('#numberWin'),
					offset: ['55%', '40%']
				});
			}
		}else{
			layer.alert("请选择要生成的合同草稿",{offset: ['222px', '390px'], shade:0.01});
		}
  	}
  	
  	function save(){
  		var ids =[];
  		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		var apN = $("#apN").val();
		if(apN!=null && apN!=''){
			window.location.href="${pageContext.request.contextPath}/purchaseContract/updateDraftById.html?id="+ids+"&apN="+apN+"&status=2";
		}else{
			layer.alert("请先填写合同批准文号",{offset: ['55%', '40%'], shade:0.01});
		}
	}
	
  	function printformal(){
  		var ids =[];
  		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		});
  		if(ids.length>0){
			if(ids.length>1){
				layer.alert("只可选择一条合同打印",{offset: ['380px', '395px'], shade:0.01});
			}else{
				window.location.href="${pageContext.request.contextPath}/purchaseContract/printFormalContract.html?ids="+ids;
			}
		}else{
			layer.alert("请选择一条合同打印",{offset: ['380px', '395px'], shade:0.01});
		}
  	}
  	
	function cancel(){
		layer.close(ind);
	}
	
	function execution(){
		var ids =[];
  		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			if(ids.length>1){
				layer.alert("只可选择一条合同填写",{offset: ['380px', '395px'], shade:0.01});
			}else{
				window.location.href="${pageContext.request.contextPath}/performance/createPerformance.html?contractId="+ids;
			}
		}else{
			layer.alert("请选择一条合同填写",{offset: ['380px', '395px'], shade:0.01});
		}
	}
	
	function out(content){
		layer.msg(content, {
			    skin: 'demo-class',
				shade:false,
				closeBtn:[0,true],
				area: ['600px'],
				time : 4000    //默认消息框不关闭
		});//去掉msg图标
  	}
	
	function updateModel(){
		window.location.href="${pageContext.request.contextPath}/templet/search.html?temType=合同模板";
	}
  </script>
  </head>
  
  <body>

	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a></li><li><a href="javascript:void(0);">采购合同管理</a></li><li><a href="javascript:void(0);">正式合同管理</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <div class="headline-v2 fl">
        <h2>正式合同列表
	    </h2>
     </div>
<!-- 项目戳开始 -->
    <form id="form1" action="${pageContext.request.contextPath}/purchaseContract/selectFormalContract.html" method="post">
    <input type="hidden" value="" name="page" id="page"/>
     <div class="search_detail">
    	<ul class="demand_list">
          <li class="fl"><label class="fl">采购项目：</label><span><input type="text" value="${purCon.projectName }" id="projectName" name="projectName" class="mb0 mt5"/></span></li>
	      <li class="fl"><label class="fl">合同编号：</label><span><input type="text" value="${purCon.code }" id="code" name="code" class="mb0 mt5"/></span></li>
	      <li class="fl"><label class="fl">需求部门：</label><span><input type="text" value="${purCon.demandSector }" id="demandSector" name="demandSector" class="mb0 mt5 w100"/></span></li>
	      <li class="fl"><label class="fl">计划文件号：</label><span><input type="text" value="${purCon.documentNumber }" id="documentNumber" name="documentNumber" class="mb0 mt5 w100"/></span></li>
	      <li class="fl"><label class="fl">供应商：</label><span><input type="text" value="${purCon.supplierDepName }" id="supplierDepName" name="supplierDepName" class="mb0 mt5"/></span></li>
	      <li class="fl"><label class="fl">采购机构：</label><span><input type="text" value="${purCon.purchaseDepName }" id="purchaseDepName" name="purchaseDepName" class="mb0 mt5"/></span></li>
	      <li class="fl"><label class="fl">年度：</label><span><input type="text" value="${purCon.year_string }" id="year" name="year_string" class="mb0 mt5 w100"/></span></li>
	      <li class="fl"><label class="fl">项级预算科目：</label><span><input type="text" value="${purCon.budgetSubjectItem }" id="budgetSubjectItem" name="budgetSubjectItem" class="mb0 mt5 w100"/></span></li>
    	  <div class="fl col-md-12 tc mt10">
    	    <input type="submit" class="btn" value="查询"/>
    	    <input type="button" onclick="resetForm()" class="btn" value="重置"/>
    	  </div>
    	</ul>
    	  <div class="clear"></div>
    	  </div>
      </form>
   	   <div class="col-md-12 pl20 mt10">
   	  	  <button class="btn" onclick="printformal()">打印</button>
   	  	  <button class="btn" onclick="execution()">合同执行情况登记</button>
   	  	  <button class="btn" onclick="updateModel()">更新合同模板</button>
   	  	  <div class="fr mt5 b">
	      	项目总金额：${contractSum}
	      </div>
	   </div>
   <div class="content table_box">
   	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th class="tnone"></th>
				<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
			    <th class="info w50">序号</th>
				<th class="info">合同名称</th>
				<th class="info">合同编号</th>
				<th class="info">合同金额</th>
				<th class="info">项目名称</th>
				<th class="info">供应商名称</th>
				<th class="info">采购机构</th>
				<th class="info">需求部门</th>
				<th class="info">计划文件号</th>
				<th class="info">预算</th>
				<th class="info">年度</th>
				<th class="info">项级预算科目</th>
			</tr>
		</thead>
		<c:forEach items="${formalConList}" var="formalCon" varStatus="vs">
			<tr>
				<td class="tnone">${formalCon.purchaseType}</td>
				<td class="tc pointer"><input onclick="check()" type="checkbox" name="chkItem" value="${formalCon.id}" /></td>
				<td class="tl pl20 pointer" onclick="showDraftContract('${formalCon.id}')">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
				<c:set value="${formalCon.code}" var="code"></c:set>
				<c:set value="${fn:length(code)}" var="length"></c:set>
				<c:if test="${length>7}">
					<td onclick="showDraftContract('${formalCon.id}')" class="pointer" onmouseover="titleMouseOver('${code}',this)" onmouseout="titleMouseOut();">${fn:substring(code,0,7)}...</td>
				</c:if>
				<c:if test="${length<=7}">
					<td onclick="showDraftContract('${formalCon.id}')" class="pointer" onmouseover="titleMouseOver('${code}',this)" onmouseout="titleMouseOut();">${code}</td>
				</c:if>
				<c:set value="${formalCon.name}" var="name"></c:set>
				<c:set value="${fn:length(name)}" var="length"></c:set>
				<c:if test="${length>9}">
					<td onclick="showDraftContract('${formalCon.id}')" class="pointer" onmouseover="titleMouseOver('${name}',this)" onmouseout="titleMouseOut();">${fn:substring(name,0,9)}...</td>
				</c:if>
				<c:if test="${length<=9}">
					<td onclick="showDraftContract('${formalCon.id}')" class="pointer" onmouseover="titleMouseOver('${name}',this)" onmouseout="titleMouseOut();">${name}</td>
				</c:if>
				<td class="tr pr20 pointer" onclick="showDraftContract('${formalCon.id}')">${formalCon.money}</td>
				<td class="tl pl20 pointer" onclick="showDraftContract('${formalCon.id}')">${formalCon.projectName}</td>
				<td class="tl pl20 pointer" onclick="showDraftContract('${formalCon.id}')">${formalCon.supplierDepName}</td>
				<td class="tl pl20 pointer" onclick="showDraftContract('${formalCon.id}')">${formalCon.purchaseDepName}</td>
				<td class="tl pl20 pointer" onclick="showDraftContract('${formalCon.id}')">${formalCon.demandSector}</td>
				<td class="tl pl20 pointer" onclick="showDraftContract('${formalCon.id}')">${formalCon.documentNumber}</td>
				<td class="tr pr20 pointer" onclick="showDraftContract('${formalCon.id}')">${formalCon.budget}</td>
				<td class="tl pl20 pointer" onclick="showDraftContract('${formalCon.id}')">${formalCon.year}</td>
				<td class="tl pl20 pointer" onclick="showDraftContract('${formalCon.id}')">${formalCon.budgetSubjectItem}</td>
			</tr>
		</c:forEach>
	</table>
     </div>
   <div id="pagediv" align="right"></div>
   </div>
   	<div id="numberWin" class="dnone mt20">
	    <div class="col-md-12">
   		 <span class="span3">合同批准文号：</span>
   		 <input type="text" id="apN" value="" class="mb0"/>
        </div>
		<div class="tc col-md-12 mt20 ml30">
		 <input type="button" class="btn" onclick="save()" value="生成"/>
		 <input type="button" class="btn" onclick="cancel()" value="取消"/>
		</div>
	</div>
</body>
</html>

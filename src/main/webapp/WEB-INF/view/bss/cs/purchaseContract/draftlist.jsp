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
	  var status = "${purCon.status}";
	  $("#status").val(status);
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
    	var status = [];
    	var flag = true;
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
			status.push($(this).parent().next().text());
		}); 
		if(ids.length>0){
			for(var i=0;i<status.length;i++){
				if(status[i]==2){
					flag = false;
					layer.alert("正式合同不可删除",{offset: ['222px', '390px'], shade:0.01});
				}
			}
			if(flag){
				layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
					layer.close(index);
					window.location.href="${pageContext.request.contextPath}/purchaseContract/deleteDraft.html?ids="+ids;
				});
			}
			
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
  		$("#status").val("");
  	}
  	
  	function updateDraft(){
  		var ids =[]; 
  		var status = "";
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
			status = ($(this).parent().next().text());
		}); 
		if(ids.length>0){
			if(ids.length>1){
				layer.alert("只可选择一条修改",{offset: ['222px', '390px'], shade:0.01});
			}else{
				if(status=="1"){
					window.location.href="${pageContext.request.contextPath}/purchaseContract/createDraftContract.html?ids="+ids;
				}else if(status=="0"){
					window.location.href="${pageContext.request.contextPath}/purchaseContract/updateZanCun.html?id="+ids[0];
				}else{
					layer.alert("正式合同不能修改",{offset: ['222px', '390px'], shade:0.01});
				}
			}
		}else{
			layer.alert("请选择要修改的合同",{offset: ['222px', '390px'], shade:0.01});
		}
  	}
  	
  	function showDraftContract(id,status){
  		window.location.href="${pageContext.request.contextPath}/purchaseContract/showDraftContract.html?ids="+id+"&status="+status;
  	}
  	
  	var ind;
  	function createContract(){
  		var ids =[];
  		var status = "";
  		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val());
			status=($(this).parent().next().text());
		}); 
		if(ids.length>0){
			if(ids.length>1){
				layer.alert("只可选择一条草案生成",{offset: ['222px', '390px'], shade:0.01});
			}else{
				if(status!=1){
					layer.alert("只可选择草案合同生成",{offset: ['222px', '390px'], shade:0.01});
				}else{
				/*ind = layer.open({
					shift: 1, //0-6的动画形式，-1不开启
				    moveType: 1, //拖拽风格，0是默认，1是传统拖动
				    title: ['请输入合同批准文号','border-bottom:1px solid #e5e5e5'],
				    shade:0.01, //遮罩透明度
					type : 1,
					skin : 'layui-layer-rim', //加上边框
					area : [ '40%', '300px' ], //宽高
					content : $('#numberWin'),
					offset: ['10%', '25%']
				});*/
				window.location.href="${pageContext.request.contextPath}/purchaseContract/toFormalContract.html?id="+ids;
				}
			}
		}else{
			layer.alert("请选择要生成的合同草稿",{offset: ['222px', '390px'], shade:0.01});
		}
  	}
  	/*var ind;
  	function createDraftContract(){
  		var ids =[];
  		var status = "";
  		flag = true;
  		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
			status=($(this).parent().next().text());
		}); 
		if(ids.length>0){
			if(ids.length>1){
				layer.alert("只可选择一条暂存合同生成",{offset: ['222px', '390px'], shade:0.01});
			}else{
				if(status!=0){
					flag = false;
					layer.alert("只可选择暂存的合同生成",{offset: ['222px', '390px'], shade:0.01});
				}
				if(flag){
					ind = layer.open({
						shift: 1, //0-6的动画形式，-1不开启
					    moveType: 1, //拖拽风格，0是默认，1是传统拖动
					    title: ['生成草案所需信息','border-bottom:1px solid #e5e5e5'],
					    shade:0.01, //遮罩透明度
						type : 1,
						skin : 'layui-layer-rim', //加上边框
						area : [ '40%', '300px' ], //宽高
						content : $('#numberWin'),
						offset: ['10%', '25%']
					});
				}
			}
		}else{
			layer.alert("请选择要生成的暂存合同",{offset: ['222px', '390px'], shade:0.01});
		}
  	}*/
  	function save(){
  		var ids =[];
  		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
  		$("#ids").val(ids);
  		$.ajax({
  			url:"${pageContext.request.contextPath}/purchaseContract/toValidRoughContract.html",
  			data:$('#form2').serialize(),
  			type:"post",
  			dataType:"json",
  			success:function(data){
  				if(data==1){
					$("#form2").submit();
				}else{
					var obj = new Function("return" + data)();
					$("#gitTime").text(obj.gitAt);
					$("#reviewTime").text(obj.reviewAt);
				}
  			}
  		});
	}
	
	function cancel(){
		layer.close(ind);
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
	
	function printContract(){
		var ids =[];
  		var status = "";
  		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
			status=($(this).parent().next().text());
		}); 
		if(ids.length>0){
			if(ids.length>1){
				layer.alert("只可选择一条合同打印",{offset: ['222px', '390px'], shade:0.01});
			}else{
				if(status==1){
					layer.open({
					shift: 1, //0-6的动画形式，-1不开启
				    moveType: 1, //拖拽风格，0是默认，1是传统拖动
				    title: ['打印草案合同','border-bottom:1px solid #e5e5e5'],
				    shade:0.01, //遮罩透明度
					type : 2,
					skin : 'layui-layer-rim', //加上边框
					area : [ '35%', '250px' ], //宽高
					content : '${pageContext.request.contextPath}/purchaseContract/filePage.html?id='+ids+'&status='+status,
					offset: ['30%', '25%'],
					shadeClose : true
				   });
				}else{
					window.location.href="${pageContext.request.contextPath}/purchaseContract/printContract.html?id="+ids+"&status="+status;
				}
			}
		}else{
			layer.alert("请选择要打印的合同",{offset: ['222px', '390px'], shade:0.01});
		}
	}
	
	function toprintmodel(){
		var ids =[];
  		var status = "";
  		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
			status=($(this).parent().next().text());
		});
		window.location.href="${pageContext.request.contextPath}/purchaseContract/printContract.html?id="+ids+"&status="+status;
	}
	function manualCreateContract(){
  		window.location.href="${pageContext.request.contextPath}/purchaseContract/manualCreateContract.html";
  	}
	function updateZanCun(){
		var ids =[]; 
  		var status = "";
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
			status = ($(this).parent().next().text());
		}); 
		if(ids.length>0){
			if(ids.length>1){
				layer.alert("只可选择一条修改",{offset: ['222px', '390px'], shade:0.01});
			}else{
				if(status=="0"){
					window.location.href="${pageContext.request.contextPath}/purchaseContract/updateZanCun.html?id="+ids[0];
				}else{
					layer.alert("只可修改暂存的合同",{offset: ['222px', '390px'], shade:0.01});
				}
			}
		}else{
			layer.alert("请选择要修改的合同",{offset: ['222px', '390px'], shade:0.01});
		}
  		/* var ids =[]; 
  		var supcheckid = [];
  		var isCreateContract =[] ;
		$('input[name="chkItem"]:checked').each(function(){
			ids.push($(this).val()); 
			supcheckid.push($(this).parent().next().next().text());
			isCreateContract.push($(this).parent().next().next().next().text());
		});
		if(ids.length>0){
			if(ids.length>1){
				layer.alert("只可选择一条修改",{offset: ['222px', '390px'], shade:0.01});
			}else{
				if(isCreateContract==2){
					window.location.href="${pageContext.request.contextPath}/purchaseContract/updateZanCun.html?supcheckid="+supcheckid+"&isCreateContract="+isCreateContract;
				}else{
					layer.alert("只可选择暂存的修改",{offset: ['222px', '390px'], shade:0.01});
				}
			}
		}else{
			layer.alert("请选择要修改的信息",{offset: ['222px', '390px'], shade:0.01});
		} */
  	}
  	
  </script>
  </head>
  
  <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">采购合同管理</a></li><li><a href="#">合同管理</a></li>
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
    <form id="form1" action="${pageContext.request.contextPath}/purchaseContract/selectDraftContract.html" method="post">
    <input type="hidden" value="" name="page" id="page"/>
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
	      <li class="fl"><label class="fl">状态：</label><span>
	      	<select id="status" name="status" class="mb0 mt5 w200">
	      		<option value="">--请选择--</option>
	      		<option value="1">草案</option>
	      		<option value="2">正式</option>
	      	</select>
	      </span></li>
    	  <div class="fl col-md-12 tc mt10">
    	    <input type="submit" class="btn" value="查询"/>
    	    <input type="button" onclick="resetForm()" class="btn" value="重置"/>
    	  </div>
    	</ul>
    	  <div class="clear"></div>
    	  </div>
      </form>
         <div class="col-md-12 col-xs-12 col-sm-12 pl20 mt10 p0">
          
   	  	  <button class="btn btn-windows edit" onclick="updateDraft()">修改</button>
   	  	  <button class="btn btn-windows delete" onclick="delDraft()">删除</button>
   	  	  <button class="btn" onclick="printContract()">打印</button>
	      <button class="btn" onclick="createContract()">生成正式合同</button>
	      <button class="btn" onclick="manualCreateContract()">新增合同</button>
	      <!-- <button class="btn" onclick="updateZanCun()">修改暂存</button> -->
	      <%--<button class="btn" onclick="createDraftContract()">生成草案合同</button>
	      <button class="btn" onclick="updateModel()">更新合同模板</button>
	      --%>
	      
	      <div class="fr mt5 b">
	      	项目总金额(万元)：${contractSum}
	      </div>
	     </div>
   <div class="content table_box over_auto table_wrap">
   	<table class="table table-striped table-bordered table-hover">
		<thead>
			<tr>
				<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
				<th class="tnone"></th>
			    <th class="info w50">序号</th>
			    <th width="10%" class="info">合同编号</th>
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

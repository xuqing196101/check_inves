<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>采购合同管理</title>  
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	  <script src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
	  <script src="${pageContext.request.contextPath}/public/laypage-v1.3/laypage/laypage.js"></script>
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
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		        	var projectName = $("#projectName").val();
		      		var projectCode = $("#projectCode").val();
		      		var purchaseDep = $("#purchaseDep").val();
		      		window.location.href="${pageContext.request.contextPath}/purchaseContract/selectAllPuCon.html?projectName="+projectName+"&projectCode="+projectCode+"&purchaseDep="+purchaseDep+"&page="+e.curr;
		        }
		    }
		});
	    $(document).keyup(function(event) {
			if (event.keyCode == 13) {
				query();
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
  		window.location.href="${pageContext.request.contextPath}/articletype/view.html?id="+id;
  	}
    
  	function del(){
    	var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		}); 
		if(ids.length>0){
			layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
				layer.close(index);
				window.location.href="${pageContext.request.contextPath}/downloadUser/deleteDownloadUser.html?ids="+ids;
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
  	
  	function reset(){
  		$("#projectName").val("");
  		$("#projectCode").val("");
  		$("#purchaseDep").val("");
  	}
  	
  	function createContract(){
  		var ids =[]; 
  		var supid = null;
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
			supid = $(this).parent().next().text();
		}); 
		if(ids.length>0){
			if(ids.length>1){
				layer.alert("只可选择一条项目生成",{offset: ['222px', '390px'], shade:0.01});
			}else{
				alert(supid);
				/*$.ajax({
		  			url:"${pageContext.request.contextPath}/purchaseContract/selectSuppliers.html?packageId="+ids,
		  			dataType:"text",
		  			type:"POST",
		  			success:function(data){
		  				$("#delSele").html("");
		  				$("#delSele").append(data);
		  				ind = layer.open({
							shift: 1, //0-6的动画形式，-1不开启
						    moveType: 1, //拖拽风格，0是默认，1是传统拖动
						    title: ['请选择供应商','border-bottom:1px solid #e5e5e5'],
						    shade:0.01, //遮罩透明度
							type : 1,
							skin : 'layui-layer-rim', //加上边框
							area : [ '40%', '200px' ], //宽高
							content : $('#numberWin'),
							offset: ['5%', '25%']
						});
		  			}
		  		});*/
		  		window.location.href="${pageContext.request.contextPath}/purchaseContract/createCommonContract.html?supid="+supid+"&id="+ids;
			}
		}else{
			layer.alert("请选择要生成的项目",{offset: ['222px', '390px'], shade:0.01});
		}
  	}
  	
  	function cancel(){
  		layer.close(ind);
  	}
  	
  	/*function save(){
  		var supid = $("#delSele").val();
  		var ids =[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			ids.push($(this).val()); 
		});
		$.ajax({
			url:"${pageContext.request.contextPath}/purchaseContract/isChoiceSupplier.html?delSupplier="+supid,
			type:"post",
			dataType:"json",
			success:function(data){
				if(data==1){
					window.location.href="${pageContext.request.contextPath}/purchaseContract/createCommonContract.html?supid="+supid+"&id="+ids;
				}else{
					var obj = new Function("return" + data)();
					$("#delsuperr").text(obj.delsuerr);
				}
			}
		});
  	}*/
  	
  	function someCreateContract(){
  		var ids =[]; 
  		var chekeds=[];
		$('input[name="chkItem"]:checked').each(function(){
			ids.push($(this).val()); 
		});
		
		if(ids.length>0){
			if(ids.length>1){
				$.ajax({
					url:"${pageContext.request.contextPath}/purchaseContract/createAllCommonContract.html?ids="+ids,
					type:"POST",
					dataType:"text",
					success:function(data){
						var dd = data.replace("\"","");
						var ss = dd.split("=");
						if(ss[0]=="true"){
							$.ajax({
					  			url:"${pageContext.request.contextPath}/purchaseContract/selectSupplierByPId.html?packageId="+ids,
					  			dataType:"text",
					  			type:"POST",
					  			success:function(data){
									window.location.href="${pageContext.request.contextPath}/purchaseContract/createCommonContract.html?id="+ids+"&supid="+data;
					  			}
							});
						}else if(ss[0]=="false"){
							layer.alert(ss[1],{offset: ['222px', '390px'], shade:0.01});
						}
					}
				});
			}else if(ids.length==1){
				layer.alert("请至少选择两条",{offset: ['222px', '390px'], shade:0.01});
			}
			//layer.alert("请选择相同的供应商",{offset: ['222px', '390px'], shade:0.01});
		}else if(ids.length==0){
			layer.alert("请选择要生成的项目",{offset: ['222px', '390px'], shade:0.01});
		}
  	}
  </script>
  </head>
  
  <body>

	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">采购合同管理</a></li><li><a href="#">采购项目列表</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="container">
   <div class="headline-v2">
      <h2>成交项目列表
	  </h2>
   </div> 
<!-- 项目戳开始 -->
     <div class="search_detail">
    	<ul class="demand_list">
          <li class="fl"><label class="fl">采购项目名称：</label><span><input type="text" value="${projectName }" id="projectName" class=""/></span></li>
	      <li class="fl"><label class="fl">编号：</label><span><input type="text" value="${projectCode }" id="projectCode" class=""/></span></li>
	      <li class="fl"><label class="fl">采购机构：</label><span><input type="text" value="${purchaseDep }" id="purchaseDep" class=""/></span></li>
	    	<button type="button" onclick="query()" class="btn">查询</button>
	    	<button type="reset" onclick="reset()" class="btn">重置</button>  	
    	</ul>
    	  <div class="clear"></div>
     </div>
    <div class="col-md-12 pl20 mt10">
		<button class="btn" onclick="createContract()">生成</button>
		<button class="btn" onclick="someCreateContract()">合并生成</button>
	</div>
   <div class="content table_box">
   	<table class="table table-bordered table-condensed table-hover">
		<thead>
			<tr>
				<th class="info w30"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
				<th class="tnone"></th>
			    <th class="info w50">序号</th>
				<th class="info">采购项目名称</th>
				<th class="info">编号</th>
				<th class="info">包名</th>
				<th class="info">成交金额</th>
				<th class="info">成交供应商</th>
				<th class="info">采购机构</th>
			</tr>
		</thead>
		<c:forEach items="${packageList}" var="pack" varStatus="vs">
			<tr>
				<td class="tc pointer"><input onclick="check()" type="checkbox" name="chkItem" value="${pack.id}" /></td>
				<td class="tnone">${pack.supplier.id}</td>
				<td class="tc pointer">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
				<c:set value="${pack.project.name}" var="name"></c:set>
				<c:set value="${fn:length(name)}" var="length"></c:set>
				<c:if test="${length>10}">
					<td class="pointer" title="${name}">${fn:substring(name,0,10)}...</td>
				</c:if>
				<c:if test="${length<=10}">
					<td class="pointer" title="${name}">${name}</td>
				</c:if>
				<c:set value="${pack.project.projectNumber}" var="code"></c:set>
				<c:set value="${fn:length(code)}" var="length"></c:set>
				<c:if test="${length>10}">
					<td class="pointer" title="${code}">${fn:substring(code,0,10)}...</td>
				</c:if>
				<c:if test="${length<=10}">
					<td class="pointer" title="${code}">${code}</td>
				</c:if>
				<td class="tc pointer">${pack.name}</td>
				<td class="tc pointer">${pack.project.amount}</td>
				<td class="tc pointer">${pack.supplier.supplierName}</td>
				<td class="tc pointer">${pack.project.purchaseDep.depName}</td>
			</tr>
		</c:forEach>
	</table>
    </div>
   <div id="pagediv" align="right"></div>
   <!-- <ul class="list-unstyled dnone mt10 col-xs-offset-3" id="numberWin">
  		    <li class="col-md-12">
			   <span class="fl mt20"><div class="red star_red">*</div>成交供应商：</span>
			   <div class="select_common mt15">
			     <select name="delsupplier" id="delSele">
			     </select>
			     <div id='delsuperr' class="cue"></div>
			   </div>
			</li>
			<li class="tc col-md-12 tl pl50 pb20">
			 <input type="button" class="btn mt10" onclick="save()" value="确定"/>
			 <input type="button" class="btn mt10" onclick="cancel()" value="取消"/>
			</li>
	 </ul>
	  -->
</body>
</html>

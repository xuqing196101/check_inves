<%@ page language="java" import="java.util.*,ses.util.StringUtil" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML PUBLIC>
<html>
  <head>
  <%@ include file="/WEB-INF/view/common.jsp"%>  
<script type="text/javascript">
	var datas;
	var treeid=null;
	$(document).ready(function(){
		loadPage();
	     var setting={
		    async:{
					autoParam:["id","name"],
					enable:true,
					url:"${pageContext.request.contextPath}/category/createtree.do",
					dataType:"json",
					type:"post",
				},
				callback:{
			    	onClick:zTreeOnClick,//点击节点触发的事件
			    	onExpand: expandNode
			    }, 
				data:{
					keep:{
						parent:true
					},
					key:{
						title:"title",
						name:"name",
					},
					simpleData:{
						enable:true,
						idKey:"id",
						pIdKey:"pId",
						rootPId:"0",
					}
			    },
			    
			   check:{
				    chkboxType:{"Y" : "ps", "N" : "ps"},//勾选checkbox对于父子节点的关联关系  
	       		    chkStyle:"checkbox",
	       		    nocheckInherit: false,
					enable: true
			   },
			   view:{
			        selectedMulti: false,
			        showTitle: false,
			   },
	     };
	        $.fn.zTree.init($("#ztree"),setting,datas); 
	 }); 

    /**点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
		treeid=treeNode.id;
		$("#cateid").val(treeid);
    }
   
    /** 分页  */
    function loadPage(){
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
  		    	return "${list.pageNum}" == 0 ? 1 : "${list.pageNum}";
  		    }(), 
  		    jump: function(e, first){ //触发分页后的回调
  		        if(!first){ //一定要加此判断，否则初始时会无限刷新
  		            $("#page").val(e.curr);
  		            $("#form").submit();
  		        }
  		    }
  		});
    }

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
     
     /** 取消分配任务 */
     function uAllocate(){
    	var cateId=[];
 		var treeObj=$.fn.zTree.getZTreeObj("ztree");  
 	    var nodes=treeObj.getCheckedNodes(true);  
 	    for(var i=0;i<nodes.length;i++){ 
 	    	if (nodes[i].pId != '0'){
 	    		cateId.push(nodes[i].id);
 	    	}
 	    } 
 	    var orgId=[]; 
 	    var isErrorName;
		$('input[name="chkItem"]:checked').each(function(){ 
			var cateName = $(this).parents('tr').find('td').eq(5).text();
			isErrorName = null;
			if (cateName == null || cateName == ""){
				var name = $(this).parents('tr').find('td').eq(2).text();
				isErrorName = name;
				return false;
			}
			orgId.push($(this).val());
		});
		
		if (isErrorName != null){
			layer.msg("部门:" + isErrorName + ",没有分配品目,无法取消");	
			return;
		}
		
		if (orgId.length == 0){
			layer.msg("请选择需求部门");
			return;
		}
		if (cateId.length == 0) {
			layer.confirm('您确认要取消吗？', {
				  btn: ['确认','取消']
			    },function (){
			    	unassigned(orgId,'');
			    	
			    }
		  	  );
		} else {
			unassigned(orgId,cateId);
		}
		
     }
     
     /** 取消  */
     function unassigned(orgId, cateId){
    	var  ids = getCheckedItems(orgId);
 		$.ajax({
     		type:"post",
     		url:"${pageContext.request.contextPath}/categoryparam/unassigned.do?orgId= "+ orgId + "&cateId=" +cateId ,
     		success:function(data){
     			if (data == "ok"){
     				layer.msg('取消成功');
     				getResult(orgId);
     				disableChekbox(ids,false);
     			} else {
     				layer.msg(data);
     			}
     		}
     	});
 	}
     
	//获取选中节点 
	
	function allocate(){
		var cateId =[];
		var cateName = [];
		var treeObj=$.fn.zTree.getZTreeObj("ztree");  
	    var nodes=treeObj.getCheckedNodes(true);  
	    for(var i=0;i<nodes.length;i++){ 
	    	if (nodes[i].pId != '0'){
	    		cateId.push(nodes[i].id);
		    	cateName.push(nodes[i].name);
	    	}
	    } 
		var orgId=[]; 
		$('input[name="chkItem"]:checked').each(function(){ 
			orgId.push($(this).val());
		}); 
		
		if (cateId.length == 0){
			layer.msg("请选择品目");
			return ;
		}
		
		if (orgId.length == 0){
			layer.msg("请选择需求部门");
			return ;
		}
		
		if (orgId.length  > 1){
			layer.msg("只能选择一个需求部门");
			return;
		}
		
		assigned(orgId,cateId,cateName);
		
	  }
	
	/** 分配 */
	function assigned(orgId, cateId ,cateName){
		cateName=escape(encodeURIComponent(cateName))
		$.ajax({
    		type:"post",
    		url:"${pageContext.request.contextPath}/categoryparam/assigned.do?orgId= "+ orgId + "&cateId=" +cateId + "&cateName= "+ cateName ,
    		success:function(data){
    			if (data == "ok"){
    				layer.msg('分配成功');
    				getResult(orgId);
    				disableChekbox(cateId,true);
    			} else {
    				layer.msg(data);
    			}
    		}
    	});
	}
	
	/** 更新结果 */
	function getResult(orgIds){
		$.ajax({
    		type:"post",
    		dataType:"json",
    		url:"${pageContext.request.contextPath}/categoryparam/assignedRes.do?orgIds= "+ orgIds ,
    		success:function(data){
    			showCateResult(data);
    		}
    	});
	}
	
	/** 展示结果 */
	function showCateResult(data){
		if (data && data.length > 0){
			for (var i =0;i<data.length;i++) {
				showValue(data[i]);
			}
		}
	}
	
	/** 显示值 **/
	function showValue(obj){
		$('input[name="chkItem"]:checked').each(function(){ 
			var checkedId = $(this).val();
			if (checkedId == obj.orgId) {
				$(this).parents('tr').find('td').eq(5).removeAttr("onmouseover");
				var names = obj.cateNames;
				if (names.length > 17){
					$(this).parents('tr').find('td').eq(5).text(names.substring(0,17) + "...");
				} else {
					$(this).parents('tr').find('td').eq(5).text(names);
				}
				$(this).parents('tr').find('td').eq(5).attr("onmouseover","titleMouseOver('"+obj.cateNames+"',this)");
			}
		});
	}
	
	/** 展开tree */
	function expandNode(){
		allocaItemIds('',true);
	}
	
	/** 禁用,启用 */
	function disableChekbox(cateIds,opera){
		var treeObj=$.fn.zTree.getZTreeObj("ztree");  
		for (var i = 0;i<cateIds.length;i++){
			var node = treeObj.getNodeByParam("id",$.trim(cateIds[i]), null);
			if (node != null){
				treeObj.setChkDisabled(node,opera);
			}
		}
	}
	
	/** 获取已分配的组织机构 */
	function allocaItemIds(orgIds, opera){
		$.ajax({
			type:"post",
    		dataType:"json",
    		data:{'orgId':orgIds},
    		async:false,
    		url:"${pageContext.request.contextPath}/categoryparam/allocaItemIds.do",
    		success:function(data){
    			if (data != null && data != ""){
    				disableChekbox(data,opera);
    			}
    		}
		});
	}
	
	/**
	  获取取消的参数Id
	*/
	function getCheckedItems(orgIds){
		var checkedObj = null;
		$.ajax({
			type:"post",
    		dataType:"json",
    		data:{'orgId':orgIds},
    		async:false,
    		url:"${pageContext.request.contextPath}/categoryparam/allocaItemIds.do",
    		success:function(data){
    			if (data != null && data != ""){
    				checkedObj = data;
    			}
    		}
		});
		return checkedObj;
	}
	
	
	/* 查询 */
	function query(){
		$("#form").submit();
	}
	
	/** 重置 */
	function resetSearch(){
		$("#orgId").val("");
		$("#leadId").val("");
		query();
	}
	
</script>
  </head>
  <body>
  <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">产品参数管理</a></li><li><a href="#">分配</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
  <div class="container">
	   <div class="col-md-3">
		 <div class="tag-box tag-box-v3 mt10">
		 	<div>
		 		<ul id="ztree" class="ztree s_ztree" />
		 	</div>
		 </div>
	   </div>
	   <div class="col-md-9">
		    <div class="headline-v2">
		     <h2>产品分配列表</h2>
		    </div>
		   <div class="search_detail">
			   <form id="form" action="${pageContext.request.contextPath}/categoryparam/query_orgnization.html" method="post">
			       	<input type="hidden"/>
			   		<input type="hidden" name="page" value="" id="page"/>	
			        <ul class="demand_list">
			        	 <li>
					    	<label class="fl">需求部门：</label><span><input type="text" id="orgId" value="${name}" name="name"  /></span>
					     </li>
					     <li>
					    	<label class="fl">负责人：</label><span><input type="text" id="leadId" name="princinpal"  value="${princinpal}"/></span>
					     </li>
			        </ul>
			        <button type="button"  onclick="query();"  class="btn">查询</button>
			    	<button type="button"  onclick="resetSearch();"  class="btn">重置</button>  	
		        </form>
		      </div>
		      <div class="col-md-12 pl20 mt10">
		      		<button  type="button"  onclick="allocate();"class="btn">分配</button>
		      		<button  type="button"  onclick="uAllocate();"class="btn">取消</button>
		      </div>
		      <div class="content table_box">
		        <table class="table table-bordered table-condensed table-hover table-striped" >
		            <thead>    
		                <tr>
			                <th class="info w50"><input id="checkAll" type="checkbox" onclick="selectAll()"  /></th>
			                <th class="info w80">序号</th>
			                <th class="info w150">需求部门</th>
			                <th class="info w100">负责人</th>
			                <th class="info w120">电话</th>
			                <th class="info">品目名称</th>
		                </tr>
		            </thead>
		            <c:forEach var="cate" items="${cate}"  varStatus="vs">
		            <c:set value="${fn:length(cate.cateNames)}"  var="length"/>
		              <tr>
		                <td class="tc pointer"><input  onclick="check()" type="checkbox" name="chkItem" value="${cate.id}"/></td>
		                <td class="tc pointer">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
		                <td class="tc pointer">${cate.name}</td>
		                <td class="tc pointer">${cate.princinpal}</td>
		                <td class="tc pointer">${cate.mobile}</td>
		                <c:if test="${length > 17}">
		                	<td class="pointer" onmouseover="titleMouseOver('${cate.cateNames}',this)" onmouseout="titleMouseOut();">
		                		${fn:substring(cate.cateNames,0,17)}...
		                	</td>
		                </c:if>
		                <c:if test="${length <= 17}">
		                	<td class="pointer" onmouseover="titleMouseOver('${cate.cateNames}',this)" onmouseout="titleMouseOut();" >
		                		${cate.cateNames}
		                	</td>
		                </c:if>
		              </tr>
		            </c:forEach>
		       </table>
         	</div>
        	<div id="pagediv" align="right"></div>
         </div>
      </div>
  </body>
</html>

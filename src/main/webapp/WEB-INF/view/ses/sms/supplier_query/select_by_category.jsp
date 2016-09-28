<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>My JSP 'category.jsp' starting page</title>

 <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/zTreeStyle.css"> 
<%-- <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/demo.css"> --%>

<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.exedit.js"></script>

<script type="text/javascript">
	var datas;
	 var treeid=null;
 $(document).ready(function(){
	 var setting={
		async:{
					autoParam:["id","name"],
					enable:true,
					url:"<%=basePath%>category/createtree.do",
					dataType:"json",
					type:"post",
				},
				callback:{
			    	onClick:zTreeOnClick,//点击节点触发的事件
			    	beforeRemove: zTreeBeforeRemove,
			    	beforeRename: zTreeBeforeRename, 
					onRemove: zTreeOnRemove,
       			    onRename: zTreeOnRename,
       			  /*    onNodeCreated: zTreeOnNodeCreated, */
       			   
			    }, 
				data:{
					keep:{
						parent:true
					},
					key:{
						title:"title"
					},
					simpleData:{
						enable:true,
						idKey:"id",
						pIdKey:"pId",
						rootPId:"a",
					}
			    },
			    edit:{
			    	enable:true,
					editNameSelectAll:true,
					showRemoveBtn: true,
					showRenameBtn: true,
					removeTitle: "删除",
					renameTitle:"重命名",
				},
			   check:{
					enable: true
			   },
			
  };
	 
    $.fn.zTree.init($("#ztree"),setting,datas);
    var lastValue ="", nodeList=[];
    var value ="";
    key.bind("propertychange",searchNode).bind("input",searchNode);
}) 
    var ztree = $.fn.zTree.getZTreeObj("ztree");
    var key =$("#key");
	
    function searchNode(e){
	     alert(4);
    	value = $.trim(key.get(0).value);
    	var keyType ="name";
    	alert(3);
    	if(lastValue== value){
    		alert(1);
    		return;
    	}
    	lastValue= value;
    	updateNodes(false);
    	if(value==""){
    		alert(2);
    		return;
    	}
    	nodeList = ztree.getNodesByParamFuzzy(keyType,value);
    	console.info(nodeList);
    	updateNodes(true);
    	//遍历隐藏其他父节点
    }	
    function updateNodes(highlight){
    	var pnodes= ztree.getNodes();
    	for(var j=0;j<pnodes.length;j++){
    		ztree.showNode(pnodes[j]);
    		if(highlight){
				var have = false;
				for(var i=0, l=nodeList.length; i<l; i++) {
					if(pnodes[j].isParent && nodeList[i].pId == pnodes[j].id){
						have = true;
					}
				}
				if(have){
					for(var q = 0;q<pnodes[j].children.length;q++){
						for(var k = 0;k<nodeList.length;k++){
							if(pnodes[j].children[q].name==nodeList[k].name){
								ztree.showNode(pnodes[j].children[q]);
								break;
							}else if(k == nodeList.length-1){
								if(value.length == 1){
									ztree.showNode(pnodes[j].children[q]);
								}else{ 
									ztree.hideNode(pnodes[j].children[q]);
								}
								
							}
						}  
					}
				}
				if(!have){
					ztree.hideNode(pnodes[j]);
				}
			}else{
				ztree.showNode(pnodes[j]);
			}
       	}
		for( var i=0, l=nodeList.length; i<l; i++) {
			nodeList[i].highlight = highlight;
			ztree.updateNode(nodeList[i]);
		}
	}
    	
    function filter(node) {
		return !node.isParent && node.isFirstNode;
	}
    
	
   /*删除图片*/
    function deletepic(obj){
		layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
			layer.close(index);
	       	layer.msg('删除成功',{offset: ['222px', '390px']});
		    window.setTimeout(function(){
		    	$(obj).prev().hide();
				$(obj).next().remove();
				$(obj).hide();
				$("#showid").val(0);
				$(".order").val("");
		    }, 1000);
		});
		
	}
    /*点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
		treeid=treeNode.id
    }
    /*添加采购目录*/
    function news(){
			if (treeid==null) {
			alert("请选择一个节点");
					return;		
			}else{
				$.ajax({
					success:function(){
						var html = "";
						html = html+"<tr><td>目录名称</td>"+"<td><input name='name'/></td></tr>" ;
				/* 		html = html+"<tr><td>父节点</td>"+"<td><input name='parentId'/></td></tr>"; */
						html = html+"<tr><td>排序</td>"+"<td><input name='position'/></td></tr>";
						html = html+"<tr><td>编码</td>"+"<td><input name='code'/></td></tr>";
						html = html+"<tr><td>图片</td>"+"<td id='uploadAttach'><input id='pic'type='file' class='toinline' name='attaattach' /></td></tr>";
						html = html+"<tr><td>描述</td>"+"<td><textarea name='descrption'/></td></tr>";
						html = html+"<tr><td colspan='2'><input type='submit' value='提交' onclick='check()' class='btn btn-window'/></td></tr>";
						$("#result").append(html);
					}
				
				})
			}
			
		}
	/*修改节点信息*/
    function update(){
	 		if (treeid==null) {
				alert("请选择一个节点");
			}else{
				$.ajax({
					url:"<%=basePath%>category/update.do?id="+treeid,
					dataType:"json",
					type:"post",
					success:function(cate){
						alert(cate.name);
						var html = "";
						html = html+"<tr><td>目录名称</td><td><input value='"+cate.name+"'/></td></tr>";
						/* html = html+"<tr><td>父节点</td>"+"<td></td></tr>"; */
						html = html+"<tr><td>父节点</td><td><input value='"+cate.parentId+"'/></td></tr>";
						html = html+"<tr><td>排序</td><td><input value='"+cate.position+"'/></td></tr>";
						html = html+"<tr><td>编码</td><td><input value='"+cate.code+"'/></td></tr>";
						html = html+"<tr><td>附件</td><td id='uploadAttach'><input id='pic' type='file' value='"+cate.attchment+"'/>"
						+"<input onclick='deletepic(this)'  id='close_pic' class='close' type='button' value='×'/>"
						+"</td></tr>";
						html = html+"<tr><td>描述</td><td><input value='"+cate.description+"'/></td></tr>";
						html = html+"<tr><td colspan='2'><input type='submit' onclick='mysubmit()' value='更新' class='btn btn-window '/></td></tr>"
						$("#result").append(html);
					}
				})
			}
 		}
 	/*休眠-激活*/
    function ros(){
 			var str="";
	 		var treeObj = $.fn.zTree.getZTreeObj("ztree");
			var nodes = treeObj.getCheckedNodes(true);
			for ( var i = 0; i < nodes.length; i++) {
				str+=nodes[i].id+",";
				alert(str);
			}
			alert(str);
			$.ajax({
				type:"POST",
				url:"<%=basePath%>category/ros.do?ids="+str,
			})
 		}
 		
 	/*重命名和删除的回掉函数*/	
    function zTreeOnRemove(event, treeId, treeNode,isCancel) {
		}
    function zTreeOnRename(event, treeId, treeNode, isCancel) {
				 alert(treeNode.tId + ", " + treeNode.name); 
				
		}
	/*删除目录信息*/
    function zTreeBeforeRemove(treeId, treeNode){
	 		$.ajax({
	 			type:"post",
	 			url:"<%=basePath%>category/del.do?id="+treeNode.id,
	 		});
		}
	 	
	/*节点重命名*/
    function zTreeBeforeRename(treeId,treeNode,newName,isCancel){
			$.ajax({
	 			type:"post",
	 			url:"<%=basePath%>category/rename.do?id="+treeNode.id+"&name="+newName,
	 		});
		} 
    /*新增提交*/		
	function check(){
		document.fm.action="<%=basePath%>category/save.do";
		document.fm.submit();
	}
	/*更新数据*/
	function mysubmit(){
		document.fm.action="<%=basePath%>category/edit.do";
		document.fm.submit();
	}	
    /*关键字查询*/
  <%--    function searchM(){
    	window.location.href="<%=basePath%>category/search.do?name="+name;
    	var name = $("#keyword").val();
    	$.ajax({
    		type:"post",
    		dataType:"json",
    		url:"<%=basePath%>category/search.do?name="+name,
    		success:function(nodeList){
    			console.info(nodeList);
    		
    		if(nodeList!=null){
    			for(i=0; i<nodeList.length;i++){
    				
    				var treeid = nodeList[i].id;
    				alert(treeid);
    			}
    		}else{
    			
    		}
    		}
    	});
    
    } --%>
 /* function searchM() {
    	  var param =$("#keyword").val();
    	  
    	  var treeObj = $.fn.zTree.getZTreeObj("ztree");
    	  var node = treeObj.get
    	 
    	  if(param != ""){
    	    param = encodeURI(encodeURI(param));
    	    treeObj.setting.async.otherParam=["param", param];
    	  }else {
    	    //搜索参数为空时必须将参数数组设为空
    	    treeObj.setting.async.otherParam=[];
    	  }
    	  treeObj.reAsyncChildNodes(node, "refresh");
    	}

    	function zTreeOnNodeCreated(event, treeId, treeNode) {
    	  var param = $("#keyword").val();
    	  var treeObj = $.fn.zTree.getZTreeObj("ztree");
    	  //只有搜索参数不为空且该节点为父节点时才进行异步加载
    	  if(param != ""){
    	    treeObj.reAsyncChildNodes(treeNode, "refresh");
    	  } 
    	};
    */
   
   
   
</script>
</head>

<body>
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a><li><a href="#">按照品目查询供应商</a><li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <div class="col-md-3">
     
	 <div id="ztree" class="ztree"></div>
	</div>
		<div class="mt10 col-md-9">
			  <form id="form1" action="${pageContext.request.contextPath}/importSupplier/auditList.html" method="post">
		       <input type="hidden" name="page" id="page">
			   <span class="">供应商名称：</span>
			   <div class="input-append">
		        <input class="span2" name="supName" value="${name }" type="text">
		       </div>
		       <input class="btn padding-left-20 padding-right-20 btn_back" onclick="submit()" type="button" value="查询">
		     </form>
   <table id="tb1"  class="table table-bordered table-condensed tc">
		      <thead>
				<tr>
					<th class="info">供应商名称</th>
					<th class="info">联系人</th>
					<th class="info">电话</th>
					<th class="info">级别</th>
				</tr>
			  </thead>
			  <tbody>
				 <c:forEach items="${isList.list }" var="list" varStatus="vs">
					<tr>
						<td>${list.supplierName }</td>
						<td>${list.contactName}</td>
						<td>${list.contactTelephone}</td>
						<td>${list. }</td>
					</tr>
				</c:forEach> 
			  </tbody>
		 </table>
    <form  id="form" action="" name="fm" method="post"  enctype="multipart/form-data">
	    <input type="hidden"  onclick="check()" value="submit"/>
	    <input type="hidden"  onclick="mysubmit()" value="submit"/>
    <table id="result"  class="table table-bordered table-condensedb mt15" ></table>
    </form>
        </div>
	</div>
</body>
</html>

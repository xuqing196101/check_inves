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
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>
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
			   /*  edit:{
			    	enable:true,
					editNameSelectAll:true,
					showRemoveBtn: true,
					showRenameBtn: true,
					removeTitle: "删除",
					renameTitle:"重命名",
				}, */
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
	function getChildren(){
		var Obj=$.fn.zTree.getZTreeObj("ztree");  
	     var nodes=Obj.getCheckedNodes(true);  
	     var ids = new Array();  
	     for(var i=0;i<nodes.length;i++){ 
	    	 if(!nodes[i].isParent){
	        //获取选中节点的值  
	         ids.push(nodes[i].id); 
	    	 }
	     } 
	     $("#categoryIds").val(ids);
	}	
	  	  $(function(){
		  laypage({
			    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
			    pages: "${listSupplier.pages}", //总页数
			    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
			    skip: true, //是否开启跳页
			    total: "${listSupplier.total}",
			    startRow: "${listSupplier.startRow}",
			    endRow: "${listSupplier.endRow}",
			    groups: "${listSupplier.pages}">=5?5:"${listSupplier.pages}", //连续显示分页数
			    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
			        var page = location.search.match(/page=(\d+)/);
			        return page ? page[1] : 1;
			    }(), 
			    jump: function(e, first){ //触发分页后的回调
			        if(!first){ //一定要加此判断，否则初始时会无限刷新
			             location.href = '<%=basePath%>supplierUpdate/list.do?page='+e.curr;
			        }
			    }
			});
	  });
	  function submit(){
	  	form1.submit();
	  }
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
			  <form id="form1" action="${pageContext.request.contextPath}/supplierQuery/selectByCategory.html" method="post">
		       <input type="hidden" name="page" id="page">
		       <input type="hidden" id="categoryIds" name="categoryIds"/>
			   <span class="">供应商名称：</span>
			   <div class="input-append">
		        <input class="span2" name="supplierName"  type="text">
		       </div>
		       <input class="btn padding-left-20 padding-right-20 btn_back" onclick="submit()" type="button" value="查询">
		     </form>
   		<table id="tb1"  class="table table-striped table-bordered table-hover">
		      <thead>
				<tr>
					<th class="info">供应商名称</th>
					<th class="info">联系人</th>
					<th class="info">电话</th>
					<th class="info">级别</th>
				</tr>
			  </thead>
			  <tbody>
				 <c:forEach items="${listSupplier.list }" var="list" varStatus="vs">
					<tr>
						<td><a href="<%=basePath%>supplierQuery/essential.html?supplierId=${list.id}">${list.supplierName }</a></td>
						<td>${list.contactName}</td>
						<td>${list.contactTelephone}</td>
						<td></td>
					</tr>
				</c:forEach> 
			  </tbody>
		 </table>
		 <div id="pagediv" align="right"></div>
		 </div>
    <form  id="form" action="" name="fm" method="post"  enctype="multipart/form-data">
	    <input type="hidden"  onclick="check()" value="submit"/>
	    <input type="hidden"  onclick="mysubmit()" value="submit"/>
    <table id="result"  class="table table-bordered table-condensedb mt15" ></table>
    </form>
	</div>
</body>
</html>

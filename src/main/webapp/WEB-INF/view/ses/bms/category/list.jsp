<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="/WEB-INF/view/common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
 <base href="<%=basePath%>">
    
<title>My JSP 'category.jsp' starting page</title>

 <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/zTreeStyle.css"> 
<%-- <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/demo.css"> --%>

<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript" src="<%=basePath%>/public/ztree/jquery.ztree.exedit.js"></script>
<script src="<%=basePath%>public/layer/layer.js"></script>
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
						rootPId:"0",
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
    /* var lastValue ="", nodeList=[]; */
    /* var value ="";
    key.bind("propertychange",searchNode).bind("input",searchNode); */
}) 
    var treeObj = $.fn.zTree.getZTreeObj("ztree");

   /*  var key =$("#key");
	
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
     */
	
   /**删除图片*/
   function deletepic(treeid,obj){
		$(obj).prev().remove();
		$(obj).next().remove();
		$(obj).remove();
	}

    /**点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
		treeid=treeNode.id
		treename=treeNode.name
    }
    /**添加采购目录*/
    function news(){
			if (treeid==null) {
			alert("请选择一个节点");
					return;		
			}else{
				$.ajax({
					success:function(){
						var html = "";
						html = html+"<tr><td>排序</td>"+"<td><input value='"+treename+"'/></td></tr>";
						html = html+"<tr><td>目录名称</td>"+"<td><input name='name'/></td></tr>" ;
				        html = html+"<input type='hidden' value='"+treeid+"' name='parentId'/>";
						html = html+"<tr><td>排序</td>"+"<td><input name='position'/></td></tr>";
						html = html+"<tr><td>编码</td>"+"<td><input name='code'/></td></tr>";
						html = html+"<tr><td>图片</td>"+"<td id='uploadAttach'><input type='file' class='toinline' name='attaattach' value='上传图片'/></td></tr>";
						html = html+"<tr><td>描述</td>"+"<td><textarea name='description'/></td></tr>";
						html = html+"<tr><td colspan='2'><input type='submit' value='提交' onclick='check()' class='btn btn-window'/></td></tr>";
						$("#result").append(html);
				
					}
				
				})
			}
			
		}
	/**修改节点信息*/
    function update(){
	 		if (treeid==null) {
				alert("请选择一个节点");
			}else{
				$.ajax({
					url:"<%=basePath%>category/update.do?id="+treeid,
					dataType:"json",
					type:"get",
					success:function(cate){
	                 	
						var fileName = cate.categoryAttchment.attchmentPath;
						alert(fileName);
						if(fileName!=null){
						var fileNameTrue = fileName.split("/");
						
						var html = "";
						
						html = html+"<tr><td>目录名称</td><td><input value='"+cate.name+"' name='name'/></td></tr>";
						html = html+"<tr><td>上级目录</td><td><input value='"+cate.parentId+"' name='parentId'/></td></tr>";
						html = html+"<input type='hidden' name='id' value='"+cate.id+"'/>";
						html = html+"<tr><td>排序</td><td><input value='"+cate.position+"' name='position'/></td></tr>";
						html = html+"<tr><td>编码</td><td><input value='"+cate.code+"' name='code'/></td></tr>";
						html = html+"<tr><td>已上传的图片</td><td><button type='button' onclick='showPic('"+fileName+"')'>相关图片</button>"
						+"<img type='hide' id='photo' src='"+fileName+"'/>"
						+"<input type='file' name='attaattach' value='重新上传'/></td></tr>";
						html = html+"<tr><td>描述</td><td><textarea value='"+cate.description+"' name='description'/></td></tr>";
						html = html+"<tr><td colspan='2'><input type='submit' onclick='mysubmit()' value='更新' class='btn btn-window '/></td></tr>";
						$("#result").append(html);
					}
					}
				});
			}
 		}
  
    function showPic(url,name){
		layer.open({
			  type: 1,
			  title: false,
			  closeBtn: 0,
			  area: '50px',
			  skin: 'layui-layer-nobg', //没有背景色
			  shadeClose: true,
			  content: $("#photo")
			});
	};
    
 	/**休眠-激活*/
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
 		
 	/**重命名和删除的回调函数*/	
    function zTreeOnRemove(event, treeId, treeNode,isCancel) {
		}
    function zTreeOnRename(event, treeId, treeNode, isCancel) {
				 alert(treeNode.tId + ", " + treeNode.name); 
				
		}
	/**删除目录信息*/
    function zTreeBeforeRemove(treeId, treeNode){
	 		$.ajax({
	 			type:"post",
	 			url:"<%=basePath%>category/del.do?id="+treeNode.id,
	 		});
		}
	 	
	/**节点重命名*/
    function zTreeBeforeRename(treeId,treeNode,newName,isCancel){
			$.ajax({
	 			type:"post",
	 			url:"<%=basePath%>category/rename.do?id="+treeNode.id+"&name="+newName,
	 		});
		} 
    /**新增提交*/		
	function add(){
		document.fm.action="<%=basePath%>category/save.do";
		document.fm.submit();
	}
	/**更新数据*/
	function renew(id){
		document.fm.action="<%=basePath%>category/edit.do";
		document.fm.submit();
	}
	/**删除附件*/
	function del(){
	     window.location.href="<%=basePath%>category/deleted.do";
	}	
    /**关键字查询*/
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

<div class="wrapper">

  <div class="header-v4 header-v5">
    <!-- Navbar -->
    <div class="navbar navbar-default mega-menu" role="navigation">
      <div class="container">
        <!-- logo和搜索 -->
        <div class="navbar-header">
          <div class="row container">
            <div class="col-md-4 padding-bottom-30">
              <a href="">
                 <img alt="Logo" src="<%=basePath%>public/ZHH/images/logo_2.png" id="logo-header">
              </a>
            </div>
			<!--搜索开始-->
            <div class="col-md-8 topbar-v1 col-md-12 padding-0">
              <ul class="top-v1-data padding-0">
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_01.png"/></div>
				  <span>决策支持</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_02.png"/></div>
				  <span>业务监管</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_03.png"/></div>
				  <span>障碍作业</span>
				 </a>
				</li>	
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_04.png"/></div>
				  <span>信息服务</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_05.png"/></div>
				  <span>支撑环境</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_06.png"/></div>
				  <span>配置配置</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_07.png"/></div>
				  <span>后台首页</span>
				 </a>
				</li>
			    <li>
				<a href="#">
				  <div><img src="<%=basePath%>public/ZHH/images/top_08.png"/></div>
				  <span>安全退出</span>
				 </a>
				</li>
				
			  </ul>
			</div>
          </div>
	     </div>
	    </div>
       </div>
    </div>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a><li><a href="#">采购目录管理</a><li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <div class="col-md-3">
     
	 <div>
	   <input id="key" type="text" class="mt10"  placeholder="请输入..."  value=""/>
 	 <!--   <button onclick="searchM()" class="btn  btn-window mr10"  type="button">sou</button> -->
 	 </div>
 	  <div class="tag-box tag-box-v3">
	 <div><ul id="ztree" class="ztree"></ul></div>
	</div>
	</div>
		<div class=" tag-box tag-box-v4 mt50 col-md-9">
			<span id="add"><a href="javascript:void(0);" onclick="news()" class="btn btn-window ">新增 </a></span> 
			<span><a href="javascript:void(0);" onclick="update()"  class="btn btn-window ">修改</a></span> 
			<span><a href="javascript:void(0);" onclick="ros()"  class="btn btn-window ">激活/休眠</a></span>
  <%--  <form action="<%=basePath%>category/save.do" method="post" name="temp" enctype="multipart/form-data">
        
        <table id="result"  class="table table-bordered table-condensedb mt15" ></table>
    </form>
    < --%>
    <form  id="form" action="" name="fm" method="post"  enctype="multipart/form-data">
    <input type="hidden"  onclick="add()" value="submit"/>
    <input type="hidden"  onclick="renew()" value="submit"/>
    <input type="hidden" name="attchmentId" value=""/>
    <table id="result"  class="table table-bordered table-condensedb mt15" ></table>
    </form>
        </div>
	</div>
	
	<!--底部代码开始-->
    <div class="footer-v2 clear" id="footer-v2">
      <div class="footer">
            <!-- Address -->
              <address class="">
			  Copyright © 2016 版权所有：中央军委后勤保障部 京ICP备09055519号
              </address>
              <div class="">
		       浏览本网主页，建议将电脑显示屏的分辨率调为1024*768
              </div> 
            <!-- End Address -->
<!--/footer--> 
    </div>
    </div>
</body>
</html>

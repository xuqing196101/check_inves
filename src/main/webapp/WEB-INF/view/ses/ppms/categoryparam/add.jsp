<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'categoryparam.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
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
   
}) 
   
   /**点击事件*/
   function zTreeOnClick(event,treeId,treeNode){
		treeid=treeNode.id;
		$("#cateid").val(treeid);
   }
   /**添加采购参数*/
   function news(){
			if (treeid==null) {
			alert("请选择一个节点");
					return;		
			}else{
			alert(treeid)
						var html = "";
						html = html+"<tr><td><input name='name'/></td>"
						+"<td><select>"
						    +"<option name='valueType' value='a'>字符型</option>"
						    +"<option name='valueType' value='b'>数字型</option>"
						    +"<option name='valueType' value='c'>日期</option>"
						    +"</select</td></tr>";
						/* html = html+"<tr><td colspan='2'><input type='submit' value='提交'/></td></tr>"; */
						$("#result").prepend(html);
					   
				
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
					type:"post",
					success:function(cate){
						alert(cate.name);
						var html = "";
						html = html+"<tr><td></td><td><input value='"+cate.name+"'/></td></tr>";
					
						$("#result").append(html);
					}
				})
			}
		}
		
	/**重命名和删除的回掉函数*/	
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
    /**导入excel*/
    function imports(){
     window.location.href="<%=basePath%>categoryparam/import.do";
    }
    /**导出excel*/
    function exports(){
    window.location.href="<%=basePath%>categoryparam/exports.do";
    }
    function addAttach(){
		html="<input type='text' class='mt10' name='productName'/><a class=' btn mb15' onclick='deleteattach(this)'>X</a><br/>";
		$("#addinput").append(html);
	}
	 function addAtt(){
		html="<input type='text'  name='saleName'/><a class='ml30' onclick='deleteattach(this)'>X</a><br/>";
		$("#addnews").append(html);
	}
	function deleteattach(obj){
		$(obj).prev().remove();
		$(obj).next().remove();
		$(obj).remove();
	}
	
   <%--    /**新增提交*/		
	function check(){
		document.fm.action="<%=basePath%>category/save.do";
		document.fm.submit();
	} --%>
	<%-- /**更新数据*/
	function mysubmit(){
		document.fm.action="<%=basePath%>category/edit.do";
		document.fm.submit();
	}	
   --%>
</script>
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
		   <li><a href="#"> 首页</a><><li><a href="#">产品参数管理</a><><li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <div class="col-md-3">
     

	 <div><ul id="ztree" class="ztree"></ul></div>
	</div>
		<div class="mt10 col-md-9">
			<span id="add"><a href="javascript:void(0);" onclick="news()" class="btn btn-window ">添加参数 </a></span> 
			<span ><a href="javascript:void(0);" onclick="imports()" class="btn btn-window ">导入Excel</a></span>
			<span><a href="javascript:void(0);" onclick="exports()" class="btn btn-window ">导出Excel</a></span>
    <form action="<%=basePath%>categoryparam/save.do"  method="post" >
                     <input id="cateid" type="hidden" name="id" value=""/>
                <table id="result"  class="table table-bordered table-condensedb mt15" >
					 <tr><td >是否公开</td>
					 <td>
					 <span class="ml30"><input type="radio" value="0" name="ispublish" />是</span>
					 <span class="ml60"><input type="radio" value="1" name="ispublish"/>否</span>
					 </td></tr>
					 <!-- <tr><td>公布范围</td>
					 <td>
					 <span><input type="checkbox" value="true" name="scopePublic"/>外网</span>
					 <span><input type="checkbox" value="false" name="scopePublic"/>内网</span>
					 </td></tr> -->
					 <tr><td >产品类型</td>
					 <td>
					 <span class="ml30"><input type="checkbox" value="a" name="type"/>生产型</span>
					 <span class="ml30"><input type="checkbox" value="b" name="type"/>销售型</span>
					 <span class="ml30"><input type="checkbox" value="c" name="type"/>服务型</span>
					 <span class="ml30"><input type="checkbox" value="d" name="type"/>工程型</span>
					 </td></tr>
					 <tr><td>验证规范</td><td><textarea name="acceptRange"></textarea></td></tr>
					 <tr><td>生产型资质文件</td>
					 <td ><span id="addinput"></span><input type="text" name="productName"/></span>
					 <input  type="button" value="添加" onclick="addAttach()"/><br/>
					 </td></tr>
					 <tr><td>销售型资质文件</td>
					 <td><span id="addnews"></span><input type="text" name="saleName"/></span>
					 <input  type="button" value="添加" onclick="addAtt()"/><br/>
					 </td></tr>
					 <tr><td colspan="2"  >
					 <input type="submit" class="btn mr30"/>
					 <input type="button"class="btn" value="返回"/></td>
					 </tr>
				   
                </table>
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

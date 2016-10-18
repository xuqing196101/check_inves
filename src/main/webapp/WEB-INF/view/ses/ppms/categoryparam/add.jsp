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
 
    
}); 
   
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
				var html = "";
					html = html+"<tr><td><laber>参数名称：</label><input name='name'/></td>"
					+"<td><span>参数类型：</span><select name='valueType' calss='w200'>"
					+"<option>--请选择--</option>"
					+"<option value='字符型'>字符型</option>"
					+"<option value='数字型'>数字型</option>"
				    +"<option  value='日期'>日期</option>"
				    +"</select</td></tr>";
					$("#result").prepend(html);
			}
		}
    /**修改参数*/
    function update(){
        if(treeid==null){
        alert("请选择一个节点");
            return;
        }else{
          window.location.href="<%=basePath%>categoryparam/findOne.do?id="+treeid;
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
		html="<input class='mt10' type='text'  name='productName'/><a class='ml10 btn ' onclick='deleteattach(this)'>X</a><br/>";
		$("#addinput").append(html);
	}
	 function addAtt(){
		html="<input class='mt10' type='text'  name='saleName'/><a class='ml10  btn' onclick='deleteattach(this)'>X</a><br/>";
		$("#addnews").append(html);
	}
	function deleteattach(obj){
		$(obj).prev().remove();
		$(obj).next().remove();
		$(obj).remove();
	}
	
       /**新增提交*/		
	function fun(){
	    var name="";
    	var value="";
    	var str="";
    	var sts="";
    	var type="";
    	/**根据name获取各项数据的值*/
        obj = document.getElementsByName("name");
      
        
       abj = document.getElementsByName("valueType");
        
		cbj=document.getElementsByName("productName");
		
		dbj=document.getElementsByName("saleName");
		
		ebj=document.getElementsByName("type");
	
        for ( var i = 0; i < obj.length; i++) {
			name+=$(obj[i]).val()+",";
		}
        for ( var j = 0; j < abj.length; j++) {
			value+=$(abj[j]).val()+",";
		}
		for ( var k= 0; k < cbj.length; k++) {
			str+=$(cbj[k]).val()+",";
		}
        for ( var n = 0; n < dbj.length; n++) {
			sts+=$(dbj[n]).val()+",";
		}
		for ( var m = 0; m < ebj.length; m++) {
			type+=$(ebj[m]).val()+",";
		}
		$("#sss").val(name);
		$("#bbb").val(value);
		$("#ddd").val(str);
		$("#ccc").val(sts);
		$("#eee").val(type);
		$("#form").submit();
	} 
	
</script>
     </head>
 <body>

  
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a><li><a href="#">产品参数管理</a><li><a href="#">审核</a><li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
   <div class="col-md-3">
     
	 <div class="tag-box tag-box-v3 mt10">
	 <div><ul id="ztree" class="ztree "></ul></div>
	 </div>
	</div >
		<div class=" tag-box tag-box-v4 mt10 col-md-9">
			<span id="add"><a href="javascript:void(0);" onclick="news()" class="btn btn-windows add ">添加参数 </a></span>
			<span><a href="javascript:void(0);" onclick="update()" class="btn btn-windows edit">修改参数 </a></span>
			<span><a href="javascript:void(0);" onclick="imports()" class="btn btn-windows input">导入Excel</a></span>
			<span><a href="javascript:void(0);" onclick="exports()" class="btn btn-windows output">导出Excel</a></span>
			
                 <form id="form" action="<%=basePath%>categoryparam/save.do" method="post">
                     <input id="cateid" type="hidden" name="categoryId" value=""/>
                     <input type="hidden" id="sss" name="names" value="" />
                     <input type="hidden" id="bbb" name="values" value=" "/>
                     <input type="hidden" id="ddd" name="products" value=""/>
                     <input type="hidden" id="ccc" name="sales" value=""/>
                     <input type="hidden" id="eee" name="kinds" value=""/>
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
					 <span class="ml30"><input type="checkbox" value="E73923CC68A44E2981D5EA6077580372" name="type" id="box"/>生产型</span>
					 <span class="ml30"><input type="checkbox" value="18A966C6FF17462AA0C015549F9EAD79" name="type" id="box"/>销售型</span>
					 </td></tr>
					 <tr><td>验证规范</td><td><textarea name="acceptRange"></textarea></td></tr>
					 <tr><td>生产型资质</td>
					 <td><div id="addinput"><input  type="text" value="" name="productName"/>
					 <input  type="button" value="添加" onclick="addAttach()" class="mb10"/><br/></div>
					 </td></tr>
					 <tr><td>销售型资质</td>
					 <td><div id="addnews"><input  type="text" name="saleName" value=""/>
					 <input  type="button" value="添加" onclick="addAtt()" class="mb10"/><br/></div>
					 </td></tr>
				     <tr><td colspan="2"  >
					<input  type="button" class="btn mr30 btn-windows git" onclick="fun()" value="提交"/>
					 <input type="button"class="btn btn-windows back" value="返回" onclick="javascript:history.go(-1);"/>
					 </td></tr> 
                </table>
            </form>
       </div>
        </div>

	
  
  </body>
</html>

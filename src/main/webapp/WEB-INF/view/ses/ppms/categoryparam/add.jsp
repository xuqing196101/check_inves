<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML PUBLIC>
<html>
  <head>
<%@ include file="/WEB-INF/view/common.jsp" %>
    <title>产品参数添加</title>   
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
<script type="text/javascript">
    var datas;
	var treeid=null;
    $(document).ready(function(){
    	
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
		parentKind=treeNode.kind;
		
		$("#cateid").val(treeid);
   }
   /**添加采购参数*/
   function news(){
			if (treeid==null) {
				layer.alert("请选择一个节点",{offset: ['150px', '500px'], shade:0.01});

					return;		
			}else{
				if (parentKind=="服务型"||parentKind == "工程型") {
					$("#kind").hide();
				}
				var zTree = $.fn.zTree.getZTreeObj("ztree");
				nodes = zTree.getSelectedNodes();
				var node = nodes[0];
				if(node.isParent){
					layer.alert("当前节点无法添加参数",{offset: ['150px', '500px'], shade:0.01});
				}else{
				
				var html = "";
					html = html+"<tr><td class='info'>参数名</td><td><input class='mb0' type='text' name='name'/><div id='td_input' class='cue'></div></td>"
					+"<td class='info'>参数类型</td><td><select name='valueType'>"
					+"<option>--请选择--</option>"
					+"<option value='字符型'>字符型</option>"
					+"<option value='数字型'>数字型</option>"
				    +"<option  value='日期型'>日期型</option>"
				    +"</select><div id='td_select' class='cue'></div></td></tr>";
					$("#result").prepend(html);
				}
			}
		}
    /**修改参数*/
    function update(){
        	var zTree = $.fn.zTree.getZTreeObj("ztree");
			nodes = zTree.getSelectedNodes();
			var node = nodes[0];
        if(treeid==null){
        	layer.alert("请选择一个节点",{offset: ['150px', '500px'], shade:0.01});
            return;
        }else if(treeid!=null){
			
				layer.alert("当前节点无法添加参数",{offset: ['150px', '500px'], shade:0.01});
        }else{
          window.location.href="${pageContext.request.contextPath}/categoryparam/findOne.do?id="+treeid;
    
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
	 			url:"${pageContext.request.contextPath}/category/del.do?id="+treeNode.id,
	 		});
		}
	 	
	/**节点重命名*/
   function zTreeBeforeRename(treeId,treeNode,newName,isCancel){
			$.ajax({
	 			type:"post",
	 			url:"${pageContext.request.contextPath}/category/rename.do?id="+treeNode.id+"&name="+newName,
	 		});
		} 
    /**导入excel*/
    function imports(){
    	if (treeid==null) {
    		layer.alert("请选择一个节点",{offset: ['150px', '500px'], shade:0.01});
			return;
		}else{
     window.location.href="${pageContext.request.contextPath}/categoryparam/import.html?id="+treeid;
    }
    }
    /**导出excel*/
    function exports(){
    	if (treeid==null) {
    		layer.alert("请选择一个节点",{offset: ['150px', '500px'], shade:0.01});
			return;
		}else{
    window.location.href="${pageContext.request.contextPath}/categoryparam/exports.html?id="+treeid;
    }
    }
    /**添加资质文件输入框*/
    function addAttach(){
		html="<input class='mt10' type='text'  name='productName'/><input class='ml10' type='button' value='-' onclick='deleteattach(this)'/><br/>";
		$("#addinput").append(html);
	}
	 function addAtt(){
		html="<input class='mt10' type='text'  name='saleName'/><input class='ml10' type='button' value='-' onclick='deleteattach(this)'/><br/>";
		$("#addnews").append(html);
	 }
	 /**删除新增资质文件输入框*/
	 function deleteattach(obj){
			$(obj).prev().remove();
			$(obj).next().remove();
			$(obj).remove();
	 }
       /**新增提交*/		
	function fun(request){
    	
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
	    $.ajax({
	    	cache:true,
	    	dataType:"json",
	    	type:"post",
	    	data:$("#form").serialize(),
	    	url :"${pageContext.request.contextPath}/categoryparam/save.do",
	    	success:callback
	    })
	} 
       /**回显错误信息*/
      function callback(allListNews){
      $("#td_input").html(allListNews.name);
      $("#td_select").html(allListNews.value);
      $("#span_input").html(allListNews.ispublish);
      $("#span_td").html(allListNews.kind);
      $("#td_textarea").html(allListNews.acceptrange);
      $("#div_input").html(allListNews.product);
      $("#add_input").html(allListNews.sale);
      } 
	
</script>
     </head>
 <body>
	<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0);"> 首页</a><li><a href="javascript:void(0);">产品参数管理</a><li><a href="javascript:void(0);">首页</a><li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   <div class="container">
  	 	<div class="col-md-3 col-sm-4 col-xs-12">
		 	<div class="tag-box tag-box-v3 mt10">
	 			<ul id="ztree" class="ztree "></ul>
			 </div>
     	</div>
		<div class=" tag-box tag-box-v3 mt10 col-md-9 col-sm-8 col-xs-12">
			<span id="add"><a href="javascript:void(0);" onclick="news()" class="btn btn-windows add ">添加参数 </a></span>
			<span><a href="javascript:void(0);" onclick="update()" class="btn btn-windows edit">修改参数 </a></span>
			<span><a href="javascript:void(0);" onclick="imports()" class="btn btn-windows input">导入Excel</a></span>
			<span><a href="javascript:void(0);" onclick="exports()" class="btn btn-windows output">导出Excel</a></span>
            <form id="form" action="${pageContext.request.contextPath}/categoryparam/save.do" method="post">
                     <input id="cateid" type="hidden" name="id" value=""/>
                     <input type="hidden" id="sss" name="names" value="" />
                     <input type="hidden" id="bbb" name="values" value=" "/>
                     <input type="hidden" id="ddd" name="products" value=""/>
                     <input type="hidden" id="ccc" name="sales" value=""/>
                     <input type="hidden" id="eee" name="kinds" value=""/>
                     <table id="result"  class="table table-bordered table-condensedb mt15" >
				     <tr><td class="info">是否公开</td>
					 <td colspan="3">
					 <span class="ml30"><input type="radio" value="0" name="ispublish"  class="mt0"/> 是</span>
					 <span class="ml60"><input type="radio" value="1" name="ispublish" class="mt0"/> 否</span>
					 <div id="span_input" class="cue"></div>
					 </td></tr>
					 <tr id="kind"><td class="info" >产品类型</td>
					 <td colspan="3">
					 <span class="ml30"><input type="checkbox" value="E73923CC68A44E2981D5EA6077580372" name="type" id="box" class="mt0"/> 生产型</span>
					 <span class="ml60"><input type="checkbox" value="18A966C6FF17462AA0C015549F9EAD79" name="type" id="box" class="mt0"/>销售型</span>
					 <div id="span_td" class="cue"></div>
					 </td></tr>
					 <tr><td class="info">验收规范</td><td colspan="3"><textarea name="acceptRange" class="col-md-8 h100"></textarea><div id="td_textarea" class="cue"></div></td></tr>
					 <tr><td class="info">生产型资质</td>
					 <td colspan="3"><div id="addinput"><input  type="text" value="" name="productName"/>
					 <input  type="button" value="+" onclick="addAttach()" class="mb10"/><br/></div><div id="div_input" class="cue"></div></td>
					 </tr>
					 <tr><td class="info">销售型资质</td>
					 <td colspan="3"><div id="addnews"><input  type="text" name="saleName" value=""/>
					 <input  type="button" value="+" onclick="addAtt()" class="mb10"/><br/></div><div id="add_input" class="cue"></div></td></tr>
                     </table>
					 <div class="col-md-12 tc">
					 <input  type="button" class=" btn mr30 btn-windows git" onclick="fun()" value="提交"/>
					 <input type="button"class="btn btn-windows back" value="返回" onclick="javascript:history.go(-1);"/>
					 </div>
                  </form>
              </div>
         </div>
    </body>
</html>

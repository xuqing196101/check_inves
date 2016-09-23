<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title></title>
<link rel="stylesheet" type="text/css"
	href="<%=basePath%>/public/ztree/css/zTreeStyle.css">
<%-- <link rel="stylesheet" type="text/css" href="<%=basePath%>/public/ztree/css/demo.css"> --%>

<script type="text/javascript"
	src="<%=basePath%>/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/public/ztree/jquery.ztree.excheck.js"></script>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script type="text/javascript">
    var datas;
  $(function(){
     var setting={
        async:{
                    autoParam:["id"],
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
                }, 
                data:{
                    keep:{
                        parent:true,
                    },                  
                    simpleData:{
                        enable:true,
                        idKey:"id",
                        pIdKey:"pId",
                        rootPId:0,
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
               }
  };
    var treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
            
    })
    var treeid=null;
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
                /*      html = html+"<tr><td>父节点</td>"+"<td><input name='parentId'/></td></tr>"; */
                        html = html+"<tr><td>排序</td>"+"<td><input name='position'/></td></tr>";
                        html = html+"<tr><td>编码</td>"+"<td><input name='code'/></td></tr>";
                        html = html+"<tr><td>附件</td>"+"<td id='uploadAttach'><input id='pic'type='file' class='toinline' name='attaattach' /><input class='toinline' type='button' value='添加' onclick='addAttach()'/></td></tr>";
                        html = html+"<tr><td>描述</td>"+"<td><input name='descrption'/></td></tr>";
                        html = html+"<tr><td colspan='2'><input type='submit' value='提交' class='btn btn-window'/></td></tr>"
                        $("#result").append(html);
                    }
                
                })
            }
            
        }
    function addAttach(){
        html="<input id='pic' type='file' class='toinline' name='attaattach'/><a href='#' onclick='deleteattach(this)' class='toinline'>x</a><br/>";
        $("#uploadAttach").append(html);
    }
        /*修改节点信息*/
    function update(){
            if (treeid==null) {
                alert("请选择一个节点");
            }else{
                $.ajax({
                    dataType:"json",
                    type:"post",
                    success:function(){
                        var html = "";
                        html = html+"<tr><td>目录名称</td>"+"<td></td></tr>";
                        /* html = html+"<tr><td>父节点</td>"+"<td></td></tr>"; */
                        html = html+"<tr><td>父节点</td>"+"<td></td></tr>";
                        html = html+"<tr><td>排序</td>"+"<td></td></tr>";
                        html = html+"<tr><td>编码</td>"+"<td></td></tr>";
                        html = html+"<tr><td>附件</td>"+"<td></td></tr>";
                        html = html+"<tr><td>描述</td>"+"<td></td></tr>";
                        html = html+"<tr><td colspan='2'><input type='submit' value='更新'/></td></tr>"
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
        
    
</script>
</head>
<body>
<!-- 修改订列表开始-->
   <div class="container">
   <form action="<%=basePath %>SupplierExtracts/listSupplier.do" method="post">
   <div>
   <div class="headline-v2">
   </div>
   <ul class="list-unstyled list-flow p0_20">
            <input class="span2" name="id" type="hidden" >
             <li class="col-md-6 p0 " >
                            产品目录名称：
               <div class="input-append">
                <input class="span2 w200" name="title" type="text" >
               </div>
             </li>
             <li class="col-md-6  p0 " >
                <div class="fl mr10"><input type="radio" name="radio" value="${type.id}^${type.name}"  class="fl"/><div class="ml5 fl">满足某一产品条件即可</div></div>
                   <div class="fl mr10"><input type="radio" name="radio" value="${type.id}^${type.name}"  class="fl"/><div class="ml5 fl">同时满足多个产品条件</div></div>
             </li> 
   </ul>
   <br/>
    <br/>
  </div>   
      <div id="ztree" class="ztree"></div>
  <div  class="col-md-12">
    <div class="fl padding-10">
        <button class="btn btn-windows reset" type="submit">确定</button>
        <button class="btn btn-windows git"  type="reset">清空</button>
    </div>
  </div>
  </form>
 </div>			
		
</body>
</html>

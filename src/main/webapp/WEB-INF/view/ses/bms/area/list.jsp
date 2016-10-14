<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>地区管理</title>
<script src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/public/ztree/css/zTreeStyle.css">
<script type="text/javascript"
	src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/public/layer/layer.js"></script>
<script type="text/javascript">
        var datas;
        var setting;
        function loadTree(name) {
            
            var url = "<%=basePath%>area/listByOne.do";
            if (name) {
                url = "<%=basePath%>area/listByOne.do?name=" + name; 
            }
	        /*树的设置*/
	        setting={
	            async:{
	                autoParam:["id"],
	                enable:true,
	                url:url,
	                dataType:"json",
	                type:"post",
	            },
	            data:{
	                simpleData:{
	                    enable:true,
	                    idKey:"id",
	                    pId:"pId",
	                    rootPId:-1,
	                }
	            },
	            callback:{
	               onClick:zTreeOnClick
	            }
	        };
	         var treeObj=$.fn.zTree.init($("#tree"),setting,datas);
        }
        
        function zTreeOnClick(event,treeId,treeNode){
            $("#mid").val(treeNode.id);
        };
        
        
     $(function(){
      
        loadTree("");
        
        treeObj.expandAll(false);
    });
     
    function selectTree() {
        var name = $("input[name='name']").val();
        loadTree(name);
    }
    
      function add(){
        var pid = $("#mid").val();
        layer.open({
          type: 2, //page层
          area: ['430px', '400px'],
          title: '添加地区',
          closeBtn: 1,
          shade:0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['120px', '550px'],
          shadeClose: false,
          content: '<%=basePath%>area/add.html?pid='+pid
        }); 
        }
        function edit(){
        var pid = $("#mid").val();
        if(pid != null && pid != '' ){
        layer.open({
          type: 2, //page层
          area: ['430px', '400px'],
          title: '添加地区',
          closeBtn: 1,
          shade:0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['120px', '550px'],
          shadeClose: false,
          content: '<%=basePath%>area/edit.html?pid='+pid
        }); 
        }else{
            layer.alert("请选择一个节点",{offset: ['222px', '390px'], shade:0.01});
        }
        }
        function del(){
        var mid = $("#mid").val();
        if(mid != null && mid != '' ){
            layer.confirm('您确定要删除该地区吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
                layer.close(index);
                window.location.href="<%=basePath%>area/delete.html?id="+mid;
            });
        }else{
            layer.alert("请选择要删除的地区",{offset: ['222px', '390px'], shade:0.01});
        }
    }
</script>

</head>

<body>
    <!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">后台管理</a></li><li class="active"><a href="#">菜单功能管理</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
   <div class="container">
       <div class="headline-v2">
            <h2>菜单功能管理</h2>
       </div>
   </div>
   
   
   <div class="container content height-350">
     <label class="fl">地区名称：<input type="text"
                                id="search_condition" name="name" /> </label>
                            <button
                                class="btn padding-left-10 padding-right-10 btn_back fl margin-top-5"
                                onclick="selectTree();">查询</button>
       <div class="row">
                <!-- Begin Content -->
                <div class="col-md-12" style="min-height:400px;">
                    <div class="col-md-3 md-margin-bottom-40" id="show_tree_div">
                        <div class="tag-box tag-box-v3" style="overflow:auto;">
                            <ul id="ztree_show" class="ztree" >
                                <!-- 菜单树-->
                                <div id="tree" class="ztree"></div>
                            </ul>
                        </div>
                    </div>
                    <div style="margin-bottom: 6px; ">
                        <button class="btn btn-windows add" type="button" onclick="add();">新增</button> 
                        <button class="btn btn-windows edit" type="button" onclick="edit();">修改</button> 
                        <button class="btn btn-windows delete" type="button" onclick="del();">删除</button> 
                        <input type="hidden" name="nodeId" id="mid">
                       
                    </div>
                    <div class="tag-box tag-box-v4 col-md-9" id="show_content_div">
                        <input type="hidden" name="nodeId" id="mid">
                        <div class="tab-pane fade in" id="dep_tab-2">
                           <ul class="timeline-v2" id="con">
                           </ul>
                       </div>
                    </div>
                </div>
          </div>
      </div>
      <!--=== End Content Part ===-->
  </body>
</html>

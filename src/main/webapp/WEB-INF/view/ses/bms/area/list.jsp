<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<script type="text/javascript">
        var datas;
        var setting;
        var treeObj;
        function loadTree(name) {
            
            var url = "${pageContext.request.contextPath}/area/listByOne.do";
            if (name) {
            	name = encodeURI(name);
                url = "${pageContext.request.contextPath}/area/listByOne.do?name=" + name; 
            }
            /*树的设置*/
            setting={
                async:{
                    autoParam:["id"],
                    enable:true,
                    url:url,
                    dataType:"json",
                    type:"post"
                },
                data:{
                    simpleData:{
                        enable:true,
                        idKey:"id",
                        pId:"pId",
                        rootPId:0
                    }
                },
                callback:{
                   onClick:zTreeOnClick
                }
            };
            treeObj=$.fn.zTree.init($("#tree"),setting,datas);
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
	   /*      layer.open({
	          type: 2, //page层
	          area: ['430px', '400px'],
	          title: '添加地区',
	          closeBtn: 1,
	          shade:0.01, //遮罩透明度
	          moveType: 1, //拖拽风格，0是默认，1是传统拖动
	          shift: 1, //0-6的动画形式，-1不开启
	          offset: ['120px', '550px'],
	          shadeClose: false,
	          content: '${pageContext.request.contextPath}/area/add.html?pid='+pid
	        });  */
	        window.location.href="${pageContext.request.contextPath}/area/add.html?pid="+pid;
        }
        function edit(){
	        var pid = $("#mid").val();
	        if(pid != null && pid != '' ){
		        /* layer.open({
		          type: 2, //page层
		          area: ['430px', '400px'],
		          title: '修改地区',
		          closeBtn: 1,
		          shade:0.01, //遮罩透明度
		          moveType: 1, //拖拽风格，0是默认，1是传统拖动
		          shift: 1, //0-6的动画形式，-1不开启
		          offset: ['120px', '550px'],
		          shadeClose: false,
		          content: '${pageContext.request.contextPath}/area/edit.html?pid='+pid
		        });  */
		        window.location.href="${pageContext.request.contextPath}/area/edit.html?pid="+pid;
	        }else{
	            layer.alert("请选择一个节点",{offset: ['222px', '390px'], shade:0.01});
	        }
        }
        function del(){
        var mid = $("#mid").val();
        if(mid != null && mid != '' ){
            layer.confirm('您确定要删除该地区吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
                layer.close(index);
                window.location.href="${pageContext.request.contextPath}/area/delete.html?id="+mid;
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
           <li><a href="javascript:void(0)"> 首页</a></li><li><a href="javascript:void(0)">支撑系统</a></li><li><a href="javascript:void(0)">后台管理</a></li><li class="active"><a href="javascript:void(0)">地区管理</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
   <div class="container mt20">
       <div class="headline-v2">
            <h2>地区管理</h2>
       </div>
   </div>
   
   
   <div class="container content">
        <h2>
            <ul class="demand_list fl pr20 ">
                <li class="pr20">
                 <label class="">地区名称：</label><input class="w110" type="text" id="search_condition" name="name" /> 
                <button  class="btn  ml5 fl mt1 " onclick="selectTree();">查询</button>
                </li>
            </ul>
            <br>
         </h2>
       <div class="row">
                <!-- Begin Content -->
                <div class="col-md-12" style="min-height:400px;">
                    <div class="col-md-3 col-sm-4 col-xs-12" id="show_tree_div">
                        <div class="tag-box tag-box-v3 over_auto">
                            <ul id="ztree_show" class="ztree s_ztree" >
                                <!-- 菜单树-->
                                <div id="tree" class="ztree"></div>
                            </ul>
                        </div>
                    </div>
                    <div class="tag-box tag-box-v4 col-md-9 col-sm" id="show_content_div">
                    <div style="margin-bottom: 6px; ">
                        <button class="btn btn-windows add" type="button" onclick="add();">新增</button> 
                        <button class="btn btn-windows edit" type="button" onclick="edit();">修改</button> 
                        <button class="btn btn-windows delete" type="button" onclick="del();">删除</button> 
                    </div>
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

<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML PUBLIC>
<html>
  <head>  
<%@ include file="/WEB-INF/view/common.jsp"%>
  
    <title>My JSP 'allocate.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

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
     };
        $.fn.zTree.init($("#ztree"),setting,datas); 
       var  treeObj = $.fn.zTree.getZTreeObj("ztree");
//        treeObj.expandAll(false);
       
      }); 

    /**点击事件*/
    function zTreeOnClick(event,treeId,treeNode){
    	   $("#cateid").val(treeNode.id);
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
          content: '${pageContext.request.contextPath}/area/add.html?pid='+pid
        }); 
    }
    
    function edit(){
        var pid = $("#mid").val();
        if(pid != null && pid != '' ){
            layer.open({
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
            }); 
        }else{
            layer.alert("请选择一个节点",{offset: ['222px', '390px'], shade:0.01});
        }
    }
    
    function getDetail(id){
        $.ajax({   
            type: "POST",  
            dataType: "json",
            async:false,
            url: "${pageContext.request.contextPath}/preMenu/get.do?id="+id,         
            success: function(data) {
                if(data != null && data != ''){
                    var tabhtml = "";
                    var state;
                    var kind;
                    var pName;
                    if(data[0].status == 0){
                        state = "<span class='label rounded-2x label-u'>可用</span>";
                    }else if(data[0].status == 1){
                        state = "<span class='label rounded-2x label-dark'>暂停</span>";
                    }
                    if(data[0].kind == 0){
                        kind = "采购管理后台";
                    }else if(data[0].kind == 1){
                        kind = "供应商后台";
                    }else if(data[0].kind == 2){
                        kind = "专家后台";
                    }else if(data[0].kind == 3){
                        kind = "进口供应商后台";
                    }
                    if(data[0].parentId == null){
                        pName = "";
                    }else{
                        pName = data[0].parentId.name;
                    }
                    tabhtml +='<h2 class="f16 jbxx">菜单详情</h2><table class="table table-bordered"><tbody>';
                    tabhtml +='<tr><td class="bggrey tr">上级菜单：</td><td>'+pName+'</td>';
                    tabhtml +='<td class="bggrey tr">菜单名称：</td><td>'+data[0].name+'</td>';
                    tabhtml +='<td class="bggrey tr">请求路径：</td><td>'+data[0].url+'</td></tr>';
                    tabhtml +='<tr><td class="bggrey tr">菜单类型：</td><td>'+data[0].type+'</td>';
                    tabhtml +='<td class="bggrey tr">菜单序号：</td><td>'+data[0].position+'</td>';
                    tabhtml +='<td class="bggrey tr">菜单级别：</td><td>'+data[0].menulevel+'</td></tr>';
                    tabhtml +='<tr><td class="bggrey tr">菜单状态：</td><td>'+state+'</td>';
                    tabhtml +='<td class="bggrey tr">菜单图标：</td><td>'+data[0].icon+'</td>';
                    tabhtml +='<td class="bggrey tr">创建时间：</td><td>'+data[0].createdAt+'</td></tr>';
                    tabhtml +='<tr><td class="bggrey tr">修改时间：</td><td colspan="5">'+data[0].updatedAt+'</td></tr>';
                    tabhtml +='</tbody></table>';
                    $("#show_content_div").html("");
                    $("#show_content_div").append(tabhtml);
                }
            }  
        });
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
		   <li><a href="#"> 首页</a></li><li><a href="#">产品参数管理</a></li><li><a href="#">创建品目</a></li>
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
        <div class="w100p fl mb5">
           <button class="btn btn-windows add" type="button" onclick="add();">新增子节点</button> 
           <button class="btn btn-windows edit" type="button" onclick="edit();">保存</button> 
           <button class="btn btn-windows delete" type="button" onclick="del();">删除</button> 
        </div>
        <input type="hidden" name="nodeId" id="mid">
           <ul class="ul_list">
              <li class="col-md-6 margin-0 padding-0 ">
		         <span class="col-md-12 padding-left-5">名称</span>
			      <div class="input-append">
			        <input class="span5" id="appendedInput" type="text">
			          <span class="add-on">i</span>
				  </div>
			  </li>  
			  <li class="col-md-12 col-sm-12 col-xs-12 margin-0 padding-0 ">
			     <span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">描述</span>
				   <div class=" col-md-12 ol-sm-12 col-xs-12">
				       <textarea class="w100p h100"  title="不超过800个字"></textarea>
				   </div>
			  </li> 
           </ul>
         </div>
      </div>
  </body>
</html>

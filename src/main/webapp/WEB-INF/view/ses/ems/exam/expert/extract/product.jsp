<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../../../common.jsp"%>
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
<script type="text/javascript"
	src="<%=basePath%>/public/ztree/jquery.ztree.core.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/public/ztree/jquery.ztree.excheck.js"></script>
<script type="text/javascript"
	src="<%=basePath%>/public/ztree/jquery.ztree.exedit.js"></script>
<!-- Meta -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<script type="text/javascript">
    var datas;
    var treeObj;
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
                          
                      }, 
                      data:{
                          simpleData:{
                              enable:true,
                              idKey:"id",
                              pIdKey:"pId",
                              rootPId:0,
                          }
                      },
                     check:{
                          chkboxType:{"Y" : "ps", "N" : "ps"},//勾选checkbox对于父子节点的关联关系  
                          chkStyle:"checkbox", 
                          enable: true
                     }
        };
    treeObj=$.fn.zTree.init($("#ztree"),setting,datas);
            
    });
    var treeid=null;
  /*树点击事件*/
  function zTreeOnClick(event,treeId,treeNode){
      treeid=treeNode.id
      
      
  }
  //获取选中子节点id
  function getChildren(){
      var Obj=$.fn.zTree.getZTreeObj("ztree");  
       var nodes=Obj.getCheckedNodes(true);  
       var ids = new Array();
       var names=new Array();
       for(var i=0;i<nodes.length;i++){ 
           if(!nodes[i].isParent){
          //获取选中节点的值  
           ids+=nodes[i].id+"^"; 
           names+=nodes[i].name+"^";
           }
       } 

       //专家数量
//         parent.$("#extcount").val($("#extcount").val());
//        //专家类型
//          parent.$("#exttypeid").val($("#exttypeid").val());
//          // 执业资格 
//          parent.$("#extqualifications").val($("#extqualifications").val());
//        //品目name
//          parent.$("#extheadingname").val(names);
//        //品目id
//          parent.$("#extheading").val(ids);
         //类型
          var expertstypeid=$('input[name="expertstypeid"]:checked ').val();
          //是否满足
          var issatisfy=$('input[name="radio"]:checked ').val();
          
    
          
          
          var html='';
          html+="<tr>"+
             "<input class='hide' name='categoryId'  type='hidden' value='"+ids+"'>"+
             "<input class='hide' name='isSatisfy'  type='hidden' value='"+issatisfy+"'>"+
             "<input class='hide' name='expertsTypeId' readonly='readonly' type='hidden' value='"+expertstypeid+"'>"+
	              "<td class='tc w30'><input type='checkbox' value=''"+
	                  "name='chkItem' onclick='check()'></td>"+
	              "<td class='tc'>";
	              if(expertstypeid==1){
	            	   html+="<input class='hide' name='expertsTypeId' readonly='readonly' type='text' value='技术'>";
	              }else if(expertstypeid==2){
	            	    html+="<input class='hide' name='expertsTypeId' readonly='readonly' type='text' value='法律'>";
	              }else if(expertstypeid==3){
	            	    html+="<input class='hide' name='expertsTypeId' readonly='readonly' type='text' value='商务'>";
	              }
                  html+="</td>"+
	              "<td class='tc'><input class='hide' name='extCount' readonly='readonly' type='text' value='"+$('#extcount').val()+"'></td>"+
	              "<td class='tc'><input class='hide' name='extQualifications' readonly='readonly' type='text' value='"+$('#extqualifications').val()+"'></td>"+
	              "<td class='tc'><input class='hide' name='categoryName' readonly='readonly' type='text' value='"+names+"'></td>"+
	             "</tr>";
	             parent.$("#tbody").append(html);
	             var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	             parent.layer.close(index);
  }
      
</script>
</head>
<body>
	<!-- 修改订列表开始-->
	<div class="container">
		<form action="<%=basePath%>SupplierExtracts/listSupplier.do"
			method="post">
			<div>
				<ul class="list-unstyled list-flow p0_20">
					<input class="span2" name="id" type="hidden">
					<li class="col-md-6 p0 ">专家类型
 					
							<input name="expertstypeid" type="radio" value="1">技术<input
								name="expertstypeid" type="radio" value="2">商务<input
								name="expertstypeid" type="radio" value="3">法律
 					
					</li>
					<li class="col-md-6 p0 ">专家数量：
						<div class="input-append">
							<input class="span2 w200" id="extcount" name="title" type="text">
						</div>
					</li>
					<li class="col-md-6 p0 ">执业资格：
						<div class="input-append">
							<input class="span2 w200" id="extqualifications" name="title"
								type="text">
						</div>
					</li>
					<li class="col-md-6 p0 ">产品目录名称：
						<div class="input-append">
							<input class="span2 w200" name="title" type="text">
						</div>
					</li>
					<li class="col-md-6  p0 ">
						<div class="fl mr10">
							<input type="radio" name="radio" id="radio" value="1" class="fl" />
							<div class="ml5 fl">满足某一产品条件即可</div>
						</div>
						<div class="fl mr10">
							<input type="radio" name="radio" id="radio" value="2" class="fl" />
							<div class="ml5 fl">同时满足多个产品条件</div>
						</div>
					</li>
				</ul>
				<br /> <br />
			</div>
			<div id="ztree" class="ztree"></div>
			<div class="col-md-12">
				<div class="fl padding-10">
					<button class="btn btn-windows reset" type="button"
						onclick="getChildren();">确定</button>
					<button class="btn btn-windows git" type="reset">清空</button>
				</div>
			</div>
		</form>
	</div>

</body>
</html>

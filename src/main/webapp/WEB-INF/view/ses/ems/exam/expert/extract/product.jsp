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
	<link rel="stylesheet"
    href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
    type="text/css">
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
                          url:"${pageContext.request.contextPath}/category/createtree.do",
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
	     var Obj=$.fn.zTree.getZTreeObj("ztree");  
    });
    var treeid=null;
  /*树点击事件*/
  function zTreeOnClick(event,treeId,treeNode){
      treeid=treeNode.id;
  }
  //获取选中子节点id
  function getChildren(){
      var Obj=$.fn.zTree.getZTreeObj("ztree");  
       var nodes=Obj.getCheckedNodes(true);  
       var ids = new Array();
       var names=new Array();
       var copynams=" ";
       for(var i=0;i<nodes.length;i++){ 
           if(!nodes[i].isParent){
          //获取选中节点的值  
           ids+=nodes[i].id+"^"; 
           names+=nodes[i].name+"^";
           copynams+=nodes[i].name+",";
           }
       } 
     //类型
       var expertstypeid=$('input[name="expertstypeid"]:checked ').val();
       if($('#extcount').val()==''){
           layer.msg('请输入抽取数量');
       }else if($("#extqualifications").val()==''){
           layer.msg('请输入执业资格'); 
       }else if(ids==''&&expertstypeid==1){
           layer.msg('请选择品目');
       }else{
         
          //是否满足
          var issatisfy=$('input[name="radio"]:checked ').val();
          if(issatisfy==null){
        	  issatisfy=0;
          }
          var html='';
          html+="<tr>"+
             "<input class='hide' name='extCategoryId'  type='hidden' value='"+ids+"'>"+
             "<input class='hide' name='isSatisfy'  type='hidden' value='"+issatisfy+"'>"+
             "<input class='hide' name='expertsTypeId' readonly='readonly' type='hidden' value='"+expertstypeid+"'>"+
             "<input class='hide' name='extCategoryName' readonly='readonly' type='hidden' value='"+names+"'>"+
	              "<td class='tc w30'><input type='checkbox' value=''"+
	                  "name='chkItem' onclick='check()'></td>"+
	              "<td class='tc'>";
	              if(expertstypeid==1){
	            	   html+="<input class='hide' readonly='readonly' type='text' value='技术'>";
	              }else if(expertstypeid==2){
	            	    html+="<input class='hide' readonly='readonly' type='text' value='法律'>";
	              }else if(expertstypeid==3){
	            	    html+="<input class='hide' readonly='readonly' type='text' value='商务'>";
	              }
                  html+="</td>"+
	              "<td class='tc'><input class='hide' name='extCount' readonly='readonly' type='text' value='"+$('#extcount').val()+"'></td>"+
	              "<td class='tc'><input class='hide' name='extQualifications' readonly='readonly' type='text' value='"+$('#extqualifications').val()+"'></td>"+
	              "<td class='tc'><input class='hide' name='' readonly='readonly' type='text' value='"+copynams.substring(0,copynams.length-1)+"'></td>"+
	             "</tr>";
	             parent.$("#tbody").append(html);
	             var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	             parent.layer.close(index);
       }
  }
  function exptype(){
	   $("#ztree").css("display","none");
	   $("#liradio").css("display","none");
	   var x=document.getElementsByName("radio");  
	    for(var i=0;i<x.length;i++){ //对所有结果进行遍历，如果状态是被选中的，则将其选择取消  
	        if (x[i].checked==true)  
	        {  
	            x[i].checked=false;  
	        }  
	    }  
  }
  function exptype1(){
      $("#ztree").css("display","block");
      $("#liradio").css("display","block");
      var x=document.getElementsByName("radio");  //获取所有name=brand的元素  
              x[0].checked=true;  
   
 }
</script>
</head>
<body>
	<!-- 修改订列表开始-->
	<div class="container padding-top-20   ">
		<form action="${pageContext.request.contextPath}/SupplierExtracts/listSupplier.do"
			method="post">
			<div>
				<ul class="list-unstyled list-flow p0_20">
					<input class="span2" name="id" type="hidden">
					<li class="col-md-6 p0 fl">
						<div class="fl mr10"><span class="red textspan">*</span>专家类型：</div>
						<div class="fl mr10">
							<input name="expertstypeid" checked="checked" type="radio"
								onclick="exptype1();" class="fl" value="1">
							<div class="ml5 fl">技术</div>
						</div>
						<div class="fl mr10">
							<input name="expertstypeid" type="radio" onclick="exptype();"
								class="fl" value="2">
							<div class="ml5 fl">法律</div>
						</div>
						<div class="fl mr10">
							<input name="expertstypeid" type="radio" onclick="exptype();"
								class="fl" value="3">
							<div class="ml5 fl">商务</div>
						</div>
					</li>
					<li class="col-md-6 p0 fl"><span class="red textspan">*</span>专家数量：
						<div class="input-append">
							<input  maxlength="4"  onkeyup="this.value=this.value.replace(/\D/g,'')"
                        onafterpaste="this.value=this.value.replace(/\D/g,'')" class="span2 w200" value="10" id="extcount" name="title"
								type="text">
						</div>
					</li>
					<li class="col-md-6 p0 fl"><span class="red textspan">*</span>执业资格：
						<div class="input-append">
							<input maxlength="30" class="span2 w200" id="extqualifications" name="title"
								type="text">
						</div>
					</li>
					<li class="col-md-6  p0 " id="liradio">
						<div class="fl mr10">
							<input type="radio" name="radio" id="radio" checked="checked"
								value="1" class="fl" />
							<div class="ml5 fl">满足某一产品条件即可</div>
						</div>
						<div class="fl mr10">
							<input type="radio" name="radio" id="radio" value="2" class="fl" />
							<div class="ml5 fl">同时满足多个产品条件</div>
						</div>
					</li>
				</ul>
			</div>
			<div id="ztree" class="ztree margin-left-13" ></div>
			
		</form>
	</div>
</body>
</html>

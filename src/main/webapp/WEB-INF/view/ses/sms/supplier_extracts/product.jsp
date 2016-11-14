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
                          onCheck:zTreeOnCheck
                          
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
      treeid=treeNode.id;
  }
  /*树点击事件*/
  var check;
  var sccuess=''; //品目父节点
  function zTreeOnCheck(event,treeId,treeNode){
      treeid=treeNode.id;
      var pNode = treeNode.getParentNode();
      if(pNode!=null){
      var name=treeNode.getParentNode();
      while(pNode!=null) {
    	  if(pNode.getParentNode()!=null){
    		  name = pNode.getParentNode();
    		  pNode = pNode.getParentNode();
    	  }else{
    		  pNode=null;
    	  }
    	}
      }else{
    	  name=treeNode;
      }
      alert(name.kind);
      if(name.kind==0){  
    	 var boo= name.checked;
    	 if(boo==false){
    		  $("#ultype").css("display","none");
    		  document.getElementById("xschecked").checked=false;
    		  sccuess='';
    	 }else{
    		  $("#ultype").css("display","block");
    		  document.getElementById("xschecked").checked=true;
    		  sccuess='sccuess';
    	 }
      }else{
    	  if(sccuess!='sccuess'){
    		  document.getElementById("xschecked").checked=false;  
    		  sccuess='';
    	  }
      }
      
  }
  //获取选中子节点id
  function getChildren(){
      var Obj=$.fn.zTree.getZTreeObj("ztree");  
       var nodes=Obj.getCheckedNodes(true);  
       var ids = new Array();
       var names=new Array();
       var copynames=new Array();
       for(var i=0;i<nodes.length;i++){ 
           if(!nodes[i].isParent){
          //获取选中节点的值  
           ids+=nodes[i].id+"^"; 
           names+=nodes[i].name+"^";
           copynames+=nodes[i].name+",";
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
       
          var expertstypeid=""; 
          $('input[name="expertstypeid"]:checked').each(function(){ 
        	  expertstypeid+=$(this).val()+"^";
          }); 
          var html='';
          //是否满足
          var issatisfy=$('input[name="radio"]:checked ').val();
          if($('#extcount').val()==''){
        	  layer.msg('请输入抽取数量');
          }else if(ids==''){
        	  layer.msg('请选择品目');
          }else if(sccuess=='sccuess' && expertstypeid==''){
        	  layer.msg('请选择类型');
          }else {
          
          html+="<tr>"+
             "<input class='hide' name='extCategoryId'  type='hidden' value='"+ids+"'>"+
             "<input class='hide' name='isSatisfy'  type='hidden' value='"+issatisfy+"'>"+
             "<input class='hide' name='extCategoryName' readonly='readonly' type='hidden' value='"+names+"'>"+
             "<input class='hide' name='expertsTypeId' readonly='readonly' type='hidden' value='"+expertstypeid+"'>"+
	              "<td class='tc w30'><input type='checkbox' value=''"+
	                  "name='chkItem' onclick='check()'></td>"+
	              "<td class='tc'>";
	              if(expertstypeid=='1^2^'){
	            	   html+="<input class='hide' readonly='readonly' type='text' value='生产型,销售型'>";
	              }else if(expertstypeid=='1^'){
	            	    html+="<input class='hide' readonly='readonly' type='text' value='生产型'>";
	              }else if(expertstypeid=='2^'){
	            	    html+="<input class='hide' readonly='readonly' type='text' value='销售型'>";
	              }
                  html+="</td>"+
	              "<td class='tc'><input class='hide' name='extCount' readonly='readonly' type='text' value='"+$('#extcount').val()+"'></td>"+
	             
	              "<td class='tc'><input class='hide' name='' readonly='readonly' title='"+copynames.substring(0,copynames.length-1)+"' type='text' value='"+copynames.substring(0,copynames.length-1)+"'></td>"+
	             "</tr>";
	             
	             parent.$("#tbody").append(html);
	             var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
	             parent.layer.close(index);
          }
  }     
  function resetQuery(){
	  alert("sds");
//       $("#form1").find(":input").not(":button,:submit,:reset,:hidden").val("10");
  }
  
</script>
</head>
<body>
	<!-- 修改订列表开始-->
	<div class="container margin-top-30">
		<form action="${pageContext.request.contextPath}/SupplierExtracts/listSupplier.do"
			method="post" id="form1">
			<div>
				<ul class="list-unstyled list-flow p0_20">
					<input class="span2" name="id" type="hidden">
					<li class="col-md-6 p0 ">
				    	<span class="red textspan">*</span>抽取数量：
						<div class="input-append">
							<input class="span2 w200" maxlength="3" id="extcount" value="10" name="title" type="text" onkeyup="this.value=this.value.replace(/\D/g,'')"
                                onafterpaste="this.value=this.value.replace(/\D/g,'')">
						</div>
					</li>
					<li class="col-md-6  p0 ">
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
				<br />
			</div>
			 <span class="red textspan margin-left-13">*</span>
			<div id="ztree" class="ztree margin-left-13"></div>
			<br />
			<ul id="ultype" class="list-unstyled list-flow p0_20 none">
				<li class="col-md-6 p0 ">
				<div  class="ml5 fl">供应商类型:</div>
					<div class="fl mr10">
					  <input name="expertstypeid" class="fl"  id="xschecked"  type="checkbox"
                            value="1">
						<div class="ml5 fl">销售型</div>
					</div>
					<div class="fl mr10">
						<input name="expertstypeid" class="fl" type="checkbox" value="2">
						<div class="ml5 fl">生产型</div>
					</div>
				</li>
			</ul>
		</form>
	</div>
</body>
</html>

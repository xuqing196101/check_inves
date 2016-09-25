<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!-->
<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]--><head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

<script src="<%=basePath%>public/layer/layer.js"></script>
	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">
</head>
<script type="text/javascript">
$(function (){
	var areas="${listArea[0].id}";
	 $.ajax({
	      type:"POST",
	      url:"<%=basePath%>SupplierExtracts/city.do",
	      data:{area:areas},
	      dataType:"json",
	      success: function(data){
	           var list = data;
	           $("#city").empty();
	           for(var i=0;i<list.length;i++){
	                $("#city").append("<option value="+list[i].id+">"+list[i].name+"</option>");
	           }
	      }
	  });
	 
	 
});

function areas(){
  var areas=$("#area").find("option:selected").val();
  $.ajax({
      type:"POST",
      url:"<%=basePath%>SupplierExtracts/city.do",
      data:{area:areas},
      dataType:"json",
      success: function(data){
           var list = data;
           $("#city").empty();
           for(var i=0;i<list.length;i++){
        	    $("#city").append("<option value="+list[i].id+">"+list[i].name+"</option>");
           }
      }
  });
}
function cityt(){
	   var check=$("input[name='radio']:checked").val();
	   if(check!=null){
		  var radio= check.split("^");
		   $("#supplierTypeId").val(radio[0]);
	        $("#salesType").val(radio[1]);
	   }
	    	  $("#address").val($("#area option:selected").text()+$("#city option:selected").text());
	          $("#locality").val($("#area option:selected").text()+$("#city option:selected").text());
	 
	   
	return true;
}

function opens(){
// 	iframe层
	layer.open({
	  type: 2,
	  title:false,
	  shadeClose: true,
	  shade: 0.01,
	  move: false,
	  offset: ['90px', '660px'],
	  area: ['430px', '450px'],
	  content: '<%=basePath%>SupplierExtracts/showproduct.do' //iframe的url
	}); 
}

function supervise(){
// 	iframe层
	layer.open({
	  type: 2,
	  title:false,
	  shadeClose: true,
	  shade: 0.01,
	  move: false,
	  area: ['1000px', '500px'],
	  content: '<%=basePath%>SupplierExtracts/showSupervise.do' //iframe的url
	}); 
}
</script>


<body>
<div id="menuContent" class="menuContent mc"  >
        <ul id="treeDemo" class="ztree ztreestyle w200"   ></ul>
    </div>
<!--面包屑导航开始-->
   <div class="margin-top-10 breadcrumbs ">
      <div class="container">
		   <ul class="breadcrumb margin-left-0">
		   <li><a href="#"> 首页</a></li><li><a href="#">业务管理</a></li><li><a href="#">订单中心</a></li><li class="active"><a href="#">修改订单</a></li>
		   </ul>
		<div class="clear"></div>
	  </div>
   </div>
   
 <!--=== End Breadcrumbs ===-->

        <!--=== Content Part ===-->
        <div class="container content height-350">
            <div class="row">
               <form action="<%=basePath%>SupplierExtracts/JumpResultSupplier.do" method="post">
                <!-- Begin Content -->
                  <div class="col-md-12" style="min-height:400px;">
                      <div class="col-md-3 md-margin-bottom-40" id="show_tree_div">
                      <input type="hidden" name="eid" value="${id }">
                      <input type="hidden" name="sids" id="sids" />
                        <div class="tag-box tag-box-v3">
                            <ul class=" list-unstyled ">
                             <li class=" col-md-12 p0"><label class="">产品目录:</label><span><input onclick="opens();" readonly    class="title" 
					                  /> </span></li>
					                             <li class=" col-md-12 p0"><label class="">地区：</label>
                                         <span class="clear">
                                         <input type="hidden" id="address" name="address" value=""/>
                                           <input type="hidden" id="locality" name="locality" value=""/>
                                    <select  class="form-control input-lg mr15"  id="area" onchange="areas();">
                                        <c:forEach items="${listArea }" var="area" varStatus="index">
                                            <option value="${area.id }">${area.name }</option>
                                        </c:forEach>
                                    </select>
                                    <select name="extractionSites" class="form-control input-lg" id="city" ></select>
                                </span>
                             </li>
                             <li class="mt10 col-md-12 p0"><label class="">类型：</label>
                               <span class="clear">
                                <input type="hidden" id="supplierTypeId" name="supplierTypeId" value="">
                                       <input type="hidden" id="salesType" name="salesType" value="">
                                <c:forEach items="${listType}" var="type" >
                                    <div class="fl mr10"><input type="radio" name="radio" value="${type.id}^${type.name}"  class="fl"/><div class="ml5 fl">${type.name}</div></div>
                                </c:forEach>
                                </span>
                                <div class="clear"></div>
                             </li>
                             <li class=" mt10 col-md-12 p0"><label class="fl mt5">抽取数量：</label><span><input  name="count" value="1" type="text" class="w50"></span></li>
                             <li class=" col-md-12 p0"><label class="">供应商级别：</label>
                               <span class="clear">
                               <div class="fl mr10"><input type="checkbox" class="fl"/><div class="ml5 fl">一级</div></div>
                               <div class="fl mr10"><input type="checkbox" class="fl"/><div class="ml5 fl">二级</div></div>
                               <div class="fl mr10"><input type="checkbox" class="fl"/><div class="ml5 fl">三级</div></div>
                               <div class="fl mr10"><input type="checkbox" class="fl"/><div class="ml5 fl">四级</div></div>
                               <div class="fl mr10"><input type="checkbox" class="fl"/><div class="ml5 fl">五级</div></div>
                               <div class="fl mr10"><input type="checkbox" class="fl"/><div class="ml5 fl">六级</div></div>
                               <div class="fl mr10"><input type="checkbox" class="fl"/><div class="ml5 fl">七级</div></div>
                               <div class="fl mr10"><input type="checkbox" class="fl"/><div class="ml5 fl">八级</div></div>
                               <div class="fl mr10"><input type="checkbox" class="fl"/><div class="ml5 fl">九级</div></div> 
                               <div class="clear"></div>
                                </span>
                             </li>
                               <li class=" col-md-12 p0"><label class="">监督人员:</label><span><input onclick="supervise();"  readonly  id="supervises"   class="title" 
                                      /> </span></li>
                               <div class="clear"></div>
                            </ul>
                            <div class="mt20 clear">
                              <input type="submit" onclick="return cityt();" class="btn ml20" value="抽取"/>
                            </div>
                            <div class="clear">
                        </div>
                      </div>
                      </div>
                      <div class="tag-box tag-box-v4 col-md-9">
                        <ul class="demand_list">
                          <li class="fl mr8"><label class="fl mt0">项目名称：</label><input name="projectName" type="text"></li>
                          <li class="fl mr8"><label class="fl mt0">采购编号：</label><input type="text"></li>
                          <li class="fl mr8"><label class="fl mt0">采购方式：</label><span>邀请招标</span></li>
                         </ul>
                         <div class="clear"></div>
                         <div class="col-md-9 f18 b tc mt20">满足抽取条件的供应商共<span class="red">18</span>家</div>
                      </div>
                   </div>
                   </form>
                </div>
                <!-- End Content -->
            </div>
        <!--/container-->
        <!--=== End Content Part ===-->
</body>
</html>

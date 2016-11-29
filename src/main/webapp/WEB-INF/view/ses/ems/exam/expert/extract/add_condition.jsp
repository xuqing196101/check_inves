<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../../../common.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${pageContext.request.contextPath}/">

<title>抽取条件</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->


<link rel="stylesheet"
	href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css"
	type="text/css">


<script type="text/javascript">
    $(function (){
        $("#area").empty();
        var city="";
       //所在地区回显
        var address="${ExpExtCondition.address}";
        if(address != null && address != ''){
               var addressArray=address.split(",");  
               city=addressArray[1];
               <c:forEach items="${listArea}" var="item" varStatus="status" >  
               if("${item.name}" == addressArray[0]){
                      $("#area").append("<option selected='selected' value='${item.id}'>${item.name}</option>");
               }else{
               
               $("#area").append("<option  value='${item.id}'>${item.name}</option>");
               }
               </c:forEach> 
           
        }else{
             <c:forEach items="${listArea}" var="item" varStatus="status" >  
           $("#area").append("<option  value='${item.id}'>${item.name}</option>");
           </c:forEach> 
        }
        
           
       var areas=$("#area option:selected").val();
        $.ajax({
             type:"POST",
             url:"${pageContext.request.contextPath}/SupplierExtracts/city.do",
             data:{area:areas},
             dataType:"json",
             success: function(data){
                  var list = data;
                  $("#city").empty();
                  for(var i=0;i<list.length;i++){
                      if(list[i].name==city){
                          $("#city").append("<option selected='selected' value="+list[i].id+">"+list[i].name+"</option>");
                      }
                       $("#city").append("<option  value="+list[i].id+">"+list[i].name+"</option>");
                  }
             }
         });

       
    	
    	
    	
       
        
         
    });
    
    //专家类型级联
    function  cascade(sl){
    	if($(sl).val()==1){
    		$("#dnone").css("display","block");

    	}else{
    		$("#dnone").css("display","none");
            $("#supplierTypeIds").attr("value","");
            $("#supplierType").attr("value","");
    	}
    	
    }
    
    //添加按钮
    function condition(){
        var html="";
        var count=$('#eCount').val();
        var typeId= $('#supplierTypeIds').val();
        var sum=0;
        var et= $("#etype option:selected").val();
        
        
        
        if($("#ageMinC").val() == '' && $("#ageMaxC").val() == ''){
        	$("#agediv").text("不能为空");
        }
        
        
       if(et != null && et == 1){
        if(typeId == null || typeId == ''){
        	$("#typeIds").text("不能为空");
        	sum=1;
        }
       }
       if(count==null || count==''){
           $("#dCount").text("不能为空");
           sum=1;
       }

        if(sum!=1){
              html+="<tr>"+
              "<input class='hide' name='extCategoryId' id='ids'  type='hidden' value='"+$('#supplierTypeIds').val()+"'>"+
              "<input class='hide' name='extCategoryName' id='ids'  type='hidden' value='"+$('#supplierType').val().replace(",","^")+"'>"+
                "<td class='tc w30'><input type='checkbox' value=''"+
                    "name='chkItem' onclick='check()'></td>"+  
                "<td class='tc'>";
               html+="<select name='expertsTypeId' id='estype'>"+
		                     "<option value='1'>技术</option>"+
		                     "<option value='2'>法律</option>"+
		                     "<option value='3'>商务</option>"+
		               "</select>";
               html+="</td>"+
                "<td class='tc'><input class='hide' name='extCount'  type='text' value='"+$('#eCount').val()+"'></td>"+
                
                "<td class='tc'><select name='expertsFrom' id='expfrom' >";
                
                
                <c:forEach items="${find}" var="item" varStatus="status" >
               
                html+="<option value='${item.id}'>${item.name}</option>";
                
                </c:forEach> 
                
                
                html+="</td>";
                
                html+="<td class='tc'><input class='hide'  readonly='readonly'  title='"+$("#supplierType").val()+"' type='text' value='"+$("#supplierType").val()+"'></td>"+
              
                "</tr>";
               
               
               alert($("#supplierType").val());
               
               $("#tbody").append(html);
               
               //专家来源
               $("tr:last").find("#expfrom option[value='"+$("#expertsFromcopy option:selected ").val()+"']").attr("selected",true);
               //专家类型
               $("tr:last").find("#estype option[value='"+$("#etype option:selected").val()+"']").attr("selected",true);
               
               $("#extCategoryName").val("");
               $("#extCategoryId").val("");
               $("#expertsTypeId").val("");
               $("#expertsTypeName").val("");
               $("#dCount").text("");
               $("#eCount").val("");
               $("#expertsFromcopy").text("");
               $("#typeIds").text("");
               $("#agediv").text("");
        }
        
             
    }
    
    
    
    //专家地址
    function areas1(){
      var areas1=$("#area1").find("option:selected").val();
      $.ajax({
          type:"POST",
          url:"${pageContext.request.contextPath}/SupplierExtracts/city.do",
          data:{area:areas1},
          dataType:"json",
          success: function(data){
               var list = data;
               $("#city1").empty();
               for(var i=0;i<list.length;i++){
            	   if(i==0){
                       $("#city1").append("<option selected='selected' value="+list[i].id+">"+list[i].name+"</option>");
            	   }

                   $("#city1").append("<option value="+list[i].id+">"+list[i].name+"</option>");
               }
          }
      });
    }
    //抽取地址
    function areas(){
        var areas=$("#area").find("option:selected").val();
        $.ajax({
            type:"POST",
            url:"${pageContext.request.contextPath}/SupplierExtracts/city.do",
            data:{area:areas},
            dataType:"json",
            success: function(data){
                 var list = data;
                 $("#city").empty();
                 for(var i=0;i<list.length;i++){
                     if(i==0){
                         $("#city").append("<option selected='selected' value="+list[i].id+">"+list[i].name+"</option>");
                     }

                     $("#city").append("<option value="+list[i].id+">"+list[i].name+"</option>");
                 }
            }
        });
      }
      
    
    
    
    
    /** 全选全不选 */
    function selectAll(){
         var checklist = document.getElementsByName ("chkItem");
         var checkAll = document.getElementById("checkAll");
         if(checkAll.checked){
               for(var i=0;i<checklist.length;i++)
               {
                  checklist[i].checked = true;
               } 
             }else{
              for(var j=0;j<checklist.length;j++)
              {
                 checklist[j].checked = false;
              }
            }
        }
    /** 单选 */
    function check(){
         var count=0;
         var checklist = document.getElementsByName ("chkItem");
         var checkAll = document.getElementById("checkAll");
         for(var i=0;i<checklist.length;i++){
               if(checklist[i].checked == false){
                   checkAll.checked = false;
                   break;
               }
               for(var j=0;j<checklist.length;j++){
                     if(checklist[j].checked == true){
                           checkAll.checked = true;
                           count++;
                       }
                 }
           }
    }
    
    function updates(){
    
        var id=[]; 
        $('input[name="chkItem"]:checked').each(function(){ 
            id.push($(this).val());
        }); 
        if(id.length==1){
        	  //  iframe层
            layer.open({
              type: 2,
              title:"修改条件",
              shadeClose: true,
              shade: 0.01,
              area: ['430px', '400px'],
              offset: '20px',
              content: '${pageContext.request.contextPath}/ExpExtract/addHeading.do?id='+id, //iframe的url
            });
        	
        }else if(id.length>1){
            layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
        }else{
            layer.alert("请现在要修改的条件",{offset: ['222px', '390px'], shade:0.01});
        }
    }      
    function del(){
        var ids =[]; 
        $('input[name="chkItem"]:checked').each(function(){ 
            ids.push($(this).val()); 
        }); 
        if(ids.length>0){
            layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
                $('input[name="chkItem"]:checked').each(function(){ 
                    this.parentNode.parentNode.remove();
                 }); 
                 layer.close(index); 
            	
            	$.ajax({
            	          type:"POST",
            	          url:"${pageContext.request.contextPath}/ExtCondition/dels.do?delids="+ids,
            	          data:{delids:ids},
            	          success: function(data){
            	        
            	          }
            	      });
            });
        }else{
            layer.alert("请选择查询的条件",{offset: ['222px', '390px'], shade:0.01});
        }
    }
    //ajax提交表单
    function cityt(){
//         $("#address").val($("#area option:selected").text()+","+$("#city option:selected").text());
//         $("#extAddress").val($("#area1 option:selected").text()+","+$("#city1 option:selected").text());
//         $("#addressId").val($("#city option:selected").val());
//         $("#areaId").val($("#area option:selected").val());
//         $("#expertId").val($("#city option:selected").val());
        $.ajax({
            cache: true,
            type: "POST",
            dataType : "json",
            url:'${pageContext.request.contextPath}/ExtCondition/saveExtCondition.html',
            data:$('#form1').serialize(),// 你的formid
            async: false,
            success: function(data) {
            	$("#tenderTime").text("");
            	$("#responseTime").text("");
            	$("#agediv").text("");
            	$("#supervisediv").text("");
            	$("#typeArray").text("");
            	var map =data;
            	$("#tenderTime").text(map.tenderTime);
            	$("#responseTime").text(map.responseTime);
            	$("#agediv").text(map.age);
            	$("#supervisediv").text(map.supervise);
            	$("#typeArray").text(map.typeArray);
            	if(map.sccuess=="sccuess"){
            		  window.location.href = '${pageContext.request.contextPath}/ExpExtract/Extraction.do?id=${projectId}&&typeclassId=${typeclassId}';
            	}
            }
        });

return true;
}

    
    
    
    
    
    
    function showSupplierType() {
        var setting = {
        check: {
                enable: true,
                chkboxType:{"Y" : "ps", "N" : "ps"},//勾选checkbox对于父子节点的关联关系  
            },
            view: {
                dblClickExpand: false
            },
            data : {
            simpleData : {
                enable : true,
                idKey : "id",
                pIdKey : "parentId"
            }
        },
            callback: {
                beforeClick: beforeClick,
                onCheck: onCheck
            }
        };
        $.ajax({
         type: "GET",
         async: false, 
         url: "${pageContext.request.contextPath}/ExpExtract/findType.do",
         dataType: "json",
         success: function(zNodes){
                 for (var i = 0; i < zNodes.length; i++) { 
                    if (zNodes[i].isParent) {  
          
                    } else {  
                        //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
                    }  
                }  
                tree = $.fn.zTree.init($("#treeSupplierType"), setting, zNodes);  
                tree.expandAll(true);//全部展开
           }
        });
        var cityObj = $("#supplierType");
        var cityOffset = $("#supplierType").offset();
        $("#supplierTypeContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");
        $("body").bind("mousedown", onBodyDownSupplierType);
    }
    function beforeClick(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeSupplierType");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
        }
    
    var nodes="";
    function onCheck(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeSupplierType"),
        
        nodes = zTree.getCheckedNodes(true),
        v = "";
        var rid = "";
        for (var i=0, l=nodes.length; i<l; i++) {
            if(!nodes[i].isParent){
            v += nodes[i].name + ",";
            rid += nodes[i].id + "^";
            }
        }
        if (v.length > 0 ) v = v.substring(0, v.length-1);
        if (rid.length > 0 ) rid = rid.substring(0, rid.length-1);
        var cityObj = $("#supplierType");
        cityObj.attr("value", v);
        $("#supplierTypeIds").val(rid); 
        
        
    }
    
    
    function hideRole() {
        $("#roleContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownOrg);
        
    }
    function hideSupplierType() {
        $("#supplierTypeContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownOrg);
        
    }
    function onBodyDownOrg(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "roleSel" || event.target.id == "roleContent" || $(event.target).parents("#roleContent").length>0)) {
            hideRole();
        }
    }
    function onBodyDownSupplierType(event) {
        if (!(event.target.id == "menuBtn" || $(event.target).parents("#supplierTypeContent").length>0)) {
            hideSupplierType();
        }
    }
    
    
    
    function supervise(){
    //  iframe层
        layer.open({
          type: 2,
          title:"选择监督人员",
          shadeClose: true,
          shade: 0.01,
          offset: '20px',
          move: false,
          area: ['90%', '50%'],
          content: '${pageContext.request.contextPath}/SupplierExtracts/showSupervise.do',
          success: function(layero, index){
              iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
            },
          btn: ['保存', '关闭'] 
              ,yes: function(){
                  iframeWin.add();
              
              }
              ,btn2: function(){
                layer.closeAll();
              }//iframe的url
        }); 
    }
	    function opens(){
	    //  iframe层
	       var iframeWin;
	        layer.open({
	          type: 2,
	          title:"选择条件",
	          shadeClose: true,
	          shade: 0.01,
	          area: ['430px', '400px'],
	          offset: '20px',
	          content: '${pageContext.request.contextPath}/ExpExtract/addHeading.do', //iframe的url
	          success: function(layero, index){
                  iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                },
              btn: ['保存', '重置'] 
                  ,yes: function(){
                      iframeWin.getChildren();
                  
                  }
                  ,btn2: function(){
                	  opens();
                  }
            });
	    }
    
    </script>
</head>
<script type="text/javascript">
	
</script>
<body>
   
	<!--面包屑导航开始-->
	
	<c:if test="${typeclassId!=null && typeclassId !=''}">
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a></li>
				<li><a href="#">支撑环境系统</a></li>
				<li><a href="#">专家管理</a></li>
				<li><a href="#">专家抽取</a></li>
				<li class="active"><a href="#">添加专家抽取</a></li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	</c:if>
	<div class="container">
		<div class="headline-v2">
			<h2>抽取条件</h2>
		</div>
	</div>
	<div class="container">
		   <div id="supplierTypeContent" class="supplierTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
            <ul id="treeSupplierType" class="ztree" style="margin-top:0;"></ul>
       </div>
		<form id="form1" method="post" >
		 <div class="mlr container search_detail">
			<!--         专家所在地区 -->
			<input type="hidden" name="id" id="id" value="${ExpExtCondition.id}">
			<!--         专家所在地区 -->
			<input type="hidden" name="address" id="address" value="">
			<!--         专家id-->
			<input type="hidden" name="expertId" id="expertId" value="">
			<!--         项目id -->
			<input type="hidden" name="projectId" id="pid" value="${projectId}">
			<!-- 				监督人员 -->
			<input type="hidden" name="sids" id="sids" value="${userId}" />
			<!--       省id -->
			<input type="hidden" name="areaId" id="areaId" value="" />
			<!-- 		       市id -->
			<input type="hidden" name="addressId" id="cityId" value="${addressId}" />
			<!--         抽取地区 -->
                <input type="hidden" name="extAddress" id="extAddress"  value="${extractionSites}">
			<!-- 类型  html -->
			<input type="hidden" name="typehtml" id="typehtml" value="" />
			<!-- 类型   -->
            <input type="hidden" name="typeclassId"  value="${typeclassId}"  />
            <div>
                        <h2 class="count_flow"><i>1</i>抽取条件</h2>
                          <ul class="ul_list">
                            <li class="col-md-4 col-sm-6 col-xs-12  pl15">
                             <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>专家地区：</span>
			                               <div class="input-append input_group col-sm-12 col-xs-12 p0">
			                              <select class=" w150 fl"
			                        id="area" onchange="areas();">
			                    </select> <select name="extractionSites" class="w100 fl"
			                        id="city"></select>
			                     <div class=" f12 red tip w150 fl" id=""></div>
                               </div>
                            </li>
                              <li class="col-md-4 col-sm-6 col-xs-12 ">
                              <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>年龄：</span>
                                  <div class="input-append input_group col-sm-12 col-xs-12 p0">
                        <input class="w118 fl" maxlength="2"
                        value="${ExpExtCondition.ageMin}"  id="ageMinC" name="ageMin" type="text"><span class="f14 fl ">至</span><input class="w118 fl" 
                        value="${ExpExtCondition.ageMax}" maxlength="2" id="ageMaxC" name="ageMax" type="text">
                           <div class="  f12 red tip w150" id="agediv"></div>
                        </div>
                      </li>
                           <li class="col-md-4 col-sm-6 col-xs-12">
                           <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>抽取数量：</span>
                           <div class="input-append input_group col-sm-12 col-xs-12 p0">
                             <input class="input_group w224" maxlength="6" id="eCount" type="text">
                             <span class="add-on">i</span>
                             <div class=" f12 red tip w150 fl" id="dCount" ></div>
                           </div>
                         </li>  
                         <li class="col-md-4 col-sm-6 col-xs-12">
                           <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>专家来源：</span>
                           <div class=" select_common input-append input_group col-sm-12 col-xs-12 p0">
                             <select class="w250" name="expertsFromcopy" id="expertsFrom">
                           <c:forEach items="${find}" var="item" varStatus="status" >
               
                            <option value='${item.id}'>${item.name}</option>";
                
                            </c:forEach> 
                       </select>
                             <div class=" f12 red tip w200 fl" id=""></div>
                           </div>
                         </li> 
                          <li class="col-md-4 col-sm-6 col-xs-12">
                           <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>专家类型：</span>
                           <div class="select_common input-append input_group col-sm-12 col-xs-12 p0">
                             <select class="" name="" id="etype" onchange="cascade(this);">
                                    <option selected="selected" value="1">技术</option>
                                    <option value="2">法律</option>
                                    <option value="3">商务</option>
                             </select>
                             
                           </div>
                         </li> 
                            <li class="col-md-4 col-sm-6 col-xs-12 " id="dnone">
                           <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>产品服务/分类：</span>
                           <div class="input-append input_group col-sm-12 col-xs-12 p0">
                            <input id="supplierType" class="w224" type="text" readonly name="supplierType" value="${supplierType }" onclick="showSupplierType();" />
                             <input   type="hidden" name="supplierTypeIds"  id="supplierTypeIds" value="${supplierTypeIds }" />
                             <span class="add-on">i</span>
                             <div class=" f12 red tip w150 fl" id="typeIds"></div>
                           </div>
                         </li> 
                          
		                <div class="w300">
		                  <button class="btn btn-windows add"
		                              id="save" onclick="condition();" type="button">添加</button>
		                  <button class="btn btn-windows delete"
		                                    id="backups" onclick="del();" type="button">删除</button>
		                  <button class="btn btn-windows add" id="save" onclick="cityt();"
		                                        type="button">保存条件</button>
		                </div>
		                    
		                <table class="table table-bordered table-condensed mt5">
		                    <thead>
		                        <tr>
		                            <th class="info w30"><input type="checkbox" id="checkAll"
		                                onclick="selectAll()" alt=""></th> 
		                            <th class="info">专家类型</th>
		                            <th class="info">专家数量</th>
		                            <th class="info">专家来源</th>
		                            <th class="info">产品类别</th>
		                        </tr>
		                    </thead>
		                    <tbody id="tbody">
		                        <c:forEach items="${ExpExtCondition.conTypes}" var="conTypes">
		                            <tr>
		                                <input class="hide" type="hidden" name="typeId"
		                                    value="${conTypes.id}">
		                                <input class="hide" type="hidden" name="extCategoryId"
		                                    value="${conTypes.categoryId }">
		                                <input class="hide" type="hidden" name="extCategoryId"
		                                    value="${conTypes.categoryId }">
		                                <td class='tc w30'><input type="checkbox"
		                                    value="${conTypes.categoryId},${conTypes.expertsTypeId},${conTypes.expertsCount},${conTypes.expertsQualification}"
		                                    name="chkItem" onclick="check()"></td>
		                                <td class="tc">
		                                     <select name='expertsTypeId' id='estype'>
		                                     <c:if test="${ conTypes.expertsTypeId==1 }">
					                             <option selected="selected" value='1'>技术</option>
					                              <option  value='2'>法律</option>
					                               <option  value='3'>商务</option>
		                                     </c:if>
		                                     <c:if test="${ conTypes.expertsTypeId==2 }">
		                                         <option value='1'>技术</option>
					                             <option selected="selected" value='2'>法律</option>
					                              <option  value='3'>商务</option>
		                                     </c:if>
		                                     <c:if test="${ conTypes.expertsTypeId==3 }">
		                                        <option value='1'>技术</option>
		                                         <option  value='2'>法律</option>
		                                      <option selected="selected" value='3'>商务</option>
		                                     </c:if>
		                                     </select>
		                                </td>
		                                <td class="tc"><input class="hide" readonly="readonly"
		                                    name="extCount" type="text" value="${conTypes.expertsCount }"></td>
		                                <td class="tc">
		                                    <select>
		                                        <c:forEach items="${find}" var="item">
		                                            <option value="${item.id}">${item.name}</option>
		                                        </c:forEach>
		                                   </select>
		                                </td>
		                                <td class="tc">
		                                    <input class='hide'  readonly='readonly'  title='${conTypes.categoryName}' type='text' value="${conTypes.categoryName}"/>
		                                </td>
		                            </tr>
		                        </c:forEach>
		                    </tbody>
		                </table>
		                <div align="right" class="padding-10">
                             <div class="col-md-12  f12 red tip" id="typeArray"></div>
		                 </div>
                 </ul>
            </div>
			</div>
			    
			
		</form>
	</div>
</body>
</html>

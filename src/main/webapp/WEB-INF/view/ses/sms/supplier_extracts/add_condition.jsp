<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="${pageContext.request.contextPath}/">

<title>添加抽取条件</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">

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
    
    //供应商地区
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
    
    //添加按钮
    function condition(){
    	var html="";
    	var names =$("#extCategoryNames").val();
    	var copynames =$("#extCategoryName").val();
    	var count=$('#eCount').val();
    	var sum=0;
    	if(names==null || names==''){
    		$("#dCategoryName").text("不能为空");
    		sum=1;
    	}
    	
    	if(count==null || count==''){
            $("#dCount").text("不能为空");
            sum=1;
        }
    	if(sum!=1){
    		  html+="<tr>"+
              "<input class='hide' name='extCategoryId' id='ids'  type='hidden' value='"+$('#extCategoryId').val()+"'>"+
              "<input class='hide' name='isSatisfy'  type='hidden' value='"+$('#isSatisfy').val()+"'>"+
              "<input class='hide' name='extCategoryName' id='names'  readonly='readonly' type='hidden' value='"+names+"'>"+
              "<input class='hide' name='expertsTypeId' readonly='readonly' type='hidden' value='"+$("#expertsTypeId").val()+"'>"+
                "<td class='tc w30'><input type='checkbox' value=''"+
                    "name='chkItem' onclick='check()'></td>"+  
                "<td class='tc'>";
                     html+="<input class='hide' readonly='readonly' id='expTypeName' type='text' value='"+$("#expertsTypeName").val()+"'>";
                   html+="</td>"+
                "<td class='tc'><input class='hide' name='extCount'  type='text' value='"+$('#eCount').val()+"'></td>"+
                  
                "<td class='tc'><input class='hide' name='' readonly='readonly' onclick='opens(this);' title='"+copynames+"' type='text' value='"+copynames+"'></td>"+
               "</tr>";
               $("#tbody").append(html);
               
               $("#extCategoryName").val("");
               $("#extCategoryId").val("");
               $("#expertsTypeId").val("");
               $("#expertsTypeName").val("");
               $("#isSatisfy").val("");
               $("#eCount").val("");
               $("#dCategoryName").text("");
               $("#dCount").text("");
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
              content: '${pageContext.request.contextPath}/SupplierExtracts/addHeading.do?id='+id, //iframe的url
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
                          url:"${pageContext.request.contextPath}/SupplierExtracts/dels.do?delids="+ids,
                          data:{delids:ids},
                          success: function(data){
                        
                          }
                      });
            });
        }else{
            layer.alert("请要删除的条件",{offset: ['222px', '390px'], shade:0.01});
        }
    }
    function cityt(){
        $("#address").val($("#area option:selected").text()+","+$("#city option:selected").text());
        $("#expertId").val($("#city option:selected").val());
        $.ajax({
            cache: true,
            type: "POST",
            dataType : "json",
            url:'${pageContext.request.contextPath}/SupplierCondition/saveSupplierCondition.do',
            data:$('#form1').serialize(),// 你的formid
            async: false,
            success: function(data) {
            	$("#supervise").text("");
            	   $("#array").text("");
            	var map =data;
            	$("#supervise").text(map.supervise);
            	$("#array").text(map.array);
            	if(map.sccuess=="sccuess"){
            		  window.location.href = '${pageContext.request.contextPath}/SupplierExtracts/Extraction.do?id=${projectId}&&typeclassId=${typeclassId}';
            	}
            }
        });
        
return false;
}
    
    
        function opens(cate){
        //  iframe层
          var iframeWin;
            layer.open({
              type: 2,
              title:"选择条件",
              shadeClose: true,
              shade: 0.01,
              area: ['430px', '400px'],
              offset: '20px',
              content: '${pageContext.request.contextPath}/SupplierExtracts/addHeading.do?projectId=${projectId}', //iframe的url
              success: function(layero, index){
                  iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                },
              btn: ['保存', '重置'] 
                  ,yes: function(){
                      iframeWin.getChildren(cate);
                  
                  }
                  ,btn2: function(){
                	  opens();
                  }
            });
        }
    
    </script>
<script type="text/javascript">
        var setting = {
            check: {
                enable: true,
                chkStyle: "radio",
                radioType: "all"
            },
            view: {
                dblClickExpand: false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                onClick: onClick,
                onCheck: onCheck
            }
        };
        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo");
            zTree.checkNode(treeNode, !treeNode.checked, null, true);
            return false;
        }
        function onCheck(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("treeDemo"),
            nodes = zTree.getCheckedNodes(true),
            v = "";
            for (var i=0, l=nodes.length; i<l; i++) {
                v += nodes[i].name + ",";
                $("#pid").val(nodes[i].id);
            }
            if (v.length > 0 ) v = v.substring(0, v.length-1);
            var cityObj = $("#citySel");
            cityObj.attr("value", v);
            
            hideMenu();
        }
        function showMenu() {
            $.ajax({
             type: "GET",
             async: false, 
             url: "${pageContext.request.contextPath}/preMenu/treedata.do?",
            dataType : "json",
            success : function(zNodes) {
                for (var i = 0; i < zNodes.length; i++) {
                    if (zNodes[i].isParent) {

                    } else {
                        //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
                    }
                }
                tree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
                tree.expandAll(true);//全部展开
            }
        });
        var cityObj = $("#citySel");
        var cityOffset = $("#citySel").offset();
        $("#menuContent").css({
            left : cityOffset.left + "px",
            top : cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDown);
    }
    function hideMenu() {
        $("#menuContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDown);
    }
    function onBodyDown(event) {
        if (!(event.target.id == "menuBtn" || event.target.id == "citySel"
                || event.target.id == "menuContent" || $(event.target).parents(
                "#menuContent").length > 0)) {
            hideMenu();
        }
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
					<li><a href="#">供应商管理</a></li>
					<li><a href="#">供应商抽取</a></li>
					<li class="active"><a href="#">添加供应商抽取</a></li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
	</c:if>
	<div class="container container_box">
		<form id="form1" method="post">
				<!--        条件id-->
				<input type="hidden" name="id" id="id" value="${ExpExtCondition.id}">
				<!--        专家所在地区 -->
				<input type="hidden" name="address" id="address" value="">
				<!--        项目id -->
				<input type="hidden" name="projectId" id="pid" value="${projectId}">
                <!-- 		抽取地区 -->
				<input type="hidden" name="extAddress" id="extAddress"  value="${extractionSites}">
                <!--		 品目id -->
				<input class='hide' name='' id='extCategoryId'  type='hidden'>
                <!-- 类型id -->
				<input class="hide" type="hidden" name="" id="expertsTypeId" value="${conTypes.supplieTypeId }"  value="">
				<!-- 类型Name -->
                <input class="hide" type="hidden" name="" id="expertsTypeName" value="" >
                <!-- 	满足多个条件 -->
                <input class="hide" type="hidden" name="" id="isSatisfy"  value="${conTypes.isMulticondition }">
                <!-- 品目Name ， -->
                <input class="hide" type="hidden" name="" id="extCategoryNames" value="" >
					 <div>
					    <h2 class="count_flow"><i>1</i>抽取条件</h2>
					      <ul class="ul_list">
					        <li class="col-md-3 col-sm-6 col-xs-12 pl15">
					         <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><span class="red">*</span>所在地区：</span>
					           <div class="input-append input_group col-sm-12 col-xs-12 p0">
					           <select class="w93 input_group fl" id="area" onchange="areas();">
                            </select> <select name="extractionSites" class=" w93 input_group fl" id="city"></select>
					       </div>
					     </li>
					     <li class="col-md-4 col-sm-6 col-xs-12">
					       <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>品目：</span>
					       <div class="input-append input_group col-sm-12 col-xs-12 p0">
					         <input class="input_group " readonly id="extCategoryName" onclick="opens(this);" type="text">
					         <span class="add-on">i</span>
					         <div class=" f12 red tip w150 fl" id="dCategoryName"></div>
					       </div>
					     </li>
					     <li class="col-md-4 col-sm-6 col-xs-12">
                           <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><span class="red">*</span>抽取数量：</span>
                           <div class="input-append input_group col-sm-12 col-xs-12 p0">
                             <input class="input_group" id="eCount" type="text">
                             <span class="add-on">i</span>
                             <div class=" f12 red tip w150 fl" id="dCount"></div>
                           </div>
                         </li>  
                         
                            <div class=" w300 pl20 mt10">
								<button class="btn btn-windows add"
									id="save" onclick="condition();" type="button">添加</button>
								<button class="btn btn-windows delete"
									id="backups" onclick="del();" type="button">删除</button>
									   <button class="btn btn-windows add" id="save" onclick="cityt();"
				                        type="button">保存条件</button>
				                        </div>
				                        <div class="content table_box">
								<table class="table table-bordered table-condensed table-hover table-striped">
									<thead>
										<tr>
											<th class="info w30"><input type="checkbox" id="checkAll"
												onclick="selectAll()" alt=""></th>
											<th class="info">供应商类型</th>
											<th class="info">供应商抽取数量</th>
											<th class="info">产品类别</th>
										</tr>
									</thead>
									<tbody id="tbody">
										<c:forEach items="${ExpExtCondition.conTypes}" var="conTypes">
											<tr>
												<input class="hide" type="hidden" name="typeId"
													value="${conTypes.id}"/>
												<input class="hide" type="hidden" name="expertsTypeId"
													value="${conTypes.supplieTypeId }" id="expertsTypeId"/>
												<input class="hide" type="hidden" name="extCategoryId"
													value="${conTypes.categoryId }" id="extCategoryId" />
													   <input class="hide" type="hidden" name="extCategoryName"
                          value="${conTypes.categoryName }" id="extCategoryNamest" />
												<input class="hide" type="hidden" name="isSatisfy"
													value="${conTypes.isMulticondition }" id="isSatisfy"/>
												<td class='tc w30'><input type="checkbox"
													value="${conTypes.categoryId}" name="chkItem" onclick="check()"></td>
												<td class="tc">
												<c:set value="${ fn:split(conTypes.supplieTypeId, '^') }" var="typeId" />
												
                                                 <c:forEach var="type" items="${typeId}">
                                                     <c:if test="${type=='PRODUCT' }">
                                                        <c:set value="${typeIdC},生产型" var="typeIdC" />                                                                            
                                                     </c:if>
                                                     <c:if test="${type=='SALES' }">
                                                        <c:set value="${typeIdC},销售型" var="typeIdC" />                                                                             
                                                    </c:if>
                                                     <c:if test="${type=='PROJECT' }">
                                                             <c:set value="${typeIdC},工程型" var="typeIdC" />                                                                                    
                                                    </c:if>
                                                     <c:if test="${type=='PROJECT' }">
                                                              <c:set value="${typeIdC},服务" var="typeIdC" />                                                                                      
                                                    </c:if>
                                                
                                                </c:forEach>
													<input readonly="readonly" id="expTypeName" class="hide" type="text"  
                                                 value='${fn:substring(typeIdC, 1, typeIdC.length())}'>
												</td>
												<td class="tc"><input class="hide" 
													name="extCount" type="text"  value="${conTypes.supplieCount }"></td>
												<td class="tc">
												
				                                <input class="hide" readonly onclick="opens(this);"
				                                    type="text"
				                                    value="${fn:replace(conTypes.categoryName,'^',',')}"></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
								</div>
								<div align="right" class="padding-10">
									<div class="col-md-12  f12 red tip" id="array"></div>
								
								</div>
						</ul>
                      </div>

		</form>
	</div>
</body>
</html>

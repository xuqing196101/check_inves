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
        var city1="";
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
        
          $("#area1").empty();   
          //抽取地址回显    
           var extractAddress="${extractionSites}";
           if(extractAddress != null && extractAddress != ''){
               var extractAddressArray=extractAddress.split(","); 
               city1=extractAddressArray[1];
               <c:forEach items="${listArea}" var="item" varStatus="status" >  
               if("${item.name}" == extractAddressArray[0]){
                      $("#area1").append("<option selected='selected' value='${item.id}'>${item.name}</option>");
               }else{
               $("#area1").append("<option  value='${item.id}'>${item.name}</option>");
               }
               </c:forEach> 
           }else{
                  <c:forEach items="${listArea}" var="item" varStatus="status" >  
                  $("#area1").append("<option value='${item.id}'>${item.name}</option>");
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

        var areas1=$("#area1 option:selected").val();
        $.ajax({
             type:"POST",
             url:"${pageContext.request.contextPath}/SupplierExtracts/city.do",
             data:{area:areas1},
             dataType:"json",
             success: function(data){
                  var list = data;
                  $("#city1").empty();
                  for(var i=0;i<list.length;i++){
                      if(list[i].name==city1){
                          $("#city1").append("<option selected='selected' value="+list[i].id+">"+list[i].name+"</option>");
                      }
                       $("#city1").append("<option  value="+list[i].id+">"+list[i].name+"</option>");
                  }
             }
         }); 
       
    	
    	
    	
    	
         $('#minute').bind('input propertychange', function() {
        	 var count=$(this).val();
        		 if(count>60){
                     $("#minute").val("59");
        		 }
        		 if(count==0){
        			 $("#minute").val("");
        		 }
        	    
        	   
        	});
        
         
    });
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
        $("#address").val($("#area option:selected").text()+","+$("#city option:selected").text());
        $("#extAddress").val($("#area1 option:selected").text()+","+$("#city1 option:selected").text());
        $("#addressId").val($("#city option:selected").val());
        $("#areaId").val($("#area option:selected").val());
        $("#expertId").val($("#city option:selected").val());
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
            		  window.location.href = '${pageContext.request.contextPath}/ExpExtract/Extraction.do?projectId=${projectId}&&typeclassId=${typeclassId}';
            	}
            }
        });

return true;
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
		<div id="menuContent" class="menuContent"
			style="display: none; position: absolute; left: 0px; top: 0px; z-index: 999;">
			<ul id="treeDemo" class="ztree" style="width: 220px"></ul>
		</div>
		   <h2>
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
			<ul class="demand_list">
			     <li>
                <label class="fl"><span class="red">*</span>抽取地区：</label>
                    <span> <select class=" w150"
                        id="area1" onchange="areas1();">
                            <c:forEach items="${listArea }" var="area" varStatus="index">
                                <option value="${area.id }">${area.name }</option>
                            </c:forEach>
                    </select> <select name="extractionSites" class="w93"
                        id="city1"></select>
                        <div class="fr b f14 red tip w150"></div>
                  </span>
                </li>
				<li>
				<label class="fl"><span class="red">*</span>专家地区：</label>
					<span> <select class=" w150"
						id="area" onchange="areas();">
							<c:forEach items="${listArea }" var="area" varStatus="index">
								<c:if test="${area.name eq areas}">
									<option selected="selected" value="${area.id }">${area.name }</option>
								</c:if>
								<option value="${area.id }">${area.name }</option>
							</c:forEach>
					</select> <select name="extractionSites" class="w93"
						id="city"></select>
						<div class="fr b f14 red tip w150"></div>
				  </span>
				</li>
				<li><label class="fl"><span class="red">*</span>专家来源：</label>
				    <span>
						<select class="w250" name="expertsFrom">
							<c:choose>
								<c:when test="${ExpExtCondition.expertsFrom eq '军队'}">
									<option selected="selected" value="军队">军内</option>
									<option value="地方">地方</option>
									<option value="其他">不限</option>
								</c:when>
								<c:when test="${ExpExtCondition.expertsFrom eq '地方'}">
									<option value="军队">军内</option>
									<option selected="selected" value="地方">地方</option>
									<option value="其他">不限</option>
								</c:when>
								<c:when test="${ExpExtCondition.expertsFrom eq '其他'}">
									<option value="军队">军内</option>
									<option value="地方">地方</option>
									<option selected="selected" value="其他">不限</option>
								</c:when>
								<c:otherwise>
									<option value="军队">军内</option>
									<option value="地方">地方</option>
									<option value="其他">不限</option>
								</c:otherwise>
							</c:choose>
					   </select>
					   <div class="fr b f14 red tip w150"></div>
				  </span>
				</li>
				<li><label class="fl"><span class="red">*</span>开标时间：</label> <span>
						<input class="Wdate w250" readonly="readonly"
						name="tenderTime"
						value="<fmt:formatDate value='${ExpExtCondition.tenderTime}'
                                pattern='yyyy-MM-dd' />"
						maxlength="30" onclick="WdatePicker();" type="text">
						<div class="fr b f14 red tip w150" id="tenderTime">
						</div>
				</span>
				</li>
				<li><label class="fl"><span class="red">*</span>年龄：</label> <span>
                        <input class="w118" maxlength="2"
                        value="${ExpExtCondition.ageMin}" name="ageMin" type="text"><span class="f14">至</span><input class="w118" name="ageMax"
                        value="${ExpExtCondition.ageMax}" maxlength="2" type="text">
                        <div class="fr b f14 red tip w150" id="agediv"></div>
                </span>
                </li>
				<li><label class="fl"><span class="red">*</span>响应时限：</label>
					<span> <input class="w118" name="hour" value="${hour}"
						maxlength="3" type="text"><span class="f14">时</span><input
						class="w118" value="${minute}" id="minute" name="minute"
						maxlength="3" type="text"
						onkeyup="this.value=this.value.replace(/\D/g,'')"
						onafterpaste="this.value=this.value.replace(/\D/g,'')"><span class="f14">分</span>
						<div class="fr b f14 red tip w150" id="responseTime">${responseTime }</div>
				</span></li>
				
				<li><label class="fl"><span class="red">*</span>监督人员：</label> <span>
						<input class=" w250" readonly id="supervises" name="userName"
						value="${userName}" onclick="supervise();" type="text">
						<div class="fr b f14 red tip w150" id="supervisediv"></div>
				</span></li>
			</ul>
			</div>
			<div align="right" >
				<button class="btn padding-left-10 padding-right-10 btn_back"
					id="save" onclick="opens();" type="button">添加</button>
				<!-- 				<button class="btn padding-left-10 padding-right-10 btn_back" -->
				<!-- 					id="update" onclick="updates();" type="button">修改</button> -->
				<button class="btn padding-left-10 padding-right-10 btn_back"
					id="backups" onclick="del();" type="button">删除</button>
				<table class="table table-bordered table-condensed mt5">
					<thead>
						<tr>
							<th class="info w30"><input type="checkbox" id="checkAll"
								onclick="selectAll()" alt=""></th>
							<th class="info">专家类型</th>
							<th class="info">专家数量</th>
							<th class="info">执业资格</th>
							<th class="info">产品类别</th>
						</tr>
					</thead>
					<tbody id="tbody">
						<c:forEach items="${ExpExtCondition.conTypes}" var="conTypes">
							<tr>
								<input class="hide" type="hidden" name="typeId"
									value="${conTypes.id}">
								<input class="hide" type="hidden" name="expertsTypeId"
									value="${conTypes.expertsTypeId }">
								<input class="hide" type="hidden" name="extCategoryId"
									value="${conTypes.categoryId }">
								<input class="hide" type="hidden" name="isSatisfy"
									value="${conTypes.isMulticondition }">
								<td class='tc w30'><input type="checkbox"
									value="${conTypes.categoryId},${conTypes.expertsTypeId},${conTypes.expertsCount},${conTypes.expertsQualification}"
									name="chkItem" onclick="check()"></td>
								<td class="tc"><c:if test="${conTypes.expertsTypeId==1 }">
										<input readonly="readonly" class="hide" type="text" value="技术">
									</c:if> <c:if test="${conTypes.expertsTypeId==2}">
										<input readonly="readonly" class="hide" type="text" value="法律">
									</c:if> <c:if test="${conTypes.expertsTypeId==3 }">
										<input readonly="readonly" class="hide" type="text"
											value="商务 ">
									</c:if></td>
								<td class="tc"><input class="hide" readonly="readonly"
									name="extCount" type="text" value="${conTypes.expertsCount }"></td>
								<td class="tc"><input class="hide" readonly="readonly"
									name="extQualifications" type="text"
									value="${conTypes.expertsQualification }"></td>
								<td class="tc">
								 <c:set value="${fn:substring(conTypes.categoryName, 0, conTypes.categoryName.length()-1)}" var="category" ></c:set>
								<input class="hide" readonly="readonly"
									name="extCategoryName" type="text"
									value="${fn:replace(category,'^',',')}"></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>

			<div align="right" class=" padding-10">
				<div class=" b f14 red tip" id="typeArray"></div>
				<button class="btn btn-windows add" id="save" onclick="cityt();"
					type="button">保存抽取条件</button>
			</div>
		</form>
		   </h2>
	</div>
</body>
</html>

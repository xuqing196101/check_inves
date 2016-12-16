<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ include file="../../../../../common.jsp"%>
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

    <link rel="stylesheet" href="${pageContext.request.contextPath}/public/supplier/css/supplieragents.css" type="text/css">

    <script type="text/javascript">
    $(function (){
    var cate="${listCon.conTypes[0].categoryName}";
    if (cate != null && cate != ''){
    	 $("#dnone").removeClass("dnone");
    }else{
        $("#dnone").addClass("dnone");
    }
    });
  
    

      //专家类型级联
      function cascade(sl) {
        if($(sl).val() == 1) {
          $("#dnone").css("display", "block");

        } else {
          $("#dnone").css("display", "none");
          $("#supplierTypeIds").attr("value", "");
          $("#supplierType").attr("value", "");
        }

      }

      

      //专家地址
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
               
               var  html="";
               var areas=$("#area").find("option:selected").text();
               if(areas == '全国'){
                 html="<option value=''>全国</option>";
               }
               for(var i=0;i<list.length;i++){
                 html +="<option value="+list[i].id+">"+list[i].name+"</option>";
               }
               $("#city").append(html);
          }
      });
    }

  
      //ajax提交表单
      function cityt() {
    	  $.ajax({
              type: "POST",
              url: "${pageContext.request.contextPath}/ExpExtract/isFinish.do",
              data: {packageId:"${packageId}"},
              dataType: "json",
              success: function(data){
                if(data=="SUCCESS"){
                    layer.confirm('是否完成本次抽取？', {
                           btn: ['确定','取消'],offset: ['40%', '40%'], shade:0.01
                         }, function(index){
                        	 ext();
                         }, function(index){
                           layer.close(index);
                         });
                  } else {
                	  ext();
                  }
                }
             
        });
           

        return false;
      }
      
      function ext(){
    	  $("#tbody").empty();
          $("#address").val($("#city option:selected").val());
            $.ajax({
              cache: true,
              type: "POST",
              dataType: "json",
              url: '${pageContext.request.contextPath}/ExtCondition/saveExtCondition.html',
              data: $('#form1').serialize(), // 你的formid
              async: false,
              success: function(data) {
                $("#tenderTime").text("");
                $("#responseTime").text("");
                $("#agediv").text("");
                $("#supervisediv").text("");
                $("#typeArray").text("");
                $("#expertsCountError").text("");
                var map = data;
                $("#tenderTime").text(map.tenderTime);
                $("#responseTime").text(map.responseTime);
                $("#agediv").text(map.age);
                $("#supervisediv").text(map.supervise);
                $("#typeArray").text(map.typeArray);
                $("#expertsCountError").text(map.expertsCountError);
                if(map.sccuess == "sccuess") {
                   var list=map.extRelateListYes;
                     var noList=map.extRelateListNo;
                     var extConType=map.extConType;
                   var tex="";
                   if (list != null && list.length !=0){
                   for(var i=0;i<list.length;i++){
                       if(list[i]!=null){
                        if(list[0]!=null){
                          var html="";
                          $("#extcontype").empty();
                          for(var l=0;l<extConType.length;l++){
                              html+="";
                                 if(extConType.categoryName != null && extConType.categoryName != ''){
                                   var cName=extConType.categoryName;
                                      cName=cName.substring(0,cName.length-1);
                                           html+="抽取品目:"+cName+",";
                                 }
                                 
                                 html+="抽取数量:"+extConType[l].alreadyCount+"/"+extConType[l].expertsCount;
                                html+="<br/>";
                              }
                          $("#extcontype").append(html);
                        } 
                       tex+="<tr class='cursor'>"+
                           "<td class='tc' onclick='show();'>"+(i+1)+"</td>"+
                           "<td class='tc' onclick='show();'>"+list[i].expert.relName+"</td>"+
                           "<td class='tc' onclick='show();'>"+list[i].expert.mobile+"</td>"+
                           "<td class='tc' onclick='show();'>"+list[i].expert.workUnit+"</td>"+
                           "<td class='tc' onclick='show();'>"+list[i].expert.professTechTitles+"</td>"+
                       " <td class='tc' >"+
                         "<select id='select' onchange='operation(this);'>";
                         
                          if(list[i].operatingType==1){
                              tex+="<option value='"+list[i].id+","+list[i].expertConditionId+",1' selected='selected' disabled='disabled'>能参加</option>";
                          }else if(list[i].operatingType==2){
                              tex+="<option value='"+list[i].id+","+list[i].expertConditionId+",1'>能参加</option>"+
                              "<option value='"+list[i].id+","+list[i].expertConditionId+",3'>不能参加</option>"+
                              "<option selected='selected' value='"+list[i].id+","+list[i].expertConditionId+",2'>待定</option>";
                          }else if(list[i].operatingType==3){
                              tex+="<option value='"+list[i].id+","+list[i].expertConditionId+",1' selected='selected' disabled='disabled'>不能参加</option>";
                          }else{
                              tex+= "<option >请选择</option>"+
                                  "<option value='"+list[i].id+","+list[i].expertConditionId+",1'>能参加</option>"+
                              "<option value='"+list[i].id+","+list[i].expertConditionId+",3'>不能参加</option>"+
                              "<option  value='"+list[i].id+","+list[i].expertConditionId+",2'>待定</option>";
                          }
                          tex+="</select>"+
                        "</td>"+
                   "</tr>";
                   }
                   }
                   for(var i=0;i<noList.length;i++){
                       
                       tex+="<tr class='cursor'>"+
                             "<td class='tc' onclick='show();'>"+(i+1)+1+"</td>"+
                             "<td class='tc' onclick='show();'>*****</td>"+
                             "<td class='tc' onclick='show();'>*****</td>"+
                             "<td class='tc' onclick='show();'>*****</td>"+
                             "<td class='tc' onclick='show();'>*****</td>"+
                             "<td class='tc'>请选择</td>"+
                           "</tr>";
                         
                     }
                   
                     $("#tbody").append(tex);
                }else{
                    layer.alert("本条件没有查询结果!",{offset: ['222px', '390px'], shade:0.01});
                }
                   
                }
              }

            });
          
      }
      
      /**完成**/
      function finish(){
    	   $.ajax({
               type: "POST",
               url: "${pageContext.request.contextPath}/ExpExtract/isFinish.do",
               data: {packageId:"${packageId}"},
               dataType: "json",
               success: function(data){
            	   if(data=="SUCCESS"){
            		     layer.confirm('是否完成本次抽取？', {
            	              btn: ['确定','取消'],offset: ['40%', '40%'], shade:0.01
            	            }, function(index){
            	               window.location.href="${pageContext.request.contextPath}/ExpExtract/Extraction.html?projectId=${projectId}&&typeclassId=${typeclassId}&&packageId=${packageId}";
            	            }, function(index){
            	              layer.close(index);
            	            });
            	          }
            	   }
              
    	   });
    	  
      }
      
      function operation(select){
          layer.confirm('确定本次操作吗？', {
            btn: ['确定','取消'],offset: ['40%', '40%'], shade:0.01
          }, function(index){
            var strs= new Array();
            var v=select.value;
             strs=v.split(",");
             layer.close(index);
            if(strs[2]=="3"){
              layer.prompt({
                  formType: 2,
                  shade:0.01,
                  offset: ['40%', '40%'],
                  title: '不参加理由'
                }, function(value, index, elem){
                     ajaxs(select.value,value);
                     layer.close(index);
                });
            }else{
            select.disabled=true;
               ajaxs(select.value,'');
            }
          }, function(index){
            layer.close(index);
          });
        }
        
       function ajaxs(id,v){
         $.ajax({
               type: "POST",
               url: "${pageContext.request.contextPath}/ExpExtract/resultextract.do",
               data: {id:id,reason:v},
               dataType: "json",
               success: function(data){
                           list=data;
                           if('sccuess'==list){
                               alert("ss");
                           }else{
                           var tex='';
                           for(var i=0;i<list.length;i++){
                               if(list[i]!=null){
                                if(list[0]!=null){
                                  var html="";
                                  $("#extcontype").empty();
                                  for(var l=0;l<list[0].conType.length;l++){
                                  html+="";
//                                      if(extConType[l].expertsTypeId==1){
//                                             html+="技术,";
//                                           }else if(extConType[l].expertsTypeId==2){
//                                            html+="法律,";
//                                           }else if(extConType[l].expertsTypeId==3){
//                                              html+="商务,";
//                                           }
                                     if(list[0].conType.categoryName != null && list[0].conType.categoryName != ''){
                                       var cName=list[0].conType.categoryName.replace("^",",");
                                               cName=cName.substring(0,cName.length-1);
                                               html+="抽取品目:"+cName+",";
                                     }
                                     
                                     html+="抽取数量:"+list[0].conType[l].alreadyCount+"/"+list[0].conType[l].expertsCount;
                                    html+="<br/>";
                                  }
                                  $("#extcontype").append(html);
                                } 
                               tex+="<tr class='cursor'>"+
                                   "<td class='tc' onclick='show();'>"+(i+1)+"</td>"+
                                   "<td class='tc' onclick='show();'>"+list[i].expert.relName+"</td>"+
                                   "<td class='tc' onclick='show();'>"+list[i].expert.mobile+"</td>"+
                                   "<td class='tc' onclick='show();'>"+list[i].expert.workUnit+"</td>"+
                                   "<td class='tc' onclick='show();'>"+list[i].expert.professTechTitles+"</td>"+
                               " <td class='tc' >"+
                                 "<select id='select' onchange='operation(this);'>";
                                 
                                  if(list[i].operatingType==1){
                                      tex+="<option value='"+list[i].id+","+list[i].expertConditionId+",1' selected='selected' disabled='disabled'>能参加</option>";
                                  }else if(list[i].operatingType==2){
                                      tex+="<option value='"+list[i].id+","+list[i].expertConditionId+",1'>能参加</option>"+
                                      "<option value='"+list[i].id+","+list[i].expertConditionId+",3'>不能参加</option>"+
                                      "<option selected='selected' value='"+list[i].id+","+list[i].expertConditionId+",2'>待定</option>";
                                  }else if(list[i].operatingType==3){
                                      tex+="<option value='"+list[i].id+","+list[i].expertConditionId+",1' selected='selected' disabled='disabled'>不能参加</option>";
                                  }else{
                                      tex+= "<option >请选择</option>"+
                                          "<option value='"+list[i].id+","+list[i].expertConditionId+",1'>能参加</option>"+
                                      "<option value='"+list[i].id+","+list[i].expertConditionId+",3'>不能参加</option>"+
                                      "<option  value='"+list[i].id+","+list[i].expertConditionId+",2'>待定</option>";
                                  }
                                  tex+="</select>"+
                                "</td>"+
                           "</tr>";
                           }
                           }
                           $('#tbody tr:lt('+list.length+')').remove();
                          $("#tbody").prepend(tex);
                         }
               }
           });
       }
       
       function resetQuery(){
           $("#form1").find(":input[type='text']").attr("value","");
           $("#area").find("option:first").prop("selected", 'selected');
            areas();
         }


      function supervise() {
        //  iframe层
        layer.open({
          type: 2,
          title: "选择监督人员",
          shadeClose: true,
          shade: 0.01,
          offset: '20px',
          move: false,
          area: ['90%', '50%'],
          content: '${pageContext.request.contextPath}/SupplierExtracts/showSupervise.do',
          success: function(layero, index) {
            iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
          },
          btn: ['保存', '关闭'],
          yes: function() {
            iframeWin.add();

          },
          btn2: function() {
              layer.closeAll();
            } //iframe的url
        });
      }

      function opens(cate) {
    	  var type=$("#expertsTypeId").val();
        //  iframe层
        var iframeWin;
        layer.open({
          type: 2,
          title: "选择条件",
          shadeClose: true,
          shade: 0.01,
          area: ['430px', '400px'],
          offset: '20px',
          content: '${pageContext.request.contextPath}/ExpExtract/addHeading.do?type='+type, //iframe的url
          success: function(layero, index) {
            iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
          },
          btn: ['保存', '重置'],
          yes: function() {
            iframeWin.getChildren(cate);

          },
          btn2: function() {
            opens();
          }
        });
      }
      
      function expTypeChange(select){
    	  
//     	  alert(select.value);
    	  var id = select.value;
    	  if( id != 1){
//     		  alert($(select).parent().parent().parent().html());
    		  $(select).parent().parent().parent().find("#extCategoryNameC").val("");
    		  $(select).parent().parent().parent().find("#extCategoryId").val("");
    		  $(select).parent().parent().parent().find("#extCategoryName").val("");
    	  }
    	  
      }
    </script>
  </head>
<script type="text/javascript">
      function showExpertType() {
        var setting = {
          check: {
            enable: true,
            chkboxType: {
              "Y": "",
              "N": ""
            }
          },
          view: {
            dblClickExpand: false
          },
          data: {
            simpleData: {
              enable: true,
              idKey: "id",
              pIdKey: "parentId"
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
          url: "${pageContext.request.contextPath}/ExpExtract/projectType.do",
          dataType: "json",
          success: function(zNodes) {
            tree = $.fn.zTree.init($("#treeExpertType"), setting, zNodes);
            tree.expandAll(true); //全部展开
          }
        });
        var cityObj = $("#expertsTypeName");
        var cityOffset = $("#expertsTypeName").offset();
        $("#expertTypeContent").css({
          left: cityOffset.left + "px",
          top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDownExpertType);
      }

      function onBodyDownExpertType(event) {
        if(!(event.target.id == "menuBtn" || $(event.target).parents("#expertTypeContent").length > 0)) {
          hideExpertType();
        }
      }

      function hideExpertType() {
        $("#expertTypeContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownExpertType);

      }

      function beforeClick(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeExpertType");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
      }

      function onCheck(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeExpertType"),
          nodes = zTree.getCheckedNodes(true),
          v = "";
        var rid = "";
        for(var i = 0, l = nodes.length; i < l; i++) {
          v += nodes[i].name + ",";
          rid += nodes[i].id + ",";
        }
        if(v.length > 0) v = v.substring(0, v.length - 1);
        if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
        var cityObj = $("#expertsTypeName");
        cityObj.attr("value", v);
        $("#expertsTypeId").val(rid);
        if (v != null && v != ''){
        	$("#dnone").removeClass("dnone");
        	 $("#dnone").find("input").attr("value","");
        } else{
        	 $("#dnone").addClass("dnone");
        }
        
        
      }
    </script>
    
    <script type="text/javascript">
      function showExpertsFromType() {
        var setting = {
          check: {
            enable: true,
            chkboxType: {
              "Y": "",
              "N": ""
            }
          },
          view: {
            dblClickExpand: false
          },
          data: {
            simpleData: {
              enable: true,
              idKey: "id",
              pIdKey: "parentId"
            }
          },
          callback: {
            beforeClick: beforeClickFrom,
            onCheck: onCheckFrom
          }
        };
        $.ajax({
          type: "GET",
          async: false,
          url: "${pageContext.request.contextPath}/ExpExtract/expertsFrom.do",
          dataType: "json",
          success: function(zNodes) {
            tree = $.fn.zTree.init($("#treeFromType"), setting, zNodes);
            tree.expandAll(true); //全部展开
          }
        });
        var cityObj = $("#expertsFromName");
        var cityOffset = $("#expertsFromName").offset();
        $("#expertFromContent").css({
          left: cityOffset.left + "px",
          top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDownFromType);
      }

      function onBodyDownFromType(event) {
        if(!(event.target.id == "menuBtn" || $(event.target).parents("#expertFromContent").length > 0)) {
          hideFromType();
        }
      }

      function hideFromType() {
        $("#expertFromContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownFromType);

      }

      function beforeClickFrom(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeFromType");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
      }

      function onCheckFrom(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeFromType"),
          nodes = zTree.getCheckedNodes(true),
          v = "";
        var rid = "";
        for(var i = 0, l = nodes.length; i < l; i++) {
          v += nodes[i].name + ",";
          rid += nodes[i].id + ",";
        }
        if(v.length > 0) v = v.substring(0, v.length - 1);
        if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
        var cityObj = $("#expertsFromName");
        cityObj.attr("value", v);
        $("#expertsFrom").val(rid);
      }
    </script>

  <body>

    <!--面包屑导航开始-->
  <div id="expertTypeContent" class="expertTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="treeExpertType" class="ztree" style="margin-top:0;"></ul>
  </div>
   <div id="expertFromContent" class="expertFromContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="treeFromType" class="ztree" style="margin-top:0;"></ul>
  </div>
    <c:if test="${typeclassId!=null && typeclassId !=''}">
      <div class="margin-top-10 breadcrumbs ">
        <div class="container">
          <ul class="breadcrumb margin-left-0">
            <li>
              <a href="#"> 首页</a>
            </li>
            <li>
              <a href="#">支撑环境系统</a>
            </li>
            <li>
              <a href="#">专家管理</a>
            </li>
            <li>
              <a href="#">专家抽取</a>
            </li>
            <li class="active">
              <a href="#">添加专家抽取</a>
            </li>
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
      <form id="form1" method="post">
        <div class="container container_box">
          <!--         专家所在地区 -->
          <input type="hidden" name="address" id="address" value="">
          <!--         专家id-->
          <input type="hidden" name="expertId" id="expertId" value="">
          <!--         项目id -->
          <input type="hidden" name="projectId" id="pid" value="${packageId}">
          <!-- 类型   -->
          <input type="hidden" name="typeclassId" value="${typeclassId}" />
             <!--  满足多个条件 -->
        <input type="hidden" name="isMulticondition" id="isSatisfy" >
          <div>
            <h2 class="count_flow"><i>1</i>抽取条件</h2>
            <ul class="ul_list">
              <li class="col-md-3 col-sm-6 col-xs-12  pl15">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">专家地区：</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                     <select class="col-md-6 col-sm-6 col-xs-6 p0" id="area" onchange="areas();">
                     <option value="">全国</option>
                      <c:forEach  items="${privnce }" var="prin">
                   <c:if test="${prin.id==area.parentId }">
                    <option value="${prin.id }" selected="selected" >${prin.name }</option>
                   </c:if>
                   <c:if test="${prin.id!=area.parentId }">
                    <option value="${prin.id }"  >${prin.name }</option>
                   </c:if>
                   </c:forEach>
                  </select>
                  <select name="extractionSites" class="col-md-6 col-sm-6 col-xs-6 p0" id="city">
                     <option value="">全国</option>
                  <c:forEach  items="${city }" var="city">
                   <c:if test="${city.id==listCon.address}">
                    <option value="${city.id }" selected="selected" >${city.name }</option>
                   </c:if>
                   <c:if test="${city.id!=listCon.address }">
                    <option value="${city.id }"  >${city.name }</option>
                   </c:if>
                </c:forEach>
                  </select>
                </div>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12 ">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">年龄：</span>
                <div class="input-append col-sm-12 col-xs-12 col-md-12 p0">
               
                  <input class="col-md-5 col-sm-5 col-xs-5" maxlength="2" value="${listCon.ageMin}" id="ageMinC" name="ageMin" type="text"> 
                  <span class="f14 fl col-md-2 col-sm-2 col-xs-2">至</span>
                  <input class="col-md-5 col-sm-5 col-xs-5" value="${listCon.ageMax}" maxlength="2" id="ageMaxC" name="ageMax" type="text">
                  
                </div>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">专家来源：</span>
                 <div class="input-append input_group col-sm-12 col-xs-12 p0">
                    <c:set value="" var="froms"></c:set>
                     <c:forEach var="ay" items="${listCon.expertsFromSplit}" >
                      <c:forEach var="from" items="${find}">
                      <c:if test="${ay eq from.id}"> 
                      <c:set value="${froms},${from.name}" var="froms"></c:set>
                    </c:if> 
                      </c:forEach> 
                    </c:forEach>
                    <input   id="expertsFromName"  type="text" readonly name="expertsFromName" value="${fn:substring(froms,1,froms.length()) }" onclick="showExpertsFromType();" />
                    <input type="hidden" name="expertsFrom" id="expertsFrom" value="" />
                    <span class="add-on">i</span>
                 </div>
              </li>
                <li class="col-md-3 col-sm-6 col-xs-12">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">执业资格：</span>
                 <div class="input-append input_group col-sm-12 col-xs-12 p0">
                    <input   id="expertsQualification"  type="text" name="expertsQualification" value="${listCon.conTypes[0].expertsQualification} " />
                    <span class="add-on">i</span>
                 </div>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">抽取数量：</span>
                <div class="input-append input_group col-sm-12 col-xs-12 p0">
                  <input class="input_group " maxlength="6" name="expertsCount" value="${listCon.conTypes[0].expertsCount}"  id="eCount" type="text">
                  <span class="add-on">i</span>
                  <div class="cue" id="expertsCountError"></div>
                </div>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">专家类型：</span>
                <div class="input-append input_group col-sm-12 col-xs-12 p0">
                  <c:set value="" var="typeId"></c:set>
                <c:forEach items="${listCon.conTypes[0].expertsTypeSplit}" var="split">
	                <c:forEach var="project" items="${ddList}">
	                 <c:if test="${split eq project.id}">
	                  <c:set value="${typeId},${project.name}" var="typeId"></c:set>
	                 </c:if>
	                </c:forEach>
                </c:forEach>
                   <input   id="expertsTypeName"  type="text" readonly name="expertsTypeName" value="${fn:substring(typeId,1,typeId.length() )}" onclick="showExpertType();" />
                  <input type="hidden" name="expertsTypeId" id="expertsTypeId"  />
                  <span class="add-on">i</span>
                </div>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12 dnone" id="dnone">
                <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">产品服务/分类：</span>
                <div class="input-append input_group col-sm-12 col-xs-12 p0">
                  <input class="input_group " readonly id="categoryName" name="categoryName" value="${listCon.conTypes[0].categoryName}"  onclick="opens(this);" type="text">
                    <input  readonly id="categoryId" type="hidden" name="categoryId">
                  <span class="add-on">i</span>
                </div>
              </li>
              <li class="col-md-3 col-sm-6 col-xs-12">
            <div class=" w300 pl20 mt24">
            <button class="btn btn-windows add" id="save" onclick="cityt();" type="button">抽取</button>
            <button class="btn btn-windows add" id="save" onclick="resetQuery();" type="button">重置</button>
            </div>
          </li>
            </ul>
               <!--=== Content Part ===-->
            <h2 class="count_flow"><i>2</i>抽取结果</h2>
    <ul class="ul_list">
    <div class="row">
     <div class=" w300 pl20 ml10 mt10 mb10">
            <button class="btn " id="save" onclick="finish();" type="button">完成</button>
             <button class="btn btn-windows back" id="save" onclick="javascript:history.back(-1);" type="button">返回</button>
          </div>
      <!-- Begin Content -->
      <div class="col-md-12" id="count" style="min-height: 400px;">
        <div id="extcontype">
        <c:forEach var="con" items="${extConType}">
            <c:if test="${con.categoryName != null && con.categoryName != ''}">
                                                                 &nbsp;&nbsp;&nbsp;&nbsp;抽取品目 :${fn:replace(con.categoryName, "^", ",")}
                    </c:if>
            <c:if test="${con.isMulticondition != null && isMulticondition != ''}">

              <c:if test="${con.isMulticondition==1}">
                            满足一个条件,
                                                               
                    </c:if>
              <c:if test="${con.isMulticondition==2}">
                          满足多个条件,                             
                    </c:if>
                                                                    抽取数量${con.alreadyCount}/${con.expertsCount }
                        </c:if>
            <br />
          </c:forEach>
        </div>
        <div class="col-md-12" style="min-height: 400px;">

          <div class="clear"></div>
         <table id="table" class="table table-bordered table-condensed">
          <thead>
            <tr>
              <th class="info w50">序号</th>
              <th class="info">专家姓名</th>
              <th class="info">联系电话</th>
              <th class="info">工作单位名称</th>
              <th class="info">专家技术职称</th>
              <th class="info">操作</th>
            </tr>
          </thead>
          <tbody id="tbody">
            <c:forEach items="${extRelateListYes}" var="listyes"
              varStatus="vs">
              <tr class='cursor '>
                <td class='tc'>${vs.index+1}</td>
                <td class='tc'>${listyes.expert.relName}</td>
                <td class='tc'>${listyes.expert.mobile}</td>
                <td class='tc'>${listyes.expert.workUnit}</td>
                <td class='tc'>${listyes.expert.professTechTitles}</td>
                <td class='tc'><select id='select'
                  onchange='operation(this);'>
                    <c:choose>
                      <c:when test="${listyes.operatingType==1}">
                        <option selected="selected" disabled="disabled"
                          value='${listyes.id},${listyes.expertConditionId},1'>能参加</option>
                      </c:when>
                      <c:when test="${listyes.operatingType==2}">
                        <option value='${listyes.id},${listyes.expertConditionId},1'>能参加</option>
                        <option value='${listyes.id},${listyes.expertConditionId},3'>不能参加</option>
                        <option selected="selected" disabled="disabled"
                          value='${listyes.id},${listyes.expertConditionId},2'>待定</option>
                      </c:when>
                      <c:when test="${listyes.operatingType==3}">
                        <option selected="selected" disabled="disabled"
                          value='${listyes.id},${listyes.expertConditionId},3'>不能参加</option>
                      </c:when>
                      <c:otherwise>
                        <option>请选择</option>
                        <option value='${listyes.id},${listyes.expertConditionId},1'>能参加</option>
                        <option value='${listyes.id},${listyes.expertConditionId},3'>不能参加</option>
                        <option value='${listyes.id},${listyes.expertConditionId},2'>待定</option>
                      </c:otherwise>
                    </c:choose>
                </select></td>
              </tr>
            </c:forEach>
            <c:forEach items="${extRelateListNo }" var="listno" varStatus="vs">
              <tr class='cursor'>
                <td class='tc'>${(vs.index+1)+1}</td>
                <td class='tc'>*****</td>
                <td class='tc'>*****</td>
                <td class='tc'>*****</td>
                <td class='tc'>*****</td>
                <td class='tc'>请选择</td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
        </div>
      </div>
    </div>
        </ul>
          </div>
        </div>

      </form>
    </div>
  </body>

</html>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="../../../common.jsp"%>
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
               
               var  html="";
               var areas=$("#area").find("option:selected").text();
               if(areas == '全国'){
            	   html="<option value='' selected='selected' >所有地区</option>";
               }else{
            	   html="<option value='' selected='selected' >所有市</option>";
               }
               for(var i=0;i<list.length;i++){
            	   html +="<option value="+list[i].id+">"+list[i].name+"</option>";
               }
               $("#city").append(html);
               selectLikeSupplier();
          }
      });
   
    }
    
    function resetQuery(){
        $("#form1").find(":input[type='text']").attr("value","");
        $("#extCategoryName").val("");
        $("#area").find("option:first").prop("selected", 'selected');
         areas();
      }
    
    function selectLikeSupplier(){
    var v = document.getElementById("city").value;  	
     $("#address").val(v);
     var area = document.getElementById("area").value; 
     $("#province").val(area);
     $.ajax({
         cache: true,
         type: "POST",
         dataType : "json",
         url:'${pageContext.request.contextPath}/SupplierCondition/selectLikeSupplier.do',
         data:$('#form1').serialize(),// 你的formid
         async: false,
         success: function(data) {
        	$("#count").text(data);
         }
     });
     
return false;
}

   
    //ajax提交表单
    function cityt() {
        var  eCount =$("#supplierCount").val();
        if(eCount != null && eCount != '' ){
       
         var iframeWin;
         
         var typeCode = $("#supplierTypeId").val();
            var addressReson = $("#addressReson").val();
            if(typeCode == '' && addressReson == '' ){
              fax();
            }else{
             layer.open({
                   type: 2,
                   title: "选择",
                   shadeClose: true,
                   shade: 0.01,
                   offset: '20px',
                   move: false,
                   area: ['90%', '430px'],
                   content: '${pageContext.request.contextPath}/SupplierExtracts/reasonnumber.do?supplierTypeId='+$("#supplierTypeId").val()+'&&addressReson='+$("#city option:selected").val()+ $("#area option:selected").val()+'&&eCount='+$("#supplierCount").val(),
                   success: function(layero, index) {
                     iframeWin = window[layero.find('iframe')[0]['name']]; //得到iframe页的窗口对象，执行iframe页的方法：iframeWin.method();
                   },
                   btn: ['保存', '关闭'],
                   yes: function() {
                     iframeWin.save();
                     var type=$("#hiddentype").val();
                     if(type != null && type != '' && type == '1'){
                       
                       fax();      
                         layer.closeAll();
                     }
                     
                
                   },
                   btn2: function() {
                       layer.closeAll();
                     } //iframe的url
                 });
     

        
            }
            
          }else{
            $("#countSupplier").text("不能为空");
          }
            return false;
    }
    /**点击抽取按钮*/
    function fax(){
    	
      $.ajax({
            type: "POST",
            url: "${pageContext.request.contextPath}/SupplierExtracts/isFinish.do",
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
    	
    } 
    function ext(){
        $("#address").val($("#city option:selected").val());
        $.ajax({
            cache: true,
            type: "POST",
            dataType : "json",
            url:'${pageContext.request.contextPath}/SupplierCondition/saveSupplierCondition.do',
            data:$('#form1').serialize(),// 你的formid
            async: false,
            success: function(data) {
            	   $("#countSupplier").text("");
            	var map =data;
            	$("#countSupplier").text(map.count);
            	if(map.sccuess=="sccuess"){
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
                                	    html+="供应商类型:"+extConType[l].expertsType.name;
                                       html+="抽取数量:"+extConType[l].alreadyCount+"/"+extConType[l].supplierCount;
                                      html+="<br/>";
                                  }
                                  $("#extcontype").append(html);
                              } 
                          tex+="<tr class='cursor'>"+
                              "<td class='tc' onclick='show();'>"+(i+1)+"</td>"+
                              "<td class='tc' onclick='show();'>"+list[i].supplier.supplierName+"</td>"+
                              "<td class='tc' onclick='show();'>"+list[i].supplier.supplierName+"</td>"+
                              "<td class='tc' onclick='show();'>"+list[i].supplier.contactName+"</td>"+
                              "<td class='tc' onclick='show();'>"+list[i].supplier.contactTelephone+"</td>"+
                              "<td class='tc' onclick='show();'>"+list[i].supplier.contactMobile+"</td>"+
                          " <td class='tc' >"+
                            "<select id='select' onchange='operation(this);'>";
                            
                             if(list[i].operatingType==1){
                                 tex+="<option value='"+list[i].id+","+list[i].supplierConditionId+",1' selected='selected' disabled='disabled'>能参加</option>";
                             }else if(list[i].operatingType==2){
                                 tex+="<option value='"+list[i].id+","+list[i].supplierConditionId+",1'>能参加</option>"+
                                 "<option value='"+list[i].id+","+list[i].supplierConditionId+",3'>不能参加</option>"+
                                 "<option selected='selected' value='"+list[i].id+","+list[i].supplierConditionId+",2'>待定</option>";
                             }else if(list[i].operatingType==3){
                                 tex+="<option value='"+list[i].id+","+list[i].supplierConditionId+",1' selected='selected' disabled='disabled'>不能参加</option>";
                             }else{
                                 tex+= "<option >请选择</option>"+
                                     "<option value='"+list[i].id+","+list[i].supplierConditionId+",1'>能参加</option>"+
                                 "<option value='"+list[i].id+","+list[i].supplierConditionId+",3'>不能参加</option>"+
                                 "<option  value='"+list[i].id+","+list[i].supplierConditionId+",2'>待定</option>";
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
    /**暂存*/
    function temporary(){
    	 window.location.href="${pageContext.request.contextPath}/SupplierExtracts/Extraction.html?projectId=${projectId}&&typeclassId=${typeclassId}&&packageId=${packageId}";
    }
    
    
    /**完成**/
    function finish(){
    	 layer.confirm('是否需要打印', {
             btn: ['打印','取消'],offset: ['40%', '40%'], shade:0.01
           }, function(index){
        	   window.location.href="${pageContext.request.contextPath}/SupplierExtracts/Extraction.html?projectId=${projectId}&&typeclassId=${typeclassId}&&packageId=${packageId}";
           }, function(index){
             layer.close(index);
           });
    	 /* $.ajax({
    	        type: "POST",
    	        url: "${pageContext.request.contextPath}/SupplierExtracts/isFinish.do",
    	        data: {packageId:"${packageId}"},
    	        dataType: "json",
    	        success: function(data){
    	          if(data=="SUCCESS"){
    	              layer.confirm('是否完成本次抽取？', {
    	                     btn: ['确定','取消'],offset: ['40%', '40%'], shade:0.01
    	                   }, function(index){
    	                     window.location.href="${pageContext.request.contextPath}/SupplierExtracts/Extraction.html?projectId=${projectId}&&typeclassId=${typeclassId}&&packageId=${packageId}";
    	                   }, function(index){
    	                     layer.close(index);
    	                   });
    	                 }else{
    	                   window.location.href="${pageContext.request.contextPath}/SupplierExtracts/Extraction.html?projectId=${projectId}&&typeclassId=${typeclassId}&&packageId=${packageId}";
    	                 }
    	          }
    	       
    	  }); */
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
                      selectLikeSupplier();
                  
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
        selectLikeSupplier();
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
      function showSupplierType() {
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
          url: "${pageContext.request.contextPath}/SupplierExtracts/supplieType.do",
          dataType: "json",
          success: function(zNodes) {
            for(var i = 0; i < zNodes.length; i++) {
              if(zNodes[i].isParent) {

              } else {
                //zNodes[i].icon = "${ctxStatic}/images/532.ico";//设置图标  
              }
            }
            tree = $.fn.zTree.init($("#treeSupplierType"), setting, zNodes);
            tree.expandAll(true); //全部展开
          }
        });
        var cityObj = $("#supplierType");
        var cityOffset = $("#supplierType").offset();
        $("#supplierTypeContent").css({
          left: cityOffset.left + "px",
          top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDownSupplierType);
      }

      function onBodyDownSupplierType(event) {
        if(!(event.target.id == "menuBtn" || $(event.target).parents("#supplierTypeContent").length > 0)) {
          hideSupplierType();
        }
      }

      function hideSupplierType() {
        $("#supplierTypeContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownSupplierType);
        selectLikeSupplier();
      }

      function beforeClick(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeSupplierType");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
      }

      function onCheck(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeSupplierType"),
          nodes = zTree.getCheckedNodes(true),
          v = "";
        var rid = "";
        var codes = "";
        for(var i = 0, l = nodes.length; i < l; i++) {
          v += nodes[i].name + ",";
          rid += nodes[i].id + ",";
          codes += nodes[i].code + ",";
        }
        if(v.length > 0) v = v.substring(0, v.length - 1);
        if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
        if(codes.length > 0) codes = codes.substring(0, codes.length - 1);
       $("#supplierType").val(v);
       $("#supplierType").attr("title", v);
        $("#supplierTypeId").val(codes);
      }
    </script>
    
     <script type="text/javascript">
      function showLevel() {
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
            beforeClick: beforeClickLevel,
            onCheck: onCheckLevel
          }
        };
        
        var zNodes = [
                     {id:1, pId:0, name: "一级"},
                     {id:2, pId:0, name: "二级"},
                     {id:3, pId:0, name: "三级"},
                     {id:3, pId:0, name: "四级"},
                     {id:3, pId:0, name: "五级"}
                   ];
        tree = $.fn.zTree.init($("#treeLevelType"), setting, zNodes);
        tree.expandAll(true); //全部展开
        
        var cityObj = $("#levelType");
        var cityOffset = $("#levelType").offset();
        $("#levelTypeContent").css({
          left: cityOffset.left + "px",
          top: cityOffset.top + cityObj.outerHeight() + "px"
        }).slideDown("fast");
        $("body").bind("mousedown", onBodyDownLevelType);
      }

      function onBodyDownLevelType(event) {
        if(!(event.target.id == "menuBtn" || $(event.target).parents("#levelTypeContent").length > 0)) {
          hideLevelType();
        }
      }

      function hideLevelType() {
        $("#levelTypeContent").fadeOut("fast");
        $("body").unbind("mousedown", onBodyDownLevelType);
        selectLikeSupplier();

      }

      function beforeClickLevel(treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeLevelType");
        zTree.checkNode(treeNode, !treeNode.checked, null, true);
        return false;
      }

      function onCheckLevel(e, treeId, treeNode) {
        var zTree = $.fn.zTree.getZTreeObj("treeLevelType"),
          nodes = zTree.getCheckedNodes(true),
          v = "";
        var rid = "";
        for(var i = 0, l = nodes.length; i < l; i++) {
          v += nodes[i].name + ",";
          rid += nodes[i].id + ",";
        }
        if(v.length > 0) v = v.substring(0, v.length - 1);
        if(rid.length > 0) rid = rid.substring(0, rid.length - 1);
        var cityObj = $("#levelType");
        cityObj.attr("value", v);
        cityObj.attr("title", v);
        $("#levelTypeId").val(rid);
      }
    </script>
    
    <script type="text/javascript">
    function edit(){
        var id=[]; 
      $('input[name="chkItem"]:checked').each(function(){ 
        id.push($(this).val());
      }); 
      if(id.length==1){
        window.location.href="${pageContext.request.contextPath}/StationMessage/showStationMessage.do?id="+id+"&&type='edit'";
      }else if(id.length>1){  
        layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
      }else{
        layer.alert("请选择需要修改的用户",{offset: ['222px', '390px'], shade:0.01});
      }
      }
      function del(){
        var ids =[]; 
      $('input[name="chkItem"]:checked').each(function(){ 
        ids.push($(this).val()); 
      }); 
      if(ids.length>0){
        layer.confirm('您确定要删除吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
          layer.close(index);
          window.location.href="${pageContext.request.contextPath}/StationMessage/deleteSoftSMIsDelete.do?ids="+ids;
        });
      }else{
        layer.alert("请选择要删除的用户",{offset: ['222px', '390px'], shade:0.01});
      }
      }
      function add(){
        window.location.href="${pageContext.request.contextPath}/StationMessage/showInsertSM.do";
      }

      function continues(id){
           window.location.href="${pageContext.request.contextPath}/SupplierExtracts/conditions.do?id="+id;
      }
      function operation(select){
        layer.confirm('确定本次操作吗？', {
          btn: ['确定','取消'],offset: ['100px', '200px'], shade:0.01
        }, function(index){
          var strs= new Array();
          var v=select.value;
           strs=v.split(",");
           layer.close(index);
          if(strs[2]=="3"){
            layer.prompt({
                formType: 2,
                shade:0.01,
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
             url: "${pageContext.request.contextPath}/SupplierExtracts/resultextract.do",
             data: {id:id,reason:v},
             dataType: "json",
             success: function(data){
                         list=data;
                         if('sccuess'==list){
                         }else{
                         var tex='';
                         for(var i=0;i<list.length;i++){
                             if(list[i]!=null){
                               if(list[0]!=null){
                                     var html="";
                                     $("#extcontype").empty();
                                     for(var l=0;l<list[0].conType.length;l++){
                                       
                                         html+="供应商类型:"+list[0].conType[l].expertsType.name;
                                          html+="抽取数量:"+list[0].conType[l].alreadyCount+"/"+list[0].conType[l].supplierCount;
                                         html+="<br/>";
                                     }
                                     $("#extcontype").append(html);
                                 } 
                               
                             tex+="<tr class='cursor'>"+
                                 "<td class='tc' onclick='show();'>"+(i+1)+"</td>"+
                                 "<td class='tc' onclick='show();'>"+list[i].supplier.supplierName+"</td>"+
                                 "<td class='tc' onclick='show();'>"+list[i].supplier.supplierName+"</td>"+
                                 "<td class='tc' onclick='show();'>"+list[i].supplier.contactName+"</td>"+
                                 "<td class='tc' onclick='show();'>"+list[i].supplier.contactTelephone+"</td>"+
                                 "<td class='tc' onclick='show();'>"+list[i].supplier.contactMobile+"</td>"+
                             " <td class='tc' >"+
                               "<select id='select' onchange='operation(this);'>";
                               
                                if(list[i].operatingType==1){
                                    tex+="<option value='"+list[i].id+","+list[i].supplierConditionId+",1' selected='selected' disabled='disabled'>能参加</option>";
                                }else if(list[i].operatingType==2){
                                    tex+="<option value='"+list[i].id+","+list[i].supplierConditionId+",1'>能参加</option>"+
                                    "<option value='"+list[i].id+","+list[i].supplierConditionId+",3'>不能参加</option>"+
                                    "<option selected='selected' value='"+list[i].id+","+list[i].supplierConditionId+",2'>待定</option>";
                                }else if(list[i].operatingType==3){
                                    tex+="<option value='"+list[i].id+","+list[i].supplierConditionId+",1' selected='selected' disabled='disabled'>不能参加</option>";
                                }else{
                                    tex+= "<option >请选择</option>"+
                                        "<option value='"+list[i].id+","+list[i].supplierConditionId+",1'>能参加</option>"+
                                    "<option value='"+list[i].id+","+list[i].supplierConditionId+",3'>不能参加</option>"+
                                    "<option  value='"+list[i].id+","+list[i].supplierConditionId+",2'>待定</option>";
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

    </script>
<body>
  <div id="supplierTypeContent" class="supplierTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="treeSupplierType" class="ztree" style="margin-top:0;"></ul>
  </div>
    <div id="levelTypeContent" class="levelTypeContent" style="display:none; position: absolute;left:0px; top:0px; z-index:999;">
    <ul id="treeLevelType" class="ztree" style="margin-top:0;"></ul>
  </div>
  <!--面包屑导航开始-->
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
            <a href="#">供应商管理</a>
          </li>
          <li>
            <a href="#">供应商抽取</a>
          </li>
          <li class="active">
            <a href="#">添加供应商抽取</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
  </c:if>
  <div class="container container_box">
    <form id="form1" method="post">
      <!--        项目id -->
      <input type="hidden" name="projectId" id="pid" value="${packageId}">
      <!-- 地区 -->
      <input type="hidden" name="address" id="address">
   
      <!-- 类型id -->
      <input  type="hidden" name="supplierTypeId" id="supplierTypeId" >
          
      
      <!--  满足多个条件 -->
      <input type="hidden" name="isMulticondition" id="isSatisfy" >
      <!-- 品目Name ， -->
      <input  type="hidden" name="categoryName" id="extCategoryNames" >
      <!--     品目id -->
      <input  type='hidden' name='categoryId' id='extCategoryId' >
             <!-- 货物 -->
        <input type="hidden" name="goodsCount" id="goodsCount" >
          <!--  物资 -->
        <input type="hidden" name="projectCount" id="projectCount" >
<!--         服务 -->
        <input type="hidden" name="serviceCount" id="serviceCount" >
<!--         物资生产 -->
        <input type="hidden" name="productCount" id="productCount" >
<!--         物资销售 -->
        <input type="hidden" name="salesCount" id="salesCount" >
        <!--      限制地区理由-->
        <input type="hidden" name="addressReson" id="addressReson" >
<!--         省 -->
        <input type="hidden"  name="province" id="province" />
           <input type="hidden" name="" id="hiddentype">
      
      <div>
        <h2 class="count_flow"><i>1</i>抽取条件</h2>
        <ul class="ul_list" style="background-color: #fbfbfb">
          <li class="col-md-3 col-sm-6 col-xs-12 pl15">
                <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5 "><div class="star_red">*</div>所在地区：</span>
                <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
                  <select class="col-md-6 col-sm-6 col-xs-6 p0" id="area" onchange="areas();">
                     <option value="" selected="selected">全国</option>
                      <c:forEach  items="${privnce }" var="prin">
                   <c:if test="${prin.id==area.parentId }">
                    <option value="${prin.id }" selected="selected" >${prin.name }</option>
                   </c:if>
                   <c:if test="${prin.id!=area.parentId }">
                    <option value="${prin.id }"  >${prin.name }</option>
                   </c:if>
                   </c:forEach>
                  </select>
                  <select name="city" class="col-md-6 col-sm-6 col-xs-6 p0" id="city" onchange="selectLikeSupplier();">
                     <option value="" selected="selected">所有地区</option>
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
          <li class="col-md-3 col-sm-6 col-xs-12">
            <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">品目：</span>
            <div class="input-append input_group col-sm-12 col-xs-12 p0">
              <input class="input_group " readonly id="extCategoryName" value="${listCon.conTypes[0].categoryName}"  onclick="opens(this);" type="text">
              <span class="add-on">i</span>
              <div class="cue" id="dCategoryName"></div>
            </div>
          </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
            <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">抽取类型：</span>
            <div class="input-append input_group col-sm-12 col-xs-12 p0">
              <input id="supplierType" class="" type="text" readonly  value="${listCon.conTypes[0].supplierTypeName }" name="supplierTypeName" onclick="showSupplierType();" />
                <span class="add-on">i</span>
              <div class="cue" id="dCount"></div>
            </div>
          </li>
           <li class="col-md-3 col-sm-6 col-xs-12">
            <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">抽取级别：</span>
            <div class="input-append input_group col-sm-12 col-xs-12 p0">
               <input id="levelType" class="" type="text" readonly name="supplierLevel" value="${listCon.supplierLevel}" onclick="showLevel();" />
              <span class="add-on">i</span> 
              <div class="cue" id="dCount"></div>
            </div>
          </li>
           <li class="col-md-3 col-sm-6 col-xs-12">
            <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">抽取总数量：</span>
            <div class="input-append input_group col-sm-12 col-xs-12 p0">
              <input class="input_group"  name="supplierCount" id="supplierCount" value="${listCon.conTypes[0].supplierCount }" type="text">
              <span class="add-on">i</span>
              <div class="cue" id="countSupplier"></div>
            </div>
          </li>
            <li class="col-md-3 col-sm-6 col-xs-12">
            <div class=" w300 pl20 mt24">
            <button class="btn " id="save" onclick="cityt();" type="button">抽取</button>
              <button class="btn  " id="save" onclick="finish();" type="button">完成</button>
            <button class="btn " id="save" onclick="resetQuery();" type="button">暂存</button>
          </div>
          </li>
          
        </ul>
          <!--=== Content Part ===-->
            <h2 class="count_flow"><i>2</i>抽取结果</h2>
              <div align="center" id="countdnone" class="f26    ">满足条件共有<span class="f26 red" id="count">0</span>人</div>
    <ul class="ul_list">
    
      <!-- Begin Content -->
      <div class="col-md-12" id="count" style="min-height: 400px;">
        <div id="extcontype">
         <c:forEach var="con" items="${extConType}">
                
                &nbsp;&nbsp;&nbsp;&nbsp;抽取数量${con.alreadyCount}/${con.supplierCount }                             
            <br />
          </c:forEach>
        </div>
        <div class="col-md-12" style="min-height: 400px;">

          <div class="clear"></div>
          <table id="table" class="table table-bordered table-condensed">
            <thead>
              <tr>
                <th class="info w50">序号</th>
                <th class="info">供应商名称</th>
                <th class="info">类型，级别</th>
                <th class="info">联系人名称</th>
                <th class="info">联系人电话</th>
                <th class="info">联系人手机</th>
                <th class="info">操作</th>
              </tr>
            </thead>
            <tbody id="tbody">
            <c:forEach items="${extRelateListYes}" var="listyes"
                varStatus="vs">
                <tr class='cursor '>
                  <td class='tc' onclick='show();'>${vs.index+1}</td>
                  <td class='tc' onclick='show();'>${listyes.supplier.supplierName}</td>
                  <td class='tc' onclick='show();'>${listyes.supplier.supplierName}</td>
                  <td class='tc' onclick='show();'>${listyes.supplier.contactName}</td>
                  <td class='tc' onclick='show();'>${listyes.supplier.contactTelephone}</td>
                  <td class='tc' onclick='show();'>${listyes.supplier.contactMobile}</td>
                  <td class='tc'><select id='select'
                    onchange='operation(this);'>
                      <c:choose>
                        <c:when test="${listyes.operatingType==1}">
                          <option selected="selected" disabled="disabled"
                            value='${listyes.id},${listyes.supplierConditionId},1'>能参加</option>
                        </c:when>
                        <c:when test="${listyes.operatingType==2}">
                          <option
                            value='${listyes.id},${listyes.supplierConditionId},1'>能参加</option>
                          <option
                            value='${listyes.id},${listyes.supplierConditionId},3'>不能参加</option>
                          <option selected="selected" disabled="disabled"
                            value='${listyes.id},${listyes.supplierConditionId},2'>待定</option>
                        </c:when>
                        <c:when test="${listyes.operatingType==3}">
                          <option selected="selected" disabled="disabled"
                            value='${listyes.id},${listyes.supplierConditionId},3'>不能参加</option>
                        </c:when>
                        <c:otherwise>
                          <option>请选择</option>
                          <option
                            value='${listyes.id},${listyes.supplierConditionId},1'>能参加</option>
                          <option
                            value='${listyes.id},${listyes.supplierConditionId},3'>不能参加</option>
                          <option
                            value='${listyes.id},${listyes.supplierConditionId},2'>待定</option>
                        </c:otherwise>
                      </c:choose>
                  </select></td>
                </tr>
              </c:forEach>
              <c:forEach items="${extRelateListNo }" var="listno"
                varStatus="vs">
                <tr class='cursor'>
                  <td class='tc' onclick='show();'>${(vs.index+1)+1}</td>
                  <td class='tc' onclick='show();'>*****</td>
                  <td class='tc' onclick='show();'>*****</td>
                  <td class='tc' onclick='show();'>*****</td>
                  <td class='tc' onclick='show();'>*****</td>
                  <td class='tc' onclick='show();'>*****</td>
                  <td class='tc'>请选择</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </div>
      </div>
        </ul>
        
      </div>

    </form>
  </div>
</body>
</html>

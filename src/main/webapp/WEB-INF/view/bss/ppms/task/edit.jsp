<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>任务管理</title>  
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">    
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    


<link href="<%=basePath%>public/ZHH/css/common.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/bootstrap.min.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/line-icons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/app.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/application.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v4.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/header-v5.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/brand-buttons.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/footer-v2.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/img-hover.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/page_job.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/ZHH/css/shop.style.css" media="screen" rel="stylesheet" type="text/css">
<link href="<%=basePath%>public/purchase/css/purchase.css" media="screen" rel="stylesheet" type="text/css" >

<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery_ujs.js"></script>
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=basePath%>public/My97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath%>public/layer/layer.js"></script>
<script src="<%=basePath%>public/laypage-v1.3/laypage/laypage.js"></script>

 
  <script type="text/javascript">
  
  
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
    
     function view(){
       layer.open({
          type: 2, //page层
          area: ['500px', '300px'],
          title: '查看变更记录',
          shade:0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['220px', '630px'],
          shadeClose: true,
          content: '<%=basePath%>task/history.html'
        });
    
  }
    
       function edit(){
        var purchaseCount =[]; 
        $('input[name="purchaseCount"]').each(function(){ 
            purchaseCount.push($(this).val()); 
        }); 
        var price =[]; 
        $('input[name="price"]').each(function(){ 
            price.push($(this).val()); 
        }); 
         var budget =[]; 
        $('input[name="budget"]').each(function(){ 
            budget.push($(this).val()); 
        });
        var id =[]; 
        $('input[name="id"]').each(function(){ 
            id.push($(this).val()); 
        });
        var fileName = $("#fileName").val();
        var planNo = $("#planNo").val();
        var ide = $("#ide").val();
           layer.open({
          type: 2, //page层
          area: ['500px', '300px'],
          title: '您是要变更明细吗？',
          shade:0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['220px', '630px'],
          shadeClose: true,
          content: '<%=basePath%>task/addFile.html?purchaseCount='+purchaseCount+'&price='+price+'&id='+id+'&fileName='+fileName+'&planNo='+planNo+'&ide='+ide+'&budget='+budget
        });
            
    }
    
    
    
    
     function sum2(obj){  //数量
         var id=$(obj).next().val();
             $.ajax({
                url:"<%=basePath%>task/viewIds.html",
                type:"post",
                data:"id="+id,
                dataType:"json",
                success:function(data){
                        
                          var purchaseCount = $(obj).val()-0;//数量
                          var price2 = $(obj).parent().next().children(":last").prev();//价钱
                           var price = $(price2).val()-0;
                            var sum = purchaseCount*price;
                            var budgets = $(obj).parent().next().next().children(":last").prev();
                            $(budgets).val(sum);
            
            
            
                        var budget=0;
                       $("#table tr").each(function(){
                        var cid= $(this).find("td:eq(8)").children(":last").val();
                        var same= $(this).find("td:eq(8)").children(":last").prev().val()-0;
                       if(id==cid){
                           
                          budget=budget+same; //查出所有的子节点的值
                       }
                    });
                   for (var i = 0; i < data.length; i++) {
                          var v1 = data[i].id;
                             $("#table tr").each(function(){
                                var pid= $(this).find("td:eq(8)").children(":first").val();//上级id
                                if(data[i].id==pid){
                                    $(this).find("td:eq(8)").children(":first").next().val(budget);
                                }
           
                           }); 
                       }
                    
                    },
                    error: function(data){
                    }
                });
        }  
        
        
        function sum1(obj){
            var id=$(obj).next().val();
             $.ajax({
                url:"<%=basePath%>task/viewIds.html",
                type:"post",
                data:"id="+id,
                dataType:"json",
                success:function(data){
                        
                            var purchaseCount = $(obj).val()-0; //价钱
                            var price2 = $(obj).parent().prev().children(":last").prev().val()-0;//数量
                            var sum = purchaseCount*price2;
                            $(obj).parent().next().children(":last").prev().val(sum);
            
            
            
                        var budget=0;
                       $("#table tr").each(function(){
                        var cid= $(this).find("td:eq(8)").children(":last").val();
                        var same= $(this).find("td:eq(8)").children(":last").prev().val()-0;
                       if(id==cid){
                           
                          budget=budget+same; //查出所有的子节点的值
                       }
                    });
                   for (var i = 0; i < data.length; i++) {
                          var v1 = data[i].id;
                             $("#table tr").each(function(){
                                var pid= $(this).find("td:eq(8)").children(":first").val();//上级id
                                if(data[i].id==pid){
                                    $(this).find("td:eq(8)").children(":first").next().val(budget);
                                }
           
                           }); 
                       }
                    
                    },
                    error: function(data){
                    }
                });
        }
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="#"> 首页</a></li><li><a href="#">保障作业系统</a></li><li><a href="#">采购任务管理</a></li><li class="active"><a href="#">采购计划调整</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
  
<!-- 录入采购计划开始-->
 <div class="container">
   <div class="headline-v2">
      <h2>采购计划调整</h2>
   </div>
<!-- 项目戳开始 -->
   <div class="container clear">
    <div class="p10_25">
     <h2 class="padding-10">
    <ul class="demand_list">
      <li class="fl">
        <input type="hidden" id="ide" value="${queryById.id}"/>
        <label class="fl">
                           计划名称：<input type="text" id="fileName" name="fileName" value="${queryById.fileName}"/>
        </label>
      </li>
      <li class="fl">
        <label class="fl">
                                计划编号：<input type="text" id="planNo" name="planNo" value="${queryById.planNo}"/> 
        </label>
      </li>
       <%-- <label class="fl">计划类型：${collectPlan.fileName} </label>  --%>
     </ul>
   
     <div class="clear"></div>
     </h2>
   </div>
  </div>
   <div class="headline-v2 fl">
      <h2>需求明细调整
      </h2>
       </div> 
     
  
      <span class="fr option_btn margin-top-10">
        <button class="btn btn-windows save" onclick="edit();">变更</button>
        <button class="btn btn-windows back" onclick="location.href='javascript:history.go(-1);'">取消</button>
        <!-- <button class="btn padding-left-10 padding-right-10 btn_back" onclick="view();">查看变更记录</button> -->
      </span>
   <div class="container clear margin-top-30">
        <table class="table table-bordered table-condensed mt5">
        <thead>
        <tr>
          <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
          <th class="info w50">序号</th>
          <th class="info">需求部门</th>
          <th class="info">物资名称</th>
          <th class="info">规格型号</th>
          <th class="info">质量技术标准</th>
          <th class="info">计量单位</th>
          <th class="info">采购数量</th>
          <th class="info">单价（元）</th>
          <th class="info">预算金额（万元）</th>
          <th class="info">交货期限</th>
          <th class="info">采购方式建议</th>
          <th class="info">供应商名称</th>
          <th class="info">是否申请办理免税</th>
          <th class="info">物资用途（进口）</th>
          <th class="info">使用单位（进口）</th>
        </tr>
        </thead>
          <c:forEach items="${lists}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w30"><input type="checkbox" value="${obj.id }" name="chkItem" onclick="check()"  alt=""></td>
              <td class="tc w50">${obj.seq}</td>
              <td class="tc">${obj.department}</td>
              <td class="tc">${obj.goodsName}</td>
              <td class="tc">${obj.stand}</td>
              <td class="tc">${obj.qualitStand}</td>
              <td class="tc">${obj.item}</td>
              <td class="tc">
              <input   type="hidden" name="ss"   value="${obj.id }">
              <input maxlength="11" id="purchaseCount" onblur="sum2(this);"  onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" name="purchaseCount" style="width:50%;"  value="${obj.purchaseCount}"/>
              <input type="hidden" name="ss"   value="${obj.parentId }">
              </td>
              <td class="tc">
              <input   type="hidden" name="ss"   value="${obj.id }">
              <input maxlength="11" id="price"  name="price" style="width:50%;" onblur="sum1(this);"  value="${obj.price}"/>
              <input type="hidden" name="ss"   value="${obj.parentId }">
              </td>
              <td class="tc">
              <input   type="hidden" name="ss"   value="${obj.id }">
              <input maxlength="11" id="budget" name="budget" style="width:50%;border-style:none" readonly="readonly"  value="${obj.budget}"/>
              <input type="hidden" name="ss"   value="${obj.parentId }">
              </td>
             
              <td class="tc">${obj.deliverDate}</td>
              <td class="tc">${obj.purchaseType}</td>
              <td class="tc">${obj.supplier}</td>
              <td class="tc">${obj.isFreeTax}</td>
              <td class="tc">${obj.goodsUse}</td>
              <td class="tc">${obj.useUnit}</td>
            </tr>
     
         </c:forEach>  
         

      </table>
      
      <div id="pagediv" align="right"></div>
   </div>
 </div>


 <div id="content" class="div_show">
     <p align="center" class="type">
             请选择类别
    <br>
    
     <input type="radio" name="goods" value="1">:物资<br>
     <input type="radio" name="goods" value="2">:工程<br>
     <input type="radio" name="goods" value="3">:服务<br>
        </p>
        
 </div>
 
     </body>
</html>

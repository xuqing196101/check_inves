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
       var id =[]; 
        $('input[name="chkItem"]:checked').each(function(){ 
            id.push($(this).val()); 
        }); 
        if(id.length==1){
        window.location.href="<%=basePath%>project/view.html?id="+id;
        }else if(id.length>1){
            layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
        }else{
            layer.alert("请选择需要查看的项目",{offset: ['222px', '390px'], shade:0.01});
        }
    
  }
    
       function start(){
         var id =[]; 
        $('input[name="chkItem"]:checked').each(function(){ 
            id.push($(this).val()); 
        }); 
        if(id.length==1){
           layer.open({
          type: 2, //page层
          area: ['500px', '300px'],
          title: '您是要启动项目吗？',
          shade:0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['220px', '630px'],
          shadeClose: true,
          content: '<%=basePath%>project/startProject.html?id='+id
        });
            
        }else if(id.length>1){
            layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
        }else{
            layer.alert("请选择需要启动的项目",{offset: ['222px', '390px'], shade:0.01});
        }
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
 
   
    
                         计划名称：${queryById.fileName}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                计划编号： ${queryById.planNo}
       <%-- <label class="fl">计划类型：${collectPlan.fileName} </label>  --%>
     
   
     <div class="clear"></div>

 
   <div class="headline-v2 fl">
      <h2>需求明细调整
      </h2>
       </div> 
     
  
      <span class="fr option_btn margin-top-10">
        <button class="btn padding-left-10 padding-right-10 btn_back" >变更</button>
        <button class="btn padding-left-10 padding-right-10 btn_back" onclick="start();">取消</button>
        <button class="btn padding-left-10 padding-right-10 btn_back" onclick="view();">查看变更记录</button>
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
              <td class="tc w50">${(vs.index+1)}</td>
              <td class="tc">${obj.department}</td>
              <td class="tc">${obj.goodsName}</td>
              <td class="tc">${obj.stand}</td>
              <td class="tc"><input name="qualitStand" style="width:50%;"  value="${obj.qualitStand}"/></td>
              <td class="tc"><input name="item" style="width:50%;"  value="${obj.item}"/></td>
              <td class="tc"><input name="purchaseCount" style="width:50%;"  value="${obj.purchaseCount}"/></td>
              <td class="tc"><input name="purchaseCount" style="width:50%;"  value="${obj.price}"/></td>
              <td class="tc">${obj.budget}</td>
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

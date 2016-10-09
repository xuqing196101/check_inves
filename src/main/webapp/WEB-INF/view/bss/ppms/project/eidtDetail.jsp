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
  
  
    
       function edit(){
        var purchaseCount =[]; 
        $('input[name="purchaseCount"]').each(function(){ 
            purchaseCount.push($(this).val()); 
        }); 
        var price =[]; 
        $('input[name="price"]').each(function(){ 
            price.push($(this).val()); 
        }); 
        var id =[]; 
        $('input[name="id"]').each(function(){ 
            id.push($(this).val()); 
        });
        var purchaseType =[]; 
        
        var v = "";
        $(".advice").each(function() {
            var select = $(this).find("select[name='purchaseType']");
            if (!select.size()) {
                v = "";
            } else {
                v = select.val();
            }
             purchaseType.push(v); 
        });  
         layer.confirm('您确定要修改吗?',{
                offset: ['50px','90px'],
                shade:0.01,
                btn:['是','否'],
                },function(){
                window.location.href = '<%=basePath%>project/editDetail.html?purchaseCount='+purchaseCount+'&price='+price+'&id='+id+'&purchaseType='+purchaseType;
                window.setTimeout(function(){
                            location.reload();
                        }, 1000);
                    /*  var index=parent.layer.getFrameIndex(window.name);
                     parent.layer.close(index); */ 
                },function(){
                    var index=parent.layer.getFrameIndex(window.name);
                     parent.layer.close(index);
                }
                    
            ); 
            
    }
    function cancel(){
     var index=parent.layer.getFrameIndex(window.name);
     parent.layer.close(index);
     
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
     <div class="clear"></div>

 
   <div class="headline-v2 fl">
      <h2>修改采购明细
      </h2>
       </div> 
     
  
      <span class="fr option_btn margin-top-10">
        <button class="btn padding-left-10 padding-right-10 btn_back" onclick="edit();">确定</button>
        <button class="btn padding-left-10 padding-right-10 btn_back" onclick="cancel();">返回</button>
      </span>
   <div class="container clear margin-top-30">
        <table class="table table-bordered table-condensed mt5">
        <thead>
        <tr>
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
              <input type="hidden" value="${obj.id }" name="id"/>
              <td class="tc w50">${obj.seq}</td>
              <td class="tc">${obj.department}</td>
              <td class="tc">${obj.goodsName}</td>
              <td class="tc">${obj.stand}</td>
              <td class="tc">${obj.qualitStand}</td>
              <td class="tc">${obj.item}</td>
              <td class="tc"><input name="purchaseCount" style="width:50%;"  value="${obj.purchaseCount}"/></td>
              <td class="tc"><input name="price" style="width:50%;"  value="${obj.price}"/></td>
              <td class="tc">${obj.budget}</td>
              <td class="tc">${obj.deliverDate}</td>
              <td class="tc advice">
              <c:if test="${null!=obj.purchaseType && obj.purchaseType != ''}">
              <select name="purchaseType" style="width:100px" id="select">
              
                            <option value="公开招标" <c:if test="${'公开招标'==obj.purchaseType}">selected="selected"</c:if>>公开招标</option>
                            <option value="邀请招标" <c:if test="${'邀请招标'==obj.purchaseType}">selected="selected"</c:if>>邀请招标</option>
                            <option value="竞争性谈判" <c:if test="${'竞争性谈判'==obj.purchaseType}">selected="selected"</c:if>>竞争性谈判</option>
                            <option value="询价采购" <c:if test="${'询价采购'==obj.purchaseType}">selected="selected"</c:if>>询价采购</option>
                            <option value="单一来源" <c:if test="${'单一来源'==obj.purchaseType}">selected="selected"</c:if>>单一来源</option>
                </select></c:if>
               <%-- <c:if test="${null==obj.purchaseType}">
                            <input name="purchaseType" style="width:50%;"  value="${obj.purchaseType}"/>
               </c:if> --%>
              </td>
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

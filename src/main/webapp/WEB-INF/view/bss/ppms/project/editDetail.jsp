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
         function sum2(obj){  //数量
         var id=$(obj).next().val();
             $.ajax({
                url:"<%=basePath%>project/viewIds.html",
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
                url:"<%=basePath%>project/viewIds.html",
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
        var budget =[]; 
        $('input[name="budget"]').each(function(){ 
            budget.push($(this).val()); 
        });
        
        var purchaseType =[]; 
       /*  var purchase = $("input[name='chkItem']:checked").parents("tr").find("td").eq(10).text(); */
        var ids = $("#ide").val();
        var name = $("#jname").val();
        var projectNumber = $("#projectNumber").val();
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
            layer.confirm('您确定要修改吗?', {title:'提示',offset: ['222px','360px'],shade:0.01}, function(index){
                $.ajax({
                    url:"<%=basePath%>project/editDetail.html",
                    data:"purchaseCount="+purchaseCount+"&price="+price+"&id="+id+"&purchaseType="+purchaseType+"&budget="+budget+"&name="+name+"&projectNumber="+projectNumber+"&ids="+ids,
                    type:"post",
                    dateType:"json",
                    success:function(){
                       layer.msg("修改成功",{offset: ['222px', '390px']});
                        window.setTimeout(function(){
                            window.location.href="<%=basePath%>project/list.html";
                        }, 1000);
                    },
                    error: function(){
                        layer.msg("修改失败",{offset: ['222px', '390px']});
                    }
                });
            });
    }
    
    function sel(obj) {
     var val=$(obj).val();
     $("select option").each(function(){
        var opt = $(this).val();
        if(val==opt){
        $(this).attr("selected","selected");
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
           <li><a href="#"> 首页</a></li><li><a href="#">保障作业系统</a></li><li><a href="#">项目管理</a></li><li class="active"><a href="#">项目调整</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
  
<!-- 录入采购计划开始-->
 <div class="container">
<!-- 项目戳开始 -->
     <div class="clear"></div>

 
   <div class="headline-v2 fl">
      <h2>修改项目明细
      </h2>
       </div> 
     <div class="container clear margin-top-30">
    <input type="hidden" id="ide"  value="${project.id}"/>
    <label class="fl">
                           项目名称：<input type="text" id="jname" name="name" value="${project.name}"/>&nbsp;&nbsp;
     </label>
      <label class="fl">
                           项目编号：<input type="text" id="projectNumber" name="projectNumber" value="${project.projectNumber}"/>
     </label>
      <span class="fr option_btn margin-top-10">
        <button class="btn padding-left-10 padding-right-10 btn_back" onclick="edit();">确定</button>
        <button class="btn padding-left-10 padding-right-10 btn_back" onclick="location.href='javascript:history.go(-1);'">返回</button>
      </span>
   <div class="container clear margin-top-30">
        <table id="table"  class="table table-bordered table-condensed mt5">
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
          <c:forEach items="${lists}"  var="obj" varStatus="vs">
            <tr class="${obj.parentId}" style="cursor: pointer;">
            <input   type="hidden" name="id"   value="${obj.id }">
              <td class="tc w50">${obj.serialNumber}</td>
              <td class="tc">${obj.department}</td>
              <td class="tc">${obj.goodsName}</td>
              <td class="tc">${obj.stand}</td>
              <td class="tc">${obj.qualitStand}</td>
              <td class="tc">${obj.item}</td>
              <td class="tc">
              <c:if test="${obj.purchaseCount!=null }">
              <input   type="hidden" name="ss"   value="${obj.id }">
              <input maxlength="11" id="purchaseCount" onblur="sum2(this);"  onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" name="purchaseCount" style="width:50%;"  value="${obj.purchaseCount}"/>
              <input type="hidden" name="ss"   value="${obj.parentId }">
              </c:if>
              <c:if test="${obj.purchaseCount==null }">
              <input style="border: 0px;"  disabled="disabled"  type="text" name="purchaseCount"  value="${obj.purchaseCount }">
              </c:if>
              </td>
              <td class="tc">
              <c:if test="${obj.price!=null }">
              <input   type="hidden" name="ss"   value="${obj.id }">
              <input maxlength="11" id="price"  name="price" style="width:50%;" onblur="sum1(this);"  value="${obj.price}"/>
              <input type="hidden" name="ss"   value="${obj.parentId }">
              </c:if>
              <c:if test="${obj.price==null}">
              <input style="border: 0px;"  readonly="readonly" onblur="sum1(this)"  type="text" name="price" value="${obj.price }">
              </c:if>
              </td>
              <td class="tc">
              <input   type="hidden" name="ss"   value="${obj.id }">
              <input maxlength="11" id="budget" name="budget" style="width:50%;border-style:none" readonly="readonly"  value="${obj.budget}"/>
              <input type="hidden" name="ss"   value="${obj.parentId }">
              </td>
             
              <td class="tc">${obj.deliverDate}</td>
              <td class="tc advice">
              <c:if test="${null!=obj.purchaseType && obj.purchaseType != ''}">
              <select name="purchaseType" onchange="sel(this);" style="width:100px" id="select">
              
                            <option value="公开招标" <c:if test="${'公开招标'==obj.purchaseType}">selected="selected"</c:if>>公开招标</option>
                            <option value="邀请招标" <c:if test="${'邀请招标'==obj.purchaseType}">selected="selected"</c:if>>邀请招标</option>
                            <option value="竞争性谈判" <c:if test="${'竞争性谈判'==obj.purchaseType}">selected="selected"</c:if>>竞争性谈判</option>
                            <option value="询价采购" <c:if test="${'询价采购'==obj.purchaseType}">selected="selected"</c:if>>询价采购</option>
                            <option value="单一来源" <c:if test="${'单一来源'==obj.purchaseType}">selected="selected"</c:if>>单一来源</option>
                </select></c:if>
              </td>
              <td class="tc">${obj.supplier}</td>
              <td class="tc">${obj.isFreeTax}</td>
              <td class="tc">${obj.goodsUse}</td>
              <td class="tc">${obj.useUnit}</td>
            </tr>
     
         </c:forEach>  
         

      </table>
      </div>
   </div>
 </div>
     </body>
</html>

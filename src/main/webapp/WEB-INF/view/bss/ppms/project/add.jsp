<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
    <%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String tokenValue= new Date().getTime()+UUID.randomUUID().toString()+"";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    
    
    <title>项目管理</title>  
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
<script type="text/javascript" src="<%=basePath%>public/ZHH/js/jquery.validate.min.js"></script>

 
  <script type="text/javascript">
  
  /*分页  */
  $(function(){
      laypage({
            cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
            pages: "${info.pages}", //总页数
            skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
            skip: true, //是否开启跳页
            groups: "${info.pages}">=3?3:"${info.pages}", //连续显示分页数
            curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
//                  var page = location.search.match(/page=(\d+)/);
//                  return page ? page[1] : 1;
                return "${info.pageNum}";
            }(), 
            jump: function(e, first){ //触发分页后的回调
                    if(!first){ //一定要加此判断，否则初始时会无限刷新
                //  $("#page").val(e.curr);
                    // $("#form1").submit();
                    
                 location.href = '<%=basePath%>project/add.html?page='+e.curr;
                }  
            }
        });
        
        $("#form1").validate({
            errorElement: "span",
            rules:{
                name:{
                    remote:{
                        type:"post",
                        url:"<%=basePath%>project/SameNameCheck.html",
                        dataType: "json",
                        data:{
                            name:function(){
                                return $("#pic").val();
                            }
                        }
                    }
                },
                
            },
            messages:{
                name:{
                    remote:"<span class='spredm'>*该项目名称已存在</span>"
                    },
                
                
            }
        });
  });
  
    
    /** 勾选父子节点 */
    function check(ele){
        var flag = $(ele).prop("checked");
         var purchaseType = $("input[name='chkItem']:checked").parents("tr").find("td").eq(10).text();
             purchaseType = $.trim(purchaseType);
        var id = $(ele).val();
        $.ajax({
                    url:"<%=basePath%>project/checkDeail.html",
                    data:"id="+id,
                    type:"post",
                    dataType:"json",
                    success:function(result){
                       for (var i = 0; i < result.length; i++) {
                           $("input[name='chkItem']").each(function() {
                                var v1 = result[i].id;
                                var v3 = result[i].purchaseType;
                          if(v3 == purchaseType){
                                var v2 = $(this).val();
                                if (v1 == v2) {
                                    $(this).prop("checked", flag);
                                }
                             }else{
                                    layer.alert("采购方式不相同",{offset: ['222px', '390px'], shade:0.01});
                                }
                           });
                       }
                    },
                    error: function(){
                        layer.msg("失败",{offset: ['222px', '390px']});
                    }
                });
    }
    
    
    function add(){
        var id =[]; 
        var name = $("input[name='name']").val();
        var projectNumber = $("input[name='projectNumber']").val();
        $('input[name="chkItem"]:checked').each(function(){ 
            id.push($(this).val()); 
        }); 
        $("#ids").val(id);
         if(id==""){
                layer.tips("请勾选明细","#check");
          } else if (name==""){
                layer.tips("项目名称不允许为空","#pic");
          } else if (projectNumber==""){
                layer.tips("项目编号不允许为空","#pc");
          }else{
            layer.msg("添加成功",{offset: ['222px', '690px']});
            window.setTimeout(function(){
                $("#form1").submit();
             }, 1000);
          }
         
    }
    
    function bask(){
       window.location.href="<%=basePath%>project/list.html";
    }
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="#"> 首页</a></li><li><a href="#">保障作业系统</a></li><li><a href="#">立项管理</a></li><li class="active"><a href="#">新增需求明细</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
<!-- 录入采购计划开始-->
 <div class="container">
    <div class="headline-v2">
      <h2>新增信息</h2>
   </div>
   <form id="form1" action="<%=basePath%>project/create.html" method="post">
   <%
            session.setAttribute("tokenSession", tokenValue);
         %>
         <input type="hidden"  name="token2" value="<%=tokenValue%>">
         <input type="hidden"  name="id" id="ids">
   <span class="f14 fl">项目名称：</span>
        <div class="fl" >
          <input id="pic" type="text" class="toinline" name="name"/>
          &nbsp;&nbsp;
        </div>
        
         <span class="f14 fl">项目编号：</span>
        <div class="fl" >
          <input id="pc" type="text" class="toinline" name="projectNumber"/>
        </div>
        <%--
<!-- 项目戳开始 -->
 
   <div class="p10_25">
    <h2 class="padding-10 border1">
     <form id="add_form" action="<%=basePath%>project/add.html" method="post" >
     <ul class="demand_list">
     <li class="fl">
       <label class="fl">需求部门：<input type="text" name="purchaseRequiredId" /></label>
       </li>
      <label class="fl">年度：<select name="giveTime" style="width:70px" id="select">
    <option selected="selected" value="">请选择</option>
       <c:forEach items="${task}" var="task">
                            
                            <option value="${task.giveTime}">${task.giveTime}</option>
                         
       </c:forEach>  
  </select> </label>
  <li class="fl">
      <label class="fl">采购方式：<select name="procurementMethod" style="width:100px" id="select">
       <option selected="selected" value="">请选择</option>
                            <option value="公开招标" <c:if test="${'公开招标'==obj.purchaseType}">selected="selected"</c:if>>公开招标</option>
                            <option value="邀请招标" <c:if test="${'邀请招标'==obj.purchaseType}">selected="selected"</c:if>>邀请招标</option>
                            <option value="竞争性谈判" <c:if test="${'竞争性谈判'==obj.purchaseType}">selected="selected"</c:if>>竞争性谈判</option>
                            <option value="询价采购" <c:if test="${'询价采购'==obj.purchaseType}">selected="selected"</c:if>>询价采购</option>
                            <option value="单一来源" <c:if test="${'单一来源'==obj.purchaseType}">selected="selected"</c:if>>单一来源</option>
  </select> </label> 
  </li>
       <li class="fl">
       <label class="fl">采购机构：<input type="text" name="purchaseId"/></label>
       </li>
         <button class="btn" type="submit">查询</button>
         <button type="reset" class="btn">重置</button> 
     </ul>
     <div class="clear"></div>
    </form>
    </h2>
    </div> --%>
     <div class="clear"></div>

 </form>
   <div class="headline-v2 fl">
      <h2>选择需求明细
      </h2>
   </div> 
   <span class="fr option_btn margin-top-10">
        <button class="btn btn-windows save" type="button" onclick="add()">确认</button>
        <button class="btn btn-windows back" type="button"  onclick="bask();">返回</button>
      </span>
   <div class="container margin-top-5">
    <table class="table table-striped table-bordered table-hover">
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
          <th class="info">备注</th>
          <th class="info w50">选择</th>
        </tr>
        </thead>
            <tbody id="tbody_id">
                 <c:forEach items="${list}" var="obj" varStatus="vs">
          <%-- <c:if test="${'0'==obj.status}"> --%>
            <tr style="cursor: pointer;">
              <td class="tc w50">${obj.seq}</td>
              <td class="tc">${obj.department}</td>
              <td class="tc">${obj.goodsName}</td>
              <td class="tc">${obj.stand}</td>
              <td class="tc">${obj.qualitStand}</td>
              <td class="tc">${obj.item}</td>
              <td class="tc">${obj.purchaseCount}</td>
              <td class="tc">${obj.price}</td>
              <td class="tc">${obj.budget}</td>
              <td class="tc">${obj.deliverDate}</td>
              <td class="tc"> ${obj.purchaseType}</td>
              <td class="tc">${obj.supplier}</td>
              <td class="tc">${obj.isFreeTax}</td>
              <td class="tc">${obj.goodsUse}</td>
              <td class="tc">${obj.useUnit}</td>
              <td class="tc">${obj.memo}</td>
              <td class="tc w30"><input type="checkbox" id="check" value="${obj.id }" name="chkItem" onchange="check(this)"  alt=""></td>
            </tr>
          <%-- </c:if> --%>
         </c:forEach>
            </tbody> 
         

      </table>
      
   </div>
   
 </div>
    
    
 
     </body>
</html>

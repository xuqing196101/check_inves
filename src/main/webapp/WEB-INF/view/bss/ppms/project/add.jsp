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
        
        
        var id = $("#idss").val();
        if(!id){
            $("#hide_detail").hide();
        }else{
            $("#hide_detail").show();
        }
        
        var v = "${checkedIds}";
        console.info(v);
        if (v) {
            var vs = v.split(",");
            for (var i = 0; i < vs.length; i++) {
                 $("#task_id").find(":checkbox").each(function() {
                    if(vs[i] == $(this).val()) {
                        $(this).prop("checked", true);
                    }
                });
            }
        }
       
  });
  
    
    /** 勾选父子节点 */
    function check(ele){
       var id = $(ele).val();
       var checkedIds = "";
       $("#task_id").find(":checkbox:checked").each(function() {
            if (checkedIds) {
                checkedIds += ",";
            }
            checkedIds += $(this).val();
       });
       if ($(ele).prop("checked")) {
            window.location.href = "<%=basePath%>project/addDeatil.html?id=" + id +"&checkedIds=" + checkedIds;
       }
      
    }
    
    function add(){
       // var id =[]; 
        var name = $("input[name='name']").val();
        var projectNumber = $("input[name='projectNumber']").val();
        /* $('input[name="chkItem"]:checked').each(function(){ 
            id.push($(this).val()); 
        });  */
         /* if(id==""){
                layer.tips("请勾选明细","#check");
          } else  */
          if (name==""){
                layer.tips("项目名称不允许为空","#pic");
          } else if (projectNumber==""){
                layer.tips("项目编号不允许为空","#pc");
          }else{
            window.setTimeout(function(){
                $("#form1").submit();
                layer.msg("添加成功",{offset: ['222px', '690px']});
             }, 1000);
          }
         
    }
    
     <%-- function bask(){
        var id = $("#idss").val();
        if(!id){
            window.history.go(-1);
        }else{
            layer.confirm('您要删除之前的操作吗?',{
                offset: ['300px','600px'],
                shade:0.01,
                btn:['是','否'],
                },function(){
                 window.location.href="<%=basePath%>project/list.html";
                },function(){
                 window.history.bask();
                }
                    
            );
        } 
      }   --%>
         function bask(){
                 window.location.href="<%=basePath%>project/list.html";
    } 
  </script>
  </head>
  
 <body>
 <form id="form1" action="<%=basePath%>project/create.html" method="post">
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="#"> 首页</a></li><li><a href="#">保障作业系统</a></li><li><a href="#">采购项目管理</a></li><li class="active"><a href="#">立项管理</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
 <div class="container">
    <div class="col-md-12 tab-pane active">
      <h1 class="f16 count_flow">
      <i>01</i>
                        请填写信息
      </h1>
   </div>
   <%-- 
        <%
            session.setAttribute("tokenSession", tokenValue);
         %>
         <input type="hidden"  name="token2" value="<%=tokenValue%>"> --%>
            <span class="f14 fl"><i class="spredm">*</i>&nbsp;项目名称：</span>
        <div class="fl" >
          <input id="pic" type="text" class="toinline" name="name"/>
          &nbsp;&nbsp;
        </div>
        
         <span class="f14 fl"><i class="spredm">*</i>&nbsp;项目编号：</span>
        <div class="fl" >
          <input id="pc" type="text" class="toinline" name="projectNumber"/>
        </div>
     
  
      <div class="col-md-12 tab-pane active">
      <h1 class="f16 count_flow">
      <i>02</i>
                        选择采购明细
      </h1>
   </div>
   <span class="fr option_btn margin-top-10">
        <button class="btn btn-windows save" onclick="add();" type="button">确定</button>
        <button class="btn btn-windows back" onclick="bask();" type="button">返回</button>
        <input type="hidden" id="idss" value="${ids}"/>
      </span>
   <div class="container clear margin-top-30">
     <div class="container margin-top-5">
    <table class="table table-striped table-bordered table-hover">
        <thead>
        <tr>
          <th class="info w50">序号</th>
          <th class="info">采购任务名称</th>
          <th class="info">采购管理部门</th>
          <th class="info">下达文件编号</th>
          <th class="info">状态</th>
          <th class="info">下达时间</th>
          <th class="info"><i class="spredm">*</i>&nbsp;操作</th>
        </tr>
        </thead>
        <tbody id="task_id">
         <c:forEach items="${info.list}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
              <td class="tc">${obj.name}</td>
              <td class="tc">${obj.purchaseId }</td>
              <td class="tc">${obj.documentNumber}</td>
              <td class="tc">
               <c:if test="${'0'==obj.status}"><span class="label rounded-2x label-u">受领</span></c:if>
              </td>
              <td class="tc" ><fmt:formatDate value="${obj.giveTime }"/></td>
              <td class="tc w30"><input type="checkbox" value="${obj.id }" name="chkItem" onchange="check(this)"  alt=""></td>
            </tr>
     
         </c:forEach> 
          </tbody>
      </table>
      </div>
      <div id="pagediv" align="right"></div>
   </div>
 </div>
    <div id="hide_detail">
         <div class="container clear margin-top-30">
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
        </tr>
        </thead>
          <c:forEach items="${lists}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w50">${obj.seq}<input type="hidden" name="list[${vs.index }].seq" value="${obj.seq }"><input type="hidden" name="list[${vs.index }].id" value="${obj.id }"></td>
              <td class="tc">${obj.department}<input type="hidden" name="list[${vs.index }].department" value="${obj.department }"></td>
              <td class="tc">${obj.goodsName}<input type="hidden" name="list[${vs.index }].goodsName" value="${obj.goodsName }"></td>
              <td class="tc">${obj.stand}<input type="hidden" name="list[${vs.index }].stand" value="${obj.stand }"></td>
              <td class="tc">${obj.qualitStand}<input type="hidden" name="list[${vs.index }].qualitStand" value="${obj.qualitStand }"></td>
              <td class="tc">${obj.item}<input type="hidden" name="list[${vs.index }].item" value="${obj.item }"></td>
              <td class="tc">${obj.purchaseCount}<input type="hidden" name="list[${vs.index }].purchaseCount" value="${obj.purchaseCount }"></td>
              <td class="tc">${obj.price}<input type="hidden" name="list[${vs.index }].price" value="${obj.price }"></td>
              <td class="tc">${obj.budget}<input type="hidden" name="list[${vs.index }].budget" value="${obj.budget }"></td>
              <td class="tc">${obj.deliverDate}<input type="hidden" name="list[${vs.index }].deliverDate" value="${obj.deliverDate }"></td>
              <td class="tc"> ${obj.purchaseType}<input type="hidden" name="list[${vs.index }].purchaseType" value="${obj.purchaseType }"></td>
              <td class="tc">${obj.supplier}<input type="hidden" name="list[${vs.index }].supplier" value="${obj.supplier }"></td>
              <td class="tc">${obj.isFreeTax}<input type="hidden" name="list[${vs.index }].isFreeTax" value="${obj.isFreeTax }"></td>
              <td class="tc">${obj.goodsUse}<input type="hidden" name="list[${vs.index }].goodsUse" value="${obj.goodsUse }"></td>
              <td class="tc">${obj.useUnit}<input type="hidden" name="list[${vs.index }].useUnit" value="${obj.useUnit }"></td>
              <td class="tc">${obj.memo}<input type="hidden" name="list[${vs.index }].memo" value="${obj.memo }">
                <input type="hidden" name="list[${vs.index }].parentId" value="${obj.parentId }">
              </td>
            </tr>
     
         </c:forEach>  
         

      </table>
      </div>
      </div>
    </div>

 </form>
</body>
</html>

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
        var idr = $("#idr").val();
        if(idr==null || idr == ''){
            $("#qwe").hide();
        }
        
  });
  
    
    /** 单选 */
    function check(){
        var id =[]; 
        $('input[name="chkItem"]:checked').each(function(){ 
            id.push($(this).val()); 
        }); 
        if(id.length==1){
           layer.open({
          type: 2, //page层
          area: ['900px', '700px'],
         // title: '您是要取消任务吗？',
          shade:0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['220px', '630px'],
          shadeClose: true,
          content: '<%=basePath%>project/viewDet.html?id='+id
        });
        }else if(id.length>1){
            layer.alert("只能选择一个",{offset: ['222px', '390px'], shade:0.01});
        }
        
    }
    
        function add(){
        var idss = $("input[name='idss']").val();
        if(!idss){
           layer.alert("请选择",{offset: ['222px', '390px'], shade:0.01});
            
        }else{
            
            layer.open({
          type: 2, //page层
          area: ['500px', '300px'],
         // title: '您是要取消任务吗？',
          shade:0.01, //遮罩透明度
          moveType: 1, //拖拽风格，0是默认，1是传统拖动
          shift: 1, //0-6的动画形式，-1不开启
          offset: ['220px', '630px'],
          shadeClose: true,
          content: '<%=basePath%>project/create.html?id='+idss
        });
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
           <li><a href="#"> 首页</a></li><li><a href="#">保障作业系统</a></li><li><a href="#">采购任务管理</a></li><li class="active"><a href="#">采购任务受领管理</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
<!-- 录入采购计划开始-->
 <div class="container">
   <div class="headline-v2">
      <h2>查询条件</h2>
   </div>
<!-- 项目戳开始 -->
 
   <div class="p10_25">
    <h2 class="padding-10 border1">
     <form id="add_form" action="<%=basePath%>project/add.html" method="post" >
     <ul class="demand_list">
     <li class="fl">
       <label class="fl">需求部门：<input type="text" name="purchaseRequiredId" /></label>
       </li>
     <%--  <label class="fl">年度：<select name="giveTime" style="width:70px" id="select">
    <option selected="selected" value="">请选择</option>
       <c:forEach items="${task}" var="task">
                            
                            <option value="${task.giveTime}">${task.giveTime}</option>
                         
       </c:forEach>  
  </select> </label>--%>
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
    </div>
     <div class="clear"></div>

 <input type="hidden" name="idss" value="${idss }">
 <input type="hidden" name="idr" id="idr" value="${sessionScope.idr }">
   <div class="headline-v2 fl">
      <h2>选择采购任务
      </h2>
   </div> 
   <span class="fr option_btn margin-top-10">
        <button class="btn btn-windows save" onclick="add()">确认</button>
        <button class="btn btn-windows back"  onclick="bask();">返回</button>
      </span>
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
          <th class="info">操作</th>
        </tr>
        </thead>
         <c:forEach items="${info.list}" var="obj" varStatus="vs">
          <c:if test="${'0'==obj.status}">
            <tr style="cursor: pointer;">
              <td class="tc w50">${(vs.index+1)+(list.pageNum-1)*(list.pageSize)}</td>
              <td class="tc" >${obj.name}</td>
              <td class="tc">${obj.purchaseId }</td>
              <td class="tc" >${obj.documentNumber }</td>
              <td class="tc">
                  <c:if test="${'0'==obj.status}"><span class="label rounded-2x label-u">受领</span></c:if>
              </td>
              <td class="tc" ><fmt:formatDate value="${obj.giveTime }"/></td>
              <td class="tc w30"><input type="checkbox" value="${obj.id }" name="chkItem" onclick="check()"  alt=""></td>
            </tr>
          </c:if>
         </c:forEach> 
         

      </table>
      
      <div id="pagediv" align="right"></div>
   </div>
 </div>
    
    <div id= "qwe">
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
          <th class="info w30"><input type="checkbox" id="checkAll" onclick="selectAll()"  alt=""></th>
        </tr>
        </thead>
          <c:forEach items="${lists}" var="obj" varStatus="vs">
            <tr style="cursor: pointer;">
              <td class="tc w50">${obj.serialNumber}</td>
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
              <td class="tc w30"><input type="checkbox" value="${obj.id }" name="chkItem" onclick="check()"  alt=""></td>
            </tr>
     
         </c:forEach>  
         

      </table>
    </div>
 
     </body>
</html>

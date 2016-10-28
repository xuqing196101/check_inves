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
    
    
    <title></title>  
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
     function save(){
        var id =[]; 
        $('input[name="chkItem"]:checked').each(function(){ 
            id.push($(this).val()); 
        }); 
       if(id.length>0){
            window.location.href="<%=basePath%>project/save.html?id="+id;
        }else{
            layer.alert("请选择明细",{offset: ['222px', '390px'], shade:0.01});
        }
    }
    
    function cancel(){
    	form1.submit();
	}
	function select(){
	    var projectId=$("#projectId").val();
	    var packageId=$("#packageId").val();
		window.location.href="<%=basePath%>mulQuo/quoteHistory.html?projectId="+projectId+"&packageId="+packageId;
	}
     function addTotal(value){
     	var patt1 = new RegExp("^([0]|[1-9][0-9]*)+(.[0-9]*)?$");
		var price=document.getElementsByName("price"+value)[0].value;
		var quantity=document.getElementsByName("quantity"+value)[0].value;
		if(patt1.test(price)&&patt1.test(quantity)){
			document.getElementsByName("total"+value)[0].value=(price*quantity).toFixed(2);
			//给合同总金额赋值
			var totalMoney = 0;
		    var tableObj = document.getElementById("tb");
		    for (var i = 1; i < tableObj.rows.length-1; i++) {    //遍历Table的所有Row
		           	  var total = document.getElementsByName("total"+(i-1))[0].value;   //获取Table中单元格的内容
					  if(total.length>0){
					  totalMoney+=parseFloat(total);
					  }
		    }
		    $("#quotePrice").val(totalMoney);
		}
	}
  </script>
  </head>
  
  <body>
<!--面包屑导航开始-->
 <div class="margin-top-10 breadcrumbs ">
      <div class="container">
           <ul class="breadcrumb margin-left-0">
           <li><a href="#"> 首页</a></li><li><a href="#">供应商管理</a></li><li><a href="#">供应商报价</a></li><li class="active"><a href="#">多次报价</a></li>
           </ul>
        <div class="clear"></div>
      </div>
   </div>
<!-- 录入采购计划开始-->
 <div class="container">
<!-- 项目戳开始 -->
   <div class="clear"></div>
   <div class="headline-v2 fl">
      <h2>报价明细
      </h2>
       </div> 
       <span class="fr option_btn margin-top-10">
       <button class="btn btn-windows back" onclick="history.go(-1)" type="button">返回</button>
        <button class="btn btn-windows save" onclick="cancel();">确定</button>
        <button class="btn btn-windows save" onclick="select();">报价记录</button>
      </span>
     
   <div class="container clear margin-top-30">
        <table id="tb" class="table table-bordered table-condensed mt5">
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
          <th class="info">小计</th>
        </tr>
        </thead>
          <c:forEach items="${list}" var="obj" varStatus="vs">
            <tr class="hand">
              <td class="tc w50">${obj.serialNumber}</td>
              <td class="tc">${obj.department}</td>
              <td class="tc">${obj.goodsName}</td>
              <td class="tc">${obj.stand}</td>
              <td class="tc">${obj.qualitStand}</td>
              <td class="tc">${obj.item}</td>
              <td class="tc"><input name="quantity${vs.index }" readonly="readonly" value="${obj.purchaseCount}" /></td>
              <td class="tc"><input maxlength="15" name="price${vs.index }" onkeyup="addTotal('${vs.index}')" /></td>
              <td class="tc"><input readonly="readonly" name="total${vs.index }" /></td>
            </tr>
         </c:forEach>  
         <tr>
         	<td class="tc" colspan="2"><b>总金额</b>
         	</td>
         	<td class="tl" colspan="7">
         		<form id="form1" action="<%=basePath%>mulQuo/save.html">
	         		<input readonly="readonly" id="quotePrice" name="quotePrice"  />
	         		<input type="hidden" id="projectId" name="projectId" value="${id }" />
	         		<input type="hidden" id="packageId" name="packageId" value="${packageId }" />
         		</form>
         	</td>
         </tr>
      </table>
   </div>
 </div>
     </body>
</html>

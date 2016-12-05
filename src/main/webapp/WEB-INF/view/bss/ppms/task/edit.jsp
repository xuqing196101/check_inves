<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="/tld/upload" prefix="f"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<script type="text/javascript">
  /** 全选全不选 */
  function selectAll(){
    var checklist = document.getElementsByName ("chkItem");
    var checkAll = document.getElementById("checkAll");
    if(checkAll.checked){
      for(var i=0;i<checklist.length;i++){
        checklist[i].checked = true;
      } 
    }else{
      for(var j=0;j<checklist.length;j++){
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
    
  //查看
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
      content: '${pageContext.request.contextPath}/task/history.html'
    });
  }

  //数量  
  function sum2(obj){  
    var id=$(obj).next().val();
    $.ajax({
      url:"${pageContext.request.contextPath}/task/viewIds.html",
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
    });
  }  
        
  //单价      
  function sum1(obj){
    var id=$(obj).next().val();
    $.ajax({
      url:"${pageContext.request.contextPath}/task/viewIds.html",
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
    });
  }
        
  //修改      
  function edit(){
    var fileName = $("#fileName").val();
    var planNo = $("#planNo").val();
    if(fileName==""){
      layer.tips("计划名称不能为空","#fileName");
    }else if(planNo==""){
      layer.tips("计划编号不能为空","#planNo");
    }else{
      /* layer.open({
        type: 1, //page层
        area : [ '400px', '200px' ],
        title: '请上传更改附件',
        shade:0.01, //遮罩透明度
        moveType: 1, //拖拽风格，0是默认，1是传统拖动
        shift: 1, //0-6的动画形式，-1不开启
        offset: ['220px', '630px'],
        shadeClose: true,
        content:$("#file")
      }); */
       $("#form1").submit();
    }
  }
        
  //上传附件      
  function delTask(id){
    var upload_id = $("#upload_id").val();
    if(upload_id){
      $("#form1").submit();
    }else{
      layer.tips("请上传附件", "#uuId");
    } 
  }
  
  //关闭弹出框
  function cancel(){
    layer.closeAll();
  }
</script>
</head>
  
<body>
  <!--面包屑导航开始-->
  <div class="margin-top-10 breadcrumbs ">
    <div class="container">
      <ul class="breadcrumb margin-left-0">
        <li><a href="javascript:void(0)"> 首页</a></li>
        <li><a href="javascript:void(0)">保障作业系统</a></li>
        <li><a href="javascript:void(0)">采购任务管理</a></li>
        <li class="active"><a href="javascript:void(0)">采购计划调整</a></li>
      </ul>
      <div class="clear"></div>
    </div>
  </div>
  
<!-- 录入采购计划开始-->
  <div class="container container_box">
    <sf:form action="${pageContext.request.contextPath}/task/update.html" id="form1" method="post" modelAttribute="task">
      <div>
        <h2 class="count_flow"><i>1</i>采购计划调整</h2>
        <ul class="ul_list">
	      <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">计划名称</span>
			<div class="input-append input_group col-sm-12 col-xs-12 p0">
			  <input type="text" id="fileName" maxlength="20" name="name" class="input_group" value="${task.name}"/>
			  <span class="add-on">i</span>
			  <div class="cue">${ERR_name}</div>
			</div>
	      </li>
		  <li class="col-md-3 col-sm-6 col-xs-12 pl15">
            <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">计划编号</span>
            <div class="input-append input_group col-sm-12 col-xs-12 p0">
              <input type="text" id="planNo" maxlength="20" name="documentNumber" class="input_group" value="${task.documentNumber}"/> 
              <span class="add-on">i</span>
              <div class="cue">${ERR_documentNumber}</div>
            </div>
          </li>
		</ul>
      </div>
      <div>
        <h2 class="count_flow"><i>2</i>需求明细调整</h2>
        <ul class="ul_list">
          <div class="content table_box">
            <table id="table" class="table table-bordered table-condensed table-hover">
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
			      <td class="tc w50">${obj.seq}  <input style="border: 0px;" type="hidden" name="list[${vs.index }].id" value="${obj.id }"></td>
			      <td class="tc">${obj.department}</td>
			      <td class="tc">${obj.goodsName}</td>
			      <td class="tc">${obj.stand}</td>
			      <td class="tc">${obj.qualitStand}</td>
			      <td class="tc">${obj.item}</td>
			      <td class="tc">
			        <c:if test="${obj.purchaseCount!=null}">
			          <input   type="hidden" name="ss"   value="${obj.id }">
			          <input maxlength="11" id="purchaseCount" onblur="sum2(this);"  onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" name="list[${vs.index }].purchaseCount" style="width:50%;"  value="${obj.purchaseCount}"/>
			          <input type="hidden" name="ss"   value="${obj.parentId }">
			        </c:if>
			        <c:if test="${obj.purchaseCount==null }">
			          <input  class="border0"  disabled="disabled"  type="text" name="list[${vs.index }].purchaseCount" onblur="checks(this)"  value="${obj.purchaseCount }">
			        </c:if>
			      </td>
			      <td class="tc">
			        <c:if test="${obj.price!=null}">
			          <input   type="hidden" name="ss"   value="${obj.id }">
			          <input maxlength="11" id="price"  name="list[${vs.index }].price" style="width:50%;" onblur="sum1(this);"  value="${obj.price}"/>
			          <input type="hidden" name="ss"   value="${obj.parentId }">
			        </c:if>
			        <c:if test="${obj.price==null}">
			          <input class="border0"  readonly="readonly"  type="text" name="list[${vs.index }].price" value="${obj.price }">
			        </c:if>
			      </td>
			      <td class="tc">
			        <input   type="hidden" name="ss"   value="${obj.id }">
			        <input maxlength="11" id="budget" name="list[${vs.index }].budget" style="width:50%;border-style:none" readonly="readonly"  value="${obj.budget}"/>
			        <input type="hidden" name="ss"   value="${obj.parentId }">
			      </td>
			      <td class="tc">${obj.deliverDate}</td>
			      <td class="tc">
			        <c:forEach items="${kind}" var="kind" >
			          <c:if test="${kind.id == obj.purchaseType}">${kind.name}</c:if>
			        </c:forEach>
			      </td>
			      <td class="tc">${obj.supplier}</td>
			      <td class="tc">${obj.isFreeTax}</td>
			      <td class="tc">${obj.goodsUse}</td>
			      <td class="tc">${obj.useUnit}</td>
			      <td class="tc">${obj.memo }
			         <input type="hidden" name="list[${vs.index }].seq" value="${obj.seq }">
			         <input type="hidden" name="list[${vs.index }].department" value="${obj.department }">
			         <input type="hidden" name="list[${vs.index }].goodsName" value="${obj.goodsName }">
			         <input type="hidden" name="list[${vs.index }].stand" value="${obj.stand }">
			         <input type="hidden" name="list[${vs.index }].qualitStand" value="${obj.qualitStand }">
			         <input type="hidden" name="list[${vs.index }].item" value="${obj.item }">
			         <input type="hidden" name="list[${vs.index }].deliverDate" value="${obj.deliverDate }">
			         <input type="hidden" name="list[${vs.index }].purchaseType" value="${obj.purchaseType }">
			         <input type="hidden" name="list[${vs.index }].supplier" value="${obj.supplier }">
			         <input type="hidden" name="list[${vs.index }].isFreeTax" value="${obj.isFreeTax }">
			         <input type="hidden" name="list[${vs.index }].goodsUse" value="${obj.goodsUse }">
			         <input type="hidden" name="list[${vs.index }].useUnit" value="${obj.useUnit }">
			         <input type="hidden" name="list[${vs.index }].memo" value="${obj.memo }">
			         <input type="hidden" name="list[${vs.index }].planName" value="${obj.planName }">
			         <input type="hidden" name="list[${vs.index }].planNo" value="${obj.planNo }">
			         <input type="hidden" name="list[${vs.index }].planType" value="${obj.planType }">
			         <input type="hidden" name="list[${vs.index }].parentId" value="${obj.parentId }">
			         <input type="hidden" name="list[${vs.index }].historyStatus" value="${obj.historyStatus }">
			         <input type="hidden" name="list[${vs.index }].goodsType" value="${obj.goodsType }">
			         <input type="hidden" name="list[${vs.index }].organization" value="${obj.organization }">
			         <input type="hidden" name="list[${vs.index }].auditDate" value="${obj.auditDate }">
			         <input type="hidden" name="list[${vs.index }].isMaster" value="${obj.isMaster }">
			         <input type="hidden" name="list[${vs.index }].isDelete" value="${obj.isDelete }">
			         <input type="hidden" name="list[${vs.index }].status" value="${obj.status }">
			       </td>
			     </tr>
			   </c:forEach>  
             </table>
           </div>   
         </ul>
       </div>
       <div class="col-md-12 tc">
         <button class="btn btn-windows save" type="button" onclick="edit();">变更</button>
         <button class="btn btn-windows back" type="button" onclick="location.href='javascript:history.go(-1);'">取消</button>
       </div>
       <!-- 上传  --> 
       <div id="file" class="dnone">
         <div class="drop_window">
        <ul class="list-unstyled">
        <li class="mt10 col-md-12 p0">
            <label class="col-md-12 pl20" id="uuId"></label>
            <span class="col-md-12">
               <input type="hidden" name="id" value="${task.id}"/>
         <f:upload id="upload_id" businessId="${task.id}" typeId="${dataId}" sysKey="2"/>
         <f:show showId="upload_id" businessId="${task.id}" sysKey="2" typeId="${dataId}"/>
            </span>
          </li>
        </ul>
        </div>
		
		 <div class="tc mt10 col-md-12">
		   <br>
		   <a class="btn btn-windows save" onclick="delTask('${task.id}');">确认</a>
		   <input class="btn btn-windows cancel" value="取消" type="button" onclick="cancel();">
		 </div>
       </div> 
     </sf:form>
  </div>
</body>
</html>

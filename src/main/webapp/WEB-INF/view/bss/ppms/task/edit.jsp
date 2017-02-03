<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp"%>
<script type="text/javascript" src="http://code.jquery.com/jquery-1.6.1.min.js"></script>
<script src="${pageContext.request.contextPath}/public/backend/js/lock_table_head.js" ></script>
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
        sum = sum/1000;
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
        sum = sum/1000;
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
        
  
  //关闭弹出框
  function cancel(){
    layer.closeAll();
  }
  
  var flag = true;
  function verify(){
	  var taskNum = $("#taskNum").val();
    var documentNumber = $("input[name='documentNumber']").val();
     $.ajax({
      url : "${pageContext.request.contextPath}/task/verify.html",
      type : "post",
      data : "documentNumber=" + documentNumber+"&taskNum=" +taskNum,
      dataType : "json",
      success : function(data) {
         var datas = eval("("+data+")");
         if(datas == false){
          $("#sps").html("已存在").css('color','red');
          flag = false;
         } else{ 
           $("#sps").html("");
           flag = true;
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
      if(flag == true){
        $("#form1").submit();
      }
       
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
  
  //重置
  function sel(obj) {
  var val = $(obj).val();
  $("select option").each(function() {
    var opt = $(this).val();
    if (val == opt) {
    $(this).attr("selected", "selected");
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
        <li><a href="javascript:void(0)">首页</a></li>
        <li><a href="javascript:void(0)">保障作业系统</a></li>
        <li><a href="javascript:void(0)">采购任务管理</a></li>
        <li class="active"><a href="javascript:void(0)">采购计划调整</a></li>
      </ul>
      <div class="clear"></div>
    </div>
  </div>
  <input type="hidden" id="taskNum" value="${task.documentNumber}"/>
<!-- 录入采购计划开始-->
  <div class="container container_box">
    <sf:form action="${pageContext.request.contextPath}/task/update.html" id="form1" method="post" modelAttribute="task">
      <div>
        <h2 class="count_flow"><i>1</i>采购任务调整</h2>
        <ul class="ul_list">
	      <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			<span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">任务名称</span>
			<div class="input-append input_group col-sm-12 col-xs-12 p0">
			  <input type="text" id="fileName" maxlength="20" name="name" class="input_group" value="${task.name}"/>
			  <input type="hidden" name="collectId" value="${task.collectId }"/>
			  <span class="add-on">i</span>
			  <div class="cue">${ERR_name}</div>
			 
			</div> 
	      </li>
		  <li class="col-md-3 col-sm-6 col-xs-12 pl15">
            <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12">任务编号</span>
            <div class="input-append input_group col-sm-12 col-xs-12 p0">
              <input type="text" id="planNo" maxlength="20" name="documentNumber" class="input_group" onblur="verify();" value="${task.documentNumber}"/> 
              <span class="add-on">i</span>
              <div id="sps" class="cue">${ERR_documentNumber}</div>
            </div>
          </li>
		</ul>
      </div>
      <div>
        <h2 class="count_flow"><i>2</i>需求明细调整</h2>
        <div class="ul_list">
          <div class="content table_box over_scroll" id="content">
            <table id="table" class="table table-bordered table_input" style="border-color: rgb(221, 221, 221); color: rgb(51, 51, 51); width: 1600px; font-size: medium; max-width: 10000px; margin: 0px;">
              <thead>
			    <tr class="space_nowrap">
			      <th class="info w50">序号</th>
			      <th class="info w80">需求部门</th>
			      <th class="info w80">物资名称</th>
			      <th class="info w80">规格型号</th>
			      <th class="info w80">质量技术标准</th>
			      <th class="info w80">计量<br>单位</th>
			      <th class="info w80">采购<br>数量</th>
			      <th class="info w80">单价<br>（元）</th>
			      <th class="info w80">预算金额<br>（万元）</th>
			      <th class="info w80">交货期限</th>
			      <th class="info w100">采购方式</th>
			      <th class="info w100">供应商名称</th>
			      <th class="info w80">是否申请<br>办理免税</th>
			      <th class="info w80">物资用途<br>（进口）</th>
			      <th class="info w80">使用单位<br>（进口）</th>
			      <th class="info w160">备注</th>
			    </tr>
			  </thead>
			  <c:forEach items="${lists}" var="obj" varStatus="vs">
			    <tr class="pointer">
			      <td class="tc w50">${obj.seq}  <input style="border: 0px;" type="hidden" name="list[${vs.index }].id" value="${obj.id }"></td>
			      <td>
			      <c:if test="${obj.department == orgnization.id}">
			      <input class="w80" name="list[${vs.index }].department" value="${orgnization.name }" type="text">
			      </c:if>
			      </td>
			      <td class="tc"><input type="text" name="list[${vs.index }].goodsName" value="${obj.goodsName }" class="w80"></td>
			      <td class="tc"><input type="text" name="list[${vs.index }].stand" value="${obj.stand }" class="w80"></td>
			      <td class="tc"><input type="text" name="list[${vs.index }].qualitStand" value="${obj.qualitStand }" class="w80"></td>
			      <td class="tc"><input type="text" name="list[${vs.index }].item" value="${obj.item }" class="w80 tc"></td>
			      <td class="tc">
			        <c:if test="${obj.purchaseCount!=null}">
			          <input   type="hidden" name="ss"   value="${obj.id }">
			          <input maxlength="11" class="w80 tc" id="purchaseCount" onblur="sum2(this);"  onkeyup="this.value=this.value.replace(/\D/g,'')"  onafterpaste="this.value=this.value.replace(/\D/g,'')" name="list[${vs.index }].purchaseCount"  type="text" value="${obj.purchaseCount}"/>
			          <input type="hidden" name="ss"   value="${obj.parentId }">
			        </c:if>
			        <c:if test="${obj.purchaseCount==null }">
			          <input  class="border0 w80 tc"  disabled="disabled"  type="text" name="list[${vs.index }].purchaseCount" onblur="checks(this)"  value="${obj.purchaseCount }"  type="text">
			        </c:if>
			      </td>
			      <td class="tc">
			        <c:if test="${obj.price!=null}">
			          <input   type="hidden" name="ss"   value="${obj.id }">
			          <input maxlength="11" id="price" class="w80 tr"   name="list[${vs.index }].price" type="text"  onblur="sum1(this);"  value="${obj.price}"/>
			          <input type="hidden" name="ss"   value="${obj.parentId }">
			        </c:if>
			        <c:if test="${obj.price==null}">
			          <input class="border0 w80 tr"  readonly="readonly"  type="text" name="list[${vs.index }].price" value="${obj.price }">
			        </c:if>
			      </td>
			      <td class="tc">
			        <input   type="hidden" name="ss"   value="${obj.id }">
			        <input maxlength="11" class="w80 tr"  id="budget" type="text" name="list[${vs.index }].budget" style="text-align:center;border-style:none" readonly="readonly"  value="${obj.budget}"/>
			        <input type="hidden" name="ss"   value="${obj.parentId }">
			      </td>
			      <td class="tc"><input type="text" class="w80" name="list[${vs.index }].deliverDate" value="${obj.deliverDate }"></td>
			      <td class="tc">
			         <c:if test="${null!=obj.purchaseType && obj.purchaseType != ''}">
			            <select name="list[${vs.index }].purchaseType" onchange="sel(this);" style="width:100px" id="select">
			            <c:forEach items="${kind}" var="kind" >
			                           <option value="${kind.id}" <c:if test="${kind.id == obj.purchaseType}">selected="selected" </c:if>> ${kind.name}</option>
			                        </c:forEach>
			            </select> 
			          </c:if> 
			      </td>
			      <td class="tc"><input type="text" class="w100" name="list[${vs.index }].supplier" value="${obj.supplier }"></td>
			      <td class="tc"><input type="text" class="w80" name="list[${vs.index }].isFreeTax" value="${obj.isFreeTax }"></td>
			      <td class="tc"><input type="text" class="w80" name="list[${vs.index }].goodsUse" value="${obj.goodsUse }"></td>
			      <td class="tc"> <input type="text" class="w80" name="list[${vs.index }].useUnit" value="${obj.useUnit }"></td>
			      <td class="tc"> <input type="text" class="w160" name="list[${vs.index }].memo" value="${obj.memo }">
			         <input type="hidden" name="list[${vs.index }].seq" value="${obj.seq }">
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
         </div>
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
         <u:upload id="upload_id" businessId="${task.id}" multiple="true" typeId="${dataId}" exts="png,jpeg,jpg,bmp,gif" sysKey="2"/>
         <u:show showId="upload_id" businessId="${task.id}" sysKey="2" typeId="${dataId}"/>
            </span>
          </li>
        </ul>
        </div>
		
		 <div class="tc mt20 col-md-12 col-md-12 col-xs-12">
		   <br>
		   <a class="btn btn-windows save" onclick="delTask('${task.id}');">确认</a>
		   <input class="btn btn-windows cancel" value="取消" type="button" onclick="cancel();">
		 </div>
       </div> 
     </sf:form>
  </div>
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>
    <title>原、辅材料工艺定额消耗明细表</title>

    <script type="text/javascript">
      /** 全选全不选 */
      function selectAll() {
        var checklist = document.getElementsByName("chkItem");
        var checkAll = document.getElementById("checkAll");
        if(checkAll.checked) {
          for(var i = 0; i < checklist.length; i++) {
            checklist[i].checked = true;
          }
        } else {
          for(var j = 0; j < checklist.length; j++) {
            checklist[j].checked = false;
          }
        }
      }
      //判断数组中是否含有obj
      function contains(arr, obj) {  
    	    var i = arr.length;  
    	    while (i--) {  
    	        if (arr[i] === obj) {  
    	            return true;  
    	        }  
    	    }  
    	    return false;  
    	} 
     var pojo=[];
	function update(obj){
		var tr=$(obj).parent().parent().attr("id");
		if(pojo.length>0){
			if(contains(pojo,tr)==false){
				pojo.push(tr);
			}
		}else{
			pojo.push(tr);
		}
	}
      function edit() {
        var proId = $("#proId").val();
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        if(id.length == 1) {
          window.location.href = "${pageContext.request.contextPath }/accessoriesCon/edit.do?id=" + id + "&proId=" + proId;
        } else if(id.length > 1) {
          layer.alert("只能选择一个", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        } else {
          layer.alert("请选择修改的内容", {
            offset: ['222px', '390px'],
            shade: 0.01
          });
        }
      }
      function html(num,index,type){
    	  var html="<tr id='add'>"
      	+"<td class='tc '><input   type='checkbox' name='chkItem' /></td>"
      	+"<td class='tc'>"+index+"</td>";
      	if(type==1){
      		html+="<td class='w80'>辅助材料<input type='hidden' name='listAcc["+num+"].productNature'  value='1'><input type='hidden' name='listAcc["+num+"].contractProduct.id'  value='${proId}'></td>";
      	}else{
      		html+="<td class='w80'>主要材料<input type='hidden' name='listAcc["+num+"].productNature'  value='0'><input type='hidden' name='listAcc["+num+"].contractProduct.id'  value='${proId}'></td>";
      	}
      	html+="<td><input type='text' class='m0 p0  border0 w80' name='listAcc["+num+"].stuffName'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listAcc["+num+"].norm'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listAcc["+num+"].paperCode'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listAcc["+num+"].workAmout'  value='' onblur='workWeightTotals(this,\"3\");' ></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listAcc["+num+"].workWeight' onblur='workWeightTotals(this,\"1\");'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listAcc["+num+"].workWeightTotal' readonly='readonly'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listAcc["+num+"].workPrice'  onblur='workWeightTotals(this,\"2\");' value='' ></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listAcc["+num+"].workMoney' readonly='readonly'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listAcc["+num+"].supplyUnit'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listAcc["+num+"].remark'  value=''></td>"
      	+"</tr>";
      	return html;
      }
      var num=100;
      function add() {
    	var index;
        var proId = $("#proId").val();
        /* window.location.href = "${pageContext.request.contextPath }/accessoriesCon/add.html?proId=" + proId; */
        var check0=document.getElementById("check0");
        var check1=document.getElementById("check1");
        var overTr;
        if(check0.checked==false&&check1.checked==false){
        	layer.alert("请选择勾选父节点添加");
        }else if(check0.checked){
        	var tr0=$("#tr0").nextAll();
        	for(var i=0;i<tr0.length;i++){
        		if($(tr0[i]).attr("id")=="tr1"){
        			if(i==0){
        				overTr=$("#tr0");
        				index=0;
        			}else{
        				overTr=tr0[i-1];
        				var tr=$(tr0[i-1]).children()[1];
        				index=parseInt($(tr).text());
        			}
        		}
        	}
        	index++;
        	$(overTr).after(html(num,index,0));
        	num++;
        }else if(check1.checked){
        	var tr1=$("#tr1").nextAll();
        	if(tr1.length<=1){
        		overTr=$("#tr1");
        		index=0;
        	}else{
        		overTr=tr1[tr1.length-2];
        		var tr=$(tr1[tr1.length-2]).children()[1];
				index=parseInt($(tr).text());
        	}
        	index++;
        	$(overTr).after(html(num,index,1));
        	num++;
        }
      }
      function save(){
    	  var addTr=$("tr[id='add']");    	  
    	  if(addTr.length>0||pojo.length>0){
    	  /* for(var i=0;i<addTr.length;i++){
    		 //$(addTr[i]).clone(true).appendTo($("#saveObject"));
    	  } */
    	  $("#table1").clone(true).appendTo($("#saveObject"));
    	  $.ajax({
	          url: "${pageContext.request.contextPath }/accessoriesCon/save.do",
	          type: "POST",
	          dataType: "json",
	          data: $("#saveObject").serialize(),
	          success: function(data) {
	        	  if(data=="ok"){
	        		  layer.confirm("保存成功", {
	        	            title: '提示',
	        	            offset: ['222px', '360px'],
	        	            shade: 0.01
	        	          }, function(index) {
	        	            layer.close(index);
	        	            location.href='${pageContext.request.contextPath }/accessoriesCon/select.do?proId=${proId}';
	        	          });
	        	  }
	        	
	          }   
	    });
       }else{
    	   layer.alert("没有要保存的信息", {
               offset: ['222px', '390px'],
               shade: 0.01
             });
       }
      }
      function del() {
        var proId = $("#proId").val();
        var ids = [];
        var objNull=[];
        $('input[name="chkItem"]:checked').each(function() {
         if($(this).val()=="on"){
        	 objNull.push(this);
         }else{
        	 ids.push($(this).val());
         }
        });
        if(objNull.length>0&&ids.length<1){
        	layer.confirm('您确定要删除吗?', {
                title: '提示',
                offset: ['222px', '360px'],
                shade: 0.01
              }, function(index) {
                layer.close(index);
                for(var i=0;i<objNull.length;i++){
                	$(objNull[i]).parent().parent().remove();
                }
              });
        }else if(objNull.length>0&&ids.length>0){
        	layer.confirm('您确定要删除吗?', {
                title: '提示',
                offset: ['222px', '360px'],
                shade: 0.01
              }, function(index) {
                layer.close(index);
                for(var i=0;i<objNull.length;i++){
                	$(objNull[i]).parent().parent().remove();
                }
                window.location.href = "${pageContext.request.contextPath }/accessoriesCon/delete.html?proId=" + proId + "&ids=" + ids;
              });
        }else if(objNull.length<1&&ids.length>0){
        	layer.confirm('您确定要删除吗?', {
                title: '提示',
                offset: ['222px', '360px'],
                shade: 0.01
              }, function(index) {
                layer.close(index);
                window.location.href = "${pageContext.request.contextPath }/accessoriesCon/delete.html?proId=" + proId + "&ids=" + ids;
              });
        }else{
        	layer.alert("请选择要删除的信息", {
                offset: ['222px', '390px'],
                shade: 0.01
              });
        }
      }

      function workWeightTotals(obj,type){
    	  if(type==1){
    		  var num=0;
        	  var weight=0;
        	  if($(obj).parent().prev().children(":first").val()!=""){
        		 num=parseFloat($(obj).parent().prev().children(":first").val());
        	  }
        	  if($(obj).val()!=""){
        		 weight =parseFloat($(obj).val());
        	  }
        	  $(obj).parent().next().children(":first").val((num*weight).toFixed(2));
    	  }else if(type==2){
    		  var num=0;
        	  var price=0;
        	  if($(obj).parent().prev().prev().prev().children(":first").val()!=""){
        		num=parseFloat($(obj).parent().prev().prev().prev().children(":first").val()); 
        	  }
        	  if($(obj).val()!=""){
        		 price=parseFloat($(obj).val());
        	  }
        	  $(obj).parent().next().children(":first").val((num*price).toFixed(2));
    	  }else if(type==3){
    		  var weight=0;
    		  var price=0;
    		  var num=0;
    		  if($(obj).parent().next().children(":first").val()!=""){
    			  weight=parseFloat($(obj).parent().next().children(":first").val());
    		  }
    		  if($(obj).parent().next().next().next().children(":first").val()!=""){
    			  price=parseFloat($(obj).parent().next().next().next().children(":first").val());
    		  }
    		  if($(obj).val()!=""){
    			  num=parseFloat($(obj).val());
    		  }
    		  $(obj).parent().next().next().children(":first").val((num*weight).toFixed(2));
    		  $(obj).parent().next().next().next().next().children(":first").val((num*price).toFixed(2))
    		  
    		  
    	  }
    	  
      }
      function onStep() {
        var productId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath }/offer/selectProductInfo.do?productId=" + productId;
      }

      function nextStep() {
        var proId = $("#proId").val();
        /* var total = $("#total").val();
        if(total == "NaN") {
          total = 0;
        } */
        window.location.href = "${pageContext.request.contextPath }/outproductCon/select.do?proId=" + proId;
      }

      $(document).ready(function() {
        /* var totalRow = 0;
        var totalRow2 = 0;
        $('#table1 tr:not(:last)').each(function() {
          $(this).find('td:eq(10)').each(function() {
            totalRow += parseFloat($(this).text());
          });
          $(this).find('td:eq(15)').each(function() {
            totalRow2 += parseFloat($(this).text());
          });
        }); */
        var workWeightTotal0=document.getElementsByName("workWeightTotal0");
        var workMoney0=document.getElementsByName("workMoney0");
        var workWeightTotal1=document.getElementsByName("workWeightTotal1");
        var workMoney1=document.getElementsByName("workMoney1");
        var workWeightTotal=0;
        var workMoney=0;
        for(var i=0;i<workWeightTotal0.length;i++){
        	if($(workWeightTotal0[i].firstChild).val()!=""){
        		workWeightTotal+=parseFloat($(workWeightTotal0[i].firstChild).val());
        	}
        	if($(workMoney0[i].firstChild).val()!=""){
        		workMoney+=parseFloat($(workMoney0[i].firstChild).val());
        	}
        }
        $("#workWeightTotal0").text(workWeightTotal.toFixed(2));
        $("#workMoney0").text(workMoney.toFixed(2));
        workWeightTotal=0;
        workMoney=0;
        for(var i=0;i<workWeightTotal1.length;i++){
        	if($(workWeightTotal1[i].firstChild).val()!=""){
        		workWeightTotal+=parseFloat($(workWeightTotal1[i].firstChild).val());
        	}
        	if($(workMoney1[i].firstChild).val()!=""){
        		workMoney+=parseFloat($(workMoney1[i].firstChild).val());
        	}
        }
        $("#workWeightTotal1").text(workWeightTotal.toFixed(2));
        $("#workMoney1").text(workMoney.toFixed(2));
        $('#total').text((parseFloat($("#workMoney1").text())+parseFloat($("#workMoney0").text())).toFixed(2));
      });
    </script>

  </head>

  <body>

    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)"> 首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">供应商报价</a>
          </li>
          <li>
            <a href="javascript:void(0)">产品报价</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>原、辅材料工艺定额消耗明细表</h2>
      </div>

      <div class="col-md-8 mt10 pl20 ml5">
        <button class="btn btn-windows add" type="button" onclick="add()">添加</button>
        <!-- <button class="btn btn-windows edit" type="button" onclick="edit()">修改</button> -->
        <button class="btn btn-windows delete" type="button" onclick="del()">删除</button>
        <button class="btn btn-windows save" type="button" onclick="save()">保存</button>
        
      </div>

    </div>

    <input type="hidden" id="proId" name="contractProduct.id" class="w230 mb0" value="${proId }" readonly>
      <form action="" method="post" id="saveObject" style="display: none;"></form>
    <div class="container margin-top-5">
      <div class="container padding-left-25 padding-right-25">
        <table id="table1" class="table table-bordered table-condensed">
            <tr>
              <th rowspan="2" class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th rowspan="2" class="info w50">序号</th>
              <th rowspan="2" class="info">材料性质</th>
              <th rowspan="2" class="info">材料名称</th>
              <th rowspan="2" class="info">规格型号</th>
              <th rowspan="2" class="info">图纸位置号(代号)</th>
              <th colspan="5" class="info">所属加工生产装配工艺消耗定额（数量、质量、含税金额）</th>
              <!-- <th colspan="5" class="info">消耗定额审核核准数（含税金额）</th> -->
              <th rowspan="2" class="info">供货单位</th>
              <th rowspan="2" class="info">备 注</th>
            </tr>
            <tr>
              <th class="info">数量</th>
              <th class="info">单件重</th>
              <th class="info">重量小计</th>
              <th class="info">单价</th>
              <th class="info">金额</th>
             <!--  <th class="info">单位</th>
              <th class="info">单件重</th>
              <th class="info">重量小计</th>
              <th class="info">单价</th>
              <th class="info">金额</th> -->
            </tr>
          <tr id="tr0">
             <td class="tc"><input  type="checkbox" id="check0" /></td>
             <td class="tc">一</td>
             <td class="tc">主要材料</td>
             <td></td>
             <td></td>
             <td></td>
             <td class="tc"></td>
             <td class="tc"></td>
             <td class="tr" id="workWeightTotal0"></td>
             <td class="tc"></td>
             <td class="tr" id="workMoney0"></td>
           <!--   <td class="tc"></td>
             <td class="tc"></td>
             <td class="tc"></td>
             <td class="tc"></td>
             <td class="tc"></td> -->
             <td></td>
             <td></td>
          </tr>
            <c:forEach items="${list0}" var="acc" varStatus="vs">
              <tr id="${acc.id}">
                <td class="tc"><input  type="checkbox" name="chkItem" value="${acc.id }" /></td>
                <td class="tc">${vs.index+1 }</td>
                <td class="tc">
                <input type='hidden' class='m0 p0  border0 w80' name='listAcc[${vs.index}].productNature' value='${acc.productNature}'>
                <input type='hidden' class='m0 p0  border0 w80' name='listAcc[${vs.index}].id'  value='${acc.id }'>
                  <c:if test="${acc.productNature=='0' }">
                    主要材料
                  </c:if>
                  <c:if test="${acc.productNature=='1' }">
                    辅助材料
                  </c:if>
                </td>
                <td><input type='text' class='m0 p0  border0 w80' name='listAcc[${vs.index}].stuffName'  value='${acc.stuffName }'></td>
                <td><input type='text' class='m0 p0  border0 w80' name='listAcc[${vs.index}].norm' value='${acc.norm }' onchange="update(this);"></td>
                <td><input type='text' class='m0 p0  border0 w80'  name='listAcc[${vs.index}].paperCode' value='${acc.paperCode }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50 tr' name='listAcc[${vs.index}].workAmout'  value='${acc.workAmout }' onblur='workWeightTotals(this,"3");' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50 tr'  name='listAcc[${vs.index}].workWeight' value='${acc.workWeight }' onblur='workWeightTotals(this,"1");' onchange="update(this);"></td>
                <td class="tc" name="workWeightTotal0"><input type='text' class='m0 p0  border0 w80 tr' name='listAcc[${vs.index}].workWeightTotal' value='${acc.workWeightTotal }' readonly="readonly" onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50 tr' name='listAcc[${vs.index}].workPrice' value='${acc.workPrice }' onblur='workWeightTotals(this,"2");' onchange="update(this);"></td>
                <td class="tc" name="workMoney0"><input type='text' class='m0 p0  border0 w80 tr' name='listAcc[${vs.index}].workMoney'  value='${acc.workMoney }' readonly="readonly" onchange="update(this);"></td>
                <%-- <td class="tc">${acc.consumeAmout }</td>
                <td class="tc">${acc.consumeWeight }</td>
                <td class="tc">${acc.consumeWeightTotal }</td>
                <td class="tc">${acc.consumePrice }</td>
                <td class="tc">${acc.consumeMoney }</td> --%>
                <td><input type='text' class='m0 p0  border0 w100' name='listAcc[${vs.index}].supplyUnit'  value='${acc.supplyUnit }' onchange="update(this);"></td>
                <td><input type='text' class='m0 p0  border0 w100' name='listAcc[${vs.index}].remark' value='${acc.remark }' onchange="update(this);"></td>
              </tr>
            </c:forEach>
            <tr id="tr1">
             <td class="tc"><input  type="checkbox" id="check1" /></td>
             <td class="tc">二</td>
             <td class="tc">辅助材料</td>
             <td></td>
             <td></td>
             <td></td>
             <td class="tc"></td>
             <td class="tc"></td>
             <td class="tr" id="workWeightTotal1"></td>
             <td class="tc"></td>
             <td class="tr" id="workMoney1"></td>
            <!--  <td class="tc"></td>
             <td class="tc"></td>
             <td class="tc"></td>
             <td class="tc"></td>
             <td class="tc"></td> -->
             <td></td>
             <td></td>
          </tr>
           <c:forEach items="${list1}" var="acc" varStatus="vs">
              <tr id="${acc.id}">
                <td class="tc"><input  type="checkbox" name="chkItem" value="${acc.id }" /></td>
                <td class="tc">${vs.index+1 }</td>
                <td class="tc">
                <input type='hidden' class='m0 p0  border0 w80' name='listAcc[${fn:length(list0)+vs.index+1}].productNature' value='${acc.productNature}'>
                <input type='hidden' class='m0 p0  border0 w80' name='listAcc[${fn:length(list0)+vs.index+1}].id'  value='${acc.id }'>
                  <c:if test="${acc.productNature=='0' }">
                    主要材料
                  </c:if>
                  <c:if test="${acc.productNature=='1' }">
                    辅助材料
                  </c:if>
                </td>
                <td><input type='text' class='m0 p0  border0 w80' name='listAcc[${fn:length(list0)+vs.index+1}].stuffName'  value='${acc.stuffName }' onchange="update(this);"></td>
                <td><input type='text' class='m0 p0  border0 w80' name='listAcc[${fn:length(list0)+vs.index+1}].norm' value='${acc.norm }' onchange="update(this);"></td>
                <td><input type='text' class='m0 p0  border0 w80'  name='listAcc[${fn:length(list0)+vs.index+1}].paperCode' value='${acc.paperCode }' onchange="update(this);"></td>
                <td ><input type='text' class='m0 p0  border0 w50 tr' name='listAcc[${fn:length(list0)+vs.index+1}].workAmout'  value='${acc.workAmout }' onchange="update(this);"></td>
                <td ><input type='text' class='m0 p0  border0 w50 tr'  name='listAcc[${fn:length(list0)+vs.index+1}].workWeight'  value='${acc.workWeight }' onblur='workWeightTotals(this);' onchange="update(this);"></td>
                <td name="workWeightTotal1"><input type='text' class='m0 p0  border0 w80 tr' name='listAcc[${fn:length(list0)+vs.index+1}].workWeightTotal' value='${acc.workWeightTotal }' readonly="readonly" onchange="update(this);"></td>
                <td ><input type='text' class='m0 p0  border0 w50 tr' name='listAcc[${fn:length(list0)+vs.index+1}].workPrice' value='${acc.workPrice }' onblur='workMoneys(this);' onchange="update(this);"></td>
                <td class="tr" name="workMoney1"><input type='text' class='m0 p0  border0 w80 tr' name='listAcc[${fn:length(list0)+vs.index+1}].workMoney'  value='${acc.workMoney }' readonly="readonly" onchange="update(this);"></td>
                <%-- <td class="tc">${acc.consumeAmout }</td>
                <td class="tc">${acc.consumeWeight }</td>
                <td class="tc">${acc.consumeWeightTotal }</td>
                <td class="tc">${acc.consumePrice }</td>
                <td class="tc">${acc.consumeMoney }</td> --%>
                <td><input type='text' class='m0 p0  border0 w100' name='listAcc[${fn:length(list0)+vs.index+1}].supplyUnit'  value='${acc.supplyUnit }' onchange="update(this);"></td>
                <td><input type='text' class='m0 p0  border0 w100' name='listAcc[${fn:length(list0)+vs.index+1}].remark' value='${acc.remark }' onchange="update(this);"></td>
              </tr>
            </c:forEach>
            <tr id="totalRow">
              <td colspan="6" class="tc">总计：</td>
              <td colspan="4"></td>
              <td class="tc" id="total"></td>
             <!--  <td colspan="4"></td>
              <td class="tc" id="total2"></td> -->
              <td colspan="2"></td>
            </tr>
        </table>
      </div>

      <div class="col-md-12 col-xs-12 col-sm-12 mt20 tc">
        <button class="btn" type="button" onclick="onStep()">上一步</button>
        <button class="btn " type="button" onclick="nextStep()">下一步</button>
      </div>

    </div>

  </body>

</html>
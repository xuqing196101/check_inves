<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>外协加工件消耗定额明细</title>

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

      
      function edit() {
        var proId = $("#proId").val();
        var id = [];
        $('input[name="chkItem"]:checked').each(function() {
          id.push($(this).val());
        });
        if(id.length == 1) {
          window.location.href = "${pageContext.request.contextPath}/outsourcingCon/edit.do?id=" + id + "&proId=" + proId;
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
      function html(num,index,type){
    	  var html="<tr id='add'>"
      	+"<td class='tc '><input   type='checkbox' name='chkItem' /></td>"
      	+"<td class='tc'>"+index+"</td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listOutSou["+num+"].outsourcingName'  value=''><input type='hidden' name='listOutSou["+num+"].contractProduct.id'  value='${proId}'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listOutSou["+num+"].norm'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listOutSou["+num+"].paperCode'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listOutSou["+num+"].workAmout' onkeyup='if(isNaN(value))execCommand(\"undo\")' value='' onblur='workWeightTotals(this,\"3\");'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listOutSou["+num+"].workWeight' onkeyup='if(isNaN(value))execCommand(\"undo\")' value='' onblur='workWeightTotals(this,\"1\");'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listOutSou["+num+"].workWeightTotal'  value='' readonly='readonly'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50 tr' name='listOutSou["+num+"].workPrice' onkeyup='if(isNaN(value))execCommand(\"undo\")' value='' onblur='workWeightTotals(this,\"2\");'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80 tr' name='listOutSou["+num+"].workMoney'  value='' readonly='readonly'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listOutSou["+num+"].supplyUnit'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listOutSou["+num+"].remark'  value=''></td>"
      	+"</tr>";
      	return html;
      }
      var num=100;
      function add() {
    	var index;
        var proId = $("#proId").val();
        /* window.location.href = "${pageContext.request.contextPath}/outsourcingCon/add.html?proId=" + proId; */
        var overTr;
        var tr0=$("#tr0").nextAll();
        if(tr0.length<=2){
    		overTr=$(tr0[0]);
    		index=0;
    	}else{
    		overTr=tr0[tr0.length-2];
    		var tr=$(tr0[tr0.length-2]).children()[1];
			index=parseInt($(tr).text());
    	}
    	index++;
    	$(overTr).after(html(num,index));
    	num++;
      }
      function save(){
    	 /*  var tr=$("input[name='chkItem']:checked");
    	  if(tr.length>0){
    	  for(var i=0;i<tr.length;i++){
    		 $(tr[i]).parent().parent().clone(true).appendTo($("#saveObject"));
    	  } */
    	  var addTr=$("tr[id='add']");
    	  if(addTr.length>0||pojo.length>0){
    	  $("#table1").clone(true).appendTo($("#saveObject"));
    	  $.ajax({
	          url: "${pageContext.request.contextPath }/outsourcingCon/save.do",
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
	        	            location.href='${pageContext.request.contextPath }/outsourcingCon/select.do?proId=${proId}';
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
                window.location.href = "${pageContext.request.contextPath }/outsourcingCon/delete.html?proId=" + proId + "&ids=" + ids;
              });
        }else if(objNull.length<1&&ids.length>0){
        	layer.confirm('您确定要删除吗?', {
                title: '提示',
                offset: ['222px', '360px'],
                shade: 0.01
              }, function(index) {
                layer.close(index);
                window.location.href = "${pageContext.request.contextPath }/outsourcingCon/delete.html?proId=" + proId + "&ids=" + ids;
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
         	var num=0;
	           	var price=0;
	           	var weight=0;
	           	if($(obj).val()!=""){
	           		num =parseFloat($(obj).val());
	             }
	           	if($(obj).parent().next().children(":first").val()!=""){
	           		weight=parseFloat($(obj).parent().next().children(":first").val());
	           	}
	           	if($(obj).parent().next().next().next().children(":first").val()){
	           		price=parseFloat($(obj).parent().next().next().next().children(":first").val());
	           	}
	           	$(obj).parent().next().next().children(":first").val((num*weight).toFixed(2));
	           	$(obj).parent().next().next().next().next().children(":first").val((num*price).toFixed(2));
         }
      }
      function onStep() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/outproductCon/select.do?proId=" + proId;
      }

      function nextStep() {
        var proId = $("#proId").val();
        var total = $("#total").val();
        /* if(total == "NaN") {
          total = 0;
        } */
        window.location.href = "${pageContext.request.contextPath}/specialCost/select.do?proId=" + proId;
      }

      $(document).ready(function() {
        var totalRow = 0;
        var totalRow2 = 0;
        $('#table1 tr:not(:last)').each(function() {
          $(this).find('td:eq(9)').each(function() {
        	  if($(this.firstChild).val()!=""){
        		  totalRow += parseFloat($(this.firstChild).val());
        	  }
          });
          /* $(this).find('td:eq(12)').each(function() {
            totalRow2 += parseFloat($(this).text());
          }); */
        });
        $('#total').text(totalRow.toFixed(2));
       /*  $('#total2').val(totalRow2); */
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
            <a href="javascript:void(0)">外协加工件消耗定额明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>外协加工件消耗定额明细</h2>
      </div>

      <div class="col-md-8 mt10 ml10">
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
            <tr id="tr0">
              <th rowspan="2" class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th rowspan="2" class="info">序号</th>
              <th rowspan="2" class="info">外协加工工件名称</th>
              <th rowspan="2" class="info">规格型号</th>
              <th rowspan="2" class="info">图纸位置号(代号)</th>
              <th colspan="5" class="info">所属加工生产装配工艺消耗定额（数量、质量、含税金额）</th>
              <!-- <th colspan="3" class="info">消耗定额审核核准数（含税金额）</th> -->
              <th rowspan="2" class="info">供货单位</th>
              <th rowspan="2" class="info">备 注</th>
            </tr>
            <tr>
              <th class="info">数量</th>
              <th class="info">单件重</th>
              <th class="info">重量小计</th>
              <th class="info">单价</th>
             <!--  <th class="info">金额</th>
              <th class="info">单位</th>
              <th class="info">单价</th> -->
              <th class="info">金额</th>
            </tr>
            <c:forEach items="${list}" var="out" varStatus="vs">
              <tr id="${out.id}">
                <td class="tc"><input  type="checkbox" name="chkItem" value="${out.id }" /></td>
                <td class="tc">${vs.index+1 }</td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listOutSou[${vs.index}].outsourcingName'  value='${out.outsourcingName }' onchange="update(this);"><input type='hidden' class='m0 p0  border0 w80' name='listOutSou[${vs.index}].id'  value='${out.id }'></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listOutSou[${vs.index}].norm'  value='${out.norm }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listOutSou[${vs.index}].paperCode'  value='${out.paperCode }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50' name='listOutSou[${vs.index}].workAmout' onkeyup="if(isNaN(value))execCommand('undo')" value='${out.workAmout }' onchange="update(this);" onblur='workWeightTotals(this,"3");'></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50' name='listOutSou[${vs.index}].workWeight' onkeyup="if(isNaN(value))execCommand('undo')" value='${out.workWeight }' onchange="update(this);" onblur='workWeightTotals(this,"1");'></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listOutSou[${vs.index}].workWeightTotal'  value='${out.workWeightTotal }' onchange="update(this);" readonly="readonly"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50 tr' name='listOutSou[${vs.index}].workPrice' onkeyup="if(isNaN(value))execCommand('undo')" value='${out.workPrice }' onchange="update(this);" onblur='workWeightTotals(this,"2");'></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80 tr' name='listOutSou[${vs.index}].workMoney'  value='${out.workMoney }' onchange="update(this);" readonly="readonly"></td>
               <%--  <td class="tc">${out.consumeAmout }</td>
                <td class="tc">${out.consumePrice }</td>
                <td class="tc">${out.consumeMoney }</td> --%>
                <td class="tc"><input type='text' class='m0 p0  border0 w100' name='listOutSou[${vs.index}].supplyUnit'  value='${out.supplyUnit }' onchange="update(this);"></td>
                <td><input type='text' class='m0 p0  border0 w100' name='listOutSou[${vs.index}].remark'  value='${out.remark }' onchange="update(this);"></td>
              </tr>
            </c:forEach>
            <tr id="totalRow">
              <td class="tc" colspan="5">总计：</td>
              <td colspan="4"></td>
              <td class="tc" id="total"></td>
             <!--  <td colspan="2"></td>
              <td class="tc"><input type="text" id="total2" class="border0 tc w50 m0" readonly="readonly"></td> -->
              <td colspan="2"></td>
            </tr>
        </table>
      </div>

      <div class="col-md-12 col-xs-12 col-sm-12 mt20 tc">
        <button class="btn" type="button" onclick="onStep()">上一步</button>
        <button class="btn" type="button" onclick="nextStep()">下一步</button>
      </div>

    </div>

  </body>

</html>
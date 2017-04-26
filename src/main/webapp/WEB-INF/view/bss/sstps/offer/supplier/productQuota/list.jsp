<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>产品工时定额明细</title>

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
          window.location.href = "${pageContext.request.contextPath}/productQuota/edit.do?id=" + id + "&proId=" + proId;
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
      function html(num,index){
    	  var html="<tr id='add'>"
      	+"<td class='tc '><input   type='checkbox' name='chkItem' /></td>"
      	+"<td class='tc'>"+index+"</td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listPro["+num+"].partsName'  value=''><input type='hidden' name='listPro["+num+"].contractProduct.id'  value='${proId}'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listPro["+num+"].partsDrawingCode'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listPro["+num+"].processName'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listPro["+num+"].offer' onkeyup='if(isNaN(value))execCommand(\"undo\")' value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listPro["+num+"].processingOffer' onkeyup='if(isNaN(value))execCommand(\"undo\")' value='' ></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listPro["+num+"].assemblyOffer' onkeyup='if(isNaN(value))execCommand(\"undo\")' value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listPro["+num+"].debuggingOffer' onkeyup='if(isNaN(value))execCommand(\"undo\")' value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listPro["+num+"].testOffer' onkeyup='if(isNaN(value))execCommand(\"undo\")' value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listPro["+num+"].otherOffer' onkeyup='if(isNaN(value))execCommand(\"undo\")' value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listPro["+num+"].subtotalOffer' onkeyup='if(isNaN(value))execCommand(\"undo\")' value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listPro["+num+"].measuringUnit'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listPro["+num+"].assortOffer' onkeyup='if(isNaN(value))execCommand(\"undo\")' value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listPro["+num+"].remark'  value=''></td>"
      	+"</tr>";
      	return html;
      }
      var num=100;
      var index;
      function add() {
        var proId = $("#proId").val();
        var overTr;
        var tr0=$("#tr0").nextAll();
        if(tr0.length<=3){
    		overTr=$(tr0[1]);
    		index=0;
    	}else{
    		overTr=tr0[tr0.length-2];
    		var tr=$(tr0[tr0.length-2]).children()[1];
			index=parseInt($(tr).text());
    	}
    	index++;
    	$(overTr).after(html(num,index));
    	num++;
        
        /* window.location.href = "${pageContext.request.contextPath}/productQuota/add.html?proId=" + proId; */
      }
      function save(){
    	  /* var tr=$("input[name='chkItem']:checked");
    	  if(tr.length>0){
    	  for(var i=0;i<tr.length;i++){
    		 $(tr[i]).parent().parent().clone(true).appendTo($("#saveObject"));
    	  } */
    	  var addTr=$("tr[id='add']");
    	  if(addTr.length>0||pojo.length>0){
    	  $("#table1").clone(true).appendTo($("#saveObject"));
    	  $.ajax({
	          url: "${pageContext.request.contextPath }/productQuota/save.do",
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
	        	            location.href='${pageContext.request.contextPath }/productQuota/select.do?proId=${proId}';
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
        var flg=false;
        var po=[];
        $('input[name="chkItem"]:checked').each(function() {
        	if($(this).val()=="on"){
	           	/*  $(this).parent().parent().remove(); */
	           	 flg=true;
	           	 po.push(this);
          }else{
          	ids.push($(this).val());
          }
        });
        
        if(flg==true){
        	if(ids.length>0){
        		layer.confirm('您确定要删除吗?', {
                    title: '提示',
                    offset: ['222px', '360px'],
                    shade: 0.01
                  }, function(index) {
                    layer.close(index);
                    window.location.href = "${pageContext.request.contextPath}/productQuota/delete.html?proId=" + proId + "&ids=" + ids;
                  });
        	}else{
        		layer.confirm('您确定要删除吗?', {
                    title: '提示',
                    offset: ['222px', '360px'],
                    shade: 0.01
                  }, function(index) {
                    layer.close(index);
                    for(var i=0;i<po.length;i++){
                  	  $(po[i]).parent().parent().remove();
                    }
                  });
        	}
        }else{
        	if(ids.length > 0) {
                layer.confirm('您确定要删除吗?', {
                  title: '提示',
                  offset: ['222px', '360px'],
                  shade: 0.01
                }, function(index) {
                  layer.close(index);
                  window.location.href = "${pageContext.request.contextPath}/productQuota/delete.html?proId=" + proId + "&ids=" + ids;
                });
              } else {
      	          layer.alert("请选择要删除的信息", {
      	            offset: ['222px', '390px'],
      	            shade: 0.01
      	          });
              }
        }
      }
      function totalOffer1(obj){
    	    var offer1=0;
			var offer2=0;
			var offer3=0;
			var offer4=0;
			var offer5=0;
			var offer6=0;
    	  if($(obj).val()!=""){
				offer1=parseFloat($(obj).val());
			}
			if($(obj).parent().next().children(":first").val()!=""){
				offer2=parseFloat($(obj).parent().next().children(":first").val());
			}
			if($(obj).parent().next().next().children(":first").val()!=""){
				offer3=parseFloat($(obj).parent().next().next().children(":first").val());
			}
			if($(obj).parent().next().next().next().children(":first").val()!=""){
				offer4=parseFloat($(obj).parent().next().next().next().children(":first").val());
			}
			if($(obj).parent().next().next().next().next().children(":first").val()!=""){
				offer5=parseFloat($(obj).parent().next().next().next().next().children(":first").val());
			}
			if($(obj).parent().next().next().next().next().next().children(":first").val()!=""){
				offer6=parseFloat($(obj).parent().next().next().next().next().next().children(":first").val());
			}
			return (offer1+offer2+offer3+offer4+offer5+offer6).toFixed(2);
      }
      function totalOffer2(obj){
    	   var offer1=0;
			var offer2=0;
			var offer3=0;
			var offer4=0;
			var offer5=0;
			var offer6=0;
    	  if($(obj).val()!=""){
    		   offer2=parseFloat($(obj).val());
			}
			if($(obj).parent().prev().children(":first").val()!=""){
				offer1=parseFloat($(obj).parent().prev().children(":first").val());
			}
			if($(obj).parent().next().children(":first").val()!=""){
				offer3=parseFloat($(obj).parent().next().children(":first").val());
			}
			if($(obj).parent().next().next().children(":first").val()!=""){
				offer4=parseFloat($(obj).parent().next().next().children(":first").val());
			}
			if($(obj).parent().next().next().next().children(":first").val()!=""){
				offer5=parseFloat($(obj).parent().next().next().next().children(":first").val());
			}
			if($(obj).parent().next().next().next().next().children(":first").val()!=""){
				offer6=parseFloat($(obj).parent().next().next().next().next().children(":first").val());
			}
			return (offer1+offer2+offer3+offer4+offer5+offer6).toFixed(2);
      }
      function totalOffer3(obj){
   	   var offer1=0;
			var offer2=0;
			var offer3=0;
			var offer4=0;
			var offer5=0;
			var offer6=0;
   	       if($(obj).val()!=""){
   		      offer3=parseFloat($(obj).val());
			}
			if($(obj).parent().prev().prev().children(":first").val()!=""){
				offer1=parseFloat($(obj).parent().prev().prev().children(":first").val());
			}
			if($(obj).parent().prev().children(":first").val()!=""){
				offer2=parseFloat($(obj).parent().prev().children(":first").val());
			}
			if($(obj).parent().next().children(":first").val()!=""){
				offer4=parseFloat($(obj).parent().next().children(":first").val());
			}
			if($(obj).parent().next().next().children(":first").val()!=""){
				offer5=parseFloat($(obj).parent().next().next().children(":first").val());
			}
			if($(obj).parent().next().next().next().children(":first").val()!=""){
				offer6=parseFloat($(obj).parent().next().next().next().children(":first").val());
			}
			return (offer1+offer2+offer3+offer4+offer5+offer6).toFixed(2);
     }
      function totalOffer4(obj){
      	   var offer1=0;
   			var offer2=0;
   			var offer3=0;
   			var offer4=0;
   			var offer5=0;
   			var offer6=0;
      	     if($(obj).val()!=""){
      		      offer4=parseFloat($(obj).val());
   			}
   			if($(obj).parent().prev().prev().prev().children(":first").val()!=""){
   				offer1=parseFloat($(obj).parent().prev().prev().prev().children(":first").val());
   			}
   			if($(obj).parent().prev().prev().children(":first").val()!=""){
   				offer2=parseFloat($(obj).parent().prev().prev().children(":first").val());
   			}
   			if($(obj).parent().prev().children(":first").val()!=""){
   				offer3=parseFloat($(obj).parent().prev().children(":first").val());
   			}
   			if($(obj).parent().next().children(":first").val()!=""){
   				offer5=parseFloat($(obj).parent().next().children(":first").val());
   			}
   			if($(obj).parent().next().next().children(":first").val()!=""){
   				offer6=parseFloat($(obj).parent().next().next().children(":first").val());
   			}
   			return (offer1+offer2+offer3+offer4+offer5+offer6).toFixed(2);
        }
       function totalOffer5(obj){
     	    var offer1=0;
  			var offer2=0;
  			var offer3=0;
  			var offer4=0;
  			var offer5=0;
  			var offer6=0;
     	     if($(obj).val()!=""){
     		      offer5=parseFloat($(obj).val());
  			}
  			if($(obj).parent().prev().prev().prev().prev().children(":first").val()!=""){
  				offer1=parseFloat($(obj).parent().prev().prev().prev().prev().children(":first").val());
  			}
  			if($(obj).parent().prev().prev().prev().children(":first").val()!=""){
  				offer2=parseFloat($(obj).parent().prev().prev().prev().children(":first").val());
  			}
  			if($(obj).parent().prev().prev().children(":first").val()!=""){
  				offer3=parseFloat($(obj).parent().prev().prev().children(":first").val());
  			}
  			if($(obj).parent().prev().children(":first").val()!=""){
  				offer4=parseFloat($(obj).parent().prev().children(":first").val());
  			}
  			if($(obj).parent().next().children(":first").val()!=""){
  				offer6=parseFloat($(obj).parent().next().children(":first").val());
  			}
  			return (offer1+offer2+offer3+offer4+offer5+offer6).toFixed(2);
       }
       function totalOffer6(obj){
    	    var offer1=0;
 			var offer2=0;
 			var offer3=0;
 			var offer4=0;
 			var offer5=0;
 			var offer6=0;
    	     if($(obj).val()!=""){
    		      offer6=parseFloat($(obj).val());
 			}
 			if($(obj).parent().prev().prev().prev().prev().prev().children(":first").val()!=""){
 				offer1=parseFloat($(obj).parent().prev().prev().prev().prev().prev().children(":first").val());
 			}
 			if($(obj).parent().prev().prev().prev().prev().children(":first").val()!=""){
 				offer2=parseFloat($(obj).parent().prev().prev().prev().prev().children(":first").val());
 			}
 			if($(obj).parent().prev().prev().prev().children(":first").val()!=""){
 				offer3=parseFloat($(obj).parent().prev().prev().prev().children(":first").val());
 			}
 			if($(obj).parent().prev().prev().children(":first").val()!=""){
 				offer4=parseFloat($(obj).parent().prev().prev().children(":first").val());
 			}
 			if($(obj).parent().prev().children(":first").val()!=""){
 				offer5=parseFloat($(obj).parent().prev().children(":first").val());
 			}
 			return (offer1+offer2+offer3+offer4+offer5+offer6).toFixed(2);
      }
		function totalOffer(obj,type){
			if(type==1){
				$(obj).parent().next().next().next().next().next().next().children(":first").val(totalOffer1(obj));
			}else if(type==2){
				$(obj).parent().next().next().next().next().next().children(":first").val(totalOffer2(obj));
			}else if(type==3){
				$(obj).parent().next().next().next().next().children(":first").val(totalOffer3(obj));
			}else if(type==4){
				$(obj).parent().next().next().next().children(":first").val(totalOffer4(obj));
			}else if(type==5){
				$(obj).parent().next().next().children(":first").val(totalOffer5(obj));
			}else if(type==6){
				$(obj).parent().next().children(":first").val(totalOffer6(obj));
			}
		}
      function onStep() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/yearPlan/select.do?proId=" + proId;
      }

      function nextStep() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/comCostDis/select.do?proId=" + proId;
      }

      $(function() {
        var totalRow1 = 0;
        var totalRow2 = 0;
        $("#table1 tr:not(:last)").each(function() {
          $(this).find("td:eq(11)").each(function() {
            totalRow1 += parseFloat($(this).text());
          });
          $(this).find("td:eq(14)").each(function() {
            totalRow2 += parseFloat($(this).text());
          });

        });
        if(totalRow1 != null || totalRow1 == "NaN") {
          $("#total1").html(totalRow1);
        }
        if(totalRow2 != null || totalRow2 == "NaN") {
          $("#total2").html(totalRow2);
        }
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
            <a href="javascript:void(0)">产品工时定额明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>产品工时定额明细</h2>
      </div>

      <div class="col-md-8 mt10 pl20 ml5">
        <button class="btn btn-windows add" type="button" onclick="add()">添加</button>
       <!--  <button class="btn btn-windows edit" type="button" onclick="edit()">修改</button> -->
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
              <th rowspan="3" class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th rowspan="3" class="info">序号</th>
              <th rowspan="3" class="info">零组部件名称</th>
              <th rowspan="3" class="info">零组部件图号</th>
              <th rowspan="3" class="info">工序名称</th>
              <th colspan="7" class="info">单位产品工时定额</th>
              <th rowspan="3" class="info">计量单位</th>
              <th rowspan="2" class="info">配套数量</th>
              <!-- <th rowspan="2" class="info">单位产品工时审核核定数</th> -->
              <th rowspan="3" class="info">备 注</th>
            </tr>
            <tr>
              <th class="info">准结工时</th>
              <th class="info">加工工时</th>
              <th class="info">装配工时</th>
              <th class="info">调试工时</th>
              <th class="info">试验工时</th>
              <th class="info">其他工时</th>
              <th class="info">小计</th>
            </tr>
            <tr>
              <th class="info">报价</th>
              <th class="info">报价</th>
              <th class="info">报价</th>
              <th class="info">报价</th>
              <th class="info">报价</th>
              <th class="info">报价</th>
              <th class="info">报价</th>
              <th class="info">报价</th>
              <!-- <th class="info">报价</th> -->
            </tr>
            <c:forEach items="${list}" var="yp" varStatus="vs">
              <tr id="${yp.id}">
                <td class="tc"><input  type="checkbox" name="chkItem" value="${yp.id }" /></td>
                <td class="tc">${vs.index+1 }</td>
                <td class="tc"><input type='text' class='m0 p0  border0 w100' name='listPro[${vs.index}].partsName'  value='${yp.partsName }' onchange="update(this);"><input type='hidden' name='listPro[${vs.index}].id'  value='${yp.id}'></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w100' name='listPro[${vs.index}].partsDrawingCode'  value='${yp.partsDrawingCode }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w100' name='listPro[${vs.index}].processName'  value='${yp.processName }' onchange="update(this);"></td>

                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listPro[${vs.index}].offer' onkeyup="if(isNaN(value))execCommand('undo')" value='${yp.offer }' onchange="update(this);" onblur="totalOffer(this,'1')"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listPro[${vs.index}].processingOffer' onkeyup="if(isNaN(value))execCommand('undo')" value='${yp.processingOffer }' onchange="update(this);" onblur="totalOffer(this,'2')"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listPro[${vs.index}].assemblyOffer' onkeyup="if(isNaN(value))execCommand('undo')" value='${yp.assemblyOffer }' onchange="update(this);" onblur="totalOffer(this,'3')"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listPro[${vs.index}].debuggingOffer' onkeyup="if(isNaN(value))execCommand('undo')" value='${yp.debuggingOffer }' onchange="update(this);" onblur="totalOffer(this,'4')"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listPro[${vs.index}].testOffer' onkeyup="if(isNaN(value))execCommand('undo')" value='${yp.testOffer }' onchange="update(this);" onblur="totalOffer(this,'5')"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listPro[${vs.index}].otherOffer' onkeyup="if(isNaN(value))execCommand('undo')" value='${yp.otherOffer }' onchange="update(this);" onblur="totalOffer(this,'6')"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listPro[${vs.index}].subtotalOffer' onkeyup="if(isNaN(value))execCommand('undo')" value='${yp.subtotalOffer }' onchange="update(this);" readonly="readonly"></td>

                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listPro[${vs.index}].measuringUnit'  value='${yp.measuringUnit }' onchange="update(this);"></td>

                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listPro[${vs.index}].assortOffer' onkeyup="if(isNaN(value))execCommand('undo')" value='${yp.assortOffer }' onchange="update(this);"></td>
                <%-- <td class="tc">${yp.approvedOffer }</td> --%>
                <td class="tc"><input type='text' class='m0 p0  border0 w100' name='listPro[${vs.index}].remark'  value='${yp.remark }' onchange="update(this);"></td>
              </tr>
            </c:forEach>
            <tr>
              <td class="tc" colspan="5">总计：</td>
              <td colspan="6"></td>
              <td class="tc" id="total1"></td>
              <td></td>
              <td></td>
              <td></td>
            </tr>
        </table>
      </div>

      <div class="col-md-12">
        <div class="mt40 tc mb50">
          <button class="btn" type="button" onclick="onStep()">上一步</button>
          <button class="btn" type="button" onclick="nextStep()">下一步</button>
        </div>
      </div>

    </div>

  </body>

</html>
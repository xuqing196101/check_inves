<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>专项费用明细</title>

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
          window.location.href = "${pageContext.request.contextPath}/specialCost/edit.do?id=" + id + "&proId=" + proId;
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
      function moneys(obj,type){
    	  var num=0;
    	  var sharePrice=0;
    	  var price=0;
    	  if(type==1){
    		  if($(obj).parent().prev().children(":first").val()!=""){
    			  num= parseFloat($(obj).parent().prev().children(":first").val());
    		  }
    		 if($(obj).val()!=""){
    			 price=parseFloat($(obj).val());
    		 }
        	 $(obj).parent().next().children(":first").val((price*num).toFixed(2));
    	  }else if(type==2){
    		  if($(obj).parent().prev().children(":first").val()!=""){
    			  price =parseFloat($(obj).parent().prev().children(":first").val());
    		  }
    		  if($(obj).val()!=""&&$(obj).val()!="0"){
    			  num=parseFloat($(obj).val());
    			  $(obj).parent().next().children(":first").val((price/num).toFixed(2));
    		  }else{
    			  $(obj).parent().next().children(":first").val((0).toFixed(2));
    		  }
        	  
    	  }else{
    		  if($(obj).val()!=""){
    			  num=parseFloat($(obj).val());
    		  }
    		  if($(obj).parent().next().children(":first").val()!=""){
    			  price=parseFloat($(obj).parent().next().children(":first").val());
    		  }
    		  if($(obj).parent().next().next().next().children(":first").val()!=""&&$(obj).parent().next().next().next().children(":first").val()!="0"){
    			  sharePrice=parseFloat($(obj).parent().next().next().next().children(":first").val());
    			  $(obj).parent().next().next().next().next().children(":first").val((num*price/sharePrice).toFixed(2));
    		  }else{
    			  $(obj).parent().next().next().next().next().children(":first").val((0).toFixed(2));
    		  }
    		  $(obj).parent().next().next().children(":first").val((num*price).toFixed(2))
    		  
    		  
    	  }
    	 
      }
      /* function html(num){
    	  var html="<tr id='add'>"
      	+"<td class='tc '><input  checked='checked' type='checkbox' name='chkItem' /></td>"
      	+"<td colspan='2'><input type='text' class='m0 p0  border0 w80' name='listSpec["+num+"].projectName'  value=''><input type='hidden' name='listSpec["+num+"].contractProduct.id'  value='${proId}'><input type='hidden' name='listSpec["+num+"].parentId'  value='0''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listSpec["+num+"].productDetal'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listSpec["+num+"].name'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listSpec["+num+"].norm'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listSpec["+num+"].measuringUnit'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listSpec["+num+"].amount'  value='' ></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listSpec["+num+"].price'  value='' onblur='moneys(this);'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listSpec["+num+"].money'  value='' ></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listSpec["+num+"].proportionAmout'  value='' onblur='proportionPrices(this);'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listSpec["+num+"].proportionPrice'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listSpec["+num+"].remark'  value=''></td>"
      	+"</tr>";
      	return html;
      }  */
      /*  var num=100;
      function add() {
        var proId = $("#proId").val();
        var overTr;
        var tr0=$("#tr0").nextAll();
        if(tr0.length<=1){
    		overTr=$($("#tr0"));
    	}else{
    		overTr=tr0[tr0.length-2];
    	}
        $(overTr).after(html(num));
        num++;
      } */
       
      
      function htmls(num,number,parentId){
    	  var html="<tr id='add'>"
      	+"<td class='tc'><input   type='checkbox' name='chkItem' /></td>"
      	+"<td class='hidden'></td>"
      	+"<td class='hidden'></td>"
      	+"<td class='hidden'><input  type='text'  name='listSpec["+num+"].serialNumber'  value='"+number+"'><input  type='text'  name='listSpec["+num+"].parentId'  value='"+parentId+"'><input type='hidden' name='listSpec["+num+"].contractProduct.id'  value='${proId}'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listSpec["+num+"].productDetal'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listSpec["+num+"].name'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listSpec["+num+"].norm'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listSpec["+num+"].measuringUnit'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listSpec["+num+"].amount'  value='' onblur='moneys(this,\"3\");' ></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listSpec["+num+"].price'  value='' onblur='moneys(this,\"1\");' ></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listSpec["+num+"].money'  value='' readonly='readonly'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listSpec["+num+"].proportionAmout'  value='' onblur='moneys(this,\"2\");'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listSpec["+num+"].proportionPrice'  value='' readonly='readonly'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listSpec["+num+"].remark'  value=''></td>"
      	+"</tr>";
      	return html;
      } 
      
      var num=100;
      function addDetail(obj,parentId){
    	   var rowspan=$(obj).parent().parent().attr("rowspan");
    	   var idx=$(obj).parent().parent().prev().attr("rowspan");
    	   var number;
    	   if(typeof(rowspan)== 'undefined'){
    		   var tableTr=$(obj).parent().parent().parent();
    		   rowspan=1;
    		   idx=1;
    		   $(obj).parent().parent().attr("rowspan",parseInt(rowspan)+1);
        	   $(obj).parent().parent().prev().attr("rowspan",parseInt(idx)+1);
        	   number=$(obj).parent().parent().prev().text()+"."+2;
        	   $(tableTr).after(htmls(num,number,parentId));
    	   }else{
    		   var trAll=$(obj).parent().parent().parent().nextAll();
        	   var chil=$(trAll[rowspan-2]).children();
        	   number=$(chil[3].firstChild).val();
        	   number=number.split(".")[0]+"."+(parseInt(number.split(".")[1])+1)
        	   $(obj).parent().parent().attr("rowspan",parseInt(rowspan)+1);
        	   $(obj).parent().parent().prev().attr("rowspan",parseInt(idx)+1);
        	   $(trAll[rowspan-2]).after(htmls(num,number,parentId));
    	   }
    	   num++;
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
      function save(){
    	  /* var tr=$("input[name='chkItem']:checked"); */
    	  var addTr=$("tr[id='add']");
    	  if(addTr.length>0||pojo.length>0){
    	  $("#table1").clone(true).appendTo($("#saveObject"));
    	  $.ajax({
	          url: "${pageContext.request.contextPath }/specialCost/save.do",
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
	        	            location.href='${pageContext.request.contextPath }/specialCost/select.do?proId=${proId}';
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
	           	 /* $(this).parent().parent().remove(); */
	           	 flg=true;
	             po.push(this);
           }else{
           	ids.push($(this).val());
           }
        });
        if(flg==true){
        	if(ids.length > 0) {
        		 layer.confirm('您确定要删除吗?', {
                     title: '提示',
                     offset: ['222px', '360px'],
                     shade: 0.01
                   }, function(index) {
                     layer.close(index);
                     window.location.href = "${pageContext.request.contextPath}/specialCost/delete.html?proId=" + proId + "&ids=" + ids;
                   });
        	}else{
        		layer.confirm('您确定要删除吗?', {
                    title: '提示',
                    offset: ['222px', '360px'],
                    shade: 0.01
                  }, function(index) {
                    layer.close(index);
                    for(var i=0;i<po.length;i++){
                		var tdcos=$(po[i]).parent().parent().prevAll();
                		for(var j=0;j<tdcos.length;j++){
                			var rowspan=$($(tdcos[j]).children()[2]).attr("rowspan");
                			if(typeof(rowspan) != 'undefined' ){
                				$($(tdcos[j]).children()[2]).attr("rowspan",rowspan-1);
                				$($(tdcos[j]).children()[2]).prev().attr("rowspan",rowspan-1);
                			    $(po[i]).parent().parent().remove();
                				break;
                			}
                		}
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
                  window.location.href = "${pageContext.request.contextPath}/specialCost/delete.html?proId=" + proId + "&ids=" + ids;
                });
              } else {
      	          layer.alert("请选择要删除的信息", {
      	            offset: ['222px', '390px'],
      	            shade: 0.01
      	          });
              }
        	
        }
        
      }

      function onStep() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/outsourcingCon/select.do?proId=" + proId;
      }

      function nextStep() {
        var proId = $("#proId").val();
        var total = $("#total").val();
        window.location.href = "${pageContext.request.contextPath}/burningPower/select.do?proId=" + proId + "&total=" + total;
      }
       var index=0;
       jQuery.fn.rowspan = function(colIdx) { //把td相同的数据行合并
    	  return this.each(function(){
	    	  var that;
	    	  $('tr', this).each(function(row) {
		    	  $('td:eq('+colIdx+')', this).filter(':visible').each(function(col) {
			    	  if (that!=null && $(this).html() == $(that).html()) {
				    	  rowspan = $(that).attr("rowSpan");
				    	  if (rowspan == undefined) {
				    	  	$(that).attr("rowSpan",1);
				    	  	rowspan = $(that).attr("rowSpan");
				    	  }
				    	  rowspan = Number(rowspan)+1;
				    	  $(that).attr("rowSpan",rowspan);
				    	  $(that).prev().attr("rowSpan",rowspan);
				    	  $(that).prev().html(index);
				    	  $(this).hide(); 
				    	  $(this).prev().hide();
				    	  } else {
				    		index++;
				    		$(this).prev().html(index);
				    	  	that = this;
				    	  }
			    	  });
		    	  });
	    	  });
    	  }   
      $(document).ready(function() {
      $("#table1").rowspan(2);
        var totalRow = 0;
        var total = 0;
        $('#table1 tr:not(:last)').each(function() {
          $(this).find('td:eq(9)').each(function() {
        	  if($(this.firstChild).val()!=null&&$(this.firstChild).val()!=""){
        		  totalRow += parseFloat($(this.firstChild).val());
        	  }
            
          });
          $(this).find('td:eq(11)').each(function() {
        	  if($(this.firstChild).val()!=null&&$(this.firstChild).val()!=""){
        	    total += parseFloat($(this.firstChild).val());
        	  }
            });
        });
        $('#total').text(totalRow.toFixed(2));
        $('#total2').text(total.toFixed(2));
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
            <a href="javascript:void(0)">专项费用明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>专项费用明细</h2>
      </div>

      <div class="col-md-8 mt10 ml10">
       <!--  <button class="btn btn-windows add" type="button" onclick="add()">添加</button>  -->
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
              <th class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th class="info">序号</th>
              <th class="info">项目名称</th>
              <th class="info">项目明细</th>
              <th class="info">名称</th>
              <th class="info">规格型号</th>
              <th class="info">计量单位</th>
              <th class="info">数量(消耗使用)</th>
              <th class="info">单价</th>
              <th class="info">金额</th>
              <th class="info">分摊数量</th>
              <th class="info">单位产品分摊额</th>
              <th class="info">备 注</th>
            </tr>
            <c:set value="" var="id"></c:set>
            <c:set value="" var="value"></c:set>
            <c:set value="" var="num"></c:set>
            <c:forEach items="${list}" var="sc" varStatus="vs">
              <c:if test="${sc.parentId=='0'}">
	              <c:set value="${sc.projectName }" var="value"></c:set>
	              <c:set value="${sc.id }" var="id"></c:set>
	              <c:set value="${sc.serialNumber }" var="num"></c:set>
              </c:if>
              <c:if test="${sc.parentId!='0'}">
	              <tr id='${sc.id }'>
	                <td class="tc"><input  type="checkbox" name="chkItem" value="${sc.id }" /><input type='hidden' name='listSpec[${vs.index}].id'  value='${sc.id }'><input type='hidden' class='m0 p0  border0 w80' name='listSpec[${vs.index}].projectName'  value='${sc.projectName }'></td>
	                <td class="tc"></td>
	                <td class="tc"><span class="fl">${value}</span><div class="fr"><input type='hidden'  value='${value }'><input type='hidden'  value='${id }'><input type='hidden'  value='${num }'><a class="addItem item_size" title="增加" onclick="addDetail(this,'${id}')"></a><!-- <a class="item_size deleteItem"  title="删除"  ></a> --></div></td>
	                <td class="hidden"><input  type='text' class='m0 p0  border0 w80' name='listSpec[${vs.index}].serialNumber'  value='${sc.serialNumber }'></td>
	                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listSpec[${vs.index}].productDetal'  value='${sc.productDetal }' onchange="update(this);"></td>
	                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listSpec[${vs.index}].name'  value='${sc.name }' onchange="update(this);"></td>
	                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listSpec[${vs.index}].norm'  value='${sc.norm }' onchange="update(this);"></td>
	                <td class="tc"><input type='text' class='m0 p0  border0 w50' name='listSpec[${vs.index}].measuringUnit'  value='${sc.measuringUnit }' onchange="update(this);"></td>
	                <td class="tc"><input type='text' class='m0 p0  border0 w50' name='listSpec[${vs.index}].amount'  value='${sc.amount }'  onchange="update(this);" onblur='moneys(this,"3");'></td>
	                <td class="tc"><input type='text' class='m0 p0  border0 w50' name='listSpec[${vs.index}].price'  value='${sc.price }' onblur='moneys(this,"1");' onchange="update(this);"></td>
	                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listSpec[${vs.index}].money'  value='${sc.money }'  onchange="update(this);" readonly="readonly"></td>
	                <td class="tc"><input type='text' class='m0 p0  border0 w50' name='listSpec[${vs.index}].proportionAmout'  value='${sc.proportionAmout }' onchange="update(this);" onblur='moneys(this,"2");'></td>
	                <td class="tc"><input type='text' class='m0 p0  border0 w100' name='listSpec[${vs.index}].proportionPrice'  value='${sc.proportionPrice }' onchange="update(this);" readonly="readonly"></td>
	                <td><input type='text' class='m0 p0  border0 w100' name='listSpec[${vs.index}].remark'  value='${sc.remark }' onchange="update(this);"></td>
	              </tr>
              </c:if>
              
              
            </c:forEach>
            <tr id="totalRow">
              <td class="tc" colspan="9">总计：</td>
              <td class="tc" id="total" ></td>
              <td ></td>
              <td id="total2"></td>
              <td></td>
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
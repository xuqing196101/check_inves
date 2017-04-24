<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>年度计划任务总工时明细</title>

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
          window.location.href = "${pageContext.request.contextPath}/yearPlan/edit.do?id=" + id + "&proId=" + proId;
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
      function values(tr0,indx,value,bool){
    	  var val=0;
  		for(var i=1;i<tr0.length-1;i++){
  			if($(tr0[i].children[1]).text().split(".").length==indx){
  				 if(bool==false){
  					if($(tr0[i].children[1]).text().substring(0,$(tr0[i].children[1]).text().lastIndexOf("."))==value){
  						if(val<=parseInt($(tr0[i].children[1]).text().substring($(tr0[i].children[1]).text().lastIndexOf(".")+1,$(tr0[i].children[1]).text().length))){
      						val=parseInt($(tr0[i].children[1]).text().substring($(tr0[i].children[1]).text().lastIndexOf(".")+1,$(tr0[i].children[1]).text().length));
      					}else{
      						val=val;
      					}
  					}
  				 }else{
  					if(val<=parseInt($(tr0[i].children[1]).text().split(".")[0])){
  						val=parseInt($(tr0[i].children[1]).text().split(".")[0]);
  					}else{
  						val=val;
  					}
  				 }
  			}
  		}
  		return val;
      }
      function html(num,index,parentId){
    	  var html="<tr id='add'>"
      	+"<td class='tc '><input   type='checkbox' name='chkItem' /></td>"
      	+"<td class='tc'>"+index+"</td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listYear["+num+"].projectName'  value=''><input type='hidden' name='listYear["+num+"].contractProduct.id'  value='${proId}'><input type='hidden' name='listYear["+num+"].parentId'  value='"+parentId+"'><input type='hidden' name='listYear["+num+"].serialNumber'  value='"+index+"'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listYear["+num+"].measuringUnit'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listYear["+num+"].oyaHourUnit'  value='' onblur='totalHours(this,\"1\")'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listYear["+num+"].oyaInvestAcount' onkeyup='if(isNaN(value))execCommand(\"undo\")' value='' onblur='totalHours(this,\"2\")'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listYear["+num+"].oyaHourTotal'  value='' readonly='readonly'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listYear["+num+"].newHourUnit'  value='' onblur='totalHours(this,\"1\")'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listYear["+num+"].newInvestAcount' onkeyup='if(isNaN(value))execCommand(\"undo\")' value='' onblur='totalHours(this,\"2\")'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listYear["+num+"].newHourTotal'  value='' readonly='readonly'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listYear["+num+"].remark'  value='' ></td>"
        +"</tr>";
      	return html;
      }
      var num=100;
      function add() {
        var proId = $("#proId").val();
        /* window.location.href = "${pageContext.request.contextPath}/yearPlan/add.html?proId=" + proId; */
        var proId = $("#proId").val();
        var ids = [];
        var obj;
        var flg=true;
        $('input[name="chkItem"]:checked').each(function() {
        	if($(this).parent().next().text().length==1){
        		if($(this).val()!="on"){
        			obj=this;
        			ids.push($(this).val());
        		}
        	 }else{
        		 if($(this).val()!="on"){
        			 layer.alert("请选择一个父节点", {
                         offset: ['222px', '390px'],
                         shade: 0.01
                       });
            		 flg=false;
            		 return false;
        		 }
        	 }
        });
        if(flg==false){
        	return false;
        }
        if(ids.length>1){
        	layer.alert("请选择一个父节点", {
                offset: ['222px', '390px'],
                shade: 0.01
              });
        	return;
        }
        var tr0=$("#tr0").nextAll();
        var serial;
        var overTr;
        if(tr0.length>2){
        	overTr=tr0[tr0.length-2];
        	if(obj!=null){
        		var value=$(obj).parent().next().text();
            	//第一级
            	if(value.split(".").length==1){
            		//tr0 所有的tr，2添加第二级时有只有1个.隔开，value当前点击的序号，false
            		var number=values(tr0,2,value,false);
            		serial=value+"."+(parseInt(number)+1);
            	}else if(value.split(".").length==2){
            		var number=values(tr0,3,value,false);
            		serial=value+"."+(parseInt(number)+1);
            	}
            	var next=$(obj).parent().parent().nextAll();
            	var bool=false;
            	for(var i=0;i<next.length;i++){
            		if(value==$($(next[i]).children()[1]).text().substring(0,$($(next[i]).children()[1]).text().lastIndexOf("."))){
            			overTr=next[i];
            			bool=true;
            		}
            	}
            	if(bool==false){
            		overTr=$(obj).parent().parent();
            	}
            	$(overTr).after(html(num,serial,ids[0]));
        	}else{
        		var number=values(tr0,1,null,true);
        		serial=(parseInt(number)+1);
        		$(overTr).after(html(num,serial,0));
        	}
        }else{
        	overTr=$("#tr0")[0];
        	var number=values(tr0,1,null,true);
    		serial=(parseInt(number)+1);
    		$(overTr).after(html(num,serial,0));
        }
        num++;
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
	          url: "${pageContext.request.contextPath }/yearPlan/save.do",
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
	        	            location.href='${pageContext.request.contextPath }/yearPlan/select.do?proId=${proId}';
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
        	if(ids.length>0){
        		layer.confirm('您确定要删除吗?', {
                    title: '提示',
                    offset: ['222px', '360px'],
                    shade: 0.01
                  }, function(index) {
                    layer.close(index);
                    window.location.href = "${pageContext.request.contextPath}/yearPlan/delete.html?proId=" + proId + "&ids=" + ids;
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
                  window.location.href = "${pageContext.request.contextPath}/yearPlan/delete.html?proId=" + proId + "&ids=" + ids;
                });
              } else {
      	          layer.alert("请选择要删除的信息", {
      	            offset: ['222px', '390px'],
      	            shade: 0.01
      	          });
              }
        }
        
        
      }
      
      
      function totalHours(obj,type){
    	  var hourUnit=0;
    	  var investAcount=0;
    	  if(type==1){
    		  if($(obj).val()!=""){
    			  hourUnit=parseFloat($(obj).val());
        	  }
    		  if($(obj).parent().next().children(":first").val()!=""){
    			  investAcount=parseFloat($(obj).parent().next().children(":first").val());
    		  }
    		  $(obj).parent().next().next().children(":first").val((hourUnit*investAcount).toFixed(2));
    	  }else{
    		  if($(obj).val()!=""){
    			  investAcount=parseFloat($(obj).val());
    		  }
    		  if($(obj).parent().prev().children(":first").val()!=""){
    			  hourUnit=parseFloat($(obj).parent().prev().children(":first").val());
    		  }
    		  $(obj).parent().next().children(":first").val((investAcount*hourUnit).toFixed(2));
    		  
    	  }
    	  
      }
      

      function onStep() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/periodCost/select.do?proId=" + proId;
      }

      function nextStep() {
        var proId = $("#proId").val();
      /*   var total = $("#total3").val(); */
        window.location.href = "${pageContext.request.contextPath}/productQuota/select.do?proId=" + proId;
      }

      $(document).ready(function() {
        var totalRow = 0;
        var totalRow2 = 0;
        var totalRow3 = 0;
        $('#table1 tr:not(:last)').each(function() {
         /*  $(this).find('td:eq(7)').each(function() {
            totalRow += parseFloat($(this).text());
          }); */
          $(this).find('td:eq(6)').each(function() {
        	  if($(this.firstChild).val()!=null&&$(this.firstChild).val()!=""){
        		  totalRow2 += parseFloat($(this.firstChild).val());
        	  }
          });
          $(this).find('td:eq(9)').each(function() {
        	  if($(this.firstChild).val()!=null&&$(this.firstChild).val()!=""){
        		  totalRow3 += parseFloat($(this.firstChild).val());
        	  }
          });
        });
        /* $('#total1').val(totalRow); */
        $('#total2').val(totalRow2.toFixed(2));
        $('#total3').val(totalRow3.toFixed(2));

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
            <a href="javascript:void(0)">年度计划任务总工时明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>年度计划任务总工时明细</h2>
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
            <tr id="tr0">
              <th rowspan="2" class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th rowspan="2" class="info">序号</th>
              <th rowspan="2" class="info">产品或任务名称</th>
             <!--  <th rowspan="2" class="info">产品单位</th> -->
              <th rowspan="2" class="info">计量单位</th>
              <!-- <th colspan="3" class="info">报价前2年</th> -->
              <th colspan="3" class="info">报价前1年</th>
              <th colspan="3" class="info">报价当年</th>
              <th rowspan="2" class="info">备 注</th>
            </tr>
            <tr>
              <!-- <th class="info">单位产品定额工时</th>
              <th class="info">投产数量</th>
              <th class="info">工时合计</th> -->
              <th class="info">单位产品定额工时</th>
              <th class="info">投产数量</th>
              <th class="info">工时合计</th>
              <th class="info">单位产品定额工时</th>
              <th class="info">投产数量</th>
              <th class="info">工时合计</th>
            </tr>
            <c:forEach items="${list}" var="yp" varStatus="vs">
              <tr id="${yp.id}">
                <td class="tc"><input  type="checkbox" name="chkItem" value="${yp.id }" /></td>
                <td>${yp.serialNumber }</td>
                <td class="tc"><input type='text' class='m0 p0  border0 w100' name='listYear[${vs.index }].projectName'  value='${yp.projectName }' onchange="update(this);"><input type='hidden' name='listYear[${vs.index }].id'  value='${yp.id }'></td>
                <%-- <td class="tc">${yp.productName }</td> --%>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listYear[${vs.index }].measuringUnit'  value='${yp.measuringUnit }' onchange="update(this);"></td>

                <%-- <td class="tc">${yp.tyaHourUnit }</td>
                <td class="tc">${yp.tyaInvestAcount }</td>
                <td class="tc" id="tyaHourTotal">${yp.tyaHourTotal }</td> --%>

                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listYear[${vs.index }].oyaHourUnit'  value='${yp.oyaHourUnit }' onchange="update(this);" onblur="totalHours(this,'1')"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listYear[${vs.index }].oyaInvestAcount' onkeyup="if(isNaN(value))execCommand('undo')" value='${yp.oyaInvestAcount }' onchange="update(this);" onblur="totalHours(this,'2')"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w100' name='listYear[${vs.index }].oyaHourTotal'  value='${yp.oyaHourTotal }' onchange="update(this);" readonly="readonly"></td>

                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listYear[${vs.index }].newHourUnit'  value='${yp.newHourUnit }' onchange="update(this);" onblur="totalHours(this,'1')"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listYear[${vs.index }].newInvestAcount' onkeyup="if(isNaN(value))execCommand('undo')" value='${yp.newInvestAcount }' onchange="update(this);" onblur="totalHours(this,'2')"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w100' name='listYear[${vs.index }].newHourTotal'  value='${yp.newHourTotal }' onchange="update(this);" readonly="readonly"></td>

                <td class="tc"><input type='text' class='m0 p0  border0 w100' name='listYear[${vs.index }].remark'  value='${yp.remark }' onchange="update(this);"></td>
              </tr>
            </c:forEach>
            <tr id="totalRow">
              <td colspan="4" class="tc">总计：</td>
              <!-- <td colspan="2"></td>
              <td class="tc"><input type="text" id="total1" class="border0 tc w50 m0" readonly="readonly"></td> -->
              <td colspan="2"></td>
              <td class="tc"><input type="text" id="total2" class="border0 tc w50 m0" readonly="readonly"></td>
              <td colspan="2"></td>
              <td class="tc"><input type="text" id="total3" class="border0 tc w50 m0" readonly="readonly"></td>
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
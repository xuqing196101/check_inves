<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
     <%@ include file="../../../../../common.jsp"%>

    <title>燃料动力费明细</title>

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
          window.location.href = "${pageContext.request.contextPath}/burningPower/edit.do?id=" + id + "&proId=" + proId;
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
      function html(num,serial,parentId){
    	  var html="<tr id='add'>"
      	+"<td class='tc '><input  checked='checked' type='checkbox' name='chkItem' /></td>"
      	+"<td>"+serial+"</td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listBurn["+num+"].firsetProduct'  value=''><input type='hidden' name='listBurn["+num+"].contractProduct.id'  value='${proId}'><input type='hidden' name='listBurn["+num+"].serialNumber'  value='"+serial+"'><input type='hidden' name='listBurn["+num+"].parentId'  value='"+parentId+"'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listBurn["+num+"].unit'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listBurn["+num+"].tyaAcount'  value='' onblur='moneys(this,\"2\");'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50 tr' name='listBurn["+num+"].tyaAvgPrice'  value='' onblur='moneys(this,\"1\");'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80 tr' name='listBurn["+num+"].tyaMoney'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listBurn["+num+"].oyaAcount'  value='' onblur='moneys(this,\"2\");'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50 tr' name='listBurn["+num+"].oyaAvgPrice'  value='' onblur='moneys(this,\"1\");'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80 tr' name='listBurn["+num+"].oyaMoney'  value='' ></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listBurn["+num+"].newAcount'  value='' onblur='moneys(this,\"2\");'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50 tr' name='listBurn["+num+"].newAvgPrice'  value='' onblur='moneys(this,\"1\");'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80 tr' name='listBurn["+num+"].newMoney'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listBurn["+num+"].remark'  value=''></td>"
      	+"</tr>";
      	return html;
      }
      var num=100;
      function add() {
        var proId = $("#proId").val();
        /* window.location.href = "${pageContext.request.contextPath}/burningPower/add.html?proId=" + proId; */
       var ids = [];
        var flg=true;
        var obj;
        $('input[name="chkItem"]:checked').each(function() {
        	if($(this).next().val()!=3){
        	    if($(this).val()!="on"){
        	    	obj=this;
        			ids.push($(this).val());
        		 }
        	 }else{
        		 layer.alert("请选择一个父节点", {
                     offset: ['222px', '390px'],
                     shade: 0.01
                   });
        		 flg=false;
        		 return false;
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
        var overTr;
        var serial;
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
    	 /*  var tr=$("input[name='chkItem']:checked");
    	  if(tr.length>0){
    	  for(var i=0;i<tr.length;i++){
    		 $(tr[i]).parent().parent().clone(true).appendTo($("#saveObject"));
    	  } */
    	  var addTr=$("tr[id='add']");
    	  if(addTr.length>0||pojo.length>0){
    	  $("#table1").clone(true).appendTo($("#saveObject"));
    	  $.ajax({
	          url: "${pageContext.request.contextPath }/burningPower/save.do",
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
	        	            location.href='${pageContext.request.contextPath }/burningPower/select.do?proId=${proId}';
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
      function moneys(obj,type){
    	  var num=0;
    	  var price=0;
    	    if(type==1){
    	    	if($(obj).parent().prev().children(":first").val()!=""){
    	    		num=parseFloat($(obj).parent().prev().children(":first").val());
    	    	}
    	    	if($(obj).val()!=""){
    	    		price =parseFloat($(obj).val());
    	    	}
    	    	$(obj).parent().next().children(":first").val((num*price).toFixed(2));
    	    }else{
    	    	if($(obj).val()!=""){
    	    		num =parseFloat($(obj).val());
    	    	}
    	    	if($(obj).parent().next().children(":first").val()!=""){
    	    		price=parseFloat($(obj).parent().next().children(":first").val());
    	    	}
    	    	$(obj).parent().next().next().children(":first").val((price*num).toFixed(2));
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
                    window.location.href = "${pageContext.request.contextPath}/burningPower/delete.html?proId=" + proId + "&ids=" + ids;
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
                  window.location.href = "${pageContext.request.contextPath}/burningPower/delete.html?proId=" + proId + "&ids=" + ids;
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
        window.location.href = "${pageContext.request.contextPath}/specialCost/select.do?proId=" + proId;
      }

      function nextStep() {
        var proId = $("#proId").val();
       /*  var total = $("#total3").val(); */
        window.location.href = "${pageContext.request.contextPath}/wagesPayable/select.do?proId=" + proId ;
      }

      $(function() {
        var totalRow1 = 0;
        var totalRow2 = 0;
        var totalRow3 = 0;
        $("#table1 tr:not(:last)").each(function() {
          $(this).find("td:eq(8)").each(function() {
        	  if($(this.firstChild).val()!=null&&$(this.firstChild).val()!=""){
                 totalRow1 += parseFloat($(this.firstChild).val());
        	  }
          });
          $(this).find("td:eq(11)").each(function() {
        	  if($(this.firstChild).val()!=null&&$(this.firstChild).val()!=""){
                 totalRow2 += parseFloat($(this.firstChild).val());
        	  }
          });
          $(this).find("td:eq(12)").each(function() {
        	  if($(this.firstChild).val()!=null&&$(this.firstChild).val()!=""){
                 totalRow3 += parseFloat($(this.firstChild).val());
        	  }
          });
        });
        if(totalRow1 != null) {
          $("#total1").val(totalRow1.toFixed(2));
        }
        if(totalRow2 != null) {
          $("#total2").val(totalRow2.toFixed(2));
        }
        if(totalRow3 != null) {
          $("#total3").val(totalRow3.toFixed(2));
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
            <a href="javascript:void(0)">燃料动力费明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>燃料动力费明细</h2>
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
              <th rowspan="2" class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th rowspan="2" class="info">序号</th>
              <th rowspan="2" class="info">项目名称</th>
              <!-- <th rowspan="2" class="info">二级项目</th>
              <th rowspan="2" class="info">三级项目</th> -->
              <th rowspan="2" class="info">计量单位</th>
              <th colspan="3" class="info">报价前2年</th>
              <th colspan="3" class="info">报价前1年</th>
              <th colspan="3" class="info">报价当年</th>
              <th rowspan="2" class="info">备 注</th>
            </tr>
            <tr>
              <th class="info">数量</th>
              <th class="info">平均单价</th>
              <th class="info">金额</th>
              <th class="info">数量</th>
              <th class="info">平均单价</th>
              <th class="info">金额</th>
              <th class="info">数量</th>
              <th class="info">平均单价</th>
              <th class="info">金额</th>
            </tr>
            <c:forEach items="${list}" var="bp" varStatus="vs">
              <tr id="${bp.id}">
                <td class="tc"><input  type="checkbox" name="chkItem" value="${bp.id }" /><input  type="hidden"  value="${bp.parentLevel }" /></td>
                <td class="tl">${bp.serialNumber }</td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listBurn[${vs.index}].firsetProduct'  value='${bp.firsetProduct}'><input type='hidden' class='m0 p0  border0 w80' name='listBurn[${vs.index}].id'  value='${bp.id }'></td>
                <%-- <td class="tc">${bp.secondProduct }</td>
                <td class="tc">${bp.thirdProduct }</td> --%>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listBurn[${vs.index}].unit'  value='${bp.unit }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50' name='listBurn[${vs.index}].tyaAcount'  value='${bp.tyaAcount }' onchange="update(this);" onblur='moneys(this,"2");'></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50 tr' name='listBurn[${vs.index}].tyaAvgPrice'  value='${bp.tyaAvgPrice }' onblur='moneys(this,"1");' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80 tr' name='listBurn[${vs.index}].tyaMoney'  value='${bp.tyaMoney }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50' name='listBurn[${vs.index}].oyaAcount'  value='${bp.oyaAcount }' onchange="update(this);" onblur='moneys(this,"2");'></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50 tr' name='listBurn[${vs.index}].oyaAvgPrice'  value='${bp.oyaAvgPrice }' onblur='moneys(this,"1");' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80 tr' name='listBurn[${vs.index}].oyaMoney'  value='${bp.oyaMoney }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50' name='listBurn[${vs.index}].newAcount'  value='${bp.newAcount }' onchange="update(this);" onblur='moneys(this,"2");'></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50 tr' name='listBurn[${vs.index}].newAvgPrice'  value='${bp.newAvgPrice }' onblur='moneys(this,"1");' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80 tr' name='listBurn[${vs.index}].newMoney'  value='${bp.newMoney }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listBurn[${vs.index}].remark'  value='${bp.remark }' onchange="update(this);"></td>
              </tr>
            </c:forEach>
            <tr>
              <td class="tc" colspan="6">总计：</td>
              <td class="tr"><input type="text" id="total1" class="border0 tc w50 mb0 tr" readonly="readonly"></td>
              <td colspan="2"></td>
              <td class="tr"><input type="text" id="total2" class="border0 tc w50 mb0 tr" readonly="readonly"></td>
              <td colspan="2"></td>
              <td class="tr"><input type="text" id="total3" class="border0 tc w50 mb0 tr" readonly="readonly"></td>
              <td class="tc"></td>
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
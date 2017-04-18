<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML PUBLIC >
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>应付工资明细</title>

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

      /** 单选 */
      function check() {
        var count = 0;
        var checklist = document.getElementsByName("chkItem");
        var checkAll = document.getElementById("checkAll");
        for(var i = 0; i < checklist.length; i++) {
          if(checklist[i].checked == false) {
            checkAll.checked = false;
            break;
          }
          for(var j = 0; j < checklist.length; j++) {
            if(checklist[j].checked == true) {
              checkAll.checked = true;
              count++;
            }
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
          window.location.href = "${pageContext.request.contextPath}/wagesPayable/edit.do?id=" + id + "&proId=" + proId;
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
      	+"<td class='tc '><input   type='checkbox' name='chkItem' /><input  type='hidden'  value='"+serial+"' /></td>"
      	+"<td class='tc'>"+serial+"</td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listWages["+num+"].firsetProduct'  value=''><input type='hidden' name='listWages["+num+"].contractProduct.id'  value='${proId}'><input type='hidden' name='listWages["+num+"].serialNumber'  value='"+serial+"'><input type='hidden' name='listWages["+num+"].parentId'  value='"+parentId+"'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listWages["+num+"].oyaProduceUser'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listWages["+num+"].oyaWorkshopUser'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listWages["+num+"].oyaManageUser'  value='' ></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listWages["+num+"].oyaOtherUser'  value='' onblur='moneys(this);'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listWages["+num+"].oyaTotal'  value='' ></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listWages["+num+"].newProduceUser'  value='' ></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listWages["+num+"].newWorkshopUser'  value='' ></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listWages["+num+"].newManageUser'  value='' ></td>"
      	+"<td><input type='text' class='m0 p0  border0 w50' name='listWages["+num+"].newOtherUser'  value='' onblur='moneys(this);'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listWages["+num+"].newTotal'  value='' ></td>"
      	+"<td><input type='text' class='m0 p0  border0 w80' name='listWages["+num+"].remark'  value=''></td>"
      	+"</tr>";
      	return html;
      }
      
      
      
      var num=100;
      function add() {
        var proId = $("#proId").val();
        var ids = [];
        var obj;
        var flg=true;
        $('input[name="chkItem"]:checked').each(function() {
        	if($(this).next().val().length==1){
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
        /* window.location.href = "${pageContext.request.contextPath}/wagesPayable/add.html?proId=" + proId; */
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
	          url: "${pageContext.request.contextPath }/wagesPayable/save.do",
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
	        	            location.href='${pageContext.request.contextPath }/wagesPayable/select.do?proId=${proId}';
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
                    window.location.href = "${pageContext.request.contextPath}/wagesPayable/delete.html?proId=" + proId + "&ids=" + ids;
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
                  window.location.href = "${pageContext.request.contextPath}/wagesPayable/delete.html?proId=" + proId + "&ids=" + ids;
                });
              } else {
      	          layer.alert("请选择要删除的信息", {
      	            offset: ['222px', '390px'],
      	            shade: 0.01
      	          });
              }
        }
      }
      function moneys(obj){
    	  var weight =parseFloat($(obj).val())+parseFloat($(obj).parent().prev().children(":first").val())+parseFloat($(obj).parent().prev().prev().children(":first").val())+parseFloat($(obj).parent().prev().prev().prev().children(":first").val());
    	  $(obj).parent().next().children(":first").val(weight.toFixed(2));
      }
      function onStep() {
        var proId = $("#proId").val();
        window.location.href = "${pageContext.request.contextPath}/burningPower/select.do?proId=" + proId;
      }

      function nextStep() {
        var proId = $("#proId").val();
        /* var total = $("#total3").val(); */
        window.location.href = "${pageContext.request.contextPath}/manufacturingCost/select.do?proId=" + proId;
      }

      $(function() {
        var totalRow1 = 0;
        var totalRow2 = 0;
        var totalRow3 = 0;
        $("#table1 tr:not(:last)").each(function() {
          /* $(this).find("td:eq(9)").each(function() {
            totalRow1 += parseFloat($(this).text());
          }); */
          $(this).find("td:eq(8)").each(function() {
        	  if($(this.firstChild).val()!=null&&$(this.firstChild).val()!=""){
        		  totalRow2 += parseFloat($(this.firstChild).val());
        	  }
            
          });
          $(this).find("td:eq(13)").each(function() {
        	  if($(this.firstChild).val()!=null&&$(this.firstChild).val()!=""){
        		  totalRow3 += parseFloat($(this.firstChild).val());
        	  }
            
          });

        });
       /*  if(totalRow1 != null) {
          $("#total1").val(totalRow1);
        } */
        if(totalRow2 != null) {
          $("#total2").val(totalRow2);
        }
        if(totalRow3 != null) {
          $("#total3").val(totalRow3);
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
            <a href="javascript:void(0)">应付工资明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>应付工资明细</h2>
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
              <!-- <th rowspan="2" class="info">部门</th>
              <th rowspan="2" class="info">一级项目</th>
              <th rowspan="2" class="info">二级项目</th> -->
              <th class="info">项目名称</th>
             <!--  <th colspan="5" class="info">报价前2年</th> -->
              <th colspan="5" class="info">报价前1年</th>
              <th colspan="5" class="info">报价当年</th>
              <th rowspan="2" class="info">备 注</th>
            </tr>
            <tr>
              <th class="info">部门</th>
              <!-- <th class="info">基本生产人员</th>
              <th class="info">车间管理人员</th>
              <th class="info">管理人员</th>
              <th class="info">其他人员</th>
              <th class="info">合计</th> -->
              <th class="info">基本生产人员</th>
              <th class="info">车间管理人员</th>
              <th class="info">管理人员</th>
              <th class="info">其他人员</th>
              <th class="info">合计</th>
              <th class="info">基本生产人员</th>
              <th class="info">车间管理人员</th>
              <th class="info">管理人员</th>
              <th class="info">其他人员</th>
              <th class="info">合计</th>
            </tr>
            <c:forEach items="${list}" var="wp" varStatus="vs">
              <tr id="${wp.id}">
                <td class="tc"><input onclick="check()" type="checkbox" name="chkItem" value="${wp.id }" /><input  type="hidden"  value="${wp.serialNumber }" /></td>
                <td class="tl">${wp.serialNumber }</td>
                <%-- <td class="tc">${vs.index+1 }</td> --%>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listWages[${vs.index}].firsetProduct'  value='${wp.firsetProduct }' onchange="update(this);"><input  type="hidden" name='listWages[${vs.index}].id' value="${wp.id }" /></td>
                <%-- <td class="tc">${wp.firsetProduct }</td>
                <td class="tc">${wp.secondProduct }</td> --%>
                <%-- <td class="tc">${wp.tyaProduceUser }</td>
                <td class="tc">${wp.tyaWorkshopUser }</td>
                <td class="tc">${wp.tyaManageUser }</td>
                <td class="tc">${wp.tyaOtherUser }</td>
                <td class="tc">${wp.tyaTotal }</td> --%>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listWages[${vs.index}].oyaProduceUser'  value='${wp.oyaProduceUser }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50' name='listWages[${vs.index}].oyaWorkshopUser'  value='${wp.oyaWorkshopUser }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50' name='listWages[${vs.index}].oyaManageUser'  value='${wp.oyaManageUser }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listWages[${vs.index}].oyaOtherUser'  value='${wp.oyaOtherUser }' onblur='moneys(this);' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50' name='listWages[${vs.index}].oyaTotal'  value='${wp.oyaTotal }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50' name='listWages[${vs.index}].newProduceUser'  value='${wp.newProduceUser }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listWages[${vs.index}].newWorkshopUser'  value='${wp.newWorkshopUser }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50' name='listWages[${vs.index}].newManageUser'  value='${wp.newManageUser }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w50' name='listWages[${vs.index}].newOtherUser'  value='${wp.newOtherUser }' onchange="update(this);" onblur='moneys(this);'></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listWages[${vs.index}].newTotal'  value='${wp.newTotal }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w80' name='listWages[${vs.index}].remark'  value='${wp.remark }' onchange="update(this);"></td>
              </tr>
            </c:forEach>
            <tr>
              <td class="tc" colspan="3">总计：</td>
             <!--  <td colspan="4"></td>
              <td class="tc"><input type="text" id="total1" class="border0 tc w50 mb0" readonly="readonly"></td> -->
              <td colspan="4"></td>
              <td class="tc"><input type="text" id="total2" class="border0 tc w50 mb0" readonly="readonly"></td>
              <td colspan="4"></td>
              <td class="tc"><input type="text" id="total3" class="border0 tc w50 mb0" readonly="readonly"></td>
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
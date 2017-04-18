<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML >
<html>

  <head>
    <%@ include file="../../../../../common.jsp"%>

    <title>制造费用明细</title>

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
          window.location.href = "${pageContext.request.contextPath}/manufacturingCost/edit.do?id=" + id + "&proId=" + proId;
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
      	+"<td><input type='text' class='m0 p0  border0 w130' name='listManu["+num+"].projectName'  value=''><input type='hidden' name='listManu["+num+"].contractProduct.id'  value='${proId}'></td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listManu["+num+"].tyaQuoteprice'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listManu["+num+"].oyaQuoteprice'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w100' name='listManu["+num+"].newQuoteprice'  value=''></td>"
      	+"<td><input type='text' class='m0 p0  border0 w130' name='listManu["+num+"].remark'  value='' ></td>"
        +"</tr>";
      	return html;
      }
      var num=100;
      var index;
      function add() {
        var proId = $("#proId").val();
        /* window.location.href = "${pageContext.request.contextPath}/manufacturingCost/add.html?proId=" + proId; */
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
    	  /* var tr=$("input[name='chkItem']:checked");
    	  if(tr.length>0){
    	  for(var i=0;i<tr.length;i++){
    		 $(tr[i]).parent().parent().clone(true).appendTo($("#saveObject"));
    	  } */
    	  var addTr=$("tr[id='add']");
    	  if(addTr.length>0||pojo.length>0){
    	  $("#table1").clone(true).appendTo($("#saveObject"));
    	  $.ajax({
	          url: "${pageContext.request.contextPath }/manufacturingCost/save.do",
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
	        	            location.href='${pageContext.request.contextPath }/manufacturingCost/select.do?proId=${proId}';
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
                    window.location.href = "${pageContext.request.contextPath}/manufacturingCost/delete.html?proId=" + proId + "&ids=" + ids;
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
                  window.location.href = "${pageContext.request.contextPath}/manufacturingCost/delete.html?proId=" + proId + "&ids=" + ids;
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
        window.location.href = "${pageContext.request.contextPath}/wagesPayable/select.do?proId=" + proId;
      }

      function nextStep() {
        var proId = $("#proId").val();
        /* var total = $("#total3").val(); */
        window.location.href = "${pageContext.request.contextPath}/periodCost/select.do?proId=" + proId ;
      }

      $(function() {
        var totalRow1 = 0;
        var totalRow2 = 0;
        var totalRow3 = 0;
        $("#table1 tr:not(:last)").each(function() {
          $(this).find("td:eq(3)").each(function() {
        	  if($(this.firstChild).val()!=null&&$(this.firstChild).val()!=""){
        		  totalRow1 += parseFloat($(this.firstChild).val());
        	  }
          });
          $(this).find("td:eq(4)").each(function() {
        	  if($(this.firstChild).val()!=null&&$(this.firstChild).val()!=""){
        		  totalRow2 += parseFloat($(this.firstChild).val());
        	  }
          });
          $(this).find("td:eq(5)").each(function() {
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
            <a href="javascript:void(0)">制造费用明细</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>

    <div class="container">
      <div class="headline-v2">
        <h2>制造费用明细</h2>
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
              <th class="info"><input id="checkAll" type="checkbox" onclick="selectAll()" /></th>
              <th class="info">序号</th>
              <th class="info">项目名称</th>
              <th class="info">报价前2年</th>
              <th class="info">报价前1年</th>
              <th class="info">报价当年</th>
              <th class="info">备 注</th>
            </tr>
            <c:forEach items="${list}" var="mc" varStatus="vs">
              <tr id="${mc.id}">
                <td class="tc"><input type="checkbox" name="chkItem" value="${mc.id }" /></td>
                <td class="tc">${vs.index+1 }</td>
                <td class="tc"><input type='text' class='m0 p0  border0 w130' name='listManu[${vs.index}].projectName'  value='${mc.projectName }' onchange="update(this);"><input type="hidden" name='listManu[${vs.index}].id' value="${mc.id }"/></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w100' name='listManu[${vs.index}].tyaQuoteprice'  value='${mc.tyaQuoteprice }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w100' name='listManu[${vs.index}].oyaQuoteprice'  value='${mc.oyaQuoteprice }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w100' name='listManu[${vs.index}].newQuoteprice'  value='${mc.newQuoteprice }' onchange="update(this);"></td>
                <td class="tc"><input type='text' class='m0 p0  border0 w130' name='listManu[${vs.index}].remark'  value='${mc.remark }' onchange="update(this);"></td>
              </tr>
            </c:forEach>
            <tr>
              <td class="tc" colspan="3">总计：</td>
              <td class="tc"><input type="text" id="total1" class="border0 tc w50 m0" readonly="readonly"></td>
              <td class="tc"><input type="text" id="total2" class="border0 tc w50 m0" readonly="readonly"></td>
              <td class="tc"><input type="text" id="total3" class="border0 tc w50 m0" readonly="readonly"></td>
              <td></td>
            </tr>
        </table>
      </div>

      <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
        <button class="btn" type="button" onclick="onStep()">上一步</button>
        <button class="btn" type="button" onclick="nextStep()">下一步</button>
      </div>

    </div>

  </body>

</html>
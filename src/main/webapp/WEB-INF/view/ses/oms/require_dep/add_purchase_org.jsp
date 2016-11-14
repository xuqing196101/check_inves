<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>采购机构查询</title>
<script type="text/javascript">
  	  $(function(){
	  laypage({
		    cont: $("#pagediv"), //容器。值支持id名、原生dom对象，jquery对象,
		    pages: "${list.pages}", //总页数
		    skin: '#2c9fA6', //加载内置皮肤，也可以直接赋值16进制颜色值，如:#c00
		    skip: true, //是否开启跳页
		    total: "${list.total}",
		    startRow: "${list.startRow}",
		    endRow: "${list.endRow}",
		    groups: "${list.pages}">=5?5:"${list.pages}", //连续显示分页数
		    curr: function(){ //通过url获取当前页，也可以同上（pages）方式获取
		        var page = location.search.match(/page=(\d+)/);
		        return page ? page[1] : 1;
		    }(), 
		    jump: function(e, first){ //触发分页后的回调
		        if(!first){ //一定要加此判断，否则初始时会无限刷新
		             location.href = '<%=basePath %>purchaseManage/addPurchaseOrg.html?page='+e.curr+"&address=${address}";
		        }
		    }
		});
  });
  function fanhui(){
  	window.location.href="<%=basePath%>purchaseManage/add.html"
  }
  function submit(){
  	$("#form1").submit();
  }
function chongzhi(){
	$("#name").val('');
}
$(function() {
		/* var optionNodes = $("option");
		for ( var i = 1; i < optionNodes.length; i++) {
			if ("${supplier.supplierType}" == $(optionNodes[i]).val()) {
				optionNodes[i].selected = true;
			}
		} */
	});
	/** 全选全不选 */
    Array.prototype.indexOf = function(val) {
		for (var i = 0; i < this.length; i++) {
			if (this[i] == val) return i;
		}
		return -1;
	};
	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
			if (index > -1) {
				this.splice(index, 1);
			}
	};
    var arr=[];
	function selectAll(){
		//alert($("#allId").prop("checked"));
		if ($("#allId").prop("checked")) {  
            $("input[name=items]").each(function() {  
                $(this).prop("checked", true);  
            });  
        } else {  
            $("input[name=items]").each(function() {  
                $(this).prop("checked", false);  
            });  
        }   
	}
	function add(){
		console.dir(arr);
		console.dir(parent.array);
		var index = parent.layer.getFrameIndex(window.name);
		//parent.$("#tab tr").remove();
		parent.$("#tab tr:gt(0)").remove();
		for(var i=0;i<parent.array.length;i++){
			var id = parent.array[i].substr(0,parent.array[i].indexOf(","));
			var name = parent.array[i].substr(parent.array[i].indexOf(",")+1,parent.array[i].length);
			//id = "\'"+id+"\'";
			parent.$("#tab").append("<tr id="+id+" align='center'>"
            					+"<td><input type='checkbox'/></td>"     
                                +"<td>"+Number(i+1)+"</td>"
                                //+"<td><input type='text' name='desc"+_len+"' id='name"+_len+"' value='"+_len+"' /></td>"
                                +"<td>"+name+"</td>"
                                 +"<td class='hide'>"+id+"</td>"
                                +"<td><a href=\'#\' onclick=\'deltr("+"\""+id+"\""+","+"\""+name+"\""+")\'>删除</a></td>"
                            +"</tr>"); 
		}
		//var _len="dddd";
		 
        parent.layer.close(index); //执行关闭
		/*  var _len = arr[i];        
            $("#tab").append("<tr id="+_len+" align='center'>"
            					+"<td><input type='checkbox'/></td>"     
                                +"<td>"+_len+"</td>"
                                //+"<td><input type='text' name='desc"+_len+"' id='name"+_len+"' value='"+_len+"' /></td>"
                                 +"<td>"+_len+"</td>"
                                +"<td>"+_len+"</td>"
                                +"<td><a href=\'#\' onclick=\'deltr("+_len+")\'>删除</a></td>"
                            +"</tr>");     */
			
	}
	function check(id,name){
		var data = id+","+name;
		//单选时间
		if($("#"+id).prop("checked")){
			arr.push(data);
			//console.dir(parent.array);
			//console.dir(data);
			//console.dir(parent.array.indexOf(data)==0);
			if(parent.array.indexOf(data)==-1){
				parent.array.push(data);
			}
		}else{
			arr.remove(data);
			parent.array.remove(data);
		}
		//console.dir(id);
	}
</script>
</head>
  <body>
  	<div class="margin-top-10 breadcrumbs ">
	      <div class="container">
			   <ul class="breadcrumb margin-left-0">
			   <li><a href="#"> 首页</a></li><li><a href="#">支撑系统</a></li><li><a href="#">机构管理</a></li><li class="active"><a href="#">添加机构</a></li>
			   </ul>
			<div class="clear"></div>
		  </div>
	   </div>
  	<div class="container">
  		<div class="headline-v2">
		     <h2>采购机构信息</h2>
	    </div>
	    <h2 class="search_detail">
		<form id="form1" class="mb0" action="${pageContext.request.contextPath}/purchaseManage/addPurchaseOrg.html" method="post" id="form1">
			<input type="hidden" name="page" id="page"> 
			<input type="hidden" name="flag" value="0">
		    <input type="hidden" name="typeName" value="${orgnization.typeName }" />
		    <ul class="demand_list">
	          <li>
	            <label class="fl">机构名称：</label><span><input type="text" name="name" id="name" value="${orgnization.name }"></span>
	          </li>
	            <button class="btn" onclick="submit()" type="button">查询</button>
                <button class="btn" onclick="chongzhi()" type="button"> 重置</button>
	        </ul>
	        <div class="clear"></div>
		</form>
		</h2>
		   <div class="col-md-12 pl20 mt10">
		    <button class="btn btn-windows add" type="button" onclick="add()">添加</button>
		    <button class="btn btn-windows back" type="button" onclick="location.href='javascript:history.go(-1);'">返回</button>
		   </div>
		<div class="content table_box">
            <table class="table table-bordered table-condensed table-hover table-striped">
			<thead>
				<tr>
					<th class="info w30"><input type="checkbox"
						onclick="selectAll();" id="allId" alt="全选">
					</th>
					<th class="info w50">序号</th>
					<th class="info">机构名称</th>
					<th class="info">类型</th>
				</tr>
			</thead>

			<tbody>
				<c:forEach items="${orgnizationList}" var="p" varStatus="vs">
					<tr class="cursor">
						<!-- 选择框 -->
						<td class="tc"><input onclick="check('${p.id}','${p.name }')"
							type="checkbox" name="items" id="${p.id }" value="${p.id}" />
						</td>
						<!-- 序号 -->
						<td class="tc">${vs.index+1}</td>
						<!-- 内容 -->
						<td class="tc">${p.name}</td>
						<!-- 创建人-->
						<td class="tc">
							<c:choose>
								<c:when test="${p.typeName=='0'}">
									监管部门
								</c:when>
								<c:when test="${p.typeName=='1'}">
									采购机构
								</c:when>
								<c:otherwise>
									需求部门
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<div id="pagediv" align="right"></div>
		</div>
     </div>
  </body>
</html>

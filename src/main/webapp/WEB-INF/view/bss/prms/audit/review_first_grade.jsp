<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<jsp:include page="/WEB-INF/view/common.jsp"></jsp:include>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>项目评分</title>
<script type="text/javascript">
$(document).ready(function() { 
	 $("input[name='expertValue']").bind("keypress", function(event) {  
   var event= event || window.event;  
   var getValue = $(this).val();  
   //控制第一个不能输入小数点"."  
   if (getValue.length == 0 && event.which == 46) {  
       event.preventDefault();  
       return;  
   }  
   //控制只能输入一个小数点"."  
   if (getValue.indexOf('.') != -1 && event.which == 46) {  
       event.preventDefault();  
       return;  
   }  
   //控制只能输入的值  
   if (event.which && (event.which < 48 || event.which > 57) && event.which != 8 && event.which != 46) {  
       event.preventDefault();  
        return;  
       }  
   });  
   //失去焦点是触发  
   $("input[name='expertValue']").bind("blur", function(event) {  
   var value = $(this).val(), reg = /\.$/;  
   if (reg.test(value)) {  
   value = value.replace(reg, "");  
   $(this).val(value);  
   }  
   });  
   });  
   
	function audit(obj,id,supplierId,typeName,markTermId,quotaId){
		if(typeName=='2' || typeName=='3' ||typeName=='4' ||typeName=='5' ){
			var flag = 0;
			//填写的所有分数
			var expertValues=[];
			$(obj).parent().parent().find("input[name='expertValue']").each(function(){
				//该行的所有填写的值
				var value = $(this).val();
				expertValues.push(value);
				//判断是否有未填写的
				if(value=='' || value==null || value==undefined){
					flag++;
				}
			});
			//flag为0证明都填写了
			if(flag==0){
					var supplierIds = [];
					$(obj).parent().parent().find("input[name='supplierId']").each(function(){
						//所有供应商的id
						var value = $(this).val();
						supplierIds.push(value);
					});
					$("#markTermId").val(markTermId);
					$("#supplierIds").val(supplierIds);
					$("#expertValues").val(expertValues);
					$("#id").val(id);
					$("#typeName").val(typeName);
					$("#quotaId").val(quotaId);
					$.ajax({
						url:'${pageContext.request.contextPath}/reviewFirstAudit/caseGrade.html',
						data:$("#score_form").serialize(),
						type:"post",
						dataType:'JSON',
						success:function(data){
							for(var i = 0;i<data.length;i++){
								$(obj).parent().parent().find("input[name='supplierId']").each(function(){
									//算出的分数
									if(data[i].supplierId == $(this).val()){
										$(this).next().val(data[i].score);
									}
								});
							}
						}
					});
			}
		}else{
		
			var expertValue = $(obj).val();
			if(expertValue != ""){
			$("#supplierIds").val(supplierId);
			$("#expertValues").val(expertValue);
			$("#id").val(id);
			$("#typeName").val(typeName);
			$("#quotaId").val(quotaId);
			$.ajax({
				url:'${pageContext.request.contextPath}/reviewFirstAudit/caseGradeTwo.html',
				data:$("#score_form").serialize(),
				type:"post",
				dataType:"JSON",
				success:function(data){
					$(obj).parent().next().find("input[name='expertScore']").val(data);
					// 修改,将input框改为直接显示,input设置为hidden,将input值传给span
					$(obj).parent().next().find("input[name='expertScore']").next().html(data);
				}
				
			});
		}
		}
		
	}
	//提交
	function submit1(){
		var count = 0;
		$("#table2").find("input[name='expertValue']").each(function(){
			if($(this).val()==""){
				count++;
			}
		});
		$("#table2").find("input[name='expertScore']").each(function(){
			if($(this).val()==""){
				count++;
			}
		});
		if(count==0){
		$("#form1").submit();
		}else{
			layer.msg("还有未评分项",{offset: ['350px', '800px']});
		}
		
	}
	
  </script>
  </head>
  
<body>
  <div class="dnone">
  	<form  id="score_form">
  	  <input type="hidden" name="supplierIds" id="supplierIds">
  	  <input type="hidden" name="expertValues" id="expertValues">
  	  <input type="hidden" name="markTermId" id="markTermId">
      <input type="hidden" name="scoreModelId" id="id">
  	  <input type="hidden" name="typeName" id="typeName">
  	  <input type="hidden" name="quotaId" id="quotaId">
  	  <input type="hidden" name="projectId" id="projectId" value="${projectId }">
  	  <input type="hidden" name="packageId" id="packageId" value="${packageId }">
  	</form>
  </div>
  <div class="tab-content clear step_cont">
    <div class=class="col-md-12 tab-pane active"  id="tab-1">
 	  <div class="container clear margin-top-30" id="package">
 	    <table >
 	      <thead>
 	        <th><h2>项目名称：</h2></th><th><h2>${project.name }（${pack.name }）&nbsp;&nbsp;&nbsp;&nbsp;</h2></th>
 	        <th><h2>编号：</h2></th><th><h2>${project.projectNumber }</h2></th>
 	        <tr>
 	          <th></th>
 	        </tr>
 	      </thead>
 	    </table>
		<form action="${pageContext.request.contextPath}/expert/saveGrade.html" id="form1" method="post" >
		  <!--项目id  -->
	   	  <input type="hidden" name="projectId" id="projectId" value="${projectId }">
		  <!--包id  -->
	   	  <input type="hidden" name="packageId" id="packageId" value="${packageId }">
	   	  <div style="overflow:scroll;">
	        <table class="table table-bordered table-condensed mt5" id="table2" style="overflow: hidden;word-spacing: keep-all;" >
			  <tr>
			    <th  colspan="3"></th>
			    <c:forEach items="${supplierList}" var="supplier">
			      <th colspan="2">${supplier.suppliers.supplierName}</th>
			    </c:forEach>
			  </tr>
			  <tr>
			   	  	  <th>评审项目</th>
			   	  	  <th>计分模型</th>
			   	      <th>标准分值</th>
			   	  	  <c:forEach items="${supplierList}" var="supplier">
			        	<th>评委填写</th>
     		        	<th>评审得分</th>
	    		  	  </c:forEach>
			   		</tr>
			  <c:forEach items="${markTermTypeList}" var="type">
			    <tr>
			      <td class="info" colspan="${length}">${type.name}</td>
			    </tr>
			    <c:forEach items="${markTermList}" var="markTerm">
			      <c:if test="${markTerm.typeName eq type.id}">
			        <tr>
			          <td class="info" colspan="${length}">${markTerm.name}</td>
			        </tr>
			   		<c:forEach items="${scoreModelList}" var="score" varStatus="vs">
			    	  <c:if test="${score.markTerm.pid eq markTerm.id}">
			    	    <tr>
			 	  		  <td class="w100"><a href="javascript:void();" title="${score.reviewContent}">${score.name}</a></td>
			 	  		  <td class="tc">
			 	    	    <c:if test="${score.typeName == 0}">模型1:是否判断</c:if>
			 	            <c:if test="${score.typeName == 1}">模型2:按项加减分</c:if>
				 	        <c:if test="${score.typeName == 2}">模型3:评审数额最高递减</c:if>
				 	        <c:if test="${score.typeName == 3}">模型4:评审数额最低递增</c:if>
				 	        <c:if test="${score.typeName == 4}">模型5:评审数额高计算</c:if>
				 	        <c:if test="${score.typeName == 5}">模型6:评审数额低计算</c:if>
				 	        <c:if test="${score.typeName == 6}">模型7:评审数额低区间递增</c:if>
				 	        <c:if test="${score.typeName == 7}">模型8:评审数额高区间递减</c:if>
				 	      </td>
				 	      <td class="tc">${score.standardScore}</td>
				 	      <c:forEach items="${supplierList}" var="supplier">
					 	    <c:choose>
					 	      <c:when test="${score.typeName == '0'}">
					 	        <td class="tc">
					 	          <select name="expertValue" 
					 	            style="width: 50px;"  onchange="audit(this,'${score.id}','${supplier.suppliers.id}','${score.typeName}','${score.markTermId}','')"
					 	          >
					 	            <option value=""></option>
					 	            <option value="1" 
					 	              <c:forEach items="${scores}" var="sco">
					 	                <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id and sco.expertValue eq '1'}">selected=selected</c:if>
					 	              </c:forEach>
					 	            >是</option>
					 	            <option value="0"
					 	              <c:forEach items="${scores}" var="sco">
					 	                <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id and sco.expertValue eq '0'}">selected=selected</c:if>
					 	              </c:forEach>
					 	            >否</option>
					 	          </select>
					 	        </td>
					 	      </c:when>
					 	      <c:otherwise>
					 	        <td class="tc">
					 	          <input type="text" name="expertValue" id="ipt5" onpaste="return false;"
					 	            style="width: 50px; ime-mode:disabled" onchange="audit(this,'${score.id}','${supplier.suppliers.id}','${score.typeName}','${score.markTermId}','')"
					 	            <c:forEach items="${scores}" var="sco">
					 	              <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id}">value="${sco.expertValue}"</c:if>
					 	            </c:forEach>
					 	          >
					 	        </td>
					 	      </c:otherwise>
					 	    </c:choose>
					 	    <td class="tc">
					 	      <input type="hidden" name="supplierId"  value="${supplier.suppliers.id}"/>
					 	      <input type="hidden" name="expertScore" readonly="readonly" style="width: 50px;"/>
					 	      <span><c:forEach items="${scores}" var="sco">
					 	          <c:if test="${sco.packageId eq packageId and sco.expertId eq expertId and sco.supplierId eq supplier.suppliers.id and sco.scoreModelId eq score.id}">${sco.score}</c:if>
					 	        </c:forEach></span>
					 	    </td>
				 	      </c:forEach>
					    </tr> 
					  </c:if>
			        </c:forEach>
			      </c:if>
			    </c:forEach>
			  </c:forEach>
			  <!-- 之前的代码
			  <thead>
			    <tr>
			      <th></th>
			   	  <th></th>
			   	  <th></th>
			   	  <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
			   	    <th colspan="3">${supplier.suppliers.supplierName }</th>
			   	  </c:forEach>
			   	</tr>
			    <tr>
			      <th>序号</th>
			   	  <th>评审项目</th>
			   	  <th>计分模型</th>
			   	  <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
			   	    <th>标准分值</th>
			   		<th>投标人填写</th>
			   		<th>投标文件页码</th>
			   		<th>系统初始得分</th>
			        <th>评委填写</th>
     		        <th>评审得分</th>
	    		  </c:forEach>
			   	</tr>
			  </thead>
			  <c:forEach items="${list }" var="l" varStatus="vs">
			    <tr align="center">
			 	  <td>${vs.count }</td>
			 	  <td>${l.markTermName }</td>
			 	  <td>
			 	    <c:if test="${l.typeName == 0}">模型1:是否判断</c:if>
			 	    <c:if test="${l.typeName == 1}">模型2:按项加减分</c:if>
			 	    <c:if test="${l.typeName == 2}">模型3:评审数额最高递减</c:if>
			 	    <c:if test="${l.typeName == 3}">模型4:评审数额最低递增</c:if>
			 	    <c:if test="${l.typeName == 4}">模型5:评审数额高计算</c:if>
			 	    <c:if test="${l.typeName == 5}">模型6:评审数额低计算</c:if>
			 	    <c:if test="${l.typeName == 6}">模型7:评审数额低区间递增</c:if>
			 	    <c:if test="${l.typeName == 7}">模型8:评审数额高区间递减</c:if>
			 	  </td>
			 	  <c:forEach items="${supplierList }" var="supplier" varStatus="vs">
			 	    <td>${l.standardScore }</td>
				 	<td>${l.supplierValue }</td>
				 	<td>${l.page }</td>
				 	<td>${l.initScore }</td>
				 	<c:choose>
				 	  <c:when test="${l.typeName == '0' }">
				 	    <td>
				 	      <select name="expertValue" 
				 	        <c:if test="${l.round == 0  }"> style="width: 50px;"  onchange="audit(this,'${l.id}','${supplier.suppliers.id }','${l.typeName}','${l.markTermId }','${l.quotaId }')"</c:if>
				 	        <c:if test="${l.round == 1  }"> style="width: 50px; color:red;"  onchange="audit(this,'${l.id}','${supplier.suppliers.id }','${l.typeName}','${l.markTermId }','${l.quotaId }')"</c:if>
				 	        <c:if test="${l.round == 2  }"> disabled="disabled" style="width: 50px;"</c:if>
				 	      >
				 	        <option value=""> </option>
				 	        <option <c:if test="${l.expertValue == 1 }">selected</c:if> value="1">是</option>
				 	        <option <c:if test="${l.expertValue == 0 }">selected</c:if> value="0">否</option>
				 	      </select>
				 	    </td>
				 	  </c:when>
				 	  <c:otherwise>
				 	    <td>
				 	      <input type="text"  name="expertValue" id="ipt5" onpaste="return false;" value="${l.expertValue }"
				 	        <c:if test="${l.round == 0  }">style="width: 50px; ime-mode:disabled" onchange="audit(this,'${l.id}','${supplier.suppliers.id }','${l.typeName}','${l.markTermId }','${l.quotaId }')"</c:if>
				 	        <c:if test="${l.round == 1  }">style="width: 50px; color:red; ime-mode:disabled" onchange="audit(this,'${l.id}','${supplier.suppliers.id }','${l.typeName}','${l.markTermId }','${l.quotaId }')"</c:if>
				 	        <c:if test="${l.round == 2  }">readonly="readonly" style="width: 50px;"</c:if>
				 	      >
				 	    </td>
				 	  </c:otherwise>
				 	</c:choose>
				 	<td>
				 	  <input type="hidden" name="supplierId"  value="${supplier.suppliers.id }"/>
				 	  <input type="text" name="expertScore" readonly="readonly" value="${l.finalScore }"  style="width: 50px;"/>
				 	</td>
			 	  </c:forEach>
				</tr> 
			  </c:forEach>
			  -->
			</table>
			<div class="tc">
			  <input type="button" onclick="submit1();"  value="提交" class="btn btn-windows git">
			  <input class="btn btn-windows back" value="返回" type="button" onclick="location.href='javascript:history.go(-1);'"><br/><br/><br/>
		    </div>
		  </div>
		</form>
	  </div> 
	</div>
  </div>
</body>
</html>

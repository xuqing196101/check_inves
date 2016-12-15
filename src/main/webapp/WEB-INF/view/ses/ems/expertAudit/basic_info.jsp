<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ include file="../../../common.jsp"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<style type="text/css">
			input {
			  cursor:pointer;
			}
			textarea {
			  cursor:pointer;
			}
		</style>
		
		<script type="text/javascript">
			$(function() {
	      $(":input").each(function() {
	        var onmouseover = "this.style.border='solid 1px #FF0000'";
	        var onmouseout = "this.style.border='solid 1px #D3D3D3'";
	        $(this).attr("onmousemove",onmouseover);
	        $(this).attr("onmouseout",onmouseout);
	    	});
	    	
	    	//为只读
	    	$(":input").each(function() {
	      $(this).attr("readonly", "readonly");
	      });
	    });
		</script>
		
		<script type="text/javascript">
			function reason(obj,str){
			  var expertId = $("#expertId").val();
			  var auditField;
			  var auditContent;
			  var html = "<div class='abolish' style='padding-right;30px'>×</div>";
		    $("#"+obj.id+"").each(function() {
		      auditField = $(this).parents("li").find("span").text().replace("：","").trim();
          auditContent = $(this).parents("li").find("input").val();
    		});
					var index = layer.prompt({
				    title : '请填写不通过的理由：', 
				    formType : 2, 
				    offset : '100px',
				}, 
		    function(text){
				    $.ajax({
				      url:"${pageContext.request.contextPath}/expertAudit/auditReasons.html",
				      type:"post",
				      dataType:"json",
				      data:"suggestType=one"+"&auditContent="+auditContent+"&auditReason="+text+"&expertId="+expertId+"&auditField="+auditField,
					    success:function(result){
				        result = eval("(" + result + ")");
				        if(result.msg == "fail"){
				           layer.msg('该条信息已审核过！', {	            
				             shift: 6, //动画类型
				             offset:'100px'
				          });
				        }
				      }
				    });
					$(obj).after(html);
		      layer.close(index);
			    });
		  	}
		  	
			//获取选中子节点id
			$(function (){
				var ids=new Array();
				var checklist1 = document.getElementsByName ("chkItem_1");
				for(var i=0;i<checklist1.length;i++){
			 		var vals=checklist1[i].value;
			 		if(checklist1[i].checked){
			 			ids.push(vals);
			 		}
				}
				var checklist2 = document.getElementsByName ("chkItem_2");
				for(var i=0;i<checklist2.length;i++){
			 		var vals=checklist2[i].value;
			 		if(checklist2[i].checked){
			 			ids.push(vals);
			 		}
				}
				var isEdit = "${isEdit}";
				if (isEdit == "0") {
					// 没有修改过
					var index = layer.confirm('该专家在上次审核退回后未做任何修改!', {
						btn : [ '确定' ],
			            offset:'100px'
					}, function() {
						layer.close(index);
					});
				}
			});
			
			// 提示之前的信息
			function isCompare(inputName,fieldName, type){
				$.ajax({
					url: "${pageContext.request.contextPath}/expertAudit/getFieldContent.do",
					data: {"field":fieldName,"type":type,"expertId":"${expertId}"},
					async: false,
					success: function(response){
						layer.tips("原值:" + response, "#" + inputName, {
		    				tips : 2
		    			});
					}
				});
			}
		</script>
		<script type="text/javascript">
			function jump(str){
			  var action;
			  if(str=="experience"){
			     action ="${pageContext.request.contextPath}/expertAudit/experience.html";
			  }
			  if(str=="product"){
			    action = "${pageContext.request.contextPath}/expertAudit/product.html";
			  }
			  if(str=="expertFile"){
			    action = "${pageContext.request.contextPath}/expertAudit/expertFile.html";
			  }
			  if(str=="reasonsList"){
			    action = "${pageContext.request.contextPath}/expertAudit/reasonsList.html";
			  }
			  $("#form_id").attr("action",action);
			  $("#form_id").submit();
			}
		</script>
		
	</head>
	
	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:void(0)">首页</a>
					</li>
					<li>
						<a href="javascript:void(0)">支撑系统</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家管理</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家审核</a>
					</li>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container container_box">
			<div class=" content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="nav nav-tabs bgdd">
						<li class="active">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">基本信息</a><i></i>
						</li>
						<li onclick="jump('experience')" >
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">经历经验</a><i></i>
						</li>
						<li onclick="jump('product')" >
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">产品目录</a><i></i>
						</li>
						<li onclick="jump('expertFile')" >
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">附件</a><i></i>
						</li>
						<li onclick="jump('reasonsList')" >
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">审核汇总</a>
						</li>
					</ul>
					
					<h2 class="count_flow"><i>1</i>专家个人信息</h2>
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
							<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">专家来源：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="expertsFrom" <c:if test="${fn:contains(editFields,'getExpertsFrom')}">onmouseover="isCompare('expertsFrom','getExpertsFrom','1');"</c:if> value="${expertsFrom }" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">专家姓名：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="relName" <c:if test="${fn:contains(editFields,'getRelName')}">onmouseover="isCompare('relName','getRelName','0');"</c:if> value="${expert.relName}" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">性别：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="gender" <c:if test="${fn:contains(editFields,'getGender')}">onmouseover="isCompare('gender','getGender','1');"</c:if> value="${gender }" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">出生日期：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input readonly="readonly" <c:if test="${fn:contains(editFields,'getBirthday')}">onmouseover="isCompare('birthday','getBirthday','2');"</c:if> value="<fmt:formatDate type='date' value='${expert.birthday}' dateStyle='default' pattern='yyyy-MM-dd'/>" id="birthday" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">省：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="" value="" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">市：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="" value="" type="text" onclick="reason(this);" <c:if test="${fn:contains(editFields,'getAddress')}">onmouseover="isCompare('address','getAddress','1');"</c:if>/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">政治面貌：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="politicsStatus" <c:if test="${fn:contains(editFields,'getPoliticsStatus')}">onmouseover="isCompare('politicsStatus','getPoliticsStatus','1');"</c:if> value="${politicsStatus }" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">民族：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.nation}" <c:if test="${fn:contains(editFields,'getNation')}">onmouseover="isCompare('nation','getNation','0');"</c:if> id="nation" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">健康状态：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.healthState}" <c:if test="${fn:contains(editFields,'getHealthState')}">onmouseover="isCompare('healthState','getHealthState','0');"</c:if> id="healthState" type="text" onclick="reason(this);"/>
							</div>
						</li>
						
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">缴纳社会保险证明：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.coverNote}" <c:if test="${fn:contains(editFields,'getCoverNote')}">onmouseover="isCompare('coverNote','getCoverNote','0');"</c:if> id="coverNote" type="text" onclick="reason(this);"/>
							</div>
						</li>
						
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">居民身份证号码：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.idCardNumber}" <c:if test="${fn:contains(editFields,'getIdCardNumber')}">onmouseover="isCompare('idCardNumber','getIdCardNumber','0');"</c:if> id="idCardNumber" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">军队人员身份证件类型：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="idType" <c:if test="${fn:contains(editFields,'getIdType')}">onmouseover="isCompare('idType','getIdType','1');"</c:if> value="${idType }" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">证件号码：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.idNumber}" <c:if test="${fn:contains(editFields,'getIdNumber')}">onmouseover="isCompare('idNumber','getIdNumber','0');"</c:if> id="idNumber" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">手机：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${user.mobile}" <c:if test="${fn:contains(editFields,'getMobile')}">onmouseover="isCompare('mobile','getMobile','0');"</c:if> readonly="readonly" id="mobile" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">固定电话：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.telephone}" <c:if test="${fn:contains(editFields,'getTelephone')}">onmouseover="isCompare('telephone','getTelephone','0');"</c:if> id="telephone" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 传真电话：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.fax}" <c:if test="${fn:contains(editFields,'getFax')}">onmouseover="isCompare('fax','getFax','0');"</c:if> id="fax" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">个人邮箱：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.email}" <c:if test="${fn:contains(editFields,'getEmail')}">onmouseover="isCompare('email','getEmail','0');"</c:if> id="email" type="text" onclick="reason(this);"/>
							</div>
						</li>
					</ul>
					
					<h2 class="count_flow"><i>2</i>专家学历信息</h2>
					<ul class="ul_list">
					  <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">毕业院校及专业：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.graduateSchool}" <c:if test="${fn:contains(editFields,'getGraduateSchool')}">onmouseover="isCompare('graduateSchool','getGraduateSchool','0');"</c:if> id="graduateSchool" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">最高学历：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="hightEducation" <c:if test="${fn:contains(editFields,'getHightEducation')}">onmouseover="isCompare('hightEducation','getHightEducation','1');"</c:if> value="${hightEducation }" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 最高学位：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${degree}" <c:if test="${fn:contains(editFields,'getDegree')}">onmouseover="isCompare('degree','getDegree','1');"</c:if> id="degree" type="text" onclick="reason(this);"/>
							</div>
						</li>
					</ul>
					
					<h2 class="count_flow"><i>3</i>专家专业信息</h2>
					<ul class="ul_list">
					  <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">所在单位：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.workUnit}" <c:if test="${fn:contains(editFields,'getWorkUnit')}">onmouseover="isCompare('workUnit','getWorkUnit','0');"</c:if> id="workUnit" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">单位地址：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.unitAddress}" <c:if test="${fn:contains(editFields,'getUnitAddress')}">onmouseover="isCompare('unitAddress','getUnitAddress','0');"</c:if> id="unitAddress" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 单位邮编：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.postCode}" <c:if test="${fn:contains(editFields,'getPostCode')}">onmouseover="isCompare('postCode','getPostCode','0');"</c:if> id="postCode" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 现任职务：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.atDuty}" <c:if test="${fn:contains(editFields,'getAtDuty')}">onmouseover="isCompare('atDuty','getAtDuty','0');"</c:if> id="appendedInput" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">从事专业：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.major}" <c:if test="${fn:contains(editFields,'getMajor')}">onmouseover="isCompare('major','getMajor','0');"</c:if> id="major" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 从事专业起始年度：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input <c:if test="${fn:contains(editFields,'getTimeStartWork')}">onmouseover="isCompare('timeStartWord','getTimeStartWord','3');"</c:if> value="<fmt:formatDate type='date' value='${expert.timeStartWork}' dateStyle='default' pattern='yyyy-MM-dd'/>" readonly="readonly" id="timeStartWork" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">专家技术职称/职业资格：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input maxlength="20" <c:if test="${fn:contains(editFields,'getProfessTechTitles')}">onmouseover="isCompare('professTechTitles','getProfessTechTitles','0');"</c:if> value="${expert.professTechTitles}" name="professTechTitles" id="professTechTitles" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">取得技术职称时间：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input <c:if test="${fn:contains(editFields,'getMakeTechDate')}">onmouseover="isCompare('makeTechDate','getMakeTechDate','3');"</c:if> value="<fmt:formatDate type='date' value='${expert.makeTechDate}' dateStyle='default' pattern='yyyy-MM-dd'/>" readonly="readonly" id="makeTechDate" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 参加工作时间：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input readonly="readonly" <c:if test="${fn:contains(editFields,'getTimeToWork')}">onmouseover="isCompare('timeToWork','getTimeToWork','3');"</c:if> value="<fmt:formatDate value='${expert.timeToWork}' pattern='yyyy-MM'/>" id="timeToWork" type="text" onclick="reason(this);"/>
							</div>
						</li>
					</ul>
					
					<!-- 专家专业信息 -->
					<%-- <h2 class="count_flow"><i>4</i>专家类别</h2>
					<ul class="ul_list" id="expertType" onclick="reason(this);">
						<li class="col-md-3 col-sm-6 col-xs-12 pl10" >
							<div class="input-append col-sm-12 col-xs-12 col-md-12 p0" >
								<c:forEach items="${spList}" var="sp" >
							  	<span><input type="checkbox" name="chkItem_1" value="${sp.id}" />${sp.name} </span>
							  </c:forEach> 
							  <c:forEach items="${jjList}" var="jj" >
									<span><input type="checkbox" name="chkItem_2"  value="${jj.id}" />${jj.name} </span>
								</c:forEach>
							</div>
						</li>
					</ul> --%>
				</div>
			</div>
		</div>
		
		<input value="${expertId}" id="expertId" type="hidden">
		
		<form id="form_id" action="" method="post">
   	  <input name="expertId" value="${expertId }" type="hidden">
    </form>
		
	</body>

</html>
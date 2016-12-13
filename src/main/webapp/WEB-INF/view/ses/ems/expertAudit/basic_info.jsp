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
	        $(this).attr("onmouseover",onmouseover);
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
			  var textAuditContent;
			  var html = "<div class='abolish' style='padding-right;30px'>×</div>";
		    $("#"+obj.id+"").each(function() {
		      auditField = $(this).parents("li").find("span").text().replace("：","");
          auditContent = $(this).parents("li").find("input").val();
          textAuditContent = $(this).parents("li").find("textarea").text();
    		});
					var index = layer.prompt({
				    title : '请填写不通过的理由：', 
				    formType : 2, 
				    offset : '100px',
				}, 
		    function(text){
		    	if(str == null){
				    $.ajax({
				      url:"${pageContext.request.contextPath}/expertAudit/auditReasons.html",
				      type:"post",
				      dataType:"json",
				      data:"auditType=basic_page"+"&auditContent="+auditContent+"&auditReason="+text+"&expertId="+expertId+"&auditField="+auditField,
				    });
			    }else{
			    
			    	$.ajax({
				      url:"${pageContext.request.contextPath}/expertAudit/auditReasons.html",
				      type:"post",
				      dataType:"json",
				      data:"auditType=basic_page"+"&auditContent="+textAuditContent+"&auditReason="+text+"&expertId="+expertId+"&auditField="+str,
				    });
			    }
					$(obj).after(html);
		      layer.close(index);
			    });
		  	}
		</script>
		
	</head>
	
	<body>
		<div class="container container_box">
			<div class=" content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<ul class="flow_step">
						<li onclick="jump('essential')" class="active">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">基本信息</a><i></i>
						</li>
						<li onclick="jump('essential')" class="">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">专家类型</a><i></i>
						</li>
						<li onclick="jump('essential')" class="">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">产品目录</a><i></i>
						</li>
						<li onclick="jump('essential')" class="">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">申请表</a><i></i>
						</li>
						<li onclick="jump('essential')" class="">
							<a aria-expanded="false" href="#tab-1" data-toggle="tab">审核汇总</a>
						</li>
					</ul>
					<h2 class="count_flow"><i>1</i>专家基本信息</h2>
					
					<input value="${expert.id}" id="expertId" type="hidden">
					
					<ul class="ul_list">
						<li class="col-md-3 col-sm-6 col-xs-12 pl15">
							<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">专家来源：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="expertsFrom" value="${expertsFrom }" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">专家姓名：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="relName" value="${expert.relName}" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12">
							<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">性别：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="gender" value="${gender }" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">出生日期：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input readonly="readonly" value="<fmt:formatDate type='date' value='${expert.birthday}' dateStyle='default' pattern='yyyy-MM-dd'/>" id="birthday" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">省：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="" value="" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">市：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="" value="" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">政治面貌：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="politicsStatus" value="${politicsStatus }" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">民族：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.nation}" id="nation" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">健康状态：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.healthState}" id="healthState" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">所在单位：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.workUnit}" id="workUnit" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">缴纳社会保险证明：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.coverNote}" id="coverNote" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">单位地址：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.unitAddress}" id="unitAddress" type="text" onclick="reason(this);"/>

							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 单位邮编：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.postCode}" id="postCode" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 现任职务：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.atDuty}" id="appendedInput" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">居民身份证号码：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.idCardNumber}" id="idCardNumber" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">军队人员身份证件类型：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="idType" value="${idType }" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">证件号码：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.idNumber}" id="idNumber" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">从事专业：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.major}" id="major" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 从事专业起始年度：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="<fmt:formatDate type='date' value='${expert.timeStartWork}' dateStyle='default' pattern='yyyy-MM-dd'/>" readonly="readonly" id="timeStartWork" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">专家技术职称/职业资格：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input maxlength="20" value="${expert.professTechTitles}" name="professTechTitles" id="professTechTitles" type="text" />
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 取得技术职称时间：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="<fmt:formatDate type='date' value='${expert.makeTechDate}' dateStyle='default' pattern='yyyy-MM-dd'/>" readonly="readonly" id="makeTechDate" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">毕业院校及专业：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.graduateSchool}" id="graduateSchool" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">最高学历：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input id="hightEducation" value="${hightEducation }" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 最高学位：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${degree}" id="degree" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">手机：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${user.mobile}" readonly="readonly" id="mobile" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">固定电话：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.telephone}" id="telephone" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 传真电话：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.fax}" id="fax" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">个人邮箱：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input value="${expert.email}" id="email" type="text" onclick="reason(this);"/>
							</div>
						</li>
						<li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 参加工作时间：</span>
							<div class="input-append col-md-12 col-sm-12 col-xs-12 input_group p0 col-md-12 col-sm-12 col-xs-12 input_group p0">
								<input readonly="readonly" value="<fmt:formatDate value='${expert.timeToWork}' pattern='yyyy-MM'/>" id="timeToWork" type="text" onclick="reason(this);"/>
							</div>
						</li>
					</ul>

					<!-- 参评的产品类别 -->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>2</i>参评的产品类别</h2>
						<ul class="ul_list">
							<li>
								<textarea rows="10" id="productCategories" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'参评的产品类别');">${expert.productCategories}</textarea>
							</li>
						</ul>
					</div>
					<!-- 主要工作经历-->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>3</i>主要工作经历</h2>
						<ul class="ul_list">
							<li>
								<textarea rows="10" id="jobExperiences" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'主要工作经历');">${expert.jobExperiences}</textarea>
							</li>
						</ul>
					</div>
					<!-- 专业学术成果 -->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>4</i>专业学术成果</h2>
						<ul class="ul_list">
							<li>
								<textarea rows="10" id="academicAchievement" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'专业学术成果');">${expert.academicAchievement}</textarea>
							</li>
						</ul>
					</div>
					<!-- 主要工作经历-->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>5</i>参加军队地方采购评审情况</h2>
						<ul class="ul_list">
							<li>
								<textarea rows="10" id="reviewSituation" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'参加军队地方采购评审情况');">${expert.reviewSituation}</textarea>
							</li>
						</ul>
					</div>
					<!-- 主要工作经历-->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>6</i>需要申请回避的情况</h2>
						<ul class="ul_list">
							<li>
								<textarea rows="10" id="avoidanceSituation" style="height: 150px; width: 100%; resize: none;" onclick="reason(this,'需要申请回避的情况');">${expert.avoidanceSituation}</textarea>
							</li>
						</ul>
					</div>
					<!-- 附件信息-->
					<div class="padding-top-10 clear">
						<h2 class="count_flow"><i>7</i>上传附件</h2>
						<ul class="ul_list">
							<table class="table table-bordered">
								<tbody>
									<tr>
										<td class="bggrey" width="15%">身份证</td>
										<td>
											<u:upload id="expert1" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID}" auto="true" />
											<u:show showId="show1" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_IDNUMBER_TYPEID}" />
										</td>
										<td class="bggrey" width="15%">学历证书</td>
										<td>
											<u:upload id="expert2" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID}" auto="true" />
											<u:show showId="show2" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_ACADEMIC_TYPEID}" />
										</td>
									</tr>
									<tr>
										<td class="bggrey">职称证书</td>
										<td>
											<u:upload id="expert3" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_TITLE_TYPEID}" auto="true" />
											<u:show showId="show3" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_TITLE_TYPEID}" />
										</td>
										<td class="bggrey">学位证书</td>
										<td>
											<u:upload id="expert4" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_DEGREE_TYPEID}" auto="true" />
											<u:show showId="show4" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_DEGREE_TYPEID}" />
										</td>
									</tr>
									<tr>
										<td class="bggrey">个人照片</td>
										<td colspan="3">
											<u:upload id="expert5" groups="expert1,expert2,expert3,expert4,expert5,expert6,expert7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_PHOTO_TYPEID}" auto="true" />
											<u:show showId="show5" groups="show1,show2,show3,show4,show5,show6,show7" businessId="${sysId}" sysKey="${expertKey}" typeId="${typeMap.EXPERT_PHOTO_TYPEID}" />
										</td>
									</tr>
								</tbody>
							</table>
						</ul>
					</div>
				</div>
			</div>
	</body>

</html>
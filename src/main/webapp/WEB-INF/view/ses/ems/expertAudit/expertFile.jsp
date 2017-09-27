<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="up" uri="/tld/upload"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
        <%@ include file="/WEB-INF/view/common/webupload.jsp"%>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/ses/ems/expertAudit/merge_jump.js"></script>
		<script type="text/javascript">
			$(function () {
			    // 导航栏显示
                $("#reverse_of_four").attr("class","active");
                $("#reverse_of_four").removeAttr("onclick");
            })
			//下一步
			function nextStep() {
				var status = ${status};
				var sign = $("input[name='sign']").val();
				if(sign == 2 || status == 10 || status == 5){
					var action = "${pageContext.request.contextPath}/expertAudit/preliminaryInfo.html";
				}else{
					var action = "${pageContext.request.contextPath}/expertAudit/reasonsList.html";
				}
				
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}

			//上一步
			function lastStep() {
				var action = "${pageContext.request.contextPath}/expertAudit/product.html";
				$("#form_id").attr("action", action);
				$("#form_id").submit();
			}
		</script>
		<script type="text/javascript">
			function trim(str){ //删除左右两端的空格
				return str.replace(/(^\s*)|(\s*$)/g, "");
				}

			function reason(obj,str){
				var status = ${status};
        var sign = $("input[name='sign']").val();
        //只能审核可以审核的状态
        if(status ==-2 || status == 0 || status == 15|| status == 16|| status == 9 || (sign ==3 && status ==6) || status ==4){
				  var expertId = $("#expertId").val();
				  var showId =  obj.id+"1";
			    $("#"+obj.id+"").each(function() {
			      auditField = $(this).parents("li").find("span").text().replace("：","");
	    		});
	    		var auditContent = auditField + "附件信息";
					var index = layer.prompt({
				    title : '请填写不通过的理由：', 
				    formType : 2, 
				    offset : '100px',
				    maxlength : '50',
					}, 
			    function(text){
			    	var text = trim(text);
					  if(text != null && text !=""){
						    $.ajax({
						      url:"${pageContext.request.contextPath}/expertAudit/auditReasons.html",
						      type:"post",
						      dataType:"json",
						      data:"suggestType=five"+"&auditContent="+auditContent+"&auditReason="+text+"&expertId="+expertId+"&auditField="+auditField + "&auditFalg=" + sign,
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
								$("#"+showId+"").css('visibility', 'visible');
					      layer.close(index);
				      }else{
				      	layer.msg('不能为空！', {offset:'100px'});
				      }
				    });
					}
		  	}
		  	
		  	//暂存
       function zancun(){
         var expertId = $("#expertId").val();
         $.ajax({
           url: "${pageContext.request.contextPath}/expertAudit/temporaryAudit.do",
           dataType: "json",
           data:{expertId : expertId},
           success : function (result) {
               layer.msg(result, {offset : [ '100px' ]});
           },error : function(){
             layer.msg("暂存失败", {offset : [ '100px' ]});
           }
         });
       }
		</script>
	</head>

	<body>
		<!--面包屑导航开始-->
		<div class="margin-top-10 breadcrumbs ">
			<div class="container">
				<ul class="breadcrumb margin-left-0">
					<li>
						<a href="javascript:jumppage('${pageContext.request.contextPath}/login/home.html')"> 首页</a>
					</li>
					<li>
						<a href="javascript:void(0)">支撑系统</a>
					</li>
					<li>
						<a href="javascript:void(0)">专家管理</a>
					</li>
					<c:if test="${sign == 1}">
						<li>
							<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=1')">专家初审</a>
						</li>
					</c:if>
					<c:if test="${sign == 2}">
						<li>
							<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=2')">专家复审</a>
						</li>
					</c:if>
					<c:if test="${sign == 3}">
						<li>
							<a href="javascript:void(0)" onclick="jumppage('${pageContext.request.contextPath}/expertAudit/list.html?sign=3')">专家复查</a>
						</li>
					</c:if>
				</ul>
				<div class="clear"></div>
			</div>
		</div>
		<div class="container container_box">
			<div class=" content height-350">
				<div class="col-md-12 tab-v2 job-content">
					<%@include file="/WEB-INF/view/ses/ems/expertAudit/common_jump.jsp" %>
					<ul class="ul_list hand count_flow">
						<li class="col-md-6 col-sm-6 col-xs-12 p0 mt10 mb25">
							<span <c:if test="${fn:contains(fileModify,'14')}"> style="border: 1px solid #FF8C00;"</c:if> class="col-md-5 padding-left-5" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="application" onclick="reason(this);">军队评审专家承诺书：</span>
								<up:show showId="14" delete="false" businessId="${expertId}" sysKey="${expertKey}" typeId="14" />
								<a style="visibility:hidden" id="application1" class='abolish'><img style="padding-right: 120px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
								<c:if test="${fn:contains(conditionStr,'军队评审专家承诺书')}">
								  <a class='abolish'>
								    <img style="padding-right: 120px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
								  </a>
								</c:if>
						</li>
						<li class="col-md-6 col-sm-6 col-xs-12  p0 mt10 mb25">
							<span <c:if test="${fn:contains(fileModify,'13')}"> style="border: 1px solid #FF8C00;"</c:if> class="col-md-5 padding-left-5" onmouseover="this.style.background='#E8E8E8'" onmouseout="this.style.background='#FFFFFF'" id="contract" onclick="reason(this);">军队评审专家入库申请表：</span>
								<up:show showId="13"  delete="false" businessId="${expertId}" sysKey="${expertKey}" typeId="13" />
								<a style="visibility:hidden" id="contract1" class='abolish'><img style="padding-right: 120px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'></a>
								<c:if test="${fn:contains(conditionStr,'军队评审专家入库申请表')}"> 
								  <a class='abolish'>
								    <img style="padding-right: 120px;" src='${pageContext.request.contextPath}/public/backend/images/sc.png'>
								  </a>
								</c:if>
						</li>
					</ul>
				</div>
				<div class="col-md-12 col-sm-12 col-xs-12  add_regist tc">
					<a class="btn" type="button" onclick="lastStep();">上一步</a>
					<c:if test="${status == -2 || status == 0 || status == 9 || (sign ==3 && status ==6) || status ==4}">
					  <a class="btn padding-left-20 padding-right-20 btn_back margin-5" onclick="zancun();">暂存</a>
					</c:if>
					<a class="btn" type="button" onclick="nextStep();">下一步</a>
				</div>
				
			</div>
		</div>

		<input value="${expertId}" id="expertId" type="hidden">

		<form id="form_id" action="" method="post">
			<input name="expertId" value="${expertId}" type="hidden">
			<input name="sign" value="${sign}" type="hidden">
		</form>
        <input value="${status}" id="status" type="hidden">
	</body>

</html>
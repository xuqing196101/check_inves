<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<style type="text/css">
.panel-title>a {
	color: #333
}
</style>
<link href="${pageContext.request.contextPath}/public/ztree/css/ztree-extend.css" type="text/css" rel="stylesheet" >
<script src="${pageContext.request.contextPath}/js/oms/purchase/jquery.metadata.js"></script>
<script src="${pageContext.request.contextPath}/js/oms/purchase/layer-extend.js"></script>
<script src="${pageContext.request.contextPath}/js/oms/purchase/select-tree.js"></script>
<script src="${pageContext.request.contextPath}/js/oms/purchase/validate-extend.js"></script>
<script type="text/javascript">
	var setting = {
		view : {
			dblClickExpand : false
		},
		async : {
			autoParam : [ "id" ],
			enable : true,
			url : "${pageContext.request.contextPath}/purchaseManage/gettree.do",
			dataType : "json",
			type : "post",
		},
		data : {
			simpleData : {
				enable : true,
				idKey : "id",
				pId : "pId",
				rootPId : -1,
			}
		},
		callback : {
			beforeClick : beforeClick,
			onClick : onClick
		}
	};
	$(document).ready(function() {
		$.fn.zTree.init($("#treeDemo"), setting, datas);
	});
</script>
<script type="text/javascript">
	 $(document).ready(function(){
	 	var proviceId = $("#pid").val();
		//console.dir(proviceId);
		
	 	$.ajax({
		    type: 'post',
		    url: "${pageContext.request.contextPath}/purchaseManage/getProvinceList.do?",
		    data : {pid:1},
		    success: function(data) {
		    	$("#city").append("<option value='-1'>请选择</option>");
		    	$("#province").append("<option value='-1'>请选择</option>");
			    $.each(data, function(idx, item) {
					if(item.id==proviceId){
						var html = "<option value='" + item.id + "' selected>" + item.name
						+ "</option>";
						$("#province").append(html);
						loadCities(proviceId);
					}else{
						var html = "<option value='" + item.id + "'>" + item.name
						+ "</option>";
						$("#province").append(html);
					}
				});
				if(proviceId!=null && proviceId!="" && proviceId!=undefined){
					//loadCities(proviceId);
				}
            	/*  var optionHTML="<select name=\"province\" onchange=\"loadCities(this.value)\">";
	             var optionHTML="";
				  optionHTML+="<option value=\""+"-1"+"\">"+"清选择"+"</option>"; 
				  for(var i=0;i<data.length;i++){
			       // console.dir(data[i].id);
			        optionHTML+="<option value=\""+data[i].id+"\">"+data[i].name+"</option>"; 
				  }
				  optionHTML+="</select>";
				  $("#province").html(optionHTML);//将数据填充到省份的下拉列表中
				  console.dir(optionHTML); */
		    }
		});
		
    });
    function loadCities(pid){
    	$("#pid").val(pid);
    	var cityId = $("#cid").val();
    	$.ajax({
		    type: 'post',
		    url: "${pageContext.request.contextPath}/purchaseManage/getProvinceList.do?",
		    data : {pid:pid},
		    success: function(data) {
		    	$.each(data, function(idx, item) {
					if(item.id==cityId){
						var html = "<option value='" + item.id + "' selected>" + item.name
						+ "</option>";
						$("#city").append(html);
					}else{
						var html = "<option value='" + item.id + "'>" + item.name
						+ "</option>";
						$("#city").append(html);
					}
					
				});
             /* var optionHTML="";
			  optionHTML+="<option value=\""+"-1"+"\">"+"清选择"+"</option>"; 
			  for(var i=0;i<data.length;i++){
		       // console.dir(data[i].id);
		        optionHTML+="<option value=\""+data[i].id+"\">"+data[i].name+"</option>"; 
			  }
			  optionHTML+="</select>";
			  $("#city").html(optionHTML);//将数据填充到省份的下拉列表中
			  //console.dir(optionHTML); */
		    }
		});
    }
    function loadTown(pid){
    	$("#cid").val(pid);
    }
	function update(){
		$.ajax({
		    type: 'post',
		    url: "${pageContext.request.contextPath}/purchase/updateAjax.do?",
		    data : $("#formID").serialize(),
		    //data: {'pid':pid,$("#formID").serialize()},
		    success: function(data) {
		        truealert(data.message,data.success == false ? 5:1);
		    }
		});
		
	}
	function submit(){
		$("#formID").submit();
	}
	function truealert(text,iconindex){
		layer.open({
		    content: text,
		    icon: iconindex,
		    shade: [0.3, '#000'],
		    yes: function(index){
		        //do something
		         //parent.location.reload();
		    	 layer.closeAll();
		    	 //parent.layer.close(index); //执行关闭
		    	 parent.location.href="${pageContext.request.contextPath}/purchase/list.do";
		    }
		});
	}
	function pageOnload(){
		var gender = $("#gender-select").val();
		var purcahserType = $("#purcahserType-select").val();
		var quaLevel  = $("#quaLevel-select").val();
		if(gender!=null && gender!=""&&gender!="null" && gender!=undefined){
			$("#gender").val(gender);
		}
		if(purcahserType!=null && purcahserType!=""&&purcahserType!="null" && purcahserType!=undefined){
			$("#purcahserType").val(purcahserType);
		}
		if(quaLevel!=null && quaLevel!=""&&quaLevel!="null" && quaLevel!=undefined){
			$("#quaLevel").val(quaLevel);
		}
	}
</script>
</head>
<body onload="pageOnload();">

	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="#"> 首页</a>
				</li>
				<li><a href="#">支撑系统</a>
				</li>
				<li><a href="#">采购机构管理</a>
				</li>
				<li class="active"><a href="#">修改采购人</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 修改订列表开始-->
	<div class="container container_box">
		<div id="menuContent" class="menuContent divpopups menutree">
			<ul id="treeDemo" class="ztree"></ul>
		</div>
		<form action="${pageContext.request.contextPath}/purchase/update.do" method="post" id="formID" enctype="multipart/form-data">
		      <input class="hide"  name="id" type="hidden" value="${purchaseInfo.id }">
              <input class="hide" name="userId" type="hidden" value="${purchaseInfo.userId }">
			<div>
			    <h2 class="count_flow"><i>1</i>修改基本信息</h2>
			         <ul class="ul_list">
					     <li class="col-md-3 col-sm-6 col-xs-12 pl15"> 
					       <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">姓名</span>
					       <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
					        <input class="input_group" name="relName" type="text" value="${purchaseInfo.relName }"> 
					        <span class="add-on">i</span>
					        <div class="b f18 ml10 red hand">${name_msg}</div>
					       </div>
					     </li>
					     <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">性别</span>
                                        <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                                            <input type="hidden" id="gender-select" value="${purchaseInfo.gender }"/>
                                            <select  name="gender" id="gender"> 
                                                <option value="">-请选择-</option>
                                                <option value="M">男</option>
                                                <option value="F">女</option>
                                            </select> 
                                        </div>
                          </li>
                          <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">省</span>
                                        <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                                            <select name="provinceId" id="province" onchange="loadCities(this.value);"> 
                                            </select>
                                            <input type="hidden" name="purchaseInfo.provinceId" id="pid" value="${purchaseInfo.provinceId }">
                                        </div>
                           </li>
                           <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">市</span>
                                        <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                                            <select name="cityId" id="city" onchange="loadTown(this.value);">
                                            </select> 
                                            <input type="hidden" name="purchaseInfo.cityId" id="cid" value="${purchaseInfo.cityId }">
                                        </div>
                            </li>
                            <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">民族</span>
                                        <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                                            <input class="input_group" name="nation" value="${purchaseInfo.nation }"
                                                type="text"> <span class="add-on">i</span>
                                        </div>
                             </li>
                             <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">政治面貌</span>
                                        <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                                            <input class="input_group" name="political" value="${purchaseInfo.political }"
                                                type="text"> <span class="add-on">i</span>
                                        </div>
                            </li>
                            <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">＊</i>出生年月</span>
                                        <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                                            <input class="input_group" type="text" readonly="readonly" onClick="WdatePicker()" name="birthAt" value="${purchaseInfo.birthAt}" />
                                        </div>
                            </li>
                            <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">身份证号</span>
                                        <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                                            <input class="input_group" name="idCard" value="${ purchaseInfo.idCard}"
                                                type="text"> <span class="add-on">i</span>
                                        </div>
                            </li>
                            <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">人员类别</span>
                                        <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                                            <input type="hidden" id="purcahserType-select" value="${purchaseInfo.purcahserType }"/>
                                            <select name="purcahserType" id="purcahserType"> 
                                                <option value="">-请选择-</option>
                                                <option value="0">军人</option>
                                                <option value="1">文职</option>
                                                <option value="2">职工</option>
                                                <option value="3">战士</option>
                                            </select>
                                        </div> 
                           </li>
                           <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">职称</span>
                                        <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                                            <input class="input_group" name="professional" type="text" value="${ purchaseInfo.professional}"> <span
                                                class="add-on">i</span>
                                        </div>
                            </li>
                            <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">所属采购机构</span>
                                        <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                                            <input id="proSec" type="text" class="input_group" readonly value="${purchaseInfo.purchaseDepName }" name="purchaseDepName"  onclick="showMenu(); return false;"/>
                                            <input type="hidden"  id="treeId" name="purchaseDepId" value="${purchaseInfo.purchaseDepId }"  class="text"/>
                                        </div>
                            </li>
                            <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">职务</span>
                                        <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                                            <input class="input_group" name="duties" type="text" value="${ purchaseInfo.duties}"> <span
                                                class="add-on">i</span>
                                        </div>
                           </li>
                           <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">年龄</span>
                                        <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                                            <input class="input_group" name="age" type="text" value="${ purchaseInfo.age}"> <span
                                                class="add-on">i</span>
                                        </div>
                            </li>
					  </ul>
							</div>
							<div class="padding-top-10 clear">
							     <h2 class="count_flow"><i>2</i>专业信息</h2>
							     <ul class="ul_list">
							         <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">学历</span>
                                        <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                                            <input class="input_group" name="topStudy" type="text" value="${ purchaseInfo.topStudy}"> <span
                                                class="add-on">i</span>
                                        </div>
                                     </li>
                                     <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">毕业院校</span>
                                        <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                                            <input class="input_group" name="graduateSchool" type="text" value="${ purchaseInfo.graduateSchool}"> <span
                                                class="add-on">i</span>
                                        </div>
                                    </li>
                                    <li class="col-md-12 col-sm-12 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">工作经历</span>
                                        <div class="col-md-12 col-sm-12 col-xs-12 p0">
                                            <textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px" name="workExperience" value="${ purchaseInfo.workExperience}"
                                                maxlength="400" title="" placeholder=""></textarea>
                                        </div>
                                    </li>
                                    <li class="col-md-12 col-sm-12 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">培训经历</span>
                                        <div class="col-md-12 col-sm-12 col-xs-12 p0">
                                            <textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px" name="trainExperience" value="${ purchaseInfo.trainExperience}"
                                                maxlength="400" title="" placeholder=""></textarea>
                                        </div>
                                    </li>
							     </ul>
							</div>
							<div class="padding-top-10 clear">
							     <h2 class="count_flow"><i>3</i>资质信息</h2>
							     <ul class="ul_list">
							         <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">资质编号</span>
                                        <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                                            <input class="input_group" name="quaCode" type="text" value="${ purchaseInfo.quaCode}"> <span
                                                class="add-on">i</span>
                                        </div>
                                    </li>
                                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购资质范围</span>
                                        <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                                            <input class="input_group" name="quaRank" type="text" value="${ purchaseInfo.quaRank}"> <span
                                                class="add-on">i</span>
                                        </div>
                                    </li>
                                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">＊</i>采购资质开始日期</span>
                                        <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                                            <input class="Wdate w230" type="text" readonly="readonly" onClick="WdatePicker()" name="quaStartDate"  value="${purchaseInfo.quaStartDate}"/> 
                                        </div>
                                    </li>
                                   <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><i class="red">＊</i>采购资质截止日期</span>
                                        <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                                            <input class="Wdate w230" type="text" readonly="readonly" onClick="WdatePicker()" name="quaEdndate" value="${purchaseInfo.quaEdndate}"/> 
                                        </div>
                                    </li>
                                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">采购资质等级</span>
                                        <div class="select_common col-md-12 col-sm-12 col-xs-12 p0">
                                            <input type="hidden" id="quaLevel-select" value="${purchaseInfo.quaLevel }"/>
                                            <select  name="quaLevel" id="quaLevel">
                                                <option value="">-请选择-</option>
                                                <option value="0">初</option>
                                                <option value="1">中</option>
                                                <option value="2">高</option>
                                            </select> 
                                        </div>
                                    </li>
                                    <li id="tax_li_id" class="col-md-3 margin-0 padding-0"><span class="zzzx"><i class="red">＊</i> 采购资格证书图片</span>
                                        <c:if test="${purchaseInfo.quaCert != null}">
                                            <div>
                                                <a class="color7171C6" href="javascript:void(0)"
                                                    onclick="downloadFile('${purchaseInfo.quaCert}')">下载附件</a>
                                                <a title="重新上传" class="ml10 red fz17"
                                                    href="javascript:void(0)" onclick="uploadNew('tax_li_id')">☓</a>
                                            </div>
                                        </c:if>
                                         <c:if test="${purchaseInfo.quaCert == null}">
                                                <div class="uploader orange h32 m0">
                                                    <input type="text" class="filename fz11 h32 m0"
                                                        readonly="readonly" /> <input type="button" name="file"
                                                        class="button" value="选择..." /> <input name="quaCert"
                                                        type="file" size="30" />
                                                </div>
                                        </c:if>
                                    </li>
							     </ul>
							</div>
							<div class="padding-top-10 clear">
							    <h2 class="count_flow"><i>4</i>人员信息</h2>
							   <ul class="ul_list">
							         <li class="col-md-3 col-sm-6 col-xs-12 pl15"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">手机号码</span>
                                        <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                                            <input class="input_group" name="mobile" type="text" value="${ purchaseInfo.mobile}"> <span
                                                class="add-on">i</span>
                                        </div>
                                    </li>
                                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">办公号码</span>
                                        <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                                            <input class="input_group" name="telephone" type="text" value="${ purchaseInfo.telephone}"> <span
                                                class="add-on">i</span>
                                        </div>
                                    </li>
                                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">传真号码</span>
                                        <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                                            <input class="input_group" name="fax" type="text" value="${ purchaseInfo.fax}"> <span
                                                class="add-on">i</span>
                                        </div>
                                    </li>
                                    <li class="col-md-3 col-sm-6 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">邮政编码</span>
                                        <div class="input-append col-md-12 col-sm-12 col-xs-12 p0">
                                            <input class="input_group" name="postCode" type="text" value="${ purchaseInfo.postCode}"> <span
                                                class="add-on">i</span>
                                        </div>
                                    </li>
                                    <li class="col-md-12 col-sm-12 col-xs-12"><span class="col-md-12 col-sm-12 col-xs-12 padding-left-5">联系地址</span>
                                        <div class="col-md-12 col-sm-12 col-xs-12 p0">
                                            <textarea class="col-md-12 col-sm-12 col-xs-12" style="height:130px" name="address" value="${ purchaseInfo.address}"
                                                maxlength="400" title="" placeholder=""></textarea>
                                        </div>
                                    </li>
							   </ul>
							 </div>
							         <div class="col-md-12">
						                <div class="mt40 tc  mb50 ">
						                    <!-- <button type="button" class="btn  padding-right-20 btn_back margin-5" onclick="submit();">确认</button> -->
						                    <button type="button" class="btn btn-windows git" onclick="update();">确认</button> 
						                    <!-- <button type="button" class="btn  padding-right-20 btn_back margin-5" onclick="stash();">暂存</button> -->
						                    <button type="button" class="btn btn-windows cancel" onclick="history.go(-1)">取消</button>
						                </div>
						            </div>
							</form>
						</div>
			
</body>
</html>

<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<%@ include file="../../../common.jsp"%>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>修改采购机构</title>
<!-- Meta -->

<script type="text/javascript">
	var num1 =1;
	var num2 =1;
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
	var array =[];
	 var deltr =function(index,name)
    {
        //var _len = $("#tab tr").length;
        //console.dir(index);
        //console.dir(index.id);
        //console.dir($("tr[id='" + index.id + "']"));
        var deldata = index+","+name;
        array.remove(deldata);
        $("tr[id='" + index + "']").remove();//删除当前行   
		var num = $("#tab tbody tr").length;
		var trs = $("#tab tbody tr");
		for (i = 0; i < num; i++) {
			trs.find("td:eq(1)").each(function(i) {
				$(this).text(i + 1);
			});
		}
	};
	 $(document).ready(function(){
	 	var proviceId = $("#pid").val();
		//console.dir(proviceId);
		
	 	$.ajax({
		    type: 'post',
		    url: "${pageContext.request.contextPath}/purchaseManage/getProvinceList.do?",
		    data : {pid:1},
		    success: function(data) {
		    	$("#city").append("<option value='-1'>请选择</option>");
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
		    url: "${pageContext.request.contextPath}/purchaseManage/updatePurchaseDepAjxa.do?",
		    data : $('#formID').serialize(),
		    //data: {'pid':pid,$("#formID").serialize()},
		    success: function(data) {
		        truealert(data.message,data.success == false ? 5:1);
		    }
		});
		
	}
	function stash(){
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
		    	 parent.location.href="${pageContext.request.contextPath}/purchaseManage/purchaseUnitList.do";
		    }
		});
	}
	function pageOnload(){
		var proviceId = $("#pid").val();
		console.dir(proviceId);
		var cityId = $("#cid").val();
		var isAudit = $("#cid").val();
		$("#province").val('A4CCB12438AD4E49AADE355B3B02910C');
		$("#province").get(0).selectedIndex=proviceId;
		$("#province option[value ='"+proviceId+"']").attr("selected", true);//val(2);
		$("#city").val(cityId);
		//$("#provinceId").val(proviceId);
		//$("div.panel-collapse").addClass("in");
		
	}
	function dynamicaddThree(){
    	var typeName = $("#typeName").val();
        showiframe("添加机构",1000,600,"${pageContext.request.contextPath}/purchaseManage/addPurchaseOrg.do?typeName="+typeName,"-4");
    }
    function showiframe(titles,width,height,url,top){
		 if(top == null || top == "underfined"){
		  top = 120;
		 }
		layer.open({
	        type: 2,
	        title: [titles,"background-color:#83b0f3;color:#fff;font-size:16px;text-align:center;"],
	        maxmin: true,
	        shade: [0.3, '#000'],
	       	offset: top+"px",
	        shadeClose: false, //点击遮罩关闭层 
	        area : [width+"px" , height+"px"],
	        content: url
	    });
	}
	function addOffice(){
		$.ajax({
		    type: 'post',
		    url: "${pageContext.request.contextPath}/purchaseManage/addOffice.do?",
		    data : {num:num1},
		    success: function(data) {
		    	console.dir(data);
		        $("#tab-position").append(data.message);
		        num1++;
		    }
		});
		
		
	}
	function addOffice1(){
		countAdd();
		$("#tab-position").append("<tr id="+num1+" align='center'>"
            					+"<td>"+num1+"</td>"     
                                +"<td><select class='purchaseRoomTypeName' id=purchaseRoomTypeName"+num1+" name='purchaseRoomTypeName'> <option value=''>请选择</option><option value='0'>办公室</option><option value='1'>会议室</option><option value='2'>招标室</option><option value='3'>评标室</option></select></td>"
                                //+"<td><input type='text' name='desc"+_len+"' id='name"+_len+"' value='"+_len+"' /></td>"
                                 +"<td><input id=purchaseRoomCode"+num1+" name='purchaseRoomCode' style='width:100px;'/></td>"
                                +"<td><input name='purchaseRoomLocation' style='width:100px;'/></td>"
                                   +"<td><input name='purchaseRoomArea' style='width:100px;'/></td>"
                                    +"<td><input name='purchaseRoomNetConnectStyle' style='width:100px;'/></td>"
                                     +"<td><input name='purchaseRoomCapacity' style='width:100px;'/></td>"
                                    +"<td><select name='purchaseRoomIsNetConnect'> <option value='-1'>请选择</option><option value='0'>是</option><option value='1'>否</option></select></td>"
                                    +"<td><select name='purchaseRoomHasVideoSys'> <option value='-1'>请选择</option><option value='0'>是</option><option value='1'>否</option></select></td>"
                                +"<td><a href=\'#\' onclick=\'delPositionTr(this)\'>删除</a></td>"
                            +"</tr>"); 
                            num++;
	}
	//统计
	var bg_office=0;
	var hy_office=0;
	var zb_office=0;
	var pb_office=0;
	var area=0;
	function countAdd(){
		var num = $("#tab-position tbody tr").length;
		var trs = $("#tab-position tbody tr");
		
		var t =$("select.purchaseRoomTypeName");
		for(j=0;j<t.length;j++){
			if(t[j].value!=undefined && t[j].value=="0"){
				bg_office++;
			}else if(t[j].value=="1"){
				hy_office++;
			}
			else if(t[j].value=="2"){
				zb_office++;
			}
			else if(t[j].value=="3"){
				pb_office++;
			}
		}
		for (i = 0; i < num; i++) {
			trs.find("td:eq(4)").each(function(i) {
				area = Number($(this).text())+Number(area);
			});
		}  
		console.dir(bg_office);
		console.dir(area);
	}
	function addOrg(){
		$("#tab-orgnization").append("<tr id="+num2+" align='center'>"
            					+"<td>"+num2+"</td>"     
                                +"<td><input name='purchaseUnitName'/></td>"
                                 +"<td><input name='purchaseUnitDuty'/></td>"
                                +"<td><a href=\'javascript:void(0);\' onclick=\'delOrgTr(this)\'>删除</a></td>"
                            +"</tr>"); 
                            num2++;
	}
	function delOrgTr(obj){
		var tr=obj.parentNode.parentNode;
        tr.parentNode.removeChild(tr);
		//$(obj).parent.remove();//删除当前行   
		var num = $("#tab-orgnization tbody tr").length;
		var trs = $("#tab-orgnization tbody tr");
		console.dir(trs.find("td:eq(0)"));
		for (i = 0; i < num; i++) {
			trs.find("td:eq(0)").each(function(i) {
				$(this).text(i + 1);
			});
		}  
		num2--;
	}
	function delPositionTr(obj){
		var tr=obj.parentNode.parentNode;
        tr.parentNode.removeChild(tr);
		//$(obj).parent.remove();//删除当前行   
		var num = $("#tab-position tbody tr").length;
		var trs = $("#tab-position tbody tr");
		console.dir(trs.find("td:eq(0)"));
		for (i = 0; i < num; i++) {
			trs.find("td:eq(0)").each(function(i) {
				$(this).text(i + 1);
			});
		}  
		num1--;
	}
</script>
<script type="text/javascript">
	$(document).ready(function() {
		$("#formID").validate({
			focusInvalid : false, //当为false时，验证无效时，没有焦点响应  
			onkeyup : false,
			rules : {
				levelDep : {
					required : true,
					isZipCode : true
				},
				purchaseRoomTypeName:{
					required : true
				},
				purchaseRoomCode:{
					required : true
				}
			},
			messages : {
				levelDep : {
					required : "必填项 !",
					valiEnglish : "邮编6位数字组成!"
				},
				purchaseRoomTypeName : {
					required : "必填项 !",
					valiEnglish : "邮编6位数字组成!"
				},
				purchaseRoomCode : {
					required : "必填项 !",
					valiEnglish : "邮编6位数字组成!"
				}
			},
			showErrors: function(errorMap, errorList) {
	           $.each(this.successList, function(index, value) {
	             return $(value).popover("hide");
	           });
           	   return $.each(errorList, function(index, value) {
             		var _popover;
             		_popover = $(value.element).popover({
                    trigger: "manual",
                    placement: "top",
                    content: value.message,
                    template: "<div class=\"popover\"><div class=\"arrow\"></div> <div class=\"popover-inner\"><div class=\"popover-content\"><p></p></div></div></div>"
               });
             _popover.data("bs.popover").options.content = value.message;
             return _popover.popover("show");
           });
         }
		});
	 });
</script>
</head>
<body onload="pageOnload();">
	<!--面包屑导航开始-->
	<div class="margin-top-10 breadcrumbs ">
		<div class="container">
			<ul class="breadcrumb margin-left-0">
				<li><a href="javascript:void(0);"> 首页</a>
				</li>
				<li><a href="javascript:void(0);">支撑系统</a>
				</li>
				<li><a href="javascript:void(0);">采购机构管理</a>
				</li>
				<li class="active"><a href="javascript:void(0);">修改采购机构</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>
	<div class="wrapper">
	<!-- 修改订列表开始-->
	<div class="container">
		<form action="${pageContext.request.contextPath}/purchaseManage/updatePurchaseDep.do" method="post" id="formID">
			<input type="hidden" value="${purchaseDep.typeName }" name="orgnization.typeName"/>
			<div class="padding-top-10">
				<div class="headline-v2">
					<h2>修改采购机构</h2>
				</div>
				<!-- 伸缩层 -->
						<div class="margin-bottom-0">
							<h2 class="f16 jbxx mt40">
								<i>01</i>基本信息
							</h2>
						</div>
						<input class="hide"  name="orgnization.id" type="hidden" value="${purchaseDep.orgId }">
						<input class="hide" name="id" type="hidden" value="${purchaseDep.id }">
								<ul class="list-unstyled list-flow p0_20">
									<li class="col-md-6 p0"><span class="">采购机构名称：</span>
										<div class="input-append">
											<input class="span2" name="orgnization.name" type="text" value="${purchaseDep.name }"> <span
												class="add-on">i</span>
											<div class="b f18 ml10 red hand">${name_msg}</div>
										</div>
									</li>
									<li class="col-md-6 p0"><span class="">采购机构简称：</span>
										<div class="input-append">
											<input class="span2" name="orgnization.shortName" type="text" value="${purchaseDep.shortName }"> <span
												class="add-on">i</span>
											<div class="b f18 ml10 red hand">${name_msg}</div>
										</div>
									</li>
									
									<li class="col-md-6  p0 "><span class="">采购机构单位级别：</span>
										<div class="input-append">
											<input class="span2" name="levelDep" type="text" value="${purchaseDep.levelDep }"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">行政隶属单位：</span>
										<div class="input-append">
											<input class="span2" name="subordinateOrgName" value="${purchaseDep.subordinateOrgName }"
												type="text"> <span class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">采购业务范围：</span>
										<div class="input-append">
											<input class="span2" name="businessRange" value="${purchaseDep.businessRange }"
												type="text"> <span class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6 p0"><span class="">采购机构地址：</span>
										<div class="input-append">
											<input class="span2" name="orgnization.address" type="text" value="${purchaseDep.address }"> <span
												class="add-on">i</span>
											<div class="b f18 ml10 red hand">${name_msg}</div>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">邮编：</span>
										<div class="input-append">
											<input class="span2" name="orgnization.postCode" value="${ purchaseDep.postCode}"
												type="text"> <span class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">传真号：</span>
										<div class="input-append">
											<input class="span2" name="fax" type="text" value="${ purchaseDep.fax}"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">省：</span>
											<div class="input-append">
											 <select
												class="span2" name="provinceId" id="province"
												onchange="loadCities(this.value);">
											</select> <input type="hidden" name="orgnization.provinceId" id="pid"
												value="${purchaseDep.provinceId }">
												</div>
												</li>
											<li class="col-md-6  p0 "><span class="">市：</span>
												<div class="input-append">
												 <select
													class="span2" name="cityId" id="city"
													onchange="loadTown(this.value);">
												</select>
											 	<input type="hidden" name="orgnization.cityId" id="cid"
												value="${purchaseDep.cityId }">
												</div>
											</li>
												
											<li class="col-md-6  p0 "><span class="">值班室电话：</span>
												<div class="input-append">
													<input class="span2" name="dutyRoomPhone" type="text"
														value="${ purchaseDep.dutyRoomPhone}"> <span
														class="add-on">i</span>
												</div></li>
											<li class="col-md-6  p0 "><span class="">是否具有审核供应商：</span>
												<select class="span2" name="isAuditSupplier">
													<option value="">-请选择-</option>
													<option value="1">是</option>
													<option value="0">否</option>
											</select></li>
									<div class="clear"></div>
								</ul>
					<!--  class="panel panel-default" -->
						<div class="margin-bottom-0">
							<h2 class="f16 jbxx mt40">
								<i>02</i>资质信息
							</h2>
						</div>
								<ul class="list-unstyled list-flow p0_20">
									<li class="col-md-6 p0"><span class="">采购资质等级：</span>
										<select class="span2" name="quaLevel" type="text"> 
											<option value="">-请选择-</option>
											<option value="1">一级</option>
											<option value="2">二级</option>
											<option value="3">三级</option>
											<option value="4">四级</option>
											<option value="5">五级</option>
											<option value="6">六级</option>
											<option value="7">七级</option>
											<option value="8">八级</option>
											<option value="9">九级</option>
										</select>
									</li>
									<li class="col-md-6  p0 "><span class="">采购资质范围：</span>
										<select class="span2" name="quaRange" type="text">
											<option value="">-请选择-</option>
											<option value="1">综合</option>
											<option value="1">物资</option>
											<option value="1">工程</option>
											<option value="1">服务</option>
										</select>
									</li>
									<li class="col-md-6  p0 "><span class=""><i class="red">＊</i>采购资质开始日期：</span>
										<div class="input-append">
											<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="quaStartDate" value="<fmt:formatDate value="${purchaseDep.quaStartDate}" pattern="yyyy-MM-dd" />" /> 
											<span class="add-on"> <img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class=""><i class="red">＊</i>采购资质截止日期：</span>
										<div class="input-append">
											<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="quaEdndate" value="<fmt:formatDate value="${purchaseDep.quaEdndate}" pattern="yyyy-MM-dd" />"/> 
											<span class="add-on"> <img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
										</div>
									</li>
									<%-- <li>
										<div>
											<input id="d12" type="text"/>
											<img onclick="WdatePicker({el:'d12'})" src="${pageContext.request.contextPath}/public/My97DatePicker/skin/datePicker.gif" width="16" height="22" align="absmiddle">
										</div>
									</li> --%>
									<li class="col-md-6  p0 "><span class="">采购资质编号：</span>
										<div class="input-append">
											<input class="span2" name="quaCode" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6 p0"><span class=""><i class="red">＊</i>采购资格证书图片：</span>
										<div class="uploader orange m0">
											<input type="text" class="filename h32 m0 fz11" readonly="readonly" value="未选择任何文件..." /> 
											<input type="button" class="button" value="选择文件..." /> 
											<input type="file" size="30" accept="image/*" />
										</div>
									</li>
								</ul>
								<div class="clear"></div>
						<div class="margin-bottom-0">
							<h2 class="f16 jbxx mt40">
								<i>03</i>部门信息
							</h2>
						</div>
								<div class="mt40 mb50">
									<button type="button"
										class="btn  padding-right-20 btn_back margin-5"
										id="dynamicAdd" onclick="addOrg();">添加部门</button>
								</div>
								<div>
									<table class="table table-bordered table-condensed" id="tab-orgnization">
										<thead>
											<tr>
												<th class="info f13">序号</th>
												<th class="info f13">部门名称</th>
												<th class="info f13">主要职责</th>
												<th class="info f13">操作</th>
											</tr>
										</thead>
										<tbody>
											
										</tbody>
									</table>
									</div>
									<div class="clear"></div>
						<div class="margin-bottom-0">
							<h2 class="f16 jbxx mt40">
								<i>04</i>个人信息
							</h2>
						</div>
								<ul class="list-unstyled list-flow p0_20">
									<li class="col-md-6 p0"><span class="">单位主要领导姓名及电话：</span>
										<div class="input-append">
											<input class="span2" name="leaderTelephone" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">军官编制人数：</span>
										<div class="input-append">
											<input class="span2" name="officerCountnum" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">军官现有人数：</span>
										<div class="input-append">
											<input class="span2" name="officerNowCounts" 
												type="text"> <span class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">士兵现有人数：</span>
										<div class="input-append">
											<input class="span2" name="soldierNowCounts" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6 p0"><span class="">士兵编制人数：</span>
										<div class="input-append">
											<input class="span2" name="soldierNum" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">职工编制人数：</span>
										<div class="input-append">
											<input class="span2" name="staffNum" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">职工现有人数：</span>
										<div class="input-append">
											<input class="span2" name="staffNowCounts" 
												type="text"> <span class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">具备采购资格人员数量：</span>
										<div class="input-append">
											<input class="span2" name="purchasersCount" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">初级采购师人数：</span>
										<div class="input-append">
											<input class="span2" name="juniorPurCount" 
												type="text"> <span class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">高级采购师人数：</span>
										<div class="input-append">
											<input class="span2" name="seniorPurCount" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
								</ul>
					<div class="clear"></div>
						<div class="margin-bottom-0">
							<h2 class="f16 jbxx mt40">
								<i>05</i>甲方信息
							</h2>
						</div>
								<ul class="list-unstyled list-flow p0_20">
									<li class="col-md-6 p0"><span class="">单位名称：</span>
										<div class="input-append">
											<input class="span2" name="depName" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">法定代表人：</span>
										<div class="input-append">
											<input class="span2" name="legal" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">委托代理人：</span>
										<div class="input-append">
											<input class="span2" name="agent" 
												type="text"> <span class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">联系人：</span>
										<div class="input-append">
											<input class="span2" name="contact" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
										<li class="col-md-6  p0 "><span class="">联系电话：</span>
										<div class="input-append">
											<input class="span2" name="contactTelephone" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">通讯地址：</span>
										<div class="input-append">
											<input class="span2" name="contactAddress" 
												type="text"> <span class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">邮政编码：</span>
										<div class="input-append">
											<input class="span2" name="unitPostCode" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
										<li class="col-md-6  p0 "><span class="">付款单位：</span>
										<div class="input-append">
											<input class="span2" name="payDep" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">开户银行：</span>
										<div class="input-append">
											<input class="span2" name="bank" 
												type="text"> <span class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">银行账号：</span>
										<div class="input-append">
											<input class="span2" name="bankAccount" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
								</ul>
					<div class="clear"></div>
						<div class="margin-bottom-0">
							<h2 class="f16 jbxx mt40">
								<i>06</i>场所信息
							</h2>
						</div>
								<ul class="list-unstyled list-flow p0_20">
									 <li class="col-md-6 p0"><span class="">办公场地总面积：</span>
										<div class="input-append">
											<input class="span2" name="officeArea" type="text" value="${purchaseDep.officeArea}"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">办公司数量：</span>
										<div class="input-append">
											<input class="span2" name="officeCount" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">会议室数量：</span>
										<div class="input-append">
											<input class="span2" name="mettingRoomCount" 
												type="text"> <span class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">招标室数量：</span>
										<div class="input-append">
											<input class="span2" name="inviteRoomCount" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">评标室数量：</span>
										<div class="input-append">
											<input class="span2" name="bidRoomCount" 
												type="text"> <span class="add-on">i</span>
										</div>
									</li>
								</ul>
								<div class="clear"></div>
									<input type="button"
										class="btn"
										id="dynamicAdd" onclick="addOffice();" value="添加场所"></input>
								<!-- style="width:700px; height:1225px; overflow:scroll;" width="732px" -->
								<div
									class="content padding-left-25 padding-right-25 padding-top-5 .f13" >
									<table class="table table-bordered table-condensed" id="tab-position" >
										<thead>
											<tr>
												<th class="info f13">序号</th>
												<th class="info f13">类型</th>
												<th class="info f13">编号</th>
												<th class="info f13">位置</th>
												<th class="info f13">面积</th>
												<th class="info f13">接入方式</th>
												<th class="info f13">容纳人员数量</th>
												<th class="info f13">是否介入网络</th>
												<th class="info f13">是否具备监控系统</th>
												<th class="info f13">操作</th>
											</tr>
										</thead>
										<tbody>
											
										</tbody>
									</table>
								</div>
								<div class="clear"></div>
						<div class="margin-bottom-0">
							<h2 class="f16 jbxx mt40">
								<i>07</i>选择采购管理部门
							</h2>
						</div>
								<div class="mt40 mb50">
									<button type="button"
										class="btn  padding-right-20 btn_back margin-5"
										id="dynamicAdd" onclick="dynamicaddThree();">添加监管部门</button>
								</div>
								<div
									class="content padding-left-25 padding-right-25 padding-top-5">
									<table class="table table-bordered table-condensed" id="tab">
										<thead>
											<tr>
												<th class="info w30"><input id="checkAll"
													type="checkbox" onclick="selectAll()" />
												</th>
												<th class="info w50">序号</th>
												<th class="info">机构名称</th>
												<th class="hide">机构id</th>
												<th class="info">操作</th>
											</tr>
										</thead>
										<tbody>

										</tbody>
									</table>
								</div>
				<!-- 伸缩层 -->
				<div class="clear"></div>
			
			
		  </div>
		  <div class="col-md-12">
				<div class="mt40 tc  mb50 ">
					<button type="button" class="btn  padding-right-20 btn_back margin-5" onclick="update();">确认</button>
					<button type="button" class="btn  padding-right-20 btn_back margin-5" onclick="stash();">暂存</button> 
					<button type="button" class="btn  padding-right-20 btn_back margin-5" onclick="history.go(-1)">取消</button>
				</div>
			</div>
		</form>
	</div>
	</div>
</body>
</html>

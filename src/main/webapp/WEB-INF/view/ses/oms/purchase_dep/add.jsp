<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ include file="../../../common.jsp"%>
<!DOCTYPE html>
<html class=" js cssanimations csstransitions" lang="en">
<!--<![endif]-->
<head>
<style type="text/css">
.panel-title>a
{
	color: #333
}
	
</style>
<script type="text/javascript">
	function save(){
		var index = parent.layer.getFrameIndex(window.name); 
		var pid = parent.$("#parentid").val();
		console.dir(pid);
		$.ajax({
		    type: 'post',
		    url: "${pageContext.request.contextPath}/purchaseManage/saveOrg.do?",
		    data : $.param({'parentId':pid}) + '&' + $('#formID').serialize(),
		    //data: {'pid':pid,$("#formID").serialize()},
		    success: function(data) {
		        truealert(data.message,data.success == false ? 5:1);
		    }
		});
		
	}
	function truealert(text,iconindex){
		layer.open({
		    content: text,
		    icon: iconindex,
		    shade: [0.3, '#000'],
		    yes: function(index){
		        //do something
		         parent.location.reload();
		    	 layer.closeAll();
		    	 parent.layer.close(index); //执行关闭
		    	 //parent.location.href="${pageContext.request.contextPath}/purchaseManage/list.do";
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
				<li><a href="#"> 首页</a>
				</li>
				<li><a href="#">支撑系统</a>
				</li>
				<li><a href="#">后台管理</a>
				</li>
				<li class="active"><a href="#">需求部门管理</a>
				</li>
			</ul>
			<div class="clear"></div>
		</div>
	</div>

	<!-- 修改订列表开始-->
	<div class="container">
		<form action="${pageContext.request.contextPath}/purchaseManage/createPurchaseDep.do" method="post" id="formID">
			<input type="hidden" value="2" name="typeName"/>
			<div>
				<div class="headline-v2">
					<h2>新增采购机构</h2>
				</div>
				<!-- 伸缩层 -->
				<div class="panel-group" id="accordion">
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion" 
								   href="#collapseOne">
									1、基本信息
								</a>
							</h4>
						</div>
						<div id="collapseOne" class="panel-collapse collapse in">
							<div class="panel-body">
								<ul class="list-unstyled list-flow p0_20">
									<li class="col-md-6 p0"><span class="">采购机构名称：</span>
										<div class="input-append">
											<input class="span2" name="name" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">采购机构单位级别：</span>
										<div class="input-append">
											<input class="span2" name="subordinateOrgName" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">行政隶属单位：</span>
										<div class="input-append">
											<input class="span2" name="mobile" 
												type="text"> <span class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">采购业务范围：</span>
										<div class="input-append">
											<input class="span2" name="businessRange" 
												type="text"> <span class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">单位地址：</span>
										<div class="input-append">
											<input class="span2" name="address" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">邮编：</span>
										<div class="input-append">
											<input class="span2" name="postCode" 
												type="text"> <span class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">传真号：</span>
										<div class="input-append">
											<input class="span2" name="fax" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">值班室电话：</span>
										<div class="input-append">
											<input class="span2" name="dutyRoomPhone" type="text"> <span
												class="add-on">i</span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class="">省：</span>
										<select class="span2" name="provinceId"> 
											<option value="">-请选择-</option>
										</select>
									</li>
									<li class="col-md-6  p0 "><span class="">市：</span>
										<select class="span2" name="cityId">
											<option value="">-请选择-</option>
										</select> 
									</li>
									<li class="col-md-6  p0 "><span class="">是否具有审核供应商：</span>
										<select class="span2" name="isAuditSupplier">
											<option value="">-请选择-</option>
											<option value="1">是</option>
											<option value="0">否</option>
										</select> 
									</li>
								</ul>
							</div>
						</div>
					</div>
					<!--  class="panel panel-default" -->
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion" 
								   href="#collapseTwo">
									2、资质信息
								</a>
							</h4>
						</div>
						<div id="collapseTwo" class="panel-collapse collapse">
							<div class="panel-body">
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
											<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="quaStartDate" /> 
											<span class="add-on"> <img src="${pageContext.request.contextPath}/public/ZHQ/images/time_icon.png" class="mb10" /> </span>
										</div>
									</li>
									<li class="col-md-6  p0 "><span class=""><i class="red">＊</i>采购资质截止日期：</span>
										<div class="input-append">
											<input class="span2" type="text" readonly="readonly" onClick="WdatePicker()" name="quaEdndate" /> 
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
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion" 
								   href="#collapseThree">
									3、机构信息
								</a>
							</h4>
						</div>
						<div id="collapseThree" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="mt40  mb50 ">
									<button type="button" class="btn  padding-right-20 btn_back margin-5">添加</button>
									<button type="button" class="btn  padding-right-20 btn_back margin-5">删除</button>
								</div>
								<div>
									<table>
										<tr><td>2</td><td>3</td><td>4</td></tr>
									</table>
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion" 
								   href="#collapseFour">
									4、人员信息
								</a>
							</h4>
						</div>
						<div id="collapseFour" class="panel-collapse collapse">
							<div class="panel-body">
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
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion" 
								   href="#collapseFive">
									5、甲方信息
								</a>
							</h4>
						</div>
						<div id="collapseFive" class="panel-collapse collapse">
							<div class="panel-body">
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
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion" 
								   href="#collapseSix">
									6、场所信息
								</a>
							</h4>
						</div>
						<div id="collapseSix" class="panel-collapse collapse">
							<div class="panel-body">
								<ul class="list-unstyled list-flow p0_20">
									<li class="col-md-6 p0"><span class="">办公场地总面积：</span>
										<div class="input-append">
											<input class="span2" name="officeArea" type="text"> <span
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
								<div class="uploader orange m0 fr">
									<input type="button" class="button" value="添加" /> 
								</div>
							</div>
						</div>
					</div>
					<div class="panel panel-default">
						<div class="panel-heading">
							<h4 class="panel-title">
								<a data-toggle="collapse" data-parent="#accordion" 
								   href="#collapseSeven">
									7、选择采购管理部门
								</a>
							</h4>
						</div>
						<div id="collapseSeven" class="panel-collapse collapse">
							<div class="panel-body">
								<div class="mt40  mb50 ">
									<button type="button" class="btn  padding-right-20 btn_back margin-5">添加</button>
									<button type="button" class="btn  padding-right-20 btn_back margin-5">删除</button>
								</div>
								<div>
									<table>
										<tr><td>2</td><td>3</td><td>4</td></tr>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- 伸缩层 -->
			</div>

			<div class="col-md-12">
				<div class="mt40 tc  mb50 ">
					<button type="button" class="btn  padding-right-20 btn_back margin-5">确认</button>
					<button type="button" class="btn  padding-right-20 btn_back margin-5">暂存</button>
					<button type="button" class="btn  padding-right-20 btn_back margin-5">取消</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>

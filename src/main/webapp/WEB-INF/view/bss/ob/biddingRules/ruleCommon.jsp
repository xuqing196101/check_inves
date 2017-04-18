<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<script type="text/javascript">
</script>
</head>
<body>
   <!-- 发布定型产品页面开始 -->
  		  <input name="ruleCommonid" type="hidden" value="${ obRule.id }" />
		  <div>
		   <ul class="ul_list">
		     <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>竞价规则名称：</span>
			   <div class="input-append input_group col-sm-12 col-xs-12 p0" id="supplierselect">
					<input class="input_group"  type="text" disabled="disabled" value="${obRule.name }" class="w230 mb0 border0">
		        <div class="cue"><span><font id="nameErr" style="color: red"></font></span></div>
		       </div>
			 </li>
			 
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>间隔工作日（天）：</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0" id="supplierselect">
			        <input class="input_group"  type="text" disabled="disabled" value="${ obRule.intervalWorkday }"  class="w230 mb0 border0">
					<div class="cue"><span><font id="intervalWorkdayErr" style="color: red"></font></span></div>
		       </div>
			 </li>
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 padding-left-5 col-sm-12 col-xs-12"><div class="red star_red">*</div>竞价开始时间：</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0" id="supplierselect">
				    <input type="text"  maxlength="100" disabled="disabled" value='<fmt:formatDate value="${ obRule.definiteTime }" pattern="HH:mm:ss"/>' id="d242" onclick="WdatePicker({dateFmt:'HH:mm:ss'})"  class="Wdate" />
					<div class="cue"><span><font id="definiteTimeErr" style="color: red"></font></span></div>
		       </div>
			 </li> 
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>第一轮报价时间（分钟）：</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0" id="supplierselect">
				    <input class="input_group"   disabled="disabled" type="text" value="${ obRule.quoteTime }" class="mb0 border0">
					<div class="cue"><span><font id="quoteTimeErr" style="color: red"></font></span></div>
		       </div>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>第二轮报价时间（分钟）：</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0">
				    <input class="input_group"  disabled="disabled" type="text" value="${ obRule.quoteTimeSecond }"  class="mb0 border0">
					<div class="cue"><span><font id="quoteTimeSecondErr" style="color: red"></font></span></div>
		       </div>
			 </li>
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>第一轮确认时间（分钟）：</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0" id="supplierselect">
				    <input class="input_group"  type="text" disabled="disabled" value="${ obRule.confirmTime }" class="mb0 border0">
					<div class="cue"><span><font id="confirmTimeErr" style="color: red"></font></span></div>
		       </div>
			 </li> 
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>第二轮确认时间（分钟）</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0" id="supplierselect">
				    <input class="input_group"  disabled="disabled" type="text" value="${ obRule.confirmTimeSecond }" class="mb0 border0" />
					<div class="cue"><span><font id="confirmTimeSecondErr" style="color: red"></font></span></div>
		       </div>
			 </li> 
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>最少报价供应商数</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0" id="supplierselect">
				    <input class="input_group"  disabled="disabled" type="text" value="${ obRule.leastSupplierNum }" class="mb0 border0" />
					<div class="cue"><span><font id="leastSupplierNumErr" style="color: red"></font></span></div>
		       </div>
			 </li> 
			 
			   <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>有效报价百分比</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0" >
				    <input class="input_group"  type="text" class="mb0 border0" onkeyup="this.value=this.value.replace(/\D/g,'')"
				     onafterpaste="this.value=this.value.replace(/\D/g,'')" disabled="disabled"  value="${ obRule.percent }">
					<div class="cue"><span><font id="percentErr" style="color: red"></font></span></div>
		       </div>
			 </li> 
			  <c:if test="${not empty obRule.floatPercent}">
			    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span class="col-md-12 col-sm-12 col-xs-12 padding-left-5"><div class="red star_red">*</div>浮动百分比</span>
			   <div class="input-append input_group col-md-12 col-sm-12 col-xs-12 p0" >
				    <input class="input_group"  type="text" class="mb0 border0" 
				     disabled="disabled"  value="${ obRule.floatPercent }">
					<div class="cue"><span><font id="percentErr" style="color: red"></font></span></div>
		       </div>
			 </li> 
			 </c:if>
		   </ul>
		       <div class="clear"></div> 
		  </div> 
</body>
</html>
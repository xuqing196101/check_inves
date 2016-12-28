<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML >
<html>
<head>
	<jsp:include page="/WEB-INF/view/common.jsp"/>
    <title>合同草稿修改</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/public/ztree/css/zTreeStyle.css"> 
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/ztree/jquery.ztree.core.js"></script>
    <script language="javascript" type="text/javascript" src="${pageContext.request.contextPath}/public/layer/layer.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/public/layer/extend/layer.ext.js"></script>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
	function dayin() {
		var LODOP = getLodop();
		if (LODOP) {
			LODOP.PRINT_INIT("打印表格"); 
			LODOP.ADD_PRINT_TABLE("0","0","100%","100%",document.getElementById("newTable").innerHTML);
			
		    LODOP.PREVIEW(); 
		}
	}
	</script>
  </head>
<body>
 <div class="container" id="newTable">
 <h2 class="tc"><span class="tdu">${purCon.name}</span>采购合同<span class="fr">密级</span></h2>
 <div class="mt25 tc"><span class="fl">合同编号：${purCon.code}</span><span>签订日期：</span><span class="fr">签订地点：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></div> 
  <table class="table table-bordered mb0 mt5 contract_table">
    <thead>
	  <tr>
	    <th colspan="2" width="50%">甲方</th>
		<th colspan="2" width="50%">乙方</th>
	  </tr>
	 </thead>
	 <tbody>
	  <tr>
	   <th width="15%">单位名称</th>
	   <td width="35%">${purCon.purchaseDepName}</td>
	   <th width="15%">单位名称</th>
	   <td width="35%">${purCon.supplierDepName}</td>
	  </tr>
	  <tr>
	   <th width="15%">法定代表人</th>
	   <td width="35%">${purCon.purchaseLegal}</td>
	   <th width="15%">法定代表人</th>
	   <td width="35%">${purCon.supplierLegal}</td>
	  </tr>
	  <tr>
	   <th width="15%">委托代理人</th>
	   <td width="35%">${purCon.purchaseAgent}</td>
	   <th width="15%">委托代理人</th>
	   <td width="35%">${purCon.supplierAgent}</td>
	  </tr>
	  <tr>
	   <th width="15%">联系人</th>
	   <td width="35%">${purCon.purchaseContact}</td>
	   <th width="15%">联系人</th>
	   <td width="35%">${purCon.supplierContact}</td>
	  </tr>
	  <tr>
	   <th width="15%">联系电话</th>
	   <td width="35%">${purCon.purchaseContactTelephone}</td>
	   <th width="15%">联系电话</th>
	   <td width="35%">${purCon.supplierContactTelephone}</td>
	  </tr>
	  <tr>
	   <th width="15%">通讯地址</th>
	   <td width="35%">${purCon.purchaseContactAddress}</td>
	   <th width="15%">通讯地址</th>
	   <td width="35%">${purCon.supplierContactAddress}</td>
	  </tr>
	  <tr>
	   <th width="15%">邮政编码</th>
	   <td width="35%">${purCon.purchaseUnitpostCode}</td>
	   <th width="15%">邮政编码</th>
	   <td width="35%">${purCon.supplierUnitpostCode}</td>
	  </tr>
	  <tr>
	   <th width="15%">付款单位</th>
	   <td width="35%">${purCon.purchasePayDep}</td>
	   <th width="15%">开户名称</th>
	   <td width="35%">${purCon.supplierBankName}</td>
	  </tr>
	  <tr>
	   <th width="15%">开户银行</th>
	   <td width="35%">${purCon.purchaseBank}</td>
	   <th width="15%">开户银行</th>
	   <td width="35%">${purCon.supplierBank}</td>
	  </tr>
	  <tr>
	   <th width="15%">银行账号</th>
	   <td width="35%">${purCon.purchaseBankAccount_string}</td>
	   <th width="15%">银行账号</th>
	   <td width="35%">${purCon.supplierBankAccount_string}</td>
	  </tr>
	  <tr class="btomnone">
	    <td colspan="4" class="btomnone" >
	     <p><div class="fl">一、</div><div class="fl mr10">计划任务文号：<span class="tdu">${purCon.documentNumber}</span></div>
		     <div class="fl mr10">合同批准文号：<span class="tdu">${purCon.approvalNumber}</span></div>
			 <div class="fl mr10">采购机构资格证号：<span class="tdu">${purCon.quaCode}</span></div>
		 </p>
		 <p class="clear"><div class="fl">二、</div><div class="mr10">合同标的：</div></p> 
        </td>
	  </tr>
	  </table>
	  
	  <table class="table table-bordered contract_table btomnone">
	   <tr>
	    <th>序号</th>
		<th>编号</th>
		<th>物资名称</th>
		<th>品牌商标</th>
		<th>规格型号</th>
		<th>计量单位</th>
		<th>数量</th>
		<th>单价(元)</th>
		<th>合计金额(元)</th>
		<th>交付时间</th>
		<th>备注</th>
	  </tr>
	  <c:forEach items="${requList}" var="requ" varStatus="vs">
	  <tr>
	    <td>${(vs.index+1)}</td>
		<td class="tl pl20">${requ.planNo}</td>
		<td class="tl pl20">${requ.goodsName}</td>
		<td class="tl pl20">${requ.brand}</td>
		<td class="tl pl20">${requ.stand}</td>
		<td class="tl pl20">${requ.item}</td>
		<td class="tl pl20">${requ.purchaseCount}</td>
		<td class="tl pl20">${requ.price}</td>
		<td class="tl pl20">${requ.amount}</td>
		<td class="tl pl20">${requ.deliverDate}</td>
		<td class="tl pl20">${requ.memo}</td>
	  </tr>
	  </c:forEach>
	  <tr>
	    <td  colspan="11" >
	     <p class="mb0 b">合计：${purCon.money_string}人民币（      ）金额（大写）    亿    仟    佰    拾    万    仟    佰    拾    元    角    分   （小写）￥：
		 </p>
        </td>
	  </tr>
	  <tr>
	    <td  colspan="11" class="contract_desc">
	  <p>三、 质量标准  乙方提供的货物必须是全新的、未使用过的，物资质量应符合　□国际标准　□国家标准　□国军标　□行业标准　□企业标准 □设计任务书　□投标书或投标文件承诺 
 □其它<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>。</p>
 
 <p>四、 包装及资料 物资包装应符合  □国际标准　□国家标准　□国军标　□行业标准　□企业标准 □设计任务书　□投标书或投标文件承诺  □其它<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>。
□物资出厂资料  □中文使用操作说明书（   套）　□售后服务手册　□操作维修光盘（   套）  □履历书　□装箱清单　□随装工具  □随装备件 □质量检验证明  
□产品合格证  □军检合格证  □装备铭牌（块数、式样、材质、安装位置供需双方商定）□其它 <ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins> 。</p>

<p>五、 检验验收  □出厂验收由<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>组织，乙方配合。  □乙方详细生产地址 <ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>。
□交货验收<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>□过程检验<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>□其它<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>。</p>

<p>六、 交货地点  □乙方<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>□甲方发运接收单指定地点  □其它 <ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>。</p>

<p>七、 交货方式  □甲方自提　□乙方送货　□甲方负责申请（□公路  □铁路  □水运  □航空）军事运输计划组织发运，乙方配合 □其他<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>。</p>

<p>八、 运输费用  □甲方承担，乙方代垫，凭票据报销 □乙方承担  □甲方承担费用<ins>&nbsp;&nbsp;&nbsp;&nbsp;</ins>元，乙方包干使用  □其它<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>。</p>

<p>九、 售后服务  □售后服务联系方式：固定电话<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>，手机<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>，电子邮箱<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>。
□质保期<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>。质保期内<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>，超出质保期后<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>。
□保修期<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>。保修期内<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>，超出保修期后<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>。
□培训方式及费用承担<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>。
部队之日起<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins> □乙方承诺在战时和平时特殊情况下的军事行动中优先向甲方提供有关支援服务。□其他<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>。</p>

<p>十、资金结算  □自本合同签订生效之日起<ins>&nbsp;&nbsp;&nbsp;</ins>天内，甲方向乙方支付合同总金额的<ins>&nbsp;&nbsp;&nbsp;</ins>%的预付款。□首检合格后再办理预付款。物资检验验收合格并完成交货后<ins>&nbsp;&nbsp;&nbsp;</ins>天内，甲方凭乙方提供的相关票据单证，向乙方支付合同总金额的<ins>&nbsp;&nbsp;&nbsp;</ins>%， □余<ins>&nbsp;&nbsp;&nbsp;</ins>%作为质量保证金，自交货之日起□三个月 □六个月 □十二个月 □<ins>&nbsp;&nbsp;&nbsp;</ins>月正常使用且无质量问题时，一次性结清。□最终结算按审价报告执行。</p>

<p>十一、知识产权  乙方应保证甲方使用其提供的物资时不受第三方关于侵犯专利权、商标权和工业设计权的指控，甲方不承担任何连带责任和赔偿责任。</p>

<p>十二、保密责任 □甲方对乙方的商业秘密应当保密  □乙方对本合同的签订、履行及解除等事项保密， □涉及物资的全部技术资料等未经甲方同意乙方不得向社会公开  □乙方应对甲方委托送货的发运单、接收单位目录和售后服务单位目录等资料，按密级管理，不得泄密。 □其他<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>。</p>

<p>十三、合同变更与解除  □甲乙双方任何一方要求变更、解除或终止合同时，应及时通知对方，并采用书面形式由双方达成协议  □甲方因任务取消等情况，可以变更或解除合同，给乙方造成损失的，甲方应当赔偿  □乙方不能履行合同时，甲方有权解除合同，给甲方造成损失的，乙方应当赔偿   □未经甲方允许，乙方不得部分或全部转让其应履行的合同义务  □产品出厂验收不合格，甲方有权拒收货物和支付货款，由此造成的一切损失由乙方承担 □其他<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>。</p>

<p>十四、违约责任 甲乙任何一方未经对方允许而违约时，违约方应向对方支付违约金，按 <ins>&nbsp;&nbsp;&nbsp;</ins>执行，违约金最高限额为合同总金额的<ins>&nbsp;&nbsp;&nbsp;</ins>%，违约金达到最高限额违约方仍不能完全履行合同时，另一方可以终止合同，造成的实际损失大于最高违约金时，违约方要给予足额赔偿。甲乙双方任何一方由于不可抗力影响合同履行时都要在灾害发生36小时内将情况通知另一方，在灾害发生后14天内向另一方出具权威部门的证明文件。如果不可抗力影响连续120天以上时，双方可以重新商定合同履行问题。</p>

<p>十五、合同争议解决方式 □甲乙双方协商解决　□提交甲方或乙方主管部门调解  □提交<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins> 仲裁委员会仲裁  □依法向 <ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>人民法院起诉。</p>

<p>十六、合同生效 □草本合同由甲乙双方法定代表人或委托代理人签字并经单位盖章，由甲方报上级有关部门审批通过后，签订正式合同。
<br>□正式合同由甲乙双方法定代表人或委托代理人签字并经单位盖章后生效。
<br>正式合同一式<ins>&nbsp;&nbsp;&nbsp;</ins>份，正本两份，甲乙双方各执一份，副本<ins>&nbsp;&nbsp;&nbsp;</ins>份，正副本合同具有同等法律效力。□合同有效期限<ins>&nbsp;&nbsp;&nbsp;</ins> </p>                                  。

<p>十七、合同附件 1.交货清单 □2.主要技术指标参数 □3.售后服务承诺 □4.易损易耗件清单。</p>

<p>十八、其    他<ins>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</ins>。</p>

<p>十九、未尽事宜由甲乙双方协商确定。</p> 
	    
        </td>
	  </tr>
	  </table>
		<input type="button" class="btn" value="打印" onclick="dayin()"/>
		<input type="button" class="btn" value="返回" onclick="javaScript"/>
	 </tbody>
  </table>
 </div>
</body>
</html>

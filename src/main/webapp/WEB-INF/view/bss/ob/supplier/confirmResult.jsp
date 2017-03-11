<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
<title>确认结果页面</title>
<script type="text/javascript">
	//倒计时方法
	function getRTime(){
		var getOverTime = $("#confirmOverTime").text();
		var EndTime= new Date('2017-3-10 18:00:00'); //截止时间
		var NowTime = new Date();
		var t =EndTime.getTime() - NowTime.getTime();
		if(t > 0) {
			var d = Math.floor(t/1000/60/60/24);
			var h = Math.floor(t/1000/60/60%24);
			var m = Math.floor(t/1000/60%60);
			var s = Math.floor(t/1000%60);
			$("#confirmCountDown").text(d + "天" + h + "时" + m + "分" + s + "秒");
		} else {
			$("#confirmCountDown").text("确认倒计时已经结束");
		}
	}
	//占比下调,返回对应占比后数量@param 值
	function getDownRatioVal(passVal,beforeRatio,afterRatio) {
		var br = parseInt(beforeRatio)/100;
		var ar = parseInt(afterRatio)/100;
		return (parseInt(passVal) / br) * ar;
	}
	$(function() {
		//定时器调用
		setInterval(getRTime,1000);
		
		var currentVal = $("input[name='confirmRatioFirst']").val();
		$("input[name='confirmRatioFirst']").keyup(function(event) {
			var currentPressKey = event.keyCode;//当前输入的字符
			var afterInputVal = $(this).val();
			if(currentPressKey >= 48 && currentPressKey <= 57 || currentPressKey == 8) {
				if(parseInt(afterInputVal) > parseInt(currentVal)) {
					$(this).val(currentVal);
					layer.alert("占比只能下调");
				} else if(parseInt(afterInputVal) == 0) {
					$(this).val(currentVal);
					layer.alert("占比不能为0");
				} else {
					var changeRatioCounts = [];
					var productPrices = [];
					var allCount = 0;
					
					$("[title='theProductCount']").each(function(index,element) {
						changeRatioCounts.push($(this).text());
					});
					$("[title='theProductPrice']").each(function() {
						productPrices.push($(this).text());
					});
					var allCount = 0;
					$("[title='theProductTotalPrice']").each(function(index,element) {
						var afterCount = getDownRatioVal(changeRatioCounts[index],currentVal,afterInputVal).toFixed(2);
						$(this).text(afterCount * productPrices[index]);
						allCount += afterCount * productPrices[index];
					});
					$("[title='allProductTotalPrice']").text(allCount);
				}
			} else if(currentPressKey == 13) {
				//删除键和回车，放行
			} else {
				$(this).val(currentVal);
				layer.alert("请输入合法数字");
			}
		});
		
	});
	
</script>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">确认结果</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div> 
    
    <!-- 表格开始 -->
    <div class="container container_box">
    
    <c:forEach items="${oBProjectResultList }" var="projectResult" varStatus="p">
    <h2>竞价管理-结果查询
    	<span style="font-weight: lighter;font-size: 18px;padding-left: 22px;">竞价标题：测试-263842312346</span>
    </h2>
    <h2 class="count_flow">排名：${projectResult.ranking }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	状态：中标&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	中标比例:<input id="" name="" readonly="readonly" value="50" type="text" class="w5 mb0 ">%
    </h2>
    <h2 class="count_flow">确认结束时间：<span id="confirmOverTime">2016-1-1 12：12：12</span></h2>
    
     <div>
     <div class="clear total f22">
     	<span class="fl block">基本数量确认：</span>
     	<h2 class="count_flow">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确认成交&nbsp;&nbsp;
     		<input id="" name="confirmRatioFirst" value="50" type="text" class="w5 mb0 ">%
     		<span style="padding-left: 22px;">确认倒计时：<span id="confirmCountDown"></span></span>
     	</h2>
     </div>
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info">序号</th>
		  <th class="info">产品名称</th>
		  <th class="info">数量</th>
		  <th class="info">自报单价（元）</th>
		  <th class="info">成交单价（元）</th>
		  <th class="info">成交总价（元）</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc">1</td>
		  <td class="tc">台式计算机</td>
		  <td class="tc" title="theProductCount">20</td>
		  <td class="tc">100</td>
		  <td class="tc" title="theProductPrice">100</td>
		  <td class="tc" title="theProductTotalPrice">1000</td>
		</tr>
		<tr>
		  <td class="tc">2</td>
		  <td class="tc">便携式式计算机</td>
		  <td class="tc" title="theProductCount">20</td>
		  <td class="tc">200</td>
		  <td class="tc" title="theProductPrice">200</td>
		  <td class="tc" title="theProductTotalPrice">4000</td>
		</tr>
		<tr>
		  <td class="tc">3</td>
		  <td class="tc">服务器</td>
		  <td class="tc" title="theProductCount">10</td>
		  <td class="tc">300</td>
		  <td class="tc" title="theProductPrice">300</td>
		  <td class="tc" title="theProductTotalPrice">3000</td>
		</tr>
		<tr>
		  <td class="tc"></td>
		  <td class="tc" colspan="4">合计</td>
		  <td class="tc" title="allProductTotalPrice">12000</td>
		</tr>
	</table>
  </div>
  </div>
  
  <c:if test="${projectResult.status=='0'}">
  <div>
     <div class="clear total f22"><span class="fl block">第二轮确认：</span><h2 class="count_flow">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;确认成交&nbsp;&nbsp;<input id="" name="" value="20" type="text" class="w5 mb0 ">%&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第二轮确认倒计时：0小时5分钟12秒</h2></div>
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info">序号</th>
		  <th class="info">产品名称</th>
		  <th class="info">数量</th>
		  <th class="info">自报单价（元）</th>
		  <th class="info">成交总价（元）</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc">1</td>
		  <td class="tc">台式计算机</td>
		  <td class="tc">20</td>
		  <td class="tc">100</td>
		  <td class="tc">1000</td>
		</tr>
		<tr>
		  <td class="tc">2</td>
		  <td class="tc">便携式式计算机</td>
		  <td class="tc">20</td>
		  <td>200</td>
		  <td class="tc">4000</td>
		</tr>
		<tr>
		  <td class="tc">3</td>
		  <td class="tc">服务器</td>
		  <td class="tc">10</td>
		  <td>300</td>
		  <td class="tc">3000</td>
		</tr>
		<tr>
		  <td class="tc"></td>
		  <td class="tc" colspan="3">合计</td>
		  <td class="tc">12000</td>
		</tr>
	</table>
  </div>
  </div>
  	</c:if>
  </c:forEach>
  
  
  <div class="star_red">规则1、第一轮确认如果都按比例成交，则没有第二轮确认，如果不是按比例成交，则有第二轮确认，第一轮正在确认的时候不显示<br/>第二轮数据，只有所有供应商第一轮确认完毕后，才有第二轮确认。<br/>
						规则2、自动生成成交比例（可修改），修改后成交数量和总价发生变化，未中标的的显示未中标，不显示成交比例，未中标的只能<br/>看到已中标明细，未中标的只有返回按钮。
  </div>
  <div class="col-md-12 clear tc mt10">
  <button class="btn" type="submit">接受</button>
  <button class="btn" type="submit">放弃</button>
  </div>
  </div>
</body>
</html>
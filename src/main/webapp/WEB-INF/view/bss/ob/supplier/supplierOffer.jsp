<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>
<!DOCTYPE HTML>
<html>
	<head>
		<%@ include file="/WEB-INF/view/common.jsp" %>
	<title>供应商报价页面</title>
</head>
<body>
<!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
		   <li><a href="javascript:void(0)">提供单价</a></li><li><a href="javascript:void(0)">供应商报价</a></li>
		   </ul>
        <div class="clear"></div>
      </div>
    </div>
    
    <!-- 修改订列表开始-->
   <div class="container container_box">
   <div class="star_red">提示：报价一旦提交将不能修改，请谨慎提交！</div>
   <div>
    <h2 class="count_flow"><i>1</i>竞价基本信息</h2>
		<table class="table table-bordered mt10">
			    <tbody>
				  <tr>
				    <td class="tc">竞价标题</td>
				    <td class="tc">XXXXXXXXXXXXXX</td>
				    <td class="tc">交货截止时间</td>
				    <td class="tc">2016-01-01</td>
				  </tr>
				  <tr>
				    <td class="tc">交货地点</td>
				    <td class="tc">XXXXXXXXXXXXXX</td>
				    <td class="tc">成交供应商数</td>
				    <td class="tc">3</td>
				  </tr>
				  <tr>
				    <td class="tc">运杂费</td>
				    <td class="tc">300</td>
				    <td class="tc">需求单位</td>
				    <td class="tc">XXXXXXXXXXXXXXX</td>
				  </tr>
				  <tr>
				    <td class="tc">联系人</td>
				    <td class="tc">XXXXX</td>
				    <td class="tc">联系电话</td>
				    <td class="tc">12334567892</td>
				  </tr>
				  <tr>
				    <td class="tc">采购机构</td>
				    <td class="tc">XXXXXXXXXXXX</td>
				    <td class="tc">采购联系人</td>
				    <td class="tc">XXXX</td>
				  </tr>
				  <tr>
				    <td class="tc">联系电话</td>
				    <td class="tc">12345678910</td>
				    <td class="tc">竞价开始时间</td>
				    <td class="tc">2016-1-8 12:12:12</td>
				  </tr>
				  <tr>
				    <td class="tc">竞价结束时间</td>
				    <td class="tc">2016-1-8 12:12:12</td>
				  </tr>
				  <tr>
				    <td class="tc">竞价内容</td>
				    <td class="tc" colspan="3" style="height:130px">2016-1-8 12:12:12</td>
				  </tr>
				  <tr>
				    <td class="tc">竞价文件</td>
				    <td class="tc">XXXXXXXXXXXXXX.pdf</td>
				    <td class="tc"><button class="btn">查看</button></td>
				  </tr>
				 </tbody>
			 </table>
  </div> 
  <div class="clear" ></div>
  <form>
  <div>
    <h2 class="count_flow"><i>2</i>产品信息</h2>
	<div class="content table_box">
    	<table class="table table-bordered table-condensed table-hover table-striped">
		<thead>
		<tr>
		  <th class="w30 info"><input alt="" type="checkbox"></th>
		  <th class="info">定型产品名称</th>
		  <th class="info">限价（元）</th>
		  <th class="info">采购数量</th>
		  <th class="info">报价</th>
		  <th class="info">总价（元）</th>
		  <th class="info">备注信息</th>
		</tr>
		</thead>
		<tr>
		  <td class="tc"><input type="checkbox" alt=""></td>
		  <td class="tc">台式计算机</td>
		  <td class="tc">1000</td>
		  <td class="tc">2</td>
		  <td><input id="" name="" value="500" type="text" class="w230 mb0 border0"></td>
		  <td class="tc">1000</td>
		  <td class="tc">CPU :AD300 内存：2G 硬盘：200G</td>
		</tr>
		<tr>
		  <td class="tc"><input type="checkbox" alt=""></td>
		  <td class="tc">便携式式计算机</td>
		  <td class="tc">3000</td>
		  <td class="tc">5</td>
		  <td><input id="" name="" value="600" type="text" class="w230 mb0 border0"></td>
		  <td class="tc">3000</td>
		  <td class="tc">CPU :AD300 内存：2G 硬盘：200G</td>
		</tr>
		<tr>
		  <td class="tc"><input type="checkbox" alt=""></td>
		  <td class="tc">服务器</td>
		  <td class="tc">4000</td>
		  <td class="tc">5</td>
		  <td><input id="" name="" value="800" type="text" class="w230 mb0 border0"></td>
		  <td class="tc">4000</td>
		  <td class="tc">CPU :AD300 内存：2G 硬盘：200G</td>
		</tr>
		<tr>
		  <td class="tc"><input type="checkbox" alt=""></td>
		  <td class="tc" colspan="4">合计</td>
		  <td class="tc">8000</td>
		  <td class="tc"></td>
		</tr>
	</table>
	<div class="col-md-12 clear tc mt10">
   		<button class="btn btn-windows save" type="submit">确认</button>
   		<button class="btn btn-windows back" type="button" onclick="history.go(-1)">返回</button>
   	</div>
  </div>
  </div> 
  </form>
 </div>
</body>
</html>
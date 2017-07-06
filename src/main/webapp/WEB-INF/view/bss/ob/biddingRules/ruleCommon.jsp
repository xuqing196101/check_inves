<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
</head>
<body>
  		  <div class="tab-content padding-left-20 padding-right-20">
				<table class="table table-bordered">
				 <tbody>
				 <tr>
				  <td class="bggrey" width="18%"> 竞价规则名称：</td>
				  <td width="20%">${obRule.name}</td>
				  <td class="bggrey"  width="17%">间隔工作日（天）：</td>
				  <td with="10%">${ obRule.intervalWorkday}</td>
				  <td class="bggrey"  width="25%">竞价开始时间：</td>
				  <td width="10%"><fmt:formatDate value="${ obRule.definiteTime }" pattern="HH:mm:ss"/></td>
				 </tr> 
				 <tr>
				  <td class="bggrey ">第一轮报价时间（分钟）：</td>
				  <td>${ obRule.quoteTime }</td>
				  <td class="bggrey ">第二轮报价时间（分钟）：</td>
				  <td>${ obRule.quoteTimeSecond }</td>
				  <td class="bggrey ">第一轮确认时间（分钟）：</td>
				  <td>${ obRule.confirmTime }</td>
				 </tr> 
				 <tr>
				  <td class="bggrey ">第二轮确认时间（分钟）：</td>
				  <td>${ obRule.confirmTimeSecond }</td>
				  <td class="bggrey ">最少报价供应商数：</td>
				  <td>${ obRule.leastSupplierNum }</td>
				  <td class="bggrey ">有效供应商报价平均值的百分比（%）：</td>
				  <td>${ obRule.percent }</td>
				 </tr> 
				 <tr>
				  <td class="bggrey ">浮动百分比（%）：</td>
				  <td>${ obRule.floatPercent }</td>
				  <td class="bggrey "></td>
				  <td></td>
				  <td class="bggrey "></td>
				  <td></td>
				 </tr> 
				</tbody>
			   </table>
  		  </div>
		   <%--   <li class="col-md-3 col-sm-6 col-xs-12 pl15">
			   <span >
			          竞价规则名称：${obRule.name }</span>
			 </li>
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span >间隔工作日（天）：${ obRule.intervalWorkday }</span>
			 </li>
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span >竞价开始时间：<fmt:formatDate value="${ obRule.definiteTime }" pattern="HH:mm:ss"/></span>
			 </li> 
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span >第一轮报价时间（分钟）：${ obRule.quoteTime }</span>
			 </li>
			 <li class="col-md-3 col-sm-6 col-xs-12">
			   <span >第二轮报价时间（分钟）：${ obRule.quoteTimeSecond }</span>
			 </li>
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span >第一轮确认时间（分钟）：${ obRule.confirmTime }</span>
			 </li> 
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span >第二轮确认时间（分钟）：${ obRule.confirmTimeSecond }</span>
			 </li> 
		     <li class="col-md-3 col-sm-6 col-xs-12">
			   <span >最少报价供应商数：${ obRule.leastSupplierNum }</span>
			 </li> 
			   <li class="col-md-3 col-sm-6 col-xs-12">
			   <span >有效报价百分比：${ obRule.percent }</span>
			 </li> 
			  <c:if test="${not empty obRule.floatPercent}">
			    <li class="col-md-3 col-sm-6 col-xs-12">
			   <span >浮动百分比：${ obRule.floatPercent }</span>
			 </li> 
			 </c:if>
		   </ul>
		       <div class="clear"></div>  --%>
</body>
</html>
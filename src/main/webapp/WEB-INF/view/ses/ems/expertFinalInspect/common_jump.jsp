<%@ page language="java" pageEncoding="UTF-8"%>
<ul class="flow_step">
    <li id="reverse_of_one" onclick="jump('basicInfo')">
        <a aria-expanded="false" href="#tab-1" data-toggle="tab">基本信息</a><i></i>
    </li>
    <li id="reverse_of_two" onclick="jump('expertType')">
        <a aria-expanded="false" href="#tab-1" data-toggle="tab">专家类别</a><i></i>
    </li>
    <li id="reverse_of_three" onclick="jump('product')">
        <a aria-expanded="false" href="#tab-1" data-toggle="tab">参评类别</a><i></i>
    </li>
    <li id="reverse_of_four" onclick="jump('expertFile')">
        <a aria-expanded="false" href="#tab-1" data-toggle="tab">承诺书和申请表</a><i></i>
    </li>
    <c:if test="${expert.finalInspectCount>0}">
	    <c:forEach var="i" begin="1" end="${expert.finalInspectCount}" step="1">
			<li id="reverse_of_four" onclick="tojump('expertAttachment',${i})">
	        	<a aria-expanded="false" href="#tab-1" data-toggle="tab">历史复查信息</a><i></i>
	    	</li>
		</c:forEach>
    </c:if>
    <!-- 复查 -->
    <c:if test="${sign == 3}">
      <li id="reverse_of_five" onclick="jump('expertAttachment')">
        <a aria-expanded="false" href="#tab-1" data-toggle="tab">专家复查</a>
      </li>
    </c:if>
</ul>

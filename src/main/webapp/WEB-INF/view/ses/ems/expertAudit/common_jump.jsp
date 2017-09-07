<%@ page language="java" pageEncoding="UTF-8"%>
<ul class="flow_step">
    <li id="reverse_of_one" onclick="jump('basicInfo')">
        <a aria-expanded="false" href="#tab-1" data-toggle="tab">基本信息</a><i></i>
    </li>
    <!-- <li onclick="jump('experience')">
        <a aria-expanded="false" href="#tab-1" data-toggle="tab">经历经验</a><i></i>
    </li> -->
    <li id="reverse_of_two" onclick="jump('expertType')">
        <a aria-expanded="false" href="#tab-1" data-toggle="tab">专家类别</a><i></i>
    </li>
    <li id="reverse_of_three" onclick="jump('product')">
        <a aria-expanded="false" href="#tab-1" data-toggle="tab">产品类别</a><i></i>
    </li>
    <li id="reverse_of_four" onclick="jump('expertFile')">
        <a aria-expanded="false" href="#tab-1" data-toggle="tab">承诺书和申请表</a><i></i>
    </li>
    <c:if test="${sign == 2}">
      <li id="reverse_of_seven" onclick="jump('preliminaryInfo')">
          <a aria-expanded="false" href="#tab-1" data-toggle="tab">采购机构初审意见</a><i></i>
          <i id="reverse_of_seven_i"></i>
      </li>
    </c:if>
    <c:if test="${sign == 1 and (expert.status == 10 or status == 10 or expert.status == 5 or status == 5)}">
      <li id="reverse_of_seven" onclick="jump('preliminaryInfo')">
          <a aria-expanded="false" href="#tab-1" data-toggle="tab">专家复审意见</a><i></i>
          <i id="reverse_of_seven_i"></i>
      </li>
    </c:if>
    <li id="reverse_of_five" onclick="jump('reasonsList')">
        <c:if test="${sign == 2}">
          <a aria-expanded="false" href="#tab-1" data-toggle="tab">专家复审意见</a>
        </c:if>
        <c:if test="${sign == 3 || sign == 1}">
          <a aria-expanded="false" href="#tab-1" data-toggle="tab">审核汇总</a>
        </c:if>
        <c:if test="${sign == 1 and (expert.status != 0 and status != 0 and expert.status != 3 and status != 3)}">
          <i id="reverse_of_five_i"></i>
        </c:if>
    </li>
    <c:if test="${sign == 1 and (expert.status != 0 and status != 0 and expert.status != 3 and status != 3)}">
	    <li id="reverse_of_six" onclick="jump('uploadApproveFile')">
	      <a aria-expanded="false" href="#tab-1" data-toggle="tab">上传批准审核表</a>
	    </li>
    </c:if>
</ul>

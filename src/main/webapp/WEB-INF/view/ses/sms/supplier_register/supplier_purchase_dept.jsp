<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

		<style>
			.m_fixInfo {
				position: fixed;
				top: 210px;
				right: 10px;
				background-color: #FFF;
				-webkit-box-shadow: 0 0 5px rgba(0, 0, 0, .2);
				-moz-box-shadow: 0 0 5px rgba(0, 0, 0, .2);
				box-shadow: 0 0 5px rgba(0, 0, 0, .2);
				border: solid 1px #FCBE3F;
				padding: 10px;
				z-index: 1001;
			}
			.m_fixInfo li {
				overflow: hidden;
				margin-top: 10px;
			}
			.m_fixInfo li:first-child {
				margin-top: 0;
			}
			.m_fixInfo li span {
				display: block;
				float: left;
				white-space: nowrap;
				text-overflow: ellipsis;
				overflow: hidden;
				color: #333;
				font-size: 14px;
			}
			.m_fixInfo li span.mfi_tit {
				width: 100px;
				text-align: right;
			}
			.m_fixInfo li span.mfi_info {
				width: 220px;
			}
		</style>
			
		<!-- 右上角漂浮窗 -->
		<c:if test="${!empty sessionScope.orgnization}">
			<ul class="m_fixInfo">
				<li>
					<span class="mfi_tit">采购机构：</span>
					<span class="mfi_info">${sessionScope.orgnization.name}</span>
				</li>
				<li>
					<span class="mfi_tit">联系人：</span>
					<span class="mfi_info">${sessionScope.orgnization.supplierContact}</span>
				</li>
				<li>
					<span class="mfi_tit">联系电话：</span>
					<span class="mfi_info">${sessionScope.orgnization.supplierPhone}</span>
				</li>
			</ul>
		</c:if>
		<!-- 右上角漂浮窗 -->
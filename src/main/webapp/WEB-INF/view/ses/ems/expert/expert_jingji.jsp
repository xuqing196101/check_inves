<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
		<li class="col-md-3 col-sm-6 col-xs-12 pl10">
			<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>执业资格职称</span> 
                    <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
             
                        <input    value="${expertId}"  name="ecoList[${index}].expertId"  type="hidden"/>
                        <input  onblur="tempSave()"  maxlength="20" value=""  name="ecoList[${index}].qualifcationTitle"   type="text"/>
                        <span class="add-on">i</span> <span class="input-tip"></span>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span
                        class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> <i class="red">*</i>执业资格</span>
                    <div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
                        <u:upload
                                singleFileSize="${properties['file.picture.upload.singleFileSize']}"
                                exts="${properties['file.picture.type']}" id="eco_${index}"    businessId="${id}" sysKey="${expertKey}"
                                typeId="9" auto="true" maxcount="20"/>
                        <u:show showId="eco_${index }"  businessId="${id}" sysKey="${expertKey}"
                                typeId="9"/>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span
                        class="col-md-12 col-xs-12 col-sm-12 padding-left-5"><i class="red">*</i>取得执业资格时间</span>
                    <!--/职业资格时间  -->
                    <div
                            class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                        <input onblur="tempSave()"   readonly="readonly" name="ecoList[${index}].titleTime"   type="text" onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})"/> 
                        <span class="add-on">i</span> <span class="input-tip">如：XXXX-XX</span>
                    </div>
                </li>
                
                <li class="col-md-3 col-sm-6 col-xs-12">
					<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
						<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
							<input type="button" onclick="addPractice(2)" class="btn list_btn" value="十" />
							<input type="button" onclick="delPractice(this)" class="btn list_btn" value="一" />
							<input  value="${id }"  name="ecoList[${index }].id"  type="hidden"/>
							</div>
			  </li>

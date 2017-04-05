<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/common/tags.jsp" %>
		<li class="col-md-3 col-sm-6 col-xs-12 pl15">
			<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5">执业资格职称</span> 
                    <div class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
             
                        <input    value="${expertId }"  name="titles[${index }].expertId"  type="hidden"/>
                        <input  onblur="tempSave()"  maxlength="20" value=""  name="titles[${index }].qualifcationTitle"   type="text"/>
                        <span class="add-on">i</span> <span class="input-tip"></span>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span
                        class="col-md-12 col-xs-12 col-sm-12 padding-left-5"> 执业资格</span>
                    <div class="input-append h30 input_group col-sm-12 col-xs-12 col-md-12 p0">
                        <u:upload
                                singleFileSize="${properties['file.picture.upload.singleFileSize']}"
                                exts="${properties['file.picture.type']}" id="expter_${index }" maxcount="1"   businessId="${id}" sysKey="${expertKey}"
                                typeId="9" auto="true"/>
                        <u:show showId="expter_${index }"  businessId="${sysId}" sysKey="${expertKey}"
                                typeId="9"/>
                    </div>
                </li>
                <li class="col-md-3 col-sm-6 col-xs-12"><span
                        class="col-md-12 col-xs-12 col-sm-12 padding-left-5">取得执业资格时间</span>
                    <!--/职业资格时间  -->
                    <div
                            class="input-append input_group col-sm-12 col-xs-12 col-md-12 p0">
                        <input onblur="tempSave()"   readonly="readonly" name="tiles[${index}].titleTime"   type="text" onclick="WdatePicker({lang:'zh-cn',dateFmt:'yyyy-MM'})"/> 
                        <span class="add-on">i</span> <span class="input-tip">如：XXXX-XX</span>
                    </div>
                </li>
                
                <li class="col-md-3 col-sm-6 col-xs-12">
					<span class="col-md-12 col-xs-12 col-sm-12 padding-left-5 white">操作</span>
						<div class="col-md-12 col-xs-12 col-sm-12 p0 mb25 h30">
							<input type="button" onclick="addPractice(this)" class="btn list_btn" value="十" />
							<input type="button" onclick="delPractice(this)" class="btn list_btn" value="一" />
							<input  value="${id }"  name="titles[${index }].id"  type="hidden"/>
							</div>
			  </li>

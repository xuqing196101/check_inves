<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file ="/WEB-INF/view/common/tags.jsp" %>

<!DOCTYPE HTML>
<html>

  <head>
    <%@ include file="/WEB-INF/view/common.jsp"%>
    <script type="text/javascript">
    
    </script>
    <style>
		.flow_tips{
			color:#bbbbbb;
			padding:0px;
			height:70px;
		}
		.flow_tips a{
			display:block;
			width:100%;
			height:100%;
		}
		.flow_tips .tip_main{
			position:relative;
		}
		.tip_btn{
			padding:2px 0px;
			border-radius:4px!important;
			background-color:#e9e9e5;
			z-index:1000;
		}
		.pre_btn a p,.current_btn a p{
			color:#ffffff;
		}
		.flow_tips.pre_btn .tip_btn{
			background-color:#009fa1;
			color:#ffffff;
		}
		.flow_tips.current_btn .tip_btn{
			background-color:#ffb522;
			color:#ffffff;
			z-index:1000;
		}
		.tip_btn a p{
			margin-bottom:0px;
			text-align:center;
			font-size:1.2rem;
		}
		.tip_line{
			position:relative;
			border:2px solid #e9e9e5;
			top:20px;
			padding:0px;
		}
		.flow_tips.current_btn .tip_line{
			border:2px solid #ffb522;
		}
		.flow_tips.pre_btn .tip_line{
			border:2px solid #009fa1;
		}
		.flow_tips.pre_btn .tip_down{
			border: 2px solid #009fa1;
		}
		.tip_down{
			height: 50px;
			border: 2px solid #e9e9e5;
			width: 0px;
			position: absolute;
			top: 25px;
		}
		.current_btn .tip_down{
			border:2px solid #ffb522;
		}
		.last_r{
		float:right;
		} 
		@media (min-width:768px){
		.tip_down{
			left: 29%;
		}
		.tip_down{
		display:none;
		}
		.round_r .tip_down{
		display:block;
	    }
		.round_r .tip_line{
		display:none;
	    }
		.round_l .tip_down{
		display:block;
	    }
		
		}
		@media (max-width:767px){
		
		.tip_down{
		display:block;
	    }
		.tip_line{
		display:none;
	    }
		
		}
	</style>
  </head>

  <body>
    <!--面包屑导航开始-->
    <div class="margin-top-10 breadcrumbs ">
      <div class="container">
        <ul class="breadcrumb margin-left-0">
          <li>
            <a href="javascript:void(0)">首页</a>
          </li>
          <li>
            <a href="javascript:void(0)">业务监管系统</a>
          </li>
          <li>
            <a href="javascript:void(0)">采购业务监督</a>
          </li>
          <li class="active">
            <a href="javascript:void(0)">采购项目监督</a>
          </li>
        </ul>
        <div class="clear"></div>
      </div>
    </div>
    <div class="container container_box">
      <div>
        <h2 class="count_flow"><i>1</i>项目基本信息</h2>
        <ul class="ul_list">
          <table class="table table-bordered mt10">
            <tbody>
              <tr>
                <td width="25%" class="info">项目名称：</td>
                <td width="25%">${project.name}</td>
                <td width="25%" class="info">项目编号：</td>
                <td width="25%">${project.projectNumber}</td>
              </tr>
              <tr>
                <td width="25%" class="info">计划名称：</td>
                <td width="25%">${name}</td>
                <td width="25%" class="info">计划编号：</td>
                <td width="25%">${number}</td>
              </tr>
              <tr>
                <td width="25%" class="info">需求部门：</td>
                <td width="25%"></td>
                <td width="25%" class="info">采购管理部门：</td>
                <td width="25%">${org}</td>
              </tr>
              <tr>
                <td width="25%" class="info">项目状态：</td>
                <td width="25%">${project.status}</td>
                <td width="25%" class="info">创建人：</td>
                <td width="25%">${project.appointMan}</td>
              </tr>
              <tr>
                <td width="25%" class="info">创建日期：</td>
                <td width="25%">
                  <fmt:formatDate value='${project.createAt}' pattern='yyyy年MM月dd日  HH:mm:ss' />
                </td>
                <td width="25%" class="info"></td>
                <td width="25%"></td>
              </tr>
            </tbody>
          </table>
        </ul>
      </div>
      <div class="padding-top-10 clear">
        <h2 class="count_flow"><i>2</i>执行进度</h2>
           <div class="col-md-12 col-xs-12 col-sm-12 flow_more">
		<div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">采购需求编报</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
			<div class="tip_line col-md-5 col-sm-3"></div>
			<div class="tip_down col-xs-offset-6"></div>
		</div>
		
		<div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn last_small">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">采购需求受理</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
			<div class="tip_line col-md-5 col-sm-3"></div>
			<div class="tip_down col-xs-offset-6"></div>
		</div>
		
		<div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn small_r">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">预研任务下达</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
			<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
			<div class="tip_down col-xs-offset-6"></div>
		</div>
		
		<div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn small_r">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">采购计划审核</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
			<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
			<div class="tip_down col-xs-offset-6"></div>
		</div>
		 
		<div class="flow_tips col-md-2 col-sm-2 col-xs-12 pre_btn  ">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">采购计划下达</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
			<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
			<div class="tip_down col-xs-offset-6"></div>
		</div>
		
		<div class="flow_tips col-md-2 col-sm-2 col-xs-12 current_btn round_tips round_r">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">采购任务受领</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
			<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
			<div class="tip_down col-xs-offset-6 col-sm-offset-1 col-md-offset-1  col-md-offset-0"></div>
			
		</div>
		
		<div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">采购项目立项</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
			<div class="tip_down col-xs-offset-6"></div>
		</div>
		
		<div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">采购文件编报</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
			<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
			<div class="tip_down col-xs-offset-6"></div>
		</div>
		
		<div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">采购公告发布</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
			<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
			<div class="tip_down col-xs-offset-6"></div>
		</div>
		
		<div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">供应商抽取</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
			<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
			<div class="tip_down col-xs-offset-6"></div>
		</div>
		
		<div class="flow_tips col-md-2 col-sm-2 col-xs-12 last_r">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">采购文件发售</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
			<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
			<div class="tip_down col-xs-offset-6"></div>
		</div><div class="flow_tips col-md-2 col-sm-2 col-xs-12 round_tips round_l last_r">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">评审专家抽取</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
			<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
			<div class="tip_down col-xs-offset-6 col-sm-offset-1 col-md-offset-0"></div>
		</div>
		
		

		
		<div class="flow_tips col-md-2 col-sm-2 col-xs-12">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">开标</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
			<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
			<div class="tip_down col-xs-offset-6"></div>
		</div>
		
		<div class="flow_tips col-md-2 col-sm-2 col-xs-12">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">采购项目评审</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
			<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
			<div class="tip_down col-xs-offset-6"></div>
		</div>
		
		<div class="flow_tips col-md-2 col-sm-2 col-xs-12">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">中标供应商确定</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
			<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
			<div class="tip_down col-xs-offset-6"></div>
		</div>
		
		<div class="flow_tips col-md-2 col-sm-2 col-xs-12">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">采购合同签订</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
			<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
			<div class="tip_down col-xs-offset-6"></div>
		</div>
		
		<div class="flow_tips col-md-2 col-sm-2 col-xs-12">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">采购合同履约</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
			<div class="tip_line col-md-5 col-sm-3 col-xs-4"></div>
			<div class="tip_down col-xs-offset-6"></div>
		</div>
		
		<div class="flow_tips col-md-2 col-sm-2 col-xs-12">
			<div class="col-md-7 col-sm-9 col-xs-12 tip_btn">
			<a href="javascript:void(0);">
			<p class="tip_main">质量校验验收</p>
			<p class="tip_time">2016-08-08</p>
			</a>
			</div>
		</div>
		
		
	</div>
        
        
        
      </div>
      <div class="padding-top-10 clear">
        <h2 class="count_flow"><i>3</i>项目实施明细</h2>
       
       
      </div>
      <div class="col-md-12 col-xs-12 col-sm-12 tc mt20">
        <button class="btn btn-windows back" onclick="window.history.go(-1)" type="button">返回</button>
      </div>
    </div>
  </body>

</html>
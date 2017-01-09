<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<jsp:include page="/WEB-INF/view/front.jsp"></jsp:include>

<html class=" js cssanimations csstransitions" lang="en"><!--<![endif]-->
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
	<title></title>

	<!-- Meta -->
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta name="description" content="">
	<meta name="author" content="">

</head>

<body>
  <div class="wrapper">
	<div class="header-v4">
    <!-- Navbar -->
    <div class="navbar navbar-default mega-menu" role="navigation">
      <div class="container">
        <!-- logo和搜索 -->
        <div class="navbar-header">
          <div class="row container margin-bottom-10">
            <div class="col-md-8">
              <a href="">
                 <img alt="Logo" src="<%=basePath%>public/ZHQ/images/logo.png" id="logo-header">
              </a>
            </div>
			<!--搜索开始-->
            <div class="col-md-4 mt50">
              <div class="search-block-v2">
                <div class="">
                  <form accept-charset="UTF-8" action="" method="get">
				    <div style="display:none">
				     <input name="utf8" value="" type="hidden">
					</div>
                    <input id="t" name="t" value="search_products" type="hidden">
                    <div class="col-md-12 pull-right">
                      <div class="input-group bround4">
                        <input class="form-control h38" id="k" name="k" placeholder="" type="text">
                        <span class="input-group-btn">
                          <input class="btn-u h38" name="commit" value="搜索" type="submit">
                        </span>
                      </div>
                    </div>
                  </form>               
               </div>
              </div>
            </div>
          <!--搜索结束-->
          </div>
		 </div>

          <button data-target=".navbar-responsive-collapse" data-toggle="collapse" class="navbar-toggle" type="button">
            <span class="full-width-menu">全部菜单分类</span>
            <span class="icon-toggle">
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
              <span class="icon-bar"></span>
            </span>
          </button>
      </div>

    <div class="clearfix"></div>

    <div style="height: 0px;" aria-expanded="false" class="navbar-collapse navbar-responsive-collapse collapse">
    <div class="container">
      <ul class="nav navbar-nav">
      <!-- 通知 -->
        <li class="active dropdown tongzhi_li">
          <a class=" dropdown-toggle p0_30" href=""><i class="tongzhi nav_icon"></i>通知</a>
        </li>
      <!-- End 通知 -->

      <!-- 公告 -->
        <li class="dropdown gonggao_li">
          <a class=" dropdown-toggle p0_30" href=""><i class="gonggao nav_icon"></i>公告</a>
        </li>
      <!-- End 公告 -->

      <!-- 公示 -->
        <li class="dropdown gongshi_li">
          <a data-toggle="dropdown" class="dropdown-toggle p0_30 " href=""><i class="gongshi nav_icon"></i>公示</a>
        </li>
      <!-- End 公示 -->

      <!-- 专家 -->
        <li class="dropdown zhuanjia_li">
          <a  href="#" class="dropdown-toggle p0_30 " data-toggle="dropdown"><i class="zhuanjia nav_icon"></i>专家</a>
        </li>
      <!-- End 专家 -->

      <!-- 投诉 -->
        <li class="dropdown tousu_li">
          <a data-toggle="dropdown" class="dropdown-toggle p0_30" href="" ><i class="tousu nav_icon"></i>投诉</a>
        </li>
      <!-- End 投诉 -->

      <!-- 法规 -->
        <li class="dropdown  fagui_li">
          <a href="" class="dropdown-toggle p0_30" data-toggle="dropdown" ><i class="fagui nav_icon"></i>法规</a>
        </li>
      <!-- End 法规 -->

        <li class="dropdown luntan_li">
          <a aria-expanded="false" href="" class="dropdown-toggle p0_30" data-toggle="dropdown"><i class="luntan nav_icon"></i>论坛</a>
        </li>

      </ul>
    </div>
	</div>

  <!--/end container-->
   </div>
  </div>
  <div class="container content height-350 job-content ">

   
    <div class="col-md-12 p20 border1 margin-top-20 mb40">
        <div class="tab-v1">
          <h2 class="tc bbgrey">
            阅读军队物资供应商须知
		  </h2>
        </div>
          <div class="tab-content margin-bottom-20 margin-top-20 lh24">
            加入军队物资供应商库须知
 
一、需要准备的资质材料
（一）原件
1、营业执照副本，法定代表人身份证，组织机构代码证副本，税务登记证副本，各类认证证书，产品代理销售授权书（现场审验后退回）。
2、最近三年年终会计师事务所审计报告（包括资产负债表、损益表），5个工作日审验后退回。
3、税务部门出具的最近三年完税证明(完税发票收据无效)，社会保障机构出具的最近三年缴纳社会保障资金证明(发票收据无效)，开户银行出具的基本账户资信证明。
（二）复印件
1、营业执照副本，法定代表人身份证，组织机构代码证副本，税务登记证副本，各类认证证书，产品代理销售授权书。
2、最近三年年终会计师事务所审计报告（包括资产负债表、损益表）。
注：所有复印件必须装订成册，审计报告结论页和其他所有证明材料复印件均需要盖公章和法定代表人（或被授权人）签字或加盖有效个人签名章（其它印章无效）。
（三）供应商注册申请表一份。
二、申请注册程序
1、登录军队采购网（http://www.plap.cn/）,打开“供应商申请注册”页面；
2、选择受理供应商入库申请的军队物资采购机构；
3、按要求填写有关注册信息、上传营业执照副本、法定代表人身份证、组织机构代码证副本、各类认证证书、产品代理销售授权书的电子图片；
4、在线打印“军队物资供应商注册申请表”；
5、向军队物资采购机构提交“军队物资供应商注册申请表”、上述证明材料、以及资质材料的原件和复印件、复印件需要法定代表人签字（或签名章）并加盖公章。
三、其他注册事项
1、生产多种产品的集团企业,由具有企业法人资格的下属公司申请注册；
2、从事产品代理销售的供应商,应当是产品生产企业的全国总代理或者地区一级代理。
3、资质材料原件采购机构审核后退回,复印件留存；
4、每页复印件需要法定代表人或授权代理人签字并加盖公章,由授权代理人签字的、应有法定代表人授权书；
5、装订要求：“军队物资供应商注册申请表”装订成一册,法定代表人授权书,证明材料和资质材料复印件装订成一册。
6、注册咨询电话：010-66946342,联系人：陈工；
四、用户名和密码查询方法
如果忘记注册时使用的用户名和密码,可通过下面方法查询：
1、拟制用户名与密码查询申请（格式自拟）并加盖公章、注明申请人,联系方式和电子信箱地址（必须提供、否则无法恢复）；
2、提交组织机构代码证,营业执照副本复印件,加盖公章；
3、将以上文件邮寄至：北京市丰台区丰台西路15号信息中心，收件人:陈工，邮编100071，咨询电话010-66945675；
4、我们在收到后密码查询申请函后。于每周一集中办理（如遇节假日顺延）以邮件的方式将查询到的用户名和密码发送给您；
5、受理时间：周一至周五上午8:30-11:00、下午2:30-3:30、周六、周日及节假日不受理
五、注销注册信息
凡在军队采购网提交网上注册申请的供应商,因资质材料填报有误,在库状态为已注册、暂存和退回供应商,可申请注销,已经通过采购机构审核的供应商不能办理网上注销。
2015年一月一日起注销注册信息按下列程序办理：
1、按照要求填写《军队采购网注册信息注销申请表》并加盖公章。附件下载：军队采购网注册信息注销申请表.rar
2、提交组织机构代码证和营业执照复印件，以及法人委托书，以及被委托人身份证复印件联系方式。加盖公章；
3、将注销申请表、组织机构代码证和营业执照请邮寄至：北京市丰台区丰台西路15号信息中心，收件人：华高工，邮编100071，咨询电话010-66945606；
4、我们将自收到注销申请资料后，集中办理，在采购网主页进行公告，提交注销申请的供应商自行上网查询。注销后供应商即可在网上重新注册；
5、申报时间：周一至周四上午8:30-11:00下午2:30-3:30周六周日及节假日不受理。
六、部分指标填写说明
（一）会计报表
每年3月1日起，需提交自上年度起经会计师事务所审计的年度会计报表。
（二）完税证明
主要是证明按照国家税法足额纳税，没有欠税、偷税、漏税的行为。如果国税和地税机构分开，则应当分别开具国税和地税完税证明。不能使用缴税发票代替完税证明。
（三）社保证明
主要是证明按照国家有关规定，足额缴纳员工的社会保险金。由当地社会保险经办机构出具并盖单。
（四）资信证明
基本帐户银行出具的资信证明。开户银行有固定资信证明格式的，使用银行的制式证明。银行无固定格式的，可参照下列格式出具。
采购机构名称：
    XX公司在XX银行开立基本结算帐户，账号：XXXXX；至XX年XX月XX止，在我行办理的各项信贷业务，无逾期和欠息记录，资金结算方面无不良记录，执行结算纪律情况良好。特此证明。
XX银行（盖章）   
XXX年XX月XX日   
负责人或授权代理人签字   
（五）质量体系认证证书
由国家质量检验监督总局认证认可委员会批准的可在中国大陆从事认证业务的国内和国外认证机构出具的质量体系认证证书。如果是外国认证机构，其出具证书必须为中文。认证证书可在认证认可委员会或认证机构网站查询。如正在年检，应当有认证机构出具的受理年检证明。执行的质量认证标准一般为国标（GB\T19001）或国军标（GJB\T19001）。有些行业（如食品、药品等）执行特殊的质量认证标准。
（六）环境体系认证证书
认证机构和证书查询方法同质量体系认证证书。执行的环境保护认证标准一般为国标GB\T24001。
（七）国家行业准入证书
国家对有准入要求的特殊行业需要提供国家行业准入证书。如医疗器械、药品的生产企业应当有《医疗器械生产企业许可证》或《药品生产许可证》，医疗器械、药品的经营企业应当有《医疗器械经营企业许可证》或《药品经营许可证》。
 
推荐使用IE8浏览器进行注册，其他浏览器的注册兼容会陆续上线！


<div class="mt40">
  <div class="fl">文件下载：<span class="ml10">供应商注册须知</span><a href="#" class="download"></a></div>
  <div class="fl ml20">产品分类目录<a href="#" class="download"></a></div>
  <div class="clear"></div>  
</div>
<div class="mt40"><input type="radio" class="radio_orange"><span class="ml10">我已阅读，并且完全遵守相关规定</span></div>
<div class="mt40 tc">
 <button class="btn padding-left-20 padding-right-20 btn_back">返回</button>
</div>
</div>
</div>
</div>
    <jsp:include page="/index_bottom.jsp"></jsp:include>
</body>
</html>

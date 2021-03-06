<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/view/common.jsp" %>
<%@ include file="/WEB-INF/view/common/webupload.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>军队采购网上商城APP下载</title>
<script type="text/javascript">
  //JS监听浏览器文字大小代码
  (function (doc, win) {
      var docEl = doc.documentElement,
          resizeEvt = 'orientationchange' in window ? 'orientationchange' : 'resize',
          recalc = function () {
              var clientWidth = docEl.clientWidth;
              if (!clientWidth) return;
              docEl.style.fontSize = 10 * (clientWidth / 320) + 'px';
          };

      if (!doc.addEventListener) return;
      win.addEventListener(resizeEvt, recalc, false);
      doc.addEventListener('DOMContentLoaded', recalc, false);
  })(document, window);
  
  /*文件下载*/
  function download(bid,key,url){
    var zipFileName = null;
    var fileName = null;
      if(bid != ""){
        var id = bid;
      	$("#dow").attr("src","${pageContext.request.contextPath}/api/v1/download.html?id="+ id +"&key="+ key +"&zipFileName=" + encodeURI(encodeURI(zipFileName)) + "&fileName=" + encodeURI(encodeURI(fileName)));
      }
  }
  
  //判断微信
  window.onload = function(){
    if(isWeiXin()){
    	document.getElementById('guide').style.display = 'block';
    } else {
    	document.getElementById('qrCode_body').style.display = 'block';
    }
  };
  
  function isWeiXin(){
      var ua = window.navigator.userAgent.toLowerCase();
      if(ua.match(/MicroMessenger/i) == 'micromessenger'){
          return true;
      }else{
          return false;
      }
  }
</script>
</head>
<body>
  <div id="qrCode_body">
  <div class="all">
    <div class="logo">
      <img src="${pageContext.request.contextPath}/public/portal/images/shancgehnglogo.jpg" />
    </div>
    <div class="ewm_title">军队采购网上商城APP</div>
  </div>
  <div class="down_load">
    <a >
      <div class="down_andrio down_main">
        <p class="white" onclick = "download('${id}',${sysKey},'')"><img src="${pageContext.request.contextPath}/public/portal/images/android.png">点此下载</p>
        <div class="footer_tips">温馨提示：本APP只适用于安卓操作系统的手机。</div>
      </div>
    </a>
  </div>
  </div>
  <!-- 引导 -->
  <div class="guide" id="guide"><img src="${pageContext.request.contextPath}/public/portal/images/mallwx.png" alt=""></div>
  <!-- End 引导 -->
  <!-- 下载页面 -->
  <iframe class = "hide" id = "dow"></iframe>
</body>
</html>
<style type="text/css">
  /* CSS Document */
  body, ol, ul, h1, h2, h3, h4, h5, h6, p, th, td, dl, dd, form, fieldset, legend, input, textarea, select, iframe {
      margin: 0;
      padding: 0;
  }
  img {
      border: 0;
  }
  a{
    text-decoration:none;
  }
  @media screen (max-width: 320px){
  html{
    font-size:83.3%;
  }

  }

  @media screen (max-width: 640px){
  html{
    font-size:62.5%;
  }

  }

  .all {
      max-width: 64rem;
      height: auto;
      margin: 5rem auto 3rem auto;
  }
  .logo{
    width:100%;
    text-align:center;
  }
  .ewm_title{
    font-family: "Microsoft YaHei";
    width:100%;
    text-align:center;
  }
  .ewm_box{
    width:100%;
    text-align:center;
  }
  .ewm_img{
    margin:0 auto;
    background-color:#000000;
  }
  .down_load{
    width:100%;
    text-align:center;
  }
  .down_load .down_main{
    margin:0 auto;
    text-align:left;
    color:#ffffff;
    font-size: 2.3rem;
  }
  .down_main img{
    width: 2rem;
    height: auto;
    position: relative;
    margin-top: -0.5rem;
    margin-left: 6.5rem;
    margin-right: 0.5rem;
  }
  .down_load .down_main a{
    width:100%;
    color:#ffffff;
  }

  .down_main.down_andrio{
    background-color:#2c9fa6;
  }
 /*@media screen and (max-width:799px){*/
  .logo img {
      width: 80%;
  }
  .ewm_title {
      font-size: 2.5rem;
      margin-top: 3rem;
  }
  .ewm_img {
    width:50%;
  }
  .down_load {
    margin-top:2rem;
  }
  .down_load .down_main {
    width:80%;
    margin-top:2rem;
    padding:1rem 0;
  }
  .down_load .down_main a {
    font-size:0.4rem;
  }
  .ewm_box {
      margin-top: 3rem;
  }
  .footer_tips{
       font-size:1rem;
       color:#ffffff;
       text-align: center;
       margin-top: 1rem;
  }
  #qrCode_body {
    display: none;
  }
  /* 引导 */
  .guide {
    display: none;
    position: fixed;
    width: 100%;
    height: 100%;
    top: 0;
    left: 0;
    padding-top: 20px;
    padding-right: 20px;
    background-color: #FFF;
    text-align: right;
  }
  .guide img {
    max-width: 100%;
  }
</style>
// 请勿修改，否则可能出错
var userAgent = navigator.userAgent, rMsie = /(msie\s|trident.*rv:)([\w.]+)/, rFirefox = /(firefox)\/([\w.]+)/, rOpera = /(opera).+version\/([\w.]+)/, rChrome = /(chrome)\/([\w.]+)/, rSafari = /version\/([\w.]+).*(safari)/;
var browser;
var version;
var ua = userAgent.toLowerCase();
function uaMatch(ua) {
	var match = rMsie.exec(ua);
	if (match != null) {
		return {
			browser : "IE",
			version : match[2] || "0"
		};
	}
	var match = rFirefox.exec(ua);
	if (match != null) {
		return {
			browser : match[1] || "",
			version : match[2] || "0"
		};
	}
	var match = rOpera.exec(ua);
	if (match != null) {
		return {
			browser : match[1] || "",
			version : match[2] || "0"
		};
	}
	var match = rChrome.exec(ua);
	if (match != null) {
		return {
			browser : match[1] || "",
			version : match[2] || "0"
		};
	}
	var match = rSafari.exec(ua);
	if (match != null) {
		return {
			browser : match[2] || "",
			version : match[1] || "0"
		};
	}
	if (match != null) {
		return {
			browser : "",
			version : "0"
		};
	}
}
var browserMatch = uaMatch(userAgent.toLowerCase());
if (browserMatch.browser) {
	browser = browserMatch.browser;
	version = browserMatch.version;
}
// document.write(browser);
/*
 * 谷歌浏览器事件接管
 */
function OnComplete2(type, code, html) {
	if (html == "OK")
		document.getElementById("TANGER_OCX").ShowTipMessage("提示", "SaveToURL成功!");
	else
		document.getElementById("TANGER_OCX").ShowTipMessage("提示", "SaveToURL失败!");
}
function OnComplete4(UserName, SignName, SignUser, SignSN, EkeySN, IsCancel) {
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", "OnComplete4");
}
function OnComplete(type, code, html) {
	// document.getElementById("TANGER_OCX").ShowTipMessage("提示",type);
	// document.getElementById("TANGER_OCX").ShowTipMessage("提示",code);
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", html);
}
function OnComplete3(str, doc) { /*
									 * document.getElementById("TANGER_OCX").ShowTipMessage("提示","马上执行设置office的username"); document.getElementById("TANGER_OCX").ActiveDocument.Application.UserName = "pengtao"; document.getElementById("TANGER_OCX").ActiveDocument.Application.Options.InsertedTextColor = 11;//插入的颜色 document.getElementById("TANGER_OCX").ShowTipMessage("提示","ondocumentopened成功回调");
									 */
	OFFICE_CONTROL_OBJ.activeDocument.saved = true;// saved属性用来判断文档是否被修改过,文档打开的时候设置成ture,当文档被修改,自动被设置为false,该属性由office提供.
	// 获取文档控件中打开的文档的文档类型
	switch (OFFICE_CONTROL_OBJ.doctype) {
	case 1:
		fileType = "Word.Document";
		fileTypeSimple = "wrod";
		break;
	case 2:
		fileType = "Excel.Sheet";
		fileTypeSimple = "excel";
		break;
	case 3:
		fileType = "PowerPoint.Show";
		fileTypeSimple = "ppt";
		break;
	case 4:
		fileType = "Visio.Drawing";
		break;
	case 5:
		fileType = "MSProject.Project";
		break;
	case 6:
		fileType = "WPS Doc";
		fileTypeSimple = "wps";
		break;
	case 7:
		fileType = "Kingsoft Sheet";
		fileTypeSimple = "et";
		break;
	default:
		fileType = "unkownfiletype";
		fileTypeSimple = "unkownfiletype";
	}
	setFileOpenedOrClosed(true);
}
var i = 1;
function addtemplatefromurl(type, code, html) {
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", "addtemplatefromurl");
}
function onActivated(a) {
	// document.getElementById("TANGER_OCX").addEventListener("Mouseout","onActivated",false);
	// document.getElementById("TANGER_OCX").ShowTipMessage("提示","OnActivated");

}
function AfterControlCreateFinished() {
	document.getElementById("TANGER_OCX").AddCustomMenu2(1, "我的菜单");// 添加主菜单
	document.getElementById("TANGER_OCX").AddCustomMenuItem2(1, 0, -1, false, "我的子菜单");
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", "AfterControlCreateFinished");

}
function publishashtml(type, code, html) {
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", html + "," + code + "," + type);// type就是返回值
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", "Onpublishashtmltourl成功回调");
}
function publishaspdf(type, code, html) {
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", html);
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", "Onpublishaspdftourl成功回调");
}
function saveasotherurl(type, code, html) {
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", html);
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", "SaveAsOtherformattourl成功回调");
}
function dowebget(type, code, html) {
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", html);
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", "OnDoWebGet成功回调");
}
function webExecute(type, code, html) {
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", html);
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", "OnDoWebExecute成功回调");
}
function webExecute2(type, code, html) {
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", html);
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", "OnDoWebExecute2成功回调");
}
function FileCommand(TANGER_OCX_str, TANGER_OCX_obj) {
	alert("ok");
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", "OnFileCommand事件！");
	if (TANGER_OCX_str == 3) {
		
		document.getElementById("TANGER_OCX").ShowTipMessage("提示", "不能保存！");
		/*
		 * var FileName=document.getElementById("SaveFileName").value; 
		 * if(FileName == "")
		 *  { document.getElementById("TANGER_OCX").ShowTipMessage("提示","请输入保存的文件名"); return; }
		 *   //document.getElementById("TANGER_OCX").ShowTipMessage("提示","执行FileCommand"); 
		 *   sleep(); document.getElementById("TANGER_OCX").SaveToURL("Save","NTKOFILE","ppp=0",FileName,"MyFile");
		 */
		document.getElementById("TANGER_OCX").CancelLastCommand = true;
	}
}
function onClosed() {
	setFileOpenedOrClosed(false);
}
function CustomFileMenuCmd(menuIndex, menuCaption, menuID) {
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", menuCaption);
}
function CustomMenuCmd(menuPos, submenuPos, subsubmenuPos, menuCaption, menuID) {
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", "第" + menuPos + "," + submenuPos + "," + subsubmenuPos + "个菜单项,menuID=" + menuID + ",菜单标题为\"" + menuCaption + "\"的命令被执行.");
}

var classid = 'C9BC4DFF-4248-4a3c-8A49-63A7D317F404';
if (browser == "IE") {

	document.write('<!-- 用来产生编辑状态的ActiveX控件的JS脚本-->   ');
	document.write('<!-- 因为微软的ActiveX新机制，需要一个外部引入的js-->   ');
	if (window.navigator.platform == "Win32") {
		document.write('<object id="TANGER_OCX" classid="clsid:A64E3073-2016-4baf-A89D-FFE1FAA10EC0"');
		document.write('codebase="OfficeControl.cab#version=5,0,2,5" width="100%" height="70%">   ');
	} else {
		document.write('<object id="TANGER_OCX" classid="clsid:A64E3073-2016-4baf-A89D-FFE1FAA10EE0"');
		document.write('codebase="OfficeControl_x64.cab#version=5,0,2,6" width="100%" height="70%">   ');
	}
	document.write('<param name="IsUseUTF8URL" value="-1">   ');
	document.write('<param name="IsUseUTF8Data" value="-1">   ');
	document.write('<param name="BorderStyle" value="1">   ');
	document.write('<param name="BorderColor" value="14402205">   ');
	document.write('<param name="TitlebarColor" value="15658734">   ');
	document.write('<param name="isoptforopenspeed" value="0">   ');
	document.write('<param name="TitlebarTextColor" value="0">   ');
	document.write('<param name="MenubarColor" value="14402205">   ');
	document.write('<param name="MenuButtonColor" VALUE="16180947">   ');
	document.write('<param name="MenuBarStyle" value="3">   ');
	document.write('<param name="MenuButtonStyle" value="7">   ');
	document.write('<param name="WebUserName" value="NTKO">   ');
	document.write('<param name="Caption" value="NTKO OFFICE文档控件示例演示 http://www.ntko.com">   ');
	document.write('<SPAN STYLE="color:red">不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>   ');
	document.write('</object>');
} else if (browser == "firefox") {
	document.write('<object id="TANGER_OCX" type="application/ntko-plug"  codebase="OfficeControl.cab#version=5,0,2,5" width="100%" height="600px" onmouseout="onActivated(false)" ForOnSaveToURL="OnComplete2" ForOnBeginOpenFromURL="OnComplete" ForOndocumentopened="OnComplete3"');
	document.write('ForOnDocumentClosed="onClosed"');
	document.write('ForOnDocActivated="onActivated"');
	document.write('ForOnpublishAshtmltourl="publishashtml"');
	document.write('ForOnBeforeDoSecSignFromEkey="OnComplete4"');
	document.write('ForOnpublishAspdftourl="publishaspdf"');
	document.write('ForOnSaveAsOtherFormatToUrl="saveasotherurl"');
	document.write('ForOnAddTemplateFromURL="addtemplatefromurl"');
	document.write('ForOnDoWebGet="dowebget" ');
	document.write('ForOnDoWebExecute="webExecute" ');
	document.write('ForOnDoWebExecute2="webExecute2" ');
	document.write('ForOnFileCommand="FileCommand" ');
	document.write('ForOnCustomMenuCmd2="CustomMenuCmd" ');
	document.write('ForOnCustomFileMenuCmd="CustomFileMenuCmd" ');
	document.write('_IsUseUTF8URL="-1"   ');
	document.write('_IsUseUTF8Data="-1"   ');
	document.write('_BorderStyle="1"   ');
	document.write('_BorderColor="14402205"   ');
	document.write('_MenubarColor="14402205"   ');
	document.write('_MenuButtonColor="16180947"   ');
	document.write('_MenuBarStyle="3"  ');
	document.write('_MenuButtonStyle="7"   ');
	document.write('_WebUserName="NTKO"   ');
	document.write('clsid="{A64E3073-2016-4baf-A89D-FFE1FAA10EC0}" >');
	document.write('<SPAN STYLE="color:red">尚未安装NTKO Web FireFox跨浏览器插件。请点击<a href="NtkoCrossBrowserSetup20160522.msi">安装组1件</a></SPAN>   ');
	document.write('</object>   ');
} else if (browser == "chrome") {
	document.write('<object id="TANGER_OCX" clsid="{A64E3073-2016-4baf-A89D-FFE1FAA10EC0}"  ForOnSaveToURL="OnComplete2" ForOnBeginOpenFromURL="OnComplete" ForOndocumentopened="OnComplete3"');
	document.write('ForOnpublishAshtmltourl="publishashtml"');
	document.write('ForOnpublishAspdftourl="publishaspdf"');
	document.write('ForOnAddTemplateFromURL="addtemplatefromurl"');
	document.write('ForAfterControlCreateFinished="AfterControlCreateFinished"');
	document.write('ForOnSaveAsOtherFormatToUrl="saveasotherurl"');
	document.write('ForOnDoWebGet="dowebget"');
	document.write('ForOnDoWebExecute="webExecute"');
	document.write('ForOnDoWebExecute2="webExecute2"');
	document.write('ForOnFileCommand="FileCommand"');
	document.write('ForOnCustomMenuCmd2="CustomMenuCmd"');
	document.write('ForOnCustomFileMenuCmd="CustomFileMenuCmd"');
	document.write('codebase="OfficeControl.cab#version=5,0,2,5" width="100%" height="600px" type="application/ntko-plug" ');
	document.write('_IsUseUTF8URL="-1"   ');
	document.write('_IsUseUTF8Data="-1"   ');
	document.write('_BorderStyle="1"   ');
	document.write('_BorderColor="14402205"   ');
	document.write('_MenubarColor="14402205"   ');
	document.write('_MenuButtonColor="16180947"   ');
	document.write('_MenuBarStyle="3"  ');
	document.write('_MenuButtonStyle="7"');
	document.write('_WebUserName="NTKO"  ');
	document.write('_Caption="NTKO OFFICE文档控件示例演示 http://www.ntko.com">    ');
	document.write('<SPAN STYLE="color:red">尚未安装NTKO Web Chrome跨浏览器插件。请点击<a href="NtkoCrossBrowserSetup20160522.msi">安装组件</a></SPAN>   ');
	document.write('</object>');
} else if (Sys.opera) {
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", "sorry,ntko web印章暂时不支持opera!");
} else if (Sys.safari) {
	document.getElementById("TANGER_OCX").ShowTipMessage("提示", "sorry,ntko web印章暂时不支持safari!");
}
/**
 * 创建cookie
 * @param name
 * @param value
 * @param days
 */
function createCookie(name,value,days) {
    var expires;
    if (days) {
        var date = new Date();
        date.setTime(date.getTime()+(days*24*60*60*1000));
        expires = "; expires="+date.getTime();
    } else expires = "";
    document.cookie = name+"="+value+expires+"; path=/;domain=" + location.hostname + "";
}

/**
 *
 * 读取Cookie根据名称
 * @param name
 * @returns {*}
 */
function readCookie(name) {
    var nameEQ = name + "=";
    var cookieAll = document.cookie.split(';');
    for(var i=0;i < cookieAll.length;i++) {
        var c = cookieAll[i];
        while (c.charAt(0)==' ') c = c.substring(1,c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
    }
    return null;
}

function eraseCookie(name) {
    createCookie(name,"",-1);
}
/**
 * devcCommon.js
 * developer : huhyub
 * date : 2020.02.25
 * */
var userAgentTxt = navigator.userAgent.toLowerCase();
var isAndroidDevc = (userAgentTxt.indexOf('android') > -1);
var isIosDevc = (userAgentTxt.indexOf("iphone") > -1 || userAgentTxt.indexOf("ipad") > -1 ||  userAgentTxt.indexOf("safari") > -1) && !isAndroidDevc;

var isAndroidStore = (window.android && navigator.userAgent.match(/Android/i) || userAgentTxt.indexOf("chrome") > -1);
var isIosStore = (userAgentTxt.indexOf("iphone") > -1 || userAgentTxt.indexOf("ipad") > -1 ||  userAgentTxt.indexOf("safari") > -1);
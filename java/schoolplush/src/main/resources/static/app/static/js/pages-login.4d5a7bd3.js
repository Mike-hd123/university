(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-login"],{"55ec":function(e,n,t){"use strict";Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0;var a={data:function(){return{username:"",password:"",error:""}},onLoad:function(){},methods:{login:function(){var e=this;console.log("登录..."),uni.request({url:"/api/login",data:{username:this.username,password:this.password},method:"POST",success:function(n){var t=n.data;200==t.code?(console.log("登录成功!"),uni.setStorage({key:"token",data:t.data}),uni.navigateBack()):(console.log("登录失败!"),e.error=t.msg,e.$refs.msg.open("top"))}})}}};n.default=a},c142:function(e,n,t){"use strict";var a=t("d206"),o=t.n(a);o.a},c6f7:function(e,n,t){"use strict";t.r(n);var a=t("d5fc"),o=t("d797");for(var s in o)["default"].indexOf(s)<0&&function(e){t.d(n,e,(function(){return o[e]}))}(s);t("c142");var r,u=t("f0c5"),i=Object(u["a"])(o["default"],a["b"],a["c"],!1,null,"6c961bd0",null,!1,a["a"],r);n["default"]=i.exports},d147:function(e,n,t){var a=t("24fb");n=a(!1),n.push([e.i,".middle[data-v-6c961bd0]{-webkit-align-content:center;align-content:center}.name[data-v-6c961bd0]{background-color:#e8f0fd;margin:50px;height:30px}.pass[data-v-6c961bd0]{background-color:#e8f0fd;margin:50px;height:30px}.login[data-v-6c961bd0]{margin:50px}",""]),e.exports=n},d206:function(e,n,t){var a=t("d147");"string"===typeof a&&(a=[[e.i,a,""]]),a.locals&&(e.exports=a.locals);var o=t("4f06").default;o("b53ebd90",a,!0,{sourceMap:!1,shadowMode:!1})},d5fc:function(e,n,t){"use strict";t.d(n,"b",(function(){return o})),t.d(n,"c",(function(){return s})),t.d(n,"a",(function(){return a}));var a={uniPopup:t("7822").default,uniPopupMessage:t("7a51").default},o=function(){var e=this,n=e.$createElement,t=e._self._c||n;return t("v-uni-view",{staticClass:"middle"},[t("v-uni-input",{staticClass:"name",model:{value:e.username,callback:function(n){e.username=n},expression:"username"}}),t("v-uni-input",{staticClass:"pass",attrs:{password:"ture"},model:{value:e.password,callback:function(n){e.password=n},expression:"password"}}),t("v-uni-button",{staticClass:"login",on:{click:function(n){arguments[0]=n=e.$handleEvent(n),e.login.apply(void 0,arguments)}}},[e._v("登录")]),t("uni-popup",{ref:"msg",attrs:{type:"message"}},[t("uni-popup-message",{attrs:{type:"error",message:e.error,duration:2e3}})],1)],1)},s=[]},d797:function(e,n,t){"use strict";t.r(n);var a=t("55ec"),o=t.n(a);for(var s in a)["default"].indexOf(s)<0&&function(e){t.d(n,e,(function(){return a[e]}))}(s);n["default"]=o.a}}]);
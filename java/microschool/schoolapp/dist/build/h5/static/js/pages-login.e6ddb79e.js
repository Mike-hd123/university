(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["pages-login"],{1523:function(e,n,a){"use strict";a.d(n,"b",(function(){return o})),a.d(n,"c",(function(){return s})),a.d(n,"a",(function(){return t}));var t={uniPopup:a("7822").default,uniPopupMessage:a("7a51").default},o=function(){var e=this,n=e.$createElement,a=e._self._c||n;return a("v-uni-view",{staticClass:"middle"},[a("v-uni-input",{staticClass:"name",model:{value:e.username,callback:function(n){e.username=n},expression:"username"}}),a("v-uni-input",{staticClass:"pass",attrs:{password:"ture"},model:{value:e.password,callback:function(n){e.password=n},expression:"password"}}),a("v-uni-button",{staticClass:"login",on:{click:function(n){arguments[0]=n=e.$handleEvent(n),e.login.apply(void 0,arguments)}}},[e._v("登录")]),a("uni-popup",{ref:"msg",attrs:{type:"message"}},[a("uni-popup-message",{attrs:{type:"error",message:e.error,duration:2e3}})],1)],1)},s=[]},"55ec":function(e,n,a){"use strict";Object.defineProperty(n,"__esModule",{value:!0}),n.default=void 0;var t={data:function(){return{username:"",password:"",error:""}},onLoad:function(){},methods:{login:function(){var e=this;console.log("登录..."),uni.request({url:"/api/user/login",data:{username:this.username,password:this.password},method:"POST",success:function(n){var a=n.data;200==a.code?(console.log("登录成功!"),uni.setStorage({key:"token",data:a.data}),uni.navigateBack()):(console.log("登录失败!"),e.error=a.msg,e.$refs.msg.open("top"))}})}}};n.default=t},"86fd":function(e,n,a){var t=a("e616");"string"===typeof t&&(t=[[e.i,t,""]]),t.locals&&(e.exports=t.locals);var o=a("4f06").default;o("2008a9d8",t,!0,{sourceMap:!1,shadowMode:!1})},c6f7:function(e,n,a){"use strict";a.r(n);var t=a("1523"),o=a("d797");for(var s in o)["default"].indexOf(s)<0&&function(e){a.d(n,e,(function(){return o[e]}))}(s);a("fdf9");var r,u=a("f0c5"),i=Object(u["a"])(o["default"],t["b"],t["c"],!1,null,"047e5ce0",null,!1,t["a"],r);n["default"]=i.exports},d797:function(e,n,a){"use strict";a.r(n);var t=a("55ec"),o=a.n(t);for(var s in t)["default"].indexOf(s)<0&&function(e){a.d(n,e,(function(){return t[e]}))}(s);n["default"]=o.a},e616:function(e,n,a){var t=a("24fb");n=t(!1),n.push([e.i,".middle[data-v-047e5ce0]{-webkit-align-content:center;align-content:center}.name[data-v-047e5ce0]{background-color:#e8f0fd;margin:50px;height:30px}.pass[data-v-047e5ce0]{background-color:#e8f0fd;margin:50px;height:30px}.login[data-v-047e5ce0]{margin:50px}",""]),e.exports=n},fdf9:function(e,n,a){"use strict";var t=a("86fd"),o=a.n(t);o.a}}]);
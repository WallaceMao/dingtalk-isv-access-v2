(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-c26a0966"],{"3cc1":function(t,e,a){},"6d2b":function(t,e,a){"use strict";var o=a("3cc1"),r=a.n(o);r.a},"7f97":function(t,e,a){"use strict";(function(t){a.d(e,"a",function(){return r});var o=a("b775");function r(e,a,r){return o["a"].post("".concat(t.basic.LOGIN,"?corpId=").concat(e,"&username=").concat(a,"&password=").concat(r),{headers:{"Content-Type":"application/json"}}).then(function(t){return t.data})}}).call(this,a("8a20")["default"])},c6f7:function(t,e,a){"use strict";a.r(e);var o=function(){var t=this,e=this,a=e.$createElement,o=e._self._c||a;return o("div",{staticClass:"container"},[o("div",{staticClass:"content"},[e._m(0),o("div",{staticClass:"login"},[o("a-form",{attrs:{autoFormCreate:function(e){return t.form=e}},on:{submit:e.onSubmit}},[o("a-form-item",{attrs:{fieldDecoratorId:"companyId",fieldDecoratorOptions:{rules:[{required:!0,message:"请输入公司ID",whitespace:!0}]}}},[o("a-input",{attrs:{size:"large",placeholder:"公司ID"}},[o("a-icon",{attrs:{slot:"prefix",type:"user"},slot:"prefix"})],1)],1),o("a-form-item",{attrs:{fieldDecoratorId:"name",fieldDecoratorOptions:{rules:[{required:!0,message:"请输入账户名",whitespace:!0}]}}},[o("a-input",{attrs:{size:"large",placeholder:"账户"}},[o("a-icon",{attrs:{slot:"prefix",type:"user"},slot:"prefix"})],1)],1),o("a-form-item",{attrs:{fieldDecoratorId:"password",fieldDecoratorOptions:{rules:[{required:!1,message:"请输入密码",whitespace:!0}]}}},[o("a-input",{attrs:{size:"large",placeholder:"密码",type:"password"}},[o("a-icon",{attrs:{slot:"prefix",type:"lock"},slot:"prefix"})],1)],1),o("a-form-item",[o("a-button",{staticStyle:{width:"100%","margin-top":"24px"},attrs:{loading:e.logging,size:"large",htmlType:"submit",type:"primary"}},[e._v("登录")])],1)],1)],1)])])},r=[function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"top"},[a("div",{staticClass:"header"},[a("span",{staticClass:"title"},[t._v("功倍CRM管理系统")])])])}];a("cadf"),a("551c"),a("097d");var i=a("7f97"),n={name:"loginPage",data:function(){return{logging:!1,checked:!1}},components:{},computed:{},methods:{onSubmit:function(t){var e=this;t.preventDefault(),this.form.validateFields(function(t){t||(e.logging=!0,localStorage.removeItem("login_token"),Object(i["a"])(e.form.getFieldValue("companyId"),e.form.getFieldValue("name"),e.form.getFieldValue("password")).then(function(t){e.logging=!1,"0"===t.errcode?e.$router.push("/home"):(e.$message.warning("公司ID或用户名填写不正确！"),e.logging=!1)}).catch(function(t){e.$message.warning("公司ID或用户名填写不正确！"),e.logging=!1}))})}},watch:{},mounted:function(){}},s=n,c=(a("6d2b"),a("2877")),l=Object(c["a"])(s,o,r,!1,null,"329cea74",null);l.options.__file="login.vue";e["default"]=l.exports}}]);
//# sourceMappingURL=chunk-c26a0966.7685a0f1.js.map
package com.rishiqing.dingtalk.svc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 注意，这里只枚举需要处理的tag，其他的tag不要在这里加上
 */
public class CorpCallbackMessage {

    public enum Tag {
        //  通讯录用户增加
        USER_ADD_ORG("user_add_org"),
        //  通讯录用户更改
        USER_MODIFY_ORG("user_modify_org"),
        //  通讯录用户离职
        USER_LEAVE_ORG("user_leave_org"),
        //  通讯录用户被设为管理员
        ORG_ADMIN_ADD("org_admin_add"),
        //  通讯录用户被取消设置为管理员
        ORG_ADMIN_REMOVE("org_admin_remove"),
        //  通讯录企业部门创建
        ORG_DEPT_CREATE("org_dept_create"),
        //  通讯录企业部门修改
        ORG_DEPT_MODIFY("org_dept_modify"),
        //  通讯录企业部门删除
        ORG_DEPT_REMOVE("org_dept_remove"),
        //  企业被解散
        ORG_REMOVE("org_remove"),
        //  企业信息发生变更
        ORG_CHANGE("org_change");

        private final String key;

        Tag(String key) {
            this.key = key;
        }

        public String getKey() {
            return key;
        }


        public static  List<String> getAllTag(){
            List<String> tagList = new ArrayList<String>();
            Tag[] tagArr = Tag.values();
            for (Tag o : tagArr) {
                tagList.add(o.getKey());
            }
            return tagList;
        }
    }
}

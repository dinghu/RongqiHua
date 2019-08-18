package com.rongqi.hua.rongqihua.activity;

import com.blankj.utilcode.util.TimeUtils;
import com.fkh.support.ui.widget.KeyValueListView;
import com.fkh.support.ui.widget.KeyValueView;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.base.RqBaseActivity;
import com.rongqi.hua.rongqihua.entity.resp.TUserInfo;
import com.rongqi.hua.rongqihua.uitls.UserUtils;

import java.util.LinkedHashMap;

import butterknife.BindView;

public class MyRegisInfoDetailActivity extends RqBaseActivity {
    @BindView(R.id.keyValueListView)
    KeyValueListView keyValueListView;

    @Override
    protected int getLayout() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initView() {

        TUserInfo userInfo = UserUtils.getUserInfo();
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("姓名", userInfo.name);
        linkedHashMap.put("昵称", userInfo.nick);
        linkedHashMap.put("性别", userInfo.sex ? "男" : "女");
//        linkedHashMap.put("工号",userInfo.hiredate);
        linkedHashMap.put("身份证号", userInfo.nid);
        linkedHashMap.put("出生年月",TimeUtils.millis2String(userInfo.birth));
        linkedHashMap.put("入职日期", TimeUtils.millis2String(userInfo.hiredate));
        linkedHashMap.put("联系电话", userInfo.phone);
        linkedHashMap.put("联系邮箱", userInfo.email);
        linkedHashMap.put("住址", userInfo.place);
        linkedHashMap.put("邀请码", userInfo.invCode);
        keyValueListView.setKeyValues(linkedHashMap, new KeyValueListView.KeyValueListViewListener() {
            @Override
            public void onKeyValueView(String key, String value, KeyValueView keyValueView) {

            }
        });

    }
}

package com.rongqi.hua.rongqihua.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.fkh.support.ui.fragment.BaseFragment;
import com.fkh.support.ui.widget.KeyValueView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.activity.AboatActivity;
import com.rongqi.hua.rongqihua.activity.MyRegisInfoDetailActivity;
import com.rongqi.hua.rongqihua.activity.RegisInfotActivity;
import com.rongqi.hua.rongqihua.uitls.ActivityUtils;
import com.rongqi.hua.rongqihua.uitls.UserUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * Created by dinghu on 2019/8/15.
 */
public class MineFragment extends BaseFragment {
    @BindView(R.id.head)
    RoundedImageView head;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.lable)
    TextView lable;
    @BindView(R.id.loginLay)
    RelativeLayout loginLay;
    @BindView(R.id.kefuphone)
    KeyValueView kefuphone;
    @BindView(R.id.aboat)
    KeyValueView aboat;
    Unbinder unbinder;
    @BindView(R.id.extends_img)
    ImageView extendsImg;
    @BindView(R.id.mynews)
    KeyValueView mynews;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {
        lable.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserUtils.isLogin()) {
            userName.setText(UserUtils.getDisplayName());
        }
    }

    /**
     * 拨打电话（跳转到拨号界面，用户手动点击拨打）
     *
     * @param phoneNum 电话号码
     */
    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    @PermissionSuccess(requestCode = 100)
    public void perSucccess() {
        callPhone("12345678910");
    }

    @PermissionFail(requestCode = 100)
    public void perFail() {
        ToastUtils.showLong("无拨打电话权限，无法拨号");
    }


    @OnClick({R.id.loginLay, R.id.aboat, R.id.kefuphone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.kefuphone:
                PermissionGen.with(this).addRequestCode(100).permissions(Manifest.permission.CALL_PHONE).request();
                break;
            case R.id.loginLay:
                if (!UserUtils.isLogin()) {
                    UserUtils.reqLogin(getContext());
                } else {
                    if (UserUtils.getUserInfo() == null) {
                        ActivityUtils.startActivity(getContext(), RegisInfotActivity.class);
                    } else {
                        ActivityUtils.startActivity(getContext(), MyRegisInfoDetailActivity.class);
                    }
                }
                break;
            case R.id.aboat:
                ActivityUtils.startActivity(getContext(), AboatActivity.class);
                break;
        }
    }
}

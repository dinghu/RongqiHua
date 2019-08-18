package com.rongqi.hua.rongqihua.fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fkh.support.ui.fragment.BaseFragment;
import com.fkh.support.ui.widget.KeyValueView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.activity.MyChidrensAvtivity;
import com.rongqi.hua.rongqihua.activity.MyRegisInfoDetailActivity;
import com.rongqi.hua.rongqihua.activity.RegisInfotActivity;
import com.rongqi.hua.rongqihua.uitls.ActivityUtils;
import com.rongqi.hua.rongqihua.uitls.UserUtils;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

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
    Unbinder unbinder1;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (UserUtils.isLogin()) {
            userName.setText(UserUtils.getDisplayName());
        }
    }

    @OnClick(R.id.loginLay)
    public void onViewClicked() {
        if (!UserUtils.isLogin()) {
            UserUtils.reqLogin(getContext());
        }else {
            if (UserUtils.getUserInfo() == null){
                ActivityUtils.startActivity(getContext(), RegisInfotActivity.class);
            }else {
                ActivityUtils.startActivity(getContext(), MyRegisInfoDetailActivity.class);
            }
        }
    }
}

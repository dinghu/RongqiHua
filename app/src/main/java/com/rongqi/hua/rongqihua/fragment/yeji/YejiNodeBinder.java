package com.rongqi.hua.rongqihua.fragment.yeji;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fkh.support.ui.widget.KeyValueView;
import com.rongqi.hua.rongqihua.R;
import com.rongqi.hua.rongqihua.entity.resp.YejiTotal;

import butterknife.BindView;
import butterknife.ButterKnife;
import tellh.com.recyclertreeview_lib.TreeNode;
import tellh.com.recyclertreeview_lib.TreeViewBinder;

/**
 * Created by tlh on 2016/10/1 :)
 */

public class YejiNodeBinder extends TreeViewBinder<YejiNodeBinder.ViewHolder> {
    @Override
    public ViewHolder provideViewHolder(View itemView) {
        return new ViewHolder(itemView);
    }

    @Override
    public void bindView(ViewHolder holder, int position, TreeNode node) {
        holder.indicatorArrow.setRotation(0);
        holder.indicatorArrow.setImageResource(R.drawable.right);
        int rotateDegree = node.isExpand() ? 90 : 0;
        holder.indicatorArrow.setRotation(rotateDegree);
        YejiTotal dirNode = (YejiTotal) node.getContent();
        holder.kvName.setValue(dirNode.getName());
//        if (node.isLeaf()) {
//            holder.indicatorArrow.setVisibility(View.INVISIBLE);
//        } else {
//            holder.indicatorArrow.setVisibility(View.VISIBLE);
//        }
        setYejiTotalData(node.isExpand(), dirNode, holder);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_yeji_total;
    }

    private void setYejiTotalData(final boolean isEXpand, final YejiTotal yejiTotal, final ViewHolder viewHolder) {
        viewHolder.kvName.setValue(yejiTotal.getName());
        viewHolder.sex.setValue(yejiTotal.isSex() ? "男" : "女");
        viewHolder.shenfenCode.setValue(yejiTotal.getNid());
        viewHolder.phone.setValue(yejiTotal.getPhone());
        viewHolder.shenfenType.setValue(yejiTotal.getType());
        viewHolder.zuanye.setValue(yejiTotal.getMajor());
        viewHolder.yuanxiao.setValue(yejiTotal.getGraduate());
        viewHolder.workCode.setValue(yejiTotal.getWorkId());
        viewHolder.address.setValue(yejiTotal.getPlace());
        viewHolder.TitleName.setText(yejiTotal.getName());
        if ("合伙人".equals(yejiTotal.getType())) {
            viewHolder.zuanye.setVisibility(View.GONE);
            viewHolder.yuanxiao.setVisibility(View.GONE);
            viewHolder.workCode.setVisibility(View.VISIBLE);
            viewHolder.address.setVisibility(View.VISIBLE);
        } else {
            viewHolder.zuanye.setVisibility(View.VISIBLE);
            viewHolder.yuanxiao.setVisibility(View.VISIBLE);
            viewHolder.workCode.setVisibility(View.GONE);
            viewHolder.address.setVisibility(View.GONE);
        }
        viewHolder.detail.setVisibility(isEXpand ? View.VISIBLE : View.GONE);
    }

    public class ViewHolder extends TreeViewBinder.ViewHolder {
        @BindView(R.id.indicator_arrow)
        ImageView indicatorArrow;
        @BindView(R.id.TitleName)
        TextView TitleName;
        @BindView(R.id.kvName)
        KeyValueView kvName;
        @BindView(R.id.sex)
        KeyValueView sex;
        @BindView(R.id.shenfenCode)
        KeyValueView shenfenCode;
        @BindView(R.id.workCode)
        KeyValueView workCode;
        @BindView(R.id.phone)
        KeyValueView phone;
        @BindView(R.id.address)
        KeyValueView address;
        @BindView(R.id.shenfenType)
        KeyValueView shenfenType;
        @BindView(R.id.zuanye)
        KeyValueView zuanye;
        @BindView(R.id.yuanxiao)
        KeyValueView yuanxiao;
        @BindView(R.id.detail)
        LinearLayout detail;

        public ImageView getIndicatorArrow() {
            return indicatorArrow;
        }

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

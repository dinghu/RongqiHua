package com.fkh.support.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fkh.support.ui.R;
import com.fkh.support.ui.adapter.BaseListAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dinghu on 2019/7/26.
 */
public class SecondaryListSelectDialog<T1, T2> extends BaseDialog {

    private ImageView ivClose;
    private TextView title;
    private ListView dataT1Listview;
    private ListView dataT2Listview;
    private List<T1> dataT1 = new ArrayList<>();
    private List<List<T2>> dataT2List = new ArrayList<>();
    private List<T2> dataT2 = new ArrayList<>();
    private BaseAdapter adapterT1;
    private BaseAdapter adapterT2;
    private LinkedHashMap<T1, List<T2>> dataContracts;
    private OnSecondaryListSelectListener onSecondaryListSelectListener;
    private T1 item1;
    private T2 item2;

    public void setOnSecondaryListSelectListener(OnSecondaryListSelectListener onSecondaryListSelectListener) {
        this.onSecondaryListSelectListener = onSecondaryListSelectListener;
    }

    public interface OnSecondaryListSelectListener<T1, T2> {
        void onSelect(T1 item1);

        void onSelect(T1 item1, T2 item2);
    }

    public SecondaryListSelectDialog(@NonNull Activity context, LinkedHashMap<T1, List<T2>> dataContracts) {
        this(context, R.style.ClassicDialog, dataContracts);
    }

    public SecondaryListSelectDialog(@NonNull Context context, int themeResId, LinkedHashMap<T1, List<T2>> dataContracts) {
        super(context, themeResId);
        this.dataContracts = dataContracts;
        initData();
    }

    private void initData() {
        // 1. entrySet遍历，在键和值都需要时使用（最常用）
        for (Map.Entry<T1, List<T2>> entry : dataContracts.entrySet()) {
            dataT1.add(entry.getKey());
            dataT2List.add(entry.getValue());
        }

        //默认选中第一个
        dataT2.addAll(dataT2List.get(0));
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        this.setContentView(R.layout.dialog_item_secondary_select_list);
        dataT1Listview = findViewById(R.id.dataT1Listview);
        dataT2Listview = findViewById(R.id.dataT2Listview);
        ivClose = findViewById(R.id.ivClose);
        title = findViewById(R.id.title);
        initDialogWindow(Gravity.BOTTOM, 1.0f);
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        initListViewT1();
        initListViewT2();
    }

    private void initListViewT1() {
        adapterT1 = new BaseListAdapter<T1, ViewHolder>(dataT1, getContext()) {
            @Override
            public int getItemLayout() {
                return R.layout.item_select;
            }

            @Override
            public ViewHolder getViewHolder(View convertView) {
                return new ViewHolder(convertView);
            }

            @Override
            public void initializeViews(int position, T1 t1, ViewHolder viewHolder) {
                if (t1 instanceof String) {
                    viewHolder.tvContent.setText((String) t1);
                }
            }
        };
        dataT1Listview.setAdapter(adapterT1);
        dataT1Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onSecondaryListSelectListener != null) {
                    onSecondaryListSelectListener.onSelect(item1);
                }
                item1 = dataT1.get(position);
                dataT2.clear();
                dataT2.addAll(dataT2List.get(position));
                adapterT2.notifyDataSetChanged();
            }
        });
    }

    private void initListViewT2() {
        adapterT2 = new BaseListAdapter<T2, ViewHolder>(dataT2, getContext()) {
            @Override
            public int getItemLayout() {
                return R.layout.item_select;
            }

            @Override
            public ViewHolder getViewHolder(View convertView) {
                return new ViewHolder(convertView);
            }

            @Override
            public void initializeViews(int position, T2 t2, ViewHolder viewHolder) {

                if (t2 instanceof String) {
                    viewHolder.tvContent.setText((String) t2);
                }
            }
        };
        dataT2Listview.setAdapter(adapterT2);
        dataT2Listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                item2 = dataT2.get(position);
                if (onSecondaryListSelectListener != null) {
                    onSecondaryListSelectListener.onSelect(item1, item2);
                }
            }
        });
    }

    class ViewHolder {
        TextView tvContent;
        ImageView ivSelected;
        TextView tvSelected;

        ViewHolder(View view) {
            tvContent = view.findViewById(R.id.tvContent);
            ivSelected = view.findViewById(R.id.ivSelected);
            tvSelected = view.findViewById(R.id.tvSelected);
        }
    }
}

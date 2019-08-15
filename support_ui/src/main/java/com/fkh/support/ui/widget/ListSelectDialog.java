package com.fkh.support.ui.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fkh.support.ui.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dinghu on 2019/6/12.
 */
public class ListSelectDialog<T> extends BaseDialog {
    ListView listView;
    View ivClose;
    TextView title;

    private List<T> items;
    BaseAdapter itemSelectAdapter;

    int pos = 0;

    private OnItemSelectListener onItemSelectListener;

    public ListSelectDialog(@NonNull Activity context, List<T> items, OnItemSelectListener<T> onItemSelectListener) {
        super(context, R.style.ClassicDialog);
        this.items = items;
        this.onItemSelectListener = onItemSelectListener;
    }

    public void setSelectAccount(String selectAccount) {
        for (int i = 0; i < items.size(); i++) {
            if (selectAccount.equals(items.get(i))) {
                listView.setSelection(i);
                pos = i;
                itemSelectAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    public void setTitle(String titleText) {
        if (title != null) {
            title.setText(titleText);
        }
    }

    public BaseAdapter getItemSelectAdapter(Context context, List<T> items) {
        return new ItemSelectAdapter(getContext(), items);
    }

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new BitmapDrawable());
        this.setContentView(R.layout.dialog_item_select_list);
        listView = findViewById(R.id.listView);
        ivClose = findViewById(R.id.ivClose);
        title = findViewById(R.id.title);
        initDialogWindow(Gravity.BOTTOM, 1.0f);
        itemSelectAdapter = getItemSelectAdapter(getContext(), items);
        listView.setAdapter(itemSelectAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemSelectAdapter.notifyDataSetChanged();
                dismiss();
                if (onItemSelectListener != null) {
                    onItemSelectListener.onSelect(items.get(position));
                }
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public interface OnItemSelectListener<T> {
        void onSelect(T item);
    }

    protected void initializeViews(T object, int pos, ItemSelectAdapter.ViewHolder holder) {
        if (object instanceof String) {
            holder.tvContent.setText((String) object);
            holder.ivSelected.setVisibility(ListSelectDialog.this.pos == pos ? View.VISIBLE : View.GONE);
        } else if (object instanceof Integer) {
            holder.tvContent.setText((Integer) object + "");
            holder.ivSelected.setVisibility(ListSelectDialog.this.pos == pos ? View.VISIBLE : View.GONE);
        }
    }

    public class ItemSelectAdapter extends BaseAdapter {

        private List<T> objects = new ArrayList<>();

        private LayoutInflater layoutInflater;

        public ItemSelectAdapter(Context context, List<T> items) {
            this.layoutInflater = LayoutInflater.from(context);
            objects.addAll(items);
        }

        @Override
        public int getCount() {
            return objects.size();
        }

        @Override
        public T getItem(int position) {
            return objects.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = layoutInflater.inflate(R.layout.item_select, null);
                convertView.setTag(new ViewHolder(convertView));
            }
            initializeViews((T) getItem(position), position, (ViewHolder) convertView.getTag());
            return convertView;
        }

        public class ViewHolder {
            public TextView tvContent;
            public View ivSelected;

            public ViewHolder(View view) {
                tvContent = (TextView) view.findViewById(R.id.tvContent);
                ivSelected = view.findViewById(R.id.ivSelected);
            }
        }
    }
}

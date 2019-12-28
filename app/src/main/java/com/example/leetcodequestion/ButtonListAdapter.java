package com.example.leetcodequestion;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.util.List;

public class ButtonListAdapter extends ArrayAdapter<MainActivity.ButtonVO> {
    private int resourceId;

    private List<MainActivity.ButtonVO> dataList;

    public ButtonListAdapter(Context context, int textViewResourceId,
                             List<MainActivity.ButtonVO> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        dataList = objects;
    }

    public void setDataList(List<MainActivity.ButtonVO> dataList) {
        this.dataList = dataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MainActivity.ButtonVO buttonVO = getItem(position); // 获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent,
                false);
        Button questionButton = (Button) view.findViewById(R.id.question_button);
        final ImageView expendBtn = (ImageView)view.findViewById(R.id.expend_btn);
        final TextView tagTv = (TextView)view.findViewById(R.id.tag_tv);
        tagTv.setText(buttonVO.getTag());
        questionButton.setText(buttonVO.getButtonName());
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), buttonVO.getaClass());
                getContext().startActivity(intent);
            }
        });
        expendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!buttonVO.isExpend()){
                    buttonVO.setExpend(true);
                    tagTv.setVisibility(View.VISIBLE);
                    expendBtn.setImageResource(R.drawable.fold);
                }else {
                    buttonVO.setExpend(false);
                    tagTv.setVisibility(View.GONE);
                    expendBtn.setImageResource(R.drawable.expend);
                }
            }
        });
        return view;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Nullable
    @Override
    public MainActivity.ButtonVO getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

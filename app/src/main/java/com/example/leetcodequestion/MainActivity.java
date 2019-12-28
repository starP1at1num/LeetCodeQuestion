package com.example.leetcodequestion;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.leetcodequestion.LeetCodeQuestion.LeetCode1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<ButtonVO> buttonVOList = new ArrayList<ButtonVO>();
    private EditText searchEt;
    private ImageView searchBtn;
    private HashMap<String, ButtonVO> tagMap = new HashMap<>();
    private ButtonListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        searchBtn = (ImageView) findViewById(R.id.search_button);
        searchEt = (EditText) findViewById(R.id.search_edit_text);
        listView = (ListView) findViewById(R.id.main_list_view);
        initDataList();
        adapter = new ButtonListAdapter(MainActivity.this,
                R.layout.button_list_item, buttonVOList);
        listView.setAdapter(adapter);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(searchEt.getText().toString().trim())) {
                    search(searchEt.getText().toString().trim());
                } else {
                    adapter.setDataList(buttonVOList);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        this.finish();
        super.onDestroy();
    }

    private void initDataList() {
        createButtonVO("1.两数之和", "数组#哈希表#简单", LeetCode1.class);
        createButtonVO("2.两数相加", "数字#链表#中等", LeetCode1.class);
        createButtonVO("3.无重复字符的最长子串", "哈希表#双指针#字符串#滑动窗口#中等", LeetCode1.class);

    }

    public class ButtonVO {
        private final Class aClass;
        private final String buttonName;
        private final String tag;
        private boolean isExpend = false;

        public ButtonVO(String buttonName, String tag, Class aClass) {
            this.aClass = aClass;
            this.buttonName = buttonName;
            this.tag = tag;
        }

        public Class getaClass() {
            return aClass;
        }

        public String getButtonName() {
            return buttonName;
        }

        public String getTag() {
            return tag;
        }

        public boolean isExpend() {
            return isExpend;
        }

        public void setExpend(boolean expend) {
            isExpend = expend;
        }
    }

    private void search(String parameter) {
        String[] tagList = parameter.split("#");
        Set<String> keySet = tagMap.keySet();
        List<ButtonVO> newList = new ArrayList<ButtonVO>();
        // 用一个布尔记录是否完全匹配
        //先默认完全匹配,一旦出现不匹配就置为false
        boolean matched = true;
        for (String tagOfMap : keySet) {
            matched = true;
            for (String tagOfSearch : tagList) {
                //一旦出现不匹配就停止
                if (!tagOfMap.contains(tagOfSearch)) {
                    matched = false;
                    break;
                }
            }
            if (matched) {
                newList.add(tagMap.get(tagOfMap));
            }
        }
        if (!newList.isEmpty()) {
            adapter.setDataList(newList);
            adapter.notifyDataSetChanged();
        } else {
            //没搜到的情况
            Toast.makeText(this, "未找到匹配的结果", Toast.LENGTH_SHORT).show();
        }
    }

    private void createButtonVO(String buttonName, String tag, Class aClass) {
        //把构造VO和添加map抽出一个方法来了
        ButtonVO buttonVO = new ButtonVO(buttonName, tag, aClass);
        buttonVOList.add(buttonVO);
        tagMap.put(tag, buttonVO);
    }
}

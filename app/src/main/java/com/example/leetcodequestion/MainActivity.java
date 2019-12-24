package com.example.leetcodequestion;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private ListView listView;
    private List<ButtonVO> buttonVOList = new ArrayList<ButtonVO>();
    private EditText searchEt;
    private Button searchBtn;
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
        searchBtn = (Button) findViewById(R.id.search_button);
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
        createButtonVO("题目一", "题目一的描述", "类型1#类型2", BaseActivity.class);
        createButtonVO("题目二", "题目二的描述", "类型1", BaseActivity.class);
        createButtonVO("题目三", "题目三的描述", "类型2", BaseActivity.class);

    }

    public class ButtonVO {
        private final String buttonDescription;
        private final Class aClass;
        private final String buttonName;

        public ButtonVO(String buttonName, String buttonDescription, Class aClass) {
            this.aClass = aClass;
            this.buttonDescription = buttonDescription;
            this.buttonName = buttonName;
        }

        public String getButtonDescription() {
            return buttonDescription;
        }

        public Class getaClass() {
            return aClass;
        }

        public String getButtonName() {
            return buttonName;
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
            if(matched) {
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

    private void createButtonVO(String buttonName, String buttonDescription, String tag, Class aClass) {
        //把构造VO和添加map抽出一个方法来了
        ButtonVO buttonVO = new ButtonVO(buttonName, buttonDescription, aClass);
        buttonVOList.add(buttonVO);
        tagMap.put(tag, buttonVO);
    }
}

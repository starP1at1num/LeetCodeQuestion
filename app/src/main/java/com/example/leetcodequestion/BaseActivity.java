package com.example.leetcodequestion;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {
    protected TextView mDescription;
    protected EditText mParameter;
    protected TextView mAnswer;
    protected Button next, back, calculate, setTestCase, analysis;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_layout);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.hide();
        }
        mDescription = (TextView) findViewById(R.id.description);
        mAnswer = (TextView) findViewById(R.id.answer);
        mParameter = (EditText) findViewById(R.id.parameter);
        next = (Button) findViewById(R.id.next);
        next.setOnClickListener(this);
        back = (Button) findViewById(R.id.back);
        back.setOnClickListener(this);
        analysis = (Button)findViewById(R.id.analysis);
        analysis.setOnClickListener(this);
        calculate = (Button) findViewById(R.id.calculate);
        calculate.setOnClickListener(this);
        mDescription.setText(setDescription());
        setTestCase = (Button) findViewById(R.id.set_test_case);
        setTestCase.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.next:
                goToNext();
                break;
            case R.id.calculate:
                if ("".equals(mParameter.getText().toString().trim())) {
                    Toast.makeText(this, "请输入参数", Toast.LENGTH_SHORT).show();
                    break;
                }
                mAnswer.setText(calculateAnswer(mParameter.getText().toString().trim()));
                mAnswer.setVisibility(View.VISIBLE);
                break;
            case R.id.set_test_case:
                mParameter.setText(setDefaultTestCase());
                break;
            case R.id.analysis:
                Intent goToAnalysis = new Intent(this, QuestionAnalysisActivity.class);
                goToAnalysis.putExtra("analysis", setQuestionAnalysis());
                startActivity(goToAnalysis);
                break;
        }
    }

    protected void goToNext() {
        goToNext(MainActivity.class);
    }

    protected void goToNext(Class nextClass) {
        Intent intent = new Intent(this, nextClass);
        startActivity(intent);
    }

    protected String setQuestionAnalysis(){
        return "test";
    }

    protected String calculateAnswer(String parameter) {

        if (!"".equals(parameter)) {
            mAnswer.setVisibility(View.VISIBLE);
        }
        return "";
    }

    protected String setDescription() {
        return "";
    }

    protected String setDefaultTestCase() {
        return "";
    }

    protected List<Integer> getParaArrayList(String parameter) {
        String[] paraList = parameter.split("#");
        List<Integer> mArrayList = new ArrayList<>();
        for (String i : paraList) {
            mArrayList.add(Integer.parseInt(i));
        }
        return mArrayList;
    }

    protected List<Integer> getParaLinkedList(String parameter) {
        String[] paraList = parameter.split("#");
        List<Integer> mArrayList = new LinkedList<>();
        for (String i : paraList) {
            mArrayList.add(Integer.parseInt(i));
        }
        return mArrayList;
    }

    protected List<String> getParaLinkedListForString(String parameter) {
        String[] paraList = parameter.split("#");
        List<String> mArrayList = new LinkedList<>();
        for (String i : paraList) {
            mArrayList.add(i);
        }
        return mArrayList;
    }

    protected Map<Integer, Integer> getParaHashMap(String parameter) {
        String[] paraList = parameter.split("#");
        Map<Integer, Integer> mMap = new HashMap<>();
        int key = 0;
        for (String i : paraList) {
            mMap.put(key, Integer.parseInt(i));
            key++;
        }
        return mMap;
    }

    protected class BinaryTree {
        public BinaryTree left;
        public BinaryTree right;
        public BinaryTree root;
        protected int i = 0;
        //扩展二叉树的字符串
        public String parameter;
        //    数据域
        public Object data;
        //    存节点
        public List<BinaryTree> dataList;

        public BinaryTree(BinaryTree left, BinaryTree right, Object data) {
            this.left = left;
            this.right = right;
            this.data = data;
        }

        //    将初始的左右孩子值为空
        public BinaryTree(Object data) {
            this(null, null, data);
        }

        public BinaryTree() {
        }

        public void create(Object[] objs) {
            dataList = new ArrayList<BinaryTree>();
            //将一个数组的值依次转换为Node节点
            for (Object o : objs) {
                dataList.add(new BinaryTree(o));
            }
            //第一个数为根节点
            root = dataList.get(0);
            //建立二叉树
            for (int i = 0; i < objs.length / 2; i++) {
            //左孩子
                dataList.get(i).left = dataList.get(i * 2 + 1);
            //右孩子
                if (i * 2 + 2 < dataList.size()) {//避免偶数的时候 下标越界
                    dataList.get(i).right = dataList.get(i * 2 + 2);
                }
            }
        }

        public void CreateByExtended(BinaryTree root) {
            //以扩展二叉树方式创建二叉树
            char ch = parameter.charAt(i);
            i++;
            if (ch == '#') {
                root.data = null;
            } else {
                root.data = String.valueOf(ch);
                root.left = new BinaryTree();
                root.right = new BinaryTree();
                CreateByExtended(root.left);
                CreateByExtended(root.right);
            }
        }

        public void CreateByExtendedWithInt(BinaryTree root) {
            //以扩展二叉树方式创建二叉树
            String[] paraList = parameter.split(",");
            String string = paraList[i];
            i++;
            if ("#".equals(string)) {
                root.data = null;
            } else {
                root.data = Integer.parseInt(string);
                root.left = new BinaryTree();
                root.right = new BinaryTree();
                CreateByExtendedWithInt(root.left);
                CreateByExtendedWithInt(root.right);
            }
        }

        public void fresh(BinaryTree root){
            if(root != null){
                if(root.left.data == null){
                    root.left = null;
                }
                if(root.right.data == null){
                    root.right = null;
                }
                fresh(root.left);
                fresh(root.right);
            }
        }

        public BinaryTree getbinaryTree(String parameter) {
            //通过前序遍历和中序遍历的字符串来构建一棵二叉树
            //前序和中序遍历之间用","隔开
            //前序和中序遍历内部用"#"隔开
            //注意parameter要不是输入框的parameter,需要根据实际情况裁剪
            if (!parameter.contains("#") || !parameter.contains(",")) {
                return null;
            }
            String[] paraList = parameter.split(",");
            String[] preOrder = paraList[0].split("#");
            String[] inOrder = paraList[1].split("#");
            if (preOrder.length != inOrder.length) {
                return null;
            }
            BinaryTree resultTree = construct(preOrder, inOrder, 0,
                    preOrder.length - 1, 0, inOrder.length - 1);
            return resultTree;
        }


        public BinaryTree construct(String[] preOrder, String[] inOrder, int startPreOrder,
                                       int endPreOrder, int startInOrder, int endInOrder) {
            //做法是通过递归把大的遍历序列变成很多小的遍历序列
            //然后前序遍历序列的第一个数字是根节点的值
            // 而对应中序遍历序列的值的左边是左子树,右边是右子树
            String root = preOrder[startPreOrder];
            BinaryTree treeRoot = new BinaryTree(root);
            if (preOrder[startPreOrder] == preOrder[endPreOrder]) {
                if (inOrder[startInOrder] == inOrder[endInOrder]) {
                    return treeRoot;
                } else {
                    return null;
                }
            }
            //在中序遍历中找到根节点的值
            int inOrderRootLocation = startInOrder;
            while (inOrderRootLocation <= endInOrder && !root.equals(inOrder[inOrderRootLocation])) {
                inOrderRootLocation++;
            }
            //中序遍历中没有根节点,用户输入了错误的参数
            if (inOrderRootLocation == endInOrder && !root.equals(inOrder[inOrderRootLocation])) {
                return null;
            }
            //左子树节点个数
            int leftLength = inOrderRootLocation - startInOrder;
            //先序遍历中左子树的最后一个节点的位置
            int leftPreOrderEnd = startPreOrder + leftLength;
            if (leftLength > 0) {
                //构建左子树
                treeRoot.left = construct(preOrder, inOrder, startPreOrder + 1,
                        leftPreOrderEnd, startInOrder, inOrderRootLocation - 1);
            }
            if (leftLength < endPreOrder - startPreOrder) {
                //构建右子树
                treeRoot.right = construct(preOrder, inOrder, leftPreOrderEnd + 1,
                        endPreOrder, inOrderRootLocation + 1, endInOrder);
            }
            return treeRoot;
        }
    }

}

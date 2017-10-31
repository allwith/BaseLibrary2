package com.radiogroup.baselibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.radiogroup.baselibrary.R;
import com.radiogroup.baselibrary.photopicker.adapter.NinePicturesAdapter;
import com.radiogroup.baselibrary.widget.NoScrollGridView;
import com.yuyh.library.imgsel.ISNav;
import com.yuyh.library.imgsel.common.ImageLoader;
import com.yuyh.library.imgsel.config.ISListConfig;

import java.util.List;

/**
 * Created by Administrator on 2017/10/28.
 */

public class TestImageChooseActivity extends AppCompatActivity {


    private EditText mEtContent;
    private View mViewGad;
    private NoScrollGridView mGridview;
    private TextView mTvSave;


    private NinePicturesAdapter ninePicturesAdapter;
    private int REQUEST_CODE=120;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_publish_zone);
        initView();
    }

    private void initView() {
        mEtContent = (EditText) findViewById(R.id.et_content);
        mViewGad = (View) findViewById(R.id.view_gad);
        mGridview = (NoScrollGridView) findViewById(R.id.gridview);
        mTvSave = (TextView) findViewById(R.id.tv_save);

        // 自定义图片加载器
        ISNav.getInstance().init(new ImageLoader() {
            @Override
            public void displayImage(Context context, String path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        });


        ninePicturesAdapter = new NinePicturesAdapter(this,9, new NinePicturesAdapter.OnClickAddListener() {
            @Override
            public void onClickAdd(int positin) {
                choosePhoto();
            }
        });
        mGridview.setAdapter(ninePicturesAdapter);
    }

    private void submit() {
        // validate
        String content = mEtContent.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            Toast.makeText(this, "你此刻的心情...", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }




    /**
     * 开启图片选择器
     */
    private void choosePhoto() {

        // 自由配置选项
        ISListConfig config = new ISListConfig.Builder()
                // 是否多选, 默认true
                .multiSelect(true)
                // 是否记住上次选中记录, 仅当multiSelect为true的时候配置，默认为true
                .rememberSelected(false)
                // “确定”按钮背景色
                .btnBgColor(Color.GRAY)
                // “确定”按钮文字颜色
                .btnTextColor(Color.BLUE)
                // 使用沉浸式状态栏
                .statusBarColor(Color.parseColor("#3F51B5"))
                // 返回图标ResId
                .backResId(R.drawable.ic_arrow_back)
                // 标题
                .title("图片")
                // 标题文字颜色
                .titleColor(Color.WHITE)
                // TitleBar背景色
                .titleBgColor(Color.parseColor("#3F51B5"))
                // 裁剪大小。needCrop为true的时候配置
                //.cropSize(1, 1, 200, 200)
               // .needCrop(true)
                // 第一个是否显示相机，默认true
                .needCamera(true)
                // 最大选择图片数量，默认9
                .maxNum(9)
                .build();

// 跳转到图片选择器
        ISNav.getInstance().toListActivity(this, config, REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            List<String> pathList = data.getStringArrayListExtra("result");
            if(ninePicturesAdapter!=null){
                ninePicturesAdapter.addAll(pathList);
            }
        }
    }

    /**
     * 获取到拼接好的图片
     * @return
     */
    private String getPictureString(){
        //拼接图片链接
        List<String> strings = ninePicturesAdapter.getData();
        if (strings != null && strings.size() > 0) {
            StringBuilder allUrl = new StringBuilder();
            for (int i = 0; i < strings.size(); i++) {
                if (!TextUtils.isEmpty(strings.get(i))) {
                    allUrl.append(strings.get(i) + ";");
                }
            }
            if (!TextUtils.isEmpty(allUrl)) {
                String url = allUrl.toString();
                url = url.substring(0, url.lastIndexOf(";"));
                return url;
            }else{
                return "";
            }
        }else{
            return "";
        }
    }












}

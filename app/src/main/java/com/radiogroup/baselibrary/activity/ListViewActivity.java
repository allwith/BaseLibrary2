package com.radiogroup.baselibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.radiogroup.baselibrary.R;
import com.radiogroup.baselibrary.ninegrideview.LGNineGrideView;
import com.radiogroup.baselibrary.photoview.lookover.ViewPagerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 测试9宫格图片显示，以及图片点击放大
 */
public class ListViewActivity extends AppCompatActivity {
    private static String TAG = "ListViewActivity";
    private ListView listView;
    private boolean loadMore;
    private List<DataModel> models = new ArrayList();
    private MyListAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        listView = (ListView)findViewById(R.id.list_view);
        adapter = new MyListAdapter(this,models);
        listView.setAdapter(adapter);
        loadMore();
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_IDLE){
                    if(loadMore){
                        loadMore();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;
            }
        });
    }

    private void loadMore(){
        Log.d(TAG,"loadMore...");
        Random random = new Random();
        int modeSize = models.size();
        int count = 100;
        for (int i = 0; i < count; ++i){
            DataModel model = new DataModel();
            ArrayList<String> lists = new ArrayList();
            int picNum = random.nextInt(9) + 1;
            for (int j = 0; j < picNum; ++j){
                int index = random.nextInt(picNum);
                lists.add(TestData.urlsArray[index]);
            }
            model.setUrls(lists);
            model.setText("num:" + (modeSize + i) + " pic size:" + lists.size());
            models.add(model);
        }
        adapter.notifyDataSetChanged();
    }

    private class DataModel{
        private List<String> urls;
        private String text;

        public List<String> getUrls() {
            return urls;
        }

        public void setUrls(List<String> urls) {
            this.urls = urls;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    private static class MyListAdapter extends BaseAdapter {
        private Context context;
        private List<DataModel> datas;

        public MyListAdapter(Context context, List<DataModel> datas) {
            this.context = context;
            this.datas = datas;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public DataModel getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        static class ViewHolder{
            TextView tv;
            LGNineGrideView grideView;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final DataModel model = getItem(position);
            ViewHolder holder = null;
            if(convertView == null){
                holder = new ViewHolder();
                convertView = ((LayoutInflater)context.getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.list_view_item,parent,false);
                TextView title = (TextView)convertView.findViewById(R.id.text);
                LGNineGrideView LGNineGrideView = (LGNineGrideView)convertView.findViewById(R.id.nine_gride);
                holder.tv = title;
                holder.grideView = LGNineGrideView;
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            holder.tv.setText(model.getText());
            holder.grideView.setUrls(model.getUrls());
            holder.grideView.setOnItemClickListener(new LGNineGrideView.OnItemClickListener() {
                @Override
                public void onClickItem(int position, View view) {
                    Intent intent = new Intent(context, ViewPagerActivity.class);
                    intent.putExtra("position",position);
                    intent.putStringArrayListExtra(ViewPagerActivity.KEY_IMAGE_URLS,(ArrayList<String>) model.getUrls());
                    context.startActivity(intent);
                }
            });
            return convertView;
        }
    }
}

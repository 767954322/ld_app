package com.autodesk.shejijia.consumer.codecorationBase.packages.fragment;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.autodesk.shejijia.consumer.R;
import com.autodesk.shejijia.consumer.codecorationBase.packages.activity.PackageDetailActivity;
import com.autodesk.shejijia.consumer.codecorationBase.packages.activity.ReservationFormActivity;
import com.autodesk.shejijia.shared.framework.fragment.BaseFragment;

/**
 * 套餐
 */
public class PackagesFragment extends BaseFragment implements View.OnClickListener, GridView.OnItemClickListener {


    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_packages;
    }

    @Override
    protected void initView() {

        gv_packages = (GridView) rootView.findViewById(R.id.gv_packages);
        bt_packages_yuyue = (ImageButton) rootView.findViewById(R.id.bt_packages_yuyue);
        iv_packages_tital = (ImageView) rootView.findViewById(R.id.iv_packages_tital);

    }

    @Override
    protected void initData() {

        PacksAdapter packasAdapter = new PacksAdapter();
        gv_packages.setAdapter(packasAdapter);

    }


    @Override
    protected void initListener() {

        bt_packages_yuyue.setOnClickListener(this);
        iv_packages_tital.setOnClickListener(this);
        gv_packages.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bt_packages_yuyue:
                Intent intent_yuyue = new Intent(activity, ReservationFormActivity.class);
                intent_yuyue.putExtra("item_num", 0);
                intent_yuyue.putExtra("item_name", "套餐名称");
                activity.startActivity(intent_yuyue);
                break;
            case R.id.iv_packages_tital:
                Intent intent_detail = new Intent(activity, PackageDetailActivity.class);
                intent_detail.putExtra("item_num", 0);
                intent_detail.putExtra("item_name", "套餐名称");
                activity.startActivity(intent_detail);
                break;
            default:
                break;
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(activity, PackageDetailActivity.class);
        intent.putExtra("item_num", position + 1);
        intent.putExtra("item_name", packages_name[position]);
        startActivity(intent);

    }

    class PacksAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return packages.length;
        }

        @Override
        public Object getItem(int position) {
            return packages[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            MyHolder myHolder;
            if (convertView == null) {
                myHolder = new MyHolder();
                convertView = LayoutInflater.from(activity).inflate(R.layout.fragment_packages_item, null);
                myHolder.iv_packets = (ImageView) convertView.findViewById(R.id.iv_packages_item);
                convertView.setTag(myHolder);
            }
            myHolder = (MyHolder) convertView.getTag();
            myHolder.iv_packets.setImageResource(packages[position]);

            return convertView;
        }

        class MyHolder {
            private ImageView iv_packets;
        }
    }

    private String[] packages_name = {"标题",
            "居然装饰东捷套餐", "居然住美幸福家", "居然装饰北舒套餐",
            "居然住美艺术家", "居然装饰南韵套餐", "居然住美品质家"};

    private int[] packages = {R.drawable.one_pack, R.drawable.two_pack,
            R.drawable.three_pack, R.drawable.four_pack,
            R.drawable.five_pack, R.drawable.six_pack};//R.drawable.seven_pack, R.drawable.eight_pack第七个八个
    private GridView gv_packages;
    private ImageButton bt_packages_yuyue;
    private ImageView iv_packages_tital;

}

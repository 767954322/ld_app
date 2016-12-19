package com.autodesk.shejijia.enterprise.personalcenter.activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;
public class ConstructionIntroductionActivity extends AboutBaseActivity {
    private WebView mWvDesignerIntroduced;
    private TextView mTvVersion;
    @Override
    protected int getSubLayoutResId() {
        return R.layout.activity_design_introduction;
    }

    @Override
    protected void initView() {
        mWvDesignerIntroduced = (WebView) findViewById(R.id.wv_designer_introduced);
        mTvVersion = (TextView) findViewById(R.id.tv_version);
        initWebView(mWvDesignerIntroduced,"file:///android_asset/about/legal/legal.html");//选使用设计家的，待后期更改
    }
    @Override
    protected void initData(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.designer_introduced));
        }
        mTvVersion.setText(Constant.VERSION_NUMBER);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

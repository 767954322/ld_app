package com.autodesk.shejijia.enterprise.personalcenter.activity;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;
import com.autodesk.shejijia.enterprise.R;
import com.autodesk.shejijia.shared.components.common.appglobal.Constant;

public class VersionDescriptionActivity extends AboutBaseActivity {
    private WebView mWvVersionDescription;
    private TextView mTvVersion;
    @Override
    protected int getLayoutResId() {
        return R.layout.activity_version_description;
    }

    @Override
    protected void initView() {
        mWvVersionDescription = (WebView) findViewById(R.id.wview_version_description);
        mTvVersion = (TextView) findViewById(R.id.tv_version);
        initWebView(mWvVersionDescription,"file:///android_asset/about/legal/legelAndroid.html");//选使用设计家的，待后期更改
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(getString(R.string.version_introduced));
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

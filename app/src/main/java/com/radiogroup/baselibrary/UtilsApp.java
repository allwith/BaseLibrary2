package com.radiogroup.baselibrary;


import com.radiogroup.baselibrary.base.BaseApplication;
import com.radiogroup.baselibrary.utils.FileIOUtils;
import com.radiogroup.baselibrary.utils.FileUtils;
import com.radiogroup.baselibrary.utils.Utils;

import java.io.IOException;

/**
 * <pre>
 *     author: Blankj
 *     blog  : http://blankj.com
 *     time  : 2016/10/12
 *     desc  : 工具类测试App
 * </pre>
 */
public class UtilsApp extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
        initLog();
        initCrash();
        initAssets();
    }

    public static void initLog() {
    }

    private void initCrash() {

    }

    private void initAssets() {
        if (!FileUtils.isFileExists(Config.getTestApkPath())) {
            try {
                FileIOUtils.writeFileFromIS(Config.getTestApkPath(), getAssets().open("test_install"), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

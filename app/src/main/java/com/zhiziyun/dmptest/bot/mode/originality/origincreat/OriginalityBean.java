package com.zhiziyun.dmptest.bot.mode.originality.origincreat;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhiziyun on 2018/7/19.
 */

public class OriginalityBean implements Serializable {


    private List<CreativeTemplatePackages> creativeTemplatePackages;

    public void setCreativeTemplatePackages(List<CreativeTemplatePackages> creativeTemplatePackages) {
        this.creativeTemplatePackages = creativeTemplatePackages;
    }

    public List<CreativeTemplatePackages> getCreativeTemplatePackages() {
        return creativeTemplatePackages;
    }


}

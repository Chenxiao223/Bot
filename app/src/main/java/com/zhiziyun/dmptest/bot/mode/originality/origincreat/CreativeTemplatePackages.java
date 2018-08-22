package com.zhiziyun.dmptest.bot.mode.originality.origincreat;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zhiziyun on 2018/7/19.
 */

public class CreativeTemplatePackages implements Serializable {

    private String templatePackageId;
    private String templatePackageName;
    private String templatePackageDesc;
    private String templatePackageCoverImage;
    private List<String> templatePackagePreviewImages;

    public void setTemplatePackageId(String templatePackageId) {
        this.templatePackageId = templatePackageId;
    }

    public String getTemplatePackageId() {
        return templatePackageId;
    }

    public void setTemplatePackageName(String templatePackageName) {
        this.templatePackageName = templatePackageName;
    }

    public String getTemplatePackageName() {
        return templatePackageName;
    }

    public void setTemplatePackageDesc(String templatePackageDesc) {
        this.templatePackageDesc = templatePackageDesc;
    }

    public String getTemplatePackageDesc() {
        return templatePackageDesc;
    }

    public void setTemplatePackageCoverImage(String templatePackageCoverImage) {
        this.templatePackageCoverImage = templatePackageCoverImage;
    }

    public String getTemplatePackageCoverImage() {
        return templatePackageCoverImage;
    }

    public void setTemplatePackagePreviewImages(List<String> templatePackagePreviewImages) {
        this.templatePackagePreviewImages = templatePackagePreviewImages;
    }

    public List<String> getTemplatePackagePreviewImages() {
        return templatePackagePreviewImages;
    }
}

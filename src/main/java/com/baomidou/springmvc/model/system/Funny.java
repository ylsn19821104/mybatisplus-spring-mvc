package com.baomidou.springmvc.model.system;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.springmvc.common.SuperEntity;
import lombok.Data;

import java.util.Date;

@Data
public class Funny extends SuperEntity {
    private Integer funnyId;

    private String articleGenre;

    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date behotTime;

    private String chineseTag;

    private String groupId;

    private String hasGallery;

    private String imageUrl;

    private String isFeedAd;

    private String mediaAvatarUrl;

    private String mediaUrl;

    private String middleMode;

    private String moreMode;

    private String singleMode;

    private String source;

    private String sourceUrl;

    private String tag;

    private String tagUrl;

    private String title;

    private String commentsCount;

    private String document;

}

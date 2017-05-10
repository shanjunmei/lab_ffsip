package com.ffzx.ffsip.service;

import com.ffzx.common.service.BaseService;
import com.ffzx.ffsip.model.WxArticle;
import com.ffzx.ffsip.vo.ArticleInfo;

import java.util.List;

public interface WxArticleService extends BaseService<WxArticle, String> ,Marker{

    /**
     * 获取文章列表
     *
     * @param example
     * @return
     */
    public List<ArticleInfo> findArticleInfo(Object example);
}

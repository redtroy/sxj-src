package com.sxj.mybatis.shard.entity;

import java.util.Date;

public class Blog {
    // 主键
    private Integer id;

    // 用户ID
    private Integer userId;

    // 标题
    private String title;

    // 发表时间
    private Date createTime;

    // 是否启用
    private Boolean isUse;

    // 内容
    private String context;

    /**
     * 返回主键
     * @return 主键
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 返回用户ID
     * @return 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 返回标题
     * @return 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 返回发表时间
     * @return 发表时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置发表时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 返回是否启用
     * @return 是否启用
     */
    public Boolean getIsUse() {
        return isUse;
    }

    /**
     * 设置是否启用
     */
    public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
    }

    /**
     * 返回内容
     * @return 内容
     */
    public String getContext() {
        return context;
    }

    /**
     * 设置内容
     */
    public void setContext(String context) {
        this.context = context == null ? null : context.trim();
    }
}
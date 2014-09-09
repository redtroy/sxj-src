package com.sxj.mybatis.shard.entity;

import java.util.Date;

public class Feedback {
    // 主键
    private Integer id;

    // 所属博文ID
    private Integer blogId;

    // 回复时间
    private Date createTime;

    // 回复内容
    private String content;

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
     * 返回所属博文ID
     * @return 所属博文ID
     */
    public Integer getBlogId() {
        return blogId;
    }

    /**
     * 设置所属博文ID
     */
    public void setBlogId(Integer blogId) {
        this.blogId = blogId;
    }

    /**
     * 返回回复时间
     * @return 回复时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置回复时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 返回回复内容
     * @return 回复内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置回复内容
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}
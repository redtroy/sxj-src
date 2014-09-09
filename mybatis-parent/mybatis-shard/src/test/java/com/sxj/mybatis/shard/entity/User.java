package com.sxj.mybatis.shard.entity;

import java.util.Date;

public class User {
    // 主键ID
    private Integer id;

    // email
    private String email;

    // 密码
    private String password;

    // 真实姓名
    private String realName;

    // 昵称
    private String nickName;

    // 登陆次数
    private Integer loginTimes;

    // 上次登陆时间
    private Date lastLoginTime;

    // 上次登陆IP
    private String lastLoginIp;

    // 生日
    private Date birth;

    // 身份证号
    private String personCard;

    // 头像、logo图片路径
    private String headImage;

    // 等级积分（经验值）
    private Integer levelScore;

    // 商城积分
    private Integer buyScore;

    // 注册时间
    private Date regTime;

    // 启用状态
    private Boolean isUse;

    // 排序
    private Integer sort;

    /**
     * 返回主键ID
     * @return 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 返回email
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置email
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 返回密码
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 返回真实姓名
     * @return 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    /**
     * 返回昵称
     * @return 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * 返回登陆次数
     * @return 登陆次数
     */
    public Integer getLoginTimes() {
        return loginTimes;
    }

    /**
     * 设置登陆次数
     */
    public void setLoginTimes(Integer loginTimes) {
        this.loginTimes = loginTimes;
    }

    /**
     * 返回上次登陆时间
     * @return 上次登陆时间
     */
    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    /**
     * 设置上次登陆时间
     */
    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    /**
     * 返回上次登陆IP
     * @return 上次登陆IP
     */
    public String getLastLoginIp() {
        return lastLoginIp;
    }

    /**
     * 设置上次登陆IP
     */
    public void setLastLoginIp(String lastLoginIp) {
        this.lastLoginIp = lastLoginIp == null ? null : lastLoginIp.trim();
    }

    /**
     * 返回生日
     * @return 生日
     */
    public Date getBirth() {
        return birth;
    }

    /**
     * 设置生日
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * 返回身份证号
     * @return 身份证号
     */
    public String getPersonCard() {
        return personCard;
    }

    /**
     * 设置身份证号
     */
    public void setPersonCard(String personCard) {
        this.personCard = personCard == null ? null : personCard.trim();
    }

    /**
     * 返回头像、logo图片路径
     * @return 头像、logo图片路径
     */
    public String getHeadImage() {
        return headImage;
    }

    /**
     * 设置头像、logo图片路径
     */
    public void setHeadImage(String headImage) {
        this.headImage = headImage == null ? null : headImage.trim();
    }

    /**
     * 返回等级积分（经验值）
     * @return 等级积分（经验值）
     */
    public Integer getLevelScore() {
        return levelScore;
    }

    /**
     * 设置等级积分（经验值）
     */
    public void setLevelScore(Integer levelScore) {
        this.levelScore = levelScore;
    }

    /**
     * 返回商城积分
     * @return 商城积分
     */
    public Integer getBuyScore() {
        return buyScore;
    }

    /**
     * 设置商城积分
     */
    public void setBuyScore(Integer buyScore) {
        this.buyScore = buyScore;
    }

    /**
     * 返回注册时间
     * @return 注册时间
     */
    public Date getRegTime() {
        return regTime;
    }

    /**
     * 设置注册时间
     */
    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    /**
     * 返回启用状态
     * @return 启用状态
     */
    public Boolean getIsUse() {
        return isUse;
    }

    /**
     * 设置启用状态
     */
    public void setIsUse(Boolean isUse) {
        this.isUse = isUse;
    }

    /**
     * 返回排序
     * @return 排序
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
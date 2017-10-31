package com.zzh;

import java.util.Date;

import lombok.*;
/**
  * 
  * account
  * 
  * @author xxx
  * Date 2017-10-31 20:48:19
  */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @ApiModelProperty("账号id")
    private Integer id;

    @ApiModelProperty("账号名")
    private String name;

    @ApiModelProperty("微信联合登陆")
    private String unionid;

    @ApiModelProperty("账号密码")
    private String pwd;

    @ApiModelProperty("来源渠道")
    private Short channel;

    @ApiModelProperty("账号类型；1：手机用户，2：QQ用户，3：微信用户，4：新浪用户，5：支付宝用户，6：微信拼团用户，7：APP拼团用户，8：环球捕手微信用户，9：燕网微信用户，10：环球捕手手机用户，11：返利网快捷登录用户，12：b2b用户 14脉宝用户")
    private Byte type;

    @ApiModelProperty("注册版本")
    private String version;

    @ApiModelProperty("用户头像URL")
    private String image;

    @ApiModelProperty("可用积分")
    private Integer availablePoint;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("昵称（用户可自己修改，跟第三方帐号登录的nickname无关）")
    private String petname;

    @ApiModelProperty("生日")
    private Date birthday;

    @ApiModelProperty("手机号码")
    private String mobileNumber;

    @ApiModelProperty("会员等级类型；0：V0会员，1：V1会员，2：V2会员，3：V3会员")
    private Byte level;

    @ApiModelProperty("累计交易成功订单总金额")
    private Float totalSuccessPrice;

    @ApiModelProperty("邀请码")
    private String recommendedCode;

    @ApiModelProperty("自己推荐人数")
    private Integer recommendedCount;

    @ApiModelProperty("自己推荐下单人数")
    private Integer recommendedOrderCount;

    @ApiModelProperty("小伙伴推荐人数")
    private Integer subRecommendedCount;

    @ApiModelProperty("累计获得的推荐积分")
    private Integer totalRecommendedPoint;

    @ApiModelProperty("是否填写过推荐账户；0：否，1：是")
    private Byte isRecommended;

    @ApiModelProperty("合伙人状态；1：不是合伙人，2：是合伙人，3：合伙人被禁用")
    private Byte partnerStatus;

    @ApiModelProperty("申请合伙人状态；1:未申请，2：申请中，3：申请未通过，4：申请通过")
    private Byte applyPartnerStatus;

    @ApiModelProperty("是否有返回推荐积分；0：否，1：是")
    private Byte isHasOrder;

    @ApiModelProperty("是否购买，1是，0否")
    private Byte isBuy;

    @ApiModelProperty("请求秘钥")
    private String secretKey;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;

    @ApiModelProperty("国家区号(手机)")
    private Integer countryCode;
}
package freshsupermaket.model;

import java.util.Date;

public class BeanCoupon {
    private int coupon_id;//优惠券ID
    private String content;//内容
    private double suitable_amount;//适用金额
    private double credit_amount;//优惠金额
    private Date start_date;//开始日期
    private Date end_date;//结束日期

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public double getSuitable_amount() {
        return suitable_amount;
    }

    public void setSuitable_amount(double suitable_amount) {
        this.suitable_amount = suitable_amount;
    }

    public double getCredit_amount() {
        return credit_amount;
    }

    public void setCredit_amount(double credit_amount) {
        this.credit_amount = credit_amount;
    }

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }
}

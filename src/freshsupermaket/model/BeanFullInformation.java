package freshsupermaket.model;

import java.util.Date;

public class BeanFullInformation {
    private int full_reduction_id;//满折ID
    private String content;//满折内容
    private int suitable_good_quantity;//适用数量
    private double discount;//折扣
    private Date start_date;//开始日期
    private Date end_date;//结束日期

    public int getFull_reduction_id() {
        return full_reduction_id;
    }

    public void setFull_reduction_id(int full_reduction_id) {
        this.full_reduction_id = full_reduction_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSuitable_good_quantity() {
        return suitable_good_quantity;
    }

    public void setSuitable_good_quantity(int suitable_good_quantity) {
        this.suitable_good_quantity = suitable_good_quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
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

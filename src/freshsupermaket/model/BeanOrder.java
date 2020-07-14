package freshsupermaket.model;

import javax.xml.stream.events.StartDocument;
import java.util.Date;

public class BeanOrder {
    private int order_id;//订单ID
    private String customer_id;//顾客ID
    private int coupon_id;//优惠ID
    private int address_id;//地址ID

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    private double original_price;//应付价格
    private double settlement_price;//实付价格
    private Date receive_time;//预计送达时间
    private String order_state;//订单状态
    private String content;//描述
    private Date evaluate_date;//评价日期
    private int star;//星级

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getEvaluate_date() {
        return evaluate_date;
    }

    public void setEvaluate_date(Date evaluate_date) {
        this.evaluate_date = evaluate_date;
    }

    public int getStar() {
        return star;
    }

    public void setStar(int star) {
        this.star = star;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public int getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(int coupon_id) {
        this.coupon_id = coupon_id;
    }

    public double getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(double original_price) {
        this.original_price = original_price;
    }

    public double getSettlement_price() {
        return settlement_price;
    }

    public void setSettlement_price(double settlement_price) {
        this.settlement_price = settlement_price;
    }

    public Date getReceive_time() {
        return receive_time;
    }

    public void setReceive_time(Date receive_time) {
        this.receive_time = receive_time;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }
}

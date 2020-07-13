package freshsupermaket.model;

public class BeanOrderDetail {
    private int order_id;//订单ID
    private int full_reduction_id;//满折ID
    private int good_id;//商品ID
    private int quantity;//数量
    private double price;//价格
    private double discount;//折扣

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getFull_reduction_id() {
        return full_reduction_id;
    }

    public void setFull_reduction_id(int full_reduction_id) {
        this.full_reduction_id = full_reduction_id;
    }

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}

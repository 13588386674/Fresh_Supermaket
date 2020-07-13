package freshsupermaket.model;

public class BuyCart {
    private int good_id;//商品ID
    private  String good_name;//商品名称
    private  int good_type;//商品类别
    private Double price;//商品价格
    private Double MemberPrice;//会员价格
    private Double finallyPrice;//结算价格
    private int quantity;//数量
    private int full_reduction_id;//满折编号
    private double discount;//折扣

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    private String content;

    public int getGood_id() {
        return good_id;
    }

    public Double getFinallyPrice() {
        return finallyPrice;
    }

    public void setFinallyPrice(Double finallyPrice) {
        this.finallyPrice = finallyPrice;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public String getGood_name() {
        return good_name;
    }

    public void setGood_name(String good_name) {
        this.good_name = good_name;
    }


    public int getGood_type() {
        return good_type;
    }

    public void setGood_type(int good_type) {
        this.good_type = good_type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getMemberPrice() {
        return MemberPrice;
    }

    public void setMemberPrice(Double memberPrice) {
        MemberPrice = memberPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

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
}

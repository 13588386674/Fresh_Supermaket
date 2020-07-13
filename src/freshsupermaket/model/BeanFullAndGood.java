package freshsupermaket.model;

import java.util.Date;

public class BeanFullAndGood {
    private int good_id;//商品ID
    private int full_reduction_id;//满折ID
    private Date start_date;//开始时间
    private Date end_date;//结束时间

    public int getGood_id() {
        return good_id;
    }

    public void setGood_id(int good_id) {
        this.good_id = good_id;
    }

    public int getFull_reduction_id() {
        return full_reduction_id;
    }

    public void setFull_reduction_id(int full_reduction_id) {
        this.full_reduction_id = full_reduction_id;
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

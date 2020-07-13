package freshsupermaket.model;

public class BeanAddress {
    private int address_id;//地址ID
    private String customer_id;//顾客ID
    private String province;//省
    private String city;//市
    private String area;//区
    private String address;//地址
    private String connect_person;//联系人
    private String connect_number;//联系电话

    public int getAddress_id() {
        return address_id;
    }

    public void setAddress_id(int address_id) {
        this.address_id = address_id;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getConnect_person() {
        return connect_person;
    }

    public void setConnect_person(String connect_person) {
        this.connect_person = connect_person;
    }

    public String getConnect_number() {
        return connect_number;
    }

    public void setConnect_number(String connect_number) {
        this.connect_number = connect_number;
    }
}

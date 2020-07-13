package freshsupermaket.model;

public class BeanSystemUser {
    private String system_user_id;//管理员ID
    private String system_user_name;//管理员姓名
    private String password;//管理员密码


    public String getSystem_user_id() {
        return system_user_id;
    }

    public void setSystem_user_id(String system_user_id) {
        this.system_user_id = system_user_id;
    }

    public String getSystem_user_name() {
        return system_user_name;
    }

    public void setSystem_user_name(String system_user_name) {
        this.system_user_name = system_user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

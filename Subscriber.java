package com.bee.am;
import java.sql.Timestamp;
public class Subscriber {

    public void setSubscriber_no(String subscriber_no) {
        this.subscriber_no = subscriber_no;
    }

    public void setSys_creation_date(Timestamp sys_creation_date) {
        this.sys_creation_date = sys_creation_date;
    }

    public void setSubscriber(String subscriber) {
        this.subscriber = subscriber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEffective_date(Timestamp effective_date) {
        this.effective_date = effective_date;
    }

    public void setAdditional_info(String additional_info) {
        this.additional_info = additional_info;
    }

    public void setSoc(String soc) {
        this.soc = soc;
    }

    public String getSubscriber_no() {
        return subscriber_no;
    }

    public Timestamp getSys_creation_date() {
        return sys_creation_date;
    }

    public String getSubscriber() {
        return subscriber;
    }

    public String getAddress() {
        return address;
    }

    public Timestamp getEffective_date() {
        return effective_date;
    }

    public String getAdditional_info() {
        return additional_info;
    }

    public String getSoc() {
        return soc;
    }

    public String getLogical_dvc_id() {
        return logical_dvc_id;
    }

    public void setLogical_dvc_id(String logical_dvc_id) {
        this.logical_dvc_id = logical_dvc_id;
    }

    private String subscriber_no;
    private Timestamp sys_creation_date;
    private String subscriber;
    private String address;
    private Timestamp effective_date;
    private String additional_info;
    private String soc;
    private String logical_dvc_id;


}




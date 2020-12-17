package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class SPServiceListResponse {


    /**
     * Status : Success
     * Message : SP Service List
     * Data : [{"service_list":"Service - 1"},{"service_list":"Service - 2"},{"service_list":"Service - 3"},{"service_list":"Service - 4"},{"service_list":"Service - 5"},{"service_list":"Service - 6"}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    private List<DataBean> Data;


    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }


    public String getMessage() {
        return Message;
    }

    public void setMessage(String Message) {
        this.Message = Message;

    }


    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;

    }


    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;

    }

    public static class DataBean  {
        /**
         * service_list : Service - 1
         */

        private String service_list;
        private boolean isSelected;

        public boolean isSelected() {
            return isSelected;
        }

        public void setSelected(boolean selected) {
            isSelected = selected;
        }

        public String getService_list() {
            return service_list;
        }

        public void setService_list(String service_list) {
            this.service_list = service_list;

        }
    }
}

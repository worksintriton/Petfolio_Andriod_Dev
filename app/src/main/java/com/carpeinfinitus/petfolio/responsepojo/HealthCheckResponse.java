package com.carpeinfinitus.petfolio.responsepojo;

import java.util.List;

public class HealthCheckResponse {

    /**
     * statusCode : 200
     * success : true
     * data : {"message":"Success: API services are running successfully.","health":[{"name":"DB","accessible":[{"type":"Connection","success":true,"message":"DB Connection Success"},{"type":"Write","success":true,"message":"Write Operation Success"},{"type":"Read","success":true,"message":"Read Operation Success"},{"type":"Delete","success":true,"message":"Delete Operation Success"},{"type":"Connection Close","success":true,"message":"DB Connection Closed Successfully"}]},{"name":"Redis","accessible":[{"type":"Connection","success":true,"message":"Redis Connected Successfully"},{"type":"Write","success":true,"message":"Write Operation Success"},{"type":"Read","success":true,"message":"Read Operation Success"},{"type":"Delete","success":true,"message":"Delete Operation Success"},{"type":"Connection Close","success":true,"message":"Redis Connection Closed Successfully"}]},{"name":"Internal Micro Services","accessible":[{"api":"http://notification-server-dev.smef-dev.svc.cluster.local/api/notification-mgmt/ping","name":"Notification Management","message":"Notification Management Connected Successfully","status":200,"success":true}]}]}
     */

    private int statusCode;
    private boolean success;
    /**
     * message : Success: API services are running successfully.
     * health : [{"name":"DB","accessible":[{"type":"Connection","success":true,"message":"DB Connection Success"},{"type":"Write","success":true,"message":"Write Operation Success"},{"type":"Read","success":true,"message":"Read Operation Success"},{"type":"Delete","success":true,"message":"Delete Operation Success"},{"type":"Connection Close","success":true,"message":"DB Connection Closed Successfully"}]},{"name":"Redis","accessible":[{"type":"Connection","success":true,"message":"Redis Connected Successfully"},{"type":"Write","success":true,"message":"Write Operation Success"},{"type":"Read","success":true,"message":"Read Operation Success"},{"type":"Delete","success":true,"message":"Delete Operation Success"},{"type":"Connection Close","success":true,"message":"Redis Connection Closed Successfully"}]},{"name":"Internal Micro Services","accessible":[{"api":"http://notification-server-dev.smef-dev.svc.cluster.local/api/notification-mgmt/ping","name":"Notification Management","message":"Notification Management Connected Successfully","status":200,"success":true}]}]
     */

    private DataBean data;

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String message;
        /**
         * name : DB
         * accessible : [{"type":"Connection","success":true,"message":"DB Connection Success"},{"type":"Write","success":true,"message":"Write Operation Success"},{"type":"Read","success":true,"message":"Read Operation Success"},{"type":"Delete","success":true,"message":"Delete Operation Success"},{"type":"Connection Close","success":true,"message":"DB Connection Closed Successfully"}]
         */

        private List<HealthBean> health;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<HealthBean> getHealth() {
            return health;
        }

        public void setHealth(List<HealthBean> health) {
            this.health = health;
        }

        public static class HealthBean {
            private String name;
            /**
             * type : Connection
             * success : true
             * message : DB Connection Success
             */

            private List<AccessibleBean> accessible;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public List<AccessibleBean> getAccessible() {
                return accessible;
            }

            public void setAccessible(List<AccessibleBean> accessible) {
                this.accessible = accessible;
            }

            public static class AccessibleBean {
                private String type;
                private boolean success;
                private String message;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public boolean isSuccess() {
                    return success;
                }

                public void setSuccess(boolean success) {
                    this.success = success;
                }

                public String getMessage() {
                    return message;
                }

                public void setMessage(String message) {
                    this.message = message;
                }
            }
        }
    }
}

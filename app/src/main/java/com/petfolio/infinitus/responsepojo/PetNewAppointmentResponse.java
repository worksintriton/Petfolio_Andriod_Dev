package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class PetNewAppointmentResponse {

    /**
     * Status : Success
     * Message : New Appointment List
     * Data : [{"doc_attched":[],"_id":"5fc5f3bcefe256673d63f59e","doctor_id":{"_id":"5fc4eb2c913fec4204e4b15d","first_name":"BALAJI","last_name":"Dk","user_email":"balajiws@gmail.com","user_phone":"9842670817","date_of_reg":"30/11/2020 06:22 PM","user_type":4,"user_status":"complete","otp":126809,"fb_token":"","device_id":"","device_type":"","__v":0},"appointment_UID":"PET-1606808508267","booking_date":"01-12-2020","booking_time":"02:00 PM","booking_date_time":"01-12-2020 02:00 PM","communication_type":"","msg_id":"PET-1606808508267","video_id":"","user_id":{"_id":"5fc0a53496ce26431e9f3a81","first_name":"Dk","last_name":"pet","user_email":"dkpet1@gmail.com","user_phone":"6383791451","date_of_reg":"27/11/2020 12:35 PM","user_type":1,"user_status":"complete","otp":338724,"fb_token":"","device_id":"","device_type":"","__v":0},"pet_id":{"_id":"5fc4db37729c8f3f654aaad2","user_id":"5fc0a53496ce26431e9f3a81","pet_name":"","pet_type":"Select Pet Type","pet_gender":"","pet_color":"","pet_weight":0,"pet_age":0,"vaccinated":false,"last_vaccination_date":"","default_status":true,"date_and_time":"30-11-2020 05:14 PM","__v":0},"problem_info":"test","appoinment_status":"Incomplete","start_appointment_status":"Not Started","end_appointment_status":"Not End","doc_feedback":"","doc_rate":"0","user_feedback":"","user_rate":"0","display_date":"","server_date_time":"","payment_method":"","prescription_details":"","vaccination_details":"","appointment_types":"","allergies":"test","payment_id":"","amount":"0","__v":0}]
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
         * doc_attched : []
         * _id : 5fc5f3bcefe256673d63f59e
         * doctor_id : {"_id":"5fc4eb2c913fec4204e4b15d","first_name":"BALAJI","last_name":"Dk","user_email":"balajiws@gmail.com","user_phone":"9842670817","date_of_reg":"30/11/2020 06:22 PM","user_type":4,"user_status":"complete","otp":126809,"fb_token":"","device_id":"","device_type":"","__v":0}
         * appointment_UID : PET-1606808508267
         * booking_date : 01-12-2020
         * booking_time : 02:00 PM
         * booking_date_time : 01-12-2020 02:00 PM
         * communication_type :
         * msg_id : PET-1606808508267
         * video_id :
         * user_id : {"_id":"5fc0a53496ce26431e9f3a81","first_name":"Dk","last_name":"pet","user_email":"dkpet1@gmail.com","user_phone":"6383791451","date_of_reg":"27/11/2020 12:35 PM","user_type":1,"user_status":"complete","otp":338724,"fb_token":"","device_id":"","device_type":"","__v":0}
         * pet_id : {"_id":"5fc4db37729c8f3f654aaad2","user_id":"5fc0a53496ce26431e9f3a81","pet_name":"","pet_type":"Select Pet Type","pet_gender":"","pet_color":"","pet_weight":0,"pet_age":0,"vaccinated":false,"last_vaccination_date":"","default_status":true,"date_and_time":"30-11-2020 05:14 PM","__v":0}
         * problem_info : test
         * appoinment_status : Incomplete
         * start_appointment_status : Not Started
         * end_appointment_status : Not End
         * doc_feedback :
         * doc_rate : 0
         * user_feedback :
         * user_rate : 0
         * display_date :
         * server_date_time :
         * payment_method :
         * prescription_details :
         * vaccination_details :
         * appointment_types :
         * allergies : test
         * payment_id :
         * amount : 0
         * __v : 0
         */

        private String _id;
        private DoctorIdBean doctor_id;
        private String appointment_UID;
        private String booking_date;
        private String booking_time;
        private String booking_date_time;
        private String communication_type;
        private String msg_id;
        private String video_id;
        private UserIdBean user_id;
        private PetIdBean pet_id;
        private String problem_info;
        private String appoinment_status;
        private String start_appointment_status;
        private String end_appointment_status;
        private String doc_feedback;
        private String doc_rate;
        private String user_feedback;
        private String user_rate;
        private String display_date;
        private String server_date_time;
        private String payment_method;
        private String prescription_details;
        private String vaccination_details;
        private String appointment_types;
        private String allergies;
        private String payment_id;
        private String amount;
        private int __v;
        private List<?> doc_attched;


        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;

        }


        public DoctorIdBean getDoctor_id() {
            return doctor_id;
        }

        public void setDoctor_id(DoctorIdBean doctor_id) {
            this.doctor_id = doctor_id;

        }


        public String getAppointment_UID() {
            return appointment_UID;
        }

        public void setAppointment_UID(String appointment_UID) {
            this.appointment_UID = appointment_UID;

        }


        public String getBooking_date() {
            return booking_date;
        }

        public void setBooking_date(String booking_date) {
            this.booking_date = booking_date;
        }


        public String getBooking_time() {
            return booking_time;
        }

        public void setBooking_time(String booking_time) {
            this.booking_time = booking_time;

        }


        public String getBooking_date_time() {
            return booking_date_time;
        }

        public void setBooking_date_time(String booking_date_time) {
            this.booking_date_time = booking_date_time;

        }


        public String getCommunication_type() {
            return communication_type;
        }

        public void setCommunication_type(String communication_type) {
            this.communication_type = communication_type;

        }


        public String getMsg_id() {
            return msg_id;
        }

        public void setMsg_id(String msg_id) {
            this.msg_id = msg_id;

        }


        public String getVideo_id() {
            return video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;

        }


        public UserIdBean getUser_id() {
            return user_id;
        }

        public void setUser_id(UserIdBean user_id) {
            this.user_id = user_id;

        }


        public PetIdBean getPet_id() {
            return pet_id;
        }

        public void setPet_id(PetIdBean pet_id) {
            this.pet_id = pet_id;

        }


        public String getProblem_info() {
            return problem_info;
        }

        public void setProblem_info(String problem_info) {
            this.problem_info = problem_info;

        }


        public String getAppoinment_status() {
            return appoinment_status;
        }

        public void setAppoinment_status(String appoinment_status) {
            this.appoinment_status = appoinment_status;

        }


        public String getStart_appointment_status() {
            return start_appointment_status;
        }

        public void setStart_appointment_status(String start_appointment_status) {
            this.start_appointment_status = start_appointment_status;

        }


        public String getEnd_appointment_status() {
            return end_appointment_status;
        }

        public void setEnd_appointment_status(String end_appointment_status) {
            this.end_appointment_status = end_appointment_status;

        }


        public String getDoc_feedback() {
            return doc_feedback;
        }

        public void setDoc_feedback(String doc_feedback) {
            this.doc_feedback = doc_feedback;

        }


        public String getDoc_rate() {
            return doc_rate;
        }

        public void setDoc_rate(String doc_rate) {
            this.doc_rate = doc_rate;

        }

        public String getUser_feedback() {
            return user_feedback;
        }

        public void setUser_feedback(String user_feedback) {
            this.user_feedback = user_feedback;

        }


        public String getUser_rate() {
            return user_rate;
        }

        public void setUser_rate(String user_rate) {
            this.user_rate = user_rate;

        }


        public String getDisplay_date() {
            return display_date;
        }

        public void setDisplay_date(String display_date) {
            this.display_date = display_date;
        }


        public String getServer_date_time() {
            return server_date_time;
        }

        public void setServer_date_time(String server_date_time) {
            this.server_date_time = server_date_time;
        }


        public String getPayment_method() {
            return payment_method;
        }

        public void setPayment_method(String payment_method) {
            this.payment_method = payment_method;
        }


        public String getPrescription_details() {
            return prescription_details;
        }

        public void setPrescription_details(String prescription_details) {
            this.prescription_details = prescription_details;
        }


        public String getVaccination_details() {
            return vaccination_details;
        }

        public void setVaccination_details(String vaccination_details) {
            this.vaccination_details = vaccination_details;

        }


        public String getAppointment_types() {
            return appointment_types;
        }

        public void setAppointment_types(String appointment_types) {
            this.appointment_types = appointment_types;
        }

        public String getAllergies() {
            return allergies;
        }

        public void setAllergies(String allergies) {
            this.allergies = allergies;
        }

        public String getPayment_id() {
            return payment_id;
        }

        public void setPayment_id(String payment_id) {
            this.payment_id = payment_id;
        }


        public String getAmount() {
            return amount;
        }

        public void setAmount(String amount) {
            this.amount = amount;

        }


        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;

        }

        public List<?> getDoc_attched() {
            return doc_attched;
        }

        public void setDoc_attched(List<?> doc_attched) {
            this.doc_attched = doc_attched;

        }

        public static class DoctorIdBean  {
            /**
             * _id : 5fc4eb2c913fec4204e4b15d
             * first_name : BALAJI
             * last_name : Dk
             * user_email : balajiws@gmail.com
             * user_phone : 9842670817
             * date_of_reg : 30/11/2020 06:22 PM
             * user_type : 4
             * user_status : complete
             * otp : 126809
             * fb_token :
             * device_id :
             * device_type :
             * __v : 0
             */

            private String _id;
            private String first_name;
            private String last_name;
            private String user_email;
            private String user_phone;
            private String date_of_reg;
            private int user_type;
            private String user_status;
            private int otp;
            private String fb_token;
            private String device_id;
            private String device_type;
            private int __v;


            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;

            }


            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;

            }


            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;

            }

            public String getUser_email() {
                return user_email;
            }

            public void setUser_email(String user_email) {
                this.user_email = user_email;

            }


            public String getUser_phone() {
                return user_phone;
            }

            public void setUser_phone(String user_phone) {
                this.user_phone = user_phone;

            }


            public String getDate_of_reg() {
                return date_of_reg;
            }

            public void setDate_of_reg(String date_of_reg) {
                this.date_of_reg = date_of_reg;

            }


            public int getUser_type() {
                return user_type;
            }

            public void setUser_type(int user_type) {
                this.user_type = user_type;
            }


            public String getUser_status() {
                return user_status;
            }

            public void setUser_status(String user_status) {
                this.user_status = user_status;
            }

            public int getOtp() {
                return otp;
            }

            public void setOtp(int otp) {
                this.otp = otp;
            }


            public String getFb_token() {
                return fb_token;
            }

            public void setFb_token(String fb_token) {
                this.fb_token = fb_token;
            }

            public String getDevice_id() {
                return device_id;
            }

            public void setDevice_id(String device_id) {
                this.device_id = device_id;
            }

            public String getDevice_type() {
                return device_type;
            }

            public void setDevice_type(String device_type) {
                this.device_type = device_type;
            }

            public int get__v() {
                return __v;
            }

            public void set__v(int __v) {
                this.__v = __v;
            }
        }

        public static class UserIdBean  {
            /**
             * _id : 5fc0a53496ce26431e9f3a81
             * first_name : Dk
             * last_name : pet
             * user_email : dkpet1@gmail.com
             * user_phone : 6383791451
             * date_of_reg : 27/11/2020 12:35 PM
             * user_type : 1
             * user_status : complete
             * otp : 338724
             * fb_token :
             * device_id :
             * device_type :
             * __v : 0
             */

            private String _id;
            private String first_name;
            private String last_name;
            private String user_email;
            private String user_phone;
            private String date_of_reg;
            private int user_type;
            private String user_status;
            private int otp;
            private String fb_token;
            private String device_id;
            private String device_type;
            private int __v;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getFirst_name() {
                return first_name;
            }

            public void setFirst_name(String first_name) {
                this.first_name = first_name;
            }

            public String getLast_name() {
                return last_name;
            }

            public void setLast_name(String last_name) {
                this.last_name = last_name;
            }

            public String getUser_email() {
                return user_email;
            }

            public void setUser_email(String user_email) {
                this.user_email = user_email;
            }

            public String getUser_phone() {
                return user_phone;
            }

            public void setUser_phone(String user_phone) {
                this.user_phone = user_phone;
            }

            public String getDate_of_reg() {
                return date_of_reg;
            }

            public void setDate_of_reg(String date_of_reg) {
                this.date_of_reg = date_of_reg;
            }

            public int getUser_type() {
                return user_type;
            }

            public void setUser_type(int user_type) {
                this.user_type = user_type;
            }

            public String getUser_status() {
                return user_status;
            }

            public void setUser_status(String user_status) {
                this.user_status = user_status;
            }

            public int getOtp() {
                return otp;
            }

            public void setOtp(int otp) {
                this.otp = otp;
            }

            public String getFb_token() {
                return fb_token;
            }

            public void setFb_token(String fb_token) {
                this.fb_token = fb_token;
            }

            public String getDevice_id() {
                return device_id;
            }

            public void setDevice_id(String device_id) {
                this.device_id = device_id;
            }

            public String getDevice_type() {
                return device_type;
            }

            public void setDevice_type(String device_type) {
                this.device_type = device_type;
            }

            public int get__v() {
                return __v;
            }

            public void set__v(int __v) {
                this.__v = __v;
            }
        }

        public static class PetIdBean {
            /**
             * _id : 5fc4db37729c8f3f654aaad2
             * user_id : 5fc0a53496ce26431e9f3a81
             * pet_name :
             * pet_type : Select Pet Type
             * pet_gender :
             * pet_color :
             * pet_weight : 0
             * pet_age : 0
             * vaccinated : false
             * last_vaccination_date :
             * default_status : true
             * date_and_time : 30-11-2020 05:14 PM
             * __v : 0
             */

            private String _id;
            private String user_id;
            private String pet_name;
            private String pet_type;
            private String pet_gender;
            private String pet_color;
            private int pet_weight;
            private int pet_age;
            private boolean vaccinated;
            private String last_vaccination_date;
            private boolean default_status;
            private String date_and_time;
            private int __v;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;

            }


            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;

            }


            public String getPet_name() {
                return pet_name;
            }

            public void setPet_name(String pet_name) {
                this.pet_name = pet_name;
            }

            public String getPet_type() {
                return pet_type;
            }

            public void setPet_type(String pet_type) {
                this.pet_type = pet_type;
            }

            public String getPet_gender() {
                return pet_gender;
            }

            public void setPet_gender(String pet_gender) {
                this.pet_gender = pet_gender;
            }

            public String getPet_color() {
                return pet_color;
            }

            public void setPet_color(String pet_color) {
                this.pet_color = pet_color;
            }

            public int getPet_weight() {
                return pet_weight;
            }

            public void setPet_weight(int pet_weight) {
                this.pet_weight = pet_weight;
            }


            public int getPet_age() {
                return pet_age;
            }

            public void setPet_age(int pet_age) {
                this.pet_age = pet_age;

            }


            public boolean isVaccinated() {
                return vaccinated;
            }

            public void setVaccinated(boolean vaccinated) {
                this.vaccinated = vaccinated;

            }


            public String getLast_vaccination_date() {
                return last_vaccination_date;
            }

            public void setLast_vaccination_date(String last_vaccination_date) {
                this.last_vaccination_date = last_vaccination_date;
            }

            public boolean isDefault_status() {
                return default_status;
            }

            public void setDefault_status(boolean default_status) {
                this.default_status = default_status;
            }

            public String getDate_and_time() {
                return date_and_time;
            }

            public void setDate_and_time(String date_and_time) {
                this.date_and_time = date_and_time;
            }

            public int get__v() {
                return __v;
            }

            public void set__v(int __v) {
                this.__v = __v;
            }
        }
    }
}

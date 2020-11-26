package com.petfolio.infinitus.responsepojo;


import java.util.List;


public class DoctorMissedAppointmentResponse {


    /**
     * Status : Success
     * Message : Completed Appointment List
     * Data : [{"doc_attched":[{"file":"http://google.pdf"}],"_id":"5fb63e80c361654227ec0a17","doctor_id":null,"appointment_UID":"PET-1605779072517","booking_date":"19/11/2020","booking_time":"12:22 pm","booking_date_time":"19/11/2020 12:22 pm","communication_type":"","msg_id":"PET-1605779072517","video_id":"http://vidoe.com","user_id":null,"pet_id":{"_id":"5fb38ea334f6014ea9013d30","pet_img":"http://mysalveo.com/api/uploads/images.jpeg","pet_name":"POP","pet_type":"Dog","pet_breed":"breed 1","pet_gender":"Male","pet_color":"white","pet_weight":120,"pet_age":20,"vaccinated":true,"last_vaccination_date":"23-10-1996","default_status":true,"date_and_time":"23-10-1996 12:09 AM","__v":0},"problem_info":"problem info","appoinment_status":"Completed","start_appointment_status":"Not Started","end_appointment_status":"Not End","doc_feedback":"doc feedback","doc_rate":"5","user_feedback":"user feedback","user_rate":"4.5","display_date":"19/11/2020 01:00 PM","server_date_time":"09/12/2020 02:00 PM","payment_method":"Card","prescription_details":"","vaccination_details":"","appointment_types":"Normal","allergies":"this is","payment_id":"1234567890","amount":"400","__v":0}]
     * Code : 200
     */

    private String Status;
    private String Message;
    private int Code;
    private List<DataBean> Data;


    public static class DataBean {
        /**
         * doc_attched : [{"file":"http://google.pdf"}]
         * _id : 5fb63e80c361654227ec0a17
         * doctor_id : null
         * appointment_UID : PET-1605779072517
         * booking_date : 19/11/2020
         * booking_time : 12:22 pm
         * booking_date_time : 19/11/2020 12:22 pm
         * communication_type :
         * msg_id : PET-1605779072517
         * video_id : http://vidoe.com
         * user_id : null
         * pet_id : {"_id":"5fb38ea334f6014ea9013d30","pet_img":"http://mysalveo.com/api/uploads/images.jpeg","pet_name":"POP","pet_type":"Dog","pet_breed":"breed 1","pet_gender":"Male","pet_color":"white","pet_weight":120,"pet_age":20,"vaccinated":true,"last_vaccination_date":"23-10-1996","default_status":true,"date_and_time":"23-10-1996 12:09 AM","__v":0}
         * problem_info : problem info
         * appoinment_status : Completed
         * start_appointment_status : Not Started
         * end_appointment_status : Not End
         * doc_feedback : doc feedback
         * doc_rate : 5
         * user_feedback : user feedback
         * user_rate : 4.5
         * display_date : 19/11/2020 01:00 PM
         * server_date_time : 09/12/2020 02:00 PM
         * payment_method : Card
         * prescription_details :
         * vaccination_details :
         * appointment_types : Normal
         * allergies : this is
         * payment_id : 1234567890
         * amount : 400
         * __v : 0
         */

        private String _id;
        private Object doctor_id;
        private String appointment_UID;
        private String booking_date;
        private String booking_time;
        private String booking_date_time;
        private String communication_type;
        private String msg_id;
        private String video_id;
        private Object user_id;
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
        private List<DocAttchedBean> doc_attched;


        public static class PetIdBean {
            /**
             * _id : 5fb38ea334f6014ea9013d30
             * pet_img : http://mysalveo.com/api/uploads/images.jpeg
             * pet_name : POP
             * pet_type : Dog
             * pet_breed : breed 1
             * pet_gender : Male
             * pet_color : white
             * pet_weight : 120
             * pet_age : 20
             * vaccinated : true
             * last_vaccination_date : 23-10-1996
             * default_status : true
             * date_and_time : 23-10-1996 12:09 AM
             * __v : 0
             */

            private String _id;
            private String pet_img;
            private String pet_name;
            private String pet_type;
            private String pet_breed;
            private String pet_gender;
            private String pet_color;
            private int pet_weight;
            private int pet_age;
            private boolean vaccinated;
            private String last_vaccination_date;
            private boolean default_status;
            private String date_and_time;
            private int __v;
        }


        public static class DocAttchedBean {
            /**
             * file : http://google.pdf
             */

            private String file;
        }
    }
}




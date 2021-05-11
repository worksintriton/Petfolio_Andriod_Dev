package com.petfolio.infinitus.responsepojo;

import java.util.List;

public class FetchProductByIdResponse {


    /**
     * Status : Success
     * Message : product list
     * Product_details : {"_id":"602e11404775fa0735d7bf40","product_img":["http://54.212.108.156:3000/api/uploads/resize-1613548631141238608collar.jpg","http://54.212.108.156:3000/api/uploads/resize-16135506762041500057collar2.jpg"],"product_title":"DOGISTA PET PRODUCTS Dog Rope Leash,Brass","product_price":180,"product_discount":0,"breed_type":[{"_id":"602d1c0c562e0916bc9b3216","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"Akita","date_and_time":"2/17/2021, 7:07:16 PM","delete_status":false,"updatedAt":"2021-05-07T11:55:15.572Z","createdAt":"2021-02-17T13:37:16.917Z","__v":0},{"_id":"602d1c17562e0916bc9b3217","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"Alaskan Malamute","date_and_time":"2/17/2021, 7:07:26 PM","delete_status":false,"updatedAt":"2021-05-07T11:55:36.570Z","createdAt":"2021-02-17T13:37:27.054Z","__v":0},{"_id":"602d1c20562e0916bc9b3218","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"American Bully","date_and_time":"2/17/2021, 7:07:35 PM","delete_status":false,"updatedAt":"2021-05-07T11:56:17.558Z","createdAt":"2021-02-17T13:37:36.277Z","__v":0},{"_id":"602d1c29562e0916bc9b3219","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"Australlian Sheperd","date_and_time":"2/17/2021, 7:07:44 PM","delete_status":false,"updatedAt":"2021-05-07T11:56:47.871Z","createdAt":"2021-02-17T13:37:45.189Z","__v":0},{"_id":"602d1c3b562e0916bc9b321a","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"Basset Hound","date_and_time":"2/17/2021, 7:08:02 PM","delete_status":false,"updatedAt":"2021-05-07T11:57:12.113Z","createdAt":"2021-02-17T13:38:03.311Z","__v":0},{"_id":"602d1c45562e0916bc9b321b","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"Beagle","date_and_time":"2/17/2021, 7:08:12 PM","delete_status":false,"updatedAt":"2021-05-07T11:57:29.796Z","createdAt":"2021-02-17T13:38:13.348Z","__v":0},{"_id":"602d1c58562e0916bc9b321c","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"Bernese Mountain Dog","date_and_time":"2/17/2021, 7:08:32 PM","delete_status":false,"updatedAt":"2021-05-07T11:58:07.352Z","createdAt":"2021-02-17T13:38:32.748Z","__v":0},{"_id":"602d1c86562e0916bc9b321e","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"Bichon Frise","date_and_time":"2/17/2021, 7:09:18 PM","delete_status":false,"updatedAt":"2021-05-07T11:58:48.236Z","createdAt":"2021-02-17T13:39:18.783Z","__v":0},{"_id":"602d1c97562e0916bc9b321f","pet_type_id":"602d1c6b562e0916bc9b321d","pet_breed":"Abyssinian","date_and_time":"2/17/2021, 7:09:35 PM","delete_status":false,"updatedAt":"2021-05-07T12:20:02.072Z","createdAt":"2021-02-17T13:39:35.923Z","__v":0},{"_id":"602d1cb5562e0916bc9b3220","pet_type_id":"602d1c6b562e0916bc9b321d","pet_breed":"American shortairs","date_and_time":"2/17/2021, 7:10:04 PM","delete_status":false,"updatedAt":"2021-05-07T12:20:39.359Z","createdAt":"2021-02-17T13:40:05.273Z","__v":0},{"_id":"602d1cc2562e0916bc9b3221","pet_type_id":"602d1c6b562e0916bc9b321d","pet_breed":"Cornish rex","date_and_time":"2/17/2021, 7:10:17 PM","delete_status":false,"updatedAt":"2021-05-07T12:21:21.541Z","createdAt":"2021-02-17T13:40:18.477Z","__v":0},{"_id":"602d1cda562e0916bc9b3222","pet_type_id":"602d1c6b562e0916bc9b321d","pet_breed":"Himalayan","date_and_time":"2/17/2021, 7:10:41 PM","delete_status":false,"updatedAt":"2021-05-07T12:22:11.923Z","createdAt":"2021-02-17T13:40:42.336Z","__v":0},{"_id":"602d1ce5562e0916bc9b3223","pet_type_id":"602d1c6b562e0916bc9b321d","pet_breed":"Maine coon","date_and_time":"2/17/2021, 7:10:52 PM","delete_status":false,"updatedAt":"2021-05-07T12:22:44.286Z","createdAt":"2021-02-17T13:40:53.127Z","__v":0},{"_id":"602d1d03562e0916bc9b3225","pet_type_id":"602d1cf4562e0916bc9b3224","pet_breed":"Abyssinian guinea pig","date_and_time":"2/17/2021, 7:11:22 PM","delete_status":false,"updatedAt":"2021-05-07T12:47:12.331Z","createdAt":"2021-02-17T13:41:23.050Z","__v":0},{"_id":"602d1d17562e0916bc9b3226","pet_type_id":"602d1cf4562e0916bc9b3224","pet_breed":"Cuy","date_and_time":"2/17/2021, 7:11:43 PM","delete_status":false,"updatedAt":"2021-05-07T12:48:04.119Z","createdAt":"2021-02-17T13:41:43.781Z","__v":0},{"_id":"602d1d35562e0916bc9b3227","pet_type_id":"602d1cf4562e0916bc9b3224","pet_breed":"Himalayan guinea pig","date_and_time":"2/17/2021, 7:12:12 PM","delete_status":false,"updatedAt":"2021-05-07T12:48:45.271Z","createdAt":"2021-02-17T13:42:13.292Z","__v":0},{"_id":"602d1d55562e0916bc9b3228","pet_type_id":"602d1cf4562e0916bc9b3224","pet_breed":"Sheltie","date_and_time":"2/17/2021, 7:12:44 PM","delete_status":false,"updatedAt":"2021-05-07T12:49:04.082Z","createdAt":"2021-02-17T13:42:45.730Z","__v":0},{"_id":"602d1d68562e0916bc9b3229","pet_type_id":"6095420913144b6dfa3158ce","pet_breed":"Chinese hamsters","date_and_time":"2/17/2021, 7:13:04 PM","delete_status":false,"updatedAt":"2021-05-07T13:37:56.411Z","createdAt":"2021-02-17T13:43:04.974Z","__v":0},{"_id":"602d1d84562e0916bc9b322b","pet_type_id":"602d1d73562e0916bc9b322a","pet_breed":"Chicken","date_and_time":"2/17/2021, 7:13:32 PM","delete_status":false,"updatedAt":"2021-05-07T12:21:01.761Z","createdAt":"2021-02-17T13:43:32.834Z","__v":0},{"_id":"602d1d8d562e0916bc9b322c","pet_type_id":"602d1d73562e0916bc9b322a","pet_breed":"Duroc","date_and_time":"2/17/2021, 7:13:41 PM","delete_status":false,"updatedAt":"2021-02-17T13:43:41.835Z","createdAt":"2021-02-17T13:43:41.835Z","__v":0},{"_id":"602d1d99562e0916bc9b322d","pet_type_id":"602d1d73562e0916bc9b322a","pet_breed":"Hampshire","date_and_time":"2/17/2021, 7:13:52 PM","delete_status":false,"updatedAt":"2021-02-17T13:43:53.109Z","createdAt":"2021-02-17T13:43:53.109Z","__v":0},{"_id":"602d1da3562e0916bc9b322e","pet_type_id":"602d1d73562e0916bc9b322a","pet_breed":"Landrace","date_and_time":"2/17/2021, 7:14:02 PM","delete_status":false,"updatedAt":"2021-02-17T13:44:03.098Z","createdAt":"2021-02-17T13:44:03.098Z","__v":0},{"_id":"602d1dac562e0916bc9b322f","pet_type_id":"602d1d73562e0916bc9b322a","pet_breed":"Poland China","date_and_time":"2/17/2021, 7:14:11 PM","delete_status":false,"updatedAt":"2021-02-17T13:44:12.250Z","createdAt":"2021-02-17T13:44:12.250Z","__v":0},{"_id":"602d1db8562e0916bc9b3230","pet_type_id":"602d1d73562e0916bc9b322a","pet_breed":"Spotted","date_and_time":"2/17/2021, 7:14:23 PM","delete_status":false,"updatedAt":"2021-02-17T13:44:24.650Z","createdAt":"2021-02-17T13:44:24.650Z","__v":0},{"_id":"602d1dc7562e0916bc9b3231","pet_type_id":"602d1d73562e0916bc9b322a","pet_breed":"Yorkshire","date_and_time":"2/17/2021, 7:14:38 PM","delete_status":false,"updatedAt":"2021-02-17T13:44:39.394Z","createdAt":"2021-02-17T13:44:39.394Z","__v":0},{"_id":"602d1e91562e0916bc9b3233","pet_type_id":"602d1dd5562e0916bc9b3232","pet_breed":"guppies","date_and_time":"2/17/2021, 7:18:00 PM","delete_status":false,"updatedAt":"2021-05-07T13:01:08.523Z","createdAt":"2021-02-17T13:48:01.013Z","__v":0},{"_id":"602d1ea0562e0916bc9b3234","pet_type_id":"602d1dd5562e0916bc9b3232","pet_breed":"arowana","date_and_time":"2/17/2021, 7:18:16 PM","delete_status":false,"updatedAt":"2021-05-07T13:00:40.047Z","createdAt":"2021-02-17T13:48:16.990Z","__v":0},{"_id":"602d1efc562e0916bc9b3235","pet_type_id":"602d1dd5562e0916bc9b3232","pet_breed":"Bettas","date_and_time":"2/17/2021, 7:19:47 PM","delete_status":false,"updatedAt":"2021-05-07T12:59:44.605Z","createdAt":"2021-02-17T13:49:48.067Z","__v":0},{"_id":"602d1f1a562e0916bc9b3239","pet_type_id":"602d1dd5562e0916bc9b3232","pet_breed":"Gold fishes","date_and_time":"2/17/2021, 7:20:17 PM","delete_status":false,"updatedAt":"2021-05-07T13:00:08.597Z","createdAt":"2021-02-17T13:50:18.445Z","__v":0},{"_id":"602d1f25562e0916bc9b323a","pet_type_id":"602d1dd5562e0916bc9b3232","pet_breed":"Angelfishes","date_and_time":"2/17/2021, 7:20:28 PM","delete_status":false,"updatedAt":"2021-05-07T12:59:10.146Z","createdAt":"2021-02-17T13:50:29.195Z","__v":0},{"_id":"602d1f35562e0916bc9b323b","pet_type_id":"602d1dd5562e0916bc9b3232","pet_breed":"cat fishes","date_and_time":"2/17/2021, 7:20:44 PM","delete_status":false,"updatedAt":"2021-05-07T13:01:58.132Z","createdAt":"2021-02-17T13:50:45.678Z","__v":0},{"_id":"602d1f47562e0916bc9b323c","pet_type_id":"602d1dd5562e0916bc9b3232","pet_breed":"farming fishes","date_and_time":"2/17/2021, 7:21:02 PM","delete_status":false,"updatedAt":"2021-05-07T13:02:20.489Z","createdAt":"2021-02-17T13:51:03.389Z","__v":0},{"_id":"602d1f60562e0916bc9b323e","pet_type_id":"602d1f52562e0916bc9b323d","pet_breed":"Akhal-teke","date_and_time":"2/17/2021, 7:21:27 PM","delete_status":false,"updatedAt":"2021-05-08T08:54:17.023Z","createdAt":"2021-02-17T13:51:28.582Z","__v":0},{"_id":"602d1f6e562e0916bc9b323f","pet_type_id":"602d1f52562e0916bc9b323d","pet_breed":"Arabian","date_and_time":"2/17/2021, 7:21:41 PM","delete_status":false,"updatedAt":"2021-05-08T08:54:52.504Z","createdAt":"2021-02-17T13:51:42.308Z","__v":0},{"_id":"602d1f78562e0916bc9b3240","pet_type_id":"602d1f52562e0916bc9b323d","pet_breed":"Bhutia","date_and_time":"2/17/2021, 7:21:51 PM","delete_status":false,"updatedAt":"2021-05-08T08:55:03.805Z","createdAt":"2021-02-17T13:51:52.553Z","__v":0},{"_id":"602d1f82562e0916bc9b3241","pet_type_id":"602d1f52562e0916bc9b323d","pet_breed":"Deccani","date_and_time":"2/17/2021, 7:22:02 PM","delete_status":false,"updatedAt":"2021-05-08T08:55:22.525Z","createdAt":"2021-02-17T13:52:02.995Z","__v":0},{"_id":"602d1f8f562e0916bc9b3242","pet_type_id":"602d1f52562e0916bc9b323d","pet_breed":"Friesian","date_and_time":"2/17/2021, 7:22:14 PM","delete_status":false,"updatedAt":"2021-05-08T08:55:52.874Z","createdAt":"2021-02-17T13:52:15.281Z","__v":0},{"_id":"602d1f9a562e0916bc9b3243","pet_type_id":"602d1f52562e0916bc9b323d","pet_breed":"Gypsy","date_and_time":"2/17/2021, 7:22:26 PM","delete_status":false,"updatedAt":"2021-05-08T08:54:05.791Z","createdAt":"2021-02-17T13:52:26.747Z","__v":0}],"pet_type":[{"_id":"602d1bf4562e0916bc9b3215","pet_type_title":" Dogs - (Indian & Foreign) ","date_and_time":"2/17/2021, 7:06:51 PM","delete_status":false,"updatedAt":"2021-05-07T13:49:18.725Z","createdAt":"2021-02-17T13:36:52.141Z","__v":0}],"age":[1,2,3,4,5],"cat_id":{"_id":"602ccc8518da357d363da7cb","img_path":"http://54.212.108.156:3000/api/uploads/resize-1613548631141238608collar.jpg","product_cate":"PET COLLARS & LEASHES","img_index":0,"show_status":true,"date_and_time":"2/17/2021, 1:27:57 PM","delete_status":false,"updatedAt":"2021-02-17T07:57:57.193Z","createdAt":"2021-02-17T07:57:57.193Z","__v":0},"threshould":"145","product_discription":"100 % Brand new and high quality, very durable and 100 percent soft feeling\nGreat to help stop your dog from pulling and train your dog\nDog rope cast hook with zinc plating iron fitting\nThis leash is the best choice for tracking, patrolling, walking, training and just for daily use. It doesn't lose its shape so if your dog pulls it too much this leash won't stretch","product_fav":false,"product_rating":1.6,"product_review":5,"product_related":[{"_id":"602e11404775fa0735d7bf40","product_img":"http://54.212.108.156:3000/api/uploads/resize-1613548631141238608collar.jpg","product_title":"DOGISTA PET PRODUCTS Dog Rope Leash,Brass","product_price":180,"product_discount":0,"product_fav":false,"product_rating":1.6,"product_review":5}],"product_cart_count":6}
     * vendor_details : {"bussiness_gallery":[{"bussiness_gallery":"http://54.212.108.156:3000/api/uploads/602a1ff9b3c2dd2c152d77d715-02-2021 12:47 PMPetfolio1.jpg"}],"certifi":[{"certifi":"http://54.212.108.156:3000/api/uploads/602a1ff9b3c2dd2c152d77d715-02-2021 12:47 PM0001019010010862809_12132020_01022021.PDF"}],"_id":"602a2061b3c2dd2c152d77d8","user_id":"602a1ff9b3c2dd2c152d77d7","user_name":"Dinesh","user_email":"iddinesh@gmail.com","bussiness_name":"Grooming","bussiness_email":"iddineshgmailcom","bussiness":"Grooming 1","bussiness_phone":"6383791452","business_reg":"chennai","photo_id_proof":"http://54.212.108.156:3000/api/uploads/602a1ff9b3c2dd2c152d77d715-02-2021 12:47 PM0001019010010862809_12132020_01022021.PDF","govt_id_proof":"http://54.212.108.156:3000/api/uploads/602a1ff9b3c2dd2c152d77d715-02-2021 12:47 PM0001019010010862809_12132020_01022021.PDF","date_and_time":"15/02/2021 12:48 PM","mobile_type":"Android","profile_status":true,"profile_verification_status":"Verified","bussiness_loc":"Chennai 2a, 3rd Main Rd, Ram Nagar, Karnam Street, Dhadeswaram Nagar, Velachery, Chennai, Tamil Nadu 600042, India","bussiness_lat":12.983187999999998,"bussiness_long":80.2236476,"delete_status":false,"updatedAt":"2021-02-16T05:24:26.413Z","createdAt":"2021-02-15T07:18:57.547Z","__v":0}
     * Code : 200
     */

    private String Status;
    private String Message;
    /**
     * _id : 602e11404775fa0735d7bf40
     * product_img : ["http://54.212.108.156:3000/api/uploads/resize-1613548631141238608collar.jpg","http://54.212.108.156:3000/api/uploads/resize-16135506762041500057collar2.jpg"]
     * product_title : DOGISTA PET PRODUCTS Dog Rope Leash,Brass
     * product_price : 180
     * product_discount : 0
     * breed_type : [{"_id":"602d1c0c562e0916bc9b3216","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"Akita","date_and_time":"2/17/2021, 7:07:16 PM","delete_status":false,"updatedAt":"2021-05-07T11:55:15.572Z","createdAt":"2021-02-17T13:37:16.917Z","__v":0},{"_id":"602d1c17562e0916bc9b3217","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"Alaskan Malamute","date_and_time":"2/17/2021, 7:07:26 PM","delete_status":false,"updatedAt":"2021-05-07T11:55:36.570Z","createdAt":"2021-02-17T13:37:27.054Z","__v":0},{"_id":"602d1c20562e0916bc9b3218","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"American Bully","date_and_time":"2/17/2021, 7:07:35 PM","delete_status":false,"updatedAt":"2021-05-07T11:56:17.558Z","createdAt":"2021-02-17T13:37:36.277Z","__v":0},{"_id":"602d1c29562e0916bc9b3219","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"Australlian Sheperd","date_and_time":"2/17/2021, 7:07:44 PM","delete_status":false,"updatedAt":"2021-05-07T11:56:47.871Z","createdAt":"2021-02-17T13:37:45.189Z","__v":0},{"_id":"602d1c3b562e0916bc9b321a","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"Basset Hound","date_and_time":"2/17/2021, 7:08:02 PM","delete_status":false,"updatedAt":"2021-05-07T11:57:12.113Z","createdAt":"2021-02-17T13:38:03.311Z","__v":0},{"_id":"602d1c45562e0916bc9b321b","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"Beagle","date_and_time":"2/17/2021, 7:08:12 PM","delete_status":false,"updatedAt":"2021-05-07T11:57:29.796Z","createdAt":"2021-02-17T13:38:13.348Z","__v":0},{"_id":"602d1c58562e0916bc9b321c","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"Bernese Mountain Dog","date_and_time":"2/17/2021, 7:08:32 PM","delete_status":false,"updatedAt":"2021-05-07T11:58:07.352Z","createdAt":"2021-02-17T13:38:32.748Z","__v":0},{"_id":"602d1c86562e0916bc9b321e","pet_type_id":"602d1bf4562e0916bc9b3215","pet_breed":"Bichon Frise","date_and_time":"2/17/2021, 7:09:18 PM","delete_status":false,"updatedAt":"2021-05-07T11:58:48.236Z","createdAt":"2021-02-17T13:39:18.783Z","__v":0},{"_id":"602d1c97562e0916bc9b321f","pet_type_id":"602d1c6b562e0916bc9b321d","pet_breed":"Abyssinian","date_and_time":"2/17/2021, 7:09:35 PM","delete_status":false,"updatedAt":"2021-05-07T12:20:02.072Z","createdAt":"2021-02-17T13:39:35.923Z","__v":0},{"_id":"602d1cb5562e0916bc9b3220","pet_type_id":"602d1c6b562e0916bc9b321d","pet_breed":"American shortairs","date_and_time":"2/17/2021, 7:10:04 PM","delete_status":false,"updatedAt":"2021-05-07T12:20:39.359Z","createdAt":"2021-02-17T13:40:05.273Z","__v":0},{"_id":"602d1cc2562e0916bc9b3221","pet_type_id":"602d1c6b562e0916bc9b321d","pet_breed":"Cornish rex","date_and_time":"2/17/2021, 7:10:17 PM","delete_status":false,"updatedAt":"2021-05-07T12:21:21.541Z","createdAt":"2021-02-17T13:40:18.477Z","__v":0},{"_id":"602d1cda562e0916bc9b3222","pet_type_id":"602d1c6b562e0916bc9b321d","pet_breed":"Himalayan","date_and_time":"2/17/2021, 7:10:41 PM","delete_status":false,"updatedAt":"2021-05-07T12:22:11.923Z","createdAt":"2021-02-17T13:40:42.336Z","__v":0},{"_id":"602d1ce5562e0916bc9b3223","pet_type_id":"602d1c6b562e0916bc9b321d","pet_breed":"Maine coon","date_and_time":"2/17/2021, 7:10:52 PM","delete_status":false,"updatedAt":"2021-05-07T12:22:44.286Z","createdAt":"2021-02-17T13:40:53.127Z","__v":0},{"_id":"602d1d03562e0916bc9b3225","pet_type_id":"602d1cf4562e0916bc9b3224","pet_breed":"Abyssinian guinea pig","date_and_time":"2/17/2021, 7:11:22 PM","delete_status":false,"updatedAt":"2021-05-07T12:47:12.331Z","createdAt":"2021-02-17T13:41:23.050Z","__v":0},{"_id":"602d1d17562e0916bc9b3226","pet_type_id":"602d1cf4562e0916bc9b3224","pet_breed":"Cuy","date_and_time":"2/17/2021, 7:11:43 PM","delete_status":false,"updatedAt":"2021-05-07T12:48:04.119Z","createdAt":"2021-02-17T13:41:43.781Z","__v":0},{"_id":"602d1d35562e0916bc9b3227","pet_type_id":"602d1cf4562e0916bc9b3224","pet_breed":"Himalayan guinea pig","date_and_time":"2/17/2021, 7:12:12 PM","delete_status":false,"updatedAt":"2021-05-07T12:48:45.271Z","createdAt":"2021-02-17T13:42:13.292Z","__v":0},{"_id":"602d1d55562e0916bc9b3228","pet_type_id":"602d1cf4562e0916bc9b3224","pet_breed":"Sheltie","date_and_time":"2/17/2021, 7:12:44 PM","delete_status":false,"updatedAt":"2021-05-07T12:49:04.082Z","createdAt":"2021-02-17T13:42:45.730Z","__v":0},{"_id":"602d1d68562e0916bc9b3229","pet_type_id":"6095420913144b6dfa3158ce","pet_breed":"Chinese hamsters","date_and_time":"2/17/2021, 7:13:04 PM","delete_status":false,"updatedAt":"2021-05-07T13:37:56.411Z","createdAt":"2021-02-17T13:43:04.974Z","__v":0},{"_id":"602d1d84562e0916bc9b322b","pet_type_id":"602d1d73562e0916bc9b322a","pet_breed":"Chicken","date_and_time":"2/17/2021, 7:13:32 PM","delete_status":false,"updatedAt":"2021-05-07T12:21:01.761Z","createdAt":"2021-02-17T13:43:32.834Z","__v":0},{"_id":"602d1d8d562e0916bc9b322c","pet_type_id":"602d1d73562e0916bc9b322a","pet_breed":"Duroc","date_and_time":"2/17/2021, 7:13:41 PM","delete_status":false,"updatedAt":"2021-02-17T13:43:41.835Z","createdAt":"2021-02-17T13:43:41.835Z","__v":0},{"_id":"602d1d99562e0916bc9b322d","pet_type_id":"602d1d73562e0916bc9b322a","pet_breed":"Hampshire","date_and_time":"2/17/2021, 7:13:52 PM","delete_status":false,"updatedAt":"2021-02-17T13:43:53.109Z","createdAt":"2021-02-17T13:43:53.109Z","__v":0},{"_id":"602d1da3562e0916bc9b322e","pet_type_id":"602d1d73562e0916bc9b322a","pet_breed":"Landrace","date_and_time":"2/17/2021, 7:14:02 PM","delete_status":false,"updatedAt":"2021-02-17T13:44:03.098Z","createdAt":"2021-02-17T13:44:03.098Z","__v":0},{"_id":"602d1dac562e0916bc9b322f","pet_type_id":"602d1d73562e0916bc9b322a","pet_breed":"Poland China","date_and_time":"2/17/2021, 7:14:11 PM","delete_status":false,"updatedAt":"2021-02-17T13:44:12.250Z","createdAt":"2021-02-17T13:44:12.250Z","__v":0},{"_id":"602d1db8562e0916bc9b3230","pet_type_id":"602d1d73562e0916bc9b322a","pet_breed":"Spotted","date_and_time":"2/17/2021, 7:14:23 PM","delete_status":false,"updatedAt":"2021-02-17T13:44:24.650Z","createdAt":"2021-02-17T13:44:24.650Z","__v":0},{"_id":"602d1dc7562e0916bc9b3231","pet_type_id":"602d1d73562e0916bc9b322a","pet_breed":"Yorkshire","date_and_time":"2/17/2021, 7:14:38 PM","delete_status":false,"updatedAt":"2021-02-17T13:44:39.394Z","createdAt":"2021-02-17T13:44:39.394Z","__v":0},{"_id":"602d1e91562e0916bc9b3233","pet_type_id":"602d1dd5562e0916bc9b3232","pet_breed":"guppies","date_and_time":"2/17/2021, 7:18:00 PM","delete_status":false,"updatedAt":"2021-05-07T13:01:08.523Z","createdAt":"2021-02-17T13:48:01.013Z","__v":0},{"_id":"602d1ea0562e0916bc9b3234","pet_type_id":"602d1dd5562e0916bc9b3232","pet_breed":"arowana","date_and_time":"2/17/2021, 7:18:16 PM","delete_status":false,"updatedAt":"2021-05-07T13:00:40.047Z","createdAt":"2021-02-17T13:48:16.990Z","__v":0},{"_id":"602d1efc562e0916bc9b3235","pet_type_id":"602d1dd5562e0916bc9b3232","pet_breed":"Bettas","date_and_time":"2/17/2021, 7:19:47 PM","delete_status":false,"updatedAt":"2021-05-07T12:59:44.605Z","createdAt":"2021-02-17T13:49:48.067Z","__v":0},{"_id":"602d1f1a562e0916bc9b3239","pet_type_id":"602d1dd5562e0916bc9b3232","pet_breed":"Gold fishes","date_and_time":"2/17/2021, 7:20:17 PM","delete_status":false,"updatedAt":"2021-05-07T13:00:08.597Z","createdAt":"2021-02-17T13:50:18.445Z","__v":0},{"_id":"602d1f25562e0916bc9b323a","pet_type_id":"602d1dd5562e0916bc9b3232","pet_breed":"Angelfishes","date_and_time":"2/17/2021, 7:20:28 PM","delete_status":false,"updatedAt":"2021-05-07T12:59:10.146Z","createdAt":"2021-02-17T13:50:29.195Z","__v":0},{"_id":"602d1f35562e0916bc9b323b","pet_type_id":"602d1dd5562e0916bc9b3232","pet_breed":"cat fishes","date_and_time":"2/17/2021, 7:20:44 PM","delete_status":false,"updatedAt":"2021-05-07T13:01:58.132Z","createdAt":"2021-02-17T13:50:45.678Z","__v":0},{"_id":"602d1f47562e0916bc9b323c","pet_type_id":"602d1dd5562e0916bc9b3232","pet_breed":"farming fishes","date_and_time":"2/17/2021, 7:21:02 PM","delete_status":false,"updatedAt":"2021-05-07T13:02:20.489Z","createdAt":"2021-02-17T13:51:03.389Z","__v":0},{"_id":"602d1f60562e0916bc9b323e","pet_type_id":"602d1f52562e0916bc9b323d","pet_breed":"Akhal-teke","date_and_time":"2/17/2021, 7:21:27 PM","delete_status":false,"updatedAt":"2021-05-08T08:54:17.023Z","createdAt":"2021-02-17T13:51:28.582Z","__v":0},{"_id":"602d1f6e562e0916bc9b323f","pet_type_id":"602d1f52562e0916bc9b323d","pet_breed":"Arabian","date_and_time":"2/17/2021, 7:21:41 PM","delete_status":false,"updatedAt":"2021-05-08T08:54:52.504Z","createdAt":"2021-02-17T13:51:42.308Z","__v":0},{"_id":"602d1f78562e0916bc9b3240","pet_type_id":"602d1f52562e0916bc9b323d","pet_breed":"Bhutia","date_and_time":"2/17/2021, 7:21:51 PM","delete_status":false,"updatedAt":"2021-05-08T08:55:03.805Z","createdAt":"2021-02-17T13:51:52.553Z","__v":0},{"_id":"602d1f82562e0916bc9b3241","pet_type_id":"602d1f52562e0916bc9b323d","pet_breed":"Deccani","date_and_time":"2/17/2021, 7:22:02 PM","delete_status":false,"updatedAt":"2021-05-08T08:55:22.525Z","createdAt":"2021-02-17T13:52:02.995Z","__v":0},{"_id":"602d1f8f562e0916bc9b3242","pet_type_id":"602d1f52562e0916bc9b323d","pet_breed":"Friesian","date_and_time":"2/17/2021, 7:22:14 PM","delete_status":false,"updatedAt":"2021-05-08T08:55:52.874Z","createdAt":"2021-02-17T13:52:15.281Z","__v":0},{"_id":"602d1f9a562e0916bc9b3243","pet_type_id":"602d1f52562e0916bc9b323d","pet_breed":"Gypsy","date_and_time":"2/17/2021, 7:22:26 PM","delete_status":false,"updatedAt":"2021-05-08T08:54:05.791Z","createdAt":"2021-02-17T13:52:26.747Z","__v":0}]
     * pet_type : [{"_id":"602d1bf4562e0916bc9b3215","pet_type_title":" Dogs - (Indian & Foreign) ","date_and_time":"2/17/2021, 7:06:51 PM","delete_status":false,"updatedAt":"2021-05-07T13:49:18.725Z","createdAt":"2021-02-17T13:36:52.141Z","__v":0}]
     * age : [1,2,3,4,5]
     * cat_id : {"_id":"602ccc8518da357d363da7cb","img_path":"http://54.212.108.156:3000/api/uploads/resize-1613548631141238608collar.jpg","product_cate":"PET COLLARS & LEASHES","img_index":0,"show_status":true,"date_and_time":"2/17/2021, 1:27:57 PM","delete_status":false,"updatedAt":"2021-02-17T07:57:57.193Z","createdAt":"2021-02-17T07:57:57.193Z","__v":0}
     * threshould : 145
     * product_discription : 100 % Brand new and high quality, very durable and 100 percent soft feeling
     Great to help stop your dog from pulling and train your dog
     Dog rope cast hook with zinc plating iron fitting
     This leash is the best choice for tracking, patrolling, walking, training and just for daily use. It doesn't lose its shape so if your dog pulls it too much this leash won't stretch
     * product_fav : false
     * product_rating : 1.6
     * product_review : 5
     * product_related : [{"_id":"602e11404775fa0735d7bf40","product_img":"http://54.212.108.156:3000/api/uploads/resize-1613548631141238608collar.jpg","product_title":"DOGISTA PET PRODUCTS Dog Rope Leash,Brass","product_price":180,"product_discount":0,"product_fav":false,"product_rating":1.6,"product_review":5}]
     * product_cart_count : 6
     */

    private ProductDetailsBean Product_details;
    /**
     * bussiness_gallery : [{"bussiness_gallery":"http://54.212.108.156:3000/api/uploads/602a1ff9b3c2dd2c152d77d715-02-2021 12:47 PMPetfolio1.jpg"}]
     * certifi : [{"certifi":"http://54.212.108.156:3000/api/uploads/602a1ff9b3c2dd2c152d77d715-02-2021 12:47 PM0001019010010862809_12132020_01022021.PDF"}]
     * _id : 602a2061b3c2dd2c152d77d8
     * user_id : 602a1ff9b3c2dd2c152d77d7
     * user_name : Dinesh
     * user_email : iddinesh@gmail.com
     * bussiness_name : Grooming
     * bussiness_email : iddineshgmailcom
     * bussiness : Grooming 1
     * bussiness_phone : 6383791452
     * business_reg : chennai
     * photo_id_proof : http://54.212.108.156:3000/api/uploads/602a1ff9b3c2dd2c152d77d715-02-2021 12:47 PM0001019010010862809_12132020_01022021.PDF
     * govt_id_proof : http://54.212.108.156:3000/api/uploads/602a1ff9b3c2dd2c152d77d715-02-2021 12:47 PM0001019010010862809_12132020_01022021.PDF
     * date_and_time : 15/02/2021 12:48 PM
     * mobile_type : Android
     * profile_status : true
     * profile_verification_status : Verified
     * bussiness_loc : Chennai 2a, 3rd Main Rd, Ram Nagar, Karnam Street, Dhadeswaram Nagar, Velachery, Chennai, Tamil Nadu 600042, India
     * bussiness_lat : 12.983187999999998
     * bussiness_long : 80.2236476
     * delete_status : false
     * updatedAt : 2021-02-16T05:24:26.413Z
     * createdAt : 2021-02-15T07:18:57.547Z
     * __v : 0
     */

    private VendorDetailsBean vendor_details;
    private int Code;

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

    public ProductDetailsBean getProduct_details() {
        return Product_details;
    }

    public void setProduct_details(ProductDetailsBean Product_details) {
        this.Product_details = Product_details;
    }

    public VendorDetailsBean getVendor_details() {
        return vendor_details;
    }

    public void setVendor_details(VendorDetailsBean vendor_details) {
        this.vendor_details = vendor_details;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public static class ProductDetailsBean {
        private String _id;
        private String product_title;
        private int product_price;
        private int product_discount;
        /**
         * _id : 602ccc8518da357d363da7cb
         * img_path : http://54.212.108.156:3000/api/uploads/resize-1613548631141238608collar.jpg
         * product_cate : PET COLLARS & LEASHES
         * img_index : 0
         * show_status : true
         * date_and_time : 2/17/2021, 1:27:57 PM
         * delete_status : false
         * updatedAt : 2021-02-17T07:57:57.193Z
         * createdAt : 2021-02-17T07:57:57.193Z
         * __v : 0
         */

        private CatIdBean cat_id;
        private String threshould;
        private String product_discription;
        private boolean product_fav;
        private double product_rating;
        private int product_review;
        private int product_cart_count;
        private List<String> product_img;
        /**
         * _id : 602d1c0c562e0916bc9b3216
         * pet_type_id : 602d1bf4562e0916bc9b3215
         * pet_breed : Akita
         * date_and_time : 2/17/2021, 7:07:16 PM
         * delete_status : false
         * updatedAt : 2021-05-07T11:55:15.572Z
         * createdAt : 2021-02-17T13:37:16.917Z
         * __v : 0
         */

        private List<BreedTypeBean> breed_type;
        /**
         * _id : 602d1bf4562e0916bc9b3215
         * pet_type_title :  Dogs - (Indian & Foreign)
         * date_and_time : 2/17/2021, 7:06:51 PM
         * delete_status : false
         * updatedAt : 2021-05-07T13:49:18.725Z
         * createdAt : 2021-02-17T13:36:52.141Z
         * __v : 0
         */

        private List<PetTypeBean> pet_type;
        private List<Integer> age;
        /**
         * _id : 602e11404775fa0735d7bf40
         * product_img : http://54.212.108.156:3000/api/uploads/resize-1613548631141238608collar.jpg
         * product_title : DOGISTA PET PRODUCTS Dog Rope Leash,Brass
         * product_price : 180
         * product_discount : 0
         * product_fav : false
         * product_rating : 1.6
         * product_review : 5
         */

        private List<ProductRelatedBean> product_related;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getProduct_title() {
            return product_title;
        }

        public void setProduct_title(String product_title) {
            this.product_title = product_title;
        }

        public int getProduct_price() {
            return product_price;
        }

        public void setProduct_price(int product_price) {
            this.product_price = product_price;
        }

        public int getProduct_discount() {
            return product_discount;
        }

        public void setProduct_discount(int product_discount) {
            this.product_discount = product_discount;
        }

        public CatIdBean getCat_id() {
            return cat_id;
        }

        public void setCat_id(CatIdBean cat_id) {
            this.cat_id = cat_id;
        }

        public String getThreshould() {
            return threshould;
        }

        public void setThreshould(String threshould) {
            this.threshould = threshould;
        }

        public String getProduct_discription() {
            return product_discription;
        }

        public void setProduct_discription(String product_discription) {
            this.product_discription = product_discription;
        }

        public boolean isProduct_fav() {
            return product_fav;
        }

        public void setProduct_fav(boolean product_fav) {
            this.product_fav = product_fav;
        }

        public double getProduct_rating() {
            return product_rating;
        }

        public void setProduct_rating(double product_rating) {
            this.product_rating = product_rating;
        }

        public int getProduct_review() {
            return product_review;
        }

        public void setProduct_review(int product_review) {
            this.product_review = product_review;
        }

        public int getProduct_cart_count() {
            return product_cart_count;
        }

        public void setProduct_cart_count(int product_cart_count) {
            this.product_cart_count = product_cart_count;
        }

        public List<String> getProduct_img() {
            return product_img;
        }

        public void setProduct_img(List<String> product_img) {
            this.product_img = product_img;
        }

        public List<BreedTypeBean> getBreed_type() {
            return breed_type;
        }

        public void setBreed_type(List<BreedTypeBean> breed_type) {
            this.breed_type = breed_type;
        }

        public List<PetTypeBean> getPet_type() {
            return pet_type;
        }

        public void setPet_type(List<PetTypeBean> pet_type) {
            this.pet_type = pet_type;
        }

        public List<Integer> getAge() {
            return age;
        }

        public void setAge(List<Integer> age) {
            this.age = age;
        }

        public List<ProductRelatedBean> getProduct_related() {
            return product_related;
        }

        public void setProduct_related(List<ProductRelatedBean> product_related) {
            this.product_related = product_related;
        }

        public static class CatIdBean {
            private String _id;
            private String img_path;
            private String product_cate;
            private int img_index;
            private boolean show_status;
            private String date_and_time;
            private boolean delete_status;
            private String updatedAt;
            private String createdAt;
            private int __v;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getImg_path() {
                return img_path;
            }

            public void setImg_path(String img_path) {
                this.img_path = img_path;
            }

            public String getProduct_cate() {
                return product_cate;
            }

            public void setProduct_cate(String product_cate) {
                this.product_cate = product_cate;
            }

            public int getImg_index() {
                return img_index;
            }

            public void setImg_index(int img_index) {
                this.img_index = img_index;
            }

            public boolean isShow_status() {
                return show_status;
            }

            public void setShow_status(boolean show_status) {
                this.show_status = show_status;
            }

            public String getDate_and_time() {
                return date_and_time;
            }

            public void setDate_and_time(String date_and_time) {
                this.date_and_time = date_and_time;
            }

            public boolean isDelete_status() {
                return delete_status;
            }

            public void setDelete_status(boolean delete_status) {
                this.delete_status = delete_status;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public int get__v() {
                return __v;
            }

            public void set__v(int __v) {
                this.__v = __v;
            }
        }

        public static class BreedTypeBean {
            private String _id;
            private String pet_type_id;
            private String pet_breed;
            private String date_and_time;
            private boolean delete_status;
            private String updatedAt;
            private String createdAt;
            private int __v;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getPet_type_id() {
                return pet_type_id;
            }

            public void setPet_type_id(String pet_type_id) {
                this.pet_type_id = pet_type_id;
            }

            public String getPet_breed() {
                return pet_breed;
            }

            public void setPet_breed(String pet_breed) {
                this.pet_breed = pet_breed;
            }

            public String getDate_and_time() {
                return date_and_time;
            }

            public void setDate_and_time(String date_and_time) {
                this.date_and_time = date_and_time;
            }

            public boolean isDelete_status() {
                return delete_status;
            }

            public void setDelete_status(boolean delete_status) {
                this.delete_status = delete_status;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public int get__v() {
                return __v;
            }

            public void set__v(int __v) {
                this.__v = __v;
            }
        }

        public static class PetTypeBean {
            private String _id;
            private String pet_type_title;
            private String date_and_time;
            private boolean delete_status;
            private String updatedAt;
            private String createdAt;
            private int __v;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getPet_type_title() {
                return pet_type_title;
            }

            public void setPet_type_title(String pet_type_title) {
                this.pet_type_title = pet_type_title;
            }

            public String getDate_and_time() {
                return date_and_time;
            }

            public void setDate_and_time(String date_and_time) {
                this.date_and_time = date_and_time;
            }

            public boolean isDelete_status() {
                return delete_status;
            }

            public void setDelete_status(boolean delete_status) {
                this.delete_status = delete_status;
            }

            public String getUpdatedAt() {
                return updatedAt;
            }

            public void setUpdatedAt(String updatedAt) {
                this.updatedAt = updatedAt;
            }

            public String getCreatedAt() {
                return createdAt;
            }

            public void setCreatedAt(String createdAt) {
                this.createdAt = createdAt;
            }

            public int get__v() {
                return __v;
            }

            public void set__v(int __v) {
                this.__v = __v;
            }
        }

        public static class ProductRelatedBean {
            private String _id;
            private String product_img;
            private String product_title;
            private int product_price;
            private int product_discount;
            private boolean product_fav;
            private double product_rating;
            private int product_review;

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getProduct_img() {
                return product_img;
            }

            public void setProduct_img(String product_img) {
                this.product_img = product_img;
            }

            public String getProduct_title() {
                return product_title;
            }

            public void setProduct_title(String product_title) {
                this.product_title = product_title;
            }

            public int getProduct_price() {
                return product_price;
            }

            public void setProduct_price(int product_price) {
                this.product_price = product_price;
            }

            public int getProduct_discount() {
                return product_discount;
            }

            public void setProduct_discount(int product_discount) {
                this.product_discount = product_discount;
            }

            public boolean isProduct_fav() {
                return product_fav;
            }

            public void setProduct_fav(boolean product_fav) {
                this.product_fav = product_fav;
            }

            public double getProduct_rating() {
                return product_rating;
            }

            public void setProduct_rating(double product_rating) {
                this.product_rating = product_rating;
            }

            public int getProduct_review() {
                return product_review;
            }

            public void setProduct_review(int product_review) {
                this.product_review = product_review;
            }
        }
    }

    public static class VendorDetailsBean {
        private String _id;
        private String user_id;
        private String user_name;
        private String user_email;
        private String bussiness_name;
        private String bussiness_email;
        private String bussiness;
        private String bussiness_phone;
        private String business_reg;
        private String photo_id_proof;
        private String govt_id_proof;
        private String date_and_time;
        private String mobile_type;
        private boolean profile_status;
        private String profile_verification_status;
        private String bussiness_loc;
        private double bussiness_lat;
        private double bussiness_long;
        private boolean delete_status;
        private String updatedAt;
        private String createdAt;
        private int __v;
        /**
         * bussiness_gallery : http://54.212.108.156:3000/api/uploads/602a1ff9b3c2dd2c152d77d715-02-2021 12:47 PMPetfolio1.jpg
         */

        private List<BussinessGalleryBean> bussiness_gallery;
        /**
         * certifi : http://54.212.108.156:3000/api/uploads/602a1ff9b3c2dd2c152d77d715-02-2021 12:47 PM0001019010010862809_12132020_01022021.PDF
         */

        private List<CertifiBean> certifi;

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

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_email() {
            return user_email;
        }

        public void setUser_email(String user_email) {
            this.user_email = user_email;
        }

        public String getBussiness_name() {
            return bussiness_name;
        }

        public void setBussiness_name(String bussiness_name) {
            this.bussiness_name = bussiness_name;
        }

        public String getBussiness_email() {
            return bussiness_email;
        }

        public void setBussiness_email(String bussiness_email) {
            this.bussiness_email = bussiness_email;
        }

        public String getBussiness() {
            return bussiness;
        }

        public void setBussiness(String bussiness) {
            this.bussiness = bussiness;
        }

        public String getBussiness_phone() {
            return bussiness_phone;
        }

        public void setBussiness_phone(String bussiness_phone) {
            this.bussiness_phone = bussiness_phone;
        }

        public String getBusiness_reg() {
            return business_reg;
        }

        public void setBusiness_reg(String business_reg) {
            this.business_reg = business_reg;
        }

        public String getPhoto_id_proof() {
            return photo_id_proof;
        }

        public void setPhoto_id_proof(String photo_id_proof) {
            this.photo_id_proof = photo_id_proof;
        }

        public String getGovt_id_proof() {
            return govt_id_proof;
        }

        public void setGovt_id_proof(String govt_id_proof) {
            this.govt_id_proof = govt_id_proof;
        }

        public String getDate_and_time() {
            return date_and_time;
        }

        public void setDate_and_time(String date_and_time) {
            this.date_and_time = date_and_time;
        }

        public String getMobile_type() {
            return mobile_type;
        }

        public void setMobile_type(String mobile_type) {
            this.mobile_type = mobile_type;
        }

        public boolean isProfile_status() {
            return profile_status;
        }

        public void setProfile_status(boolean profile_status) {
            this.profile_status = profile_status;
        }

        public String getProfile_verification_status() {
            return profile_verification_status;
        }

        public void setProfile_verification_status(String profile_verification_status) {
            this.profile_verification_status = profile_verification_status;
        }

        public String getBussiness_loc() {
            return bussiness_loc;
        }

        public void setBussiness_loc(String bussiness_loc) {
            this.bussiness_loc = bussiness_loc;
        }

        public double getBussiness_lat() {
            return bussiness_lat;
        }

        public void setBussiness_lat(double bussiness_lat) {
            this.bussiness_lat = bussiness_lat;
        }

        public double getBussiness_long() {
            return bussiness_long;
        }

        public void setBussiness_long(double bussiness_long) {
            this.bussiness_long = bussiness_long;
        }

        public boolean isDelete_status() {
            return delete_status;
        }

        public void setDelete_status(boolean delete_status) {
            this.delete_status = delete_status;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public List<BussinessGalleryBean> getBussiness_gallery() {
            return bussiness_gallery;
        }

        public void setBussiness_gallery(List<BussinessGalleryBean> bussiness_gallery) {
            this.bussiness_gallery = bussiness_gallery;
        }

        public List<CertifiBean> getCertifi() {
            return certifi;
        }

        public void setCertifi(List<CertifiBean> certifi) {
            this.certifi = certifi;
        }

        public static class BussinessGalleryBean {
            private String bussiness_gallery;

            public String getBussiness_gallery() {
                return bussiness_gallery;
            }

            public void setBussiness_gallery(String bussiness_gallery) {
                this.bussiness_gallery = bussiness_gallery;
            }
        }

        public static class CertifiBean {
            private String certifi;

            public String getCertifi() {
                return certifi;
            }

            public void setCertifi(String certifi) {
                this.certifi = certifi;
            }
        }
    }
}

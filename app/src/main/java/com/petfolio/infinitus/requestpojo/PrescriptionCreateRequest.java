package com.petfolio.infinitus.requestpojo;

import java.util.List;

public class PrescriptionCreateRequest{


	/**
	 * doctor_id : 5ef3472a4b9bd73eb1cff539
	 * Date : 23-10-2020 12:00 AM
	 * Doctor_Comments : test
	 * PDF_format :
	 * Prescription_type : Image / PDF
	 * Prescription_img : http://mysalveo.com/api/public/prescriptions/231afd32-6d68-4288-a8e5-1c599833c0e8.pdf
	 * user_id : 5ef2c092c006bb0ed174c771
	 * Prescription_data : [{"Quantity":"3","Tablet_name":"dolo","consumption":"twice"}]
	 * Treatment_Done_by : Self
	 * Appointment_ID
	 */

	private String doctor_id;
	private String Date;
	private String Doctor_Comments;
	private String PDF_format;
	private String Prescription_type;
	private String Prescription_img;
	private String user_id;
	private String Treatment_Done_by;
	private List<PrescriptionDataBean> Prescription_data;
	private String Appointment_ID;

	public String getAppointment_ID() {
		return Appointment_ID;
	}

	public void setAppointment_ID(String appointment_ID) {
		Appointment_ID = appointment_ID;
	}

	public String getDoctor_id() {
		return doctor_id;
	}

	public void setDoctor_id(String doctor_id) {
		this.doctor_id = doctor_id;

	}

	public String getDate() {
		return Date;
	}

	public void setDate(String Date) {
		this.Date = Date;

	}


	public String getDoctor_Comments() {
		return Doctor_Comments;
	}

	public void setDoctor_Comments(String Doctor_Comments) {
		this.Doctor_Comments = Doctor_Comments;

	}


	public String getPDF_format() {
		return PDF_format;
	}

	public void setPDF_format(String PDF_format) {
		this.PDF_format = PDF_format;

	}


	public String getPrescription_type() {
		return Prescription_type;
	}

	public void setPrescription_type(String Prescription_type) {
		this.Prescription_type = Prescription_type;

	}


	public String getPrescription_img() {
		return Prescription_img;
	}

	public void setPrescription_img(String Prescription_img) {
		this.Prescription_img = Prescription_img;

	}


	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;

	}


	public String getTreatment_Done_by() {
		return Treatment_Done_by;
	}

	public void setTreatment_Done_by(String Treatment_Done_by) {
		this.Treatment_Done_by = Treatment_Done_by;

	}


	public List<PrescriptionDataBean> getPrescription_data() {
		return Prescription_data;
	}

	public void setPrescription_data(List<PrescriptionDataBean> Prescription_data) {
		this.Prescription_data = Prescription_data;

	}

	public static class PrescriptionDataBean  {
		/**
		 * Quantity : 3
		 * Tablet_name : dolo
		 * consumption : twice
		 */

		private String Quantity;
		private String Tablet_name;
		private String consumption;


		public String getQuantity() {
			return Quantity;
		}

		public void setQuantity(String Quantity) {
			this.Quantity = Quantity;

		}


		public String getTablet_name() {
			return Tablet_name;
		}

		public void setTablet_name(String Tablet_name) {
			this.Tablet_name = Tablet_name;

		}

		public String getConsumption() {
			return consumption;
		}

		public void setConsumption(String consumption) {
			this.consumption = consumption;

		}
	}
}
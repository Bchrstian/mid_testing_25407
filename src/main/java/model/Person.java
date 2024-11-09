package model;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public class Person {
	@Column(name = "person_id")
	public String person_id;

	@Column(name = "first_name")
	public String first_name;

	@Column(name = "last_name")
	public String last_name;

	@Enumerated(EnumType.STRING)
	@Column(name = "gender")
	public Gender gender;

	@Column(name = "phone_number")
	public String phone_number;
	
	@Column(name="email")
	public String email;

	public Person() {
		super();
	}

	public Person(String person_id, String first_name, String last_name, Gender gender, String phone_number, String email) {
		super();
		this.person_id = person_id;
		this.first_name = first_name;
		this.last_name = last_name;
		this.gender = gender;
		this.phone_number = phone_number;
		this.email = email;
	}

	public String getPerson_id() {
		return person_id;
	}

	public void setPerson_id(String person_id) {
		this.person_id = person_id;
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	

}

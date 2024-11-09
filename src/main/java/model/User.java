package model;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "app_user")
public class User extends Person {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", updatable = false, nullable = false)
	public UUID user_id;

	@Column(name = "user_name")
	public String user_name;

	@Column(name = "password")
	public String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "role")
	public Role role;

	@OneToMany(mappedBy = "user")
	List<Borrower> borrower;

	@OneToMany(mappedBy = "user")
	List<Membership> membership;
	
	@ManyToOne
	@JoinColumn(name="village_id", referencedColumnName = "location_id")
	Location location;

	public User() {
		super();
	}
	
	





	public User(UUID user_id, String user_name, String password, Role role, List<Borrower> borrower,
			List<Membership> membership, Location location) {
		super();
		this.user_id = user_id;
		this.user_name = user_name;
		this.password = password;
		this.role = role;
		this.borrower = borrower;
		this.membership = membership;
		this.location = location;
	}







	public UUID getUser_id() {
		return user_id;
	}



	public void setUser_id(UUID user_id) {
		this.user_id = user_id;
	}



	public String getUser_name() {
		return user_name;
	}



	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public Role getRole() {
		return role;
	}



	public void setRole(Role role) {
		this.role = role;
	}



	public List<Borrower> getBorrower() {
		return borrower;
	}



	public void setBorrower(List<Borrower> borrower) {
		this.borrower = borrower;
	}



	public List<Membership> getMembership() {
		return membership;
	}



	public void setMembership(List<Membership> membership) {
		this.membership = membership;
	}



	public Location getLocation() {
		return location;
	}



	public void setLocation(Location location) {
		this.location = location;
	}	
}

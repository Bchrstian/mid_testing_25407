package model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "Location")
public class Location {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "location_id")
	private UUID locationId;

	@Column(name = "location_code")
	private String locationCode;

	@Column(name = "location_name", nullable = false)
	private String locationName;

	@Column(name = "location_type", nullable = false)
	@Enumerated(EnumType.STRING)
	private Location_type locationType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id", referencedColumnName = "location_id")
	private Location parentLocation;

	@OneToMany(mappedBy = "parentLocation", cascade = CascadeType.ALL)
	private List<Location> childLocations = new ArrayList<>();

	@OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
	private List<User> users = new ArrayList<>();

	// Getters and Setters

	public UUID getLocationId() {
		return locationId;
	}

	public void setLocationId(UUID locationId) {
		this.locationId = locationId;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getLocationName() {
		return locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public Location_type getLocationType() {
		return locationType;
	}

	public void setLocationType(Location_type locationType) {
		this.locationType = locationType;
	}

	public Location getParentLocation() {
		return parentLocation;
	}

	public void setParentLocation(Location parentLocation) {
		this.parentLocation = parentLocation;
	}

	public List<Location> getChildLocations() {
		return childLocations;
	}

	public void setChildLocations(List<Location> childLocations) {
		this.childLocations = childLocations;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	// Utility method to add child location
	public void addChildLocation(Location childLocation) {
		childLocation.setParentLocation(this);
		this.childLocations.add(childLocation);
	}
}
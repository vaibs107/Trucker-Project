package io.egen.trucker.entity;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
		@NamedQuery(name = "Geolocation.getAllGeolocationsByVin", query = "SELECT g FROM Geolocation g WHERE g.vin=:paramVin") })
public class Geolocation {

	@Id
	private String geolocationId;

	private String formattedAddress;
	private String vin;

	public Geolocation() {
		this.geolocationId = UUID.randomUUID().toString();
	}

	public String getGeolocationId() {
		return geolocationId;
	}

	public void setGeolocationId(String geolocationId) {
		this.geolocationId = geolocationId;
	}

	public String getFormattedAddress() {
		return formattedAddress;
	}

	public void setFormattedAddress(String formattedAddress) {
		this.formattedAddress = formattedAddress;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	@Override
	public String toString() {
		return "Geolocation [geolocationId=" + geolocationId + ", formattedAddress=" + formattedAddress + ", vin=" + vin
				+ "]";
	}

}

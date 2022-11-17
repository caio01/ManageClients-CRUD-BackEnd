package dio.springbootweb.model;

import java.util.Objects;

import org.bson.types.ObjectId;

public class Client {
	
	private ObjectId _id;
	private String name;
	private String phone;
	private String email;
	private String address;
	private String country;
	
	public ObjectId getId() {
		return _id;
	}
	public void setId(ObjectId _id) {
		this._id = _id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	@Override
	public int hashCode() {
		return Objects.hash(address, country, email, _id, name, phone);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		return Objects.equals(address, other.address) && Objects.equals(country, other.country)
				&& Objects.equals(email, other.email) && Objects.equals(_id, other._id)
				&& Objects.equals(name, other.name) && Objects.equals(phone, other.phone);
	}
	
	@Override
    public String toString() {
        return "Clients [_id=" + _id + ", phone=" + phone + ", email=" + email + ", address=" + address + ", country=" + country + "]";
    }
	
}


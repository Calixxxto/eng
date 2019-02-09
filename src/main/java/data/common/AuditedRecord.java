package data.common;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.Date;

@MappedSuperclass
public class AuditedRecord {
	
	@CreatedBy // expects AuditorAware implementation in app.
	@Column(name="add_user")
	private String addUser;
	
	@LastModifiedBy // expects AuditorAware implementation in app.
	@Column(name="mod_user")
	private String modUser;
	
	@LastModifiedDate
	@Column(name="mod_date")
	private Date modDate;

	@CreatedDate
	@Column(name="add_date")
	private Date addDate;

	public String getAddUser() {
		return addUser;
	}

	public void setAddUser(String addUser) {
		this.addUser = addUser;
	}

	public String getModUser() {
		return modUser;
	}

	public void setModUser(String modUser) {
		this.modUser = modUser;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}
	
	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}
	
	@PrePersist
	private void initModDate() {
		this.modDate = new Date();
	}

}

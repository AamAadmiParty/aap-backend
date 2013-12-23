package com.next.aap.core.persistance;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name = "ac_role")
public class PcRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Version
	@Column(name = "ver")
	private int ver;
	@Column(name = "date_created")
	private Date dateCreated;
	@Column(name = "date_modified")
	private Date dateModified;
	@Column(name = "creator_id")
	private Long creatorId;
	@Column(name = "modifier_id")
	private Long modifierId;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="pc_id")
    private ParliamentConstituency parliamentConstituency;
	@Column(name="pc_id", insertable=false,updatable=false)
	private Long parliamentConstituencyId;

	@ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="role_id")
    private Role role;
	@Column(name="role_id", insertable=false,updatable=false)
	private Long roleId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getVer() {
		return ver;
	}
	public void setVer(int ver) {
		this.ver = ver;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getDateModified() {
		return dateModified;
	}
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public Long getModifierId() {
		return modifierId;
	}
	public void setModifierId(Long modifierId) {
		this.modifierId = modifierId;
	}
	public ParliamentConstituency getParliamentConstituency() {
		return parliamentConstituency;
	}
	public void setParliamentConstituency(ParliamentConstituency parliamentConstituency) {
		this.parliamentConstituency = parliamentConstituency;
	}
	public Long getParliamentConstituencyId() {
		return parliamentConstituencyId;
	}
	public void setParliamentConstituencyId(Long parliamentConstituencyId) {
		this.parliamentConstituencyId = parliamentConstituencyId;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Long getRoleId() {
		return roleId;
	}
	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}
	
	
	
}

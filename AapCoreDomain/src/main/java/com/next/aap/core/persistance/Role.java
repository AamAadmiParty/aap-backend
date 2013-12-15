package com.next.aap.core.persistance;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="role")
public class Role {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Version
	@Column(name="ver")
	private int ver;
	@Column(name="date_created")
	private Date dateCreated;
	@Column(name="date_modified")
	private Date dateModified;
	@Column(name="creator_id")
	private Long creatorId;
	@Column(name="modifier_id")
	private Long modifierId;

	@Column(name = "name", nullable = false)
	private String name;

	@ManyToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "role_permissions",
	joinColumns = {
	@JoinColumn(name="role_id") 
	},
	inverseJoinColumns = {
	@JoinColumn(name="permission_id")
	})
	Set<Permission> permissions;



}

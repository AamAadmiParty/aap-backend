package com.next.aap.web.dto;

import java.io.Serializable;

public class AssemblyConstituencyDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private Long districtId;
	private String urlName;
	
	public AssemblyConstituencyDto(AssemblyConstituencyDto assemblyConstituencyDto){
		id = assemblyConstituencyDto.id;
		name = assemblyConstituencyDto.name;
	}
	public AssemblyConstituencyDto(){
		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getDistrictId() {
		return districtId;
	}
	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
	public String getUrlName() {
		return urlName;
	}
	public void setUrlName(String urlName) {
		this.urlName = urlName;
	}
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AssemblyConstituencyDto other = (AssemblyConstituencyDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AssemblyConstituencyDto [id=" + id + ", name=" + name
				+ ", urlName=" + urlName + "]";
	}
	
}

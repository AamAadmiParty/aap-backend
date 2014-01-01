package com.next.aap.web.jsf.beans.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.next.aap.web.dto.RoleDto;

public class RoleDtoModel extends ListDataModel<RoleDto> implements SelectableDataModel<RoleDto>{

	public RoleDtoModel() {  
    }  
  
    public RoleDtoModel(List<RoleDto> data) {  
        super(data);  
    }  
    
	@Override
	public RoleDto getRowData(String rowKey) {
		List<RoleDto> roles = (List<RoleDto>) getWrappedData();  
        
        for(RoleDto car : roles) {  
            if(car.getId().toString().equals(rowKey))  
                return car;  
        }  
          
        return null;  
	}

	@Override
	public Object getRowKey(RoleDto roleDto) {
		return roleDto.getId().toString();
	}

}

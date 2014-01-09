package com.next.aap.web.jsf.beans.model;

import java.util.List;

import javax.faces.model.ListDataModel;

import org.primefaces.model.SelectableDataModel;

import com.next.aap.web.dto.InterestDto;

public class InterestDtoModel extends ListDataModel<InterestDto> implements SelectableDataModel<InterestDto>{

	public InterestDtoModel() {  
    }  
  
    public InterestDtoModel(List<InterestDto> data) {  
        super(data);  
    }  
    
	@Override
	public InterestDto getRowData(String rowKey) {
		List<InterestDto> roles = (List<InterestDto>) getWrappedData();  
        
        for(InterestDto car : roles) {  
            if(car.getId().toString().equals(rowKey))  
                return car;  
        }  
          
        return null;  
	}

	@Override
	public Object getRowKey(InterestDto roleDto) {
		return roleDto.getId().toString();
	}

}

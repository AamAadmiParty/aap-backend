package com.next.aap.web.dto;

public class UserInterestDto extends InterestDto {

    private static final long serialVersionUID = 1L;

    private boolean selected;

    public UserInterestDto(InterestDto interestDto) {
        super.setDescription(interestDto.getDescription());
        super.setId(interestDto.getId());
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

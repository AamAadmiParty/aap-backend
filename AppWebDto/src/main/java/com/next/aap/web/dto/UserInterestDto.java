package com.next.aap.web.dto;

public class UserInterestDto extends InterestDto {

    private static final long serialVersionUID = 1L;

    private boolean selected;

    public UserInterestDto() {
    }

    public UserInterestDto(InterestDto interestDto, boolean selected) {
        super(interestDto.getId(), interestDto.getDescription());
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

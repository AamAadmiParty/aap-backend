package com.next.aap.web.dto;

import java.util.Date;
import java.util.List;

public class TemplateDto {

	private Long id;
	private int ver;
	private Date dateCreated;
	private Date dateModified;
	private Long creatorId;
	private Long modifierId;

    private String name;
    private String status;
    private boolean global;
    private List<TemplateUrlDto> templateUrlDtos;
    private StateDto stateDto;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    public List<TemplateUrlDto> getTemplateUrlDtos() {
        return templateUrlDtos;
    }

    public void setTemplateUrlDtos(List<TemplateUrlDto> templateUrlDtos) {
        this.templateUrlDtos = templateUrlDtos;
    }

    public StateDto getStateDto() {
        return stateDto;
    }

    public void setStateDto(StateDto stateDto) {
        this.stateDto = stateDto;
    }

}

package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.InterestGroup;

public interface InterestGroupDao {

	public abstract InterestGroup saveInterestGroup(InterestGroup interestGroup);

	public abstract InterestGroup getInterestGroupById(Long id);

	public abstract List<InterestGroup> getAllInterestGroups();
}
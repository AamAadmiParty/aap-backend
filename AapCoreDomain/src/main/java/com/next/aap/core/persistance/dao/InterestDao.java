package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Interest;

public interface InterestDao {

	public abstract Interest saveInterest(Interest interest);

	public abstract Interest getInterestById(Long id);
	
	public abstract List<Interest> getAllInterests();

}
package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.State;

public interface StateDao {

	/**
	 * Creates/updates a state in Database
	 * 
	 * @param state
	 * @return saved state
	 * @
	 */
	public abstract State saveState(State state);

	/**
	 * deletes a state in Database
	 * 
	 * @param state
	 * @return updated state
	 * @
	 */
	public abstract void deleteState(State state);

	/**
	 * return a State with given primary key/id
	 * 
	 * @param id
	 * @return State with PK as id(parameter)
	 * @
	 */
	public abstract State getStateById(Long id);
	
	public abstract State getStateByName(String name);

	public abstract List<State> getAllStates();

}
package com.next.aap.core.persistance.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Repository;

import com.next.aap.core.persistance.State;
import com.next.aap.core.persistance.dao.StateDao;

@Repository
public class StateDaoHibernateSpringImpl extends BaseDaoHibernateSpring<State> implements StateDao  {

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.StateDao#saveState(com.next.aap.server.persistance.State)
	 */
	@Override
	public State saveState(State state) 
	{
		checkIfStringMissing("Name", state.getName());
		checkIfStateExistsWithSameName(state);
		state.setNameUp(state.getName().toUpperCase());
		state = super.saveObject(state);
		return state;
	}
	

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.StateDao#deleteState(com.next.aap.server.persistance.State)
	 */
	@Override
	public void deleteState(State state)  {
		super.deleteObject(state);
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.StateDao#getStateById(java.lang.Long)
	 */
	@Override
	public State getStateById(Long id) 
	{
		State state = super.getObjectById(State.class, id);
		return state;
	}

	/* (non-Javadoc)
	 * @see com.next.aap.server.persistance.dao.impl.StateDao#getAllStates()
	 */
	@Override
	public List<State> getAllStates() 
	{
		List<State> list = executeQueryGetList("from State order by name asc");
		return list;
	}
	private void checkIfStateExistsWithSameName(State state) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("nameUp", state.getName());
		State existingState;
		if(state.getId() != null && state.getId() > 0){
			params.put("id", state.getId());
			existingState = executeQueryGetObject("from State where nameUp=:nameUp and id != :id", params);
		}else{
			existingState = executeQueryGetObject("from State where nameUp=:nameUp ", params);
		}
		
		if(existingState != null){
			throw new RuntimeException("State already exists with name = "+state.getName());
		}
	}
	
	@Override
	public State getStateByName(String name) {
		Map<String, Object> params = new TreeMap<String, Object>();
		params.put("nameUp", name.toUpperCase());
		State existingState = executeQueryGetObject("from State where nameUp = :nameUp ", params);
		return existingState;
	}

}
package com.next.aap.core.persistance.dao;

import java.util.List;

import com.next.aap.core.persistance.Election;

public interface ElectionDao {

    public abstract Election saveElection(Election Election);

    public abstract Election getElectionById(Long id);

    public abstract List<Election> getAllElections();

}
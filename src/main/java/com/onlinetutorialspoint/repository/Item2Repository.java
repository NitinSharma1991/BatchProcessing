package com.onlinetutorialspoint.repository;

import com.onlinetutorialspoint.model.Item;
import com.onlinetutorialspoint.model.Item2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Item2Repository extends JpaRepository<Item2, Integer> {
}

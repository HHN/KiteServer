package com.hhn.kite2server.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for CRUD operations on DataObject entities.
 */
@Repository
public interface DataRepository extends JpaRepository<DataObject, Long> {
}

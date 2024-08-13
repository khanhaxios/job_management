package com.fira.app.services.base;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface ICrudService<CreateRequest, UpdateRequest, ID> {
    ResponseEntity<?> store(CreateRequest request) throws Exception;

    ResponseEntity<?> update(UpdateRequest request) throws Exception;

    ResponseEntity<?> destroy(ID id) throws Exception;

    ResponseEntity<?> destroyAll(Set<ID> ids) throws Exception;

    ResponseEntity<?> getAll(Pageable pageable, String fieldSort, String sortDesc, String query) throws Exception;
}

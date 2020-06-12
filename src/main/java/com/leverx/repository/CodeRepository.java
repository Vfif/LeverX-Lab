package com.leverx.repository;

import com.leverx.model.Code;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CodeRepository extends CrudRepository<Code, Integer> {
    Optional<Code> findByUserId(int userId);
    Optional<Code> findByConfirmationCode(String code);
}

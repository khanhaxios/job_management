package com.fira.app.repository;

import com.fira.app.entities.IDCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IDCardRepository extends JpaRepository<IDCard,String> {
}

package com.kyc.dms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kyc.dms.domain.DOCStorage;

/**
 * @author Dhaval.Ajmera
 * Date : 31-07-2024
 */
@Repository
public interface DOCStorageRepository extends JpaRepository<DOCStorage, Long> {

    public
    DOCStorage findByReferenceId(String referenceId);
}


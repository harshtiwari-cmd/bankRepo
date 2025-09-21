package com.example.card.repository;

import com.example.card.constrants.entity.BankBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankBranchRepository extends JpaRepository<BankBranch, Long> {
    boolean existsByBankBranchName(String bankBranchName);
    boolean existsByBranchNumber(String branchNumber);
    boolean existsByContact(String contact);

    boolean existsByAddress(String address);


    boolean existsByCoordinatesLatitudeAndCoordinatesLongitude(Double lat, Double lon);
}

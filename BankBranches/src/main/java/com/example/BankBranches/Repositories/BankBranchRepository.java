package com.example.BankBranches.Repositories;

import com.example.BankBranches.Entities.BankBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankBranchRepository extends JpaRepository<BankBranch,Long>
{

}

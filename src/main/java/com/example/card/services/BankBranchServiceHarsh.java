package com.example.card.services;

import com.example.card.constrants.dto.BankBranchHarshDTO;
import com.example.card.constrants.dto.CreateBankHarshBranchDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface BankBranchServiceHarsh {
    List<BankBranchHarshDTO> getAllBranches();
    boolean isBranchOpen(Long branchId, LocalDateTime dateTime);
    BankBranchHarshDTO createBankBranch(CreateBankHarshBranchDTO createDTO);
}

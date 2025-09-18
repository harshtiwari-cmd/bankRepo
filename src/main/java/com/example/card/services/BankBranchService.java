package com.example.card.services;

import com.example.card.constrants.dto.BankBranchDTO;
import com.example.card.constrants.dto.CreateBankHarshBranchDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface BankBranchService {
    List<BankBranchDTO> getAllBranches();
    boolean isBranchOpen(Long branchId, LocalDateTime dateTime);
    BankBranchDTO createBankBranch(CreateBankHarshBranchDTO createDTO);
}

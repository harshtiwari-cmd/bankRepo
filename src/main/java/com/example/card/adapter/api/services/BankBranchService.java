package com.example.card.adapter.api.services;

import com.example.card.domain.dto.BankBranchDTO;
import com.example.card.domain.dto.CreateBankHarshBranchDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface BankBranchService {
    List<BankBranchDTO> getAllBranches();

    boolean isBranchOpen(Long branchId, LocalDateTime dateTime);

    BankBranchDTO createBankBranch(CreateBankHarshBranchDTO createDTO);
}

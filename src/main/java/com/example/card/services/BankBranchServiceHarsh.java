package com.example.card.services;

import com.example.card.constrants.dto.BankBranchHarshDTO;
import com.example.card.constrants.dto.CreateBankHarshBranchDTO;

import java.util.List;

public interface BankBranchServiceHarsh {
    List<BankBranchHarshDTO> getAllBranches();
    BankBranchHarshDTO createBankBranch(CreateBankHarshBranchDTO createDTO);
}

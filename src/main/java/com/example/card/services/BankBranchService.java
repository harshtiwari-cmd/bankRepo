package com.example.card.services;

import com.example.card.constrants.dto.BankBranchDTO;
import com.example.card.constrants.dto.CreateBankBranchDTO;

import java.util.List;

public interface BankBranchService {
    List<BankBranchDTO> getAllBranches();
    BankBranchDTO createBankBranch(CreateBankBranchDTO createDTO);
}

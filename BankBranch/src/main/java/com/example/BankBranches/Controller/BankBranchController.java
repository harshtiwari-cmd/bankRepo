package com.example.BankBranches.Controller;

import com.example.BankBranches.DTO.BankBranchDTO;
import com.example.BankBranches.Entities.BankBranch;
import com.example.BankBranches.Services.BankBranchMethodsImplementation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/branches")
public class BankBranchController
{
    @Autowired
    private BankBranchMethodsImplementation bankBranchMethodsImplementation;

    @PostMapping("/addbranch")
    public ResponseEntity<BankBranch> createBankBranch(@RequestBody BankBranchDTO branch)
    {
        BankBranch savedBankBranch = bankBranchMethodsImplementation.saveBankBranch(branch);
        return ResponseEntity.ok(savedBankBranch);
    }

}

package com.example.BankBranches.Services;

import com.example.BankBranches.DTO.BankBranchDTO;
import com.example.BankBranches.DTO.Coordinates;
import com.example.BankBranches.DTO.Holiday;
import com.example.BankBranches.Entities.BankBranch;
import com.example.BankBranches.Repositories.BankBranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;
@Service
public class BankBranchMethodsImplementation implements BankBranchMethods
{
    @Autowired
    private BankBranchRepository bankBranchRepository;
    @Override
    @Transactional
    public BankBranch saveBankBranch(BankBranchDTO branch)
    {

        BankBranch bankBranch=new BankBranch();
        bankBranch.setBankName(branch.getBankName());
        bankBranch.setBankBranchName(branch.getBankBranchName());
        bankBranch.setBranchNumber(branch.getBranchNumber());
        bankBranch.setBankBranchType(branch.getBankBranchType());
        bankBranch.setDescription(branch.getDescription());
        bankBranch.setCountryName(branch.getCountryName());
        bankBranch.setOpenTime(branch.getOpenTime());
        bankBranch.setCloseTime(branch.getCloseTime());
//        bankBranch.setCoordinates(branch.getCoordinates());
        if(branch.getCoordinates()!=null)
        {
            Coordinates coo = new Coordinates();
            coo.setLatitude(branch.getCoordinates().getLatitude());
            coo.setLongitude(branch.getCoordinates().getLongitude());
            bankBranch.setCoordinates(coo);
        }
        if(branch.getHolidayCalendar() != null)
        {
            bankBranch.setHolidayCalendar(
                    branch.getHolidayCalendar().stream()
                            .map(h-> new Holiday(h.getDate(),h.getName(),h.getType()))
                            .collect(Collectors.toList())
            );
        }

        if(branch.getWeeklyHolidays()!=null)
        {
            bankBranch.setWeeklyHolidayList(branch.getWeeklyHolidays());
        }
        return bankBranchRepository.save(bankBranch);
    }
}

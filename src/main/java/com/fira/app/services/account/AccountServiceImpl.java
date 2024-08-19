package com.fira.app.services.account;

import com.fira.app.entities.Account;
import com.fira.app.entities.IDCard;
import com.fira.app.entities.Role;
import com.fira.app.expceptions.AppException;
import com.fira.app.repository.AccountRepository;
import com.fira.app.repository.IDCardRepository;
import com.fira.app.repository.RoleRepository;
import com.fira.app.requests.account.CreateAccountRequest;
import com.fira.app.requests.account.UpdateAccountRequest;
import com.fira.app.utils.ApiResponse;
import com.fira.app.utils.BeanHelper;
import com.fira.app.utils.ResponseHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;

    private final IDCardRepository idCardRepository;
    private final RoleRepository roleRepository;

    @Override
    public ResponseEntity<?> store(CreateAccountRequest createAccountRequest) throws Exception {
        Account account = accountRepository.findByUsername(createAccountRequest.getUsername()).orElse(null);
        Role role = roleRepository.findByName(createAccountRequest.getRole()).orElse(null);
        if (account != null) {
            throw new AppException("Account Exited");
        }
        account = new Account();
        BeanUtils.copyProperties(createAccountRequest, account, BeanHelper.getNullPropertyNames(createAccountRequest));
        account.setPassword(new BCryptPasswordEncoder().encode(createAccountRequest.getPassword()));
        account.setRole(role);
        return ResponseHelper.success(accountRepository.save(account));
    }

    @Override
    public ResponseEntity<?> update(UpdateAccountRequest updateAccountRequest) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<?> destroy(String s) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<?> destroyAll(Set<String> strings) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<?> getAll(Pageable pageable, String fieldSort, String sortDesc, String query) throws Exception {
        return null;
    }

    @Override
    public ResponseEntity<?> completeAccountInfo(String accountId, UpdateAccountRequest updateAccountRequest) throws Exception {
        Account account = accountRepository.findById(accountId).orElseThrow(() -> new AppException("Account Not Found"));
        if (updateAccountRequest.getExpiredAt().isBefore(updateAccountRequest.getIssuedAt())) {
            throw new AppException("Issued date should before expire date");
        }
        IDCard idCard = new IDCard();
        BeanUtils.copyProperties(updateAccountRequest, idCard, BeanHelper.getNullPropertyNames(updateAccountRequest));
        idCard.setID(updateAccountRequest.getID());
        account.setPhone(updateAccountRequest.getPhone());
        account.setVerifiedAt(LocalDate.now());
        IDCard savedId = idCardRepository.save(idCard);
        account.setIdCard(savedId);
        account.setVerifiedAt(LocalDate.now());
        account.setVerify(true);
        return ResponseHelper.success(accountRepository.save(account));
    }
}

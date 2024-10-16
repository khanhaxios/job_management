package com.fira.app.services.account;

import com.fira.app.entities.Account;
import com.fira.app.requests.account.CreateAccountRequest;
import com.fira.app.requests.account.UpdateAccountRequest;
import com.fira.app.services.base.ICrudService;
import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface AccountService extends ICrudService<CreateAccountRequest, UpdateAccountRequest, String> {

    ResponseEntity<?> addStaff(String accountId, Set<String> staffId);

    ResponseEntity<?> removeStaff(String accountId, Set<String> staffId);

    ResponseEntity<?> completeAccountInfo(String accountId, UpdateAccountRequest updateAccountRequest) throws Exception;
}

package com.fira.app.services.account;

import com.fira.app.requests.account.CreateAccountRequest;
import com.fira.app.requests.account.UpdateAccountRequest;
import com.fira.app.services.base.ICrudService;
import org.springframework.http.ResponseEntity;

public interface AccountService extends ICrudService<CreateAccountRequest, UpdateAccountRequest, String> {

    ResponseEntity<?> completeAccountInfo(String accountId, UpdateAccountRequest updateAccountRequest) throws Exception;
}

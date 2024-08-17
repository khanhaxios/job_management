package com.fira.app.requests.job;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GiveJobForUserRequest {
    private List<String> userIds = new ArrayList<>();
}

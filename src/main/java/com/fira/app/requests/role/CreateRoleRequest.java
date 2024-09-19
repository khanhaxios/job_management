package com.fira.app.requests.role;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CreateRoleRequest {
    private String name;

    private List<Long> permIds = new ArrayList<>();
}

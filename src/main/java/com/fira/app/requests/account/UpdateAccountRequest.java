package com.fira.app.requests.account;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateAccountRequest {

    @NotBlank(message = "ID card cannot blank")
    private String ID;
    @Pattern(regexp = "(\\+84|0)\\d{9,10}", message = "Invalid phone number")
    private String phone;

    @Past(message = "Birthday has to a pass day")
    private String birthday;

    @NotNull
    @Past(message = "Issue Date has to a pass day")
    private LocalDate issuedAt;
    @NotNull
    private LocalDate expiredAt;

    @NotNull
    private String avatar;

    private String address;

    @NotNull
    private String pin;
}

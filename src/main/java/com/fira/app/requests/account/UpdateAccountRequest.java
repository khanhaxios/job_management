package com.fira.app.requests.account;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class UpdateAccountRequest {

    @NotBlank(message = "ID card cannot blank")
    private String ID;
    @Pattern(regexp = "(\\+84|0)\\d{9,10}", message = "Invalid phone number")
    private String phone;

    @Past(message = "Birthday has to a pass day")
    private LocalDate birthday;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    @Past(message = "Issue Date has to a pass day")
    private LocalDate issuedAt;
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    private LocalDate expiredAt;

    @NotNull
    private String avatar;

    private String address;

    @NotNull
    @Size(min = 6,max = 6)
    private String pin;
}

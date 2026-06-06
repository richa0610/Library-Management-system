package com.example.richa.libraryManagementSystem.dtos;

import com.example.richa.libraryManagementSystem.model.TxnStatus;
import com.example.richa.libraryManagementSystem.model.TxnType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TxnRequest {

    @Positive
    private Integer bookId;

    @Positive
    private Integer studentId;

    @NotNull
    private TxnType txnType;
}

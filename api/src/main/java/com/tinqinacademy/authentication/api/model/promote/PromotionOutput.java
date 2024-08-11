package com.tinqinacademy.authentication.api.model.promote;

import com.tinqinacademy.authentication.api.base.OperationOutput;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class PromotionOutput implements OperationOutput {
    private String message;
}

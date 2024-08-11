package com.tinqinacademy.authentication.api.model.promote;

import com.tinqinacademy.authentication.api.base.OperationInput;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor()
@AllArgsConstructor()
@Builder
public class PromotionInput implements OperationInput {
    private UUID id;
}

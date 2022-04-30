package com.finalyearproject.fyp.dto.Response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
public class WishResponseDTO implements Serializable {
    private Long id;
    private LocalDate addedOn;
    private Long userId;
    private Long productId;
}

package com.msglearning.javabackend.to;

import com.msglearning.javabackend.entity.Game;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class PurchaseTO implements Serializable {

    private Long id;
    private String gameName;
    private String userName;
    private LocalDate purchaseDate;
}

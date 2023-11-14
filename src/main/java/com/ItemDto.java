package com;


import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
    private String itemName;
    private Integer qty;
    private Double itemPrice;
    private Double price;

}

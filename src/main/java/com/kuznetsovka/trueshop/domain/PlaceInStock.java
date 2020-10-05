package com.kuznetsovka.trueshop.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "place_in_stock")
public class PlaceInStock {
    private static final String SEQ_NAME = "place_seq";
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQ_NAME)
    @SequenceGenerator(name = SEQ_NAME, sequenceName = SEQ_NAME, allocationSize = 1)
    private Long id;
    private int row;
    private int span;
    private int shelf;
    private int place;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "place_in_stock_products",
            joinColumns = @JoinColumn(name = "place_in_stock_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

}

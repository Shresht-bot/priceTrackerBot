package com.personal.PriceTracker;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "products")
public class ProductEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_generator")
    @SequenceGenerator(name = "products_generator", sequenceName = "products_seq", allocationSize = 1)
    private int id;

    @Column(name = "userid")
    private long userid;

    @Column(name = "url")
    private String url;

    @Column(name = "amount")
    private int amount;

    @Column(name = "flag")
    private String flag;
}

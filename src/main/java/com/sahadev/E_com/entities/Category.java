    package com.sahadev.E_com.entities;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import jakarta.persistence.*;
    import lombok.Data;
    import lombok.Getter;
    import lombok.Setter;

    import java.util.List;

    @Entity
    @Getter @Setter
    @Table (uniqueConstraints = @UniqueConstraint(columnNames = {"category_name"}))
    public class Category {
        @Id
        @GeneratedValue (strategy = GenerationType.IDENTITY)
        private Long id;
        private String categoryName;

        @OneToMany (mappedBy = "category",cascade = CascadeType.ALL,orphanRemoval = true)
        private List<Product> products;

    }

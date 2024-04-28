package antigravity.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Embedded
    private ProductPrice productPrice;

    @Builder
    public Product(Long id, String name, ProductPrice productPrice) {
        this.id = id;
        this.name = name;
        this.productPrice = productPrice;
    }
}

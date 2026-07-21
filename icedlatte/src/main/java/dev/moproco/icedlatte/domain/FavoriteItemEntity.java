package dev.moproco.icedlatte.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "favorite_item_entity")
public class FavoriteItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "version")
    private Integer version;

    @NotNull
    @Column(name = "product_id")
    private Long productId;

    @ManyToOne
    @JoinColumn(name = "favorite_list_id")
    private FavoriteListEntity favoriteList;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Long getProductId() {
        return this.productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public FavoriteListEntity getFavoriteList() {
        return this.favoriteList;
    }

    public void setFavoriteList(FavoriteListEntity favoriteList) {
        this.favoriteList = favoriteList;
    }

}

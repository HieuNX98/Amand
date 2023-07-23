package com.amand.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity extends BaseEntity {

    @Column
    private String name;

    @Column(name = "oldprice")
    private int oldPrice;

    @Column(name = "saleprice")
    private int salePrice;

    @Column
    private int amount;

    @Column
    private String season;

    @ManyToMany
    @JoinTable(name = "product_size",
               joinColumns = @JoinColumn(name = "product_id"),
                inverseJoinColumns = @JoinColumn(name = "size_id"))
    private List<SizeEntity> sizes ;

    @ManyToMany
    @JoinTable(name = "product_color",
                joinColumns = @JoinColumn(name = "product_id"),
                inverseJoinColumns = @JoinColumn(name = "color_id"))
    private List<ColorEntity> colors;

    @ManyToMany
    @JoinTable(name = "product_bag",
                joinColumns = @JoinColumn(name = "product_id"),
                inverseJoinColumns = @JoinColumn(name = "bag_id"))
    private List<BagEntity> bags;

    @ManyToMany
    @JoinTable(name = "product_oder",
                joinColumns = @JoinColumn(name = "product_id"),
                inverseJoinColumns = @JoinColumn(name = "oder_id"))
    private List<OderEntity> oders;

    @ManyToOne
    @JoinColumn(name = "category_Id")
    private CategoryEntity category;

    @OneToMany(mappedBy = "products")
    private List<ImageEntity> images;

    public List<OderEntity> getOders() {
        return oders;
    }

    public void setOders(List<OderEntity> oders) {
        this.oders = oders;
    }

    public List<BagEntity> getBags() {
        return bags;
    }

    public void setBags(List<BagEntity> bags) {
        this.bags = bags;
    }

    public List<ImageEntity> getImages() {
        return images;
    }

    public void setImages(List<ImageEntity> images) {
        this.images = images;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public List<ColorEntity> getColors() {
        return colors;
    }

    public void setColors(List<ColorEntity> colors) {
        this.colors = colors;
    }

    public List<SizeEntity> getSizes() {
        return sizes;
    }

    public void setSizes(List<SizeEntity> sizes) {
        this.sizes = sizes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(int oldPrice) {
        this.oldPrice = oldPrice;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(int salePrice) {
        this.salePrice = salePrice;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSeason() {
        return season;
    }

    public void setSeason(String season) {
        this.season = season;
    }


}

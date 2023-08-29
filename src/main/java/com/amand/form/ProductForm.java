package com.amand.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductForm extends BaseForm<ProductForm> {

    private Integer categoryId;

    private String name;

    private Double oldPrice;

    private Double salePrice;

    private Integer amount;

    private String season;

    private List<Integer> colorIds;

    private List<Integer> sizeIds;

    private MultipartFile image1;

    private MultipartFile image2;

    private MultipartFile image3;

    private MultipartFile image4;

}

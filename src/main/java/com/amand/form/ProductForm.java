package com.amand.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProductForm extends BaseForm {

    private Integer categoryId;

    private String name;

    private Double oldPrice;

    private Double salePrice;

    private Integer amount;

    private String season;

    private List<Integer> colorIds;

    private List<Integer> sizeIds;

    private String colorName;

    private String sizeName;

    private List<String> colorNames;

    private List<String> sizeNames;

    private MultipartFile image1;

    private MultipartFile image2;

    private MultipartFile image3;

    private MultipartFile image4;

    private String categoryCode;

}

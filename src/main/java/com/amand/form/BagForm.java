package com.amand.form;

import lombok.Data;

@Data
public class BagForm extends BaseForm {

    private Integer productId;

    private Integer amount;

    private String colorName;

    private String sizeName;
}

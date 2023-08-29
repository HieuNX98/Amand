package com.amand.form;

import lombok.Data;

@Data
public class RoleForm extends BaseForm<RoleForm> {
    private String name;

    private String code;
}

package com.raddle.crud.vo;

import com.raddle.crud.model.toolgen.CrudDefinition;

public class CrudDefinitionVo extends CrudDefinition {

    private String crudDsName;

    public String getCrudDsName() {
        return crudDsName;
    }

    public void setCrudDsName(String crudDsName) {
        this.crudDsName = crudDsName;
    }
}

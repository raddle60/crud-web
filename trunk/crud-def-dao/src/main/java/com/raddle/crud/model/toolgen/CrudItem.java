package com.raddle.crud.model.toolgen;

import java.util.Date;

public class CrudItem {
    private Long id;

    private Long crudDefId;

    private String fkType;

    private String title;

    private String varName;

    private String itemType;

    private String inputType;

    private String actionType;

    private String href;

    private String webChkRule;

    private String serverChkRule;

    private String optionType;

    private String optionValue;

    private Long crudDsId;

    private Short deleted;

    private Date createdAt;

    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCrudDefId() {
        return crudDefId;
    }

    public void setCrudDefId(Long crudDefId) {
        this.crudDefId = crudDefId;
    }

    public String getFkType() {
        return fkType;
    }

    public void setFkType(String fkType) {
        this.fkType = fkType == null ? null : fkType.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName == null ? null : varName.trim();
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType == null ? null : itemType.trim();
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType == null ? null : inputType.trim();
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType == null ? null : actionType.trim();
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href == null ? null : href.trim();
    }

    public String getWebChkRule() {
        return webChkRule;
    }

    public void setWebChkRule(String webChkRule) {
        this.webChkRule = webChkRule == null ? null : webChkRule.trim();
    }

    public String getServerChkRule() {
        return serverChkRule;
    }

    public void setServerChkRule(String serverChkRule) {
        this.serverChkRule = serverChkRule == null ? null : serverChkRule.trim();
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType == null ? null : optionType.trim();
    }

    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue == null ? null : optionValue.trim();
    }

    public Long getCrudDsId() {
        return crudDsId;
    }

    public void setCrudDsId(Long crudDsId) {
        this.crudDsId = crudDsId;
    }

    public Short getDeleted() {
        return deleted;
    }

    public void setDeleted(Short deleted) {
        this.deleted = deleted;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
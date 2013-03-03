package com.raddle.crud.model.toolgen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CrudItemExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CrudItemExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andCrudDefIdIsNull() {
            addCriterion("CRUD_DEF_ID is null");
            return (Criteria) this;
        }

        public Criteria andCrudDefIdIsNotNull() {
            addCriterion("CRUD_DEF_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCrudDefIdEqualTo(Long value) {
            addCriterion("CRUD_DEF_ID =", value, "crudDefId");
            return (Criteria) this;
        }

        public Criteria andCrudDefIdNotEqualTo(Long value) {
            addCriterion("CRUD_DEF_ID <>", value, "crudDefId");
            return (Criteria) this;
        }

        public Criteria andCrudDefIdGreaterThan(Long value) {
            addCriterion("CRUD_DEF_ID >", value, "crudDefId");
            return (Criteria) this;
        }

        public Criteria andCrudDefIdGreaterThanOrEqualTo(Long value) {
            addCriterion("CRUD_DEF_ID >=", value, "crudDefId");
            return (Criteria) this;
        }

        public Criteria andCrudDefIdLessThan(Long value) {
            addCriterion("CRUD_DEF_ID <", value, "crudDefId");
            return (Criteria) this;
        }

        public Criteria andCrudDefIdLessThanOrEqualTo(Long value) {
            addCriterion("CRUD_DEF_ID <=", value, "crudDefId");
            return (Criteria) this;
        }

        public Criteria andCrudDefIdIn(List<Long> values) {
            addCriterion("CRUD_DEF_ID in", values, "crudDefId");
            return (Criteria) this;
        }

        public Criteria andCrudDefIdNotIn(List<Long> values) {
            addCriterion("CRUD_DEF_ID not in", values, "crudDefId");
            return (Criteria) this;
        }

        public Criteria andCrudDefIdBetween(Long value1, Long value2) {
            addCriterion("CRUD_DEF_ID between", value1, value2, "crudDefId");
            return (Criteria) this;
        }

        public Criteria andCrudDefIdNotBetween(Long value1, Long value2) {
            addCriterion("CRUD_DEF_ID not between", value1, value2, "crudDefId");
            return (Criteria) this;
        }

        public Criteria andFkTypeIsNull() {
            addCriterion("FK_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andFkTypeIsNotNull() {
            addCriterion("FK_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andFkTypeEqualTo(String value) {
            addCriterion("FK_TYPE =", value, "fkType");
            return (Criteria) this;
        }

        public Criteria andFkTypeNotEqualTo(String value) {
            addCriterion("FK_TYPE <>", value, "fkType");
            return (Criteria) this;
        }

        public Criteria andFkTypeGreaterThan(String value) {
            addCriterion("FK_TYPE >", value, "fkType");
            return (Criteria) this;
        }

        public Criteria andFkTypeGreaterThanOrEqualTo(String value) {
            addCriterion("FK_TYPE >=", value, "fkType");
            return (Criteria) this;
        }

        public Criteria andFkTypeLessThan(String value) {
            addCriterion("FK_TYPE <", value, "fkType");
            return (Criteria) this;
        }

        public Criteria andFkTypeLessThanOrEqualTo(String value) {
            addCriterion("FK_TYPE <=", value, "fkType");
            return (Criteria) this;
        }

        public Criteria andFkTypeLike(String value) {
            addCriterion("FK_TYPE like", value, "fkType");
            return (Criteria) this;
        }

        public Criteria andFkTypeNotLike(String value) {
            addCriterion("FK_TYPE not like", value, "fkType");
            return (Criteria) this;
        }

        public Criteria andFkTypeIn(List<String> values) {
            addCriterion("FK_TYPE in", values, "fkType");
            return (Criteria) this;
        }

        public Criteria andFkTypeNotIn(List<String> values) {
            addCriterion("FK_TYPE not in", values, "fkType");
            return (Criteria) this;
        }

        public Criteria andFkTypeBetween(String value1, String value2) {
            addCriterion("FK_TYPE between", value1, value2, "fkType");
            return (Criteria) this;
        }

        public Criteria andFkTypeNotBetween(String value1, String value2) {
            addCriterion("FK_TYPE not between", value1, value2, "fkType");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("TITLE is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("TITLE is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("TITLE =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("TITLE <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("TITLE >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("TITLE >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("TITLE <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("TITLE <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("TITLE like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("TITLE not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("TITLE in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("TITLE not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("TITLE between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("TITLE not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andVarNameIsNull() {
            addCriterion("VAR_NAME is null");
            return (Criteria) this;
        }

        public Criteria andVarNameIsNotNull() {
            addCriterion("VAR_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andVarNameEqualTo(String value) {
            addCriterion("VAR_NAME =", value, "varName");
            return (Criteria) this;
        }

        public Criteria andVarNameNotEqualTo(String value) {
            addCriterion("VAR_NAME <>", value, "varName");
            return (Criteria) this;
        }

        public Criteria andVarNameGreaterThan(String value) {
            addCriterion("VAR_NAME >", value, "varName");
            return (Criteria) this;
        }

        public Criteria andVarNameGreaterThanOrEqualTo(String value) {
            addCriterion("VAR_NAME >=", value, "varName");
            return (Criteria) this;
        }

        public Criteria andVarNameLessThan(String value) {
            addCriterion("VAR_NAME <", value, "varName");
            return (Criteria) this;
        }

        public Criteria andVarNameLessThanOrEqualTo(String value) {
            addCriterion("VAR_NAME <=", value, "varName");
            return (Criteria) this;
        }

        public Criteria andVarNameLike(String value) {
            addCriterion("VAR_NAME like", value, "varName");
            return (Criteria) this;
        }

        public Criteria andVarNameNotLike(String value) {
            addCriterion("VAR_NAME not like", value, "varName");
            return (Criteria) this;
        }

        public Criteria andVarNameIn(List<String> values) {
            addCriterion("VAR_NAME in", values, "varName");
            return (Criteria) this;
        }

        public Criteria andVarNameNotIn(List<String> values) {
            addCriterion("VAR_NAME not in", values, "varName");
            return (Criteria) this;
        }

        public Criteria andVarNameBetween(String value1, String value2) {
            addCriterion("VAR_NAME between", value1, value2, "varName");
            return (Criteria) this;
        }

        public Criteria andVarNameNotBetween(String value1, String value2) {
            addCriterion("VAR_NAME not between", value1, value2, "varName");
            return (Criteria) this;
        }

        public Criteria andItemTypeIsNull() {
            addCriterion("ITEM_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andItemTypeIsNotNull() {
            addCriterion("ITEM_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andItemTypeEqualTo(String value) {
            addCriterion("ITEM_TYPE =", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeNotEqualTo(String value) {
            addCriterion("ITEM_TYPE <>", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeGreaterThan(String value) {
            addCriterion("ITEM_TYPE >", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ITEM_TYPE >=", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeLessThan(String value) {
            addCriterion("ITEM_TYPE <", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeLessThanOrEqualTo(String value) {
            addCriterion("ITEM_TYPE <=", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeLike(String value) {
            addCriterion("ITEM_TYPE like", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeNotLike(String value) {
            addCriterion("ITEM_TYPE not like", value, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeIn(List<String> values) {
            addCriterion("ITEM_TYPE in", values, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeNotIn(List<String> values) {
            addCriterion("ITEM_TYPE not in", values, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeBetween(String value1, String value2) {
            addCriterion("ITEM_TYPE between", value1, value2, "itemType");
            return (Criteria) this;
        }

        public Criteria andItemTypeNotBetween(String value1, String value2) {
            addCriterion("ITEM_TYPE not between", value1, value2, "itemType");
            return (Criteria) this;
        }

        public Criteria andInputTypeIsNull() {
            addCriterion("INPUT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andInputTypeIsNotNull() {
            addCriterion("INPUT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andInputTypeEqualTo(String value) {
            addCriterion("INPUT_TYPE =", value, "inputType");
            return (Criteria) this;
        }

        public Criteria andInputTypeNotEqualTo(String value) {
            addCriterion("INPUT_TYPE <>", value, "inputType");
            return (Criteria) this;
        }

        public Criteria andInputTypeGreaterThan(String value) {
            addCriterion("INPUT_TYPE >", value, "inputType");
            return (Criteria) this;
        }

        public Criteria andInputTypeGreaterThanOrEqualTo(String value) {
            addCriterion("INPUT_TYPE >=", value, "inputType");
            return (Criteria) this;
        }

        public Criteria andInputTypeLessThan(String value) {
            addCriterion("INPUT_TYPE <", value, "inputType");
            return (Criteria) this;
        }

        public Criteria andInputTypeLessThanOrEqualTo(String value) {
            addCriterion("INPUT_TYPE <=", value, "inputType");
            return (Criteria) this;
        }

        public Criteria andInputTypeLike(String value) {
            addCriterion("INPUT_TYPE like", value, "inputType");
            return (Criteria) this;
        }

        public Criteria andInputTypeNotLike(String value) {
            addCriterion("INPUT_TYPE not like", value, "inputType");
            return (Criteria) this;
        }

        public Criteria andInputTypeIn(List<String> values) {
            addCriterion("INPUT_TYPE in", values, "inputType");
            return (Criteria) this;
        }

        public Criteria andInputTypeNotIn(List<String> values) {
            addCriterion("INPUT_TYPE not in", values, "inputType");
            return (Criteria) this;
        }

        public Criteria andInputTypeBetween(String value1, String value2) {
            addCriterion("INPUT_TYPE between", value1, value2, "inputType");
            return (Criteria) this;
        }

        public Criteria andInputTypeNotBetween(String value1, String value2) {
            addCriterion("INPUT_TYPE not between", value1, value2, "inputType");
            return (Criteria) this;
        }

        public Criteria andActionTypeIsNull() {
            addCriterion("ACTION_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andActionTypeIsNotNull() {
            addCriterion("ACTION_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andActionTypeEqualTo(String value) {
            addCriterion("ACTION_TYPE =", value, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeNotEqualTo(String value) {
            addCriterion("ACTION_TYPE <>", value, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeGreaterThan(String value) {
            addCriterion("ACTION_TYPE >", value, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeGreaterThanOrEqualTo(String value) {
            addCriterion("ACTION_TYPE >=", value, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeLessThan(String value) {
            addCriterion("ACTION_TYPE <", value, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeLessThanOrEqualTo(String value) {
            addCriterion("ACTION_TYPE <=", value, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeLike(String value) {
            addCriterion("ACTION_TYPE like", value, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeNotLike(String value) {
            addCriterion("ACTION_TYPE not like", value, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeIn(List<String> values) {
            addCriterion("ACTION_TYPE in", values, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeNotIn(List<String> values) {
            addCriterion("ACTION_TYPE not in", values, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeBetween(String value1, String value2) {
            addCriterion("ACTION_TYPE between", value1, value2, "actionType");
            return (Criteria) this;
        }

        public Criteria andActionTypeNotBetween(String value1, String value2) {
            addCriterion("ACTION_TYPE not between", value1, value2, "actionType");
            return (Criteria) this;
        }

        public Criteria andHrefIsNull() {
            addCriterion("HREF is null");
            return (Criteria) this;
        }

        public Criteria andHrefIsNotNull() {
            addCriterion("HREF is not null");
            return (Criteria) this;
        }

        public Criteria andHrefEqualTo(String value) {
            addCriterion("HREF =", value, "href");
            return (Criteria) this;
        }

        public Criteria andHrefNotEqualTo(String value) {
            addCriterion("HREF <>", value, "href");
            return (Criteria) this;
        }

        public Criteria andHrefGreaterThan(String value) {
            addCriterion("HREF >", value, "href");
            return (Criteria) this;
        }

        public Criteria andHrefGreaterThanOrEqualTo(String value) {
            addCriterion("HREF >=", value, "href");
            return (Criteria) this;
        }

        public Criteria andHrefLessThan(String value) {
            addCriterion("HREF <", value, "href");
            return (Criteria) this;
        }

        public Criteria andHrefLessThanOrEqualTo(String value) {
            addCriterion("HREF <=", value, "href");
            return (Criteria) this;
        }

        public Criteria andHrefLike(String value) {
            addCriterion("HREF like", value, "href");
            return (Criteria) this;
        }

        public Criteria andHrefNotLike(String value) {
            addCriterion("HREF not like", value, "href");
            return (Criteria) this;
        }

        public Criteria andHrefIn(List<String> values) {
            addCriterion("HREF in", values, "href");
            return (Criteria) this;
        }

        public Criteria andHrefNotIn(List<String> values) {
            addCriterion("HREF not in", values, "href");
            return (Criteria) this;
        }

        public Criteria andHrefBetween(String value1, String value2) {
            addCriterion("HREF between", value1, value2, "href");
            return (Criteria) this;
        }

        public Criteria andHrefNotBetween(String value1, String value2) {
            addCriterion("HREF not between", value1, value2, "href");
            return (Criteria) this;
        }

        public Criteria andWebChkRuleIsNull() {
            addCriterion("WEB_CHK_RULE is null");
            return (Criteria) this;
        }

        public Criteria andWebChkRuleIsNotNull() {
            addCriterion("WEB_CHK_RULE is not null");
            return (Criteria) this;
        }

        public Criteria andWebChkRuleEqualTo(String value) {
            addCriterion("WEB_CHK_RULE =", value, "webChkRule");
            return (Criteria) this;
        }

        public Criteria andWebChkRuleNotEqualTo(String value) {
            addCriterion("WEB_CHK_RULE <>", value, "webChkRule");
            return (Criteria) this;
        }

        public Criteria andWebChkRuleGreaterThan(String value) {
            addCriterion("WEB_CHK_RULE >", value, "webChkRule");
            return (Criteria) this;
        }

        public Criteria andWebChkRuleGreaterThanOrEqualTo(String value) {
            addCriterion("WEB_CHK_RULE >=", value, "webChkRule");
            return (Criteria) this;
        }

        public Criteria andWebChkRuleLessThan(String value) {
            addCriterion("WEB_CHK_RULE <", value, "webChkRule");
            return (Criteria) this;
        }

        public Criteria andWebChkRuleLessThanOrEqualTo(String value) {
            addCriterion("WEB_CHK_RULE <=", value, "webChkRule");
            return (Criteria) this;
        }

        public Criteria andWebChkRuleLike(String value) {
            addCriterion("WEB_CHK_RULE like", value, "webChkRule");
            return (Criteria) this;
        }

        public Criteria andWebChkRuleNotLike(String value) {
            addCriterion("WEB_CHK_RULE not like", value, "webChkRule");
            return (Criteria) this;
        }

        public Criteria andWebChkRuleIn(List<String> values) {
            addCriterion("WEB_CHK_RULE in", values, "webChkRule");
            return (Criteria) this;
        }

        public Criteria andWebChkRuleNotIn(List<String> values) {
            addCriterion("WEB_CHK_RULE not in", values, "webChkRule");
            return (Criteria) this;
        }

        public Criteria andWebChkRuleBetween(String value1, String value2) {
            addCriterion("WEB_CHK_RULE between", value1, value2, "webChkRule");
            return (Criteria) this;
        }

        public Criteria andWebChkRuleNotBetween(String value1, String value2) {
            addCriterion("WEB_CHK_RULE not between", value1, value2, "webChkRule");
            return (Criteria) this;
        }

        public Criteria andServerChkRuleIsNull() {
            addCriterion("SERVER_CHK_RULE is null");
            return (Criteria) this;
        }

        public Criteria andServerChkRuleIsNotNull() {
            addCriterion("SERVER_CHK_RULE is not null");
            return (Criteria) this;
        }

        public Criteria andServerChkRuleEqualTo(String value) {
            addCriterion("SERVER_CHK_RULE =", value, "serverChkRule");
            return (Criteria) this;
        }

        public Criteria andServerChkRuleNotEqualTo(String value) {
            addCriterion("SERVER_CHK_RULE <>", value, "serverChkRule");
            return (Criteria) this;
        }

        public Criteria andServerChkRuleGreaterThan(String value) {
            addCriterion("SERVER_CHK_RULE >", value, "serverChkRule");
            return (Criteria) this;
        }

        public Criteria andServerChkRuleGreaterThanOrEqualTo(String value) {
            addCriterion("SERVER_CHK_RULE >=", value, "serverChkRule");
            return (Criteria) this;
        }

        public Criteria andServerChkRuleLessThan(String value) {
            addCriterion("SERVER_CHK_RULE <", value, "serverChkRule");
            return (Criteria) this;
        }

        public Criteria andServerChkRuleLessThanOrEqualTo(String value) {
            addCriterion("SERVER_CHK_RULE <=", value, "serverChkRule");
            return (Criteria) this;
        }

        public Criteria andServerChkRuleLike(String value) {
            addCriterion("SERVER_CHK_RULE like", value, "serverChkRule");
            return (Criteria) this;
        }

        public Criteria andServerChkRuleNotLike(String value) {
            addCriterion("SERVER_CHK_RULE not like", value, "serverChkRule");
            return (Criteria) this;
        }

        public Criteria andServerChkRuleIn(List<String> values) {
            addCriterion("SERVER_CHK_RULE in", values, "serverChkRule");
            return (Criteria) this;
        }

        public Criteria andServerChkRuleNotIn(List<String> values) {
            addCriterion("SERVER_CHK_RULE not in", values, "serverChkRule");
            return (Criteria) this;
        }

        public Criteria andServerChkRuleBetween(String value1, String value2) {
            addCriterion("SERVER_CHK_RULE between", value1, value2, "serverChkRule");
            return (Criteria) this;
        }

        public Criteria andServerChkRuleNotBetween(String value1, String value2) {
            addCriterion("SERVER_CHK_RULE not between", value1, value2, "serverChkRule");
            return (Criteria) this;
        }

        public Criteria andOptionTypeIsNull() {
            addCriterion("OPTION_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andOptionTypeIsNotNull() {
            addCriterion("OPTION_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andOptionTypeEqualTo(String value) {
            addCriterion("OPTION_TYPE =", value, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeNotEqualTo(String value) {
            addCriterion("OPTION_TYPE <>", value, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeGreaterThan(String value) {
            addCriterion("OPTION_TYPE >", value, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeGreaterThanOrEqualTo(String value) {
            addCriterion("OPTION_TYPE >=", value, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeLessThan(String value) {
            addCriterion("OPTION_TYPE <", value, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeLessThanOrEqualTo(String value) {
            addCriterion("OPTION_TYPE <=", value, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeLike(String value) {
            addCriterion("OPTION_TYPE like", value, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeNotLike(String value) {
            addCriterion("OPTION_TYPE not like", value, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeIn(List<String> values) {
            addCriterion("OPTION_TYPE in", values, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeNotIn(List<String> values) {
            addCriterion("OPTION_TYPE not in", values, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeBetween(String value1, String value2) {
            addCriterion("OPTION_TYPE between", value1, value2, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionTypeNotBetween(String value1, String value2) {
            addCriterion("OPTION_TYPE not between", value1, value2, "optionType");
            return (Criteria) this;
        }

        public Criteria andOptionValueIsNull() {
            addCriterion("OPTION_VALUE is null");
            return (Criteria) this;
        }

        public Criteria andOptionValueIsNotNull() {
            addCriterion("OPTION_VALUE is not null");
            return (Criteria) this;
        }

        public Criteria andOptionValueEqualTo(String value) {
            addCriterion("OPTION_VALUE =", value, "optionValue");
            return (Criteria) this;
        }

        public Criteria andOptionValueNotEqualTo(String value) {
            addCriterion("OPTION_VALUE <>", value, "optionValue");
            return (Criteria) this;
        }

        public Criteria andOptionValueGreaterThan(String value) {
            addCriterion("OPTION_VALUE >", value, "optionValue");
            return (Criteria) this;
        }

        public Criteria andOptionValueGreaterThanOrEqualTo(String value) {
            addCriterion("OPTION_VALUE >=", value, "optionValue");
            return (Criteria) this;
        }

        public Criteria andOptionValueLessThan(String value) {
            addCriterion("OPTION_VALUE <", value, "optionValue");
            return (Criteria) this;
        }

        public Criteria andOptionValueLessThanOrEqualTo(String value) {
            addCriterion("OPTION_VALUE <=", value, "optionValue");
            return (Criteria) this;
        }

        public Criteria andOptionValueLike(String value) {
            addCriterion("OPTION_VALUE like", value, "optionValue");
            return (Criteria) this;
        }

        public Criteria andOptionValueNotLike(String value) {
            addCriterion("OPTION_VALUE not like", value, "optionValue");
            return (Criteria) this;
        }

        public Criteria andOptionValueIn(List<String> values) {
            addCriterion("OPTION_VALUE in", values, "optionValue");
            return (Criteria) this;
        }

        public Criteria andOptionValueNotIn(List<String> values) {
            addCriterion("OPTION_VALUE not in", values, "optionValue");
            return (Criteria) this;
        }

        public Criteria andOptionValueBetween(String value1, String value2) {
            addCriterion("OPTION_VALUE between", value1, value2, "optionValue");
            return (Criteria) this;
        }

        public Criteria andOptionValueNotBetween(String value1, String value2) {
            addCriterion("OPTION_VALUE not between", value1, value2, "optionValue");
            return (Criteria) this;
        }

        public Criteria andCrudDsIdIsNull() {
            addCriterion("CRUD_DS_ID is null");
            return (Criteria) this;
        }

        public Criteria andCrudDsIdIsNotNull() {
            addCriterion("CRUD_DS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCrudDsIdEqualTo(Long value) {
            addCriterion("CRUD_DS_ID =", value, "crudDsId");
            return (Criteria) this;
        }

        public Criteria andCrudDsIdNotEqualTo(Long value) {
            addCriterion("CRUD_DS_ID <>", value, "crudDsId");
            return (Criteria) this;
        }

        public Criteria andCrudDsIdGreaterThan(Long value) {
            addCriterion("CRUD_DS_ID >", value, "crudDsId");
            return (Criteria) this;
        }

        public Criteria andCrudDsIdGreaterThanOrEqualTo(Long value) {
            addCriterion("CRUD_DS_ID >=", value, "crudDsId");
            return (Criteria) this;
        }

        public Criteria andCrudDsIdLessThan(Long value) {
            addCriterion("CRUD_DS_ID <", value, "crudDsId");
            return (Criteria) this;
        }

        public Criteria andCrudDsIdLessThanOrEqualTo(Long value) {
            addCriterion("CRUD_DS_ID <=", value, "crudDsId");
            return (Criteria) this;
        }

        public Criteria andCrudDsIdIn(List<Long> values) {
            addCriterion("CRUD_DS_ID in", values, "crudDsId");
            return (Criteria) this;
        }

        public Criteria andCrudDsIdNotIn(List<Long> values) {
            addCriterion("CRUD_DS_ID not in", values, "crudDsId");
            return (Criteria) this;
        }

        public Criteria andCrudDsIdBetween(Long value1, Long value2) {
            addCriterion("CRUD_DS_ID between", value1, value2, "crudDsId");
            return (Criteria) this;
        }

        public Criteria andCrudDsIdNotBetween(Long value1, Long value2) {
            addCriterion("CRUD_DS_ID not between", value1, value2, "crudDsId");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNull() {
            addCriterion("DELETED is null");
            return (Criteria) this;
        }

        public Criteria andDeletedIsNotNull() {
            addCriterion("DELETED is not null");
            return (Criteria) this;
        }

        public Criteria andDeletedEqualTo(Short value) {
            addCriterion("DELETED =", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotEqualTo(Short value) {
            addCriterion("DELETED <>", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThan(Short value) {
            addCriterion("DELETED >", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedGreaterThanOrEqualTo(Short value) {
            addCriterion("DELETED >=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThan(Short value) {
            addCriterion("DELETED <", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedLessThanOrEqualTo(Short value) {
            addCriterion("DELETED <=", value, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedIn(List<Short> values) {
            addCriterion("DELETED in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotIn(List<Short> values) {
            addCriterion("DELETED not in", values, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedBetween(Short value1, Short value2) {
            addCriterion("DELETED between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andDeletedNotBetween(Short value1, Short value2) {
            addCriterion("DELETED not between", value1, value2, "deleted");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNull() {
            addCriterion("CREATED_AT is null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIsNotNull() {
            addCriterion("CREATED_AT is not null");
            return (Criteria) this;
        }

        public Criteria andCreatedAtEqualTo(Date value) {
            addCriterion("CREATED_AT =", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotEqualTo(Date value) {
            addCriterion("CREATED_AT <>", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThan(Date value) {
            addCriterion("CREATED_AT >", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("CREATED_AT >=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThan(Date value) {
            addCriterion("CREATED_AT <", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtLessThanOrEqualTo(Date value) {
            addCriterion("CREATED_AT <=", value, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtIn(List<Date> values) {
            addCriterion("CREATED_AT in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotIn(List<Date> values) {
            addCriterion("CREATED_AT not in", values, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtBetween(Date value1, Date value2) {
            addCriterion("CREATED_AT between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andCreatedAtNotBetween(Date value1, Date value2) {
            addCriterion("CREATED_AT not between", value1, value2, "createdAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIsNull() {
            addCriterion("UPDATED_AT is null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIsNotNull() {
            addCriterion("UPDATED_AT is not null");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtEqualTo(Date value) {
            addCriterion("UPDATED_AT =", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotEqualTo(Date value) {
            addCriterion("UPDATED_AT <>", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThan(Date value) {
            addCriterion("UPDATED_AT >", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtGreaterThanOrEqualTo(Date value) {
            addCriterion("UPDATED_AT >=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThan(Date value) {
            addCriterion("UPDATED_AT <", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtLessThanOrEqualTo(Date value) {
            addCriterion("UPDATED_AT <=", value, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtIn(List<Date> values) {
            addCriterion("UPDATED_AT in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotIn(List<Date> values) {
            addCriterion("UPDATED_AT not in", values, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtBetween(Date value1, Date value2) {
            addCriterion("UPDATED_AT between", value1, value2, "updatedAt");
            return (Criteria) this;
        }

        public Criteria andUpdatedAtNotBetween(Date value1, Date value2) {
            addCriterion("UPDATED_AT not between", value1, value2, "updatedAt");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
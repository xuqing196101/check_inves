package ses.model.sms.review;

import java.util.ArrayList;
import java.util.List;

public class SupplierAttachAuditExample {
    /**
     * T_SES_SMS_SUPPLIER_ATCH_AUDIT
     */
    protected String orderByClause;

    /**
     * T_SES_SMS_SUPPLIER_ATCH_AUDIT
     */
    protected boolean distinct;

    /**
     * T_SES_SMS_SUPPLIER_ATCH_AUDIT
     */
    protected List<Criteria> oredCriteria;

    public SupplierAttachAuditExample() {
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

    /**
     * T_SES_SMS_SUPPLIER_ATCH_AUDIT null
     */
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

        public Criteria andIdEqualTo(String value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("ID like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("ID not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNull() {
            addCriterion("SUPPLIER_ID is null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIsNotNull() {
            addCriterion("SUPPLIER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andSupplierIdEqualTo(String value) {
            addCriterion("SUPPLIER_ID =", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotEqualTo(String value) {
            addCriterion("SUPPLIER_ID <>", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThan(String value) {
            addCriterion("SUPPLIER_ID >", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdGreaterThanOrEqualTo(String value) {
            addCriterion("SUPPLIER_ID >=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThan(String value) {
            addCriterion("SUPPLIER_ID <", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLessThanOrEqualTo(String value) {
            addCriterion("SUPPLIER_ID <=", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdLike(String value) {
            addCriterion("SUPPLIER_ID like", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotLike(String value) {
            addCriterion("SUPPLIER_ID not like", value, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdIn(List<String> values) {
            addCriterion("SUPPLIER_ID in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotIn(List<String> values) {
            addCriterion("SUPPLIER_ID not in", values, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdBetween(String value1, String value2) {
            addCriterion("SUPPLIER_ID between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andSupplierIdNotBetween(String value1, String value2) {
            addCriterion("SUPPLIER_ID not between", value1, value2, "supplierId");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNull() {
            addCriterion("TYPE_ID is null");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNotNull() {
            addCriterion("TYPE_ID is not null");
            return (Criteria) this;
        }

        public Criteria andTypeIdEqualTo(String value) {
            addCriterion("TYPE_ID =", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotEqualTo(String value) {
            addCriterion("TYPE_ID <>", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThan(String value) {
            addCriterion("TYPE_ID >", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThanOrEqualTo(String value) {
            addCriterion("TYPE_ID >=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThan(String value) {
            addCriterion("TYPE_ID <", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThanOrEqualTo(String value) {
            addCriterion("TYPE_ID <=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLike(String value) {
            addCriterion("TYPE_ID like", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotLike(String value) {
            addCriterion("TYPE_ID not like", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdIn(List<String> values) {
            addCriterion("TYPE_ID in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotIn(List<String> values) {
            addCriterion("TYPE_ID not in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdBetween(String value1, String value2) {
            addCriterion("TYPE_ID between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotBetween(String value1, String value2) {
            addCriterion("TYPE_ID not between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdIsNull() {
            addCriterion("BUSINESS_ID is null");
            return (Criteria) this;
        }

        public Criteria andBusinessIdIsNotNull() {
            addCriterion("BUSINESS_ID is not null");
            return (Criteria) this;
        }

        public Criteria andBusinessIdEqualTo(String value) {
            addCriterion("BUSINESS_ID =", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotEqualTo(String value) {
            addCriterion("BUSINESS_ID <>", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdGreaterThan(String value) {
            addCriterion("BUSINESS_ID >", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdGreaterThanOrEqualTo(String value) {
            addCriterion("BUSINESS_ID >=", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdLessThan(String value) {
            addCriterion("BUSINESS_ID <", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdLessThanOrEqualTo(String value) {
            addCriterion("BUSINESS_ID <=", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdLike(String value) {
            addCriterion("BUSINESS_ID like", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotLike(String value) {
            addCriterion("BUSINESS_ID not like", value, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdIn(List<String> values) {
            addCriterion("BUSINESS_ID in", values, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotIn(List<String> values) {
            addCriterion("BUSINESS_ID not in", values, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdBetween(String value1, String value2) {
            addCriterion("BUSINESS_ID between", value1, value2, "businessId");
            return (Criteria) this;
        }

        public Criteria andBusinessIdNotBetween(String value1, String value2) {
            addCriterion("BUSINESS_ID not between", value1, value2, "businessId");
            return (Criteria) this;
        }

        public Criteria andAttatchCodeIsNull() {
            addCriterion("ATTATCH_CODE is null");
            return (Criteria) this;
        }

        public Criteria andAttatchCodeIsNotNull() {
            addCriterion("ATTATCH_CODE is not null");
            return (Criteria) this;
        }

        public Criteria andAttatchCodeEqualTo(String value) {
            addCriterion("ATTATCH_CODE =", value, "attatchCode");
            return (Criteria) this;
        }

        public Criteria andAttatchCodeNotEqualTo(String value) {
            addCriterion("ATTATCH_CODE <>", value, "attatchCode");
            return (Criteria) this;
        }

        public Criteria andAttatchCodeGreaterThan(String value) {
            addCriterion("ATTATCH_CODE >", value, "attatchCode");
            return (Criteria) this;
        }

        public Criteria andAttatchCodeGreaterThanOrEqualTo(String value) {
            addCriterion("ATTATCH_CODE >=", value, "attatchCode");
            return (Criteria) this;
        }

        public Criteria andAttatchCodeLessThan(String value) {
            addCriterion("ATTATCH_CODE <", value, "attatchCode");
            return (Criteria) this;
        }

        public Criteria andAttatchCodeLessThanOrEqualTo(String value) {
            addCriterion("ATTATCH_CODE <=", value, "attatchCode");
            return (Criteria) this;
        }

        public Criteria andAttatchCodeLike(String value) {
            addCriterion("ATTATCH_CODE like", value, "attatchCode");
            return (Criteria) this;
        }

        public Criteria andAttatchCodeNotLike(String value) {
            addCriterion("ATTATCH_CODE not like", value, "attatchCode");
            return (Criteria) this;
        }

        public Criteria andAttatchCodeIn(List<String> values) {
            addCriterion("ATTATCH_CODE in", values, "attatchCode");
            return (Criteria) this;
        }

        public Criteria andAttatchCodeNotIn(List<String> values) {
            addCriterion("ATTATCH_CODE not in", values, "attatchCode");
            return (Criteria) this;
        }

        public Criteria andAttatchCodeBetween(String value1, String value2) {
            addCriterion("ATTATCH_CODE between", value1, value2, "attatchCode");
            return (Criteria) this;
        }

        public Criteria andAttatchCodeNotBetween(String value1, String value2) {
            addCriterion("ATTATCH_CODE not between", value1, value2, "attatchCode");
            return (Criteria) this;
        }

        public Criteria andAttatchNameIsNull() {
            addCriterion("ATTATCH_NAME is null");
            return (Criteria) this;
        }

        public Criteria andAttatchNameIsNotNull() {
            addCriterion("ATTATCH_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andAttatchNameEqualTo(String value) {
            addCriterion("ATTATCH_NAME =", value, "attatchName");
            return (Criteria) this;
        }

        public Criteria andAttatchNameNotEqualTo(String value) {
            addCriterion("ATTATCH_NAME <>", value, "attatchName");
            return (Criteria) this;
        }

        public Criteria andAttatchNameGreaterThan(String value) {
            addCriterion("ATTATCH_NAME >", value, "attatchName");
            return (Criteria) this;
        }

        public Criteria andAttatchNameGreaterThanOrEqualTo(String value) {
            addCriterion("ATTATCH_NAME >=", value, "attatchName");
            return (Criteria) this;
        }

        public Criteria andAttatchNameLessThan(String value) {
            addCriterion("ATTATCH_NAME <", value, "attatchName");
            return (Criteria) this;
        }

        public Criteria andAttatchNameLessThanOrEqualTo(String value) {
            addCriterion("ATTATCH_NAME <=", value, "attatchName");
            return (Criteria) this;
        }

        public Criteria andAttatchNameLike(String value) {
            addCriterion("ATTATCH_NAME like", value, "attatchName");
            return (Criteria) this;
        }

        public Criteria andAttatchNameNotLike(String value) {
            addCriterion("ATTATCH_NAME not like", value, "attatchName");
            return (Criteria) this;
        }

        public Criteria andAttatchNameIn(List<String> values) {
            addCriterion("ATTATCH_NAME in", values, "attatchName");
            return (Criteria) this;
        }

        public Criteria andAttatchNameNotIn(List<String> values) {
            addCriterion("ATTATCH_NAME not in", values, "attatchName");
            return (Criteria) this;
        }

        public Criteria andAttatchNameBetween(String value1, String value2) {
            addCriterion("ATTATCH_NAME between", value1, value2, "attatchName");
            return (Criteria) this;
        }

        public Criteria andAttatchNameNotBetween(String value1, String value2) {
            addCriterion("ATTATCH_NAME not between", value1, value2, "attatchName");
            return (Criteria) this;
        }

        public Criteria andIsAccordIsNull() {
            addCriterion("IS_ACCORD is null");
            return (Criteria) this;
        }

        public Criteria andIsAccordIsNotNull() {
            addCriterion("IS_ACCORD is not null");
            return (Criteria) this;
        }

        public Criteria andIsAccordEqualTo(Integer value) {
            addCriterion("IS_ACCORD =", value, "isAccord");
            return (Criteria) this;
        }

        public Criteria andIsAccordNotEqualTo(Integer value) {
            addCriterion("IS_ACCORD <>", value, "isAccord");
            return (Criteria) this;
        }

        public Criteria andIsAccordGreaterThan(Integer value) {
            addCriterion("IS_ACCORD >", value, "isAccord");
            return (Criteria) this;
        }

        public Criteria andIsAccordGreaterThanOrEqualTo(Integer value) {
            addCriterion("IS_ACCORD >=", value, "isAccord");
            return (Criteria) this;
        }

        public Criteria andIsAccordLessThan(Integer value) {
            addCriterion("IS_ACCORD <", value, "isAccord");
            return (Criteria) this;
        }

        public Criteria andIsAccordLessThanOrEqualTo(Integer value) {
            addCriterion("IS_ACCORD <=", value, "isAccord");
            return (Criteria) this;
        }

        public Criteria andIsAccordIn(List<Integer> values) {
            addCriterion("IS_ACCORD in", values, "isAccord");
            return (Criteria) this;
        }

        public Criteria andIsAccordNotIn(List<Integer> values) {
            addCriterion("IS_ACCORD not in", values, "isAccord");
            return (Criteria) this;
        }

        public Criteria andIsAccordBetween(Integer value1, Integer value2) {
            addCriterion("IS_ACCORD between", value1, value2, "isAccord");
            return (Criteria) this;
        }

        public Criteria andIsAccordNotBetween(Integer value1, Integer value2) {
            addCriterion("IS_ACCORD not between", value1, value2, "isAccord");
            return (Criteria) this;
        }

        public Criteria andSuggestIsNull() {
            addCriterion("SUGGEST is null");
            return (Criteria) this;
        }

        public Criteria andSuggestIsNotNull() {
            addCriterion("SUGGEST is not null");
            return (Criteria) this;
        }

        public Criteria andSuggestEqualTo(String value) {
            addCriterion("SUGGEST =", value, "suggest");
            return (Criteria) this;
        }

        public Criteria andSuggestNotEqualTo(String value) {
            addCriterion("SUGGEST <>", value, "suggest");
            return (Criteria) this;
        }

        public Criteria andSuggestGreaterThan(String value) {
            addCriterion("SUGGEST >", value, "suggest");
            return (Criteria) this;
        }

        public Criteria andSuggestGreaterThanOrEqualTo(String value) {
            addCriterion("SUGGEST >=", value, "suggest");
            return (Criteria) this;
        }

        public Criteria andSuggestLessThan(String value) {
            addCriterion("SUGGEST <", value, "suggest");
            return (Criteria) this;
        }

        public Criteria andSuggestLessThanOrEqualTo(String value) {
            addCriterion("SUGGEST <=", value, "suggest");
            return (Criteria) this;
        }

        public Criteria andSuggestLike(String value) {
            addCriterion("SUGGEST like", value, "suggest");
            return (Criteria) this;
        }

        public Criteria andSuggestNotLike(String value) {
            addCriterion("SUGGEST not like", value, "suggest");
            return (Criteria) this;
        }

        public Criteria andSuggestIn(List<String> values) {
            addCriterion("SUGGEST in", values, "suggest");
            return (Criteria) this;
        }

        public Criteria andSuggestNotIn(List<String> values) {
            addCriterion("SUGGEST not in", values, "suggest");
            return (Criteria) this;
        }

        public Criteria andSuggestBetween(String value1, String value2) {
            addCriterion("SUGGEST between", value1, value2, "suggest");
            return (Criteria) this;
        }

        public Criteria andSuggestNotBetween(String value1, String value2) {
            addCriterion("SUGGEST not between", value1, value2, "suggest");
            return (Criteria) this;
        }

        public Criteria andViewUrlIsNull() {
            addCriterion("VIEW_URL is null");
            return (Criteria) this;
        }

        public Criteria andViewUrlIsNotNull() {
            addCriterion("VIEW_URL is not null");
            return (Criteria) this;
        }

        public Criteria andViewUrlEqualTo(String value) {
            addCriterion("VIEW_URL =", value, "viewUrl");
            return (Criteria) this;
        }

        public Criteria andViewUrlNotEqualTo(String value) {
            addCriterion("VIEW_URL <>", value, "viewUrl");
            return (Criteria) this;
        }

        public Criteria andViewUrlGreaterThan(String value) {
            addCriterion("VIEW_URL >", value, "viewUrl");
            return (Criteria) this;
        }

        public Criteria andViewUrlGreaterThanOrEqualTo(String value) {
            addCriterion("VIEW_URL >=", value, "viewUrl");
            return (Criteria) this;
        }

        public Criteria andViewUrlLessThan(String value) {
            addCriterion("VIEW_URL <", value, "viewUrl");
            return (Criteria) this;
        }

        public Criteria andViewUrlLessThanOrEqualTo(String value) {
            addCriterion("VIEW_URL <=", value, "viewUrl");
            return (Criteria) this;
        }

        public Criteria andViewUrlLike(String value) {
            addCriterion("VIEW_URL like", value, "viewUrl");
            return (Criteria) this;
        }

        public Criteria andViewUrlNotLike(String value) {
            addCriterion("VIEW_URL not like", value, "viewUrl");
            return (Criteria) this;
        }

        public Criteria andViewUrlIn(List<String> values) {
            addCriterion("VIEW_URL in", values, "viewUrl");
            return (Criteria) this;
        }

        public Criteria andViewUrlNotIn(List<String> values) {
            addCriterion("VIEW_URL not in", values, "viewUrl");
            return (Criteria) this;
        }

        public Criteria andViewUrlBetween(String value1, String value2) {
            addCriterion("VIEW_URL between", value1, value2, "viewUrl");
            return (Criteria) this;
        }

        public Criteria andViewUrlNotBetween(String value1, String value2) {
            addCriterion("VIEW_URL not between", value1, value2, "viewUrl");
            return (Criteria) this;
        }

        public Criteria andAuditTypeIsNull() {
            addCriterion("AUDIT_TYPE is null");
            return (Criteria) this;
        }

        public Criteria andAuditTypeIsNotNull() {
            addCriterion("AUDIT_TYPE is not null");
            return (Criteria) this;
        }

        public Criteria andAuditTypeEqualTo(Integer value) {
            addCriterion("AUDIT_TYPE =", value, "auditType");
            return (Criteria) this;
        }

        public Criteria andAuditTypeNotEqualTo(Integer value) {
            addCriterion("AUDIT_TYPE <>", value, "auditType");
            return (Criteria) this;
        }

        public Criteria andAuditTypeGreaterThan(Integer value) {
            addCriterion("AUDIT_TYPE >", value, "auditType");
            return (Criteria) this;
        }

        public Criteria andAuditTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("AUDIT_TYPE >=", value, "auditType");
            return (Criteria) this;
        }

        public Criteria andAuditTypeLessThan(Integer value) {
            addCriterion("AUDIT_TYPE <", value, "auditType");
            return (Criteria) this;
        }

        public Criteria andAuditTypeLessThanOrEqualTo(Integer value) {
            addCriterion("AUDIT_TYPE <=", value, "auditType");
            return (Criteria) this;
        }

        public Criteria andAuditTypeIn(List<Integer> values) {
            addCriterion("AUDIT_TYPE in", values, "auditType");
            return (Criteria) this;
        }

        public Criteria andAuditTypeNotIn(List<Integer> values) {
            addCriterion("AUDIT_TYPE not in", values, "auditType");
            return (Criteria) this;
        }

        public Criteria andAuditTypeBetween(Integer value1, Integer value2) {
            addCriterion("AUDIT_TYPE between", value1, value2, "auditType");
            return (Criteria) this;
        }

        public Criteria andAuditTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("AUDIT_TYPE not between", value1, value2, "auditType");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("IS_DELETED is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("IS_DELETED is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Integer value) {
            addCriterion("IS_DELETED =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Integer value) {
            addCriterion("IS_DELETED <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Integer value) {
            addCriterion("IS_DELETED >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Integer value) {
            addCriterion("IS_DELETED >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Integer value) {
            addCriterion("IS_DELETED <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Integer value) {
            addCriterion("IS_DELETED <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Integer> values) {
            addCriterion("IS_DELETED in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Integer> values) {
            addCriterion("IS_DELETED not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Integer value1, Integer value2) {
            addCriterion("IS_DELETED between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Integer value1, Integer value2) {
            addCriterion("IS_DELETED not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andPositionIsNull() {
            addCriterion("POSITION is null");
            return (Criteria) this;
        }

        public Criteria andPositionIsNotNull() {
            addCriterion("POSITION is not null");
            return (Criteria) this;
        }

        public Criteria andPositionEqualTo(Integer value) {
            addCriterion("POSITION =", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotEqualTo(Integer value) {
            addCriterion("POSITION <>", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThan(Integer value) {
            addCriterion("POSITION >", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionGreaterThanOrEqualTo(Integer value) {
            addCriterion("POSITION >=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThan(Integer value) {
            addCriterion("POSITION <", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionLessThanOrEqualTo(Integer value) {
            addCriterion("POSITION <=", value, "position");
            return (Criteria) this;
        }

        public Criteria andPositionIn(List<Integer> values) {
            addCriterion("POSITION in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotIn(List<Integer> values) {
            addCriterion("POSITION not in", values, "position");
            return (Criteria) this;
        }

        public Criteria andPositionBetween(Integer value1, Integer value2) {
            addCriterion("POSITION between", value1, value2, "position");
            return (Criteria) this;
        }

        public Criteria andPositionNotBetween(Integer value1, Integer value2) {
            addCriterion("POSITION not between", value1, value2, "position");
            return (Criteria) this;
        }
    }

    /**
     * T_SES_SMS_SUPPLIER_ATCH_AUDIT
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * T_SES_SMS_SUPPLIER_ATCH_AUDIT null
     */
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
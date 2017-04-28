package bss.model.ob;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OBProductExample implements Serializable {
    /** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;

	protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OBProductExample() {
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

        public Criteria andCodeIsNull() {
            addCriterion("CODE is null");
            return (Criteria) this;
        }

        public Criteria andCodeIsNotNull() {
            addCriterion("CODE is not null");
            return (Criteria) this;
        }

        public Criteria andCodeEqualTo(String value) {
            addCriterion("CODE =", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotEqualTo(String value) {
            addCriterion("CODE <>", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThan(String value) {
            addCriterion("CODE >", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeGreaterThanOrEqualTo(String value) {
            addCriterion("CODE >=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThan(String value) {
            addCriterion("CODE <", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLessThanOrEqualTo(String value) {
            addCriterion("CODE <=", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeLike(String value) {
            addCriterion("CODE like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotLike(String value) {
            addCriterion("CODE not like", value, "code");
            return (Criteria) this;
        }

        public Criteria andCodeIn(List<String> values) {
            addCriterion("CODE in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotIn(List<String> values) {
            addCriterion("CODE not in", values, "code");
            return (Criteria) this;
        }

        public Criteria andCodeBetween(String value1, String value2) {
            addCriterion("CODE between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andCodeNotBetween(String value1, String value2) {
            addCriterion("CODE not between", value1, value2, "code");
            return (Criteria) this;
        }

        public Criteria andNameIsNull() {
            addCriterion("NAME is null");
            return (Criteria) this;
        }

        public Criteria andNameIsNotNull() {
            addCriterion("NAME is not null");
            return (Criteria) this;
        }

        public Criteria andNameEqualTo(String value) {
            addCriterion("NAME =", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotEqualTo(String value) {
            addCriterion("NAME <>", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThan(String value) {
            addCriterion("NAME >", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameGreaterThanOrEqualTo(String value) {
            addCriterion("NAME >=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThan(String value) {
            addCriterion("NAME <", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLessThanOrEqualTo(String value) {
            addCriterion("NAME <=", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameLike(String value) {
            addCriterion("NAME like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotLike(String value) {
            addCriterion("NAME not like", value, "name");
            return (Criteria) this;
        }

        public Criteria andNameIn(List<String> values) {
            addCriterion("NAME in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotIn(List<String> values) {
            addCriterion("NAME not in", values, "name");
            return (Criteria) this;
        }

        public Criteria andNameBetween(String value1, String value2) {
            addCriterion("NAME between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andNameNotBetween(String value1, String value2) {
            addCriterion("NAME not between", value1, value2, "name");
            return (Criteria) this;
        }

        public Criteria andProcurementIdIsNull() {
            addCriterion("PROCUREMENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andProcurementIdIsNotNull() {
            addCriterion("PROCUREMENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andProcurementIdEqualTo(String value) {
            addCriterion("PROCUREMENT_ID =", value, "procurementId");
            return (Criteria) this;
        }

        public Criteria andProcurementIdNotEqualTo(String value) {
            addCriterion("PROCUREMENT_ID <>", value, "procurementId");
            return (Criteria) this;
        }

        public Criteria andProcurementIdGreaterThan(String value) {
            addCriterion("PROCUREMENT_ID >", value, "procurementId");
            return (Criteria) this;
        }

        public Criteria andProcurementIdGreaterThanOrEqualTo(String value) {
            addCriterion("PROCUREMENT_ID >=", value, "procurementId");
            return (Criteria) this;
        }

        public Criteria andProcurementIdLessThan(String value) {
            addCriterion("PROCUREMENT_ID <", value, "procurementId");
            return (Criteria) this;
        }

        public Criteria andProcurementIdLessThanOrEqualTo(String value) {
            addCriterion("PROCUREMENT_ID <=", value, "procurementId");
            return (Criteria) this;
        }

        public Criteria andProcurementIdLike(String value) {
            addCriterion("PROCUREMENT_ID like", value, "procurementId");
            return (Criteria) this;
        }

        public Criteria andProcurementIdNotLike(String value) {
            addCriterion("PROCUREMENT_ID not like", value, "procurementId");
            return (Criteria) this;
        }

        public Criteria andProcurementIdIn(List<String> values) {
            addCriterion("PROCUREMENT_ID in", values, "procurementId");
            return (Criteria) this;
        }

        public Criteria andProcurementIdNotIn(List<String> values) {
            addCriterion("PROCUREMENT_ID not in", values, "procurementId");
            return (Criteria) this;
        }

        public Criteria andProcurementIdBetween(String value1, String value2) {
            addCriterion("PROCUREMENT_ID between", value1, value2, "procurementId");
            return (Criteria) this;
        }

        public Criteria andProcurementIdNotBetween(String value1, String value2) {
            addCriterion("PROCUREMENT_ID not between", value1, value2, "procurementId");
            return (Criteria) this;
        }

        public Criteria andCategoryParentIdIsNull() {
            addCriterion("CATEGORY_PARENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andCategoryParentIdIsNotNull() {
            addCriterion("CATEGORY_PARENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryParentIdEqualTo(String value) {
            addCriterion("CATEGORY_PARENT_ID =", value, "categoryParentId");
            return (Criteria) this;
        }

        public Criteria andCategoryParentIdNotEqualTo(String value) {
            addCriterion("CATEGORY_PARENT_ID <>", value, "categoryParentId");
            return (Criteria) this;
        }

        public Criteria andCategoryParentIdGreaterThan(String value) {
            addCriterion("CATEGORY_PARENT_ID >", value, "categoryParentId");
            return (Criteria) this;
        }

        public Criteria andCategoryParentIdGreaterThanOrEqualTo(String value) {
            addCriterion("CATEGORY_PARENT_ID >=", value, "categoryParentId");
            return (Criteria) this;
        }

        public Criteria andCategoryParentIdLessThan(String value) {
            addCriterion("CATEGORY_PARENT_ID <", value, "categoryParentId");
            return (Criteria) this;
        }

        public Criteria andCategoryParentIdLessThanOrEqualTo(String value) {
            addCriterion("CATEGORY_PARENT_ID <=", value, "categoryParentId");
            return (Criteria) this;
        }

        public Criteria andCategoryParentIdLike(String value) {
            addCriterion("CATEGORY_PARENT_ID like", value, "categoryParentId");
            return (Criteria) this;
        }

        public Criteria andCategoryParentIdNotLike(String value) {
            addCriterion("CATEGORY_PARENT_ID not like", value, "categoryParentId");
            return (Criteria) this;
        }

        public Criteria andCategoryParentIdIn(List<String> values) {
            addCriterion("CATEGORY_PARENT_ID in", values, "categoryParentId");
            return (Criteria) this;
        }

        public Criteria andCategoryParentIdNotIn(List<String> values) {
            addCriterion("CATEGORY_PARENT_ID not in", values, "categoryParentId");
            return (Criteria) this;
        }

        public Criteria andCategoryParentIdBetween(String value1, String value2) {
            addCriterion("CATEGORY_PARENT_ID between", value1, value2, "categoryParentId");
            return (Criteria) this;
        }

        public Criteria andCategoryParentIdNotBetween(String value1, String value2) {
            addCriterion("CATEGORY_PARENT_ID not between", value1, value2, "categoryParentId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIsNull() {
            addCriterion("CATEGORY_ID is null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIsNotNull() {
            addCriterion("CATEGORY_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCategoryIdEqualTo(String value) {
            addCriterion("CATEGORY_ID =", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotEqualTo(String value) {
            addCriterion("CATEGORY_ID <>", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThan(String value) {
            addCriterion("CATEGORY_ID >", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdGreaterThanOrEqualTo(String value) {
            addCriterion("CATEGORY_ID >=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThan(String value) {
            addCriterion("CATEGORY_ID <", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLessThanOrEqualTo(String value) {
            addCriterion("CATEGORY_ID <=", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdLike(String value) {
            addCriterion("CATEGORY_ID like", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotLike(String value) {
            addCriterion("CATEGORY_ID not like", value, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdIn(List<String> values) {
            addCriterion("CATEGORY_ID in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotIn(List<String> values) {
            addCriterion("CATEGORY_ID not in", values, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdBetween(String value1, String value2) {
            addCriterion("CATEGORY_ID between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andCategoryIdNotBetween(String value1, String value2) {
            addCriterion("CATEGORY_ID not between", value1, value2, "categoryId");
            return (Criteria) this;
        }

        public Criteria andStandardModelIsNull() {
            addCriterion("STANDARD_MODEL is null");
            return (Criteria) this;
        }

        public Criteria andStandardModelIsNotNull() {
            addCriterion("STANDARD_MODEL is not null");
            return (Criteria) this;
        }

        public Criteria andStandardModelEqualTo(String value) {
            addCriterion("STANDARD_MODEL =", value, "standardModel");
            return (Criteria) this;
        }

        public Criteria andStandardModelNotEqualTo(String value) {
            addCriterion("STANDARD_MODEL <>", value, "standardModel");
            return (Criteria) this;
        }

        public Criteria andStandardModelGreaterThan(String value) {
            addCriterion("STANDARD_MODEL >", value, "standardModel");
            return (Criteria) this;
        }

        public Criteria andStandardModelGreaterThanOrEqualTo(String value) {
            addCriterion("STANDARD_MODEL >=", value, "standardModel");
            return (Criteria) this;
        }

        public Criteria andStandardModelLessThan(String value) {
            addCriterion("STANDARD_MODEL <", value, "standardModel");
            return (Criteria) this;
        }

        public Criteria andStandardModelLessThanOrEqualTo(String value) {
            addCriterion("STANDARD_MODEL <=", value, "standardModel");
            return (Criteria) this;
        }

        public Criteria andStandardModelLike(String value) {
            addCriterion("STANDARD_MODEL like", value, "standardModel");
            return (Criteria) this;
        }

        public Criteria andStandardModelNotLike(String value) {
            addCriterion("STANDARD_MODEL not like", value, "standardModel");
            return (Criteria) this;
        }

        public Criteria andStandardModelIn(List<String> values) {
            addCriterion("STANDARD_MODEL in", values, "standardModel");
            return (Criteria) this;
        }

        public Criteria andStandardModelNotIn(List<String> values) {
            addCriterion("STANDARD_MODEL not in", values, "standardModel");
            return (Criteria) this;
        }

        public Criteria andStandardModelBetween(String value1, String value2) {
            addCriterion("STANDARD_MODEL between", value1, value2, "standardModel");
            return (Criteria) this;
        }

        public Criteria andStandardModelNotBetween(String value1, String value2) {
            addCriterion("STANDARD_MODEL not between", value1, value2, "standardModel");
            return (Criteria) this;
        }

        public Criteria andQualityTechnicalStandardIsNull() {
            addCriterion("QUALITY_TECHNICAL_STANDARD is null");
            return (Criteria) this;
        }

        public Criteria andQualityTechnicalStandardIsNotNull() {
            addCriterion("QUALITY_TECHNICAL_STANDARD is not null");
            return (Criteria) this;
        }

        public Criteria andQualityTechnicalStandardEqualTo(String value) {
            addCriterion("QUALITY_TECHNICAL_STANDARD =", value, "qualityTechnicalStandard");
            return (Criteria) this;
        }

        public Criteria andQualityTechnicalStandardNotEqualTo(String value) {
            addCriterion("QUALITY_TECHNICAL_STANDARD <>", value, "qualityTechnicalStandard");
            return (Criteria) this;
        }

        public Criteria andQualityTechnicalStandardGreaterThan(String value) {
            addCriterion("QUALITY_TECHNICAL_STANDARD >", value, "qualityTechnicalStandard");
            return (Criteria) this;
        }

        public Criteria andQualityTechnicalStandardGreaterThanOrEqualTo(String value) {
            addCriterion("QUALITY_TECHNICAL_STANDARD >=", value, "qualityTechnicalStandard");
            return (Criteria) this;
        }

        public Criteria andQualityTechnicalStandardLessThan(String value) {
            addCriterion("QUALITY_TECHNICAL_STANDARD <", value, "qualityTechnicalStandard");
            return (Criteria) this;
        }

        public Criteria andQualityTechnicalStandardLessThanOrEqualTo(String value) {
            addCriterion("QUALITY_TECHNICAL_STANDARD <=", value, "qualityTechnicalStandard");
            return (Criteria) this;
        }

        public Criteria andQualityTechnicalStandardLike(String value) {
            addCriterion("QUALITY_TECHNICAL_STANDARD like", value, "qualityTechnicalStandard");
            return (Criteria) this;
        }

        public Criteria andQualityTechnicalStandardNotLike(String value) {
            addCriterion("QUALITY_TECHNICAL_STANDARD not like", value, "qualityTechnicalStandard");
            return (Criteria) this;
        }

        public Criteria andQualityTechnicalStandardIn(List<String> values) {
            addCriterion("QUALITY_TECHNICAL_STANDARD in", values, "qualityTechnicalStandard");
            return (Criteria) this;
        }

        public Criteria andQualityTechnicalStandardNotIn(List<String> values) {
            addCriterion("QUALITY_TECHNICAL_STANDARD not in", values, "qualityTechnicalStandard");
            return (Criteria) this;
        }

        public Criteria andQualityTechnicalStandardBetween(String value1, String value2) {
            addCriterion("QUALITY_TECHNICAL_STANDARD between", value1, value2, "qualityTechnicalStandard");
            return (Criteria) this;
        }

        public Criteria andQualityTechnicalStandardNotBetween(String value1, String value2) {
            addCriterion("QUALITY_TECHNICAL_STANDARD not between", value1, value2, "qualityTechnicalStandard");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("REMARK is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("REMARK is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("REMARK =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("REMARK <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("REMARK >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("REMARK >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("REMARK <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("REMARK <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("REMARK like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("REMARK not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("REMARK in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("REMARK not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("REMARK between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("REMARK not between", value1, value2, "remark");
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

        public Criteria andCreaterIdIsNull() {
            addCriterion("CREATER_ID is null");
            return (Criteria) this;
        }

        public Criteria andCreaterIdIsNotNull() {
            addCriterion("CREATER_ID is not null");
            return (Criteria) this;
        }

        public Criteria andCreaterIdEqualTo(String value) {
            addCriterion("CREATER_ID =", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdNotEqualTo(String value) {
            addCriterion("CREATER_ID <>", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdGreaterThan(String value) {
            addCriterion("CREATER_ID >", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdGreaterThanOrEqualTo(String value) {
            addCriterion("CREATER_ID >=", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdLessThan(String value) {
            addCriterion("CREATER_ID <", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdLessThanOrEqualTo(String value) {
            addCriterion("CREATER_ID <=", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdLike(String value) {
            addCriterion("CREATER_ID like", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdNotLike(String value) {
            addCriterion("CREATER_ID not like", value, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdIn(List<String> values) {
            addCriterion("CREATER_ID in", values, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdNotIn(List<String> values) {
            addCriterion("CREATER_ID not in", values, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdBetween(String value1, String value2) {
            addCriterion("CREATER_ID between", value1, value2, "createrId");
            return (Criteria) this;
        }

        public Criteria andCreaterIdNotBetween(String value1, String value2) {
            addCriterion("CREATER_ID not between", value1, value2, "createrId");
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
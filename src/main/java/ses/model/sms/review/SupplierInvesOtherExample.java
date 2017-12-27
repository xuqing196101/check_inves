package ses.model.sms.review;

import java.util.ArrayList;
import java.util.List;

public class SupplierInvesOtherExample {
    /**
     * T_SES_SMS_SUPPLIER_INVES_OTHER
     */
    protected String orderByClause;

    /**
     * T_SES_SMS_SUPPLIER_INVES_OTHER
     */
    protected boolean distinct;

    /**
     * T_SES_SMS_SUPPLIER_INVES_OTHER
     */
    protected List<Criteria> oredCriteria;

    public SupplierInvesOtherExample() {
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
     * T_SES_SMS_SUPPLIER_INVES_OTHER null
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

        public Criteria andProductionPlaceInfoIsNull() {
            addCriterion("PRODUCTION_PLACE_INFO is null");
            return (Criteria) this;
        }

        public Criteria andProductionPlaceInfoIsNotNull() {
            addCriterion("PRODUCTION_PLACE_INFO is not null");
            return (Criteria) this;
        }

        public Criteria andProductionPlaceInfoEqualTo(String value) {
            addCriterion("PRODUCTION_PLACE_INFO =", value, "productionPlaceInfo");
            return (Criteria) this;
        }

        public Criteria andProductionPlaceInfoNotEqualTo(String value) {
            addCriterion("PRODUCTION_PLACE_INFO <>", value, "productionPlaceInfo");
            return (Criteria) this;
        }

        public Criteria andProductionPlaceInfoGreaterThan(String value) {
            addCriterion("PRODUCTION_PLACE_INFO >", value, "productionPlaceInfo");
            return (Criteria) this;
        }

        public Criteria andProductionPlaceInfoGreaterThanOrEqualTo(String value) {
            addCriterion("PRODUCTION_PLACE_INFO >=", value, "productionPlaceInfo");
            return (Criteria) this;
        }

        public Criteria andProductionPlaceInfoLessThan(String value) {
            addCriterion("PRODUCTION_PLACE_INFO <", value, "productionPlaceInfo");
            return (Criteria) this;
        }

        public Criteria andProductionPlaceInfoLessThanOrEqualTo(String value) {
            addCriterion("PRODUCTION_PLACE_INFO <=", value, "productionPlaceInfo");
            return (Criteria) this;
        }

        public Criteria andProductionPlaceInfoLike(String value) {
            addCriterion("PRODUCTION_PLACE_INFO like", value, "productionPlaceInfo");
            return (Criteria) this;
        }

        public Criteria andProductionPlaceInfoNotLike(String value) {
            addCriterion("PRODUCTION_PLACE_INFO not like", value, "productionPlaceInfo");
            return (Criteria) this;
        }

        public Criteria andProductionPlaceInfoIn(List<String> values) {
            addCriterion("PRODUCTION_PLACE_INFO in", values, "productionPlaceInfo");
            return (Criteria) this;
        }

        public Criteria andProductionPlaceInfoNotIn(List<String> values) {
            addCriterion("PRODUCTION_PLACE_INFO not in", values, "productionPlaceInfo");
            return (Criteria) this;
        }

        public Criteria andProductionPlaceInfoBetween(String value1, String value2) {
            addCriterion("PRODUCTION_PLACE_INFO between", value1, value2, "productionPlaceInfo");
            return (Criteria) this;
        }

        public Criteria andProductionPlaceInfoNotBetween(String value1, String value2) {
            addCriterion("PRODUCTION_PLACE_INFO not between", value1, value2, "productionPlaceInfo");
            return (Criteria) this;
        }

        public Criteria andFacilitiesInfoIsNull() {
            addCriterion("FACILITIES_INFO is null");
            return (Criteria) this;
        }

        public Criteria andFacilitiesInfoIsNotNull() {
            addCriterion("FACILITIES_INFO is not null");
            return (Criteria) this;
        }

        public Criteria andFacilitiesInfoEqualTo(String value) {
            addCriterion("FACILITIES_INFO =", value, "facilitiesInfo");
            return (Criteria) this;
        }

        public Criteria andFacilitiesInfoNotEqualTo(String value) {
            addCriterion("FACILITIES_INFO <>", value, "facilitiesInfo");
            return (Criteria) this;
        }

        public Criteria andFacilitiesInfoGreaterThan(String value) {
            addCriterion("FACILITIES_INFO >", value, "facilitiesInfo");
            return (Criteria) this;
        }

        public Criteria andFacilitiesInfoGreaterThanOrEqualTo(String value) {
            addCriterion("FACILITIES_INFO >=", value, "facilitiesInfo");
            return (Criteria) this;
        }

        public Criteria andFacilitiesInfoLessThan(String value) {
            addCriterion("FACILITIES_INFO <", value, "facilitiesInfo");
            return (Criteria) this;
        }

        public Criteria andFacilitiesInfoLessThanOrEqualTo(String value) {
            addCriterion("FACILITIES_INFO <=", value, "facilitiesInfo");
            return (Criteria) this;
        }

        public Criteria andFacilitiesInfoLike(String value) {
            addCriterion("FACILITIES_INFO like", value, "facilitiesInfo");
            return (Criteria) this;
        }

        public Criteria andFacilitiesInfoNotLike(String value) {
            addCriterion("FACILITIES_INFO not like", value, "facilitiesInfo");
            return (Criteria) this;
        }

        public Criteria andFacilitiesInfoIn(List<String> values) {
            addCriterion("FACILITIES_INFO in", values, "facilitiesInfo");
            return (Criteria) this;
        }

        public Criteria andFacilitiesInfoNotIn(List<String> values) {
            addCriterion("FACILITIES_INFO not in", values, "facilitiesInfo");
            return (Criteria) this;
        }

        public Criteria andFacilitiesInfoBetween(String value1, String value2) {
            addCriterion("FACILITIES_INFO between", value1, value2, "facilitiesInfo");
            return (Criteria) this;
        }

        public Criteria andFacilitiesInfoNotBetween(String value1, String value2) {
            addCriterion("FACILITIES_INFO not between", value1, value2, "facilitiesInfo");
            return (Criteria) this;
        }

        public Criteria andPhotographerIsNull() {
            addCriterion("PHOTOGRAPHER is null");
            return (Criteria) this;
        }

        public Criteria andPhotographerIsNotNull() {
            addCriterion("PHOTOGRAPHER is not null");
            return (Criteria) this;
        }

        public Criteria andPhotographerEqualTo(String value) {
            addCriterion("PHOTOGRAPHER =", value, "photographer");
            return (Criteria) this;
        }

        public Criteria andPhotographerNotEqualTo(String value) {
            addCriterion("PHOTOGRAPHER <>", value, "photographer");
            return (Criteria) this;
        }

        public Criteria andPhotographerGreaterThan(String value) {
            addCriterion("PHOTOGRAPHER >", value, "photographer");
            return (Criteria) this;
        }

        public Criteria andPhotographerGreaterThanOrEqualTo(String value) {
            addCriterion("PHOTOGRAPHER >=", value, "photographer");
            return (Criteria) this;
        }

        public Criteria andPhotographerLessThan(String value) {
            addCriterion("PHOTOGRAPHER <", value, "photographer");
            return (Criteria) this;
        }

        public Criteria andPhotographerLessThanOrEqualTo(String value) {
            addCriterion("PHOTOGRAPHER <=", value, "photographer");
            return (Criteria) this;
        }

        public Criteria andPhotographerLike(String value) {
            addCriterion("PHOTOGRAPHER like", value, "photographer");
            return (Criteria) this;
        }

        public Criteria andPhotographerNotLike(String value) {
            addCriterion("PHOTOGRAPHER not like", value, "photographer");
            return (Criteria) this;
        }

        public Criteria andPhotographerIn(List<String> values) {
            addCriterion("PHOTOGRAPHER in", values, "photographer");
            return (Criteria) this;
        }

        public Criteria andPhotographerNotIn(List<String> values) {
            addCriterion("PHOTOGRAPHER not in", values, "photographer");
            return (Criteria) this;
        }

        public Criteria andPhotographerBetween(String value1, String value2) {
            addCriterion("PHOTOGRAPHER between", value1, value2, "photographer");
            return (Criteria) this;
        }

        public Criteria andPhotographerNotBetween(String value1, String value2) {
            addCriterion("PHOTOGRAPHER not between", value1, value2, "photographer");
            return (Criteria) this;
        }
    }

    /**
     * T_SES_SMS_SUPPLIER_INVES_OTHER
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * T_SES_SMS_SUPPLIER_INVES_OTHER null
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
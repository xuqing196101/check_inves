package bss.model.ob;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OBProjectExample implements Serializable {
    /** 
	* @Fields serialVersionUID : 
	*/ 
	private static final long serialVersionUID = 1L;

	protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public OBProjectExample() {
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

        public Criteria andDeliveryDeadlineIsNull() {
            addCriterion("DELIVERY_DEADLINE is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeadlineIsNotNull() {
            addCriterion("DELIVERY_DEADLINE is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeadlineEqualTo(Date value) {
            addCriterion("DELIVERY_DEADLINE =", value, "deliveryDeadline");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeadlineNotEqualTo(Date value) {
            addCriterion("DELIVERY_DEADLINE <>", value, "deliveryDeadline");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeadlineGreaterThan(Date value) {
            addCriterion("DELIVERY_DEADLINE >", value, "deliveryDeadline");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeadlineGreaterThanOrEqualTo(Date value) {
            addCriterion("DELIVERY_DEADLINE >=", value, "deliveryDeadline");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeadlineLessThan(Date value) {
            addCriterion("DELIVERY_DEADLINE <", value, "deliveryDeadline");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeadlineLessThanOrEqualTo(Date value) {
            addCriterion("DELIVERY_DEADLINE <=", value, "deliveryDeadline");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeadlineIn(List<Date> values) {
            addCriterion("DELIVERY_DEADLINE in", values, "deliveryDeadline");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeadlineNotIn(List<Date> values) {
            addCriterion("DELIVERY_DEADLINE not in", values, "deliveryDeadline");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeadlineBetween(Date value1, Date value2) {
            addCriterion("DELIVERY_DEADLINE between", value1, value2, "deliveryDeadline");
            return (Criteria) this;
        }

        public Criteria andDeliveryDeadlineNotBetween(Date value1, Date value2) {
            addCriterion("DELIVERY_DEADLINE not between", value1, value2, "deliveryDeadline");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressIsNull() {
            addCriterion("DELIVERY_ADDRESS is null");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressIsNotNull() {
            addCriterion("DELIVERY_ADDRESS is not null");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressEqualTo(String value) {
            addCriterion("DELIVERY_ADDRESS =", value, "deliveryAddress");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressNotEqualTo(String value) {
            addCriterion("DELIVERY_ADDRESS <>", value, "deliveryAddress");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressGreaterThan(String value) {
            addCriterion("DELIVERY_ADDRESS >", value, "deliveryAddress");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressGreaterThanOrEqualTo(String value) {
            addCriterion("DELIVERY_ADDRESS >=", value, "deliveryAddress");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressLessThan(String value) {
            addCriterion("DELIVERY_ADDRESS <", value, "deliveryAddress");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressLessThanOrEqualTo(String value) {
            addCriterion("DELIVERY_ADDRESS <=", value, "deliveryAddress");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressLike(String value) {
            addCriterion("DELIVERY_ADDRESS like", value, "deliveryAddress");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressNotLike(String value) {
            addCriterion("DELIVERY_ADDRESS not like", value, "deliveryAddress");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressIn(List<String> values) {
            addCriterion("DELIVERY_ADDRESS in", values, "deliveryAddress");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressNotIn(List<String> values) {
            addCriterion("DELIVERY_ADDRESS not in", values, "deliveryAddress");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressBetween(String value1, String value2) {
            addCriterion("DELIVERY_ADDRESS between", value1, value2, "deliveryAddress");
            return (Criteria) this;
        }

        public Criteria andDeliveryAddressNotBetween(String value1, String value2) {
            addCriterion("DELIVERY_ADDRESS not between", value1, value2, "deliveryAddress");
            return (Criteria) this;
        }

        public Criteria andTradedSupplierCountIsNull() {
            addCriterion("TRADED_SUPPLIER_COUNT is null");
            return (Criteria) this;
        }

        public Criteria andTradedSupplierCountIsNotNull() {
            addCriterion("TRADED_SUPPLIER_COUNT is not null");
            return (Criteria) this;
        }

        public Criteria andTradedSupplierCountEqualTo(Integer value) {
            addCriterion("TRADED_SUPPLIER_COUNT =", value, "tradedSupplierCount");
            return (Criteria) this;
        }

        public Criteria andTradedSupplierCountNotEqualTo(Integer value) {
            addCriterion("TRADED_SUPPLIER_COUNT <>", value, "tradedSupplierCount");
            return (Criteria) this;
        }

        public Criteria andTradedSupplierCountGreaterThan(Integer value) {
            addCriterion("TRADED_SUPPLIER_COUNT >", value, "tradedSupplierCount");
            return (Criteria) this;
        }

        public Criteria andTradedSupplierCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("TRADED_SUPPLIER_COUNT >=", value, "tradedSupplierCount");
            return (Criteria) this;
        }

        public Criteria andTradedSupplierCountLessThan(Integer value) {
            addCriterion("TRADED_SUPPLIER_COUNT <", value, "tradedSupplierCount");
            return (Criteria) this;
        }

        public Criteria andTradedSupplierCountLessThanOrEqualTo(Integer value) {
            addCriterion("TRADED_SUPPLIER_COUNT <=", value, "tradedSupplierCount");
            return (Criteria) this;
        }

        public Criteria andTradedSupplierCountIn(List<Integer> values) {
            addCriterion("TRADED_SUPPLIER_COUNT in", values, "tradedSupplierCount");
            return (Criteria) this;
        }

        public Criteria andTradedSupplierCountNotIn(List<Integer> values) {
            addCriterion("TRADED_SUPPLIER_COUNT not in", values, "tradedSupplierCount");
            return (Criteria) this;
        }

        public Criteria andTradedSupplierCountBetween(Integer value1, Integer value2) {
            addCriterion("TRADED_SUPPLIER_COUNT between", value1, value2, "tradedSupplierCount");
            return (Criteria) this;
        }

        public Criteria andTradedSupplierCountNotBetween(Integer value1, Integer value2) {
            addCriterion("TRADED_SUPPLIER_COUNT not between", value1, value2, "tradedSupplierCount");
            return (Criteria) this;
        }

        public Criteria andTransportFeesIsNull() {
            addCriterion("TRANSPORT_FEES is null");
            return (Criteria) this;
        }

        public Criteria andTransportFeesIsNotNull() {
            addCriterion("TRANSPORT_FEES is not null");
            return (Criteria) this;
        }

        public Criteria andTransportFeesEqualTo(BigDecimal value) {
            addCriterion("TRANSPORT_FEES =", value, "transportFees");
            return (Criteria) this;
        }

        public Criteria andTransportFeesNotEqualTo(BigDecimal value) {
            addCriterion("TRANSPORT_FEES <>", value, "transportFees");
            return (Criteria) this;
        }

        public Criteria andTransportFeesGreaterThan(BigDecimal value) {
            addCriterion("TRANSPORT_FEES >", value, "transportFees");
            return (Criteria) this;
        }

        public Criteria andTransportFeesGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANSPORT_FEES >=", value, "transportFees");
            return (Criteria) this;
        }

        public Criteria andTransportFeesLessThan(BigDecimal value) {
            addCriterion("TRANSPORT_FEES <", value, "transportFees");
            return (Criteria) this;
        }

        public Criteria andTransportFeesLessThanOrEqualTo(BigDecimal value) {
            addCriterion("TRANSPORT_FEES <=", value, "transportFees");
            return (Criteria) this;
        }

        public Criteria andTransportFeesIn(List<BigDecimal> values) {
            addCriterion("TRANSPORT_FEES in", values, "transportFees");
            return (Criteria) this;
        }

        public Criteria andTransportFeesNotIn(List<BigDecimal> values) {
            addCriterion("TRANSPORT_FEES not in", values, "transportFees");
            return (Criteria) this;
        }

        public Criteria andTransportFeesBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANSPORT_FEES between", value1, value2, "transportFees");
            return (Criteria) this;
        }

        public Criteria andTransportFeesNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("TRANSPORT_FEES not between", value1, value2, "transportFees");
            return (Criteria) this;
        }

        public Criteria andDemandUnitIsNull() {
            addCriterion("DEMAND_UNIT is null");
            return (Criteria) this;
        }

        public Criteria andDemandUnitIsNotNull() {
            addCriterion("DEMAND_UNIT is not null");
            return (Criteria) this;
        }

        public Criteria andDemandUnitEqualTo(String value) {
            addCriterion("DEMAND_UNIT =", value, "demandUnit");
            return (Criteria) this;
        }

        public Criteria andDemandUnitNotEqualTo(String value) {
            addCriterion("DEMAND_UNIT <>", value, "demandUnit");
            return (Criteria) this;
        }

        public Criteria andDemandUnitGreaterThan(String value) {
            addCriterion("DEMAND_UNIT >", value, "demandUnit");
            return (Criteria) this;
        }

        public Criteria andDemandUnitGreaterThanOrEqualTo(String value) {
            addCriterion("DEMAND_UNIT >=", value, "demandUnit");
            return (Criteria) this;
        }

        public Criteria andDemandUnitLessThan(String value) {
            addCriterion("DEMAND_UNIT <", value, "demandUnit");
            return (Criteria) this;
        }

        public Criteria andDemandUnitLessThanOrEqualTo(String value) {
            addCriterion("DEMAND_UNIT <=", value, "demandUnit");
            return (Criteria) this;
        }

        public Criteria andDemandUnitLike(String value) {
            addCriterion("DEMAND_UNIT like", value, "demandUnit");
            return (Criteria) this;
        }

        public Criteria andDemandUnitNotLike(String value) {
            addCriterion("DEMAND_UNIT not like", value, "demandUnit");
            return (Criteria) this;
        }

        public Criteria andDemandUnitIn(List<String> values) {
            addCriterion("DEMAND_UNIT in", values, "demandUnit");
            return (Criteria) this;
        }

        public Criteria andDemandUnitNotIn(List<String> values) {
            addCriterion("DEMAND_UNIT not in", values, "demandUnit");
            return (Criteria) this;
        }

        public Criteria andDemandUnitBetween(String value1, String value2) {
            addCriterion("DEMAND_UNIT between", value1, value2, "demandUnit");
            return (Criteria) this;
        }

        public Criteria andDemandUnitNotBetween(String value1, String value2) {
            addCriterion("DEMAND_UNIT not between", value1, value2, "demandUnit");
            return (Criteria) this;
        }

        public Criteria andContactNameIsNull() {
            addCriterion("CONTACT_NAME is null");
            return (Criteria) this;
        }

        public Criteria andContactNameIsNotNull() {
            addCriterion("CONTACT_NAME is not null");
            return (Criteria) this;
        }

        public Criteria andContactNameEqualTo(String value) {
            addCriterion("CONTACT_NAME =", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameNotEqualTo(String value) {
            addCriterion("CONTACT_NAME <>", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameGreaterThan(String value) {
            addCriterion("CONTACT_NAME >", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameGreaterThanOrEqualTo(String value) {
            addCriterion("CONTACT_NAME >=", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameLessThan(String value) {
            addCriterion("CONTACT_NAME <", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameLessThanOrEqualTo(String value) {
            addCriterion("CONTACT_NAME <=", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameLike(String value) {
            addCriterion("CONTACT_NAME like", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameNotLike(String value) {
            addCriterion("CONTACT_NAME not like", value, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameIn(List<String> values) {
            addCriterion("CONTACT_NAME in", values, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameNotIn(List<String> values) {
            addCriterion("CONTACT_NAME not in", values, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameBetween(String value1, String value2) {
            addCriterion("CONTACT_NAME between", value1, value2, "contactName");
            return (Criteria) this;
        }

        public Criteria andContactNameNotBetween(String value1, String value2) {
            addCriterion("CONTACT_NAME not between", value1, value2, "contactName");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNull() {
            addCriterion("START_TIME is null");
            return (Criteria) this;
        }

        public Criteria andStartTimeIsNotNull() {
            addCriterion("START_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andStartTimeEqualTo(Date value) {
            addCriterion("START_TIME =", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotEqualTo(Date value) {
            addCriterion("START_TIME <>", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThan(Date value) {
            addCriterion("START_TIME >", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("START_TIME >=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThan(Date value) {
            addCriterion("START_TIME <", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeLessThanOrEqualTo(Date value) {
            addCriterion("START_TIME <=", value, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeIn(List<Date> values) {
            addCriterion("START_TIME in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotIn(List<Date> values) {
            addCriterion("START_TIME not in", values, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeBetween(Date value1, Date value2) {
            addCriterion("START_TIME between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andStartTimeNotBetween(Date value1, Date value2) {
            addCriterion("START_TIME not between", value1, value2, "startTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNull() {
            addCriterion("END_TIME is null");
            return (Criteria) this;
        }

        public Criteria andEndTimeIsNotNull() {
            addCriterion("END_TIME is not null");
            return (Criteria) this;
        }

        public Criteria andEndTimeEqualTo(Date value) {
            addCriterion("END_TIME =", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotEqualTo(Date value) {
            addCriterion("END_TIME <>", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThan(Date value) {
            addCriterion("END_TIME >", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("END_TIME >=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThan(Date value) {
            addCriterion("END_TIME <", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeLessThanOrEqualTo(Date value) {
            addCriterion("END_TIME <=", value, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeIn(List<Date> values) {
            addCriterion("END_TIME in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotIn(List<Date> values) {
            addCriterion("END_TIME not in", values, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeBetween(Date value1, Date value2) {
            addCriterion("END_TIME between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andEndTimeNotBetween(Date value1, Date value2) {
            addCriterion("END_TIME not between", value1, value2, "endTime");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("CONTENT	 is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("CONTENT	 is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("CONTENT	 =", value, "content	");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("CONTENT	 <>", value, "content	");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("CONTENT	 >", value, "content	");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("CONTENT	 >=", value, "content	");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("CONTENT	 <", value, "content	");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("CONTENT	 <=", value, "content	");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("CONTENT	 like", value, "content	");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("CONTENT	 not like", value, "content	");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("CONTENT	 in", values, "content	");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("CONTENT	 not in", values, "content	");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("CONTENT	 between", value1, value2, "content	");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("CONTENT	 not between", value1, value2, "content	");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdIsNull() {
            addCriterion("ATTACHMENT_ID is null");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdIsNotNull() {
            addCriterion("ATTACHMENT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdEqualTo(String value) {
            addCriterion("ATTACHMENT_ID =", value, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdNotEqualTo(String value) {
            addCriterion("ATTACHMENT_ID <>", value, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdGreaterThan(String value) {
            addCriterion("ATTACHMENT_ID >", value, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdGreaterThanOrEqualTo(String value) {
            addCriterion("ATTACHMENT_ID >=", value, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdLessThan(String value) {
            addCriterion("ATTACHMENT_ID <", value, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdLessThanOrEqualTo(String value) {
            addCriterion("ATTACHMENT_ID <=", value, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdLike(String value) {
            addCriterion("ATTACHMENT_ID like", value, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdNotLike(String value) {
            addCriterion("ATTACHMENT_ID not like", value, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdIn(List<String> values) {
            addCriterion("ATTACHMENT_ID in", values, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdNotIn(List<String> values) {
            addCriterion("ATTACHMENT_ID not in", values, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdBetween(String value1, String value2) {
            addCriterion("ATTACHMENT_ID between", value1, value2, "attachmentId");
            return (Criteria) this;
        }

        public Criteria andAttachmentIdNotBetween(String value1, String value2) {
            addCriterion("ATTACHMENT_ID not between", value1, value2, "attachmentId");
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

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("STATUS not between", value1, value2, "status");
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

        public Criteria andFormIdIsNull() {
            addCriterion("FORM_ID is null");
            return (Criteria) this;
        }

        public Criteria andFormIdIsNotNull() {
            addCriterion("FORM_ID is not null");
            return (Criteria) this;
        }

        public Criteria andFormIdEqualTo(String value) {
            addCriterion("FORM_ID =", value, "formId");
            return (Criteria) this;
        }

        public Criteria andFormIdNotEqualTo(String value) {
            addCriterion("FORM_ID <>", value, "formId");
            return (Criteria) this;
        }

        public Criteria andFormIdGreaterThan(String value) {
            addCriterion("FORM_ID >", value, "formId");
            return (Criteria) this;
        }

        public Criteria andFormIdGreaterThanOrEqualTo(String value) {
            addCriterion("FORM_ID >=", value, "formId");
            return (Criteria) this;
        }

        public Criteria andFormIdLessThan(String value) {
            addCriterion("FORM_ID <", value, "formId");
            return (Criteria) this;
        }

        public Criteria andFormIdLessThanOrEqualTo(String value) {
            addCriterion("FORM_ID <=", value, "formId");
            return (Criteria) this;
        }

        public Criteria andFormIdLike(String value) {
            addCriterion("FORM_ID like", value, "formId");
            return (Criteria) this;
        }

        public Criteria andFormIdNotLike(String value) {
            addCriterion("FORM_ID not like", value, "formId");
            return (Criteria) this;
        }

        public Criteria andFormIdIn(List<String> values) {
            addCriterion("FORM_ID in", values, "formId");
            return (Criteria) this;
        }

        public Criteria andFormIdNotIn(List<String> values) {
            addCriterion("FORM_ID not in", values, "formId");
            return (Criteria) this;
        }

        public Criteria andFormIdBetween(String value1, String value2) {
            addCriterion("FORM_ID between", value1, value2, "formId");
            return (Criteria) this;
        }

        public Criteria andFormIdNotBetween(String value1, String value2) {
            addCriterion("FORM_ID not between", value1, value2, "formId");
            return (Criteria) this;
        }

        public Criteria andTurnoverRationIsNull() {
            addCriterion("TURNOVER_RATION is null");
            return (Criteria) this;
        }

        public Criteria andTurnoverRationIsNotNull() {
            addCriterion("TURNOVER_RATION is not null");
            return (Criteria) this;
        }

        public Criteria andTurnoverRationEqualTo(Integer value) {
            addCriterion("TURNOVER_RATION =", value, "turnoverRation");
            return (Criteria) this;
        }

        public Criteria andTurnoverRationNotEqualTo(Integer value) {
            addCriterion("TURNOVER_RATION <>", value, "turnoverRation");
            return (Criteria) this;
        }

        public Criteria andTurnoverRationGreaterThan(Integer value) {
            addCriterion("TURNOVER_RATION >", value, "turnoverRation");
            return (Criteria) this;
        }

        public Criteria andTurnoverRationGreaterThanOrEqualTo(Integer value) {
            addCriterion("TURNOVER_RATION >=", value, "turnoverRation");
            return (Criteria) this;
        }

        public Criteria andTurnoverRationLessThan(Integer value) {
            addCriterion("TURNOVER_RATION <", value, "turnoverRation");
            return (Criteria) this;
        }

        public Criteria andTurnoverRationLessThanOrEqualTo(Integer value) {
            addCriterion("TURNOVER_RATION <=", value, "turnoverRation");
            return (Criteria) this;
        }

        public Criteria andTurnoverRationIn(List<Integer> values) {
            addCriterion("TURNOVER_RATION in", values, "turnoverRation");
            return (Criteria) this;
        }

        public Criteria andTurnoverRationNotIn(List<Integer> values) {
            addCriterion("TURNOVER_RATION not in", values, "turnoverRation");
            return (Criteria) this;
        }

        public Criteria andTurnoverRationBetween(Integer value1, Integer value2) {
            addCriterion("TURNOVER_RATION between", value1, value2, "turnoverRation");
            return (Criteria) this;
        }

        public Criteria andTurnoverRationNotBetween(Integer value1, Integer value2) {
            addCriterion("TURNOVER_RATION not between", value1, value2, "turnoverRation");
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
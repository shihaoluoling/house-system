package com.example.user.center.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HouseTypeExample {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table house_type
     *
     * @mbg.generated Mon Jan 04 16:20:27 CST 2021
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table house_type
     *
     * @mbg.generated Mon Jan 04 16:20:27 CST 2021
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table house_type
     *
     * @mbg.generated Mon Jan 04 16:20:27 CST 2021
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Mon Jan 04 16:20:27 CST 2021
     */
    public HouseTypeExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Mon Jan 04 16:20:27 CST 2021
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Mon Jan 04 16:20:27 CST 2021
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Mon Jan 04 16:20:27 CST 2021
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Mon Jan 04 16:20:27 CST 2021
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Mon Jan 04 16:20:27 CST 2021
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Mon Jan 04 16:20:27 CST 2021
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Mon Jan 04 16:20:27 CST 2021
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Mon Jan 04 16:20:27 CST 2021
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Mon Jan 04 16:20:27 CST 2021
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table house_type
     *
     * @mbg.generated Mon Jan 04 16:20:27 CST 2021
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table house_type
     *
     * @mbg.generated Mon Jan 04 16:20:27 CST 2021
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
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andPremisesIdIsNull() {
            addCriterion("premises_id is null");
            return (Criteria) this;
        }

        public Criteria andPremisesIdIsNotNull() {
            addCriterion("premises_id is not null");
            return (Criteria) this;
        }

        public Criteria andPremisesIdEqualTo(Integer value) {
            addCriterion("premises_id =", value, "premisesId");
            return (Criteria) this;
        }

        public Criteria andPremisesIdNotEqualTo(Integer value) {
            addCriterion("premises_id <>", value, "premisesId");
            return (Criteria) this;
        }

        public Criteria andPremisesIdGreaterThan(Integer value) {
            addCriterion("premises_id >", value, "premisesId");
            return (Criteria) this;
        }

        public Criteria andPremisesIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("premises_id >=", value, "premisesId");
            return (Criteria) this;
        }

        public Criteria andPremisesIdLessThan(Integer value) {
            addCriterion("premises_id <", value, "premisesId");
            return (Criteria) this;
        }

        public Criteria andPremisesIdLessThanOrEqualTo(Integer value) {
            addCriterion("premises_id <=", value, "premisesId");
            return (Criteria) this;
        }

        public Criteria andPremisesIdIn(List<Integer> values) {
            addCriterion("premises_id in", values, "premisesId");
            return (Criteria) this;
        }

        public Criteria andPremisesIdNotIn(List<Integer> values) {
            addCriterion("premises_id not in", values, "premisesId");
            return (Criteria) this;
        }

        public Criteria andPremisesIdBetween(Integer value1, Integer value2) {
            addCriterion("premises_id between", value1, value2, "premisesId");
            return (Criteria) this;
        }

        public Criteria andPremisesIdNotBetween(Integer value1, Integer value2) {
            addCriterion("premises_id not between", value1, value2, "premisesId");
            return (Criteria) this;
        }

        public Criteria andHouseNameIsNull() {
            addCriterion("house_name is null");
            return (Criteria) this;
        }

        public Criteria andHouseNameIsNotNull() {
            addCriterion("house_name is not null");
            return (Criteria) this;
        }

        public Criteria andHouseNameEqualTo(String value) {
            addCriterion("house_name =", value, "houseName");
            return (Criteria) this;
        }

        public Criteria andHouseNameNotEqualTo(String value) {
            addCriterion("house_name <>", value, "houseName");
            return (Criteria) this;
        }

        public Criteria andHouseNameGreaterThan(String value) {
            addCriterion("house_name >", value, "houseName");
            return (Criteria) this;
        }

        public Criteria andHouseNameGreaterThanOrEqualTo(String value) {
            addCriterion("house_name >=", value, "houseName");
            return (Criteria) this;
        }

        public Criteria andHouseNameLessThan(String value) {
            addCriterion("house_name <", value, "houseName");
            return (Criteria) this;
        }

        public Criteria andHouseNameLessThanOrEqualTo(String value) {
            addCriterion("house_name <=", value, "houseName");
            return (Criteria) this;
        }

        public Criteria andHouseNameLike(String value) {
            addCriterion("house_name like", value, "houseName");
            return (Criteria) this;
        }

        public Criteria andHouseNameNotLike(String value) {
            addCriterion("house_name not like", value, "houseName");
            return (Criteria) this;
        }

        public Criteria andHouseNameIn(List<String> values) {
            addCriterion("house_name in", values, "houseName");
            return (Criteria) this;
        }

        public Criteria andHouseNameNotIn(List<String> values) {
            addCriterion("house_name not in", values, "houseName");
            return (Criteria) this;
        }

        public Criteria andHouseNameBetween(String value1, String value2) {
            addCriterion("house_name between", value1, value2, "houseName");
            return (Criteria) this;
        }

        public Criteria andHouseNameNotBetween(String value1, String value2) {
            addCriterion("house_name not between", value1, value2, "houseName");
            return (Criteria) this;
        }

        public Criteria andAreaIsNull() {
            addCriterion("area is null");
            return (Criteria) this;
        }

        public Criteria andAreaIsNotNull() {
            addCriterion("area is not null");
            return (Criteria) this;
        }

        public Criteria andAreaEqualTo(Double value) {
            addCriterion("area =", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotEqualTo(Double value) {
            addCriterion("area <>", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThan(Double value) {
            addCriterion("area >", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaGreaterThanOrEqualTo(Double value) {
            addCriterion("area >=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThan(Double value) {
            addCriterion("area <", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaLessThanOrEqualTo(Double value) {
            addCriterion("area <=", value, "area");
            return (Criteria) this;
        }

        public Criteria andAreaIn(List<Double> values) {
            addCriterion("area in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotIn(List<Double> values) {
            addCriterion("area not in", values, "area");
            return (Criteria) this;
        }

        public Criteria andAreaBetween(Double value1, Double value2) {
            addCriterion("area between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andAreaNotBetween(Double value1, Double value2) {
            addCriterion("area not between", value1, value2, "area");
            return (Criteria) this;
        }

        public Criteria andSupplyIsNull() {
            addCriterion("supply is null");
            return (Criteria) this;
        }

        public Criteria andSupplyIsNotNull() {
            addCriterion("supply is not null");
            return (Criteria) this;
        }

        public Criteria andSupplyEqualTo(Integer value) {
            addCriterion("supply =", value, "supply");
            return (Criteria) this;
        }

        public Criteria andSupplyNotEqualTo(Integer value) {
            addCriterion("supply <>", value, "supply");
            return (Criteria) this;
        }

        public Criteria andSupplyGreaterThan(Integer value) {
            addCriterion("supply >", value, "supply");
            return (Criteria) this;
        }

        public Criteria andSupplyGreaterThanOrEqualTo(Integer value) {
            addCriterion("supply >=", value, "supply");
            return (Criteria) this;
        }

        public Criteria andSupplyLessThan(Integer value) {
            addCriterion("supply <", value, "supply");
            return (Criteria) this;
        }

        public Criteria andSupplyLessThanOrEqualTo(Integer value) {
            addCriterion("supply <=", value, "supply");
            return (Criteria) this;
        }

        public Criteria andSupplyIn(List<Integer> values) {
            addCriterion("supply in", values, "supply");
            return (Criteria) this;
        }

        public Criteria andSupplyNotIn(List<Integer> values) {
            addCriterion("supply not in", values, "supply");
            return (Criteria) this;
        }

        public Criteria andSupplyBetween(Integer value1, Integer value2) {
            addCriterion("supply between", value1, value2, "supply");
            return (Criteria) this;
        }

        public Criteria andSupplyNotBetween(Integer value1, Integer value2) {
            addCriterion("supply not between", value1, value2, "supply");
            return (Criteria) this;
        }

        public Criteria andTransactionIsNull() {
            addCriterion("transaction is null");
            return (Criteria) this;
        }

        public Criteria andTransactionIsNotNull() {
            addCriterion("transaction is not null");
            return (Criteria) this;
        }

        public Criteria andTransactionEqualTo(Integer value) {
            addCriterion("transaction =", value, "transaction");
            return (Criteria) this;
        }

        public Criteria andTransactionNotEqualTo(Integer value) {
            addCriterion("transaction <>", value, "transaction");
            return (Criteria) this;
        }

        public Criteria andTransactionGreaterThan(Integer value) {
            addCriterion("transaction >", value, "transaction");
            return (Criteria) this;
        }

        public Criteria andTransactionGreaterThanOrEqualTo(Integer value) {
            addCriterion("transaction >=", value, "transaction");
            return (Criteria) this;
        }

        public Criteria andTransactionLessThan(Integer value) {
            addCriterion("transaction <", value, "transaction");
            return (Criteria) this;
        }

        public Criteria andTransactionLessThanOrEqualTo(Integer value) {
            addCriterion("transaction <=", value, "transaction");
            return (Criteria) this;
        }

        public Criteria andTransactionIn(List<Integer> values) {
            addCriterion("transaction in", values, "transaction");
            return (Criteria) this;
        }

        public Criteria andTransactionNotIn(List<Integer> values) {
            addCriterion("transaction not in", values, "transaction");
            return (Criteria) this;
        }

        public Criteria andTransactionBetween(Integer value1, Integer value2) {
            addCriterion("transaction between", value1, value2, "transaction");
            return (Criteria) this;
        }

        public Criteria andTransactionNotBetween(Integer value1, Integer value2) {
            addCriterion("transaction not between", value1, value2, "transaction");
            return (Criteria) this;
        }

        public Criteria andSouthWideIsNull() {
            addCriterion("south_wide is null");
            return (Criteria) this;
        }

        public Criteria andSouthWideIsNotNull() {
            addCriterion("south_wide is not null");
            return (Criteria) this;
        }

        public Criteria andSouthWideEqualTo(Double value) {
            addCriterion("south_wide =", value, "southWide");
            return (Criteria) this;
        }

        public Criteria andSouthWideNotEqualTo(Double value) {
            addCriterion("south_wide <>", value, "southWide");
            return (Criteria) this;
        }

        public Criteria andSouthWideGreaterThan(Double value) {
            addCriterion("south_wide >", value, "southWide");
            return (Criteria) this;
        }

        public Criteria andSouthWideGreaterThanOrEqualTo(Double value) {
            addCriterion("south_wide >=", value, "southWide");
            return (Criteria) this;
        }

        public Criteria andSouthWideLessThan(Double value) {
            addCriterion("south_wide <", value, "southWide");
            return (Criteria) this;
        }

        public Criteria andSouthWideLessThanOrEqualTo(Double value) {
            addCriterion("south_wide <=", value, "southWide");
            return (Criteria) this;
        }

        public Criteria andSouthWideIn(List<Double> values) {
            addCriterion("south_wide in", values, "southWide");
            return (Criteria) this;
        }

        public Criteria andSouthWideNotIn(List<Double> values) {
            addCriterion("south_wide not in", values, "southWide");
            return (Criteria) this;
        }

        public Criteria andSouthWideBetween(Double value1, Double value2) {
            addCriterion("south_wide between", value1, value2, "southWide");
            return (Criteria) this;
        }

        public Criteria andSouthWideNotBetween(Double value1, Double value2) {
            addCriterion("south_wide not between", value1, value2, "southWide");
            return (Criteria) this;
        }

        public Criteria andLivingWideIsNull() {
            addCriterion("living_wide is null");
            return (Criteria) this;
        }

        public Criteria andLivingWideIsNotNull() {
            addCriterion("living_wide is not null");
            return (Criteria) this;
        }

        public Criteria andLivingWideEqualTo(Double value) {
            addCriterion("living_wide =", value, "livingWide");
            return (Criteria) this;
        }

        public Criteria andLivingWideNotEqualTo(Double value) {
            addCriterion("living_wide <>", value, "livingWide");
            return (Criteria) this;
        }

        public Criteria andLivingWideGreaterThan(Double value) {
            addCriterion("living_wide >", value, "livingWide");
            return (Criteria) this;
        }

        public Criteria andLivingWideGreaterThanOrEqualTo(Double value) {
            addCriterion("living_wide >=", value, "livingWide");
            return (Criteria) this;
        }

        public Criteria andLivingWideLessThan(Double value) {
            addCriterion("living_wide <", value, "livingWide");
            return (Criteria) this;
        }

        public Criteria andLivingWideLessThanOrEqualTo(Double value) {
            addCriterion("living_wide <=", value, "livingWide");
            return (Criteria) this;
        }

        public Criteria andLivingWideIn(List<Double> values) {
            addCriterion("living_wide in", values, "livingWide");
            return (Criteria) this;
        }

        public Criteria andLivingWideNotIn(List<Double> values) {
            addCriterion("living_wide not in", values, "livingWide");
            return (Criteria) this;
        }

        public Criteria andLivingWideBetween(Double value1, Double value2) {
            addCriterion("living_wide between", value1, value2, "livingWide");
            return (Criteria) this;
        }

        public Criteria andLivingWideNotBetween(Double value1, Double value2) {
            addCriterion("living_wide not between", value1, value2, "livingWide");
            return (Criteria) this;
        }

        public Criteria andMasterWideIsNull() {
            addCriterion("master_wide is null");
            return (Criteria) this;
        }

        public Criteria andMasterWideIsNotNull() {
            addCriterion("master_wide is not null");
            return (Criteria) this;
        }

        public Criteria andMasterWideEqualTo(Double value) {
            addCriterion("master_wide =", value, "masterWide");
            return (Criteria) this;
        }

        public Criteria andMasterWideNotEqualTo(Double value) {
            addCriterion("master_wide <>", value, "masterWide");
            return (Criteria) this;
        }

        public Criteria andMasterWideGreaterThan(Double value) {
            addCriterion("master_wide >", value, "masterWide");
            return (Criteria) this;
        }

        public Criteria andMasterWideGreaterThanOrEqualTo(Double value) {
            addCriterion("master_wide >=", value, "masterWide");
            return (Criteria) this;
        }

        public Criteria andMasterWideLessThan(Double value) {
            addCriterion("master_wide <", value, "masterWide");
            return (Criteria) this;
        }

        public Criteria andMasterWideLessThanOrEqualTo(Double value) {
            addCriterion("master_wide <=", value, "masterWide");
            return (Criteria) this;
        }

        public Criteria andMasterWideIn(List<Double> values) {
            addCriterion("master_wide in", values, "masterWide");
            return (Criteria) this;
        }

        public Criteria andMasterWideNotIn(List<Double> values) {
            addCriterion("master_wide not in", values, "masterWide");
            return (Criteria) this;
        }

        public Criteria andMasterWideBetween(Double value1, Double value2) {
            addCriterion("master_wide between", value1, value2, "masterWide");
            return (Criteria) this;
        }

        public Criteria andMasterWideNotBetween(Double value1, Double value2) {
            addCriterion("master_wide not between", value1, value2, "masterWide");
            return (Criteria) this;
        }

        public Criteria andGuestWideIsNull() {
            addCriterion("guest_wide is null");
            return (Criteria) this;
        }

        public Criteria andGuestWideIsNotNull() {
            addCriterion("guest_wide is not null");
            return (Criteria) this;
        }

        public Criteria andGuestWideEqualTo(Double value) {
            addCriterion("guest_wide =", value, "guestWide");
            return (Criteria) this;
        }

        public Criteria andGuestWideNotEqualTo(Double value) {
            addCriterion("guest_wide <>", value, "guestWide");
            return (Criteria) this;
        }

        public Criteria andGuestWideGreaterThan(Double value) {
            addCriterion("guest_wide >", value, "guestWide");
            return (Criteria) this;
        }

        public Criteria andGuestWideGreaterThanOrEqualTo(Double value) {
            addCriterion("guest_wide >=", value, "guestWide");
            return (Criteria) this;
        }

        public Criteria andGuestWideLessThan(Double value) {
            addCriterion("guest_wide <", value, "guestWide");
            return (Criteria) this;
        }

        public Criteria andGuestWideLessThanOrEqualTo(Double value) {
            addCriterion("guest_wide <=", value, "guestWide");
            return (Criteria) this;
        }

        public Criteria andGuestWideIn(List<Double> values) {
            addCriterion("guest_wide in", values, "guestWide");
            return (Criteria) this;
        }

        public Criteria andGuestWideNotIn(List<Double> values) {
            addCriterion("guest_wide not in", values, "guestWide");
            return (Criteria) this;
        }

        public Criteria andGuestWideBetween(Double value1, Double value2) {
            addCriterion("guest_wide between", value1, value2, "guestWide");
            return (Criteria) this;
        }

        public Criteria andGuestWideNotBetween(Double value1, Double value2) {
            addCriterion("guest_wide not between", value1, value2, "guestWide");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNull() {
            addCriterion("create_date is null");
            return (Criteria) this;
        }

        public Criteria andCreateDateIsNotNull() {
            addCriterion("create_date is not null");
            return (Criteria) this;
        }

        public Criteria andCreateDateEqualTo(LocalDateTime value) {
            addCriterion("create_date =", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotEqualTo(LocalDateTime value) {
            addCriterion("create_date <>", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThan(LocalDateTime value) {
            addCriterion("create_date >", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_date >=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThan(LocalDateTime value) {
            addCriterion("create_date <", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_date <=", value, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateIn(List<LocalDateTime> values) {
            addCriterion("create_date in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotIn(List<LocalDateTime> values) {
            addCriterion("create_date not in", values, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_date between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andCreateDateNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_date not between", value1, value2, "createDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateIsNull() {
            addCriterion("modify_date is null");
            return (Criteria) this;
        }

        public Criteria andModifyDateIsNotNull() {
            addCriterion("modify_date is not null");
            return (Criteria) this;
        }

        public Criteria andModifyDateEqualTo(LocalDateTime value) {
            addCriterion("modify_date =", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotEqualTo(LocalDateTime value) {
            addCriterion("modify_date <>", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateGreaterThan(LocalDateTime value) {
            addCriterion("modify_date >", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("modify_date >=", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateLessThan(LocalDateTime value) {
            addCriterion("modify_date <", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("modify_date <=", value, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateIn(List<LocalDateTime> values) {
            addCriterion("modify_date in", values, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotIn(List<LocalDateTime> values) {
            addCriterion("modify_date not in", values, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("modify_date between", value1, value2, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andModifyDateNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("modify_date not between", value1, value2, "modifyDate");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNull() {
            addCriterion("is_deleted is null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIsNotNull() {
            addCriterion("is_deleted is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeletedEqualTo(Byte value) {
            addCriterion("is_deleted =", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotEqualTo(Byte value) {
            addCriterion("is_deleted <>", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThan(Byte value) {
            addCriterion("is_deleted >", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedGreaterThanOrEqualTo(Byte value) {
            addCriterion("is_deleted >=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThan(Byte value) {
            addCriterion("is_deleted <", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedLessThanOrEqualTo(Byte value) {
            addCriterion("is_deleted <=", value, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedIn(List<Byte> values) {
            addCriterion("is_deleted in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotIn(List<Byte> values) {
            addCriterion("is_deleted not in", values, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedBetween(Byte value1, Byte value2) {
            addCriterion("is_deleted between", value1, value2, "isDeleted");
            return (Criteria) this;
        }

        public Criteria andIsDeletedNotBetween(Byte value1, Byte value2) {
            addCriterion("is_deleted not between", value1, value2, "isDeleted");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table house_type
     *
     * @mbg.generated do_not_delete_during_merge Mon Jan 04 16:20:27 CST 2021
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table house_type
     *
     * @mbg.generated Mon Jan 04 16:20:27 CST 2021
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
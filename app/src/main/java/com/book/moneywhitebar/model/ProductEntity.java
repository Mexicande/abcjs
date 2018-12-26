package com.book.moneywhitebar.model;

import java.io.Serializable;

/**
 *
 * @author apple
 * @date 2018/5/18
 *
 */

public class ProductEntity implements Serializable {

    /**
     * id : 142
     * sort : 196
     * name : 安信花
     * link : http://axh.wx273.com/Home/Login/invite?invite=dp3evus6utae6sum363
     * product_logo : http://or2eh71ll.bkt.clouddn.com/153499572682556.png?e=1534999327&token=Npg7Sanmf4z8uv3mvwwffjOvoCMYN8Ezm4T8pDrC:lkvsWLEfA35NajOvNqNApfMoqlc=
     * product_introduction : 无抵押贷款，易申请，额度高,放款快
     * interest_algorithm : 日利率
     * min_algorithm : 0.030%
     * max_algorithm : 0.030%
     * maximum_amount : 50000
     * minimum_amount : 100
     */
    private String fastest_time;
    private String id;
    private int sort;
    private String name;
    private String link;
    private String product_logo;
    private String product_introduction;
    private String interest_algorithm;
    private String min_algorithm;
    private String max_algorithm;
    private String maximum_amount;
    private String minimum_amount;

    public String getFastest_time() {
        return fastest_time;
    }

    public void setFastest_time(String fastest_time) {
        this.fastest_time = fastest_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getProduct_logo() {
        return product_logo;
    }

    public void setProduct_logo(String product_logo) {
        this.product_logo = product_logo;
    }

    public String getProduct_introduction() {
        return product_introduction;
    }

    public void setProduct_introduction(String product_introduction) {
        this.product_introduction = product_introduction;
    }

    public String getInterest_algorithm() {
        return interest_algorithm;
    }

    public void setInterest_algorithm(String interest_algorithm) {
        this.interest_algorithm = interest_algorithm;
    }

    public String getMin_algorithm() {
        return min_algorithm;
    }

    public void setMin_algorithm(String min_algorithm) {
        this.min_algorithm = min_algorithm;
    }

    public String getMax_algorithm() {
        return max_algorithm;
    }

    public void setMax_algorithm(String max_algorithm) {
        this.max_algorithm = max_algorithm;
    }

    public String getMaximum_amount() {
        return maximum_amount;
    }

    public void setMaximum_amount(String maximum_amount) {
        this.maximum_amount = maximum_amount;
    }

    public String getMinimum_amount() {
        return minimum_amount;
    }

    public void setMinimum_amount(String minimum_amount) {
        this.minimum_amount = minimum_amount;
    }
}

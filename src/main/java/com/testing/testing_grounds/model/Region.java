package com.testing.testing_grounds.model;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "REGION")
public class Region {
    @Id
    private Integer id;

    public String getRegion_name() {
        return region_name;
    }

    public void setRegion_name(String region_name) {
        this.region_name = region_name;
    }

    @Column(name = "region_name")
    private String region_name;

    public Set<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(Set<Customer> customers) {
        this.customers = customers;
    }

    @OneToMany(mappedBy = "region",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Customer> customers;
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}

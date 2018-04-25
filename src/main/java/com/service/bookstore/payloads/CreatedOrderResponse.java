package com.service.bookstore.payloads;

/**
 * Created by nipon on 4/26/18.
 */
public class CreatedOrderResponse {
    private Double price;

    public CreatedOrderResponse(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
    }
}

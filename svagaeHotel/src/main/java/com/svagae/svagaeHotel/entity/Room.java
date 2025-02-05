package com.svagae.svagaeHotel.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name="rooms")

public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    private String roomDescription;
    private BigDecimal roomPrice;
    private String roomPhotoUrl;
    private String roomType;
    @OneToMany(mappedBy = "room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Booking> bookings = new ArrayList<>();

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomType='" + roomType + '\'' +
                ", roomPrice=" + roomPrice +
                ", roomDescription='" + roomDescription + '\'' +
                ", roomPhotoUrl='" + roomPhotoUrl + '\'' +
                '}';
    }


}

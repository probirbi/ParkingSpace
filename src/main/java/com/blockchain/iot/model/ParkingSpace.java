package com.blockchain.iot.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ParkingSpace {

    private String timeStamp;
    private int totalSpace;
    private int parkedSpace;
    private int freeSpace;
}

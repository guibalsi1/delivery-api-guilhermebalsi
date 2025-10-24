package com.deliverytech.delivery.dto.restaurant;

import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;


@Data
public class WorkHoursDTO {
    private LocalTime start;
    private LocalTime end;
}

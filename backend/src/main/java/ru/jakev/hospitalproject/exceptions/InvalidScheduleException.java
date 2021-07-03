package ru.jakev.hospitalproject.exceptions;

import ru.jakev.hospitalproject.dto.ScheduleDTO;

public class InvalidScheduleException extends Exception{

    private final ScheduleDTO scheduleDTO;

    public InvalidScheduleException(ScheduleDTO schedule) {
        super("Invalid schedule dto: " + schedule);
        this.scheduleDTO = schedule;
    }


    public ScheduleDTO getScheduleDTO() {
        return scheduleDTO;
    }
}

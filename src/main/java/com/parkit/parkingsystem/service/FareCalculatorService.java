package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.constants.Time;
import com.parkit.parkingsystem.model.Ticket;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static com.parkit.parkingsystem.constants.Time.THIRTY_MINUTES;


public class FareCalculatorService {

    public void calculateFare(Ticket ticket) {
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime()
                    .toString());
        }

        Instant inHour = ticket.getInTime().toInstant();
        Instant outHour = ticket.getOutTime().toInstant();

        double duration = (double) Duration.between(inHour, outHour).truncatedTo(ChronoUnit.MINUTES)
                .toMinutes() / 60;
        //System.out.println("Duration : " + duration);

          if(duration <= THIRTY_MINUTES) {
              ticket.setPrice(0);

          }   else {
              switch (ticket.getParkingSpot().getParkingType()) {
                  case CAR: {
                      ticket.setPrice(duration * Fare.CAR_RATE_PER_HOUR);
                      break;
                  }
                  case BIKE: {
                      ticket.setPrice(duration * Fare.BIKE_RATE_PER_HOUR);
                      break;
                  }
                  default:
                      throw new IllegalArgumentException("Unknown Parking Type");
              }
        }
    }
}
package com.parkit.parkingsystem.service;

import com.parkit.parkingsystem.constants.Fare;
import com.parkit.parkingsystem.model.Ticket;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;



public class FareCalculatorService {

    public static final double THIRTY_MINUTES = 0.5;

    public void calculateFare(Ticket ticket, boolean isDiscount) {             // + rajout d'un paramètre à false pour les tests déjà existants ?
        if ((ticket.getOutTime() == null) || (ticket.getOutTime().before(ticket.getInTime()))) {
            throw new IllegalArgumentException("Out time provided is incorrect:" + ticket.getOutTime()
                    .toString());
        }

        Instant inHour = ticket.getInTime().toInstant();
        Instant outHour = ticket.getOutTime().toInstant();

        double duration = (double) Duration.between(inHour, outHour).truncatedTo(ChronoUnit.MINUTES)
                .toMinutes() / 60;

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
              } if (isDiscount)  {
                   ticket.setPrice(ticket.getPrice()*0.95);
              }
        }
    }


  public void calculateFare(Ticket ticket) {
           calculateFare(ticket, false);

  }
}
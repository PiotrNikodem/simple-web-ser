package com.wrox.DAO;

import com.wrox.model.Ticket;

import java.util.LinkedHashMap;
import java.util.Map;

public class TicketDatabase {

    private Map<Integer, Ticket> ticketMap;

    public TicketDatabase() {
        if(ticketMap==null) {
            ticketMap = new LinkedHashMap<>();
        }
    }

    public Map<Integer, Ticket> getTicketMap() {
        return ticketMap;
    }

}

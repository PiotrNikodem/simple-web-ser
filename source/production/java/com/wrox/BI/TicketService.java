package com.wrox.BI;

import com.wrox.DAO.TicketDatabase;
import com.wrox.model.Attachment;
import com.wrox.model.Ticket;
import javax.servlet.http.Part;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TicketService {


    private TicketDatabase ticketDatabase;
    private AtomicInteger TICKET_ID_SEQUENCE = new AtomicInteger(0);

   public TicketService(){
        setTicketDatabase();
    }


    public Attachment downloadAttachment(String ticketId, String attachmentName)
            throws IOException {
        if (attachmentName == null)
            return null;

        Ticket ticket = getTicket(ticketId);

        if (ticket == null)
            return null;

        Attachment attachment = ticket.getAttachment(attachmentName);
        if (attachment == null) {
            return null;
        }

        return attachment;

    }


    public Integer createTicket(String username, String subject, String body, Part filePart)
            throws IOException {

        if (subject == null || body == null || subject.length() == 0 || body.length() == 0)
            return null;

        Ticket ticket = new Ticket();
        ticket.setCustomerName(username);
        ticket.setSubject(subject);
        ticket.setBody(body);

        if (filePart != null && filePart.getSize() > 0) {
            Attachment attachment = this.processAttachment(filePart);
            if (attachment != null)
                ticket.addAttachment(attachment);
        }

        int id = this.TICKET_ID_SEQUENCE.incrementAndGet();
        this.ticketDatabase.getTicketMap().put(id, ticket);

        return id;
    }

    public Attachment processAttachment(Part filePart) throws IOException {

        InputStream inputStream = filePart.getInputStream();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        int read;
        final byte[] bytes = new byte[1024];

        while ((read = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, read);
        }

        Attachment attachment = new Attachment();
        attachment.setName(filePart.getSubmittedFileName());
        attachment.setContents(outputStream.toByteArray());

        return attachment;
    }

    public Ticket getTicket(String idString) throws IOException {
        if (idString == null || idString.length() == 0) {
            return null;
        }
        Ticket ticket = this.ticketDatabase.getTicketMap().get(Integer.parseInt(idString));
        if (ticket == null) {
            return null;
        }
        return ticket;

    }

    public Map<Integer, Ticket> getListOfTickets() {
        return this.ticketDatabase.getTicketMap();
    }

    private void setTicketDatabase() {
        this.ticketDatabase = new TicketDatabase();
    }
}

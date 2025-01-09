package com.panda0day.kbffa.tickets;

import com.panda0day.kbffa.Main;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Tickets {
    private final UUID playerUid;
    private int tickets;

    public Tickets(UUID playerUid) {
        this.playerUid = playerUid;
        this.tickets = -1;

        try {
            ResultSet resultSet = Main.getDatabase().executeQuery("SELECT * FROM tickets WHERE uid=?", playerUid.toString());
            while (resultSet.next()) {
                this.tickets = resultSet.getInt("tickets");
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void addTicket() {
        this.tickets += 1;
    }

    public void removeTicket() {
        this.tickets -= 1;
    }

    public void update() {
        Main.getDatabase().executeQuery("UPDATE tickets SET tickets=? WHERE uid=?", tickets, playerUid.toString());
    }

    public int getTickets() {
        return this.tickets;
    }
}

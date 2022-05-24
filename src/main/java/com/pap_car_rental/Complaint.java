package com.pap_car_rental;

import java.sql.Date;

public class Complaint {
    public final int id;
    public final int ClientId;
    public final String Text;
    public final boolean Resolved;

    Complaint(int id,int ClientId, String Text, boolean Resolved) {
        this.id = id;
        this.ClientId = ClientId;
        this.Text = Text;
        this.Resolved = Resolved;
    }
}

package entities.transfers;

import entities.accounts.Base_Account;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Base_Transfer {
    protected Integer amount;
    protected Base_Account source;
    protected Base_Account destination;
    protected float commission;
    protected LocalDateTime time;
    public Base_Transfer(){
        amount =0;
        source =null;
        destination =null;
        commission =0f;
        time =LocalDateTime.now();
    }
    public Base_Transfer(Integer amount, Base_Account source,Base_Account destination,float commission,LocalDateTime time){
        this.amount =amount;
        this.source =source;
        this.destination =destination;
        this.commission =commission;
        this.time =time;
    }
    @Override
    public String toString(){
        return amount.toString()+" from "+source.getIBAN()+" to "+destination.getIBAN()+" at "+time.truncatedTo(ChronoUnit.MINUTES);
    }
    public Base_Account getSource(){
        return this.source;
    }
    public LocalDateTime getTime(){return time;}
    public void setTime(LocalDateTime time){this.time=time;}
}


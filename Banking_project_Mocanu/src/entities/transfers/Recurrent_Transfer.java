package entities.transfers;

import entities.accounts.Base_Account;

import java.time.LocalDateTime;
import java.time.Period;

public class Recurrent_Transfer extends Base_Transfer{
    private Period time_to_recur;
    public Recurrent_Transfer(Integer amount, Base_Account source, Base_Account destination, float commission, LocalDateTime time,Period time_to_recur){
        super(amount, source, destination, commission, time);
        this.time_to_recur=time_to_recur;
    }
    public  Period getTime_to_recur() {
        return this.time_to_recur;
    }
    public Base_Transfer generateNewTransfer(){
        return new Base_Transfer(this.amount, this.source, this.destination, this.commission, LocalDateTime.now());
    }

    @Override
    public String toString() {
        return super.toString()+" happens every: "+time_to_recur;
    }

}

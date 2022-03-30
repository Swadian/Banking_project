package entities.accounts;

import entities.cards.Credit_Card;

import java.lang.Math;
public class Credit_Account extends Base_Account{
    private Integer credit=0;
    private float interest;
    public Credit_Account(String IBAN,int balance,float interest){
        super(IBAN,balance);
        this.interest=interest;
    }
    @Override
    public int AddFunds(int amount)
    {
        this.Balance+=amount;
        if(this.Balance < 0)
        {
            this.credit+=this.Balance;
            this.Balance=0;
        }
        return 1;
    }
    @Override
    public void Monthly_Operation(){
        if(this.credit<0)
        {
            this.credit+=Math.round(this.credit*this.interest/100);
        }
    }
    @Override
    public String toString(){
        return super.toString()+"\ndebt: "+credit;
    }

}

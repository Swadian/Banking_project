package entities.accounts;

public class Base_Account {
    protected String IBAN;
    protected Integer Balance;
    public Base_Account(){
        this.IBAN="RO000";
        this.Balance=0;
    }
    public Base_Account(String IBAN,int balance){
        this.IBAN=IBAN;
        this.Balance=balance;
    }
    public int AddFunds(int amount)
    {
        if(this.Balance+amount<0)
            return 0;
        else{
            this.Balance+=amount;
            return 1;
        }
    }
    public void Monthly_Operation(){}
    public Integer getBalance(){
        return this.Balance;
    }
    public String getIBAN() {
        return IBAN;
    }
    @Override
    public String toString(){
        return IBAN+": "+Balance;
    }
}

package entities.cards;

import entities.accounts.Base_Account;

public class Base_Card {
    private Base_Account Account;
    protected String holderName;
    protected String holderSurname;
    protected Integer CVV;
    protected String code;
    protected String PIN;
    public Base_Card(){
        this.code ="0";
        this.CVV=100;
        this.holderName ="Default";
        this.holderSurname ="McDefault Face";
        this.PIN="1234";
    }
    public Base_Card(String holderName,String holderSurname,Integer CVV,String code,String PIN)
    {
        this.holderName =holderName;
        this.holderSurname =holderSurname;
        this.CVV=CVV;
        this.code =code;
        this.PIN=PIN;
    }
    public Base_Card(String holderName,String holderSurname,Integer CVV,String code,String PIN,Base_Account Account)
    {
        this.holderName =holderName;
        this.holderSurname =holderSurname;
        this.CVV=CVV;
        this.code =code;
        this.PIN=PIN;
        this.Account=Account;
    }

    public String getCode() {
        return code;
    }
    public Boolean comparePIN(String pin)
    {
        return (pin.equals(this.PIN));
    }
    public Base_Account getAccount() {
        return Account;
    }
    @Override
    public String toString(){
    return "Owner: "+holderName+' '+holderSurname+" Balance: "+this.getAccount().getBalance();
    }
}

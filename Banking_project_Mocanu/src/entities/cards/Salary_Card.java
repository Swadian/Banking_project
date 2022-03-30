package entities.cards;


import entities.accounts.Salary_Account;

public class Salary_Card extends Base_Card{
    private Salary_Account account;
    public Salary_Card(){
        super();
        this.account = new Salary_Account();
    }
    public Salary_Card(String holderName,String holderSurname,Integer CVV,String code,String PIN,Salary_Account account){
        super(holderName, holderSurname, CVV, code, PIN);
        this.account =account;
    }

    @Override
    public Salary_Account getAccount() {
        return account;
    }
}

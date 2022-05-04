package entities.accounts;


public class Salary_Account extends Base_Account{
    private Integer Monthly_Income;
    public Salary_Account(){
    this.Monthly_Income=0;
    }
    public Salary_Account(String IBAN,int balance,int monthly){
        super(IBAN,balance);
        this.Monthly_Income=monthly;
    }
    public Integer getSalary(){return this.Monthly_Income;}
    @Override
    public void Monthly_Operation()
    {
        this.Balance+=Monthly_Income;
    }
}

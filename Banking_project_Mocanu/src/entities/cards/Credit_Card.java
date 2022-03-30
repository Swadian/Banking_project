package entities.cards;

import entities.accounts.Credit_Account;

public class Credit_Card extends Base_Card{
    private Credit_Account Account;
    public Credit_Card(String holder_Name,String holder_Surname,Integer CVV,String Code,String PIN,Credit_Account Account){
        super(holder_Name, holder_Surname, CVV, Code, PIN);
        this.Account=Account;
    }

    @Override
    public Credit_Account getAccount() {
        return Account;
    }
}

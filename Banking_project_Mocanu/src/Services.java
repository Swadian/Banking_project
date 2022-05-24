import entities.accounts.Base_Account;
import entities.accounts.Credit_Account;
import entities.accounts.Salary_Account;
import entities.cards.Base_Card;
import entities.cards.Credit_Card;
import entities.cards.Salary_Card;
import entities.transfers.Base_Transfer;
import entities.transfers.Recurrent_Transfer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Services {
    Scanner scanner = new Scanner(System.in);
    audit log=null;
    Services(){
//        storage_read storage = new storage_read();
        Database db = Database.getInstance();
        try {
            Accounts = (ArrayList<Base_Account>) db.getAccounts();
        // (ArrayList<Base_Account>) storage.read_Accounts("storage.csv");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("couldn't read");
        }
        log = audit.get_instance();
    }
    private ArrayList<Base_Account> Accounts= new ArrayList<>();
    private ArrayList<Base_Card> Cards= new ArrayList<>();
    private List<Base_Transfer> TransferLog = new ArrayList<>();
    private List<Recurrent_Transfer> Recurrents = new ArrayList<>();
    private Base_Card Login(String code){
        String pin;
        for (Base_Card card : Cards) {
            if (card.getCode().equals(code)) {
                while (true) {
                    System.out.println("PIN (type \"abort\" to abort):");
                    pin = scanner.next();
                    if(pin.equals("abort"))
                        return new Base_Card();
                    if (card.comparePIN(pin)) {
                        return card;
                    }
                }
            }
        }
        return new Base_Card();
    }

    public void Transfer(){
        System.out.println("Card code:");
        String sender_code = scanner.next();
        Base_Card sender = Login(sender_code);
        System.out.println("Amount:");
        int amount = scanner.nextInt();
        System.out.println("Code of receiving card:");
        String receiver_code = scanner.next();
        Base_Card receiver = new Base_Card();
        for(Base_Card card : Cards)
        {
            if (card.getCode().equals(receiver_code))
            {
                receiver=card;
                break;
            }
        }
        receiver.getAccount().AddFunds(amount);
        sender.getAccount().AddFunds((-1*amount));
        TransferLog.add(new Base_Transfer(amount,sender.getAccount(),receiver.getAccount(),0f, LocalDateTime.now()));
        log.add_to_log("transfer");
    }
    public void executeMonthlyOperations(){
        for(Base_Account account : Accounts)
                account.Monthly_Operation();
                log.add_to_log("monthly_ops");
    }
    public void Print_logs(){
        for(Object transfer : TransferLog){
            System.out.println(transfer);
        }
        log.add_to_log("print_logs");
    }

    public void addAccount(int type)//0=base,1=credit,2=salary
    {
        String IBAN;
        System.out.println("IBAN:");
        IBAN=scanner.next();
        if(type==0){
            Accounts.add(new Base_Account(IBAN,0));
        }
        else if(type==1){
            System.out.println("Interest:");
            float interest=scanner.nextFloat();
            Accounts.add(new Credit_Account(IBAN,0,interest));
        }
        else{
            System.out.println("Monthly Salary:");
            int monthly=scanner.nextInt();
            Accounts.add(new Salary_Account(IBAN,0,monthly));
        }
        log.add_to_log("add_acc");
    }
    public void addCard(int type){//0=base,1=credit,2=salary
        int cvv;
        String name,surname,code,pin;
        System.out.println("Name:");
        name=scanner.next();
        System.out.println("Surname:");
        surname=scanner.next();
        System.out.println("Card number:");
        code=scanner.next();
        System.out.println("PIN:");
        pin=scanner.next();
        System.out.println("CVV:");
        cvv=scanner.nextInt();
        System.out.println("Account (leave blank for new):");
        String acc_iban=scanner.next();
        Base_Account acc=null;
        for(Base_Account c : Accounts)
        {
            if(c.getIBAN().equals(acc_iban)){
                acc=c;
                break;
            }
        }
        if(type==0){
            if(acc==null){
                addAccount(0);
                acc=Accounts.get(Accounts.size()-1);
            }
            Cards.add(new Base_Card(name,surname,cvv,code,pin,acc));
        }
        else if(type==1) {
        if(acc==null){
            addAccount(1);
            acc=Accounts.get(Accounts.size()-1);
        }
            Cards.add(new Credit_Card(name,surname,cvv,code,pin,(Credit_Account)acc));
        }
        else{
            if(acc==null){
                addAccount(2);
                acc=Accounts.get(Accounts.size()-1);
            }
            Cards.add(new Salary_Card(name,surname,cvv,code,pin,(Salary_Account)acc));
        }
        log.add_to_log("add_card");
    }

    public void printCard(){
        System.out.println("Code:");
        String code=scanner.next();
        Base_Card toPrint=Login(code);
        System.out.println(toPrint);
        log.add_to_log("print_card");
    }
    public void closeAccount(){
        log.add_to_log("remove_account");
        System.out.println("IBAN: ");
        String IBAN=scanner.next();
        for(Base_Account toRemove :Accounts){
            if(toRemove.getIBAN().equals(IBAN)){
                Accounts.remove(toRemove);
                for(Base_Card card : Cards)
                    if(card.getAccount().equals(toRemove))
                    {
                        Cards.remove(card);
                    }
                Database.getInstance().deleteAccount(toRemove);
            }
        }
    }
    public void printAccountBalance(){
    log.add_to_log("print_acc_balance");
    System.out.println("IBAN: ");
    String IBAN=scanner.next();
    for(Base_Account toPrint :Accounts){
        if(toPrint.getIBAN().equals(IBAN)){
            System.out.println(toPrint);
            return;
        }
    }
    }
    public void store_accounts(){
        Database db = Database.getInstance();
        db.postAccounts(this.Accounts);
      //  storage_write.write_storage("storage.csv", this.Accounts);
    }

    public void addMoneyToAccount(){
        log.add_to_log("add_money_to_acc");
        System.out.println("IBAN: ");
        String IBAN=scanner.next();
        System.out.println("sum: ");
        Integer sum = scanner.nextInt();
        for(Base_Account toAdd :Accounts){
            if(toAdd.getIBAN().equals(IBAN)){
                toAdd.AddFunds(sum);
                Database.getInstance().updateAccountBalance(toAdd);
                return;
            }
        }

    }
    public void printRecurrencesOf(String IBAN){
        for(Recurrent_Transfer r : Recurrents) {
            if (r.getSource().getIBAN().equals(IBAN))
                System.out.println(r);
        }
        log.add_to_log("print_recc_of");
    }
    public void addRecurrentTransfer(){
        System.out.println("Card code:");
        String sender_code = scanner.next();
        Base_Card sender = Login(sender_code);
        System.out.println("Amount:");
        int amount = scanner.nextInt();
        System.out.println("Code of receiving card:");
        String receiver_code = scanner.next();
        Base_Card receiver = new Base_Card();
        for(Base_Card card : Cards)
        {
            if (card.getCode().equals(receiver_code))
            {
                receiver=card;
                break;
            }
        }
        receiver.getAccount().AddFunds(amount);
        sender.getAccount().AddFunds((-1*amount));
        int y,m,d;
        System.out.println("Recurrence:\nYears: ");
        y=scanner.nextInt();
        System.out.println("Months: ");
        m=scanner.nextInt();
        System.out.println("Days: ");
        d=scanner.nextInt();
        Period p = Period.parse(String.format("P%sY%sM%sD",y,m,d));
        Recurrents.add(new Recurrent_Transfer(amount,sender.getAccount(),receiver.getAccount(),0f, LocalDateTime.now(),p));
        log.add_to_log("add_recc");
    }
    public void checkRecurrences(){
        for(Recurrent_Transfer r : Recurrents){
            if(r.getTime().plus(r.getTime_to_recur()).compareTo(LocalDateTime.now())>0){
                r.generateNewTransfer();
                r.setTime(LocalDateTime.now());
            }
        }
        log.add_to_log("check_recc");
    }

}

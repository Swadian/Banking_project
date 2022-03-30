import java.util.Scanner;

public class main {


public static void main(String args[]){
    int command;
    Services s = new Services();
    String COMMS="\n0.Exit\n1.Add Account\n2.Add Card\n3.Add Transfer\n4.Print log of transfers" +
            "\n5.Manually execute monthly operations\n6.Print card\n7.Print account balance\n8.Print recurrent transfers of an account" +
            "\n9.Add new recurrent transfer\n10.Check list for due transfers";
    String TYPES="\nType ?\n0.Base\n1.Credit\n2.Salary";
    Scanner scanner = new Scanner(System.in);
    int type;
    while(true) {
        System.out.println(COMMS);
        command=scanner.nextInt();

        switch (command){
            case 0:
                return;
            case 1:
                System.out.println(TYPES);
                type=scanner.nextInt();
                s.addAccount(type);
                break;
            case 2:
                System.out.println(TYPES);
                type=scanner.nextInt();
                s.addCard(type);
                break;
            case 3:
                s.Transfer();
                break;
            case 4:
                s.Print_logs();
                break;
            case 5:
                s.executeMonthlyOperations();
                break;
            case 6:
                s.printCard();
                break;
            case 7:
                s.printAccountBalance();
                break;
            case 8:
                System.out.println("IBAN: ");
                String IBAN=scanner.next();
                s.printRecurrencesOf(IBAN);
            case 9:
                s.addRecurrentTransfer();
            case 10:
                s.checkRecurrences();
        }
    }

}
}

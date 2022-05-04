import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import entities.accounts.Base_Account;
import entities.accounts.Credit_Account;
import entities.accounts.Salary_Account;

public class storage_read{

    public List<Base_Account> read_Accounts(String filename)throws IOException{
    BufferedReader reader = new BufferedReader(new FileReader("Banking_project_Mocanu/src/resources/"+filename));
    String line = null;
    Scanner scanner = null;
    int index = 0;
    List<Base_Account> accList = new ArrayList<>();

    while ((line = reader.readLine()) != null) {
        scanner = new Scanner(line);
        scanner.useDelimiter(",");
        Base_Account acc=null;
        Integer balance=0;
        String IBAN="";
        String type="";
        String extra="";//can be 0 for base
        while (scanner.hasNext()) {
            String data = scanner.next();
            if (index == 0)
                IBAN=data;
            else if (index == 1)
                balance=Integer.decode(data);
            else if (index == 2)
                type=data;
                else if (index == 3)
                extra=data;
            else
                System.out.println("invalid data::" + data);
            index++;
        }
        if(type.equals("base"))
        {
            acc=new Base_Account(IBAN,balance);
        }
        else if(type.equals("credit"))
        {
            acc=new Credit_Account(IBAN, balance, Float.parseFloat(extra));

        }
        else{
            acc= new Salary_Account(IBAN, balance, Integer.decode(extra));
        }
        index = 0;
        accList.add(acc);
    }
    reader.close();     
    return accList;
}



}
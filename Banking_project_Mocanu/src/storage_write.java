import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.ListIterator;

import entities.accounts.Base_Account;
import entities.accounts.Credit_Account;
import entities.accounts.Salary_Account;

public class storage_write {
    public static void write_storage(String filename,List<Base_Account> source){
    try {
        File storage = new File("src/resources/"+filename);
        if(!storage.createNewFile()){storage.delete();storage.createNewFile();}
        ListIterator<Base_Account> it = source.listIterator();
        PrintWriter writer = new PrintWriter(storage);
        while(it.hasNext()){
            Base_Account current = it.next();
            if(current instanceof Credit_Account){
                Credit_Account acc = (Credit_Account)current;
                String out= acc.getIBAN()+','+acc.getBalance()+','+"credit,"+acc.getInterest()+'\n';
                writer.write(out);
            } 
            else if(current instanceof Salary_Account){
                Salary_Account acc = (Salary_Account)current;
                String out= acc.getIBAN()+','+acc.getBalance()+','+"salary,"+acc.getSalary()+'\n';
                writer.write(out);
            }
            else{
                String out = current.getIBAN()+','+current.getBalance()+','+"base,0\n";
                writer.write(out);
            }
        }
        writer.close();
    } catch (Exception e) {
        System.out.println("can't write");
    }
}
}

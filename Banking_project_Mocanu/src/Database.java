import entities.accounts.Base_Account;
import entities.accounts.Credit_Account;
import entities.accounts.Salary_Account;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static Database instance = null;
    public static Database getInstance(){
        if (instance!=null) return instance;
        else return new Database();
    }
    Connection con;
    private Database(){connect();}
    void connect()
    {
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pao_database", "root","root");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void finalize(){
        try{
        con.commit();
        con.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<Base_Account> getAccounts(){
        List<Base_Account> acclist = new ArrayList<>();
        String sql = "SELECT * from accounts";
        try {
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                if(rs.getString(3).equals("base"))
                    acclist.add(new Base_Account(rs.getString(1),rs.getInt(2)));
                else if(rs.getString(3).equals("credit"))
                    acclist.add(new Credit_Account(rs.getString(1),rs.getInt(2),Float.parseFloat(rs.getString(4))));
                    else acclist.add(new Salary_Account(rs.getString(1),rs.getInt(2),Integer.decode(rs.getString(4))));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return acclist;
    }
    public void postAccounts(List<Base_Account> list){
        StringBuilder sql =new StringBuilder("INSERT INTO accounts values ");
        List<Base_Account> previous = getAccounts();
        boolean repeating,hasToPost=false;
        //ugly, ugly workaround. Not proud of myself for this :(
        for(Base_Account acc : list) {
            repeating=false;
            for(Base_Account old:previous)
                if(old.getIBAN().equals(acc.getIBAN()))
                repeating=true;
            if(repeating)
                updateAccountBalance(acc);
            else
            {
                hasToPost=true;
        if(acc instanceof Credit_Account){
            sql.append("('"+acc.getIBAN()+"',"+acc.getBalance()+",'credit','"+((Credit_Account) acc).getInterest()+"'),");
        }
        else if (acc instanceof  Salary_Account){
            sql.append("('"+acc.getIBAN()+"',"+acc.getBalance()+",'salary','"+((Salary_Account) acc).getSalary()+"'),");
        }
        else sql.append("('"+acc.getIBAN()+"',"+acc.getBalance()+",'base','0'),");
        }
        }
        sql.deleteCharAt(sql.length()-1);
        sql.append(";");
        if(hasToPost)
        try {
            Statement stmt = con.prepareStatement(sql.toString());
            stmt.executeUpdate(sql.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void updateAccountBalance(Base_Account acc){
        String sql = "UPDATE accounts set balance= "+acc.getBalance()+" where IBAN='"+acc.getIBAN()+"';";
        try {
            Statement stmt = con.prepareStatement(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void deleteAccount(Base_Account acc){
        String sql = "DELETE from accounts where IBAN = '"+acc.getIBAN()+"' ;";
        try {
            Statement stmt = con.prepareStatement(sql);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


}

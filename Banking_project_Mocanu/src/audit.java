import java.io.File;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class audit {
    PrintWriter writer;
    private audit(String filename){
        try{
            File log = new File("src/resources/"+filename);
            if(!log.createNewFile()){
                log.delete();log.createNewFile();
            }
            writer = new PrintWriter(log);
            }
            catch(Exception e){
                System.out.println("couldn't audit");


            }
    }
    public void add_to_log(String operation){
        writer.write(operation+','+LocalDateTime.now()+'\n');
        writer.flush();
    }
    private static audit instance=null;
    public static audit get_instance(){
        if (instance!=null) return instance;
        else return new audit("audit_log.csv");
    }
    @Override
    protected void finalize(){
        writer.flush();
        writer.close();
    }
}

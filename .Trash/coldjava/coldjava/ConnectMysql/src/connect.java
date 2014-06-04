import java.sql.Connection;   
import java.sql.DriverManager;   
import java.sql.ResultSet;   
import java.sql.Statement;   
  
/**  
 * @author www.javaworkspace.com  
 *   
 */  
public class connect
{   
    public static void main(String[] args) 
    {   
        try 
        {   
        	int flag=0;
        	String database=args[0];
        	String tablename=args[1];
        	String idno=args[2];

            Class.forName("com.mysql.jdbc.Driver");   
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/"+database, "root", "keval");   
            Statement statement = connection.createStatement();   
            ResultSet resultSet = statement.executeQuery("SELECT EMPID, EMPNAME, AGE FROM "+tablename+" WHERE EMPID="+idno);  
      
            while (resultSet.next()) 
            {   
                System.out.println("EMPLOYEE ID:" + resultSet.getString("EMPID") + "   EMPLOYEE NAME:" + resultSet.getString("EMPNAME") + "  AGE:"+resultSet.getString("AGE"));
                flag=1;
            }
            if(flag==0)
            {
            	System.out.println("RECORD NOT FOUND");
            }
            //connection.createStatement().execute("CREATE TABLE people(id int(64) NOT NULL AUTO_INCREMENT,name varchar(255) NOT NULL,address varchar(255) NOT NULL,UNIQUE (id),FULLTEXT(name, address))");  
        } 
        catch (Exception e) 
        {   
        	System.out.println("Database or Table or EMPID are incorrect");
            //e.printStackTrace();   
        }   
    }   
}  
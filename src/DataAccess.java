
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class DataAccess {
	/**
	 * @param fileName
	 * @return countryMap
	 * 
	 */	
	public void excelRead(String filename){

		Connection c = null;
		Statement stmnt = null;		
		try
		{
			Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
			//using DSN-less connection
			c = DriverManager.getConnection( "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)}"
					+ ";DBQ="+ filename);

			stmnt = c.createStatement();
			String query = "select * from [Table$];";
			ResultSet rs = stmnt.executeQuery( query );
			while( rs.next() )
			{					
				for(int i=0;i<=rs.getMetaData().getColumnCount();i++){
					//do stuff here
				}					
			}						
		}
		catch( Exception e )
		{
			e.printStackTrace();
			System.err.println( "Exception in reading from file :  " + e.getStackTrace() );
		}
		finally
		{
			try
			{
				stmnt.close();
				c.close();
			}
			catch( Exception e )
			{
				System.err.println( "Error while closing the statement :" + e );
			}
		}			
	}
}
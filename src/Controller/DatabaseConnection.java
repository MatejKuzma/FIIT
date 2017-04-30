import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

//trieda pre zistovanie aktivnosti pripojenia na databazu
public class DatabaseConnection
{
	Connection connection;
	private boolean running = true;
	
	public DatabaseConnection(Connection connection)
	{
		this.connection = connection;
	}
	
	// otestuj pritomnost driveru, pripoj sa na databazu
	public Connection connectToDatabase(String DBname, String DBuser, String DBpassword)
	{
		System.out.println("Database connection process, please wait a moment ..."); // informacna sprava pre pouzivatela o prebiehajucej aktivite

		try // hladanie JDBC pluginu 
		{
			Class.forName("org.postgresql.Driver");
		} 
		catch (ClassNotFoundException e)
		{
			System.out.println("JDBC driver NOT found!");
			e.printStackTrace();
			return null;
		}
		System.out.println("JDBC driver found!");
	
		try 
		{
			connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/"+DBname, DBuser,DBpassword);
		} 
		catch (SQLException e) 
		{
			System.out.println("Connection Failed to DB: \""+DBname+"\" with user: \""+DBuser+"\" !");
			e.printStackTrace();
			return null;
		}

		if (connection != null) 
			System.out.println("Connection succesfull to DB: \""+DBname+"\" with user: \""+DBuser+"\" !");
		else 
			System.out.println("Connection Failed to DB: \""+DBname+"\" with user: \""+DBuser+"\" !");
		
		this.start(); // odpalenie samostatneho vlakna na kontrolu spojenia s databazou
		return connection;
	}
	
	// Kontrola prebieha na separatnom vlakne
	Thread connectionThread = new Thread()
	{
		public void run()
		{
			while(running)
			{
				try 
				{
					running = connection.isValid(5); // kontrola, èi server stále spojený s databázou
			       Thread.sleep(30000);
			       System.out.println("Connection with DB working! "); 
			     } 
				catch (InterruptedException e) 
				{
			       e.printStackTrace();
					running = false;
					System.out.println("Connection with DB stopped working!"); 
			    } 
				catch (SQLException e) 
			    {
					e.printStackTrace();
					running = false;
				}
			}
		}
	};
	
	// ukonci kontrolu pripojenia
	public void terminate()
	{
		running = false;
		System.out.println("Connection with DB shut!"); 
	}
	
	// zacni kontrolu pripojenia
	public void start()
	{
		connectionThread.start();
	}
}

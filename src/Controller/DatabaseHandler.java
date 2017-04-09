import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class DatabaseHandler 
{
	private Connection connection;
	
	public DatabaseHandler() 
	{
		connection = null;
	}

	// otestuj pritomnost driveru, pripoj sa na databazu
	public void connectToDatabase(String DBname, String DBuser, String DBpassword)
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
			return;
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
			return;
		}

		if (connection != null) 
			System.out.println("Connection succesfull to DB: \""+DBname+"\" with user: \""+DBuser+"\" !");
		else 
			System.out.println("Connection Failed to DB: \""+DBname+"\" with user: \""+DBuser+"\" !");
	}
	
	// funkcia na vybranie dat z tabulky player za urcitych parametrov.
	public DefaultTableModel getPlayerDataFromDB(int limit, int offset)
	{
		Vector<String> columnNames = new Vector();
		columnNames.add("Id");
		columnNames.add("Sex");
		columnNames.add("Nickname");
		columnNames.add("Revenue from dlc");
		
		String query = "SELECT pl.id ,pl.sex,pl.nickname,sum(d.price) FROM player pl  "
						 + "JOIN  	purchase pu ON 	pl.id 	= pu.player_id "
						 + "JOIN  	dlc d 		ON 	d.id 	= pu.dlc_id"
						 + " GROUP BY(pl.id) LIMIT "+limit + " OFFSET "+ offset;
		
		return queryAdministration(limit, offset, columnNames, query);
	}
	
	// funkcia na vybranie dat z tabulky character .
	public DefaultTableModel getCharacterDataFromDB(int limit, int offset)
	{
		Vector<String> columnNames = new Vector();
		columnNames.add("Character id");
		columnNames.add("Player id");
		columnNames.add("Character name");
		columnNames.add("Type");
		columnNames.add("Inception time");
		
		String query = "SELECT c.id,p.id,c.character_name,ct.name,c.create_time FROM player p"
				+ " JOIN character c ON p.id = c.player_id "
				+ " JOIN character_type ct ON ct.id = c.type LIMIT "+limit + " OFFSET "+ offset;
		
		return queryAdministration(limit, offset, columnNames, query);
	}
	
	// funkcia na vybranie dat z tabulky character .
	public DefaultTableModel getLevelDataFromDB(int limit, int offset)
	{
			Vector<String> columnNames = new Vector();
			columnNames.add("Id");
			columnNames.add("Belong to dlc ");
			columnNames.add("Level name");
			columnNames.add("Played hours total");
			columnNames.add("Average score");
			
			String query = " SELECT tmp.id,d.name, level_name,sum,round FROM "
						 + "(SELECT  l.id,l.dlc_id,l.level_name, SUM(ps.playhours), ROUND(AVG(ps.score),2) FROM level l "
						 + " JOIN player_statistic ps ON ps.level_id = l.id "
						 + " GROUP BY (l.id)) tmp JOIN dlc d ON d.id = tmp.dlc_id LIMIT "+limit + " OFFSET "+ offset;
			
			return queryAdministration(limit, offset, columnNames, query);
	}
	
	// MAZACIE QUERINY 
	
	public void cascadePlayerById(int playerId)
	{
		String inputQuery =" delete from purchase where player_id = "+playerId +";"
					+ " delete from player_statistic where player_id = "+playerId +";"
					+ " delete from achiavement_taking where player_id = "+playerId +";"
					+ " DELETE FROM character as char "
					+ " USING stock_taking as stockTak 	"
					+ " WHERE char.player_id = "+playerId +" AND stockTak.character_id = char.id;"
					+ " delete from player where id = "+playerId +";";
		
		
		Statement st;
		ResultSet rs;
		try
		{
			st = connection.createStatement();
			rs = st.executeQuery(inputQuery);
			rs.close();
			st.close();
		} 
		catch (SQLException e) 
		{
		}
		
	}
	
	public void cascadeCharacterById(int characterId)
	{
		String inputQuery =" DELETE FROM stock_taking WHERE character_id = " +characterId+";"
						   + " DELETE FROM character as char WHERE char.id = " +characterId+";";
		
		Statement st;
		ResultSet rs;
		try
		{
			st = connection.createStatement();
			rs = st.executeQuery(inputQuery);
			rs.close();
			st.close();
		} 
		catch (SQLException e) 
		{
		}
		
	}
	
	// pomocna metoda pre odpalenie query
	public DefaultTableModel queryAdministration(int limit, int offset,Vector<String> inputColumnNames, String inputQuery)
	{
		Vector<String> columnNames = inputColumnNames;
		DefaultTableModel defaultTableModel;
		
		try
		{
			// vytvorenie a odpalenie query dopytu
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(inputQuery);
						
			Vector<Vector<Object>> data = new Vector<Vector<Object>>();
			ResultSetMetaData metaData = rs.getMetaData();;
			int columnCount = metaData.getColumnCount();
						
			while (rs.next()) 
			{				
				Vector<Object> vector = new Vector<Object>();
				for (int columnIndex = 1; columnIndex <= columnCount; columnIndex++) 
				{
					vector.add(rs.getObject(columnIndex));
				}
				data.add(vector);
			}
			rs.close();
			st.close();
						
			defaultTableModel = new DefaultTableModel(data, columnNames);
			return defaultTableModel;
		}
		catch (Exception e) 
		{
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
	         System.exit(0);
		}
		return null;
	}
}

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
	private DatabaseConnection databaseConnection;
	// konštruktor
	public DatabaseHandler(String DBname, String DBuser, String DBpassword) 
	{
		databaseConnection = new DatabaseConnection(connection);
		connection = databaseConnection.connectToDatabase(DBname, DBuser, DBpassword);
	}
	
//_________________________________________QUERIES_______________________________________________________
	
//________________________________________QUERIES_PRE_COMBOBOXY____________________________________________
	// metoda na vyber vsetkych DLC v databaze 
	public List<String> getAllDlc()
	{
		List<String> allString = new ArrayList();
		
		String query = "SELECT name FROM dlc";
		
		try 
		{
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
		
			while (rs.next()) allString.add(rs.getString(1));
			
			rs.close();
			st.close();
		} 
		catch (SQLException e)  {e.printStackTrace();}
		return allString;
	}
	
	public List<String> allCountry()
	{
		List<String> allString = new ArrayList();
		
		String query = "SELECT country_name FROM country";
		try 
		{
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
		
			while (rs.next()) allString.add(rs.getString(1));
			
			rs.close();
			st.close();
		} 
		catch (SQLException e)  {e.printStackTrace();}
		return allString;
	}
	
// _______________________________GET_QUERIES________________________________________________________
	//metoda na vyber dat z tabulky o cahractere podla jeho id
	public CharacterData getCharacterDataFromDBbyID(int id)
	{
		CharacterData characterData = new CharacterData();
		String query = "select c.id, c.character_name,c.create_time, c.type, c.player_id from character c "
				+ " join character_type ct on ct.id = type where c.id = "+id+";";
		
		// vytvorenie a odpalenie query dopytu
		try 
		{
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
		
			if (rs.next())
			{
				characterData.setId(rs.getInt(1));
				characterData.setCharacterName(rs.getString(2));
				characterData.setCreateTime(rs.getString(3));
				characterData.setType(rs.getInt(4));
				characterData.setPlayerID(rs.getInt(5));
			}
			rs.close();
			st.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return characterData;
	}
	
	// metoda na vybranie dat z tabulky player pre konkretne ID .
	public PlayerData getPlayerDataFromDBbyID(int id)
	{
		PlayerData playerData = new PlayerData();
		String query = "select p.id, p.country_id,p.sex, p.nickname from player p"
						+ " join country c on c.id = p.country_id where p.id = "+id+";";
		
		// vytvorenie a odpalenie query dopytu
		try 
		{
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
		
			if (rs.next())
			{
				playerData.setId(rs.getInt(1));
				playerData.setCountryInt(rs.getInt(2));
				playerData.setSex(rs.getString(3).charAt(0));
				playerData.setNickname(rs.getString(4));
			}
			rs.close();
			st.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return playerData;
	}
	
	//metoda na vybranie dat z tabulky stock_taking pre urcite ID
	public DefaultTableModel getStockDataFromDBbyID(int id)
	{
		Vector<String> columnNames = new Vector();
		columnNames.add("id");
		columnNames.add("Item");
		columnNames.add("Brand");
		
		String query = "select st.id, pi.name, pi.brand from stock_taking st JOIN pickup_item pi ON pi.id = st.pickup_item_id"
				+ " where st.character_id = "+id+";";
		
		return queryAdministrationDefaultTableModel( columnNames, query);
	}
	
	// metoda na vybranie dat z tabulky player za urcitych parametrov.
	public DefaultTableModel getPlayerDataFromDB(int limit, int offset)
	{
		Vector<String> columnNames = new Vector();
		columnNames.add("Id");
		columnNames.add("Sex");
		columnNames.add("Nickname");
		columnNames.add("Revenue from dlc");
		
		String query = "SELECT pl.id ,pl.sex,pl.nickname,coalesce(sum(d.price),0) FROM player pl  "
						 + "LEFT JOIN  	purchase pu ON 	pl.id 	= pu.player_id "
						 + "LEFT JOIN  	dlc d 		ON 	d.id 	= pu.dlc_id"
						 + " GROUP BY(pl.id) ORDER BY pl.id DESC LIMIT "+limit + " OFFSET "+ offset;
		
		return queryAdministrationDefaultTableModel( columnNames, query);
	}
	
	// metoda na pull dat o hracovej statistike podla jeho ID
	public DefaultTableModel getPlayerStatisticFromDB(int id)
	{
		Vector<String> columnNames = new Vector();
		columnNames.add("id");
		columnNames.add("Level");
		columnNames.add("Score");
		columnNames.add("Playhours");
		
		String query = "SELECT DISTINCT ps.id,l.level_name, ps.score, ps.playhours FROM player_statistic ps"
				+ " JOIN level l ON  l.id = ps.level_id WHERE ps.player_id = "+id+";";
		
		return queryAdministrationDefaultTableModel(columnNames, query);
	}
	
	// metoda na pull dat o hracovych achavementoch podla jeho ID
	public DefaultTableModel getPlayerAchiavementFromDB(int id)
	{
		Vector<String> columnNames = new Vector();
		columnNames.add("id");
		columnNames.add("Achiavement");
		columnNames.add("Earn Time");
		
		String query = " SELECT at.id,a.name, at.earn_time FROM achiavement_taking at "
				+ " JOIN achiavement a ON a.id = at.name_id WHERE at.player_id = "+id+";";
		
		return queryAdministrationDefaultTableModel(columnNames, query);
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
				+ " JOIN character_type ct ON ct.id = c.type ORDER BY c.id ASC"
				+ " LIMIT "+limit + " OFFSET "+ offset;
		
		return queryAdministrationDefaultTableModel( columnNames, query);
	}
	
	// metoda na vybranie dat z tabulky character .
	public DefaultTableModel getLevelDataFromDB(int limit, int offset)
	{
			Vector<String> columnNames = new Vector();
			columnNames.add("Id");
			columnNames.add("Belong to dlc ");
			columnNames.add("Level name");
			columnNames.add("Played hours total");
			columnNames.add("Average score");
			
			String query = " SELECT tmp.id, coalesce(d.name, \'No dlc\'), level_name,sum,round FROM "
						 + "(SELECT  l.id,l.dlc_id,l.level_name, SUM(ps.playhours), ROUND(AVG(ps.score),2) FROM level l "
						 + " JOIN player_statistic ps ON ps.level_id = l.id "
						 + " GROUP BY (l.id)) tmp LEFT JOIN dlc d ON d.id = tmp.dlc_id LIMIT "+limit + " OFFSET "+ offset;
			
			return queryAdministrationDefaultTableModel(columnNames, query);
	}
//__________________________________INSERTOVACIE_QUERIES_____________________________________________
	public boolean insertPlayer(PlayerData playerData)
	{
		String query = "INSERT INTO player (country_id, sex, nickname) "
					  + " VALUES ( "+playerData.getCountryInt()+", \'"+playerData.getSex()+"\', "
					  + " \'"+playerData.getNickname()+"\' ) ;";
		
		return transactionQueryAdministration(query);
	}
	
//__________________________________UPRAVOVACIE_QUERIES______________________________________________
	public boolean updatePlayerData(int id, PlayerData playerData)
	{
		playerData.setCountryInt(playerData.getCountryInt()+1);
		System.out.println("POZOR: ID "+id+" "+playerData.getCountryInt()+" NICK "+playerData.getNickname()+" SEX "+playerData.getSex()+";");
		String query = "UPDATE player SET country_id = "+playerData.getCountryInt()+", nickname = \'"+playerData.getNickname()+"\', sex = \'"+playerData.getSex()+"\' WHERE id = "+id+";";
		try
		{
			// vytvorenie a odpalenie query dopytu
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
		}
		catch (Exception e) {}
		
		return true;
	}
	
	//uprava charakteru
	public boolean updateCharacterData( CharacterData charData)
	{
		String query = "UPDATE character SET character_name = \'"+charData.getCharacterName()+"\', "
				+ " create_time = \'"+charData.getCreateTime()+"\', type = "+charData.getType()+" "
						+ " WHERE id = "+charData.getId()+";";
		try
		{
			// vytvorenie a odpalenie query dopytu
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
		}
		catch (Exception e) 
		{
	         System.err.println( e.getClass().getName()+": "+ e.getMessage() );
		}
		return true;
	}
	
	// uprav hracovu statistiku
	public boolean updateStaisticData(int id, int score, int playhours)
	{
	//	System.out.println("POZOR: ID "+id+" NICK "+nickname+" SEX "+sex+";");
		String query = "UPDATE player_statistic SET score = "+score+" , playhours = "+playhours+" WHERE id = "+id+";";
		try
		{
			// vytvorenie a odpalenie query dopytu
			Statement st = connection.createStatement();
			ResultSet rs = st.executeQuery(query);
		}
		catch (Exception e) {}
		return true;
	}
	
	// uprav hracove achiavementy
	public boolean updateAchiavementData(int id, String date)
	{
		String query = "UPDATE achiavement_taking SET earn_time = \'"+date+"\' WHERE id = "+id+";";
		return transactionQueryAdministration(query);
	}
		
//_________________________________________________MAZACIE_QUERIES______________-> pouzitie transakcii z dovodu zachovania integrity dat
	/*
	 * ak by sa nevymazali uplne vsetky data ohladom hraca mohlo by dojst k nespravnym statistikam-
	 */
	public void cascadePlayerById(int playerId)
	{
		String inputQuery =" delete from purchase where player_id = "+playerId +";"
				+ " DELETE FROM player_statistic where player_id = "+playerId +";"
				+ " DELETE FROM achiavement_taking where player_id = "+playerId +";"
				+ " DELETE FROM stock_taking st USING character c WHERE c.id = st.character_id"
					+ " AND c.player_id = "+playerId +";"
				+ " DELETE FROM character as char WHERE player_id = "+playerId +";"
				+ " DELETE FROM player where id = "+playerId +";";
		transactionQueryAdministration(inputQuery); // pouzitie transakcii
	}
	
	public void cascadeCharacterById(int characterId)
	{
		String inputQuery =" DELETE FROM stock_taking WHERE character_id = " +characterId+";"
						   + " DELETE FROM character as char WHERE char.id = " +characterId+";";
		transactionQueryAdministration(inputQuery); // pouzitie transakcii
	}
	
// _____________________________FILTROVACIE QUERIES____________________________________________
	public DefaultTableModel filterLevel(LevelFilterRequest lfr, int limit,int offset)
	{
		// nazvy stlpcov do tabulky
		Vector<String> columnNames = new Vector();
		columnNames.add("Id");
		columnNames.add("Belong to dlc ");
		columnNames.add("Level name");
		columnNames.add("Played hours total");
		columnNames.add("Average score");
		
		// spracovanie dat do query z LevelFilterRequest
		// having query
		String havingQuery = " HAVING ";
		int logicHoursCount = lfr.getLogicPlayedHoursChoice();
		float valueHoursCount = lfr.getPlayedHoursValue();
		int logicScore = lfr.getLogicPlayedHoursChoice();
		float valueScore = lfr.getPlayedHoursValue();
		if(logicHoursCount == 0) havingQuery += " SUM(ps.playhours) = ";
		if(logicHoursCount == 1) havingQuery += " SUM(ps.playhours) <= ";
		if(logicHoursCount == 2) havingQuery += " SUM(ps.playhours) >= ";
		havingQuery += valueHoursCount+" ";
		
		if(logicScore == 0) havingQuery += " AND ROUND(AVG(ps.score),2) = ";
		if(logicScore == 1) havingQuery += " AND ROUND(AVG(ps.score),2) <= ";
		if(logicScore == 2) havingQuery += " AND ROUND(AVG(ps.score),2) >= ";
		havingQuery += valueScore+" ";
		
		// where query
		String whereQuery = "WHERE level_name LIKE \'"+lfr.getLevelName()+"%\'";
		
		String dlcName = lfr.getDlcChoice();
		if(dlcName != "all dlc") whereQuery += " AND coalesce(d.name, \'no dlc\') =\'"+dlcName+"\' ";
				
		String query = " SELECT tmp.id, coalesce(d.name, \'no dlc\'), level_name,sum,round FROM "
				 + "(SELECT  l.id,l.dlc_id,l.level_name, SUM(ps.playhours), ROUND(AVG(ps.score),2) FROM level l "
				 + " JOIN player_statistic ps ON ps.level_id = l.id "
				 + " GROUP BY (l.id) "+havingQuery+" ) tmp LEFT JOIN dlc d ON d.id = tmp.dlc_id "+whereQuery+" "
				 + " LIMIT "+limit + " OFFSET "+ offset;
		
		return queryAdministrationDefaultTableModel(columnNames, query);
	
	}
	
	public DefaultTableModel filterPlayer(PlayerFilterRequest playerFilterRequest, int limit, int offset)
	{
		Vector<String> columnNames = new Vector();
		columnNames.add("Id");
		columnNames.add("Sex");
		columnNames.add("Nickname");
		columnNames.add("Revenue from dlc");
		
		// vytvorenie regulacnej casti query 
		String bounds = " WHERE ";
		String havingBounds = " HAVING ";
		// sex
		int sexChoice = playerFilterRequest.getSexRequest();
		if(sexChoice == 0) bounds += "pl.sex = \'F\' AND ";	
		else if(sexChoice == 1) bounds += "pl.sex = \'M\' AND ";
		else bounds += " ";	
		
		// name
		bounds += " pl.nickname LIKE \'"+playerFilterRequest.getName()+"%\'";
		
		// dlc revenue
		int logicChoice = playerFilterRequest.getLogicRequest();
		
		// money value
		if(logicChoice == 1) havingBounds += " coalesce(sum(d.price),0) <= ";
		if(logicChoice == 2) havingBounds += " coalesce(sum(d.price),0) >= ";
		havingBounds += ""+playerFilterRequest.getSumValue();
		// special event
		if(logicChoice == 0)
		{
			float value = playerFilterRequest.getSumValue();
			float downBorder = (float) (value - 0.1);
			float topBorder = (float) (value + 0.1);
			havingBounds = "HAVING coalesce(sum(d.price),0) <= "+topBorder+" "
					+ " AND coalesce(sum(d.price),0) >= "+downBorder+" ";	
		}
		
		String query = "SELECT pl.id ,pl.sex,pl.nickname,coalesce(sum(d.price),0) FROM player pl "
				 + " LEFT JOIN purchase pu ON pl.id = pu.player_id "
				 + " LEFT JOIN dlc d ON d.id = pu.dlc_id "+bounds+""
				 + " GROUP BY(pl.id) "+havingBounds+" ORDER BY pl.id LIMIT "+limit + " OFFSET "+ offset;
				
		
		return queryAdministrationDefaultTableModel(columnNames, query);
		
	}
	
//________________________________________NOSNE_FUNKCIE_PRE_QUERRIES___________________________________-
	// pomocna metoda pre odpalenie query
	public DefaultTableModel queryAdministrationDefaultTableModel(Vector<String> inputColumnNames, String inputQuery)
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
	
	public boolean transactionQueryAdministration(String query)
	{	
		// transakcie
		try 
		{
			Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
			connection.setAutoCommit(false);
			stmt.executeUpdate(query);
			//connection.prepareStatement(inputQuery);
			connection.commit();
			stmt.close();
		} 
		catch (SQLException e) 
		{
			try 
			{
				connection.rollback();
			} 
			catch (SQLException e1) 
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		try 
		{
			connection.setAutoCommit(true);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
}

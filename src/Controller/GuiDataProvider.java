import java.util.List;

import javax.swing.table.DefaultTableModel;
// trieda na sprostredkovanie prenosov dat medzi najnizsou a najvyssou vrstvou

public class GuiDataProvider 
{
		// singletonova trieda , potrebujem len jedneho prostrednika medzi DB a GUI
		public static final GuiDataProvider instance = new GuiDataProvider(); // singleton pre dostupnost vzdy a vsade
		private DatabaseHandler databaseHandler; // inštancia najnizsej vrstvy aplikacie
		
		// konstruktor
		private GuiDataProvider() 
		{
			databaseHandler = new DatabaseHandler("DBS", "postgres", "mato1996");
		}
		
//____________________________GET&DTM________________________________________________
		// pull z databazy pre player
		public DefaultTableModel getPlayerDataForTable(int pageSize, int page)
		{
			int offset = pageSize*page - pageSize;
			return databaseHandler.getPlayerDataFromDB(pageSize, offset);
		}
		
		// dáta charakteru naplnené v DTM
		public DefaultTableModel getCharacterDataForTable(int pageSize, int page)
		{
			int offset = pageSize*page - pageSize;
			return databaseHandler.getCharacterDataFromDB(pageSize, offset);
		}
		
		// dáta levelu naplnene v DTM
		public DefaultTableModel getLevelDataForTable(int pageSize, int page)
		{
			int offset = pageSize*page - pageSize;
			return databaseHandler.getLevelDataFromDB(pageSize, offset);
		}
		
		// detaily z databazy o hracovych statistikach naplnene v DTM
		
		public DefaultTableModel getPlayerStatisticByID(int id)
		{		
			return databaseHandler.getPlayerStatisticFromDB(id);
		}
		
		//detaily z databazy o hracovych achiavementoch
		public DefaultTableModel getPlayerAchiavementByID(int id)
		{
			return databaseHandler.getPlayerAchiavementFromDB(id);
		}
		
		// data z databazy o inventare postavy
		public DefaultTableModel getStockDataByID(int id)
		{
			return databaseHandler.getStockDataFromDBbyID(id);
		}
		
		//  vyfiltruj data player
		public DefaultTableModel filterPlayer(PlayerFilterRequest playerFilterRequest, int pageSize, int page)
		{
			int offset = pageSize*page - pageSize;
			return databaseHandler.filterPlayer(playerFilterRequest,pageSize,offset);
		}
		
		//vyfiltruj data pre level
		public DefaultTableModel filterLevel(LevelFilterRequest lfr, int pageSize,int page)
		{
			int offset = pageSize*page - pageSize;
			return databaseHandler.filterLevel(lfr,pageSize,offset);
		}

//_______________________________GET&CUSTOM_CLASS_________________________________
		// detaily z databazy o hracovi
		public PlayerData getPlayerDataByID(int id)
		{
			return databaseHandler.getPlayerDataFromDBbyID(id);
		}
		
		// detaily z databazy o postave
		public CharacterData getCharacterDataByID(int id)
		{
			return databaseHandler.getCharacterDataFromDBbyID(id);
		}
		
//_______________________________DELETE___________________________________________
		// mazanie z databazy
		public void deletePlayerDataFromTable(int playerId)
		{
			databaseHandler.cascadePlayerById(playerId);
		}
		
		// vymaz charakter z databazy podla ID
		public void deleteCharacterDataFromTable(int characterId)
		{
			databaseHandler.cascadeCharacterById(characterId);
		}
		

//___________________________________UPDATE______________________________________________
		// updatni player podla zadanej info
		public void updatePlayer(List<PlayerAchiavement> playerAchiav,List<PlayerStatistic> playerStat, PlayerData playerData)
		{
			databaseHandler.updatePlayerData(playerData.getId(),playerData);
			for (PlayerStatistic temp : playerStat)  databaseHandler.updateStaisticData(temp.getId(),temp.getScore(),temp.getPlayhours());
			for (PlayerAchiavement temp : playerAchiav)databaseHandler.updateAchiavementData(temp.getId(),temp.getEarnTime());
		}
		
		// updatni data o charaktere
		public void updateCharacter(CharacterData charData)
		{
			databaseHandler.updateCharacterData(charData);
		}
		
//_____________________________________INSERTY____________________________________________________
		// vlozenie hraca do databazy
		public boolean insertPlayerToDB(PlayerData playerData)
		{
			return databaseHandler.insertPlayer(playerData);
		}
	
//_____________________________________DATA_PRE_COMBOBOXY_________________________________________
		// vsetky dlc v databaze
		public String[] getAllDlcFromDB()
		{
			// premena datoveho typu
			List<String> stringList = databaseHandler.getAllDlc();
			String[] stringArray = new String[stringList.size()+2];
			for(int i = 0 ; i < stringList.size(); i++) stringArray[i] = stringList.get(i);
			
			stringArray[stringList.size()]="no dlc";
			stringArray[stringList.size()+1]="all dlc";
			
			return stringArray;
		}
		
		//vsetky krajiny v databaze
		public String[] getAllCountryFromDB()
		{
			List<String> stringList = databaseHandler.allCountry();
			String[] stringArray = new String[stringList.size()];
			for(int i = 0 ; i < stringList.size(); i++) stringArray[i] = stringList.get(i);
			
			return stringArray;
		}	
}


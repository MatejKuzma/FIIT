import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableModel;

public class GuiDataProvider 
{
		DatabaseHandler databaseHandler;
		// konstruktor
		public GuiDataProvider() 
		{
			databaseHandler = new DatabaseHandler();
			databaseHandler.connectToDatabase("DBS", "postgres", "mato1996");
		}
		
		// pull z databazy
		public DefaultTableModel getPlayerDataForTable(int pageSize, int page)
		{
			int offset = pageSize*page - pageSize;
			return databaseHandler.getPlayerDataFromDB(pageSize, offset);
		}
		
		public DefaultTableModel getCharacterDataForTable(int pageSize, int page)
		{
			int offset = pageSize*page - pageSize;
			return databaseHandler.getCharacterDataFromDB(pageSize, offset);
		}
		
		public DefaultTableModel getLevelDataForTable(int pageSize, int page)
		{
			int offset = pageSize*page - pageSize;
			return databaseHandler.getLevelDataFromDB(pageSize, offset);
		}
		
		// mazanie z databazy
		public void deletePlayerDataFromTable(int playerId)
		{
			databaseHandler.cascadePlayerById(playerId);
		}
		
		public void deleteCharacterDataFromTable(int characterId)
		{
			databaseHandler.cascadeCharacterById(characterId);
		}
		
}


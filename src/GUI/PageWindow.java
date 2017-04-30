
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PageWindow extends JFrame
{
	private int actualPage; // aktualna strana v strankovani
	static PlayerFilterRequest pfr; // trieda pre filtrovanie hraèov
	static LevelFilterRequest lfr; // trieda pre filtrovanie levelov
	
	//premenne potrebne na spravu filtrovania
	private boolean filtering;
	private int filteringPage;
	
	JFrame jFrame;
	JTable table;
	JScrollPane jScrollPane;
	private JTextField textFieldPageSize;
	private JButton btnSetPageSize;
	private JLabel lblNewLabel;
	private JTextField textFieldPage;
	private JButton btnPerviousPage;
	private JButton btnNextPage;
	private JButton btnGoToPage;
	private JLabel lblErrorPageDownBound;
	private JLabel lblNewLabelDataDeleted;
	private JButton btnView;
	private JButton btnDeleteRow;
	private JButton btnFilterData;
	private JComboBox comboBox;
	private JButton btnCeaseDataFilter;
	
	// dalsie gui okna 
	PlayerFilterWindow playerFilterWindow;
	LevelFilterWindow levelFilterWindow;
	 
	// vsetky potrebne stringove polia
	private String[] dlcStringField; 
	private String[] countryStringField;
	private String[] sexStringField = {"F","M", "F&M"};
	private String[] basicSexStringField = {"F","M"};
	private JButton btnInsertRow;
	
	// singletonova trieda , potrebujem len jedneho prostrednika medzi DB a GUI
	public static final PageWindow instance = new PageWindow(); 
	
	private PageWindow() 
	{	
		
	}
	
	public void showPageWindow() 
	{	
		//naplnanie stringovych poli
		dlcStringField = GuiDataProvider.instance.getAllDlcFromDB();
		countryStringField = GuiDataProvider.instance.getAllCountryFromDB();
		
		actualPage = 1;
		filtering = false;
		pfr = new PlayerFilterRequest();
		lfr = new LevelFilterRequest();
		
		// FRAME
		jFrame = new JFrame();
		jFrame.setTitle("Database table");
		jFrame.setSize(700, 700);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	     
		//SCROLLOVATELNA TABULKA
	     table = new JTable();
	     jScrollPane = new JScrollPane(table);
	     jScrollPane.setBounds(24, 81, 485, 460);
		 jFrame.getContentPane().setLayout(null);
		 jFrame.getContentPane().add(jScrollPane);
		 
		 textFieldPageSize = new JTextField();
		 textFieldPageSize.setBounds(241, 25, 50, 20);
		 textFieldPageSize.setText("100");
		 textFieldPageSize.setColumns(10);
		 jFrame.getContentPane().add(textFieldPageSize);
		 
		 btnSetPageSize = new JButton("Set page size & pull data ");
		 btnSetPageSize.setBounds(301, 24, 192, 23);
		 jFrame.getContentPane().add(btnSetPageSize);
		 
		 lblNewLabel = new JLabel("Page size:");
		 lblNewLabel.setFont(new Font("Sylfaen", Font.PLAIN, 15));
		 lblNewLabel.setBounds(172, 27, 79, 17);
		 jFrame.getContentPane().add(lblNewLabel);
		 
		 textFieldPage = new JTextField();
		 textFieldPage.setBounds(228, 552, 50, 20);
		 jFrame.getContentPane().add(textFieldPage);
		 textFieldPage.setColumns(10);
		 
		 btnPerviousPage = new JButton("<");
		 btnPerviousPage.setFont(new Font("Tahoma", Font.BOLD, 15));
		 btnPerviousPage.setBounds(117, 551, 48, 23);
		 jFrame.getContentPane().add(btnPerviousPage);
		 
		 btnNextPage = new JButton(">");
		 btnNextPage.setFont(new Font("Tahoma", Font.BOLD, 15));
		 btnNextPage.setBounds(172, 551, 48, 23);
		 jFrame.getContentPane().add(btnNextPage);
		 
		 btnGoToPage = new JButton("Go to page");
		 btnGoToPage.setFont(new Font("Tahoma", Font.ITALIC, 11));
		 btnGoToPage.setBounds(288, 551, 100, 23);
		 jFrame.getContentPane().add(btnGoToPage);
		 
		 lblErrorPageDownBound = new JLabel("Cant go that low !");
		 lblErrorPageDownBound.setForeground(Color.RED);
		 lblErrorPageDownBound.setFont(new Font("Tahoma", Font.ITALIC, 15));
		 lblErrorPageDownBound.setBounds(196, 590, 192, 14);
		 lblErrorPageDownBound.setVisible(false);
		 jFrame.getContentPane().add(lblErrorPageDownBound);
		 
		 btnView = new JButton("View/Update row");
		 btnView.setFont(new Font("Tahoma", Font.PLAIN, 12));
		 btnView.setBounds(519, 81, 155, 36);
		 jFrame.getContentPane().add(btnView);
		 btnView.setVisible(false);
		 
		 btnDeleteRow = new JButton("Delete row");
		 btnDeleteRow.setFont(new Font("Tahoma", Font.PLAIN, 12));
		 btnDeleteRow.setBounds(519, 137, 155, 36);
		 btnDeleteRow.setVisible(false);
		 jFrame.getContentPane().add(btnDeleteRow);
		 
		 String[] comboBoxStrings = {"player table", "character table", "level table"};
		 comboBox = new JComboBox(comboBoxStrings);
		 comboBox.setBounds(24, 25, 131, 20);
		 jFrame.getContentPane().add(comboBox);
		 
		 lblNewLabelDataDeleted = new JLabel("Data succesfully deleted !");
		 lblNewLabelDataDeleted.setForeground(Color.GREEN);
		 lblNewLabelDataDeleted.setFont(new Font("Source Sans Pro Black", Font.ITALIC, 14));
		 lblNewLabelDataDeleted.setBounds(519, 172, 165, 20);
		 lblNewLabelDataDeleted.setVisible(false);
		 jFrame.getContentPane().add(lblNewLabelDataDeleted);
		 
		 btnFilterData = new JButton("Filter data");
		 btnFilterData.setFont(new Font("Tahoma", Font.PLAIN, 12));
		 btnFilterData.setBounds(519, 203, 155, 36);
		 btnFilterData.setVisible(false);
		 jFrame.getContentPane().add(btnFilterData);
		 
		 btnCeaseDataFilter = new JButton("Cease data filter");
		 btnCeaseDataFilter.setForeground(Color.RED);
		 btnCeaseDataFilter.setBounds(519, 238, 155, 23);
		 btnCeaseDataFilter.setVisible(false);
		 jFrame.getContentPane().add(btnCeaseDataFilter);
		 
		 btnInsertRow = new JButton("Insert row");
		 btnInsertRow.setVisible(false);
		 btnInsertRow.setFont(new Font("Tahoma", Font.PLAIN, 12));
		 btnInsertRow.setBounds(519, 272, 155, 36);
		 btnInsertRow.setVisible(false);
		 jFrame.getContentPane().add(btnInsertRow);
		 
		 jFrame.setVisible(true);
	
//_______________________________________BUTTON_LISTENERS______________________________________________
		
		 // tlacidla na ovladanie strankovania
		btnSetPageSize.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  setPage(chooseTableByactualComboChoice());
		  }
		});

		// tlacidlo na prechod na dalsiu stranku
		btnNextPage.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  actualPage++;
			  setPage(chooseTableByactualComboChoice());
		  }
		});
		
		// tlacidlo na prechod na predoslu stranku
		btnPerviousPage.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  actualPage--;
			  setPage(chooseTableByactualComboChoice());
		  }
		});
		
		// tlacidlo prechodu na stranu
		btnGoToPage.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  actualPage = Integer.parseInt(textFieldPage.getText());
			  setPage(chooseTableByactualComboChoice());
		  }
		});
		
		//tlacidlo na vyfiltrovanie dat
		btnFilterData.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			if(chooseTableByactualComboChoice() == 1)
			{
				filteringPage = 1;
				playerFilterWindow = new PlayerFilterWindow(btnCeaseDataFilter,table,pfr, sexStringField);
			}
			else 
			{
				filteringPage = 2;
				levelFilterWindow = new LevelFilterWindow(btnCeaseDataFilter,dlcStringField, lfr,table);
			}
			
			filtering = true;
		  }
		});
		
		// tlacidlo na skoncovanie filtrovania dat
		btnCeaseDataFilter.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			filtering = false;
			btnCeaseDataFilter.setVisible(false);
			actualPage = 1;
			setPage(chooseTableByactualComboChoice());
		  }
		});
		
		// tlacidlo na vymazanie
		btnDeleteRow.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  int dialogButton = 0;
			  int selectedRow = table.getSelectedRow();
			  
			  if(selectedRow != -1)
			  {
				  String stringWarning = "Are you sure you want to delete row and all references in other tables with ID : "+ table.getValueAt(selectedRow, 0)+ " ?" ;
				  int dialogResult = JOptionPane.showConfirmDialog (null, stringWarning,"Warning",dialogButton);
				  if(dialogResult == JOptionPane.YES_OPTION)
				  {
					  if(chooseTableByactualComboChoice() == 1)
						  GuiDataProvider.instance.deletePlayerDataFromTable( (Integer) table.getValueAt(selectedRow, 0));
					  if(chooseTableByactualComboChoice() == 2)
						  GuiDataProvider.instance.deleteCharacterDataFromTable((Integer) table.getValueAt(selectedRow, 0));
					  setPage(chooseTableByactualComboChoice());
					  lblNewLabelDataDeleted.setVisible(true);
				  }
			  }
		  }
		});
		
		// tlacidlo na podrobny prehlad prvku
		btnView.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  int tableRowID = (Integer) table.getSelectedRow();
			
			  if(tableRowID != -1)
			  {					  
					int selectedRowID = (Integer) table.getValueAt(table.getSelectedRow(), 0);;
					// podrobny pohlad na player  
				  	if(chooseTableByactualComboChoice() == 1)
				  	{
						PlayerDetailWindow playerDetailWindow;
						
							playerDetailWindow = new PlayerDetailWindow(selectedRowID, countryStringField);
				  	}
				  	if(chooseTableByactualComboChoice() == 2)
				  	{
						CharacterDetailWindow characterDetailWindow;
				  		characterDetailWindow = new CharacterDetailWindow(selectedRowID); 
				  	}
			  }
		  }
		});
		
		// tlacidlo na vlozenie noveho prvku
		btnInsertRow.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  InsertNewPlayer insertNewPlayer = new InsertNewPlayer(countryStringField, basicSexStringField);
		  }
		});
	}
	
//__________________________________________METHODS__________________________________________________
	// nastavenie strany
	private void setPage(int pageId)
	{
		if(actualPage >= 1)
		{  
			 lblErrorPageDownBound.setVisible(false);
			 DefaultTableModel dtm = null;
			 int pageSize ;
			 //velkost stranky neprekroci 500
			 if( Integer.parseInt(textFieldPageSize.getText()) > 500) 
			 {
				 textFieldPageSize.setText("500");
				 pageSize = 500;
			 }
			 else
				 pageSize = Integer.parseInt(textFieldPageSize.getText());
			 // nastavovanie GUI podla vyberu tabulky
			 if(pageId == 1) 
			 {
				 if(filtering == false)
				 {
					 dtm = GuiDataProvider.instance.getPlayerDataForTable(pageSize,actualPage);
					 btnCeaseDataFilter.setVisible(false);
				 }
				 else
					 dtm = GuiDataProvider.instance.filterPlayer(pfr, pageSize, actualPage);
				//nastavovanie viditelnosti tlacitok
				 btnView.setVisible(true);
				 btnFilterData.setVisible(true); 
				 btnDeleteRow.setVisible(true);
				 btnInsertRow.setVisible(true);
			 }
			 if(pageId == 2) 
			 {
				
				  dtm = GuiDataProvider.instance.getCharacterDataForTable(pageSize,actualPage);
				
				  //nastavovanie viditelnosti tlacitok
				  btnView.setVisible(true);
				  btnDeleteRow.setVisible(true);
				  btnFilterData.setVisible(false);
				  btnInsertRow.setVisible(false);
			 }
			 if(pageId == 3) 
			 {
				 if(filtering == false)
				 {
					 dtm = GuiDataProvider.instance.getLevelDataForTable(pageSize,actualPage);
					 btnCeaseDataFilter.setVisible(false);
				 }
				 else
					 dtm = GuiDataProvider.instance.filterLevel(lfr, pageSize, actualPage);
				 
				  //nastavovanie viditelnosti tlacitok
				  btnView.setVisible(false);
				  btnDeleteRow.setVisible(false);
				  btnFilterData.setVisible(true);
				  btnInsertRow.setVisible(false);
			 }
			 
			 // vycisti zaznamy z tabulky 
			 DefaultTableModel dm = (DefaultTableModel) table.getModel();
			 int rowCount = dm.getRowCount();
			 
			 for (int i = rowCount - 1; i >= 0; i--) {
			     dm.removeRow(i);
			 }
			 // nastav nove data pre tabulku
			 table.setModel(dtm);
			 //nastav text stranky
			 textFieldPage.setText(Integer.toString(actualPage));
			 
			 // nastavovanie viditelnosti tlacitok
			 if(actualPage == 1) btnPerviousPage.setVisible(false);
			 else btnPerviousPage.setVisible(true);
		}
		else
		{
			lblErrorPageDownBound.setVisible(true);
		}
		lblNewLabelDataDeleted.setVisible(false);
	}
	
	// metoda na vyber strany podla comboboxu
	private int chooseTableByactualComboChoice()
	{
		if ((String) comboBox.getSelectedItem() == "player table") 		return 1;
		if ((String) comboBox.getSelectedItem() == "character table") 	return 2;
		if ((String) comboBox.getSelectedItem() == "level table") 		return 3;
		return 1; //default stav
	}
	
	// na obnovenie tabulky
	public void refreshTable()
	{
		 setPage(chooseTableByactualComboChoice());
	}
}
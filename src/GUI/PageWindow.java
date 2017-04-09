
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

public class PageWindow extends JFrame
{
	GuiDataProvider dataProvider; // sprostredkuje spojenie s databazou na vyssej urovni
	private int actualPage; // aktualna strana v strankovani
	
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
	private JButton btnUpdateRow;
	private JButton btnDeleteRow;
	private JComboBox comboBox;
	
	public PageWindow() 
	{	
		dataProvider = new GuiDataProvider();
		actualPage = 1;
		
		// FRAME
		jFrame = new JFrame();
		jFrame.setTitle("Table");
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
		 
		 btnView = new JButton("View row");
		 btnView.setFont(new Font("Tahoma", Font.PLAIN, 17));
		 btnView.setBounds(519, 142, 124, 36);
		 jFrame.getContentPane().add(btnView);
		 
		 btnUpdateRow = new JButton("Update row");
		 btnUpdateRow.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		 btnUpdateRow.setBounds(519, 189, 124, 36);
		 jFrame.getContentPane().add(btnUpdateRow);
		 
		 btnDeleteRow = new JButton("Delete row");
		 btnDeleteRow.setFont(new Font("Tahoma", Font.PLAIN, 17));
		 btnDeleteRow.setBounds(519, 236, 124, 36);
		 jFrame.getContentPane().add(btnDeleteRow);
		 
		 String[] comboBoxStrings = {"player table", "character table", "level table"};
		 comboBox = new JComboBox(comboBoxStrings);
		 comboBox.setBounds(24, 25, 131, 20);
		 jFrame.getContentPane().add(comboBox);
		 
		 lblNewLabelDataDeleted = new JLabel("Data succesfully deleted !");
		 lblNewLabelDataDeleted.setForeground(Color.GREEN);
		 lblNewLabelDataDeleted.setFont(new Font("Source Sans Pro Black", Font.ITALIC, 15));
		 lblNewLabelDataDeleted.setBounds(196, 615, 192, 20);
		 lblNewLabelDataDeleted.setVisible(false);
		 jFrame.getContentPane().add(lblNewLabelDataDeleted);
		 
		 jFrame.setVisible(true);
	
		//_________________________BUTTON_LISTENERS______________________________________________
		
		 // tlacidla na ovladanie strankovania
		btnSetPageSize.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  setPage(chooseTableByactualComboChoice());
		  }
		});

		btnNextPage.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  actualPage++;
			  setPage(chooseTableByactualComboChoice());
		  }
		});
		
		btnPerviousPage.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  actualPage--;
			  setPage(chooseTableByactualComboChoice());
		  }
		});
		
		btnGoToPage.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  actualPage = Integer.parseInt(textFieldPage.getText());
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
						  dataProvider.deletePlayerDataFromTable( (Integer) table.getValueAt(selectedRow, 0));
					  if(chooseTableByactualComboChoice() == 2)
						  dataProvider.deleteCharacterDataFromTable((Integer) table.getValueAt(selectedRow, 0));
					  setPage(chooseTableByactualComboChoice());
					  lblNewLabelDataDeleted.setVisible(true);
				  }
			  }
		  }
		});
		
	}
	
	private void setPage(int pageId)
	{
		if(actualPage >= 1)
		{  
			 lblErrorPageDownBound.setVisible(false);
			 DefaultTableModel dtm = null;
			 int pageSize ;
			 if( Integer.parseInt(textFieldPageSize.getText()) > 500) 
			 {
				 textFieldPageSize.setText("500");
				 pageSize = 500;
			 }
			 else
				 pageSize = Integer.parseInt(textFieldPageSize.getText());
			 
			 if(pageId == 1) 
			 {
				  dtm = dataProvider.getPlayerDataForTable(pageSize,actualPage);
				  btnDeleteRow.setVisible(true);
			 }
			 if(pageId == 2) 
			 {
				  dtm = dataProvider.getCharacterDataForTable(pageSize,actualPage);
				  btnDeleteRow.setVisible(true);
			 }
			 if(pageId == 3) 
			 {
				  dtm = dataProvider.getLevelDataForTable(pageSize,actualPage);
				  btnDeleteRow.setVisible(false);
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
	
	private int chooseTableByactualComboChoice()
	{
		if ((String) comboBox.getSelectedItem() == "player table") 		return 1;
		if ((String) comboBox.getSelectedItem() == "character table") 	return 2;
		if ((String) comboBox.getSelectedItem() == "level table") 		return 3;
		return 1; //default stav
	}
}
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.table.DefaultTableModel;

public class LevelFilterWindow extends JFrame
{
	private JTextField textFieldLevelName;
	private JTextField textFieldPlayHoursValue;
	private JTextField textFieldAverageScoreValue;
	
	JTable filterTable;
	JButton ceaseButton;
	JFrame jFrame;
	
	public LevelFilterWindow(JButton ceaseBut,String[] dlcStringField,final LevelFilterRequest lfr, final JTable table) 
	{
		jFrame = this;
		jFrame.setSize(350,200);
		ceaseButton = ceaseBut;
		
		filterTable = table;
		getContentPane().setLayout(null);
		
		JLabel lblByDlc = new JLabel("By dlc:");
		lblByDlc.setBounds(10, 30, 155, 14);
		getContentPane().add(lblByDlc);
		
		JLabel lblNewLabel = new JLabel("By level name:");
		lblNewLabel.setBounds(10, 55, 155, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblByPlayedHours = new JLabel("By played hours in total:");
		lblByPlayedHours.setBounds(10, 80, 197, 14);
		getContentPane().add(lblByPlayedHours);
		
		JLabel lblByAverageScore = new JLabel("By average score:");
		lblByAverageScore.setBounds(10, 105, 197, 14);
		getContentPane().add(lblByAverageScore);
		
		String[] logicStrings = {"=","<=", "=>"};
		final JComboBox comboBoxAverageScore = new JComboBox(logicStrings);
		comboBoxAverageScore.setBounds(168, 102, 39, 20);
		comboBoxAverageScore.setSelectedIndex(2);
		getContentPane().add(comboBoxAverageScore);
		
		final JComboBox comboBoxPlayHours = new JComboBox(logicStrings);
		comboBoxPlayHours.setBounds(168, 77, 39, 20);
		comboBoxPlayHours.setSelectedIndex(2);
		getContentPane().add(comboBoxPlayHours);
		
		final JComboBox comboBoxDlc = new JComboBox(dlcStringField);
		comboBoxDlc.setBounds(168, 27, 110, 20);
		comboBoxDlc.setSelectedIndex(2);
		getContentPane().add(comboBoxDlc);
		
		textFieldLevelName = new JTextField();
		textFieldLevelName.setBounds(166, 52, 86, 20);
		getContentPane().add(textFieldLevelName);
		textFieldLevelName.setColumns(10);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.setBounds(10, 130, 89, 23);
		getContentPane().add(btnCancel);
		
		JButton btnFilter = new JButton("Filter");
		btnFilter.setBounds(217, 130, 89, 23);
		getContentPane().add(btnFilter);
		
		textFieldPlayHoursValue = new JTextField();
		textFieldPlayHoursValue.setBounds(217, 77, 86, 20);
		getContentPane().add(textFieldPlayHoursValue);
		textFieldPlayHoursValue.setText("0");
		textFieldPlayHoursValue.setColumns(10);
		
		textFieldAverageScoreValue = new JTextField();
		textFieldAverageScoreValue.setBounds(217, 102, 86, 20);
		textFieldAverageScoreValue.setText("0");
		getContentPane().add(textFieldAverageScoreValue);
		textFieldAverageScoreValue.setColumns(10);
		
		this.setVisible(true);
		
		btnCancel.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  jFrame.setVisible(false);
			  jFrame = null;
		  }
		});
		
		btnFilter.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
			  lfr.setDlcChoice((String) comboBoxDlc.getSelectedItem());
			  lfr.setLevelName(textFieldLevelName.getText());
			  lfr.setLogicPlayedHoursChoice(comboBoxPlayHours.getSelectedIndex());
			  lfr.setPlayedHoursValue(Float.parseFloat(textFieldPlayHoursValue.getText()));
			  lfr.setAverageScoreChoice(comboBoxAverageScore.getSelectedIndex());
			//  lfr.setAverageScoreValue(Float.parseFloat(textFieldAverageScoreValue.getSelectedText()));
			  lfr.setAverageScoreValue(0);
			  
			  DefaultTableModel dtm = GuiDataProvider.instance.filterLevel(lfr, 100, 1);
			  filterTable.setModel(dtm);
			  ceaseButton.setVisible(true);
			  
		  }
		});
	}
	
	
}

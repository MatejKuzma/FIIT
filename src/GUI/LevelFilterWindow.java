import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class LevelFilterWindow extends JFrame
{
	private JTextField textField;
	private JTextField textField_1;
	public LevelFilterWindow() {
		getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Player filtering");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblNewLabel.setBounds(148, 11, 251, 28);
		getContentPane().add(lblNewLabel);
		
		JRadioButton rdbtnRadioButtonMale = new JRadioButton("Male");
		rdbtnRadioButtonMale.setBounds(33, 94, 109, 23);
		getContentPane().add(rdbtnRadioButtonMale);
		
		JRadioButton rdbtnRadioButtonFemale = new JRadioButton("Female");
		rdbtnRadioButtonFemale.setBounds(139, 94, 109, 23);
		getContentPane().add(rdbtnRadioButtonFemale);
		
		textField = new JTextField();
		textField.setBounds(75, 67, 70, 20);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(313, 67, 86, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("by id:");
		lblNewLabel_1.setBounds(35, 70, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("by nickname:");
		lblNewLabel_2.setBounds(233, 70, 70, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("By revenue:");
		lblNewLabel_3.setBounds(35, 146, 70, 14);
		getContentPane().add(lblNewLabel_3);
	}
}

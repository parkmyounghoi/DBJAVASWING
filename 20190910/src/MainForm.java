import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MainForm extends JFrame {
	private JTable table;

	Vector<String> vec = new Vector<>();

	DefaultTableModel dtModel = new DefaultTableModel(0, vec.size());

	public MainForm() {
		setTitle("MainForm");

		setLocation(400, 200);

		// setLocationRelativeTo(null);
		setSize(487, 305);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 79, 471, 188);
		panel.setBackground(new Color(0, 0, 153));

		getContentPane().add(panel);
		panel.setLayout(null);

		JButton btnNewButton = new JButton("SelectMember");
		btnNewButton.setFont(new Font("±Ã¼­Ã¼", Font.BOLD, 17));
		btnNewButton.setBounds(300, 127, 159, 51);
		panel.add(btnNewButton);

		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				dtModel.addRow(new Object[] { "111", "222", "333" });
				dtModel.fireTableDataChanged();
			}
		});

		vec.addElement("TEST");
		vec.addElement("TEST");
		vec.addElement("TEST");

		dtModel.setColumnIdentifiers(vec);

		table = new JTable(dtModel);
		table.setModel(dtModel);
		table.setRowHeight(40);
		table.setBounds(22, 10, 252, 149);

		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(22, 10, 256, 153);

		panel.add(sp);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 471, 88);
		panel_1.setBackground(new Color(51, 51, 204));
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel = new JLabel("MainForm");
		lblNewLabel.setFont(new Font("±Ã¼­Ã¼", Font.BOLD, 30));
		lblNewLabel.setBounds(23, 10, 224, 58);
		panel_1.add(lblNewLabel);
		setVisible(true);

	}
}

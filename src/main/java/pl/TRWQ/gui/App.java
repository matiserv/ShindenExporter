package pl.TRWQ.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.FlowLayout;
import javax.swing.JFormattedTextField;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import java.awt.Button;
import javax.swing.JRadioButton;
import java.awt.Window.Type;
import javax.swing.JProgressBar;
import javax.swing.ButtonGroup;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JCheckBox;

public class App {

	private JFrame frmShindenListExporter;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private static JFormattedTextField formattedTextField;
	private static JProgressBar progressBar;
	private static JRadioButton rdbtnNewRadioButton;
	private static JRadioButton rdbtnNewRadioButton_1;
	private static Button button;
	private static JCheckBox chckbxNewCheckBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frmShindenListExporter.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmShindenListExporter = new JFrame();
		frmShindenListExporter.setResizable(false);
		frmShindenListExporter.setTitle("Eksport list dla shinden");
		frmShindenListExporter.setBounds(100, 100, 626, 188);
		frmShindenListExporter.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmShindenListExporter.getContentPane().setLayout(new MigLayout("", "[][grow]", "[][][][]"));
		
		JLabel lblNewJgoodiesLabel = DefaultComponentFactory.getInstance().createLabel("Adres url listy na shindenie");
		frmShindenListExporter.getContentPane().add(lblNewJgoodiesLabel, "cell 0 0,alignx left,aligny center");
		
		formattedTextField = new JFormattedTextField();
		formattedTextField.setToolTipText("Np. https://shinden.pl/animelist/00000-example");
		frmShindenListExporter.getContentPane().add(formattedTextField, "cell 1 0,growx");
		
		rdbtnNewRadioButton = new JRadioButton("Eksport jako JSON");
		buttonGroup.add(rdbtnNewRadioButton);
		rdbtnNewRadioButton.setSelected(true);
		frmShindenListExporter.getContentPane().add(rdbtnNewRadioButton, "flowy,cell 0 1,alignx left,aligny center");
		
		chckbxNewCheckBox = new JCheckBox("Eksportuj nie rozpoznane anime (MAL)");
		frmShindenListExporter.getContentPane().add(chckbxNewCheckBox, "cell 0 2");
		
		progressBar = new JProgressBar();
		frmShindenListExporter.getContentPane().add(progressBar, "cell 0 3,alignx center,aligny center");
		
		button = new Button("Zapisz");
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				progressBar.setValue(0);
				//Save button
				button.setEnabled(false);
				Thread runner = new Thread() {
				    public void run() {
				    	scrapList scrlist = new scrapList();
				    	scrlist.scrap();
				    }
				};
				runner.start();
			}
		});
		frmShindenListExporter.getContentPane().add(button, "cell 1 3,alignx right,aligny center");
		
		rdbtnNewRadioButton_1 = new JRadioButton("Eksport jako XML (format MAL)");
		buttonGroup.add(rdbtnNewRadioButton_1);
		frmShindenListExporter.getContentPane().add(rdbtnNewRadioButton_1, "cell 0 1,alignx left,aligny center");
	}

	public static JFormattedTextField getFormattedTextField() {
		return formattedTextField;
	}
	public static JProgressBar getProgressBar() {
		return progressBar;
	}
	public static JRadioButton getRdbtnNewRadioButton() {
		return rdbtnNewRadioButton;
	}
	public static JRadioButton getRdbtnNewRadioButton_1() {
		return rdbtnNewRadioButton_1;
	}
	public static Button getButton() {
		return button;
	}
	
	public static void setEnabled(Boolean bool) {
		getButton().setEnabled(bool);
	}
	
	public static JCheckBox getChckbxNewCheckBox() {
		return chckbxNewCheckBox;
	}
}

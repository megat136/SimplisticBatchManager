package gui.views;

import gui.presenters.ManipulationPresenter;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.BatFileModel;
import net.miginfocom.swing.MigLayout;

public class ManipulationView extends JFrame {

	private static final long serialVersionUID = 1L;
	// Presenter
	ManipulationPresenter presenter;

	// Observers
	Observer mainPrensenterObs;

	// Components
	JPanel containerPanel;
	JPanel labelPanel;
	JPanel inputPanel;
	JPanel buttonPanel;
	JButton okeyButton;
	JButton clearButton;
	JButton validateButton;
	JTextField jobNameField;
	JTextField descriptionField;
	JTextField fileNameField;
	JTextField commandArgsField;
	JTextField schedulePatternField;
	JTextField minutesField;
	JTextField hoursField;
	JTextField daysField;
	JTextField weeksField;
	JTextField monthField;
	JTextField nextExecutionTimeField;
	JCheckBox isActiveCheckBox;
	String[] labels = { "Job Name:", "Brief Description:", "File Name:",
			"Command Args:", "Execution Pattern:", "Minutes", "Hour:", "Day:",
			"Week:", "Month:", "Next Execution Time:", "isActive" };
	List<JTextField> allTextFields = new ArrayList<JTextField>();
	List<JTextField> schedulingPatternFields = new ArrayList<JTextField>();
	List<JCheckBox> checkBoxes = new ArrayList<JCheckBox>();

	public ManipulationView() {

	}

	public void initComponent() {
		/*
		 * Init JFRAME
		 */
		setTitle("Add Job");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		getContentPane().setPreferredSize(new Dimension(415, 540));
		this.setLayout(new MigLayout("wrap 2", "[]10[]", ""));

		/*
		 * Init containerPanel
		 */
		containerPanel = new JPanel(new MigLayout("", "[]5[]", ""));
		containerPanel.setBorder(BorderFactory
				.createTitledBorder("ContainerPanel"));

		/*
		 * Init labelPanel This panel contains all TextLabels
		 */

		labelPanel = new JPanel(new MigLayout("wrap 1", "[]", "[]15[]"));
		labelPanel
				.setBorder(BorderFactory.createTitledBorder("LabelContainer"));
		initLabels();

		/*
		 * Init inputPanel. This panel contains all inputFields
		 */

		inputPanel = new JPanel(new MigLayout("wrap 1", "[]", "[]11[]"));
		inputPanel
				.setBorder(BorderFactory.createTitledBorder("InputContainer"));
		initInputFields();

		/*
		 * Init buttonPanel. This panel contains all buttons
		 */

		buttonPanel = new JPanel(new MigLayout());
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Button Panel"));

		initButtons();
		initAddActionListeners();
		initCommonListeners();

		containerPanel.add(labelPanel, "cell 0 0");
		containerPanel.add(inputPanel, "cell 1 0");
		containerPanel.add(buttonPanel, "cell 0 1 3 1");
		this.getContentPane().add(containerPanel, "push,align center");
		setPresenter(ManipulationPresenter.getInstance());
		pack();
	}

	public void initComponent(BatFileModel file) {
		/*
		 * Init JFRAME
		 */
		setTitle("Add Job");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		getContentPane().setPreferredSize(new Dimension(415, 540));
		this.setLayout(new MigLayout("wrap 2", "[]10[]", ""));

		/*
		 * Init containerPanel
		 */
		containerPanel = new JPanel(new MigLayout("", "[]5[]", ""));
		containerPanel.setBorder(BorderFactory
				.createTitledBorder("ContainerPanel"));

		/*
		 * Init labelPanel This panel contains all TextLabels
		 */

		labelPanel = new JPanel(new MigLayout("wrap 1", "[]", "[]15[]"));
		labelPanel
				.setBorder(BorderFactory.createTitledBorder("LabelContainer"));
		initLabels();

		/*
		 * Init inputPanel. This panel contains all inputFields
		 */

		inputPanel = new JPanel(new MigLayout("wrap 1", "[]", "[]11[]"));
		inputPanel
				.setBorder(BorderFactory.createTitledBorder("InputContainer"));
		initInputFields();

		/*
		 * Init buttonPanel. This panel contains all buttons
		 */

		buttonPanel = new JPanel(new MigLayout());
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Button Panel"));
		initEditButtons();
		initEditActionListeners();
		initFile(file);
		initCommonListeners();

		containerPanel.add(labelPanel, "cell 0 0");
		containerPanel.add(inputPanel, "cell 1 0");
		containerPanel.add(buttonPanel, "cell 0 1 3 1");
		this.getContentPane().add(containerPanel, "push,align center");
		pack();
	}

	/*
	 * Add Event Initialization
	 */
	private void initButtons() {
		okeyButton = new JButton("Add");
		validateButton = new JButton("Validate");
		clearButton = new JButton("Clear");
		buttonPanel.add(okeyButton, "cell 0 0");
		buttonPanel.add(clearButton, "cell 1 0");
		buttonPanel.add(validateButton, "cell 2 0");
		okeyButton.setEnabled(false);

	}

	private void initInputFields() {
		jobNameField = new JTextField();
		descriptionField = new JTextField();
		fileNameField = new JTextField();
		commandArgsField = new JTextField();
		schedulePatternField = new JTextField();
		minutesField = new JTextField();
		hoursField = new JTextField();
		daysField = new JTextField();
		weeksField = new JTextField();
		monthField = new JTextField();
		nextExecutionTimeField = new JTextField();

		minutesField.setText("*");
		hoursField.setText("*");
		daysField.setText("*");
		weeksField.setText("*");
		monthField.setText("*");

		setCommandArgsField("cmd /c");

		allTextFields.add(jobNameField);
		allTextFields.add(descriptionField);
		allTextFields.add(fileNameField);
		allTextFields.add(commandArgsField);
		allTextFields.add(schedulePatternField);
		allTextFields.add(minutesField);
		allTextFields.add(hoursField);
		allTextFields.add(daysField);
		allTextFields.add(weeksField);
		allTextFields.add(monthField);
		allTextFields.add(nextExecutionTimeField);

		for (JTextField jtf : allTextFields) {
			inputPanel.add(jtf, "w 200!");
		}
		initCheckBox();
	}

	private void initLabels() {
		for (int i = 0; i < labels.length; i++) {
			JLabel textLabel = new JLabel(labels[i]);
			labelPanel.add(textLabel);
		}
	}

	private void initCheckBox() {
		isActiveCheckBox = new JCheckBox();
		inputPanel.add(isActiveCheckBox);
	}

	private void initAddActionListeners() {
		okeyButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					presenter.onAddEvent();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		clearButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				clearTextFields();

			}
		});

		validateButton.addActionListener(new ActionListener() {
			// This logic can be implemented in this class.
			public void actionPerformed(ActionEvent e) {
				presenter.onValidateEvent();

			}
		});
	}

	/*
	 * Edit Event Initialization
	 */

	public void initFile(BatFileModel file) {
		jobNameField.setText(file.getName());
		descriptionField.setText(file.getDesc());
		fileNameField.setText(file.getFileName());
		commandArgsField.setText(file.getCommandArgsAsString());
		schedulePatternField.setText(file.getExecutionPattern());
		nextExecutionTimeField.setText(file.getNextExecution());
		String[] pattern = file.getExecutionPattern().split("\\s+");
		minutesField.setText(pattern[0]);
		hoursField.setText(pattern[1]);
		daysField.setText(pattern[2]);
		weeksField.setText(pattern[3]);
		monthField.setText(pattern[4]);

	}

	public void initEditButtons() {
		okeyButton = new JButton("Edit");
		validateButton = new JButton("Validate");
		buttonPanel.add(okeyButton, "cell 0 0");
		buttonPanel.add(validateButton, "cell 1 0");

	}

	private void initEditActionListeners() {
		okeyButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					presenter.onEditEvent();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		validateButton.addActionListener(new ActionListener() {
			// This logic can be implemented in this class.
			public void actionPerformed(ActionEvent e) {
				presenter.onValidateEvent();

			}
		});

	}

	/*
	 * Common Listeners
	 */

	public void initCommonListeners() {

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					presenter.onCloseEvent();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
	}

	/*
	 * Logic
	 */
	public void clearTextFields() {
		for (JTextField jtf : allTextFields) {
			jtf.setText(" ");
		}
		minutesField.setText("*");
		hoursField.setText(" *");
		daysField.setText("*");
		weeksField.setText("*");
		monthField.setText("*");
		nextExecutionTimeField.setText(" ");
	}

	/*
	 * Setters Getters
	 */
	public void setPresenter(ManipulationPresenter presenter) {
		this.presenter = presenter;
	}

	public String getJobNameString() {
		return jobNameField.getText();
	}

	public String getDescriptionString() {
		return descriptionField.getText();
	}

	public String getFileNameString() {
		return fileNameField.getText();
	}

	public List<String> getCommandArgsAsList() {
		String[] array=commandArgsField.getText().split("\\s+");
		List<String> commandArgsList=new ArrayList<String>();
		for(int i=0;i<array.length;i++){
			commandArgsList.add(array[i]);
		}
		return commandArgsList;
	}

	public String getSchedulerPatternString() {
		return schedulePatternField.getText();
	}

	public String getMinutesField() {
		return minutesField.getText();
	}

	public String getHoursField() {
		return hoursField.getText();
	}

	public String getDaysField() {
		return daysField.getText();
	}

	public String getWeeksField() {
		return weeksField.getText();
	}

	public String getMonthsField() {
		return monthField.getText();
	}

	public String getNextExecTimeField() {
		return nextExecutionTimeField.getText();
	}

	public boolean checkBoxIsActive() {
		return isActiveCheckBox.isSelected();
	}

	public void setNextExecTimeField(String time) {
		nextExecutionTimeField.setText(time);
	}

	public void setOkeyButtonEnabled(boolean b) {
		okeyButton.setEnabled(b);
	}

	public void setExecPattern(String pattern) {
		schedulePatternField.setText(pattern);
	}

	public void setCommandArgsField(String cmd) {
		commandArgsField.setText(cmd);
	}

	public String getCommandArgsField() {
		return commandArgsField.getText();
	}
	
	public void showMessage(String message){
		JOptionPane.showMessageDialog(rootPane, message);
	}
}

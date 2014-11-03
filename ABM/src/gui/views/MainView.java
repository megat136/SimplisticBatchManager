package gui.views;

import gui.models.BatTableModel;
import gui.presenters.MainPresenter;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import models.BatFileModel;
import net.miginfocom.swing.MigLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainView extends JFrame {

	private static final long serialVersionUID = 1L;
	JPanel containerPanel;
	JPanel navigationPanel;
	JPanel tablePanel;
	JScrollPane scrollTablePanel;
	JTable table;
	BatTableModel tableModel;
	JButton okeyButton;
	JButton editButton;
	JButton deleteButton;
	MigLayout containerPanelLayout;
	MainPresenter presenter;
	Logger logger=LoggerFactory.getLogger(MainView.class);

	public MainView() throws Exception {

	}

	public void initComponent(List<BatFileModel> modelList) throws Exception {

		/*
		 * Init ViewMain JFrame
		 */
		setTitle("Batch Manager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		this.setLayout(new MigLayout("", "[]", ""));

		/*
		 * Init ContainerPanel JPanel
		 */
		containerPanel = new JPanel();
		containerPanel.setLayout(new MigLayout("wrap 2", "[100][1100]", ""));
		containerPanel.setPreferredSize(new Dimension(1200, 600)); // This will
																	// be
																	// deactivated
																	// to test!
		containerPanel.setBorder(BorderFactory
				.createTitledBorder("ContainerPanel"));
		containerPanel.setVisible(true);

		/*
		 * Init navigationPanel JPanel
		 */
		navigationPanel = new JPanel();
		navigationPanel.setLayout(new MigLayout("wrap 1", "[80]", "[]"));
		navigationPanel.setPreferredSize(new Dimension(100, 600));
		navigationPanel.setBorder(BorderFactory
				.createTitledBorder("Navigation"));

		/*
		 * Init tablePanel JPanel This Panel contains all table elements!
		 */
		tablePanel = new JPanel();
		tablePanel.setLayout(new MigLayout("wrap 1", "[1100]", ""));
		tablePanel.setPreferredSize(new Dimension(1100, 600));
		tablePanel.setBorder(BorderFactory.createTitledBorder("Table"));

		/*
		 * Inýt TableModel
		 */
		tableModel = new BatTableModel(modelList);

		/*
		 * Init table JTable
		 */
		table = new JTable();
		table.setPreferredSize(new Dimension(900, 600));

		/*
		 * Init scrollTablePanel JScrollPane This panel is wrapper for JTable
		 */
		scrollTablePanel = new JScrollPane();
		scrollTablePanel.setPreferredSize(new Dimension(1000, 600));
		scrollTablePanel.setBorder(BorderFactory
				.createTitledBorder("ScrollPane"));

		/*
		 * Init Buttons
		 */
		okeyButton = new JButton("Add");
		editButton = new JButton("Edit");
		deleteButton = new JButton("Remove");
		addActionListeners();

		/*
		 * Adding Components Each Other
		 */
		table.setModel(tableModel);
		scrollTablePanel.setViewportView(table);
		tablePanel.add(scrollTablePanel, "span");

		navigationPanel.add(okeyButton, "w 100!");
		navigationPanel.add(editButton, "w 100!");
		navigationPanel.add(deleteButton, "w 100!");

		containerPanel.add(navigationPanel);
		containerPanel.add(tablePanel);
		setPresenter(MainPresenter.getInstance());
		this.add(containerPanel);
		this.validate();
		pack();
	}

	private void addActionListeners() {
		okeyButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				getPresenter().onAddEvent();

			}
		});

		editButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				try {
					getPresenter().onEditEvent();
				} catch (Exception e1) {
					logger.error("Can't invoke onEditEvent, It's a bug!");
				}

			}
		});

		deleteButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				
					try {
						getPresenter().onDeleteEvent();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						logger.error("Can't invoke OnDeleteEvent, It's a bug!");
					}
				

			}
		});
	}

	private void setPresenter(MainPresenter presenter) {
		this.presenter = presenter;
	}

	private MainPresenter getPresenter() {
		return this.presenter;
	}

	public String getSelectedCell() {
		int selectedColumn = table.getSelectedColumn();
		int[] selectedRows = table.getSelectedRows();
		return (String) table.getValueAt(selectedRows[0], selectedColumn);
	}
	
	public String getSelectedRowName(){
		int selectedRow=table.getSelectedRow();
		return (String) table.getValueAt(selectedRow, 0);
	}


	public JTable getTable() {
		return this.table;
	}

	public void setTableAndRepaint(JTable jTable) {
		this.table=jTable;
		table.repaint();
		table.validate();
		this.repaint();
		this.pack();
	}
	
	public void fireTableDataChanged(){
		tableModel.fireTableDataChanged();
		this.repaint();
	}

}

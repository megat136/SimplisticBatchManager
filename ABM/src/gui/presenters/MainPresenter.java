package gui.presenters;

import javax.swing.SwingUtilities;

import gui.views.MainView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import accesobjects.IDataMapper;
import accesobjects.XMLDataMapper;

public class MainPresenter {

	private MainView view;
	IDataMapper dataMapper = XMLDataMapper.getInstance();
	Logger logger=LoggerFactory.getLogger(MainPresenter.class);

	public void createView() {
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					MainView newMainView = new MainView();
					newMainView.initComponent(dataMapper.findAll());
					setView(newMainView);
					setViewVisible(true);
				} catch (Exception e) {
					logger.error("Can't create main view! Check your XML file for errors!");
				}

			}
		});
	}

	public void onAddEvent() {
		ManipulationPresenter.getInstance().createView(view);
		view.setEnabled(false);
	}

	public void onDeleteEvent() throws Exception {
		String fileName = view.getSelectedCell();
		dataMapper.remove(fileName);
		view.fireTableDataChanged();
	}

	public void onEditEvent() throws Exception {
		String fileName = view.getSelectedRowName();
		ManipulationPresenter.getInstance().createView(
				dataMapper.find(fileName), this.view);
		view.setEnabled(false);
	}

	public void setView(MainView view) {
		this.view = view;
	}

	public void setViewVisible(boolean isVisible) {
		view.setVisible(isVisible);
	}

	public void setViewEnable(boolean isEnable) {
		view.setEnabled(isEnable);
	}

	public static MainPresenter getInstance() {
		return InstanceHolder.instance;
	}

	public static class InstanceHolder {
		final static MainPresenter instance = new MainPresenter();
	}

}

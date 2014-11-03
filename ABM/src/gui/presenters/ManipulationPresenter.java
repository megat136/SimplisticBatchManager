package gui.presenters;

import gui.views.MainView;
import gui.views.ManipulationView;
import it.sauronsoftware.cron4j.SchedulingPattern;

import java.util.Date;

import javax.swing.SwingUtilities;

import models.BatFileModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utils.ExecutionTimePredictor;
import accesobjects.IDataMapper;
import accesobjects.XMLDataMapper;

public class ManipulationPresenter {
	private ManipulationView view;
	private MainView mainView;
	private IDataMapper dataMapper = XMLDataMapper.getInstance();
	private static Logger logger = LoggerFactory
			.getLogger(ManipulationPresenter.class);

	public void createView(MainView view) {
		this.mainView = view;
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					ManipulationView newView = new ManipulationView();
					newView.initComponent();
					ManipulationPresenter.getInstance().setView(newView);
					setView(newView);
					newView.setPresenter(ManipulationPresenter.getInstance());
					setViewVisible(true);
				} catch (Exception e) {
					logger.error("Can't invoke Manipulation view.");
				}

			}
		});
	}

	public void createView(final BatFileModel file, MainView view) {
		this.mainView = view;
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				try {
					ManipulationView view = new ManipulationView();
					view.initComponent(file);
					setView(view);
					view.setPresenter(ManipulationPresenter.getInstance());
					setViewVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	public ManipulationPresenter() {
	}

	public void setView(ManipulationView view) {
		this.view = view;
	}

	public void setViewVisible(boolean isVisible) {
		view.setVisible(isVisible);
	}

	public void setViewEnable(boolean isEnable) {
		view.setEnabled(isEnable);
	}

	public static ManipulationPresenter getInstance() {
		return InstanceHolder.instance;
	}

	public static class InstanceHolder {
		final static ManipulationPresenter instance = new ManipulationPresenter();
	}

	public void onAddEvent() throws Exception {
		BatFileModel fileModel = new BatFileModel();
		fileModel.setName(view.getJobNameString());
		fileModel.setDesc(view.getDescriptionString());
		fileModel.setActive(view.checkBoxIsActive());
		fileModel.setCommandArgs(view.getCommandArgsAsList());
		fileModel.setExecutionPattern(view.getSchedulerPatternString());
		fileModel.setFileName(view.getFileNameString());
		fileModel.setNextExecution(view.getNextExecTimeField());
		fileModel.setCreationTime(new Date().toString());
		dataMapper.add(fileModel);
		logger.info("New model added:{}", fileModel.getName());
		onCloseEvent();
		disposeView();
	}

	public void onValidateEvent() {
		if (!view.getJobNameString().isEmpty()
				&& !view.getFileNameString().isEmpty()
				&& !view.getCommandArgsField().isEmpty()) {
			StringBuilder sb = new StringBuilder();
			sb.append(view.getMinutesField());
			sb.append(" ");
			sb.append(view.getHoursField());
			sb.append(" ");
			sb.append(view.getDaysField());
			sb.append(" ");
			sb.append(view.getWeeksField());
			sb.append(" ");
			sb.append(view.getMonthsField());
			if (SchedulingPattern.validate(sb.toString())) {

				view.setNextExecTimeField(ExecutionTimePredictor.predictNext(sb
						.toString()));
				view.setExecPattern(sb.toString());
				view.setOkeyButtonEnabled(true);
			} else {
				view.setNextExecTimeField("Wrong Scheduling Pattern");
				view.setExecPattern(sb.toString());
				view.showMessage("Validation Pattern is wrong!");
			}
		} else {
			view.showMessage("Fields can't be empty!");
		}

	}

	public void onClearEvent() {
		view.clearTextFields();
	}

	public void onEditEvent() throws Exception {
		BatFileModel fileModel = new BatFileModel();
		fileModel.setName(view.getJobNameString());
		fileModel.setDesc(view.getDescriptionString());
		fileModel.setActive(view.checkBoxIsActive());
		fileModel.setCommandArgs(view.getCommandArgsAsList());
		fileModel.setExecutionPattern(view.getSchedulerPatternString());
		fileModel.setFileName(view.getFileNameString());
		fileModel.setNextExecution(view.getNextExecTimeField());
		fileModel.setCreationTime(new Date().toString());
		dataMapper.update(fileModel);
		logger.info("Model editted:{}", fileModel.getName());
		onCloseEvent();
		disposeView();
	}

	public void onCloseEvent() throws Exception {
		mainView.fireTableDataChanged();
		mainView.setEnabled(true);
		mainView.repaint();
	}
	
	public void disposeView(){
		view.dispose();
	}

}

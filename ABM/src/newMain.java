

import gui.presenters.MainPresenter;

import java.util.Date;

import managers.SchedulerManager;
import managers.TaskCollectionManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class newMain {

	public static void main(String[] args) throws Exception {
		final Logger logger= LoggerFactory.getLogger(newMain.class);
		logger.info("Application has started {}",new Date().toString());
		SchedulerManager scheduler=SchedulerManager.getInstance();
		scheduler.registerTaskCollector(TaskCollectionManager.getInstance());
		scheduler.startScheduler();
		MainPresenter presenter=MainPresenter.getInstance();
		presenter.createView();
	}

}

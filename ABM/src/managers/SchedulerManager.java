package managers;

import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.TaskExecutor;
import models.BatFileModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import abmTasks.BatTask;
import accesobjects.IDataMapper;
import accesobjects.XMLDataMapper;

public class SchedulerManager {
	private Scheduler scheduler = new Scheduler();
	IDataMapper dataMapper = XMLDataMapper.getInstance();
	private static final Logger logger = LoggerFactory
			.getLogger(SchedulerManager.class);

	public SchedulerManager() {
		scheduler.setTimeZone(java.util.TimeZone.getDefault());
	}

	public void registerTaskCollector(TaskCollectionManager tcm) {
		scheduler.addTaskCollector(tcm);
		logger.info("Task Collector has registered.");
	}

	public void startScheduler() {
		scheduler.start();
		logger.info("Scheduler has started.");
	}

	public void stopScheduler() {
		scheduler.stop();
		logger.info("Scheduler has stopped.");
	}

	public void destroyScheduler() {
		this.scheduler = null;
	}

	public void createNewScheduler() {
		scheduler = new Scheduler();
	}

	public void registerTask(BatFileModel model) {
		scheduler.schedule(model.getExecutionPattern(), new BatTask(model));
		logger.info("Task has been registered:{}", model.getName());
	}

	public TaskExecutor[] getExecutingTasks() {
		return scheduler.getExecutingTasks();
	}

	public static SchedulerManager getInstance() {
		return InstanceHolder.instance;
	}

	public static class InstanceHolder {
		final static SchedulerManager instance = new SchedulerManager();
	}

}

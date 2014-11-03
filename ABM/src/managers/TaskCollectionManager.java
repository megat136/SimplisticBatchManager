package managers;

import it.sauronsoftware.cron4j.SchedulingPattern;
import it.sauronsoftware.cron4j.TaskCollector;
import it.sauronsoftware.cron4j.TaskTable;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import models.BatFileModel;
import abmTasks.BatTask;
import accesobjects.IDataMapper;
import accesobjects.XMLDataMapper;

public class TaskCollectionManager implements TaskCollector {
	private IDataMapper dataMapper = XMLDataMapper.getInstance();
	private List<BatFileModel> fileList;
	private static final Logger logger = LoggerFactory
			.getLogger(TaskCollectionManager.class);
	private BatTask newTask;
	private TaskTable taskTable;

	private TaskCollectionManager() {

	}
	
	public TaskTable getTasks() {
		try{
			
		
		taskTable = new TaskTable();
		fileList = dataMapper.findAll();
		for (BatFileModel batFile : fileList) {
			if (batFile.isActive()) {
				newTask = new BatTask(batFile);
				taskTable.add(
						new SchedulingPattern(batFile.getExecutionPattern()),
						newTask);
			}
		}
		}
		catch(Exception e) {
			logger.error("Can't fetch tasks from XML File",e);
			
		}
		logger.info("TaskCollector has runned.");
		return taskTable;
	}

	public static TaskCollectionManager getInstance() {
		return InstanceHolder.instance;
	}

	private static class InstanceHolder {
		final static TaskCollectionManager instance = new TaskCollectionManager();
	}
}

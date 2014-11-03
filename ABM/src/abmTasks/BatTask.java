package abmTasks;

import it.sauronsoftware.cron4j.Task;
import it.sauronsoftware.cron4j.TaskExecutionContext;

import java.io.File;

import managers.WindowsBatchManager;
import managers.interfaces.IBatchManager;
import models.BatFileModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exceptions.BatTaskException;

import accesobjects.IDataMapper;
import accesobjects.XMLDataMapper;

public class BatTask extends Task {

	private final static Logger logger = LoggerFactory.getLogger(BatTask.class);
	private String batchFileDir = System.getProperty("user.dir")
			+ "/abmfiles/batchfiles/";

	BatFileModel scheduledJob;
	IBatchManager batchMngr = WindowsBatchManager.getInstance();
	IDataMapper dataMapper = XMLDataMapper.getInstance();

	public BatTask(BatFileModel batFile) {
		File taskFile = new File(batchFileDir + batFile.getFileName());
		if (taskFile.exists()) {
			this.scheduledJob = batFile;
		} else {
			throw new BatTaskException("The task file(" + batFile.getFileName()
					+ ") is not exist! ");
		}
	}

	@Override
	public void execute(TaskExecutionContext arg0) {
		batchMngr.execute(scheduledJob);
		scheduledJob.updateNextExecTime();
		dataMapper.update(scheduledJob);
		logger.info("Task:"+scheduledJob.getName()+" has been executed.");
	}
}

package managers;

import java.io.File;
import java.io.IOException;

import managers.interfaces.IBatchManager;
import models.BatFileModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import exceptions.TaskExecutionException;

public class WindowsBatchManager implements IBatchManager {
	private String batchFileDir = System.getProperty("user.dir")
			+ "/abmfiles/batchfiles/";
	private static final Logger logger = LoggerFactory
			.getLogger(WindowsBatchManager.class);

	public boolean execute(BatFileModel _file) {
		try {
			ProcessBuilder pb = new ProcessBuilder(_file.getExecutionString());
			pb.directory(new File(batchFileDir));
			Process p = pb.start();
			p.waitFor();
			logger.info("Task has been executed", _file.getName());
			return true;
		} catch (IOException ioe) {
			throw new TaskExecutionException(ioe);
		} catch (InterruptedException ie) {
			throw new TaskExecutionException(ie);
		}
	}

	public static IBatchManager getInstance() {
		return InstanceHolder.instance;
	}

	public static class InstanceHolder {
		static final IBatchManager instance = new WindowsBatchManager();
	}

}

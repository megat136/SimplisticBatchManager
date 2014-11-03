package managers.interfaces;

import models.BatFileModel;

public interface IBatchManager {

	public boolean execute(BatFileModel scheduledJob);
	
}

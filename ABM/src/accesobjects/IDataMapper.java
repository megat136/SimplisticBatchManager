package accesobjects;

import java.util.List;

import models.BatFileModel;

public interface IDataMapper {

	public boolean add(BatFileModel _batch);
	public BatFileModel find(String name);
	public List<BatFileModel> findAll();
	public boolean update(BatFileModel updateFile);
	public boolean remove(String name);
}

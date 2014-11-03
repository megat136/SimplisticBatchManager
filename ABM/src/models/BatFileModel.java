package models;

import java.util.ArrayList;
import java.util.List;

import accesobjects.IDataMapper;
import accesobjects.XMLDataMapper;

import utils.ExecutionTimePredictor;

public class BatFileModel {

	private String jobName;
	private String desc;
	private String fileName;
	private List<String> commandArgs=new ArrayList<String>();
	private String creationTime;
	private String nextExecution;
	private String executionPattern;
	private boolean active;

	IDataMapper dataMapper = XMLDataMapper.getInstance();

	/*
	 * Important: Adding new field to this entity obligete the changer to
	 * refactor getAsRow class! It's not optional!
	 */
	public BatFileModel() {
	}

	public String getName() {
		return jobName;
	}

	public void setName(String name) {
		this.jobName = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String briefDesc) {
		this.desc = briefDesc;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "\r\n" + jobName + "\r\n" + "BriefDescription= " + desc + "\r\n"
				+ "FileName= " + fileName + "\r\n" + "CommandArgs= "
				+ commandArgs + "\r\n" + "FirstExecutionTime= " + creationTime
				+ "\r\n" + "NextExecutionTime= " + nextExecution + "\r\n"
				+ "ExecutionPattern= " + executionPattern + "\r\n";

	}

	public Object[] getAsRow() {
		Object[] row = { getName(), getDesc(), getFileName(), getCommandArgs(),
				getCreationTime(), getNextExecution(), getExecutionPattern(),
				getActiveState() };
		return row;

	}

	public String getNextExecution() {
		if (isActive()) {
			return ExecutionTimePredictor.predictNext(getExecutionPattern());
		} else
			return " ";
	}

	public void setNextExecution(String nextExecution) {
		this.nextExecution = nextExecution;
	}

	public String getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(String creationTime) {
		this.creationTime = creationTime;
	}

	public String getExecutionPattern() {
		return executionPattern;
	}

	public void setExecutionPattern(String executionPattern) {
		this.executionPattern = executionPattern;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	/*
	 * This is used for TableModel! Acts like convertor from boolean to string representation!
	 */
	public String getActiveState() {
		if (active == true) {
			return "True";
		} else
			return "False";
	}

	public void setActiveState(String state) {
		if (state.equalsIgnoreCase("True")) {
			setActive(true);
		} else
			setActive(false);
	}

	public String getAsString(List<String> stringList) {
		StringBuilder sb = new StringBuilder();
		for (String s : stringList) {
			sb.append(s);
			sb.append(" ");
		}
		return sb.toString();
	}
	
	public void updateNextExecTime(){
		this.nextExecution=ExecutionTimePredictor.predictNext(this.getExecutionPattern());
	}
	
	public List<String> getExecutionString(){
		List<String> executionList;
		executionList=getCommandArgs();
		executionList.add(getFileName());
		return executionList;
	}
	/*
	 * Command Args
	 */
	public void setCommandArgs(List<String> args) {
		this.commandArgs=args;
	}

	public List<String> getCommandArgs() {
		return this.commandArgs;
	}
	
	public void addCommandArgs(String e){
		this.commandArgs.add(e);
	}
	
	public String getCommandArgsAsString() {
		StringBuilder sb = new StringBuilder();
		for (String s : commandArgs) {
			sb.append(s);
			sb.append(" ");
		}
		return sb.toString();
	}
}

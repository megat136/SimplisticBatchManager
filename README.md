Simplistic Batch Manager
==============

General Information
------------------
SBM is designed to schedule the execution of bat. files. It uses cron-like scheduling pattern. It has simple GUI to create, edit and remove tasks. It saves all defined tasks as XML file in sub-directory(batchfiles.xml). It collects all job per minute from that file. **You should put your batch files under the batchfiles directory.** You can check logs from bm.log file.



- Click add button to create task.
- Give a unique name to job.
- Command args are first parameters that the job executed with. Default is 'cmd /c'. This means, when you execute helloworld.bat. It's execution string will be 'cmd /c helloworld.bat'.
- Scheduler pattern is the execution string. You can find examples from [cron4j manuals](http://www.sauronsoftware.it/projects/cron4j/manual.php#p02).
- You should validate your scheduling pattern before adding job.
- When you edit a job it's first version will be removed from batchfiles.xml and new version will be added.


RoadMap
-------------------
- V.1.1
	- Job will be collected by new task that will be executed by user given parameter. (Now it is one minute, you would be specify it by 30 secs.)
	- Collected jobs will be persisted on **H2 in-memory** with TaskID. It will give us more flexibility on task execution.
	- Project will be wrapped to .exe
	- Better exception handling will be implemented.
	- Batch Files will be added by GUI and it won't need to be in batcfiles directory anymore.
	- Tasks can be collected from XML files. I need to create example XML schema.
- V.2.0
	- Jobs on another computers will be scheduled, executed by main application. So we will have General Management console for batch files execution.
	- There will be preferences screen for any parametric settings.


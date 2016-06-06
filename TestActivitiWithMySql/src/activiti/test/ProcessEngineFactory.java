package activiti.test;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;

public final class ProcessEngineFactory {

	private static ProcessEngine processEngine = null;
	
	private ProcessEngineFactory(){}
	
	public static ProcessEngine getEngine()
	{
		if(processEngine == null)
		{
			ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
			processEngine = processEngineConfiguration.buildProcessEngine();
		}
		return processEngine;
	}
 }

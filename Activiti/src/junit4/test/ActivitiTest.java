package junit4.test;

import static org.junit.Assert.*;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ActivitiTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void testActivitiEnv()
	{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:activiti.cfg.xml");
		ProcessEngineConfiguration processEngineConfiguration =  (ProcessEngineConfiguration) applicationContext.getBean("processEngineConfiguration");
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		System.err.println(processEngine);
	}
	@Test
	public void getService()
	{
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:activiti.cfg.xml");
		ProcessEngineConfiguration processEngineConfiguration =  (ProcessEngineConfiguration) applicationContext.getBean("processEngineConfiguration");
		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
		RepositoryService repositoryService = processEngine.getRepositoryService();
		RuntimeService runtimeService  = processEngine.getRuntimeService();
		TaskService  taskService =  processEngine.getTaskService();
		HistoryService historyService = processEngine.getHistoryService();
		IdentityService identityService = processEngine.getIdentityService();
		FormService formService = processEngine.getFormService();
		System.out.println(repositoryService);
		System.out.println(runtimeService);
		System.out.println(taskService);
		System.out.println(historyService);
		System.out.println(identityService);
		System.out.println(formService);
	}

}

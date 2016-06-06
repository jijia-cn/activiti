package JUnit4Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramCanvas;
import org.activiti.engine.impl.bpmn.diagram.ProcessDiagramGenerator;
import org.activiti.engine.impl.cmd.DeleteDeploymentCmd;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.Configuration;

import com.sun.org.apache.xml.internal.security.Init;

public class ActivitiTest {
	
	private ProcessEngine processEngine = null;
	
	@Before
	public void Init()
	{
//		//init from code ProcessEngines
//		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
//		processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
////		processEngineConfiguration.setJdbcUrl("jdbc:oracle:thin:@192.168.11.136:1521:test");
//		processEngineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/activiti?useUnicode=true&amp;characterEncoding=utf8");
//		processEngineConfiguration.setJdbcUsername("root");
//		processEngineConfiguration.setJdbcPassword("root");
//		processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//		
//		processEngine = processEngineConfiguration.buildProcessEngine();
		
		//init from xml
		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
		processEngine = processEngineConfiguration.buildProcessEngine();
	}
	
	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Test
	public void testEnv()
	{
		System.out.println("test..");
	}
	
	@Test
	public void TestActiviti()
	{
//		ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration.createStandaloneProcessEngineConfiguration();
//		processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
////		processEngineConfiguration.setJdbcUrl("jdbc:oracle:thin:@192.168.11.136:1521:test");
//		processEngineConfiguration.setJdbcUrl("jdbc:mysql://localhost:3306/activiti?useUnicode=true&amp;characterEncoding=utf8");
//		processEngineConfiguration.setJdbcUsername("root");
//		processEngineConfiguration.setJdbcPassword("root");
//		processEngineConfiguration.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
//		
//		ProcessEngine processEngine = processEngineConfiguration.buildProcessEngine();
//		System.out.println(processEngine);
	}
	
	@Test
	public void testProcessEngine()
	{
		System.out.println(processEngine);
	}
	
	//����������
	@Test
	public void delpoy()
	{
		Deployment deployment = processEngine.getRepositoryService()
				.createDeployment()
				.addClasspathResource("JUnit4Test/Helloworld.bpmn")
				.addClasspathResource("JUnit4Test/Helloworld.png")
				.name("���Բ�������")
				.deploy();
		
		System.out.println(deployment.getId()+" name:"+deployment.getName());
	}
	//����zip������
	@Test
	public void deployZip()
	{
		
		Deployment deployment = processEngine.getRepositoryService().createDeployment().addZipInputStream(new ZipInputStream(this.getClass().getClassLoader().getResourceAsStream("JUnit4Test/Helloworld.zip")))
				.name("����ZIP��ʽ�Ĺ�����")
				.deploy();
		System.out.println(deployment.getId()+" "+deployment.getName());
	}
	
	//ɾ����������
	@Test
	public void DeleteDeploy()
	{
//		processEngine.getRepositoryService().deleteDeployment("1");
		processEngine.getRepositoryService().deleteDeploymentCascade("2501");
	}
	
	//����������
	@Test
	public void startProcess()
	{
		ProcessInstance processInstance = processEngine.getRuntimeService()
				.startProcessInstanceByKey("�����ɷ�"); 	//�Թ�������key����������
//				.startProcessInstanceById("1001");
		System.out.println("pId: "+processInstance.getId()+" acitivedId: "+processInstance.getActivityId());
	}
	//�������̱���
	@Test
	public void setVariables()
	{
		TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery().taskId("3004").singleResult();
		System.out.println(task.getProcessDefinitionId());
		taskService.setAssignee("3004","����");
		taskService.setOwner("3004","�ܾ���");
		//�������̱���
		taskService.setVariable("3004", "name", "zhangsan");
		taskService.setVariable("3004", "age", "25");
		taskService.setVariable("3004", "sex", "man");
		taskService.setVariable("3004", "weight", "80kg");
		
		task = taskService.createTaskQuery().taskId("3004").singleResult();
		System.out.println(task.toString());
	}
	//��ȡ���̱���
	@Test
	public void getVariables()
	{
		TaskService taskService = processEngine.getTaskService();
		Task task = taskService.createTaskQuery().taskId("3004").singleResult();
		System.out.println(task.getProcessDefinitionId());
		//�������̱���
		taskService.setVariable("3004", "name", "zhangsan");
		taskService.setVariable("3004", "age", "25");
		taskService.setVariable("3004", "sex", "man");
		taskService.setVariable("3004", "weight", "80kg");
		
		//��ȡ���̱���
		System.out.println("������: "+task.getAssignee());
		System.out.println("ӵ����: "+task.getOwner());
		System.out.println("name: "+taskService.getVariable("3004", "name"));
		System.out.println("age: "+taskService.getVariable("3004", "age"));
		System.out.println("sex: "+taskService.getVariable("3004", "sex"));
		System.out.println("weight: "+taskService.getVariable("3004", "weight"));
	}
	
	//��ѯ������
	@Test
	public void queryMyTask()
	{
/*		List<Task> tasks = processEngine.getTaskService().createTaskQuery()
							.list();
		if(tasks != null)
		{
			for(Task task : tasks)
			{
				System.out.println("id:"+task.getId()+" proc_in_id:"+task.getProcessInstanceId()+" owner:"+task.getOwner()+" name:"+task.getName()+" getAssignee"+task.getAssignee());
			}
		}*/
		String assignee = "����"; 	//ָ��������
//		TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery().taskName("User tasek one");
//		System.out.println(taskQuery.count());
//		List<Task> tasks = taskQuery.list();
//		for(Task task:tasks)
//			System.out.println(task.toString());
		TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery().taskAssignee(assignee);
		System.out.println(taskQuery.count());
		
	}
	
	//��������
	@Test
	public void completeTask()
	{
//		String taskIdString = "1104";
//		processEngine.getTaskService().complete(taskIdString);
//		System.out.println("�����"+taskIdString+"����");
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().taskName("User tasek one").list();
		for(Task task:tasks)
		{
			processEngine.getTaskService().complete(task.getId());
			System.out.println(task.getOwner()+" ����˵�һ�����������񣡣�");
		}
	}
	
	//4.1�������������
	@Test 
	public void completeZhangsanTask()
	{
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().taskName("Zhang San view").list();
		for(Task task:tasks)
		{
			processEngine.getTaskService().complete(task.getId());
			System.out.println(task.getProcessInstanceId()+ " ��������������񣡣�");
		}
	}
	
	//4.1�������������
	@Test 
	public void completeLisiTask()
	{
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().taskName("Li Si View").list();
		for(Task task:tasks)
		{
			processEngine.getTaskService().complete(task.getId());
			System.out.println(task.getProcessInstanceId()+" ��������ĵ����񣡣�");
		}
	}
	//�鿴���̸������鿴ͼƬ)
	@Test
	public void viewImage() throws IOException
	{
		String deployId = "2501";
		List<String> names = processEngine.getRepositoryService().getDeploymentResourceNames(deployId);
		String imageName = null;
		for(String name : names)
		{
			if(name.indexOf(".png") > 0)
			{
				imageName = name;
				break;
			}
		}
		
		System.out.println("image name: "+imageName);
		if(imageName != null)
		{
			File file = new File("./image/"+imageName);
			InputStream in = processEngine.getRepositoryService().getResourceAsStream(deployId, imageName);
			FileUtils.copyInputStreamToFile(in, file);
		}
		
	}
	
	//�鿴���°汾�����̶���
	@Test
	public void queryTheLatestVersion()
	{
		List<ProcessDefinition> processDefinitions = processEngine.getRepositoryService().createProcessDefinitionQuery().orderByProcessDefinitionKey().asc().list();
		//���˳����°汾
		Map<String, ProcessDefinition> map = new HashMap<String, ProcessDefinition>();
		
		for(ProcessDefinition processDefinition : processDefinitions)
			map.put(processDefinition.getKey(), processDefinition);
		
		//��ʾ����
		for(ProcessDefinition processDefinition : map.values())
		{
			System.out.println("id: "+processDefinition.getId()+" name: "+processDefinition.getName()+" key: "+processDefinition.getKey()+" version: "+processDefinition.getVersion());
			System.out.println("##################################################");
		}
	}
	//ɾ�����̶���
	@Test
	public void deleteByKey()
	{
		List<ProcessDefinition> lists = processEngine.getRepositoryService().createProcessDefinitionQuery().processDefinitionKey("myProcess").list();
		for(ProcessDefinition processDefinition:lists)
		{
			processEngine.getRepositoryService().deleteDeployment(processDefinition.getDeploymentId());
			System.out.println(processDefinition.getId()+" ɾ���ɹ�����");
		}
	}
	
	//************************************����parallel��*********************************////////////////
	
	//�ύҵ������
	@Test
	public void deployParallel()
	{
		Deployment deployment = processEngine.getRepositoryService().createDeployment()
				.addClasspathResource("JUnit4Test/Parallel.bpmn")
				.addClasspathResource("JUnit4Test/Parallel.png")
				.name("���Բ���ҵ����").deploy();
		System.out.println(deployment.getId() + " "+deployment.getName());
	}
	//��������
	@Test
	public void startProcessParallel()
	{
		processEngine.getRuntimeService().startProcessInstanceByKey("parallelTest"); // TaskListener
	}
	//������� ֻҪ ����������ž�������һ������ɼ���
	@Test
	public void completeProcessParallel()
	{
		//�ž���
		//processEngine.getTaskService().complete("3507");
		//������
		processEngine.getTaskService().complete("3509");
	}
	
	//************************************����Exclusive��*********************************////////////////
	
		//�ύҵ������
		@Test
		public void deployExclusive()
		{
			Deployment deployment = processEngine.getRepositoryService().createDeployment()
					.addClasspathResource("JUnit4Test/Exclusive.bpmn")
					.addClasspathResource("JUnit4Test/Exclusive.png")
					.name("���Ի���ҵ����").deploy();
			System.out.println(deployment.getId() + " "+deployment.getName());
		}
		//��������
		@Test
		public void startProcessExclusive()
		{
//			RuntimeService runtimeService = processEngine.getRuntimeService();
//			runtimeService.setVariable("exclusive:1:5104","num", 102);
//			runtimeService.startProcessInstanceByKey("exclusive");
			Map<String, Object> values = new HashMap<String, Object>();
			values.put("num", 160);
			processEngine.getRuntimeService().startProcessInstanceByKey("exclusive",values);
		}
		//������� ֻҪ ����������ž�������һ������ɼ���
		@Test
		public void completeProcessExclusive()
		{
			//�ž���
			//processEngine.getTaskService().complete("3507");
			//������
			processEngine.getTaskService().complete("5606");
		}
		
		/******Test******/
		@Test
		public void Test()
		{
			Deployment deployment = processEngine.getRepositoryService().createDeployment()
					.addClasspathResource("JUnit4Test/Test.bpmn")
					.addClasspathResource("JUnit4Test/Test.png")
					.name("����Script...").deploy();
			System.out.println(deployment.getId() + " "+deployment.getName());
			
		}
		@Test
		public void startProcessTest()
		{
			processEngine.getRuntimeService().startProcessInstanceByKey("test");
		}
		
		@Test
		public void completeTest()
		{	
		processEngine.getTaskService().complete("7708");
		}
		
		@Test
		public void generateDiagram() throws IOException
		{	
			BpmnModel bpmnModel = processEngine.getRepositoryService().getBpmnModel("test:5:7604");
			File file = new File("./image/generate3.png");
			FileUtils.copyInputStreamToFile(ProcessDiagramGenerator.generatePngDiagram(bpmnModel), file);
		}
		
		//3 ��ʷ����鿴(ĳһ�����̵�ִ�о����Ķ�������ڵ�)
		@Test
		public void queryHistoricTask() throws Exception {
			String processInstanceId = "2001";
			List<HistoricTaskInstance> htiList = processEngine.getHistoryService()
			.createHistoricTaskInstanceQuery()//������ʷ����Ĳ�ѯ
			.processInstanceId(processInstanceId )// ʹ������ʵ��id��ѯ
			//.listPage(firstResult, maxResults)// ��ҳ����
			.orderByHistoricTaskInstanceStartTime().asc()// ��������
			.list();// ִ�в�ѯ
			for (HistoricTaskInstance histirucTaskInst : htiList) {
				System.out.print("taskId:" + histirucTaskInst.getId()+"��");
				System.out.print("name:" + histirucTaskInst.getName()+"��");
				System.out.print("pdId:" + histirucTaskInst.getProcessDefinitionId()+"��");
				System.out.print("pid:" + histirucTaskInst.getProcessInstanceId()+"��");
				System.out.print("assignee:" + histirucTaskInst.getAssignee()+"��");
				System.out.print("startTime:" + histirucTaskInst.getStartTime()+"��");
				System.out.print("endTime:" + histirucTaskInst.getEndTime()+"��");
				System.out.println("duration:" + histirucTaskInst.getDurationInMillis());
			}
		}

}

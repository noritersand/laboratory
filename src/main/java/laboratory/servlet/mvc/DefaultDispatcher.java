package laboratory.servlet.mvc;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import laboratory.servlet.controller.TestController;
import laboratory.servlet.controller.fileupload.FileUploadWithOreilly;
import laboratory.servlet.controller.fileupload.FileUploadWithSpring;
import laboratory.servlet.mvc.finder.MethodFinder;
import laboratory.servlet.mvc.finder.UrlMethodFinder;
import laboratory.servlet.mvc.invoker.MethodInvoker;
import laboratory.servlet.mvc.invoker.SimpleMethodInvoker;
import laboratory.servlet.view.DefaultViewResolver;

/**
 * 
 * 
 * @since 2017-06-19
 * @author fixalot
 */
public class DefaultDispatcher extends HttpServlet {
	private static final long serialVersionUID = 6174311087878978970L;

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(DefaultDispatcher.class);

	@SuppressWarnings("unused")
	private static final String VIEW_SUFIX = ".view";
	@SuppressWarnings("unused")
	private static final String DATA_SUFIX = ".data";

	private MethodFinder methodFinder = new UrlMethodFinder();
	private MethodInvoker methodInvoker = new SimpleMethodInvoker();
	private DefaultViewResolver viewResolver = new DefaultViewResolver();

	private TestController testController = new TestController();
	private FileUploadWithSpring fileUploadWithSpring = new FileUploadWithSpring();
	private FileUploadWithOreilly fileUploadWithOreilly = new FileUploadWithOreilly();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		process(req, resp);
	}

	private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		 * setCharacterEncoding을 호출하기 전에 request에 접근하면(정확히 뭘 건드리는게 문제인지는 모르겠지만) 나중에 인코딩을 적용해도 소용이 없다. 따라서 setCharacterEncoding 호출은 filter
		 * 레벨에서 하는게 적절함.
		 * 
		 * req.setCharacterEncoding("UTF-8");
		 */
		List<Object> instanceList = new LinkedList<>();
		instanceList.add(testController);
		instanceList.add(fileUploadWithSpring);
		instanceList.add(fileUploadWithOreilly);
		
		Object[] object = methodFinder.findMethod(instanceList, request, response);
		Object instance = object[0];
		Method method = (Method) object[1];

		Object responseObject = methodInvoker.invoke(instance, method, request, response);
		
		viewResolver.sendResponse(responseObject, request, response);
	}
}
package web.context;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.reflections.Reflections;

import web.controls.Component;

public class ApplicationContext {

Hashtable<String, Object> objTable = new Hashtable<String, Object>();
	
	public Object getBean(String key) {
		//key(주소)에 대응하는 page컨트롤러 객체 리턴 
		return objTable.get(key);
	}
	
	//생성자 
	public ApplicationContext(String propertiesPath) throws Exception{
		
		// 프로퍼티 파일 목록 읽어오기 
		Properties props = new Properties();
		props.load(new FileReader(propertiesPath));
		
		prepareObjects(props);	//객체 생성 
		prepareAnnotationObjects();
		//listener에서 setMemberDao로 주입하던 것을 
		injectDependency();		//memberDao 객체를 pageController에 주입 
		
	}
	
	private void prepareObjects(Properties props) throws Exception{
		Context ctx = new InitialContext();
		String key = null;
		String value = null;
		
		for(Object item : props.keySet()) {
			key = (String)item;
			value = props.getProperty(key);
			
			// tomcat의 DataSource객체를 찾아서 저장 
			if(key.startsWith("jndi.")) {
            //key = jndi.dataSource / value = java:comp/env/jdbc/kntting
				objTable.put(key, ctx.lookup(value));
			} else{
				// 나머지 클래스들은 직접 객체를 생성한다 
				objTable.put(key, Class.forName(value).newInstance());
			}
		}
		
	}
	
	private void prepareAnnotationObjects() throws Exception{
		/* Reflections(패키지) : 해당 패키지 하위를 모두 찾는다
		 * Reflections("spms") : spms 하위를 모두 찾는다
		 * Reflections("spms.controls") : spms.controls 하위를 모두 찾는다
		 * Reflections("") : classpath 하위를 모두 찾는다
		 * */
		Reflections reflector = new Reflections("");
		
		// @Component 애노테이션을 가진 type만 추출함
		// component가 등록된 클래스를 가져온다 
		Set<Class<?>> list = 
				reflector.getTypesAnnotatedWith(Component.class);
		String key = null;
		for(Class<?> clazz : list) {
			// ex) value = "/auth/login.do"
			key = clazz.getAnnotation(Component.class).value();
			// key, key의 클래스 객체를 등록 
			objTable.put(key, clazz.newInstance());
		}
	}
	
	private void injectDependency() throws Exception{
		for(String key : objTable.keySet()) {
			if(!key.startsWith("jndi.")) {
				// 객체를 callSetter 
				callSetter(objTable.get(key));
			}
		}
	}
	
	private void callSetter(Object obj) throws Exception{
		Object dependency = null;
		// obj에는 properties의 객체들이 저장되어 있음. 거기서 setter메서드를 찾기 
		for(Method m : obj.getClass().getMethods()) {
			//메서드 중에 Setter를 찾아라 
			/*DAO 객체는 set으로 시작하는 메서드가 없으므로 
			주입 대상이 되지 않는다 
			pageController 객체만 setter가 있다 
			*/
			if(m.getName().startsWith("set")) {
				/* 현재 objTable로부터 첫번째 매개변수에 해당하는 클래스 객체를 찾아라 
 					즉 DAO 객체를 찾아라 
					dependency == MemberDao 객체 
				*/
				dependency = findObjectByType(m.getParameterTypes()[0]);
				if(dependency != null) {
					// ex) 로그인컨트롤러, memberDao
				m.invoke(obj, dependency);
			}
		}
	}
	}
	
	private Object findObjectByType(Class<?> type) {
		for(Object obj : objTable.values()) {
//			type 클래스로 obj가 만들어진 것이라면 리턴 
			if(type.isInstance(obj))
				return obj;
		}
		return null;
	}

}

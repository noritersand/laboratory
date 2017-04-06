package laboratory.test.google.guava;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import com.google.common.reflect.ClassPath.ResourceInfo;

public class ClassPathTest {
//	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ClassPathTest.class);

	@Test
	public void testGetAllClasses() throws IOException {
		ClassPath classPath = ClassPath.from(this.getClass().getClassLoader());
		ImmutableSet<ClassInfo> classInfoSet = classPath.getAllClasses();
		UnmodifiableIterator<ClassInfo> iterator = classInfoSet.iterator();
		while (iterator.hasNext()) {
			ClassInfo classInfo = iterator.next();
			log.debug(String.valueOf(classInfo));
		}
	}
	
	@Test
	public void testGetAllResources() throws IOException {
		ClassPath classPath = ClassPath.from(this.getClass().getClassLoader());
		ImmutableSet<ResourceInfo> resourceInfoSet = classPath.getResources();
		UnmodifiableIterator<ResourceInfo> iterator = resourceInfoSet.iterator();
		while (iterator.hasNext()) {
			ResourceInfo resourceInfo = iterator.next();
			log.debug(String.valueOf(resourceInfo));
		}
	}
}
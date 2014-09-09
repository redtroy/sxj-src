package test;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sxj.mybatis.shard.dao.BlogMapper;
import com.sxj.mybatis.shard.entity.Blog;
import com.sxj.mybatis.shard.entity.BlogExample;

import test.manager.TestManager;

public class OldTest {

	private BeanFactory factory;

	@Before
	public void before() throws Exception {
		factory = new ClassPathXmlApplicationContext("/spring-old.xml");
	}

	@Test
	public void testMapper() {
		BlogMapper mapper = factory.getBean(BlogMapper.class);
		BlogExample ex = new BlogExample();
		ex.createCriteria().andTitleLike("%").andIdIsNotNull();

		Blog record = new Blog();
		record.setContext("aaaaaa");
		
		for(int i=0 ; i< 100; i++){
			mapper.insert(record);
		}
		
		//mapper.selectByExample(ex);
	}
	
	@Test
	public void managerTest() {
		TestManager manager = factory.getBean(TestManager.class);
		manager.test();
	}
	
}

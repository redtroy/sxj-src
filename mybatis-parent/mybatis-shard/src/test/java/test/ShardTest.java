package test;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sxj.mybatis.shard.dao.BlogMapper;
import com.sxj.mybatis.shard.entity.Blog;
import com.sxj.mybatis.shard.entity.BlogExample;

import test.manager.TestManager;

public class ShardTest {

	private BeanFactory factory;

	@Before
	public void before() throws Exception {
		factory = new ClassPathXmlApplicationContext("/spring-shard.xml");
	}

	@Test
	public void testMapper() {
		BlogMapper mapper = factory.getBean(BlogMapper.class);
		BlogExample ex = new BlogExample();
		ex.createCriteria().andTitleLike("%").andIdIsNotNull().andUserIdEqualTo(32);

		Blog record = new Blog();
		record.setContext("aaaaaa");
		record.setCreateTime(new Date());
		record.setIsUse(true);
		mapper.updateByExampleSelective(record, ex);
	}

	@Test
	public void testShard() {
		BlogMapper mapper = factory.getBean(BlogMapper.class);
		Blog record = new Blog();
		record.setContext("aaaaaa");
		record.setCreateTime(new Date());
		record.setIsUse(true);
		for (int i = 0; i < 100; i++) {
			record.setUserId(i);
			mapper.insert(record);
		}
	}

	@Test
	public void managerTest() {
		TestManager manager = factory.getBean(TestManager.class);
		manager.test();
	}

}

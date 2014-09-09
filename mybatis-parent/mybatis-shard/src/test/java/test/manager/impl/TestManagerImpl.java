package test.manager.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sxj.mybatis.shard.dao.BlogMapper;
import com.sxj.mybatis.shard.entity.Blog;

import test.manager.TestManager;

@Service
public class TestManagerImpl implements TestManager {

	@Autowired
	BlogMapper mapper;

	
	@Transactional
	public void test() {
		Blog record = new Blog();
		record.setContext("aaaaaa");
		record.setCreateTime(new Date());
		record.setIsUse(true);
		for (int i = 0; i < 100; i++) {
			record.setUserId(i);
			mapper.insert(record);
			if(i>5){
				//throw new RuntimeException();
			}
		}

	}

}

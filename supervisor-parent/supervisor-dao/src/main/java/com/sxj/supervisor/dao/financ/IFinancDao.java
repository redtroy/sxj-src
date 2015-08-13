package com.sxj.supervisor.dao.financ;

import java.sql.SQLException;
import java.util.List;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.financ.FinancEntity;
import com.sxj.util.persistent.QueryCondition;

public interface IFinancDao
{
    @Insert
    public void addFinanc(FinancEntity financ) throws SQLException;

    public List<FinancEntity> financeList(QueryCondition<FinancEntity> condition);
}

package com.sxj.supervisor.dao.financ;

import java.sql.SQLException;

import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.supervisor.entity.financ.FinancEntity;

public interface IFinancDao
{
    @Insert
    public void addFinanc(FinancEntity financ) throws SQLException;
}

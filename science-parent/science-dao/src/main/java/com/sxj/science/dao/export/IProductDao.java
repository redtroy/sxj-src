package com.sxj.science.dao.export;

import java.util.List;
import java.util.Map;

import com.sxj.mybatis.orm.annotations.BatchInsert;
import com.sxj.mybatis.orm.annotations.Get;
import com.sxj.mybatis.orm.annotations.Insert;
import com.sxj.mybatis.orm.annotations.Update;
import com.sxj.science.entity.export.ProductEntity;

public interface IProductDao
{
    @Insert
    public void addProduct(ProductEntity doc);
    
    @BatchInsert
    public void addProductList(List<ProductEntity> doc);
    
    public void deleteProduct(String[] docIds);
    
    @Update
    public void updateProduct(ProductEntity doc);
    
    @Get
    public ProductEntity getProduct(String id);

    public List<ProductEntity> getProductByDocIds(Map<String, Object> paramMap);
}

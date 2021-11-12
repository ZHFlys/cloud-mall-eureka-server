package com.zh.cloud.mall.practice.categoryproduct.service;

import com.github.pagehelper.PageInfo;
import com.zh.cloud.mall.practice.categoryproduct.model.pojo.Category;
import com.zh.cloud.mall.practice.categoryproduct.model.request.AddCategoryReq;
import com.zh.cloud.mall.practice.categoryproduct.model.vo.CategoryVO;

import java.util.List;

/**
 * 描述：     分类目录Service
 */
public interface CategoryService {

    void add(AddCategoryReq addCategoryReq);

    void update(Category updateCategory);

    void delete(Integer id);

    PageInfo listForAdmin(Integer pageNum, Integer pageSize);

    List<CategoryVO> listCategoryForCustomer(Integer parentId);
}

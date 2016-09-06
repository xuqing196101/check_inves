package yggc.dao.bms;

import yggc.model.bms.ArticleFile;

public interface ArticleFileMapper {
    int deleteByPrimaryKey(String id);

    int insert(ArticleFile record);

    int insertSelective(ArticleFile record);

    ArticleFile selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ArticleFile record);

    int updateByPrimaryKey(ArticleFile record);
}
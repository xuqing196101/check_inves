package bss.dao.sstps;

import java.util.List;

import bss.model.sstps.Contracts;

/**
* @Title:ContractsMapper 
* @Description: 
* @author Shen Zhenfei
* @date 2016-10-10上午10:13:28
 */
public interface ContractsMapper {

    Contracts selectById(String id);
    
    List<Contracts> select(Contracts contract);

}
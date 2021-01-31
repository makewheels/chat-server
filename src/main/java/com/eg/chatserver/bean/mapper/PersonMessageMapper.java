package com.eg.chatserver.bean.mapper;

import com.eg.chatserver.bean.PersonMessage;
import com.eg.chatserver.bean.PersonMessageExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PersonMessageMapper {
    long countByExample(PersonMessageExample example);

    int deleteByExample(PersonMessageExample example);

    int deleteByPrimaryKey(Long id);

    int insert(PersonMessage record);

    int insertSelective(PersonMessage record);

    List<PersonMessage> selectByExampleWithBLOBs(PersonMessageExample example);

    List<PersonMessage> selectByExample(PersonMessageExample example);

    PersonMessage selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PersonMessage record, @Param("example") PersonMessageExample example);

    int updateByExampleWithBLOBs(@Param("record") PersonMessage record, @Param("example") PersonMessageExample example);

    int updateByExample(@Param("record") PersonMessage record, @Param("example") PersonMessageExample example);

    int updateByPrimaryKeySelective(PersonMessage record);

    int updateByPrimaryKeyWithBLOBs(PersonMessage record);

    int updateByPrimaryKey(PersonMessage record);
}
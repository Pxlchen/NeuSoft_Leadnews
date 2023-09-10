package com.neusoft.wemedia.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.neusoft.model.wemedia.dtos.NewsAuthDto;
import com.neusoft.model.wemedia.dtos.WmNewsDto;
import com.neusoft.model.wemedia.pojos.WmNews;
import com.neusoft.model.wemedia.vo.WmNewsVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface WmNewsMapper  extends BaseMapper<WmNews> {

    List<WmNewsVo> AdQueryList(NewsAuthDto dto);
}

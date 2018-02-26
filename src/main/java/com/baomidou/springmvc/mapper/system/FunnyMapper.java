package com.baomidou.springmvc.mapper.system;

import com.baomidou.springmvc.common.SuperMapper;
import com.baomidou.springmvc.model.system.Funny;

public interface FunnyMapper extends SuperMapper<Funny> {
    Funny selectByGroupId(String group_id);
}

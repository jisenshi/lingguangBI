package com.lgbi.springbootinit.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgbi.springbootinit.model.entity.Userinfo;
import com.lgbi.springbootinit.service.UserinfoService;
import com.lgbi.springbootinit.mapper.UserinfoMapper;
import org.springframework.stereotype.Service;

/**
* @author SJS
* @description 针对表【userinfo】的数据库操作Service实现
* @createDate 2024-03-05 12:24:23
*/
@Service
public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper, Userinfo>
    implements UserinfoService{

}





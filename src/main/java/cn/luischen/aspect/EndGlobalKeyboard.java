package cn.luischen.aspect;

import cn.luischen.utils.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;


@Component
@Slf4j
public class EndGlobalKeyboard implements DisposableBean, ExitCodeGenerator {

    @PreDestroy
    // 系统关闭时自动执行的方法，用于保存数据
    public void destory() throws Exception {
        log.info("----------------------系统关闭前执行的操作--------------------------");
        MailUtil.stopMailNotice();
        log.info("----------------------系统关闭后执行的操作--------------------------");
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public int getExitCode() {
        return 0;
    }
}

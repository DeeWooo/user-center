package com.liyou.uc.user.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: ivywooo
 * @date:2018/3/7
 **/

public abstract class BaseUserCenterJob implements SimpleJob {

    private static final Logger logger = LoggerFactory.getLogger(BaseUserCenterJob.class);

    @Override
    public void execute(ShardingContext shardingContext) {
        Long startTime = System.currentTimeMillis();
        Exception exception = null;
        try {
            runJob(shardingContext);
        } catch (Exception ex) {
            exception = ex;
        }
        String jobName = shardingContext != null ? shardingContext.getJobName() : "测试";
        Long spentTime = System.currentTimeMillis() - startTime;
        if (exception == null) {
            logger.info("［信息］执行job：{} 成功，执行时间：{}ms", jobName, spentTime);
        } else {
            logger.error("［错误］执行job：{} 失败，执行时间：{}ms，异常：{}",
                    jobName, spentTime, exception);
        }
    }

    /**
     * 子类实现该方法，用于执行要运行的业务逻辑
     *
     * @param context job上下文
     * @throws Exception 可能抛出的异常
     */
    public abstract void runJob(ShardingContext context) throws Exception;
}

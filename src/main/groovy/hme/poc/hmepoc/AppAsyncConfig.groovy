package hme.poc.hmepoc

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync

import java.lang.reflect.Method
import java.util.concurrent.Executor
import java.util.concurrent.Executors

@Configuration
@EnableAsync
class AppAsyncConfig implements AsyncConfigurer {

    private final static Logger logger = LoggerFactory.getLogger(HmepocApplication)

    @Override
    Executor getAsyncExecutor() {
        Executors.newCachedThreadPool()
    }

    @Override
    AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return new AsyncUncaughtExceptionHandler() {
            @Override
            void handleUncaughtException(Throwable ex, Method method, Object... params) {
                logger.error('error', ex)
            }
        }
    }
}

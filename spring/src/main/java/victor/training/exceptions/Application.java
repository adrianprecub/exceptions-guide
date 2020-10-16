package victor.training.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.MessageSource;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;
import victor.training.exceptions.MyException.ErrorCode;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.Executor;


@EnableAsync
@SpringBootApplication
@Slf4j
public class Application implements AsyncConfigurer {
   public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
   }

   @Override
   public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
      return new AsyncUncaughtExceptionHandler() {
         @Override
         public void handleUncaughtException(Throwable ex, Method method, Object... params) {
            System.err.println("Async exception " + ex);
         }
      };
   }

}

@RestController
class HelloController {
   @GetMapping("hello")
   public String hello() {
      throw new MyException(ErrorCode.ERROR1);
   }
}

class MyException extends RuntimeException {
   enum ErrorCode {
      ERROR1
   }

   private final ErrorCode code;

   public MyException(ErrorCode code) {
      this.code = code;
   }

   public MyException(String message, ErrorCode code) {
      super(message);
      this.code = code;
   }

   public MyException(String message, Throwable cause, ErrorCode code) {
      super(message, cause);
      this.code = code;
   }

   public MyException(Throwable cause, ErrorCode code) {
      super(cause);
      this.code = code;
   }

   public ErrorCode getCode() {
      return code;
   }

}

@RestControllerAdvice
@RequiredArgsConstructor
class GlobalExceptionHandler {
   private final MessageSource messageSource;

   @ExceptionHandler(Exception.class)
   @ResponseStatus
   public String handle(Exception e, HttpServletRequest request) {
      return messageSource.getMessage(e.getMessage(), null, request.getLocale());
   }

   @ExceptionHandler(MyException.class)
   @ResponseStatus
   public String handleMy(MyException e, HttpServletRequest request) {
      return messageSource.getMessage(e.getCode().name(), null, request.getLocale());
   }
}
package victor.training.exceptions;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import victor.training.exceptions.MyException.ErrorCode;
import victor.training.exceptions.model.A;
import victor.training.exceptions.model.B;
import victor.training.exceptions.model.Data;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HelloService {
   private final OtherService otherService;

   public String hello(String name) {
      if (name == null) {
         throw new MyException(ErrorCode.NO_NAME_PARAM);
      }
      B b = new B();
      b.setLabel(name);
      Data data = new Data(new A(b));
      deeper(data);
      // more code
      return "Greeting: " + otherService.receive();
   }






   private void deeper(Data data) {
      // more code
      andDeeper(data);
      // more code
   }








   private void andDeeper(Data data) {
      Long interestingId = 13L; //critical for understanding the error
      // more code
      andEvenDeeper(data);
      // more code
   }








   private void andEvenDeeper(Data data) {
      // more code
      after1HourOfReading(data);
      // more code
   }










   private static final Logger log = LoggerFactory.getLogger(HelloService.class);

   @SneakyThrows
   private void after1HourOfReading(Data data) {
      otherService.save(data);
   }
}

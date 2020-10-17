package victor.training.exceptions;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;
import victor.training.exceptions.model.Data;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

@Service
public class OtherService {

   private String data; // demo side-effect. never have state in a @Service!

   public void send(Data data) throws IOException {
      try (Reader reader = new FileReader("hello.txt")) {
         this.data = IOUtils.toString(reader);
      }
      this.data += data.getA().getB().getLabel().toUpperCase();
   }

   public String receive() {
      return data;
   }
}

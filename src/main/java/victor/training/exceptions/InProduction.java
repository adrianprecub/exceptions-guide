package victor.training.exceptions;

import lombok.extern.slf4j.Slf4j;
import victor.training.exceptions.model.Customer;
import victor.training.exceptions.model.MemberCard;
import victor.training.exceptions.model.Order;

import java.util.Date;

@Slf4j
public class InProduction {

   private static Biz biz = new Biz();

   public static void main(String[] args) {
   }
}

/**
 * Created by rohit on 04/01/17.
 */
import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;


public class RunInThreadRule implements TestRule {

  @Override
  public Statement apply( Statement base, Description description ) {
    Statement result = base;
    RunInThread annotation = description.getAnnotation( RunInThread.class );
    if( annotation != null ) {
      result = new RunInThreadStatement( base );
    }
    return result;
  }
}


import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * @author kathan
 *
 */
public class HelloJob implements Job
{
	public void execute(JobExecutionContext ctx) throws JobExecutionException
	{
		System.out.println("Hello Quartz world.");
	}
}


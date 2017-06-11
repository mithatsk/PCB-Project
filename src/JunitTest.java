import java.awt.Color;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class JunitTest {
	  @Parameterized.Parameters
	  public static List<Object[]> data() {
	        return Arrays.asList(new Object[10][0]);
	    }
	@Test
	public void test() {
		connectionNode test1 = new connectionNode(Color.BLACK);
		test1.setLabelId(id);
		id++;
		connectionNode test2 = new connectionNode(Color.RED);
		test2.setLabelId(id);
		assertTrue(test1.getLabelId() < test2.getLabelId());
		assertTrue(test2.typeComp() == test1.typeComp());
		//System.out.println(test2.typeComp());
	}
	private int id =0;
}